using System;
using System.Collections.Generic;

namespace net.esper.view
{
	/// <summary>
    /// Interface for use by views to indicate that the view must couple to parent views.
    /// </summary>
	
    public interface ParentAwareView
	{
		/// <summary> Called to indicate the parent views.</summary>
		/// <param name="parentViews">is a list of parent views in top-down order 
		/// </param>

        IList<View> ParentAware
        {
            set;
        }
	}
}