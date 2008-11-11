grammar EsperEPL2Grammar;

options
{
	k = 4;
	output = AST;
	ASTLabelType=CommonTree;
}

// language tokens
// Declare all tokens here and add to the "parserTokenParaphases" map below.
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
	INNER='inner';
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
	FIRST='first';
	LAST='last';
	INSERT='insert';
	INTO='into';
	ORDER='order';
	ASC='asc';
	DESC='desc';
	RSTREAM='rstream';
	ISTREAM='istream';
	IRSTREAM='irstream';
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
	UNTIL='until';
	AT='at';
	TIMEPERIOD_DAY='day';
	TIMEPERIOD_DAYS='days';
	TIMEPERIOD_HOUR='hour';
	TIMEPERIOD_HOURS='hours';
	TIMEPERIOD_MINUTE='minute';
	TIMEPERIOD_MINUTES='minutes';
	TIMEPERIOD_SEC='sec';
	TIMEPERIOD_SECOND='second';
	TIMEPERIOD_SECONDS='seconds';	
	TIMEPERIOD_MILLISEC='msec';
	TIMEPERIOD_MILLISECOND='millisecond';
	TIMEPERIOD_MILLISECONDS='milliseconds';
	BOOLEAN_TRUE='true';
	BOOLEAN_FALSE='false';
	VALUE_NULL='null';
	ROW_LIMIT_EXPR='limit';
	OFFSET='offset';
	
   	NUMERIC_PARAM_RANGE;
   	NUMERIC_PARAM_LIST;
   	NUMERIC_PARAM_FREQUENCY;   	
   	FOLLOWED_BY_EXPR;
   	ARRAY_PARAM_LIST;
   	EVENT_FILTER_EXPR;
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
   	INNERJOIN_EXPR;
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
	TIMEPERIOD_LIMIT_EXPR;
	CRONTAB_LIMIT_EXPR;
	CRONTAB_LIMIT_EXPR_PARAM;
	WHEN_LIMIT_EXPR;
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
	MATCH_UNTIL_EXPR;
	MATCH_UNTIL_RANGE_HALFOPEN;
	MATCH_UNTIL_RANGE_HALFCLOSED;
	MATCH_UNTIL_RANGE_CLOSED;
	MATCH_UNTIL_RANGE_BOUNDED;
	CREATE_WINDOW_COL_TYPE_LIST;
	CREATE_WINDOW_COL_TYPE;
	
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
  package com.espertech.esper.epl.generated;
}
@lexer::header {
  package com.espertech.esper.epl.generated;
}
@lexer::members {
  protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);  
  }

  public void recoverFromMismatchedToken(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) throws RecognitionException {
    throw recognitionException;
  }

  public void recoverFromMismatchedSet(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) throws RecognitionException {
    throw recognitionException;
  }

  protected boolean recoverFromMismatchedElement(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) {
    throw new RuntimeException("Error recovering from mismatched element", recognitionException);
  }
  
  public String getErrorMessage(RecognitionException e, String[] tokenNames) {
    if(e instanceof EarlyExitException)
        {
            throw new RuntimeException(e);
        }
    return super.getErrorMessage(e, tokenNames);
  }
}
@members {
  // provide nice error messages
  private Stack<String> paraphrases = new Stack<String>();
  
  // static information initialized once
  private static Map<Integer, String> lexerTokenParaphases = new HashMap<Integer, String>();
  private static Map<Integer, String> parserTokenParaphases = new HashMap<Integer, String>();
  private static java.util.Set<String> parserKeywordSet = new java.util.HashSet<String>();
    
  public Stack getParaphrases() {
    return paraphrases;
  }

  public java.util.Set<String> getKeywords() {
  	getParserTokenParaphrases();
  	return parserKeywordSet;
  }
  
  public Map<Integer, String> getLexerTokenParaphrases() {
    if (lexerTokenParaphases.size() == 0) {
      	lexerTokenParaphases.put(IDENT, "an identifier");
      	lexerTokenParaphases.put(NUM_INT, "a numeric literal");
	lexerTokenParaphases.put(FOLLOWED_BY, "an followed-by '->'");
	lexerTokenParaphases.put(EQUALS, "an equals '='");
	lexerTokenParaphases.put(SQL_NE, "a sql-style not equals '<>'");
	lexerTokenParaphases.put(QUESTION, "a questionmark '?'");
	lexerTokenParaphases.put(LPAREN, "an opening parenthesis '('");
	lexerTokenParaphases.put(RPAREN, "a closing parenthesis ')'");
	lexerTokenParaphases.put(LBRACK, "a left angle bracket '['");
	lexerTokenParaphases.put(RBRACK, "a right angle bracket ']'");
	lexerTokenParaphases.put(LCURLY, "a left curly bracket '{'");
	lexerTokenParaphases.put(RCURLY, "a right curly bracket '}'");
	lexerTokenParaphases.put(COLON, "a colon ':'");
	lexerTokenParaphases.put(COMMA, "a comma ','");
	lexerTokenParaphases.put(EQUAL, "an equals compare '=='");
	lexerTokenParaphases.put(LNOT, "a not '!'");
	lexerTokenParaphases.put(BNOT, "a binary not '~'");
	lexerTokenParaphases.put(NOT_EQUAL, "a not equals '!='");
	lexerTokenParaphases.put(DIV, "a division operator '\'");
	lexerTokenParaphases.put(DIV_ASSIGN, "a division assign '/='");
	lexerTokenParaphases.put(PLUS, "a plus operator '+'");
	lexerTokenParaphases.put(PLUS_ASSIGN, "a plus assign '+='");
	lexerTokenParaphases.put(INC, "an increment operator '++'");
	lexerTokenParaphases.put(MINUS, "a minus '-'");
	lexerTokenParaphases.put(MINUS_ASSIGN, "a minus assign '-='");
	lexerTokenParaphases.put(DEC, "a decrement operator '--'");
	lexerTokenParaphases.put(STAR, "a star '*'");
	lexerTokenParaphases.put(STAR_ASSIGN, "a star assign '*='");
	lexerTokenParaphases.put(MOD, "a modulo");
	lexerTokenParaphases.put(MOD_ASSIGN, "a modulo assign");
	lexerTokenParaphases.put(SR, "a shift right '>>'");
	lexerTokenParaphases.put(SR_ASSIGN, "a shift right assign '>>='");
	lexerTokenParaphases.put(BSR, "a binary shift right '>>>'");
	lexerTokenParaphases.put(BSR_ASSIGN, "a binary shift right assign '>>>='");
	lexerTokenParaphases.put(GE, "a greater equals '>='");
	lexerTokenParaphases.put(GT, "a greater then '>'");
	lexerTokenParaphases.put(SL, "a shift left '<<'");
	lexerTokenParaphases.put(SL_ASSIGN, "a shift left assign '<<='");
	lexerTokenParaphases.put(LE, "a less equals '<='");
	lexerTokenParaphases.put(LT, "a lesser then '<'");
	lexerTokenParaphases.put(BXOR, "a binary xor '^'");
	lexerTokenParaphases.put(BXOR_ASSIGN, "a binary xor assign '^='");
	lexerTokenParaphases.put(BOR, "a binary or '|'");
	lexerTokenParaphases.put(BOR_ASSIGN, "a binary or assign '|='");
	lexerTokenParaphases.put(LOR, "a logical or '||'");
	lexerTokenParaphases.put(BAND, "a binary and '&'");
	lexerTokenParaphases.put(BAND_ASSIGN, "a binary and assign '&='");
	lexerTokenParaphases.put(LAND, "a logical and '&&'");
	lexerTokenParaphases.put(SEMI, "a semicolon ';'");
	lexerTokenParaphases.put(DOT, "a dot '.'");		
    }
    return lexerTokenParaphases;
  }
  
  public Map<Integer, String> getParserTokenParaphrases() {
    if (parserTokenParaphases.size() == 0) {
	parserTokenParaphases.put(CREATE, "'create'");
	parserTokenParaphases.put(WINDOW, "'window'");
	parserTokenParaphases.put(IN_SET, "'in'");
	parserTokenParaphases.put(BETWEEN, "'between'");
	parserTokenParaphases.put(LIKE, "'like'");
	parserTokenParaphases.put(REGEXP, "'regexp'");
	parserTokenParaphases.put(ESCAPE, "'escape'");
	parserTokenParaphases.put(OR_EXPR, "'or'");
	parserTokenParaphases.put(AND_EXPR, "'and'");
	parserTokenParaphases.put(NOT_EXPR, "'not'");
	parserTokenParaphases.put(EVERY_EXPR, "'every'");
	parserTokenParaphases.put(WHERE, "'where'");
	parserTokenParaphases.put(AS, "'as'");	
	parserTokenParaphases.put(SUM, "'sum'");
	parserTokenParaphases.put(AVG, "'avg'");
	parserTokenParaphases.put(MAX, "'max'");
	parserTokenParaphases.put(MIN, "'min'");
	parserTokenParaphases.put(COALESCE, "'coalesce'");
	parserTokenParaphases.put(MEDIAN, "'median'");
	parserTokenParaphases.put(STDDEV, "'stddev'");
	parserTokenParaphases.put(AVEDEV, "'avedev'");
	parserTokenParaphases.put(COUNT, "'count'");
	parserTokenParaphases.put(SELECT, "'select'");
	parserTokenParaphases.put(CASE, "'case'");
	parserTokenParaphases.put(CASE2, "'case'");
	parserTokenParaphases.put(ELSE, "'else'");
	parserTokenParaphases.put(WHEN, "'when'");
	parserTokenParaphases.put(THEN, "'then'");
	parserTokenParaphases.put(END, "'end'");
	parserTokenParaphases.put(FROM, "'from'");
	parserTokenParaphases.put(OUTER, "'outer'");
	parserTokenParaphases.put(JOIN, "'join'");
	parserTokenParaphases.put(LEFT, "'left'");
	parserTokenParaphases.put(RIGHT, "'right'");
	parserTokenParaphases.put(FULL, "'full'");
	parserTokenParaphases.put(ON, "'on'");	
	parserTokenParaphases.put(IS, "'is'");
	parserTokenParaphases.put(BY, "'by'");
	parserTokenParaphases.put(GROUP, "'group'");
	parserTokenParaphases.put(HAVING, "'having'");
	parserTokenParaphases.put(DISTINCT, "'distinct'");
	parserTokenParaphases.put(ALL, "'all'");
	parserTokenParaphases.put(OUTPUT, "'output'");
	parserTokenParaphases.put(EVENTS, "'events'");
	parserTokenParaphases.put(FIRST, "'first'");
	parserTokenParaphases.put(LAST, "'last'");
	parserTokenParaphases.put(INSERT, "'insert'");
	parserTokenParaphases.put(INTO, "'into'");
	parserTokenParaphases.put(ORDER, "'order'");
	parserTokenParaphases.put(ASC, "'asc'");
	parserTokenParaphases.put(DESC, "'desc'");
	parserTokenParaphases.put(RSTREAM, "'rstream'");
	parserTokenParaphases.put(ISTREAM, "'istream'");
	parserTokenParaphases.put(IRSTREAM, "'irstream'");
	parserTokenParaphases.put(UNIDIRECTIONAL, "'unidirectional'");
	parserTokenParaphases.put(PATTERN, "'pattern'");
	parserTokenParaphases.put(SQL, "'sql'");
	parserTokenParaphases.put(METADATASQL, "'metadatasql'");
	parserTokenParaphases.put(PREVIOUS, "'prev'");
	parserTokenParaphases.put(PRIOR, "'prior'");
	parserTokenParaphases.put(EXISTS, "'exists'");
	parserTokenParaphases.put(WEEKDAY, "'weekday'");
	parserTokenParaphases.put(LW, "'lastweekday'");
	parserTokenParaphases.put(INSTANCEOF, "'instanceof'");
	parserTokenParaphases.put(CAST, "'cast'");
	parserTokenParaphases.put(CURRENT_TIMESTAMP, "'current_timestamp'");
	parserTokenParaphases.put(DELETE, "'delete'");
	parserTokenParaphases.put(SNAPSHOT, "'snapshot'");
	parserTokenParaphases.put(SET, "'set'");
	parserTokenParaphases.put(VARIABLE, "'variable'");
	parserTokenParaphases.put(UNTIL, "'until'");
	parserTokenParaphases.put(AT, "'at'");
	parserTokenParaphases.put(TIMEPERIOD_DAY, "'day'");
	parserTokenParaphases.put(TIMEPERIOD_DAYS, "'days'");
	parserTokenParaphases.put(TIMEPERIOD_HOUR, "'hour'");
	parserTokenParaphases.put(TIMEPERIOD_HOURS, "'hours'");
	parserTokenParaphases.put(TIMEPERIOD_MINUTE, "'minute'");
	parserTokenParaphases.put(TIMEPERIOD_MINUTES, "'minutes'");
	parserTokenParaphases.put(TIMEPERIOD_SEC, "'sec'");
	parserTokenParaphases.put(TIMEPERIOD_SECOND, "'second'");
	parserTokenParaphases.put(TIMEPERIOD_SECONDS, "'seconds';");
	parserTokenParaphases.put(TIMEPERIOD_MILLISEC, "'msec'");
	parserTokenParaphases.put(TIMEPERIOD_MILLISECOND, "'millisecond'");
	parserTokenParaphases.put(TIMEPERIOD_MILLISECONDS, "'milliseconds'");
	parserTokenParaphases.put(BOOLEAN_TRUE, "'true'");
	parserTokenParaphases.put(BOOLEAN_FALSE, "'false'");
	parserTokenParaphases.put(VALUE_NULL, "'null'");
	parserTokenParaphases.put(ROW_LIMIT_EXPR, "'limit'");
	parserTokenParaphases.put(OFFSET, "'offset'");

	parserKeywordSet = new java.util.TreeSet<String>(parserTokenParaphases.values());
    }
    return parserTokenParaphases;
  }

  protected void mismatch(IntStream input, int ttype, BitSet follow) throws RecognitionException {
    throw new MismatchedTokenException(ttype, input);  
  }

  public void recoverFromMismatchedToken(IntStream intStream, RecognitionException recognitionException, int i, BitSet bitSet) throws RecognitionException {
    throw recognitionException;
  }

  public void recoverFromMismatchedSet(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) throws RecognitionException {
    throw recognitionException;
  }

  protected boolean recoverFromMismatchedElement(IntStream intStream, RecognitionException recognitionException, BitSet bitSet) {
    throw new RuntimeException("Error recovering from mismatched element: " + recognitionException.getMessage(), recognitionException);
  }
}
@rulecatch {
  catch (RecognitionException rex) {
    throw rex;
  }
}

