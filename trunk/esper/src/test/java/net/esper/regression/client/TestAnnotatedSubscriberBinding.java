package net.esper.regression.client;

import junit.framework.TestCase;
import net.esper.client.*;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.bean.*;
import net.esper.support.util.ArrayAssertionUtil;

public class TestAnnotatedSubscriberBinding extends TestCase
{
    private EPServiceProvider epService;
    private String fields[] = "string,intPrimitive".split(",");

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.getEngineDefaults().getThreading().setInternalTimerEnabled(false);
        String pkg = SupportBean.class.getPackage().getName();
        config.addEventTypeAutoAlias(pkg);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testBindMap()
    {
        MyAnnotatedMapSubscriber subscriber = new MyAnnotatedMapSubscriber();
        epService.getEPRuntime().write(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 1));
        ArrayAssertionUtil.assertProps(subscriber.getAndResetIndicateMap().get(0), fields, new Object[] {"E1", 1});

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertProps(subscriber.getAndResetIndicateMap().get(0), fields, new Object[] {"E2", 2});
    }

    public void testBindObjectVarargs()
    {
        MyAnnotatedMapSubscriber subscriber = new MyAnnotatedMapSubscriber();
        epService.getEPRuntime().write(subscriber);

        epService.getEPRuntime().sendEvent(new SupportBean("E2", 2));
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObjectVarags().get(0), new Object[] {"E2", 2});
    }

    public void testBindObjectArray()
    {
        MyAnnotatedMapSubscriber subscriber = new MyAnnotatedMapSubscriber();
        epService.getEPRuntime().write(subscriber);

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("E1", 0, 1L, ""));
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObjectVarags().get(0), new Object[] {"E1", 1L});
    }

    public void testBindWidening()
    {
        MyAnnotatedWideningSubscriber subscriber = new MyAnnotatedWideningSubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBean bean = new SupportBean();
        bean.setString("E1");
        bean.setBytePrimitive((byte)1);
        bean.setIntPrimitive(2);
        bean.setLongPrimitive(3);
        bean.setFloatPrimitive(4);
        epService.getEPRuntime().sendEvent(bean);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObjectVarags().get(0), new Object[] {1, 2L, 3d, 4d});
    }

    public void testNested()
    {
        MyAnnotatedWideningSubscriber subscriber = new MyAnnotatedWideningSubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBeanComplexProps event = SupportBeanComplexProps.makeDefaultBean();
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObjectVarags().get(0), new Object[] {event.getNested(), event.getNested().getNestedNested()});
    }

    public void testEnum()
    {
        MyAnnotatedWideningSubscriber subscriber = new MyAnnotatedWideningSubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBeanWithEnum event = new SupportBeanWithEnum("abc", SupportEnum.ENUM_VALUE_1);
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObjectVarags().get(0), new Object[] {event.getString(), event.getSupportEnum()});
    }

    public void testWildcardBean()
    {
        MyAnnotatedWideningSubscriber subscriber = new MyAnnotatedWideningSubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBean event = new SupportBean("E2", 1);
        epService.getEPRuntime().sendEvent(event);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObjectVarags().get(0), new Object[] {event});
    }

    public void testWildcardBeanBatch()
    {
        MyAnnotatedWideningSubscriber subscriber = new MyAnnotatedWideningSubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBean eventOne = new SupportBean("E3", 1);
        SupportBean eventTwo = new SupportBean("E3", 2);
        epService.getEPRuntime().sendEvent(eventOne);
        assertEquals(0, subscriber.getAndResetIndicateObjectVarags().size());

        epService.getEPRuntime().sendEvent(eventTwo);
        ArrayAssertionUtil.assertEqualsExactOrder(subscriber.getAndResetIndicateObject().iterator(), new Object[] {eventOne, eventTwo});
    }

    public void testWildcardBeanBatchArrayObject()
    {
        MyAnnotatedArraySubscriber subscriber = new MyAnnotatedArraySubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBean eventOne = new SupportBean("E1", 1);
        SupportBean eventTwo = new SupportBean("E1", 2);
        epService.getEPRuntime().sendEvent(eventOne);
        assertEquals(0, subscriber.getAndResetIndicateIStream().size());

        epService.getEPRuntime().sendEvent(eventTwo);
        Object[] result = (Object[]) subscriber.getAndResetIndicateIStream().get(0);
        ArrayAssertionUtil.assertEqualsExactOrder(result, new Object[] {eventOne, eventTwo});
    }

    public void testWildcardBeanBatchArrayBean()
    {
        MyAnnotatedArraySubscriber subscriber = new MyAnnotatedArraySubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBean eventOne = new SupportBean("E2", 1);
        SupportBean eventTwo = new SupportBean("E2", 2);
        epService.getEPRuntime().sendEvent(eventOne);
        assertEquals(0, subscriber.getAndResetIndicateIStream().size());

        epService.getEPRuntime().sendEvent(eventTwo);
        Object[] result = (Object[]) subscriber.getAndResetIndicateIStream().get(0);
        ArrayAssertionUtil.assertEqualsExactOrder(result, new Object[] {eventOne, eventTwo});
    }

    public void testWildcardBeanBatchArrayBeanIR()
    {
        MyAnnotatedArraySubscriber subscriber = new MyAnnotatedArraySubscriber();
        epService.getEPRuntime().write(subscriber);

        SupportBean eventOne = new SupportBean("E3", 1);
        SupportBean eventTwo = new SupportBean("E3", 2);
        epService.getEPRuntime().sendEvent(eventOne);
        assertEquals(0, subscriber.getAndResetIndicateIStream().size());

        epService.getEPRuntime().sendEvent(eventTwo);
        ArrayAssertionUtil.assertEqualsExactOrder((Object[]) subscriber.getAndResetIndicateIStream().get(0), new Object[] {eventOne, eventTwo});
        ArrayAssertionUtil.assertEqualsExactOrder((Object[]) subscriber.getAndResetIndicateRStream().get(0), null);

        SupportBean eventThree = new SupportBean("E3", 3);
        SupportBean eventFour = new SupportBean("E3", 4);
        epService.getEPRuntime().sendEvent(eventThree);
        assertEquals(0, subscriber.getAndResetIndicateIStream().size());
        assertEquals(0, subscriber.getAndResetIndicateRStream().size());

        epService.getEPRuntime().sendEvent(eventFour);
        ArrayAssertionUtil.assertEqualsExactOrder((Object[])subscriber.getAndResetIndicateIStream().get(0), new Object[] {eventThree, eventFour});
        ArrayAssertionUtil.assertEqualsExactOrder((Object[])subscriber.getAndResetIndicateRStream().get(0), new Object[] {eventOne, eventTwo});
    }
}
