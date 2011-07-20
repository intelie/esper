/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.regression.view;

import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.util.SerializableObjectCopier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import junit.framework.TestCase;
import junit.framework.Assert;

public class TestHavingNoGroupBy extends TestCase
{
    private static String SYMBOL_DELL = "DELL";

    private EPServiceProvider epService;
    private SupportUpdateListener listener;
    private EPStatement selectTestView;

    public void setUp()
    {
        listener = new SupportUpdateListener();
        epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
        epService.initialize();
    }

    public void testHavingWildcardSelect() {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String epl = "select * " +
                "from SupportBean.win:length_batch(2) " +
                "where intPrimitive>0 " +
                "having count(*)=2";
        EPStatement stmt = epService.getEPAdministrator().createEPL(epl);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("E1", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E2", 0));
        assertFalse(listener.isInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 1));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 1));
        assertTrue(listener.getAndClearIsInvoked());

        epService.getEPRuntime().sendEvent(new SupportBean("E3", 0));
        epService.getEPRuntime().sendEvent(new SupportBean("E4", 1));
        assertFalse(listener.getAndClearIsInvoked());
    }

    public void testSumOneViewOM() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("symbol", "price").streamSelector(StreamSelector.RSTREAM_ISTREAM_BOTH).add(Expressions.avg("price"), "avgPrice"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarketDataBean.class.getName()).addView("win", "length", Expressions.constant(5))));
        model.setHavingClause(Expressions.lt(Expressions.property("price"), Expressions.avg("price")));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String viewExpr = "select irstream symbol, price, avg(price) as avgPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "having price < avg(price)";
        assertEquals(viewExpr, model.toEPL());

        selectTestView = epService.getEPAdministrator().create(model);
        selectTestView.addListener(listener);

        runAssertion();
    }

    public void testSumOneView()
    {
        String viewExpr = "select irstream symbol, price, avg(price) as avgPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) " +
                          "having price < avg(price)";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        runAssertion();
    }

    public void testSumJoin()
    {
        String viewExpr = "select irstream symbol, price, avg(price) as avgPrice " +
                          "from " + SupportBeanString.class.getName() + ".win:length(100) as one, " +
                                    SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "where one.string = two.symbol " +
                          "having price < avg(price)";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBeanString(SYMBOL_DELL));

        runAssertion();
    }

    public void testSumHavingNoAggregatedProp()
    {
        String viewExpr = "select irstream symbol, price, avg(price) as avgPrice " +
                          "from " + SupportMarketDataBean.class.getName() + ".win:length(5) as two " +
                          "having volume < avg(price)";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);
    }

    public void testNoAggregationJoinHaving()
    {
        runNoAggregationJoin("having");
    }

    public void testNoAggregationJoinWhere()
    {
        runNoAggregationJoin("where");
    }

    public void testSubstreamSelectHaving()
    {
        epService.getEPAdministrator().getConfiguration().addEventType("SupportBean", SupportBean.class);
        String stmtText = "insert into MyStream select quote.* from SupportBean.win:length(14) quote having avg(intPrimitive) >= 3\n";
        EPStatement stmt = epService.getEPAdministrator().createEPL(stmtText);
        stmt.addListener(listener);

        epService.getEPRuntime().sendEvent(new SupportBean("abc", 2));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("abc", 2));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("abc", 3));
        assertFalse(listener.isInvoked());
        epService.getEPRuntime().sendEvent(new SupportBean("abc", 5));
        assertTrue(listener.isInvoked());
    }

    private void runNoAggregationJoin(String filterClause)
    {
        String viewExpr = "select irstream a.price as aPrice, b.price as bPrice, Math.max(a.price, b.price) - Math.min(a.price, b.price) as spread " +
                          "from " + SupportMarketDataBean.class.getName() + "(symbol='SYM1').win:length(1) as a, " +
                                    SupportMarketDataBean.class.getName() + "(symbol='SYM2').win:length(1) as b " +
                          filterClause + " Math.max(a.price, b.price) - Math.min(a.price, b.price) >= 1.4";

        selectTestView = epService.getEPAdministrator().createEPL(viewExpr);
        selectTestView.addListener(listener);

        sendPriceEvent("SYM1", 20);
        assertFalse(listener.isInvoked());

        sendPriceEvent("SYM2", 10);
        assertNewSpreadEvent(20, 10, 10);

        sendPriceEvent("SYM2", 20);
        assertOldSpreadEvent(20, 10, 10);

        sendPriceEvent("SYM2", 20);
        sendPriceEvent("SYM2", 20);
        sendPriceEvent("SYM1", 20);
        assertFalse(listener.isInvoked());

        sendPriceEvent("SYM1", 18.7);
        assertFalse(listener.isInvoked());

        sendPriceEvent("SYM2", 20);
        assertFalse(listener.isInvoked());

        sendPriceEvent("SYM1", 18.5);
        assertNewSpreadEvent(18.5, 20, 1.5d);

        sendPriceEvent("SYM2", 16);
        assertOldNewSpreadEvent(18.5, 20, 1.5d, 18.5, 16, 2.5d);

        sendPriceEvent("SYM1", 12);
        assertOldNewSpreadEvent(18.5, 16, 2.5d, 12, 16, 4);
    }

    private void assertOldNewSpreadEvent(double oldaprice, double oldbprice, double oldspread,
                                         double newaprice, double newbprice, double newspread)
    {
        Assert.assertEquals(1, listener.getOldDataList().size());
        Assert.assertEquals(1, listener.getLastOldData().length);
        Assert.assertEquals(1, listener.getNewDataList().size());   // since event null is put into the list
        Assert.assertEquals(1, listener.getLastNewData().length);

        EventBean oldEvent = listener.getLastOldData()[0];
        EventBean newEvent = listener.getLastNewData()[0];

        compareSpreadEvent(oldEvent, oldaprice, oldbprice, oldspread);
        compareSpreadEvent(newEvent, newaprice, newbprice, newspread);

        listener.reset();
    }

    private void assertOldSpreadEvent(double aprice, double bprice, double spread)
    {
        Assert.assertEquals(1, listener.getOldDataList().size());
        Assert.assertEquals(1, listener.getLastOldData().length);
        Assert.assertEquals(1, listener.getNewDataList().size());   // since event null is put into the list
        Assert.assertNull(listener.getLastNewData());

        EventBean event = listener.getLastOldData()[0];

        compareSpreadEvent(event, aprice, bprice, spread);
        listener.reset();
    }

    private void assertNewSpreadEvent(double aprice, double bprice, double spread)
    {
        Assert.assertEquals(1, listener.getNewDataList().size());
        Assert.assertEquals(1, listener.getLastNewData().length);
        Assert.assertEquals(1, listener.getOldDataList().size());
        Assert.assertNull(listener.getLastOldData());

        EventBean event = listener.getLastNewData()[0];
        compareSpreadEvent(event, aprice, bprice, spread);
        listener.reset();
    }

    private void compareSpreadEvent(EventBean event, double aprice, double bprice, double spread)
    {
        assertEquals(aprice, event.get("aPrice"));
        assertEquals(bprice, event.get("bPrice"));
        assertEquals(spread, event.get("spread"));
    }

    private void sendPriceEvent(String symbol, double price)
    {
        epService.getEPRuntime().sendEvent(new SupportMarketDataBean(symbol, price, -1L, null));
    }

    private void runAssertion()
    {
        // assert select result type
        assertEquals(String.class, selectTestView.getEventType().getPropertyType("symbol"));
        assertEquals(double.class, selectTestView.getEventType().getPropertyType("price"));
        assertEquals(Double.class, selectTestView.getEventType().getPropertyType("avgPrice"));

        sendEvent(SYMBOL_DELL, 10);
        assertFalse(listener.isInvoked());

        sendEvent(SYMBOL_DELL, 5);
        assertNewEvents(SYMBOL_DELL, 5d, 7.5d);

        sendEvent(SYMBOL_DELL, 15);
        assertFalse(listener.isInvoked());

        sendEvent(SYMBOL_DELL, 8);  // avg = (10 + 5 + 15 + 8) / 4 = 38/4=9.5
        assertNewEvents(SYMBOL_DELL, 8d, 9.5d);

        sendEvent(SYMBOL_DELL, 10);  // avg = (10 + 5 + 15 + 8 + 10) / 5 = 48/5=9.5
        assertFalse(listener.isInvoked());

        sendEvent(SYMBOL_DELL, 6);  // avg = (5 + 15 + 8 + 10 + 6) / 5 = 44/5=8.8
        // no old event posted, old event falls above current avg price
        assertNewEvents(SYMBOL_DELL, 6d, 8.8d);

        sendEvent(SYMBOL_DELL, 12);  // avg = (15 + 8 + 10 + 6 + 12) / 5 = 51/5=10.2
        assertOldEvents(SYMBOL_DELL, 5d, 10.2d);
    }

    public void testHavingSum()
    {
        String stmt = "select irstream sum(myEvent.intPrimitive) as mysum from pattern [every myEvent=" + SupportBean.class.getName() +
                "] having sum(myEvent.intPrimitive) = 2";
        selectTestView = epService.getEPAdministrator().createEPL(stmt);
        selectTestView.addListener(listener);

        sendEvent(1);
        assertFalse(listener.isInvoked());

        sendEvent(1);
        assertEquals(2, listener.assertOneGetNewAndReset().get("mysum"));

        sendEvent(1);
        assertEquals(2, listener.assertOneGetOldAndReset().get("mysum"));
    }

    public void testHavingSumIStream()
    {
        String stmt = "select istream sum(myEvent.intPrimitive) as mysum from pattern [every myEvent=" + SupportBean.class.getName() +
                "] having sum(myEvent.intPrimitive) = 2";
        selectTestView = epService.getEPAdministrator().createEPL(stmt);
        selectTestView.addListener(listener);

        sendEvent(1);
        assertFalse(listener.isInvoked());

        sendEvent(1);
        assertEquals(2, listener.assertOneGetNewAndReset().get("mysum"));

        sendEvent(1);
        assertFalse(listener.isInvoked());
    }

    private void assertNewEvents(String symbol,
                                 Double newPrice, Double newAvgPrice
                              )
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertNull(oldData);
        assertEquals(1, newData.length);

        assertEquals(symbol, newData[0].get("symbol"));
        assertEquals(newPrice, newData[0].get("price"));
        assertEquals(newAvgPrice, newData[0].get("avgPrice"));

        listener.reset();
    }

    private void assertOldEvents(String symbol,
                                 Double oldPrice, Double oldAvgPrice
                              )
    {
        EventBean[] oldData = listener.getLastOldData();
        EventBean[] newData = listener.getLastNewData();

        assertNull(newData);
        assertEquals(1, oldData.length);

        assertEquals(symbol, oldData[0].get("symbol"));
        assertEquals(oldPrice, oldData[0].get("price"));
        assertEquals(oldAvgPrice, oldData[0].get("avgPrice"));

        listener.reset();
    }

    private void sendEvent(int intPrimitive)
    {
        SupportBean bean = new SupportBean();
        bean.setIntPrimitive(intPrimitive);
        epService.getEPRuntime().sendEvent(bean);
    }

    private void sendEvent(String symbol, double price)
    {
        SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
        epService.getEPRuntime().sendEvent(bean);
    }

    private static final Log log = LogFactory.getLog(TestHavingNoGroupBy.class);
}
