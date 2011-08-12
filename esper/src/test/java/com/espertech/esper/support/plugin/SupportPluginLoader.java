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
    private static List<Properties> destroys = new LinkedList<Properties>();

    private Properties properties;

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

    public static List<Properties> getDestroys()
    {
        return destroys;
    }

    public void postInitialize()
    {
        postInitializes.add(new Date());
    }

    public void destroy()
    {
        destroys.add(properties);
    }

    public void init(PluginLoaderInitContext context)
    {
        names.add(context.getName());
        props.add(context.getProperties());
        properties = context.getProperties();
    }
}
