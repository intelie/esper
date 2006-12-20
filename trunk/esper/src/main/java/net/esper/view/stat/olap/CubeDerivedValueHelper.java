package net.esper.view.stat.olap;

import net.esper.view.ViewFieldEnum;
import net.esper.collection.Pair;
import net.esper.view.stat.BaseStatisticsBean;

import java.util.List;

/**
 * Strategy for deriving a measure cube from a fact cube.
 * Derives a measure cube consisting of the number of datapoints only.
 */
public final class CubeDerivedValueHelper
{
    /**
     * Derive given statistical measures from the multidim. cube returning dimensions and cells.
     * @param measuresToDerive is an array with field names for statistic functions
     * @param cube holds the n-dimensional cells
     * @return dimensions and cells
     */
    public static final Pair<Dimension[], Cell[]> derive(String[] measuresToDerive, MultidimCube<BaseStatisticsBean> cube)
    {
        int numDimensions = cube.getNumDimensions();
        String[] dimPropertyNames = cube.getDimensionNames();

        // Make dimensions
        Dimension dimensions[] = new DimensionImpl[numDimensions + 1];

        // The innermost dimension describes the cell itself.
        DimensionImpl cellDimension = new DimensionImpl(new String[] {"derived"});
        dimensions[0] = cellDimension;
        DimensionMember[] cellMembers = new DimensionMemberImpl[measuresToDerive.length];

        // Each cell has a set of derived values, such as frequency (total/count), average etc.
        for (int derivedIndex = 0; derivedIndex < measuresToDerive.length; derivedIndex++)
        {
            // Derived members have no direct member dimension value
            DimensionMemberImpl newMember = new DimensionMemberImpl(new Object[] { measuresToDerive[derivedIndex] });
            cellMembers[derivedIndex] = newMember;
            newMember.setDimension(cellDimension);
        }
        cellDimension.setMembers(cellMembers);

        // The remaining dimensions are the fact cube's dimensions
        for (int i = 0; i < numDimensions; i++)
        {
            String[] propertyNames = new String[] { dimPropertyNames[i] };

            DimensionImpl newDimension = new DimensionImpl(propertyNames);
            DimensionMember[] members = makeMembers(newDimension, cube.getMembers(i));
            dimensions[i + 1] = newDimension;
            newDimension.setMembers(members);
        }

        // Make Measures - for each fact we have N derived measures
        BaseStatisticsBean facts[] = cube.getCells();
        Cell[] measures = new Cell[facts.length * measuresToDerive.length];

        int measureOrdinal = 0;
        for (int factIndex = 0; factIndex < facts.length; factIndex++)
        {
            for (int derivedIndex = 0; derivedIndex < measuresToDerive.length; derivedIndex++)
            {
                double measureValue = getMeasure(measuresToDerive[derivedIndex], facts[factIndex]);
                measures[measureOrdinal] = new CellImpl(measureValue);
                measureOrdinal++;
            }
        }

        return new Pair<Dimension[], Cell[]>(dimensions, measures);
    }

    private static DimensionMember[] makeMembers(DimensionImpl dimension, List<Object> memberList)
    {
        DimensionMember[] members = new DimensionMember[memberList.size()];

        int index = 0;
        for (Object object : memberList)
        {
            DimensionMemberImpl newMember = new DimensionMemberImpl(new Object[] {object});
            newMember.setDimension(dimension);
            members[index] = newMember;
            index++;
        }

        return members;
    }

    private static double getMeasure(String measureName, BaseStatisticsBean statBean)
    {
        if (measureName.equals(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.getName()))
        {
            return statBean.getXAverage();
        }
        if (measureName.equals(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.getName()))
        {
            return statBean.getN();
        }
        if (measureName.equals(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.getName()))
        {
            return statBean.getXStandardDeviationSample();
        }
        if (measureName.equals(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.getName()))
        {
            return statBean.getXStandardDeviationPop();
        }
        if (measureName.equals(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.getName()))
        {
            return statBean.getXSum();
        }
        if (measureName.equals(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.getName()))
        {
            return statBean.getXVariance();
        }
        throw new IllegalArgumentException("Cell named " + measureName + " not known");
    }
}
