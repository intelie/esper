// ---------------------------------------------------------------------------------- /
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
// ---------------------------------------------------------------------------------- /

using System;
using System.Collections.Generic;

using NUnit.Framework;

using antlr.collections;

using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.eql.parse;
using net.esper.filter;
using net.esper.support.bean;
using net.esper.support.eql.parse;
using net.esper.support.events;

namespace net.esper.eql.spec
{
	[TestFixture]
	public class TestFilterStreamSpecRaw
	{
	    [Test]
	    public void TestNoExpr()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName);
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
	        Assert.AreEqual(0, spec.Parameters.Count);
	    }

	    [Test]
	    public void TestMultipleExpr()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName +
	                "(intPrimitive-1>2 and intBoxed-5>3)");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(typeof(SupportBean), spec.EventType.UnderlyingType);
	        Assert.AreEqual(1, spec.Parameters.Count);
	        // expecting unoptimized expressions to condense to a single bool expression, more efficient this way

	        FilterSpecParamExprNode exprNode = (FilterSpecParamExprNode) spec.Parameters[0];
	        Assert.AreEqual(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION, exprNode.PropertyName);
	        Assert.AreEqual(FilterOperator.BOOLEAN_EXPRESSION, exprNode.FilterOperator);
	        Assert.IsTrue(exprNode.ExprNode is ExprAndNode);
	    }

	    [Test]
	    public void TestInvalid()
	    {
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(intPrimitive=5L)");
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(5d = byteBoxed)");
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(5d > longBoxed)");
	        TryInvalid("select * from " + typeof(SupportBean).FullName + "(longBoxed in (5d, 1.1d))");
	    }

	    private void TryInvalid(String text)
	    {
	        try
	        {
	            FilterStreamSpecRaw raw = MakeSpec(text);
	            Compile(raw);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void TestEquals()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName + "(intPrimitive=5)");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(1, spec.Parameters.Count);
	        Assert.AreEqual("intPrimitive", spec.Parameters[0].PropertyName);
	        Assert.AreEqual(FilterOperator.EQUAL, spec.Parameters[0].FilterOperator);
	        Assert.AreEqual(5, GetConstant(spec.Parameters[0]));
	    }

	    [Test]
	    public void TestEqualsAndLess()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName + "(string='a' and intPrimitive<9)");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(2, spec.Parameters.Count);
	        EDictionary<String, FilterSpecParam> paramList = MapParameters(spec.Parameters);

	        Assert.AreEqual(FilterOperator.EQUAL, paramList.Fetch("string").FilterOperator);
	        Assert.AreEqual("a", GetConstant(paramList.Fetch("string")));

	        Assert.AreEqual(FilterOperator.LESS, paramList.Fetch("intPrimitive").FilterOperator);
	        Assert.AreEqual(9, GetConstant(paramList.Fetch("intPrimitive")));
	    }

	    private EDictionary<String, FilterSpecParam> MapParameters(List<FilterSpecParam> paramList)
	    {
	        EDictionary<String, FilterSpecParam> map = new HashDictionary<String, FilterSpecParam>();
	        foreach (FilterSpecParam param in paramList)
	        {
	            map.Put(param.PropertyName, param);
	        }
	        return map;
	    }

	    [Test]
	    public void TestCommaAndCompar()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName +
	                "(doubleBoxed>1.11, doublePrimitive>=9.11 and intPrimitive<=9, string || 'a' = 'sa')");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(4, spec.Parameters.Count);
	        EDictionary<String, FilterSpecParam> paramList = MapParameters(spec.Parameters);

	        Assert.AreEqual(FilterOperator.GREATER, paramList.Fetch("doubleBoxed").FilterOperator);
	        Assert.AreEqual(1.11, GetConstant(paramList.Fetch("doubleBoxed")));

	        Assert.AreEqual(FilterOperator.GREATER_OR_EQUAL, paramList.Fetch("doublePrimitive").FilterOperator);
	        Assert.AreEqual(9.11, GetConstant(paramList.Fetch("doublePrimitive")));

	        Assert.AreEqual(FilterOperator.LESS_OR_EQUAL, paramList.Fetch("intPrimitive").FilterOperator);
	        Assert.AreEqual(9, GetConstant(paramList.Fetch("intPrimitive")));

	        Assert.AreEqual(FilterOperator.BOOLEAN_EXPRESSION, paramList.Fetch(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION).FilterOperator);
	        Assert.IsTrue(paramList.Fetch(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION) is FilterSpecParamExprNode);
	    }

	    [Test]
	    public void TestNestedAnd()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName +
	                "((doubleBoxed=1 and doublePrimitive=2) and (intPrimitive=3 and (string like '%_a' and string = 'a')))");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(5, spec.Parameters.Count);
	        EDictionary<String, FilterSpecParam> paramList = MapParameters(spec.Parameters);

	        Assert.AreEqual(FilterOperator.EQUAL, paramList.Fetch("doubleBoxed").FilterOperator);
	        Assert.AreEqual(1.0, GetConstant(paramList.Fetch("doubleBoxed")));

	        Assert.AreEqual(FilterOperator.EQUAL, paramList.Fetch("doublePrimitive").FilterOperator);
	        Assert.AreEqual(2.0, GetConstant(paramList.Fetch("doublePrimitive")));

	        Assert.AreEqual(FilterOperator.EQUAL, paramList.Fetch("intPrimitive").FilterOperator);
	        Assert.AreEqual(3, GetConstant(paramList.Fetch("intPrimitive")));

	        Assert.AreEqual(FilterOperator.EQUAL, paramList.Fetch("string").FilterOperator);
	        Assert.AreEqual("a", GetConstant(paramList.Fetch("string")));

	        Assert.AreEqual(FilterOperator.BOOLEAN_EXPRESSION, paramList.Fetch(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION).FilterOperator);
	        Assert.IsTrue(paramList.Fetch(FilterSpecCompiler.PROPERTY_NAME_BOOLEAN_EXPRESSION) is FilterSpecParamExprNode);
	    }

	    [Test]
	    public void TestIn()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName + "(doubleBoxed in (1, 2, 3))");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(1, spec.Parameters.Count);

	        Assert.AreEqual("doubleBoxed", spec.Parameters[0].PropertyName);
	        Assert.AreEqual(FilterOperator.IN_LIST_OF_VALUES, spec.Parameters[0].FilterOperator);
	        FilterSpecParamIn inParam = (FilterSpecParamIn) spec.Parameters[0];
	        Assert.AreEqual(3, inParam.ListOfValues.Count);
	        Assert.AreEqual(1.0, GetConstant(inParam.ListOfValues[0]));
	        Assert.AreEqual(2.0, GetConstant(inParam.ListOfValues[1]));
	        Assert.AreEqual(3.0, GetConstant(inParam.ListOfValues[2]));
	    }

	    [Test]
	    public void TestNotIn()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName + "(string not in (\"a\"))");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(1, spec.Parameters.Count);

	        Assert.AreEqual("string", spec.Parameters[0].PropertyName);
	        Assert.AreEqual(FilterOperator.NOT_IN_LIST_OF_VALUES, spec.Parameters[0].FilterOperator);
	        FilterSpecParamIn inParam = (FilterSpecParamIn) spec.Parameters[0];
	        Assert.AreEqual(1, inParam.ListOfValues.Count);
	        Assert.AreEqual("a", GetConstant(inParam.ListOfValues[0]));
	    }

	    [Test]
	    public void TestRanges()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName +
	                "(intBoxed in [1:5] and doubleBoxed in (2:6) and floatBoxed in (3:7] and byteBoxed in [0:1))");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(4, spec.Parameters.Count);
	        EDictionary<String, FilterSpecParam> paramList = MapParameters(spec.Parameters);

	        Assert.AreEqual(FilterOperator.RANGE_CLOSED, paramList.Fetch("intBoxed").FilterOperator);
	        FilterSpecParamRange rangeParam = (FilterSpecParamRange) paramList.Fetch("intBoxed");
	        Assert.AreEqual(1.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(5.0, GetConstant(rangeParam.Max));

	        Assert.AreEqual(FilterOperator.RANGE_OPEN, paramList.Fetch("doubleBoxed").FilterOperator);
	        rangeParam = (FilterSpecParamRange) paramList.Fetch("doubleBoxed");
	        Assert.AreEqual(2.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(6.0, GetConstant(rangeParam.Max));

	        Assert.AreEqual(FilterOperator.RANGE_HALF_CLOSED, paramList.Fetch("floatBoxed").FilterOperator);
	        rangeParam = (FilterSpecParamRange) paramList.Fetch("floatBoxed");
	        Assert.AreEqual(3.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(7.0, GetConstant(rangeParam.Max));

	        Assert.AreEqual(FilterOperator.RANGE_HALF_OPEN, paramList.Fetch("byteBoxed").FilterOperator);
	        rangeParam = (FilterSpecParamRange) paramList.Fetch("byteBoxed");
	        Assert.AreEqual(0.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(1.0, GetConstant(rangeParam.Max));
	    }

	    [Test]
	    public void TestRangesNot()
	    {
	        FilterStreamSpecRaw raw = MakeSpec("select * from " + typeof(SupportBean).FullName +
	                "(intBoxed not in [1:5] and doubleBoxed not in (2:6) and floatBoxed not in (3:7] and byteBoxed not in [0:1))");
	        FilterSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(4, spec.Parameters.Count);
	        EDictionary<String, FilterSpecParam> paramList = MapParameters(spec.Parameters);

	        Assert.AreEqual(FilterOperator.NOT_RANGE_CLOSED, paramList.Fetch("intBoxed").FilterOperator);
	        FilterSpecParamRange rangeParam = (FilterSpecParamRange) paramList.Fetch("intBoxed");
	        Assert.AreEqual(1.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(5.0, GetConstant(rangeParam.Max));

	        Assert.AreEqual(FilterOperator.NOT_RANGE_OPEN, paramList.Fetch("doubleBoxed").FilterOperator);
	        rangeParam = (FilterSpecParamRange) paramList.Fetch("doubleBoxed");
	        Assert.AreEqual(2.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(6.0, GetConstant(rangeParam.Max));

	        Assert.AreEqual(FilterOperator.NOT_RANGE_HALF_CLOSED, paramList.Fetch("floatBoxed").FilterOperator);
	        rangeParam = (FilterSpecParamRange) paramList.Fetch("floatBoxed");
	        Assert.AreEqual(3.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(7.0, GetConstant(rangeParam.Max));

	        Assert.AreEqual(FilterOperator.NOT_RANGE_HALF_OPEN, paramList.Fetch("byteBoxed").FilterOperator);
	        rangeParam = (FilterSpecParamRange) paramList.Fetch("byteBoxed");
	        Assert.AreEqual(0.0, GetConstant(rangeParam.Min));
	        Assert.AreEqual(1.0, GetConstant(rangeParam.Max));
	    }

	    private double GetConstant(FilterSpecParamRangeValue param)
	    {
	        return ((RangeValueDouble) param).DoubleValue;
	    }

	    private Object GetConstant(FilterSpecParamInValue param)
	    {
	        InSetOfValuesConstant constant = (InSetOfValuesConstant) param;
	        return constant.GetConstant();
	    }

	    private Object GetConstant(FilterSpecParam param)
	    {
	        FilterSpecParamConstant constant = (FilterSpecParamConstant) param;
	        return constant.FilterConstant;
	    }

	    private FilterSpecCompiled Compile(FilterStreamSpecRaw raw)
	    {
	        FilterStreamSpecCompiled compiled = (FilterStreamSpecCompiled) raw.Compile(SupportEventAdapterService.GetService(), new MethodResolutionServiceImpl(new EngineImportServiceImpl()));
	        return compiled.FilterSpec;
	    }

	    private static FilterStreamSpecRaw MakeSpec(String expression)
	    {
	        AST ast = SupportParserHelper.ParseEQL(expression);
	        SupportParserHelper.DisplayAST(ast);

	        EQLTreeWalker walker = SupportEQLTreeWalkerFactory.MakeWalker();
            walker.startEQLExpressionRule(ast);

	        FilterStreamSpecRaw spec = (FilterStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];
	        return spec;
	    }
	}
} // End of namespace
