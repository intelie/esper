using System;
using System.Collections.Generic;

using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join
{

	[TestFixture]
    public class TestJoinSetComposerFactory 
    {
        private EventType[] streamTypes;
        private Viewable[] streamViewables;

        [SetUp]
        public virtual void setUp()
        {
            streamTypes = new EventType[2];
            streamTypes[0] = SupportEventTypeFactory.createBeanType(typeof(SupportBean));
            streamTypes[1] = SupportEventTypeFactory.createBeanType(typeof(SupportBean_A));

            streamViewables = new Viewable[2];
        }

        [Test]
        public virtual void testBuildIndex()
        {
            EventTable table = JoinSetComposerFactory.buildIndex(0, new String[] { "intPrimitive", "boolBoxed" }, streamTypes[0]);
            Assert.IsTrue(table is PropertyIndexedEventTable);

            table = JoinSetComposerFactory.buildIndex(0, new String[0], streamTypes[0]);
            Assert.IsTrue(table is UnindexedEventTable);

            try
            {
                JoinSetComposerFactory.buildIndex(0, null, streamTypes[0]);
                Assert.Fail();
            }
            catch (System.NullReferenceException ex)
            {
                // Expected
            }
        }

        [Test]
        public virtual void testBuildComposer()
        {
            IList<OuterJoinDesc> outerJoins = new List<OuterJoinDesc>();
            JoinSetComposerImpl composer = (JoinSetComposerImpl)JoinSetComposerFactory.makeComposer(outerJoins, new SupportExprNode(true), streamTypes, new String[] { "a", "b", "c", "d" }, streamViewables, SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);

            // verify default indexes build
            Assert.AreEqual(2, composer.Tables.Length);
            Assert.IsTrue(composer.Tables[0][0] is UnindexedEventTable);
            Assert.IsTrue(composer.Tables[1][0] is UnindexedEventTable);

            // verify default strategies
            Assert.AreEqual(2, composer.QueryStrategies.Length);
            ExecNodeQueryStrategy plan = (ExecNodeQueryStrategy)composer.QueryStrategies[0];
            Assert.AreEqual(0, plan.getForStream());
            Assert.AreEqual(2, plan.NumStreams);
            Assert.IsTrue(plan.ExecNode is TableLookupExecNode);
            plan = (ExecNodeQueryStrategy)composer.QueryStrategies[1];
            Assert.AreEqual(1, plan.getForStream());
            Assert.AreEqual(2, plan.NumStreams);
            Assert.IsTrue(plan.ExecNode is TableLookupExecNode);
        }
    }
}
