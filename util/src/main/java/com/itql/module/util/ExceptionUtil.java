package com.itql.module.util;

public class ExceptionUtil {
	public static void deal(Throwable e) {
		if (e == null) return;
		e.printStackTrace();
	}
}
