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
	public class TestLengthWindowViewFactory
	{
	    private LengthWindowViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new LengthWindowViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {10}, 10);

	        TryInvalidParameter("price");
	        TryInvalidParameter(true);
	        TryInvalidParameter(1.1d);
	        TryInvalidParameter(0);
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        factory.SetViewParameters(null, new Object[] {1000});
	        Assert.IsFalse(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	        Assert.IsFalse(factory.CanReuse(new LengthWindowView(factory, 1, null)));
	        Assert.IsTrue(factory.CanReuse(new LengthWindowView(factory, 1000, null)));
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
	        LengthWindowViewFactory factory = new LengthWindowViewFactory();
	        factory.SetViewParameters(null, param);
	        LengthWindowView view = (LengthWindowView) factory.MakeView(SupportStatementContextFactory.MakeContext());
	        Assert.AreEqual(size, view.Size);
	    }
	}
} // End of namespace
