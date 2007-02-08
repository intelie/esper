using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.exec
{
	/// <summary> Execution node for lookup in a table for outer joins. This execution node thus generates
	/// rows even if no joined events could be found, the joined table events are set to null if no
	/// joined events are found.
	/// </summary>
	public class TableOuterLookupExecNode : ExecNode
	{
        private int indexedStream;
        private TableLookupStrategy lookupStrategy;

		/// <summary> Returns strategy for lookup.</summary>
		/// <returns> lookup strategy
		/// </returns>
		virtual public TableLookupStrategy LookupStrategy
		{
			get { return lookupStrategy; }
		}

		/// <summary> Returns target stream for lookup.</summary>
		/// <returns> indexed stream
		/// </returns>
		virtual public int IndexedStream
		{
			get { return indexedStream; }
		}

		/// <summary> Ctor.</summary>
		/// <param name="indexedStream">- stream indexed for lookup
		/// </param>
		/// <param name="lookupStrategy">- strategy to use for lookup (full table/indexed)
		/// </param>
		public TableOuterLookupExecNode(int indexedStream, TableLookupStrategy lookupStrategy)
		{
			this.indexedStream = indexedStream;
			this.lookupStrategy = lookupStrategy;
		}

        public override void Process(EventBean lookupEvent, EventBean[] prefillPath, IList<EventBean[]> result)
        {
            // Lookup events
            ISet<EventBean> joinedEvents = lookupStrategy.lookup(lookupEvent);

            // If no events are found, since this is an outer join, create a result row leaving the
            // joined event as null.
            if ((joinedEvents == null) || (joinedEvents.Count == 0))
            {
                EventBean[] events = new EventBean[prefillPath.Length];
                for (int i = 0; i < events.Length; i++)
                {
                    events[i] = prefillPath[i];
                }
                result.Add(events);

                return;
            }

            // Create result row for each found event
            foreach (EventBean joinedEvent in joinedEvents)
            {
                EventBean[] events = new EventBean[prefillPath.Length];
                for (int i = 0; i < events.Length; i++)
                {
                    events[i] = prefillPath[i];
                }
                events[indexedStream] = joinedEvent;
                result.Add(events);
            }
        }

		public override void Print(IndentWriter writer)
		{
			writer.WriteLine("TableOuterLookupExecNode indexedStream=" + indexedStream + " lookup=" + lookupStrategy);
		}
	}
}
