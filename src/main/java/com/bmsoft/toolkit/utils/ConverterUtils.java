package com.bmsoft.toolkit.utils;

import lombok.NonNull;

/**
 * @author llk
 * @date 2019-11-10 02:50
 */
public class ConverterUtils {


    public static String object2Str(Object o) {
        if (o == null) {
            return "";
        }
        return o.toString();
    }

    public static Long object2Long(@NonNull Object o) {
        return Long.valueOf(o.toString());
    }

}
