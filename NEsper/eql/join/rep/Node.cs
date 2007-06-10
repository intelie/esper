using System;

using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.rep
{
	
	/// <summary> Node is the structure to hold results of event lookups in joined streams. A node holds a set of event which
	/// are the result of a lookup in a stream's table. A Node can be linked to its parent node and the event within the
	/// parent node, which was the event that was used to perform a lookup.
	/// </summary>
	public class Node
	{
		/// <summary> Returns the stream number of the stream that supplied the event results.</summary>
		/// <returns> stream number for results
		/// </returns>
		virtual public int Stream
		{
			get
			{
				return stream;
			}
			
		}

        /// <summary>
        /// Gets or sets the parent node, or null if this is a root node.
        /// </summary>
        /// <value>The parent.</value>
        /// <returns> parent node or null for root node
        /// </returns>
		virtual public Node Parent
		{
			get
			{
				return parent;
			}
			
			set
			{
				this.parent = value;
			}
			
		}

        /// <summary>
        /// Gets or sets lookup event.
        /// </summary>
        /// <value>The parent event.</value>
        /// <returns> parent node's event that was used to lookup
        /// </returns>
		virtual public EventBean ParentEvent
		{
			get
			{
				return parentEvent;
			}
			
			set
			{
				this.parentEvent = value;
			}
			
		}

        /// <summary>
        /// Gets or sets the events.
        /// </summary>
        /// <value>The events.</value>
        virtual public Set<EventBean> Events
        {
            get { return events; }
            set { events = value; }
        }

        private readonly int stream;
		private Node parent;
		private EventBean parentEvent;
		private Set<EventBean> events;
		
		/// <summary> Ctor.</summary>
		/// <param name="stream">this node stores results for
		/// </param>
		public Node(int stream)
		{
			this.stream = stream;
		}
	}
}
