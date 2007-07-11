///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;
using System.Xml.XPath;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;

namespace net.esper.events
{
	[TestFixture]
	public class TestEventAdapterServiceImpl
	{
	    private EventAdapterServiceImpl adapterService;

	    [SetUp]
	    public void SetUp()
	    {
	        adapterService = new EventAdapterServiceImpl();
	    }

	    [Test]
	    public void testSelfRefEvent()
	    {
	        EventBean originalBean = adapterService.AdapterForBean(new SupportSelfReferenceEvent());
	        Assert.AreEqual(null, originalBean["selfRef.selfRef.selfRef.value"]);
	    }

	    [Test]
	    public void testEventTypeId()
	    {
	        EventBean originalBean = adapterService.AdapterForBean(new SupportSerializableBean("e1"));
	        EventTypeSPI eventType = (EventTypeSPI) originalBean.EventType;
	        String id = eventType.EventTypeId;
	        Assert.AreEqual("TYPE_net.esper.support.bean.SupportSerializableBean", id);
	    }

	    [Test]
	    public void testCreateMapType()
	    {
	        EDictionary<String, Type> testTypesMap;
            testTypesMap = new HashDictionary<String, Type>();
	        testTypesMap.Put("key1", typeof(String));
	        EventType eventType = adapterService.CreateAnonymousMapType(testTypesMap);

	        Assert.AreEqual(typeof(IDataDictionary), eventType.UnderlyingType);
	        Assert.AreEqual(1, eventType.PropertyNames.Count);
	        Assert.AreEqual("key1", CollectionHelper.First(eventType.PropertyNames));
	    }

	    [Test]
	    public void testGetType()
	    {
	        adapterService.AddBeanType("NAME", typeof(TestEventAdapterServiceImpl).FullName);

	        EventType type = adapterService.GetEventTypeByAlias("NAME");
	        Assert.AreEqual(typeof(TestEventAdapterServiceImpl), type.UnderlyingType);

	        EventType typeTwo = adapterService.GetEventTypeByAlias(typeof(TestEventAdapterServiceImpl).FullName);
	        Assert.AreSame(typeTwo, typeTwo);

	        Assert.IsNull(adapterService.GetEventTypeByAlias("xx"));
	    }

