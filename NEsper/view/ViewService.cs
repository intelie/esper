using System;
using System.Collections.Generic;

namespace net.esper.view
{
	/// <summary> Service interface for creating views.</summary>

    public interface ViewService
    {
        /// <summary> Creates a chain of views returning the last view in the chain.</summary>
        /// <param name="eventStream">the event stream that originates the raw events
        /// </param>
        /// <param name="viewSpecList">the specification for the chain to be created
        /// </param>
        /// <param name="context">dependent services
        /// </param>
        /// <returns> last view in chain
        /// </returns>
        /// <throws>  ViewProcessingException thrown if a view cannot be created </throws>
        Viewable CreateView(EventStream eventStream, IList<ViewSpec> viewSpecList, ViewServiceContext context);

        /// <summary> Removes a view discoupling the view and any of it's parent views up the tree to the last shared parent view.</summary>
        /// <param name="eventStream">the event stream that originates the raw events
        /// </param>
        /// <param name="view">the view (should be the last in a chain) to remove
        /// </param>
        void Remove(EventStream eventStream, Viewable view);
    }
}
