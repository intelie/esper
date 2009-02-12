package com.espertech.esper.regression.client;

import junit.framework.TestCase;
import com.espertech.esper.client.*;
import com.espertech.esper.collection.UniformPair;
import com.espertech.esper.support.bean.*;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;

import java.util.Map;

public class TestSubscriberBind extends TestCase
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

    public void testSubscriberandListener()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        epService.getEPAdministrator().createEPL("insert into A1 select s.*, 1 as a from SupportBean as s");
        EPStatement stmt = epService.getEPAdministrator().createEPL("select a1.* from A1 as a1");

        SupportUpdateListener listener = new SupportUpdateListener();
        MySubscriberRowByRowObjectArr subscriber = new MySubscriberRowByRowObjectArr();

        stmt.addListener(listener);
        stmt.setSubscriber(subscriber);
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));

        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals("E1", event.get("string"));
        assertEquals(1, event.get("intPrimitive"));

        for (String property : stmt.getEventType().getPropertyNames())
        {
            EventPropertyGetter getter = stmt.getEventType().getGetter(property);
            getter.get(event);
        }
    }

    public void testOutputLimitNoJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, intPrimitive from SupportBean output every 2 events");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        assertEquals(0, subscriber.getAndResetIndicate().size());
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1", 1}, {"E2", 2}});
    }

    public void testOutputLimitJoin()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, intPrimitive from SupportBean.win:keepall(), SupportMarketDataBean.win:keepall() where symbol = string output every 2 events");
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, intPrimitive from " + SupportBean.class.getName());
        stmt.setSubscriber(subscriber);

        // send event
        epService.getEPRuntime().sendEvent(new SupportBean("E1", 100));
        ArrayAssertionUtil.assertPropsPerRow(subscriber.getAndResetIndicate(), new Object[][] {{"E1", 100}});
    }

    public void testRStreamSelect()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select rstream s0 from SupportBean.std:unique(string) as s0");
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select null, s1, s0 from SupportBean.win:keepall() as s0, SupportMarketDataBean.win:keepall() as s1 where s0.string = s1.symbol");
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string || '<', s1.* as s1, s0.* as s0 from SupportBean.win:keepall() as s0, SupportMarketDataBean.win:keepall() as s1 where s0.string = s1.symbol");
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean.win:keepall() as s0, SupportMarketDataBean.win:keepall() as s1 where s0.string = s1.symbol");
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select *, intPrimitive + 2, 'x'||string||'x' from " + SupportBean.class.getName());
        stmt.setSubscriber(subscriber);

        SupportBean s0 = new SupportBean("E1", 100);
        epService.getEPRuntime().sendEvent(s0);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {s0, 102, "xE1x"});
    }

    public void testBindWildcardIRStream()
    {
        MySubscriberMultirowUnderlying subscriber = new MySubscriberMultirowUnderlying();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream * from SupportBean.win:length_batch(2)");
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
        String stmtText = "select irstream string, intPrimitive from " + SupportBean.class.getName() + ".win:length_batch(2)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
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
        String stmtText = "select irstream string, intPrimitive from " + SupportBean.class.getName() + ".win:length_batch(2)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
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
        String stmtText = "select irstream string, intPrimitive from " + SupportBean.class.getName() + ".win:length_batch(2)";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select bytePrimitive, intPrimitive, longPrimitive, floatPrimitive from SupportBean(string='E1')");
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
        EPStatement stmt = epService.getEPAdministrator().createEPL("select * from SupportBean(string='E2')");
        stmt.setSubscriber(subscriber);

        SupportBean event = new SupportBean("E2", 1);
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {event});
    }

    public void testNested()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select nested, nested.nestedNested from SupportBeanComplexProps");
        stmt.setSubscriber(subscriber);

        SupportBeanComplexProps event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {event.getNested(), event.getNested().getNestedNested()});
    }

    public void testEnum()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, supportEnum from SupportBeanWithEnum");
        stmt.setSubscriber(subscriber);

        SupportBeanWithEnum event = new SupportBeanWithEnum("abc", SupportEnum.ENUM_VALUE_1);
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {event.getString(), event.getSupportEnum()});
    }

    public void testNullType()
    {
        MySubscriberRowByRowSpecific subscriber = new MySubscriberRowByRowSpecific();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select null, longBoxed from SupportBean");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean());
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicate().get(0), new Object[] {null, null});
    }

    public void testObjectArrayDelivery()
    {
        MySubscriberRowByRowObjectArr subscriber = new MySubscriberRowByRowObjectArr();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select string, intPrimitive from SupportBean.std:unique(string)");
        stmt.setSubscriber(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertEqualsAnyOrder(subscriber.getAndResetIndicate().get(0), new Object[] {"E1", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 10));
        ArrayAssertionUtil.assertEqualsAnyOrder(subscriber.getAndResetIndicate().get(0), new Object[] {"E2", 10});
    }

    public void testRowMapDelivery()
    {
        MySubscriberRowByRowMap subscriber = new MySubscriberRowByRowMap();
        EPStatement stmt = epService.getEPAdministrator().createEPL("select irstream string, intPrimitive from SupportBean.std:unique(string)");
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
