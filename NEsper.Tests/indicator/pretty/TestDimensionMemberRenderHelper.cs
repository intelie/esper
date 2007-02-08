using System;

using net.esper.view.stat.olap;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.indicator.pretty
{
	
	[TestFixture]
	public class TestDimensionMemberRenderHelper 
	{
		[Test]
		public virtual void  testRenderMember()
		{
			// Render a member without values
			DimensionImpl dimensionOne = new DimensionImpl(new String[]{"a", "b"});
			DimensionMemberImpl memberOne = new DimensionMemberImpl(new Object[0]);
			memberOne.setDimension(dimensionOne);
			Assert.AreEqual("[a, b]", DimensionMemberRenderHelper.renderMember(memberOne));
			
			// Render a member representing a single value
			DimensionMemberImpl memberTwo = new DimensionMemberImpl(new Object[]{"x"});
			memberTwo.setDimension(dimensionOne);
			Assert.AreEqual("x", DimensionMemberRenderHelper.renderMember(memberTwo));
			
			// Render a member representing a aggregate value
			DimensionMemberImpl memberThree = new DimensionMemberImpl(new Object[]{"x", "y"});
			memberThree.setDimension(dimensionOne);
			Assert.AreEqual("[x, y]", DimensionMemberRenderHelper.renderMember(memberThree));
		}
	}
}
