using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
    public class TestResultSetProcessorRowPerGroup 
    {
        private ResultSetProcessorRowPerGroup processor;
        private SupportAggregationService supportAggregationService;

        [SetUp]
        public virtual void setUp()
        {
            SelectExprProcessor selectProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.makeSelectListFromIdent("str", "s0"), null, SupportEventAdapterService.Service);
            supportAggregationService = new SupportAggregationService();

            IList<ExprNode> groupKeyNodes = new List<ExprNode>();
            groupKeyNodes.Add(SupportExprNodeFactory.makeIdentNode("intPrimitive", "s0"));
            groupKeyNodes.Add(SupportExprNodeFactory.makeIdentNode("intBoxed", "s0"));

            processor = new ResultSetProcessorRowPerGroup(selectProcessor, null, supportAggregationService, groupKeyNodes, null, false, false);
        }

        [Test]
        public virtual void testProcess()
        {
            EventBean[] newData = new EventBean[] { MakeEvent(1, 2), MakeEvent(3, 4) };
            EventBean[] oldData = new EventBean[] { MakeEvent(1, 2), MakeEvent(1, 10) };

            Pair<EventBean[], EventBean[]> result = processor.processViewResult(newData, oldData);

            Assert.AreEqual(2, supportAggregationService.getEnterList().Count);
            Assert.AreEqual(2, supportAggregationService.getLeaveList().Count);

            Assert.AreEqual(3, result.First.Length);
            Assert.AreEqual(3, result.Second.Length);
        }

        private EventBean MakeEvent(int intPrimitive, int intBoxed)
        {
            SupportBean bean = new SupportBean();
            bean.intPrimitive = intPrimitive;
            bean.intBoxed = intBoxed;
            return SupportEventBeanFactory.createObject(bean);
        }
    }
}