	    [Test]
	    public void testAddInvalid()
	    {
	        try
	        {
	            adapterService.AddBeanType("x", "xx");
	            Assert.Fail();
	        }
	        catch (EventAdapterException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testAddMapType()
	    {
	        EDictionary<String, Type> props = new HashDictionary<String, Type>();
	        props.Put("a", typeof(long?));
	        props.Put("b", typeof(String));

	        // check result type
	        EventType typeOne = adapterService.AddMapType("latencyEvent", props);
	        Assert.AreEqual(typeof(long?), typeOne.GetPropertyType("a"));
	        Assert.AreEqual(typeof(String), typeOne.GetPropertyType("b"));
	        Assert.AreEqual(2, typeOne.PropertyNames.Count);

	        String id = adapterService.GetIdByAlias("latencyEvent");
	        Assert.IsNotNull(id);
	        EventType typeById = adapterService.GetTypeById(id);
	        Assert.AreSame(typeById, typeOne);
	        Assert.AreEqual("latencyEvent", adapterService.GetAliasById(id));
	        Assert.AreEqual(id, adapterService.GetIdByType(typeOne));
	        Assert.AreSame(typeOne, adapterService.GetEventTypeByAlias("latencyEvent"));

	        // add the same type with the same name, should succeed and return the same reference
	        EventType typeTwo = adapterService.AddMapType("latencyEvent", props);
	        Assert.AreSame(typeOne, typeTwo);

	        // add the same name with a different type, should fail
	        props.Put("b", typeof(bool));
	        try
	        {
	            adapterService.AddMapType("latencyEvent", props);
	            Assert.Fail();
	        }
	        catch (EventAdapterException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddWrapperType()
	    {
	        EventType beanEventType = adapterService.AddBeanType("mybean", typeof(SupportMarketDataBean));
            EDictionary<String, Type> props = new HashDictionary<String, Type>();
	        props.Put("a", typeof(long?));
	        props.Put("b", typeof(String));

	        // check result type
	        EventType typeOne = adapterService.AddWrapperType("latencyEvent", beanEventType, props);
	        Assert.AreEqual(typeof(long?), typeOne.GetPropertyType("a"));
	        Assert.AreEqual(typeof(String), typeOne.GetPropertyType("b"));
	        Assert.AreEqual(7, typeOne.PropertyNames.Count);

	        String id = adapterService.GetIdByAlias("latencyEvent");
	        Assert.IsNotNull(id);
	        EventType typeById = adapterService.GetTypeById(id);
	        Assert.AreSame(typeById, typeOne);
	        Assert.AreEqual("latencyEvent", adapterService.GetAliasById(id));
	        Assert.AreEqual(id, adapterService.GetIdByType(typeOne));
	        Assert.AreSame(typeOne, adapterService.GetEventTypeByAlias("latencyEvent"));

	        // add the same name with a different type, should fail
	        props.Put("b", typeof(bool));
	        try
	        {
	            EventType beanTwoEventType = adapterService.AddBeanType("mybean", typeof(SupportBean));
	            adapterService.AddWrapperType("latencyEvent", beanTwoEventType, props);
	            Assert.Fail();
	        }
	        catch (EventAdapterException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddClassName()
	    {
	        EventType typeOne = adapterService.AddBeanType("latencyEvent", typeof(SupportBean).FullName);
	        Assert.AreEqual(typeof(SupportBean), typeOne.UnderlyingType);

	        String id = adapterService.GetIdByAlias("latencyEvent");
	        Assert.IsNotNull(id);
	        EventType typeById = adapterService.GetTypeById(id);
	        Assert.AreSame(typeById, typeOne);
	        Assert.AreEqual("latencyEvent", adapterService.GetAliasById(id));
	        Assert.AreEqual(id, adapterService.GetIdByType(typeOne));
	        Assert.AreSame(typeOne, adapterService.GetEventTypeByAlias("latencyEvent"));

	        EventType typeTwo = adapterService.AddBeanType("latencyEvent", typeof(SupportBean).FullName);
	        Assert.AreSame(typeOne, typeTwo);

	        try
	        {
	            adapterService.AddBeanType("latencyEvent", typeof(SupportBean_A).FullName);
	            Assert.Fail();
	        }
	        catch (EventAdapterException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddClass()
	    {
	        EventType typeOne = adapterService.AddBeanType("latencyEvent", typeof(SupportBean));
	        Assert.AreEqual(typeof(SupportBean), typeOne.UnderlyingType);

	        String id = adapterService.GetIdByAlias("latencyEvent");
	        Assert.IsNotNull(id);
	        EventType typeById = adapterService.GetTypeById(id);
	        Assert.AreSame(typeById, typeOne);
	        Assert.AreEqual("latencyEvent", adapterService.GetAliasById(id));
	        Assert.AreEqual(id, adapterService.GetIdByType(typeOne));
	        Assert.AreSame(typeOne, adapterService.GetEventTypeByAlias("latencyEvent"));

	        EventType typeTwo = adapterService.AddBeanType("latencyEvent", typeof(SupportBean));
	        Assert.AreSame(typeOne, typeTwo);

	        try
	        {
	            adapterService.AddBeanType("latencyEvent", typeof(SupportBean_A).FullName);
	            Assert.Fail();
	        }
	        catch (EventAdapterException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testWrap()
	    {
	        SupportBean bean = new SupportBean();
	        EventBean _event = adapterService.AdapterForBean(bean);
	        Assert.AreSame(_event.Underlying, bean);
	    }

	    [Test]
	    public void testCreateAddToEventType()
	    {
            EDictionary<String, Type> schema = new HashDictionary<String, Type>();
	        schema.Put("STDDEV", typeof(double?));
	        EventType parentEventType = adapterService.CreateAnonymousMapType(schema);

	        EventType newEventType = adapterService.CreateAddToEventType(parentEventType, new String[] {"test"}, new Type[] {typeof(int?)});

	        Assert.IsTrue(newEventType.IsProperty("test"));
	        Assert.AreEqual(typeof(int?), newEventType.GetPropertyType("test"));
	    }

	    [Test]
	    public void testAddXMLDOMType()
	    {
	        adapterService.AddXMLDOMType("XMLDOMTypeOne", GetXMLDOMConfig());
	        EventType eventType = adapterService.GetEventTypeByAlias("XMLDOMTypeOne");
	        Assert.AreEqual(typeof(XmlNode), eventType.UnderlyingType);

	        String id = adapterService.GetIdByAlias("XMLDOMTypeOne");
	        Assert.IsNotNull(id);
	        EventType typeById = adapterService.GetTypeById(id);
	        Assert.AreSame(typeById, eventType);
	        Assert.AreEqual("XMLDOMTypeOne", adapterService.GetAliasById(id));
	        Assert.AreEqual(id, adapterService.GetIdByType(eventType));
	        Assert.AreSame(eventType, adapterService.GetEventTypeByAlias("XMLDOMTypeOne"));

	        try
	        {
	            adapterService.AddXMLDOMType("a", new ConfigurationEventTypeXMLDOM());
	            Assert.Fail();
	        }
	        catch (EventAdapterException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAdapterForDOM()
	    {
	        adapterService.AddXMLDOMType("XMLDOMTypeOne", GetXMLDOMConfig());

	        String xml =
	                "<simpleEvent>\n" +
	                "  <nested1>value</nested1>\n" +
	                "</simpleEvent>";

            XmlDocument simpleDoc = new XmlDocument();
	        simpleDoc.LoadXml(xml);

	        EventBean bean = adapterService.AdapterForDOM(simpleDoc);
	        Assert.AreEqual("value", bean["nested1"]);
	    }

	    private static ConfigurationEventTypeXMLDOM GetXMLDOMConfig()
	    {
	        ConfigurationEventTypeXMLDOM config = new ConfigurationEventTypeXMLDOM();
	        config.RootElementName = "simpleEvent";
	        config.AddXPathProperty("nested1", "/simpleEvent/nested1", XPathResultType.String);
	        return config;
	    }
	}
} // End of namespace
