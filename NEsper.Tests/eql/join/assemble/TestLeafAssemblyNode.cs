using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.rep;
using net.esper.support.eql.join;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{
	[TestFixture]
	public class TestLeafAssemblyNode
	{
		private SupportJoinProcNode parentNode;
		private LeafAssemblyNode leafNode;

		[SetUp]
		public virtual void setUp()
		{
			leafNode = new LeafAssemblyNode( 1, 4 );
			parentNode = new SupportJoinProcNode( -1, 4 );
			parentNode.AddChild( leafNode );
		}

		[Test]
		public void testProcess()
		{
			IList<Node>[] result = SupportJoinResultNodeFactory.makeOneStreamResult( 4, 1, 2, 2 );

			leafNode.Process( result );

            IList<Node> node = result[1];

			Assert.AreEqual( 4, parentNode.getRowsList().Count );
			Assert.AreEqual( CollectionHelper.First( node[0].Events ), parentNode.getRowsList()[0][1] ); // compare event
		}

		[Test]
		public void testChildResult()
		{
			try
			{
                leafNode.Result(null, 0, null, null);
				Assert.Fail();
			}
			catch ( System.NotSupportedException ex )
			{
				// expected
			}
		}
	}
}
