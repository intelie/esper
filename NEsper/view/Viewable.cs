using System;
using System.Collections.Generic;

namespace net.esper.view
{
	/// <summary> The Viewable interface marks an object as supporting zero, one or more View instances.
	/// All implementing classes must call each view's 'update' method when new data enters it.
	/// Implementations must take care to synchronize methods of this interface with other methods
	/// such that data flow is threadsafe.
	/// </summary>
    public interface Viewable : EventCollection
    {
        /// <summary> Add a view to the viewable object.</summary>
        /// <param name="view">to add
        /// </param>
        /// <returns> view to add
        /// </returns>
        
        View AddView(View view);

        /// <summary> Returns all added views.</summary>
        /// <returns> list of added views
        /// </returns>

        IList<View> GetViews();

        /// <summary> Remove a view.</summary>
        /// <param name="view">to remove
        /// </param>
        /// <returns> true to indicate that the view to be removed existed within this view, false if the view to
        /// remove could not be found
        /// </returns>
        
        bool RemoveView(View view);

        /// <summary> Test is there are any views to the Viewable.</summary>
        /// <returns> true indicating there are child views, false indicating there are no child views
        /// </returns>

        bool HasViews { get; }
    }
}