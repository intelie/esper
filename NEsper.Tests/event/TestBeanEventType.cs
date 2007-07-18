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

using net.esper.client;
using net.esper.compat;
using net.esper.support.bean;
using net.esper.support.util;

namespace net.esper.events
{
	[TestFixture]
	public class TestBeanEventType
	{
	    private BeanEventAdapter beanEventAdapter;
	    private BeanEventType eventTypeSimple;
	    private BeanEventType eventTypeComplex;
	    private BeanEventType eventTypeNested;

	    private EventBean eventSimple;
	    private EventBean eventComplex;
	    private EventBean eventNested;

	    private SupportBeanSimple objSimple;
	    private SupportBeanComplexProps objComplex;
	    private SupportBeanCombinedProps objCombined;

	    [SetUp]
	    public void SetUp()
	    {
            beanEventAdapter = new BeanEventAdapter(PropertyResolutionStyle.CASE_INSENSITIVE);

	        eventTypeSimple = new BeanEventType(typeof(SupportBeanSimple), beanEventAdapter, null, "1");
	        eventTypeComplex = new BeanEventType(typeof(SupportBeanComplexProps), beanEventAdapter, null, "2");
	        eventTypeNested = new BeanEventType(typeof(SupportBeanCombinedProps), beanEventAdapter, null, "3");

	        objSimple = new SupportBeanSimple("a", 20);
	        objComplex = SupportBeanComplexProps.MakeDefaultBean();
	        objCombined = SupportBeanCombinedProps.MakeDefaultBean();

	        eventSimple = beanEventAdapter.AdapterForBean(objSimple, null);
	        eventComplex = beanEventAdapter.AdapterForBean(objComplex, null);
	        eventNested = beanEventAdapter.AdapterForBean(objCombined, null);
	    }

	    [Test]
	    public void testPropertyNames()
	    {
	        List<string> properties = new List<string>(eventTypeSimple.PropertyNames);
	        Assert.IsTrue(properties.Count == 2);
	        Assert.IsTrue(properties[0].Equals("MyInt"));
	        Assert.IsTrue(properties[1].Equals("MyString"));

	        properties = new List<string>(eventTypeComplex.PropertyNames);
	        ArrayAssertionUtil.AreEqualAnyOrder(SupportBeanComplexProps.PROPERTIES, properties);

	        properties = new List<string>(eventTypeNested.PropertyNames);
	        ArrayAssertionUtil.AreEqualAnyOrder(SupportBeanCombinedProps.PROPERTIES, properties);
	    }

	    [Test]
	    public void testUnderlyingType()
	    {
	        Assert.AreEqual(typeof(SupportBeanSimple), eventTypeSimple.UnderlyingType);
	    }

	    [Test]
	    public void testGetPropertyType()
	    {
	        Assert.AreEqual(typeof(String), eventTypeSimple.GetPropertyType("myString"));
	        Assert.IsNull(eventTypeSimple.GetPropertyType("dummy"));
	    }

	    [Test]
	    public void testIsValidProperty()
	    {
	        Assert.IsTrue(eventTypeSimple.IsProperty("myString"));
	        Assert.IsFalse(eventTypeSimple.IsProperty("dummy"));
	    }

	    [Test]
	    public void testGetGetter()
	    {
	        Assert.AreEqual(null, eventTypeSimple.GetGetter("dummy"));

	        EventPropertyGetter getter = eventTypeSimple.GetGetter("myInt");
	        Assert.AreEqual(20, getter.GetValue(eventSimple));
	        getter = eventTypeSimple.GetGetter("myString");
	        Assert.AreEqual("a", getter.GetValue(eventSimple));

	        try
	        {
	            // test mismatch between bean and object
	            EventBean _eventBean = beanEventAdapter.AdapterForBean(new Object(), null);
	            getter.GetValue(_eventBean);
	            Assert.Fail();
	        }
	        catch (PropertyAccessException ex)
	        {
	            // Expected
	        }
	    }

