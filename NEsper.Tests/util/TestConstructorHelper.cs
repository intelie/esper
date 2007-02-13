using System;

using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.util
{
	
	[TestFixture]
	public class TestConstructorHelper 
	{
		[Test]
		public virtual void  testValidInvokeConstructor()
		{
			Object[] _params = new Object[]{"test", 1};
			SupportCtorObjectArray objOne = (SupportCtorObjectArray) ConstructorHelper.invokeConstructor(typeof(SupportCtorObjectArray), _params);
			Assert.AreEqual(_params, objOne.Arguments);
			
			SupportCtorInt objTwo = (SupportCtorInt) ConstructorHelper.invokeConstructor(typeof(SupportCtorInt), new Object[]{99});
			Assert.AreEqual(99, objTwo.SomeValue);
			objTwo = (SupportCtorInt) ConstructorHelper.invokeConstructor(typeof(SupportCtorInt), new Object[]{13});
			Assert.AreEqual(13, objTwo.SomeValue);
			
			SupportCtorIntObjectArray objThree = (SupportCtorIntObjectArray) ConstructorHelper.invokeConstructor(typeof(SupportCtorIntObjectArray), new Object[]{1});
			Assert.AreEqual(1, objThree.SomeValue);
			objThree = (SupportCtorIntObjectArray) ConstructorHelper.invokeConstructor(typeof(SupportCtorIntObjectArray), _params);
			Assert.AreEqual(_params, objThree.Arguments);
		}
		
		[Test]
		public virtual void  testInvalidInvokeConstructor()
		{
			// No Ctor
			try
			{
				ConstructorHelper.invokeConstructor(typeof(SupportCtorNone), new Object[0]);
				Assert.Fail();
			}
			catch (MissingMethodException)
			{
				// Expected
			}
			
			// Not matching Ctor - number of params
			try
			{
				ConstructorHelper.invokeConstructor(typeof(SupportCtorInt), new Object[0]);
				Assert.Fail();
			}
            catch (MethodAccessException)
			{
				// Expected
			}
			
			// Type not matching
			try
			{
				ConstructorHelper.invokeConstructor(typeof(SupportCtorInt), new Object[]{"a"});
				Assert.Fail();
			}
			catch (MethodAccessException ex)
			{
				// Expected
			}
		}
	}
}
