package com.example.demo.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils{
    @SuppressWarnings("unused")
    private static final int ALIAS_TRUNCATE_LENGTH = 10;

    public static final String WHITESPACE = " \n\r\f\t";

    public static int lastIndexOfLetter(String string){
        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);
            if (!Character.isLetter(character)){
                return i - 1;
            }
        }
        return string.length() - 1;
    }
 
   public static String join(String seperator, String[] strings) {
       int length = strings.length;
       if (length == 0) {
           return "";
       }
       StringBuffer buf = new StringBuffer(length * strings[0].length()).append(strings[0]);
       for (int i = 1; i < length; i++) {
           buf.append(seperator).append(strings[i]);
       }
       return buf.toString();
   }
 
   public static String[] add(String[] x, String sep, String[] y) {
       String[] result = new String[x.length];
       for (int i = 0; i < x.length; i++) {
           result[i] = (x[i] + sep + y[i]);
       }
       return result;
   }
 
   public static String repeat(String string, int times) {
       StringBuffer buf = new StringBuffer(string.length() * times);
       for (int i = 0; i < times; i++){
           buf.append(string);
       }
       return buf.toString();
   }
 
   public static String repeat(char character, int times) {
       char[] buffer = new char[times];
       Arrays.fill(buffer, character);
       return new String(buffer);
   }

   public static String replace(String template, String placeholder, String replacement, boolean wholeWords) {
       if (template == null) {
           return template;
       }
       int loc = template.indexOf(placeholder);
       if (loc < 0) {
           return template;
       }

       boolean actuallyReplace = (!wholeWords) ||
               (loc + placeholder.length() == template.length()) ||
               (!Character.isJavaIdentifierPart(template.charAt(loc + placeholder.length())));
       String actualReplacement = actuallyReplace ? replacement : placeholder;
       return template.substring(0, loc) +
               actualReplacement +
               replace(template.substring(loc + placeholder.length()),
                       placeholder,
                       replacement,
                       wholeWords);
   }
 
   public static String replaceOnce(String template, String placeholder, String replacement) {
       if (template == null) {
           return template;
       }
       int loc = template.indexOf(placeholder);
       if (loc < 0) {
           return template;
       }

       return template.substring(0, loc) +
               replacement +
               template.substring(loc + placeholder.length());
   }
 
   public static String[] split(String seperators, String list) {
       return split(seperators, list, false);
   }
 
   public static String[] split(String seperators, String list, boolean include) {
       StringTokenizer tokens = new StringTokenizer(list, seperators, include);
       String[] result = new String[tokens.countTokens()];
       int i = 0;
       while (tokens.hasMoreTokens()) {
           result[(i++)] = tokens.nextToken();
       }
       return result;
   }
 
   public static String unqualify(String qualifiedName) {
       int loc = qualifiedName.lastIndexOf(".");
       return loc < 0 ? qualifiedName : qualifiedName.substring(loc + 1);
   }
 
   public static String qualifier(String qualifiedName) {
       int loc = qualifiedName.lastIndexOf(".");
       return loc < 0 ? "" : qualifiedName.substring(0, loc);
   }
 
   public static String collapse(String name) {
       if (name == null) {
           return null;
       }
       int breakPoint = name.lastIndexOf('.');
       if (breakPoint < 0) {
           return name;
       }
       return collapseQualifier(name.substring(0, breakPoint), true) + name.substring(breakPoint);
   }
 
   public static String collapseQualifier(String qualifier, boolean includeDots){
       StringTokenizer tokenizer = new StringTokenizer(qualifier, ".");
       String collapsed = Character.toString(tokenizer.nextToken().charAt(0));
       while (tokenizer.hasMoreTokens()) {
           if (includeDots) {
               collapsed = collapsed + '.';
           }
           collapsed = collapsed + tokenizer.nextToken().charAt(0);
       }
       return collapsed;
   }
 
   public static String partiallyUnqualify(String name, String qualifierBase) {
       if ((name == null) || (!name.startsWith(qualifierBase))) {
           return name;
       }
       return name.substring(qualifierBase.length() + 1);
   }
 
   public static String collapseQualifierBase(String name, String qualifierBase) {
       if ((name == null) || (!name.startsWith(qualifierBase))) {
           return collapse(name);
       }
       return collapseQualifier(qualifierBase, true) + name.substring(qualifierBase.length());
   }
 
   public static String[] suffix(String[] columns, String suffix) {
       if (suffix == null){
           return columns;
       }
       String[] qualified = new String[columns.length];
       for (int i = 0; i < columns.length; i++) {
           qualified[i] = suffix(columns[i], suffix);
       }
       return qualified;
   }
 
   private static String suffix(String name, String suffix) {
       return name + suffix;
   }
 
   public static String root(String qualifiedName) {
       int loc = qualifiedName.indexOf(".");
       return loc < 0 ? qualifiedName : qualifiedName.substring(0, loc);
   }
 
   public static String unroot(String qualifiedName) {
       int loc = qualifiedName.indexOf(".");
       return loc < 0 ? qualifiedName : qualifiedName.substring(loc + 1, qualifiedName.length());
   }
 
   public static boolean booleanValue(String tfString) {
       String trimmed = tfString.trim().toLowerCase();
       return (trimmed.equals("true")) || (trimmed.equals("t"));
   }
 
   public static String toString(Object[] array) {
       int len = array.length;
       if (len == 0){
           return "";
       }
       StringBuffer buf = new StringBuffer(len * 12);
       for (int i = 0; i < len - 1; i++) {
           buf.append(array[i]).append(", ");
       }
       return String.valueOf(array[(len - 1)]);
   }
 
   public static String[] multiply(String string, Iterator<?> placeholders, Iterator<?> replacements) {
       String[] result = { string };
       while (placeholders.hasNext()) {
           result = multiply(result, (String)placeholders.next(), (String[])replacements.next());
       }
       return result;
   }
 
   private static String[] multiply(String[] strings, String placeholder, String[] replacements) {
       String[] results = new String[replacements.length * strings.length];
       int n = 0;
       for (int i = 0; i < replacements.length; i++) {
           for (int j = 0; j < strings.length; j++) {
               results[(n++)] = replaceOnce(strings[j], placeholder, replacements[i]);
           }
       }
       return results;
   }
 
   public static int countUnquoted(String string, char character) {
       if ('\'' == character) {
           throw new IllegalArgumentException("Unquoted count of quotes is invalid");
       }
       if (string == null) {
           return 0;
       }

       int count = 0;
       int stringLength = string.length();
       boolean inQuote = false;
       for (int indx = 0; indx < stringLength; indx++) {
           char c = string.charAt(indx);
           if (inQuote) {
               if ('\'' == c) {
                   inQuote = false;
               }
           }
           else if ('\'' == c) {
               inQuote = true;
           }
           else if (c == character) {
               count++;
           }
       }
       return count;
   }
 
   @SuppressWarnings({ "rawtypes", "unchecked" })
   public static int[] locateUnquoted(String string, char character) {
       if ('\'' == character) {
           throw new IllegalArgumentException("Unquoted count of quotes is invalid");
       }
       if (string == null) {
           return new int[0];
       }

       ArrayList locations = new ArrayList(20);

       int stringLength = string.length();
       boolean inQuote = false;
       for (int indx = 0; indx < stringLength; indx++) {
           char c = string.charAt(indx);
           if (inQuote) {
               if ('\'' == c) {
                   inQuote = false;
               }
           }
           else if ('\'' == c) {
               inQuote = true;
           }
           else if (c == character) {
               locations.add(new Integer(indx));
           }
       }
       return ArrayUtils.toIntArray(locations);
   }
 
   public static boolean isNotEmpty(String string) {
       return (string != null) && (string.length() > 0);
   }
 
   public static boolean isEmpty(String string) {
       return (string == null) || (string.length() == 0);
   }
 
   public static String qualify(String prefix, String name) {
       if ((name == null) || (prefix == null)) {
           throw new NullPointerException();
       }
       return prefix.length() + name.length() + 1 + prefix + '.' + name;
   }
 
   public static String[] qualify(String prefix, String[] names) {
       if (prefix == null){
           return names;
       }
       int len = names.length;
       String[] qualified = new String[len];
       for (int i = 0; i < len; i++) {
           qualified[i] = qualify(prefix, names[i]);
       }
       return qualified;
   }
 
   public static int firstIndexOfChar(String sqlString, String string, int startindex) {
       int matchAt = -1;
       for (int i = 0; i < string.length(); i++) {
           int curMatch = sqlString.indexOf(string.charAt(i), startindex);
           if (curMatch >= 0) {
               if (matchAt == -1) {
                   matchAt = curMatch;
               } else {
                   matchAt = Math.min(matchAt, curMatch);
               }
           }
       }
       return matchAt;
   }
 
   public static String truncate(String string, int length) {
       if (string.length() <= length) {
           return string;
       }
       return string.substring(0, length);
   }
 
   public static String generateAlias(String description) {
       return generateAliasRoot(description) + '_';
   }
 
   public static String generateAlias(String description, int unique) {
       return generateAliasRoot(description) + Integer.toString(unique) + '_';
   }
 
   private static String generateAliasRoot(String description) {
       String result = truncate(unqualifyEntityName(description), 10)
               .toLowerCase().replace('/', '_').replace('$', '_');
       result = cleanAlias(result);
       if (Character.isDigit(result.charAt(result.length() - 1))) {
           return result + "x";
       }
       return result;
   }
 
   private static String cleanAlias(String alias) {
       char[] chars = alias.toCharArray();
       if (!Character.isLetter(chars[0])) {
           for (int i = 1; i < chars.length; i++){
               if (Character.isLetter(chars[i])) {
                   return alias.substring(i);
               }
           }
       }
       return alias;
   }
 
   public static String unqualifyEntityName(String entityName) {
       String result = unqualify(entityName);
       int slashPos = result.indexOf('/');
       if (slashPos > 0) {
           result = result.substring(0, slashPos - 1);
       }
       return result;
   }
 
   public static String toUpperCase(String str) {
       return str == null ? null : str.toUpperCase();
   }
 
   public static String toLowerCase(String str) {
       return str == null ? null : str.toLowerCase();
   }
 
   public static String moveAndToBeginning(String filter) {
       if (filter.trim().length() > 0) {
           filter = filter + " and ";
           if (filter.startsWith(" and ")){
               filter = filter.substring(4);
           }
       }
       return filter;
   }

   public static String _HREF_URL_REGEX = "(http:|https:)//[^[A-Za-z0-9\\._\\?%&+\\-=/#!]]*";
   public static final String EMPTY_STRING = "";
   private static char chars[] = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
   private static final char IntChars[] = "1234567890".toCharArray();
    /**
     * 获得int
     * @param val
     * @return
     */
    public static int getInt(String val){
        return getInt(val,-1);
    }
    /**
     * 获得int
     * @param val
     * @return
     */
    public static int getInt(Object val){
        if(val == null){
            return -1;
        }
        return getInt(val.toString(),-1);
    }
    /**
     * 获得int
     * @param val
     * @param def
     * @return
     */
    public static int getInt(Object val,int def){
        try{
            return Integer.parseInt(val.toString().trim());
        }catch(Exception e){
            return def;
        }
    }
    /**
     * 获得long
     * @param val
     * @return
     */
    public static long getLong(String val ) {

        return getLong(val,-1);
    }
    /**
     * 获得long
     * @param val
     * @param def
     * @return
     */
    public static long getLong(String val,long def){
        try{
            return Long.parseLong(val.trim());
        }catch(Exception e){
            return -1;
        }
    }

    /**
     * 获得double
     * @param value
     * @return
     */
    public static double getDouble(String value){
        return getDouble(value,0.00);
    }
    /**
     * 获得double
     * @param value
     * @param def
     * @return
     */
    public static double getDouble(String value,double def){
        try{
            return Double.valueOf(value).doubleValue();
        }catch(Exception e){
            return def;
        }
    }

    /**
     * 获得BigDecimal
     * @param str
     * @return
     */
    public static BigDecimal getBigDecimal(String str){
        try{
            BigDecimal bd = new BigDecimal(str);
            return bd;
        }catch(Exception e){
            return BigDecimal.valueOf(-1.00);
        }
    }

    /**
     * 获得BigDecimal
     * @param str
     * @return
     */
    public static BigDecimal getBigDecimal(String str,double def){
        try{
            BigDecimal bd = new BigDecimal(str);
            return bd;
        }catch(Exception e){
            return BigDecimal.valueOf(def);
        }
    }

    /**
     * yes/no转换成true/false
     * @param aValue
     * @return
     */
    public static final String getBooleanFromYesNo(String aValue) {
        if (aValue.equalsIgnoreCase("yes")) {
            return "true";
        }

        return "false";
    }

    /**
     * 替换字符串
     * @param text 原始文本
     * @param replaced 要替换内容
     * @param replacement 替换内容
     * @return
     */
    public static String replace(String text, String replaced, String replacement) {
        StringBuffer ret = new StringBuffer();
        String temp = text;

        while (temp.indexOf(replaced) > -1) {
            ret.append(temp.substring(0, temp.indexOf(replaced)) + replacement);

            temp = temp.substring(temp.indexOf(replaced) + replaced.length());
        }

        ret.append(temp);

        return ret.toString();
    }

    /**
     * 字符串替换 正反向
     * @param sIni
     * @param sFrom
     * @param sTo
     * @param caseSensitiveSearch
     * @return
     */
    public static String replaceString(String sIni, String sFrom, String sTo, boolean caseSensitiveSearch) {
        int i = 0;
        String s = new String(sIni);
        StringBuffer result = new StringBuffer();

        if (caseSensitiveSearch) {
            i = s.indexOf(sFrom);
        } else {
            i = s.toLowerCase().indexOf(sFrom.toLowerCase());
        }

        while (i != -1) {
            result.append(s.substring(0, i));
            result.append(sTo);

            s = s.substring(i + sFrom.length());

            if (caseSensitiveSearch) {
                i = s.indexOf(sFrom);
            } else {
                i = s.toLowerCase().indexOf(sFrom.toLowerCase());
            }
        }

        result.append(s);

        return result.toString();
    }


    /**
     * 删除不能成为文件名的字符
     * @param fileStr
     * @return
     */
    public static String removeIllegalFileChars(String fileStr) {
        String replacedFileStr = fileStr;

        replacedFileStr = replace(replacedFileStr, " ", "_");
        replacedFileStr = replace(replacedFileStr, "&", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, "%", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, ",", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, ";", EMPTY_STRING);
        replacedFileStr = replace(replacedFileStr, "/", "_");

        return replacedFileStr;
    }

    /**
     * 获得字符串中的指点子字符串
     * @param strIn
     * @param start
     * @param length
     * @return
     */
    public static String substring(String strIn, int start, int length){
        String strOut = null;

        if (strIn != null) {
            strOut = EMPTY_STRING;

            if (start < strIn.length() && length > 0) {
                if (start + length < strIn.length() ) {
                    strOut = strIn.substring(start, start + length );
                } else {
                    strOut = strIn.substring(start, strIn.length() );
                }
            }
        }

        return strOut;
    }
    /**
     * 随机生成length位数字字符串
     * @param length
     * @return
     */
    public static final String getRandomLengthDigit(int length){
        StringBuilder str=new StringBuilder();
        for(int i=0;i<length;i++){
            str.append(IntChars[(int)(Math.random()*10)]);
        }
        return str.toString();
    }
    /**
     * 随即生成length位字符串
     * @param length
     * @return
     */
    public static final String getRandomLengthString(int length){
        if(length < 1)
            return null;
        char ac[] = new char[length];
        for(int j = 0; j < ac.length; j++){
            ac[j] = chars[new Random().nextInt(71)];
        }
        return new String(ac);
    }

    /**
     * 随即生成i位中文
     * @param
     * @return
     */
    public static final String getRandomLengthChineseString(int length){
        StringBuilder sb = new StringBuilder();
        for (int i = length; i > 0; i--) {
            sb.append(getRandomChinese());
        }
        return sb.toString();
    }

    /**
     * 随机产生中文,长度范围为start-end
     * @param start
     * @param end
     * @return
     */
    public static String getRandomLengthChiness(int start, int end) {
        StringBuilder sb = new StringBuilder();
        int length = new Random().nextInt(end + 1);
        if (length < start) {
            return getRandomLengthChiness(start, end);
        } else {
            for (int i = 0; i < length; i++) {
                sb.append(getRandomChinese());
            }
        }
        return sb.toString();
    }

    /**
     * 随机获得中文
     * @return
     */
    public static String getRandomChinese() {
        String str = null;
        int highPos, lowPos;
        Random random = new Random();;
        highPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = 161 + Math.abs(random.nextInt(93));
        byte[] b = new byte[2];
        b[0] = (new Integer(highPos)).byteValue();
        b[1] = (new Integer(lowPos)).byteValue();
        try {
            str = new String(b, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }


    /**
     * 删除所有空格
     * @param str
     * @return
     */
    public static String removeBlanks(String str){
        if(str == null || str.equals(EMPTY_STRING)){
            return str;
        }
        char ac[] = str.toCharArray();
        char ac1[] = new char[ac.length];
        int i = 0;
        for(int j = 0; j < ac.length; j++) {
            if (!Character.isSpaceChar(ac[j])){
                ac1[i++] = ac[j];
            }
        }
        return new String(ac1, 0, i);
    }
    /**
     * 二行制转字符串
     * @param b
     * @return
     */
    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = EMPTY_STRING;
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1){
                hs.append("0").append(stmp);
            }
            else{
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }
    /**
     * 获得List的字符串连接
     * @param list
     * @param
     * @return
     */
    public static String getStringFromList(List<String> list,String...split){
        if(list==null){
            return EMPTY_STRING;
        }
        StringBuilder sb=new StringBuilder();
        for(int i=0,j=list.size();i<j;i++){
            sb.append(list.get(i));
            if(split!=null && split.length>0){
                sb.append(split[0]);
            }
        }
        return sb.toString();
    }

    /**
     * 连接对象
     * @param objs
     * @return
     */
    public static String concat(Object...objs) {

        if(objs==null || objs.length<1){
            return EMPTY_STRING;
        }
        StringBuffer sb = new StringBuffer();
        for(Object obj : objs){
            sb.append(obj==null?EMPTY_STRING:obj.toString());
        }
        return sb.toString();
    }




    /**
     * 自由组合列表
     * @param list
     * @return
     */
    public static List<String> combined(List<List<String>> list){
        if(list.size()==1){
            return list.get(0);
        }else
        if(list.size()==2){
            return combined(list.get(0),list.get(1));
        }
        else{
            List<String> tempList = new ArrayList<String>();
            for(int i=list.size();i>0;i--){
                tempList = combined(list.get(i-1),tempList);
            }
            return tempList;
        }
    }

    /**
     * 自由组合两列表
     * @param list1
     * @param list2
     * @return
     */
    public static List<String> combined(List<String> list1,List<String> list2){
        if(list2.isEmpty()){
            return list1;
        }
        List<String> results = new ArrayList<String>();
        for(String str1:list1){
            for(String str2:list2){
                results.add(str1+","+str2);
            }
        }
        return results;
    }


    /**
     * 正则替换
     * @param str 源字符串
     * @param pattern 要替换的模式
     * @param replace 替换成的内容
     * @param connector 连接器
     * @param def 默认值
     * @return
     */
    public static String regex(String str,String pattern,String replace,String connector,String def){
        if(str==null || pattern==null || connector==null){
            return null;
        }
        Pattern p = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
        Matcher matcher = p.matcher(str);
        StringBuffer sb = new StringBuffer();
        int i = 0 ;
        for(i=0;matcher.find();i++){
            if(i>0){
                sb.append(connector);
            }
            matcher.appendReplacement(sb,replace);
        }
        if(i>0){
            matcher.appendTail(sb).append(def);
        }else {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 获得指定小数位
     * @param value
     * @param fixed
     * @return
     */
    public static BigDecimal getFixedBigDecimal(String value, int fixed) {

        try{
            BigDecimal result = new BigDecimal(value);
            return result.setScale(fixed, BigDecimal.ROUND_HALF_UP);
        }catch(Exception e){

        }
        return BigDecimal.ZERO;
    }




    /**
     * 首字母大写
     * @param str
     * @return
     */
    public static String toUpperCaseFirstChar(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
                .append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1))
                .toString();
    }

    /**
     * 首字母小写
     * @param str
     * @return
     */
    public static String toLowerCaseFirstChar(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
                .append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1))
                .toString();
    }
 }