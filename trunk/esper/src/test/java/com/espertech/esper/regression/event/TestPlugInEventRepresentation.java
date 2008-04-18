package com.espertech.esper.regression.event;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.event.EventBean;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Properties;

public class TestPlugInEventRepresentation extends TestCase
{
    private static Log log = LogFactory.getLog(TestPlugInEventRepresentation.class);
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    // TODO: add to configuration operations for real-time adding of dynamic types
    // TODO: add support for event types to have a URI in the EPL
    // TODO: test EventSender for all other types, including wrappers and composite

    /**
     * TODO: use case 1: alias known in advance: no dynamic type resolution, fixed type EventSender predefines event type
     *   register representation with URI
     *   register event type alias and URI
     *   get EventSender(String alias) to send in events
     */

    /**
     * TODO: use case 2: alias known in advance: no dynamic type resolution, dynamic type EventSender decides event type
     *   register representation with URI
     *   register event type aliases and URI
     *   get EventSender(URI uri) to send in events
     */

    /**
     * TODO: use case 3: alias not known in advance: dynamic type resolution, fixed type EventSender predefines event type
     *   register representation with URI
     *   Compile statement with an event type alias or URI, each of the representations are asked to accept
     *   get EventSender(String alias) to send in events
     */

    /**
     * TODO: use case 4: alias not known in advance: dynamic type resolution, dynamic type EventSender decides event type
     *   register representation with URI
     *   Compile statement with an event type alias or URI, each of the representations are asked to accept
     *   get EventSender(URI uri) to send in events
     */

    // TODO: Entry point: (1) register alias (2) use alias in statement (3) Send via EventSender
    // TODO: Entry point: (1) use alias in statement (2) ask representations for type (3) send via EventSender
    // TODO: Entry point: (1) use URI in statement (2) ask representations for type (3) send via EventSender

    // TODO: sendEvent(Object) should also allow plug-in test

    // TODO: should there be a method getEventSender(String uri)

    // TODO: document
    // Called from: 
    //   (A) new statement being compiled (alias to be resolved)
    //   (B) new alias being added through configuration
    
    public void testCustomEventRep() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        configuration.addPlugInEventRepresentation("type://regressionRepresentation", MyPlugInEventRepresentation.class.getName(), "b1,b2");
        configuration.addPlugInEventType("MyRegressionType", "type://regressionRepresentation", "t1_1,t1_2");

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String epl = "select b1, b2, t1_1, t1_2 from MyRegressionType";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(updateListener);

        EventSender sender = epService.getEPRuntime().getEventSender("MyRegressionType");
        sender.sendEvent(makeProperties(new String[][] {{"b1", "x"}, {"b2", "y"}, {"t1_1", null}, {"t1_2", "x2"}}));
        EventBean received = updateListener.assertOneGetNewAndReset();
        ArrayAssertionUtil.assertProps(received, new String[] {"b1", "b2", "t1_1", "t1_2", "x2"},
                new Object[][] {{"b1", "x"}, {"b2", "y"}, {"t1_1", null}, {"t1_2", "x2"}});
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
