package net.esper.pattern;

import net.esper.support.util.ArrayAssertionUtil;
import junit.framework.TestCase;

public class TestEvalNodeNumber extends TestCase
{
    public void testFlow()
    {
        EvalNodeNumber parent = new EvalNodeNumber();
        
        EvalNodeNumber child1 = parent.newChildNumber();
        ArrayAssertionUtil.assertEqualsExactOrder(child1.getNumber(), new short[] {0});

        EvalNodeNumber child1_1 = child1.newChildNumber();
        ArrayAssertionUtil.assertEqualsExactOrder(child1_1.getNumber(), new short[] {0, 0});

        EvalNodeNumber child1_2 = child1_1.newSiblingNumber();
        ArrayAssertionUtil.assertEqualsExactOrder(child1_2.getNumber(), new short[] {0, 1});

        EvalNodeNumber child1_2_1 = child1_2.newChildNumber();
        ArrayAssertionUtil.assertEqualsExactOrder(child1_2_1.getNumber(), new short[] {0, 1, 0});

        EvalNodeNumber child1_2_2 = child1_2_1.newSiblingNumber();
        ArrayAssertionUtil.assertEqualsExactOrder(child1_2_2.getNumber(), new short[] {0, 1, 1});
        ArrayAssertionUtil.assertEqualsExactOrder(child1_2_2.getParentNumber().getNumber(), new short[] {0, 1});
    }

    public void testHashCode()
    {
        
    }
}
