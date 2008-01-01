grammar EsperEPL;

options
{
	output = AST;
	ASTLabelType=CommonTree;
	backtrack=true;
	memoize=true;
}

// language tokens
tokens
{
	CREATE='create';
	WINDOW='window';
	IN_SET='in';
	BETWEEN='between';
	LIKE='like';
	REGEXP='regexp';
	ESCAPE='escape';
	OR_EXPR='or';
	AND_EXPR='and';
	NOT_EXPR='not';
    	EVERY_EXPR='every';
	WHERE='where';
	AS='as';	
	SUM='sum';
	AVG='avg';
	MAX='max';
	MIN='min';
	COALESCE='coalesce';
	MEDIAN='median';
	STDDEV='stddev';
	AVEDEV='avedev';
	COUNT='count';
	SELECT='select';
	CASE='case';
   	CASE2;
	ELSE='else';
	WHEN='when';
	THEN='then';
	END='end';
	FROM='from';
	OUTER='outer';
	JOIN='join';
	LEFT='left';
	RIGHT='right';
	FULL='full';
	ON='on';	
	IS='is';
	BY='by';
	GROUP='group';
	HAVING='having';
	DISTINCT='distinct';
	ALL='all';
	OUTPUT='output';
	EVENTS='events';
	SECONDS='seconds';
	MINUTES='minutes';
	FIRST='first';
	LAST='last';
	INSERT='insert';
	INTO='into';
	ORDER='order';
	ASC='asc';
	DESC='desc';
	RSTREAM='rstream';
	ISTREAM='istream';
	UNIDIRECTIONAL='unidirectional';
	PATTERN='pattern';
	SQL='sql';
	METADATASQL='metadatasql';
	PREVIOUS='prev';
	PRIOR='prior';
	EXISTS='exists';
	WEEKDAY='weekday';
	LW='lastweekday';
	INSTANCEOF='instanceof';
	CAST='cast';
	CURRENT_TIMESTAMP='current_timestamp';
	DELETE='delete';
	SNAPSHOT='snapshot';
	SET='set';
	VARIABLE='variable';
	
   	NUMERIC_PARAM_RANGE;
   	NUMERIC_PARAM_LIST;
   	NUMERIC_PARAM_FREQUENCY;   	
   	FOLLOWED_BY_EXPR;
   	ARRAY_PARAM_LIST;
   	EVENT_FILTER_EXPR;
   	EVENT_FILTER_NAME_TAG;
   	EVENT_FILTER_IDENT;
   	EVENT_FILTER_PARAM;
   	EVENT_FILTER_RANGE;
   	EVENT_FILTER_NOT_RANGE;
   	EVENT_FILTER_IN;
   	EVENT_FILTER_NOT_IN;
   	EVENT_FILTER_BETWEEN;
   	EVENT_FILTER_NOT_BETWEEN;
   	CLASS_IDENT;
   	GUARD_EXPR;
   	OBSERVER_EXPR;
   	VIEW_EXPR;
   	PATTERN_INCL_EXPR;
   	DATABASE_JOIN_EXPR;
   	WHERE_EXPR;
   	HAVING_EXPR;
	EVAL_BITWISE_EXPR;
   	EVAL_AND_EXPR;
   	EVAL_OR_EXPR;
   	EVAL_EQUALS_EXPR;
   	EVAL_NOTEQUALS_EXPR;
   	EVAL_IDENT;
   	SELECTION_EXPR;
   	SELECTION_ELEMENT_EXPR;
   	SELECTION_STREAM;
   	STREAM_EXPR;
   	OUTERJOIN_EXPR;
   	LEFT_OUTERJOIN_EXPR;
   	RIGHT_OUTERJOIN_EXPR;
   	FULL_OUTERJOIN_EXPR;
   	GROUP_BY_EXPR;
   	ORDER_BY_EXPR;
   	ORDER_ELEMENT_EXPR;
   	EVENT_PROP_EXPR;
   	EVENT_PROP_SIMPLE;
   	EVENT_PROP_MAPPED;
   	EVENT_PROP_INDEXED;
   	EVENT_PROP_DYNAMIC_SIMPLE;
   	EVENT_PROP_DYNAMIC_INDEXED;
   	EVENT_PROP_DYNAMIC_MAPPED;
   	EVENT_LIMIT_EXPR;
	SEC_LIMIT_EXPR;
	MIN_LIMIT_EXPR;
	INSERTINTO_EXPR;
	INSERTINTO_EXPRCOL;
	CONCAT;	
	LIB_FUNCTION;
	UNARY_MINUS;
	TIME_PERIOD;
	ARRAY_EXPR;
	DAY_PART;
	HOUR_PART;
	MINUTE_PART;
	SECOND_PART;
	MILLISECOND_PART;
	NOT_IN_SET;
	NOT_BETWEEN;
	NOT_LIKE;
	NOT_REGEXP;
   	DBSELECT_EXPR;
   	DBFROM_CLAUSE;
   	DBWHERE_CLAUSE;
   	WILDCARD_SELECT;
	INSERTINTO_STREAM_NAME;
	IN_RANGE;
	NOT_IN_RANGE;
	SUBSELECT_EXPR;
	EXISTS_SUBSELECT_EXPR;
	IN_SUBSELECT_EXPR;
	NOT_IN_SUBSELECT_EXPR;
	IN_SUBSELECT_QUERY_EXPR;
	LAST_OPERATOR;
	WEEKDAY_OPERATOR;
	SUBSTITUTION;
	CAST_EXPR;
	CREATE_WINDOW_EXPR;
	CREATE_WINDOW_SELECT_EXPR;
	ON_EXPR;
	ON_DELETE_EXPR;
	ON_SELECT_EXPR;
	ON_EXPR_FROM;
	ON_SET_EXPR;
	CREATE_VARIABLE_EXPR;
	METHOD_JOIN_EXPR;
	
   	INT_TYPE;
   	LONG_TYPE;
   	FLOAT_TYPE;
   	DOUBLE_TYPE;
   	STRING_TYPE;
   	BOOL_TYPE;
   	NULL_TYPE;
   	NUM_DOUBLE;
   	
   	EPL_EXPR;
}

