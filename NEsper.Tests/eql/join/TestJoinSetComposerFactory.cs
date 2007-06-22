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

using net.esper.eql.join.exec;
using net.esper.eql.join.table;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.view;

namespace net.esper.eql.join
{
	[TestFixture]
	public class TestJoinSetComposerFactory
	{
	    private EventType[] streamTypes;
	    private Viewable[] streamViewables;

	    [SetUp]
	    public void SetUp()
	    {
	        streamTypes = new EventType[2];
	        streamTypes[0] = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        streamTypes[1] = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean_A));

	        streamViewables = new Viewable[2];
	    }

	    [Test]
	    public void TestBuildIndex()
	    {
	        EventTable table = JoinSetComposerFactory.BuildIndex(0, new String[] {"intPrimitive", "boolBoxed"}, null, streamTypes[0]);
	        Assert.IsTrue(table is PropertyIndexedEventTable);

	        table = JoinSetComposerFactory.BuildIndex(0, new String[0], null, streamTypes[0]);
	        Assert.IsTrue(table is UnindexedEventTable);

	        try
	        {
	            JoinSetComposerFactory.BuildIndex(0, null, null, streamTypes[0]);
	            Assert.Fail();
	        }
	        catch (ArgumentException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void TestBuildComposer()
	    {
	        IList<OuterJoinDesc> outerJoins = new List<OuterJoinDesc>();
	        JoinSetComposerImpl composer = (JoinSetComposerImpl) JoinSetComposerFactory.MakeComposer(outerJoins, new SupportExprNode(true), streamTypes, new String[]{"a", "b", "c", "d"}, streamViewables, SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH);

	        // verify default indexes build
	        Assert.AreEqual(2, composer.Tables.Length);
	        Assert.IsTrue(composer.Tables[0][0] is UnindexedEventTable);
	        Assert.IsTrue(composer.Tables[1][0] is UnindexedEventTable);

	        // verify default strategies
	        Assert.AreEqual(2, composer.QueryStrategies.Length);
	        ExecNodeQueryStrategy plan = (ExecNodeQueryStrategy) composer.QueryStrategies[0];
	        Assert.AreEqual(0, plan.ForStream);
	        Assert.AreEqual(2, plan.NumStreams);
	        Assert.IsTrue(plan.ExecNode is TableLookupExecNode);
	        plan = (ExecNodeQueryStrategy) composer.QueryStrategies[1];
	        Assert.AreEqual(1, plan.ForStream);
	        Assert.AreEqual(2, plan.NumStreams);
	        Assert.IsTrue(plan.ExecNode is TableLookupExecNode);
	    }
	}
} // End of namespace
