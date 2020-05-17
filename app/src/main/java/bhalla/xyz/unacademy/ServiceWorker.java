package bhalla.xyz.unacademy;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

public class ServiceWorker {

    private String name;
    private HandlerThread mThread;
    private Handler mHandler;
    private Handler mMainHandler = new Handler(Looper.getMainLooper());
    private boolean isDestroyed = false;

    public ServiceWorker(String name) {
        this.name = name;
        mThread = new HandlerThread(String.format("%s Thread", name));
        mThread.start();
        mHandler = new Handler(mThread.getLooper());
    }

    public <T> void addTask(final Task<T> t) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                final T result = t.onExecuteTask();
                if (isDestroyed) {
                    return;
                }
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        t.onTaskComplete(result);
                    }
                });
            }
        });
    }

    public void destroy() {
        isDestroyed = true;
        mHandler.getLooper().quitSafely();
    }
}