@header {
  package net.esper.eql.generated;
}
@lexer::header {
  package net.esper.eql.generated;
}
@lexer::members {
  protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);  
  }

  public void recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow) throws RecognitionException {
    throw e;
  }
}
@rulecatch {
  catch (RecognitionException recoge) {
    throw recoge;
  }
}

startPatternExpressionRule 
	:	patternExpression
		EOF!
	;
	
startEPLExpressionRule 
	:	eplExpression
		EOF
		-> ^(EPL_EXPR eplExpression) 
	;

startEventPropertyRule 
	:	eventProperty
		EOF!
	;

number
    :   ni=NUM_INT -> ^(INT_TYPE[$ni])
    |   nl=NUM_LONG -> ^(LONG_TYPE[$nl])
    |   nf=NUM_FLOAT -> ^(FLOAT_TYPE[$nf])
    |   nd=NUM_DOUBLE -> ^(DOUBLE_TYPE[$nd])
    ;

substitution
	: q=QUESTION -> SUBSTITUTION[$q]
	;
	
constant
@init {
	Token firstToken = input.LT(1);
	Token prevToken = input.LT(-1);
}
	:  (m=MINUS | p=PLUS)? n=number
		-> {$m != null}? ^({adaptor.create(firstToken)} number)
		-> ^({adaptor.create(firstToken)} number)
	|   stringconstant
    	|   t='true' -> ^(BOOL_TYPE[$t])
    	|   f='false' -> ^(BOOL_TYPE[$f])
    	|   nu='null' -> ^(NULL_TYPE[$nu])
	;

stringconstant
	:   sl=STRING_LITERAL -> ^(STRING_TYPE[$sl])
	|   qsl=QUOTED_STRING_LITERAL -> ^(STRING_TYPE[$qsl])
	;

//----------------------------------------------------------------------------
// EPL expression
//----------------------------------------------------------------------------
eplExpression 
	:	selectExpr
	|	createWindowExpr
	|	createVariableExpr
	|	onExpr
	;
	
selectExpr
	:	(INSERT! insertIntoExpr)?
		SELECT! selectClause
		FROM! streamExpression (regularJoin | outerJoinList)
		(WHERE! whereClause)?
		(GROUP! BY! groupByListExpr)?
		(HAVING! havingClause)?
		(OUTPUT! outputLimit)?
		(ORDER! BY! orderByListExpr)?
	;
	
onExpr 
	:	ON (eventFilterExpression | patternInclusionExpression) (AS i=IDENT | i=IDENT)? 
		(onDeleteExpr | onSelectExpr | onSetExpr)
		-> ^(ON_EXPR eventFilterExpression? patternInclusionExpression? $i? onDeleteExpr? onSelectExpr? onSetExpr?)
	;
	
onSelectExpr	
	:	(INSERT insertIntoExpr)?
		SELECT selectionList
		onExprFrom
		(WHERE whereClause)?		
		(GROUP BY groupByListExpr)?
		(HAVING havingClause)?
		(ORDER BY orderByListExpr)?
		-> ^(ON_SELECT_EXPR insertIntoExpr? selectionList onExprFrom whereClause? groupByListExpr? havingClause? orderByListExpr?)
	;
	
onDeleteExpr	
	:	DELETE!
		onExprFrom
		(WHERE whereClause)?		
//		-> ^(ON_DELETE_EXPR onExprFrom whereClause?)
	;
	
onSetExpr
	:	SET onSetAssignment (COMMA onSetAssignment)*
		-> ^(ON_SET_EXPR onSetAssignment+)
	;
	
onSetAssignment
	:	IDENT EQUALS! expression
	;
		
onExprFrom
	:	FROM n=IDENT (AS i=IDENT | i=IDENT)?
		-> ^(ON_EXPR_FROM $n $i?)
	;

