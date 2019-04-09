package com.itql.module.util;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


public class UtilApplication extends Application {
	private static UtilApplication sApplication;
	private static Handler sHandler;
	private static int sTid;
	private static int sPid;

	public static Handler getHandler() {
		return sHandler;
	}

	public static int getTid() {
		return sTid;
	}

	public static int getPid() {
		return sPid;
	}

	public static Context getContext() {
		return sApplication;
	}

	public static UtilApplication getApplication() {
		return sApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		sApplication = this;
		sHandler = new Handler();
		sTid = android.os.Process.myTid();
		sPid = android.os.Process.myPid();
	}
}
