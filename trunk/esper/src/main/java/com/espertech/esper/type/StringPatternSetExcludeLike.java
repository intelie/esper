package com.espertech.esper.type;

public class StringPatternSetExcludeLike extends StringPatternSetLikeBase implements StringPatternSetExclude
{
    public StringPatternSetExcludeLike(String likeString)
    {
        super(likeString);
    }

    public boolean match(String stringToMatch)
    {
        return super.matchString(stringToMatch);
    }
}
