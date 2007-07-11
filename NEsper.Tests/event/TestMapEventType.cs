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
using net.esper.support.bean;
using net.esper.support.events;
using net.esper.support.util;

using org.apache.commons.logging;

namespace net.esper.events
{
	[TestFixture]
	public class TestMapEventType
	{
	    private MapEventType eventType;
	    private EventAdapterService eventAdapterService;

	    [SetUp]
	    public void SetUp()
	    {
	        eventAdapterService = SupportEventAdapterService.GetService();

	        EDictionary<String, Type> testTypesMap = new HashDictionary<String, Type>();
	        testTypesMap.Put("myInt", typeof(int));
	        testTypesMap.Put("myString", typeof(String));
	        testTypesMap.Put("myNullableString", typeof(String));
	        testTypesMap.Put("mySupportBean", typeof(SupportBean));
	        testTypesMap.Put("myComplexBean", typeof(SupportBeanComplexProps));
	        testTypesMap.Put("myNullableSupportBean", typeof(SupportBean));
	        testTypesMap.Put("myNullType", null);
	        eventType = new MapEventType("", testTypesMap, eventAdapterService);
	    }

	    [Test]
	    public void testPropertyNames()
	    {
	        ICollection<string> properties = eventType.PropertyNames;
	        ArrayAssertionUtil.AreEqualAnyOrder(properties, new String[] {"myInt", "myString", "myNullableString", "mySupportBean", "myComplexBean", "myNullableSupportBean", "myNullType"});
	    }

	    [Test]
	    public void testGetPropertyType()
	    {
	        Assert.AreEqual(typeof(int), eventType.GetPropertyType("myInt"));
	        Assert.AreEqual(typeof(String), eventType.GetPropertyType("myString"));
	        Assert.AreEqual(typeof(SupportBean), eventType.GetPropertyType("mySupportBean"));
	        Assert.AreEqual(typeof(SupportBeanComplexProps), eventType.GetPropertyType("myComplexBean"));
	        Assert.AreEqual(typeof(int), eventType.GetPropertyType("mySupportBean.intPrimitive"));
	        Assert.AreEqual(typeof(String), eventType.GetPropertyType("myComplexBean.nested.nestedValue"));
	        Assert.AreEqual(typeof(int), eventType.GetPropertyType("myComplexBean.indexed[1]"));
	        Assert.AreEqual(typeof(String), eventType.GetPropertyType("myComplexBean.Mapped('a')"));
	        Assert.AreEqual(null, eventType.GetPropertyType("myNullType"));

	        Assert.IsNull(eventType.GetPropertyType("dummy"));
	        Assert.IsNull(eventType.GetPropertyType("mySupportBean.dfgdg"));
	        Assert.IsNull(eventType.GetPropertyType("xxx.intPrimitive"));
	        Assert.IsNull(eventType.GetPropertyType("myComplexBean.nested.nestedValueXXX"));
	    }

	    [Test]
	    public void testUnderlyingType()
	    {
	        Assert.AreEqual(typeof(IDataDictionary), eventType.UnderlyingType);
	    }

	    [Test]
	    public void testIsValidProperty()
	    {
	        Assert.IsTrue(eventType.IsProperty("myInt"));
	        Assert.IsTrue(eventType.IsProperty("myString"));
	        Assert.IsTrue(eventType.IsProperty("mySupportBean.intPrimitive"));
	        Assert.IsTrue(eventType.IsProperty("myComplexBean.nested.nestedValue"));
	        Assert.IsTrue(eventType.IsProperty("myComplexBean.indexed[1]"));
	        Assert.IsTrue(eventType.IsProperty("myComplexBean.Mapped('a')"));
	        Assert.IsTrue(eventType.IsProperty("myNullType"));

	        Assert.IsFalse(eventType.IsProperty("dummy"));
	        Assert.IsFalse(eventType.IsProperty("mySupportBean.dfgdg"));
	        Assert.IsFalse(eventType.IsProperty("xxx.intPrimitive"));
	        Assert.IsFalse(eventType.IsProperty("myComplexBean.nested.nestedValueXXX"));
	    }