	    [Test]
	    public void testProperties()
	    {
	        Type nestedOne = typeof (SupportBeanCombinedProps.NestedLevOne);
	        Type nestedOneArr = typeof (SupportBeanCombinedProps.NestedLevOne[]);
	        Type nestedTwo = typeof (SupportBeanCombinedProps.NestedLevTwo);

	        // test nested/combined/indexed/mapped properties
	        // PropertyName                 isProperty              getType         hasGetter   getterValue
	        IList<PropTestDesc> tests = new List<PropTestDesc>();

	        tests = new List<PropTestDesc>();
	        tests.Add(new PropTestDesc("simpleProperty", true, typeof(String), true, "simple"));
	        tests.Add(new PropTestDesc("dummy", false, null, false, null));
	        tests.Add(new PropTestDesc("indexed", false, null, false, null));
	        tests.Add(new PropTestDesc("indexed[1]", true, typeof(int), true, 2));
	        tests.Add(new PropTestDesc("nested", true, typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNested), true, objComplex.Nested));
	        tests.Add(new PropTestDesc("nested.nestedValue", true, typeof(String), true, objComplex.Nested.NestedValue));
	        tests.Add(new PropTestDesc("nested.nestedNested", true, typeof(SupportBeanComplexProps.SupportBeanSpecialGetterNestedNested), true, objComplex.Nested.NestedNested));
	        tests.Add(new PropTestDesc("nested.nestedNested.nestedNestedValue", true, typeof(String), true, objComplex.Nested.NestedNested.NestedNestedValue));
	        tests.Add(new PropTestDesc("nested.dummy", false, null, false, null));
	        tests.Add(new PropTestDesc("mapped", false, null, false, null));
	        tests.Add(new PropTestDesc("mapped('keyOne')", true, typeof(String), true, "valueOne"));
	        tests.Add(new PropTestDesc("arrayProperty", true, typeof(int[]), true, objComplex.ArrayProperty));
	        tests.Add(new PropTestDesc("arrayProperty[1]", true, typeof(int), true, 20));
	        tests.Add(new PropTestDesc("mapProperty('xOne')", false, null, false, null));
	        tests.Add(new PropTestDesc("google('x')", false, null, false, null));
	        tests.Add(new PropTestDesc("mapped('x')", true, typeof(String), true, null));
	        tests.Add(new PropTestDesc("mapped('x').x", false, null, false, null));
	        tests.Add(new PropTestDesc("mapProperty", true, typeof(Properties), true, objComplex.MapProperty));
	        RunTest(tests, eventTypeComplex, eventComplex);

	        tests = new List<PropTestDesc>();
	        tests.Add(new PropTestDesc("dummy", false, null, false, null));
	        tests.Add(new PropTestDesc("myInt", true, typeof(int), true, objSimple.MyInt));
	        tests.Add(new PropTestDesc("myString", true, typeof(String), true, objSimple.MyString));
	        tests.Add(new PropTestDesc("dummy('a')", false, null, false, null));
	        tests.Add(new PropTestDesc("dummy[1]", false, null, false, null));
	        tests.Add(new PropTestDesc("dummy.nested", false, null, false, null));
	        RunTest(tests, eventTypeSimple, eventSimple);

