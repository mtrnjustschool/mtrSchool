package com.mtr.njusthelper.utils;

import com.mtr.njusthelper.entity.*;
import java.util.*;

public class ArticleComparator implements Comparator<Article>
{
    @Override
    public int compare(final Article a1, final Article a2) {
        final Date a1Date = a1.getModifytime();
        final Date a2Date = a2.getModifytime();
        return a2Date.compareTo(a1Date);
    }
}
