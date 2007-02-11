using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestFilterSpec 
	{
		private EventType eventType;
		
		[SetUp]
		public virtual void  setUp()
		{
			eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
		}
		
		[Test]
		public virtual void  testHashCode()
		{
			FilterSpec spec = SupportFilterSpecBuilder.build(eventType, new Object[]{"intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3});
			int expectedHash = eventType.GetHashCode() ^ "intPrimitive".GetHashCode() ^ "intBoxed".GetHashCode();
			Assert.AreEqual(expectedHash, spec.GetHashCode());
		}
		
		[Test]
		public virtual void  testEquals()
		{
			Object[][] paramList = new Object[][]{new Object[]{"intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3}, new Object[]{"intPrimitive", FilterOperator.EQUAL, 3, "intBoxed", FilterOperator.EQUAL, 3}, new Object[]{"intPrimitive", FilterOperator.EQUAL, 2}, new Object[]{"intPrimitive", FilterOperator.RANGE_CLOSED, 1, 10}, new Object[]{"intPrimitive", FilterOperator.EQUAL, 2, "intBoxed", FilterOperator.EQUAL, 3}, new Object[]{}, new Object[]{}};
			
			List< FilterSpec > specVec = new List< FilterSpec >();
			foreach(Object [] param in paramList)
			{
				FilterSpec spec = SupportFilterSpecBuilder.build(eventType, param);
				specVec.Add(spec);
			}
			
			Assert.IsFalse(specVec[0].Equals(specVec[0]));
			Assert.IsFalse(specVec[0].Equals(specVec[0]));
			Assert.IsFalse(specVec[0].Equals(specVec[0]));
			Assert.AreEqual(specVec[0], specVec[0]);
			Assert.IsFalse(specVec[0].Equals(specVec[0]));
			Assert.AreEqual(specVec[5], specVec[5]);
			
			Assert.IsFalse(specVec[2].Equals(specVec[2]));
		}
		
		[Test]
		public virtual void  testGetValueSet()
		{
			IList<FilterSpecParam> _params = SupportFilterSpecBuilder.buildList(
				new Object[] { "intPrimitive", FilterOperator.EQUAL, 2 });
			_params.Add(new FilterSpecParamEventProp("doubleBoxed", FilterOperator.EQUAL, "asName", "doublePrimitive"));
			FilterSpec filterSpec = new FilterSpec(eventType, _params);
			
			SupportBean eventBean = new SupportBean();
			eventBean.doublePrimitive = 999.999;
			EventBean _event = SupportEventBeanFactory.createObject(eventBean);
			MatchedEventMap matchedEvents = new MatchedEventMap();
			matchedEvents.Add("asName", _event);
			FilterValueSet valueSet = filterSpec.getValueSet(matchedEvents);
			
			// Assert the generated filter value container
			Assert.AreSame(eventType, valueSet.EventType);
			Assert.AreEqual(2, valueSet.Parameters.Count);
			
			// Assert the first param
			FilterValueSetParam param = valueSet.Parameters[0];
			Assert.AreEqual("intPrimitive", param.PropertyName);
			Assert.AreEqual(FilterOperator.EQUAL, param.FilterOperator);
			Assert.AreEqual(2, param.FilterForValue);
			
			// Assert the second param
			param = valueSet.Parameters[1];
			Assert.AreEqual("doubleBoxed", param.PropertyName);
			Assert.AreEqual(FilterOperator.EQUAL, param.FilterOperator);
			Assert.AreEqual(999.999, param.FilterForValue);
		}
	}
}
