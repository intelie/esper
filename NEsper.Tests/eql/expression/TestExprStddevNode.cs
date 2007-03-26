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
			Assert.AreEqual( typeof( double? ), validatedNodeToTest.ReturnType );
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
			Assert.AreEqual( typeof( double? ), agg.ValueType );

			Assert.IsNull( agg.Value );

            agg.Enter(10);
			Assert.IsNull( agg.Value );

            agg.Enter(8);
			double result = (Double) agg.Value;
			Assert.AreEqual( "1.4142", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );

            agg.Enter(5);
			result = (Double) agg.Value;
			Assert.AreEqual( "2.5166", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );

            agg.Enter(9);
			result = (Double) agg.Value;
			Assert.AreEqual( "2.1602", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );

			agg.Leave( 10 );
			result = (Double) agg.Value;
			Assert.AreEqual( "2.0816", result.ToString().Substring( 0, ( 6 ) - ( 0 ) ) );
		}

		private ExprStddevNode makeNode( Object value, Type type )
		{
			ExprStddevNode stddevNode = new ExprStddevNode( false );
			stddevNode.AddChildNode( new SupportExprNode( value, type ) );
			stddevNode.Validate( null, null );
			return stddevNode;
		}
	}
}
