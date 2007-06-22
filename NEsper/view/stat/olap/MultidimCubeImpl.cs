using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.collection;

namespace net.esper.view.stat.olap
{
	/// <summary> Implementation for a multidimensional cube that can be configured with the type of cell to store.
	/// 
	/// This cube implementation grows in each dimension as new dimension members are made known to the cube.
	/// If cells are added for dimension members that have not been encountered before,
	/// then the new dimension members are added to the known dimension members and the cube is redimensioned, ie. grows.
	/// 
	/// In design of this class, performance is important in the following areas:
	/// - Access to cells should be very fast in the normal case.
	/// Normal case is that the cell for all dimension members (the particular coordinate) already exists.
	/// This class uses a map of dimension member set (coordinates) to fact array ordinal which gets great performance.
	/// </summary>

	public sealed class MultidimCubeImpl<V> : MultidimCube<V>
	{
		private readonly int numDimensions;

		// Fact array, grows when new dimension members are encountered
		private V[] cells;

		private readonly MultidimCubeCellFactory<V> multidimCubeCellFactory;

		// Store for each coordinate the ordinal index into the fact array
		private readonly EDictionary<MultiKeyUntyped, Int32> ordinals = new HashDictionary<MultiKeyUntyped, Int32>();

		// Store for each dimension the list of members
		private readonly EDictionary<Int32, IList<Object>> dimensionMembers = new HashDictionary<Int32, IList<Object>>();

		private String[] dimensionNames;

        /// <summary>
        /// Returns array containing name of each dimension including the name of the cell.
        /// The array size is getNumDimensions() + 1, with the first element as the cell name.
        /// </summary>
        /// <value></value>
        /// <returns> dimension names array
        /// </returns>
		public IList<String> DimensionNames
		{
			get { return dimensionNames; }
		}

        /// <summary>
        /// Get the number of dimensions of the cube. The minimum number of dimensions is 1.
        /// </summary>
        /// <value></value>
        /// <returns> number of dimensions
        /// </returns>
		public int NumDimensions
		{
			get { return numDimensions; }
		}

        /// <summary>
        /// Returns all cells.
        /// </summary>
        /// <value></value>
        /// <returns> cell array
        /// </returns>
		public V[] Cells
		{
			get { return cells; }
		}

        /// <summary>
        /// Gets the dimension sizes.
        /// </summary>
        /// <value>The dimension sizes.</value>
		private int[] DimensionSizes
		{
			get
			{
				int[] dimensionSizes = new int[this.numDimensions];
				for ( int i = 0 ; i < this.numDimensions ; i++ )
				{
					dimensionSizes[i] = dimensionMembers[ i ].Count;
				}
				return dimensionSizes;
			}
		}

		/// <summary> Constructor.</summary>
		/// <param name="dimensionNames">is the name of each dimension - and dictates the number of dimensions
		/// </param>
		/// <param name="multidimCubeCellFactory">is the factory for cube cells
		/// </param>

		public MultidimCubeImpl( String[] dimensionNames, MultidimCubeCellFactory<V> multidimCubeCellFactory )
		{
			if ( dimensionNames.Length < 2 )
			{
				throw new ArgumentException( "Cannot create a cube with less then 1 dimensions" );
			}

			this.numDimensions = dimensionNames.Length - 1;

			this.dimensionNames = dimensionNames;
			this.multidimCubeCellFactory = multidimCubeCellFactory;
			this.cells = multidimCubeCellFactory.NewCells( 0 );

			for ( int i = 0 ; i < numDimensions ; i++ )
			{
				this.dimensionMembers[i] = new List<Object>() ;
			}
		}

        /// <summary>
        /// Set dimension members from the enumeration Class.
        /// </summary>
        /// <param name="dimension">Starts at 0 and has a max of number of dimensions minus 1</param>
        /// <param name="enumType">is the class for which the enum constants are obtained, and used as members</param>
		public void SetMembers( int dimension, Type enumType )
		{
            if (enumType.IsEnum)
            {
                Array enumConstants = Enum.GetValues(enumType);
                if (enumConstants != null)
                {
                    SetMembers(dimension, enumConstants);
                }
            }
		}

