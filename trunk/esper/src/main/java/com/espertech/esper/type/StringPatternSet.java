package com.espertech.esper.type;

import java.io.Serializable;

/**
 * Implementation match a string against a pattern.
 */
public interface StringPatternSet extends Serializable
{
    /**
     * Returns true for a match, false for no-match.
     * @param stringToMatch value to match
     * @return match result
     */
    public boolean match(String stringToMatch);
}
