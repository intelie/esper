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
        private readonly EDictionary<int, ISet<int>> streamToInnerMap;
        private readonly int numStreams;

        /// <summary> Ctor.</summary>
        /// <param name="numStreams">number of streams
        /// </param>

        public OuterInnerDirectionalGraph(int numStreams)
        {
            this.numStreams = numStreams;
            this.streamToInnerMap = new EHashDictionary<int, ISet<int>>();
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
            checkArgs(outerStream, innerStream);

            // add set
            ISet<int> innerSet = streamToInnerMap.Fetch( outerStream, null ) ;
            if ( innerSet == null )
            {
                innerSet = new EHashSet<int>();
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
        public ISet<int> getInner(int outerStream)
        {
            checkArgs(outerStream);
            ISet<int> innerSet = streamToInnerMap.Fetch(outerStream, null);
            return innerSet;
        }

        /// <summary> Returns the set of outer streams for the given inner stream number.</summary>
        /// <param name="innerStream">is the stream number of the inner stream
        /// </param>
        /// <returns> set of outer streams, or null if empty
        /// </returns>
        public ISet<int> getOuter(int innerStream)
        {
            checkArgs(innerStream);

            ISet<int> result = new EHashSet<int>();
            foreach( KeyValuePair<int, ISet<int>> keyValuePair in streamToInnerMap )
            {
                int key = keyValuePair.Key;
                ISet<int> set = keyValuePair.Value;

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
        public virtual bool isInner(int outerStream, int innerStream)
        {
            checkArgs(outerStream, innerStream);

            ISet<int> innerSet = streamToInnerMap.Fetch(outerStream, null);
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
        public virtual bool isOuter(int outerStream, int innerStream)
        {
            checkArgs(outerStream, innerStream);
            ISet<int> outerStreams = getOuter(innerStream);
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

            foreach( KeyValuePair<int, ISet<int>> kvPair in streamToInnerMap )
            {
                ISet<int> set = kvPair.Value;

                buffer.Append(delimiter);
                buffer.Append(kvPair.Key);
                buffer.Append('=');
                buffer.Append(set.ToString());

                delimiter = ", ";
            }
            
            return buffer.ToString();
        }

        private void checkArgs(int stream)
        {
            if ((stream >= numStreams) || (stream < 0))
            {
                throw new ArgumentException("Out of bounds parameter for stream num");
            }
        }

        private void checkArgs(int outerStream, int innerStream)
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
