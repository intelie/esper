package com.espertech.esper.type;

import java.util.regex.Pattern;

public abstract class StringPatternSetRegexBase implements StringPatternSet
{
    private final String patternText;
    private final Pattern pattern;

    public StringPatternSetRegexBase(String patternText)
    {
        this.patternText = patternText;
        this.pattern = Pattern.compile(patternText);
    }

    protected boolean matchString(String stringToMatch)
    {
        return pattern.matcher(stringToMatch).matches();
    }
}
