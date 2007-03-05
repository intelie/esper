using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.example.stockticker.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.rsi
{
	[TestFixture]
	public class TestRSI // : StockTickerRegressionConstants
	{
		private const string SYMBOL = "GOOG";
		private int PERIOD = 4;
	
	    private RSIStockTickerListener _stockListener;
	    private RSIListener _rsiListener;
	    private EPServiceProvider _epService;
	    private EPStatement _factory;
	    private string _expressionText = null;
	
	    [SetUp]
	    protected void setUp()
	    {
	        Configuration configuration = new Configuration();
	        configuration.AddEventTypeAlias("StockTick", typeof(StockTick).FullName);
	        _epService = EPServiceProviderManager.GetProvider("TestStockTickerRSI", configuration);
	        _epService.Initialize();
	
	        _stockListener = new RSIStockTickerListener(_epService, PERIOD);
	        _expressionText = "every tick=StockTick(stockSymbol='" + SYMBOL + "')";
	        //_expressionText = "every tick1=StockTick(stockSymbol='GOOG') -> tick2=StockTick(stockSymbol='GOOG')";
	        _factory = _epService.EPAdministrator.CreatePattern(_expressionText);
	        _factory.AddListener(_stockListener.Update);
	
	        String rsiEvent = typeof(RSIEvent).FullName;
	        String viewExpr = "select * from " + rsiEvent + ".win:length(1)";
	        _rsiListener = new RSIListener();
	        _factory = _epService.EPAdministrator.CreateEQL(viewExpr);
	        _factory.AddListener(_rsiListener.Update);
			_rsiListener.Reset();
	    }
	    
	    [Test]	
		public void testFlow() {
	
			// Bullish Stock, RSI rises beyond 70
			sendEvent(SYMBOL, 50);
			sendEvent(SYMBOL, 100);
			Assert.AreEqual(_rsiListener.AvgGain, Double.MinValue);
			Assert.AreEqual(_rsiListener.RS, Double.MinValue);
			Assert.AreEqual(_rsiListener.RSI, Double.MinValue);
			Assert.AreEqual(_rsiListener.RSICount, 0);
			sendEvent(SYMBOL, 75);
			sendEvent(SYMBOL, 100);
			sendEvent(SYMBOL, 150);
			// AvgLoss = 25 / (period = 4)
			Assert.AreEqual(_rsiListener.AvgLoss, -6.2);
			// AvgGain = (50 + 50 + 25) / (period = 4)
			Assert.AreEqual(_rsiListener.AvgGain, 31.2);
			// First RSI value when number of ticks = periods
			Assert.AreEqual(_rsiListener.RSICount, 1);
			sendEvent(SYMBOL, 125);
			// Add a couple of stock events
			// The trend is bullish, RSI goes beyond 70, overbought signal
			sendEvent(SYMBOL, 200);
			sendEvent(SYMBOL, 250);
			sendEvent(SYMBOL, 225);
			sendEvent(SYMBOL, 300);
	
		}

	    private void sendEvent(String symbol, double price)
	    {
	        StockTick eventBean = new StockTick(symbol, price);
	        _epService.EPRuntime.SendEvent(eventBean);
	    }
	    
	    private static readonly Log _log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
