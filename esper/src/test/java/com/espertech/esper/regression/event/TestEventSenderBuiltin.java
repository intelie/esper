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

package com.espertech.esper.regression.event;

import com.espertech.esper.client.*;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.bean.SupportBean_G;
import com.espertech.esper.support.bean.SupportMarkerImplA;
import com.espertech.esper.support.bean.SupportMarkerInterface;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPathConstants;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class TestEventSenderBuiltin extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();
    }

    public void testSenderPOJO() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        configuration.addEventType("Marker", SupportMarkerInterface.class);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        // type resolved for each by the first event representation picking both up, i.e. the one with "r2" since that is the most specific URI
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean");
        stmt.addListener(listener);

        // send right event
        EventSender sender = epService.getEPRuntime().getEventSender("SupportBean");
        Object supportBean = new SupportBean();
        sender.sendEvent(supportBean);
        assertSame(supportBean, listener.assertOneGetNewAndReset().getUnderlying());

        // send wrong event
        try
        {
            sender.sendEvent(new SupportBean_G("G1"));
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Event object of type com.espertech.esper.support.bean.SupportBean_G does not equal, extend or implement the type com.espertech.esper.support.bean.SupportBean of event type 'SupportBean'", ex.getMessage());
        }

        // test an interface
        sender = epService.getEPRuntime().getEventSender("Marker");
        stmt.destroy();
        stmt = epService.getEPAdministrator().createEPL("select * from Marker");
        stmt.addListener(listener);
        SupportMarkerImplA implA = new SupportMarkerImplA("Q2");
        sender.sendEvent(implA);
        assertSame(implA, listener.assertOneGetNewAndReset().getUnderlying());
        SupportBean_G implB = new SupportBean_G("Q3");
        sender.sendEvent(implB);
        assertSame(implB, listener.assertOneGetNewAndReset().getUnderlying());
        sender.sendEvent(implB);
        assertSame(implB, listener.assertOneGetNewAndReset().getUnderlying());
    }

    public void testSenderMap() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        Map<String, Object> myMapType = makeMap(new Object[][] {{"f1", Integer.class}});
        configuration.addEventType("MyMap", myMapType);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        // type resolved for each by the first event representation picking both up, i.e. the one with "r2" since that is the most specific URI
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from MyMap");
        stmt.addListener(listener);

        // send right event
        EventSender sender = epService.getEPRuntime().getEventSender("MyMap");
        Map<String, Object> myMap = makeMap(new Object[][] {{"f1", 10}});
        sender.sendEvent(myMap);
        assertSame(10, listener.assertOneGetNewAndReset().get("f1"));

        // send wrong event
        try
        {
            sender.sendEvent(new SupportBean());
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Unexpected event object of type com.espertech.esper.support.bean.SupportBean, expected java.util.Map", ex.getMessage());
        }
    }

    public void testXML() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        ConfigurationEventTypeXMLDOM typeMeta = new ConfigurationEventTypeXMLDOM();
        typeMeta.setRootElementName("a");
        typeMeta.addXPathProperty("element1", "/a/b/c", XPathConstants.STRING);
        configuration.addEventType("AEvent", typeMeta);

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        String stmtText = "select b.c as type, element1 from AEvent";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        Document doc = getDocument("<a><b><c>text</c></b></a>");
        EventSender sender = epService.getEPRuntime().getEventSender("AEvent");
        sender.sendEvent(doc);
        
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("text", event.get("type"));
        assertEquals("text", event.get("element1"));

        // send wrong event
        try
        {
            sender.sendEvent(getDocument("<xxxx><b><c>text</c></b></xxxx>"));
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Unexpected root element name 'xxxx' encountered, expected a root element name of 'a'", ex.getMessage());
        }

        try
        {
            sender.sendEvent(new SupportBean());
            fail();
        }
        catch (EPException ex)
        {
            assertEquals("Unexpected event object type 'com.espertech.esper.support.bean.SupportBean' encountered, please supply a org.w3c.dom.Document or Element node", ex.getMessage());
        }

        // test adding a second type for the same root element
        configuration = SupportConfigFactory.getConfiguration();
        typeMeta = new ConfigurationEventTypeXMLDOM();
        typeMeta.setRootElementName("a");
        typeMeta.addXPathProperty("element2", "//c", XPathConstants.STRING);
        typeMeta.setEventSenderValidatesRoot(false);
        epService.getEPAdministrator().getConfiguration().addEventType("BEvent", typeMeta);

        stmtText = "select element2 from BEvent";
        EPStatement stmtTwo = epService.getEPAdministrator().createEPL(stmtText);

        // test sender that doesn't care about the root element
        EventSender senderTwo = epService.getEPRuntime().getEventSender("BEvent");
        senderTwo.sendEvent(getDocument("<xxxx><b><c>text</c></b></xxxx>"));    // allowed, not checking

        event = stmtTwo.iterator().next();
        assertEquals("text", event.get("element2"));
    }

    public void testInvalid()
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addEventType("SupportBean", SupportBean.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            epService.getEPRuntime().getEventSender("ABC");
            fail();
        }
        catch (EventTypeException ex)
        {
            assertEquals("Event type named 'ABC' could not be found", ex.getMessage());
        }

        EPStatement stmt = epService.getEPAdministrator().createEPL("insert into ABC select *, string as value from SupportBean");
        stmt.addListener(listener);

        try
        {
            epService.getEPRuntime().getEventSender("ABC");
            fail("Event type named 'ABC' could not be found");
        }
        catch (EventTypeException ex)
        {
            assertEquals("An event sender for event type named 'ABC' could not be created as the type is internal", ex.getMessage());
        }        
    }

    private Map<String, Object> makeMap(Object[][] entries)
    {
        Map result = new HashMap<String, Object>();
        for (int i = 0; i < entries.length; i++)
        {
            result.put(entries[i][0], entries[i][1]);
        }
        return result;
    }

    private Document getDocument(String xml) throws Exception
    {
        StringReader reader = new StringReader(xml);
        InputSource source = new InputSource(reader);
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        return builderFactory.newDocumentBuilder().parse(source);
    }
}
