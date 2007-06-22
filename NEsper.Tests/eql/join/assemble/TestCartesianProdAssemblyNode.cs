using System;
using System.Collections.Generic;

using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.support.eql.join;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{
	[TestFixture]
	public class TestCartesianProdAssemblyNode
	{
		private SupportJoinProcNode parentNode;
		private CartesianProdAssemblyNode optCartNode;
		private IList<Node>[] resultMultipleEvents;
		private IList<Node>[] resultSingleEvent;

		[SetUp]
		public virtual void setUp()
		{
			optCartNode = new CartesianProdAssemblyNode( 1, 4, true );

			parentNode = new SupportJoinProcNode( -1, 4 );
			parentNode.AddChild( optCartNode );

			// add child nodes to indicate what sub-streams to build the cartesian product from
			optCartNode.AddChild( new SupportJoinProcNode( 2, 4 ) );
			optCartNode.AddChild( new SupportJoinProcNode( 3, 4 ) );

			resultMultipleEvents = SupportJoinResultNodeFactory.makeOneStreamResult( 4, 1, 2, 1 ); // 2 nodes 1 event each for (1)
			resultSingleEvent = SupportJoinResultNodeFactory.makeOneStreamResult( 4, 1, 1, 1 ); // 1 nodes 1 event each for (1)
		}

		[Test]
		public virtual void testFlow()
		{
			optCartNode.Init( resultMultipleEvents );

			EventBean[] stream2Events = SupportJoinResultNodeFactory.MakeEvents( 2 ); // for identifying rows in cartesian product
			EventBean[] stream3Events = SupportJoinResultNodeFactory.MakeEvents( 2 ); // for identifying rows in cartesian product

			Node nodeOne = resultMultipleEvents[1][0];
			IEnumerator<EventBean> enumOne = nodeOne.Events.GetEnumerator();
			Assert.IsTrue( enumOne.MoveNext() );
			EventBean _eventOneStreamOne = enumOne.Current;
			Node nodeTwo = resultMultipleEvents[1][1];
			IEnumerator<EventBean> enumTwo = nodeTwo.Events.GetEnumerator();
			Assert.IsTrue( enumTwo.MoveNext() );
			EventBean _eventTwoStreamOne = enumTwo.Current;

			// generate an event row originating from child 1
			EventBean[] childRow = new EventBean[4]; // new rows for each result
			childRow[2] = stream2Events[0];
			optCartNode.Result( childRow, 2, _eventOneStreamOne, nodeOne ); // child is stream 2
			childRow = new EventBean[4];
			childRow[2] = stream2Events[1];
            optCartNode.Result(childRow, 2, _eventOneStreamOne, nodeOne); // child is stream 2

			// generate an event row originating from child 2
			childRow = new EventBean[4];
			childRow[3] = stream3Events[0];
            optCartNode.Result(childRow, 3, _eventOneStreamOne, nodeOne); // child is stream 3
			childRow = new EventBean[4];
			childRow[3] = stream3Events[1];
            optCartNode.Result(childRow, 3, _eventOneStreamOne, nodeOne); // child is stream 3

			// process posted rows (child rows were stored and are compared to find other rows to generate)
			optCartNode.Process( resultMultipleEvents );

			// 5 generated rows: 2 (stream 2) + 2 (stream 3) + 1 (self, Node 2)
			Assert.AreEqual( 5, parentNode.getRowsList().Count );

			EventBean[][] rowArr = SupportJoinResultNodeFactory.convertTo2DimArr( parentNode.getRowsList() );
            ArrayAssertionUtil.AreEqualAnyOrder(
                new EventBean[][] {
                    new EventBean[] { null, _eventOneStreamOne, stream2Events[0], stream3Events[0] },
                    new EventBean[] { null, _eventOneStreamOne, stream2Events[0], stream3Events[1] },
                    new EventBean[] { null, _eventOneStreamOne, stream2Events[1], stream3Events[0] },
                    new EventBean[] { null, _eventOneStreamOne, stream2Events[1], stream3Events[1] }, 
                    new EventBean[] { null, _eventTwoStreamOne, null, null }
                },
                rowArr);
		}

		[Test]
		public virtual void testProcessSingleEvent()
		{
            optCartNode.Init(resultSingleEvent);

			// test that the node indeed manufactures event rows for any event not received from a child
            optCartNode.Process(resultSingleEvent);

			// check generated row
			Assert.AreEqual( 1, parentNode.getRowsList().Count );
			EventBean[] row = parentNode.getRowsList()[0];
			Assert.AreEqual( 4, row.Length );
			Node node = resultSingleEvent[1][0];
			IEnumerator<EventBean> enumObj = node.Events.GetEnumerator();
			Assert.IsTrue( enumObj.MoveNext() );
			Assert.AreEqual( enumObj.Current, row[1] );
		}
	}
}
