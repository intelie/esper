using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;
using net.esper.view.window;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.view
{

	[TestFixture]
    public class TestViewServiceHelper 
    {
        private static readonly Type TEST_CLASS;

        private SupportSchemaNeutralView top;
        private SupportSchemaNeutralView child_1;
        private SupportSchemaNeutralView child_2;
        private SupportSchemaNeutralView child_2_1;
        private SupportSchemaNeutralView child_2_2;
        private SupportSchemaNeutralView child_2_1_1;
        private SupportSchemaNeutralView child_2_2_1;
        private SupportSchemaNeutralView child_2_2_2;

        [SetUp]
        public virtual void setUp()
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
        public virtual void testInstantiateChain()
        {
            List<View> existingParentViews = new List<View>();

            SupportBeanClassView topView = new SupportBeanClassView(TEST_CLASS);
        		IList<ViewSpec> specifications = SupportViewSpecFactory.makeSpecListOne();
            ViewServiceContext context = SupportViewContextFactory.makeContext();

            // Check correct views created
						IList<View> views = ViewServiceHelper.InstantiateChain(existingParentViews, topView, specifications, context);

            Assert.AreEqual(3, views.Count);
            Assert.AreEqual(ViewEnum.LENGTH_WINDOW.Clazz, views[0].GetType());
            Assert.AreEqual(ViewEnum.UNIVARIATE_STATISTICS.Clazz, views[1].GetType());
            Assert.AreEqual(ViewEnum.LAST_EVENT.Clazz, views[2].GetType());

            // Check that the context is set
            specifications = SupportViewSpecFactory.makeSpecListFive();
            views = ViewServiceHelper.InstantiateChain(existingParentViews, topView, specifications, context);
            TimeWindowView timeWindow = (TimeWindowView)views[0];
            Assert.AreEqual(context, timeWindow.ViewServiceContext);
        }

        [Test]
        public virtual void testMatch()
        {
            SupportStreamImpl stream = new SupportStreamImpl(TEST_CLASS, 10);
            IList<ViewSpec> specifications = SupportViewSpecFactory.makeSpecListOne();
			EDictionary<View, ViewSpec> repository = new EHashDictionary<View, ViewSpec>();

            // No views under stream, no matches
            Pair<Viewable, IList<View>> result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);
            Assert.AreEqual(stream, result.First);
            Assert.AreEqual(3, specifications.Count);
            Assert.AreEqual(0, result.Second.Count);

            // One top view under the stream that doesn't match
            SupportBeanClassView testView = new SupportBeanClassView(TEST_CLASS);
            repository.Put(testView, SupportViewSpecFactory.makeSpec("std", "size", null, null));
            stream.AddView(testView);
            result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);

            Assert.AreEqual(stream, result.First);
            Assert.AreEqual(3, specifications.Count);
            Assert.AreEqual(0, result.Second.Count);

            // Another top view under the stream that doesn't matches again
            testView = new SupportBeanClassView(TEST_CLASS);
            repository.Put(testView, specifications[1]);
            stream.AddView(testView);
            result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);

            Assert.AreEqual(stream, result.First);
            Assert.AreEqual(3, specifications.Count);
            Assert.AreEqual(0, result.Second.Count);

            // One top view under the stream that does actually match
            SupportBeanClassView matchViewOne = new SupportBeanClassView(TEST_CLASS);
            repository.Put(matchViewOne, specifications[0]);
            stream.AddView(matchViewOne);
            result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);

            Assert.AreEqual(matchViewOne, result.First);
            Assert.AreEqual(2, specifications.Count);
            Assert.AreEqual(1, result.Second.Count);
            Assert.AreEqual(matchViewOne, result.Second[0]);

            // One child view under the top veiew that does not match
            testView = new SupportBeanClassView(TEST_CLASS);
            specifications = SupportViewSpecFactory.makeSpecListOne();
            repository.Put(testView, specifications[2]);
            matchViewOne.AddView(testView);
            result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);
            Assert.AreEqual(1, result.Second.Count);
            Assert.AreEqual(matchViewOne, result.Second[0]);

            Assert.AreEqual(matchViewOne, result.First);
            Assert.AreEqual(2, specifications.Count);

            // Add child view under the top veiw that does match
            SupportBeanClassView matchViewTwo = new SupportBeanClassView(TEST_CLASS);
            specifications = SupportViewSpecFactory.makeSpecListOne();
            repository.Put(matchViewTwo, specifications[1]);
            matchViewOne.AddView(matchViewTwo);
            result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);

            Assert.AreEqual(matchViewTwo, result.First);
            Assert.AreEqual(1, specifications.Count);

            // Add ultimate child view under the child view that does match
            SupportBeanClassView matchViewThree = new SupportBeanClassView(TEST_CLASS);
            specifications = SupportViewSpecFactory.makeSpecListOne();
            repository.Put(matchViewThree, specifications[2]);
            matchViewTwo.AddView(matchViewThree);
            result = ViewServiceHelper.MatchExistingViews(stream, repository, specifications);

            Assert.AreEqual(matchViewThree, result.First);
            Assert.AreEqual(0, specifications.Count);
        }

        [Test]
        public virtual void testAddMergeViews()
        {
            IList<ViewSpec> specOne = SupportViewSpecFactory.makeSpecListOne();

            ViewServiceHelper.AddMergeViews(specOne);
            Assert.AreEqual(3, specOne.Count);

            IList<ViewSpec> specFour = SupportViewSpecFactory.makeSpecListTwo();
            ViewServiceHelper.AddMergeViews(specFour);
            Assert.AreEqual(2, specFour.Count);
            Assert.AreEqual("merge", specFour[1].ObjectName);
            Assert.AreEqual(specFour[0].ObjectParameters.Count, specFour[0].ObjectParameters.Count);
        }

        [Test]
        public virtual void testRemoveChainLeafView()
        {
            // Remove a non-leaf, expect no removals
            IList<View> removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_2);
            Assert.AreEqual(0, removedViews.Count);
            Assert.AreEqual(2, child_2.GetViews().Count);

            // Remove the whole tree child-by-child
            removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_2_2);
            Assert.AreEqual(1, removedViews.Count);
            Assert.AreEqual(child_2_2_2, removedViews[0]);
            Assert.AreEqual(2, child_2.GetViews().Count);

            removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_2_1);
            Assert.AreEqual(2, removedViews.Count);
            Assert.AreEqual(child_2_2_1, removedViews[0]);
            Assert.AreEqual(child_2_2, removedViews[1]);
            Assert.AreEqual(1, child_2.GetViews().Count);

            removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_1);
            Assert.AreEqual(1, removedViews.Count);
            Assert.AreEqual(child_1, removedViews[0]);

            removedViews = ViewServiceHelper.RemoveChainLeafView(top, child_2_1_1);
            Assert.AreEqual(3, removedViews.Count);
            Assert.AreEqual(child_2_1_1, removedViews[0]);
            Assert.AreEqual(child_2_1, removedViews[1]);
            Assert.AreEqual(child_2, removedViews[2]);

            Assert.AreEqual(0, child_2.GetViews().Count);
            Assert.AreEqual(0, top.GetViews().Count);
        }
 
        static TestViewServiceHelper()
        {
            TEST_CLASS = typeof(SupportMarketDataBean);
        }
    }
}
