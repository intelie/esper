using System;

using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.view;

using NUnit.Core;
using NUnit.Framework;

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
		public virtual void  setUp()
		{
			streamOne = new SupportStreamImpl(typeof(SupportMarketDataBean), 1);
			streamTwo = new SupportStreamImpl(typeof(SupportBean_A), 1);
			
			viewService = new ViewServiceImpl();
			
			ViewServiceContext context = SupportViewContextFactory.makeContext();
			
			viewOne = viewService.CreateView(streamOne, SupportViewSpecFactory.makeSpecListOne(), context);
			viewTwo = viewService.CreateView(streamOne, SupportViewSpecFactory.makeSpecListTwo(), context);
			viewThree = viewService.CreateView(streamOne, SupportViewSpecFactory.makeSpecListThree(), context);
			viewFour = viewService.CreateView(streamOne, SupportViewSpecFactory.makeSpecListFour(), context);
			viewFive = viewService.CreateView(streamTwo, SupportViewSpecFactory.makeSpecListFive(), context);
		}
		
		[Test]
		public virtual void  testCheckChainReuse()
		{
			// Child views of first and second level must be the same
			Assert.AreEqual(2, streamOne.GetViews().Count);
			View child1_1 = streamOne.GetViews()[0];
			View child2_1 = streamOne.GetViews()[0];
			Assert.IsTrue(child1_1 == child2_1);
			
			Assert.AreEqual(2, child1_1.GetViews().Count);
			View child1_1_1 = child1_1.GetViews()[0];
			View child2_1_1 = child2_1.GetViews()[0];
			Assert.IsTrue(child1_1_1 == child2_1_1);
			
			Assert.AreEqual(2, child1_1_1.GetViews().Count);
			Assert.AreEqual(2, child2_1_1.GetViews().Count);
			Assert.IsTrue(child2_1_1.GetViews()[0] != child2_1_1.GetViews()[0]);
			
			// Create one more view chain
			View child3_1 = streamOne.GetViews()[0];
			Assert.IsTrue(child3_1 == child1_1);
			Assert.AreEqual(2, child3_1.GetViews().Count);
			View child3_1_1 = child3_1.GetViews()[1];
			Assert.IsTrue(child3_1_1 != child2_1_1);
		}
		
		[Test]
		public virtual void  testRemove()
		{
			Assert.AreEqual(2, streamOne.GetViews().Count);
			Assert.AreEqual(1, streamTwo.GetViews().Count);
			
			viewService.Remove(streamOne, viewOne);
			viewService.Remove(streamOne, viewTwo);
			viewService.Remove(streamOne, viewThree);
			viewService.Remove(streamOne, viewFour);
			
			viewService.Remove(streamTwo, viewFive);
			
			Assert.AreEqual(0, streamOne.GetViews().Count);
			Assert.AreEqual(0, streamTwo.GetViews().Count);
		}
		
		[Test]
		public virtual void  testRemoveInvalid()
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
		
		[Test]
		public virtual void  testInvalid()
		{
			try
			{
				// Event doesn't have the right property
				viewFive = viewService.CreateView(streamTwo, SupportViewSpecFactory.makeSpecListOne(), null);
				Assert.Fail();
			}
			catch (ViewProcessingException ex)
			{
				// Expected
			}
		}
	}
}
