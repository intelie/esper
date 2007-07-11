using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.eql.spec;
using net.esper.support.eql;
using net.esper.type;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.join.plan
{

	[TestFixture]
    public class TestOuterJoinAnalyzer 
    {
        [Test]
        public void testAnalyze()
        {
            IList<OuterJoinDesc> descList = new List<OuterJoinDesc>();
            descList.Add(SupportOuterJoinDescFactory.MakeDesc("intPrimitive", "s0", "intBoxed", "s1", OuterJoinType.LEFT));
            descList.Add(SupportOuterJoinDescFactory.MakeDesc("simpleProperty", "s2", "string", "s1", OuterJoinType.LEFT));
            // simpleProperty in s2

            QueryGraph graph = new QueryGraph(3);
            OuterJoinAnalyzer.Analyze(descList, graph);
            Assert.AreEqual(3, graph.NumStreams);

            Assert.IsTrue(graph.IsNavigable(0, 1));
            Assert.AreEqual(1, graph.GetKeyProperties(0, 1).Length);
            Assert.AreEqual("intPrimitive", graph.GetKeyProperties(0, 1)[0]);
            Assert.AreEqual(1, graph.GetKeyProperties(1, 0).Length);
            Assert.AreEqual("intBoxed", graph.GetKeyProperties(1, 0)[0]);

            Assert.IsTrue(graph.IsNavigable(1, 2));
            Assert.AreEqual("string", graph.GetKeyProperties(1, 2)[0]);
            Assert.AreEqual("simpleProperty", graph.GetKeyProperties(2, 1)[0]);
        }
    }
}
