package com.bmsoft.toolkit.holder;

import com.bmsoft.toolkit.pojo.ParamPage;

/**
 * @author llk
 * @date 2022-06-16 17:39
 */
public class ParamPageContextHolder {

    private static ThreadLocal<ParamPage> local = new ThreadLocal<>();


    public static void set(Long pageNum, Long pageSize) {
        set(new ParamPage(pageNum, pageSize));
    }

    public static void set(ParamPage p) {
        local.set(p);
    }

    public static ParamPage get() {
        return local.get();
    }

    public static void clear() {
        local.remove();
    }
}
