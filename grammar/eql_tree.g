header
{
using System.Collections;
using org.apache.commons.logging;
}
options
{
    language = "CSharp";
	namespace = "net.esper.eql.generated";
}

class EQLBaseWalker extends TreeParser;

options
{
	k = 1;                   	// lookahead is 2 tokens
	importVocab=Eql;        	// import definitions
	exportVocab=EqlEval;     	// Call the resulting definitions something new
	buildAST=true;
    defaultErrorHandler=false;
}

tokens
{
	BOGUS;          // Used for error state detection, etc.
}

// -- Declarations --
// NOTE: The real implementations are in the subclass.
{
	private static Log log = LogFactory.GetLog(typeof(EQLBaseWalker));

	// For pattern processing within EQL and for create pattern
	protected virtual void setIsPatternWalk(bool isPatternWalk) {}
	protected virtual void endPattern() {}
	
	protected virtual void pushStmtContext() {}
	protected virtual void leaveNode(AST node) {}
	protected virtual void end() {}
}

//----------------------------------------------------------------------------
// EQL expression
//----------------------------------------------------------------------------
startEQLExpressionRule
	:	(insertIntoExpr)?
		selectClause 
		fromClause
		(whereClause)?
		(groupByClause)?
		(havingClause)?
		(outputLimitExpr)?
		(orderByClause)?
		{ end(); }
	;
	
insertIntoExpr
	:	#(i:INSERTINTO_EXPR (ISTREAM | RSTREAM)? IDENT (insertIntoExprCol)? { leaveNode(#i); } )
	;
	
insertIntoExprCol
	:	#(i:INSERTINTO_EXPRCOL IDENT (IDENT)* )
	;

selectClause
	:	#(s:SELECTION_EXPR (RSTREAM | ISTREAM)? selectionList { leaveNode(#s); })
	;

fromClause
	:	streamExpression (streamExpression (outerJoin)* )*
	;
	
selectionList
	:	selectionListElement (selectionListElement)*
	;
	
