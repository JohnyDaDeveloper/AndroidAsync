package cz.johnyapps.pocketprague.async;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AsyncWorker {
    private static final int NUMBER_OF_THREADS = 4;

    private Activity activity;
    private ExecutorService executorService;
    private List<FutureWithListener> futures;

    private boolean destroy = false;

    public AsyncWorker(final Activity activity) {
        this.activity = activity;
        executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        futures = new ArrayList<>();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                while (!destroy) {
                    for (FutureWithListener future : futures) {
                        if (future.getFuture().isDone()) {
                            future.triggerComplete(AsyncWorker.this.activity);
                            futures.remove(future);
                        }

                        if (destroy) {
                            break;
                        }
                    }
                }

                for (FutureWithListener futureWithListener : futures) {
                    futureWithListener.triggerTaskInterrupted(AsyncWorker.this.activity);
                }
            }
        });
    }

    public void destroy() {
        destroy = true;
    }

    public boolean isDestroyed() {
        return destroy;
    }

    public void execute(Callable<?> callable, OnTaskCompletedListener onTaskCompletedListener) {
        Future<?> future = executorService.submit(callable);
        futures.add(new FutureWithListener(future, onTaskCompletedListener));
    }

    public interface OnTaskCompletedListener {
        void onComplete(Object object);
        void onTaskInterrupted(Exception e);
    }

    private static class FutureWithListener {
        private Future<?> future;
        private OnTaskCompletedListener onTaskCompletedListener;

        public FutureWithListener(Future<?> future, OnTaskCompletedListener onTaskCompletedListener) {
            this.future = future;
            this.onTaskCompletedListener = onTaskCompletedListener;
        }

        public Future<?> getFuture() {
            return future;
        }

        public void triggerTaskInterrupted(Activity activity) {
            if (onTaskCompletedListener != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onTaskCompletedListener.onTaskInterrupted(new Exception("AsyncWorker was destroyed"));
                    }
                });
            }
        }

        public void triggerComplete(Activity activity) {
            if (onTaskCompletedListener != null) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            onTaskCompletedListener.onComplete(FutureWithListener.this.future.get());
                        } catch (ExecutionException | InterruptedException e) {
                            onTaskCompletedListener.onTaskInterrupted(e);
                        }
                    }
                });
            }
        }
    }
}
