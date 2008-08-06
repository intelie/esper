package com.espertech.esper.type;

import com.espertech.esper.util.LikeUtil;

import java.io.Serializable;

public abstract class StringPatternSetLikeBase implements Serializable
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

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringPatternSetLikeBase that = (StringPatternSetLikeBase) o;

        if (!likeString.equals(that.likeString)) return false;

        return true;
    }

    public int hashCode()
    {
        return likeString.hashCode();
    }
}
