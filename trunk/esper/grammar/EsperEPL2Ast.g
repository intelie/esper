tree grammar EsperEPL2Ast;

options
{
	k = 2;                   	// lookahead is 2 tokens
	tokenVocab = EsperEPL2Grammar;
	//output = AST;
    	ASTLabelType = CommonTree;
}

@header {
  package com.espertech.esper.epl.generated;
  import java.util.Stack;
  import org.apache.commons.logging.Log;
  import org.apache.commons.logging.LogFactory;
}

@members {
  private static Log log = LogFactory.getLog(EsperEPL2Ast.class);

  // For pattern processing within EPL
  protected void endPattern() {};

  protected void pushStmtContext() {};
  protected void leaveNode(Tree node) {};
  protected void end() {};

  protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);  
  }

  public void recoverFromMismatchedToken(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) throws RecognitionException {
    throw recognitionException;
  }

  public Object recoverFromMismatchedSet(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) throws RecognitionException {
    throw recognitionException;
  }

  protected boolean recoverFromMismatchedElement(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) {
    throw new RuntimeException("Error recovering from mismatched element", recognitionException);
  }
  
  public void recover(org.antlr.runtime.IntStream intStream, org.antlr.runtime.RecognitionException recognitionException) {
    throw new RuntimeException("Error recovering from recognition exception", recognitionException);
  }
}
@rulecatch {
  catch (RecognitionException rex) {
    throw rex;
  }
}

//----------------------------------------------------------------------------
// EPL expression
//----------------------------------------------------------------------------
startEPLExpressionRule
	:	^(EPL_EXPR eplExpressionRule) { end(); }		
	;

eplExpressionRule
	:	(selectExpr | createWindowExpr | createVariableExpr | onExpr)		 
	;

onExpr 
	:	^(i=ON_EXPR (eventFilterExpr | patternInclusionExpression) IDENT? 
		(onDeleteExpr | onSelectExpr | onSetExpr)
		{ leaveNode($i); } )
	;
	
onDeleteExpr
	:	^(ON_DELETE_EXPR onExprFrom (whereClause)? )
	;	

onSelectExpr
	:	^(ON_SELECT_EXPR (insertIntoExpr)? selectionList onExprFrom (whereClause)? (groupByClause)? (havingClause)? (orderByClause)?)
	;	
	
onSetExpr
	:	^(ON_SET_EXPR onSetAssignment (onSetAssignment)*)
	;
	
onSetAssignment
	:	IDENT valueExpr
	;

onExprFrom
	:	^(ON_EXPR_FROM IDENT (IDENT)? )
	;

createWindowExpr
	:	^(i=CREATE_WINDOW_EXPR IDENT (viewListExpr)? RETAINUNION? RETAININTERSECTION? 
			(
				(createSelectionList? CLASS_IDENT) 
			       | 
			        (createColTypeList)
			)
			createWindowExprInsert?
		{ leaveNode($i); })
	;

createWindowExprInsert
	:	^(INSERT valueExpr?)
	;
	
createSelectionList
	:	^(s=CREATE_WINDOW_SELECT_EXPR createSelectionListElement (createSelectionListElement)* { leaveNode($s); } )
	;
	
createColTypeList
	:	^(CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement (createColTypeListElement)*)
	;

createColTypeListElement
	:	^(CREATE_WINDOW_COL_TYPE IDENT IDENT)
	;

createSelectionListElement
	:	w=WILDCARD_SELECT { leaveNode($w); }
	|	^(s=SELECTION_ELEMENT_EXPR (
	              (eventPropertyExpr[true] (IDENT)?) 
	            | (constant[true] IDENT)
	              ) { leaveNode($s); } )
	;

createVariableExpr
	:	^(i=CREATE_VARIABLE_EXPR IDENT IDENT (valueExpr)? { leaveNode($i); } )
	;

selectExpr
	:	(insertIntoExpr)?
		selectClause 
		fromClause
		(whereClause)?
		(groupByClause)?
		(havingClause)?
		(outputLimitExpr)?
		(orderByClause)?
		(rowLimitClause)?
	;
	
