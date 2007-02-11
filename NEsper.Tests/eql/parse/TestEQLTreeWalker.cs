using System;
using System.Collections.Generic;

using antlr.collections;

using net.esper.eql.expression;
using net.esper.eql.spec;
using net.esper.events;
using net.esper.filter;
using net.esper.pattern;
using net.esper.support.bean;
using net.esper.support.eql.parse;
using net.esper.support.events;
using net.esper.type;
using net.esper.view;

using NUnit.Core;
using NUnit.Framework;

using org.apache.commons.logging;

namespace net.esper.eql.parse
{
	[TestFixture]
	public class TestEQLTreeWalker
	{
		private static String CLASSNAME = typeof( SupportBean ).FullName;
		private static String EXPRESSION = 
            "select * from " +
            CLASSNAME + "(StringValue='a').win:length(10).std:lastevent() as win1," +
            CLASSNAME + "(StringValue='b').win:length(10).std:lastevent() as win2 ";

		[Test]
		public virtual void testWalkEQLSimpleWhere()
		{
			String expression = EXPRESSION + "where win1.f1=win2.f2";

			EQLTreeWalker walker = parseAndWalkEQL( expression );

			Assert.AreEqual( 2, walker.StatementSpec.StreamSpecs.Count );

			FilterStreamSpec streamSpec = (FilterStreamSpec) walker.StatementSpec.StreamSpecs[0];
			Assert.AreEqual( 2, streamSpec.ViewSpecs.Count );
			Assert.AreEqual( typeof( SupportBean ), streamSpec.FilterSpec.EventType.UnderlyingType );
			Assert.AreEqual( "length", streamSpec.ViewSpecs[0].ObjectName );
			Assert.AreEqual( "lastevent", streamSpec.ViewSpecs[1].ObjectName );
			Assert.AreEqual( "win1", streamSpec.OptionalStreamName );

			streamSpec = (FilterStreamSpec) walker.StatementSpec.StreamSpecs[1];
			Assert.AreEqual( "win2", streamSpec.OptionalStreamName );

			// Join expression tree validation
			Assert.IsTrue( walker.StatementSpec.FilterRootNode is ExprEqualsNode );
			ExprNode EqualsNode = ( walker.StatementSpec.FilterRootNode );
			Assert.AreEqual( 2, EqualsNode.ChildNodes.Count );

			ExprIdentNode identNode = (ExprIdentNode) EqualsNode.ChildNodes[0];
			Assert.AreEqual( "win1", identNode.StreamOrPropertyName );
			Assert.AreEqual( "f1", identNode.UnresolvedPropertyName );
			identNode = (ExprIdentNode) EqualsNode.ChildNodes[1];
			Assert.AreEqual( "win2", identNode.StreamOrPropertyName );
			Assert.AreEqual( "f2", identNode.UnresolvedPropertyName );
		}

		[Test]
		public virtual void testWalkEQLWhereWithAnd()
		{
			String expression =
                "select * from " +
                CLASSNAME + "(StringValue='a').win:length(10).std:lastevent() as win1," + 
                CLASSNAME + "(StringValue='b').win:length(9).std:lastevent() as win2, " +
                CLASSNAME + "(StringValue='c').win:length(3).std:lastevent() as win3 " +
                "where win1.f1=win2.f2 and win3.f3=f4";

			EQLTreeWalker walker = parseAndWalkEQL( expression );

			// Stream spec validation
			Assert.AreEqual( 3, walker.StatementSpec.StreamSpecs.Count );
			Assert.AreEqual( "win1", walker.StatementSpec.StreamSpecs[0].OptionalStreamName );
			Assert.AreEqual( "win2", walker.StatementSpec.StreamSpecs[1].OptionalStreamName );
			Assert.AreEqual( "win3", walker.StatementSpec.StreamSpecs[2].OptionalStreamName );

			FilterStreamSpec streamSpec = (FilterStreamSpec) walker.StatementSpec.StreamSpecs[2];
			Assert.AreEqual( 2, streamSpec.ViewSpecs.Count );
			Assert.AreEqual( typeof( SupportBean ), streamSpec.FilterSpec.EventType.UnderlyingType );
			Assert.AreEqual( "length", streamSpec.ViewSpecs[0].ObjectName );
			Assert.AreEqual( "lastevent", streamSpec.ViewSpecs[1].ObjectName );

			// Join expression tree validation
			Assert.IsTrue( walker.StatementSpec.FilterRootNode is ExprAndNode );
			Assert.AreEqual( 2, walker.StatementSpec.FilterRootNode.ChildNodes.Count );
			ExprNode EqualsNode = ( walker.StatementSpec.FilterRootNode.ChildNodes[0] );
			Assert.AreEqual( 2, EqualsNode.ChildNodes.Count );

			ExprIdentNode identNode = (ExprIdentNode) EqualsNode.ChildNodes[0];
			Assert.AreEqual( "win1", identNode.StreamOrPropertyName );
			Assert.AreEqual( "f1", identNode.UnresolvedPropertyName );
			identNode = (ExprIdentNode) EqualsNode.ChildNodes[1];
			Assert.AreEqual( "win2", identNode.StreamOrPropertyName );
			Assert.AreEqual( "f2", identNode.UnresolvedPropertyName );

			EqualsNode = ( walker.StatementSpec.FilterRootNode.ChildNodes[1] );
			identNode = (ExprIdentNode) EqualsNode.ChildNodes[0];
			Assert.AreEqual( "win3", identNode.StreamOrPropertyName );
			Assert.AreEqual( "f3", identNode.UnresolvedPropertyName );
			identNode = (ExprIdentNode) EqualsNode.ChildNodes[1];
			Assert.IsNull( identNode.StreamOrPropertyName );
			Assert.AreEqual( "f4", identNode.UnresolvedPropertyName );
		}

