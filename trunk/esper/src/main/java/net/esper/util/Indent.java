package net.esper.util;

import java.util.Arrays;

/**
 * Utility class around indenting and formatting text.
 */
public class Indent
{
    /**
     * Utility method to indent a text for a number of characters.
     * @param numChars is the number of character to indent with spaces
     * @return the formatted string
     */
    public static String indent(int numChars)
    {
        if (numChars < 0)
        {
            throw new IllegalArgumentException("Number of characters less then zero");
        }
        char[] buf = new char[numChars];
        Arrays.fill(buf, ' ');
        return String.valueOf(buf);
    }
}
