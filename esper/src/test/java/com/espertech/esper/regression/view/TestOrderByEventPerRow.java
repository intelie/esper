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
import com.espertech.esper.client.soda.*;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.client.SupportConfigFactory;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.util.SerializableObjectCopier;
import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TestOrderByEventPerRow extends TestCase
{
	private static final Log log = LogFactory.getLog(TestOrderByEventPerRow.class);
	private EPServiceProvider epService;
	private SupportUpdateListener testListener;

    public void setUp()
    {
        Configuration config = SupportConfigFactory.getConfiguration();
        epService = EPServiceProviderManager.getDefaultProvider(config);
        epService.initialize();
    }

    public void testAliasesAggregationCompile() throws Exception
    {
        String statementString = "select symbol, volume, sum(price) as mySum from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price), symbol";

        EPStatementObjectModel model = epService.getEPAdministrator().compileEPL(statementString);
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);
        assertEquals(statementString, model.toEPL());

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().create(model);
        statement.addListener(testListener);

        runAssertionDefault();
    }

    public void testAliasesAggregationOM() throws Exception
    {
        EPStatementObjectModel model = new EPStatementObjectModel();
        model.setSelectClause(SelectClause.create("symbol", "volume").add(Expressions.sum("price"), "mySum"));
        model.setFromClause(FromClause.create(FilterStream.create(SupportMarketDataBean.class.getName()).addView(View.create("win", "length", Expressions.constant(20)))));
        model.setGroupByClause(GroupByClause.create("symbol"));
        model.setOutputLimitClause(OutputLimitClause.create(6));
        model.setOrderByClause(OrderByClause.create(Expressions.sum("price")).add("symbol", false));
        model = (EPStatementObjectModel) SerializableObjectCopier.copy(model);

        String statementString = "select symbol, volume, sum(price) as mySum from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price), symbol";

        assertEquals(statementString, model.toEPL());

        testListener = new SupportUpdateListener();
        EPStatement statement = epService.getEPAdministrator().create(model);
        statement.addListener(testListener);

        runAssertionDefault();
    }

    public void testAliases()
	{
		String statementString = "select symbol, volume, sum(price) as mySum from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by mySum, symbol";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        runAssertionDefault();
	}

    public void testGroupBySwitch()
	{
		// Instead of the row-per-group behavior, these should
		// get row-per-event behavior since there are properties
		// in the order-by that are not in the select expression.
		String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price), symbol, volume";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        runAssertionDefaultNoVolume();
    }

    public void testGroupBySwitchJoin()
	{
        String statementString = "select symbol, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "group by symbol " +
                                "output every 6 events " +
                                "order by sum(price), symbol, volume";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));

        runAssertionDefaultNoVolume();
	}

	public void testLast()
	{
    	String statementString = "select symbol, volume, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) " +
                                "group by symbol " +
                                "output last every 6 events " +
                                "order by sum(price)";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        runAssertionLast();
    }

    public void testLastJoin()
    {
        String statementString = "select symbol, volume, sum(price) from " +
                                SupportMarketDataBean.class.getName() + ".win:length(20) as one, " +
                                SupportBeanString.class.getName() + ".win:length(100) as two " +
                                "where one.symbol = two.string " +
                                "group by symbol " +
                                "output last every 6 events " +
                                "order by sum(price)";

        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        testListener = new SupportUpdateListener();
        statement.addListener(testListener);

        epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
        epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));

        runAssertionLast();
    }

    private void runAssertionLast()
    {
        sendEvent("IBM", 101, 3);
        sendEvent("IBM", 102, 4);
        sendEvent("CMU", 103, 1);
        sendEvent("CMU", 104, 2);
        sendEvent("CAT", 105, 5);
        sendEvent("CAT", 106, 6);

        String fields[] = "symbol,volume,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields,
                new Object[][] {{"CMU", 104L, 3.0}, {"IBM", 102L, 7.0}, {"CAT", 106L, 11.0}});
        assertNull(testListener.getLastOldData());

        sendEvent("IBM", 201, 3);
        sendEvent("IBM", 202, 4);
        sendEvent("CMU", 203, 5);
        sendEvent("CMU", 204, 5);
        sendEvent("DOG", 205, 0);
        sendEvent("DOG", 206, 1);

        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields,
                new Object[][] {{"DOG", 206L, 1.0}, {"CMU", 204L, 13.0}, {"IBM", 202L, 14.0}});
        assertNull(testListener.getLastOldData());
    }


    public void testIteratorGroupByEventPerRow()
	{
        String[] fields = new String[] {"symbol", "string", "sumPrice"};
        String statementString = "select symbol, string, sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "group by symbol " +
                    "order by symbol";
        EPStatement statement = epService.getEPAdministrator().createEPL(statementString);
        sendJoinEvents();
        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", "CAT", 65d},
                                {"CAT", "CAT", 65d},
                                {"IBM", "IBM", 149d},
                                {"IBM", "IBM", 149d},
                                });

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsAnyOrder(statement.iterator(), fields,
                new Object[][] {
                                {"CAT", "CAT", 65d},
                                {"CAT", "CAT", 65d},
                                {"IBM", "IBM", 149d},
                                {"IBM", "IBM", 149d},
                                {"KGB", "KGB", 75d},
                                });
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

    private void sendEvent(String symbol, long volume, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, volume, null);
	    epService.getEPRuntime().sendEvent(bean);
	}

	private void sendJoinEvents()
	{
		epService.getEPRuntime().sendEvent(new SupportBeanString("CAT"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("IBM"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("CMU"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("KGB"));
		epService.getEPRuntime().sendEvent(new SupportBeanString("DOG"));
	}

    private void runAssertionDefault()
    {
        sendEvent("IBM", 110, 3);
        sendEvent("IBM", 120, 4);
        sendEvent("CMU", 130, 1);
        sendEvent("CMU", 140, 2);
        sendEvent("CAT", 150, 5);
        sendEvent("CAT", 160, 6);

        String fields[] = "symbol,volume,mySum".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields,
                new Object[][] {{"CMU", 130L, 1.0}, {"CMU", 140L, 3.0}, {"IBM", 110L, 3.0},
                        {"CAT", 150L, 5.0}, {"IBM", 120L, 7.0}, {"CAT", 160L, 11.0} });
        assertNull(testListener.getLastOldData());
    }

    private void runAssertionDefaultNoVolume()
    {
        sendEvent("IBM", 110, 3);
        sendEvent("IBM", 120, 4);
        sendEvent("CMU", 130, 1);
        sendEvent("CMU", 140, 2);
        sendEvent("CAT", 150, 5);
        sendEvent("CAT", 160, 6);

        String fields[] = "symbol,sum(price)".split(",");
        ArrayAssertionUtil.assertPropsPerRow(testListener.getLastNewData(), fields,
                new Object[][] {{"CMU", 1.0}, {"CMU", 3.0}, {"IBM", 3.0},
                        {"CAT", 5.0}, {"IBM", 7.0}, {"CAT", 11.0} });
        assertNull(testListener.getLastOldData());
    }
}
