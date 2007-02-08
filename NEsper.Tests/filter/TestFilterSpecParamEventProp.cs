using System;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.filter
{
	[TestFixture]
	public class TestFilterSpecParamEventProp
	{
		[Test]
		public virtual void testEquals()
		{
			FilterSpecParamEventProp[] _params = new FilterSpecParamEventProp[5];
			_params[0] = makeParam( "a", "IntBoxed" );
			_params[1] = makeParam( "b", "IntBoxed" );
			_params[2] = makeParam( "a", "IntPrimitive" );
			_params[3] = makeParam( "c", "IntPrimitive" );
			_params[4] = makeParam( "a", "IntBoxed" );

			Assert.AreEqual( _params[0], _params[4] );
			Assert.AreEqual( _params[4], _params[0] );
			Assert.IsFalse( _params[0].Equals( _params[1] ) );
			Assert.IsFalse( _params[0].Equals( _params[2] ) );
			Assert.IsFalse( _params[0].Equals( _params[3] ) );
		}

		[Test]
		public virtual void testGetFilterValueClass()
		{
			FilterSpecParamEventProp param = makeParam( "asName", "IntBoxed" );

			EDictionary<String, EventType> taggedEventTypes = new EHashDictionary<String, EventType>();
			taggedEventTypes.Put( "asName", SupportEventTypeFactory.createBeanType( typeof( SupportBean ) ) );

			Assert.AreEqual( typeof( Int32 ), param.getFilterValueClass( taggedEventTypes ) );

			try
			{
				param.getFilterValueClass( new EHashDictionary<String, EventType>() );
				Assert.Fail();
			}
			catch ( System.SystemException ex )
			{
				// Expected
			}

			try
			{
				param.getFilterValueClass( null );
				Assert.Fail();
			}
			catch ( System.NullReferenceException ex )
			{
				// Expected
			}
		}

		[Test]
		public virtual void testGetFilterValue()
		{
			FilterSpecParamEventProp _params = makeParam( "asName", "IntBoxed" );

			SupportBean eventBean = new SupportBean();
			eventBean.IntBoxed = 1000;
			EventBean _event = SupportEventBeanFactory.createObject( eventBean );

			MatchedEventMap matchedEvents = new MatchedEventMap();
			matchedEvents.Add( "asName", _event );

			Assert.AreEqual( 1000, _params.getFilterValue( matchedEvents ) );

			try
			{
				_params.getFilterValue( new MatchedEventMap() );
				Assert.Fail();
			}
			catch ( System.SystemException ex )
			{
				// expected
			}

			try
			{
				_params.getFilterValue( null );
				Assert.Fail();
			}
			catch ( System.NullReferenceException ex )
			{
				// Expected
			}
		}

		private FilterSpecParamEventProp makeParam( String eventAsName, String property )
		{
			return new FilterSpecParamEventProp( "IntPrimitive", FilterOperator.EQUAL, eventAsName, property );
		}
	}
}
