using System;

using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events
{
	
	[TestFixture]
	public class TestReflectionPropFieldGetter 
	{
		internal EventBean unitTestBean;
		
		[SetUp]
		public virtual void  setUp()
		{
			SupportLegacyBean testEvent = new SupportLegacyBean("a");
			unitTestBean = SupportEventBeanFactory.createObject(testEvent);
		}
		
		[Test]
		public virtual void  testGetter()
		{
			ReflectionPropFieldGetter getter = makeGetter(typeof(SupportLegacyBean), "fieldLegacyVal");
            Assert.AreEqual("a", getter.GetValue(unitTestBean));
			
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
		
		private ReflectionPropFieldGetter makeGetter(Type clazz, String fieldName)
		{
			System.Reflection.FieldInfo field = clazz.GetField(fieldName, System.Reflection.BindingFlags.Instance | System.Reflection.BindingFlags.Public | System.Reflection.BindingFlags.Static);
			ReflectionPropFieldGetter getter = new ReflectionPropFieldGetter(field);
			return getter;
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TestReflectionPropFieldGetter));
	}
}
