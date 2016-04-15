package com.klower.utilities;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final double MAX_NUMBER = 100d;
    public static final double MAX_INPUT_NUMBER = 100000000d;

	public static String formatMoney(double number) {
		if (BigDecimalUtils.compareTo(number, MAX_NUMBER) == 1||BigDecimalUtils.compareTo(number, MAX_NUMBER) == 0) {
			// 大于这个数,要用科学计数法
			return scientificFormatter(number);
		} else {
			// 小于这个数
			return splitFormatter(number);
		}
	}

	public static String scientificFormatter(double number) {
		return new DecimalFormat("#.##E0").format(number);
	}

    public static String doubleToStr(double d) {
        DecimalFormat format = new DecimalFormat("###0.0");
        String str = "";
        str = format.format(d);
        return str;
    }
    
	public static String splitFormatter(double number) {
	    if(number == 0){
	        return "0.00";   
	    }
		return new DecimalFormat(",##0.00").format(roundOff(number, 2));
	}
	
	public static String simpleSplitFormatter(double number) {
		String doubleStr = String.valueOf(number);
		if (doubleStr.contains(".")) {
			String value = new DecimalFormat(",###").format(number)
					+ doubleStr.substring(doubleStr.indexOf("."),
							doubleStr.length());
			return value;
		} else {
			return new DecimalFormat(",###").format(number);
		}
	}
	
    static double roundOff(double x, int position) {
        double a = x;
        double temp = Math.pow(10.0, position);
        a *= temp;
        a = Math.round(a);
        return (a / (double)temp);
    }
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(CharSequence cs) {
		return !StringUtils.isBlank(cs);
	}

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static String nullToEmpty(CharSequence cs) {
        return isEmpty(cs) ? "" : cs.toString();
    }
    
    public static int getWordCount(CharSequence s)  {
        if(s == null) {
            return 0;
        }
        int length = 0;  
        for(int i = 0; i < s.length(); i++) {  
            int ascii = Character.codePointAt(s, i);  
            if(ascii >= 0 && ascii <=255)  
                length++;  
            else  
                length += 3;  
                  
        }  
        return length;  
    } 
    
    @SuppressWarnings("unused")
    public static boolean isValidMobile(String mobile){
        /**
         * 手机号码
         * 13*********
         * 15*********
         * 18*********
         * 
         * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
         * 联通：130,131,132,152,155,156,185,186
         * 电信：133,1349,153,180,189
         */
        String MOBILE = "^1(3[0-9]|5[0-9]|8[0-9])\\d{8}$";
        /**
         * 中国移动：China Mobile
         * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
         */
        String CM = "^1(34[0-8]|(3[5-9]|5[017-9]|8[278])\\d)\\d{7}$";
        /**
         * 中国联通：China Unicom
         * 130,131,132,152,155,156,185,186
         */
        String CU = "^1(3[0-2]|5[256]|8[56])\\d{8}$";
        /**
         * 中国电信：China Telecom
         * 133,1349,153,180,189
         */
        String CT = "^1((33|53|8[09])[0-9]|349)\\d{7}$";
        /**
         * 大陆地区固话及小灵通
         * 区号：010,020,021,022,023,024,025,027,028,029
         * 号码：七位或八位
         */
        String PHS = "^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
        
        Pattern pattern = Pattern.compile(MOBILE);
        Matcher matcher = pattern.matcher(mobile); 
        
        return matcher.matches();
    }
    
    public static boolean isValidAccount(CharSequence cs) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_]+$");
        Matcher matcher = pattern.matcher(cs); 
        
        return matcher.matches();
    }
    
	public static boolean isNotEmpty(CharSequence cs) {
		return !StringUtils.isEmpty(cs);
	}
	
	/**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public static boolean isValidMathNumber(String val) {
        Pattern pattern = Pattern.compile("((\\d+)?(\\.\\d+)?)");
        Matcher matcher = pattern.matcher(val); 
        return matcher.matches();
    }
    
    /**
     * 判断字符串是否是数字
     */
    public static boolean isDigit(String value) {
        return isInteger(value) || isDouble(value);
    }  
    
    public static String saftyTrimPhoneNum(String phoneNumber) {
        String ret = phoneNumber;
        
        if(StringUtils.isNotEmpty(phoneNumber)) {
            phoneNumber = phoneNumber.replaceAll(" |-", "");
            if(phoneNumber.startsWith("+") && phoneNumber.length() == 14) {
                phoneNumber = phoneNumber.substring(3);
            }
            ret = phoneNumber.replaceAll("\\D", "");
        }
        
        return ret;
    }
    
    public static String[] splitStr(String str, String s){
    	if(!str.contains(s)){
    		return new String[]{str};
    	}else{
    		return str.split("\\"+s);
    	}
    }
}
