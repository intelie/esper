// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

namespace net.esper.support.bean
{
	public class SupportPriceEvent
	{
	    public int Price
	    {
	        get { return price; }
	        set { price = value; }
	    }

	    private int price;

        public string Sym
	    {
	        get { return sym; }
	        set { sym = value; }
	    }

	    private String sym;

	    public SupportPriceEvent(int price, String sym) {
	        this.price = price;
	        this.sym = sym;
	    }


	}
} // End of namespace
