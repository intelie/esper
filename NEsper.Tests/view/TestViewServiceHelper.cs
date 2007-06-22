///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.core;
using net.esper.eql.spec;
using net.esper.support.bean;
using net.esper.support.view;
using net.esper.view.stat;
using net.esper.view.std;
using net.esper.view.window;

namespace net.esper.view
{
	[TestFixture]
	public class TestViewServiceHelper
	{
	    private readonly static Type TEST_CLASS = typeof(SupportMarketDataBean);

	    private SupportSchemaNeutralView top;
	    private SupportSchemaNeutralView child_1;
	    private SupportSchemaNeutralView child_2;
	    private SupportSchemaNeutralView child_2_1;
	    private SupportSchemaNeutralView child_2_2;
	    private SupportSchemaNeutralView child_2_1_1;
	    private SupportSchemaNeutralView child_2_2_1;
	    private SupportSchemaNeutralView child_2_2_2;

	    [SetUp]
	    public void SetUp()
	    {
	        top = new SupportSchemaNeutralView("top");

	        child_1 = new SupportSchemaNeutralView("1");
	        child_2 = new SupportSchemaNeutralView("2");
	        top.AddView(child_1);
	        top.AddView(child_2);

	        child_2_1 = new SupportSchemaNeutralView("2_1");
	        child_2_2 = new SupportSchemaNeutralView("2_2");
	        child_2.AddView(child_2_1);
	        child_2.AddView(child_2_2);

	        child_2_1_1 = new SupportSchemaNeutralView("2_1_1");
	        child_2_2_1 = new SupportSchemaNeutralView("2_2_1");
	        child_2_2_2 = new SupportSchemaNeutralView("2_2_2");
	        child_2_1.AddView(child_2_1_1);
	        child_2_2.AddView(child_2_2_1);
	        child_2_2.AddView(child_2_2_2);
	    }

	    [Test]
	    public void TestInstantiateChain()
	    {
	        SupportBeanClassView topView = new SupportBeanClassView(TEST_CLASS);
	        IList<ViewFactory> viewFactories = SupportViewSpecFactory.MakeFactoryListOne(topView.EventType);
	        StatementContext context = SupportStatementContextFactory.MakeContext();

	        // Check correct views created
	        IList<View> views = ViewServiceHelper.InstantiateChain(topView, viewFactories, context);

	        Assert.AreEqual(3, views.Count);
	        Assert.AreEqual(typeof(LengthWindowView), views[0].GetType());
	        Assert.AreEqual(typeof(UnivariateStatisticsView), views[1].GetType());
	        Assert.AreEqual(typeof(LastElementView), views[2].GetType());

	        // Check that the context is set
	        viewFactories = SupportViewSpecFactory.MakeFactoryListFive(topView.EventType);
	        views = ViewServiceHelper.InstantiateChain(topView, viewFactories, context);
	        TimeWindowView timeWindow = (TimeWindowView) views[0];
	    }

	    [Test]
	    public void TestMatch()
	    {
	        SupportStreamImpl stream = new SupportStreamImpl(TEST_CLASS, 10);
            IList<ViewFactory> viewFactories = SupportViewSpecFactory.MakeFactoryListOne(stream.EventType);

	        // No views under stream, no matches
	        Pair<Viewable, IList<View>> result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);
	        Assert.AreEqual(stream, result.First);
	        Assert.AreEqual(3, viewFactories.Count);
	        Assert.AreEqual(0, result.Second.Count);

	        // One top view under the stream that doesn't match
	        SupportBeanClassView testView = new SupportBeanClassView(TEST_CLASS);
	        stream.AddView(new SizeView(SupportStatementContextFactory.MakeContext()));
	        result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);

	        Assert.AreEqual(stream, result.First);
	        Assert.AreEqual(3, viewFactories.Count);
	        Assert.AreEqual(0, result.Second.Count);