	        tests = new List<PropTestDesc>();
	        tests.Add(new PropTestDesc("indexed", false, null, false, null));
	        tests.Add(new PropTestDesc("indexed[1]", true, nestedOne, true, objCombined.GetIndexed(1)));
	        tests.Add(new PropTestDesc("indexed.mapped", false, null, false, null));
	        tests.Add(new PropTestDesc("indexed[1].mapped", false, null, false, null));
	        tests.Add(new PropTestDesc("array", true, nestedOneArr, true, objCombined.Array));
	        tests.Add(new PropTestDesc("array.mapped", false, null, false, null));
	        tests.Add(new PropTestDesc("array[0]", true, nestedOne, true, objCombined.Array[0]));
	        tests.Add(new PropTestDesc("array[1].mapped", false, null, false, null));
	        tests.Add(new PropTestDesc("array[1].mapped('x')", true, nestedTwo, true, objCombined.Array[1].GetMapped("x")));
	        tests.Add(new PropTestDesc("array[1].mapped('1mb')", true, nestedTwo, true, objCombined.Array[1].GetMapped("1mb")));
	        tests.Add(new PropTestDesc("indexed[1].mapped('x')", true, nestedTwo, true, objCombined.GetIndexed(1).GetMapped("x")));
	        tests.Add(new PropTestDesc("indexed[1].mapped('x').value", true, typeof(String), true, null));
	        tests.Add(new PropTestDesc("indexed[1].mapped('1mb')", true, nestedTwo, true, objCombined.GetIndexed(1).GetMapped("1mb")));
	        tests.Add(new PropTestDesc("indexed[1].mapped('1mb').value", true, typeof(String), true, objCombined.GetIndexed(1).GetMapped("1mb").Value));
            tests.Add(new PropTestDesc("array[1].mapprop", true, typeof(DataDictionary), true, objCombined.GetIndexed(1).GetMapProp()));
	        tests.Add(new PropTestDesc("array[1].mapprop('a')", false, null, false, null));
	        tests.Add(new PropTestDesc("array[1].mapprop('a').value", false, null, false, null));
	        tests.Add(new PropTestDesc("indexed[1].mapprop", true, typeof(DataDictionary), true, objCombined.GetIndexed(1).GetMapProp()));
	        RunTest(tests, eventTypeNested, eventNested);

	        TryInvalidIsProperty(eventTypeComplex, "x[");
	        TryInvalidIsProperty(eventTypeComplex, "dummy()");
	        TryInvalidIsProperty(eventTypeComplex, "nested.xx['a']");
	        TryInvalidGetPropertyType(eventTypeComplex, "x[");
	        TryInvalidGetPropertyType(eventTypeComplex, "dummy()");
	        TryInvalidGetPropertyType(eventTypeComplex, "nested.xx['a']");
	        TryInvalidGetPropertyType(eventTypeNested, "dummy[(");
	        TryInvalidGetPropertyType(eventTypeNested, "array[1].mapprop[x].value");
	    }

