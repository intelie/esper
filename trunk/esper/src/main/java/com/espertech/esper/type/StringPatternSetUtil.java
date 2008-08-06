package com.espertech.esper.type;

import java.util.List;

public class StringPatternSetUtil
{
    public static Boolean evaluate(boolean defaultValue, List<StringPatternSet> patterns, String literal)
    {
        boolean result = defaultValue;

        for (StringPatternSet pattern : patterns)
        {
            if (result)
            {
                if (pattern instanceof StringPatternSetExclude)
                {
                    boolean testResult = pattern.match(literal);
                    if (testResult)
                    {
                        result = false;
                    }
                }
            }
            else
            {
                if (pattern instanceof StringPatternSetInclude)
                {
                    boolean testResult = pattern.match(literal);
                    if (testResult)
                    {
                        result = true;
                    }
                }
            }
        }

        return result;
    }
}
