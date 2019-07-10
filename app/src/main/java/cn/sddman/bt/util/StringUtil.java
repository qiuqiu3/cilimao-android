package cn.sddman.bt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    public static String getStringNoBlank(String str) {
        if(str!=null && !"".equals(str)) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            String strNoBlank = m.replaceAll("");
            return strNoBlank;
        }else {
            return str;
        }
    }
}