startPatternExpressionRule
	:	patternExpression
		EOF!
	;
	
startEPLExpressionRule 
	:	annotations	
		eplExpression
		EOF
		-> ^(EPL_EXPR annotations eplExpression) 
	;

startEventPropertyRule 
	:	eventProperty
		EOF!
	;

annotations
	:	(EMAILAT IDENT)*
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
		FROM! fromClause
		(WHERE! whereClause)?
		(GROUP! BY! groupByListExpr)?
		(HAVING! havingClause)?
		(OUTPUT! outputLimit)?
		(ORDER! BY! orderByListExpr)?
		(ROW_LIMIT_EXPR! rowLimit)?
	;
	
onExpr 
	:	ON (eventFilterExpression | patternInclusionExpression) (AS i=IDENT | i=IDENT)? 
		(onDeleteExpr | onSelectExpr | onSetExpr)
		-> ^(ON_EXPR eventFilterExpression? patternInclusionExpression? $i? onDeleteExpr? onSelectExpr? onSetExpr?)
	;
	
onSelectExpr	
@init  { paraphrases.push("on-select clause"); }
@after { paraphrases.pop(); }
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
@init  { paraphrases.push("on-delete clause"); }
@after { paraphrases.pop(); }
	:	DELETE
		onExprFrom
		(WHERE whereClause)?		
		-> ^(ON_DELETE_EXPR onExprFrom whereClause?)
	;
	
