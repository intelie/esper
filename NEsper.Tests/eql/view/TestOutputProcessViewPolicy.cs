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

using net.esper.collection;
using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;
using net.esper.support.view;

namespace net.esper.eql.view
{
	[TestFixture]
	public class TestOutputProcessViewPolicy
	{
	    private OutputProcessView outputProcessViewUpdate;
	    private OutputProcessView outputProcessViewProcess;
	    private SupportSchemaNeutralView childViewNoJoin;
	    private SupportSchemaNeutralView childViewJoin;
	    private SupportResultSetProcessor resultSetProcessor;

	    [SetUp]
	    public void SetUp()
	    {
	        resultSetProcessor = new SupportResultSetProcessor();
	        outputProcessViewUpdate = new OutputProcessViewPolicy(resultSetProcessor, 1, null, null);
	        outputProcessViewProcess = new OutputProcessViewPolicy(resultSetProcessor, 2, null, null);

	        childViewNoJoin = new SupportSchemaNeutralView();
	        outputProcessViewUpdate.AddView(childViewNoJoin);
	        childViewJoin = new SupportSchemaNeutralView();
	        outputProcessViewProcess.AddView(childViewJoin);
	    }

	    [Test]
	    public void TestEventType()
	    {
	        Assert.AreSame(resultSetProcessor.ResultEventType, outputProcessViewUpdate.EventType);
	    }

	    [Test]
	    public void TestUpdate()
	    {
	        EventBean[] oldData = new EventBean[1];
	        EventBean[] newData = new EventBean[1];
	        oldData[0] = SupportEventBeanFactory.CreateObject(new SupportBean());
	        newData[0] = SupportEventBeanFactory.CreateObject(new SupportBean());

	        outputProcessViewUpdate.Update(newData, oldData);

	        Assert.AreSame(newData[0], childViewNoJoin.LastNewData[0]);
	        Assert.AreSame(oldData[0], childViewNoJoin.LastOldData[0]);
	    }

	    [Test]
	    public void TestProcess()
	    {
	        EventBean[] oldData = new EventBean[1];
	        EventBean[] newData = new EventBean[1];
	        oldData[0] = SupportEventBeanFactory.CreateObject(new SupportBean());
	        newData[0] = SupportEventBeanFactory.CreateObject(new SupportBean());

	        outputProcessViewProcess.Process(MakeEventSet(newData[0]), MakeEventSet(oldData[0]));

	        Assert.AreSame(newData[0], childViewJoin.LastNewData[0]);
	        Assert.AreSame(oldData[0], childViewJoin.LastOldData[0]);
	    }

	    private Set<MultiKey<EventBean>> MakeEventSet(EventBean _event)
	    {
	        Set<MultiKey<EventBean>> result = new HashSet<MultiKey<EventBean>>();
	        result.Add(new MultiKey<EventBean>(new EventBean[] { _event}));
	        return result;
	    }
	}
} // End of namespace
