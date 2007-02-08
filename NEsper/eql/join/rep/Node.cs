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

		/// <summary> Returns the parent node, or null if this is a root node.</summary>
		/// <returns> parent node or null for root node
		/// </returns>
		/// <summary> Sets the parent node.</summary>
		/// <param name="parent">to set
		/// </param>
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

		/// <summary> Returns lookup event.</summary>
		/// <returns> parent node's event that was used to lookup
		/// </returns>
		/// <summary> Set the parent lookup (from stream) event whose results (to stream) are stored.</summary>
		/// <param name="parentEvent">is the lookup event
		/// </param>
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

        virtual public ISet<EventBean> Events
        {
            get { return events; }
            set { events = value; }
        }

        private readonly int stream;
		private Node parent;
		private EventBean parentEvent;
		private ISet<EventBean> events;
		
		/// <summary> Ctor.</summary>
		/// <param name="stream">this node stores results for
		/// </param>
		public Node(int stream)
		{
			this.stream = stream;
		}
	}
}