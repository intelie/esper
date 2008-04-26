package com.espertech.esper.util;

/**
 * Parser of a String input to an object.
 */
public interface SimpleTypeParser
{
    /**
     * Parses the text and returns an object value.
     * @param text to parse
     * @return object value
     */
    public Object parse(String text);
}