		[Test]
		public virtual void testWalkEQLPerRowFunctions()
		{
			Assert.AreEqual( 9, tryExpression( "max(6, 9)" ) );
			Assert.AreEqual( 6.11, tryExpression( "min(6.11, 6.12)" ) );
			Assert.AreEqual( 6.10, tryExpression( "min(6.11, 6.12, 6.1)" ) );
			Assert.AreEqual( "ab", tryExpression( "'a'||'b'" ) );
			Assert.AreEqual( null, tryExpression( "coalesce(null, null)" ) );
			Assert.AreEqual( 1, tryExpression( "coalesce(null, 1)" ) );
			Assert.AreEqual( 1L, tryExpression( "coalesce(null, 1l)" ) );
			Assert.AreEqual( "a", tryExpression( "coalesce(null, 'a', 'b')" ) );
			Assert.AreEqual( 13.5d, tryExpression( "coalesce(null, null, 3*4.5)" ) );
			Assert.AreEqual( true, tryExpression( "coalesce(null, true)" ) );
			Assert.AreEqual( 5, tryExpression( "coalesce(5, null, 6)" ) );
			Assert.AreEqual( 2, tryExpression( "(case 1 when 1 then 2 end)" ) );
		}

		[Test]
		public virtual void testWalkEQLMath()
		{
			Assert.AreEqual( 32, tryExpression( "5*6-3+15/3" ) );
			Assert.AreEqual( -5, tryExpression( "1-1-1-2-1-1" ) );
			Assert.AreEqual( 2.8d, tryExpression( "1.4 + 1.4" ) );
			Assert.AreEqual( 1d, tryExpression( "55.5/5/11.1" ) );
			Assert.AreEqual( 0, tryExpression( "2/3" ) );
			Assert.AreEqual( 2 / 3d, tryExpression( "2.0/3" ) );
			Assert.AreEqual( 10, tryExpression( "(1+4)*2" ) );
			Assert.AreEqual( 12, tryExpression( "(3*(6-4))*2" ) );
			Assert.AreEqual( 8, tryExpression( "(1+(4*3)+2)/2+1" ) );
			Assert.AreEqual( 1, tryExpression( "10%3" ) );
			Assert.AreEqual( 10.1 % 3, tryExpression( "10.1%3" ) );
		}

		[Test]
		public virtual void testWalkEQLRelationalOp()
		{
			Assert.AreEqual( true, tryRelationalOp( "3>2" ) );
			Assert.AreEqual( false, tryRelationalOp( "3*5/2 >= 7.5" ) );
			Assert.AreEqual( true, tryRelationalOp( "3*5/2.0 >= 7.5" ) );
			Assert.AreEqual( false, tryRelationalOp( "1.1 + 2.2 < 3.2" ) );
			Assert.AreEqual( false, tryRelationalOp( "3<=2" ) );
			Assert.AreEqual( true, tryRelationalOp( "4*(3+1)>=16" ) );

			Assert.AreEqual( false, tryRelationalOp( "(4>2) and (2>3)" ) );
			Assert.AreEqual( true, tryRelationalOp( "(4>2) or (2>3)" ) );

			Assert.AreEqual( false, tryRelationalOp( "not 3>2" ) );
			Assert.AreEqual( true, tryRelationalOp( "not (not 3>2)" ) );
		}

		[Test]
		public virtual void testWalkEQLInsertInto()
		{
			String expression =
                "insert into MyAlias select * from " +
                CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

			EQLTreeWalker walker = parseAndWalkEQL( expression );

			InsertIntoDesc desc = walker.StatementSpec.InsertIntoDesc;
			Assert.IsTrue( desc.IsStream );
			Assert.AreEqual( "MyAlias", desc.EventTypeAlias );
			Assert.AreEqual( 0, desc.ColumnNames.Count );

			expression =
                "insert rstream into MyAlias(a, b, c) select * from " +
                CLASSNAME + "().win:length(10).std:lastevent() as win1," +
                CLASSNAME + "(string='b').win:length(9).std:lastevent() as win2";

			walker = parseAndWalkEQL( expression );

			desc = walker.StatementSpec.InsertIntoDesc;
			Assert.IsFalse( desc.IsStream );
			Assert.AreEqual( "MyAlias", desc.EventTypeAlias );
			Assert.AreEqual( 3, desc.ColumnNames.Count );
			Assert.AreEqual( "a", desc.ColumnNames[0] );
			Assert.AreEqual( "b", desc.ColumnNames[1] );
			Assert.AreEqual( "c", desc.ColumnNames[2] );

			expression =
                "insert istream into Test2 select * from " +
                CLASSNAME + "().win:length(10)";
			walker = parseAndWalkEQL( expression );
			desc = walker.StatementSpec.InsertIntoDesc;
			Assert.IsTrue( desc.IsStream );
			Assert.AreEqual( "Test2", desc.EventTypeAlias );
			Assert.AreEqual( 0, desc.ColumnNames.Count );
		}

