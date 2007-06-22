///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.collection;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.eql;

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
	    private MethodResolutionService methodResolutionService;

	    [SetUp]
	    public void SetUp()
	    {
	        typeService1Stream = new SupportStreamTypeSvc1Stream();
	        typeService3Stream = new SupportStreamTypeSvc3Stream();
	        groupByList = new List<ExprNode>();
	        eventAdapterService = new EventAdapterServiceImpl();
	        orderByList = new List<Pair<ExprNode, Boolean>>();
	        methodResolutionService = new MethodResolutionServiceImpl(new EngineImportServiceImpl());
	    }

	    [Test]
	    public void TestGetProcessorNoProcessorRequired()
	    {
	        // single stream, empty group-by and wildcard select, no having clause, no need for any output processing
	        IList<SelectExprElementRawSpec> wildcardSelect = new List<SelectExprElementRawSpec>();
	        ResultSetProcessor processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(wildcardSelect), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsNull(processor);
	    }

	    [Test]
	    public void TestGetProcessorSimpleSelect()
	    {
	        // empty group-by and no event properties aggregated in select clause (wildcard), no having clause
	        IList<SelectExprElementRawSpec> wildcardSelect = new List<SelectExprElementRawSpec>();
	        ResultSetProcessor processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(wildcardSelect), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorSimple);

	        // empty group-by with select clause elements
            IList<SelectExprElementRawSpec> selectList = SupportSelectExprFactory.MakeNoAggregateSelectListUnnamed();
	        processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorSimple);

	        // non-empty group-by and wildcard select, group by ignored
	        groupByList.Add(SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0"));
	        processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(wildcardSelect), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorSimple);
	    }

	    [Test]
	    public void TestGetProcessorAggregatingAll()
	    {
	        // empty group-by but aggragating event properties in select clause (output per event), no having clause
	        // and one or more properties in the select clause is not aggregated
	        IList<SelectExprElementRawSpec> selectList = SupportSelectExprFactory.MakeAggregateMixed();
	        ResultSetProcessor processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorAggregateAll);

	        // test a case where a property is both aggregated and non-aggregated: select volume, Sum(volume)
	        selectList = SupportSelectExprFactory.MakeAggregatePlusNoAggregate();
	        processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorAggregateAll);
	    }

	    [Test]
	    public void TestGetProcessorRowForAll()
	    {
	        // empty group-by but aggragating event properties in select clause (output per event), no having clause
	        // and all properties in the select clause are aggregated
            IList<SelectExprElementRawSpec> selectList = SupportSelectExprFactory.MakeAggregateSelectListWithProps();
	        ResultSetProcessor processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorRowForAll);
	    }

	    [Test]
	    public void TestGetProcessorRowPerGroup()
	    {
	        // with group-by and the non-aggregated event properties are all listed in the group by (output per group)
	        // no having clause
            IList<SelectExprElementRawSpec> selectList = SupportSelectExprFactory.MakeAggregateMixed();
	        groupByList.Add(SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0"));
	        ResultSetProcessor processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorRowPerGroup);
	    }

	    [Test]
	    public void TestGetProcessorAggregatingGrouped()
	    {
	        // with group-by but either
	        //      wildcard
	        //      or one or more non-aggregated event properties are not in the group by (output per event)
            IList<SelectExprElementRawSpec> selectList = SupportSelectExprFactory.MakeAggregateMixed();
	        ExprNode identNode = SupportExprNodeFactory.MakeIdentNode("string", "s0");
	        selectList.Add(new SelectExprElementRawSpec(identNode, null));

	        groupByList.Add(SupportExprNodeFactory.MakeIdentNode("doubleBoxed", "s0"));
	        ResultSetProcessor processor = ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService1Stream, eventAdapterService, methodResolutionService, null);
	        Assert.IsTrue(processor is ResultSetProcessorAggregateGrouped);
	    }

	    [Test]
	    public void TestGetProcessorInvalid()
	    {
	        // invalid select clause
	        try
	        {
	            ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(SupportSelectExprFactory.MakeInvalidSelectList()), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, methodResolutionService, null);
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
	            ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(SupportSelectExprFactory.MakeNoAggregateSelectListUnnamed()), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, methodResolutionService, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }

	        // Test group by having properties that are aggregated in select clause, should fail
	        groupByList.Clear();
	        groupByList.Add(SupportExprNodeFactory.MakeSumAggregateNode());

	        List<SelectExprElementRawSpec> selectList = new List<SelectExprElementRawSpec>();
	        selectList.Add(new SelectExprElementRawSpec(SupportExprNodeFactory.MakeSumAggregateNode(), null));

	        try
	        {
	            ResultSetProcessorFactory.GetProcessor(new SelectClauseSpec(selectList), null, groupByList, null, null, orderByList, typeService3Stream, eventAdapterService, methodResolutionService, null);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }
	}
} // End of namespace
