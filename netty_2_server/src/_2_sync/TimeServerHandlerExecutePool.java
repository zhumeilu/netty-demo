package _2_sync;

import java.util.concurrent.*;

/**
 * Created by zhumeilu on 17/9/3.
 */
public class TimeServerHandlerExecutePool {

    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize){

        this.executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxPoolSize,120L, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(queueSize));
    }
    public void execute(Runnable task){
        executorService.execute(task);
    }
}
