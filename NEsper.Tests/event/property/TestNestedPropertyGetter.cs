///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;
using System.Reflection;
using NUnit.Framework;

using net.esper.client;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.events.property
{
	[TestFixture]
	public class TestNestedPropertyGetter
	{
	    private NestedPropertyGetter getter;
	    private NestedPropertyGetter getterNull;
	    private EventBean _event;
	    private SupportBeanCombinedProps bean;
	    private BeanEventAdapter beanEventAdapter;

	    [SetUp]
	    public void SetUp()
	    {
            beanEventAdapter = new BeanEventAdapter(PropertyResolutionStyle.CASE_INSENSITIVE);
	        bean = SupportBeanCombinedProps.MakeDefaultBean();
	        _event = SupportEventBeanFactory.CreateObject(bean);

	        IList<EventPropertyGetter> getters = new List<EventPropertyGetter>();
	        getters.Add(MakeGetterOne(0));
	        getters.Add(MakeGetterTwo("0ma"));
	        getter = new NestedPropertyGetter(getters, beanEventAdapter);

	        getters = new List<EventPropertyGetter>();
	        getters.Add(MakeGetterOne(2));
	        getters.Add(MakeGetterTwo("0ma"));
	        getterNull = new NestedPropertyGetter(getters, beanEventAdapter);
	    }

	    [Test]
	    public void testGet()
	    {
	        Assert.AreEqual(bean.GetIndexed(0).GetMapped("0ma"), getter.GetValue(_event));

	        // test null value returned
	        Assert.IsNull(getterNull.GetValue(_event));

	        try
	        {
	            getter.GetValue(SupportEventBeanFactory.CreateObject(""));
	            Assert.Fail();
	        }
	        catch (PropertyAccessException ex)
	        {
	            // expected
	        }
	    }

	    private KeyedPropertyGetter MakeGetterOne(int index)
        {
            Type type = typeof(SupportBeanCombinedProps);
            MethodInfo methodOne = type.GetMethod("GetIndexed", new Type[] { typeof(int) });
            IndexedPropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor("indexed", methodOne);
            return new KeyedPropertyGetter(descriptor, index);
        }

        private KeyedPropertyGetter MakeGetterTwo(String key)
        {
            Type type = typeof(SupportBeanCombinedProps.NestedLevOne);
            MethodInfo methodTwo = type.GetMethod("GetMapped", new Type[] { typeof(string) });
            IndexedPropertyDescriptor descriptor = new IndexedAccessorPropertyDescriptor("mapped", methodTwo);
            return new KeyedPropertyGetter(descriptor, key);
        }
	}
} // End of namespace
