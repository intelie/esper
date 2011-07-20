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
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.plugin.PlugInEventRepresentationContext;
import com.espertech.esper.plugin.PlugInEventTypeHandlerContext;
import com.espertech.esper.plugin.PlugInEventBeanReflectorContext;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class TestPlugInEventRepresentation extends TestCase
{
    private static Log log = LogFactory.getLog(TestPlugInEventRepresentation.class);
    private EPServiceProvider epService;
    private SupportUpdateListener[] listeners;

    public void setUp()
    {
        listeners = new SupportUpdateListener[5];
        for (int i = 0; i < listeners.length; i++)
        {
            listeners[i] = new SupportUpdateListener();
        }
    }

    /*
     * Use case 1: static event type resolution, no event object reflection (static event type assignment)
     * Use case 2: static event type resolution, dynamic event object reflection and event type assignment
     *   a) Register all representations with URI via configuration
     *   b) Register event type name and specify the list of URI to use for resolving:
     *     // at engine initialization time it obtain instances of an EventType for each name
     *   c) Create statement using the registered event type name
     *   d) Get EventSender to send in that specific type of event
     */
    public void testPreConfigStaticTypeResolution() throws Exception
    {
        Configuration configuration = getConfiguration();
        configuration.addPlugInEventType("TestTypeOne", new URI[] {new URI("type://properties/test1/testtype")}, "t1");
        configuration.addPlugInEventType("TestTypeTwo", new URI[] {new URI("type://properties/test2")}, "t2");
        configuration.addPlugInEventType("TestTypeThree", new URI[] {new URI("type://properties/test3")}, "t3");
        configuration.addPlugInEventType("TestTypeFour", new URI[] {new URI("type://properties/test2/x"), new URI("type://properties/test3")}, "t4");

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        runAssertionCaseStatic(epService);
    }

    public void testRuntimeConfigStaticTypeResolution() throws Exception
    {
        Configuration configuration = getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        ConfigurationOperations runtimeConfig = epService.getEPAdministrator().getConfiguration();
        runtimeConfig.addPlugInEventType("TestTypeOne", new URI[] {new URI("type://properties/test1/testtype")}, "t1");
        runtimeConfig.addPlugInEventType("TestTypeTwo", new URI[] {new URI("type://properties/test2")}, "t2");
        runtimeConfig.addPlugInEventType("TestTypeThree", new URI[] {new URI("type://properties/test3")}, "t3");
        runtimeConfig.addPlugInEventType("TestTypeFour", new URI[] {new URI("type://properties/test2/x"), new URI("type://properties/test3")}, "t4");

        runAssertionCaseStatic(epService);
    }

    /*
     * Use case 3: dynamic event type resolution
     *   a) Register all representations with URI via configuration
     *   b) Via configuration, set a list of URIs to use for resolving new event type names
     *   c) Compile statement with an event type name that is not defined yet, each of the representations are asked to accept, in URI hierarchy order
     *     admin.createEPL("select a, b, c from MyEventType");
     *    // engine asks each event representation to create an EventType, takes the first valid one
     *   d) Get EventSender to send in that specific type of event, or a URI-list dynamic reflection sender
     */
    public void testRuntimeConfigDynamicTypeResolution() throws Exception
    {
        Configuration configuration = getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        URI[] uriList = new URI[] {new URI("type://properties/test2/myresolver")};
        epService.getEPAdministrator().getConfiguration().setPlugInEventTypeResolutionURIs(uriList);

        runAssertionCaseDynamic(epService);
    }

    public void testStaticConfigDynamicTypeResolution() throws Exception
    {
        URI[] uriList = new URI[] {new URI("type://properties/test2/myresolver")};
        Configuration configuration = getConfiguration();
        configuration.setPlugInEventTypeResolutionURIs(uriList);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        runAssertionCaseDynamic(epService);
    }

    public void testInvalid() throws Exception
    {
        Configuration configuration = getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        try
        {
            epService.getEPRuntime().getEventSender(new URI[0]);
            fail();
        }
        catch (EventTypeException ex)
        {
            assertEquals("Event sender for resolution URIs '[]' did not return at least one event representation's event factory", ex.getMessage());
        }
    }

    public void testContextContents() throws Exception
    {
        Configuration configuration = getConfiguration();
        configuration.addPlugInEventRepresentation(new URI("type://test/support"), SupportEventRepresentation.class.getName(), "abc");
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();

        PlugInEventRepresentationContext initContext = SupportEventRepresentation.getInitContext();
        assertEquals(new URI("type://test/support"), initContext.getEventRepresentationRootURI());
        assertEquals("abc", initContext.getRepresentationInitializer());
        assertNotNull(initContext.getEventAdapterService());

        ConfigurationOperations runtimeConfig = epService.getEPAdministrator().getConfiguration();
        runtimeConfig.addPlugInEventType("TestTypeOne", new URI[] {new URI("type://test/support?a=b&c=d")}, "t1");

        PlugInEventTypeHandlerContext context = SupportEventRepresentation.getAcceptTypeContext();
        assertEquals(new URI("type://test/support?a=b&c=d"), context.getEventTypeResolutionURI());
        assertEquals("t1", context.getTypeInitializer());
        assertEquals("TestTypeOne", context.getEventTypeName());

        context = SupportEventRepresentation.getEventTypeContext();
        assertEquals(new URI("type://test/support?a=b&c=d"), context.getEventTypeResolutionURI());
        assertEquals("t1", context.getTypeInitializer());
        assertEquals("TestTypeOne", context.getEventTypeName());

        epService.getEPRuntime().getEventSender(new URI[] {new URI("type://test/support?a=b")});
        PlugInEventBeanReflectorContext contextBean = SupportEventRepresentation.getEventBeanContext();
        assertEquals("type://test/support?a=b", contextBean.getResolutionURI().toString());
    }

    private void runAssertionCaseDynamic(EPServiceProvider epService) throws Exception
    {
        // type resolved for each by the first event representation picking both up, i.e. the one with "r2" since that is the most specific URI
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from TestTypeOne");
        stmt.addListener(listeners[0]);
        stmt = epService.getEPAdministrator().createEPL("select * from TestTypeTwo");
        stmt.addListener(listeners[1]);

        // static senders
        EventSender sender = epService.getEPRuntime().getEventSender("TestTypeOne");
        sender.sendEvent(makeProperties(new String[][] {{"r2", "A"}}));
        ArrayAssertionUtil.assertAllProps(listeners[0].assertOneGetNewAndReset(), new Object[] {"A"});
        assertFalse(listeners[0].isInvoked());

        sender = epService.getEPRuntime().getEventSender("TestTypeTwo");
        sender.sendEvent(makeProperties(new String[][] {{"r2", "B"}}));
        ArrayAssertionUtil.assertAllProps(listeners[1].assertOneGetNewAndReset(), new Object[] {"B"});
    }

    private Configuration getConfiguration() throws URISyntaxException
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInEventRepresentation(new URI("type://properties"), MyPlugInEventRepresentation.class.getName(), "r3");
        configuration.addPlugInEventRepresentation(new URI("type://properties/test1"), MyPlugInEventRepresentation.class.getName(), "r1");
        configuration.addPlugInEventRepresentation(new URI("type://properties/test2"), MyPlugInEventRepresentation.class.getName(), "r2");
        return configuration;
    }

    private void runAssertionCaseStatic(EPServiceProvider epService) throws URISyntaxException
    {
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from TestTypeOne");
        stmt.addListener(listeners[0]);
        stmt = epService.getEPAdministrator().createEPL("select * from TestTypeTwo");
        stmt.addListener(listeners[1]);
        stmt = epService.getEPAdministrator().createEPL("select * from TestTypeThree");
        stmt.addListener(listeners[2]);
        stmt = epService.getEPAdministrator().createEPL("select * from TestTypeFour");
        stmt.addListener(listeners[3]);

        // static senders
        EventSender sender = epService.getEPRuntime().getEventSender("TestTypeOne");
        sender.sendEvent(makeProperties(new String[][] {{"r1", "A"}, {"t1", "B"}}));
        ArrayAssertionUtil.assertAllProps(listeners[0].assertOneGetNewAndReset(), new Object[] {"A", "B"});
        assertFalse(listeners[3].isInvoked() || listeners[1].isInvoked() || listeners[2].isInvoked());

        sender = epService.getEPRuntime().getEventSender("TestTypeTwo");
        sender.sendEvent(makeProperties(new String[][] {{"r2", "C"}, {"t2", "D"}}));
        ArrayAssertionUtil.assertAllProps(listeners[1].assertOneGetNewAndReset(), new Object[] {"C", "D"});
        assertFalse(listeners[3].isInvoked() || listeners[0].isInvoked() || listeners[2].isInvoked());

        sender = epService.getEPRuntime().getEventSender("TestTypeThree");
        sender.sendEvent(makeProperties(new String[][] {{"r3", "E"}, {"t3", "F"}}));
        ArrayAssertionUtil.assertAllProps(listeners[2].assertOneGetNewAndReset(), new Object[] {"E", "F"});
        assertFalse(listeners[3].isInvoked() || listeners[1].isInvoked() || listeners[0].isInvoked());

        sender = epService.getEPRuntime().getEventSender("TestTypeFour");
        sender.sendEvent(makeProperties(new String[][] {{"r2", "G"}, {"t4", "H"}}));
        ArrayAssertionUtil.assertAllProps(listeners[3].assertOneGetNewAndReset(), new Object[] {"G", "H"});
        assertFalse(listeners[0].isInvoked() || listeners[1].isInvoked() || listeners[2].isInvoked());

        // dynamic sender - decides on event type thus a particular update listener should see the event
        URI[] uriList = new URI[] {new URI("type://properties/test1"), new URI("type://properties/test2")};
        EventSender dynamicSender = epService.getEPRuntime().getEventSender(uriList);
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r3", "I"}, {"t3", "J"}}));
        ArrayAssertionUtil.assertAllProps(listeners[2].assertOneGetNewAndReset(), new Object[] {"I", "J"});
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r1", "K"}, {"t1", "L"}}));
        ArrayAssertionUtil.assertAllProps(listeners[0].assertOneGetNewAndReset(), new Object[] {"K", "L"});
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r2", "M"}, {"t2", "N"}}));
        ArrayAssertionUtil.assertAllProps(listeners[1].assertOneGetNewAndReset(), new Object[] {"M", "N"});
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r2", "O"}, {"t4", "P"}}));
        ArrayAssertionUtil.assertAllProps(listeners[3].assertOneGetNewAndReset(), new Object[] {"O", "P"});
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r2", "O"}, {"t3", "P"}}));
        assertNoneReceived();

        uriList = new URI[] {new URI("type://properties/test2")};
        dynamicSender = epService.getEPRuntime().getEventSender(uriList);
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r1", "I"}, {"t1", "J"}}));
        assertNoneReceived();
        dynamicSender.sendEvent(makeProperties(new String[][] {{"r2", "Q"}, {"t2", "R"}}));
        ArrayAssertionUtil.assertAllProps(listeners[1].assertOneGetNewAndReset(), new Object[] {"Q", "R"});
    }

    private void assertNoneReceived()
    {
        for (int i = 0; i < listeners.length; i++)
        {
            assertFalse(listeners[i].isInvoked());            
        }
    }

    private Properties makeProperties(String[][] values)
    {
        Properties event = new Properties();
        for (int i = 0; i < values.length; i++)
        {
            event.put(values[i][0], values[i][1]);
        }
        return event;
    }
}
