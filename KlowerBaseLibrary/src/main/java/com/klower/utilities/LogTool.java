
package com.klower.utilities;

import android.util.Log;

public class LogTool {
    public static int LOG_LEVEL = 5;
    
    public static String TAG = "Klower";

    public static void v(String tag, String msg) {
        if (LOG_LEVEL >= 5) {
            Log.v(TAG, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL >= 5) {
            Log.v(TAG, msg, tr);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_LEVEL >= 4) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL >= 4) {
            Log.d(TAG, msg, tr);
        }
    }

    public static void i(String tag, String msg) {
        if (LOG_LEVEL >= 3) {
            Log.i(TAG, msg);
        }
    }
    
    public static void i(String msg) {
        if (LOG_LEVEL >= 3) {
            Log.i(TAG, msg);
        }
    }

    static void i(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL >= 3) {
            Log.i(TAG, msg, tr);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_LEVEL >= 2) {
            Log.w(TAG, msg);
        }
    }
    
    public static void w(String msg) {
        if (LOG_LEVEL >= 2) {
            Log.w(TAG, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL >= 2) {
            Log.w(TAG, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_LEVEL >= 1) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (LOG_LEVEL >= 1) {
            Log.e(TAG, msg, tr);
        }
    }
    
    public static void e(String msg) {
        if (LOG_LEVEL >= 1) {
            Log.e(TAG, msg);
        }
    }
    
    public static void e(String tag, Throwable tr) {
        if (LOG_LEVEL >= 1) {
            Log.e(TAG, "", tr);
        }
    }

}
