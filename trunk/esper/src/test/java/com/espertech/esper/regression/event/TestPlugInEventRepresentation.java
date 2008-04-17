package com.espertech.esper.regression.event;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestPlugInEventRepresentation extends TestCase
{
    private static Log log = LogFactory.getLog(TestPlugInEventRepresentation.class);
    private EPServiceProvider epService;
    private SupportUpdateListener updateListener;

    // TODO: add to configuration operations for real-time adding of dynamic types
    // TODO: add support for event types to have a URI in the EPL
    

    // TODO: document
    // Called from: 
    //   (A) new statement being compiled (alias to be resolved)
    //   (B) new alias being added through configuration
    
    public void testCustomEventRep() throws Exception
    {
        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        configuration.addPlugInEventRepresentation("type://regressionRepresentation", MyPlugInEventRepresentation.class.getName(), "property=value");
        configuration.addPlugInEventType("MyRegressionType", "type://regressionRepresentation/MyRegressionType", "property2=value2");

        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        updateListener = new SupportUpdateListener();

        String epl = "select property, property2 from MyRegressionType";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(updateListener);
    }
}