	    [Test]
	    public void testGetGetter()
	    {
	        SupportBean nestedSupportBean = new SupportBean();
	        nestedSupportBean.SetIntPrimitive(100);
	        SupportBeanComplexProps complexPropBean = SupportBeanComplexProps.MakeDefaultBean();

	        Assert.AreEqual(null, eventType.GetGetter("dummy"));

	        EDictionary<String, Object> valuesMap = new HashDictionary<String, Object>();
	        valuesMap.Put("myInt", 20);
	        valuesMap.Put("myString", "a");
	        valuesMap.Put("mySupportBean", nestedSupportBean);
	        valuesMap.Put("myComplexBean", complexPropBean);
	        valuesMap.Put("myNullableSupportBean", null);
	        valuesMap.Put("myNullableString", null);
	        EventBean _eventBean = new MapEventBean(valuesMap, eventType);

	        EventPropertyGetter getter = eventType.GetGetter("myInt");
	        Assert.AreEqual(20, getter.GetValue(_eventBean));

	        getter = eventType.GetGetter("myString");
	        Assert.AreEqual("a", getter.GetValue(_eventBean));

	        getter = eventType.GetGetter("myNullableString");
	        Assert.IsNull(getter.GetValue(_eventBean));

	        getter = eventType.GetGetter("mySupportBean");
	        Assert.AreEqual(nestedSupportBean, getter.GetValue(_eventBean));

	        getter = eventType.GetGetter("mySupportBean.intPrimitive");
	        Assert.AreEqual(100, getter.GetValue(_eventBean));

	        getter = eventType.GetGetter("myNullableSupportBean.intPrimitive");
	        Assert.IsNull(getter.GetValue(_eventBean));

	        getter = eventType.GetGetter("myComplexBean.nested.nestedValue");
	        Assert.AreEqual("nestedValue", getter.GetValue(_eventBean));

	        try
	        {
	            _eventBean = SupportEventBeanFactory.CreateObject(new Object());
	            getter.GetValue(_eventBean);
	            Assert.IsTrue(false);
	        }
	        catch (PropertyAccessException ex)
	        {
	            // Expected
	            log.Debug(".testGetGetter Expected exception, msg=" + ex.Message);
	        }
	    }

	    [Test]
	    public void testGetSuperTypes()
	    {
	        Assert.IsNull(eventType.SuperTypes);
	    }

	    [Test]
	    public void testEquals()
	    {
	        EDictionary<String, Type> mapTwo = new LinkedDictionary<String, Type>();
	        mapTwo.Put("myInt", typeof(int));
	        mapTwo.Put("mySupportBean", typeof(SupportBean));
	        mapTwo.Put("myNullableSupportBean", typeof(SupportBean));
	        mapTwo.Put("myComplexBean", typeof(SupportBeanComplexProps));
	        Assert.IsFalse((new MapEventType("", mapTwo, eventAdapterService)).Equals(eventType));
	        mapTwo.Put("myString", typeof(String));
	        mapTwo.Put("myNullableString", typeof(String));
	        mapTwo.Put("myNullType", null);

	        // compare, should equal
	        Assert.AreEqual(new MapEventType("", mapTwo, eventAdapterService), eventType);
	        Assert.IsFalse((new MapEventType("google", mapTwo, eventAdapterService)).Equals(eventType));

	        mapTwo.Put("xx", typeof(int));
	        Assert.IsFalse(eventType.Equals(new MapEventType("", mapTwo, eventAdapterService)));
	        mapTwo.Remove("xx");
	        Assert.IsTrue(eventType.Equals(new MapEventType("", mapTwo, eventAdapterService)));

	        mapTwo.Put("myInt", typeof(int?));
	        Assert.IsFalse(eventType.Equals(new MapEventType("", mapTwo, eventAdapterService)));
	        mapTwo.Remove("myInt");
	        Assert.IsFalse(eventType.Equals(new MapEventType("", mapTwo, eventAdapterService)));
	        mapTwo.Put("myInt", typeof(int));
	        Assert.IsTrue(eventType.Equals(new MapEventType("", mapTwo, eventAdapterService)));
	    }

	    [Test]
	    public void testGetFromMap()
	    {
	        SupportBean nestedSupportBean = new SupportBean();
	        nestedSupportBean.SetIntPrimitive(100);
	        SupportBeanComplexProps complexPropBean = SupportBeanComplexProps.MakeDefaultBean();

	        EDictionary<String, Object> valuesMap = new HashDictionary<String, Object>();
	        valuesMap.Put("myInt", 20);
	        valuesMap.Put("myString", "a");
	        valuesMap.Put("mySupportBean", nestedSupportBean);
	        valuesMap.Put("myComplexBean", complexPropBean);
	        valuesMap.Put("myNullableSupportBean", null);
	        valuesMap.Put("myNullableString", null);

	        Assert.AreEqual(20, eventType.GetValue("myInt", valuesMap));
	        Assert.AreEqual(100, eventType.GetValue("mySupportBean.intPrimitive", valuesMap));
	        Assert.AreEqual("nestedValue", eventType.GetValue("myComplexBean.nested.nestedValue", valuesMap));
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
