package com.espertech.esper.type;

import com.espertech.esper.util.LikeUtil;

public abstract class StringPatternSetLikeBase implements StringPatternSet
{
    private final String likeString;
    private final LikeUtil likeUtil;

    public StringPatternSetLikeBase(String likeString)
    {
        this.likeString = likeString;
        likeUtil = new LikeUtil(likeString, '\\', false);
    }

    protected boolean matchString(String stringToMatch)
    {
        return likeUtil.compare(stringToMatch);
    }
}
