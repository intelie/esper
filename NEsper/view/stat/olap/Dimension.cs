using System;
namespace net.esper.view.stat.olap
{
	
	
	/// <summary> Dimensions are a structural attribute of cubes. A dimension is an ordinate within a multidimensional
	/// cube, consisting of a list of values (members). Each member designates a unique position along its ordinate.
	/// </summary>
	public interface Dimension
	{
		/// <summary> Returns the event property name or names providing the member values for the dimension.</summary>
		/// <returns> array of property names
		/// </returns>
		String[] PropertyNames
		{
			get;
			
		}
		
		/// <summary> Returns the member values for the dimension.</summary>
		/// <returns> array of members
		/// </returns>
		DimensionMember[] GetMembers();
	}
}