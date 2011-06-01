package com.espertech.esper.regression.client;

import com.espertech.esper.client.*;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowKeyRange;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookupFieldDesc;
import com.espertech.esper.core.EPServiceProviderSPI;
import com.espertech.esper.epl.lookup.SubordTableLookupStrategy;
import com.espertech.esper.epl.named.NamedWindowProcessor;
import com.espertech.esper.epl.virtualdw.VirtualDataWindowLookupContextSPI;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.virtualdw.SupportVirtualDW;
import com.espertech.esper.support.virtualdw.SupportVirtualDWFactory;
import com.espertech.esper.support.virtualdw.SupportVirtualDWInvalidFactory;
import junit.framework.TestCase;

import javax.naming.NamingException;
import java.util.*;

public class TestVirtualDataWindowToLookup extends TestCase {

    private EPServiceProvider epService;
    private EPServiceProviderSPI spi;
    private SupportUpdateListener listener;

    public void setUp()
    {
        listener = new SupportUpdateListener();

        Configuration configuration = SupportConfigFactory.getConfiguration();
        configuration.addPlugInVirtualDataWindow("test", "vdw", SupportVirtualDWFactory.class.getName());
        configuration.addEventType("SupportBean", SupportBean.class);
        configuration.addEventType("SupportBean_S0", SupportBean_S0.class);
        epService = EPServiceProviderManager.getDefaultProvider(configuration);
        epService.initialize();
        spi = (EPServiceProviderSPI) epService;
    }

    public void testLateConsumerNoIterate() throws Exception {

        // client-side
        epService.getEPAdministrator().createEPL("create window MyVDW.test:vdw() as SupportBean");
        SupportVirtualDW window = (SupportVirtualDW) getFromContext("/virtualdw/MyVDW");
        SupportBean supportBean = new SupportBean("E1", 100);
        window.setData(Collections.singleton(supportBean));

        EPStatement stmt = epService.getEPAdministrator().createEPL("select (select sum(intPrimitive) from MyVDW vdw where vdw.string = s0.p00) from SupportBean_S0 s0");
        stmt.addListener(listener);
        VirtualDataWindowLookupContextSPI spiContext = (VirtualDataWindowLookupContextSPI) window.getLastRequestedIndex();

        // CM side
        epService.getEPAdministrator().createEPL("create window MyWin.std:unique(string) as SupportBean");
        epService.getEPAdministrator().createEPL("insert into MyWin select * from SupportBean");
        NamedWindowProcessor processor = spi.getNamedWindowService().getProcessor("MyWin");
        SubordTableLookupStrategy strategy = processor.getRootView().getAddSubqueryLookupStrategy("ABC", spiContext.getOuterTypePerStream(), spiContext.getJoinDesc(), spiContext.isForceTableScan());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 200));

        // trigger
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "E2"));
        EventBean[] outerEvents = window.getLastAccessEvents();
        Collection<EventBean> result = strategy.lookup(outerEvents, null);
        assertTrue(!result.isEmpty());
    }

    private VirtualDataWindow getFromContext(String name) {
        try {
            return (VirtualDataWindow) epService.getContext().lookup(name);
        }
        catch (NamingException e) {
            throw new RuntimeException("Name '" + name + "' could not be looked up");
        }
    }
}
