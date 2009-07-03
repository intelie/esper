package com.espertech.esper.util;

public class TypeWidenerStringToCharCoercer implements TypeWidener
{
    public Object widen(Object input)
    {
        String result = input.toString();
        if ((result != null) && (result.length() > 0))
        {
            return result.charAt(0);
        }
        return null;
    }
}
