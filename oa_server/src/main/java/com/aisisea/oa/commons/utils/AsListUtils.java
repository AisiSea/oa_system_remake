package com.aisisea.oa.commons.utils;

import java.util.ArrayList;
import java.util.List;

public class AsListUtils {

    static public <T> List<T> asList(T[] ts) {
        List<T> list = new ArrayList<>();
        for (T item : ts)
            list.add(item);
        return list;
    }

}
