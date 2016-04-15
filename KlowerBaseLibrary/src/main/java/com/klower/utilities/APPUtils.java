package com.klower.utilities;

import java.util.List;
import java.util.UUID;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

public class APPUtils {

	/**
	 * @Description: 得到设备密度
	 * @param context
	 * @return
	 * @return float
	 */
	public static float getDensity(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		LogTool.d("density", "density: " + metric.density);
		return metric.density;

	}
	/**
	 * @Description: 得到设备宽度
	 * @param context
	 * @return
	 * @return float
	 */
	public static int getScreenWidth(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.widthPixels;
	}

	/**
	 * @Description: 得到设备高度
	 * @param context
	 * @return
	 * @return float
	 */
	public static int getScreenHeight(Activity context) {
		DisplayMetrics metric = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return metric.heightPixels - getStatusBarHeight(context);
	}
	
	/**
	 * @Description: 获取Status Bar高度
	 * @param context
	 * @return
	 * @return int
	 */
	public static int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	
	/**
	 * 获取设备UUID
	 * 
	 * @param context
	 * @return
	 */
	public static String getUUID(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}

	/**
	 * @Description: 像素值转化为DP值
	 * @param context
	 * @param value
	 * @return
	 * @return int
	 */
	public static int getDPValue(Context context, float value) {
		int dpValue = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, context.getResources()
						.getDisplayMetrics());
		return dpValue;
	}

	public static String getVersionName(Context context) {
		String version = "1.0.1";
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	
	public static int getVersionCode(Context context) {
		int version = 1;
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
	
	public static int dpToPx(final Context context, final float dp) {
		// Took from
		// http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) ((dp * scale) + 0.5f);
	}
	
	/**
	 * Returns a valid DisplayMetrics object
	 * 
	 * @param context
	 *            valid context
	 * @return DisplayMetrics object
	 */
	public static DisplayMetrics getDisplayMetrics(final Context context) {
		final WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		final DisplayMetrics metrics = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}
	
	
	/**
	 * 
	 * 检测某Activity是否在当前Task的栈顶
	 */

	public static boolean isTopActivy(String cmdName, Context context) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1);
		String cmpNameTemp = null;
		if (null != runningTaskInfos) {
			cmpNameTemp = (runningTaskInfos.get(0).topActivity).getClassName();
		}
		if (null == cmpNameTemp)
			return false;
		return cmpNameTemp.equals(cmdName);

	}

	/**
	 * 
	 * 判断程序是否在前台
	 */
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
				if (appProcess.importance == RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
					Log.i("后台", appProcess.processName);
					return true;
				} else {
					Log.i("前台", appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}
}
