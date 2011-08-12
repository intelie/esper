/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.epl;

import javax.naming.spi.InitialContextFactory;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import java.util.Hashtable;
import java.util.Map;
import java.util.HashMap;

public class SupportInitialContextFactory implements InitialContextFactory
{
    private static Map<String, Object> contextEntries = new HashMap<String, Object>();

    public static void addContextEntry(String name, Object value)
    {
        contextEntries.put(name, value);
    }

    public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException
    {
        return new SupportContext(contextEntries);
    }

    public class SupportContext extends InitialContext
    {
        private Map<String, Object> contextEntries;

        public SupportContext(Map<String, Object> contextEntries)
                throws NamingException
        {
            this.contextEntries = contextEntries;
        }

        public Object lookup(String name) throws NamingException
        {
            if (!contextEntries.containsKey(name))
            {
                throw new NamingException("Name '" + name + "' not found");
            }
            return contextEntries.get(name);
        }
    }
}
