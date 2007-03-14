using System;
using System.Collections.Generic;
using System.Data;

using net.esper.compat;
using net.esper.events;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.db
{
	//[TestFixture]
	public class TestPollExecStrategyDBQuery
	{
		private PollExecStrategyDBQuery dbPollExecStrategy;

		[SetUp]
		public virtual void setUp()
		{
			String sql = "select myvarchar from mytesttable where mynumeric = ? order by mybigint asc";

			DatabaseConnectionFactory databaseConnectionFactory = SupportDatabaseService.makeService().GetConnectionFactory( "mydb" );
			ConnectionCache connectionCache = new ConnectionNoCacheImpl( databaseConnectionFactory, sql );

			EDictionary<String, Type> resultProperties = new EHashDictionary<String, Type>();
			resultProperties.Put( "myvarchar", typeof( String ) );
			EventType resultEventType = SupportEventAdapterService.Service.CreateAnonymousMapType( resultProperties );

			EDictionary<String, DBOutputTypeDesc> propertiesOut = new EHashDictionary<String, DBOutputTypeDesc>();
			propertiesOut.Put( "myvarchar", new DBOutputTypeDesc( (int) DbType.Time, typeof( string ) ) ) ;
			
			dbPollExecStrategy = new PollExecStrategyDBQuery(
				SupportEventAdapterService.Service,
				resultEventType,
				connectionCache,
				sql,
				propertiesOut );
		}

		[Test]
		public virtual void testPoll()
		{
			dbPollExecStrategy.Start();

			IList<EventBean>[] resultRows = new List<EventBean>[3];
			resultRows[0] = dbPollExecStrategy.Poll( new Object[] { -1 } );
            resultRows[1] = dbPollExecStrategy.Poll(new Object[] { 500 });
            resultRows[2] = dbPollExecStrategy.Poll(new Object[] { 200 });

			// should have joined to two rows
			Assert.AreEqual( 0, resultRows[0].Count );
			Assert.AreEqual( 2, resultRows[1].Count );
			Assert.AreEqual( 1, resultRows[2].Count );

			EventBean _event = resultRows[1][0];
			Assert.AreEqual( "D", _event[ "myvarchar" ] );

			_event = resultRows[1][1];
			Assert.AreEqual( "E", _event[ "myvarchar" ] );

			_event = resultRows[2][0];
			Assert.AreEqual( "F", _event[ "myvarchar" ] );

			dbPollExecStrategy.Done();
			dbPollExecStrategy.Destroy();
		}
	}
}
