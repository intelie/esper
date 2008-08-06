package com.espertech.esper.type;

public class StringPatternSetExcludeRegex extends StringPatternSetRegexBase implements StringPatternSetExclude
{
    public StringPatternSetExcludeRegex(String likeString)
    {
        super(likeString);
    }

    public boolean match(String stringToMatch)
    {
        return super.matchString(stringToMatch);
    }
}