insertIntoExpr
	:	^(i=INSERTINTO_EXPR (ISTREAM | RSTREAM)? IDENT (insertIntoExprCol)? { leaveNode($i); } )
	;
	
insertIntoExprCol
	:	^(INSERTINTO_EXPRCOL IDENT (IDENT)* )
	;

selectClause
	:	^(s=SELECTION_EXPR (RSTREAM | ISTREAM | IRSTREAM)? selectionList { leaveNode($s); })
	;

fromClause
	:	streamExpression (streamExpression (outerJoin)* )*
	;
	
selectionList
	:	selectionListElement (selectionListElement)*
	;
	
selectionListElement
	:	w=WILDCARD_SELECT { leaveNode($w); }
	|	^(e=SELECTION_ELEMENT_EXPR valueExpr (IDENT)? { leaveNode($e); } )
	|	^(s=SELECTION_STREAM IDENT (IDENT)? { leaveNode($s); } )
	;
		
outerJoin
	:	outerJoinIdent
	;

outerJoinIdent
	:	^(tl=LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] (eventPropertyExpr[true] eventPropertyExpr[true])* { leaveNode($tl); } )
	|	^(tr=RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] (eventPropertyExpr[true] eventPropertyExpr[true])* { leaveNode($tr); } )
	|	^(tf=FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] (eventPropertyExpr[true] eventPropertyExpr[true])* { leaveNode($tf); } )
	|	^(i=INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] (eventPropertyExpr[true] eventPropertyExpr[true])* { leaveNode($i); } )
	;

streamExpression
	:	^(v=STREAM_EXPR (eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression) (viewListExpr)? (IDENT)? (UNIDIRECTIONAL)? (RETAINUNION|RETAININTERSECTION)? { leaveNode($v); } )
	;

eventFilterExpr
	:	^( f=EVENT_FILTER_EXPR IDENT? CLASS_IDENT propertyExpression? (valueExpr)* { leaveNode($f); } )
	;
	
propertyExpression
	:	^( EVENT_FILTER_PROPERTY_EXPR propertyExpressionAtom*)
	;	
	
propertyExpressionAtom
	:	^( a=EVENT_FILTER_PROPERTY_EXPR_ATOM propertySelectionListElement* eventPropertyExpr[false] IDENT? ^(WHERE_EXPR valueExpr?) { leaveNode($a); })
	;	
	
propertySelectionListElement
	:	w=PROPERTY_WILDCARD_SELECT { leaveNode($w); }
	|	^(e=PROPERTY_SELECTION_ELEMENT_EXPR valueExpr (IDENT)? { leaveNode($e); } )
	|	^(s=PROPERTY_SELECTION_STREAM IDENT (IDENT)? { leaveNode($s); } )
	;	

patternInclusionExpression
	:	^(p=PATTERN_INCL_EXPR exprChoice { leaveNode($p); } )
	;
	
databaseJoinExpression
	:	^(DATABASE_JOIN_EXPR IDENT (STRING_LITERAL | QUOTED_STRING_LITERAL) (STRING_LITERAL | QUOTED_STRING_LITERAL)?)
	;
	
methodJoinExpression
	:	^(METHOD_JOIN_EXPR IDENT CLASS_IDENT (valueExpr)*)
	;

viewListExpr
	:	viewExpr (viewExpr)*
	;
	
viewExpr
	:	^(n=VIEW_EXPR IDENT IDENT (valueExprWithTime)* { leaveNode($n); } )
	;
	
whereClause
	:	^(n=WHERE_EXPR valueExpr { leaveNode($n); } )
	;

groupByClause
	:	^(g=GROUP_BY_EXPR valueExpr (valueExpr)* ) { leaveNode($g); }
	;

orderByClause
	:	^(ORDER_BY_EXPR orderByElement (orderByElement)* )
	;
	
