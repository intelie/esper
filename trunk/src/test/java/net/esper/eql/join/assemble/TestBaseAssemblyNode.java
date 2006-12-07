package net.esper.eql.join.assemble;

import net.esper.support.eql.join.SupportJoinProcNode;
import net.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestBaseAssemblyNode extends TestCase
{
    public void testGetSubstreams()
    {
        SupportJoinProcNode top = new SupportJoinProcNode(2, 3);

        SupportJoinProcNode child_1 = new SupportJoinProcNode(5, 3);
        SupportJoinProcNode child_2 = new SupportJoinProcNode(1, 3);
        top.addChild(child_1);
        top.addChild(child_2);

        SupportJoinProcNode child_1_1 = new SupportJoinProcNode(6, 3);
        SupportJoinProcNode child_1_2 = new SupportJoinProcNode(7, 3);
        child_1.addChild(child_1_1);
        child_1.addChild(child_1_2);

        SupportJoinProcNode child_1_1_1 = new SupportJoinProcNode(0, 3);
        child_1_1.addChild(child_1_1_1);

        int[] result = top.getSubstreams();
        ArrayAssertionUtil.assertEqualsAnyOrder(new int[] {2, 5, 1, 6, 7, 0}, result);
    }
}
