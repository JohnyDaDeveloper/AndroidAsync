package cz.johnyapps.pocketprague.async;

import androidx.appcompat.app.AppCompatActivity;

public class AsyncCapableActivity extends AppCompatActivity {
    private AsyncWorker asyncWorker = new AsyncWorker(this);

    @Override
    protected void onDestroy() {
        asyncWorker.destroy();
        super.onDestroy();
    }

    public AsyncWorker getAsyncWorker() {
        return asyncWorker;
    }
}
