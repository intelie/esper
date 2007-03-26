using System;

namespace net.esper.view.stat.olap
{
	/// <summary>
	/// Serves up Cube dimension member information - the members dimension
	/// and its key values.
	/// </summary>
	
	public sealed class DimensionMemberImpl : DimensionMember
	{
		private Dimension dimension;
		private readonly Object[] values;

        /// <summary>
        /// Returns member values.
        /// </summary>
        /// <value></value>
        /// <returns> array of member values
        /// </returns>
		public Object[] Values
		{
			get { return values; }			
		}
		
		/// <summary> Constructor.</summary>
		/// <param name="values">is a set of values identifying the member
		/// </param>
		public DimensionMemberImpl(Object[] values)
		{
			this.values = values;
		}
		
		/// <summary> Set the dimension this member belongs to.</summary>
		/// <param name="dimension">the member belongs to
		/// </param>
		public void  setDimension(Dimension dimension)
		{
			this.dimension = dimension;
		}

        /// <summary>
        /// Returns the dimension the member belongs to.
        /// </summary>
        /// <returns>dimension that this member is a value of</returns>
		public Dimension getDimension()
		{
			return dimension;
		}
	}
}
