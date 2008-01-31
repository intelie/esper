package com.espertech.esper.view.stat.olap;

import junit.framework.TestCase;
import com.espertech.esper.support.view.olap.SupportCubeFactory;
import com.espertech.esper.support.util.DoubleValueAssertionUtil;
import com.espertech.esper.view.ViewFieldEnum;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.view.stat.BaseStatisticsBean;

public class TestCubeDerivedValueHelper extends TestCase
{
    public void testDerive()
    {
        // Test on a 2-dimensional cube
        MultidimCube<BaseStatisticsBean> testCube = SupportCubeFactory.make2DimSchema();

        String[] measureList = {
            ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName(),
            ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName(),
            ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName(),
            ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.getName(),
            ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.getName(),
            ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.getName()};

        // derive
        Pair<Dimension[], Cell[]> result = CubeDerivedValueHelper.derive(measureList, testCube);

        Dimension[] dimensions = result.getFirst();
        Cell[] measures = result.getSecond();

        // Now its 3-dimensional, dimension 0 contains all derived fields for each cell containing a fact
        assertTrue(dimensions.length == 3);
        assertTrue(measures.length == 12 * 6);

        // Check each dimension and member for correct references
        for (int dimension = 0; dimension < dimensions.length; dimension++)
        {
            DimensionMember[] members = dimensions[dimension].getMembers();

            for (int i = 0; i < members.length; i++)
            {
                assertTrue(members[i].getDimension() == dimensions[dimension]);
            }
        }

        // Check cell dimension member object values - should match derived measure name
        for (int i = 0; i < dimensions[0].getMembers().length; i++)
        {
            DimensionMember dimensionMember = dimensions[0].getMembers()[i];
            assertEquals(dimensionMember.getValues()[0], measureList[i]);
        }

        // Check some derived values
        double results[] = {1, 1, 1, 0, Double.NaN, Double.NaN};
        checkMeasures(results, measures, 0);

        results = new double[] {2, 10, 5, 3, 4.242640687, 18};
        checkMeasures(results, measures, 6 * 4);    // cell a-y

        results = new double[] {2, 14, 7, 3, 4.242640687, 18};
        checkMeasures(results, measures, 6 * 10);   // cell c-z
    }

    private void checkMeasures(double[] results, Cell[] measures, int offset)
    {
        for (int i = 0; i < results.length; i++)
        {
            double value = measures[offset + i].getValue();
            assertTrue(DoubleValueAssertionUtil.equals(value, results[i], 6));
        }
    }
}
