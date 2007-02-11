using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join
{
	[TestFixture]
	public class TestJoinSetFilter
	{
		[Test]
		public virtual void testFilter()
		{
			ExprNode topNode = SupportExprNodeFactory.make2SubNodeAnd();

			EventBean[] pairOne = new EventBean[2];
			pairOne[0] = MakeEvent( 1, 2, "a" );
			pairOne[1] = MakeEvent( 2, 1, "a" );

			EventBean[] pairTwo = new EventBean[2];
			pairTwo[0] = MakeEvent( 1, 2, "a" );
			pairTwo[1] = MakeEvent( 2, 999, "a" );

			ISet<MultiKey<EventBean>> eventSet = new EHashSet<MultiKey<EventBean>>();
			eventSet.Add( new MultiKey<EventBean>( pairOne ) );
			eventSet.Add( new MultiKey<EventBean>( pairTwo ) );

			JoinSetFilter.Filter( topNode, eventSet );

			IEnumerator<MultiKey<EventBean>> _enum = eventSet.GetEnumerator();

			Assert.AreEqual( 1, eventSet.Count );
			Assert.IsTrue( _enum.MoveNext() );
			Assert.AreSame( pairOne, _enum.Current.Array );
		}

		private EventBean MakeEvent( int intPrimitive, int intBoxed, String stringValue )
		{
			SupportBean _event = new SupportBean();
			_event.intPrimitive = intPrimitive;
			_event.intBoxed = intBoxed;
			_event.StringValue = stringValue;
			return SupportEventBeanFactory.createObject( _event );
		}
	}
}
