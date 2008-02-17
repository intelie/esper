package com.espertech.esper.view.stat.olap;

/**
 * Implementations provide a factory for cells to use by {@link MultidimCubeImpl}.
 */
public interface MultidimCubeCellFactory<V>
{
    /**
     * Supplies an instance of the object representing a cell.
     * @return cell object
     */
    public V newCell();

    /**
     * Supplies an array of the type of object representing a cell. The returned array
     * should be uninitialized.
     * @param numElements number of elements
     * @return cell object array
     */
    public V[] newCells(int numElements);
}
