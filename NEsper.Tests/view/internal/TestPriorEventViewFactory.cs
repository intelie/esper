// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.support.eql;
using net.esper.view.internals;
using net.esper.view;

namespace net.esper.view.internals
{
	[TestFixture]
	public class TestPriorEventViewFactory
	{
	    private PriorEventViewFactory factoryOne;
	    private PriorEventViewFactory factoryTwo;
	    private SupportViewResourceCallback callbackOne;
	    private SupportViewResourceCallback callbackTwo;

	    [SetUp]
	    public void SetUp()
	    {
	        factoryOne = new PriorEventViewFactory(false);
	        factoryTwo = new PriorEventViewFactory(true);
	        callbackOne = new SupportViewResourceCallback();
	        callbackTwo = new SupportViewResourceCallback();
	    }

	    [Test]
	    public void TestMakeView()
	    {
	        factoryOne.SetProvideCapability(new ViewCapPriorEventAccess(1), callbackOne);
	        factoryOne.SetProvideCapability(new ViewCapPriorEventAccess(2), callbackOne);
	        PriorEventView view = (PriorEventView) factoryOne.MakeView(null);
	        Assert.IsTrue(view.Buffer is PriorEventBufferMulti);
	        Assert.AreEqual(2, callbackOne.Resources.Count);

	        factoryTwo.SetProvideCapability(new ViewCapPriorEventAccess(1), callbackTwo);
	        view = (PriorEventView) factoryTwo.MakeView(null);
	        Assert.IsTrue(view.Buffer is PriorEventBufferUnbound);
	        Assert.AreEqual(1, callbackTwo.Resources.Count);
	    }

	    [Test]
	    public void TestSetParameters()
	    {
	        try
	        {
	            factoryOne.SetViewParameters(null, null);
	            Assert.Fail();
	        }
	        catch (UnsupportedOperationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void TestCanReuse()
	    {
	        Assert.IsFalse(factoryOne.CanReuse(null));
	    }
	}
} // End of namespace
