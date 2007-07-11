///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestSelectExprJoinWildcardProcessor
	{
	    private SelectExprJoinWildcardProcessor processor;

	    [SetUp]
	    public void SetUp()
	    {
	        SupportStreamTypeSvc3Stream supportTypes = new SupportStreamTypeSvc3Stream();
	        processor = new SelectExprJoinWildcardProcessor(supportTypes.StreamNames, supportTypes.EventTypes,
	                SupportEventAdapterService.GetService(), null);
	    }

	    [Test]
	    public void testProcess()
	    {
	        EventBean[] testEvents = SupportStreamTypeSvc3Stream.SampleEvents;

	        EventBean result = processor.Process(testEvents, true);
	        Assert.AreEqual(testEvents[0].Underlying, result["s0"]);
	        Assert.AreEqual(testEvents[1].Underlying, result["s1"]);

	        // Test null events, such as in an outer join
	        testEvents[1] = null;
	        result = processor.Process(testEvents, true);
	        Assert.AreEqual(testEvents[0].Underlying, result["s0"]);
	        Assert.IsNull(result["s1"]);
	    }

	    [Test]
	    public void testType()
	    {
	        Assert.AreEqual(typeof(SupportBean), processor.ResultEventType.GetPropertyType("s0"));
	        Assert.AreEqual(typeof(SupportBean), processor.ResultEventType.GetPropertyType("s1"));
	    }
	}
} // End of namespace
