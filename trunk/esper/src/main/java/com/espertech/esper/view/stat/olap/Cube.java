/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.view.stat.olap;

/**
 * Interface for querying multidimensional data.
 * {@link Dimension} presents information about the dimensionality of the data.
 * Cells are {@link Cell} instances.
 * The identification of cell in the cube occurs by member values for each dimension.
 */
public interface Cube
{
    /**
     * Returns all measures.
     * Individual measures can be retrieved directly by indexing into the array of measures.
     * A formual for calculating an ordinal for a 3-dimensional cube is as follows:
     *      ordinal = dimension[0].index + dimension[1].index * dimension[0].size +
     *                  dimension[2].index * dimension[0].size * dimension[1].size;
     * @return array of measures
     */
    public Cell[] getMeasures();

    /**
     * Returns dimensions. Implementations have at least 1 dimension and can be n-dimensional.
     * @return dimension array
     */
    public Dimension[] getDimensions();

    /**
     * Given the the members of each dimension that define the intersection, returns the ordinal of a measure.
     * @param members is an array of members within each dimension that intersect and thus define the cell
     * position
     * @return ordinal starts at zero and ends at Cell[].length - 1. A -1 is returned if the intersection
     * could not be determined, such as when a dimension member could not be located.
     */
    public int getOrdinal(DimensionMember[] members);

    /**
     * Returns the member value for each dimension that intersect to identify the cell of the given ordinal.
     * @param ordinal is the cell ordinal, starting at zero and with a max value of Cell[].length - 1.
     * @return member values matching the number of dimensions that intersect to identify the cell
     */
    public DimensionMember[] getMembers(int ordinal);
}
