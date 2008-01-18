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


Doc
=========
Annotations are metadata added to classes and methods in the form of an <emphasis>@name</emphasis> syntax. Annotations have
first become available with the release of Java 5, please visit http://java.sun.com/j2se/1.5.0/docs/guide/language/annotations.html
for more information on annotations.
 
In Esper, a single annotation provides direct binding of query results to Java object instances.
The result is more readable code and less code in general. There are also substantial performance benefits to
using annotations: Query results are delivered directly to your method(s) through Java virtual machine method calls,
and there is no intermediate representation (<literal>EventBean</literal>).

Esper provide a single, easy-to-use annotation by name of <emphasis>@EPLSubscription<emphasis>. This annotation provides
an EPL statement, and binds result delivery for that statement to one or more methods.

The syntax for the <emphasis>@EPLSubscription</emphasis> annotation is as follows:
  @EPLSubscription
    epl = "<emphasis>epl_statement</emphasis>"
    [ name = "<emphasis>statement_name</emphasis>" ]
    [ istream = "<emphasis>insert_stream_method</emphasis>" ]
    [ rstream = "<emphasis>remove_stream_method</emphasis>" ]
    [ begin = "<emphasis>begin_method</emphasis>" ]
    [ end = "<emphasis>end_method</emphasis>" ]

The annotation element <literal>epl</literal> contains an EPL statement. The engine provides EPL statement results to
the method(s) indicated.

The optional <literal>name</literal> element is the statement name by which the EPL statement is known. If supplied, the statement
can be looked up and managed by the given name via the administration API. If the statement name is not supplied, the engine generates
a statement name. If the statement name is already in use, the engine appends a numeric counter to the statement name.

The optional element <literal>istream</literal> provides the method that receives insert stream results, and the element
<literal>rstream</literal> provides the method that receives remove stream results.

The optional elements <literal>begin</literal> and <literal>end</literal> are useful for EPL statements that
generate multiple result rows, such as through batching or output rate limiting. The <literal>begin</literal>
element is the method the engine calls before indicating the first output row.
The <literal>end</literal> element is the method the engine calls after indicating the last output row.

In order for the engine to provide query results to your Java object methods, method parameters must meet these guidelines:

- The number of method parameters must match the number of columns in the <literal>select</literal>-clause (special rules for wildcard apply, see below)
- Each method parameter type must be compatible with the respective column type in the <literal>select</literal>-clause
  > Widening of types is allowed following Java standards
  > Interface and superclasses are honored, therefore <literal>java.lang.Object</literal> can be used to accept any select column type

When using annotations, your application can use the statement management API of <literal>EPAdministrator</literal>
and <literal>EPStatement</literal> to start and stop statements. Your application can also subscribe via <literal>UpdateListener</literal> instance or use the <literal>iterator</literal> or <literal>safeIterator</literal> methods to obtain current results.

Example.....

The Esper Registry holds instances of annotated classes and managed their associated statements.
The method to interact with the registry are found in <literal>EPRuntime</literal>.

The registry is best seen as a unordered collection of active objects, keyed on object identity.
When an object is written to the registry via the <literal>write</literal> method, the engine starts the annotated EPL statements.
When an object is taken out of the registry via the <literal>take</literal> method, the engine stops all EPL statements associated to that instance.
The registry is keyed on identity: the take operation must provide the same object instance as the write operation.
The <literal>iterator<literal> allows safe iteration over the registry.

Example:

- Interface and abstract classes honored
- istream and rstream can point to a method name to deliver events to
- POJO, Map and DOM are underlying events and map to "*", "s0.*" or "s0"

Wildcard-only and single-stream select: Delivers underlying event
-----------------------------------------------------------------
To get the underlying event of the insert stream (POJO, Map and DOM):
  @eplsubscribe(epl="select * from OrderEvent.win:time(30 sec)")
  public void indicate(OrderEvent orderEvent)
  or
  public void indicate(OrderEvent[] orderEvent)
  or
  public void indicate(OrderEvent[] orderEventNew, OrderEvent[] orderEventOld)

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

Row-by-row delivery for a list of columns: deliver expression values matching columns selected
-----------------------------------------------------------------
  @epsubscribe(epl="select string, int, s0, s1.*, * from A s0, B s1"
  public void indicate(String s, int i, A s0, B s1)
  public void start(int length)
  public void end()
 
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

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 1000));
        assertEquals(0, subscriber.getAndResetIndicateSimple().size());
    }

    public void testInsertInsertRemoveStream()
    {
        String fields[] = "symbol,volume".split(",");

        MyAnnotatedSimpleSubscriber subscriber = new MyAnnotatedSimpleSubscriber();

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

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E2", 0d, 300L, ""));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateInsertInto(), null);
        assertFalse(listener.isInvoked());
    }

    public void testCreateWindow()
    {
        String fields[] = "string,intPrimitive".split(",");

        MyAnnotatedNonSelectSubscriber subscriber = new MyAnnotatedNonSelectSubscriber();

        epService.getEPAdministrator().createEQL("insert into MyWindow select string, intPrimitive from SupportBean");

        // send event
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0d, 100L, ""));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getResetIStream(), new Object[][] {{"E1", 200L}});
    }
}
