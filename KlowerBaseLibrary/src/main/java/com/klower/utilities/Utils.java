/*
 * Copyright (C) 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.klower.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.klower.twodimensioncode.PlanarYUVLuminanceSource;
import com.klower.twodimensioncode.RGBLuminanceSource;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

/**
 * Class containing some static utility methods.
 */
public class Utils {
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	private static final double EARTH_RADIUS = 6378137.0;

	private Utils() {
	};

	/**
	 * Workaround for bug pre-Froyo, see here for more info:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 */
	public static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (hasHttpConnectionBug()) {
			System.setProperty("http.keepAlive", "false");
		}
	}

	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * Check if external storage is built-in or removable.
	 * 
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return Environment.isExternalStorageRemovable();
		}
		return true;
	}

	/**
	 * Get the memory class of this device (approx. per-app memory limit)
	 * 
	 * @param context
	 * @return
	 */
	public static int getMemoryClass(Context context) {
		return ((ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
	}

	/**
	 * Check if OS version has a http URLConnection bug. See here for more
	 * information:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 
	 * @return
	 */
	public static boolean hasHttpConnectionBug() {
		return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
	}

	/**
	 * Check if ActionBar is available.
	 * 
	 * @return
	 */
	public static boolean hasActionBar() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	/**
	 * @Description: TODO
	 * @param context
	 *            * @param title: 标题
	 * @param msg
	 *            : 分享图片的附带文本信息
	 * @param filePath
	 *            ： 分享的图片路径 例如：/mnt/sdcard/icon.png
	 * @return void
	 */
	public static void shareImgTxt(Context context, String title, String msg,
			String filePath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filePath))); // 传输图片或者文件
																				// 采用流的方式
		intent.putExtra(Intent.EXTRA_TEXT, msg); // 附带的说明信息
		intent.putExtra(Intent.EXTRA_SUBJECT, title);
		intent.setType("image/*"); // 分享图片
		context.startActivity(Intent.createChooser(intent, "分享"));
	}

	/**
	 * 获取图片宽高
	 * 
	 * @param url
	 * @return
	 */
	public static int[] getImageWH(String url) {
		String[] wh = new String[2];
		String whString = null;
		if (url.indexOf("#") != -1) {
			whString = url.substring(url.lastIndexOf("#") + 1);
		}
		if (whString != null) {
			wh = whString.split("_");
		}
		float[] imageWH = new float[] { Float.valueOf(wh[0]),
				Float.valueOf(wh[1]) };
		int[] imgWH = new int[] { (int) imageWH[0], (int) imageWH[1] };
		LogTool.d("Images", "w: " + imgWH[0] + " h: " + imgWH[1]);
		return imgWH;
	}

	/**
	 * @Description: TODO
	 * @param view
	 * @return void
	 */
	public static void disableTouchEvent(View view) {
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}

	/**
	 * 功能：判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param args
	 * @throws ParseException
	 */
	// public static void main(String[] args) throws ParseException {
	// // String IDCardNum="210102820826411";
	// // String IDCardNum="210102198208264114";
	// String IDCardNum = "500113198606245216";
	// CommonUtil cc = new CommonUtil();
	// System.out.println(cc.IDCardValidate(IDCardNum));
	// // System.out.println(cc.isDate("1996-02-29"));
	// }
	/**
	 * @Description: 检查网络状态
	 * @return void
	 */
	public static int checkNetworkState(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		State wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		boolean mobileStatus = (mobile == State.CONNECTED || mobile == State.CONNECTING);
		boolean wifiStatus = (wifi == State.CONNECTED || wifi == State.CONNECTING);
		if (!mobileStatus && !wifiStatus) {
			return 0;
		} else if (mobileStatus && !wifiStatus) {
			return 1;
		} else {
			return 2;
		}
	}

	/**
	 * @Description: 隐藏软键盘
	 * @param context
	 * @return void
	 */
	public static void hideIputMethord(Activity context) {
		if (context.getCurrentFocus() != null) {
			((InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(context.getCurrentFocus()
							.getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

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
	 * @Description: TODO
	 * @param v
	 * @param scale
	 * @return
	 * @return double
	 */
	public static double round(double v, int scale) {
		BigDecimal bg = new BigDecimal(v);
		return bg.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
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
	 * @Description: 判断是否安装了此应用
	 * @param context
	 * @param packageName
	 * @return
	 * @return boolean
	 */
	public static boolean AHAappInstalledOrNot(Context context,
			String packageName) {
		PackageManager pm = context.getPackageManager();
		boolean app_installed = false;
		try {
			pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
			app_installed = true;
		} catch (NameNotFoundException e) {
			app_installed = false;
		}
		return app_installed;
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

	/**
	 * @Description: 像素值转化为DP值
	 * @param context
	 * @param value
	 * @return
	 * @return int
	 */
	public static float getPxFromDp(Context context, float value) {
		float dpValue =  TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, value, context.getResources()
						.getDisplayMetrics());
		return dpValue;
	}

	/**
	 * @Description: TODO
	 * @param max
	 * @param min
	 * @return
	 * @return int
	 */
	public static int getRandom(int max, int min) {
		Random random = new Random();
		int result = random.nextInt(max + 1);// 取小于或等于max的一个随机数
		if (result < min) {
			result = getRandom(max, min);// 如果随机数小于min，则递归
		}
		return result;
	}

	/**
	 * @Description: 邮箱验证
	 * @param mail
	 * @return
	 * @return boolean
	 */
	public static boolean isValidEmail(String mail) {
		Pattern pattern = Pattern
				.compile("^[A-Za-z0-9][\\w\\._]*[a-zA-Z0-9]+@[A-Za-z0-9-_]+\\.([A-Za-z]{2,4})((.[A-Za-z]{2,4})*)");
		Matcher mc = pattern.matcher(mail);
		return mc.matches();
	}

	/**
	 * @Description: 邮箱验证
	 * @param mail
	 * @return
	 * @return boolean
	 */
	public static boolean isValidPassword(String mail) {
		Pattern pattern = Pattern
				.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$");
		Matcher mc = pattern.matcher(mail);
		return mc.matches();
	}

	/*
	 * Create a bitmap image by reading the image data from the path provided.
	 */
	public static Bitmap getImageFromSdcard(String path, Context ctx)
			throws FileNotFoundException {
		FileInputStream is = null;
		Bitmap mBitmap;
		try {
			is = new FileInputStream(path);
		} catch (FileNotFoundException fe) {
			return null;
		}

		// BitmapFactory.Options bounds = new BitmapFactory.Options();
		// bounds.inJustDecodeBounds = true;
		// // BitmapFactory.decodeStream(is, null, bounds);
		// if ((bounds.outWidth == -1) || (bounds.outHeight == -1))
		// return null;

		// int originalSize = (bounds.outHeight > bounds.outWidth) ?
		// bounds.outHeight
		// : bounds.outWidth;

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inPreferredConfig = Config.RGB_565;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		// opts.inSampleSize = originalSize / 100;
		mBitmap = BitmapFactory.decodeStream(is, null, opts);
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mBitmap;
	}

	/**
	 * @Description: call
	 * @param context
	 * @param number
	 * @return void
	 */
	public static void dialWithHandFree(Context context, String number) {
		if (number == null) {
			return;
		}
		Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
				+ number));
		context.startActivity(callIntent);
	}


	/**
	 * 从view 得到图片
	 * 
	 * @param view
	 * @return
	 */
	public static Bitmap getBitmapFromView(View view) {
		view.destroyDrawingCache();
		view.measure(View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED), View.MeasureSpec
				.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.setDrawingCacheEnabled(true);
		Bitmap bitmap = view.getDrawingCache(true);
		return bitmap;
	}

	/**
	 * @Description: Get distance between two geo
	 * @param longitude1
	 * @param latitude1
	 * @param longitude2
	 * @param latitude2
	 * @return
	 * @return double meter
	 */
	public static double getDistance(double longitude1, double latitude1,
			double longitude2, double latitude2) {
		double Lat1 = rad(latitude1);
		double Lat2 = rad(latitude2);
		double a = Lat1 - Lat2;
		double b = rad(longitude1) - rad(longitude2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(Lat1) * Math.cos(Lat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10000;
		return s;
	}

	public static String splitURLLastString(String string) {
		if (string == null) {
			return null;
		}
		String a[] = string.split("/");
		int length = a.length;
		if (length > 0) {
			return a[length - 1];
		}
		return null;
	}

	/**
	 * 用来判断服务是否运行.
	 * 
	 * @param context
	 * @param className
	 *            判断的服务名字：包名+类名
	 * @return true 在运行, false 不在运行
	 */

	public static boolean isServiceRunning(Context context, String className) {

		boolean isRunning = false;
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Activity.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);
		if (!(serviceList.size() > 0)) {
			return false;
		}
		for (int i = 0; i < serviceList.size(); i++) {
			if (serviceList.get(i).service.getClassName().equals(className) == true) {
				isRunning = true;
				break;

			}
		}
		LogTool.i("TEST", "service is running?==" + isRunning);
		return isRunning;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
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

	public static PopupWindow creatPopupWindow(Context context,
			final View popupView, int animationStyle, int width, int height) {
		// DisplayMetrics metric = new DisplayMetrics();
		// int popupWidth = metric.widthPixels;
		// int popupHeight = metric.heightPixels;
		// int popupWidth =
		// ((Activity)context).getWindowManager().getDefaultDisplay().getWidth();
		// int popupHeight =
		// ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
		final PopupWindow puwWindow = new PopupWindow(popupView, width, height,
				true);
		// puwWindow.setWidth(popupWidth);
		puwWindow.setBackgroundDrawable(null);
		puwWindow.setAnimationStyle(animationStyle);
		puwWindow.setFocusable(true);
		popupView.setFocusableInTouchMode(true);
		popupView.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					// TODO
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				}
				return false;
			}

		});

		popupView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				final int x = (int) event.getX();
				final int y = (int) event.getY();

				if ((event.getAction() == MotionEvent.ACTION_DOWN)
						&& ((x < 0) || (x >= popupView.getWidth()) || (y < 0) || (y >= popupView
								.getHeight()))) {
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
					if (puwWindow != null && puwWindow.isShowing()) {
						puwWindow.dismiss();
						return false;
					}
				}
				return false;
			}
		});
		return puwWindow;
	}

	@SuppressWarnings("deprecation")
	public static String getFliePath(Context context, Uri uri,
			boolean isSelectPhoto) {
		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
		if (isKitKat && isSelectPhoto) {
			return uri2filePath(uri, (Activity) context);
		}
		String result;
		Cursor cursor = context.getContentResolver().query(uri, null, null,
				null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file
								// path
			result = uri.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			result = cursor.getString(idx);
			cursor.close();
		}
		return result;
	}

	// TODO: 解析部分图片
	public static Result scanningImage(String path) {
		Bitmap scanBitmap;
		if (TextUtils.isEmpty(path)) {

			return null;

		}
		// DecodeHintType 和EncodeHintType
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 先获取原大小
		scanBitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false; // 获取新的大小

		int sampleSize = (int) (options.outHeight / (float) 200);

		if (sampleSize <= 0)
			sampleSize = 1;
		options.inSampleSize = sampleSize;
		scanBitmap = BitmapFactory.decodeFile(path, options);

		// --------------测试的解析方法---PlanarYUVLuminanceSource-这几行代码对project没作功----------

		LuminanceSource source1 = new PlanarYUVLuminanceSource(
				rgb2YUV(scanBitmap), scanBitmap.getWidth(),
				scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
				scanBitmap.getHeight());
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				source1));
		MultiFormatReader reader1 = new MultiFormatReader();
		Result result1;
		try {
			result1 = reader1.decode(binaryBitmap);
			String content = result1.getText();
			Log.i("123content", content);
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ----------------------------

		RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {

			return reader.decode(bitmap1, hints);

		} catch (NotFoundException e) {

			e.printStackTrace();

		} catch (ChecksumException e) {

			e.printStackTrace();

		} catch (FormatException e) {

			e.printStackTrace();

		}

		return null;

	}

	/**
	 * //TODO: TAOTAO 将bitmap由RGB转换为YUV //TOOD: 研究中
	 * 
	 * @param bitmap
	 *            转换的图形
	 * @return YUV数据
	 */
	public static byte[] rgb2YUV(Bitmap bitmap) {
		// 该方法来自QQ空间
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int len = width * height;
		byte[] yuv = new byte[len * 3 / 2];
		int y, u, v;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = pixels[i * width + j] & 0x00FFFFFF;

				int r = rgb & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 16) & 0xFF;

				y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
				u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
				v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

				y = y < 16 ? 16 : (y > 255 ? 255 : y);
				u = u < 0 ? 0 : (u > 255 ? 255 : u);
				v = v < 0 ? 0 : (v > 255 ? 255 : v);

				yuv[i * width + j] = (byte) y;
				// yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
				// yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
			}
		}
		return yuv;
	}

	public static String recode(String str) {
		String formart = "";

		try {
			boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
					.canEncode(str);
			if (ISO) {
				formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
				Log.i("1234      ISO8859-1", formart);
			} else {
				formart = str;
				Log.i("1234      stringExtra", str);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formart;
	}

	public static int getPageCount(int count, int pageSize) {
		if (pageSize == 0) {
			return 0;
		}
		if (count % pageSize == 0) {
			LogTool.d("page", "page" + (count / pageSize));
			return count / pageSize;
		} else {
			LogTool.d("page", "page" + (count / pageSize + 1));
			return count / pageSize + 1;
		}
	}

	public static boolean ExistSDCard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else
			return false;
	}

	public static DisplayImageOptions getNormalDisplayImageOptions(int photo) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		opt.inSampleSize = 2;
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(photo).showImageOnFail(photo)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.delayBeforeLoading(200).decodingOptions(opt).cacheOnDisc(true)
				.resetViewBeforeLoading(true).considerExifParams(true)
				.showImageOnLoading(photo)
				// .displayer(new RoundedBitmapDisplayer(20))
				.build();
		return options;
	}

	public static String uri2filePath(Uri uri, Activity activity) {

		String path = "";

		if (DocumentsContract.isDocumentUri(activity, uri)) {

			String wholeID = DocumentsContract.getDocumentId(uri);

			String id = wholeID.split(":")[1];

			String[] column = { MediaStore.Images.Media.DATA };

			String sel = MediaStore.Images.Media._ID + "=?";

			Cursor cursor = activity.getContentResolver().query(

			MediaStore.Images.Media.EXTERNAL_CONTENT_URI, column, sel,

			new String[] { id }, null);

			int columnIndex = cursor.getColumnIndex(column[0]);

			if (cursor.moveToFirst()) {

				path = cursor.getString(columnIndex);

			}
			cursor.close();

		} else {

			String[] projection = { MediaStore.Images.Media.DATA };

			Cursor cursor = activity.getContentResolver().query(uri,

			projection, null, null, null);

			int column_index = cursor

			.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			cursor.moveToFirst();

			path = cursor.getString(column_index);

		}
		return path;
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

	public static int dpToPx(final Context context, final float dp) {
		// Took from
		// http://stackoverflow.com/questions/8309354/formula-px-to-dp-dp-to-px-android
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) ((dp * scale) + 0.5f);
	}

	public static int getScreenWidth(final Context context) {
		if (context == null) {
			return 0;
		}
		return getDisplayMetrics(context).widthPixels;
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

}
