package com.itql.module.util;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderUtil {
    private static final AtomicInteger ORDER = new AtomicInteger(0);

    public static int getOrder() {
        if (ORDER.get() >= Integer.MAX_VALUE) ORDER.set(0);
        return ORDER.incrementAndGet();
    }

    public static String getOrder(String prefix) {
        return prefix == null ? String.valueOf(getOrder()) : prefix + getOrder();
    }

}
