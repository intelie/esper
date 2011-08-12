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

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderByAggregateAll extends TestCase
{
	private static final Log log = LogFactory.getLog(TestOrderByAggregateAll.class);
	private EPServiceProvider epService;
    private SupportUpdateListener testListener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testIteratorAggregateRowPerEvent()
	{
        String[] fields = new String[] {"symbol", "sumPrice"};
        String statementString = "select symbol, sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "order by symbol";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));

        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", 214d},
                                {"CAT", 214d},
                                {"IBM", 214d},
                                {"IBM", 214d},
                                });

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", 289d},
                                {"CAT", 289d},
                                {"IBM", 289d},
                                {"IBM", 289d},
                                {"KGB", 289d},
                                });
    }

    public void testAliases()
    {
        String statementString = "select symbol as mySymbol, sum(price) as mySum from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) " +
                                "output every 6 events " +
                                "order by mySymbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        String fields[] = "mySymbol,mySum".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 15.0}, {"CAT", 21.0}, {"CMU", 8.0}, {"CMU", 10.0}, {"IBM", 3.0}, {"IBM", 7.0}});
    }
    
    public void testAggregateAllJoinOrderFunction()
    {
    	String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "output every 6 events "  +
                                "order by volume*sum(price), symbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);
        sendEvent("IBM", 2);
        sendEvent("KGB", 1);
        sendEvent("CMU", 3);
        sendEvent("IBM", 6);
        sendEvent("CAT", 6);
        sendEvent("CAT", 5);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));

        String fields[] = "symbol".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT"}, {"CAT"}, {"CMU"}, {"IBM"}, {"IBM"}, {"KGB"}});
    }

    public void testAggregateAllOrderFunction()
    {
        String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) " +
                                "output every 6 events "  +
                                "order by volume*sum(price), symbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 2);
        sendEvent("KGB", 1);
        sendEvent("CMU", 3);
        sendEvent("IBM", 6);
        sendEvent("CAT", 6);
        sendEvent("CAT", 5);

        String fields[] = "symbol".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT"}, {"CAT"}, {"CMU"}, {"IBM"}, {"IBM"}, {"KGB"}});
	}

    public void testAggregateAllSum()
	{
		String statementString = "select symbol, sum(price) from " +
		                    SupportMarketDataBean.class.getName() + ".win:length(10) " +
                            "output every 6 events " +
                            "order by symbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        String fields[] = "symbol,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 15.0}, {"CAT", 21.0}, {"CMU", 8.0}, {"CMU", 10.0}, {"IBM", 3.0}, {"IBM", 7.0}});
    }

    public void testAggregateAllMaxSum()
    {
        String statementString = "select symbol, max(sum(price)) from " +
                            SupportMarketDataBean.class.getName() + ".win:length(10) " +
                            "output every 6 events " +
                            "order by symbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        String fields[] = "symbol,max(sum(price))".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 15.0}, {"CAT", 21.0}, {"CMU", 8.0}, {"CMU", 10.0}, {"IBM", 3.0}, {"IBM", 7.0}});
    }

    public void testAggregateAllSumHaving()
    {
        String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) " +
                                "having sum(price) > 0 " +
                                "output every 6 events " +
                                "order by symbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        String fields[] = "symbol,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 15.0}, {"CAT", 21.0}, {"CMU", 8.0}, {"CMU", 10.0}, {"IBM", 3.0}, {"IBM", 7.0}});
    }

    public void testAggOrderWithSum()
    {
        String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) " +
                                "output every 6 events "  +
                                "order by symbol, sum(price)";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        String fields[] = "symbol,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 15.0}, {"CAT", 21.0}, {"CMU", 8.0}, {"CMU", 10.0}, {"IBM", 3.0}, {"IBM", 7.0}});
    }

	public void testAggregateAllJoin()
    {
    	String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "output every 6 events " +
                                "order by symbol, sum(price)";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));

        String fields[] = "symbol,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 11.0}, {"CAT", 11.0}, {"CMU", 21.0}, {"CMU", 21.0}, {"IBM", 18.0}, {"IBM", 18.0}});
    }

    public void testAggregateAllJoinMax()
    {
    	String statementString = "select symbol, max(sum(price)) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "output every 6 events " +
                                "order by symbol";

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));

        String fields[] = "symbol,max(sum(price))".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 11.0}, {"CAT", 11.0}, {"CMU", 21.0}, {"CMU", 21.0}, {"IBM", 18.0}, {"IBM", 18.0}});
    }

    public void testAggHaving()
    {
        String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "having sum(price) > 0 " +
                                "output every 6 events " +
                                "order by symbol";
        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        statement.addListener(testListener);

        sendEvent("IBM", 3);
        sendEvent("IBM", 4);
        sendEvent("CMU", 1);
        sendEvent("CMU", 2);
        sendEvent("CAT", 5);
        sendEvent("CAT", 6);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));

        String fields[] = "symbol,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields, new Object[][] {
                {"CAT", 11.0}, {"CAT", 11.0}, {"CMU", 21.0}, {"CMU", 21.0}, {"IBM", 18.0}, {"IBM", 18.0}});
    }
    
	private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}
}
