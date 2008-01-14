package net.esper.regression.client;

import net.esper.client.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.util.ArrayAssertionUtil;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.util.SupportStmtAwareUpdateListener;
import net.esper.event.EventBean;
import junit.framework.TestCase;

import java.util.List;
import java.util.ArrayList;

/**
 * Dispatching
===========

Input:
  - select clause expressions
  - annotation values
  - parameter types of the method in order

Output:
  - Select expression processing to Object or Object[]
  - Dispatch strategy (array, i/rstream, one or more methods)


Use Cases
=========
- Interface and abstract classes honored
- istream and rstream can point to a method name to deliver events to
- POJO, Map and DOM are underlying events and map to "*", "s0.*" or "s0"

Wildcard-only and single-stream select: Delivers underlying event
-----------------------------------------------------------------
To get the underlying event of the insert stream (POJO):
  @epsubscribe(epl="select * from OrderEvent.win:time(30 sec)")
  public void indicate(OrderEvent orderEvent)
  or
  public void indicate(OrderEvent[] orderEvent)

To get the underlying event of the insert stream (Map):
  @epsubscribe(epl="select * from OrderEvent.win:time(30 sec)")
  public void indicate(Map event)
  or
  public void indicate(Map[] event)

To get the underlying event of the insert stream (DOM):
  @epsubscribe(epl="select * from OrderEvent.win:time(30 sec)")
  public void indicate(org.w3c.dom.Node node)
  or
  public void indicate(org.w3c.dom.Node[] node)

To get the underlying event of the remove stream:
  @epsubscribe(epl="select rstream * from OrderEvent.win:time(30 sec)")
  public void indicate(OrderEvent orderEvent)
  or
  public void indicate(OrderEvent[] orderEvent)

To get the underlying event of the insert and remove stream:
  @epsubscribe(epl="select * from OrderEvent.win:time(30 sec)" rstream="indicateRStream")
  public void indicateIStream(OrderEvent orderEvent)
  public void indicateRStream(OrderEvent orderEvent)
  or
  public void indicateIStream(OrderEvent[] orderEvent)
  public void indicateRStream(OrderEvent[] orderEvent)

Generalized delivery:
  @epsubscribe(epl="select * from OrderEvent.win:time(30 sec)")
  public void indicate(Object event)
  or
  public void indicate(Object[] events)
  or
  public void indicate(Object... events)


Wildcard-only and join: Delivers underlying events
-----------------------------------------------------------------

To get the underlying events of the insert stream (POJO, order must match order in from-clause):
  @epsubscribe(epl="select * from OrderEvent.win:time(30 sec), PriceEvent.win:time(30 sec)")
  public void indicate(OrderEvent orderEvent, PriceEvent priceEvent)


List of columns plus or without wildcard: deliver expression values matching columns selected, single-column special case for joins
-----------------------------------------------------------------

 */
public class TestAnnotatedSubscriber extends TestCase
{
    // TODO: test iterator
    // TODO: test listener + method delivery
    // TODO: test join
    // TODO: test select istream/rstream selector
    // TODO: test static method

    // TODO: support named parameter names, support rstream delivery through alt method
    // TODO: test wildcard, stream wildcard, wildcard with extra columns
    // TODO: test multirow delivery
    // TODO: test array delivery
    // TODO: test bean array delivery
    // TODO: test outside statement start/stop + statement name
    // TODO: test named windows
    // TODO: test performance
    // TODO: test non-selecting views: on-delete, create window/variable
    // TODO: test on-select view
    // TODO: test widening, test inheritance and polymorphism
    // TODO: test null value delivery
    // TODO: test ordered delivery and MT thread safety
    // TODO: test take and remove
    // TODO: test invalid
    // TODO: test with and without statement name
    // TODO: Support parameterized annotations, i.e. a->b(var=$var1)
    // TODO: Handle persistence (EHA)
    // TODO: Support for detecting a pattern or a epl
    // TODO: test write same object twice
    // TODO: test provide method to start and stop expressions (or not)
    // TODO: subscriber read API
    // TODO: should there be a begin-delivery(length) and end-delivery method
    // TODO: annotation on the class level pointing to the method name to deliver to

