using System;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.expression
{

	[TestFixture]
	public class TestExprLikeNode
	{
		private ExprLikeNode likeNodeNormal;
		private ExprLikeNode likeNodeNot;
		private ExprLikeNode likeNodeNormalEscaped;

		[SetUp]
		public virtual void setUp()
		{
			likeNodeNormal = SupportExprNodeFactory.makeLikeNode( false, null );
			likeNodeNot = SupportExprNodeFactory.makeLikeNode( true, null );
			likeNodeNormalEscaped = SupportExprNodeFactory.makeLikeNode( false, "!" );
		}

		[Test]
		public virtual void testGetType()
		{
			Assert.AreEqual( typeof( bool ), likeNodeNormal.ReturnType );
			Assert.AreEqual( typeof( bool ), likeNodeNot.ReturnType );
			Assert.AreEqual( typeof( bool ), likeNodeNormalEscaped.ReturnType );
		}

		[Test]
		public virtual void testValidate()
		{
			// No subnodes: Exception is thrown.
			tryInvalidValidate( new ExprLikeNode( true ) );

			// singe child node not possible, must be 2 at least
			likeNodeNormal = new ExprLikeNode( false );
			likeNodeNormal.AddChildNode( new SupportExprNode( (Object) 4 ) );
			tryInvalidValidate( likeNodeNormal );

			// test a type mismatch
			likeNodeNormal = new ExprLikeNode( true );
			likeNodeNormal.AddChildNode( new SupportExprNode( "sx" ) );
			likeNodeNormal.AddChildNode( new SupportExprNode( 4 ) );
			tryInvalidValidate( likeNodeNormal );

			// test numeric supported
			likeNodeNormal = new ExprLikeNode( false );
			likeNodeNormal.AddChildNode( new SupportExprNode( (Object) 4 ) );
			likeNodeNormal.AddChildNode( new SupportExprNode( "sx" ) );

			// test invalid escape char
			likeNodeNormal = new ExprLikeNode( false );
			likeNodeNormal.AddChildNode( new SupportExprNode( (Object) 4 ) );
			likeNodeNormal.AddChildNode( new SupportExprNode( "sx" ) );
			likeNodeNormal.AddChildNode( new SupportExprNode( 5 ) );
		}

		[Test]
		public virtual void testEvaluate()
		{
			// Build :      s0.string like "%abc__"  (with or witout escape)
			Assert.IsFalse( (bool) likeNodeNormal.Evaluate( MakeEvent( "abcx" ) ) );
			Assert.IsTrue( (bool) likeNodeNormal.Evaluate( MakeEvent( "dskfsljkdfabcxx" ) ) );
			Assert.IsTrue( (bool) likeNodeNot.Evaluate( MakeEvent( "abcx" ) ) );
			Assert.IsFalse( (bool) likeNodeNot.Evaluate( MakeEvent( "dskfsljkdfabcxx" ) ) );
		}

		[Test]
		public virtual void testEquals()
		{
			ExprLikeNode otherLikeNodeNot = SupportExprNodeFactory.makeLikeNode( true, "@" );
			ExprLikeNode otherLikeNodeNot2 = SupportExprNodeFactory.makeLikeNode( true, "!" );

			Assert.IsTrue( likeNodeNot.EqualsNode( otherLikeNodeNot2 ) );
			Assert.IsTrue( otherLikeNodeNot2.EqualsNode( otherLikeNodeNot ) ); // Escape char itself is an expression
			Assert.IsFalse( likeNodeNormal.EqualsNode( otherLikeNodeNot ) );
		}

		[Test]
		public virtual void testToExpressionString()
		{
			Assert.AreEqual( "s0.string like \"%abc__\"", likeNodeNormal.ExpressionString );
			Assert.AreEqual( "s0.string not like \"%abc__\"", likeNodeNot.ExpressionString );
			Assert.AreEqual( "s0.string like \"%abc__\" escape \"!\"", likeNodeNormalEscaped.ExpressionString );
		}

		private EventBean[] MakeEvent( String stringValue )
		{
			SupportBean _event = new SupportBean();
			_event.StringValue = stringValue;
			return new EventBean[] { SupportEventBeanFactory.createObject( _event ) };
		}

		private void tryInvalidValidate( ExprLikeNode exprLikeRegexpNode )
		{
			try
			{
				exprLikeRegexpNode.validate( null, null );
				Assert.Fail();
			}
			catch ( ExprValidationException )
			{
				// expected
			}
		}
	}
}
