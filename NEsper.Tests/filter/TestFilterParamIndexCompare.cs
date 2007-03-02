using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterParamIndexCompare
	{
		private SupportEventEvaluator testEvaluator;
		private SupportBean testBean;
		private EventBean testEventBean;
		private EventType testEventType;
		private IList<FilterCallback> matchesList;

		[SetUp]
		public virtual void setUp()
		{
			testEvaluator = new SupportEventEvaluator();
			testBean = new SupportBean();
			testEventBean = SupportEventBeanFactory.createObject( testBean );
			testEventType = testEventBean.EventType;
			matchesList = new List<FilterCallback>();
		}

		[Test]
		public virtual void testInvalid()
		{
			try
			{
				new FilterParamIndexCompare( "doublePrimitive", FilterOperator.EQUAL, testEventType );
				Assert.IsTrue( false );
			}
			catch ( ArgumentException ex )
			{
				// Expected exception
			}

			try
			{
				new FilterParamIndexCompare( "doublePrimitive", FilterOperator.RANGE_CLOSED, testEventType );
				Assert.IsTrue( false );
			}
			catch ( ArgumentException ex )
			{
				// Expected exception
			}
		}

		[Test]
		public virtual void testMatchDoubleAndGreater()
		{
			FilterParamIndexCompare index = new FilterParamIndexCompare( "doublePrimitive", FilterOperator.GREATER, testEventType );

			index.Put( 1.5, testEvaluator );
			index.Put( 2.1, testEvaluator );
			index.Put( 2.2, testEvaluator );

			verifydoublePrimitive( index, 1.5, 0 );
			verifydoublePrimitive( index, 1.7, 1 );
			verifydoublePrimitive( index, 2.2, 2 );
			verifydoublePrimitive( index, 2.1999999, 2 );
			verifydoublePrimitive( index, -1, 0 );
			verifydoublePrimitive( index, 99, 3 );

			Assert.AreEqual( testEvaluator, index[ 1.5d ] );
			Assert.IsTrue( index.ReadWriteLock != null );
			Assert.IsTrue( index.Remove( 1.5d ) );
			Assert.IsFalse( index.Remove( 1.5d ) );
			Assert.AreEqual( null, index[ 1.5d ] );

			try
			{
				index.Put( "a", testEvaluator );
				Assert.IsTrue( false );
			}
			catch ( ArgumentException ex )
			{
				// Expected
			}
		}

		[Test]
		public virtual void testMatchLongAndGreaterEquals()
		{
			FilterParamIndexCompare index = new FilterParamIndexCompare( "longBoxed", FilterOperator.GREATER_OR_EQUAL, testEventType );

			index[1L] = testEvaluator;
			index[2L] = testEvaluator;
			index[4L] = testEvaluator;

			// Should not match with null
			verifyLongBoxed( index, null, 0 );

			verifyLongBoxed( index, 0L, 0 );
			verifyLongBoxed( index, 1L, 1 );
			verifyLongBoxed( index, 2L, 2 );
			verifyLongBoxed( index, 3L, 2 );
			verifyLongBoxed( index, 4L, 3 );
			verifyLongBoxed( index, 10L, 3 );

			// Put a long primitive in - should work
			index.Put( 9L, testEvaluator );
			try
			{
				index.Put( 10, testEvaluator );
				Assert.IsTrue( false );
			}
			catch ( ArgumentException ex )
			{
				// Expected
			}
		}

		[Test]
		public virtual void testMatchLongAndLessThan()
		{
			FilterParamIndexCompare index = new FilterParamIndexCompare( "longPrimitive", FilterOperator.LESS, testEventType );

			index.Put( 1L, testEvaluator );
			index.Put( 10L, testEvaluator );
			index.Put( 100L, testEvaluator );

			verifylongPrimitive( index, 100, 0 );
			verifylongPrimitive( index, 101, 0 );
			verifylongPrimitive( index, 99, 1 );
			verifylongPrimitive( index, 11, 1 );
			verifylongPrimitive( index, 10, 1 );
			verifylongPrimitive( index, 9, 2 );
			verifylongPrimitive( index, 2, 2 );
			verifylongPrimitive( index, 1, 2 );
			verifylongPrimitive( index, 0, 3 );
		}

		[Test]
		public virtual void testMatchDoubleAndLessOrEqualThan()
		{
			FilterParamIndexCompare index = new FilterParamIndexCompare( "doubleBoxed", FilterOperator.LESS_OR_EQUAL, testEventType );

			index.Put( 7.4D, testEvaluator );
			index.Put( 7.5D, testEvaluator );
			index.Put( 7.6D, testEvaluator );

			verifyDoubleBoxed( index, 7.39, 3 );
			verifyDoubleBoxed( index, 7.4, 3 );
			verifyDoubleBoxed( index, 7.41, 2 );
			verifyDoubleBoxed( index, 7.5, 2 );
			verifyDoubleBoxed( index, 7.51, 1 );
			verifyDoubleBoxed( index, 7.6, 1 );
			verifyDoubleBoxed( index, 7.61, 0 );
		}

		private void verifydoublePrimitive( FilterParamIndex index, double testValue, int numExpected )
		{
			testBean.doublePrimitive = testValue;
			index.MatchEvent( testEventBean, matchesList );
			Assert.AreEqual( numExpected, testEvaluator.AndResetCountInvoked );
		}

		private void verifyDoubleBoxed( FilterParamIndex index, Double? testValue, int numExpected )
		{
			testBean.doubleBoxed = testValue;
			index.MatchEvent( testEventBean, matchesList );
			Assert.AreEqual( numExpected, testEvaluator.AndResetCountInvoked );
		}

		private void verifyLongBoxed( FilterParamIndex index, Int64? testValue, int numExpected )
		{
			testBean.longBoxed = testValue;
			index.MatchEvent( testEventBean, matchesList );
			Assert.AreEqual( numExpected, testEvaluator.AndResetCountInvoked );
		}

		private void verifylongPrimitive( FilterParamIndex index, long testValue, int numExpected )
		{
			testBean.longPrimitive = testValue;
			index.MatchEvent( testEventBean, matchesList );
			Assert.AreEqual( numExpected, testEvaluator.AndResetCountInvoked );
		}
	}
}
