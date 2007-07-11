// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.support.bean;
using net.esper.support.events;
using net.esper.view.std;
using net.esper.view.window;
using net.esper.view;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestViewFactoryDelegateImpl
	{
	    private ViewResourceDelegate dg;

	    [SetUp]
	    public void SetUp()
	    {
	        ViewFactoryChain[] factories = new ViewFactoryChain[2];

	        ViewFactory factory1 = new TimeWindowViewFactory();
	        ViewFactory factory2 = new SizeViewFactory();
	        factories[0] = new ViewFactoryChain(SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)),
	                new ViewFactory[] {factory1, factory2});
	        factories[1] = new ViewFactoryChain(SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)),
	                new ViewFactory[] {factory1});

	        dg = new ViewResourceDelegateImpl(factories);
	    }

	    private class MyViewResourceCallback : ViewResourceCallback
	    {
			public object ViewResource
			{
				set {}
			}
	    }

	    [Test]
	    public void testRequest()
	    {
	        ViewResourceCallback callback = new MyViewResourceCallback();

	        Assert.IsFalse(dg.RequestCapability(1, new SupportViewCapability(), callback));
	        Assert.IsFalse(dg.RequestCapability(0, new ViewCapDataWindowAccess(1), callback));
	        Assert.IsTrue(dg.RequestCapability(1, new ViewCapDataWindowAccess(1), callback));
	    }

	    private class SupportViewCapability : ViewCapability
	    {
	        public bool Inspect(IList<ViewFactory> viewFactories)
	        {
	            return true;
	        }
	    }
	}

} // End of namespace
