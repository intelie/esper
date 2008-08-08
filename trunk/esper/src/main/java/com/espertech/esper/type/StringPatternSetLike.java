package com.espertech.esper.type;

import com.espertech.esper.util.LikeUtil;

import java.io.Serializable;

/**
 * 
 */
public class StringPatternSetLike implements StringPatternSet
{
    private final String likeString;
    private final LikeUtil likeUtil;

    /**
     * Ctor.
     * @param likeString pattern to match
     */
    public StringPatternSetLike(String likeString)
    {
        this.likeString = likeString;
        likeUtil = new LikeUtil(likeString, '\\', false);
    }

    /**
     * Match the string returning true for a match, using SQL-like semantics.
     * @param stringToMatch string to match
     * @return true for match
     */
    public boolean match(String stringToMatch)
    {
        return likeUtil.compare(stringToMatch);
    }

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringPatternSetLike that = (StringPatternSetLike) o;

        if (!likeString.equals(that.likeString)) return false;

        return true;
    }

    public int hashCode()
    {
        return likeString.hashCode();
    }
}
