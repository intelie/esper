package com.espertech.esper.view.stat.olap;

import java.util.NoSuchElementException;

/**
 * Utility methods for dealing with Cube multidimensional tables (OLAP style).
 * Enables iterating within dimensions, obtaining cell count for dimension sizes etc..
 * Cubes can be 1-dimensional to n-dimensional and these utility methods deal with this variety.
 */
public final class CubeDimensionHelper
{
    /**
     * Returns the number of cells in a cube that has the given dimension sizes.
     * @param dimensionSizes is an array describing the number of dimensions and size of dimensions (number of members)
     * @return total number of cells (size)
     */
    protected static int getTotalCells(int[] dimensionSizes)
    {
        int product = 1;
        for (int i = 0; i < dimensionSizes.length; i++)
        {
            if (dimensionSizes[i] != 0)
            {
                product = product * dimensionSizes[i];
            }
        }
        return product;
    }

    /**
     * Increments the index values in the dimensionIndizes array given each dimension's size.
     * For example, if dimension sizes are [3, 2, 4] then the indizes follow this order:
     * [0,0,0]   [1,0,0]  [2,0,0]  [0,1,0] ... [1,0,3] [2,0,3] [0,1,3]  [1,1,3]  [2,1,3]
     * This example shows indize start and end ranges.
     * @param dimensionSizes size of each dimension
     * @param dimensionIndizes index value for each dimension, each value between and dimension size - 1
     * @throws NoSuchElementException when an attempt is made to position indizes past the max
     */
    protected static void nextIndize(int[] dimensionSizes, int[] dimensionIndizes)
    {
        int dimension = 0;

        while(true)
        {
            dimensionIndizes[dimension]++;

            if (dimensionIndizes[dimension] < dimensionSizes[dimension])
            {
                break;
            }

            dimensionIndizes[dimension] = 0;
            dimension++;

            if (dimension >= dimensionSizes.length)
            {
                throw new NoSuchElementException("Attempt to position past indize range");
            }
        }
    }

    /**
     * Compile an array of dimension sizes. The array contains as many elements as there are dimensions.
     * The size of each dimension is returned in each array element.
     * For example, a return value of [2,3,4] denotes a 3 dimensions where the first dimension
     * has 2 members, the second dimension has 3 members, and the 3rd dimension has 4 members.
     * @param dimensions is an array of dimensions
     * @return integer array with size of each dimension
     */
    protected static int[] getDimensionSizes(Dimension[] dimensions)
    {
        int[] dimensionSizes = new int[dimensions.length];
        for (int i = 0; i < dimensions.length; i++)
        {
            dimensionSizes[i] = dimensions[i].getMembers().length;
        }
        return dimensionSizes;
    }

    /**
     * Given index values for each dimension and all dimension sizes this method returns an ordinal value.
     * For example, for dimensionIndizes [1, 4, 3] and sizes [2, 5, 4] the ordinal is 1 + 4 * 2 + 3 * 10.
     * @param dimensionIndizes index value for each dimension, each value between and dimension size - 1
     * @param dimensionSizes size of each dimension
     * @return cell ordinal, between 0 and   (dimensionSizes[0] + dimensionSizes[1] * dimensionSizes[0] etc.)  
     */
    protected static int getOrdinal(int[] dimensionIndizes, int[] dimensionSizes)
    {
        int ordinal = dimensionIndizes[0];
        int offset = 1;

        for (int i = 1; i < dimensionIndizes.length; i++)
        {
            offset = offset * dimensionSizes[i - 1];
            ordinal = ordinal + dimensionIndizes[i] * offset;
        }

        return ordinal;
    }

}
