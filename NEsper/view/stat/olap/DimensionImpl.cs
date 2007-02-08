using System;

namespace net.esper.view.stat.olap
{
	/// <summary>
	/// Implements the dimension interface. Hold the data required for
	/// serving up dimension data.
	/// </summary>
	
	public sealed class DimensionImpl : Dimension
	{
		public String[] PropertyNames
		{
			get
			{
				return propertyNames;
			}
		}
		
		private readonly String[] propertyNames;
		private DimensionMember[] members;
		
		/// <summary> Constructor.</summary>
		/// <param name="propertyNames">is the names of the event properties making up the dimension
		/// </param>
		public DimensionImpl(String[] propertyNames)
		{
			this.propertyNames = propertyNames;
		}
		
		/// <summary> Set the members of the dimension.</summary>
		/// <param name="members">is an array of members of dimension
		/// </param>
		internal void  setMembers(DimensionMember[] members)
		{
			this.members = members;
		}
		
		public DimensionMember[] GetMembers()
		{
			return members;
		}
	}
}
