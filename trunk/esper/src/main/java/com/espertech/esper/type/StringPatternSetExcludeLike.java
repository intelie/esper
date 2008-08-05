package com.espertech.esper.type;

public class StringPatternSetExcludeLike extends StringPatternSetLikeBase
{
    public StringPatternSetExcludeLike(String likeString)
    {
        super(likeString);
    }

    public Boolean isInclude(String stringToMatch)
    {
        return null;
    }

    public Boolean isExclude(String stringToMatch)
    {
        return super.matchString(stringToMatch);
    }
}
