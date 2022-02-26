package com.increff.employee.dto.helper;

import com.increff.employee.util.StringUtil;

/**
 * utils
 */
public class CommonsHelper {

    public static String normalize(String s) {
        return StringUtil.toLowerCase(s).trim();
    }
}
