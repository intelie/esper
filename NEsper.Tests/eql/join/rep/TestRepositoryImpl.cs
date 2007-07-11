using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.support.eql.join;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.rep
{
	[TestFixture]
	public class TestRepositoryImpl
	{
		private EventBean s0Event;
		private RepositoryImpl repository;

		[SetUp]
		public virtual void setUp()
		{
			s0Event = SupportEventBeanFactory.CreateObject( new Object() );
			repository = new RepositoryImpl( 0, s0Event, 6 );
		}

		[Test]
		public void testGetCursors()
		{
			// get cursor for root stream lookup
			IEnumerator<Cursor> it = repository.GetCursors( 0 );
			Assert.IsTrue( it.MoveNext() );
			Cursor cursor = it.Current;
			Assert.AreSame( s0Event, cursor.Event );
			Assert.AreEqual( 0, cursor.Stream );

			Assert.IsFalse( it.MoveNext() );
			tryIteratorEmpty( it );

			// try invalid get cursor for no results
			try
			{
				repository.GetCursors( 2 );
				Assert.Fail();
			}
			catch ( NullReferenceException ex )
			{
				// expected
			}
		}

		[Test]
		public void testAddResult()
		{
			Set<EventBean> results = SupportJoinResultNodeFactory.MakeEventSet( 2 );
			repository.AddResult( CollectionHelper.Next( repository.GetCursors( 0 )), results, 1 );
			Assert.AreEqual( 1, repository.NodesPerStream[1].Count );

			try
			{
                repository.AddResult(CollectionHelper.Next(repository.GetCursors(0)), new HashSet<EventBean>(), 1);
				Assert.Fail();
			}
			catch ( ArgumentException ex )
			{
				// expected
			}
			try
			{
				repository.AddResult( CollectionHelper.Next(repository.GetCursors( 0 )), null, 1 );
				Assert.Fail();
			}
			catch ( NullReferenceException ex )
			{
				// expected
			}
		}

		[Test]
		public void testFlow()
		{
			// Lookup from s0
			Cursor[] cursors = read( repository.GetCursors( 0 ) );
			Assert.AreEqual( 1, cursors.Length );

			Set<EventBean> resultsS1 = SupportJoinResultNodeFactory.MakeEventSet( 2 );
			repository.AddResult( cursors[0], resultsS1, 1 );

			// Lookup from s1
			cursors = read( repository.GetCursors( 1 ) );
			Assert.AreEqual( 2, cursors.Length );

			Set<EventBean>[] resultsS2 = SupportJoinResultNodeFactory.MakeEventSets( new int[] { 2, 3 } );
			repository.AddResult( cursors[0], resultsS2[0], 2 );
			repository.AddResult( cursors[1], resultsS2[1], 2 );

			// Lookup from s2
			cursors = read( repository.GetCursors( 2 ) );
			Assert.AreEqual( 5, cursors.Length ); // 2 + 3 for s2

			Set<EventBean>[] resultsS3 = SupportJoinResultNodeFactory.MakeEventSets( new int[] { 2, 1, 3, 5, 1 } );
			repository.AddResult( cursors[0], resultsS3[0], 3 );
			repository.AddResult( cursors[1], resultsS3[1], 3 );
			repository.AddResult( cursors[2], resultsS3[2], 3 );
			repository.AddResult( cursors[3], resultsS3[3], 3 );
			repository.AddResult( cursors[4], resultsS3[4], 3 );

			// Lookup from s3
			cursors = read( repository.GetCursors( 3 ) );
			Assert.AreEqual( 12, cursors.Length );
		}

		private void tryIteratorEmpty( System.Collections.IEnumerator it )
		{
			try
			{
				it.MoveNext();
				Object generatedAux = it.Current;
				Assert.Fail();
			}
			catch ( ArgumentOutOfRangeException ex )
			{
				// expected
			}
		}

		private Cursor[] read( IEnumerator<Cursor> iterator )
		{
			List<Cursor> cursors = new List<Cursor>();
			while ( iterator.MoveNext() )
			{
				Cursor cursor = iterator.Current;
				cursors.Add( cursor );
			}
			return cursors.ToArray();
		}
	}
}
