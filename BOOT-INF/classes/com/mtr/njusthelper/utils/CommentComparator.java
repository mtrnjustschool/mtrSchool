package com.mtr.njusthelper.utils;

import com.mtr.njusthelper.entity.*;
import java.util.*;

public class CommentComparator implements Comparator<Comment>
{
    @Override
    public int compare(final Comment c1, final Comment c2) {
        final Date c1date = c1.getCreatetime();
        final Date c2date = c2.getCreatetime();
        return c2date.compareTo(c1date);
    }
}
