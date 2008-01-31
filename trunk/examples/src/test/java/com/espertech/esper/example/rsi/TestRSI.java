package com.espertech.esper.example.rsi;

import org.apache.commons.logging.*;

import junit.framework.TestCase;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import com.espertech.esper.example.stockticker.eventbean.StockTick;
import com.espertech.esper.example.stockticker.StockTickerRegressionConstants;

public class TestRSI extends TestCase implements StockTickerRegressionConstants {

	private static String SYMBOL = "GOOG";
	private int PERIOD = 4;
	private static final Log _log = LogFactory.getLog(TestRSI.class);

    private RSIStockTickerListener _stockListener;
    private RSIListener _rsiListener;
    private EPServiceProvider _epService;
    EPStatement _factory;
    String _expressionText = null;

	public TestRSI(String arg0) {
		super(arg0);
	}

    protected void setUp() throws Exception
    {
		super.setUp();

        Configuration configuration = new Configuration();
        configuration.addEventTypeAlias("StockTick", StockTick.class.getName());
        _epService = EPServiceProviderManager.getProvider("TestStockTickerRSI", configuration);
        _epService.initialize();

        _stockListener = new RSIStockTickerListener(_epService, PERIOD);
        _expressionText = "every tick=StockTick(stockSymbol='" + SYMBOL + "')";
        //_expressionText = "every tick1=StockTick(stockSymbol='GOOG') -> tick2=StockTick(stockSymbol='GOOG')";
        _factory = _epService.getEPAdministrator().createPattern(_expressionText);
        _factory.addListener(_stockListener);

        String rsiEvent = RSIEvent.class.getName();
        String viewExpr = "select * from " + rsiEvent + ".win:length(1)";
        _rsiListener = new RSIListener();
        _factory = _epService.getEPAdministrator().createEQL(viewExpr);
        _factory.addListener(_rsiListener);
		_rsiListener.reset();

    }

	public void testFlow() {

		// Bullish Stock, RSI rises beyond 70
		sendEvent(SYMBOL, 50);
		sendEvent(SYMBOL, 100);
		assertEquals(_rsiListener.getAvgGain(), Double.MIN_VALUE);
		assertEquals(_rsiListener.getRS(), Double.MIN_VALUE);
		assertEquals(_rsiListener.getRSI(), Double.MIN_VALUE);
		assertEquals(_rsiListener.getRSICount(), 0);
		sendEvent(SYMBOL, 75);
		sendEvent(SYMBOL, 100);
		sendEvent(SYMBOL, 150);
		// AvgLoss = 25 / (period = 4)
		assertEquals(_rsiListener.getAvgLoss(), -6.2);
		// AvgGain = (50 + 50 + 25) / (period = 4)
		assertEquals(_rsiListener.getAvgGain(), 31.2);
		// First RSI value when number of ticks = periods
		assertEquals(_rsiListener.getRSICount(), 1);
		sendEvent(SYMBOL, 125);
		// Add a couple of stock events
		// The trend is bullish, RSI goes beyond 70, overbought signal
		sendEvent(SYMBOL, 200);
		sendEvent(SYMBOL, 250);
		sendEvent(SYMBOL, 225);
		sendEvent(SYMBOL, 300);

	}


	protected void tearDown() throws Exception {
		super.tearDown();
	}


    private void sendEvent(String symbol, double price)
    {
        StockTick event = new StockTick(symbol, price);
        _epService.getEPRuntime().sendEvent(event);
    }

}
