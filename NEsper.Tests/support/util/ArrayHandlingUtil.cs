///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.support.util
{
	public class ArrayHandlingUtil
	{
	    public static Object[][] GetUnderlyingEvents(EventBean[] events, String[] keys)
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
} // End of namespace
