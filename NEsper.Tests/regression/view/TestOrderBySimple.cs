///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.regression.view
{
	[TestFixture]
	public class TestOrderBySimple {

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
		private EPServiceProvider epService;
	    private IList<double> prices;
	    private IList<string> symbols;
	    private SupportUpdateListener testListener;
		private IList<long> volumes;

	    [SetUp]
	    public void SetUp()
		{
		    epService = EPServiceProviderManager.GetDefaultProvider();
		    epService.Initialize();
		    symbols = new List<string>();
		    prices = new List<double>();
		    volumes = new List<long>();
		}

	    [Test]
	    public void TestAcrossJoin()
		{
	    	String statementString = "select symbol, string from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
	    	AssertValues(symbols, "symbol");
	    	AssertValues(symbols, "string");
	       	AssertOnlyProperties(new String[] {"symbol", "string"});
	    	ClearValues();

	    	statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by string, price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesBySymbolPrice();
	    	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol"});
	    	ClearValues();
		}

	    [Test]
	    public void TestDescending()
		{
			String statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price desc";
			CreateAndSend(statementString);
			OrderValuesByPriceDesc();
			AssertValues(symbols, "symbol");
			ClearValues();

			statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price desc, symbol asc";
			CreateAndSend(statementString);
			OrderValuesByPrice();
            CollectionHelper.Reverse(symbols);
			AssertValues(symbols, "symbol");
			ClearValues();

			statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price asc";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "symbol");
			ClearValues();

			statementString = "select symbol, volume from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by symbol desc";
			CreateAndSend(statementString);
			OrderValuesBySymbol();
            CollectionHelper.Reverse(symbols);
			AssertValues(symbols, "symbol");
			AssertValues(volumes, "volume");
			ClearValues();

			statementString = "select symbol, price from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by symbol desc, price desc";
			CreateAndSend(statementString);
			OrderValuesBySymbolPrice();
            CollectionHelper.Reverse(symbols);
            CollectionHelper.Reverse(prices);
			AssertValues(symbols, "symbol");
			AssertValues(prices, "price");
			ClearValues();

			statementString = "select symbol, price from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by symbol, price";
			CreateAndSend(statementString);
			OrderValuesBySymbolPrice();
			AssertValues(symbols, "symbol");
			AssertValues(prices, "price");
			ClearValues();
		}

	    [Test]
	    public void TestExpressions()
		{
			String statementString = "select symbol from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by (price * 6) + 5";
		 	CreateAndSend(statementString);
		 	OrderValuesByPrice();
		 	AssertValues(symbols, "symbol");
			AssertOnlyProperties(new String[] {"symbol"});
		 	ClearValues();

		 	epService.Initialize();

			statementString = "select symbol, price from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by (price * 6) + 5, price";
		 	CreateAndSend(statementString);
		 	OrderValuesByPrice();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol", "price"});
		 	ClearValues();

		 	epService.Initialize();

			statementString = "select symbol, 1+volume*23 from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by (price * 6) + 5, price, volume";
		 	CreateAndSend(statementString);
		 	OrderValuesByPrice();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol", "(1+(volume*23))"});
		 	ClearValues();

		 	epService.Initialize();

			statementString = "select symbol from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by volume*price, symbol";
		 	CreateAndSend(statementString);
		 	OrderValuesBySymbol();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol"});
		 	ClearValues();

		 	epService.Initialize();

			statementString = "select symbol, Sum(price) from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by volume*sum(price), symbol";
		 	CreateAndSend(statementString);
		 	OrderValuesBySymbol();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol", "sum(price)"});
		 	ClearValues();
		}

	    [Test]
	    public void TestExpressionsJoin()
	    {
	    	String statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by (price * 6) + 5";
	     	CreateAndSend(statementString);
	     	SendJoinEvents();
	     	OrderValuesByPriceJoin();
	     	AssertValues(symbols, "symbol");
	    	AssertOnlyProperties(new String[] {"symbol"});
	     	ClearValues();

	     	epService.Initialize();

	    	statementString = "select symbol, price from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by (price * 6) + 5, price";
	     	CreateAndSend(statementString);
	     	SendJoinEvents();
	     	OrderValuesByPriceJoin();
	     	AssertValues(prices, "price");
	       	AssertOnlyProperties(new String[] {"symbol", "price"});
	     	ClearValues();

	     	epService.Initialize();

	    	statementString = "select symbol, 1+volume*23 from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by (price * 6) + 5, price, volume";
	     	CreateAndSend(statementString);
	     	SendJoinEvents();
	     	OrderValuesByPriceJoin();
	     	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol", "(1+(volume*23))"});
	     	ClearValues();

	     	epService.Initialize();

	    	statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by volume*price, symbol";
	     	CreateAndSend(statementString);
	     	SendJoinEvents();
	     	OrderValuesBySymbol();
	     	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol"});
	     	ClearValues();

	     	epService.Initialize();

	    	statementString = "select symbol, Sum(price) from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by volume*sum(price), symbol";
	     	CreateAndSend(statementString);
	     	SendJoinEvents();
	     	OrderValuesBySymbol();
	     	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol", "sum(price)"});
	     	ClearValues();
	    }

	    [Test]
	    public void TestInvalid()
		{
			String statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by Sum(price)";
			try
			{
				CreateAndSend(statementString);
				Assert.Fail();
			}
			catch(EPStatementException ex)
			{
				// expected
			}

			statementString = "select Sum(price) from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by Sum(price + 6)";
			try
			{
				CreateAndSend(statementString);
				Assert.Fail();
			}
			catch(EPStatementException ex)
			{
				// expected
			}

			statementString = "select Sum(price + 6) from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by Sum(price)";
			try
			{
				CreateAndSend(statementString);
				Assert.Fail();
			}
			catch(EPStatementException ex)
			{
				// expected
			}
		}

	    [Test]
	    public void TestInvalidJoin()
	    {
	    	String statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by Sum(price)";
	    	try
	    	{
	    		CreateAndSend(statementString);
	    		Assert.Fail();
	    	}
	    	catch(EPStatementException ex)
	    	{
	    		// expected
	    	}

	    	statementString = "select Sum(price) from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by Sum(price + 6)";
	    	try
	    	{
	    		CreateAndSend(statementString);
	    		Assert.Fail();
	    	}
	    	catch(EPStatementException ex)
	    	{
	    		// expected
	    	}

	    	statementString = "select Sum(price + 6) from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by Sum(price)";
	    	try
	    	{
	    		CreateAndSend(statementString);
	    		Assert.Fail();
	    	}
	    	catch(EPStatementException ex)
	    	{
	    		// expected
	    	}
	    }

	    [Test]
	    public void TestMultipleKeys()
		{
			String statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
			"output every 6 events "  +
			"order by symbol, price";
			CreateAndSend(statementString);
			OrderValuesBySymbolPrice();
			AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol"});
			ClearValues();

			statementString = "select symbol from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by price, symbol, volume";
		 	CreateAndSend(statementString);
		 	OrderValuesByPriceSymbol();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol"});
		 	ClearValues();

			statementString = "select symbol, volume*2 from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by price, volume";
		 	CreateAndSend(statementString);
		 	OrderValuesByPrice();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol", "(volume*2)"});
		 	ClearValues();
		}

		[Test]
		public void TestAliases()
		{
			String statementString = "select symbol as mySymbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by mySymbol";
			CreateAndSend(statementString);
			OrderValuesBySymbol();
			AssertValues(symbols, "mySymbol");
		   	AssertOnlyProperties(new String[] {"mySymbol"});
			ClearValues();

			statementString = "select symbol as mySymbol, price as myPrice from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by myPrice";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "mySymbol");
			AssertValues(prices, "myPrice");
		   	AssertOnlyProperties(new String[] {"mySymbol", "myPrice"});
			ClearValues();

			statementString = "select symbol, price as myPrice from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by (myPrice * 6) + 5, price";
		 	CreateAndSend(statementString);
		 	OrderValuesByPrice();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol", "myPrice"});
		 	ClearValues();

			statementString = "select symbol, 1+volume*23 as myVol from " +
		 	typeof(SupportMarketDataBean).FullName + ".win:length(10) " +
		 	"output every 6 events "  +
		 	"order by (price * 6) + 5, price, myVol";
		 	CreateAndSend(statementString);
		 	OrderValuesByPrice();
		 	AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol", "myVol"});
		 	ClearValues();

			statementString = "select symbol as mySymbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"order by price, mySymbol";
			CreateAndSend(statementString);
			symbols.Add("CAT");
			AssertValues(symbols, "mySymbol");
			ClearValues();
			SendEvent("FOX", 10);
			symbols.Add("FOX");
			AssertValues(symbols, "mySymbol");
			ClearValues();
		}

	    [Test]
	    public void TestMultipleKeysJoin()
	    {
	    	String statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by symbol, price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesBySymbolPrice();
	    	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol"});
	    	ClearValues();

	    	statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by price, symbol, volume";
	     	CreateAndSend(statementString);
	    	SendJoinEvents();
	     	OrderValuesByPriceSymbol();
	     	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol"});
	     	ClearValues();

	    	statementString = "select symbol, volume*2 from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	     	"output every 6 events "  +
	     	"order by price, volume";
	     	CreateAndSend(statementString);
	    	SendJoinEvents();
	     	OrderValuesByPriceJoin();
	     	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol", "(volume*2)"});
	     	ClearValues();
	    }

	    [Test]
	    public void TestSimple()
		{
			String statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol"});
			ClearValues();

			statementString = "select symbol, price from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "symbol");
			AssertValues(prices, "price");
		   	AssertOnlyProperties(new String[] {"symbol", "price"});
			ClearValues();

			statementString = "select symbol, volume from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "symbol");
			AssertValues(volumes, "volume");
		   	AssertOnlyProperties(new String[] {"symbol", "volume"});
			ClearValues();

			statementString = "select symbol, volume*2 from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "symbol");
			AssertValues(volumes, "(volume*2)");
		   	AssertOnlyProperties(new String[] {"symbol", "(volume*2)"});
			ClearValues();

			statementString = "select symbol, volume from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by symbol";
			CreateAndSend(statementString);
			OrderValuesBySymbol();
			AssertValues(symbols, "symbol");
			AssertValues(volumes, "volume");
		   	AssertOnlyProperties(new String[] {"symbol", "volume"});
			ClearValues();

			statementString = "select price from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by symbol";
			CreateAndSend(statementString);
			OrderValuesBySymbol();
			AssertValues(prices, "price");
		   	AssertOnlyProperties(new String[] {"price"});
			ClearValues();
		}

	    [Test]
	    public void TestSimpleJoin()
	    {
	    	String statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
	    	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol"});
	    	ClearValues();

	    	statementString = "select symbol, price from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
	    	AssertValues(symbols, "symbol");
	    	AssertValues(prices, "price");
	       	AssertOnlyProperties(new String[] {"symbol", "price"});
	    	ClearValues();

	    	statementString = "select symbol, volume from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
	    	AssertValues(symbols, "symbol");
	    	AssertValues(volumes, "volume");
	       	AssertOnlyProperties(new String[] {"symbol", "volume"});
	    	ClearValues();

	    	statementString = "select symbol, volume*2 from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
	    	AssertValues(symbols, "symbol");
	    	AssertValues(volumes, "(volume*2)");
	       	AssertOnlyProperties(new String[] {"symbol", "(volume*2)"});
	    	ClearValues();

	    	statementString = "select symbol, volume from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by symbol";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesBySymbol();
	    	AssertValues(symbols, "symbol");
	    	AssertValues(volumes, "volume");
	       	AssertOnlyProperties(new String[] {"symbol", "volume"});
	    	ClearValues();

	    	statementString = "select price from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by symbol, price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesBySymbolJoin();
	    	AssertValues(prices, "price");
	       	AssertOnlyProperties(new String[] {"price"});
	    	ClearValues();
	    }

	    [Test]
	    public void TestWildcard()
		{
			String statementString = "select * from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by price";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			AssertValues(symbols, "symbol");
			AssertValues(prices, "price");
			AssertValues(volumes, "volume");
		   	AssertOnlyProperties(new String[] {"symbol", "id", "volume", "price", "feed"});
			ClearValues();

			statementString = "select * from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"output every 6 events "  +
			"order by symbol";
			CreateAndSend(statementString);
			OrderValuesBySymbol();
			AssertValues(symbols, "symbol");
			AssertValues(prices, "price");
			AssertValues(volumes, "volume");
		   	AssertOnlyProperties(new String[] {"symbol", "volume", "price", "feed", "id"});
			ClearValues();
		}


	    [Test]
	    public void TestWildcardJoin()
	    {
	    	String statementString = "select * from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events " +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
	    	AssertSymbolsJoinWildCard();
	    	ClearValues();

	    	epService.Initialize();

	    	statementString = "select * from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"output every 6 events "  +
	    	"order by symbol, price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesBySymbolJoin();
	    	AssertSymbolsJoinWildCard();
	    	ClearValues();
	    }

	    [Test]
	    public void TestNoOutputClauseView()
	    {
			String statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:length(5) " +
			"order by price";
			CreateAndSend(statementString);
			symbols.Add("CAT");
			AssertValues(symbols, "symbol");
			ClearValues();
			SendEvent("FOX", 10);
			symbols.Add("FOX");
			AssertValues(symbols, "symbol");
			ClearValues();

			epService.Initialize();

			// Set manual clocking
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

			// Set start time
			SendTimeEvent(0);

			statementString = "select symbol from " +
			typeof(SupportMarketDataBean).FullName + ".win:time_batch(1 sec) " +
			"order by price";
			CreateAndSend(statementString);
			OrderValuesByPrice();
			SendTimeEvent(1000);
			AssertValues(symbols, "symbol");
		   	AssertOnlyProperties(new String[] {"symbol"});
			ClearValues();
	    }

	    [Test]
	    public void TestNoOutputClauseJoin()
	    {
	    	String statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:length(10) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"order by price";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
			symbols.Add("KGB");
			AssertValues(symbols, "symbol");
			ClearValues();
			SendEvent("DOG", 10);
			symbols.Add("DOG");
			AssertValues(symbols, "symbol");
			ClearValues();

			epService.Initialize();

			// Set manual clocking
			epService.EPRuntime.SendEvent(new TimerControlEvent(TimerControlEvent.ClockTypeEnum.CLOCK_EXTERNAL));

			// Set start time
			SendTimeEvent(0);

	    	statementString = "select symbol from " +
	    	typeof(SupportMarketDataBean).FullName + ".win:time_batch(1) as one, " +
	    	typeof(SupportBeanString).FullName + ".win:length(100) as two " +
	    	"where one.symbol = two.string " +
	    	"order by price, symbol";
	    	CreateAndSend(statementString);
	    	SendJoinEvents();
	    	OrderValuesByPriceJoin();
			SendTimeEvent(1000);
	    	AssertValues(symbols, "symbol");
	       	AssertOnlyProperties(new String[] {"symbol"});
	    	ClearValues();
	    }

		private void AssertOnlyProperties(IEnumerable<string> requiredProperties)
	    {
	    	EventBean[] events = testListener.LastNewData;
	    	if(events == null || events.Length == 0)
	    	{
	    		return;
	    	}
	    	EventType type = events[0].EventType;
	    	List<String> actualProperties = new List<String>(type.PropertyNames);
	    	log.Debug(".AssertOnlyProperties actualProperties=="+actualProperties);
		    Assert.IsTrue(CollectionHelper.ContainsAll(actualProperties, requiredProperties));
		    CollectionHelper.RemoveAll(actualProperties, requiredProperties);
	    	Assert.IsTrue(actualProperties.Count == 0);
	    }

		private void AssertSymbolsJoinWildCard()
	    {
	    	EventBean[] events = testListener.LastNewData;
	    	log.Debug(".AssertValues event type = " + events[0].EventType);
	    	log.Debug(".AssertValues values: " + symbols);
	    	log.Debug(".AssertValues events.length==" + events.Length);
	    	for(int i = 0; i < events.Length; i++)
	    	{
	    		SupportMarketDataBean _event = (SupportMarketDataBean)events[i]["one"];
                Assert.AreEqual(symbols[i], _event.Symbol);
	    	}
	    }

	    private void AssertValues<T>(IList<T> values, String valueName)
	    {
	    	EventBean[] events = testListener.LastNewData;
            Assert.AreEqual(values.Count, events.Length);
	    	log.Debug(".AssertValues values: " + values);
	    	for(int i = 0; i < events.Length; i++)
	    	{
	    		log.Debug(".AssertValues events["+i+"]=="+events[i][valueName]);
	    		Assert.AreEqual(values[i], events[i][valueName]);
	    	}
	    }

		private void ClearValues()
	    {
	    	prices.Clear();
	    	volumes.Clear();
	    	symbols.Clear();
	    }

		private void CreateAndSend(String statementString) {
			testListener = new SupportUpdateListener();
			EPStatement statement = epService.EPAdministrator.CreateEQL(statementString);
	    	statement.AddListener(testListener);
	    	SendEvent("IBM", 2);
	    	SendEvent("KGB", 1);
	    	SendEvent("CMU", 3);
	    	SendEvent("IBM", 6);
	    	SendEvent("CAT", 6);
	    	SendEvent("CAT", 5);
		}


		private void OrderValuesByPrice()
	    {
	    	symbols.Insert(0, "KGB");
	    	symbols.Insert(1, "IBM");
	    	symbols.Insert(2, "CMU");
	    	symbols.Insert(3, "CAT");
	    	symbols.Insert(4, "IBM");
	    	symbols.Insert(5, "CAT");
	    	prices.Insert(0, 1d);
	    	prices.Insert(1, 2d);
	    	prices.Insert(2, 3d);
	    	prices.Insert(3, 5d);
	    	prices.Insert(4, 6d);
	    	prices.Insert(5, 6d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

	    private void OrderValuesByPriceDesc()
	    {
	    	symbols.Insert(0, "IBM");
	    	symbols.Insert(1, "CAT");
	    	symbols.Insert(2, "CAT");
	    	symbols.Insert(3, "CMU");
	    	symbols.Insert(4, "IBM");
	    	symbols.Insert(5, "KGB");
	    	prices.Insert(0, 6d);
	    	prices.Insert(1, 6d);
	    	prices.Insert(2, 5d);
	    	prices.Insert(3, 3d);
	    	prices.Insert(4, 2d);
	    	prices.Insert(5, 1d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

		private void OrderValuesByPriceJoin()
	    {
	    	symbols.Insert(0, "KGB");
	    	symbols.Insert(1, "IBM");
	    	symbols.Insert(2, "CMU");
	    	symbols.Insert(3, "CAT");
	    	symbols.Insert(4, "CAT");
	    	symbols.Insert(5, "IBM");
	    	prices.Insert(0, 1d);
	    	prices.Insert(1, 2d);
	    	prices.Insert(2, 3d);
	    	prices.Insert(3, 5d);
	    	prices.Insert(4, 6d);
	    	prices.Insert(5, 6d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

	    private void OrderValuesByPriceSymbol()
	    {
	    	symbols.Insert(0, "KGB");
	    	symbols.Insert(1, "IBM");
	    	symbols.Insert(2, "CMU");
	    	symbols.Insert(3, "CAT");
	    	symbols.Insert(4, "CAT");
	    	symbols.Insert(5, "IBM");
	    	prices.Insert(0, 1d);
	    	prices.Insert(1, 2d);
	    	prices.Insert(2, 3d);
	    	prices.Insert(3, 5d);
	    	prices.Insert(4, 6d);
	    	prices.Insert(5, 6d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

		private void OrderValuesBySymbol()
	    {
	    	symbols.Insert(0, "CAT");
	    	symbols.Insert(1, "CAT");
	    	symbols.Insert(2, "CMU");
	    	symbols.Insert(3, "IBM");
	    	symbols.Insert(4, "IBM");
	    	symbols.Insert(5, "KGB");
	    	prices.Insert(0, 6d);
	    	prices.Insert(1, 5d);
	    	prices.Insert(2, 3d);
	    	prices.Insert(3, 2d);
	    	prices.Insert(4, 6d);
	    	prices.Insert(5, 1d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

	    private void OrderValuesBySymbolJoin()
	    {
	    	symbols.Insert(0, "CAT");
	    	symbols.Insert(1, "CAT");
	    	symbols.Insert(2, "CMU");
	    	symbols.Insert(3, "IBM");
	    	symbols.Insert(4, "IBM");
	    	symbols.Insert(5, "KGB");
	    	prices.Insert(0, 5d);
	    	prices.Insert(1, 6d);
	    	prices.Insert(2, 3d);
	    	prices.Insert(3, 2d);
	    	prices.Insert(4, 6d);
	    	prices.Insert(5, 1d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

		private void OrderValuesBySymbolPrice()
	    {
	    	symbols.Insert(0, "CAT");
	    	symbols.Insert(1, "CAT");
	    	symbols.Insert(2, "CMU");
	    	symbols.Insert(3, "IBM");
	    	symbols.Insert(4, "IBM");
	    	symbols.Insert(5, "KGB");
	    	prices.Insert(0, 5d);
	    	prices.Insert(1, 6d);
	    	prices.Insert(2, 3d);
	    	prices.Insert(3, 2d);
	    	prices.Insert(4, 6d);
	    	prices.Insert(5, 1d);
	    	volumes.Insert(0, 0l);
	    	volumes.Insert(1, 0l);
	    	volumes.Insert(2, 0l);
	    	volumes.Insert(3, 0l);
	    	volumes.Insert(4, 0l);
	    	volumes.Insert(5, 0l);
	    }

		private void SendEvent(String symbol, double price)
		{
		    SupportMarketDataBean bean = new SupportMarketDataBean(symbol, price, 0L, null);
		    epService.EPRuntime.SendEvent(bean);
		}

		private void SendTimeEvent(int millis)
		{
	        CurrentTimeEvent _event = new CurrentTimeEvent(millis);
	        epService.EPRuntime.SendEvent(_event);
		}

		private void SendJoinEvents()
		{
			epService.EPRuntime.SendEvent(new SupportBeanString("CAT"));
			epService.EPRuntime.SendEvent(new SupportBeanString("IBM"));
			epService.EPRuntime.SendEvent(new SupportBeanString("CMU"));
			epService.EPRuntime.SendEvent(new SupportBeanString("KGB"));
			epService.EPRuntime.SendEvent(new SupportBeanString("DOG"));
		}

	}
} // End of namespace
