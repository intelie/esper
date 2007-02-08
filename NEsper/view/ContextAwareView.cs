using System;
namespace net.esper.view
{
	
	/// <summary> Views use this interface to indicate that the view requires services out of the context,
	/// such as the scheduling service.
	/// </summary>
	public interface ContextAwareView
	{
		//UPGRADE_NOTE: Respective javadoc comments were merged.  It should be changed in order to comply with .NET documentation conventions. "ms-help://MS.VSCC.v80/dv_commoner/local/redirect.htm?index='!DefaultContextWindowIndex'&keyword='jlca1199'"
		/// <summary> Returns the context instances used by the view.</summary>
		/// <returns> context instance
		/// </returns>
		/// <summary> Set the services context containing service handles.</summary>
		/// <param name="viewServiceContext">with service handles
		/// </param>

        ViewServiceContext ViewServiceContext
		{
			get;
			set;
		}
	}
}