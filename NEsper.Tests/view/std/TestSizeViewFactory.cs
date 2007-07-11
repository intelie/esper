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
using net.esper.view;

namespace net.esper.view.std
{
	[TestFixture]
	public class TestSizeViewFactory
	{
	    private SizeViewFactory factory;

	    [SetUp]
	    public void SetUp()
	    {
	        factory = new SizeViewFactory();
	    }

	    [Test]
	    public void testSetParameters()
	    {
	        TryParameter(new Object[] {});
	        TryInvalidParameter(1.1d);
	    }

	    [Test]
	    public void testCanReuse()
	    {
	        Assert.IsFalse(factory.CanReuse(new LastElementView()));
	        Assert.IsTrue(factory.CanReuse(new SizeView(SupportStatementContextFactory.MakeContext())));
	    }

	    private void TryInvalidParameter(Object param)
	    {
	        try
	        {
	            SizeViewFactory factory = new SizeViewFactory();
	            factory.SetViewParameters(null, new Object[] {param});
	            Assert.Fail();
	        }
	        catch (ViewParameterException ex)
	        {
	            // expected
	        }
	    }

	    private void TryParameter(Object[] param)
	    {
	        SizeViewFactory factory = new SizeViewFactory();
	        factory.SetViewParameters(null, param);
	        Assert.IsTrue(factory.MakeView(SupportStatementContextFactory.MakeContext()) is SizeView);
	    }
	}
} // End of namespace
