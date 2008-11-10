package com.espertech.esper.support.plugin;

import com.espertech.esper.plugin.PluginLoader;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Properties;
import java.util.List;
import java.util.LinkedList;

public class SupportPluginLoader implements PluginLoader
{
    private static List<String> names = new LinkedList<String>();
    private static List<Properties> props = new LinkedList<Properties>();

    public static List<Properties> getProps()
    {
        return props;
    }

    public static List<String> getNames()
    {
        return names;
    }

    public void destroy()
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void init(String name, Properties properties, EPServiceProviderSPI epService)
    {
        names.add(name);
        props.add(properties);
    }
}
