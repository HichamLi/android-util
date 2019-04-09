package com.itql.module.util.thread;

public interface RunnableCallback {
    void beforeExecute(Thread t, Runnable r);

    void afterExecute(Runnable r, Throwable t);

    class SimpleCallback implements RunnableCallback{

        @Override
        public void beforeExecute(Thread t, Runnable r) {

        }

        @Override
        public void afterExecute(Runnable r, Throwable t) {

        }
    }
}