onSetExpr
@init  { paraphrases.push("on-set clause"); }
@after { paraphrases.pop(); }
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
	:	CREATE WINDOW i=IDENT (DOT viewExpression (DOT viewExpression)*)? AS? 
		  (
		  	createWindowExprModelAfter		  
		  |   	LPAREN createWindowColumnList RPAREN
		  )		
		  (i1=INSERT (WHERE expression)? )?
		-> {i1 != null}? ^(CREATE_WINDOW_EXPR $i viewExpression* createWindowExprModelAfter? createWindowColumnList? 
				^(INSERT expression?))
		-> ^(CREATE_WINDOW_EXPR $i viewExpression* createWindowExprModelAfter? createWindowColumnList?)
	;

createWindowExprModelAfter
	:	(SELECT! createSelectionList FROM!)? classIdentifier
	;
		
createVariableExpr
	:	CREATE VARIABLE t=IDENT n=IDENT (EQUALS expression)?
		-> ^(CREATE_VARIABLE_EXPR $t $n expression?)
	;

createWindowColumnList 	
@init  { paraphrases.push("create window column list"); }
@after { paraphrases.pop(); }
	:	createWindowColumnListElement (COMMA createWindowColumnListElement)*
		-> ^(CREATE_WINDOW_COL_TYPE_LIST createWindowColumnListElement+)
	;
	