		[Test]
		public virtual void testWalkView()
		{
			String text =
                "select * from " + typeof( SupportBean ).FullName +
                "(StringValue=\"IBM\").win:length(10, 1.1, \"a\").stat:uni('price', false)";

			EQLTreeWalker walker = parseAndWalkEQL( text );
            FilterSpec filterSpec = ((FilterStreamSpec)walker.StatementSpec.StreamSpecs[0]).FilterSpec;

			// Check filter spec properties
			Assert.AreEqual( typeof( SupportBean ), filterSpec.EventType.UnderlyingType );
			Assert.AreEqual( 1, filterSpec.Parameters.Count );

			// Check views
            IList<ViewSpec> viewSpecs = ((FilterStreamSpec)walker.StatementSpec.StreamSpecs[0]).ViewSpecs;
			Assert.AreEqual( 2, viewSpecs.Count );

			ViewSpec specOne = viewSpecs[0];
			Assert.AreEqual( "win", specOne.ObjectNamespace );
			Assert.AreEqual( "length", specOne.ObjectName );
			Assert.AreEqual( 3, specOne.ObjectParameters.Count );
			Assert.AreEqual( 10, specOne.ObjectParameters[0] );
			Assert.AreEqual( 1.1d, specOne.ObjectParameters[1] );
			Assert.AreEqual( "a", specOne.ObjectParameters[2] );

			ViewSpec specTwo = viewSpecs[1];
			Assert.AreEqual( "stat", specTwo.ObjectNamespace );
			Assert.AreEqual( "uni", specTwo.ObjectName );
			Assert.AreEqual( 2, specTwo.ObjectParameters.Count );
			Assert.AreEqual( "price", specTwo.ObjectParameters[0] );
			Assert.AreEqual( false, specTwo.ObjectParameters[1] );
		}

		[Test]
		public virtual void testSelectList()
		{
			String text = "select intPrimitive, 2 * intBoxed, 5 as myConst, stream0.string as theString from " + typeof( SupportBean ).FullName + "().win:length(10) as stream0";
			EQLTreeWalker walker = parseAndWalkEQL( text );
			IList<SelectExprElementUnnamedSpec> selectExpressions = walker.StatementSpec.SelectListExpressions;
			Assert.AreEqual( 4, selectExpressions.Count );
			Assert.IsTrue( selectExpressions[0].SelectExpression is ExprIdentNode );
			Assert.IsTrue( selectExpressions[1].SelectExpression is ExprMathNode );
			Assert.IsTrue( selectExpressions[2].SelectExpression is ExprConstantNode );
			Assert.AreEqual( "myConst", selectExpressions[2].OptionalAsName );
			Assert.IsTrue( selectExpressions[3].SelectExpression is ExprIdentNode );
			Assert.AreEqual( "theString", selectExpressions[3].OptionalAsName );
			Assert.IsNull( walker.StatementSpec.InsertIntoDesc );

			text = "select * from " + typeof( SupportBean ).FullName + "().win:length(10)";
			walker = parseAndWalkEQL( text );
			Assert.AreEqual( 0, walker.StatementSpec.SelectListExpressions.Count );
		}

		[Test]
		public virtual void testArrayViewParams()
		{
			// Check a list of integer as a view parameter
			String text = "select * from " + typeof( SupportBean ).FullName + "().win:length({10, 11, 12})";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			IList<ViewSpec> viewSpecs = ( (FilterStreamSpec) walker.StatementSpec.StreamSpecs[0] ).ViewSpecs;
			int[] intParams = (int[]) viewSpecs[0].ObjectParameters[0];
			Assert.AreEqual( 10, intParams[0] );
			Assert.AreEqual( 11, intParams[1] );
			Assert.AreEqual( 12, intParams[2] );

			// Check a list of objects
			text = "select * from " + typeof( SupportBean ).FullName + "().win:length({false, 11.2, 's'})";
			walker = parseAndWalkEQL( text );
			viewSpecs = ( (FilterStreamSpec) walker.StatementSpec.StreamSpecs[0] ).ViewSpecs;
			Object[] objParams = (Object[]) viewSpecs[0].ObjectParameters[0];
			Assert.AreEqual( false, objParams[0] );
			Assert.AreEqual( 11.2, objParams[1] );
			Assert.AreEqual( "s", objParams[2] );
		}

		[Test]
		public virtual void testOuterJoin()
		{
			tryOuterJoin( "left", OuterJoinType.LEFT );
			tryOuterJoin( "right", OuterJoinType.RIGHT );
			tryOuterJoin( "full", OuterJoinType.FULL );
		}

		[Test]
		public virtual void testNoPackageName()
		{
			String text = "select intPrimitive from SupportBean_N().win:length(10) as win1";
			parseAndWalkEQL( text );
		}

		[Test]
		public virtual void testAggregateFunction()
		{
			String fromClause = "from " + typeof( SupportBean_N ).FullName + "().win:length(10) as win1";
			String text = "select max(distinct intPrimitive) " + fromClause;
			parseAndWalkEQL( text );

			text =
				"select sum(intPrimitive)," +
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

			parseAndWalkEQL( text );

			// try min-max aggregate versus row functions
            text =
                "select max(intPrimitive), min(intPrimitive)," +
                "max(intPrimitive,intBoxed), min(intPrimitive,intBoxed)," +
                "max(distinct intPrimitive), min(distinct intPrimitive)" + 
                fromClause;
			EQLTreeWalker walker = parseAndWalkEQL( text );
			IList<SelectExprElementUnnamedSpec> select = walker.StatementSpec.SelectListExpressions;
			Assert.IsTrue( select[0].SelectExpression is ExprMinMaxAggrNode );
			Assert.IsTrue( select[1].SelectExpression is ExprMinMaxAggrNode );
			Assert.IsTrue( select[2].SelectExpression is ExprMinMaxRowNode );
			Assert.IsTrue( select[3].SelectExpression is ExprMinMaxRowNode );
			Assert.IsTrue( select[4].SelectExpression is ExprMinMaxAggrNode );
			Assert.IsTrue( select[5].SelectExpression is ExprMinMaxAggrNode );

			try
			{
				parseAndWalkEQL( "select max(distinct intPrimitive, Intboxed)" );
				Assert.Fail();
			}
			catch ( System.Exception )
			{
				// expected
			}
		}

