using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.rep;
using net.esper.events;
using net.esper.util;

namespace net.esper.eql.join.exec
{
	/// <summary>
    /// Execution for a lookup instruction to look up in one or more event streams with a supplied event
	/// and using a given set of lookup strategies, and adding any lookup results to a lighweight repository object
	/// for later result assembly.
	/// </summary>

    public class LookupInstructionExec
    {
        private readonly int fromStream;
        private readonly String fromStreamName;
        private readonly TableLookupStrategy[] lookupStrategies;

        private readonly int numSubStreams;
        private readonly ISet<EventBean>[] resultPerStream;
        private readonly int[] requiredSubStreams;
        private readonly int[] optionalSubStreams;
        private readonly bool hasRequiredSubStreams;

        /// <summary>Ctor.</summary>
        /// <param name="fromStream">the stream supplying the lookup event</param>
        /// <param name="fromStreamName">the stream name supplying the lookup event</param>
        /// <param name="toStreams">the set of streams to look up in</param>
        /// <param name="lookupStrategies">the strategy to use for each stream to look up in</param>
        /// <param name="requiredPerStream">indicates which of the lookup streams are required to build a result and which are not</param>

        public LookupInstructionExec(int fromStream, String fromStreamName, int[] toStreams, TableLookupStrategy[] lookupStrategies, Boolean[] requiredPerStream)
        {
            if (toStreams.Length != lookupStrategies.Length)
            {
                throw new ArgumentException("Invalid number of strategies for each stream");
            }
            if (requiredPerStream.Length < lookupStrategies.Length)
            {
                throw new ArgumentException("Invalid required per stream array");
            }
            if ((fromStream < 0) || (fromStream >= requiredPerStream.Length))
            {
                throw new ArgumentException("Invalid from stream");
            }

            this.fromStream = fromStream;
            this.fromStreamName = fromStreamName;
            this.numSubStreams = toStreams.Length;
            this.lookupStrategies = lookupStrategies;

            resultPerStream = new ISet<EventBean>[numSubStreams];

            // Build a separate array for the required and for the optional streams
            IList<Int32> required = new List<Int32>();
            IList<Int32> optional = new List<Int32>();
            foreach (int stream in toStreams)
            {
                if (requiredPerStream[stream])
                {
                    required.Add(stream);
                }
                else
                {
                    optional.Add(stream);
                }
            }
            requiredSubStreams = ToArray(required);
            optionalSubStreams = ToArray(optional);
            hasRequiredSubStreams = requiredSubStreams.Length > 0;
        }

        /// <summary>Returns the stream number of the stream supplying the event to use for lookup.</summary>
        /// <returns>stream number</returns>

        public int FromStream
        {
        	get { return fromStream; }
        }

        /// <summary>Returns true if there is one or more required substreams or false if no substreams are required joins.</summary>
        /// <returns>true if any substreams are required (inner) joins, or false if not</returns>

        public Boolean HasRequiredStream
        {
        	get { return hasRequiredSubStreams; }
        }

        /// <summary>Execute the instruction adding results to the repository and obtaining events for lookup from therepository.</summary>
        /// <param name="repository">supplies events for lookup, and place to add results to</param>
        /// <returns>true if one or more results, false if no results</returns>

        public Boolean Process(Repository repository)
        {
            Boolean hasOneResultRow = false;
            IEnumerator<Cursor> it = repository.GetCursors(fromStream);

            // Loop over all events for that stream
            while( it.MoveNext() )
            {
                Cursor cursor = it.Current;
                EventBean lookupEvent = cursor.Event;
                int streamCount = 0;

                // For that event, lookup in all required streams
                while (streamCount < requiredSubStreams.Length)
                {
                    ISet<EventBean> lookupResult = lookupStrategies[streamCount].Lookup(lookupEvent);

                    // There is no result, break if this is a required stream
                    if (lookupResult == null)
                    {
                        break;
                    }
                    resultPerStream[streamCount] = lookupResult;
                    streamCount++;
                }

                // No results for a required stream, we are done with this event
                if (streamCount < requiredSubStreams.Length)
                {
                    continue;
                }
                else
                {
                    // Add results to repository
                    for (int i = 0; i < requiredSubStreams.Length; i++)
                    {
                        hasOneResultRow = true;
                        repository.AddResult(cursor, resultPerStream[i], requiredSubStreams[i]);
                    }
                }

                // For that event, lookup in all optional streams
                for (int i = 0; i < optionalSubStreams.Length; i++)
                {
                    ISet<EventBean> lookupResult = lookupStrategies[streamCount].Lookup(lookupEvent);

                    if (lookupResult != null)
                    {
                        hasOneResultRow = true;
                        repository.AddResult(cursor, lookupResult, optionalSubStreams[i]);
                    }
                    streamCount++;
                }
            }

            return hasOneResultRow;
        }

        private int[] ToArray(IList<Int32> list)
        {
            int[] arr = new int[list.Count];
            int count = 0;
            foreach (int value in list)
            {
                arr[count++] = value;
            }
            return arr;
        }

        /// <summary>Output the instruction.</summary>
        /// <param name="writer">is the write to output to</param>

        public void Print(IndentWriter writer)
        {
            writer.WriteLine("LookupInstructionExec" +
                    " fromStream=" + fromStream +
                    " fromStreamName=" + fromStreamName +
                    " numSubStreams=" + numSubStreams +
                    " requiredSubStreams=" + CollectionHelper.Render(requiredSubStreams) +
                    " optionalSubStreams=" + CollectionHelper.Render(optionalSubStreams));

            writer.IncrIndent();
            for (int i = 0; i < lookupStrategies.Length; i++)
            {
                writer.WriteLine("lookupStrategies[" + i + "] : " + lookupStrategies[i].ToString());
            }
            writer.DecrIndent();
        }
    }
}
