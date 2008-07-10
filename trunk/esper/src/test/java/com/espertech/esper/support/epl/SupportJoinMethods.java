package com.espertech.esper.support.epl;

import java.util.HashMap;
import java.util.Map;

public class SupportJoinMethods
{
    public static Map[] fetchVal(String prefix, int offset, Integer number)
    {
        if ((number == null) || (number == 0))
        {
            return new Map[0];
        }

        Map[] result = new Map[number];
        for (int i = 0; i < number; i++)
        {
            result[i] = new HashMap<String, String>();
            result[i].put("val", prefix + Integer.toString(i + 1));
            result[i].put("index", offset + i + 1);
        }

        return result;
    }

    public static Map fetchValMetadata()
    {
        Map<String, Class> values = new HashMap<String, Class>();
        values.put("val", String.class);
        values.put("index", Integer.class);
        return values;
    }
}
