package com.espertech.esper.view.stat.olap;

import java.util.Map;
import java.util.HashMap;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.view.stat.BaseStatisticsBean;
import com.espertech.esper.util.MetaDefItem;

/**
 * Cube implementation derives the cells of the cube from a measures list and a {@link MultidimCube} containing
 * {@link BaseStatisticsBean}.
 */
public final class CubeImpl implements Cube, MetaDefItem
{
    private final MultidimCube<BaseStatisticsBean> cube;
    private final String[] measuresToDerive;

    // Dimensions and Measures are derived once from a cube schema
    private Dimension[] dimensions;
    private Cell[] measures;

    // Keep a mapping of measure ordinal to dimension members defining the intersection
    private Map<Integer, DimensionMember[]> intersections;

    /**
     * Constructor. Takes a fact cube schema and a derivation strategy to be used when
     * a measure cube needs to be derived.
     * @param cube contains the fact cube and related into
     * @param measuresToDerive a list of well-named measures to derive
     */
    public CubeImpl(MultidimCube<BaseStatisticsBean> cube, String[] measuresToDerive)
    {
        this.cube = cube;
        this.measuresToDerive = measuresToDerive;
    }

    public final Dimension[] getDimensions()
    {
        if (dimensions == null)
        {
            derive();
        }
        return dimensions;
    }

    public final Cell[] getMeasures()
    {
        if (measures == null)
        {
            derive();
        }
        return measures;
    }

    public final DimensionMember[] getMembers(int ordinal)
    {
        if ((ordinal < 0) || ((measures != null) && (ordinal >= measures.length)))
        {
            throw new IllegalArgumentException("Invalid ordinal value");
        }

        if (measures == null)
        {
            derive();
        }

        if (intersections == null)
        {
            determineIntersections();
        }

        return intersections.get(ordinal);
    }

    public final int getOrdinal(DimensionMember[] members)
    {
        if (measures == null)
        {
            derive();
        }

        if (members == null)
        {
            throw new IllegalArgumentException("DimensionMember array reference is null");
        }

        if (members.length != dimensions.length)
        {
            throw new IllegalArgumentException("Size of member array does not match number of cube dimensions");
        }

        int dimensionIndizes[] = new int[dimensions.length];

        for (int dimension = 0; dimension < dimensionIndizes.length; dimension++)
        {
            int memberIndex = findMember(dimensions[dimension].getMembers(), members[dimension]);

            if (memberIndex < 0)
            {
                return -1;
            }

            dimensionIndizes[dimension] = memberIndex;
        }

        return CubeDimensionHelper.getOrdinal(dimensionIndizes, CubeDimensionHelper.getDimensionSizes(dimensions));
    }

    /**
     * Derive from the fact cube the measure cube using the given strategy.
     */
    private void derive()
    {
        Pair<Dimension[], Cell[]> derivedCube = CubeDerivedValueHelper.derive(measuresToDerive, cube);
        dimensions = derivedCube.getFirst();
        measures = derivedCube.getSecond();
    }

    /**
     * Compile a map of ordinal to member array for fast lookup of the dimension members defining
     * an intersection based on the measure array ordinal.
     */
    private void determineIntersections()
    {
        intersections = new HashMap<Integer, DimensionMember[]>();

        // Loop through all ordinals, increment dimension indizes
        int[] dimensionIndizes = new int[dimensions.length];
        int[] dimensionSizes = CubeDimensionHelper.getDimensionSizes(dimensions);

        for (int ordinal = 0; ordinal < measures.length; ordinal++)
        {
            // Determine members for each
            DimensionMember[] members = new DimensionMember[dimensions.length];
            for (int dimension = 0; dimension < members.length; dimension++)
            {
                int memberIndex = dimensionIndizes[dimension];
                members[dimension] = dimensions[dimension].getMembers()[memberIndex];
            }

            // Next indize
            if (ordinal + 1 < measures.length)
            {
                CubeDimensionHelper.nextIndize(dimensionSizes, dimensionIndizes);
            }

            intersections.put(ordinal, members);
        }
    }

    /**
     * Find a dimensionMemberionMember reference in the array of members returning the index of the dimensionMemberionMember in the dimension.
     * @param members are members in a dimension
     * @param dimensionMember is the dimensionMember to find
     * @return index of dimensionMember in dimension or -1 if not found
     */
    private static int findMember(DimensionMember[] members, DimensionMember dimensionMember)
    {
        for (int i = 0; i < members.length; i++)
        {
            if (members[i] == dimensionMember)
            {
                return i;
            }
        }
        return -1;
    }
}