		[Test]
		public virtual void testGroupBy()
		{
			String text =
                "select sum(intPrimitive) from SupportBean_N().win:length(10) as win1 where intBoxed > 5 " +
                "group by intBoxed, 3 * doubleBoxed, max(2, doublePrimitive)";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			IList<ExprNode> groupByList = walker.StatementSpec.GroupByExpressions;
			Assert.AreEqual( 3, groupByList.Count );

			ExprNode node = groupByList[0];
			Assert.IsTrue( node is ExprIdentNode );

			node = groupByList[1];
			Assert.IsTrue( node is ExprMathNode );
			Assert.IsTrue( node.ChildNodes[0] is ExprConstantNode );
			Assert.IsTrue( node.ChildNodes[1] is ExprIdentNode );

			node = groupByList[2];
			Assert.IsTrue( node is ExprMinMaxRowNode );
		}

		[Test]
		public virtual void testHaving()
		{
			String text = 
                "select sum(intPrimitive) from SupportBean_N().win:length(10) as win1 where intBoxed > 5 " + 
                "group by intBoxed having sum(intPrimitive) > 5";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			ExprNode havingNode = walker.StatementSpec.HavingExprRootNode;

			Assert.IsTrue( havingNode is ExprRelationalOpNode );
			Assert.IsTrue( havingNode.ChildNodes[0] is ExprSumNode );
			Assert.IsTrue( havingNode.ChildNodes[1] is ExprConstantNode );

			text =
                "select sum(intPrimitive) from SupportBean_N().win:length(10) as win1 where intBoxed > 5 " + 
                "having intPrimitive < avg(intPrimitive)";
			walker = parseAndWalkEQL( text );

			havingNode = walker.StatementSpec.HavingExprRootNode;
			Assert.IsTrue( havingNode is ExprRelationalOpNode );
		}

		[Test]
		public virtual void testDistinct()
		{
			String text = "select sum(distinct intPrimitive) from SupportBean_N().win:length(10) as win1";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			ExprAggregateNode aggrNode = (ExprAggregateNode) walker.StatementSpec.SelectListExpressions[0].SelectExpression;
			Assert.IsTrue( aggrNode.IsDistinct );
		}

		[Test]
		public virtual void testComplexProperty()
		{
			String text = 
                "select array [ 1 ],s0.map('a'),nested.nested2, a[1].b as x " +
                " from SupportBean_N().win:length(10) as win1 " + 
                " where a[1].b('a').nested.c[0] = 4";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			ExprIdentNode identNode = (ExprIdentNode) walker.StatementSpec.SelectListExpressions[0].SelectExpression;
			Assert.AreEqual( "array[1]", identNode.UnresolvedPropertyName );
			Assert.IsNull( identNode.StreamOrPropertyName );

			identNode = (ExprIdentNode) walker.StatementSpec.SelectListExpressions[1].SelectExpression;
			Assert.AreEqual( "map('a')", identNode.UnresolvedPropertyName );
			Assert.AreEqual( "s0", identNode.StreamOrPropertyName );

			identNode = (ExprIdentNode) walker.StatementSpec.SelectListExpressions[2].SelectExpression;
			Assert.AreEqual( "nested2", identNode.UnresolvedPropertyName );
			Assert.AreEqual( "nested", identNode.StreamOrPropertyName );

			identNode = (ExprIdentNode) walker.StatementSpec.SelectListExpressions[3].SelectExpression;
			Assert.AreEqual( "a[1].b", identNode.UnresolvedPropertyName );
			Assert.AreEqual( null, identNode.StreamOrPropertyName );

			identNode = (ExprIdentNode) walker.StatementSpec.FilterRootNode.ChildNodes[0];
			Assert.AreEqual( "a[1].b('a').nested.c[0]", identNode.UnresolvedPropertyName );
			Assert.AreEqual( null, identNode.StreamOrPropertyName );
		}

		[Test]
		public virtual void testBitWise()
		{
			String text =
                "select intPrimitive & intBoxed from " + typeof( SupportBean ).FullName + "().win:length(10) as stream0";
			EQLTreeWalker walker = parseAndWalkEQL( text );
			IList<SelectExprElementUnnamedSpec> selectExpressions = walker.StatementSpec.SelectListExpressions;
			Assert.AreEqual( 1, selectExpressions.Count );
			Assert.IsTrue( selectExpressions[0].SelectExpression is ExprBitWiseNode );

			Assert.AreEqual( 0, tryBitWise( "1&2" ) );
			Assert.AreEqual( 3, tryBitWise( "1|2" ) );
			Assert.AreEqual( 8, tryBitWise( "10^2" ) );
		}

