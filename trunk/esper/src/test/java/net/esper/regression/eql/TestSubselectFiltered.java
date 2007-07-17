package net.esper.regression.eql;

import junit.framework.TestCase;
import net.esper.client.Configuration;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.client.time.TimerControlEvent;
import net.esper.event.EventBean;
import net.esper.event.EventType;
import net.esper.support.bean.*;
import net.esper.support.util.SupportUpdateListener;
import net.esper.support.client.SupportConfigFactory;

public class TestSubselectFiltered extends TestCase
{
    private EPServiceProvider epService;
    private SupportUpdateListener listener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        config.addEventTypeAlias("Sensor", SupportSensorEvent.class);
        config.addEventTypeAlias("MyEvent", SupportBean.class);
        config.addEventTypeAlias("S0", SupportBean_S0.class);
        config.addEventTypeAlias("S1", SupportBean_S1.class);
        config.addEventTypeAlias("S2", SupportBean_S2.class);
        config.addEventTypeAlias("S3", SupportBean_S3.class);
        config.addEventTypeAlias("S4", SupportBean_S4.class);
        config.addEventTypeAlias("S5", SupportBean_S5.class);
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
        listener = new SupportUpdateListener();

        // Use external clocking for the test, reduces logging
        epService.getEPRuntime().sendEvent(new TimerControlEvent(TimerControlEvent.ClockType.CLOCK_EXTERNAL));
    }

    public void testSameEvent()
    {
        String stmtText = "select (select * from S1.win:length(1000)) as events1 from S1";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        EventType type = stmt.getEventType();
        assertEquals(SupportBean_S1.class, type.getPropertyType("events1"));

        Object event = new SupportBean_S1(-1, "Y");
        epService.getEPRuntime().sendEvent(event);
        EventBean result = listener.assertOneGetNewAndReset();
        assertSame(event, result.get("events1"));
    }

    public void testSelectWildcard()
    {
        String stmtText = "select (select * from S1.win:length(1000)) as events1 from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        EventType type = stmt.getEventType();
        assertEquals(SupportBean_S1.class, type.getPropertyType("events1"));

        Object event = new SupportBean_S1(-1, "Y");
        epService.getEPRuntime().sendEvent(event);
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        EventBean result = listener.assertOneGetNewAndReset();
        assertSame(event, result.get("events1"));
    }

    public void testSelectWildcardNoName()
    {
        String stmtText = "select (select * from S1.win:length(1000)) from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        EventType type = stmt.getEventType();
        assertEquals(SupportBean_S1.class, type.getPropertyType("*"));

        Object event = new SupportBean_S1(-1, "Y");
        epService.getEPRuntime().sendEvent(event);
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        EventBean result = listener.assertOneGetNewAndReset();
        assertSame(event, result.get("*"));
    }

    public void testWhereConstant()
    {
        String stmtText = "select (select id from S1.win:length(1000) where p10='X') as ids1 from S0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        // test no event, should return null
        epService.getEPRuntime().sendEvent(new SupportBean_S1(-1, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "Z"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertEquals(1, listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("ids1"));

        // second X event
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids1"));
    }

    public void testWherePrevious()
    {
        String stmtText = "select (select prev(1, id) from S1.win:length(1000) where id=s0.id) as value from S0 as s0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(1, listener.assertOneGetNewAndReset().get("value"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(2, listener.assertOneGetNewAndReset().get("value"));
    }

    public void testSelectWithWhereJoined()
    {
        String stmtText = "select (select id from S1.win:length(1000) where p10=s0.p00) as ids1 from S0 as s0";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "Y"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(3, "Z"));
        
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertNull(listener.assertOneGetNewAndReset().get("ids1"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "X"));
        assertEquals(1, listener.assertOneGetNewAndReset().get("ids1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "Y"));
        assertEquals(2, listener.assertOneGetNewAndReset().get("ids1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "Z"));
        assertEquals(3, listener.assertOneGetNewAndReset().get("ids1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "A"));
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids1"));
    }

    public void testSelectWhereJoined2Streams()
    {
        String stmtText = "select (select id from S0.win:length(1000) where p00=s1.p10 and p00=s2.p20) as ids0 from S1 as s1, S2 as s2 where s1.id = s2.id";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S1(10, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(10, "s0_1"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(99, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(11, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(11, "s0_1"));
        assertEquals(99, listener.assertOneGetNewAndReset().get("ids0"));
    }

    public void testSelectWhereJoined3Streams()
    {
        String stmtText = "select (select id from S0.win:length(1000) where p00=s1.p10 and p00=s3.p30) as ids0 " +
                            "from S1 as s1, S2 as s2, S3 as s3 where s1.id = s2.id and s2.id = s3.id";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S1(10, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(10, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(10, "s0_1"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(99, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(11, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(11, "xxx"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(11, "s0_1"));
        assertEquals(99, listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(98, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(12, "s0_x"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(12, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(12, "s0_1"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(13, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(13, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(13, "s0_x"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(14, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(14, "xx"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(14, "s0_2"));
        assertEquals(98, listener.assertOneGetNewAndReset().get("ids0"));
    }

    public void testSelectWhereJoined3SceneTwo()
    {
        String stmtText = "select (select id from S0.win:length(1000) where p00=s1.p10 and p00=s3.p30 and p00=s2.p20) as ids0 " +
                            "from S1 as s1, S2 as s2, S3 as s3 where s1.id = s2.id and s2.id = s3.id";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S1(10, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(10, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(10, "s0_1"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(99, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(11, "s0_1"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(11, "xxx"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(11, "s0_1"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(98, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(12, "s0_x"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(12, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(12, "s0_1"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(13, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(13, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(13, "s0_x"));
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        epService.getEPRuntime().sendEvent(new SupportBean_S1(14, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S2(14, "s0_2"));
        epService.getEPRuntime().sendEvent(new SupportBean_S3(14, "s0_2"));
        assertEquals(98, listener.assertOneGetNewAndReset().get("ids0"));
    }

    public void testSelectWhereJoined4Coercion()
    {
        String stmtText = "select " +
          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
          "  where intBoxed=s1.longBoxed and " +
                   "intBoxed=s2.doubleBoxed and " +
                   "doubleBoxed=s3.intBoxed" +
          ") as ids0 from " +
          "MyEvent(string='A') as s1, " +
          "MyEvent(string='B') as s2, " +
          "MyEvent(string='C') as s3 " +
          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
        trySelectWhereJoined4Coercion(stmtText);

        stmtText = "select " +
          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
          "  where doubleBoxed=s3.intBoxed and " +
                   "intBoxed=s2.doubleBoxed and " +
                   "intBoxed=s1.longBoxed" +
          ") as ids0 from " +
          "MyEvent(string='A') as s1, " +
          "MyEvent(string='B') as s2, " +
          "MyEvent(string='C') as s3 " +
          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
        trySelectWhereJoined4Coercion(stmtText);

        stmtText = "select " +
          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
          "  where doubleBoxed=s3.intBoxed and " +
                   "intBoxed=s1.longBoxed and " +
                   "intBoxed=s2.doubleBoxed" +
          ") as ids0 from " +
          "MyEvent(string='A') as s1, " +
          "MyEvent(string='B') as s2, " +
          "MyEvent(string='C') as s3 " +
          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
        trySelectWhereJoined4Coercion(stmtText);
    }

    public void testSelectWhereJoined4BackCoercion()
    {
        String stmtText = "select " +
          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
          "  where longBoxed=s1.intBoxed and " +
                   "longBoxed=s2.doubleBoxed and " +
                   "intBoxed=s3.longBoxed" +
          ") as ids0 from " +
          "MyEvent(string='A') as s1, " +
          "MyEvent(string='B') as s2, " +
          "MyEvent(string='C') as s3 " +
          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
        trySelectWhereJoined4CoercionBack(stmtText);

        stmtText = "select " +
          "(select intPrimitive from MyEvent(string='S').win:length(1000) " +
          "  where longBoxed=s2.doubleBoxed and " +
                   "intBoxed=s3.longBoxed and " +
                   "longBoxed=s1.intBoxed " +
          ") as ids0 from " +
          "MyEvent(string='A') as s1, " +
          "MyEvent(string='B') as s2, " +
          "MyEvent(string='C') as s3 " +
          "where s1.intPrimitive = s2.intPrimitive and s2.intPrimitive = s3.intPrimitive";
        trySelectWhereJoined4CoercionBack(stmtText);
    }

    private void trySelectWhereJoined4CoercionBack(String stmtText)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        sendBean("A", 1, 10, 200, 3000);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("B", 1, 10, 200, 3000);
        sendBean("C", 1, 10, 200, 3000);
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -1, 11, 201, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("A", 2, 201, 0, 0);
        sendBean("B", 2, 0, 0, 201);
        sendBean("C", 2, 0, 11, 0);
        assertEquals(-1, listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -2, 12, 202, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("A", 3, 202, 0, 0);
        sendBean("B", 3, 0, 0, 202);
        sendBean("C", 3, 0, -1, 0);
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -3, 13, 203, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("A", 4, 203, 0, 0);
        sendBean("B", 4, 0, 0, 203.0001);
        sendBean("C", 4, 0, 13, 0);
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -4, 14, 204, 0);     // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("A", 5, 205, 0, 0);
        sendBean("B", 5, 0, 0, 204);
        sendBean("C", 5, 0, 14, 0);
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids0"));

        stmt.stop();
    }

    private void trySelectWhereJoined4Coercion(String stmtText)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        sendBean("A", 1, 10, 200, 3000);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("B", 1, 10, 200, 3000);
        sendBean("C", 1, 10, 200, 3000);        
        assertNull(listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -2, 11, 0, 3001);
        sendBean("A", 2, 0, 11, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("B", 2, 0, 0, 11);
        sendBean("C", 2, 3001, 0, 0);
        assertEquals(-2, listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -3, 12, 0, 3002);
        sendBean("A", 3, 0, 12, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("B", 3, 0, 0, 12);
        sendBean("C", 3, 3003, 0, 0);
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -4, 11, 0, 3003);
        sendBean("A", 4, 0, 0, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("B", 4, 0, 0, 11);
        sendBean("C", 4, 3003, 0, 0);
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids0"));

        sendBean("S", -5, 14, 0, 3004);
        sendBean("A", 5, 0, 14, 0);        // intPrimitive, intBoxed, longBoxed, doubleBoxed
        sendBean("B", 5, 0, 0, 11);
        sendBean("C", 5, 3004, 0, 0);
        assertEquals(null, listener.assertOneGetNewAndReset().get("ids0"));

        stmt.stop();
    }

    public void testSelectWithWhere2Subqery()
    {
        String stmtText = "select id from S0 as s0 where " +
                        " id = (select id from S1.win:length(1000) where s0.id = id) or id = (select id from S2.win:length(1000) where s0.id = id)";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(1));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1));
        assertEquals(1, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S2(2));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2));
        assertEquals(2, listener.assertOneGetNewAndReset().get("id"));

        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean_S1(3));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(3));
        assertEquals(3, listener.assertOneGetNewAndReset().get("id"));
    }

    public void testJoinFilteredOne()
    {
        String stmtText = "select s0.id as s0id, s1.id as s1id, " +
                          "(select p20 from S2.win:length(1000) where id=s0.id) as s2p20, " +
                          "(select prior(1, p20) from S2.win:length(1000) where id=s0.id) as s2p20Prior, " +
                          "(select prev(1, p20) from S2.win:length(10) where id=s0.id) as s2p20Prev " +
                          "from S0 as s0, S1 as s1 " +
                          "where s0.id = s1.id and p00||p10 = (select p20 from S2.win:length(1000) where id=s0.id)";
        tryJoinFiltered(stmtText);
    }

    public void testJoinFilteredTwo()
    {
        String stmtText = "select s0.id as s0id, s1.id as s1id, " +
                          "(select p20 from S2.win:length(1000) where id=s0.id) as s2p20, " +
                          "(select prior(1, p20) from S2.win:length(1000) where id=s0.id) as s2p20Prior, " +
                          "(select prev(1, p20) from S2.win:length(10) where id=s0.id) as s2p20Prev " +
                          "from S0 as s0, S1 as s1 " +
                          "where s0.id = s1.id and (select s0.p00||s1.p10 = p20 from S2.win:length(1000) where id=s0.id)";
        tryJoinFiltered(stmtText);
    }

    public void testSubselectPrior()
    {
        String stmtTextOne = "insert into Pair " +
                "select * from Sensor(device='A').std:lastevent() as a, Sensor(device='B').std:lastevent() as b " +
                "where a.type = b.type";
        epService.getEPAdministrator().createEQL(stmtTextOne);

        epService.getEPAdministrator().createEQL("insert into PairDuplicatesRemoved select * from Pair(1=2)");
        
        String stmtTextTwo = "insert into PairDuplicatesRemoved " +
                "select * from Pair " +
                "where a.id != (select a.id from PairDuplicatesRemoved.std:lastevent())" +
                "  and b.id != (select b.id from PairDuplicatesRemoved.std:lastevent())";
        EPStatement stmtTwo = epService.getEPAdministrator().createEQL(stmtTextTwo);
        stmtTwo.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(1,"Temperature","A",51,94.5));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(2,"Temperature","A",57,95.5));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(3,"Humidity","B",29,67.5));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(4,"Temperature","B",55,88.0));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(2, event.get("a.id"));
        assertEquals(4, event.get("b.id"));

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(5,"Temperature","B",65,85.0));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(6,"Temperature","B",49,87.0));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(7,"Temperature","A",51,99.5));
        event = listener.assertOneGetNewAndReset();
        assertEquals(7, event.get("a.id"));
        assertEquals(6, event.get("b.id"));
    }

    public void testSubselectMixMax()
    {
        String stmtTextOne =
                     "select " +
                     " (select * from Sensor.ext:sort('measurement',true,1)) as high, " +
                     " (select * from Sensor.ext:sort('measurement',false,1)) as low " +
                     " from Sensor";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtTextOne);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(1, "Temp", "Dev1", 68.0, 96.5));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(68.0, ((SupportSensorEvent) event.get("high")).getMeasurement());
        assertEquals(68.0, ((SupportSensorEvent) event.get("low")).getMeasurement());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(2, "Temp", "Dev2", 70.0, 98.5));
        event = listener.assertOneGetNewAndReset();
        assertEquals(70.0, ((SupportSensorEvent) event.get("high")).getMeasurement());
        assertEquals(68.0, ((SupportSensorEvent) event.get("low")).getMeasurement());

        epService.getEPRuntime().sendEvent(new SupportSensorEvent(3, "Temp", "Dev2", 65.0, 99.5));
        event = listener.assertOneGetNewAndReset();
        assertEquals(70.0, ((SupportSensorEvent) event.get("high")).getMeasurement());
        assertEquals(65.0, ((SupportSensorEvent) event.get("low")).getMeasurement());
    }
    
    private void tryJoinFiltered(String stmtText)
    {
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean_S0(0, "X"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(0, "Y"));
        assertFalse(listener.isInvoked());
        
        epService.getEPRuntime().sendEvent(new SupportBean_S2(1, "ab"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(1, "a"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(1, "b"));
        EventBean event = listener.assertOneGetNewAndReset();
        assertEquals(1, event.get("s0id"));
        assertEquals(1, event.get("s1id"));
        assertEquals("ab", event.get("s2p20"));
        assertEquals(null, event.get("s2p20Prior"));
        assertEquals(null, event.get("s2p20Prev"));

        epService.getEPRuntime().sendEvent(new SupportBean_S2(2, "qx"));
        epService.getEPRuntime().sendEvent(new SupportBean_S0(2, "q"));
        epService.getEPRuntime().sendEvent(new SupportBean_S1(2, "x"));
        event = listener.assertOneGetNewAndReset();
        assertEquals(2, event.get("s0id"));
        assertEquals(2, event.get("s1id"));
        assertEquals("qx", event.get("s2p20"));
        assertEquals("ab", event.get("s2p20Prior"));
        assertEquals("ab", event.get("s2p20Prev"));
    }

    private void sendBean(String string, int intPrimitive, int intBoxed, long longBoxed, double doubleBoxed)
    {
        SupportBean bean = new SupportBean();
        bean.setString(string);
        bean.setIntPrimitive(intPrimitive);
        bean.setIntBoxed(intBoxed);
        bean.setLongBoxed(longBoxed);
        bean.setDoubleBoxed(doubleBoxed);
        epService.getEPRuntime().sendEvent(bean);
    }
}
