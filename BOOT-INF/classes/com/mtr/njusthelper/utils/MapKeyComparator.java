package com.mtr.njusthelper.utils;

import java.util.*;

public class MapKeyComparator implements Comparator<String>
{
    @Override
    public int compare(final String o1, final String o2) {
        return o2.compareTo(o1);
    }
}
