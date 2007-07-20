// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;
using System.IO;
using System.Xml;

using NUnit.Framework;

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.regression.client
{
	[TestFixture]
	public class TestConfigurationOperations
	{
	    private EPServiceProvider epService;
	    private SupportUpdateListener testListener;
	    private ConfigurationOperations configOps;

	    [SetUp]
	    public void SetUp()
	    {
	        testListener = new SupportUpdateListener();
	        epService = EPServiceProviderManager.GetDefaultProvider();
	        epService.Initialize();
	        configOps = epService.EPAdministrator.Configuration;
	    }

	    [Test]
	    public void testAddDOMType()
	    {
	        TryInvalid("AddedDOMOne");

	        // First statement with new name
	        ConfigurationEventTypeXMLDOM domConfig = new ConfigurationEventTypeXMLDOM();
	        domConfig.RootElementName = "RootAddedDOMOne";
	        configOps.AddEventTypeAlias("AddedDOMOne", domConfig);

	        EPStatement stmt = epService.EPAdministrator.CreateEQL("select * from AddedDOMOne");
	        stmt.AddListener(testListener);

	        XmlDocument eventOne = MakeDOMEvent("RootAddedDOMOne");
	        epService.EPRuntime.SendEvent(eventOne);
	        Assert.AreSame(eventOne, testListener.AssertOneGetNewAndReset().Underlying);

	        TryInvalid("AddedMapNameSecond");

	        // Second statement using a new alias to the same type, should both receive
	        domConfig = new ConfigurationEventTypeXMLDOM();
	        domConfig.RootElementName = "RootAddedDOMOne";
	        configOps.AddEventTypeAlias("AddedDOMSecond", domConfig);

	        configOps.AddEventTypeAlias("AddedMapNameSecond", domConfig);
	        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
	        stmt = epService.EPAdministrator.CreateEQL("select * from AddedMapNameSecond");
            stmt.AddListener(testListenerTwo);

            XmlDocument eventTwo = MakeDOMEvent("RootAddedDOMOne");
	        epService.EPRuntime.SendEvent(eventTwo);
	        Assert.IsFalse(testListener.IsInvoked);
	        Assert.AreEqual(eventTwo, testListenerTwo.AssertOneGetNewAndReset().Underlying);

	        // Add the same alias and type again
	        domConfig = new ConfigurationEventTypeXMLDOM();
	        domConfig.RootElementName = "RootAddedDOMOne";
	        configOps.AddEventTypeAlias("AddedDOMSecond", domConfig);

	        // Add the same alias and a different type
	        try
	        {
	            domConfig = new ConfigurationEventTypeXMLDOM();
	            domConfig.RootElementName = "RootAddedDOMXXX";
	            configOps.AddEventTypeAlias("AddedDOMSecond", domConfig);
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddMapByClass()
	    {
	        TryInvalid("AddedMapOne");

	        // First statement with new name
	        EDictionary<String, Type> mapProps = new HashDictionary<String, Type>();
	        mapProps.Put("prop1", typeof(int));
	        configOps.AddEventTypeAlias("AddedMapOne", mapProps);

	        EPStatement stmt = epService.EPAdministrator.CreateEQL("select * from AddedMapOne");
	        stmt.AddListener(testListener);

            DataDictionary eventOne = new DataDictionary();
	        eventOne.Put("prop1", 1);
	        epService.EPRuntime.SendEvent(eventOne, "AddedMapOne");
	        Assert.AreEqual(eventOne, testListener.AssertOneGetNewAndReset().Underlying);

	        TryInvalid("AddedMapNameSecond");

	        // Second statement using a new alias to the same type, should only one receive
	        configOps.AddEventTypeAlias("AddedMapNameSecond", mapProps);
	        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
	        stmt = epService.EPAdministrator.CreateEQL("select * from AddedMapNameSecond");
            stmt.AddListener(testListenerTwo);

            DataDictionary eventTwo = new DataDictionary();
	        eventTwo.Put("prop1", 1);
	        epService.EPRuntime.SendEvent(eventTwo, "AddedMapNameSecond");
	        Assert.IsFalse(testListener.IsInvoked);
	        Assert.AreEqual(eventTwo, testListenerTwo.AssertOneGetNewAndReset().Underlying);

	        // Add the same alias and type again
	        mapProps.Clear();
	        mapProps.Put("prop1", typeof(int));
	        configOps.AddEventTypeAlias("AddedNameSecond", mapProps);

	        // Add the same alias and a different type
	        try
	        {
	            mapProps.Put("XX", typeof(int));
	            configOps.AddEventTypeAlias("AddedNameSecond", mapProps);
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddMapProperties()
	    {
	        TryInvalid("AddedMapOne");

	        // First statement with new name
	        Properties mapProps = new Properties();
	        mapProps.Put("prop1", typeof(int).FullName);
	        configOps.AddEventTypeAlias("AddedMapOne", mapProps);

	        EPStatement stmt = epService.EPAdministrator.CreateEQL("select * from AddedMapOne");
	        stmt.AddListener(testListener);

            DataDictionary eventOne = new DataDictionary();
	        eventOne.Put("prop1", 1);
	        epService.EPRuntime.SendEvent(eventOne, "AddedMapOne");
	        Assert.AreEqual(eventOne, testListener.AssertOneGetNewAndReset().Underlying);

	        TryInvalid("AddedMapNameSecond");

	        // Second statement using a new alias to the same type, should only one receive
	        configOps.AddEventTypeAlias("AddedMapNameSecond", mapProps);
	        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
	        stmt = epService.EPAdministrator.CreateEQL("select * from AddedMapNameSecond");
            stmt.AddListener(testListenerTwo);

            DataDictionary eventTwo = new DataDictionary();
	        eventTwo.Put("prop1", 1);
	        epService.EPRuntime.SendEvent(eventTwo, "AddedMapNameSecond");
	        Assert.IsFalse(testListener.IsInvoked);
	        Assert.AreEqual(eventTwo, testListenerTwo.AssertOneGetNewAndReset().Underlying);

	        // Add the same alias and type again
	        mapProps.Clear();
	        mapProps.Put("prop1", typeof(int).FullName);
	        configOps.AddEventTypeAlias("AddedNameSecond", mapProps);

	        // Add the same alias and a different type
	        try
	        {
	            mapProps.Put("XX", typeof(int).FullName);
	            configOps.AddEventTypeAlias("AddedNameSecond", mapProps);
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddAliasClassName()
	    {
	        TryInvalid("AddedName");

	        // First statement with new name
	        configOps.AddEventTypeAlias("AddedName", typeof(SupportBean).FullName);
	        EPStatement stmt = epService.EPAdministrator.CreateEQL("select * from AddedName");
	        stmt.AddListener(testListener);

	        SupportBean eventOne = new SupportBean("a", 1);
	        epService.EPRuntime.SendEvent(eventOne);
	        Assert.AreSame(eventOne, testListener.AssertOneGetNewAndReset().Underlying);

	        TryInvalid("AddedNameSecond");

	        // Second statement using a new alias to the same type, should both receive
	        configOps.AddEventTypeAlias("AddedNameSecond", typeof(SupportBean).FullName);
	        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
	        stmt = epService.EPAdministrator.CreateEQL("select * from AddedNameSecond");
            stmt.AddListener(testListenerTwo);

	        SupportBean eventTwo = new SupportBean("b", 2);
	        epService.EPRuntime.SendEvent(eventTwo);
	        Assert.AreSame(eventTwo, testListener.AssertOneGetNewAndReset().Underlying);
	        Assert.AreSame(eventTwo, testListenerTwo.AssertOneGetNewAndReset().Underlying);

	        // Add the same alias and type again
	        configOps.AddEventTypeAlias("AddedNameSecond", typeof(SupportBean).FullName);

	        // Add the same alias and a different type
	        try
	        {
	            configOps.AddEventTypeAlias("AddedNameSecond", typeof(SupportBean_A).FullName);
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testAddAliasClass()
	    {
	        TryInvalid("AddedName");

	        // First statement with new name
	        configOps.AddEventTypeAlias("AddedName", typeof(SupportBean));
	        EPStatement stmt = epService.EPAdministrator.CreateEQL("select * from AddedName");
	        stmt.AddListener(testListener);

	        SupportBean eventOne = new SupportBean("a", 1);
	        epService.EPRuntime.SendEvent(eventOne);
	        Assert.AreSame(eventOne, testListener.AssertOneGetNewAndReset().Underlying);

	        TryInvalid("AddedNameSecond");

	        // Second statement using a new alias to the same type, should both receive
	        configOps.AddEventTypeAlias("AddedNameSecond", typeof(SupportBean));
	        SupportUpdateListener testListenerTwo = new SupportUpdateListener();
	        stmt = epService.EPAdministrator.CreateEQL("select * from AddedNameSecond");
            stmt.AddListener(testListenerTwo);

	        SupportBean eventTwo = new SupportBean("b", 2);
	        epService.EPRuntime.SendEvent(eventTwo);
	        Assert.AreSame(eventTwo, testListener.AssertOneGetNewAndReset().Underlying);
	        Assert.AreSame(eventTwo, testListenerTwo.AssertOneGetNewAndReset().Underlying);

	        // Add the same alias and type again
	        configOps.AddEventTypeAlias("AddedNameSecond", typeof(SupportBean));

	        // Add the same alias and a different type
	        try
	        {
	            configOps.AddEventTypeAlias("AddedNameSecond", typeof(SupportBean_A));
	            Assert.Fail();
	        }
	        catch (ConfigurationException ex)
	        {
	            // expected
	        }
	    }

	    private void TryInvalid(String alias)
	    {
	        try
	        {
	            epService.EPAdministrator.CreateEQL("select * from " + alias);
	            Assert.Fail();
	        }
	        catch (EPStatementException ex)
	        {
	            // expected
	        }
	    }

	    private XmlDocument MakeDOMEvent(String rootElementName)
	    {
	        String XML =
	            "<VAL1>\n" +
	            "  <someelement/>\n" +
	            "</VAL1>";

	        String xml = XML.Replace("VAL1", rootElementName);

	        XmlDocument document = new XmlDocument() ;
	        document.LoadXml( xml ) ;
	        return document;
	    }
	}
} // End of namespace
