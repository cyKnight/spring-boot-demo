package com.example.demo.utils;

import java.sql.Date;
import java.sql.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述: 验证工具类</br>
 * 
 * @author Shangxp
 * @version 1.0.0
 * @date 2016.03.15
 */
public class ValidatorUtils {
	
	/**
	 * 验证字符串中是否包含有特殊字符
	 * 
	 * @param value 字符串
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkSpecialChar(String value) {
		String regex = ".*[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	/**
	 * 验证Email
	 * 
	 * @param email email地址, 格式: zhangsan@126.com, zhangsan@xxx.com.cn, xxx代表邮件服务商
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}
	
	/**
	 * 验证手机号码(支持国际格式: +86135xxxx...(中国内地), +00852137xxxx...(中国香港)
	 * 
	 * @param mobile 移动, 联通, 电信运营商的号码段
	 * 
	 * <p> 
	 * 	移动的号段: 134(0-8)、135、136、137、138、139、147(预计用于TD上网卡)、150、151、152、157(TD专用)、158、159、187(未启用)、188(TD专用)
	 * </p>
	 * <p>
	 * 	联通的号段: 130、131、132、155、156(世界风专用)、185(未启用)、186(3g)
	 * </p>
	 * <p>
	 * 	电信的号段: 133、153、180(未启用)、189
	 * </p>
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "^1[34578]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * 验证中文
	 * 
	 * @param chinese 中文字符
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}
	
	/**
	 * 验证空格
	 * 
	 * @param blankSpace 含有空格的字符串
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		return blankSpace.contains(" ") || blankSpace.contains("	");
	}
	
	/**
	 * 匹配中国邮政编码
	 * 
	 * @param postcode 邮政编码
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}
	
	/**
	 * 验证企业组织机构代码
	 * 
	 * @param code 企业组织机构代码
	 * @return 验证成功返回true, 验证失败返回false
	 */
	public static boolean checkValidOrgCode(String code) {
		int[] ws = { 3, 7, 9, 10, 5, 8, 4, 2 };
		String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		String regex = "^([0-9A-Z]){8}-[0-9|X]$";
		if (!code.matches(regex)) {
			return false;
		}
		
		int sum = 0;
		for (int i = 0; i < 8; i++) {
			sum += str.indexOf(String.valueOf(code.charAt(i))) * ws[i];
		}

		int c9 = 11 - (sum % 11);

		String sc9 = String.valueOf(c9);
		if (11 == c9) {
			sc9 = "0";
		} else if (10 == c9) {
			sc9 = "X";
		}
		return sc9.equals(String.valueOf(code.charAt(9)));
	}

	/**
	 * 是否是数字型字符串
	 * @param text
	 * @return
	 */
	public static boolean isNumber(String text){
		return text.matches("[0-9]+");
	}

	/**
	 * 是否为整数格式
	 * @param obj
	 * @return
	 */
	public static boolean isInteger(Object obj){
		if (null == obj) {
			return false;
		}

		if (obj instanceof String) {
			try {
				Integer.valueOf((String)obj);
				return true;
			} catch (Exception e) {
				// TODO: handle exception
				return false;
			}
		}
		return obj instanceof Integer;

	}

	/**
	 * 是否为中文
	 * @param text
	 * @return
	 */
	public static boolean isChinese(String text){
		char[] ch = text.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
			if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
					|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
					|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
					|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
					|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				return true;
			}
		}
		return false;

	}
	/**
	 * 是否存在中文
	 * @param text
	 * @return
	 */
	public static boolean hasChinese(String text){
		char[] charArray = text.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if ((charArray[i] >= 0x4e00) && (charArray[i] <= 0x9fbb)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 验证纯文本【不含任何符号，包括中文】
	 * @param text
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isPassword(String text, int minLength, int maxLength){

		if (!check(text, minLength, maxLength)) {
			return false;
		}

		Pattern pattern = Pattern.compile("[A-Za-z]+[0-9]");
		Matcher matcher = pattern.matcher(text);

		return matcher.matches();
	}

	/**
	 * 是否为网址
	 * @param url
	 * @return
	 */
	public static boolean isUrl(String url){

		if (null == url) {
			return false;
		}

		Pattern pattern = Pattern.compile("http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?");
		Matcher matcher = pattern.matcher(url);

		return matcher.matches();
	}

	/**
	 * 是否为指定长度的邮箱
	 * @param email
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean isEmail(String email, int minLength, int maxLength){
		if (!check(email, minLength, maxLength)) {
			return false;
		}
		return isEmail(email);
	}

	/**
	 * 简单校验字符串
	 * @param text
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean check(String text, int minLength, int maxLength){
		if (null == text || text.length() < minLength) {
			return false;
		}
		return text.length() <= maxLength;
	}


	/**
	 * 是否为邮箱地址格式
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {

		if (null == email || "".equals(email)) {
			return false;
		}

		Pattern emailPattern = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
		Matcher matcher = emailPattern.matcher(email);

		return matcher.matches();

	}

	/**
	 * 是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj){
		if (null == obj) {
			return true;
		}
		if (obj instanceof Integer) {
			return 0 == (int) obj;

		}else if (obj instanceof Date) {
			return ((Date) obj).getTime() == 0;

		}else if (obj instanceof Long) {
			return 0 == (long) obj;

		}else if (obj instanceof String) {
			return 0 == ((String)obj).length();

		}else if (obj instanceof Boolean) {
			return !((boolean) obj);

		}else if (obj instanceof byte[]) {
			return 0 == ((byte[]) obj).length;

		}else if (obj instanceof Double) {
			return 0 == (double) obj;

		}else if (obj instanceof Float) {
			return 0 == (float) obj;

		}else if (obj instanceof Short) {
			return 0 == (short) obj;

		}else if (obj instanceof Time) {
			return 0 == ((Time) obj).getTime();

		}
		return false;
	}
}