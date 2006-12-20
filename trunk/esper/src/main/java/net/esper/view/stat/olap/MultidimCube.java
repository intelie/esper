package net.esper.view.stat.olap;

import net.esper.collection.MultiKeyUntyped;

import java.util.*;

/**
 * Interface for storage and access to multi-dimensional data.
 * Implementations store cells for each of multiple dimensions.
 * Implementations have a configurable number of dimensions, and the number of dimensions doesn't change.
 * Cell objects are supplied via the template.
 *
 * The identification of a cell in the cube occurs by
 * member values for each dimension. The MultiKeyUntyped class is used to supply these member values for all dimensions,
 * also referred to as coordinates. Each Object in the MultiKeyUntyped is the (new or existng) member of a single dimension.
 *
 * Implementations typically supports 1 to an unlimited number of dimensions.
 * Implementations can typically grows the members in each dimension as new dimension members become known.
 * The members of each dimension can be supplied via a setter method.

 * This is an example of a 2-dimensional cube.
 * The numbers in [] brackets are indizes per dimension, ie. [n,m] where n=dimension zero index and m=dimension one index.
 * The number in each cell is the ordinal between 0 and 11.
 *
 *                    a           b           c           d
 *                  ===         ===         ===         ===
 *      x       [0,0] 0     [1,0] 1     [2,0] 2     [3,0] 3
 *      y             4           5           6     [3,1] 7
 *      z             8           9          10     [3,2] 11
 *
 * Example: looking for (d,y) yields [3,1] with ordinal 7.
 */
public interface MultidimCube<V>
{
    /**
     * Get the number of dimensions of the cube. The minimum number of dimensions is 1.
     * @return number of dimensions
     */
    public int getNumDimensions();

    /**
     * Returns array containing name of each dimension including the name of the cell.
     * The array size is getNumDimensions() + 1, with the first element as the cell name.
     * @return dimension names array
     */
    public String[] getDimensionNames();

    /**
     * Set dimension members from the enumeration Class.
     * @param dimension starts at 0 and has a max of number of dimensions minus 1
     * @param enumType is the class for which the enum constants are obtained, and used as members
     */
    public void setMembers(int dimension, Class enumType);

    /**
     * Set dimension members from the list of value objects.
     * @param dimension starts at 0 and has a max of number of dimensions minus 1
     * @param members is a list of objects making up the dimension member values
     */
    public void setMembers(int dimension, List<Object> members);

    /**
     * Get the members making up a dimension.
     * @param dimension for which to return the members
     * @return list of member object of the dimension
     */
    public List<Object> getMembers(int dimension);

    /**
     * Get a cell, returns null if the cell does not yet exist.
     * @param coordinates contains member values for each dimension of the cube
     * @return the cell
     */
    public V getCell(MultiKeyUntyped coordinates);

    /**
     * Get a cell adding the coordinate members if the cell does not yet exist.
     * @param coordinates contains member values for each dimension of the cube
     * @return the cell
     */
    public V getCellAddMembers(MultiKeyUntyped coordinates);

    /**
     * Returns all cells.
     * @return cell array
     */
    public V[] getCells();
}
