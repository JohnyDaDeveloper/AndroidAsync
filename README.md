# AndroidAsync
Replacement for deprecated AsyncTask. All activities which will execute my new AsyncTasks must extend AsyncCapableActivity.

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
