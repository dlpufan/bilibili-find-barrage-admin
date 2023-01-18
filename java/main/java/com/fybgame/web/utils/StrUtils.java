package com.fybgame.web.utils;

/**
 * @Author:fyb
 * @Date: 2021/3/5 5:35
 * @Version:1.0
 */
public class StrUtils {
    public final static char[] databaseChars = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    public static boolean isBasicBilibiliDatabase(char s){
        for (int i = 0; i < databaseChars.length; i++) {
            if(s == databaseChars[i]){
                return true;
            }
        }
        return false;
    }
}
