package com.espertech.esper.view.stat.olap;

import junit.framework.TestCase;
import com.espertech.esper.support.view.olap.SupportCubeFactory;

public class TestCubeImpl extends TestCase
{
    CubeImpl testCube;

    protected void setUp()
    {
        String[] measureList = {"datapoints"};
        testCube = new CubeImpl(SupportCubeFactory.make2DimSchema(), measureList);
    }

    public void testValidOrdinalsAndMembers()
    {
        Dimension[] dimensions = testCube.getDimensions();
        Cell[] measures = testCube.getMeasures();

        // Check dimensions
        assertEquals(3, dimensions.length);
        assertEquals(12, measures.length);

        // Check each ordinal
        for (int ordinal = 0; ordinal < measures.length; ordinal++)
        {
            DimensionMember[] members = testCube.getMembers(ordinal);
            int shouldBeOrdinal = testCube.getOrdinal(members);

            assertEquals(shouldBeOrdinal, ordinal);

            // Pick a couple of ordinals and check member references

            // Test results for ordinal 0
            if (ordinal == 0)
            {
                assertTrue(members[0] == dimensions[0].getMembers()[0]);
                assertTrue(members[1] == dimensions[1].getMembers()[0]);
                assertTrue(members[2] == dimensions[2].getMembers()[0]);
            }

            // Test results for ordinal 5
            if (ordinal == 5)
            {
                assertTrue(members[0] == dimensions[0].getMembers()[0]);
                assertTrue(members[1] == dimensions[1].getMembers()[1]);
                assertTrue(members[2] == dimensions[2].getMembers()[1]);
            }

            // Test results for ordinal 11
            if (ordinal == 11)
            {
                assertTrue(members[0] == dimensions[0].getMembers()[0]);
                assertTrue(members[1] == dimensions[1].getMembers()[3]);
                assertTrue(members[2] == dimensions[2].getMembers()[2]);
            }
        }
    }

    public void testInvalidGetMembers()
    {
        try
        {
            testCube.getMembers(-1);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            testCube.getMembers(testCube.getMeasures().length);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }
    }

    public void testInvalidGetOrdinal()
    {
        try
        {
            testCube.getOrdinal(null);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        try
        {
            testCube.getOrdinal(new DimensionMember[1]);
            assertTrue(false);
        }
        catch (IllegalArgumentException ex)
        {
            // Expected exception
        }

        int ordinal = testCube.getOrdinal(new DimensionMember[3]);
        assertEquals(-1, ordinal);
    }
}
