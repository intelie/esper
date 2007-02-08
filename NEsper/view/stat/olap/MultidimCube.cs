using System;
using System.Collections.Generic;

using MultiKeyUntyped = net.esper.collection.MultiKeyUntyped;

namespace net.esper.view.stat.olap
{
	/// <summary> Interface for storage and access to multi-dimensional data.
	/// Implementations store cells for each of multiple dimensions.
	/// Implementations have a configurable number of dimensions, and the number of dimensions doesn't change.
	/// Cell objects are supplied via the template.
	/// 
	/// The identification of a cell in the cube occurs by
	/// member values for each dimension. The MultiKeyUntyped class is used to supply these member values for all dimensions,
	/// also referred to as coordinates. Each Object in the MultiKeyUntyped is the (new or existng) member of a single dimension.
	/// 
	/// Implementations typically supports 1 to an unlimited number of dimensions.
	/// Implementations can typically grows the members in each dimension as new dimension members become known.
	/// The members of each dimension can be supplied via a setter method.
	/// This is an example of a 2-dimensional cube.
	/// The numbers in [] brackets are indizes per dimension, ie. [n,m] where n=dimension zero index and m=dimension one index.
	/// The number in each cell is the ordinal between 0 and 11.
	/// 
	/// a           b           c           d
	/// ===         ===         ===         ===
	/// x       [0,0] 0     [1,0] 1     [2,0] 2     [3,0] 3
	/// y             4           5           6     [3,1] 7
	/// z             8           9          10     [3,2] 11
	/// 
	/// Example: looking for (d,y) yields [3,1] with ordinal 7.
	/// </summary>

    public interface MultidimCube<V>
    {
        /// <summary> Get the number of dimensions of the cube. The minimum number of dimensions is 1.</summary>
        /// <returns> number of dimensions
        /// </returns>
        int NumDimensions { get; }

        /// <summary> Returns array containing name of each dimension including the name of the cell.
        /// The array size is getNumDimensions() + 1, with the first element as the cell name.
        /// </summary>
        /// <returns> dimension names array
        /// </returns>
        IList<String> DimensionNames { get; }

        /// <summary> Set dimension members from the enumeration Class.</summary>
        /// <param name="dimension">Starts at 0 and has a max of number of dimensions minus 1
        /// </param>
        /// <param name="enumType">is the class for which the enum constants are obtained, and used as members
        /// </param>
        void SetMembers(int dimension, Type enumType);

        /// <summary> Set dimension members from the list of value objects.</summary>
        /// <param name="dimension">Starts at 0 and has a max of number of dimensions minus 1
        /// </param>
        /// <param name="members">is a list of objects making up the dimension member values
        /// </param>
        void SetMembers(int dimension, IList<Object> members);

        /// <summary> Get the members making up a dimension.</summary>
        /// <param name="dimension">for which to return the members
        /// </param>
        /// <returns> list of member object of the dimension
        /// </returns>
        IList<Object> GetMembers(int dimension);

        /// <summary> Get a cell, returns null if the cell does not yet exist.</summary>
        /// <param name="coordinates">contains member values for each dimension of the cube
        /// </param>
        /// <returns> the cell
        /// </returns>
        V GetCell(MultiKeyUntyped coordinates);

        /// <summary> Get a cell adding the coordinate members if the cell does not yet exist.</summary>
        /// <param name="coordinates">contains member values for each dimension of the cube
        /// </param>
        /// <returns> the cell
        /// </returns>
        V GetCellAddMembers(MultiKeyUntyped coordinates);

        /// <summary> Returns all cells.</summary>
        /// <returns> cell array
        /// </returns>
        V[] Cells { get; }
    }
}