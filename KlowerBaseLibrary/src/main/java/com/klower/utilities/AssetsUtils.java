package com.klower.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

public class AssetsUtils {
	/**
	 * @Description: 读取Assists文件
	 * @param context
	 * @return
	 * @return String
	 */
	public static String getAssestsJson(Context context, String fileName) {
		String jsonString = "";
		InputStreamReader mInputStreamReader = null;
		BufferedReader mbufReader = null;
		StringBuffer mStringBuffer = null;
		String temp = "";
		try {
			mInputStreamReader = new InputStreamReader(context.getResources()
					.getAssets().open(fileName));
			mbufReader = new BufferedReader(mInputStreamReader);
			mStringBuffer = new StringBuffer();
			while ((temp = mbufReader.readLine()) != null) {
				mStringBuffer.append(temp);
			}
			jsonString = mStringBuffer.toString().trim();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				mbufReader.close();
				mInputStreamReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return jsonString;

	}

}