orderByElement
	: 	^(e=ORDER_ELEMENT_EXPR valueExpr (ASC|DESC)? { leaveNode($e); } )
	;

havingClause
	:	^(n=HAVING_EXPR valueExpr { leaveNode($n); } )
	;

outputLimitExpr
	:	^(e=EVENT_LIMIT_EXPR (ALL|FIRST|LAST|SNAPSHOT)? (number|IDENT) { leaveNode($e); } ) 
	|   	^(tp=TIMEPERIOD_LIMIT_EXPR (ALL|FIRST|LAST|SNAPSHOT)? timePeriod { leaveNode($tp); } )
	|   	^(cron=CRONTAB_LIMIT_EXPR (ALL|FIRST|LAST|SNAPSHOT)? crontabLimitParameterSet { leaveNode($cron); } )
	|   	^(when=WHEN_LIMIT_EXPR (ALL|FIRST|LAST|SNAPSHOT)? valueExpr onSetExpr? { leaveNode($when); } )
	;

rowLimitClause
	:	^(e=ROW_LIMIT_EXPR (number|IDENT) (number|IDENT)? COMMA? OFFSET? { leaveNode($e); } ) 
	;

crontabLimitParameterSet
	:	^(CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime?)
	;

relationalExpr
	: 	^(n=LT relationalExprValue { leaveNode($n); } )
	| 	^(n=GT relationalExprValue { leaveNode($n); } )
	| 	^(n=LE relationalExprValue { leaveNode($n); } )
	|	^(n=GE relationalExprValue { leaveNode($n); } )
	;

relationalExprValue 
	:	(
		  valueExpr 
		  	( valueExpr
		  	| 
		  	(ANY|SOME|ALL) (valueExpr* | subSelectGroupExpr)
		  	)
		)
	;

evalExprChoice
	:	^(jo=EVAL_OR_EXPR valueExpr valueExpr (valueExpr)* { leaveNode($jo); } )
	|	^(ja=EVAL_AND_EXPR valueExpr valueExpr (valueExpr)* { leaveNode($ja); } )
	|	^(je=EVAL_EQUALS_EXPR valueExpr valueExpr { leaveNode($je); } )
	|	^(jne=EVAL_NOTEQUALS_EXPR valueExpr valueExpr { leaveNode($jne); } )
	|	^(jge=EVAL_EQUALS_GROUP_EXPR valueExpr (ANY|SOME|ALL) (valueExpr* | subSelectGroupExpr) { leaveNode($jge); } )
	|	^(jgne=EVAL_NOTEQUALS_GROUP_EXPR valueExpr (ANY|SOME|ALL) (valueExpr* | subSelectGroupExpr) { leaveNode($jgne); } )
	|	^(n=NOT_EXPR valueExpr { leaveNode($n); } )
	|	r=relationalExpr
	;
	
valueExpr
	: 	constant[true]
	|	substitution
	| 	arithmeticExpr 
	| 	eventPropertyExpr[true]
	|   	evalExprChoice
	|	builtinFunc
	|   	libFunc
	|	caseExpr
	|	inExpr 
	|	betweenExpr
	|	likeExpr
	|	regExpExpr
	|	arrayExpr
	|	subSelectInExpr
	| 	subSelectRowExpr 
	| 	subSelectExistsExpr
	;

valueExprWithTime
	:	l=LAST { leaveNode($l); }
	|	lw=LW { leaveNode($lw); }
	|	valueExpr
	|	^(ordered=OBJECT_PARAM_ORDERED_EXPR valueExpr (DESC|ASC) { leaveNode($ordered); })
	| 	rangeOperator
	| 	frequencyOperator
	|	lastOperator
	|	weekDayOperator
	| 	^( l=NUMERIC_PARAM_LIST numericParameterList+ { leaveNode($l); })
	|	s=NUMBERSETSTAR { leaveNode($s); }
	|	timePeriod
	;
	
numericParameterList
	: 	constant[true]
	| 	rangeOperator
	| 	frequencyOperator
	;
	
