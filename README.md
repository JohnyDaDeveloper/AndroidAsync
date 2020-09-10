# AndroidAsync
Replacement for deprecated AsyncTask. All activities which will execute my new AsyncTasks must extend AsyncCapableActivity.

## Disadvantages:
1) You need instance of Activity to run new AsyncTasks.
2) When the activity which owns the AsyncWorker is killed, all scheduled AsyncTasks will finish their work in background, but the onPostExecute() won't be called (the onFailure will trigger insted).

## Using new AsyncTask:
1) Make your activities which will run AsyncTasks extend AsyncCapableActivity

```java
public class MainActivity extends AsyncCapableActivity
```

2) Create custom task extending AsyncTask (similar to the old one)

```java
public class ExampleTask extends AsyncTask<String, Long> {
    public ExampleTask(AsyncWorker asyncWorker) {
        super(asyncWorker);
    }
    
    @Override
    public void onPreExecute() {
        //Stuff you want to do on the main thread before doInBackground();
    }

    @Override
    public Long doInBackground(String inputs) {
        //Stuff you want to do in background
        return null;
    }

    @Override
    public void onPostExecute(Long aLong) {
        //Stuff you want to do on the main thread with result of doInBackground();
    }

    @Override
    public void onFailure(Exception e) {
        //Handle exceptions
    }
}
```

3) Execute your task

```java
public class MainActivity extends AsyncCapableActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExampleTask exampleTask = new ExampleTask(getAsyncWorker());
        exampleTask.execute("Something");
    }
}
```
