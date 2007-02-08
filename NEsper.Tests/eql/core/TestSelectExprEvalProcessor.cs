using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestSelectExprEvalProcessor 
	{
		private SelectExprEvalProcessor methodOne;
		private SelectExprEvalProcessor methodTwo;
		
		[SetUp]
		public virtual void  setUp()
		{
			IList<SelectExprElementNamedSpec> selectList = SupportSelectExprFactory.makeNoAggregateSelectList();
			EventAdapterService eventAdapterService = SupportEventAdapterService.Service;
			
			methodOne = new SelectExprEvalProcessor(selectList, null, eventAdapterService);
			
			InsertIntoDesc insertIntoDesc = new InsertIntoDesc(true, "Hello");
			insertIntoDesc.Add("a");
			insertIntoDesc.Add("b");
			
			methodTwo = new SelectExprEvalProcessor(selectList, insertIntoDesc, eventAdapterService);
		}
		
		[Test]
		public virtual void  testGetResultEventType()
		{
			EventType type = methodOne.ResultEventType;
			Assert.IsTrue( CollectionHelper.AreEqual(
                (ICollection<string>) type.PropertyNames, 
                (ICollection<string>) new String[]{"resultOne", "resultTwo"}
                ));

			Assert.AreEqual(typeof(Double), type.GetPropertyType("resultOne"));
			Assert.AreEqual(typeof(Int32), type.GetPropertyType("resultTwo"));
			
			type = methodTwo.ResultEventType;
			Assert.IsTrue( CollectionHelper.AreEqual(
                (ICollection<string>) type.PropertyNames,
                (ICollection<string>) new String[] { "a", "b" }
                ));
			Assert.AreEqual(typeof(Double), type.GetPropertyType("a"));
			Assert.AreEqual(typeof(Int32), type.GetPropertyType("b"));
		}
		
		[Test]
		public virtual void  testProcess()
		{
			EventBean[] events = new EventBean[]{MakeEvent(8.8, 3, 4)};
			
			EventBean result = methodOne.Process(events);
			Assert.AreEqual(8.8d, result["resultOne"]);
			Assert.AreEqual(12, result["resultTwo"]);
			
			result = methodTwo.Process(events);
			Assert.AreEqual(8.8d, result["a"]);
			Assert.AreEqual(12, result["b"]);
			Assert.AreSame(result.EventType, methodTwo.ResultEventType);
		}
		
		private EventBean MakeEvent(double doubleBoxed, int intPrimitive, int intBoxed)
		{
			SupportBean bean = new SupportBean();
			bean.DoubleBoxed = doubleBoxed;
			bean.IntPrimitive = intPrimitive;
			bean.IntBoxed = intBoxed;
			return SupportEventBeanFactory.createObject(bean);
		}
	}
}
