using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.assemble
{
	/// <summary>
    /// Helper class to compute the cartesian product of the events from two streams.
    /// </summary>

    public class CartesianUtil
    {
        /// <summary>
        /// Form the 2-ary cartesian product between zero or more events from 2 streams.
        /// </summary>
        /// <param name="streamOne">the events from stream one</param>
        /// <param name="subStreamNumsOne">the list of substream numbers to stream one to include in the product</param>
        /// <param name="streamTwo">the events from stream two</param>
        /// <param name="subStreamNumsTwo">the list of substream numbers to stream two to include in the product</param>
        /// <param name="resultList">where the result of the cartesian product is added to</param>

        public static void ComputeCartesian(IList<EventBean[]> streamOne, int[] subStreamNumsOne,
                                            IList<EventBean[]> streamTwo, int[] subStreamNumsTwo,
                                            IList<EventBean[]> resultList)
        {

            if ((streamTwo == null) || (streamTwo.Count == 0))
            {
                if ((streamOne == null) || (streamOne.Count == 0))
                {
                    return;
                }
                
                CollectionHelper.AddAll( resultList, streamOne ) ;
                return;
            }

            if ((streamOne == null) || (streamOne.Count == 0))
            {
            	CollectionHelper.AddAll( resultList, streamTwo ) ;
                return;
            }

            int streamOneSize = streamOne.Count;
            int streamTwoSize = streamTwo.Count;

            if (streamOneSize == 1)
            {
                // Yes we are re-using the results of stream two, same row reference
                CopyToEach(subStreamNumsOne, streamOne[0], streamTwo);
                CollectionHelper.AddAll( resultList, streamTwo ) ;
                return;
            }

            if (streamTwoSize == 1)
            {
                // Yes we are re-using the results of stream one, same row reference
                CopyToEach(subStreamNumsTwo, streamTwo[0], streamOne);
                CollectionHelper.AddAll( resultList, streamOne ) ;
                return;
            }

            // we have more then 1 rows each child stream

            // Exchange streams if one is smaller then two
            // Since if one has 100 rows the other has 2 then we can re-use the 100 event rows.
            if (streamTwoSize > streamOneSize)
            {
                IList<EventBean[]> holdRows = streamOne;
                int holdSize = streamOneSize;
                int[] holdSubStream = subStreamNumsOne;

                streamOne = streamTwo;
                streamOneSize = streamTwoSize;
                subStreamNumsOne = subStreamNumsTwo;

                streamTwo = holdRows;
                streamTwoSize = holdSize;
                subStreamNumsTwo = holdSubStream;
            }

            // allocate resultList of join
            int cartesianTotalRows = streamOneSize * streamTwoSize;
            int numColumns = streamOne[0].Length;
            EventBean[][] results = new EventBean[cartesianTotalRows][];

            // Allocate and pre-populate copies of stream 1
            int streamOneCount = 0;
            foreach (EventBean[] row in streamOne)
            {
                // first use all events in stream 1
                results[streamOneCount] = row;

                // then allocate copies for each in stream 2
                for (int i = 1; i < streamTwoSize; i++)
                {
                    EventBean[] dupRow = new EventBean[numColumns];
                    Array.Copy(row, 0, dupRow, 0, numColumns);

                    int index = streamOneSize * i + streamOneCount;
                    results[index] = dupRow;
                }

                streamOneCount++;
            }

            // Copy stream 2 rows into rows of stream 1
            int streamTwoCount = 0;
            foreach (EventBean[] row in streamTwo)
            {
                for (int i = 0; i < streamOneSize; i++)
                {
                    int index = streamTwoCount * streamOneSize + i;
                    Copy(subStreamNumsTwo, row, results[index]);
                }
                streamTwoCount++;
            }

            // Add results
            for (int i = 0; i < results.Length; i++)
            {
                resultList.Add(results[i]);
            }
        }

        private static void CopyToEach(int[] subStreamNums, EventBean[] sourceRow, IList<EventBean[]> destRows)
        {
            foreach (EventBean[] destRow in destRows)
            {
                Copy(subStreamNums, sourceRow, destRow);
            }
        }

        private static void Copy(int[] subStreamsFrom, EventBean[] from, EventBean[] to)
        {
            for (int i = 0; i < subStreamsFrom.Length; i++)
            {
                int index = subStreamsFrom[i];
                to[index] = from[index];
            }
        }
    }
}
