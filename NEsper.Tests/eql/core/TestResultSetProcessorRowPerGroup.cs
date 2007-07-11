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
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestResultSetProcessorRowPerGroup
	{
	    private ResultSetProcessorRowPerGroup processor;
	    private SupportAggregationService supportAggregationService;

	    [SetUp]
	    public void SetUp()
	    {
	        SelectExprProcessor selectProcessor = new SelectExprEvalProcessor(SupportSelectExprFactory.MakeSelectListFromIdent("string", "s0"),
	        		null, false, new SupportStreamTypeSvc1Stream(), SupportEventAdapterService.GetService());
	        supportAggregationService = new SupportAggregationService();

	        IList<ExprNode> groupKeyNodes = new List<ExprNode>();
	        groupKeyNodes.Add(SupportExprNodeFactory.MakeIdentNode("intPrimitive", "s0"));
	        groupKeyNodes.Add(SupportExprNodeFactory.MakeIdentNode("intBoxed", "s0"));

	        processor = new ResultSetProcessorRowPerGroup(selectProcessor, null, supportAggregationService, groupKeyNodes, null, false, false);
	    }

	    [Test]
	    public void testProcess()
	    {
	        EventBean[] newData = new EventBean[] {MakeEvent(1, 2), MakeEvent(3, 4)};
	        EventBean[] oldData = new EventBean[] {MakeEvent(1, 2), MakeEvent(1, 10)};

	        Pair<EventBean[], EventBean[]> result = processor.ProcessViewResult(newData, oldData);

	        Assert.AreEqual(2, supportAggregationService.EnterList.Count);
	        Assert.AreEqual(2, supportAggregationService.LeaveList.Count);

	        Assert.AreEqual(3, result.First.Length);
	        Assert.AreEqual(3, result.Second.Length);
	    }

	    private EventBean MakeEvent(int intPrimitive, int intBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetIntBoxed(intBoxed);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
