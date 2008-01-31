package com.espertech.esper.support.eql;

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
