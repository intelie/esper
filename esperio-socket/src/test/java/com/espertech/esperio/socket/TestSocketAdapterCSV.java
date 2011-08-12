/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.socket;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esperio.socket.config.ConfigurationSocketAdapter;
import com.espertech.esperio.socket.config.SocketConfig;
import com.espertech.esperio.socket.config.DataType;
import junit.framework.TestCase;

import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class TestSocketAdapterCSV extends TestCase
{
    public static String newline = System.getProperty("line.separator");
    private SupportUpdateListener listener;

    public void setUp() throws Exception {
        listener = new SupportUpdateListener();
    }

    public void testSendCSV() throws Exception
    {
        ConfigurationSocketAdapter adapterConfig = new ConfigurationSocketAdapter();

        int port = 7989;
        String engineURI = "TestSocketAdapterCSV";

        SocketConfig socket = new SocketConfig();
        socket.setDataType(DataType.CSV);
        socket.setPort(port);
        adapterConfig.getSockets().put("SocketService", socket);

        EsperIOSocketAdapter adapter = new EsperIOSocketAdapter(adapterConfig, engineURI);

        Configuration engineConfig = new Configuration();
        engineConfig.addEventType("SupportBean", SupportBean.class);
        EPServiceProvider provider = EPServiceProviderManager.getProvider(engineURI, engineConfig);

        adapter.start();

        EPStatement stmt = provider.getEPAdministrator().createEPL("select * from SupportBean");
        stmt.addListener(listener);

        String[] fields = "stringProp,intProp".split(",");
        SupportSocketClientCSV client = new SupportSocketClientCSV(port);

        client.send("stream=SupportBean,stringProp=E1,intProp=20" + newline);
        Thread.sleep(200);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 20});

        client.send("stream=SupportBean,stringProp=E2,intProp=20,xxxx,x=msdjdjdj,intProp=21" + newline);
        Thread.sleep(200);
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E2", 21});

        client.close();
        adapter.destroy();
        provider.destroy();
    }
}