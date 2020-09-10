# AndroidAsync
Replacement for deprecated AsyncTask. All activities which will execute my new AsyncTasks must extend AsyncCapableActivity.

## Disadvantages:
1) You need instance of Activity to run new AsyncTasks.
2) When the activity which owns the AsyncWorker is killed, all scheduled AsyncTasks will finish their work in background, but the onPostExecute() won't be called (the onFailure will trigger insted).

## Using new AsyncTask:
2) Create custom task extending AsyncTask (similar to the old one)

```java
public class ExampleTask extends AsyncTask<String, Integer, String> {
    @Override
    protected void onPreExecute() {
        
    }

    @Override
    protected String doInBackground(String s) throws Exception {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        
    }

    @Override
    protected void onBackgroundError(Exception e) {
        
    }
}
```

3) Execute your task

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
