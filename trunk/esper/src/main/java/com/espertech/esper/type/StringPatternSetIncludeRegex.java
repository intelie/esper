package com.espertech.esper.type;

public class StringPatternSetIncludeRegex extends StringPatternSetRegexBase
{
    public StringPatternSetIncludeRegex(String likeString)
    {
        super(likeString);
    }

    public Boolean isInclude(String stringToMatch)
    {
        return super.matchString(stringToMatch);
    }

    public Boolean isExclude(String stringToMatch)
    {
        return null;
    }
}
