package com.espertech.esper.type;

import java.util.regex.Pattern;
import java.io.Serializable;

public abstract class StringPatternSetRegexBase implements Serializable
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

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StringPatternSetRegexBase that = (StringPatternSetRegexBase) o;

        if (!patternText.equals(that.patternText)) return false;

        return true;
    }

    public int hashCode()
    {
        return patternText.hashCode();
    }
}
