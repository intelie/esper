using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.support.util
{
    public class ArrayHandlingUtil
    {
        public static Object[][] getUnderlyingEvents(EventBean[] events, String[] keys)
        {
            IList<Object[]> resultList = new List<Object[]>();

            for (int i = 0; i < events.Length; i++)
            {
                Object[] row = new Object[keys.Length];
                for (int j = 0; j < keys.Length; j++)
                {
                    row[j] = events[i][keys[j]];
                }
                resultList.Add(row);
            }

            Object[][] results = new Object[resultList.Count][];
            int count = 0;
            foreach (Object[] row in resultList)
            {
                results[count++] = row;
            }
            return results;
        }
    }
}
