package com.longge.gather.gnss.utils;

import java.util.Collection;
/**
 * @description 集合空值判断
 * @author jianglong
 * @create 2019-03-20
 **/
public class CollectionUtil {
	
    public static boolean isNotNullAndNotEmpty(Collection<?> list) {
        if (list != null && !list.isEmpty())
            return true;
        return false;
    }

    public static boolean isNullOrEmpty(Collection<?> list) {
        if (list == null || list.isEmpty())
            return true;
        return false;
    }

    public static boolean isNotNull(Collection<?> list) {
        if (list == null)
            return false;
        return true;
    }

}