using System;

using net.esper.dispatch;
using net.esper.events;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.view.internals;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join
{
	
	[TestFixture]
	public class TestJoinExecStrategyDispatchable 
	{
		private JoinExecStrategyDispatchable dispatchable;
		private DispatchService dispatchService;
		private BufferView bufferViewOne;
		private BufferView bufferViewTwo;
		private SupportJoinExecutionStrategy joinExecutionStrategy;
		
		[SetUp]
		public virtual void  setUp()
		{
			dispatchService = new DispatchServiceImpl();
			
			bufferViewOne = new BufferView(0);
			bufferViewTwo = new BufferView(1);
			
			joinExecutionStrategy = new SupportJoinExecutionStrategy();
			
			this.dispatchable = new JoinExecStrategyDispatchable(dispatchService, joinExecutionStrategy, 2);
			
			bufferViewOne.Observer = dispatchable;
			bufferViewTwo.Observer = dispatchable;
		}
		
		[Test]
		public virtual void  testFlow()
		{
			EventBean[] oldDataOne = SupportEventBeanFactory.MakeEvents(new String[]{"a"});
			EventBean[] newDataOne = SupportEventBeanFactory.MakeEvents(new String[]{"b"});
			EventBean[] oldDataTwo = SupportEventBeanFactory.MakeEvents(new String[]{"c"});
			EventBean[] newDataTwo = SupportEventBeanFactory.MakeEvents(new String[]{"d"});
			
			bufferViewOne.Update(newDataOne, oldDataOne);
			dispatchService.Dispatch();
			Assert.AreEqual(1, joinExecutionStrategy.LastNewDataPerStream[0].Length);
			Assert.AreSame(newDataOne[0], joinExecutionStrategy.LastNewDataPerStream[0][0]);
			Assert.AreSame(oldDataOne[0], joinExecutionStrategy.LastOldDataPerStream[0][0]);
			Assert.IsNull(joinExecutionStrategy.LastNewDataPerStream[1]);
			Assert.IsNull(joinExecutionStrategy.LastOldDataPerStream[1]);

            bufferViewOne.Update(newDataTwo, oldDataTwo);
            bufferViewTwo.Update(newDataOne, oldDataOne);
			dispatchService.Dispatch();
			Assert.AreEqual(1, joinExecutionStrategy.LastNewDataPerStream[0].Length);
			Assert.AreEqual(1, joinExecutionStrategy.LastNewDataPerStream[1].Length);
			Assert.AreSame(newDataTwo[0], joinExecutionStrategy.LastNewDataPerStream[0][0]);
			Assert.AreSame(oldDataTwo[0], joinExecutionStrategy.LastOldDataPerStream[0][0]);
			Assert.AreSame(newDataOne[0], joinExecutionStrategy.LastNewDataPerStream[1][0]);
			Assert.AreSame(oldDataOne[0], joinExecutionStrategy.LastOldDataPerStream[1][0]);
		}
	}
}
