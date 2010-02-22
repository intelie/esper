package com.espertech.esper.support.plugin;

import com.espertech.esper.plugin.PluginLoader;
import com.espertech.esper.plugin.PluginLoaderInitContext;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Properties;
import java.util.List;
import java.util.LinkedList;
import java.util.Date;

public class SupportPluginLoader implements PluginLoader
{
    private static List<String> names = new LinkedList<String>();
    private static List<Properties> props = new LinkedList<Properties>();
    private static List<Date> postInitializes = new LinkedList<Date>();
    private static List<Date> destroys = new LinkedList<Date>();

    public static void reset()
    {
        names.clear();
        props.clear();
        postInitializes.clear();
        destroys.clear();        
    }

    public static List<Properties> getProps()
    {
        return props;
    }

    public static List<String> getNames()
    {
        return names;
    }

    public static List<Date> getPostInitializes()
    {
        return postInitializes;
    }

    public static List<Date> getDestroys()
    {
        return destroys;
    }

    public void postInitialize()
    {
        postInitializes.add(new Date());
    }

    public void destroy()
    {
        destroys.add(new Date());
    }

    public void init(PluginLoaderInitContext context)
    {
        names.add(context.getName());
        props.add(context.getProperties());
    }
}