rangeOperator
	:	^( r=NUMERIC_PARAM_RANGE (constant[true]|eventPropertyExpr[true]|substitution) (constant[true]|eventPropertyExpr[true]|substitution) { leaveNode($r); })
	;
		
frequencyOperator
	:	^( f=NUMERIC_PARAM_FREQUENCY (constant[true]|eventPropertyExpr[true]|substitution) { leaveNode($f); })
	;

lastOperator
	:	^( l=LAST_OPERATOR (constant[true]|eventPropertyExpr[true]|substitution) { leaveNode($l); })
	;

weekDayOperator
	:	^( w=WEEKDAY_OPERATOR (constant[true]|eventPropertyExpr[true]|substitution) { leaveNode($w); })
	;
	
subSelectGroupExpr
	:	{pushStmtContext();} ^(s=SUBSELECT_GROUP_EXPR subQueryExpr) 	// no need to leave the node since the statement is pulled where needed
	;

subSelectRowExpr
	:	{pushStmtContext();} ^(s=SUBSELECT_EXPR subQueryExpr) {leaveNode($s);}
	;

subSelectExistsExpr
	:	{pushStmtContext();} ^(e=EXISTS_SUBSELECT_EXPR subQueryExpr) {leaveNode($e);}
	;
	
subSelectInExpr
	: 	^(s=IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr) { leaveNode($s); }
	| 	^(s=NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr) { leaveNode($s); }
	;

subSelectInQueryExpr
	:	{pushStmtContext();} ^(i=IN_SUBSELECT_QUERY_EXPR subQueryExpr) {leaveNode($i);}
	;
	
subQueryExpr 
	:	selectionListElement subSelectFilterExpr (whereClause)?
	;
	
subSelectFilterExpr
	:	^(v=STREAM_EXPR eventFilterExpr (viewListExpr)? (IDENT)? RETAINUNION? RETAININTERSECTION? { leaveNode($v); } )
	;
	
caseExpr
	: ^(c=CASE (valueExpr)*) { leaveNode($c); }
	| ^(c=CASE2 (valueExpr)*) { leaveNode($c); }
	;
	
inExpr
	: ^(i=IN_SET valueExpr (LPAREN|LBRACK) valueExpr (valueExpr)* (RPAREN|RBRACK)) { leaveNode($i); }
	| ^(i=NOT_IN_SET valueExpr (LPAREN|LBRACK) valueExpr (valueExpr)* (RPAREN|RBRACK)) { leaveNode($i); }
	| ^(i=IN_RANGE valueExpr (LPAREN|LBRACK) valueExpr valueExpr (RPAREN|RBRACK)) { leaveNode($i); }
	| ^(i=NOT_IN_RANGE valueExpr (LPAREN|LBRACK) valueExpr valueExpr (RPAREN|RBRACK)) { leaveNode($i); }
	;
		
betweenExpr
	: ^(b=BETWEEN valueExpr valueExpr valueExpr) { leaveNode($b); }
	| ^(b=NOT_BETWEEN valueExpr valueExpr (valueExpr)*) { leaveNode($b); }
	;
	
likeExpr
	: ^(l=LIKE valueExpr valueExpr (valueExpr)?) { leaveNode($l); }
	| ^(l=NOT_LIKE valueExpr valueExpr (valueExpr)?) { leaveNode($l); }
	;

regExpExpr
	: ^(r=REGEXP valueExpr valueExpr) { leaveNode($r); }
	| ^(r=NOT_REGEXP valueExpr valueExpr) { leaveNode($r); }
	;
	
