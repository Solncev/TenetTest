package com.solncev.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Марат on 01.07.2017.
 */
public class LinkDetector {
    public static String extractUrl(String text) {
        int start = 1;
        int end = 1;
        Pattern pattern = Pattern.compile(
                "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                        + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                        + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
                Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
        Matcher urlMatcher = pattern.matcher(text);
        while (urlMatcher.find()) {
            start = urlMatcher.start(1);
            end = urlMatcher.end();
        }
        return text.substring(start, end);
    }
}