createWindowExpr
	:	CREATE WINDOW i=IDENT (DOT viewExpression (DOT viewExpression)*)? AS (SELECT createSelectionList FROM)? classIdentifier
		-> ^(CREATE_WINDOW_EXPR $i viewExpression* createSelectionList? classIdentifier)
	;
		
createVariableExpr
	:	CREATE VARIABLE t=IDENT n=IDENT (EQUALS expression)?
		-> ^(CREATE_VARIABLE_EXPR $t $n expression?)
	;

createSelectionList 	
	:	createSelectionListElement (COMMA createSelectionListElement)*
		-> ^(CREATE_WINDOW_SELECT_EXPR createSelectionListElement+)
	;

createSelectionListElement
	:   	s=STAR
		-> WILDCARD_SELECT[$s]
	|	eventProperty (AS i=IDENT)?
		-> ^(SELECTION_ELEMENT_EXPR eventProperty $i?)
	;

insertIntoExpr
	:	(s=ISTREAM | s=RSTREAM)? INTO i=IDENT (insertIntoColumnList)?
		-> ^(INSERTINTO_EXPR $s? $i insertIntoColumnList?)
	;
		
insertIntoColumnList
	: 	LPAREN c=IDENT (COMMA i=IDENT)* RPAREN
		-> ^(INSERTINTO_EXPRCOL $c $i*)
	;
	
regularJoin
	:	(COMMA! streamExpression)*
	;
	
outerJoinList
	:	outerJoin (outerJoin)*
	;

outerJoin
	:	(tl=LEFT|tr=RIGHT|tf=FULL) OUTER JOIN streamExpression outerJoinIdent
		-> {$tl != null}? ^(LEFT_OUTERJOIN_EXPR streamExpression outerJoinIdent)
		-> {$tr != null}? ^(RIGHT_OUTERJOIN_EXPR streamExpression outerJoinIdent)
		-> ^(FULL_OUTERJOIN_EXPR streamExpression outerJoinIdent)
	;

outerJoinIdent
	:	ON outerJoinIdentPair (AND_EXPR outerJoinIdentPair)*
		-> ^(OUTERJOIN_EXPR outerJoinIdentPair+)
	;
	
outerJoinIdentPair 
	:	eventProperty EQUALS! eventProperty
	;

whereClause
	:	evalOrExpression
		-> ^(WHERE_EXPR evalOrExpression*)
	;
	
selectClause
	:	(s=RSTREAM | s=ISTREAM)? selectionList
		-> ^(SELECTION_EXPR $s? selectionList)
	;

selectionList 	
	:	selectionListElement (COMMA! selectionListElement)*
	;

selectionListElement
	:   	s=STAR -> WILDCARD_SELECT[$s]
	|	(streamSelector) => streamSelector
	|	expression (AS i=IDENT)?
		-> ^(SELECTION_ELEMENT_EXPR expression $i?)
	;
	
streamSelector
	:	s=IDENT DOT STAR (AS i=IDENT)?
		-> ^(SELECTION_STREAM $s $i?)
	;
	
streamExpression
	:	(eventFilterExpression | patternInclusionExpression | databaseJoinExpression | methodJoinExpression)
		(DOT viewExpression (DOT viewExpression)*)? (AS i=IDENT | i=IDENT)? (u=UNIDIRECTIONAL)?
		-> ^(STREAM_EXPR eventFilterExpression? patternInclusionExpression? databaseJoinExpression? methodJoinExpression?
		viewExpression* $i? $u?)
	;
			
patternInclusionExpression
	:	PATTERN LBRACK patternExpression RBRACK
		-> ^(PATTERN_INCL_EXPR patternExpression)
	;
	
databaseJoinExpression
	:	SQL COLON i=IDENT LBRACK (s=STRING_LITERAL | s=QUOTED_STRING_LITERAL) (METADATASQL (s2=STRING_LITERAL | s2=QUOTED_STRING_LITERAL))? RBRACK
		-> ^(DATABASE_JOIN_EXPR $i $s $s2?)
	;	
	
methodJoinExpression
    	:   	i=IDENT COLON classIdentifier (LPAREN expressionList? RPAREN)?
       		-> ^(METHOD_JOIN_EXPR $i classIdentifier expressionList?)
    	;

viewExpression
	:	ns=IDENT COLON nm=IDENT LPAREN parameterSet? RPAREN
		-> ^(VIEW_EXPR $ns $nm parameterSet?)
	;

groupByListExpr
	:	expression (COMMA expression)*
		-> ^(GROUP_BY_EXPR expression+)
	;

orderByListExpr
	:	orderByListElement (COMMA orderByListElement)*
		-> ^(ORDER_BY_EXPR orderByListElement+) 
	;

orderByListElement
	:	expression (d=ASC|d=DESC)?
		-> ^(ORDER_ELEMENT_EXPR expression $d?)
	;

havingClause
	:	evalOrExpression
		-> ^(HAVING_EXPR evalOrExpression) 
	;

