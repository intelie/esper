// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.eql.agg;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.eql;

namespace net.esper.eql.agg
{
	[TestFixture]
	public class TestAggregationServiceGroupAllImpl
	{
	    private AggregationServiceGroupAllImpl service;

	    [SetUp]
	    public void SetUp()
	    {
	    	SupportAggregator[] aggregators = new SupportAggregator[2];
	        for (int i = 0; i < aggregators.Length; i++)
	        {
	            aggregators[i] = new SupportAggregator();
	        }

	        ExprEvaluator[] evaluators = new ExprEvaluator[] { new SupportExprNode(5), new SupportExprNode(2) };

	        service = new AggregationServiceGroupAllImpl(evaluators, aggregators);
	    }

	    [Test]
	    public void testApplyEnter()
	    {
	        // apply two rows, all aggregators evaluated their sub-expressions(constants 5 and 2) twice
	        service.ApplyEnter(new EventBean[1], null);
	        service.ApplyEnter(new EventBean[1], null);
	        Assert.AreEqual(10, service.GetValue(0));
	        Assert.AreEqual(4, service.GetValue(1));
	    }

	    [Test]
	    public void testApplyLeave()
	    {
	        // apply 3 rows, all aggregators evaluated their sub-expressions(constants 5 and 2)
	        service.ApplyLeave(new EventBean[1], null);
	        service.ApplyLeave(new EventBean[1], null);
	        service.ApplyLeave(new EventBean[1], null);
	        Assert.AreEqual(-15, service.GetValue(0));
	        Assert.AreEqual(-6, service.GetValue(1));
	    }

	    private static EventBean[][] MakeEvents(int countRows)
	    {
	        EventBean[][] result = new EventBean[countRows][];
	        for (int i = 0; i < countRows; i++)
	        {
	            result[i] = new EventBean[0];
	        }
	        return result;
	    }
	}
} // End of namespace
