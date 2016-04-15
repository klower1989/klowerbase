package com.klower.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap.CompressFormat;
import android.os.Build;
import android.os.StrictMode;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends Application {

	public static final boolean DEVELOPER_MODE = false;

	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (DEVELOPER_MODE
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}
		super.onCreate();
		initImageLoader(getApplicationContext());
	}

	/**
	 * @Description: 初始化图片加载器
	 * @param context
	 * @return void
	 */
	public static void initImageLoader(Context context) {
		// String imageUri = "http://site.com/image.png"; // from Web
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		// String imageUri = "content://media/external/audio/albumart/13"; //
		// from content provider
		// String imageUri = "assets://image.png"; // from assets
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)

		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				// .memoryCacheExtraOptions(480, 800)
				.memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory()
				// default = device screen dimensions CompressFormat.JPEG会导致png突变背景变成黑色
//				.discCacheExtraOptions(480, 800, CompressFormat.PNG)
				.threadPriority(Thread.NORM_PRIORITY - 3)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.writeDebugLogs().discCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO) // Not
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