outputLimit
	:   (k=ALL|k=FIRST|k=LAST|k=SNAPSHOT)? EVERY_EXPR (number | i=IDENT) (e=EVENTS|sec=SECONDS|min=MINUTES)
	    -> {$e != null}? ^(EVENT_LIMIT_EXPR $k? number? $i?)
	    -> {$sec != null}? ^(SEC_LIMIT_EXPR $k? number? $i?)
	    -> ^(MIN_LIMIT_EXPR $k? number? $i?)		
	;	

// Main expression rule
expression
	: caseExpression
	;

caseExpression
	: CASE^ whenClause+ elseClause? END!
	| CASE expression whenClause+ elseClause? END
	  -> ^(CASE2 expression whenClause+ elseClause?)
	| evalOrExpression
	;

whenClause
	: (WHEN! expression THEN! expression)
	;

elseClause
	: (ELSE! expression)
	;

evalOrExpression
	: evalAndExpression (op=OR_EXPR evalAndExpression)*
	 -> {$op != null}? ^(EVAL_OR_EXPR evalAndExpression*)
	 ->
	;

evalAndExpression
	: bitWiseExpression (op=AND_EXPR bitWiseExpression)*
	 -> {$op != null}? ^(EVAL_AND_EXPR bitWiseExpression+)
	 ->
	;

bitWiseExpression
	: negatedExpression ( (BAND^|BOR^|BXOR^) negatedExpression)*
	;		

negatedExpression
	: evalEqualsExpression 
	| NOT_EXPR^ evalEqualsExpression
	;		

evalEqualsExpression
	: evalRelationalExpression ( 
	    (eq=EQUALS
    	      |  is=IS
	      |  isnot=IS NOT_EXPR
	      |  sqlne=SQL_NE
	      |  ne=NOT_EQUAL
	     ) evalRelationalExpression -> ^(EVAL_EQUALS_EXPR evalRelationalExpression*))*	     
	    -> {$eq != null || $is != null}? ^(EVAL_EQUALS_EXPR evalRelationalExpression*)
	    -> {$isnot != null || $sqlne != null || $ne != null}? ^(EVAL_NOTEQUALS_EXPR evalRelationalExpression*)
	    -> ^(EVAL_NOTEQUALS_EXPR evalRelationalExpression*)
	;
	
evalRelationalExpression
	: concatenationExpr 
		( 
			( 
			  ( 
			    (r=LT|r=GT|r=LE|r=GE) concatenationExpr
			    -> ^({adaptor.create($r)} concatenationExpr+)
			  )* 
			  
			)  
			| (n=NOT_EXPR)? 
			(
				// Represent the optional NOT prefix using the token type by
				// testing 'n' and setting the token type accordingly.
				(i=IN_SET
					  (l=LPAREN | l=LBRACK) expression	// brackets are for inclusive/exclusive
						(
							( col=COLON (expression) )		// range
							|
							( (COMMA expression)* )		// list of values
						)
					  (r=RPAREN | r=RBRACK)	
					)
				    -> {$col == null && $n == null}? ^(IN_SET $l expression+ $r)
				    -> {$col == null && $n != null}? ^(NOT_IN_SET $l expression+ $r)
				    -> {$col != null && $n == null}? ^(IN_RANGE $l expression+ $r)
				    -> ^(NOT_IN_RANGE $l expression+ $r)
				| IN_SET inSubSelectQuery
				    -> {$n == null}? ^(IN_SUBSELECT_EXPR inSubSelectQuery)
				    -> ^(NOT_IN_SUBSELECT_EXPR inSubSelectQuery)
				| BETWEEN betweenList
				    -> {$n == null}? ^(BETWEEN betweenList)
				    -> ^(NOT_BETWEEN betweenList)				
				| LIKE concatenationExpr (ESCAPE stringconstant)?
				    -> {$n == null}? ^(LIKE concatenationExpr* stringconstant?)
				    -> ^(NOT_LIKE concatenationExpr* stringconstant?)
				| REGEXP concatenationExpr
				    -> {$n == null}? ^(REGEXP concatenationExpr)
				    -> ^(NOT_REGEXP concatenationExpr)				
			)	
		)
	;
	
inSubSelectQuery
	: subQueryExpr
	  -> ^(IN_SUBSELECT_QUERY_EXPR subQueryExpr)
	;
			
concatenationExpr
	: additiveExpression (LOR additiveExpression (LOR additiveExpression)* -> ^(CONCAT additiveExpression+) )?
	;

additiveExpression
	: multiplyExpression ( (PLUS^|MINUS^) multiplyExpression )*
	;

multiplyExpression
	: unaryExpression ( (STAR^|DIV^|MOD^) unaryExpression )*
	;
	
unaryExpression
	: MINUS eventProperty -> ^(UNARY_MINUS eventProperty)
	| constant
	| substitution
	| LPAREN! expression RPAREN!
	| eventPropertyOrLibFunction
	| (builtinFunc) => (builtinFunc)
	| arrayExpression
	| subSelectExpression
	| existsSubSelectExpression
	;
	    
