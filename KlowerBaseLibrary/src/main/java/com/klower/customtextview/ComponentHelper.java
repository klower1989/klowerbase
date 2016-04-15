package com.klower.customtextview;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class ComponentHelper {
	// PATH
	private static final String PROPS_PATH_STRING = "fonts/font_style.properties";

	// default font
	private static final String DEF_FONT = "Roboto_Regular";

	// component attribute
	private static final String FONT_ATTR = "fontStyle";

	// http://schemas.android.com/apk/res/android
	private static final String NAME_SPACE = "http://schemas.android.com/apk/res/android";

	// typeface
	private static final String TYPEFACE = "typeface";

	// default path name
	private static final String DEF_PATH_NAME = "fonts/Roboto_Regular.ttf";

	// typefaces
	private static Map<String, Typeface> sTypefaces;

	private static Map<String, Properties> sdata = new LinkedHashMap<String, Properties>();

	/**
	 * constructor
	 */
	private ComponentHelper() {

	}

	/**
	 * set font face
	 * 
	 * @param textView
	 *            set object
	 * @param attrs
	 *            attributes
	 */
	public static void setFontFace(TextView textView, AttributeSet attrs) {

		if (textView == null || attrs == null
				|| attrs.getAttributeValue(NAME_SPACE, TYPEFACE) != null) {
			return;
		}

		String tmp = null;
		String fontStyle = (attrs == null) ? DEF_FONT : ((tmp = attrs
				.getAttributeValue(null, FONT_ATTR)) == null) ? DEF_FONT : tmp;

		// HEVApplication application = (HEVApplication)
		// textView.getContext().getApplicationContext();
		Application application = (Application) textView.getContext()
				.getApplicationContext();
		Typeface typeface = null;
		if ((typeface = getTypeface(fontStyle)) != null) {
			textView.setTypeface(typeface);
			return;
		}
		typeface = Typeface.createFromAsset(
				application.getAssets(),
				getProperty(PROPS_PATH_STRING, fontStyle, DEF_PATH_NAME,
						textView.getContext()));

		addTypeface(fontStyle, typeface);
		textView.setTypeface(typeface);
	}

	/**
	 * add typeface to cache
	 * 
	 * @param faceKey
	 *            key
	 * @param typeface
	 *            typeface
	 */
	public static void addTypeface(String faceKey, Typeface typeface) {
		if (sTypefaces == null) {
			sTypefaces = new HashMap<String, Typeface>();
		}
		sTypefaces.put(faceKey, typeface);
	}

	/**
	 * get specific type through key
	 * 
	 * @param faceKey
	 *            key
	 * @return type face
	 */
	public static Typeface getTypeface(String faceKey) {
		if (sTypefaces == null || faceKey == null) {
			return null;
		}
		return sTypefaces.get(faceKey);
	}

	public static void setCustomFontStyle(TextView textView, String fontName) {
		Application application = (Application) textView.getContext()
				.getApplicationContext();
		Typeface typeface = Typeface.createFromAsset(
				application.getAssets(),
				getProperty(PROPS_PATH_STRING, fontName, DEF_PATH_NAME,
						textView.getContext()));
		textView.setTypeface(typeface);
	}

	public static String getProperty(String fileName, String key,
			String defValue, Context context) {

		if (sdata.containsKey(fileName)) {
			return sdata.get(fileName).getProperty(key, defValue);
		}

		Properties prop = new Properties();
		try {
			prop.load(context.getAssets().open(fileName));
		} catch (IOException e) {
			Log.e("load property error", fileName, e);
			return defValue;
		}

		sdata.put(fileName, prop);
		return prop.getProperty(key, defValue);
	}
}
