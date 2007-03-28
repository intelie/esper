using System;

namespace net.esper.view.stat.olap
{
	/// <summary> Implementations provide a factory for cells to use by <seealso cref="MultidimCubeImpl"/>.</summary>
    public interface MultidimCubeCellFactory<V>
    {
        /// <summary> Supplies an instance of the object representing a cell.</summary>
        /// <returns> cell object
        /// </returns>
        V NewCell();

        /// <summary> Supplies an array of the type of object representing a cell. The returned array
        /// should be uninitialized.
        /// </summary>
        /// <param name="numElements">number of elements
        /// </param>
        /// <returns> cell object array
        /// </returns>
        V[] NewCells(int numElements);
    }
}
