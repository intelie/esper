///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.core;
using net.esper.support.bean;
using net.esper.support.view;

namespace net.esper.view
{
	[TestFixture]
	public class TestViewServiceImpl
	{
	    private ViewServiceImpl viewService;

	    private Viewable viewOne;
	    private Viewable viewTwo;
	    private Viewable viewThree;
	    private Viewable viewFour;
	    private Viewable viewFive;

	    private EventStream streamOne;
	    private EventStream streamTwo;

	    [SetUp]
	    public void SetUp()
	    {
	        streamOne = new SupportStreamImpl(typeof(SupportMarketDataBean), 1);
	        streamTwo = new SupportStreamImpl(typeof(SupportBean_A), 1);

	        viewService = new ViewServiceImpl();

	        StatementContext context = SupportStatementContextFactory.MakeContext();

	        viewOne = viewService.CreateViews(streamOne, SupportViewSpecFactory.MakeFactoryListOne(streamOne.EventType), context);
	        viewTwo = viewService.CreateViews(streamOne, SupportViewSpecFactory.MakeFactoryListTwo(streamOne.EventType), context);
	        viewThree = viewService.CreateViews(streamOne, SupportViewSpecFactory.MakeFactoryListThree(streamOne.EventType), context);
	        viewFour = viewService.CreateViews(streamOne, SupportViewSpecFactory.MakeFactoryListFour(streamOne.EventType), context);
	        viewFive = viewService.CreateViews(streamTwo, SupportViewSpecFactory.MakeFactoryListFive(streamTwo.EventType), context);
	    }

	    [Test]
	    public void testCheckChainReuse()
	    {
	        // Child views of first and second level must be the same
	        Assert.AreEqual(2, streamOne.Views.Count);
	        View child1_1 = streamOne.Views[0];
	        View child2_1 = streamOne.Views[0];
	        Assert.IsTrue(child1_1 == child2_1);

	        Assert.AreEqual(2, child1_1.Views.Count);
	        View child1_1_1 = child1_1.Views[0];
	        View child2_1_1 = child2_1.Views[0];
	        Assert.IsTrue(child1_1_1 == child2_1_1);

	        Assert.AreEqual(2, child1_1_1.Views.Count);
	        Assert.AreEqual(2, child2_1_1.Views.Count);
	        Assert.IsTrue(child2_1_1.Views[0] != child2_1_1.Views[1]);

	        // Create one more view chain
	        View child3_1 = streamOne.Views[0];
	        Assert.IsTrue(child3_1 == child1_1);
	        Assert.AreEqual(2, child3_1.Views.Count);
	        View child3_1_1 = child3_1.Views[1];
	        Assert.IsTrue(child3_1_1 != child2_1_1);
	    }

	    [Test]
	    public void testRemove()
	    {
	        Assert.AreEqual(2, streamOne.Views.Count);
	        Assert.AreEqual(1, streamTwo.Views.Count);

	        viewService.Remove(streamOne, viewOne);
	        viewService.Remove(streamOne, viewTwo);
	        viewService.Remove(streamOne, viewThree);
	        viewService.Remove(streamOne, viewFour);

	        viewService.Remove(streamTwo, viewFive);

	        Assert.AreEqual(0, streamOne.Views.Count);
	        Assert.AreEqual(0, streamTwo.Views.Count);
	    }

	    [Test]
	    public void testRemoveInvalid()
	    {
	        try
	        {
	            viewService.Remove(streamOne, viewOne);
	            viewService.Remove(streamOne, viewOne);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected
	        }
	    }
	}
} // End of namespace