subSelectExpression 
	:	subQueryExpr
		-> ^(SUBSELECT_EXPR subQueryExpr)
	;

existsSubSelectExpression 
	:	EXISTS subQueryExpr
		-> ^(EXISTS_SUBSELECT_EXPR subQueryExpr)
	;

subQueryExpr 
	:	LPAREN! 
		SELECT! selectionListElement
	    FROM! subSelectFilterExpr
	    (WHERE! whereClause)?
	    RPAREN!
	;
	
subSelectFilterExpr
	:	eventFilterExpression
		(DOT viewExpression (DOT viewExpression)*)? (AS i=IDENT | i=IDENT)?
		-> ^(STREAM_EXPR eventFilterExpression viewExpression* $i?)
	;
		
arrayExpression
	: LCURLY (expression (COMMA expression)* )? RCURLY
	  -> ^(ARRAY_EXPR expression+)
	;

builtinFunc
	: SUM^ LPAREN! (ALL! | DISTINCT)? expression RPAREN!
	| AVG^ LPAREN! (ALL! | DISTINCT)? expression RPAREN!
	| COUNT^ LPAREN!
		(
			((ALL! | DISTINCT)? expression)
		|
			(STAR!) 
		)
		RPAREN!
	| MEDIAN^ LPAREN! (ALL! | DISTINCT)? expression RPAREN!
	| STDDEV^ LPAREN! (ALL! | DISTINCT)? expression RPAREN!
	| AVEDEV^ LPAREN! (ALL! | DISTINCT)? expression RPAREN!
	| COALESCE^ LPAREN! expression COMMA! expression (COMMA! expression)* RPAREN!
	| PREVIOUS^ LPAREN! expression COMMA! eventProperty RPAREN!
	| PRIOR^ LPAREN! NUM_INT COMMA! eventProperty RPAREN!
	// MIN and MAX can also be "Math.min" static function and "min(price)" aggregation function and "min(a, b, c...)" built-in function
	// therefore handled in code via libFunction as below
	| INSTANCEOF^ LPAREN! expression COMMA! classIdentifier (COMMA! classIdentifier)* RPAREN!
	| CAST^ LPAREN! expression (COMMA! | AS!) classIdentifier RPAREN!
	| EXISTS^ LPAREN! eventProperty RPAREN!
	| CURRENT_TIMESTAMP^ (LPAREN! RPAREN!)?
	;
	
maxFunc
	: (MAX^ | MIN^) LPAREN! expression (COMMA! expression (COMMA! expression)* )? RPAREN!
	;
	
eventPropertyOrLibFunction
	: (eventProperty) => eventProperty
	| libFunction
	;
	
libFunction
	: (classIdentifierNonGreedy DOT)? funcIdent LPAREN (libFunctionArgs)? RPAREN
	  -> ^(LIB_FUNCTION classIdentifierNonGreedy? funcIdent libFunctionArgs?)
	;	
	
funcIdent
	: IDENT
	| max=MAX -> IDENT[$max]
	| min=MIN -> IDENT[$min]
	;
	
libFunctionArgs
	: (ALL! | DISTINCT)? expression (COMMA! expression)*
	;
	
betweenList
	: concatenationExpr AND_EXPR! concatenationExpr
	;

//----------------------------------------------------------------------------
// Pattern event expressions / event pattern operators
//   Operators are: followed-by (->), or, and, not, every, where
//   Lowest precedence is listed first, order is (lowest to highest):  ->, or, and, not/every, within.
//   On the atomic level an expression has filters, and observer-statements.
//----------------------------------------------------------------------------
patternExpression
	:	followedByExpression
	;
	
followedByExpression
	:	orExpression (FOLLOWED_BY orExpression -> ^(FOLLOWED_BY_EXPR orExpression+) )*		
	;
	
orExpression
	:	andExpression (OR_EXPR andExpression -> ^(OR_EXPR andExpression+) )*
	;

andExpression
	:	qualifyExpression (AND_EXPR qualifyExpression -> ^(AND_EXPR qualifyExpression+) )*
	;

qualifyExpression
	:	(EVERY_EXPR^ | NOT_EXPR^)?
		guardPostFix
	;
	
guardPostFix
	:	(atomicExpression | LPAREN patternExpression RPAREN)
		(WHERE guardExpression -> ^(GUARD_EXPR atomicExpression? patternExpression? guardExpression) )?
	;

atomicExpression
	:	observerExpression | eventFilterExpression
	;
		
observerExpression
	:	ns=IDENT COLON nm=IDENT LPAREN parameterSet? RPAREN
		//-> ^(OBSERVER_EXPR $ns $nm parameterSet?)
	;

guardExpression
	:	IDENT COLON! IDENT LPAREN! (parameterSet)? RPAREN!
	;

