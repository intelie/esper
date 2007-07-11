///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using antlr.collections;

using NUnit.Framework;

using net.esper.eql.core;
using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.eql;
using net.esper.support.eql.parse;
using net.esper.support.events;
using net.esper.type;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestEQLTreeWalker
	{
	    private static String CLASSNAME = typeof(SupportBean).FullName;
	    private static String EXPRESSION = "select * from " +
	                    CLASSNAME + "(string='a').win:length(10).std:lastevent() as win1," +
	                    CLASSNAME + "(string='b').win:length(10).std:lastevent() as win2 ";

	    [Test]
	    public void testWalkEQLSimpleWhere()
	    {
	        String expression = EXPRESSION + "where win1.f1=win2.f2";

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);

	        Assert.AreEqual(2, walker.StatementSpec.StreamSpecs.Count);

	        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];
	        Assert.AreEqual(2, streamSpec.ViewSpecs.Count);
	        Assert.AreEqual(typeof(SupportBean).FullName, streamSpec.RawFilterSpec.EventTypeAlias);
	        Assert.AreEqual("length", streamSpec.ViewSpecs[0].ObjectName);
	        Assert.AreEqual("lastevent", streamSpec.ViewSpecs[1].ObjectName);
	        Assert.AreEqual("win1", streamSpec.OptionalStreamName);

	        streamSpec = (FilterStreamSpecRaw) walker.StatementSpec.StreamSpecs[1];
	        Assert.AreEqual("win2", streamSpec.OptionalStreamName);

	        // Join expression tree validation
	        Assert.IsTrue(walker.StatementSpec.FilterExprRootNode is ExprEqualsNode);
	        ExprNode equalsNode = (walker.StatementSpec.FilterExprRootNode);
	        Assert.AreEqual(2, equalsNode.ChildNodes.Count);

	        ExprIdentNode identNode = (ExprIdentNode) equalsNode.ChildNodes[0];
	        Assert.AreEqual("win1", identNode.StreamOrPropertyName);
	        Assert.AreEqual("f1", identNode.UnresolvedPropertyName);
	        identNode = (ExprIdentNode) equalsNode.ChildNodes[1];
	        Assert.AreEqual("win2", identNode.StreamOrPropertyName);
	        Assert.AreEqual("f2", identNode.UnresolvedPropertyName);
	    }

	    [Test]
	    public void testWalkEQLWhereWithAnd()
	    {
	        String expression = "select * from " +
	                        CLASSNAME + "(string='a').win:length(10).std:lastevent() as win1," +
	                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2, " +
	                        CLASSNAME + "(string='c').win:length(3).std:lastevent() as win3 " +
	                        "where win1.f1=win2.f2 and win3.f3=f4";

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);

	        // Stream spec validation
	        Assert.AreEqual(3, walker.StatementSpec.StreamSpecs.Count);
	        Assert.AreEqual("win1", walker.StatementSpec.StreamSpecs[0].OptionalStreamName);
	        Assert.AreEqual("win2", walker.StatementSpec.StreamSpecs[1].OptionalStreamName);
	        Assert.AreEqual("win3", walker.StatementSpec.StreamSpecs[2].OptionalStreamName);

	        FilterStreamSpecRaw streamSpec = (FilterStreamSpecRaw) walker.StatementSpec.StreamSpecs[2];
	        Assert.AreEqual(2, streamSpec.ViewSpecs.Count);
	        Assert.AreEqual(typeof(SupportBean).FullName, streamSpec.RawFilterSpec.EventTypeAlias);
	        Assert.AreEqual("length", streamSpec.ViewSpecs[0].ObjectName);
	        Assert.AreEqual("lastevent", streamSpec.ViewSpecs[1].ObjectName);

	        // Join expression tree validation
	        Assert.IsTrue(walker.StatementSpec.FilterExprRootNode is ExprAndNode);
	        Assert.AreEqual(2, walker.StatementSpec.FilterExprRootNode.ChildNodes.Count);
	        ExprNode equalsNode = (walker.StatementSpec.FilterExprRootNode.ChildNodes[0]);
	        Assert.AreEqual(2, equalsNode.ChildNodes.Count);

	        ExprIdentNode identNode = (ExprIdentNode) equalsNode.ChildNodes[0];
	        Assert.AreEqual("win1", identNode.StreamOrPropertyName);
	        Assert.AreEqual("f1", identNode.UnresolvedPropertyName);
	        identNode = (ExprIdentNode) equalsNode.ChildNodes[1];
	        Assert.AreEqual("win2", identNode.StreamOrPropertyName);
	        Assert.AreEqual("f2", identNode.UnresolvedPropertyName);

	        equalsNode = (walker.StatementSpec.FilterExprRootNode.ChildNodes[1]);
	        identNode = (ExprIdentNode) equalsNode.ChildNodes[0];
	        Assert.AreEqual("win3", identNode.StreamOrPropertyName);
	        Assert.AreEqual("f3", identNode.UnresolvedPropertyName);
	        identNode = (ExprIdentNode) equalsNode.ChildNodes[1];
	        Assert.IsNull(identNode.StreamOrPropertyName);
	        Assert.AreEqual("f4", identNode.UnresolvedPropertyName);
	    }

	    [Test]
	    public void testWalkEQLPerRowFunctions()
	    {
	        Assert.AreEqual(9, TryExpression("max(6, 9)"));
	        Assert.AreEqual(6.11, TryExpression("min(6.11, 6.12)"));
	        Assert.AreEqual(6.10, TryExpression("min(6.11, 6.12, 6.1)"));
	        Assert.AreEqual("ab", TryExpression("'a'||'b'"));
	        Assert.AreEqual(null, TryExpression("coalesce(null, null)"));
	        Assert.AreEqual(1, TryExpression("coalesce(null, 1)"));
	        Assert.AreEqual(1l, TryExpression("coalesce(null, 1l)"));
	        Assert.AreEqual("a", TryExpression("coalesce(null, 'a', 'b')"));
	        Assert.AreEqual(13.5d, TryExpression("coalesce(null, null, 3*4.5)"));
	        Assert.AreEqual(true, TryExpression("coalesce(null, true)"));
	        Assert.AreEqual(5, TryExpression("coalesce(5, null, 6)"));
	        Assert.AreEqual(2, TryExpression("(case 1 when 1 then 2 end)"));
	    }

	    [Test]
	    public void testWalkEQLMath()
	    {
	        Assert.AreEqual(32, TryExpression("5*6-3+15/3"));
	        Assert.AreEqual(-5, TryExpression("1-1-1-2-1-1"));
	        Assert.AreEqual(2.8d, TryExpression("1.4 + 1.4"));
	        Assert.AreEqual(1d, TryExpression("55.5/5/11.1"));
	        Assert.AreEqual(0, TryExpression("2/3"));
	        Assert.AreEqual(2/3d, TryExpression("2.0/3"));
	        Assert.AreEqual(10, TryExpression("(1+4)*2"));
	        Assert.AreEqual(12, TryExpression("(3*(6-4))*2"));
	        Assert.AreEqual(8, TryExpression("(1+(4*3)+2)/2+1"));
	        Assert.AreEqual(1, TryExpression("10%3"));
	        Assert.AreEqual(10.1 % 3, TryExpression("10.1%3"));
	    }

	    [Test]
	    public void testWalkEQLRelationalOp()
	    {
	        Assert.AreEqual(true, TryRelationalOp("3>2"));
	        Assert.AreEqual(false, TryRelationalOp("3*5/2 >= 7.5"));
	        Assert.AreEqual(true, TryRelationalOp("3*5/2.0 >= 7.5"));
	        Assert.AreEqual(false, TryRelationalOp("1.1 + 2.2 < 3.2"));
	        Assert.AreEqual(false, TryRelationalOp("3<=2"));
	        Assert.AreEqual(true, TryRelationalOp("4*(3+1)>=16"));

	        Assert.AreEqual(false, TryRelationalOp("(4>2) and (2>3)"));
	        Assert.AreEqual(true, TryRelationalOp("(4>2) or (2>3)"));

	        Assert.AreEqual(false, TryRelationalOp("not 3>2"));
	        Assert.AreEqual(true, TryRelationalOp("not (not 3>2)"));
	    }

	    [Test]
	    public void testWalkEQLInsertInto()
	    {
	        String expression = "insert into MyAlias select * from " +
	                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
	                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);

	        InsertIntoDesc desc = walker.StatementSpec.InsertIntoDesc;
	        Assert.IsTrue(desc.IsStream);
	        Assert.AreEqual("MyAlias", desc.EventTypeAlias);
	        Assert.AreEqual(0, desc.ColumnNames.Count);

	        expression = "insert rstream into MyAlias(a, b, c) select * from " +
	                        CLASSNAME + "().win:length(10).std:lastevent() as win1," +
	                        CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

	        walker = ParseAndWalkEQL(expression);

	        desc = walker.StatementSpec.InsertIntoDesc;
	        Assert.IsFalse(desc.IsStream);
	        Assert.AreEqual("MyAlias", desc.EventTypeAlias);
	        Assert.AreEqual(3, desc.ColumnNames.Count);
	        Assert.AreEqual("a", desc.ColumnNames[0]);
	        Assert.AreEqual("b", desc.ColumnNames[1]);
	        Assert.AreEqual("c", desc.ColumnNames[2]);

	        expression = "insert istream into Test2 select * from " + CLASSNAME + "().win:length(10)";
	        walker = ParseAndWalkEQL(expression);
	        desc = walker.StatementSpec.InsertIntoDesc;
	        Assert.IsTrue(desc.IsStream);
	        Assert.AreEqual("Test2", desc.EventTypeAlias);
	        Assert.AreEqual(0, desc.ColumnNames.Count);
	    }

	    [Test]
	    public void testWalkView()
	    {
	        String text = "select * from " + typeof(SupportBean).FullName + "(string=\"IBM\").win:lenght(10, 1.1, \"a\").stat:uni('price', false)";

	        EQLTreeWalker walker = ParseAndWalkEQL(text);
	        FilterSpecRaw filterSpec = ((FilterStreamSpecRaw) walker.StatementSpec.StreamSpecs[0]).RawFilterSpec;

	        // Check filter spec properties
	        Assert.AreEqual(typeof(SupportBean).FullName, filterSpec.EventTypeAlias);
	        Assert.AreEqual(1, filterSpec.FilterExpressions.Count);

	        // Check views
            IList<ViewSpec> viewSpecs = ((FilterStreamSpecRaw)walker.StatementSpec.StreamSpecs[0]).ViewSpecs;
	        Assert.AreEqual(2, viewSpecs.Count);

	        ViewSpec specOne = viewSpecs[0];
	        Assert.AreEqual("win", specOne.ObjectNamespace);
	        Assert.AreEqual("lenght", specOne.ObjectName);
	        Assert.AreEqual(3, specOne.ObjectParameters.Count);
	        Assert.AreEqual(10, specOne.ObjectParameters[0]);
	        Assert.AreEqual(1.1d, specOne.ObjectParameters[1]);
	        Assert.AreEqual("a", specOne.ObjectParameters[2]);

	        ViewSpec specTwo = viewSpecs[1];
	        Assert.AreEqual("stat", specTwo.ObjectNamespace);
	        Assert.AreEqual("uni", specTwo.ObjectName);
	        Assert.AreEqual(2, specTwo.ObjectParameters.Count);
	        Assert.AreEqual("price", specTwo.ObjectParameters[0]);
	        Assert.AreEqual(false, specTwo.ObjectParameters[1]);
	    }

	    [Test]
	    public void testSelectList()
	    {
	        String text = "select intPrimitive, 2 * intBoxed, 5 as myConst, stream0.string as theString from " + typeof(SupportBean).FullName + "().win:lenght(10) as stream0";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);
	        IList<SelectExprElementRawSpec> selectExpressions = walker.StatementSpec.SelectClauseSpec.SelectList;
	        Assert.AreEqual(4, selectExpressions.Count);
	        Assert.IsTrue(selectExpressions[0].SelectExpression is ExprIdentNode);
	        Assert.IsTrue(selectExpressions[1].SelectExpression is ExprMathNode);
	        Assert.IsTrue(selectExpressions[2].SelectExpression is ExprConstantNode);
	        Assert.AreEqual("myConst", selectExpressions[2].OptionalAsName);
	        Assert.IsTrue(selectExpressions[3].SelectExpression is ExprIdentNode);
	        Assert.AreEqual("theString", selectExpressions[3].OptionalAsName);
	        Assert.IsNull(walker.StatementSpec.InsertIntoDesc);

	        text = "select * from " + typeof(SupportBean).FullName + "().win:lenght(10)";
	        walker = ParseAndWalkEQL(text);
	        Assert.AreEqual(0, walker.StatementSpec.SelectClauseSpec.SelectList.Count);
	    }

	    [Test]
	    public void testArrayViewParams()
	    {
	        // Check a list of integer as a view parameter
	        String text = "select * from " + typeof(SupportBean).FullName + "().win:lenght({10, 11, 12})";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

            IList<ViewSpec> viewSpecs = ((FilterStreamSpecRaw)walker.StatementSpec.StreamSpecs[0]).ViewSpecs;
	        int[] intParams = (int[]) viewSpecs[0].ObjectParameters[0];
	        Assert.AreEqual(10, intParams[0]);
	        Assert.AreEqual(11, intParams[1]);
	        Assert.AreEqual(12, intParams[2]);

	        // Check a list of objects
	        text = "select * from " + typeof(SupportBean).FullName + "().win:lenght({false, 11.2, 's'})";
	        walker = ParseAndWalkEQL(text);
	        viewSpecs = ((FilterStreamSpecRaw)walker.StatementSpec.StreamSpecs[0]).ViewSpecs;
	        Object[] objParams = (Object[]) viewSpecs[0].ObjectParameters[0];
	        Assert.AreEqual(false, objParams[0]);
	        Assert.AreEqual(11.2, objParams[1]);
	        Assert.AreEqual("s", objParams[2]);
	    }

	    [Test]
	    public void testOuterJoin()
	    {
	        TryOuterJoin("left", OuterJoinType.LEFT);
	        TryOuterJoin("right", OuterJoinType.RIGHT);
	        TryOuterJoin("full", OuterJoinType.FULL);
	    }

	    [Test]
	    public void testNoPackageName()
	    {
	        String text = "select intPrimitive from SupportBean_N().win:lenght(10) as win1";
	        ParseAndWalkEQL(text);
	    }

	    [Test]
	    public void testAggregateFunction()
	    {
	        String fromClause = "from " + typeof(SupportBean_N).FullName + "().win:lenght(10) as win1";
	        String text = "select max(distinct intPrimitive) " + fromClause;
	        ParseAndWalkEQL(text);

	        text = "select sum(intPrimitive)," +
	                "sum(distinct doubleBoxed)," +
	                "avg(doubleBoxed)," +
	                "avg(distinct doubleBoxed)," +
	                "count(*)," +
	                "count(intPrimitive)," +
	                "count(distinct intPrimitive)," +
	                "max(distinct intPrimitive)," +
	                "min(distinct intPrimitive)," +
	                "max(intPrimitive)," +
	                "min(intPrimitive), " +
	                "median(intPrimitive), " +
	                "median(distinct intPrimitive)," +
	                "stddev(intPrimitive), " +
	                "stddev(distinct intPrimitive)," +
	                "avedev(intPrimitive)," +
	                "avedev(distinct intPrimitive) " +
	                fromClause;
	        ParseAndWalkEQL(text);

	        // try min-max aggregate versus row functions
	        text = "select max(intPrimitive), min(intPrimitive)," +
	                      "max(intPrimitive,intBoxed), min(intPrimitive,intBoxed)," +
	                      "max(distinct intPrimitive), min(distinct intPrimitive)" +
	                      fromClause;
	        EQLTreeWalker walker = ParseAndWalkEQL(text);
            IList<SelectExprElementRawSpec> select = walker.StatementSpec.SelectClauseSpec.SelectList;
	        Assert.IsTrue(select[0].SelectExpression is ExprMinMaxAggrNode);
	        Assert.IsTrue(select[1].SelectExpression is ExprMinMaxAggrNode);
	        Assert.IsTrue(select[2].SelectExpression is ExprMinMaxRowNode);
	        Assert.IsTrue(select[3].SelectExpression is ExprMinMaxRowNode);
	        Assert.IsTrue(select[4].SelectExpression is ExprMinMaxAggrNode);
	        Assert.IsTrue(select[5].SelectExpression is ExprMinMaxAggrNode);

	        try
	        {
	            ParseAndWalkEQL("select max(distinct intPrimitive, intboxed)");
	            Assert.Fail();
	        }
	        catch (Exception ex)
	        {
	            // expected
	        }
	    }

	    [Test]
	    public void testGroupBy()
	    {
	        String text = "select sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
	            "group by intBoxed, 3 * doubleBoxed, max(2, doublePrimitive)";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

            IList<ExprNode> groupByList = walker.StatementSpec.GroupByExpressions;
	        Assert.AreEqual(3, groupByList.Count);

	        ExprNode node = groupByList[0];
	        Assert.IsTrue(node is ExprIdentNode);

	        node = groupByList[1];
	        Assert.IsTrue(node is ExprMathNode);
	        Assert.IsTrue(node.ChildNodes[0] is ExprConstantNode);
	        Assert.IsTrue(node.ChildNodes[1] is ExprIdentNode);

	        node = groupByList[2];
	        Assert.IsTrue(node is ExprMinMaxRowNode);
	    }

	    [Test]
	    public void testHaving()
	    {
	        String text = "select Sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
	            "group by intBoxed having Sum(intPrimitive) > 5";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

	        ExprNode havingNode = walker.StatementSpec.HavingExprRootNode;

	        Assert.IsTrue(havingNode is ExprRelationalOpNode);
	        Assert.IsTrue(havingNode.ChildNodes[0] is ExprSumNode);
	        Assert.IsTrue(havingNode.ChildNodes[1] is ExprConstantNode);

	        text = "select Sum(intPrimitive) from SupportBean_N().win:lenght(10) as win1 where intBoxed > 5 " +
	            "having intPrimitive < Avg(intPrimitive)";
	        walker = ParseAndWalkEQL(text);

	        havingNode = walker.StatementSpec.HavingExprRootNode;
	        Assert.IsTrue(havingNode is ExprRelationalOpNode);
	    }

	    [Test]
	    public void testDistinct()
	    {
	        String text = "select Sum(distinct intPrimitive) from SupportBean_N().win:lenght(10) as win1";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

	        ExprAggregateNode aggrNode = (ExprAggregateNode) walker.StatementSpec.SelectClauseSpec.SelectList[0].SelectExpression;
	        Assert.IsTrue(aggrNode.IsDistinct);
	    }

	    [Test]
	    public void testComplexProperty()
	    {
	        String text = "select array [ 1 ],s0.map('a'),nested.nested2, a[1].b as x " +
	                " from SupportBean_N().win:lenght(10) as win1 " +
	                " where a[1].b('a').nested.c[0] = 4";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

	        ExprIdentNode identNode = (ExprIdentNode) walker.StatementSpec.SelectClauseSpec.SelectList[0].SelectExpression;
	        Assert.AreEqual("array[1]", identNode.UnresolvedPropertyName);
	        Assert.IsNull(identNode.StreamOrPropertyName);

	        identNode = (ExprIdentNode) walker.StatementSpec.SelectClauseSpec.SelectList[1].SelectExpression;
	        Assert.AreEqual("map('a')", identNode.UnresolvedPropertyName);
	        Assert.AreEqual("s0", identNode.StreamOrPropertyName);

	        identNode = (ExprIdentNode) walker.StatementSpec.SelectClauseSpec.SelectList[2].SelectExpression;
	        Assert.AreEqual("nested2", identNode.UnresolvedPropertyName);
	        Assert.AreEqual("nested", identNode.StreamOrPropertyName);

	        identNode = (ExprIdentNode) walker.StatementSpec.SelectClauseSpec.SelectList[3].SelectExpression;
	        Assert.AreEqual("a[1].b", identNode.UnresolvedPropertyName);
	        Assert.AreEqual(null, identNode.StreamOrPropertyName);

	        identNode = (ExprIdentNode) walker.StatementSpec.FilterExprRootNode.ChildNodes[0];
	        Assert.AreEqual("a[1].b('a').nested.c[0]", identNode.UnresolvedPropertyName);
	        Assert.AreEqual(null, identNode.StreamOrPropertyName);
	    }

	    [Test]
	    public void testBitWise()
	    {
	        String text = "select intPrimitive & intBoxed from " + typeof(SupportBean).FullName + "().win:lenght(10) as stream0";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);
            IList<SelectExprElementRawSpec> selectExpressions = walker.StatementSpec.SelectClauseSpec.SelectList;
	        Assert.AreEqual(1, selectExpressions.Count);
	        Assert.IsTrue(selectExpressions[0].SelectExpression is ExprBitWiseNode);

	        Assert.AreEqual(0, TryBitWise("1&2"));
	        Assert.AreEqual(3, TryBitWise("1|2"));
	        Assert.AreEqual(8, TryBitWise("10^2"));
	    }

	    [Test]
	    public void testPatternsOnly()
	    {
	        String patternOne = "a=" + typeof(SupportBean).FullName + " -> b=" + typeof(SupportBean).FullName;
	        String patternTwo = "c=" + typeof(SupportBean).FullName + " or " + typeof(SupportBean).FullName;

	        // Test simple case, one pattern and no "as streamName"
	        EQLTreeWalker walker = ParseAndWalkEQL("select * from pattern [" + patternOne + "]");
	        Assert.AreEqual(1, walker.StatementSpec.StreamSpecs.Count);
	        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];

	        Assert.AreEqual(typeof(EvalFollowedByNode), patternStreamSpec.EvalNode.GetType());
	        Assert.IsNull(patternStreamSpec.OptionalStreamName);

	        // Test case with "as s0"
	        walker = ParseAndWalkEQL("select * from pattern [" + patternOne + "] as s0");
	        patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];
	        Assert.AreEqual("s0", patternStreamSpec.OptionalStreamName);

	        // Test case with multiple patterns
	        walker = ParseAndWalkEQL("select * from pattern [" + patternOne + "] as s0, pattern [" + patternTwo + "] as s1");
	        Assert.AreEqual(2, walker.StatementSpec.StreamSpecs.Count);
	        patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];
	        Assert.AreEqual("s0", patternStreamSpec.OptionalStreamName);
	        Assert.AreEqual(typeof(EvalFollowedByNode), patternStreamSpec.EvalNode.GetType());

	        patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[1];
	        Assert.AreEqual("s1", patternStreamSpec.OptionalStreamName);
	        Assert.AreEqual(typeof(EvalOrNode), patternStreamSpec.EvalNode.GetType());

	        // Test 3 patterns
	        walker = ParseAndWalkEQL("select * from pattern [" + patternOne + "], pattern [" + patternTwo + "] as s1," +
	                "pattern[x=" + typeof(SupportBean_S2).FullName + "] as s2");
	        Assert.AreEqual(3, walker.StatementSpec.StreamSpecs.Count);
	        patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[2];
	        Assert.AreEqual("s2", patternStreamSpec.OptionalStreamName);

	        // Test patterns with views
	        walker = ParseAndWalkEQL("select * from pattern [" + patternOne + "].win:time(1), pattern [" + patternTwo + "].win:length(1).std:lastevent() as s1");
	        Assert.AreEqual(2, walker.StatementSpec.StreamSpecs.Count);
	        patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];
	        Assert.AreEqual(1, patternStreamSpec.ViewSpecs.Count);
	        Assert.AreEqual("time", patternStreamSpec.ViewSpecs[0].ObjectName);
	        patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[1];
	        Assert.AreEqual(2, patternStreamSpec.ViewSpecs.Count);
	        Assert.AreEqual("length", patternStreamSpec.ViewSpecs[0].ObjectName);
	        Assert.AreEqual("lastevent", patternStreamSpec.ViewSpecs[1].ObjectName);
	    }

	    [Test]
	    public void testIfThenElseCase()
	    {
	        String text;
	        text = "select case when intPrimitive > shortPrimitive then Count(intPrimitive) end from " +    typeof(SupportBean).FullName + "().win:lenght(10) as win";
	        ParseAndWalkEQL(text);
	        text = "select case when intPrimitive > shortPrimitive then Count(intPrimitive) end as p1 from " +    typeof(SupportBean).FullName + "().win:lenght(10) as win";
	        ParseAndWalkEQL(text);
	        text = "select case when intPrimitive > shortPrimitive then Count(intPrimitive) else shortPrimitive end from " +    typeof(SupportBean).FullName + "().win:lenght(10) as win";
	        ParseAndWalkEQL(text);
	        text = "select case when intPrimitive > shortPrimitive then Count(intPrimitive) when longPrimitive > intPrimitive then Count(longPrimitive) else shortPrimitive end from " +    typeof(SupportBean).FullName + "().win:lenght(10) as win";
	        ParseAndWalkEQL(text);
	        text = "select case intPrimitive  when 1 then Count(intPrimitive) end from " +    typeof(SupportBean).FullName + "().win:lenght(10) as win";
	        ParseAndWalkEQL(text);
	        text = "select case intPrimitive when longPrimitive then (intPrimitive + longPrimitive) end" +
	        " from " + typeof(SupportBean).FullName + ".win:length(3)";
	        ParseAndWalkEQL(text);
	    }

	    private void TryOuterJoin(String outerType, OuterJoinType typeExpected)
	    {
	        String text = "select intPrimitive from " +
	                        typeof(SupportBean_A).FullName + "().win:lenght(10) as win1 " +
	                        outerType + " outer join " +
	                        typeof(SupportBean_A).FullName + "().win:lenght(10) as win2 " +
	                        "on win1.f1 = win2.f2[1]";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

            IList<OuterJoinDesc> descList = walker.StatementSpec.OuterJoinDescList;
	        Assert.AreEqual(1, descList.Count);
	        OuterJoinDesc desc = descList[0];
	        Assert.AreEqual(typeExpected, desc.OuterJoinType);
	        Assert.AreEqual("f1", desc.LeftNode.UnresolvedPropertyName);
	        Assert.AreEqual("win1", desc.LeftNode.StreamOrPropertyName);
	        Assert.AreEqual("f2[1]", desc.RightNode.UnresolvedPropertyName);
	        Assert.AreEqual("win2", desc.RightNode.StreamOrPropertyName);

	        text = "select intPrimitive from " +
	                        typeof(SupportBean_A).FullName + "().win:lenght(10) as win1 " +
	                        outerType + " outer join " +
	                        typeof(SupportBean_A).FullName + "().win:lenght(10) as win2 " +
	                        "on win1.f1 = win2.f2 " +
	                        outerType + " outer join " +
	                        typeof(SupportBean_A).FullName + "().win:lenght(10) as win3 " +
	                        "on win1.f1 = win3.f3";
	        walker = ParseAndWalkEQL(text);

	        descList = walker.StatementSpec.OuterJoinDescList;
	        Assert.AreEqual(2, descList.Count);

	        desc = descList[0];
	        Assert.AreEqual(typeExpected, desc.OuterJoinType);
	        Assert.AreEqual("f1", desc.LeftNode.UnresolvedPropertyName);
	        Assert.AreEqual("win1", desc.LeftNode.StreamOrPropertyName);
	        Assert.AreEqual("f2", desc.RightNode.UnresolvedPropertyName);
	        Assert.AreEqual("win2", desc.RightNode.StreamOrPropertyName);

	        desc = descList[1];
	        Assert.AreEqual(typeExpected, desc.OuterJoinType);
	        Assert.AreEqual("f1", desc.LeftNode.UnresolvedPropertyName);
	        Assert.AreEqual("win1", desc.LeftNode.StreamOrPropertyName);
	        Assert.AreEqual("f3", desc.RightNode.UnresolvedPropertyName);
	        Assert.AreEqual("win3", desc.RightNode.StreamOrPropertyName);
	    }

	    [Test]
	    public void testWalkPattern()
	    {
	        String text = "every g=" + typeof(SupportBean).FullName + "(string=\"IBM\", intPrimitive != 1) where timer:within(20)";

	        EQLTreeWalker walker = ParseAndWalkPattern(text);

	        Assert.AreEqual(1, walker.StatementSpec.StreamSpecs.Count);
	        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];

	        EvalNode rootNode = patternStreamSpec.EvalNode;
	        rootNode.DumpDebug(".testWalk ");

	        EvalEveryNode everyNode = (EvalEveryNode) rootNode;

	        Assert.AreEqual(1, everyNode.ChildNodes.Count);
	        Assert.IsTrue(everyNode.ChildNodes[0] is EvalGuardNode);
	        EvalGuardNode guardNode = (EvalGuardNode) everyNode.ChildNodes[0];

	        Assert.AreEqual(1, guardNode.ChildNodes.Count);
	        Assert.IsTrue(guardNode.ChildNodes[0] is EvalFilterNode);
	        EvalFilterNode filterNode = (EvalFilterNode) guardNode.ChildNodes[0];

	        Assert.AreEqual("g", filterNode.EventAsName);
	        Assert.AreEqual(0, filterNode.ChildNodes.Count);
	        Assert.AreEqual(2, filterNode.RawFilterSpec.FilterExpressions.Count);
	        ExprEqualsNode equalsNode = (ExprEqualsNode) filterNode.RawFilterSpec.FilterExpressions[1];
	        Assert.AreEqual(2, equalsNode.ChildNodes.Count);
	    }

	    [Test]
	    public void testWalkPropertyPatternCombination()
	    {
	        String _event = typeof(SupportBeanComplexProps).FullName;
	        String property = TryWalkGetPropertyPattern(_event + "(mapped ( 'key' )  = 'value')");
	        Assert.AreEqual("mapped('key')", property);

	        property = TryWalkGetPropertyPattern(_event + "(indexed [ 1 ]  = 1)");
	        Assert.AreEqual("indexed[1]", property);
	        property = TryWalkGetPropertyPattern(_event + "(nested . nestedValue  = 'value')");
	        Assert.AreEqual("nestedValue", property);
	    }

	    [Test]
	    public void testWalkPatternUseResult()
	    {
	        String _event = typeof(SupportBean_N).FullName;
	        String text = "na=" + _event + "() -> every nb=" + _event + "(doublePrimitive in [0:na.doublePrimitive])";
	        ParseAndWalkPattern(text);
	    }

	    [Test]
	    public void testWalkIStreamRStreamSelect()
	    {
	        String text = "select rstream 'a' from " + typeof(SupportBean_N).FullName;
	        EQLTreeWalker walker = ParseAndWalkEQL(text);
	        Assert.AreEqual(SelectClauseStreamSelectorEnum.RSTREAM_ONLY, walker.StatementSpec.SelectStreamSelectorEnum);

	        text = "select istream 'a' from " + typeof(SupportBean_N).FullName;
	        walker = ParseAndWalkEQL(text);
	        Assert.AreEqual(SelectClauseStreamSelectorEnum.ISTREAM_ONLY, walker.StatementSpec.SelectStreamSelectorEnum);

	        text = "select 'a' from " + typeof(SupportBean_N).FullName;
	        walker = ParseAndWalkEQL(text);
	        Assert.AreEqual(SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH, walker.StatementSpec.SelectStreamSelectorEnum);
	    }

	    [Test]
	    public void testWalkPatternNoPackage()
	    {
	        SupportEventAdapterService.GetService().AddBeanType("SupportBean_N", typeof(SupportBean_N));
	        String text = "na=SupportBean_N()";
	        ParseAndWalkPattern(text);
	    }

	    [Test]
	    public void testWalkPluginAggregationFunction()
	    {
	        EngineImportService engineImportService = new EngineImportServiceImpl();
	        engineImportService.AddAggregation("concat", typeof(SupportPluginAggregationMethodOne).FullName);

	        String text = "select * from " + typeof(SupportBean).FullName + " group by concat(1)";
	        EQLTreeWalker walker = ParseAndWalkEQL(text, engineImportService);
	        ExprPlugInAggFunctionNode node = (ExprPlugInAggFunctionNode) walker.StatementSpec.GroupByExpressions[0];
	        Assert.AreEqual("concat", node.AggregationFunctionName);
	        Assert.IsFalse(node.IsDistinct);

	        text = "select * from " + typeof(SupportBean).FullName + " group by concat(distinct 1)";
	        walker = ParseAndWalkEQL(text, engineImportService);
	        node = (ExprPlugInAggFunctionNode) walker.StatementSpec.GroupByExpressions[0];
	        Assert.AreEqual("concat", node.AggregationFunctionName);
	        Assert.IsTrue(node.IsDistinct);
	    }

	    [Test]
	    public void testWalkPatternTypesValid()
	    {
	        String text = typeof(SupportBean).FullName;
	        EQLTreeWalker walker = ParseAndWalkPattern(text);
	        Assert.AreEqual(1, walker.StatementSpec.StreamSpecs.Count);
	    }

	    [Test]
	    public void testWalkPatternIntervals()
	    {
	        Object[][] intervals = new object[][]
	            {
	                new object[] {"1E2 milliseconds", 0.1d},
	                new object[] {"11 millisecond", 11/1000d},
	                new object[] {"1.1 msec", 1.1/1000d},
	                new object[] {"5 seconds", 5d},
	                new object[] {"0.1 second", 0.1d},
	                new object[] {"135L sec", 135d},
	                new object[] {"1.4 minutes", 1.4*60d},
	                new object[] {"11 minute", 11*60d},
	                new object[] {"123.2 min", 123.2*60d},
	                new object[] {".2 hour", .2*60*60d},
	                new object[] {"11.2 hours", 11.2*60*60d},
	                new object[] {"2 day", 2*24*60*60d},
	                new object[] {"11.2 days", 11.2*24*60*60d},
	                new object[]
	                    {
	                        "1 days 6 hours 2 minutes 4 seconds 3 milliseconds",
	                        1*24*60*60 + 6*60*60 + 2*60 + 4 + 3/1000d
	                    },
	                new object[]
	                    {
	                        "0.2 day 3.3 hour 1E3 minute 0.33 second 10000 millisecond",
	                        0.2d*24*60*60 + 3.3d*60*60 + 1E3*60 + 0.33 + 10000/1000
	                    },
	                new object[]
	                    {
	                        "0.2 day 3.3 hour 1E3 min 0.33 sec 10000 msec",
	                        0.2d*24*60*60 + 3.3d*60*60 + 1E3*60 + 0.33 + 10000/1000
	                    },
	                new object[] {"1.01 hour 2 sec", 1.01d*60*60 + 2},
	                new object[] {"0.02 day 5 msec", 0.02d*24*60*60 + 5/1000d},
	                new object[] {"66 min 4 sec", 66*60 + 4d},
	            };

	        for (int i = 0; i < intervals.Length; i++)
	        {
	            String interval = (String) intervals[i][0];
	            double result = TryInterval(interval);
	            double expected = (double) intervals[i][1];
	            double delta = result - expected;
	            Assert.IsTrue(Math.Abs(delta) < 0.0000001,
	                          "Interval '" + interval + "' expected=" + expected + " actual=" + result);
	        }
	    }

	    [Test]
	    public void testWalkInAndBetween()
	    {
	        Assert.IsFalse((Boolean) TryRelationalOp("1 in (2,3)"));
	        Assert.IsTrue((Boolean) TryRelationalOp("1 in (2,3,1)"));
	        Assert.IsTrue((Boolean) TryRelationalOp("1 not in (2,3)"));

	        Assert.IsTrue((Boolean) TryRelationalOp("1 between 0 and 2"));
	        Assert.IsFalse((Boolean) TryRelationalOp("-1 between 0 and 2"));
	        Assert.IsFalse((Boolean) TryRelationalOp("1 not between 0 and 2"));
	        Assert.IsTrue((Boolean) TryRelationalOp("-1 not between 0 and 2"));
	    }

	    [Test]
	    public void testWalkLikeRegex()
	    {
	        Assert.IsTrue((Boolean) TryRelationalOp("'abc' like 'a__'"));
	        Assert.IsFalse((Boolean) TryRelationalOp("'abcd' like 'a__'"));

	        Assert.IsFalse((Boolean) TryRelationalOp("'abcde' not like 'a%'"));
	        Assert.IsTrue((Boolean) TryRelationalOp("'bcde' not like 'a%'"));

	        Assert.IsTrue((Boolean) TryRelationalOp("'a_' like 'a!_' escape '!'"));
	        Assert.IsFalse((Boolean) TryRelationalOp("'ab' like 'a!_' escape '!'"));

	        Assert.IsFalse((Boolean) TryRelationalOp("'a' not like 'a'"));
	        Assert.IsTrue((Boolean) TryRelationalOp("'a' not like 'ab'"));
	    }

	    [Test]
	    public void testWalkStaticFunc()
	    {
	        String text = "select MyClass.SomeFunc(1) from SupportBean_N";
	        EQLTreeWalker walker = ParseAndWalkEQL(text);

	        SelectExprElementRawSpec spec = walker.StatementSpec.SelectClauseSpec.SelectList[0];
	        ExprStaticMethodNode staticMethod = (ExprStaticMethodNode) spec.SelectExpression;
	        Assert.AreEqual("MyClass", staticMethod.ClassName);
	        Assert.AreEqual("SomeFunc", staticMethod.MethodName);
	    }

	    [Test]
	    public void testWalkDBJoinStatement()
	    {
	        String className = typeof(SupportBean).FullName;
	        String sql = "select a from b where $x.id=c.d";
	        String expression = "select * from " + className + ", sql:mydb ['" + sql + "']";

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);
	        StatementSpecRaw statementSpec = walker.StatementSpec;
	        Assert.AreEqual(2, statementSpec.StreamSpecs.Count);
	        DBStatementStreamSpec dbSpec = (DBStatementStreamSpec) statementSpec.StreamSpecs[1];
	        Assert.AreEqual("mydb", dbSpec.DatabaseName);
	        Assert.AreEqual(sql, dbSpec.SqlWithSubsParams);
	    }

	    [Test]
	    public void testRangeBetweenAndIn()
	    {
	        String className = typeof(SupportBean).FullName;
	        String expression = "select * from " + className + "(intPrimitive in [1:2], intBoxed in (1,2), doubleBoxed between 2 and 3)";
	        ParseAndWalkEQL(expression);

	        expression = "select * from " + className + "(intPrimitive not in [1:2], intBoxed not in (1,2), doubleBoxed not between 2 and 3)";
	        ParseAndWalkEQL(expression);
	    }

	    [Test]
	    public void testSubselect()
	    {
	        String expression = "select (select a from B(id=1) where cox=mox) from C";
	        EQLTreeWalker walker = ParseAndWalkEQL(expression);
	        SelectExprElementRawSpec element = walker.StatementSpec.SelectClauseSpec.SelectList[0];
	        ExprSubselectNode exprNode = (ExprSubselectNode) element.SelectExpression;

	        // check select expressions
	        StatementSpecRaw spec = exprNode.StatementSpecRaw;
	        Assert.AreEqual(1, spec.SelectClauseSpec.SelectList.Count);

	        // check filter
	        Assert.AreEqual(1, spec.StreamSpecs.Count);
	        FilterStreamSpecRaw filter = (FilterStreamSpecRaw) spec.StreamSpecs[0];
	        Assert.AreEqual("B", filter.RawFilterSpec.EventTypeAlias);
	        Assert.AreEqual(1, filter.RawFilterSpec.FilterExpressions.Count);

	        // check where clause
	        Assert.IsTrue(spec.FilterExprRootNode is ExprEqualsNode);
	    }

	    [Test]
	    public void testWalkPatternObject()
	    {
	        String expression = "select * from pattern [" + typeof(SupportBean).FullName + " -> timer:interval(100)]";
	        ParseAndWalkEQL(expression);

	        expression = "select * from pattern [" + typeof(SupportBean).FullName + " where timer:within(100)]";
	        ParseAndWalkEQL(expression);

	        expression = "select * from pattern [" + typeof(SupportBean).FullName + " -> timer:at(2,3,4,4,4)]";
	        ParseAndWalkEQL(expression);
	    }

	    private double TryInterval(String interval)
	    {
	        String text = "select * from " + typeof(SupportBean).FullName + ".win:time(" + interval + ")";

	        EQLTreeWalker walker = ParseAndWalkEQL(text);
	        ViewSpec viewSpec = ((FilterStreamSpecRaw) walker.StatementSpec.StreamSpecs[0]).ViewSpecs[0];
	        Assert.AreEqual("win", viewSpec.ObjectNamespace);
	        Assert.AreEqual("time", viewSpec.ObjectName);
	        Assert.AreEqual(1, viewSpec.ObjectParameters.Count);
	        TimePeriodParameter timePeriodParameter = (TimePeriodParameter) viewSpec.ObjectParameters[0];
	        return timePeriodParameter.NumSeconds;
	    }

	    private String TryWalkGetPropertyPattern(String stmt)
	    {
	        EQLTreeWalker walker = ParseAndWalkPattern(stmt);

	        Assert.AreEqual(1, walker.StatementSpec.StreamSpecs.Count);
	        PatternStreamSpecRaw patternStreamSpec = (PatternStreamSpecRaw) walker.StatementSpec.StreamSpecs[0];

	        EvalFilterNode filterNode = (EvalFilterNode) patternStreamSpec.EvalNode;
	        Assert.AreEqual(1, filterNode.RawFilterSpec.FilterExpressions.Count);
	        ExprNode node = filterNode.RawFilterSpec.FilterExpressions[0];
	        ExprIdentNode identNode = (ExprIdentNode) node.ChildNodes[0];
	        return identNode.UnresolvedPropertyName;
	    }

	    private static EQLTreeWalker ParseAndWalkPattern(String expression)
	    {
	        log.Debug(".parseAndWalk Trying text=" + expression);
	        AST ast = SupportParserHelper.ParsePattern(expression);
	        log.Debug(".parseAndWalk success, tree walking...");
	        SupportParserHelper.DisplayAST(ast);

	        EQLTreeWalker walker = SupportEQLTreeWalkerFactory.MakeWalker();
	        walker.startPatternExpressionRule(ast);
	        return walker;
	    }

	    private static EQLTreeWalker ParseAndWalkEQL(String expression)
	    {
	        return ParseAndWalkEQL(expression, new EngineImportServiceImpl());
	    }

	    private static EQLTreeWalker ParseAndWalkEQL(String expression, EngineImportService engineImportService)
	    {
	        log.Debug(".parseAndWalk Trying text=" + expression);
	        AST ast = SupportParserHelper.ParseEQL(expression);
	        log.Debug(".parseAndWalk success, tree walking...");
	        SupportParserHelper.DisplayAST(ast);

	        EventAdapterService eventAdapterService = SupportEventAdapterService.GetService();
	        eventAdapterService.AddBeanType("SupportBean_N", typeof(SupportBean_N));

	        EQLTreeWalker walker = SupportEQLTreeWalkerFactory.MakeWalker(engineImportService);
            walker.startEQLExpressionRule(ast);
	        return walker;
	    }

	    private Object TryBitWise(String equation)
	    {
	        String expression = EXPRESSION + "where (" + equation + ")=win2.f2";

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);
	        ExprNode exprNode = walker.StatementSpec.FilterExprRootNode.ChildNodes[0];
	        ExprBitWiseNode bitWiseNode = (ExprBitWiseNode) (exprNode);
	        bitWiseNode.GetValidatedSubtree(null, null, null);
	        return bitWiseNode.Evaluate(null, false);
	    }

	    private static Object TryExpression(String equation)
	    {
	        String expression = EXPRESSION + "where " + equation + "=win2.f2";

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);
	        ExprNode exprNode = (walker.StatementSpec.FilterExprRootNode.ChildNodes[0]);
	        exprNode = exprNode.GetValidatedSubtree(null, null, null);
	        return exprNode.Evaluate(null, false);
	    }

	    private static Object TryRelationalOp(String subExpr)
	    {
	        String expression = EXPRESSION + "where " + subExpr;

	        EQLTreeWalker walker = ParseAndWalkEQL(expression);
	        ExprNode filterExprNode = walker.StatementSpec.FilterExprRootNode;
	        filterExprNode.GetValidatedSubtree(null, null, null);
	        return filterExprNode.Evaluate(null, false);
	    }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
} // End of namespace
