package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.SupportStmtAwareUpdateListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestSubscriberMgmt extends TestCase
{
    private EPServiceProvider epService;
    private final String fields[] = "string,intPrimitive".split(",");

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoName(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testStartStopStatement()
    {
        SubscriberInterface subscriber = new SubscriberInterface();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportMarkerInterface");
        stmt.setSubscriber(subscriber);

        SupportBean_A a1 = new SupportBean_A("A1");
        epService.getEPRuntime().sendEvent(a1);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().toArray(), new Object[] {a1});

        SupportBean_B b1 = new SupportBean_B("B1");
        epService.getEPRuntime().sendEvent(b1);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().toArray(), new Object[] {b1});
        
        stmt.stop();

        SupportBean_C c1 = new SupportBean_C("C1");
        epService.getEPRuntime().sendEvent(c1);
        assertEquals(0, subscriber.getAndResetIndicate().size());

        stmt.start();

        SupportBean_D d1 = new SupportBean_D("D1");
        epService.getEPRuntime().sendEvent(d1);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().toArray(), new Object[] {d1});
    }

    public void testVariables()
    {
        String fields[] = "myvar".split(",");
        SubscriberMap subscriberCreateVariable = new SubscriberMap();
        String stmtTextCreate = "create variable string myvar = 'abc'";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmt.setSubscriber(subscriberCreateVariable);

        SubscriberMap subscriberSetVariable = new SubscriberMap();
        String stmtTextSet = "on SupportBean set myvar = string";
        stmt = epService.getEPAdministrator().createEPL(stmtTextSet);
        stmt.setSubscriber(subscriberSetVariable);

        epService.getEPRuntime().sendEvent(new SupportBean("def", 1));
        ArrayAssertionUtil.assertPropsMap(subscriberCreateVariable.getAndResetIndicate().get(0), fields, new Object[]{"def"});
        ArrayAssertionUtil.assertPropsMap(subscriberSetVariable.getAndResetIndicate().get(0), fields, new Object[]{"def"});
    }

    public void testNamedWindow()
    {
        String fields[] = "key,value".split(",");
        SubscriberMap subscriberNamedWindow = new SubscriberMap();
        String stmtTextCreate = "create window MyWindow.win:keepall() as select string as key, intPrimitive as value from SupportBean";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtTextCreate);
        stmt.setSubscriber(subscriberNamedWindow);

        SubscriberFields subscriberInsertInto = new SubscriberFields();
        String stmtTextInsertInto = "insert into MyWindow select string as key, intPrimitive as value from SupportBean";
        stmt = epService.getEPAdministrator().createEPL(stmtTextInsertInto);
        stmt.setSubscriber(subscriberInsertInto);
        
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertPropsMap(subscriberNamedWindow.getAndResetIndicate().get(0), fields, new Object[]{"E1", 1});
        ArrayAssertionUtil.assertPropsPerRow(subscriberInsertInto.getAndResetIndicate(), new Object[][] {{"E1", 1}});

        // test on-delete
        SubscriberMap subscriberDelete = new SubscriberMap();
        String stmtTextDelete = "on SupportMarketDataBean s0 delete from MyWindow s1 where s0.symbol = s1.key";
        stmt = epService.getEPAdministrator().createEPL(stmtTextDelete);
        stmt.setSubscriber(subscriberDelete);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0, 1L, ""));
        ArrayAssertionUtil.assertPropsMap(subscriberDelete.getAndResetIndicate().get(0), fields, new Object[]{"E1", 1});

        // test on-select
        SubscriberMap subscriberSelect = new SubscriberMap();
        String stmtTextSelect = "on SupportMarketDataBean s0 select key, value from MyWindow s1";
        stmt = epService.getEPAdministrator().createEPL(stmtTextSelect);
        stmt.setSubscriber(subscriberSelect);

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("M1", 0, 1L, ""));
        ArrayAssertionUtil.assertPropsMap(subscriberSelect.getAndResetIndicate().get(0), fields, new Object[]{"E2", 2});
    }

    public void testSimpleSelectUpdateOnly()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, intPrimitive from " + SupportBean.class.getName());
        stmt.setSubscriber(subscriber);

        // get statement, attach listener
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 100});

        // remove listener
        stmt.removeAllListeners();

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 200));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E2", 200}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 200}});
        assertFalse(listener.isInvoked());

        // add listener
        SupportStmtAwareUpdateListener stmtAwareListener = new SupportStmtAwareUpdateListener();
        stmt.addListener(stmtAwareListener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 300));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E3", 300}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 300}});
        ArrayAssertionUtil.assertProps(stmtAwareListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 300});
    }

    public class SubscriberFields
    {
        private ArrayList<Object[]> indicate = new ArrayList<Object[]>();

        public void update(String key, int value)
        {
            indicate.add(new Object[] {key, value});
        }

        public List<Object[]> getAndResetIndicate()
        {
            List<Object[]> result = indicate;
            indicate = new ArrayList<Object[]>();
            return result;
        }
    }

    public class SubscriberInterface
    {
        private ArrayList<SupportMarkerInterface> indicate = new ArrayList<SupportMarkerInterface>();

        public void update(SupportMarkerInterface impl)
        {
            indicate.add(impl);
        }

        public List<SupportMarkerInterface> getAndResetIndicate()
        {
            List<SupportMarkerInterface> result = indicate;
            indicate = new ArrayList<SupportMarkerInterface>();
            return result;
        }
    }

    public class SubscriberMap
    {
        private ArrayList<Map> indicate = new ArrayList<Map>();

        public void update(Map row)
        {
            indicate.add(row);
        }

        public List<Map> getAndResetIndicate()
        {
            List<Map> result = indicate;
            indicate = new ArrayList<Map>();
            return result;
        }
    }
}