    private EPServiceProvider epService;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoAlias(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testPerformanceNatural()
    {
        final int NUM_LOOP = 10;
        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();
        epService.getEPRuntime().write(subscriber);

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();

        List<Object[]> results = subscriber.getAndResetIndicateSimple();
        assertEquals(NUM_LOOP, results.size());
        for (int i = 0; i < NUM_LOOP; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(results.get(i), new Object[] {"E1", 1000 + i});
        }
        System.out.println("delta=" + (end - start));
    }

    public void testPerformanceSynthetic()
    {
        final int NUM_LOOP = 10;
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean(intPrimitive > 10)");
        final List<Object[]> results = new ArrayList<Object[]>();

        UpdateListener listener = new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                String string = (String) newEvents[0].get("string");
                int val = (Integer) newEvents[0].get("intPrimitive");
                results.add(new Object[] {string, val});
            }
        };
        stmt.addListener(listener);

        long start = System.currentTimeMillis();
        for (int i = 0; i < NUM_LOOP; i++)
        {
            epService.getEPRuntime().sendEvent(new SupportBean("E1", 1000 + i));
        }
        long end = System.currentTimeMillis();

        assertEquals(NUM_LOOP, results.size());
        for (int i = 0; i < NUM_LOOP; i++)
        {
            ArrayAssertionUtil.assertEqualsAnyOrder(results.get(i), new Object[] {"E1", 1000 + i});
        }
        System.out.println("delta=" + (end - start));
    }

    public void testSimpleSelect()
    {
        String fields[] = "string,intPrimitive".split(",");

        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();
        epService.getEPRuntime().write(subscriber);

        // get statement, attach listener
        EPStatement stmt = epService.getEPAdministrator().getStatement("SimpleSelectStmt");
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 100}});
        ArrayAssertionUtil.assertProps(listener.assertOneGetNewAndReset(), fields, new Object[] {"E1", 100});

        // remove listener
        stmt.removeAllListeners();

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 200));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), new Object[][] {{"E2", 200}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E2", 200}});
        assertFalse(listener.isInvoked());

        // add listener
        SupportStmtAwareUpdateListener stmtAwareListener = new SupportStmtAwareUpdateListener();
        stmt.addListener(stmtAwareListener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E3", 300));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), new Object[][] {{"E3", 300}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 300}});
        ArrayAssertionUtil.assertProps(stmtAwareListener.assertOneGetNewAndReset(), fields, new Object[] {"E3", 300});

        // send no-pass event
        epService.getEPRuntime().sendEvent(new SupportBean("E4", -1));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateSimple(), null);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E3", 300}});
        assertFalse(stmtAwareListener.isInvoked());

        // remove
        epService.getEPRuntime().take(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1000));
        assertEquals(0, subscriber.getAndResetIndicateSimple().size());
    }

    public void testInsertInsertRemoveStream()
    {
        String fields[] = "symbol,volume".split(",");

        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();
        epService.getEPRuntime().write(subscriber);

        // get statement, attach listener
        EPStatement stmtIIRR = epService.getEPAdministrator().getStatement("IIRR");
        EPStatement stmtIIIR = epService.getEPAdministrator().getStatement("IIIR");
        EPStatement stmtIIRI = epService.getEPAdministrator().getStatement("IIRI");

        // TODO: error is failed to load class if an event stream was not found

        // attach listeners to insert-into streams
        SupportUpdateListener listenerIIRR = new SupportUpdateListener();
        epService.getEPAdministrator().createEQL("select * from IIRRStream").addListener(listenerIIRR);
        SupportUpdateListener listenerIIRI = new SupportUpdateListener();
        epService.getEPAdministrator().createEQL("select * from IIRIStream").addListener(listenerIIRI);
        SupportUpdateListener listenerIIIR = new SupportUpdateListener();
        epService.getEPAdministrator().createEQL("select * from IIIRStream").addListener(listenerIIIR);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0d, 100L, ""));

        assertEquals(0, subscriber.getAndResetIIRR().size());
        assertEquals(0, subscriber.getAndResetIIRI().size());
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIIIR(), new Object[][] {{"E1", 100L}});

        ArrayAssertionUtil.assertEqualsExactOrder(stmtIIRR.iterator(), fields, new Object[][] {{"E1",100L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtIIRI.iterator(), fields, new Object[][] {{"E1",100L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtIIIR.iterator(), fields, new Object[][] {{"E1",100L}});

        assertFalse(listenerIIRR.isInvoked());
        assertFalse(listenerIIIR.isInvoked());
        ArrayAssertionUtil.assertProps(listenerIIRI.assertOneGetNewAndReset(), fields, new Object[] {"E1",100L});

        // send event, generates an insert and remove stream event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0d, 200L, ""));

        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIIRI(), new Object[][] {{"E1", 100L}});
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIIRR(), new Object[][] {{"E1", 100L}});
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIIIR(), new Object[][] {{"E1", 200L}});

        ArrayAssertionUtil.assertEqualsExactOrder(stmtIIRR.iterator(), fields, new Object[][] {{"E1",200L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtIIRI.iterator(), fields, new Object[][] {{"E1",200L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmtIIIR.iterator(), fields, new Object[][] {{"E1",200L}});

        ArrayAssertionUtil.assertProps(listenerIIIR.assertOneGetNewAndReset(), fields, new Object[] {"E1",100L});
        ArrayAssertionUtil.assertProps(listenerIIRR.assertOneGetNewAndReset(), fields, new Object[] {"E1",100L});
        ArrayAssertionUtil.assertProps(listenerIIRI.assertOneGetNewAndReset(), fields, new Object[] {"E1",200L});

        // remove, stops statement
        epService.getEPRuntime().take(subscriber);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0d, 300L, ""));
        assertEquals(0, subscriber.getAndResetIIRI().size());
        assertEquals(0, subscriber.getAndResetIIRR().size());
        assertEquals(0, subscriber.getAndResetIIIR().size());
        assertFalse(listenerIIIR.isInvoked());
        assertFalse(listenerIIRI.isInvoked());
        assertFalse(listenerIIRR.isInvoked());
    }
    
    public void testUniqueSelectInsertInto()
    {
        String fields[] = "symbol,volume".split(",");

        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();
        epService.getEPRuntime().write(subscriber);

        // get statement, attach listener
        EPStatement stmt = epService.getEPAdministrator().getStatement("InsertIntoStmt");

        // create insert-into catch
        SupportUpdateListener listenerMyStream = new SupportUpdateListener();
        epService.getEPAdministrator().createEQL("select * from MyStream").addListener(listenerMyStream);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0d, 100L, ""));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateInsertInto(), new Object[][] {{"E1", 200L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 200L}});
        ArrayAssertionUtil.assertProps(listenerMyStream.assertOneGetNewAndReset(), fields, new Object[] {"E1", 200L});

        // add listener
        SupportUpdateListener listener = new SupportUpdateListener();
        stmt.addListener(listener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0d, 200L, ""));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateInsertInto(), new Object[][] {{"E1", 400L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 400L}});
        ArrayAssertionUtil.assertProps(listener.getLastNewData()[0], fields, new Object[] {"E1", 400L});
        ArrayAssertionUtil.assertProps(listener.getLastOldData()[0], fields, new Object[] {"E1", 200L});
        ArrayAssertionUtil.assertProps(listenerMyStream.assertOneGetNewAndReset(), fields, new Object[] {"E1", 400L});
        listener.reset();

        // remove listener
        stmt.removeListener(listener);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E2", 0d, 300L, ""));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateInsertInto(), new Object[][] {{"E2", 600L}});
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"E1", 400L}, {"E2", 600L}});
        ArrayAssertionUtil.assertProps(listenerMyStream.assertOneGetNewAndReset(), fields, new Object[] {"E2", 600L});
        assertFalse(listener.isInvoked());

        // remove, stops statement
        epService.getEPRuntime().take(subscriber);

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E2", 0d, 300L, ""));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateInsertInto(), null);
        assertFalse(listener.isInvoked());
    }
}
