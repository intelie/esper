// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using net.esper.adapter;
using net.esper.core;
using net.esper.compat;

namespace net.esper.support.adapter
{
	public class SupportAdapterLoader : AdapterLoader
	{
	    private static IList<String> names = new List<String>();
	    private static IList<Properties> props = new List<Properties>();

	    public static IList<Properties> Props
	    {
            get { return props; }
	    }

	    public static IList<String> Names
	    {
	        get { return names; }
	    }

	    public void Destroy()
	    {
	        //To change body of implemented methods use File | Settings | File Templates.
	    }

	    public void Init(String name, Properties properties, EPServiceProviderSPI epService)
	    {
	        names.Add(name);
	        props.Add(properties);
	    }
	}
} // End of namespace
