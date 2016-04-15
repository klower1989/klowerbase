
package com.klower.utilities;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLUtil {
    public final static String HTTP = "http://";

    public final static String HTTPS = "https://";

    public static String format(String url) {
        if (!url.toLowerCase().contains(HTTP) && !url.toLowerCase().contains(HTTPS)) {
            return HTTP + url;
        } else {
            return url;
        }
    }

    public static boolean validate(String urlString) {
        if (null == urlString) {
            return false;
        } else if ("".equals(urlString.trim())) {
            return false;
        } else if ("null".equals(urlString.trim().toLowerCase())) {
            return false;
        } else {
            try {
                // check url format
                URL url = new URL(format(urlString));
                return true;
            } catch (MalformedURLException e) {
                return false;
            }
        }
    }
    
    public static String urlEncode(String tag2) {
		String str = "";
		try {
			str = URLEncoder.encode(tag2, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;

	}
}
