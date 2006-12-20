package net.esper.support.util;

import net.esper.event.EventBean;

import java.util.List;
import java.util.LinkedList;

public class ArrayHandlingUtil
{
    public static Object[][] getUnderlyingEvents(EventBean[] events, String[] keys)
    {
        List<Object[]> resultList = new LinkedList<Object[]>();

        for (int i = 0; i < events.length; i++)
        {
            Object[] row = new Object[keys.length];
            for (int j = 0; j < keys.length; j++)
            {
                row[j] = events[i].get(keys[j]);
            }
            resultList.add(row);
        }

        Object[][] results = new Object[resultList.size()][];
        int count = 0;
        for (Object[] row : resultList)
        {
            results[count++] = row;
        }
        return results;
    }

}
