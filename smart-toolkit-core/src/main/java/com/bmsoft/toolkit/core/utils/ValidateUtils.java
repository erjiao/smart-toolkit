package com.bmsoft.toolkit.core.utils;

import java.util.Collection;

/**
 * @author llk
 * @date 2019-10-09 23:50
 */
public class ValidateUtils {


    public static boolean isValid(Collection<?> c) {
        return c != null && c.size() > 0;
    }


    public static boolean isValid(Object [] o) {
        return o != null && o.length > 0;
    }

    public static boolean isNotValid(Collection<?> c) {
        return !isValid(c);
    }

}
