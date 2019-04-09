package com.itql.module.util.thread;

import android.os.Process;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

public class ThreadPoolUtil implements RunnableCallback {
    private ExecutorService mCommonExecutor;
    private ExecutorService mHighPriorityExecutor;
    private ExecutorService mSingleExecutor;
    private Vector<RunnableCallback> mCallbacks;

    @Override
    public void beforeExecute(Thread t, Runnable r) {
        for (RunnableCallback callback : mCallbacks) {
            callback.beforeExecute(t, r);
        }
    }

    @Override
    public void afterExecute(Runnable r, Throwable t) {
        for (RunnableCallback callback : mCallbacks) {
            callback.afterExecute(r, t);
        }
    }

    private static class Holder {
        private static final ThreadPoolUtil INSTANCE = new ThreadPoolUtil();
    }

    private ThreadPoolUtil() {
        int coreSize = Runtime.getRuntime().availableProcessors();
        mCommonExecutor = new PriorityThreadPoolExecutor(1, 2 * coreSize, 60, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>(), new DefaultPriorityFactory(), this);
        mHighPriorityExecutor = new PriorityThreadPoolExecutor(1, 2 * coreSize, 60, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>(), new DefaultPriorityFactory(5), this);
        mSingleExecutor = new PriorityThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new DefaultPriorityFactory(), this);
        mCallbacks = new Vector<>();
    }

    public void addCallback(RunnableCallback callback) {
        if (!mCallbacks.contains(callback)) mCallbacks.add(callback);
    }

    public void removeCallback(RunnableCallback callback) {
        mCallbacks.remove(callback);
    }

    public static ThreadPoolUtil getInstance() {
        return Holder.INSTANCE;
    }

    public <T extends Runnable> void executeCommon(T runnable) throws RejectedExecutionException, NullPointerException {
        if (runnable instanceof PriorityRunnable) {
            mCommonExecutor.execute(runnable);
        } else {
            mCommonExecutor.execute(new PriorityRunnable(runnable));
        }
    }

    public <T extends Runnable> Future submitCommon(T runnable) throws RejectedExecutionException, NullPointerException {
        return mCommonExecutor.submit(runnable);
    }

    public <T extends Runnable> void executeHighPriority(T runnable) throws RejectedExecutionException, NullPointerException {
        if (runnable instanceof PriorityRunnable) {
            mHighPriorityExecutor.execute(runnable);
        } else {
            mHighPriorityExecutor.execute(new PriorityRunnable(runnable));
        }
    }

    public <T extends Runnable> Future submitHighPriority(T runnable) throws RejectedExecutionException, NullPointerException {
        return mHighPriorityExecutor.submit(runnable);
    }

    public <T extends Runnable> void executeSingle(T runnable) throws RejectedExecutionException, NullPointerException {
        if (runnable instanceof PriorityRunnable) {
            mSingleExecutor.execute(runnable);
        } else {
            mSingleExecutor.execute(new PriorityRunnable(runnable));
        }
    }

    public <T extends Runnable> Future submitSingle(T runnable) throws RejectedExecutionException, NullPointerException {
        return mSingleExecutor.submit(runnable);
    }

    /**
     * jdk进程优先级从低到高：0~10，
     * android进程优先级从低到高：19~-20，
     * 主线程优先级是5(jdk)，0(android),
     * 不管用哪种方式，都要保证优先级不能比主线程高
     */
    private class DefaultPriorityFactory implements ThreadFactory {
        private int mPriority = Process.THREAD_PRIORITY_BACKGROUND;

        private DefaultPriorityFactory() {
        }

        private DefaultPriorityFactory(int priority) {
            mPriority = priority;
        }

        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r) {
                @Override
                public void run() {
                    Process.setThreadPriority(mPriority);
                    super.run();
                }
            };
        }
    }
}
