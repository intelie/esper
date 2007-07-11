// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.support.eql;

namespace net.esper.eql.agg
{
	[TestFixture]
	public class TestAggregationServiceFactory
	{
	    private List<ExprAggregateNode> selectAggregateNodes;
	    private List<ExprAggregateNode> havingAggregateNodes;
	    private List<ExprAggregateNode> orderByAggregateNodes;
	    private List<ExprNode> sortByNodes;
	    private MethodResolutionService methodResolutionService;

	    [SetUp]
	    public void SetUp()
	    {
	        selectAggregateNodes = new List<ExprAggregateNode>();
	        havingAggregateNodes = new List<ExprAggregateNode>();
	        orderByAggregateNodes = new List<ExprAggregateNode>();
	        sortByNodes = new List<ExprNode>();
	        methodResolutionService = new MethodResolutionServiceImpl(null);
	    }

	    [Test]
	    public void testGetService()
	    {
	        // Test with aggregates but no group by
	        selectAggregateNodes.Add(SupportExprNodeFactory.MakeSumAggregateNode());
	        AggregationService service = AggregationServiceFactory.GetService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService);
	        Assert.IsTrue(service is AggregationServiceGroupAllImpl);

	        // Test with aggregates and group by
	        service = AggregationServiceFactory.GetService(selectAggregateNodes, havingAggregateNodes, orderByAggregateNodes, true, methodResolutionService);
	        Assert.IsTrue(service is AggregationServiceGroupByImpl);
	    }

	    [Test]
	    public void testGetNullService()
	    {
	        // Test no aggregates and no group-by
	    	AggregationService service = AggregationServiceFactory.GetService(selectAggregateNodes,havingAggregateNodes, orderByAggregateNodes, false, methodResolutionService);
	    	Assert.IsTrue(service is AggregationServiceNull);
	    }
	}
} // End of namespace
