package bhalla.xyz.unacademy;

public interface Task<T> {
    T onExecuteTask();
    void onTaskComplete(T result);
}