	    [Test]
	    public void testGetDeepSuperTypes()
	    {
	        BeanEventType type = new BeanEventType(typeof(ISupportAImplSuperGImplPlus), beanEventAdapter, null, "1");

	        List<EventType> deepSuperTypes = new List<EventType>();
	        deepSuperTypes.AddRange(type.DeepSuperTypes);

	        Assert.AreEqual(5, deepSuperTypes.Count);
	        ArrayAssertionUtil.AreEqualAnyOrder(
	                deepSuperTypes.ToArray(),
	                new EventType[] {
	                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportAImplSuperG)),
	                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportBaseAB)),
	                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportA)),
	                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportB)),
	                    beanEventAdapter.CreateOrGetBeanType(typeof(ISupportC))
	                });
	    }

	    [Test]
	    public void testGetSuper()
	    {
            LinkedHashSet<Type> classes = new LinkedHashSet<Type>();
	        BeanEventType.GetSuper(typeof(ISupportAImplSuperGImplPlus), classes);

	        Assert.AreEqual(6, classes.Count);
	        ArrayAssertionUtil.AreEqualAnyOrder(
	                classes.ToArray(),
                    new Type[] {
	                    typeof(ISupportAImplSuperG),
                        typeof(ISupportBaseAB),
	                    typeof(ISupportA),
                        typeof(ISupportB),
                        typeof(ISupportC),
	                    //typeof(Serializable),
                        typeof(Object),
	                }
	        );

	        classes.Clear();
	        BeanEventType.GetSuper(typeof(Object), classes);
	        Assert.AreEqual(0, classes.Count);
	    }

	    [Test]
	    public void testGetSuperTypes()
	    {
	        eventTypeSimple = new BeanEventType(typeof(ISupportAImplSuperGImplPlus), beanEventAdapter, null, "1");

	        List<EventType> superTypes = new List<EventType>(eventTypeSimple.SuperTypes);
	        Assert.AreEqual(3, superTypes.Count);
	        Assert.AreEqual(typeof(ISupportAImplSuperG), superTypes[0].UnderlyingType);
	        Assert.AreEqual(typeof(ISupportB), superTypes[1].UnderlyingType);
	        Assert.AreEqual(typeof(ISupportC), superTypes[2].UnderlyingType);

	        eventTypeSimple = new BeanEventType(typeof(Object), beanEventAdapter, null, "2");
	        superTypes = new List<EventType>(eventTypeSimple.SuperTypes);
	        Assert.AreEqual(0, superTypes.Count);

	        BeanEventType type = new BeanEventType(typeof(ISupportD), beanEventAdapter, null, "3");
            Assert.AreEqual(3, type.PropertyNames.Count);
	        ArrayAssertionUtil.AreEqualAnyOrder(
	                type.PropertyNames,
	                new String[] {"D", "BaseD", "BaseDBase"});
	    }

	    private static void TryInvalidGetPropertyType(BeanEventType type, String property)
	    {
	        try
	        {
	            type.GetPropertyType(property);
	            Assert.Fail();
	        }
	        catch (PropertyAccessException ex)
	        {
	            // expected
	        }
	    }

	    private static void TryInvalidIsProperty(BeanEventType type, String property)
	    {
	        try
	        {
	            type.GetPropertyType(property);
	            Assert.Fail();
	        }
	        catch (PropertyAccessException ex)
	        {
	            // expected
	        }
	    }

	    private static void RunTest(IList<PropTestDesc> tests, BeanEventType eventType, EventBean _eventBean)
	    {
	        foreach (PropTestDesc desc in tests)
	        {
	            RunTest(desc, eventType, _eventBean);
	        }
	    }

	    private static void RunTest(PropTestDesc test, BeanEventType eventType, EventBean _eventBean)
	    {
	        String propertyName = test.PropertyName;

	        Assert.AreEqual(test.IsProperty, eventType.IsProperty(propertyName), "isProperty mismatch on '" + propertyName + "',");
            Assert.AreEqual(test.Clazz, eventType.GetPropertyType(propertyName), "getPropertyType mismatch on '" + propertyName + "',");

	        EventPropertyGetter getter = eventType.GetGetter(propertyName);
	        if (getter == null)
	        {
	            Assert.IsFalse(test.HasGetter, "getGetter null on '" + propertyName + "',");
	        }
	        else
	        {
	            Assert.IsTrue(test.HasGetter, "getGetter not null on '" + propertyName + "',");
                if (test.GetterReturnValue == typeof(ArgumentException))
	            {
	                try
	                {
	                    getter.GetValue(_eventBean);
	                    Assert.Fail("getGetter not throwing null pointer on '" + propertyName);
	                }
                    catch (ArgumentException ex)
	                {
	                    // expected
	                }
	            }
	            else
	            {
	                Object value = getter.GetValue(_eventBean);
                    Assert.AreEqual(test.GetterReturnValue, value, "getter value mismatch on '" + propertyName + "',");
	            }
	        }
	    }

	    public class PropTestDesc
	    {
	        private String propertyName;
	        private bool isProperty;
	        private Type clazz;
	        private bool hasGetter;
	        private Object getterReturnValue;

	        public PropTestDesc(String propertyName, bool property, Type clazz, bool hasGetter, Object getterReturnValue)
	        {
	            this.propertyName = propertyName;
	            isProperty = property;
	            this.clazz = clazz;
	            this.hasGetter = hasGetter;
	            this.getterReturnValue = getterReturnValue;
	        }

	        public String PropertyName
	        {
	        	get { return propertyName; }
	        }

	        public bool IsProperty
	        {
	        	get { return isProperty; }
	        }

	        public Type Clazz
	        {
	        	get { return clazz; }
	        }

	        public bool HasGetter
	        {
	        	get { return hasGetter; }
	        }

	        public Object GetterReturnValue
	        {
	        	get { return getterReturnValue; }
	        }
	    }

	}
} // End of namespace
