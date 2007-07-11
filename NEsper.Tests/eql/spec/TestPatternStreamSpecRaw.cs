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

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.eql.parse;
using net.esper.filter;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.eql.parse;
using net.esper.support.events;

namespace net.esper.eql.spec
{
	[TestFixture]
	public class TestPatternStreamSpecRaw
	{
	    [Test]
	    public void testPatternEquals()
	    {
	        String text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + "(intPrimitive=5) -> " +
	                "t=" + typeof(SupportBean).FullName + "(intPrimitive=s.intBoxed)" +
	                "]";
	        TryPatternEquals(text);

	        text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + "(5=intPrimitive) -> " +
	                "t=" + typeof(SupportBean).FullName + "(s.intBoxed=intPrimitive)" +
	                "]";
	        TryPatternEquals(text);
	    }

	    [Test]
	    public void testInvalid()
	    {
	        String text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + " -> " +
	                "t=" + typeof(SupportBean).FullName + "(intPrimitive=s.doubleBoxed)" +
	                "]";
	        TryInvalid(text);

	        text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + " -> " +
	                "t=" + typeof(SupportBean).FullName + "(intPrimitive in (s.doubleBoxed))" +
	                "]";
	        TryInvalid(text);
	    }

	    private void TryInvalid(String text)
	    {
	        try
	        {
	            PatternStreamSpecRaw raw = MakeSpec(text);
	            Compile(raw);
	            Assert.Fail();
	        }
	        catch (ExprValidationException ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testPatternExpressions()
	    {
	        String text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + "(intPrimitive in (s.intBoxed + 1, 0), intBoxed+1=intPrimitive-1)" +
	                "]";

	        PatternStreamSpecRaw raw = MakeSpec(text);
	        PatternStreamSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(1, spec.TaggedEventTypes.Count);
	        Assert.AreEqual(typeof(SupportBean), spec.TaggedEventTypes.Fetch("s").UnderlyingType);

	        List<EvalFilterNode> filters = EvalNode.RecusiveFilterChildNodes(spec.EvalNode);
	        Assert.AreEqual(1, filters.Count);

	        // node 0
	        EvalFilterNode filterNode = filters[0];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(1, filterNode.FilterSpec.Parameters.Count);
	        FilterSpecParamExprNode exprParam = (FilterSpecParamExprNode) filterNode.FilterSpec.Parameters[0];
	    }

	    [Test]
	    public void testPatternInSetOfVal()
	    {
	        String text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + " -> " +
	                       typeof(SupportBean).FullName + "(intPrimitive in (s.intBoxed, 0))" +
	                "]";

	        PatternStreamSpecRaw raw = MakeSpec(text);
	        PatternStreamSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(1, spec.TaggedEventTypes.Count);
	        Assert.AreEqual(typeof(SupportBean), spec.TaggedEventTypes.Fetch("s").UnderlyingType);

	        List<EvalFilterNode> filters = EvalNode.RecusiveFilterChildNodes(spec.EvalNode);
	        Assert.AreEqual(2, filters.Count);

	        // node 0
	        EvalFilterNode filterNode = filters[0];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(0, filterNode.FilterSpec.Parameters.Count);

	        // node 1
	        filterNode = filters[1];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(1, filterNode.FilterSpec.Parameters.Count);

	        FilterSpecParamIn inlist = (FilterSpecParamIn) filterNode.FilterSpec.Parameters[0];
	        Assert.AreEqual(FilterOperator.IN_LIST_OF_VALUES, inlist.FilterOperator);
	        Assert.AreEqual(2, inlist.ListOfValues.Count);

	        // in-value 1
	        InSetOfValuesEventProp prop = (InSetOfValuesEventProp) inlist.ListOfValues[0];
	        Assert.AreEqual("s", prop.ResultEventAsName);
	        Assert.AreEqual("intBoxed", prop.ResultEventProperty);

	        // in-value 1
	        InSetOfValuesConstant constant = (InSetOfValuesConstant) inlist.ListOfValues[1];
	        Assert.AreEqual(0, constant.GetConstant());
	    }

	    [Test]
	    public void testRange()
	    {
	        String text = "select * from pattern [" +
	                "s=" + typeof(SupportBean).FullName + " -> " +
	                       typeof(SupportBean).FullName + "(intPrimitive between s.intBoxed and 100)" +
	                "]";

	        PatternStreamSpecRaw raw = MakeSpec(text);
	        PatternStreamSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(1, spec.TaggedEventTypes.Count);
	        Assert.AreEqual(typeof(SupportBean), spec.TaggedEventTypes.Fetch("s").UnderlyingType);

	        List<EvalFilterNode> filters = EvalNode.RecusiveFilterChildNodes(spec.EvalNode);
	        Assert.AreEqual(2, filters.Count);

	        // node 0
	        EvalFilterNode filterNode = filters[0];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(0, filterNode.FilterSpec.Parameters.Count);

	        // node 1
	        filterNode = filters[1];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(1, filterNode.FilterSpec.Parameters.Count);

	        FilterSpecParamRange range = (FilterSpecParamRange) filterNode.FilterSpec.Parameters[0];
	        Assert.AreEqual(FilterOperator.RANGE_CLOSED, range.FilterOperator);

	        // min-value
	        RangeValueEventProp prop = (RangeValueEventProp) range.Min;
	        Assert.AreEqual("s", prop.ResultEventAsName);
	        Assert.AreEqual("intBoxed", prop.ResultEventProperty);

	        // max-value
	        RangeValueDouble constant = (RangeValueDouble) range.Max;
	        Assert.AreEqual(100d, constant.DoubleValue);
	    }

	    private void TryPatternEquals(String text)
	    {
	        PatternStreamSpecRaw raw = MakeSpec(text);
	        PatternStreamSpecCompiled spec = Compile(raw);
	        Assert.AreEqual(2, spec.TaggedEventTypes.Count);
	        Assert.AreEqual(typeof(SupportBean), spec.TaggedEventTypes.Fetch("s").UnderlyingType);
	        Assert.AreEqual(typeof(SupportBean), spec.TaggedEventTypes.Fetch("t").UnderlyingType);

	        List<EvalFilterNode> filters = EvalNode.RecusiveFilterChildNodes(spec.EvalNode);
	        Assert.AreEqual(2, filters.Count);

	        // node 0
	        EvalFilterNode filterNode = filters[0];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(1, filterNode.FilterSpec.Parameters.Count);

	        FilterSpecParamConstant constant = (FilterSpecParamConstant) filterNode.FilterSpec.Parameters[0];
	        Assert.AreEqual(FilterOperator.EQUAL, constant.FilterOperator);
	        Assert.AreEqual("intPrimitive", constant.PropertyName);
	        Assert.AreEqual(5, constant.FilterConstant);

	        // node 1
	        filterNode = filters[1];
	        Assert.AreEqual(typeof(SupportBean), filterNode.FilterSpec.EventType.UnderlyingType);
	        Assert.AreEqual(1, filterNode.FilterSpec.Parameters.Count);

	        FilterSpecParamEventProp eventprop = (FilterSpecParamEventProp) filterNode.FilterSpec.Parameters[0];
	        Assert.AreEqual(FilterOperator.EQUAL, constant.FilterOperator);
	        Assert.AreEqual("intPrimitive", constant.PropertyName);
	        Assert.AreEqual("s", eventprop.ResultEventAsName);
	        Assert.AreEqual("intBoxed", eventprop.ResultEventProperty);
	    }

	    private PatternStreamSpecCompiled Compile(PatternStreamSpecRaw raw)
	    {
	        PatternStreamSpecCompiled compiled = (PatternStreamSpecCompiled) raw.Compile(SupportEventAdapterService.GetService(), new MethodResolutionServiceImpl(new EngineImportServiceImpl()));
	        return compiled;
	    }

	    private static PatternStreamSpecRaw MakeSpec(String expression)
	    {
	        AST ast = SupportParserHelper.ParseEQL(expression);
	        SupportParserHelper.DisplayAST(ast);

	        EQLTreeWalker walker = SupportEQLTreeWalkerFactory.MakeWalker();
            walker.startEQLExpressionRule(ast);

	        PatternStreamSpecRaw spec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];
	        return spec;
	    }
	}
} // End of namespace
