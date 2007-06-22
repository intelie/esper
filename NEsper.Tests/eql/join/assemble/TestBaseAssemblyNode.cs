using System;

using net.esper.support.eql.join;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.assemble
{

	[TestFixture]
	public class TestBaseAssemblyNode
	{
		[Test]
		public virtual void  testGetSubstreams()
		{
			SupportJoinProcNode top = new SupportJoinProcNode(2, 3);

			SupportJoinProcNode child_1 = new SupportJoinProcNode(5, 3);
			SupportJoinProcNode child_2 = new SupportJoinProcNode(1, 3);
			top.AddChild(child_1);
			top.AddChild(child_2);

			SupportJoinProcNode child_1_1 = new SupportJoinProcNode(6, 3);
			SupportJoinProcNode child_1_2 = new SupportJoinProcNode(7, 3);
			child_1.AddChild(child_1_1);
			child_1.AddChild(child_1_2);

			SupportJoinProcNode child_1_1_1 = new SupportJoinProcNode(0, 3);
			child_1_1.AddChild(child_1_1_1);

			int[] result = top.Substreams;
			ArrayAssertionUtil.AreEqualAnyOrder(new int[]{2, 5, 1, 6, 7, 0}, result);
		}
	}
}