	        // Another top view under the stream that doesn't matche again
	        testView = new SupportBeanClassView(TEST_CLASS);
	        stream.AddView(new LengthWindowView(null, 999, null));
	        result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);

	        Assert.AreEqual(stream, result.First);
	        Assert.AreEqual(3, viewFactories.Count);
	        Assert.AreEqual(0, result.Second.Count);

	        // One top view under the stream that does actually match
	        LengthWindowView myLengthWindowView = new LengthWindowView(null, 1000, null);
	        stream.AddView(myLengthWindowView);
	        result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);

	        Assert.AreEqual(myLengthWindowView, result.First);
	        Assert.AreEqual(2, viewFactories.Count);
	        Assert.AreEqual(1, result.Second.Count);
	        Assert.AreEqual(myLengthWindowView, result.Second[0]);

	        // One child view under the top view that does not match
	        testView = new SupportBeanClassView(TEST_CLASS);
	        viewFactories = SupportViewSpecFactory.MakeFactoryListOne(stream.EventType);
	        myLengthWindowView.AddView(new UnivariateStatisticsView(SupportStatementContextFactory.MakeContext(), "volume"));
	        result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);
	        Assert.AreEqual(1, result.Second.Count);
	        Assert.AreEqual(myLengthWindowView, result.Second[0]);
	        Assert.AreEqual(myLengthWindowView, result.First);
	        Assert.AreEqual(2, viewFactories.Count);

	        // Add child view under the top view that does match
	        viewFactories = SupportViewSpecFactory.MakeFactoryListOne(stream.EventType);
	        UnivariateStatisticsView myUnivarView = new UnivariateStatisticsView(SupportStatementContextFactory.MakeContext(), "price");
	        myLengthWindowView.AddView(myUnivarView);
	        result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);

	        Assert.AreEqual(myUnivarView, result.First);
	        Assert.AreEqual(1, viewFactories.Count);

	        // Add ultimate child view under the child view that does match
	        viewFactories = SupportViewSpecFactory.MakeFactoryListOne(stream.EventType);
	        LastElementView lastElementView = new LastElementView();
	        myUnivarView.AddView(lastElementView);
	        result = ViewServiceHelper.MatchExistingViews(stream, viewFactories);

	        Assert.AreEqual(lastElementView, result.First);
	        Assert.AreEqual(0, viewFactories.Count);
	    }

	    [Test]
	    public void TestAddMergeViews()
	    {
            IList<ViewSpec> specOne = SupportViewSpecFactory.MakeSpecListOne();

	        ViewServiceHelper.AddMergeViews(specOne);
	        Assert.AreEqual(3, specOne.Count);

            IList<ViewSpec> specFour = SupportViewSpecFactory.MakeSpecListTwo();
	        ViewServiceHelper.AddMergeViews(specFour);
	        Assert.AreEqual(2, specFour.Count);
	        Assert.AreEqual("merge", specFour[1].ObjectName);
	        Assert.AreEqual(specFour[0].ObjectParameters.Count, specFour[1].ObjectParameters.Count);
	    }

	    [Test]
	    public void TestRemoveChainLeafView()
	    {
	        // Remove a non-leaf, expect no removals
	        IList<View> removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_2);
	        Assert.AreEqual(0, removedViews.Count);
	        Assert.AreEqual(2, child_2.Views.Count);

	        // Remove the whole tree child-by-child
	        removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_2_2);
	        Assert.AreEqual(1, removedViews.Count);
	        Assert.AreEqual(child_2_2_2, removedViews[0]);
	        Assert.AreEqual(2, child_2.Views.Count);

	        removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_2_1);
	        Assert.AreEqual(2, removedViews.Count);
	        Assert.AreEqual(child_2_2_1, removedViews[0]);
	        Assert.AreEqual(child_2_2, removedViews[1]);
	        Assert.AreEqual(1, child_2.Views.Count);

	        removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_1);
	        Assert.AreEqual(1, removedViews.Count);
	        Assert.AreEqual(child_1, removedViews[0]);

	        removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_1_1);
	        Assert.AreEqual(3, removedViews.Count);
	        Assert.AreEqual(child_2_1_1, removedViews[0]);
	        Assert.AreEqual(child_2_1, removedViews[1]);
	        Assert.AreEqual(child_2, removedViews[2]);

	        Assert.AreEqual(0, child_2.Views.Count);
	        Assert.AreEqual(0, top.Views.Count);
	    }
	}
} // End of namespace
