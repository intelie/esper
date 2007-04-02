using System;
namespace net.esper.view.stat.olap
{
	
	/// <summary> DimensionMember models instances of dimensional members. A member is a single or multiple object derived from the
	/// event property or properties of the dimension.
	/// </summary>
	public interface DimensionMember
	{
		/// <summary> Returns member values.</summary>
		/// <returns> array of member values
		/// </returns>
		Object[] Values
		{
			get;			
		}

		/// <summary> Returns the dimension the member belongs to.</summary>
		/// <returns> dimension that this member is a value of
		/// </returns>
        Dimension Dimension
        {
            get;
        }
	}
}