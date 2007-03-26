using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.view.stat;

namespace net.esper.view.stat.olap
{
	/// <summary> Cube implementation derives the cells of the cube from a measures list and a {@link MultidimCube} containing
	/// {@link BaseStatisticsBean}.
	/// </summary>

    public sealed class CubeImpl : Cube
    {
        /// <summary>
        /// Returns dimensions. Implementations have at least 1 dimension and can be n-dimensional.
        /// </summary>
        /// <value></value>
        /// <returns> dimension array
        /// </returns>
        public IList<Dimension> Dimensions
        {
            get
            {
                if (dimensions == null)
                {
                    derive();
                }
                return dimensions;
            }

        }

        /// <summary>
        /// Returns all measures.
        /// Individual measures can be retrieved directly by indexing into the array of measures.
        /// A formual for calculating an ordinal for a 3-dimensional cube is as follows:
        /// ordinal = dimension[0].index + dimension[1].index * dimension[0].size +
        /// dimension[2].index * dimension[0].size * dimension[1].size;
        /// </summary>
        /// <value></value>
        /// <returns> array of measures
        /// </returns>
        public IList<Cell> Measures
        {
            get
            {
                if (measures == null)
                {
                    derive();
                }
                return measures;
            }
        }

        private readonly MultidimCube<BaseStatisticsBean> cube;
        private readonly String[] measuresToDerive;

        // Dimensions and Measures are derived once from a cube schema
        private Dimension[] dimensions;
        private Cell[] measures;

        // Keep a mapping of measure ordinal to dimension members defining the intersection
        private IDictionary<Int32, DimensionMember[]> intersections;

        /// <summary> Constructor. Takes a fact cube schema and a derivation strategy to be used when
        /// a measure cube needs to be derived.
        /// </summary>
        /// <param name="cube">contains the fact cube and related into
        /// </param>
        /// <param name="measuresToDerive">a list of well-named measures to derive
        /// </param>

        public CubeImpl(MultidimCube<BaseStatisticsBean> cube, String[] measuresToDerive)
        {
            this.cube = cube;
            this.measuresToDerive = measuresToDerive;
        }

        /// <summary>
        /// Returns the member value for each dimension that intersect to identify the cell of the given ordinal.
        /// </summary>
        /// <param name="ordinal">is the cell ordinal, Starting at zero and with a max value of Cell[].Length - 1.</param>
        /// <returns>
        /// member values matching the number of dimensions that intersect to identify the cell
        /// </returns>
        public IList<DimensionMember> GetMembers(int ordinal)
        {
            if ((ordinal < 0) || (ordinal >= measures.Length))
            {
                throw new ArgumentException("Invalid ordinal value");
            }

            if (measures == null)
            {
                derive();
            }

            if (intersections == null)
            {
                determineIntersections();
            }

            return intersections[ordinal];
        }

        /// <summary>
        /// Given the the members of each dimension that define the intersection, returns the ordinal of a measure.
        /// </summary>
        /// <param name="members">is an array of members within each dimension that intersect and thus define the cell
        /// position</param>
        /// <returns>
        /// ordinal Starts at zero and ends at Cell[].Length - 1. A -1 is returned if the intersection
        /// could not be determined, such as when a dimension member could not be located.
        /// </returns>
        public int GetOrdinal(IList<DimensionMember> members)
        {
            if (measures == null)
            {
                derive();
            }

            if (members == null)
            {
                throw new ArgumentException("DimensionMember array reference is null");
            }

            if (members.Count != dimensions.Length)
            {
                throw new ArgumentException("Size of member array does not match number of cube dimensions");
            }

            int[] dimensionIndizes = new int[dimensions.Length];

            for (int dimension = 0; dimension < dimensionIndizes.Length; dimension++)
            {
                int memberIndex = findMember(dimensions[dimension].GetMembers(), members[dimension]);

                if (memberIndex < 0)
                {
                    return -1;
                }

                dimensionIndizes[dimension] = memberIndex;
            }

            return CubeDimensionHelper.GetOrdinal(dimensionIndizes, CubeDimensionHelper.GetDimensionSizes(dimensions));
        }

        /// <summary>
        /// Derive from the fact cube the measure cube using the given strategy.
        /// </summary>

        private void derive()
        {
            Pair<Dimension[], Cell[]> derivedCube = CubeDerivedValueHelper.derive(measuresToDerive, cube);
            dimensions = derivedCube.First;
            measures = derivedCube.Second;
        }

        /// <summary> Compile a map of ordinal to member array for fast lookup of the dimension members defining
        /// an intersection based on the measure array ordinal.
        /// </summary>

        private void determineIntersections()
        {
            intersections = new Dictionary<Int32, DimensionMember[]>();

            // Loop through all ordinals, increment dimension indizes
            int[] dimensionIndizes = new int[dimensions.Length];
            int[] dimensionSizes = CubeDimensionHelper.GetDimensionSizes(dimensions);

            for (int ordinal = 0; ordinal < measures.Length; ordinal++)
            {
                // Determine members for each
                DimensionMember[] members = new DimensionMember[dimensions.Length];
                for (int dimension = 0; dimension < members.Length; dimension++)
                {
                    int memberIndex = dimensionIndizes[dimension];
                    members[dimension] = dimensions[dimension].GetMembers()[memberIndex];
                }

                // Next indize
                if (ordinal + 1 < measures.Length)
                {
                    CubeDimensionHelper.NextIndize(dimensionSizes, dimensionIndizes);
                }

                intersections[ordinal] = members;
            }
        }

        /// <summary> Find a dimensionMemberionMember reference in the array of members returning the index of the dimensionMemberionMember in the dimension.</summary>
        /// <param name="members">are members in a dimension
        /// </param>
        /// <param name="dimensionMember">is the dimensionMember to find
        /// </param>
        /// <returns> index of dimensionMember in dimension or -1 if not found
        /// </returns>

        private static int findMember(DimensionMember[] members, DimensionMember dimensionMember)
        {
            for (int i = 0; i < members.Length; i++)
            {
                if (members[i] == dimensionMember)
                {
                    return i;
                }
            }
            return -1;
        }
    }
}
