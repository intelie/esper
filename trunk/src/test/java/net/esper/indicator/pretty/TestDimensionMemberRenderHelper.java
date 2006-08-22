package net.esper.indicator.pretty;

import junit.framework.TestCase;
import net.esper.view.stat.olap.DimensionImpl;
import net.esper.view.stat.olap.DimensionMemberImpl;

public class TestDimensionMemberRenderHelper extends TestCase
{
    public void testRenderMember()
    {
        // Render a member without values
        DimensionImpl dimensionOne = new DimensionImpl(new String[] { "a", "b" });
        DimensionMemberImpl memberOne = new DimensionMemberImpl(new Object[0]);
        memberOne.setDimension(dimensionOne);
        assertEquals("[a, b]", DimensionMemberRenderHelper.renderMember(memberOne));

        // Render a member representing a single value
        DimensionMemberImpl memberTwo = new DimensionMemberImpl(new Object[] { "x" });
        memberTwo.setDimension(dimensionOne);
        assertEquals("x", DimensionMemberRenderHelper.renderMember(memberTwo));

        // Render a member representing a aggregate value
        DimensionMemberImpl memberThree = new DimensionMemberImpl(new Object[] { "x", "y" });
        memberThree.setDimension(dimensionOne);
        assertEquals("[x, y]", DimensionMemberRenderHelper.renderMember(memberThree));
    }
}
