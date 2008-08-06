package com.espertech.esper.type;

public class StringPatternSetIncludeLike extends StringPatternSetLikeBase implements StringPatternSetInclude
{
    public StringPatternSetIncludeLike(String likeString)
    {
        super(likeString);
    }

    public boolean match(String stringToMatch)
    {
        return super.matchString(stringToMatch);
    }
}