createWindowColumnListElement
	:   	name=IDENT type=IDENT
		-> ^(CREATE_WINDOW_COL_TYPE $name $type)
	;

createSelectionList 	
@init  { paraphrases.push("select clause"); }
@after { paraphrases.pop(); }
	:	createSelectionListElement (COMMA createSelectionListElement)*
		-> ^(CREATE_WINDOW_SELECT_EXPR createSelectionListElement+)
	;

createSelectionListElement
	:   	s=STAR
		-> WILDCARD_SELECT[$s]
	|	eventProperty (AS i=IDENT)?
		-> ^(SELECTION_ELEMENT_EXPR eventProperty $i?)
	|	constant AS i=IDENT
		-> ^(SELECTION_ELEMENT_EXPR constant $i?)
	;

insertIntoExpr
@init  { paraphrases.push("insert-into clause"); }
@after { paraphrases.pop(); }
	:	(s=ISTREAM | s=RSTREAM)? INTO i=IDENT (insertIntoColumnList)?
		-> ^(INSERTINTO_EXPR $s? $i insertIntoColumnList?)
	;
		
insertIntoColumnList
	: 	LPAREN IDENT (COMMA IDENT)* RPAREN
		-> ^(INSERTINTO_EXPRCOL IDENT*)
	;
	
fromClause 
@init  { paraphrases.push("from clause"); }
@after { paraphrases.pop(); }
	:	streamExpression (regularJoin | outerJoinList)
	;
	
regularJoin
	:	(COMMA! streamExpression)*
	;
	
outerJoinList
	:	outerJoin (outerJoin)*
	;

outerJoin
@init  { paraphrases.push("outer join"); }
@after { paraphrases.pop(); }
	:	(
	            ((tl=LEFT|tr=RIGHT|tf=FULL) OUTER)? 
	          | (i=INNER)
	        ) JOIN streamExpression outerJoinIdent
		-> {$i != null}? streamExpression ^(INNERJOIN_EXPR outerJoinIdent)
		-> {$tl != null}? streamExpression ^(LEFT_OUTERJOIN_EXPR outerJoinIdent)
		-> {$tr != null}? streamExpression ^(RIGHT_OUTERJOIN_EXPR outerJoinIdent)
		-> streamExpression ^(FULL_OUTERJOIN_EXPR outerJoinIdent)
	;

outerJoinIdent
	:	ON! outerJoinIdentPair (AND_EXPR! outerJoinIdentPair)*
	;
	
outerJoinIdentPair 
	:	eventProperty EQUALS! eventProperty
	;

whereClause
@init  { paraphrases.push("where clause"); }
@after { paraphrases.pop(); }
	:	evalOrExpression
		-> ^(WHERE_EXPR evalOrExpression)
	;
	
selectClause
@init  { paraphrases.push("select clause"); }
@after { paraphrases.pop(); }
	:	(s=RSTREAM | s=ISTREAM | s=IRSTREAM)? selectionList
		-> ^(SELECTION_EXPR $s? selectionList)
	;

selectionList 	
	:	selectionListElement (COMMA! selectionListElement)*
	;

