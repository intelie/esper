using System;
using System.Collections.Generic;

namespace net.esper.view
{
	/// <summary>
    /// Interface for use by views to indicate that the view must couple to parent views.
    /// </summary>
	
    public interface ParentAwareView
	{
		/// <summary>
        /// Sets a flag indicating that the view must couple to parent views.
        /// </summary>

        IList<View> ParentAware
        {
            set;
        }
	}
}