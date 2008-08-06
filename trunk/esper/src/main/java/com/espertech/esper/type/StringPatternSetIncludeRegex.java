package com.espertech.esper.type;

public class StringPatternSetIncludeRegex extends StringPatternSetRegexBase implements StringPatternSetInclude
{
    public StringPatternSetIncludeRegex(String likeString)
    {
        super(likeString);
    }

    public boolean match(String stringToMatch)
    {
        return super.matchString(stringToMatch);
    }
}
