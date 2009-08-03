/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class CollectionUtil<T>
{
    public static <T> String toString(Set<T> set)
    {
        if (set == null)
        {
            return "null";
        }
        if (set.isEmpty())
        {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        String delimiter = "";
        for (T t : set)
        {
            if (t == null)
            {
                continue;
            }
            buf.append(delimiter);
            buf.append(t);
            delimiter = ", ";
        }
        return buf.toString();
    }
}