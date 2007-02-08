using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.eql;

using NUnit.Core;
using NUnit.Framework;

namespace net.esper.eql.core
{
	[TestFixture]
    public class TestResultSetProcessorFactory 
    {
        private StreamTypeService typeService1Stream;
        private StreamTypeService typeService3Stream;
        private IList<ExprNode> groupByList;
        private EventAdapterService eventAdapterService;
        private IList<Pair<ExprNode, Boolean>> orderByList;

        [SetUp]
        public virtual void setUp()
        {
            typeService1Stream = new SupportStreamTypeSvc1Stream();
            typeService3Stream = new SupportStreamTypeSvc3Stream();
            groupByList = new List<ExprNode>();
            eventAdapterService = new EventAdapterServiceImpl(null);
            orderByList = new List<Pair<ExprNode, Boolean>>();
        }

        [Test]
        public virtual void testGetProcessorNoProcessorRequired()
        {
            // single stream, empty group-by and wildcard select, no having clause, no need for any output processing
            IList<SelectExprElementUnnamedSpec> wildcardSelect = new List<SelectExprElementUnnamedSpec>();
            ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(wildcardSelect, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsNull(processor);
        }

        [Test]
        public virtual void testGetProcessorSimpleSelect()
        {
            // empty group-by and no event properties aggregated in select clause (wildcard), no having clause
            IList<SelectExprElementUnnamedSpec> wildcardSelect = new List<SelectExprElementUnnamedSpec>();
            ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(wildcardSelect, null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorSimple);

            // empty group-by with select clause elements
	        	IList<SelectExprElementUnnamedSpec> selectList = SupportSelectExprFactory.makeNoAggregateSelectListUnnamed();
            processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorSimple);

            // non-empty group-by and wildcard select, group by ignored
            groupByList.Add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
            processor = ResultSetProcessorFactory.getProcessor(wildcardSelect, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorSimple);
        }

        [Test]
        public virtual void testGetProcessorAggregatingAll()
        {
            // empty group-by but aggragating event properties in select clause (output per event), no having clause
            // and one or more properties in the select clause is not aggregated
            IList<SelectExprElementUnnamedSpec> selectList = SupportSelectExprFactory.makeAggregateMixed();
            ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorAggregateAll);

            // test a case where a property is both aggregated and non-aggregated: select volume, sum(volume)
            selectList = SupportSelectExprFactory.makeAggregatePlusNoAggregate();
            processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorAggregateAll);
        }

        [Test]
        public virtual void testGetProcessorRowForAll()
        {
            // empty group-by but aggragating event properties in select clause (output per event), no having clause
            // and all properties in the select clause are aggregated
            IList<SelectExprElementUnnamedSpec> selectList = SupportSelectExprFactory.makeAggregateSelectListWithProps();
            ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorRowForAll);
        }

        [Test]
        public virtual void testGetProcessorRowPerGroup()
        {
            // with group-by and the non-aggregated event properties are all listed in the group by (output per group)
            // no having clause
            IList<SelectExprElementUnnamedSpec> selectList = SupportSelectExprFactory.makeAggregateMixed();
            groupByList.Add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));
            ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);
            Assert.IsTrue(processor is ResultSetProcessorRowPerGroup);
        }

        [Test]
        public virtual void testGetProcessorAggregatingGrouped()
        {
            // with group-by but either
            //      wildcard
            //      or one or more non-aggregated event properties are not in the group by (output per event)
            IList<SelectExprElementUnnamedSpec> selectList = SupportSelectExprFactory.makeAggregateMixed();
            ExprNode identNode = SupportExprNodeFactory.makeIdentNode("string", "s0");
            selectList.Add(new SelectExprElementUnnamedSpec(identNode, null));

            groupByList.Add(SupportExprNodeFactory.makeIdentNode("doubleBoxed", "s0"));

            ResultSetProcessor processor = ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, null);

            Assert.IsTrue(processor is ResultSetProcessorAggregateGrouped);
        }

        [Test]
        public virtual void testGetProcessorInvalid()
        {
            // invalid select clause
            try
            {
                ResultSetProcessorFactory.getProcessor(SupportSelectExprFactory.makeInvalidSelectList(), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }

            // invalid group-by
            groupByList.Add(new ExprIdentNode("xxxx", "s0"));
            try
            {
                ResultSetProcessorFactory.getProcessor(SupportSelectExprFactory.makeNoAggregateSelectListUnnamed(), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }

            // Test group by having properties that are aggregated in select clause, should fail
            groupByList.Clear();
            groupByList.Add(SupportExprNodeFactory.makeSumAggregateNode());

            IList<SelectExprElementUnnamedSpec> selectList = new List<SelectExprElementUnnamedSpec>();
            selectList.Add(new SelectExprElementUnnamedSpec(SupportExprNodeFactory.makeSumAggregateNode(), null));

            try
            {
                ResultSetProcessorFactory.getProcessor(selectList, null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, null);
                Assert.Fail();
            }
            catch (ExprValidationException ex)
            {
                // expected
            }
        }
    }
}
