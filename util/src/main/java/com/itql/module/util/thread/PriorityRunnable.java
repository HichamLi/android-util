package com.itql.module.util.thread;

import android.os.Process;

import com.itql.module.util.OrderUtil;

import androidx.annotation.NonNull;

public class PriorityRunnable implements Runnable, Prioritized, Comparable<PriorityRunnable> {
    private Runnable mRunnable;
    private int mPriority;
    private int mOrder;

    public PriorityRunnable(Runnable runnable) {
        this();
        mRunnable = runnable;
    }

    public PriorityRunnable() {
        this(Process.THREAD_PRIORITY_BACKGROUND);
    }

    public PriorityRunnable(int priority) {
        mPriority = Math.max(5, Math.min(Process.THREAD_PRIORITY_BACKGROUND, priority));
        mOrder = OrderUtil.getOrder();
    }

    @Override
    public int getPriority() {
        return mPriority;
    }

    @Override
    public int getOrder() {
        return mOrder;
    }

    @Override
    public void run() {
        if (mRunnable != null) mRunnable.run();
    }

    @Override
    public int compareTo(@NonNull PriorityRunnable o) {
        int result = mPriority - o.mPriority;
        if (result == 0) {
            result = mOrder - o.mOrder;
        }
        return result;
    }
}