//----------------------------------------------------------------------------
// Parameter Set is used by guards, observers and views
//----------------------------------------------------------------------------
parameterSet
	:	parameter (COMMA! parameter)*
	;			
	
parameter
	:	singleParameter
	| 	numericParameterList
	|	arrayParameterList
	;

singleParameter
	:	rangeOperand	
	| 	frequencyOperand
	|	lastOperator
	|	weekDayOperator
	|	LAST^
	|	LW^
	|	STAR^
	|	constant
	|	time_period
	;

frequencyOperand
	:	STAR DIV ni=NUM_INT -> NUMERIC_PARAM_FREQUENCY[$ni]
	;

rangeOperand
	:	l=NUM_INT COLON u=NUM_INT -> ^(NUMERIC_PARAM_RANGE $l $u)
	;

lastOperator
	:	l=NUM_INT LAST -> LAST_OPERATOR[$l]
	;

weekDayOperator:
		wd=NUM_INT WEEKDAY -> WEEKDAY_OPERATOR[$wd]
	;

numericParameterList
	:	LBRACK numericListParameter (COMMA numericListParameter)* RBRACK
		-> ^(NUMERIC_PARAM_LIST numericListParameter+)
	;

numericListParameter
	:	rangeOperand
	| 	frequencyOperand
	|	NUM_INT
	;

arrayParameterList
	:	LCURLY (constant (COMMA constant)*)? RCURLY
		-> ^(ARRAY_PARAM_LIST constant*)
	;

//----------------------------------------------------------------------------
// Filter expressions
//   Operators are the usual bunch =, <, >, =<, >= 
//	 Ranges such as 'property in [a,b]' are allowed and ([ and )] distinguish open/closed range endpoints
//----------------------------------------------------------------------------
eventFilterExpression
    :   ( i=IDENT EQUALS ->EVENT_FILTER_NAME_TAG[$i] )?
    	classIdentifier
       	(LPAREN expressionList? RPAREN)?
       	-> ^(EVENT_FILTER_EXPR $i? classIdentifier expressionList?)
    ;

classIdentifier
  @init { String identifier = ""; }
	:	i1=IDENT { identifier = $i1.getText(); }
	    ( 
	    	// Ambigous since exit block may also be identifier, not yet seen a problem testing this
	    	// so suppressing warning here.
	    	/*options 
	    	{ 	
	    		warnWhenFollowAmbig=false; 
	    	}*/ 
	    	 DOT i2=IDENT { identifier += "." + $i2.getText(); }
	    )* 
	    -> ^(CLASS_IDENT[identifier])
	;
	
classIdentifierNonGreedy
  @init { String identifier = ""; }
	:	i1=IDENT { identifier = $i1.getText(); }
	    ( 
	    	// Ambigous since exit block may also be identifier, not yet seen a problem testing this
	    	// so suppressing warning here.
	    	/*options 
	    	{ 	
	    		warnWhenFollowAmbig=false; 
	    	}*/ 
	    	 DOT i2=IDENT { identifier += "." + $i2.getText(); }
	    )* 
	    -> ^(CLASS_IDENT[identifier])
	;
	
expressionList
    :   expression (COMMA! expression)*
    ;
   	
eventProperty
	:	eventPropertyAtomic (DOT eventPropertyAtomic)* 
		-> ^(EVENT_PROP_EXPR eventPropertyAtomic+)
	;
	
eventPropertyAtomic
	:	i=IDENT -> ^(EVENT_PROP_SIMPLE $i)
	|	i=IDENT LBRACK ni=NUM_INT RBRACK (d=QUESTION)?
			-> {$d == null}? ^(EVENT_PROP_INDEXED $i $ni)
			-> ^(EVENT_PROP_DYNAMIC_INDEXED $i $ni)
	|	i=IDENT LPAREN (s=STRING_LITERAL | s=QUOTED_STRING_LITERAL) RPAREN (q=QUESTION)?
			-> {$d == null}? ^(EVENT_PROP_MAPPED $i $s)
			-> ^(EVENT_PROP_DYNAMIC_MAPPED $i $s)
	|	i=IDENT QUESTION -> EVENT_PROP_DYNAMIC_SIMPLE[$i]
	;

time_period 	
	:	
	(	
		dayPart hourPart? minutePart? secondPart? millisecondPart?
	|	hourPart minutePart? secondPart? millisecondPart?
	|	minutePart secondPart? millisecondPart?
	|	secondPart millisecondPart?
	|	millisecondPart
	)
		-> ^(TIME_PERIOD dayPart? hourPart? minutePart? secondPart? millisecondPart?)
	;

dayPart
	:	number ('days' | 'day')
		-> ^(DAY_PART number)
	;

hourPart 
	:	number ('hours' | 'hour')
		-> ^(HOUR_PART number)
	;

minutePart 
	:	number ('minutes' | 'minute' | 'min')
		-> ^(MINUTE_PART number)
	;
	
secondPart 
	:	number ('seconds' | 'second' | 'sec')
		-> ^(SECOND_PART number)
	;
	