selectionListElement
	:	w:WILDCARD_SELECT { leaveNode(#w); }
	|	#(s:SELECTION_ELEMENT_EXPR valueExpr (IDENT)? { leaveNode(#s); } )
	;
		
outerJoin
	:	outerJoinIdent
	;

outerJoinIdent
	:	#(tl:LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr { leaveNode(#tl); } )
	|	#(tr:RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr { leaveNode(#tr); } )
	|	#(tf:FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr { leaveNode(#tf); } )
	;

streamExpression
	:	#(v:STREAM_EXPR (eventFilterExpr | patternInclusionExpression | databaseJoinExpression) (viewListExpr)? (IDENT)? { leaveNode(#v); } )
	;

patternInclusionExpression
	:	#(p:PATTERN_INCL_EXPR { setIsPatternWalk(true); } exprChoice { setIsPatternWalk(false); leaveNode(#p); } )
	;
	
databaseJoinExpression
	:	#(d:DATABASE_JOIN_EXPR IDENT (STRING_LITERAL | QUOTED_STRING_LITERAL))
	;
	
viewListExpr
	:	viewExpr (viewExpr)*
	;
	
viewExpr
	:	#(n:VIEW_EXPR IDENT IDENT (parameter)* { leaveNode(#n); } )
	;
	
whereClause
	:	#(n:WHERE_EXPR valueExpr { leaveNode(#n); } )
	;

groupByClause
	:	#(g:GROUP_BY_EXPR valueExpr (valueExpr)* ) { leaveNode(#g); }
	;

orderByClause
	:	#(s:ORDER_BY_EXPR orderByElement (orderByElement)* )
	;
	
orderByElement
	: 	#(e:ORDER_ELEMENT_EXPR valueExpr (ASC|DESC)? { leaveNode(#e); } )
	;

havingClause
	:	#(n:HAVING_EXPR valueExpr { leaveNode(#n); } )
	;

outputLimitExpr
	:	#(e:EVENT_LIMIT_EXPR (ALL|FIRST|LAST)? number { leaveNode(#e); } ) 
	|   #(sec:SEC_LIMIT_EXPR (ALL|FIRST|LAST)? number { leaveNode(#sec); } )
	|   #(min:MIN_LIMIT_EXPR (ALL|FIRST|LAST)? number { leaveNode(#min); } )
	;

relationalExpr
	: 	#(LT_ valueExpr valueExpr)
	| 	#(GT valueExpr valueExpr)
	| 	#(LE valueExpr valueExpr)
	|	#(GE valueExpr valueExpr)
	;

evalExprChoice
	:	#(jo:EVAL_OR_EXPR valueExpr valueExpr (valueExpr)* { leaveNode(#jo); } )
	|	#(ja:EVAL_AND_EXPR valueExpr valueExpr (valueExpr)* { leaveNode(#ja); } )
	|	#(je:EVAL_EQUALS_EXPR valueExpr valueExpr { leaveNode(#je); } )
	|	#(jne:EVAL_NOTEQUALS_EXPR valueExpr valueExpr { leaveNode(#jne); } )
	|	#(n:NOT_EXPR valueExpr { leaveNode(#n); } )
	|	r:relationalExpr { leaveNode(#r); }
	;
	
valueExpr
	: 	c:constant { leaveNode(#c); }
	| 	a:arithmeticExpr { leaveNode(#a); }
	| 	eventPropertyExpr
	|   evalExprChoice
	|	f:builtinFunc { leaveNode(#f); }
	|   l:libFunc { leaveNode(#l); }
	|	cs:caseExpr { leaveNode(#cs); }
	|	in:inExpr { leaveNode(#in); }
	|	b:betweenExpr { leaveNode(#b); }
	|	li:likeExpr { leaveNode(#li); }
	|	r:regExpExpr { leaveNode(#r); }
	|	arr:arrayExpr { leaveNode(#arr); }
	|	subin:subSelectInExpr {leaveNode(#subin);}
	| 	subrow:subSelectRowExpr 
	| 	subexists:subSelectExistsExpr
	;

subSelectRowExpr
	:	{pushStmtContext();} #(SUBSELECT_EXPR subQueryExpr) {leaveNode(#subSelectRowExpr);}
	;

subSelectExistsExpr
	:	{pushStmtContext();} #(EXISTS_SUBSELECT_EXPR subQueryExpr) {leaveNode(#subSelectExistsExpr);}
	;
	
subSelectInExpr
	: 	#(IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr)
	| 	#(NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr)
	;

subSelectInQueryExpr
	:	{pushStmtContext();} #(IN_SUBSELECT_QUERY_EXPR subQueryExpr) {leaveNode(#subSelectInQueryExpr);}
	;
	
subQueryExpr 
	:	selectionListElement subSelectFilterExpr (viewExpr)* (IDENT)? (whereClause)?
	;
	
subSelectFilterExpr
	:	#(v:STREAM_EXPR eventFilterExpr (viewListExpr)? (IDENT)? { leaveNode(#v); } )
	;
	
caseExpr
	: #(CASE (valueExpr)*)
	| #(CASE2 (valueExpr)*)
	;
	
inExpr
	: #(IN_SET valueExpr (LPAREN|LBRACK) valueExpr (valueExpr)* (RPAREN|RBRACK))
	| #(NOT_IN_SET valueExpr (LPAREN|LBRACK) valueExpr (valueExpr)* (RPAREN|RBRACK))
	| #(IN_RANGE valueExpr (LPAREN|LBRACK) valueExpr valueExpr (RPAREN|RBRACK))
	| #(NOT_IN_RANGE valueExpr (LPAREN|LBRACK) valueExpr valueExpr (RPAREN|RBRACK))
	;
		
betweenExpr
	: #(BETWEEN valueExpr valueExpr valueExpr)
	| #(NOT_BETWEEN valueExpr valueExpr (valueExpr)*)
	;
	
likeExpr
	: #(LIKE valueExpr valueExpr (valueExpr)?)
	| #(NOT_LIKE valueExpr valueExpr (valueExpr)?)
	;

regExpExpr
	: #(REGEXP valueExpr valueExpr)
	| #(NOT_REGEXP valueExpr valueExpr)
	;
	
builtinFunc
	: 	#(SUM (DISTINCT)? valueExpr)
	|	#(AVG (DISTINCT)? valueExpr)
	|	#(COUNT ((DISTINCT)? valueExpr)? )
	|	#(MEDIAN (DISTINCT)? valueExpr)
	|	#(STDDEV (DISTINCT)? valueExpr)
	|	#(AVEDEV (DISTINCT)? valueExpr)
	| 	#(COALESCE valueExpr valueExpr (valueExpr)* )
	| 	#(PREVIOUS valueExpr eventPropertyExpr)
	| 	#(PRIOR c:NUM_INT eventPropertyExpr) {leaveNode(#c);}
	;
	
arrayExpr
	:	#(ARRAY_EXPR (valueExpr)*)
	;
	
arithmeticExpr
	: 	#(PLUS valueExpr valueExpr)
	| 	#(MINUS valueExpr valueExpr)
	| 	#(DIV valueExpr valueExpr)
	|	#(STAR valueExpr valueExpr)
	| 	#(MOD valueExpr valueExpr)
	|	#(BAND valueExpr valueExpr)	
	|	#(BOR valueExpr valueExpr)	
	|	#(BXOR valueExpr valueExpr)		
	| 	#(CONCAT valueExpr valueExpr (valueExpr)*)
	;
	
libFunc
	:  #(LIB_FUNCTION (CLASS_IDENT)? IDENT (DISTINCT)? (valueExpr)*)
	;
	
//----------------------------------------------------------------------------
// pattern expression
//----------------------------------------------------------------------------
startPatternExpressionRule
	:	{setIsPatternWalk(true);} exprChoice { endPattern(); end(); }
	;

exprChoice
	: 	atomicExpr
	|	patternOp
	| 	#( a:EVERY_EXPR exprChoice { leaveNode(#a); } )
	| 	#( n:NOT_EXPR exprChoice { leaveNode(#n); } )
	| 	#( g:GUARD_EXPR exprChoice IDENT IDENT (constant | time_period)* { leaveNode(#g); } )
	;
	
patternOp
	:	#( f:FOLLOWED_BY_EXPR exprChoice exprChoice (exprChoice)* { leaveNode(#f); } )
	| 	#( o:OR_EXPR exprChoice exprChoice (exprChoice)* { leaveNode(#o); } )
	| 	#( a:AND_EXPR exprChoice exprChoice (exprChoice)* { leaveNode(#a); } )	
	;
	
atomicExpr
	:	eventFilterExpr
	|   #( ac:OBSERVER_EXPR IDENT IDENT (parameter)* { leaveNode(#ac); } )
	;

eventFilterExpr
	:	#( f:EVENT_FILTER_EXPR (EVENT_FILTER_NAME_TAG)? CLASS_IDENT (valueExpr)* { leaveNode(#f); } )
	;
	
filterParam
	:	#(EVENT_FILTER_PARAM valueExpr (valueExpr)*)
	;
	
filterParamComparator
	:	#(EQUALS filterAtom)
	|	#(NOT_EQUAL filterAtom)
	|	#(LT_ filterAtom)
	|	#(LE filterAtom)
	|	#(GT filterAtom)
	|	#(GE filterAtom)
	|	#(EVENT_FILTER_RANGE (LPAREN|LBRACK) (constant|filterIdentifier) (constant|filterIdentifier) (RPAREN|RBRACK))
	|	#(EVENT_FILTER_NOT_RANGE (LPAREN|LBRACK) (constant|filterIdentifier) (constant|filterIdentifier) (RPAREN|RBRACK))
	|	#(EVENT_FILTER_IN (LPAREN|LBRACK) (constant|filterIdentifier) (constant|filterIdentifier)* (RPAREN|RBRACK))
	|	#(EVENT_FILTER_NOT_IN (LPAREN|LBRACK) (constant|filterIdentifier) (constant|filterIdentifier)* (RPAREN|RBRACK))
	|	#(EVENT_FILTER_BETWEEN (constant|filterIdentifier) (constant|filterIdentifier))
	|	#(EVENT_FILTER_NOT_BETWEEN (constant|filterIdentifier) (constant|filterIdentifier))
	;
	
filterAtom
	:	constant
	|	filterIdentifier;
	
filterIdentifier
	:	#(EVENT_FILTER_IDENT IDENT eventPropertyExpr)
	;	
	
eventPropertyExpr
	:	#(p:EVENT_PROP_EXPR eventPropertyAtomic (eventPropertyAtomic)* ) { leaveNode(#p); }
	;
	
eventPropertyAtomic
	:	#(EVENT_PROP_SIMPLE IDENT)
	|	#(EVENT_PROP_INDEXED IDENT NUM_INT)
	|	#(EVENT_PROP_MAPPED IDENT (STRING_LITERAL | QUOTED_STRING_LITERAL))
	;	
	
//----------------------------------------------------------------------------
// Parameter set
//----------------------------------------------------------------------------
parameter
	: 	singleParameter
	| 	#( NUMERIC_PARAM_LIST (numericParameterList)+ )
	|	#( ARRAY_PARAM_LIST (constant)*)
	;

singleParameter
	: 	STAR
	| 	constant
	| 	#( NUMERIC_PARAM_RANGE NUM_INT NUM_INT)
	| 	#( NUMERIC_PARAM_FREQUENCY NUM_INT)
	| 	time_period
	;

numericParameterList
	: 	NUM_INT
	| 	#( NUMERIC_PARAM_RANGE NUM_INT NUM_INT)
	| 	#( NUMERIC_PARAM_FREQUENCE NUM_INT)
	;

time_period
	: 	#( TIME_PERIOD timePeriodDef )
	;
	
timePeriodDef
	: 	dayPart (hourPart)? (minutePart)? (secondPart)? (millisecondPart)?
	|	hourPart (minutePart)? (secondPart)? (millisecondPart)?
	|	minutePart (secondPart)? (millisecondPart)?
	|	secondPart (millisecondPart)?
	|	millisecondPart
	;
	
dayPart
	:	#( DAY_PART number)
	;

hourPart
	:	#( HOUR_PART number)
	;

minutePart
	:	#( MINUTE_PART number)
	;

secondPart
	:	#( SECOND_PART number)
	;

millisecondPart
	:	#( MILLISECOND_PART number)
	;

constant
	:	INT_TYPE
	|	LONG_TYPE
	|	FLOAT_TYPE
	|	DOUBLE_TYPE
    |   STRING_TYPE
    |   BOOL_TYPE
    |	NULL_TYPE
	;

number
	:	INT_TYPE
	|	LONG_TYPE
	|	FLOAT_TYPE
	|	DOUBLE_TYPE
    ;	
