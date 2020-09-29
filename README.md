# AndroidAsync
Replacement for deprecated AsyncTask

## Using new AsyncTask:
1) Create custom task extending AsyncTask (similar to the old one)

```java
public class ExampleTask extends AsyncTask<String, Integer, String> {
    @Override
    protected void onPreExecute() {
        //Do something before doInBackground() on UI thread
    }

    @Override
    protected String doInBackground(String s) throws Exception {
        //Does some work on background thread
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        //Do something after doInBackground() on UI thread
    }

    @Override
    protected void onBackgroundError(Exception e) {
        //Handle any exception that occured while running doInBackground()
        //This is also executed on the UI thread
    }
}
```

2) Execute your task

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExampleTask exampleTask = new ExampleTask();
        exampleTask.execute("Something");
    }
}
```

3) Implement cancel() (Optional)
```java
public class ExampleTask extends AsyncTask<String, Integer, String> {
    ...

    @Override
    protected String doInBackground(String s) throws Exception {
        while (true) {
            if (isCancelled()) {
                onCancelled(); //Triggers OnCancelledListener, which can be set with setOnCancelledListener()
                break();
            }
        }
    
        return null;
    }
    
    ...
}
```

4) Implement postProgress() (Optional)
```java
public class ExampleTask extends AsyncTask<String, Integer, String> {
    ...

    @Override
    protected String doInBackground(String s) throws Exception {
        Integer progress = 0;
        while (true) {
            progress++;
            postProgress(progress);  //Triggers OnProgressListener, which can be set with setOnProgressListener()
        }
    
        return null;
    }
    
    ...
}
```
