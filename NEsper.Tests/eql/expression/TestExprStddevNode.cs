using System;

using NUnit.Core;
using NUnit.Framework;

using net.esper.eql.core;
using net.esper.support.eql;

namespace net.esper.eql.expression
{
	[TestFixture]
	public class TestExprStddevNode : TestExprAggregateNodeAdapter
	{
		[SetUp]
		public virtual void setUp()
		{
			base.validatedNodeToTest = makeNode( 5, typeof( Int32 ) );
		}

		[Test]
		public virtual void testGetType()
		{
			Assert.AreEqual( typeof( Double ), validatedNodeToTest.ReturnType );
		}

		[Test]
		public virtual void testToExpressionString()
		{
			Assert.AreEqual( "stddev(5)", validatedNodeToTest.ExpressionString );
		}

		[Test]
		public virtual void testEqualsNode()
		{
			Assert.IsTrue( validatedNodeToTest.EqualsNode( validatedNodeToTest ) );
			Assert.IsFalse( validatedNodeToTest.EqualsNode( new ExprSumNode( false ) ) );
		}

		[Test]
		public virtual void testAggregateFunction()
		{
			Aggregator agg = validatedNodeToTest.AggregationFunction;
			Assert.AreEqual( typeof( Double ), agg.ValueType );

			Assert.IsNull( agg.Value );

			agg.enter( 10 );
			Assert.IsNull( agg.Value );

			agg.enter( 8 );
			double result = (Double) agg.Value;
			Assert.AreEqual( "1.4142", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );

			agg.enter( 5 );
			result = (Double) agg.Value;
			Assert.AreEqual( "2.5166", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );

			agg.enter( 9 );
			result = (Double) agg.Value;
			Assert.AreEqual( "2.1602", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );

			agg.leave( 10 );
			result = (Double) agg.Value;
			Assert.AreEqual( "2.0816", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );
		}

		private ExprStddevNode makeNode( Object value, Type type )
		{
			ExprStddevNode stddevNode = new ExprStddevNode( false );
			stddevNode.AddChildNode( new SupportExprNode( value, type ) );
			stddevNode.validate( null, null );
			return stddevNode;
		}
	}
}
