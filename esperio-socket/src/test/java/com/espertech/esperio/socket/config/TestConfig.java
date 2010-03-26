/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.socket.config;

import junit.framework.TestCase;

import java.net.URL;

public class TestConfig extends TestCase {
    private ConfigurationSocketAdapter config;

    public void setUp()
    {
        config = new ConfigurationSocketAdapter();
    }

    public void testConfigureFromStream() throws Exception
    {
        URL url = this.getClass().getClassLoader().getResource("esperio-socket-sample-config.xml");
        ConfigurationSocketAdapterParser.doConfigure(config, url.openStream(), url.toString());
        assertFileConfig(config);
    }

    public void testEngineDefaults()
    {
        config = new ConfigurationSocketAdapter();
    }

    protected static void assertFileConfig(ConfigurationSocketAdapter config) throws Exception
    {
        assertEquals(2, config.getSockets().size());
        SocketConfig socket = config.getSockets().get("mysocketOne");
        assertEquals(7100, socket.getPort());
        assertEquals(DataType.OBJECT, socket.getDataType());

        socket = config.getSockets().get("mysocketTwo");
        assertEquals(7100, socket.getPort());
        assertEquals(DataType.CSV, socket.getDataType());
    }
}
