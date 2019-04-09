package com.itql.module.util.thread;

import android.os.Process;

import com.itql.module.util.OrderUtil;

import java.util.concurrent.FutureTask;

import androidx.annotation.NonNull;

public class PriorityTask<T> extends FutureTask<T> implements Comparable<PriorityTask<?>> {
    private int mPriority;
    private int mOrder;

    public PriorityTask(Runnable runnable, T result) {
        super(runnable, result);
        if (runnable instanceof Prioritized) {
            mPriority = ((Prioritized) runnable).getPriority();
            mOrder = ((Prioritized) runnable).getOrder();
        } else {
            mPriority = Process.THREAD_PRIORITY_BACKGROUND;
            mOrder = OrderUtil.getOrder();
        }
    }

    @Override
    public int compareTo(@NonNull PriorityTask<?> o) {
        int result = mPriority - o.mPriority;
        if (result == 0) {
            result = mOrder - o.mOrder;
        }
        return result;
    }


}
