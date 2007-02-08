using System;

using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.events
{
	
	public class TestCGLibPropertyGetter:TestCase
	{
		internal EventBean unitTestBean;
		
		[SetUp]
		public virtual void  setUp()
		{
			SupportBean testEvent = new SupportBean();
			testEvent.IntPrimitive = 10;
			testEvent.String = "a";
			testEvent.DoubleNullable = null;
			
			unitTestBean = SupportEventBeanFactory.createObject(testEvent);
		}
		
		[Test]
		public virtual void  testGetter()
		{
			CGLibPropertyGetter getter = makeGetter(typeof(SupportBean), "getIntPrimitive");
			Assert.AreEqual(10, getter[unitTestBean]);
			
			getter = makeGetter(typeof(SupportBean), "getString");
			Assert.AreEqual("a", getter[unitTestBean]);
			
			getter = makeGetter(typeof(SupportBean), "getDoubleNullable");
			Assert.AreEqual(null, getter[unitTestBean]);
			
			try
			{
				EventBean eventBean = SupportEventBeanFactory.createObject(new Object());
				getter[eventBean];
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
			CGLibPropertyGetter getter = makeGetter(typeof(SupportBean), "getIntPrimitive");
			
			log.Info(".testPerformance Starting test");
			
			for (int i = 0; i < 10; i++)
			// Change to 1E8 for performance testing
			{
				int value_Renamed = (int) getter[unitTestBean];
				Assert.AreEqual(10, value_Renamed);
			}
			
			log.Info(".testPerformance Done test");
		}
		
		private CGLibPropertyGetter makeGetter(System.Type clazz, String methodName)
		{
			FastClass fastClass = FastClass.create(clazz);
			System.Reflection.MethodInfo method = clazz.GetMethod(methodName, (new System.Type[]{} == null)?new System.Type[0]:(System.Type[]) new System.Type[]{});
			FastMethod fastMethod = fastClass.getMethod(method);
			
			CGLibPropertyGetter getter = new CGLibPropertyGetter(fastMethod);
			
			return getter;
		}
		
		[Test]
		public virtual void  testGetterSpecial()
		{
			System.Type clazz = typeof(SupportBeanComplexProps);
			FastClass fastClass = FastClass.create(clazz);
			
			// set up bean
			SupportBeanComplexProps bean = SupportBeanComplexProps.makeDefaultBean();
			
			// try mapped property
			System.Reflection.MethodInfo method = clazz.GetMethod("getMapped", (new System.Type[]{typeof(String)} == null)?new System.Type[0]:(System.Type[]) new System.Type[]{typeof(String)});
			FastMethod fastMethod = fastClass.getMethod(method);
			Object result = fastMethod.invoke(bean, new Object[]{"keyOne"});
			Assert.AreEqual("valueOne", result);
			result = fastMethod.invoke(bean, new Object[]{"keyTwo"});
			Assert.AreEqual("valueTwo", result);
			
			// try index property
			method = clazz.GetMethod("getIndexed", (new System.Type[]{typeof(int)} == null)?new System.Type[0]:(System.Type[]) new System.Type[]{typeof(int)});
			fastMethod = fastClass.getMethod(method);
			result = fastMethod.invoke(bean, new Object[]{0});
			Assert.AreEqual(1, result);
			result = fastMethod.invoke(bean, new Object[]{1});
			Assert.AreEqual(2, result);
			
			// try nested property
			method = clazz.GetMethod("getNested", (new System.Type[]{} == null)?new System.Type[0]:(System.Type[]) new System.Type[]{});
			fastMethod = fastClass.getMethod(method);
			SupportBeanComplexProps.SupportBeanSpecialGetterNested nested = (SupportBeanComplexProps.SupportBeanSpecialGetterNested) fastMethod.invoke(bean, new Object[]{});
			
			System.Type nestedClazz = typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNested);
			System.Reflection.MethodInfo methodNested = nestedClazz.GetMethod("getNestedValue", (new System.Type[]{} == null)?new System.Type[0]:(System.Type[]) new System.Type[]{});
			FastClass fastClassNested = FastClass.create(nestedClazz);
			fastClassNested.getMethod(methodNested);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(TestCGLibPropertyGetter));
	}
}
