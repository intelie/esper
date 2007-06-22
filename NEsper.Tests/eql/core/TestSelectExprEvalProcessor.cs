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
using net.esper.eql.spec;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.events;

namespace net.esper.eql.core
{
	[TestFixture]
	public class TestSelectExprEvalProcessor
	{
	    private SelectExprEvalProcessor methodOne;
	    private SelectExprEvalProcessor methodTwo;

	    [SetUp]
	    public void SetUp()
	    {
	        IList<SelectExprElementCompiledSpec> selectList = SupportSelectExprFactory.MakeNoAggregateSelectList();
	        EventAdapterService eventAdapterService = SupportEventAdapterService.GetService();

	        methodOne = new SelectExprEvalProcessor(selectList, null, false, new SupportStreamTypeSvc1Stream(), eventAdapterService);

	        InsertIntoDesc insertIntoDesc = new InsertIntoDesc(true, "Hello");
	        insertIntoDesc.Add("a");
	        insertIntoDesc.Add("b");

	        methodTwo = new SelectExprEvalProcessor(selectList, insertIntoDesc, false, new SupportStreamTypeSvc1Stream(), eventAdapterService);
	    }

	    [Test]
	    public void TestGetResultEventType()
	    {
	        EventType type = methodOne.ResultEventType;
	        Assert.IsTrue(CollectionHelper.AreEqual(type.PropertyNames, new String[] {"resultOne", "resultTwo"}));
	        Assert.AreEqual(typeof(double?), type.GetPropertyType("resultOne"));
	        Assert.AreEqual(typeof(int?), type.GetPropertyType("resultTwo"));

	        type = methodTwo.ResultEventType;
	        Assert.IsTrue(CollectionHelper.AreEqual(type.PropertyNames, new String[] {"a", "b"}));
	        Assert.AreEqual(typeof(double?), type.GetPropertyType("a"));
	        Assert.AreEqual(typeof(int?), type.GetPropertyType("b"));
	    }

	    [Test]
	    public void TestProcess()
	    {
	        EventBean[] events = new EventBean[] {MakeEvent(8.8, 3, 4)};

	        EventBean result = methodOne.Process(events, true);
	        Assert.AreEqual(8.8d, result["resultOne"]);
	        Assert.AreEqual(12, result["resultTwo"]);

	        result = methodTwo.Process(events, true);
	        Assert.AreEqual(8.8d, result["a"]);
	        Assert.AreEqual(12, result["b"]);
	        Assert.AreSame(result.EventType, methodTwo.ResultEventType);
	    }

	    private EventBean MakeEvent(double doubleBoxed, int intPrimitive, int intBoxed)
	    {
	        SupportBean bean = new SupportBean();
	        bean.SetDoubleBoxed(doubleBoxed);
	        bean.SetIntPrimitive(intPrimitive);
	        bean.SetIntBoxed(intBoxed);
	        return SupportEventBeanFactory.CreateObject(bean);
	    }
	}
} // End of namespace
