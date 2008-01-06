package net.esper.regression.view;

import junit.framework.TestCase;
import net.esper.client.EPServiceProvider;
import net.esper.client.EPServiceProviderManager;
import net.esper.client.EPStatement;
import net.esper.event.EventBean;
import net.esper.support.bean.SupportBean;
import net.esper.support.bean.SupportMarketDataBean;
import net.esper.support.client.SupportConfigFactory;
import net.esper.support.util.ArrayAssertionUtil;

import java.util.Iterator;

public class TestIterator extends TestCase
{
    private EPServiceProvider epService;

    public void setUp()
    {
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testPatternNoWindow()
    {
        // Test for Esper-115
        String cepStatementString =	"select * from pattern " +
									"[every ( addressInfo = " + SupportBean.class.getName() + "(string='address') " +
									"-> txnWD = " + SupportBean.class.getName() + "(string='txn') ) ] " +
									"where addressInfo.intBoxed = txnWD.intBoxed";
		EPStatement epStatement = epService.getEPAdministrator().createEQL(cepStatementString);

		SupportBean myEventBean1 = new SupportBean();
		myEventBean1.setString("address");
		myEventBean1.setIntBoxed(9001);
		epService.getEPRuntime().sendEvent(myEventBean1);
        assertFalse(epStatement.iterator().hasNext());

        SupportBean myEventBean2 = new SupportBean();
        myEventBean2.setString("txn");
        myEventBean2.setIntBoxed(9001);
        epService.getEPRuntime().sendEvent(myEventBean2);
        assertTrue(epStatement.iterator().hasNext());

        Iterator<EventBean> itr = epStatement.iterator();
        EventBean event = itr.next();
        assertEquals(myEventBean1, event.get("addressInfo"));
        assertEquals(myEventBean2, event.get("txnWD"));
    }

    public void testPatternWithWindow()
    {
		String cepStatementString =	"select * from pattern " +
									"[every ( addressInfo = " + SupportBean.class.getName() + "(string='address') " +
									"-> txnWD = " + SupportBean.class.getName() + "(string='txn') ) ].std:lastevent() " +
									"where addressInfo.intBoxed = txnWD.intBoxed";
		EPStatement epStatement = epService.getEPAdministrator().createEQL(cepStatementString);

		SupportBean myEventBean1 = new SupportBean();
		myEventBean1.setString("address");
		myEventBean1.setIntBoxed(9001);
		epService.getEPRuntime().sendEvent(myEventBean1);

        SupportBean myEventBean2 = new SupportBean();
        myEventBean2.setString("txn");
        myEventBean2.setIntBoxed(9001);
        epService.getEPRuntime().sendEvent(myEventBean2);

        Iterator<EventBean> itr = epStatement.iterator();
        EventBean event = itr.next();
        assertEquals(myEventBean1, event.get("addressInfo"));
        assertEquals(myEventBean2, event.get("txnWD"));
    }

    public void testOrderByWildcard()
    {
        String stmtText = "select * from " + SupportMarketDataBean.class.getName() + ".win:length(5) order by symbol, volume";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        Object eventOne = sendEvent("SYM", 1);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new Object[] {eventOne});

        Object eventTwo = sendEvent("OCC", 2);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new Object[] {eventTwo, eventOne});

