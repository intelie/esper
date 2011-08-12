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

package com.espertech.esper.client;

import junit.framework.TestCase;

import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.support.client.SupportConfigFactory;

public class TestEPServiceProviderManager extends TestCase
{
    public void testGetInstance()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();

        EPServiceProvider runtimeDef1 = EPServiceProviderManager.getDefaultProvider();
        EPServiceProvider runtimeA1 = EPServiceProviderManager.getProvider("A");
        EPServiceProvider runtimeB = EPServiceProviderManager.getProvider("B");
        EPServiceProvider runtimeA2 = EPServiceProviderManager.getProvider("A");
        EPServiceProvider runtimeDef2 = EPServiceProviderManager.getDefaultProvider(configuration);
        EPServiceProvider runtimeA3 = EPServiceProviderManager.getProvider("A", configuration);

        assertNotNull(runtimeDef1);
        assertNotNull(runtimeA1);
        assertNotNull(runtimeB);
        assertTrue(runtimeDef1 == runtimeDef2);
        assertTrue(runtimeA1 == runtimeA2);
        assertTrue(runtimeA1 == runtimeA3);
        assertFalse(runtimeA1 == runtimeDef1);
        assertFalse(runtimeA1 == runtimeB);

        assertEquals("A", runtimeA1.getURI());
        assertEquals("A", runtimeA2.getURI());
        assertEquals("B", runtimeB.getURI());
        assertEquals(EPServiceProviderSPI.DEFAULT_ENGINE_URI, runtimeDef1.getURI());
        assertEquals(EPServiceProviderSPI.DEFAULT_ENGINE_URI, runtimeDef2.getURI());

        runtimeDef1.destroy();
        runtimeA1.destroy();
        runtimeB.destroy();
        runtimeA2.destroy();
        runtimeDef2.destroy();
        runtimeA3.destroy();
    }

    public void testInvalid()
    {
        Configuration configuration = new Configuration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        configuration.addEventType("x", "xxx.noclass");

        try
        {
            EPServiceProviderManager.getProvider("someURI", configuration);
            fail();
        }
        catch (ConfigurationException ex)
        {
            // Expected
        }
    }
    
    public void testDefaultNaming()
    {
    	assertEquals("default", EPServiceProviderSPI.DEFAULT_ENGINE_URI__QUALIFIER);
    	EPServiceProvider epNoArg = EPServiceProviderManager.getDefaultProvider();
    	EPServiceProvider epDefault = EPServiceProviderManager.getProvider("default");
    	EPServiceProvider epNull = EPServiceProviderManager.getProvider(null);
    	
    	assertTrue(epNoArg == epDefault);
    	assertTrue(epNull == epDefault);
    	assertEquals("default", epNull.getURI());
    }
}