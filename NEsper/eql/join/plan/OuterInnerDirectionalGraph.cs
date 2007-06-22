using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;

namespace net.esper.eql.join.plan
{
    /// <summary> This class represents outer-join relationships between outer and inner tables.
    /// To add a left outer join between streams 0 and 1 use "Add(0, 1)".
    /// To add a full outer join between streams 0 and 1 use "Add(0, 1)" and "Add(1, 0)".
    /// To add a right outer join between streams 0 and 1 use "Add(1, 0)".
    /// </summary>

    public class OuterInnerDirectionalGraph
    {
        private readonly EDictionary<int, Set<int>> streamToInnerMap;
        private readonly int numStreams;

        /// <summary> Ctor.</summary>
        /// <param name="numStreams">number of streams
        /// </param>

        public OuterInnerDirectionalGraph(int numStreams)
        {
            this.numStreams = numStreams;
            this.streamToInnerMap = new HashDictionary<int, Set<int>>();
        }

        /// <summary> Add an outer-to-inner join stream relationship.</summary>
        /// <param name="outerStream">is the stream number of the outer stream
        /// </param>
        /// <param name="innerStream">is the stream number of the inner stream
        /// </param>
        /// <returns> graph object
        /// </returns>
        public virtual OuterInnerDirectionalGraph Add(int outerStream, int innerStream)
        {
            CheckArgs(outerStream, innerStream);

            // add set
            Set<int> innerSet = streamToInnerMap.Fetch( outerStream, null ) ;
            if ( innerSet == null )
            {
                innerSet = new HashSet<int>();
                streamToInnerMap[outerStream] = innerSet;
            }

            // populate
            if (innerSet.Contains(innerStream))
            {
                throw new ArgumentException("Inner stream already in collection");
            }

            innerSet.Add(innerStream);

            return this;
        }

        /// <summary> Returns the set of inner streams for the given outer stream number.</summary>
        /// <param name="outerStream">is the stream number of the outer stream
        /// </param>
        /// <returns> set of inner streams, or null if empty
        /// </returns>
        public Set<int> GetInner(int outerStream)
        {
            CheckArgs(outerStream);
            Set<int> innerSet = streamToInnerMap.Fetch(outerStream, null);
            return innerSet;
        }

        /// <summary> Returns the set of outer streams for the given inner stream number.</summary>
        /// <param name="innerStream">is the stream number of the inner stream
        /// </param>
        /// <returns> set of outer streams, or null if empty
        /// </returns>
        public Set<int> GetOuter(int innerStream)
        {
            CheckArgs(innerStream);

            Set<int> result = new HashSet<int>();
            foreach( KeyValuePair<int, Set<int>> keyValuePair in streamToInnerMap )
            {
                int key = keyValuePair.Key;
                Set<int> set = keyValuePair.Value;

                if (set.Contains(innerStream))
                {
                    result.Add(key);
                }
            }

            if (result.Count == 0)
            {
                return null;
            }

            return result;
        }

        /// <summary> Returns true if the outer stream has an optional relationship to the inner stream.</summary>
        /// <param name="outerStream">is the stream number of the outer stream
        /// </param>
        /// <param name="innerStream">is the stream number of the inner stream
        /// </param>
        /// <returns> true if outer-inner relationship between streams, false if not
        /// </returns>
        public virtual bool IsInner(int outerStream, int innerStream)
        {
            CheckArgs(outerStream, innerStream);

            Set<int> innerSet = streamToInnerMap.Fetch(outerStream, null);
            if (innerSet == null)
            {
                return false;
            }
            return innerSet.Contains(innerStream);
        }

        /// <summary> Returns true if the inner stream has a relationship to the outer stream.</summary>
        /// <param name="outerStream">is the stream number of the outer stream
        /// </param>
        /// <param name="innerStream">is the stream number of the inner stream
        /// </param>
        /// <returns> true if outer-inner relationship between streams, false if not
        /// </returns>
        public virtual bool IsOuter(int outerStream, int innerStream)
        {
            CheckArgs(outerStream, innerStream);
            Set<int> outerStreams = GetOuter(innerStream);
            if (outerStreams == null)
            {
                return false;
            }
            return outerStreams.Contains(outerStream);
        }

        /// <summary> Prints out collection.</summary>
        /// <returns> textual output of keys and values
        /// </returns>
        public virtual String Print()
        {
            StringBuilder buffer = new StringBuilder();
            String delimiter = "";

            foreach( KeyValuePair<int, Set<int>> kvPair in streamToInnerMap )
            {
                Set<int> set = kvPair.Value;

                buffer.Append(delimiter);
                buffer.Append(kvPair.Key);
                buffer.Append('=');
                buffer.Append(set.ToString());

                delimiter = ", ";
            }
            
            return buffer.ToString();
        }

        private void CheckArgs(int stream)
        {
            if ((stream >= numStreams) || (stream < 0))
            {
                throw new ArgumentException("Out of bounds parameter for stream num");
            }
        }

        private void CheckArgs(int outerStream, int innerStream)
        {
            if ((outerStream >= numStreams) || (innerStream >= numStreams) || (outerStream < 0) || (innerStream < 0))
            {
                throw new ArgumentException("Out of bounds parameter for inner or outer stream num");
            }
            if (outerStream == innerStream)
            {
                throw new ArgumentException("Unexpected equal stream num for inner and outer stream");
            }
        }
    }
}
