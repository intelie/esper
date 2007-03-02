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
			_params[0] = makeParam( "a", "intBoxed" );
			_params[1] = makeParam( "b", "intBoxed" );
			_params[2] = makeParam( "a", "intPrimitive" );
			_params[3] = makeParam( "c", "intPrimitive" );
			_params[4] = makeParam( "a", "intBoxed" );

			Assert.AreEqual( _params[0], _params[4] );
			Assert.AreEqual( _params[4], _params[0] );
			Assert.IsFalse( _params[0].Equals( _params[1] ) );
			Assert.IsFalse( _params[0].Equals( _params[2] ) );
			Assert.IsFalse( _params[0].Equals( _params[3] ) );
		}

		[Test]
		public virtual void testGetFilterValueClass()
		{
			FilterSpecParamEventProp param = makeParam( "asName", "intBoxed" );

			EDictionary<String, EventType> taggedEventTypes = new EHashDictionary<String, EventType>();
			taggedEventTypes.Put( "asName", SupportEventTypeFactory.createBeanType( typeof( SupportBean ) ) );

			Assert.AreEqual( typeof( int? ), param.GetFilterValueClass( taggedEventTypes ) );

			try
			{
				param.GetFilterValueClass( new EHashDictionary<String, EventType>() );
				Assert.Fail();
			}
			catch ( System.SystemException ex )
			{
				// Expected
			}

			try
			{
				param.GetFilterValueClass( null );
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
			FilterSpecParamEventProp _params = makeParam( "asName", "intBoxed" );

			SupportBean eventBean = new SupportBean();
			eventBean.intBoxed = 1000;
			EventBean _event = SupportEventBeanFactory.createObject( eventBean );

			MatchedEventMap matchedEvents = new MatchedEventMap();
			matchedEvents.Add( "asName", _event );

			Assert.AreEqual( 1000, _params.GetFilterValue( matchedEvents ) );

			try
			{
				_params.GetFilterValue( new MatchedEventMap() );
				Assert.Fail();
			}
			catch ( System.SystemException ex )
			{
				// expected
			}

			try
			{
				_params.GetFilterValue( null );
				Assert.Fail();
			}
			catch ( System.NullReferenceException ex )
			{
				// Expected
			}
		}

		private FilterSpecParamEventProp makeParam( String eventAsName, String property )
		{
			return new FilterSpecParamEventProp( "intPrimitive", FilterOperator.EQUAL, eventAsName, property );
		}
	}
}
