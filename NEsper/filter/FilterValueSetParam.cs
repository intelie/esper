using System;

namespace net.esper.filter
{
	/// <summary>
    /// This interface represents one filter parameter in an <seealso cref="FilterValueSet"/> filter specification.
	/// <para>
    /// Each filtering parameter has an property name and operator type, and a value to filter for.
    /// </para>
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