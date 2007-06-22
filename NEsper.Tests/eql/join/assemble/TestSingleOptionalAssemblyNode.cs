using System;
using System.Collections.Generic;

using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.support.eql.join;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{
	[TestFixture]
	public class TestSingleOptionalAssemblyNode
	{
		private SupportJoinProcNode parentNode;
		private BranchOptionalAssemblyNode optAssemblyNode;
		private IList<Node>[] resultMultipleEvents;
		private IList<Node>[] resultSingleEvent;

		[SetUp]
		public virtual void setUp()
		{
			optAssemblyNode = new BranchOptionalAssemblyNode( 1, 4 );
			parentNode = new SupportJoinProcNode( -1, 4 );
			parentNode.AddChild( optAssemblyNode );

			resultMultipleEvents = SupportJoinResultNodeFactory.makeOneStreamResult( 4, 1, 2, 1 ); // 2 nodes 1 event each for (1)
			resultSingleEvent = SupportJoinResultNodeFactory.makeOneStreamResult( 4, 1, 1, 1 ); // 1 nodes 1 event each for (1)
		}

		[Test]
		public virtual void testProcessMultipleEvents()
		{
			optAssemblyNode.Init( resultMultipleEvents );

			// generate an event row originating from a child for 1 of the 2 events in the result
			EventBean[] childRow = new EventBean[4];
			Node nodeOne = resultMultipleEvents[1][0];
			IEnumerator<EventBean> enumOne = nodeOne.Events.GetEnumerator();
			Assert.IsTrue( enumOne.MoveNext() );
			EventBean _eventOne = enumOne.Current;
			optAssemblyNode.Result( childRow, 3, _eventOne, nodeOne );

			// test that the node indeed manufactures event rows for any event not received from a child
			parentNode.getRowsList().Clear();
			optAssemblyNode.Process( resultMultipleEvents );

			// check generated row
			Assert.AreEqual( 1, parentNode.getRowsList().Count );
			EventBean[] row = parentNode.getRowsList()[0];
			Assert.AreEqual( 4, row.Length );
			Node nodeTwo = resultMultipleEvents[1][1];
			IEnumerator<EventBean> enumTwo = nodeTwo.Events.GetEnumerator();
			Assert.IsTrue( enumTwo.MoveNext() );
			Assert.AreEqual( enumTwo.Current, row[1] );
		}

		[Test]
		public virtual void testProcessSingleEvent()
		{
			optAssemblyNode.Init( resultSingleEvent );

			// test that the node indeed manufactures event rows for any event not received from a child
			optAssemblyNode.Process( resultMultipleEvents );

			// check generated row
			Assert.AreEqual( 1, parentNode.getRowsList().Count );
			EventBean[] row = parentNode.getRowsList()[0];
			Assert.AreEqual( 4, row.Length );
			Node node = resultSingleEvent[1][0];
			IEnumerator<EventBean> enumObj = node.Events.GetEnumerator();
			Assert.IsTrue( enumObj.MoveNext() );
			Assert.AreEqual( enumObj.Current, row[1] );
		}

		[Test]
		public virtual void testChildResult()
		{
			optAssemblyNode.Init( resultMultipleEvents );
			testChildResult( optAssemblyNode, parentNode );
		}

		protected internal static void testChildResult( BaseAssemblyNode nodeUnderTest, SupportJoinProcNode mockParentNode )
		{
			EventBean[] childRow = new EventBean[4];
			childRow[3] = SupportJoinResultNodeFactory.MakeEvent();

			EventBean myEvent = SupportJoinResultNodeFactory.MakeEvent();
			Node myNode = SupportJoinResultNodeFactory.MakeNode( 3, 1 );

			// indicate child result
			nodeUnderTest.Result( childRow, 3, myEvent, myNode );

			// assert parent node got the row
			Assert.AreEqual( 1, mockParentNode.getRowsList().Count );
			EventBean[] resultRow = mockParentNode.getRowsList()[0];

			// assert the node has added his event to the row
			Assert.AreEqual( myEvent, resultRow[1] );
		}
	}
}