selectionListElement
  @init { String identifier = null; } 
	:   	s=STAR -> WILDCARD_SELECT[$s]
	|	(streamSelector) => streamSelector
	|	expression (AS i=keywordAllowedIdent { identifier = i.getTree().toString(); } )?
		-> {identifier != null}? ^(SELECTION_ELEMENT_EXPR expression IDENT[identifier])
		-> {identifier == null}? ^(SELECTION_ELEMENT_EXPR expression)
		-> ^(SELECTION_ELEMENT_EXPR expression)
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
@init  { paraphrases.push("relational data join"); }
@after { paraphrases.pop(); }
	:	SQL COLON i=IDENT LBRACK (s=STRING_LITERAL | s=QUOTED_STRING_LITERAL) (METADATASQL (s2=STRING_LITERAL | s2=QUOTED_STRING_LITERAL))? RBRACK
		-> ^(DATABASE_JOIN_EXPR $i $s $s2?)
	;	
	
methodJoinExpression
@init  { paraphrases.push("method invocation join"); }
@after { paraphrases.pop(); }
    	:   	i=IDENT COLON classIdentifier (LPAREN expressionList? RPAREN)?
       		-> ^(METHOD_JOIN_EXPR $i classIdentifier expressionList?)
    	;

viewExpression
@init  { paraphrases.push("view specifications"); }
@after { paraphrases.pop(); }
	:	ns=IDENT COLON nm=IDENT LPAREN parameterSet? RPAREN
		-> ^(VIEW_EXPR $ns $nm parameterSet?)
	;

groupByListExpr
@init  { paraphrases.push("group-by clause"); }
@after { paraphrases.pop(); }
	:	expression (COMMA expression)*
		-> ^(GROUP_BY_EXPR expression+)
	;

orderByListExpr
@init  { paraphrases.push("order by clause"); }
@after { paraphrases.pop(); }
	:	orderByListElement (COMMA orderByListElement)*
		-> ^(ORDER_BY_EXPR orderByListElement+) 
	;

orderByListElement
	:	expression (d=ASC|d=DESC)?
		-> ^(ORDER_ELEMENT_EXPR expression $d?)
	;

havingClause
@init  { paraphrases.push("having clause"); }
@after { paraphrases.pop(); }
	:	evalOrExpression
		-> ^(HAVING_EXPR evalOrExpression) 
	;

outputLimit
@init  { paraphrases.push("output rate clause"); }
@after { paraphrases.pop(); }
	:   (k=ALL|k=FIRST|k=LAST|k=SNAPSHOT)? 
	      (
	        ( ev=EVERY_EXPR 
		  ( 
		    (time_period) => time_period
		  | (number | i=IDENT) (e=EVENTS|sec=TIMEPERIOD_SECONDS|min=TIMEPERIOD_MINUTES)
		  )
		)
		|
		( at=AT crontabLimitParameterSet )
		|
		( wh=WHEN expression (THEN onSetExpr)? )
	      )
	    -> {$ev != null && $e != null}? ^(EVENT_LIMIT_EXPR $k? number? $i?)
	    -> {$ev != null && $sec != null}? ^(SEC_LIMIT_EXPR $k? number? $i?)
	    -> {$ev != null && $min != null}? ^(MIN_LIMIT_EXPR $k? number? $i?)
	    -> {$ev != null}? ^(TIMEPERIOD_LIMIT_EXPR $k? time_period)		
	    -> {$at != null}? ^(CRONTAB_LIMIT_EXPR $k? crontabLimitParameterSet)		
	    -> ^(WHEN_LIMIT_EXPR $k? expression onSetExpr?)		
	;	

rowLimit
@init  { paraphrases.push("row limit clause"); }
@after { paraphrases.pop(); }
	:   (n1=numberconstant | i1=IDENT) ((c=COMMA | o=OFFSET) (n2=numberconstant | i2=IDENT))?
	    -> ^(ROW_LIMIT_EXPR $n1? $i1? $n2? $i2? $o? $c?)		
	;	

crontabLimitParameterSet
	:	LPAREN parameter COMMA parameter COMMA parameter COMMA parameter COMMA parameter (COMMA parameter)? RPAREN 
		-> ^(CRONTAB_LIMIT_EXPR_PARAM parameter*)			
	;			

whenClause
	: (WHEN! expression THEN! expression)
	;

elseClause
	: (ELSE! expression)
	;

// Main expression rule
expression
	: caseExpression
	;

caseExpression
	: { paraphrases.push("case expression"); }  CASE^ whenClause+ elseClause? END! { paraphrases.pop(); }
	| { paraphrases.push("case expression"); }  CASE expression whenClause+ elseClause? END { paraphrases.pop(); }
	  -> ^(CASE2 expression whenClause+ elseClause?)
	| evalOrExpression
	;

evalOrExpression
	: evalAndExpression (op=OR_EXPR evalAndExpression)*
	 -> {$op != null}? ^(EVAL_OR_EXPR evalAndExpression*)
	 -> evalAndExpression
	;

evalAndExpression
	: bitWiseExpression (op=AND_EXPR bitWiseExpression)*
	 -> {$op != null}? ^(EVAL_AND_EXPR bitWiseExpression+)
	 -> bitWiseExpression
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
	     ) evalRelationalExpression)*	     
	    -> {$eq != null || $is != null}? ^(EVAL_EQUALS_EXPR evalRelationalExpression+)
	    -> {$isnot != null || $sqlne != null || $ne != null}? ^(EVAL_NOTEQUALS_EXPR evalRelationalExpression+)
	    -> evalRelationalExpression+
	;

