using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.view;
using net.esper.view.stat;

namespace net.esper.view.stat.olap
{
    /// <summary> Strategy for deriving a measure cube from a fact cube.
    /// Derives a measure cube consisting of the number of datapoints only.
    /// </summary>
    public sealed class CubeDerivedValueHelper
    {
        /// <summary> Derive given statistical measures from the multidim. cube returning dimensions and cells.</summary>
        /// <param name="measuresToDerive">is an array with field names for statistic functions
        /// </param>
        /// <param name="cube">holds the n-dimensional cells
        /// </param>
        /// <returns> dimensions and cells
        /// </returns>
        public static Pair<Dimension[], Cell[]> Derive(String[] measuresToDerive, MultidimCube<BaseStatisticsBean> cube)
        {
            int numDimensions = cube.NumDimensions;
            IList<String> dimPropertyNames = cube.DimensionNames;

            // Make dimensions
            Dimension[] dimensions = new DimensionImpl[numDimensions + 1];

            // The innermost dimension describes the cell itself.
            DimensionImpl cellDimension = new DimensionImpl(new String[] { "derived" });
            dimensions[0] = cellDimension;
            DimensionMember[] cellMembers = new DimensionMemberImpl[measuresToDerive.Length];

            // Each cell has a set of derived values, such as frequency (total/count), average etc.
            for (int derivedIndex = 0; derivedIndex < measuresToDerive.Length; derivedIndex++)
            {
                // Derived members have no direct member dimension value
                DimensionMemberImpl newMember = new DimensionMemberImpl(new Object[] { measuresToDerive[derivedIndex] });
                cellMembers[derivedIndex] = newMember;
                newMember.SetDimension(cellDimension);
            }
            cellDimension.setMembers(cellMembers);

            // The remaining dimensions are the fact cube's dimensions
            for (int i = 0; i < numDimensions; i++)
            {
                String[] propertyNames = new String[] { dimPropertyNames[i] };

                DimensionImpl newDimension = new DimensionImpl(propertyNames);
                DimensionMember[] members = makeMembers(newDimension, cube.GetMembers(i));
                dimensions[i + 1] = newDimension;
                newDimension.setMembers(members);
            }

            // Make Measures - for each fact we have N derived measures
            BaseStatisticsBean[] facts = cube.Cells;
            Cell[] measures = new Cell[facts.Length * measuresToDerive.Length];

            int measureOrdinal = 0;
            for (int factIndex = 0; factIndex < facts.Length; factIndex++)
            {
                for (int derivedIndex = 0; derivedIndex < measuresToDerive.Length; derivedIndex++)
                {
                    double measureValue = getMeasure(measuresToDerive[derivedIndex], facts[factIndex]);
                    measures[measureOrdinal] = new CellImpl(measureValue);
                    measureOrdinal++;
                }
            }

            return new Pair<Dimension[], Cell[]>(dimensions, measures);
        }

        private static DimensionMember[] makeMembers(DimensionImpl dimension, IList<Object> memberList)
        {
            DimensionMember[] members = new DimensionMember[memberList.Count];

            int index = 0;
            foreach (Object obj in memberList)
            {
                DimensionMemberImpl newMember = new DimensionMemberImpl(new Object[] { obj });
                newMember.SetDimension(dimension);
                members[index] = newMember;
                index++;
            }

            return members;
        }

        private static double getMeasure(String measureName, BaseStatisticsBean statBean)
        {
            if (measureName.Equals(ViewFieldEnum.UNIVARIATE_STATISTICS__AVERAGE.Name))
            {
                return statBean.XAverage;
            }
            if (measureName.Equals(ViewFieldEnum.UNIVARIATE_STATISTICS__COUNT.Name))
            {
                return statBean.N;
            }
            if (measureName.Equals(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEV.Name))
            {
                return statBean.XStandardDeviationSample;
            }
            if (measureName.Equals(ViewFieldEnum.UNIVARIATE_STATISTICS__STDDEVPA.Name))
            {
                return statBean.XStandardDeviationPop;
            }
            if (measureName.Equals(ViewFieldEnum.UNIVARIATE_STATISTICS__SUM.Name))
            {
                return statBean.XSum;
            }
            if (measureName.Equals(ViewFieldEnum.UNIVARIATE_STATISTICS__VARIANCE.Name))
            {
                return statBean.XVariance;
            }
            throw new ArgumentException("Cell named " + measureName + " not known");
        }
    }
}