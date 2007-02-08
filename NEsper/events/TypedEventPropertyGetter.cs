using System;
namespace net.esper.events
{
	
	/// <summary> Interface for property getters also returning type information for the property.</summary>
	/// <author>  pablo
	/// </author>
	public interface TypedEventPropertyGetter:EventPropertyGetter
	{
		/// <summary> Returns type of event property.</summary>
		/// <returns> class of the objects returned by this getter
		/// </returns>
		Type ResultClass
		{
			get;
			
		}
	}
}