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

using net.esper.support.view;
using net.esper.view.std;
using net.esper.view;

namespace net.esper.view.window
{
	[TestFixture]
	public class TestLengthBatchViewFactory
	{
	    private LengthBatchViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new LengthBatchViewFactory();
	    }

	    [Test]
	    public void TestSetParameters()
	    {
	        TryParameter(new Object[] {Int16.Parse("10")}, 10);
	        TryParameter(new Object[] {100}, 100);

	        TryInvalidParameter("price");
	        TryInvalidParameter(true);
	        TryInvalidParameter(1.1d);
	        TryInvalidParameter(0);
	        TryInvalidParameter(1000L);
	    }

	    [Test]
	    public void TestCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {1000});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new LengthBatchView(factory, 1, null)));
	        Assert.IsTrue(factory.CanReuse(new LengthBatchView(factory, 1000, null)));
	    }

	    private void TryInvalidParameter(Object param)
	    {
	        try
	        {
	            factory.SetViewParameters(null, new Object[] {param});
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] param, int size)
	    {
	        LengthBatchViewFactory factory = new LengthBatchViewFactory();
	        factory.SetViewParameters(null, param);
	        LengthBatchView view = (LengthBatchView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(size, view.Count);
	    }
	}
} // End of namespace
