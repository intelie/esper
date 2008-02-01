package com.espertech.esper.regression.view;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.support.util.SupportUpdateListener;
import com.espertech.esper.support.util.ArrayAssertionUtil;
import com.espertech.esper.support.bean.SupportMarketDataBean;
import com.espertech.esper.support.bean.SupportBeanString;
import com.espertech.esper.support.client.SupportConfigFactory;

import java.util.List;
import java.util.LinkedList;

public class TestOrderByRowForAll extends TestCase
{
	private static final Log log = LogFactory.getLog(TestOrderByRowForAll.class);
	private EPServiceProvider epService;
    private List<String> symbols;
    private List<Double> prices;
    private List<Long> volumes;
	private SupportUpdateListener testListener;

    public void testIteratorAggregateRowForAll()
	{
        String[] fields = new String[] {"sumPrice"};
        String statementString = "select sum(price) as sumPrice from " +
    	            SupportMarketDataBean.class.getName() + ".win:length(10) as one, " +
    	            SupportBeanString.class.getName() + ".win:length(100) as two " +
                    "where one.symbol = two.string " +
                    "order by price";
        EPStatement statement = epService.getEPAdministrator().createEQL(statementString);
        sendJoinEvents();
        sendEvent("CAT", 50);
        sendEvent("IBM", 49);
        sendEvent("CAT", 15);
        sendEvent("IBM", 100);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{214d}});

        sendEvent("KGB", 75);
        ArrayAssertionUtil.assertEqualsExactOrder(statement.iterator(), fields, new Object[][] {{289d}});
    }

    private void sendEvent(String symbol, double price)
	{
	    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
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

	public void setUp()
	{
	    epService = EPServiceProviderManager.getDefaultProvider(SupportConfigFactory.getConfiguration());
	    epService.initialize();
	    symbols = new LinkedList<String>();
	    prices = new LinkedList<Double>();
	    volumes = new LinkedList<Long>();
	}
}
