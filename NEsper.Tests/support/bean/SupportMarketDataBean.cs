///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.IO;

namespace net.esper.support.bean
{
    [Serializable]
	public class SupportMarketDataBean
	{
        private readonly String symbol;
        private readonly String id;
        private readonly double price;
	    private readonly long? volume;
        private readonly String feed;

	    public SupportMarketDataBean(String symbol, double price, long? volume, String feed)
	    {
	        this.symbol = symbol;
	        this.price = price;
	        this.volume = volume;
	        this.feed = feed;
	    }

	    public SupportMarketDataBean(String symbol, String id, double price)
	    {
	        this.symbol = symbol;
	        this.id = id;
	        this.price = price;
	    }

	    public String Symbol
	    {
	    	get { return symbol; }
	    }

	    public double Price
	    {
	    	get { return price; }
	    }

	    public long? Volume
	    {
	    	get { return volume; }
	    }

	    public String Feed
	    {
	    	get { return feed; }
	    }

	    public String Id
	    {
	    	get { return id; }
	    }

	    public override String ToString()
	    {
	        return "SupportMarketDataBean " +
	               "symbol=" + symbol +
	               " price=" + price +
	               " volume=" + volume +
	               " feed=" + feed;
	    }
	}
} // End of namespace
