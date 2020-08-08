package cz.johnyapps.pocketprague.async;

import java.util.concurrent.Callable;

public abstract class AsyncTask<INPUT, OUTPUT> {
    private AsyncWorker asyncWorker;

    public AsyncTask(AsyncWorker asyncWorker) {
        this.asyncWorker = asyncWorker;
    }

    public void execute(final INPUT input) {
        if (asyncWorker.isDestroyed()) {
            onFailure(new Exception("AsyncWorker already destroyed"));
            return;
        }

        onPreExecute();

        asyncWorker.execute(new Callable<OUTPUT>() {
            @Override
            public OUTPUT call() throws Exception {
                return doInBackground(input);
            }
        }, new AsyncWorker.OnTaskCompletedListener() {
            @Override
            public void onComplete(Object object) {
                onPostExecute((OUTPUT) object);
            }

            @Override
            public void onTaskInterrupted(Exception e) {
                onFailure(e);
            }
        });
    }

    public abstract void onPreExecute();

    public abstract OUTPUT doInBackground(INPUT inputs);

    public abstract void onPostExecute(OUTPUT output);

    public abstract void onFailure(Exception e);
}
