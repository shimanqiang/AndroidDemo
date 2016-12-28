package com.shi.weixinhot.tools;

import android.util.Log;

public class LogUtil {

	public final static int NOTHING = -1;  //表示关闭打印日志

	public final static int ERROR = 0;
	public final static int WARN = 1;
	public final static int INFO = 2;
	public final static int DEBUG = 3;
	public final static int VERBOSE = 4;

	public static int LEVEL = VERBOSE; // 日志级别选择

	public static void e(String TAG, String msg) {
		if (LogUtil.ERROR <= LEVEL) {
			Log.e(TAG, ""+msg);
		}
	}
	
	public static void e(String TAG, String msg,Throwable t) {
		if (LogUtil.ERROR <= LEVEL) {
			Log.e(TAG, ""+msg,t);
		}
	}

	public static void w(String TAG, String msg) {
		if (LogUtil.WARN <= LEVEL) {
			Log.w(TAG, ""+msg);
		}
	}

	public static void i(String TAG, String msg) {
		if (LogUtil.INFO <= LEVEL) {
			Log.i(TAG, ""+msg);
		}
	}

	public static void d(String TAG, String msg) {
		if (LogUtil.DEBUG <= LEVEL) {
			Log.d(TAG, ""+msg);
		}
	}

	public static void v(String TAG, String msg) {
		if (LogUtil.VERBOSE <= LEVEL) {
			Log.v(TAG, ""+msg);
		}
	}

}