evalRelationalExpression
	: concatenationExpr 
		( 
			( 
			  ( 
			    (r=LT|r=GT|r=LE|r=GE) concatenationExpr
			  )*
			  -> {$r != null}? ^({adaptor.create($r)} concatenationExpr+)
			  -> concatenationExpr+
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
				    -> {$col == null && $n == null}? ^(IN_SET concatenationExpr $l expression+ $r)
				    -> {$col == null && $n != null}? ^(NOT_IN_SET concatenationExpr $l expression+ $r)
				    -> {$col != null && $n == null}? ^(IN_RANGE concatenationExpr $l expression+ $r)
				    -> ^(NOT_IN_RANGE concatenationExpr $l expression+ $r)
				| IN_SET inSubSelectQuery
				    -> {$n == null}? ^(IN_SUBSELECT_EXPR concatenationExpr inSubSelectQuery)
				    -> ^(NOT_IN_SUBSELECT_EXPR concatenationExpr inSubSelectQuery)
				| BETWEEN betweenList
				    -> {$n == null}? ^(BETWEEN concatenationExpr betweenList)
				    -> ^(NOT_BETWEEN concatenationExpr betweenList)				
				| LIKE concatenationExpr (ESCAPE stringconstant)?
				    -> {$n == null}? ^(LIKE concatenationExpr* stringconstant?)
				    -> ^(NOT_LIKE concatenationExpr* stringconstant?)
				| REGEXP concatenationExpr
				    -> {$n == null}? ^(REGEXP concatenationExpr+)
				    -> ^(NOT_REGEXP concatenationExpr+)				
			)	
		)
	;
	
inSubSelectQuery
	: subQueryExpr
	  -> ^(IN_SUBSELECT_QUERY_EXPR subQueryExpr)
	;
			
concatenationExpr
	: additiveExpression ( c=LOR additiveExpression ( LOR additiveExpression)* )?
	    -> {$c != null}? ^(CONCAT additiveExpression+)
	    -> additiveExpression
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
@init  { paraphrases.push("subquery"); }
@after { paraphrases.pop(); }
	:	LPAREN! 
		SELECT! selectionListElement
	    FROM! subSelectFilterExpr
	    (WHERE! whereClause)?
	    RPAREN!
	;
	
subSelectFilterExpr
@init  { paraphrases.push("subquery filter specification"); }
@after { paraphrases.pop(); }
	:	eventFilterExpression
		(DOT viewExpression (DOT viewExpression)*)? (AS i=IDENT | i=IDENT)?
		-> ^(STREAM_EXPR eventFilterExpression viewExpression* $i?)
	;
		
arrayExpression
	: LCURLY (expression (COMMA expression)* )? RCURLY
	  -> ^(ARRAY_EXPR expression*)
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
@init  { paraphrases.push("pattern expression"); }
@after { paraphrases.pop(); }
	: followedByExpression
	;
	
followedByExpression
	: orExpression (f=FOLLOWED_BY orExpression)*
	    -> {$f != null}? ^(FOLLOWED_BY_EXPR orExpression+)
	    -> orExpression
	;
	
orExpression
	:	andExpression (o=OR_EXPR andExpression)*
		-> {$o != null}? ^(OR_EXPR andExpression+)
		-> andExpression
	;

andExpression
	:	matchUntilExpression (a=AND_EXPR matchUntilExpression)*
		-> {$a != null}? ^(AND_EXPR matchUntilExpression+)
		-> matchUntilExpression
	;

matchUntilExpression
	:	(r=matchUntilRange)? qualifyExpression (a=UNTIL qualifyExpression)?
		-> {r != null && a != null}? ^(MATCH_UNTIL_EXPR matchUntilRange qualifyExpression+)
		-> {r != null && a == null}? ^(MATCH_UNTIL_EXPR matchUntilRange qualifyExpression)
		-> {$a != null}? ^(MATCH_UNTIL_EXPR qualifyExpression+)
		-> qualifyExpression
	;

qualifyExpression
	:	(EVERY_EXPR^ | NOT_EXPR^)?
		guardPostFix
	;
	
guardPostFix
	:	(atomicExpression | l=LPAREN patternExpression RPAREN) (w=WHERE guardExpression)?
		-> {$w != null}? ^(GUARD_EXPR atomicExpression? patternExpression? guardExpression) 
		-> atomicExpression? patternExpression?
	;

atomicExpression
	:	observerExpression | eventFilterExpression
	;
		
observerExpression
	:	ns=IDENT COLON (nm=IDENT | a=AT) LPAREN parameterSet? RPAREN
		-> {$a != null}? ^(OBSERVER_EXPR $ns ^(IDENT[$a.text]) parameterSet?)
		-> ^(OBSERVER_EXPR $ns $nm parameterSet?)
	;

guardExpression
	:	IDENT COLON! IDENT LPAREN! (parameterSet)? RPAREN!
	;
	
