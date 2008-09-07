package com.espertech.esper.view.stat.olap;

import junit.framework.TestCase;
import com.espertech.esper.support.view.olap.SupportCubeFactory;
import com.espertech.esper.view.stat.olap.Cube;

public class TestCubeDimensionHelper extends TestCase
{
    public void testGetDimensionSizes()
    {
        Cube testCube = SupportCubeFactory.make2DimCube();
        int[] dimensionSizes = CubeDimensionHelper.getDimensionSizes(testCube.getDimensions());

        assertEquals(3, dimensionSizes.length);
        assertEquals(3, dimensionSizes[0]); // 3 derived values
        assertEquals(4, dimensionSizes[1]);
        assertEquals(3, dimensionSizes[2]);
    }

    public void testNextIndize()
    {
        int[] indizes = new int[3];

        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                for (int k = 0; k < 4; k++)
                {
                    assertEquals(k, indizes[0]);
                    assertEquals(j, indizes[1]);
                    assertEquals(i, indizes[2]);

                    if (i == 4 && j == 2 && k == 3)
                    {
                        break;
                    }
                    CubeDimensionHelper.nextIndize(new int[] {4, 3, 5}, indizes);
                }
            }
        }
    }

    public void testCalculateTotalSize()
    {
        assertEquals(3, CubeDimensionHelper.getTotalCells(new int[] {3}));
        assertEquals(6, CubeDimensionHelper.getTotalCells(new int[] {3, 2}));
        assertEquals(30, CubeDimensionHelper.getTotalCells(new int[] {3, 2, 5}));
        assertEquals(60, CubeDimensionHelper.getTotalCells(new int[] {3, 2, 5, 2}));
        assertEquals(60, CubeDimensionHelper.getTotalCells(new int[] {3, 2, 5, 2, 1}));
        assertEquals(120, CubeDimensionHelper.getTotalCells(new int[] {3, 2, 5, 2, 1, 2}));
    }

    public void testCalculateOrdinal()
    {
        // 1-dimensional
        assertEquals(0, CubeDimensionHelper.getOrdinal(new int[] {0}, new int[] {3}));
        assertEquals(1, CubeDimensionHelper.getOrdinal(new int[] {1}, new int[] {3}));
        assertEquals(2, CubeDimensionHelper.getOrdinal(new int[] {2}, new int[] {5}));

        // 2-dimensional
        assertEquals(0, CubeDimensionHelper.getOrdinal(new int[] {0, 0}, new int[] {4, 3}));
        assertEquals(2 + 2 * 4, CubeDimensionHelper.getOrdinal(new int[] {2, 2}, new int[] {4, 3}));
        assertEquals(3 + 1 * 4, CubeDimensionHelper.getOrdinal(new int[] {3, 1}, new int[] {4, 3}));

        // 3-dimensional
        assertEquals(0 + 3 * 4 + 1 * 12, CubeDimensionHelper.getOrdinal(new int[] {0, 3, 1}, new int[] {4, 3, 5}));
        assertEquals(3 + 2 * 4 + 4 * 12, CubeDimensionHelper.getOrdinal(new int[] {3, 2, 4}, new int[] {4, 3, 5}));

        // 4-dimensional
        assertEquals(0 + 3 * 4 + 1 * 12 + 1 * 60, CubeDimensionHelper.getOrdinal(new int[] {0, 3, 1, 1}, new int[] {4, 3, 5, 2}));
        assertEquals(3 + 0 * 4 + 1 * 12 + 1 * 60, CubeDimensionHelper.getOrdinal(new int[] {3, 0, 1, 1}, new int[] {4, 3, 5, 2}));
    }
}
