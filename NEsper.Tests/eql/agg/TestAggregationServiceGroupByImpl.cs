// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.collection;
using net.esper.eql.agg;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.eql;

namespace net.esper.eql.agg
{
	[TestFixture]
	public class TestAggregationServiceGroupByImpl
	{
	    private AggregationServiceGroupByImpl service;
	    private MultiKeyUntyped groupOneKey;
	    private MultiKeyUntyped groupTwoKey;
	    private MethodResolutionService methodResolutionService;

	    [SetUp]
	    public void SetUp()
	    {
	    	SupportAggregator[] aggregators = new SupportAggregator[2];
	        for (int i = 0; i < aggregators.Length; i++)
	        {
	            aggregators[i] = new SupportAggregator();
	        }
	        ExprEvaluator[] evaluators = new ExprEvaluator[] { new SupportExprNode(5), new SupportExprNode(2) };
	        methodResolutionService = new MethodResolutionServiceImpl(null);

	        service = new AggregationServiceGroupByImpl(evaluators, aggregators, methodResolutionService);

	        groupOneKey = new MultiKeyUntyped(new Object[] {"x", "y1"});
	        groupTwoKey = new MultiKeyUntyped(new Object[] {"x", "y2"});
	    }

	    [Test]
	    public void testGetValue()
	    {
	        // apply 3 rows to group key 1, all aggregators evaluated their sub-expressions(constants 5 and 2)
	        service.ApplyEnter(new EventBean[1], groupOneKey);
	        service.ApplyEnter(new EventBean[1], groupOneKey);
	        service.ApplyEnter(new EventBean[1], groupTwoKey);

	        service.SetCurrentRow(groupOneKey);
	        Assert.AreEqual(10, service.GetValue(0));
	        Assert.AreEqual(4, service.GetValue(1));
	        service.SetCurrentRow(groupTwoKey);
	        Assert.AreEqual(5, service.GetValue(0));
	        Assert.AreEqual(2, service.GetValue(1));

	        service.ApplyLeave(new EventBean[1], groupTwoKey);
	        service.ApplyLeave(new EventBean[1], groupTwoKey);
	        service.ApplyLeave(new EventBean[1], groupTwoKey);
	        service.ApplyLeave(new EventBean[1], groupOneKey);

	        service.SetCurrentRow(groupOneKey);
	        Assert.AreEqual(10 - 5, service.GetValue(0));
	        Assert.AreEqual(4 - 2, service.GetValue(1));
	        service.SetCurrentRow(groupTwoKey);
	        Assert.AreEqual(5 - 15, service.GetValue(0));
	        Assert.AreEqual(2 - 6, service.GetValue(1));
	    }
	}
} // End of namespace
