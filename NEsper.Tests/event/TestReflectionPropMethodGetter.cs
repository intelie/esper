using System;
using System.Reflection;

using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events
{
	
	[TestFixture]
	public class TestReflectionPropMethodGetter 
	{
		internal EventBean unitTestBean;
		
		[SetUp]
		public virtual void  setUp()
		{
			SupportBean testEvent = new SupportBean();
			testEvent.intPrimitive = 10;
			testEvent.StringValue = "a";
			testEvent.doubleBoxed = null;
			
			unitTestBean = SupportEventBeanFactory.createObject(testEvent);
		}
		
		[Test]
		public virtual void  testGetter()
		{
			ReflectionPropMethodGetter getter = makeGetter(typeof(SupportBean), "getintPrimitive");
			Assert.AreEqual(10, getter.GetValue(unitTestBean));
			
			getter = makeGetter(typeof(SupportBean), "getString");
            Assert.AreEqual("a", getter.GetValue(unitTestBean));
			
			getter = makeGetter(typeof(SupportBean), "getDoubleBoxed");
            Assert.AreEqual(null, getter.GetValue(unitTestBean));
			
			try
			{
				EventBean eventBean = SupportEventBeanFactory.createObject(new Object());
                Object temp = getter.GetValue(eventBean);
				Assert.IsTrue(false);
			}
			catch (PropertyAccessException ex)
			{
				// Expected
				log.Debug(".testGetter Expected exception, msg=" + ex.Message);
			}
		}
		
		[Test]
		public virtual void  testPerformance()
		{
			ReflectionPropMethodGetter getter = makeGetter(typeof(SupportBean), "getintPrimitive");
			
			log.Info(".testPerformance Starting test");
			
			for (int i = 0; i < 10; i++)
			// Change to 1E8 for performance testing
			{
                int value = (Int32)getter.GetValue(unitTestBean);
				Assert.AreEqual(10, value);
			}
			
			log.Info(".testPerformance Done test");
		}
		
		private ReflectionPropMethodGetter makeGetter(Type clazz, String methodName)
		{
			MethodInfo method = clazz.GetMethod(
                methodName, 
                new Type[]{} == null ?
                new Type[0] :
                new Type[]{} );
			
			ReflectionPropMethodGetter getter = new ReflectionPropMethodGetter(method);
			
			return getter;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TestReflectionPropMethodGetter));
	}
}
