package com.espertech.esper.type;

public class StringPatternSetExcludeRegex extends StringPatternSetRegexBase
{
    public StringPatternSetExcludeRegex(String likeString)
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
