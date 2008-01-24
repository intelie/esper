package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.support.bean.SupportBean;
import net.esper.support.client.SupportConfigFactory;

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
public class TestAnnotatedSubscriber extends TestCase
{
    // TODO: test select istream/rstream selector
    // TODO: test static method
    // TODO: test wildcard, stream wildcard
    // TODO: test multirow delivery of underlying type
    // TODO: test output rate limiting+join
    
    // TODO: test statement start/stop
    // TODO: test on-delete, create window, create variable, on-set, on-select
    // TODO: test performance
    // TODO: test MT thread safety of set listener/subscriber
    // TODO: test invalid
    // TODO: Handle persistence (EHA)
    // TODO: subscriber read API

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

    /*
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

        List<Object[]> results = subscriber.getAndResetIndicate();
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
    */
}