builtinFunc
	: 	^(f=SUM (DISTINCT)? valueExpr) { leaveNode($f); }
	|	^(f=AVG (DISTINCT)? valueExpr) { leaveNode($f); }
	|	^(f=COUNT ((DISTINCT)? valueExpr)? ) { leaveNode($f); }
	|	^(f=MEDIAN (DISTINCT)? valueExpr) { leaveNode($f); }
	|	^(f=STDDEV (DISTINCT)? valueExpr) { leaveNode($f); }
	|	^(f=AVEDEV (DISTINCT)? valueExpr) { leaveNode($f); }
	| 	^(f=COALESCE valueExpr valueExpr (valueExpr)* ) { leaveNode($f); }
	| 	^(f=PREVIOUS valueExpr eventPropertyExpr[true]) { leaveNode($f); }
	| 	^(f=PRIOR c=NUM_INT eventPropertyExpr[true]) {leaveNode($c); leaveNode($f);}
	| 	^(f=INSTANCEOF valueExpr CLASS_IDENT (CLASS_IDENT)*) { leaveNode($f); }
	| 	^(f=CAST valueExpr CLASS_IDENT) { leaveNode($f); }
	| 	^(f=EXISTS eventPropertyExpr[true]) { leaveNode($f); }
	|	^(f=CURRENT_TIMESTAMP {}) { leaveNode($f); }
	;
	
arrayExpr
	:	^(a=ARRAY_EXPR (valueExpr)*) { leaveNode($a); }
	;
	
arithmeticExpr
	: 	^(a=PLUS valueExpr valueExpr) { leaveNode($a); }
	| 	^(a=MINUS valueExpr valueExpr) { leaveNode($a); }
	| 	^(a=DIV valueExpr valueExpr) { leaveNode($a); }
	|	^(a=STAR valueExpr valueExpr) { leaveNode($a); }
	| 	^(a=MOD valueExpr valueExpr) { leaveNode($a); }
	|	^(a=BAND valueExpr valueExpr) { leaveNode($a); }
	|	^(a=BOR valueExpr valueExpr) { leaveNode($a); }
	|	^(a=BXOR valueExpr valueExpr) { leaveNode($a); }
	| 	^(a=CONCAT valueExpr valueExpr (valueExpr)*) { leaveNode($a); }
	;
	
libFunc
	:  ^(l=LIB_FUNCTION (CLASS_IDENT)? IDENT (DISTINCT)? (valueExpr)*) { leaveNode($l); }
	;
	
//----------------------------------------------------------------------------
// pattern expression
//----------------------------------------------------------------------------
startPatternExpressionRule
	:	exprChoice { endPattern(); end(); }
	;

exprChoice
	: 	atomicExpr
	|	patternOp
	| 	^( a=EVERY_EXPR exprChoice { leaveNode($a); } )
	| 	^( n=PATTERN_NOT_EXPR exprChoice { leaveNode($n); } )
	| 	^( g=GUARD_EXPR exprChoice IDENT IDENT valueExprWithTime* { leaveNode($g); } )
	|	^( m=MATCH_UNTIL_EXPR matchUntilRange? exprChoice exprChoice? { leaveNode($m); } )
	;
	
patternOp
	:	^( f=FOLLOWED_BY_EXPR exprChoice exprChoice (exprChoice)* { leaveNode($f); } )
	| 	^( o=OR_EXPR exprChoice exprChoice (exprChoice)* { leaveNode($o); } )
	| 	^( a=AND_EXPR exprChoice exprChoice (exprChoice)* { leaveNode($a); } )	
	;
	
atomicExpr
	:	patternFilterExpr
	|   	^( ac=OBSERVER_EXPR IDENT IDENT valueExprWithTime* { leaveNode($ac); } )
	;

patternFilterExpr
	:	^( f=PATTERN_FILTER_EXPR IDENT? CLASS_IDENT propertyExpression? (valueExpr)* { leaveNode($f); } )
	;

matchUntilRange
	:	^(MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam)
	| 	^(MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam)
	| 	^(MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam)
	|	^(MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam)
	;

matchUntilRangeParam
	:	NUM_DOUBLE
	|	NUM_INT
	;

filterParam
	:	^(EVENT_FILTER_PARAM valueExpr (valueExpr)*)
	;
	
