// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;

using NUnit.Framework;

using net.esper.compat;
using net.esper.events;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.filter;
using net.esper.view;

namespace net.esper.view.stream
{
	[TestFixture]
	public class TestStreamFactorySvcImpl
	{
	    private StreamFactoryService streamFactoryService;
	    private SupportFilterServiceImpl supportFilterService;

	    private FilterSpecCompiled[] filterSpecs;
	    private EventStream[] streams;

	    [SetUp]
	    public void SetUp()
	    {
	        supportFilterService = new SupportFilterServiceImpl();
	        streamFactoryService = new StreamFactorySvcImpl(SupportEventAdapterService.GetService());
	        EventType eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));

	        filterSpecs = new FilterSpecCompiled[3];
	        filterSpecs[0] = SupportFilterSpecBuilder.Build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
	        filterSpecs[1] = SupportFilterSpecBuilder.Build(eventType, new Object[] { "string", FilterOperator.EQUAL, "a" });
	        filterSpecs[2] = SupportFilterSpecBuilder.Build(eventType, new Object[] { "string", FilterOperator.EQUAL, "b" });
	    }

	    [Test]
	    public void testInvalidJoin()
	    {
	        streams = new EventStream[3];
	        streams[0] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, true);

	        try
	        {
	            // try to reuse the same filter spec object, should fail
	            streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, true);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testCreateJoin()
	    {
	        streams = new EventStream[3];
	        streams[0] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, true);
	        streams[1] = streamFactoryService.CreateStream(filterSpecs[1], supportFilterService, null, true);
	        streams[2] = streamFactoryService.CreateStream(filterSpecs[2], supportFilterService, null, true);

	        // Streams are reused
	        Assert.AreNotSame(streams[0], streams[1]);
            Assert.AreNotSame(streams[0], streams[2]);
            Assert.AreNotSame(streams[1], streams[2]);

	        // Type is ok
	        Assert.AreEqual(typeof(SupportBean), streams[0].EventType.UnderlyingType);

	        // 2 filters are active now
	        Assert.AreEqual(3, supportFilterService.GetAdded().Count);
	    }

	    [Test]
	    public void testDropJoin()
	    {
	        streams = new EventStream[3];
	        streams[0] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, true);
	        streams[1] = streamFactoryService.CreateStream(filterSpecs[1], supportFilterService, null, true);
	        streams[2] = streamFactoryService.CreateStream(filterSpecs[2], supportFilterService, null, true);

	        streamFactoryService.DropStream(filterSpecs[0], supportFilterService, true);
	        streamFactoryService.DropStream(filterSpecs[1], supportFilterService, true);
	        Assert.AreEqual(2, supportFilterService.GetRemoved().Count);

	        // Filter removed
	        streamFactoryService.DropStream(filterSpecs[2], supportFilterService, true);
	        Assert.AreEqual(3, supportFilterService.GetRemoved().Count);

	        // Something already removed
	        try
	        {
	            streamFactoryService.DropStream(filterSpecs[2], supportFilterService, true);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testCreateNoJoin()
	    {
	        streams = new EventStream[4];
	        streams[0] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, false);
	        streams[1] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, false);
	        streams[2] = streamFactoryService.CreateStream(filterSpecs[1], supportFilterService, null, false);
	        streams[3] = streamFactoryService.CreateStream(filterSpecs[2], supportFilterService, null, false);

	        // Streams are reused
	        Assert.AreSame(streams[0], streams[1]);
	        Assert.AreSame(streams[0], streams[2]);
            Assert.AreNotSame(streams[0], streams[3]);

	        // Type is ok
	        Assert.AreEqual(typeof(SupportBean), streams[0].EventType.UnderlyingType);

	        // 2 filters are active now
	        Assert.AreEqual(2, supportFilterService.GetAdded().Count);
	    }

	    [Test]
	    public void testDropNoJoin()
	    {
	        streams = new EventStream[4];
	        streams[0] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, false);
	        streams[1] = streamFactoryService.CreateStream(filterSpecs[0], supportFilterService, null, false);
	        streams[2] = streamFactoryService.CreateStream(filterSpecs[1], supportFilterService, null, false);
	        streams[3] = streamFactoryService.CreateStream(filterSpecs[2], supportFilterService, null, false);

	        streamFactoryService.DropStream(filterSpecs[0], supportFilterService, false);
	        streamFactoryService.DropStream(filterSpecs[1], supportFilterService, false);
	        Assert.AreEqual(0, supportFilterService.GetRemoved().Count);

	        // Filter removed
	        streamFactoryService.DropStream(filterSpecs[0], supportFilterService, false);
	        Assert.AreEqual(1, supportFilterService.GetRemoved().Count);

	        streamFactoryService.DropStream(filterSpecs[2], supportFilterService, false);
	        Assert.AreEqual(2, supportFilterService.GetRemoved().Count);

	        // Something already removed
	        try
	        {
	            streamFactoryService.DropStream(filterSpecs[2], supportFilterService, false);
	            Assert.Fail();
	        }
	        catch (IllegalStateException ex)
	        {
	            // Expected
	        }
	    }
	}
} // End of namespace
