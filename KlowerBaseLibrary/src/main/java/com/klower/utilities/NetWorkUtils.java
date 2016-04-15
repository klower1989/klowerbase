package com.klower.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class NetWorkUtils {
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
			LogTool.e("", "Network is not available..");
			return 0;
		} else if (mobileStatus && !wifiStatus) {
			LogTool.e("", "3G or 2G is in use..");
			return 1;
		} else {
			LogTool.e("", "Wifi is in use..");
			return 2;
		}
	}
	
	
	
	
	
}
