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

package com.espertech.esper.regression.client;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.plugin.SupportPluginLoader;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.core.EPServiceProviderSPI;

import java.util.Properties;

import junit.framework.TestCase;

public class TestAdapterLoader extends TestCase
{
    public void setUp()
    {
        SupportPluginLoader.reset();
    }

    public void testAdapterLoader() throws Exception
    {
        // Assure destroy order ESPER-489
        Configuration config = SupportConfigFactory.getConfiguration();

        Properties props = new Properties();
        props.put("name", "val");
        config.addPluginLoader("MyLoader", SupportPluginLoader.class.getName(), props);

        props = new Properties();
        props.put("name2", "val2");
        config.addPluginLoader("MyLoader2", SupportPluginLoader.class.getName(), props);

        EPServiceProvider service = EPServiceProviderManager.getProvider("TestAdapterLoader", config);
        assertEquals(2, SupportPluginLoader.getNames().size());
        assertEquals(2, SupportPluginLoader.getPostInitializes().size());
        assertEquals("MyLoader", SupportPluginLoader.getNames().get(0));
        assertEquals("MyLoader2", SupportPluginLoader.getNames().get(1));
        assertEquals("val", SupportPluginLoader.getProps().get(0).get("name"));
        assertEquals("val2", SupportPluginLoader.getProps().get(1).get("name2"));

        EPServiceProviderSPI spi = (EPServiceProviderSPI) service;
        Object loader = spi.getEngineEnvContext().getEnvironment().get("plugin-loader/MyLoader");
        assertTrue(loader instanceof SupportPluginLoader);
        loader = spi.getEngineEnvContext().getEnvironment().get("plugin-loader/MyLoader2");
        assertTrue(loader instanceof SupportPluginLoader);

        SupportPluginLoader.getPostInitializes().clear();
        SupportPluginLoader.getNames().clear();
        service.initialize();
        assertEquals(2, SupportPluginLoader.getPostInitializes().size());
        assertEquals(2, SupportPluginLoader.getNames().size());

        service.destroy();
        assertEquals(2, SupportPluginLoader.getDestroys().size());
        assertEquals("val2", SupportPluginLoader.getDestroys().get(0).get("name2"));
        assertEquals("val", SupportPluginLoader.getDestroys().get(1).get("name"));
    }

    public void testDestroyObtainTwice() {
        Configuration cf = SupportConfigFactory.getConfiguration();
        cf.addPluginLoader("AP", SupportPluginLoader.class.getName(), null);
        EPServiceProviderManager.getProvider("TestAdapterLoader", cf);
        EPServiceProvider ep = EPServiceProviderManager.getProvider("TestAdapterLoader");
        ep.destroy();
        assertEquals(1, SupportPluginLoader.getDestroys().size());
    }
}
