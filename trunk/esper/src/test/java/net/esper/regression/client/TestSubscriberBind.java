package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.collection.UniformPair;
import net.esper.support.bean.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.Map;

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

Defaults: row-by-row delivery
-----------------------------------------------------------------
  public void start(int newDataLength, int oldDataLength)
  public void update(String s, int i, A s0, B s1)
  public void updateRStream(String s, int i, A s0, B s1)
  public void end()
  or
  public void update(Object[])
  or
  public void update(Map row)

Defaults: multirow delivery
-----------------------------------------------------------------
  public void update(Object[][] newData, Object[][] oldData)
  or
  public void update(Map[] newData, Map[] oldData)

Defaults: multirow delivery for single-column selects
-----------------------------------------------------------------
  public void update(ColumnType[] newData, ColumnType[] oldData)
 */
public class TestSubscriberBind extends TestCase
{
    private EPServiceProvider epService;
    private final String fields[] = "string,intPrimitive".split(",");

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoAlias(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testOutputLimitNoJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean output every 2 events");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, subscriber.getAndResetIndicate().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testOutputLimitJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean, SupportMarketDataBean where symbol = string output every 2 events");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0, 1L, ""));
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, subscriber.getAndResetIndicate().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1", 1}, {"E1", 2}});
    }

    public void testSimpleSelectStatic()
    {
        MySubscriberRowByRowSpecificStatic subscriber = new MySubscriberRowByRowSpecificStatic();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from " + SupportBean.class.getName());
        stmt.setSubscriber(subscriber);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1", 100}});
    }

    public void testRStreamSelect()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select rstream s0 from SupportBean.std:unique(string) as s0");
        stmt.setSubscriber(subscriber);

        // send event
        SupportBean s0 = new SupportBean("E1", 100);
        epService.getEPRuntime().sendEvent(s0);
        assertEquals(0, subscriber.getAndResetIndicate().size());

        SupportBean s1 = new SupportBean("E2", 200);
        epService.getEPRuntime().sendEvent(s1);
        assertEquals(0, subscriber.getAndResetIndicate().size());

        SupportBean s2 = new SupportBean("E1", 300);
        epService.getEPRuntime().sendEvent(s2);
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{s0}});
    }

    public void testStreamSelectJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select null, s1, s0 from SupportBean as s0, SupportMarketDataBean as s1 where s0.string = s1.symbol");
        stmt.setSubscriber(subscriber);

        // send event
        SupportBean s0 = new SupportBean("E1", 100);
        SupportMarketDataBean s1 = new SupportMarketDataBean("E1", 0, 0L, "");
        epService.getEPRuntime().sendEvent(s0);
        epService.getEPRuntime().sendEvent(s1);
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{null, s1, s0}});
    }

    public void testStreamWildcardJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string || '<', s1.* as s1, s0.* as s0 from SupportBean as s0, SupportMarketDataBean as s1 where s0.string = s1.symbol");
        stmt.setSubscriber(subscriber);

        // send event
        SupportBean s0 = new SupportBean("E1", 100);
        SupportMarketDataBean s1 = new SupportMarketDataBean("E1", 0, 0L, "");
        epService.getEPRuntime().sendEvent(s0);
        epService.getEPRuntime().sendEvent(s1);
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1<", s1, s0}});
    }

    public void testBindWildcardJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from SupportBean as s0, SupportMarketDataBean as s1 where s0.string = s1.symbol");
        stmt.setSubscriber(subscriber);

        // send event
        SupportBean s0 = new SupportBean("E1", 100);
        SupportMarketDataBean s1 = new SupportMarketDataBean("E1", 0, 0L, "");
        epService.getEPRuntime().sendEvent(s0);
        epService.getEPRuntime().sendEvent(s1);
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{s0, s1}});
    }

    public void testBindWildcardPlusProperties()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select *, intPrimitive + 2, 'x'||string||'x' from " + SupportBean.class.getName());
        stmt.setSubscriber(subscriber);

        SupportBean s0 = new SupportBean("E1", 100);
        epService.getEPRuntime().sendEvent(s0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {s0, 102, "xE1x"});
    }

    public void testBindWildcardIRStream()
    {
        MySubscriberMultirowUnderlying subscriber = new MySubscriberMultirowUnderlying();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from SupportBean.win:length_batch(2)");
        stmt.setSubscriber(subscriber);

        SupportBean s0 = new SupportBean("E1", 100);
        SupportBean s1 = new SupportBean("E2", 200);
        epService.getEPRuntime().sendEvent(s0);
        epService.getEPRuntime().sendEvent(s1);
        assertEquals(1, subscriber.getIndicateArr().size());
        UniformPair<SupportBean[]> beans = subscriber.getAndResetIndicateArr().get(0);
        assertEquals(2, beans.getFirst().length);
        assertEquals(null, beans.getSecond());
        ArrayAssertionUtil.assertEqualsExactOrder(beans.getFirst(), new Object[] {s0, s1});

        SupportBean s2 = new SupportBean("E3", 300);
        SupportBean s3 = new SupportBean("E4", 400);
        epService.getEPRuntime().sendEvent(s2);
        epService.getEPRuntime().sendEvent(s3);
        assertEquals(1, subscriber.getIndicateArr().size());
        beans = subscriber.getAndResetIndicateArr().get(0);
        assertEquals(2, beans.getFirst().length);
        assertEquals(2, beans.getSecond().length);
        ArrayAssertionUtil.assertEqualsExactOrder(beans.getFirst(), new Object[] {s2, s3});
        ArrayAssertionUtil.assertEqualsExactOrder(beans.getSecond(), new Object[] {s0, s1});
    }

    public void testBindUpdateIRStream()
    {
        MySubscriberRowByRowFull subscriber = new MySubscriberRowByRowFull();
        String stmtText = "select string, intPrimitive from " + SupportBean.class.getName() + ".win:length_batch(2)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, subscriber.getIndicateStart().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        assertEquals(1, subscriber.getIndicateStart().size());
        UniformPair<Integer> pairLength = subscriber.getAndResetIndicateStart().get(0);
        assertEquals(2, (int) pairLength.getFirst());
        assertEquals(0, (int) pairLength.getSecond());
        assertEquals(1, subscriber.getAndResetIndicateEnd().size());
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateIStream(), new Object[][] {{"E1", 1}, {"E2", 2}});
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateRStream(), null);

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        assertEquals(0, subscriber.getIndicateStart().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));
        assertEquals(1, subscriber.getIndicateStart().size());
        pairLength = subscriber.getAndResetIndicateStart().get(0);
        assertEquals(2, (int) pairLength.getFirst());
        assertEquals(2, (int) pairLength.getSecond());
        assertEquals(1, subscriber.getAndResetIndicateEnd().size());
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateIStream(), new Object[][] {{"E3", 3}, {"E4", 4}});
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicateRStream(), new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testBindObjectArr()
    {
        MySubscriberMultirowObjectArr subscriber = new MySubscriberMultirowObjectArr();
        String stmtText = "select string, intPrimitive from " + SupportBean.class.getName() + ".win:length_batch(2)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, subscriber.getIndicateArr().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        assertEquals(1, subscriber.getIndicateArr().size());
        UniformPair<Object[][]> result = subscriber.getAndResetIndicateArr().get(0);
        assertNull(result.getSecond());
        assertEquals(2, result.getFirst().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getFirst(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        assertEquals(0, subscriber.getIndicateArr().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));
        assertEquals(1, subscriber.getIndicateArr().size());
        result = subscriber.getAndResetIndicateArr().get(0);
        assertEquals(2, result.getFirst().length);
        assertEquals(2, result.getSecond().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getFirst(), fields, new Object[][] {{"E3", 3}, {"E4", 4}});
        ArrayAssertionUtil.assertPropsPerRow(result.getSecond(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testBindMap()
    {
        MySubscriberMultirowMap subscriber = new MySubscriberMultirowMap();
        String stmtText = "select string, intPrimitive from " + SupportBean.class.getName() + ".win:length_batch(2)";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, subscriber.getIndicateMap().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        assertEquals(1, subscriber.getIndicateMap().size());
        UniformPair<Map[]> result = subscriber.getAndResetIndicateMap().get(0);
        assertNull(result.getSecond());
        assertEquals(2, result.getFirst().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getFirst(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 3));
        assertEquals(0, subscriber.getIndicateMap().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 4));
        assertEquals(1, subscriber.getIndicateMap().size());
        result = subscriber.getAndResetIndicateMap().get(0);
        assertEquals(2, result.getFirst().length);
        assertEquals(2, result.getSecond().length);
        ArrayAssertionUtil.assertPropsPerRow(result.getFirst(), fields, new Object[][] {{"E3", 3}, {"E4", 4}});
        ArrayAssertionUtil.assertPropsPerRow(result.getSecond(), fields, new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testWidening()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select bytePrimitive, intPrimitive, longPrimitive, floatPrimitive from SupportBean(string='E1')");
        stmt.setSubscriber(subscriber);

        SupportBean bean = new SupportBean();
        bean.setString("E1");
        bean.setBytePrimitive((byte)1);
        bean.setIntPrimitive(2);
        bean.setLongPrimitive(3);
        bean.setFloatPrimitive(4);
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {1, 2L, 3d, 4d});
    }

    public void testWildcard()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select * from SupportBean(string='E2')");
        stmt.setSubscriber(subscriber);

        SupportBean event = new SupportBean("E2", 1);
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {event});
    }

    public void testNested()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select nested, nested.nestedNested from SupportBeanComplexProps");
        stmt.setSubscriber(subscriber);

        SupportBeanComplexProps event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {event.getNested(), event.getNested().getNestedNested()});
    }

    public void testEnum()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, supportEnum from SupportBeanWithEnum");
        stmt.setSubscriber(subscriber);

        SupportBeanWithEnum event = new SupportBeanWithEnum("abc", SupportEnum.ENUM_VALUE_1);
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {event.getString(), event.getSupportEnum()});
    }

    public void testNullType()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select null, longBoxed from SupportBean");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {null, null});
    }

    public void testObjectArrayDelivery()
    {
        MySubscriberRowByRowObjectArr subscriber = new MySubscriberRowByRowObjectArr();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean.std:unique(string)");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(subscriber.getAndResetIndicate().get(0), new Object[] {"E1", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertEqualsAnyOrder(subscriber.getAndResetIndicate().get(0), new Object[] {"E2", 10});
    }

    public void testRowMapDelivery()
    {
        MySubscriberRowByRowMap subscriber = new MySubscriberRowByRowMap();
        EPStatement stmt = epService.getEPAdministrator().createEQL("select string, intPrimitive from SupportBean.std:unique(string)");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(subscriber.getAndResetIndicateIStream().get(0), fields, new Object[] {"E1", 1});
        assertEquals(0, subscriber.getAndResetIndicateRStream().size());

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertProps(subscriber.getAndResetIndicateIStream().get(0), fields, new Object[] {"E2", 10});
        assertEquals(0, subscriber.getAndResetIndicateRStream().size());

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 2));
        ArrayAssertionUtil.assertProps(subscriber.getAndResetIndicateIStream().get(0), fields, new Object[] {"E1", 2});
        ArrayAssertionUtil.assertProps(subscriber.getAndResetIndicateRStream().get(0), fields, new Object[] {"E1", 1});
    }
}