        /// <summary>
        /// Sets the members.
        /// </summary>
        /// <param name="dimension">The dimension.</param>
        /// <param name="members">The members.</param>
        public void SetMembers(int dimension, Array members)
        {
            List<Object> tempList = new List<Object>();
            foreach (Object temp in members)
            {
                tempList.Add(temp);
            }

            SetMembers(dimension, tempList);
        }

        /// <summary>
        /// Set dimension members from the list of value objects.
        /// </summary>
        /// <param name="dimension">Starts at 0 and has a max of number of dimensions minus 1</param>
        /// <param name="members">is a list of objects making up the dimension member values</param>
		public void SetMembers( int dimension, IList<Object> members )
		{
			IList<Object> memberKeys = dimensionMembers.Fetch( dimension );

			// Check dimension
			if ( ( dimension < 0 ) || ( dimension > ( numDimensions - 1 ) ) )
			{
				throw new ArgumentException( "Cannot add dimension members for given dimension - given dimension is out of range" );
			}

			// If members already existed, disallow setting members
			if ( memberKeys.Count == 0 )
			{
				throw new ArgumentException( "Cannot add dimension members - dimension members already existed, merge not supported" );
			}

			// If ordinals (cells) already existed, disallow setting members
			if ( ordinals.Count == 0 )
			{
				throw new ArgumentException( "Cannot add dimension members - cells already existed, merge not supported" );
			}

			// Add members
			foreach ( Object member in members )
			{
				memberKeys.Add( member );
			}

			// Compute the new array size
			int currentSize = cells.Length;
			int newSize = currentSize * members.Count;
			if ( currentSize == 0 )
			{
				newSize = members.Count;
			}

			// Initialize new array
			V[] newFacts = multidimCubeCellFactory.NewCells( newSize );
			if ( currentSize > 0 )
			{
				Array.Copy(
					this.cells, 0,
					newFacts, 0,
					currentSize );
			}
			for ( int i = currentSize ; i < newFacts.Length ; i++ )
			{
				newFacts[i] = multidimCubeCellFactory.NewCell();
			}

			this.cells = newFacts;
		}

        /// <summary>
        /// Get the members making up a dimension.
        /// </summary>
        /// <param name="dimension">for which to return the members</param>
        /// <returns>list of member object of the dimension</returns>
		public IList<Object> GetMembers( int dimension )
		{
			return dimensionMembers[dimension];
		}

        /// <summary>
        /// Get a cell, returns null if the cell does not yet exist.
        /// </summary>
        /// <param name="coordinates">contains member values for each dimension of the cube</param>
        /// <returns>the cell</returns>
		public V GetCell( MultiKeyUntyped coordinates )
		{
			if ( coordinates.Count != numDimensions )
			{
				throw new ArgumentException( "Cannot return cell as number of dimension members does not match cube dimensions" );
			}

			Int32 ordinal;

			if ( !ordinals.TryGetValue( coordinates, out ordinal ) )
			{
				return default(V);
			}

			return (V) cells[ordinal];
		}

        /// <summary>
        /// Get a cell adding the coordinate members if the cell does not yet exist.
        /// </summary>
        /// <param name="coordinates">contains member values for each dimension of the cube</param>
        /// <returns>the cell</returns>
		public V GetCellAddMembers( MultiKeyUntyped coordinates )
		{
			if ( coordinates.Count != numDimensions )
			{
				throw new ArgumentException( "Cannot return cell as number of dimension members does not match cube dimensions" );
			}

			Int32 ordinal;

			// The dimension members are all known and a value has previously been added for these coordinates
			if ( ordinals.TryGetValue( coordinates, out ordinal ) )
			{
				return cells[ordinal];
			}

			// Check the coordinates against existing dimension members, compile array of indizes
			int[] indizes = new int[numDimensions];
			bool allMembersFound = CalcIndizes( coordinates, indizes );

			// All coordinates map to dimension members
			if ( allMembersFound )
			{
				// Compile array containing the size of each dimension
				int[] dimensionSizes = DimensionSizes;

				// From the individual dimension indizes [x,y,z] values calculate the ordinal into the array
				ordinal = CubeDimensionHelper.GetOrdinal( indizes, dimensionSizes );

				// Add ordinal and fact
				ordinals[coordinates] = ordinal ;

				return cells[ordinal];
			}

			// If this is the very first cell, all dimensions Started out with zero members and now became size one
			if ( cells.Length == 0 )
			{
				for ( int i = 0 ; i < coordinates.Count ; i++ )
				{
					Object member = coordinates[ i ];
					dimensionMembers[ i ].Add( member );
				}

				// Add ordinal and fact
				cells = multidimCubeCellFactory.NewCells( 1 );
				cells[0] = multidimCubeCellFactory.NewCell();
                ordinals[coordinates] = 0;

				return cells[0];
			}

			// One or more of the dimension members have never occured before, add member will redimensionize
			for ( int i = 0 ; i < indizes.Length ; i++ )
			{
				// Add each new member
				if ( indizes[i] == -1 )
				{
					AddMember( coordinates[ i ], i, multidimCubeCellFactory );
				}
			}

			// Again check the coordinates against existing dimension members, they must exist now
			indizes = new int[numDimensions];
			allMembersFound = CalcIndizes( coordinates, indizes );

			if ( !allMembersFound )
			{
				throw new SystemException( "Internal error - member for dimension could not be added" );
			}

			// Determine ordinal
			int[] dimensionSizes2 = DimensionSizes;
            ordinal = CubeDimensionHelper.GetOrdinal(indizes, dimensionSizes2);

			// Add ordinal and fact
			ordinals[coordinates] = ordinal ;
			return cells[ordinal];
		}