// syntax is [a..b]  or [..b]  or  [a..] or [a:b]   wherein a and b may be recognized as double
matchUntilRange
	:	LBRACK (
			l=NUM_INT (  (d1=DOT DOT r=NUM_INT?) 
			           | (c1=COLON r=NUM_INT)
				  )? 
		   |	db=NUM_DOUBLE (
		                        d1=DOT r=NUM_INT? 
		                        |
		                        db2=NUM_DOUBLE
		                      )? 
		   |	DOT DOT r=NUM_INT
		   |	DOT db3=NUM_DOUBLE
		   )
		RBRACK
		-> {$l != null && d1 != null && r != null}? ^(MATCH_UNTIL_RANGE_CLOSED $l $r) 
		-> {$l != null && d1 != null}? ^(MATCH_UNTIL_RANGE_HALFOPEN $l) 
		-> {$l != null && c1 != null}? ^(MATCH_UNTIL_RANGE_CLOSED $l $r) 
		-> {$l != null}? ^(MATCH_UNTIL_RANGE_BOUNDED $l) 
		-> {$db != null && d1 != null && r != null}? ^(MATCH_UNTIL_RANGE_CLOSED $db $r) 
		-> {$db != null && d1 != null}? ^(MATCH_UNTIL_RANGE_HALFOPEN $db) 
		-> {$db != null && db2 != null}? ^(MATCH_UNTIL_RANGE_CLOSED $db $db2) 
		-> {$db3 != null}? ^(MATCH_UNTIL_RANGE_HALFCLOSED $db3)
		-> {$r != null}? ^(MATCH_UNTIL_RANGE_HALFCLOSED $r) 
		-> ^(MATCH_UNTIL_RANGE_HALFCLOSED $db) 
	;
	
//----------------------------------------------------------------------------
// Parameter Set is used by guards, observers and views
//----------------------------------------------------------------------------
parameterSet
	:	parameter (COMMA! parameter)*
	;			
	
parameter
	:	(singleParameter) => singleParameter
	| 	(numericParameterList) => numericParameterList
	|	(arrayParameterList) => arrayParameterList
	|	eventProperty
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
	:	STAR DIV NUM_INT -> ^(NUMERIC_PARAM_FREQUENCY NUM_INT)
	;

rangeOperand
	:	l=NUM_INT COLON u=NUM_INT -> ^(NUMERIC_PARAM_RANGE $l $u)
	;

lastOperator
	:	l=NUM_INT LAST -> ^(LAST_OPERATOR $l)
	;

weekDayOperator:
		wd=NUM_INT WEEKDAY -> ^(WEEKDAY_OPERATOR $wd)
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
@init  { paraphrases.push("filter specification"); }
@after { paraphrases.pop(); }
    :   (i=IDENT EQUALS)?
    	classIdentifier
       	(LPAREN expressionList? RPAREN)?
       	-> ^(EVENT_FILTER_EXPR $i? classIdentifier expressionList?)
    ;

classIdentifier
  @init { String identifier = ""; }
	:	i1=IDENT { identifier = $i1.getText(); }
	    ( 
	    	 DOT i2=IDENT { identifier += "." + $i2.getText(); }
	    )* 
	    -> ^(CLASS_IDENT[identifier])
	;
	
