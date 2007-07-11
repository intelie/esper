using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.join.table;
using net.esper.events;
using net.esper.support.events;
using net.esper.support.util;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.exec
{

	[TestFixture]
    public class TestNestedIterationExecNode
    {
        private NestedIterationExecNode exec;
        private EventBean[][] streamEvents;

        [SetUp]
        public virtual void setUp()
        {
            UnindexedEventTable[] indexes = new UnindexedEventTable[4];
            for (int i = 0; i < indexes.Length; i++)
            {
                indexes[i] = new UnindexedEventTable(0);
            }

            exec = new NestedIterationExecNode(new int[] { 3, 0, 1 });
            exec.AddChildNode(new TableLookupExecNode(3, new FullTableScanLookupStrategy(indexes[3])));
            exec.AddChildNode(new TableLookupExecNode(0, new FullTableScanLookupStrategy(indexes[0])));
            exec.AddChildNode(new TableLookupExecNode(1, new FullTableScanLookupStrategy(indexes[1])));

            EventBean[][] tmpArray = new EventBean[4][];
            for (int i2 = 0; i2 < 4; i2++)
            {
                tmpArray[i2] = new EventBean[2];
            }
            streamEvents = tmpArray;
            streamEvents[0] = SupportEventBeanFactory.MakeEvents_A(new String[] { "a1", "a2" });
            streamEvents[1] = SupportEventBeanFactory.MakeEvents_B(new String[] { "b1", "b2" });
            streamEvents[2] = SupportEventBeanFactory.MakeEvents_C(new String[] { "c1", "c2" });
            streamEvents[3] = SupportEventBeanFactory.MakeEvents_D(new String[] { "d1", "d2" });

            // Fill with data
            indexes[0].Add(streamEvents[0]);
            indexes[1].Add(streamEvents[1]);
            indexes[2].Add(streamEvents[2]);
            indexes[3].Add(streamEvents[3]);
        }

        [Test]
        public void testLookup()
        {
            IList<EventBean[]> result = new List<EventBean[]>();
            EventBean[] prefill = new EventBean[4];
            prefill[2] = streamEvents[2][0];

            exec.Process(streamEvents[2][0], prefill, result);

            Assert.AreEqual(8, result.Count);

            EventBean[][] received = MakeArray(result);
            EventBean[][] expected = makeExpected();
            ArrayAssertionUtil.AreEqualAnyOrder(expected, received);
        }

        private EventBean[][] makeExpected()
        {
            EventBean[][] tmpArray = new EventBean[8][];
            for (int i = 0; i < 8; i++)
            {
                tmpArray[i] = new EventBean[4];
            }
            EventBean[][] expected = tmpArray;
            int count = 0;
            for (int i = 0; i < 2; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    for (int k = 0; k < 2; k++)
                    {
                        expected[count][0] = streamEvents[0][i];
                        expected[count][1] = streamEvents[1][j];
                        expected[count][2] = streamEvents[2][0];
                        expected[count][3] = streamEvents[3][k];
                        count++;
                    }
                }
            }
            return expected;
        }

        private EventBean[][] MakeArray(IList<EventBean[]> eventArrList)
        {
            EventBean[][] result = new EventBean[eventArrList.Count][];
            for (int i = 0; i < eventArrList.Count; i++)
            {
                result[i] = eventArrList[i];
            }
            return result;
        }
    }

    // Result
    /* 8 combinations
    d1
        a1
            b1
            b2
        a2
            b1
            b2
    d2
        a1
            b1
            b2
        a2
            b1
            b2
    */



}
