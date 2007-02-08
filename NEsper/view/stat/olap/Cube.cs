using System;
using System.Collections.Generic;

namespace net.esper.view.stat.olap
{
	/// <summary> Interface for querying multidimensional data.
	/// {@link Dimension} presents information about the dimensionality of the data.
	/// Cells are {@link Cell} instances.
	/// The identification of cell in the cube occurs by member values for each dimension.
	/// </summary>

    public interface Cube
	{
		/// <summary> Returns all measures.
		/// Individual measures can be retrieved directly by indexing into the array of measures.
		/// A formual for calculating an ordinal for a 3-dimensional cube is as follows:
		/// ordinal = dimension[0].index + dimension[1].index * dimension[0].size +
		/// dimension[2].index * dimension[0].size * dimension[1].size;
		/// </summary>
		/// <returns> array of measures
		/// </returns>

        IList<Cell> Measures
		{
			get;
			
		}
		
        /// <summary> Returns dimensions. Implementations have at least 1 dimension and can be n-dimensional.</summary>
		/// <returns> dimension array
		/// </returns>
		
        IList<Dimension> Dimensions
		{
			get;
			
		}
		
		/// <summary> Given the the members of each dimension that define the intersection, returns the ordinal of a measure.</summary>
		/// <param name="members">is an array of members within each dimension that intersect and thus define the cell
		/// position
		/// </param>
		/// <returns> ordinal Starts at zero and ends at Cell[].Length - 1. A -1 is returned if the intersection
		/// could not be determined, such as when a dimension member could not be located.
		/// </returns>
		
        int GetOrdinal(IList<DimensionMember> members);
		
		/// <summary> Returns the member value for each dimension that intersect to identify the cell of the given ordinal.</summary>
		/// <param name="ordinal">is the cell ordinal, Starting at zero and with a max value of Cell[].Length - 1.
		/// </param>
		/// <returns> member values matching the number of dimensions that intersect to identify the cell
		/// </returns>
		
        IList<DimensionMember> GetMembers(int ordinal);
	}
}