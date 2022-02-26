package com.increff.employee.dto.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.increff.employee.util.StringUtil;

/**
 * utils
 */
public class CommonsHelper {

    public static String normalize(String s) {
        return StringUtil.toLowerCase(s).trim();
    }

    public static Double normalize(Double d) {
        BigDecimal bigDecimal = new BigDecimal(d).setScale(2, RoundingMode.HALF_DOWN);
        Double scaledDouble = bigDecimal.doubleValue();
        return scaledDouble;
    }
}
