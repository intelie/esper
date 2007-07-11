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
using net.esper.collection;
using net.esper.events;
using net.esper.support.bean;
using net.esper.support.events;

namespace net.esper.filter
{
	[TestFixture]
	public class TestIndexHelper
	{
	    private EventType eventType;
	    private TreeSet<FilterValueSetParam> parameters;
	    private FilterValueSetParam parameterOne;
	    private FilterValueSetParam parameterTwo;
	    private FilterValueSetParam parameterThree;

	    [SetUp]
	    public void SetUp()
	    {
	        eventType = SupportEventTypeFactory.CreateBeanType(typeof(SupportBean));
	        parameters = new TreeSet<FilterValueSetParam>(new FilterValueSetParamComparator());

	        // Create parameter test list
	        parameterOne = new FilterValueSetParamImpl("intPrimitive", FilterOperator.GREATER, 10);
	        parameters.Add(parameterOne);
	        parameterTwo = new FilterValueSetParamImpl("doubleBoxed", FilterOperator.GREATER, 20d);
	        parameters.Add(parameterTwo);
	        parameterThree = new FilterValueSetParamImpl("string", FilterOperator.EQUAL, "sometext");
	        parameters.Add(parameterThree);
	    }

	    [Test]
	    public void testFindIndex()
	    {
	        IList<FilterParamIndexBase> indexes = new List<FilterParamIndexBase>();

	        // Create index list wity index that doesn't match
	        FilterParamIndexBase indexOne = IndexFactory.CreateIndex(eventType, "boolPrimitive", FilterOperator.EQUAL);
	        indexes.Add(indexOne);
	        Assert.IsTrue(IndexHelper.FindIndex(parameters, indexes) == null);

	        // Create index list wity index that doesn't match
	        indexOne = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.GREATER_OR_EQUAL);
	        indexes.Clear();
	        indexes.Add(indexOne);
	        Assert.IsTrue(IndexHelper.FindIndex(parameters, indexes) == null);

	        // Add an index that does match a parameter
	        FilterParamIndexBase indexTwo = IndexFactory.CreateIndex(eventType, "doubleBoxed", FilterOperator.GREATER);
	        indexes.Add(indexTwo);
	        Pair<FilterValueSetParam, FilterParamIndexBase> pair = IndexHelper.FindIndex(parameters, indexes);
	        Assert.IsTrue(pair != null);
	        Assert.AreEqual(parameterTwo, pair.First);
	        Assert.AreEqual(indexTwo, pair.Second);

	        // Add another index that does match a parameter, should return first match however which is doubleBoxed
	        FilterParamIndexBase indexThree = IndexFactory.CreateIndex(eventType, "intPrimitive", FilterOperator.GREATER);
	        indexes.Add(indexThree);
	        pair = IndexHelper.FindIndex(parameters, indexes);
	        Assert.AreEqual(parameterTwo, pair.First);
	        Assert.AreEqual(indexTwo, pair.Second);

	        // Try again removing one index
	        indexes.Remove(indexTwo);
	        pair = IndexHelper.FindIndex(parameters, indexes);
	        Assert.AreEqual(parameterOne, pair.First);
	        Assert.AreEqual(indexThree, pair.Second);
	    }

	    [Test]
	    public void testFindParameter()
	    {
	        FilterParamIndexBase indexOne = IndexFactory.CreateIndex(eventType, "boolPrimitive", FilterOperator.EQUAL);
	        Assert.IsNull(IndexHelper.FindParameter(parameters, indexOne));

	        FilterParamIndexBase indexTwo = IndexFactory.CreateIndex(eventType, "string", FilterOperator.EQUAL);
	        Assert.AreEqual(parameterThree, IndexHelper.FindParameter(parameters, indexTwo));

	        FilterParamIndexBase indexThree = IndexFactory.CreateIndex(eventType, "intPrimitive", FilterOperator.GREATER);
	        Assert.AreEqual(parameterOne, IndexHelper.FindParameter(parameters, indexThree));
	    }
	}
} // End of namespace
