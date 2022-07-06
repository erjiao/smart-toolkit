package com.bmsoft.toolkit.web.properties;

import java.util.Set;

/**
 * @author llk
 * @date 2022-06-17 15:35
 */
public interface AccessCtrl {

    boolean isEnabled();

    Set<String> getWhiteIps();

    Set<String> getBlackIps();
}