		private void AddMember( Object member, int dimension, MultidimCubeCellFactory<V> multidimCubeCellFactory )
		{
			// Add member, capture old and new dimension sizes
			int[] oldDimensionSizes = this.DimensionSizes;
			dimensionMembers[dimension].Add( member );
			int[] newDimensionSizes = this.DimensionSizes;

			// Allocate new array
			int newSize = CubeDimensionHelper.GetTotalCells( newDimensionSizes );
			V[] newFacts = multidimCubeCellFactory.NewCells( newSize );

			// Adding to the last dimension will just grow the array
			if ( dimension == ( numDimensions - 1 ) )
			{
				Array.Copy( this.cells, 0, newFacts, 0, cells.Length );
				for ( int i = cells.Length ; i < newFacts.Length ; i++ )
				{
					newFacts[i] = multidimCubeCellFactory.NewCell();
				}
				this.cells = newFacts;
				return;
			}

			int[] indizes = new int[numDimensions];
			int[] newOrdinals = new int[cells.Length];

			// Determine new ordinals for the old cells and copy over cells,
			for ( int i = 0 ; i < cells.Length ; i++ )
			{
                int newOrdinal = CubeDimensionHelper.GetOrdinal(indizes, newDimensionSizes);
				newOrdinals[i] = newOrdinal;

				newFacts[newOrdinal] = cells[i];

				if ( i + 1 < cells.Length )
				{
					CubeDimensionHelper.NextIndize( oldDimensionSizes, indizes );
				}
			}

			// Initialize the new fact array where null
			for ( int i = 0 ; i < newFacts.Length ; i++ )
			{
				if ( newFacts[i] == null )
				{
					newFacts[i] = multidimCubeCellFactory.NewCell();
				}
			}

            if (ordinals.Count != 0)
            {
                List<KeyValuePair<MultiKeyUntyped, Int32>> tempList = new List<KeyValuePair<MultiKeyUntyped, int>>();
                // Change references to old ordinals
                foreach (KeyValuePair<MultiKeyUntyped, Int32> entry in ordinals)
                {
                    tempList.Add(entry);
                }

                foreach (KeyValuePair<MultiKeyUntyped, Int32> entry in tempList)
                {
                    int oldOrdinal = entry.Value;
                    int newOrdinal = newOrdinals[oldOrdinal];
                    ordinals[entry.Key] = newOrdinal;
                }
            }

			this.cells = newFacts;
		}

		private bool CalcIndizes( MultiKeyUntyped coordinates, int[] indizes )
		{
			bool allFound = true;

			for ( int i = 0 ; i < coordinates.Count ; i++ )
			{
				Object memberKey = coordinates[ i ];
				IList<Object> existingMembers = dimensionMembers[i] ;

				int index = existingMembers.IndexOf( memberKey );
				if ( index == -1 )
				{
					allFound = false;
					indizes[i] = -1;
				}
				else
				{
					indizes[i] = index;
				}
			}

			return allFound;
		}
	}
}
