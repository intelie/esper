using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	
	[TestFixture]
	public class TestSelectExprJoinWildcardProcessor 
	{
		private SelectExprJoinWildcardProcessor processor;
		
		[SetUp]
		public virtual void  setUp()
		{
			SupportStreamTypeSvc3Stream supportTypes = new SupportStreamTypeSvc3Stream();
			processor = new SelectExprJoinWildcardProcessor(supportTypes.StreamNames, supportTypes.EventTypes, SupportEventAdapterService.Service);
		}
		
		[Test]
		public virtual void  testProcess()
		{
			EventBean[] testEvents = SupportStreamTypeSvc3Stream.SampleEvents;
			
			EventBean result = processor.Process(testEvents);
			Assert.AreEqual(testEvents[0].Underlying, result["s0"]);
			Assert.AreEqual(testEvents[1].Underlying, result["s1"]);
			
			// Test null events, such as in an outer join
			testEvents[1] = null;
			result = processor.Process(testEvents);
			Assert.AreEqual(testEvents[0].Underlying, result["s0"]);
			Assert.IsNull(result["s1"]);
		}
		
		[Test]
		public virtual void  testType()
		{
			Assert.AreEqual(typeof(SupportBean), processor.ResultEventType.GetPropertyType("s0"));
			Assert.AreEqual(typeof(SupportBean), processor.ResultEventType.GetPropertyType("s1"));
		}
	}
}