filterParamComparator
	:	^(EQUALS filterAtom)
	|	^(NOT_EQUAL filterAtom)
	|	^(LT filterAtom)
	|	^(LE filterAtom)
	|	^(GT filterAtom)
	|	^(GE filterAtom)
	|	^(EVENT_FILTER_RANGE (LPAREN|LBRACK) (constant[false]|filterIdentifier) (constant[false]|filterIdentifier) (RPAREN|RBRACK))
	|	^(EVENT_FILTER_NOT_RANGE (LPAREN|LBRACK) (constant[false]|filterIdentifier) (constant[false]|filterIdentifier) (RPAREN|RBRACK))
	|	^(EVENT_FILTER_IN (LPAREN|LBRACK) (constant[false]|filterIdentifier) (constant[false]|filterIdentifier)* (RPAREN|RBRACK))
	|	^(EVENT_FILTER_NOT_IN (LPAREN|LBRACK) (constant[false]|filterIdentifier) (constant[false]|filterIdentifier)* (RPAREN|RBRACK))
	|	^(EVENT_FILTER_BETWEEN (constant[false]|filterIdentifier) (constant[false]|filterIdentifier))
	|	^(EVENT_FILTER_NOT_BETWEEN (constant[false]|filterIdentifier) (constant[false]|filterIdentifier))
	;
	
filterAtom
	:	constant[false]
	|	filterIdentifier;
	
filterIdentifier
	:	^(EVENT_FILTER_IDENT IDENT eventPropertyExpr[true])
	;	
	
eventPropertyExpr[boolean isLeaveNode]
	:	^(p=EVENT_PROP_EXPR eventPropertyAtomic (eventPropertyAtomic)* ) { if ($isLeaveNode) leaveNode($p); }
	;
	
eventPropertyAtomic
	:	^(EVENT_PROP_SIMPLE IDENT)
	|	^(EVENT_PROP_INDEXED IDENT NUM_INT)
	|	^(EVENT_PROP_MAPPED IDENT (STRING_LITERAL | QUOTED_STRING_LITERAL))
	|	^(EVENT_PROP_DYNAMIC_SIMPLE IDENT)
	|	^(EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT)
	|	^(EVENT_PROP_DYNAMIC_MAPPED IDENT (STRING_LITERAL | QUOTED_STRING_LITERAL))
	;	
	
timePeriod
	: 	^( t=TIME_PERIOD timePeriodDef { leaveNode($t); })
	;
	
timePeriodDef
	: 	dayPart (hourPart)? (minutePart)? (secondPart)? (millisecondPart)?
	|	hourPart (minutePart)? (secondPart)? (millisecondPart)?
	|	minutePart (secondPart)? (millisecondPart)?
	|	secondPart (millisecondPart)?
	|	millisecondPart
	;
	
dayPart
	:	^( DAY_PART valueExpr )
	;

hourPart
	:	^( HOUR_PART valueExpr )
	;

minutePart
	:	^( MINUTE_PART valueExpr )
	;

secondPart
	:	^( SECOND_PART valueExpr )
	;

millisecondPart
	:	^( MILLISECOND_PART valueExpr )
	;

substitution
	:	s=SUBSTITUTION { leaveNode($s); }
	;

constant[boolean isLeaveNode]
	:	c=INT_TYPE { if ($isLeaveNode) leaveNode($c); }
	|	c=LONG_TYPE { if ($isLeaveNode) leaveNode($c); }
	|	c=FLOAT_TYPE { if ($isLeaveNode) leaveNode($c); }
	|	c=DOUBLE_TYPE { if ($isLeaveNode) leaveNode($c); }
    	|   	c=STRING_TYPE { if ($isLeaveNode) leaveNode($c); }
    	|   	c=BOOL_TYPE { if ($isLeaveNode) leaveNode($c); }
    	|	c=NULL_TYPE { if ($isLeaveNode) leaveNode($c); }
	;

number
	:	INT_TYPE
	|	LONG_TYPE
	|	FLOAT_TYPE
	|	DOUBLE_TYPE
    ;	