millisecondPart 
	:	number ('milliseconds' | 'millisecond' | 'msec')
		-> ^(MILLISECOND_PART number)
	;
	
//----------------------------------------------------------------------------
// LEXER
//----------------------------------------------------------------------------


/*options {
	exportVocab=Epl;      // call the vocabulary "Java"
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\u7FFE';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	// bottom of JavaLexer.java
	codeGenBitsetTestThreshold=20;
	caseSensitive=false;
	caseSensitiveLiterals=false;
}*/

// Operators
FOLLOWED_BY /*options {paraphrase = 'an followed-by \"->\"';}*/		:	'->'	;
EQUALS /*options {paraphrase = 'an equals '='';}*/					:	'='		;
SQL_NE /*options {paraphrase = 'a sql-style not equals \"<>\"';}*/	: 	'<>'	;
QUESTION /*options {paraphrase = 'a questionmark '?'';}*/			:	'?'		;
LPAREN /*options {paraphrase = 'an opening parenthesis '('';}*/		:	'('		;
RPAREN /*options {paraphrase = 'a closing parenthesis ')'';}*/		:	')'		;
LBRACK /*options {paraphrase = 'a left angle bracket '['';}*/		:	'['		;
RBRACK /*options {paraphrase = 'a right angle bracket ']'';}*/		:	']'		;
LCURLY /*options {paraphrase = 'a left curly bracket '{'';}*/		:	'{'		;
RCURLY /*options {paraphrase = 'a right curly bracket '}'';}*/		:	'}'		;
COLON /*options {paraphrase = 'a colon ':'';}*/						:	':'		;
COMMA /*options {paraphrase = 'a comma ','';}*/						:	','		;
EQUAL /*options {paraphrase = 'an equals compare \"==\"';}*/		:	'=='	;
LNOT /*options {paraphrase = 'a not '!'';}*/						:	'!'		;
BNOT /*options {paraphrase = 'a binary not '~'';}*/					:	'~'		;
NOT_EQUAL /*options {paraphrase = 'a not equals \"!=\"';}*/			:	'!='	;
DIV /*options {paraphrase = 'a division operator '\'';}*/			:	'/'		;
DIV_ASSIGN /*options {paraphrase = 'a division assign \"/=\"';}*/	:	'/='	;
PLUS /*options {paraphrase = 'a plus operator '+'';}*/				:	'+'		;
PLUS_ASSIGN	/*options {paraphrase = 'a plus assign \"+=\"';}*/		:	'+='	;
INC /*options {paraphrase = 'an increment operator '++'';}*/		:	'++'	;
MINUS /*options {paraphrase = 'a minus '-'';}*/					:	'-'		;
MINUS_ASSIGN /*options {paraphrase = 'a minus assign \"-=\"';}*/	:	'-='	;
DEC /*options {paraphrase = 'a decrement operator '--'';}*/		:	'--'	;
STAR /*options {paraphrase = 'a star '*'';}*/						:	'*'		;
STAR_ASSIGN /*options {paraphrase = 'a star assign '*='';}*/		:	'*='	;
MOD /*options {paraphrase = 'a modulo '%'';}*/						:	'%'		;
MOD_ASSIGN /*options {paraphrase = 'a module assign \"%=\"';}*/		:	'%='	;
SR /*options {paraphrase = 'a shift right '>>'';}*/				:	'>>'	;
SR_ASSIGN /*options {paraphrase = 'a shift right assign '>>='';}*/	:	'>>='	;
BSR /*options {paraphrase = 'a binary shift right \">>>\"';}*/		:	'>>>'	;
BSR_ASSIGN /*options {paraphrase = 'a binary shift right assign \">>>=\"';}*/		:	'>>>='	;
GE /*options {paraphrase = 'a greater equals \">=\"';}*/			:	'>='	;
GT /*options {paraphrase = 'a greater then '>'';}*/					:	'>'		;
SL /*options {paraphrase = 'a shift left \"<<\"';}*/				:	'<<'	;
SL_ASSIGN /*options {paraphrase = 'a shift left assign \"<<=\"';}*/	:	'<<='	;
LE /*options {paraphrase = 'a less equals \"<=\"';}*/				:	'<='	;
LT /*options {paraphrase = 'a lesser then '<'';}*/					:	'<'		;
BXOR /*options {paraphrase = 'a binary xor '^'';}*/					:	'^'		;
BXOR_ASSIGN /*options {paraphrase = 'a binary xor assign \"^=\"';}*/:	'^='	;
BOR	/*options {paraphrase = 'a binary or '|'';}*/					:	'|'		;
BOR_ASSIGN /*options {paraphrase = 'a binary or assign \"|=\"';}*/	:	'|='	;
LOR	/*options {paraphrase = 'a logical or \"||\"';}*/				:	'||'	;
BAND /*options {paraphrase = 'a binary and '&'';}*/					:	'&'		;
BAND_ASSIGN /*options {paraphrase = 'a binary and assign \"&=\"';}*/:	'&='	;
LAND /*options {paraphrase = 'a logical and \"&&\"';}*/				:	'&&'	;
SEMI /*options {paraphrase = 'a semicolon ';'';}*/					:	';'		;
DOT : '.';

// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	/*options {generateAmbigWarnings=false;}*/
				'\r\n'  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
		)+
		{ $channel=HIDDEN; }
	;

// Single-line comments
SL_COMMENT
	:	'//'
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
		{$channel=HIDDEN;}
	;

// multiple-line comments
ML_COMMENT
	:	'/*'
		(	/*	'\r' '\n' can be matched in one alternative or by matching
				'\r' in one iteration and '\n' in another.  I am trying to
				handle any flavor of newline that comes in, but the language
				that allows both "\r\n" and "\r" and "\n" to all be valid
				newline is ambiguous.  Consequently, the resulting grammar
				must be ambiguous.  I'm shutting this warning off.
			 */
			/*options {
				generateAmbigWarnings=false;
			}*/
		
			{ input.LA(2)!='/' }? '*'
		|	'\r' '\n'		
		|	'\r'			
		|	'\n'			
		|	~('*'|'\n'|'\r')
		)*
		'*/'
		{$channel=HIDDEN;}
	;

// string literals
STRING_LITERAL
	:	'"' (ESC|~('"'|'\\'|'\n'|'\r'))* '"'
	;

QUOTED_STRING_LITERAL
	:	'\'' (ESC|~('\''|'\\'|'\n'|'\r'))* '\''
	;

// escape sequence -- note that this is protected; it can only be called
//   from another lexer rule -- it will not ever directly return a token to
//   the parser
// There are various ambiguities hushed in this rule.  The optional
// '0'...'9' digit matches should be matched here rather than letting
// them go back to STRING_LITERAL to be matched.  ANTLR does the
// right thing by matching immediately; hence, it's ok to shut off
// the FOLLOW ambig warnings.
fragment
ESC
	:	'\\'
		(	'n'
		|	'r'
		|	't'
		|	'b'
		|	'f'
		|	'"'
		|	'\''
		|	'\\'
		|	('u')+ HEX_DIGIT HEX_DIGIT HEX_DIGIT HEX_DIGIT
		|	'0'..'3'
			(
				/*options {
					warnWhenFollowAmbig = false;
				}*/
				'0'..'7'
				(
					/*options {
						warnWhenFollowAmbig = false;
					}*/
					'0'..'7'
				)?
			)?
		|	'4'..'7'
			(
				/*options {
					warnWhenFollowAmbig = false;
				}*/
				'0'..'7'
			)?
		)
	;


// hexadecimal digit (again, note it's protected!)
fragment
HEX_DIGIT
	:	('0'..'9'|'a'..'f')
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	/*options {testLiterals=true; paraphrase = 'an identifier';}*/		   
	:	('a'..'z'|'_'|'$') ('a'..'z'|'_'|'0'..'9'|'$')*
	;


// a numeric literal
NUM_INT
	/*options {paraphrase = 'a numeric literal';}*/		   
	@init {boolean isDecimal=false; Token t=null;}
    :   '.' {$type = DOT;}
            (	('0'..'9')+ (EXPONENT)? (f1=FLOAT_SUFFIX {t=f1;})?
                {
				if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
                	$type = NUM_FLOAT;
				}
				else {
                	$type = NUM_DOUBLE; // assume double
				}
				}
            )?

	|	(	'0' {isDecimal = true;} // special case for just '0'
			(	('x')
				(											// hex
					// the 'e'|'E' and float suffix stuff look
					// like hex digits, hence the (...)+ doesn't
					// know when to stop: ambig.  ANTLR resolves
					// it correctly by matching immediately.  It
					// is therefor ok to hush warning.
					/*options {
						warnWhenFollowAmbig=false;
					}*/
					HEX_DIGIT
				)+

			|	//float or double with leading zero
				(('0'..'9')+ ('.'|EXPONENT|FLOAT_SUFFIX)) => ('0'..'9')+

			|	('0'..'7')+									// octal
			)?
		|	('1'..'9') ('0'..'9')*  {isDecimal=true;}		// non-zero decimal
		)
		(	('l') { $type = NUM_LONG; }

		// only check to see if it's a float if looks like decimal so far
		|	{isDecimal}?
            (   '.' ('0'..'9')* (EXPONENT)? (f2=FLOAT_SUFFIX {t=f2;})?
            |   EXPONENT (f3=FLOAT_SUFFIX {t=f3;})?
            |   f4=FLOAT_SUFFIX {t=f4;}
            )
            {
			if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                $type = NUM_FLOAT;
			}
            else {
	           	$type = NUM_DOUBLE; // assume double
			}
			}
        )?
	;


// a couple protected methods to assist in matching floating point numbers
fragment
EXPONENT
	:	('e') ('+'|'-')? ('0'..'9')+
	;


fragment
FLOAT_SUFFIX
	:	'f'|'d'
	;
