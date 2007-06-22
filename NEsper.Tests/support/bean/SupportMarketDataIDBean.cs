// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.IO;

namespace net.esper.support.bean
{
	[Serializable]
	public class SupportMarketDataIDBean
	{
	    private String symbol;
	    private String id;
	    private double price;

	    public SupportMarketDataIDBean(String symbol, String id, double price)
	    {
	        this.symbol = symbol;
	        this.id = id;
	        this.price = price;
	    }

	    public String GetSymbol()
	    {
	        return symbol;
	    }

	    public double GetPrice()
	    {
	        return price;
	    }

	    public String GetId()
	    {
	        return id;
	    }
	}
} // End of namespace