        Object eventThree = sendEvent("TOC", 3);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new Object[] {eventTwo, eventOne, eventThree});

        Object eventFour = sendEvent("SYM", 0);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new Object[] {eventTwo, eventFour, eventOne, eventThree});

        Object eventFive = sendEvent("SYM", 10);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new Object[] {eventTwo, eventFour, eventOne, eventFive, eventThree});

        Object eventSix = sendEvent("SYM", 4);
        ArrayAssertionUtil.assertEqualsExactOrderUnderlying(stmt.iterator(), new Object[] {eventTwo, eventFour, eventSix, eventFive, eventThree});
    }

    public void testOrderByProps()
    {
        String[] fields = new String[] {"symbol", "volume"};
        String stmtText = "select symbol, volume from " + SupportMarketDataBean.class.getName() + ".win:length(3) order by symbol, volume";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"SYM", 1L}});

        sendEvent("OCC", 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"OCC", 2L}, {"SYM", 1L}});

        sendEvent("SYM", 0);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"OCC", 2L}, {"SYM", 0L}, {"SYM", 1L}});

        sendEvent("OCC", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"OCC", 2L}, {"OCC", 3L}, {"SYM", 0L}});
    }

    public void testFilter()
    {
        String[] fields = new String[] {"symbol", "vol"};
        String stmtText = "select symbol, volume * 10 as vol from " + SupportMarketDataBean.class.getName() + ".win:length(5)" +
                      " where volume < 0";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 100);
        assertFalse(stmt.iterator().hasNext());
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, null);

        sendEvent("SYM", -1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] {{"SYM", -10L}});

        sendEvent("SYM", -6);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -10L}, {"SYM", -60L}});

        sendEvent("SYM", 1);
        sendEvent("SYM", 16);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -10L}, {"SYM", -60L}});

        sendEvent("SYM", -9);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -10L}, {"SYM", -60L}, {"SYM", -90L}});

        sendEvent("SYM", 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -60L}, {"SYM", -90L}});

        sendEvent("SYM", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -90L}});

        sendEvent("SYM", 4);
        sendEvent("SYM", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -90L}});
        sendEvent("SYM", 6);
        assertFalse(stmt.iterator().hasNext());
    }

    public void testGroupByRowPerGroupOrdered()
    {
        String[] fields = new String[] {"symbol", "sumVol"};
        String stmtText = "select symbol, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol " +
                          "order by symbol";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 100L}});

        sendEvent("OCC", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"OCC", 5L}, {"SYM", 100L}});

        sendEvent("SYM", 10);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"OCC", 5L}, {"SYM", 110L}});

        sendEvent("OCC", 6);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"OCC", 11L}, {"SYM", 110L}});

        sendEvent("ATB", 8);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"ATB", 8L}, {"OCC", 11L}, {"SYM", 110L}});

        sendEvent("ATB", 7);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"ATB", 15L}, {"OCC", 11L}, {"SYM", 10L}});
    }

    public void testGroupByRowPerGroup()
    {
        String[] fields = new String[] {"symbol", "sumVol"};
        String stmtText = "select symbol, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 100L}});

        sendEvent("SYM", 10);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 110L}});

        sendEvent("TAC", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 110L}, {"TAC", 1L}});

        sendEvent("SYM", 11);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 121L}, {"TAC", 1L}});

        sendEvent("TAC", 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 121L}, {"TAC", 3L}});

        sendEvent("OCC", 55);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 21L}, {"TAC", 3L}, {"OCC", 55L}});

        sendEvent("OCC", 4);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"TAC", 3L}, {"SYM", 11L}, {"OCC", 59L}});

        sendEvent("OCC", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 11L}, {"TAC", 2L}, {"OCC", 62L}});
    }

    public void testGroupByRowPerGroupHaving()
    {
        String[] fields = new String[] {"symbol", "sumVol"};
        String stmtText = "select symbol, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol having sum(volume) > 10";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 100L}});

        sendEvent("SYM", 5);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 105L}});

        sendEvent("TAC", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 105L}});

        sendEvent("SYM", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 108L}});

        sendEvent("TAC", 12);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 108L}, {"TAC", 13L}});

        sendEvent("OCC", 55);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"TAC", 13L}, {"OCC", 55L}});

        sendEvent("OCC", 4);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"TAC", 13L}, {"OCC", 59L}});

        sendEvent("OCC", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"TAC", 12L}, {"OCC", 62L}});
    }

    public void testGroupByComplex()
    {
        String[] fields = new String[] {"symbol", "msg"};
        String stmtText = "insert into Cutoff " +
                          "select symbol, (String.valueOf(count(*)) || 'x1000.0') as msg " +
                          "from " + SupportMarketDataBean.class.getName() + ".std:groupby(symbol).win:length(1) " +
                          "where price - volume >= 1000.0 group by symbol having count(*) = 1";
        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("SYM", -1, -1L, null));
        assertFalse(stmt.iterator().hasNext());

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("SYM", 100000d, 0L, null));
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", "1x1000.0"}});

        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("SYM", 1d, 1L, null));
        assertFalse(stmt.iterator().hasNext());
    }

    public void testGroupByRowPerEventOrdered()
    {
        String[] fields = new String[] {"symbol", "price", "sumVol"};
        String stmtText = "select symbol, price, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol " +
                          "order by symbol";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", -1, 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -1d, 100L}});

        sendEvent("TAC", -2, 12);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 100L}, {"TAC", -2d, 12L}});

        sendEvent("TAC", -3, 13);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 100L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}});

        sendEvent("SYM", -4, 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 101L}, {"SYM", -4d, 101L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}});

        sendEvent("OCC", -5, 99);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"OCC", -5d, 99L}, {"SYM", -1d, 101L}, {"SYM", -4d, 101L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}});

        sendEvent("TAC", -6, 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"OCC", -5d, 99L}, {"SYM", -4d, 1L}, {"TAC", -2d, 27L}, {"TAC", -3d, 27L}, {"TAC", -6d, 27L}});
    }

    public void testGroupByRowPerEvent()
    {
        String[] fields = new String[] {"symbol", "price", "sumVol"};
        String stmtText = "select symbol, price, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", -1, 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -1d, 100L}});

        sendEvent("TAC", -2, 12);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 100L}, {"TAC", -2d, 12L}});

        sendEvent("TAC", -3, 13);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 100L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}});

        sendEvent("SYM", -4, 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 101L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}, {"SYM", -4d, 101L}});

        sendEvent("OCC", -5, 99);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 101L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}, {"SYM", -4d, 101L}, {"OCC", -5d, 99L}});

        sendEvent("TAC", -6, 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"TAC", -2d, 27L}, {"TAC", -3d, 27L}, {"SYM", -4d, 1L}, {"OCC", -5d, 99L}, {"TAC", -6d, 27L}});
    }

    public void testGroupByRowPerEventHaving()
    {
        String[] fields = new String[] {"symbol", "price", "sumVol"};
        String stmtText = "select symbol, price, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "group by symbol having sum(volume) > 20";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", -1, 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", -1d, 100L}});

        sendEvent("TAC", -2, 12);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 100L}});

        sendEvent("TAC", -3, 13);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 100L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}});

        sendEvent("SYM", -4, 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 101L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}, {"SYM", -4d, 101L}});

        sendEvent("OCC", -5, 99);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"SYM", -1d, 101L}, {"TAC", -2d, 25L}, {"TAC", -3d, 25L}, {"SYM", -4d, 101L}, {"OCC", -5d, 99L}});

        sendEvent("TAC", -6, 2);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields,
                new Object[][] { {"TAC", -2d, 27L}, {"TAC", -3d, 27L}, {"OCC", -5d, 99L}, {"TAC", -6d, 27L}});
    }

    public void testAggregateAll()
    {
        String[] fields = new String[] {"symbol", "sumVol"};
        String stmtText = "select symbol, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());        

        sendEvent("SYM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 100L}});

        sendEvent("TAC", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 101L}, {"TAC", 101L}});

        sendEvent("MOV", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 104L}, {"TAC", 104L}, {"MOV", 104L}});

        sendEvent("SYM", 10);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"TAC", 14L}, {"MOV", 14L}, {"SYM", 14L}});
    }

    public void testAggregateAllOrdered()
    {
        String[] fields = new String[] {"symbol", "sumVol"};
        String stmtText = "select symbol, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) " +
                          " order by symbol asc";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 100L}});

        sendEvent("TAC", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 101L}, {"TAC", 101L}});

        sendEvent("MOV", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"MOV", 104L}, {"SYM", 104L}, {"TAC", 104L}});

        sendEvent("SYM", 10);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"MOV", 14L}, {"SYM", 14L}, {"TAC", 14L}});
    }

    public void testAggregateAllHaving()
    {
        String[] fields = new String[] {"symbol", "sumVol"};
        String stmtText = "select symbol, sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) having sum(volume) > 100";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("SYM", 100);
        assertFalse(stmt.iterator().hasNext());

        sendEvent("TAC", 1);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 101L}, {"TAC", 101L}});

        sendEvent("MOV", 3);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {"SYM", 104L}, {"TAC", 104L}, {"MOV", 104L}});

        sendEvent("SYM", 10);
        assertFalse(stmt.iterator().hasNext());
    }

    public void testRowForAll()
    {
        String[] fields = new String[] {"sumVol"};
        String stmtText = "select sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) ";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {null}});

        sendEvent(100);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {100L}});

        sendEvent(50);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {150L}});

        sendEvent(25);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {175L}});

        sendEvent(10);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {85L}});
    }

    public void testRowForAllHaving()
    {
        String[] fields = new String[] {"sumVol"};
        String stmtText = "select sum(volume) as sumVol " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(3) having sum(volume) > 100";

        EPStatement stmt = epService.getEPAdministrator().createEQL(stmtText);
        assertFalse(stmt.iterator().hasNext());

        sendEvent(100);
        assertFalse(stmt.iterator().hasNext());

        sendEvent(50);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {150L}});

        sendEvent(25);
        ArrayAssertionUtil.assertEqualsExactOrder(stmt.iterator(), fields, new Object[][] { {175L}});

        sendEvent(10);
        assertFalse(stmt.iterator().hasNext());
    }

    private void sendEvent(String symbol, double price, long volume)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, price, volume, null));
    }

    private SupportMarketDataBean sendEvent(String symbol, long volume)
    {
        SupportMarketDataBean event = new SupportMarketDataBean(symbol, 0, volume, null);
        epService.getEPRuntime().sendEvent(event);
        return event;
    }

    private void sendEvent(long volume)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean("SYM", 0, volume, null));
    }
}
