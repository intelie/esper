using System;
using System.Text;

using net.esper.collection;
using net.esper.compat;
using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join
{
	[TestFixture]
    public class TestJoinSetComposerImpl 
    {
        private JoinSetComposerImpl joinSetComposerImpl;
        private EventBean[] indexedEventOne, indexedEventTwo, newEventOne, newEventTwo;
        private UnindexedEventTable indexLeft;
        private UnindexedEventTable indexRight;

        [SetUp]
        public virtual void setUp()
        {
            indexedEventOne = SupportEventBeanFactory.MakeEvents(new String[] { "s1_1", "s1_2" });
            indexedEventTwo = SupportEventBeanFactory.MakeEvents(new String[] { "s2_1", "s2_2" });

            newEventOne = SupportEventBeanFactory.MakeEvents(new String[] { "s1_3" });
            newEventTwo = SupportEventBeanFactory.MakeEvents(new String[] { "s2_3" });

            indexLeft = new UnindexedEventTable(1);
            indexLeft.Add(indexedEventOne);
            indexRight = new UnindexedEventTable(1);
            indexRight.Add(indexedEventTwo);

            QueryStrategy[] queryStrategies = new QueryStrategy[2];
            TableLookupExecNode lookupLeft = new TableLookupExecNode(1, new FullTableScanLookupStrategy(indexRight));
            TableLookupExecNode lookupRight = new TableLookupExecNode(0, new FullTableScanLookupStrategy(indexLeft));
            queryStrategies[0] = new ExecNodeQueryStrategy(0, 2, lookupLeft);
            queryStrategies[1] = new ExecNodeQueryStrategy(1, 2, lookupRight);

            EventTable[][] tmpArray = new EventTable[2][];
            for (int i = 0; i < 2; i++)
            {
                tmpArray[i] = new EventTable[1];
            }
            EventTable[][] indexesPerStream = tmpArray;
            indexesPerStream[0][0] = indexLeft;
            indexesPerStream[1][0] = indexRight;
            joinSetComposerImpl = new JoinSetComposerImpl(indexesPerStream, queryStrategies, SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);
        }

        [Test]
        public virtual void testJoin()
        {
            // Should return all possible combinations, not matching performed, remember: duplicate pairs have been removed
		    UniformPair<Set<MultiKey<EventBean>>> result = joinSetComposerImpl.Join(
                new EventBean[][] {newEventOne, newEventTwo},                 // new left and right
                new EventBean[][] {new EventBean[] {indexedEventOne[0]}, new EventBean[] {indexedEventTwo[1]}} // old left and right
                );

            Assert.AreEqual(3, result.First.Count); // check old events joined
            String eventStringText = toString(result.Second);
            Assert.IsTrue(eventStringText.Contains("s1_1|s2_1"));
            Assert.IsTrue(eventStringText.Contains("s1_1|s2_2"));
            Assert.IsTrue(eventStringText.Contains("s1_2|s2_2"));

            // check new events joined, remember: duplicate pairs have been removed
            Assert.AreEqual(3, result.Second.Count);
            eventStringText = toString(result.First);
            Assert.IsTrue(eventStringText.Contains("s1_3|s2_1"));
            Assert.IsTrue(eventStringText.Contains("s1_3|s2_3"));
            Assert.IsTrue(eventStringText.Contains("s1_2|s2_3"));
        }

        private String toString(Set<MultiKey<EventBean>> events)
        {
            String delimiter = "";
            StringBuilder buf = new StringBuilder();

            foreach (MultiKey<EventBean> key in events)
            {
                buf.Append(delimiter);
                buf.Append(toString(key.Array));
                delimiter = ",";
            }
            return buf.ToString();
        }

        private String toString(EventBean[] events)
        {
            String delimiter = "";
            StringBuilder buf = new StringBuilder();
            foreach (EventBean _event in events)
            {
                buf.Append(delimiter);
                buf.Append(((SupportBean)_event.Underlying).GetString());
                delimiter = "|";
            }
            return buf.ToString();
        }
    }
}
