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
                Boolean testResult = pattern.isExclude(literal);
                if ((testResult != null) && (testResult))
                {
                    result = false;
                }
            }
            else
            {
                Boolean testResult = pattern.isInclude(literal);
                if ((testResult != null) && (testResult))
                {
                    result = true;
                }
            }
        }

        return result;
    }
}
