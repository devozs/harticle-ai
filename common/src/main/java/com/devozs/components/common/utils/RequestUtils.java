package com.devozs.components.common.utils;


public class RequestUtils {

    private RequestUtils(){

    }


    public static String getSourceRequest(String source, String browserDetails ){
        if (source == null || source.length() == 0){

            if(browserDetails == null){
                source = CommonConstants.UNKNOWN;
            }else {
                final String user = browserDetails.toLowerCase();
                if (user.contains(CommonConstants.PYTHON_REQUEST)) {
                    source = CommonConstants.SDK;
                } else if (user.contains(CommonConstants.JAVA)) {
                    source = CommonConstants.INTERNAL;
                } else if (user.contains("msie")) {
                    source = CommonConstants.IE;
                } else if (user.contains("safari")  && user.contains("version")) {
                    source = CommonConstants.SAFARI;
                } else if (user.contains("opr") || user.contains("opera")) {
                    source = CommonConstants.OPERA;
                } else if (user.contains("chrome")) {
                    source = CommonConstants.CHROME;
                } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6")) || (user.contains(
                        "mozilla/4.7")) || (user.contains("mozilla/4.78")) || (user.contains(
                        "mozilla/4.08")) || (user.contains("mozilla/3"))) {
                    source = CommonConstants.NETSCAPE;

                } else if (user.contains("firefox")) {
                    source = CommonConstants.FIREFOX;
                } else if (user.contains("rv")) {
                    source = CommonConstants.IE;
                } else {
                    source = CommonConstants.UNKNOWN;
                }

            }
        }
        return source;
    }
}