classIdentifierNonGreedy
  @init { String identifier = ""; } 
	:	i1=IDENT { identifier = $i1.getText(); }
	    ( 
	    	 options {greedy=false;} :
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
	:	eventPropertyIdent (
			lb=LBRACK ni=NUM_INT RBRACK (q=QUESTION)?
			|
			lp=LPAREN (s=STRING_LITERAL | s=QUOTED_STRING_LITERAL) RPAREN (q=QUESTION)?
			|
			q1=QUESTION 
			)?
		
		-> {lb!= null && $q == null}? ^(EVENT_PROP_INDEXED eventPropertyIdent $ni)
		-> {lb!= null && $q != null}? ^(EVENT_PROP_DYNAMIC_INDEXED eventPropertyIdent $ni)
		-> {lp!= null && $q == null}? ^(EVENT_PROP_MAPPED eventPropertyIdent $s)
		-> {lp!= null && $q != null}? ^(EVENT_PROP_DYNAMIC_MAPPED eventPropertyIdent $s)
		-> {q1 != null}? 	      ^(EVENT_PROP_DYNAMIC_SIMPLE eventPropertyIdent)
		-> ^(EVENT_PROP_SIMPLE eventPropertyIdent)
		;
		
eventPropertyIdent
  @init { String identifier = ""; } 
	:	ipi=keywordAllowedIdent { identifier = ipi.getTree().toString(); }
		(
		  ESCAPECHAR DOT ipi2=keywordAllowedIdent? { identifier += "."; if (ipi2 != null) identifier += ipi2.getTree().toString(); }
		)*
	    	-> ^(IDENT[identifier])
	;
	
keywordAllowedIdent
  @init { String identifier = ""; } 
	:	i1=IDENT { identifier = $i1.getText(); }
		|AT { identifier = "at"; }
		|COUNT { identifier = "count"; }
		|ESCAPE { identifier = "escape"; }
    		|EVERY_EXPR { identifier = "every"; }
		|SUM { identifier = "sum"; }
		|AVG { identifier = "avg"; }
		|MAX { identifier = "max"; }
		|MIN { identifier = "min"; }
		|COALESCE { identifier = "coalesce"; }
		|MEDIAN { identifier = "median"; }
		|STDDEV { identifier = "stddev"; }
		|AVEDEV { identifier = "avedev"; }
		|EVENTS { identifier = "events"; }
		|TIMEPERIOD_SECONDS { identifier = "seconds"; }
		|TIMEPERIOD_MINUTES { identifier = "minutes"; }
		|FIRST { identifier = "first"; }
		|LAST { identifier = "last"; }
		|UNIDIRECTIONAL { identifier = "unidirectional"; }
		|UNTIL { identifier = "until"; }
		|PATTERN { identifier = "pattern"; }
		|SQL { identifier = "sql"; }
		|METADATASQL { identifier = "metadatasql"; }
		|PREVIOUS { identifier = "prev"; }
		|PRIOR { identifier = "prior"; }
		|WEEKDAY { identifier = "weekday"; }
		|LW { identifier = "lastweekday"; }
		|INSTANCEOF { identifier = "instanceof"; }
		|CAST { identifier = "cast"; }
		|SNAPSHOT { identifier = "snapshot"; }
		|VARIABLE { identifier = "variable"; }		
		|WINDOW { identifier = "window"; }
		|LEFT { identifier = "left"; }
		|RIGHT { identifier = "right"; }
		|OUTER { identifier = "outer"; }
		|FULL { identifier = "full"; }
		|JOIN { identifier = "join"; }
	-> ^(IDENT[identifier])
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
	:	number (TIMEPERIOD_DAYS | TIMEPERIOD_DAY)
		-> ^(DAY_PART number)
	;

hourPart 
	:	number (TIMEPERIOD_HOURS | TIMEPERIOD_HOUR)
		-> ^(HOUR_PART number)
	;

minutePart 
	:	number (TIMEPERIOD_MINUTES | TIMEPERIOD_MINUTE | MIN)
		-> ^(MINUTE_PART number)
	;
	
secondPart 
	:	number (TIMEPERIOD_SECONDS | TIMEPERIOD_SECOND | TIMEPERIOD_SEC)
		-> ^(SECOND_PART number)
	;
	
millisecondPart 
	:	number (TIMEPERIOD_MILLISECONDS | TIMEPERIOD_MILLISECOND | TIMEPERIOD_MILLISEC)
		-> ^(MILLISECOND_PART number)
	;
	
number
    :   ni=NUM_INT -> INT_TYPE[$ni]
    |   nl=NUM_LONG -> LONG_TYPE[$nl]
    |   nf=NUM_FLOAT -> FLOAT_TYPE[$nf]
    |   nd=NUM_DOUBLE -> DOUBLE_TYPE[$nd]
    ;

substitution
	: q=QUESTION -> SUBSTITUTION[$q]
	;
	
constant
	:   numberconstant
	|   stringconstant
    	|   t=BOOLEAN_TRUE -> ^(BOOL_TYPE[$t])
    	|   f=BOOLEAN_FALSE -> ^(BOOL_TYPE[$f])
    	|   nu=VALUE_NULL -> ^(NULL_TYPE[$nu])
	;

numberconstant
	:  (m=MINUS | p=PLUS)? number
		-> {$m != null}? {adaptor.create($number.tree.getType(), "-" + $number.text)}
		-> number
	;

stringconstant
	:   sl=STRING_LITERAL -> ^(STRING_TYPE[$sl])
	|   qsl=QUOTED_STRING_LITERAL -> ^(STRING_TYPE[$qsl])
	;

//----------------------------------------------------------------------------
// LEXER
//----------------------------------------------------------------------------

// Operators
FOLLOWED_BY 	: '->';
EQUALS 		: '=';
SQL_NE 		: '<>';
QUESTION 	: '?';
LPAREN 		: '(';
RPAREN 		: ')';
LBRACK 		: '[';
RBRACK 		: ']';
LCURLY 		: '{';
RCURLY 		: '}';
COLON 		: ':';
COMMA 		: ',';
EQUAL 		: '==';
LNOT 		: '!';
BNOT 		: '~';
NOT_EQUAL 	: '!=';
DIV 		: '/';
DIV_ASSIGN 	: '/=';
PLUS 		: '+';
PLUS_ASSIGN	: '+=';
INC 		: '++';
MINUS 		: '-';
MINUS_ASSIGN 	: '-=';
DEC 		: '--';
STAR 		: '*';
STAR_ASSIGN 	: '*=';
MOD 		: '%';
MOD_ASSIGN 	: '%=';
SR 		: '>>';
SR_ASSIGN 	: '>>=';
BSR 		: '>>>';
BSR_ASSIGN 	: '>>>=';
GE 		: '>=';
GT 		: '>';
SL 		: '<<';
SL_ASSIGN 	: '<<=';
LE 		: '<=';
LT 		: '<';
BXOR 		: '^';
BXOR_ASSIGN 	: '^=';
BOR		: '|';
BOR_ASSIGN 	: '|=';
LOR		: '||';
BAND 		: '&';
BAND_ASSIGN 	: '&=';
LAND 		: '&&';
SEMI 		: ';';
DOT 		: '.';
NUM_LONG	: '\u18FF';  // assign bogus unicode characters so the token exists
NUM_DOUBLE	: '\u18FE';
NUM_FLOAT	: '\u18FD';
ESCAPECHAR	: '\\';
EMAILAT		: '@';

// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(
				'\r'    // Macintosh
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
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
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
				'0'..'7'
				(
					'0'..'7'
				)?
			)?
		|	'4'..'7'
			(
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
	:	('a'..'z'|'_'|'$') ('a'..'z'|'_'|'0'..'9'|'$')*
	;


// a numeric literal
NUM_INT	
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
