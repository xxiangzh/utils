package com.xzh.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author 向振华
 * @date 2020/08/14 15:10
 */
public class ObjectUtils {

    /**
     * 是否为空
     *
     * @param object
     * @return
     */
    public static boolean isNull(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            return StringUtils.isBlank((String) object);
        } else if (object instanceof Collection) {
            return CollectionUtils.isEmpty((Collection) object);
        } else {
            return false;
        }
    }
}
