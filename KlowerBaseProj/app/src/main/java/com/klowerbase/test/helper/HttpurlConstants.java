package com.klowerbase.test.helper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import com.klower.utilities.LogTool;

public class HttpurlConstants {
	private static final String baseUrl = "http://image.baidu.com/channel/listjson?";

	public static String tag1 = "%E7%BE%8E%E5%A5%B3";
	public static String stag1 = "%E5%A3%81%E7%BA%B8";
	static int PAGE_SIZE = 10;

	private static final String BASE_URL = "http://image.baidu.com/channel/"
			+ "listjson?pn=0&rn=" + PAGE_SIZE + "&tag1=" + tag1 + "&ftags="
			+ "&sorttype=0&ie=utf8&oe=utf-8&image_id=&tag2=";
	private String strs = "%E6%B0%94%E8%B4%A8&width=580&height=200";

	/**
	 * 具体分类可参照百度：http://image.baidu.com/channel?c=家居&t=热门推荐&s=20&t3=全部
	 * 
	 * @param name
	 *            : 搜索的内容
	 * @param sort
	 *            ： 大分类 例如：动物、美女
	 * @param pageIndex
	 *            ：从第几个下标开始
	 * @param pageSize
	 *            ：每页数目
	 * @return
	 */
	public static String appendUrl(String sort, String name, int pageIndex,
			int pageSize) {
		return baseUrl + "pn=" + pageIndex + "&rn=" + pageSize + "&" + "&tag1="
				+ urlEncode(sort)
				+ "&ftags=&sorttype=0&ie=utf8&oe=utf-8&image_id=&tag2=" + name;
	}

	public static String getMusicUrl(String keywords) {
		return "http://v5.pc.duomi.com/search-ajaxsearch-searchall?kw="
				+ urlEncode(keywords) + "&pi=" + 0 + "&pz=" + 10;
	}

	public static String appendPnandRn(int num) {
		String str = "pn=0&rn=6&";
		if (num > 6) {
			// int pn = num - 6 + 1;
			int pn = 0;
			int rn = num + 1;
			str = "pn=" + pn + "&rn=" + rn + "&";
		}
		return str;
	}

	public static String urlEncode(String tag2) {
		String str = "";
		try {
			LogTool.d("", "--: " + URLDecoder.decode(tag1, "UTF-8"));
			str = URLEncoder.encode(tag2, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;

	}
}
