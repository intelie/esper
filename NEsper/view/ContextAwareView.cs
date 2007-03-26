using System;
namespace net.esper.view
{
	
	/// <summary> Views use this interface to indicate that the view requires services out of the context,
	/// such as the scheduling service.
	/// </summary>
	public interface ContextAwareView
	{
        /// <summary>
        /// Gets or sets the context instances used by the view.
        /// </summary>
        /// <value>The view service context.</value>

        ViewServiceContext ViewServiceContext
		{
			get;
			set;
		}
	}
}