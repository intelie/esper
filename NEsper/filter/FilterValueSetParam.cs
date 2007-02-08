using System;
namespace net.esper.filter
{
	
	/// <summary> This interface represents one filter parameter in an {@link FilterValueSet} filter specification.
	/// <p> Each filtering parameter has an property name and operator type, and a value to filter for.
	/// </summary>
	public interface FilterValueSetParam
	{
		/// <summary> Returns the property name for the filter parameter.</summary>
		/// <returns> property name
		/// </returns>
		String PropertyName
		{
			get;
			
		}
		/// <summary> Returns the filter operator type.</summary>
		/// <returns> filter operator type
		/// </returns>
		FilterOperator FilterOperator
		{
			get;
			
		}
		/// <summary> Return the filter parameter constant to filter for.</summary>
		/// <returns> filter parameter constant's value
		/// </returns>
		Object FilterForValue
		{
			get;
			
		}
	}
}