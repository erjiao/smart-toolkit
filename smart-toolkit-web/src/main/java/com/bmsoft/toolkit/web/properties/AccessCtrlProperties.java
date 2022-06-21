package com.bmsoft.toolkit.web.properties;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author llk
 * @date 2020-08-17 23:13
 */
@Data
public class AccessCtrlProperties implements AccessCtrl {

    private boolean enable;

    private Set<String> whiteIps = new HashSet<>();

    private Set<String> blackIps = new HashSet<>();
}
