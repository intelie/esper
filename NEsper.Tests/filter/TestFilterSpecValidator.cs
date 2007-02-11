using System;

using net.esper.eql.parse;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	
	[TestFixture]
	public class TestFilterSpecValidator 
	{
		private EventType eventType;
		
		[SetUp]
		public virtual void  setUp()
		{
			eventType = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
		}
		
		[Test]
		public virtual void  testValidate()
		{
			assertInvalid("boolPrimitive", FilterOperator.GREATER, false);
			assertInvalid("boolPrimitive", FilterOperator.RANGE_CLOSED, 1, 1);
			assertValid("boolPrimitive", FilterOperator.EQUAL, false);
			assertValid("boolPrimitive", FilterOperator.EQUAL, true);

            assertInvalid("str", FilterOperator.LESS, "a");
            assertInvalid("str", FilterOperator.RANGE_CLOSED, 10, 20);
            assertInvalid("str", FilterOperator.EQUAL, null);
            assertValid("str", FilterOperator.EQUAL, "a");
			
			assertInvalid("dummy", FilterOperator.EQUAL, "a");
			
			assertInvalid("doubleBoxed", FilterOperator.EQUAL, "a");
			assertValid("doubleBoxed", FilterOperator.RANGE_CLOSED, 2, 2);
			assertInvalid("doubleBoxed", FilterOperator.GREATER, 2);
			assertValid("doubleBoxed", FilterOperator.GREATER, 2d);
		}
		
		private
		void assertValid(params object[] filterParameters)
		{
			FilterSpec spec = SupportFilterSpecBuilder.build(eventType, filterParameters);
			FilterSpecValidator.validate(spec, null);
		}
		
		private
		void assertInvalid(params object[] filterParameters)
		{
			try
			{
				FilterSpec spec = SupportFilterSpecBuilder.build(eventType, filterParameters);
				FilterSpecValidator.validate(spec, null);
				Assert.Fail();
			}
			catch (ASTFilterSpecValidationException ex)
			{
				// Expected exception
			}
		}
	}
}
