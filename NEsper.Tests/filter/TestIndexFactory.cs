///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;

using NUnit.Framework;

using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.filter
{
	[TestFixture]
	public class TestIndexFactory
	{
	    EventType eventType;

	    [SetUp]
	    public void SetUp()
	    {
	        eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	    }

	    [Test]
	    public void testCreateIndex()
	    {
	        // Create a "greater" index
	        FilterParamIndexBase index = IndexFactory.CreateIndex(eventType, "intPrimitive", FilterOperator.GREATER);

	        Assert.IsTrue(index != null);
	        Assert.IsTrue(index is FilterParamIndexCompare);
	        Assert.IsTrue(((FilterParamIndexCompare)index).PropertyName.Equals("intPrimitive"));
	        Assert.IsTrue(index.FilterOperator == FilterOperator.GREATER);

	        // Create an "equals" index
	        index = IndexFactory.CreateIndex(eventType, "string", FilterOperator.EQUAL);

	        Assert.IsTrue(index != null);
	        Assert.IsTrue(index is FilterParamIndexEquals);
	        Assert.IsTrue(((FilterParamIndexEquals)index).PropertyName.Equals("string"));
	        Assert.IsTrue(index.FilterOperator == FilterOperator.EQUAL);

	        // Create an "not equals" index
	        index = IndexFactory.CreateIndex(eventType, "string", FilterOperator.NOT_EQUAL);

	        Assert.IsTrue(index != null);
	        Assert.IsTrue(index is FilterParamIndexNotEquals);
	        Assert.IsTrue(((FilterParamIndexNotEquals)index).PropertyName.Equals("string"));
	        Assert.IsTrue(index.FilterOperator == FilterOperator.NOT_EQUAL);

	        // Create a range index
	        index = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.RANGE_CLOSED);
	        Assert.IsTrue(index is FilterParamIndexRange);
	        index = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.NOT_RANGE_CLOSED);
	        Assert.IsTrue(index is FilterParamIndexNotRange);

	        // Create a in-index
	        index = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.IN_LIST_OF_VALUES);
	        Assert.IsTrue(index is FilterParamIndexIn);
	        index = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.NOT_IN_LIST_OF_VALUES);
	        Assert.IsTrue(index is FilterParamIndexNotIn);

	        // Create a bool-expression-index
	        index = IndexFactory.CreateIndex(eventType, "bool", FilterOperator.BOOLEAN_EXPRESSION);
	        Assert.IsTrue(index is FilterParamIndexBooleanExpr);
	        index = IndexFactory.CreateIndex(eventType, "bool", FilterOperator.BOOLEAN_EXPRESSION);
	        Assert.IsTrue(index is FilterParamIndexBooleanExpr);
	    }
	}

} // End of namespace
