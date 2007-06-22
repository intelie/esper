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

using net.esper.compat;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestStreamTypeServiceImpl
	{
	    private StreamTypeServiceImpl serviceRegular;
	    private StreamTypeServiceImpl serviceStreamZeroUnambigous;
	    private StreamTypeServiceImpl serviceRequireStreamName;

	    [SetUp]
	    public void SetUp()
	    {
	        // Prepare regualar test service
	        EventType[] eventTypes = new EventType[] {
	            SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)),
	            SupportEventTypeFactory.CreateBeanType(typeof(SupportBean)),
	            SupportEventTypeFactory.CreateBeanType(typeof(SupportBean_A)),
	            SupportEventTypeFactory.CreateBeanType(typeof(SupportMarketDataBean))
	            };
	        String[] streamNames = new String[] {"s1", null, "s3", "s4"};
	        serviceRegular = new StreamTypeServiceImpl(eventTypes, streamNames);

	        // Prepare with stream-zero being unambigous
            LinkedDictionary<String, EventType> streamTypes = new LinkedDictionary<String, EventType>();
	        for (int i = 0; i < streamNames.Length; i++)
	        {
	            streamTypes.Put(streamNames[i], eventTypes[i]);
	        }
	        serviceStreamZeroUnambigous = new StreamTypeServiceImpl(streamTypes, true, false);

	        // Prepare requiring stream names for non-zero streams
	        serviceRequireStreamName = new StreamTypeServiceImpl(streamTypes, true, true);
	    }

	    [Test]
	    public void TestResolveByStreamAndPropNameInOne()
	    {
	        TryResolveByStreamAndPropNameInOne(serviceRegular);
	        TryResolveByStreamAndPropNameInOne(serviceStreamZeroUnambigous);
	        TryResolveByStreamAndPropNameInOne(serviceRequireStreamName);
	    }

	    [Test]
	    public void TestResolveByPropertyName()
	    {
	        TryResolveByPropertyName(serviceRegular);
	        serviceStreamZeroUnambigous.ResolveByPropertyName("boolPrimitive");
	        serviceRequireStreamName.ResolveByPropertyName("boolPrimitive");

	        try
	        {
	            serviceRequireStreamName.ResolveByPropertyName("volume");
	            Assert.Fail();
	        }
	        catch (PropertyNotFoundException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void TestResolveByStreamAndPropNameBoth()
	    {
	        TryResolveByStreamAndPropNameBoth(serviceRegular);
	        TryResolveByStreamAndPropNameBoth(serviceStreamZeroUnambigous);
	        TryResolveByStreamAndPropNameBoth(serviceRequireStreamName);
	    }

	    private static void TryResolveByStreamAndPropNameBoth(StreamTypeService service)
	    {
	        // Test lookup by stream name and prop name
	        PropertyResolutionDescriptor desc = service.ResolveByStreamAndPropName("s4", "volume");
	        Assert.AreEqual(3, (int) desc.StreamNum);
	        Assert.AreEqual(typeof(long?), desc.PropertyType);
	        Assert.AreEqual("volume", desc.PropertyName);
	        Assert.AreEqual("s4", desc.StreamName);
	        Assert.AreEqual(typeof(SupportMarketDataBean), desc.StreamEventType.UnderlyingType);

	        try
	        {
	            service.ResolveByStreamAndPropName("xxx", "volume");
	            Assert.Fail();
	        }
	        catch (StreamNotFoundException ex)
	        {
	            // Expected
	        }

	        try
	        {
	            service.ResolveByStreamAndPropName("s4", "xxxx");
	            Assert.Fail();
	        }
	        catch (PropertyNotFoundException ex)
	        {
	            // Expected
	        }
	    }

	    private static void TryResolveByPropertyName(StreamTypeService service)
	    {
	        // Test lookup by property name only
	        PropertyResolutionDescriptor desc = service.ResolveByPropertyName("volume");
	        Assert.AreEqual(3, (int) (desc.StreamNum));
	        Assert.AreEqual(typeof(long?), desc.PropertyType);
	        Assert.AreEqual("volume", desc.PropertyName);
	        Assert.AreEqual("s4", desc.StreamName);
	        Assert.AreEqual(typeof(SupportMarketDataBean), desc.StreamEventType.UnderlyingType);

	        try
	        {
	            service.ResolveByPropertyName("boolPrimitive");
	            Assert.Fail();
	        }
	        catch (DuplicatePropertyException ex)
	        {
	            // Expected
	        }

	        try
	        {
	            service.ResolveByPropertyName("xxxx");
	            Assert.Fail();
	        }
	        catch (PropertyNotFoundException ex)
	        {
	            // Expected
	        }
	    }

	    private static void TryResolveByStreamAndPropNameInOne(StreamTypeService service)
	    {
	        // Test lookup by stream name and prop name
	        PropertyResolutionDescriptor desc = service.ResolveByStreamAndPropName("s4.volume");
	        Assert.AreEqual(3, (int) desc.StreamNum);
	        Assert.AreEqual(typeof(long?), desc.PropertyType);
	        Assert.AreEqual("volume", desc.PropertyName);
	        Assert.AreEqual("s4", desc.StreamName);
	        Assert.AreEqual(typeof(SupportMarketDataBean), desc.StreamEventType.UnderlyingType);

	        try
	        {
	            service.ResolveByStreamAndPropName("xxx.volume");
	            Assert.Fail();
	        }
	        catch (PropertyNotFoundException ex)
	        {
	            // Expected
	        }

	        try
	        {
	            service.ResolveByStreamAndPropName("s4.xxxx");
	            Assert.Fail();
	        }
	        catch (PropertyNotFoundException ex)
	        {
	            // Expected
	        }
	    }
	}
} // End of namespace