		[Test]
		public virtual void testPatternsOnly()
		{
			String patternOne = "a=" + typeof( SupportBean ).FullName + " -> b=" + typeof( SupportBean ).FullName;
			String patternTwo = "c=" + typeof( SupportBean ).FullName + " or " + typeof( SupportBean ).FullName;

			// Test simple case, one pattern and no "as streamName"
			EQLTreeWalker walker = parseAndWalkEQL( "select * from pattern [" + patternOne + "]" );
			Assert.AreEqual( 1, walker.StatementSpec.StreamSpecs.Count );
			PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];

			Assert.AreEqual( typeof( EvalFollowedByNode ), patternStreamSpec.EvalNode.GetType() );
			Assert.AreEqual( 2, patternStreamSpec.TaggedEventTypes.Count );
			Assert.AreEqual( typeof( SupportBean ), patternStreamSpec.TaggedEventTypes.Fetch( "a" ).UnderlyingType );
			Assert.IsNull( patternStreamSpec.OptionalStreamName );

			// Test case with "as s0"
			walker = parseAndWalkEQL( "select * from pattern [" + patternOne + "] as s0" );
			patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];
			Assert.AreEqual( "s0", patternStreamSpec.OptionalStreamName );

			// Test case with multiple patterns
			walker = parseAndWalkEQL( "select * from pattern [" + patternOne + "] as s0, pattern [" + patternTwo + "] as s1" );
			Assert.AreEqual( 2, walker.StatementSpec.StreamSpecs.Count );
			patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];
			Assert.AreEqual( "s0", patternStreamSpec.OptionalStreamName );
			Assert.AreEqual( typeof( EvalFollowedByNode ), patternStreamSpec.EvalNode.GetType() );
			Assert.AreEqual( 2, patternStreamSpec.TaggedEventTypes.Count );
			Assert.AreEqual( typeof( SupportBean ), patternStreamSpec.TaggedEventTypes.Fetch( "b" ).UnderlyingType );

			patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[1];
			Assert.AreEqual( "s1", patternStreamSpec.OptionalStreamName );
			Assert.AreEqual( typeof( EvalOrNode ), patternStreamSpec.EvalNode.GetType() );
			Assert.AreEqual( 1, patternStreamSpec.TaggedEventTypes.Count );
			Assert.AreEqual( typeof( SupportBean ), patternStreamSpec.TaggedEventTypes.Fetch( "c" ).UnderlyingType );

			// Test 3 patterns
			walker = parseAndWalkEQL( "select * from pattern [" + patternOne + "], pattern [" + patternTwo + "] as s1," + "pattern[x=" + typeof( SupportBean_S2 ).FullName + "] as s2" );
			Assert.AreEqual( 3, walker.StatementSpec.StreamSpecs.Count );
			patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[2];
			Assert.AreEqual( "s2", patternStreamSpec.OptionalStreamName );
			Assert.AreEqual( typeof( SupportBean_S2 ), patternStreamSpec.TaggedEventTypes.Fetch( "x" ).UnderlyingType );

			// Test patterns with views
			walker = parseAndWalkEQL( "select * from pattern [" + patternOne + "].win:time(1), pattern [" + patternTwo + "].win:length(1).std:lastevent() as s1" );
			Assert.AreEqual( 2, walker.StatementSpec.StreamSpecs.Count );
			patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];
			Assert.AreEqual( 1, patternStreamSpec.ViewSpecs.Count );
			Assert.AreEqual( "time", patternStreamSpec.ViewSpecs[0].ObjectName );
			patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[1];
			Assert.AreEqual( 2, patternStreamSpec.ViewSpecs.Count );
			Assert.AreEqual( "length", patternStreamSpec.ViewSpecs[0].ObjectName );
			Assert.AreEqual( "lastevent", patternStreamSpec.ViewSpecs[1].ObjectName );
		}

		[Test]
		public virtual void testIfThenElseCase()
		{
			String text;
            text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) end from " + typeof(SupportBean).FullName + "().win:length(10) as win";
			parseAndWalkEQL( text );
            text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) end as p1 from " + typeof(SupportBean).FullName + "().win:length(10) as win";
			parseAndWalkEQL( text );
            text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) else shortPrimitive end from " + typeof(SupportBean).FullName + "().win:length(10) as win";
			parseAndWalkEQL( text );
            text = "select case when intPrimitive > shortPrimitive then count(intPrimitive) when longPrimitive > intPrimitive then count(longPrimitive) else shortPrimitive end from " + typeof(SupportBean).FullName + "().win:length(10) as win";
			parseAndWalkEQL( text );
            text = "select case intPrimitive  when 1 then count(intPrimitive) end from " + typeof(SupportBean).FullName + "().win:length(10) as win";
			parseAndWalkEQL( text );
            text = "select case intPrimitive when longPrimitive then (intPrimitive + longPrimitive) end" + " from " + typeof(SupportBean).FullName + ".win:length(3)";
			parseAndWalkEQL( text );
		}

		private void tryOuterJoin( String outerType, OuterJoinType typeExpected )
		{
			String text =
			  "select intPrimitive from " +
				typeof( SupportBean_A ).FullName + "().win:length(10) as win1 " + outerType + " outer join " +
				typeof( SupportBean_A ).FullName + "().win:length(10) as win2 " + "on win1.f1 = win2.f2[1]";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			IList<OuterJoinDesc> descList = walker.StatementSpec.OuterJoinDescList;
			Assert.AreEqual( 1, descList.Count );
			OuterJoinDesc desc = descList[0];
			Assert.AreEqual( typeExpected, desc.OuterJoinType );
			Assert.AreEqual( "f1", desc.LeftNode.UnresolvedPropertyName );
			Assert.AreEqual( "win1", desc.LeftNode.StreamOrPropertyName );
			Assert.AreEqual( "f2[1]", desc.RightNode.UnresolvedPropertyName );
			Assert.AreEqual( "win2", desc.RightNode.StreamOrPropertyName );

			text = "select intPrimitive from " + typeof( SupportBean_A ).FullName + "().win:length(10) as win1 " + outerType + " outer join " + typeof( SupportBean_A ).FullName + "().win:length(10) as win2 " + "on win1.f1 = win2.f2 " + outerType + " outer join " + typeof( SupportBean_A ).FullName + "().win:length(10) as win3 " + "on win1.f1 = win3.f3";
			walker = parseAndWalkEQL( text );

			descList = walker.StatementSpec.OuterJoinDescList;
			Assert.AreEqual( 2, descList.Count );

			desc = descList[0];
			Assert.AreEqual( typeExpected, desc.OuterJoinType );
			Assert.AreEqual( "f1", desc.LeftNode.UnresolvedPropertyName );
			Assert.AreEqual( "win1", desc.LeftNode.StreamOrPropertyName );
			Assert.AreEqual( "f2", desc.RightNode.UnresolvedPropertyName );
			Assert.AreEqual( "win2", desc.RightNode.StreamOrPropertyName );

			desc = descList[1];
			Assert.AreEqual( typeExpected, desc.OuterJoinType );
			Assert.AreEqual( "f1", desc.LeftNode.UnresolvedPropertyName );
			Assert.AreEqual( "win1", desc.LeftNode.StreamOrPropertyName );
			Assert.AreEqual( "f3", desc.RightNode.UnresolvedPropertyName );
			Assert.AreEqual( "win3", desc.RightNode.StreamOrPropertyName );
		}

		[Test]
		public virtual void testWalkPattern()
		{
			String text = "every g=" + typeof( SupportBean ).FullName + "(StringValue=\"IBM\", intPrimitive != 1) where timer:within(20)";

			EQLTreeWalker walker = parseAndWalkPattern( text );

			Assert.AreEqual( 1, walker.StatementSpec.StreamSpecs.Count );
			PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];

			EvalNode rootNode = patternStreamSpec.EvalNode;
			rootNode.DumpDebug( ".testWalk " );

			EvalEveryNode everyNode = (EvalEveryNode) rootNode;

			Assert.AreEqual( 1, everyNode.ChildNodes.Count );
			Assert.IsTrue( everyNode.ChildNodes[0] is EvalGuardNode );
			EvalGuardNode guardNode = (EvalGuardNode) everyNode.ChildNodes[0];

			Assert.AreEqual( 1, guardNode.ChildNodes.Count );
			Assert.IsTrue( guardNode.ChildNodes[0] is EvalFilterNode );
			EvalFilterNode filterNode = (EvalFilterNode) guardNode.ChildNodes[0];

			Assert.AreEqual( "g", filterNode.EventAsName );
			Assert.AreEqual( 0, filterNode.ChildNodes.Count );
			Assert.AreEqual( 2, filterNode.FilterSpec.Parameters.Count );
			Assert.AreEqual( "intPrimitive", filterNode.FilterSpec.Parameters[1].PropertyName );
			Assert.AreEqual( FilterOperator.NOT_EQUAL, filterNode.FilterSpec.Parameters[1].FilterOperator );
			Assert.AreEqual( 1, filterNode.FilterSpec.Parameters[1].getFilterValue( null ) );

			Assert.AreEqual( 1, patternStreamSpec.TaggedEventTypes.Count );
			Assert.AreEqual( typeof( SupportBean ), patternStreamSpec.TaggedEventTypes.Fetch( "g" ).UnderlyingType );
		}

		[Test]
		public virtual void testWalkPropertyPatternCombination()
		{
			String EVENT = typeof( SupportBeanComplexProps ).FullName;
			String property = tryWalkGetPropertyPattern( EVENT + "(mapped ( 'key' )  = 'value')" );
			Assert.AreEqual( "mapped('key')", property );

			property = tryWalkGetPropertyPattern( EVENT + "(indexed [ 1 ]  = 1)" );
			Assert.AreEqual( "indexed[1]", property );
			property = tryWalkGetPropertyPattern( EVENT + "(nested . nestedValue  = 'value')" );
			Assert.AreEqual( "nested.nestedValue", property );
		}

		[Test]
		public virtual void testWalkPatternUseResult()
		{
			String EVENT = typeof( SupportBean_N ).FullName;
			String text = "na=" + EVENT + "() -> every nb=" + EVENT + "(doublePrimitive in [0:na.doublePrimitive])";
			parseAndWalkPattern( text );
		}

		[Test]
		public virtual void testWalkIStreamRStreamSelect()
		{
			String text = "select rstream 'a' from " + typeof( SupportBean_N ).FullName;
			EQLTreeWalker walker = parseAndWalkEQL( text );
			Assert.AreEqual( SelectClauseStreamSelectorEnum.RSTREAM_ONLY, walker.StatementSpec.SelectStreamSelectorEnum );

			text = "select istream 'a' from " + typeof( SupportBean_N ).FullName;
			walker = parseAndWalkEQL( text );
			Assert.AreEqual( SelectClauseStreamSelectorEnum.ISTREAM_ONLY, walker.StatementSpec.SelectStreamSelectorEnum );

			text = "select 'a' from " + typeof( SupportBean_N ).FullName;
			walker = parseAndWalkEQL( text );
			Assert.AreEqual( SelectClauseStreamSelectorEnum.RSTREAM_ISTREAM_BOTH, walker.StatementSpec.SelectStreamSelectorEnum );
		}

		[Test]
		public virtual void testWalkPatternNoPackage()
		{
			SupportEventAdapterService.Service.AddBeanType( "SupportBean_N", typeof( SupportBean_N ) );
			String text = "na=SupportBean_N()";
			parseAndWalkPattern( text );
		}

		[Test]
		public virtual void testWalkPatternTypesValid()
		{
			String text = typeof( SupportBean ).FullName;
			EQLTreeWalker walker = parseAndWalkPattern( text );
			Assert.AreEqual( 1, walker.StatementSpec.StreamSpecs.Count );
			PatternStreamSpec spec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];
			Assert.AreEqual( 0, spec.TaggedEventTypes.Count );
		}

		[Test]
		public virtual void testPatternWalkTypesInvalid()
		{
			String text = "a=" + typeof( SupportBean ).FullName + " or a=" + typeof( SupportBean_A ).FullName;

			try
			{
				parseAndWalkPattern( text );
				Assert.Fail();
			}
			catch ( System.Exception ex )
			{
				log.Debug( ".testWalkTypesInvalid Expected exception, msg=" + ex.Message );
			}
		}

		[Test]
		public virtual void testWalkPatternIntervals()
		{
			Object[][] intervals = new Object[][]{
				new Object[]{"1E2 milliseconds", 0.1d},
				new Object[]{"11 millisecond", 11 / 1000d},
				new Object[]{"1.1 msec", 1.1 / 1000d},
				new Object[]{"5 seconds", 5d}, 
				new Object[]{"0.1 second", 0.1d}, 
				new Object[]{"135L sec", 135d}, 
				new Object[]{"1.4 minutes", 1.4 * 60d}, 
				new Object[]{"11 minute", 11 * 60d},
				new Object[]{"123.2 min", 123.2 * 60d},
				new Object[]{".2 hour", .2 * 60 * 60d},
				new Object[]{"11.2 hours", 11.2 * 60 * 60d}, 
				new Object[]{"2 day", 2 * 24 * 60 * 60d},
				new Object[]{"11.2 days", 11.2 * 24 * 60 * 60d},
				new Object[]{"1 days 6 hours 2 minutes 4 seconds 3 milliseconds", 1 * 24 * 60 * 60 + 6 * 60 * 60 + 2 * 60 + 4 + 3 / 1000d},
				new Object[]{"0.2 day 3.3 hour 1E3 minute 0.33 second 10000 millisecond", 0.2d * 24 * 60 * 60 + 3.3d * 60 * 60 + 1e3 * 60 + 0.33 + 10000 / 1000},
				new Object[]{"0.2 day 3.3 hour 1E3 min 0.33 sec 10000 msec", 0.2d * 24 * 60 * 60 + 3.3d * 60 * 60 + 1e3 * 60 + 0.33 + 10000 / 1000},
				new Object[]{"1.01 hour 2 sec", 1.01d * 60 * 60 + 2}, new Object[]{"0.02 day 5 msec", 0.02d * 24 * 60 * 60 + 5 / 1000d},
				new Object[]{"66 min 4 sec", 66 * 60 + 4d}
			};

			for ( int i = 0 ; i < intervals.Length ; i++ )
			{
				String interval = (String) intervals[i][0];
				double result = tryInterval( interval );
				double expected = (Double) intervals[i][1];
				double delta = result - expected;
				Assert.IsTrue( Math.Abs( delta ) < 0.0000001, "Interval '" + interval + "' expected=" + expected + " actual=" + result );
			}
		}

		[Test]
		public virtual void testWalkInAndBetween()
		{
			Assert.IsFalse( (bool) tryRelationalOp( "1 in (2,3)" ) );
			Assert.IsTrue( (bool) tryRelationalOp( "1 in (2,3,1)" ) );
			Assert.IsTrue( (bool) tryRelationalOp( "1 not in (2,3)" ) );

			Assert.IsTrue( (bool) tryRelationalOp( "1 between 0 and 2" ) );
			Assert.IsFalse( (bool) tryRelationalOp( "-1 between 0 and 2" ) );
			Assert.IsFalse( (bool) tryRelationalOp( "1 not between 0 and 2" ) );
			Assert.IsTrue( (bool) tryRelationalOp( "-1 not between 0 and 2" ) );
		}

		[Test]
		public virtual void testWalkLikeRegex()
		{
			Assert.IsTrue( (bool) tryRelationalOp( "'abc' like 'a__'" ) );
			Assert.IsFalse( (bool) tryRelationalOp( "'abcd' like 'a__'" ) );

			Assert.IsFalse( (bool) tryRelationalOp( "'abcde' not like 'a%'" ) );
			Assert.IsTrue( (bool) tryRelationalOp( "'bcde' not like 'a%'" ) );

			Assert.IsTrue( (bool) tryRelationalOp( "'a_' like 'a!_' escape '!'" ) );
			Assert.IsFalse( (bool) tryRelationalOp( "'ab' like 'a!_' escape '!'" ) );

			Assert.IsFalse( (bool) tryRelationalOp( "'a' not like 'a'" ) );
			Assert.IsTrue( (bool) tryRelationalOp( "'a' not like 'ab'" ) );
		}

		[Test]
		public virtual void testWalkStaticFunc()
		{
			String text = "select MyClass.someFunc(1) from SupportBean_N";
			EQLTreeWalker walker = parseAndWalkEQL( text );

			SelectExprElementUnnamedSpec spec = walker.StatementSpec.SelectListExpressions[0];
			ExprStaticMethodNode staticMethod = (ExprStaticMethodNode) spec.SelectExpression;
			Assert.AreEqual( "MyClass", staticMethod.ClassName );
			Assert.AreEqual( "someFunc", staticMethod.MethodName );
		}

		[Test]
		public virtual void testWalkDBJoinStatement()
		{
			String className = typeof( SupportBean ).FullName;
			String sql = "select a from b where $x.id=c.d";
			String expression = "select * from " + className + ", sql:mydb ['" + sql + "']";

			EQLTreeWalker walker = parseAndWalkEQL( expression );
			StatementSpec statementSpec = walker.StatementSpec;
			Assert.AreEqual( 2, statementSpec.StreamSpecs.Count );
			DBStatementStreamSpec dbSpec = (DBStatementStreamSpec) statementSpec.StreamSpecs[1];
			Assert.AreEqual( "mydb", dbSpec.DatabaseName );
			Assert.AreEqual( sql, dbSpec.SqlWithSubsParams );
		}

		private double tryInterval( String interval )
		{
			String text = "select * from " + typeof( SupportBean ).FullName + ".win:time(" + interval + ")";

			EQLTreeWalker walker = parseAndWalkEQL( text );
			ViewSpec viewSpec = ( (FilterStreamSpec) walker.StatementSpec.StreamSpecs[0] ).ViewSpecs[0];
			Assert.AreEqual( "win", viewSpec.ObjectNamespace );
			Assert.AreEqual( "time", viewSpec.ObjectName );
			Assert.AreEqual( 1, viewSpec.ObjectParameters.Count );
			TimePeriodParameter timePeriodParameter = (TimePeriodParameter) viewSpec.ObjectParameters[0];
			return timePeriodParameter.NumSeconds;
		}

		private String tryWalkGetPropertyPattern( String stmt )
		{
			EQLTreeWalker walker = parseAndWalkPattern( stmt );

			Assert.AreEqual( 1, walker.StatementSpec.StreamSpecs.Count );
			PatternStreamSpec patternStreamSpec = (PatternStreamSpec) walker.StatementSpec.StreamSpecs[0];

			EvalFilterNode filterNode = (EvalFilterNode) patternStreamSpec.EvalNode;
			Assert.AreEqual( 1, filterNode.FilterSpec.Parameters.Count );
			return filterNode.FilterSpec.Parameters[0].PropertyName;
		}

		private static EQLTreeWalker parseAndWalkPattern( String expression )
		{
			log.Debug( ".parseAndWalk Trying text=" + expression );
			AST ast = SupportParserHelper.parsePattern( expression );
			log.Debug( ".parseAndWalk success, tree walking..." );
			SupportParserHelper.displayAST( ast );

			EQLTreeWalker walker = new EQLTreeWalker( SupportEventAdapterService.Service );
			walker.startPatternExpressionRule( ast );
			return walker;
		}

		private static EQLTreeWalker parseAndWalkEQL( String expression )
		{
			log.Debug( ".parseAndWalk Trying text=" + expression );
			AST ast = SupportParserHelper.parseEQL( expression );
			log.Debug( ".parseAndWalk success, tree walking..." );
			SupportParserHelper.displayAST( ast );

			EventAdapterService eventAdapterService = SupportEventAdapterService.Service;
			eventAdapterService.AddBeanType( "SupportBean_N", typeof( SupportBean_N ) );

			EQLTreeWalker walker = new EQLTreeWalker( eventAdapterService );
			walker.startEQLExpressionRule( ast );
			return walker;
		}

		private Object tryBitWise( String equation )
		{
			String expression = EXPRESSION + "where (" + equation + ")=win2.f2";

			EQLTreeWalker walker = parseAndWalkEQL( expression );
			ExprNode exprNode = walker.StatementSpec.FilterRootNode.ChildNodes[0];
			ExprBitWiseNode bitWiseNode = (ExprBitWiseNode) ( exprNode );
			bitWiseNode.GetValidatedSubtree( null, null );
			return bitWiseNode.Evaluate( null );
		}

		private Object tryExpression( String equation )
		{
			String expression = EXPRESSION + "where " + equation + "=win2.f2";

			EQLTreeWalker walker = parseAndWalkEQL( expression );
			ExprNode exprNode = ( walker.StatementSpec.FilterRootNode.ChildNodes[0] );
			exprNode = exprNode.GetValidatedSubtree( null, null );
			return exprNode.Evaluate( null );
		}

		private Object tryRelationalOp( String subExpr )
		{
			String expression = EXPRESSION + "where " + subExpr;

			EQLTreeWalker walker = parseAndWalkEQL( expression );
			ExprNode filterExprNode = walker.StatementSpec.FilterRootNode;
			filterExprNode.GetValidatedSubtree( null, null );
			return filterExprNode.Evaluate( null );
		}

		private static readonly Log log = LogFactory.GetLog( System.Reflection.MethodBase.GetCurrentMethod().DeclaringType );
	}
}
