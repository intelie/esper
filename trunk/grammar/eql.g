header
{
  package net.esper.eql.generated;
}

class EQLStatementParser extends Parser;
options
{
	k = 3;                           // lookahead
	exportVocab=Eql;
	buildAST = true;
    defaultErrorHandler=false;
}

// language tokens
tokens
{
	IN="in";	
	OR_EXPR="or";
	AND_EXPR="and";
	NOT_EXPR="not";
    EVERY_EXPR="every";
	WHERE="where";
	AS="as";	
	SUM="sum";
	AVG="avg";
	MAX="max";
	MIN="min";
	MEDIAN="median";
	STDDEV="stddev";
	AVEDEV="avedev";
	COUNT="count";
	SELECT="select";
	FROM="from";
	OUTER="outer";
	JOIN="join";
	LEFT="left";
	RIGHT="right";
	FULL="full";
	ON="on";	
	IS="is";
	BY="by";
	GROUP="group";
	HAVING="having";
	DISTINCT="distinct";
	ALL="all";
	OUTPUT="output";
	EVENTS="events";
	SECONDS="seconds";
	MINUTES="minutes";
	LAST="last";
	INSERT="insert";
	INTO="into";
	ORDER="order";
	ASC="asc";
	DESC="desc";
	RSTREAM="rstream";
	ISTREAM="istream";
   	
   	NUMERIC_PARAM_RANGE;
   	NUMERIC_PARAM_LIST;
   	NUMERIC_PARAM_FREQUENCY;   	
   	FOLLOWED_BY_EXPR;
   	ARRAY_PARAM_LIST;
   	EVENT_FILTER_EXPR;
   	EVENT_FILTER_NAME_TAG;
   	EVENT_FILTER_IDENT;
   	EVENT_FILTER_PARAM;
   	CLASS_IDENT;
   	GUARD_EXPR;
   	OBSERVER_EXPR;
   	VIEW_EXPR;
   	STREAM_EXPR;
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
   	EVENT_LIMIT_EXPR;
	SEC_LIMIT_EXPR;
	MIN_LIMIT_EXPR;
	INSERTINTO_EXPR;
	INSERTINTO_EXPRCOL;
	CONCAT;	
	LIB_FUNCTION;
	UNARY_MINUS;
	
   	INT_TYPE;
   	LONG_TYPE;
   	FLOAT_TYPE;
   	DOUBLE_TYPE;
   	STRING_TYPE;
   	BOOL_TYPE;
   	NULL_TYPE;
}

startPatternExpressionRule 
	:	patternExpression
		EOF!
	;
	
startEQLExpressionRule 
	:	eqlExpression
		EOF!
	;

startEventPropertyRule 
	:	eventProperty
		EOF!
	;

number
    :   NUM_INT^ { #number.setType(INT_TYPE); }
    |   NUM_LONG^ { #number.setType(LONG_TYPE); }
    |   NUM_FLOAT^ { #number.setType(FLOAT_TYPE); }
    |   NUM_DOUBLE^ { #number.setType(DOUBLE_TYPE); }
    ;

constant
	:	(m:MINUS! | PLUS!)? n:number { #constant.setType(#n.getType()); 
	                                   #constant.setText( (m == null) ? #n.getText() : "-" + #n.getText()); 
	                                 }
	|	STRING_LITERAL { #constant.setType(STRING_TYPE); }
	|	QUOTED_STRING_LITERAL { #constant.setType(STRING_TYPE); }
    |   "true" { #constant.setType(BOOL_TYPE); }
    |   "false" { #constant.setType(BOOL_TYPE); }
    |	"null" { #constant.setType(NULL_TYPE); #constant.setText(null); }
	;

//----------------------------------------------------------------------------
// EQL expression
//----------------------------------------------------------------------------
eqlExpression 
	:	(INSERT! insertIntoExpr)?
		SELECT! selectionListExpr 
		FROM! streamExpression
		(regularJoin | outerJoinList)
		(WHERE! whereClause)?
		(GROUP! BY! groupByListExpr)?
		(HAVING! havingClause)?
		(OUTPUT! outputLimit)?
		(ORDER! BY! orderByListExpr)?
	;
	
insertIntoExpr
	:	(ISTREAM | RSTREAM)? INTO! IDENT (insertIntoColumnList)?
		{ #insertIntoExpr = #([INSERTINTO_EXPR,"insertIntoExpr"], #insertIntoExpr); }
	;
	
insertIntoColumnList
	: 	LPAREN! IDENT (COMMA! IDENT)* RPAREN!
		{ #insertIntoColumnList = #([INSERTINTO_EXPRCOL,"insertIntoColumnList"], #insertIntoColumnList); }
	;
	
regularJoin
	:	(COMMA! streamExpression)*
	;
	
outerJoinList
	:	outerJoin (outerJoin)*
	;

outerJoin
	:	(tl:LEFT!|tr:RIGHT!|tf:FULL!) OUTER! JOIN! streamExpression 
		i:outerJoinIdent
		{
			if (tl!=null) #i.setType(LEFT_OUTERJOIN_EXPR);
			if (tr!=null) #i.setType(RIGHT_OUTERJOIN_EXPR);
			if (tf!=null) #i.setType(FULL_OUTERJOIN_EXPR);
		}
	;

outerJoinIdent
	:	ON! eventProperty EQUALS! eventProperty
		{ #outerJoinIdent = #([OUTERJOIN_EXPR,"outerJoinIdent"], #outerJoinIdent); }
	;

whereClause
	:	evalOrExpression
		{ #whereClause = #([WHERE_EXPR,"whereClause"], #whereClause); }
	;
	
selectionListExpr
	:	STAR
	|	selectionListElement (COMMA! selectionListElement)*
		{ #selectionListExpr = #([SELECTION_EXPR,"selectionListExpr"], #selectionListExpr); }
	;
		
selectionListElement
	:	expression (AS! IDENT)?
		{ #selectionListElement = #([SELECTION_ELEMENT_EXPR,"selectionListElement"], #selectionListElement); }
	;
		
streamExpression
	:	eventFilterExpression DOT! viewExpression (DOT! viewExpression)* (AS! IDENT | IDENT)?
		{ #streamExpression = #([STREAM_EXPR,"streamExpression"], #streamExpression); }
	;

viewExpression
	:	IDENT COLON! IDENT LPAREN! (parameterSet)? RPAREN!
		{ 
		 	#viewExpression = #([VIEW_EXPR,"viewExpression"], #viewExpression); 
		 }
	;

groupByListExpr
	:	expression (COMMA! expression)*
		{ #groupByListExpr = #([GROUP_BY_EXPR,"groupByListExpr"], #groupByListExpr); }
	;

orderByListExpr
	:	orderByListElement (COMMA! orderByListElement)*
		{ #orderByListExpr = #([ORDER_BY_EXPR,"orderByListExpr"], #orderByListExpr); }
	;

orderByListElement
	:	expression (ASC|DESC)?
		{ #orderByListElement = #([ORDER_ELEMENT_EXPR,"orderByListElement"], #orderByListElement); }
	;

havingClause
	:	evalOrExpression
		{ #havingClause = #([HAVING_EXPR,"havingClause"], #havingClause); }
	;

outputLimit
	:   (ALL|LAST)? EVERY_EXPR! number (e:EVENTS!|sec:SECONDS!|min:MINUTES!)
		{ 
			if (e != null) #outputLimit = #([EVENT_LIMIT_EXPR,"outputLimitEvent"], #outputLimit); 
			if (sec != null) #outputLimit = #([SEC_LIMIT_EXPR,"outputLimitSec"], #outputLimit); 
			if (min != null) #outputLimit = #([MIN_LIMIT_EXPR,"outputLimitMin"], #outputLimit);
		}
	;	

// Main expression rule
expression
	: evalOrExpression
	;

evalOrExpression
	: evalAndExpression (op:OR_EXPR! evalAndExpression)*
		{ if (op != null)
		  #evalOrExpression = #([EVAL_OR_EXPR,"evalOrExpression"], #evalOrExpression);
		}		
	;

evalAndExpression
	:	bitWiseExpression (op:AND_EXPR! bitWiseExpression)*
		{ if (op != null)
		  #evalAndExpression = #([EVAL_AND_EXPR,"evalAndExpression"], #evalAndExpression);
		}		
	;

bitWiseExpression
	: negatedExpression ( (BAND^|BOR^|BXOR^) negatedExpression)*
	;		

negatedExpression
	: evalEqualsExpression 
	| NOT_EXPR^ evalEqualsExpression
	;		


evalEqualsExpression
	:	evalRelationalExpression ( 
			(eq:EQUALS! 
		  |  is:IS!
		  |  isnot:IS! NOT_EXPR!
		  |  sqlne:SQL_NE!
		  |  ne:NOT_EQUAL!
		    ) evalRelationalExpression)*
		{ 
			if ((eq != null) || (is != null))
			{
				#evalEqualsExpression = #([EVAL_EQUALS_EXPR,"evalEqualsExpression"], #evalEqualsExpression); 
			}
			if ((isnot != null) || (sqlne != null) || (ne != null))
			{
				#evalEqualsExpression = #([EVAL_NOTEQUALS_EXPR,"evalEqualsExpression"], #evalEqualsExpression); 
		  	}
		}
	;
	
evalRelationalExpression
	: concatenationExpr ( (LT^|GT^|LE^|GE^) concatenationExpr )*
	;
		
concatenationExpr
	: additiveExpression ( c:LOR! additiveExpression ( LOR! additiveExpression)* )?
		{
			if (c != null) #concatenationExpr = #([CONCAT,"concatenationExpr"], #concatenationExpr); 
		}
	;

additiveExpression
	: multiplyExpression ( (PLUS^|MINUS^) multiplyExpression )*
	;

multiplyExpression
	: unaryExpression ( (STAR^|DIV^|MOD^) unaryExpression )*
	;
	
unaryExpression
	: MINUS^ {#MINUS.setType(UNARY_MINUS);} eventProperty
	| eventPropertyOrLibFunction
	| constant
	| LPAREN! expression RPAREN!
	| builtinFunc
	;
	
builtinFunc
	: (MAX^ | MIN^) LPAREN! (ALL! | DISTINCT)? expression (COMMA! expression (COMMA! expression)* )? RPAREN!
	| SUM^ LPAREN! (ALL! | DISTINCT)? expression RPAREN!
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
	;
	
eventPropertyOrLibFunction
	: (eventProperty) => eventProperty
	| libFunction
	;
	
libFunction
	: classIdentifierNonGreedy DOT! funcIdent LPAREN! (libFunctionArgs)? RPAREN!
	{ #libFunction = #([LIB_FUNCTION,"libFunction"], #libFunction); }	
	;	
	
funcIdent
	: IDENT
	| MAX {#MAX.setType(IDENT);}
	| MIN {#MIN.setType(IDENT);}
	;
	
libFunctionArgs
	: expression (COMMA! expression)*
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
	:	orExpression (op:FOLLOWED_BY! orExpression)*
		{ if (op != null) 
		   #followedByExpression = #([FOLLOWED_BY_EXPR,"followedByExpression"], #followedByExpression);
		}
	;
	
orExpression
	:	andExpression (op:OR_EXPR! andExpression)*
		{ if (op != null) 
		   #orExpression = #([OR_EXPR,"orExpression"], #orExpression);
		}
	;

andExpression
	:	qualifyExpression (op:AND_EXPR! qualifyExpression)*
		{ if (op != null)
		   #andExpression = #([AND_EXPR,"andExpression"], #andExpression);
		}
	;

qualifyExpression
	:	(EVERY_EXPR^ | NOT_EXPR^)?
		guardPostFix
	;
	
guardPostFix
	:	(atomicExpression | LPAREN! patternExpression RPAREN!)
		(op:WHERE! guardExpression)?
		{ if (op != null)
		   #guardPostFix = #([GUARD_EXPR,"guardPostFix"], #guardPostFix);
		}
	;

atomicExpression
	:	observerExpression | eventFilterExpression
	;
		
observerExpression
	:	IDENT COLON! IDENT LPAREN! (parameterSet)? RPAREN!
		{ #observerExpression = #([OBSERVER_EXPR, "observerExpression"], #observerExpression); }
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
	|	STAR^
	|	constant
	;

frequencyOperand
	:	STAR! DIV! NUM_INT
		{ #frequencyOperand = #([NUMERIC_PARAM_FREQUENCY, "frequencyOperand"], #frequencyOperand); }
	;

rangeOperand
	:	NUM_INT COLON! NUM_INT
		{ #rangeOperand = #([NUMERIC_PARAM_RANGE, "rangeOperand"], #rangeOperand); }
	;

numericParameterList
	:	LBRACK! numericListParameter (COMMA! numericListParameter)* RBRACK!
		{ #numericParameterList = #([NUMERIC_PARAM_LIST, "numericParameterList"], #numericParameterList); }
	;

numericListParameter
	:	rangeOperand
	| 	frequencyOperand
	|	NUM_INT
	;

arrayParameterList
	:	LCURLY! (constant (COMMA! constant)*)? RCURLY!
		{ #arrayParameterList = #([ARRAY_PARAM_LIST,"arrayParameterList"], #arrayParameterList); }
	;

//----------------------------------------------------------------------------
// Filter expressions
//   Operators are the usual bunch =, <, >, =<, >= 
//	 Ranges such as 'property in [a,b]' are allowed and ([ and )] distinguish open/closed range endpoints
//----------------------------------------------------------------------------
eventFilterExpression
    :   ( i:IDENT! EQUALS! { #i.setType(EVENT_FILTER_NAME_TAG); } )?
    	(c:classIdentifier!)
       	(LPAREN! (s:filterParamSet!)? RPAREN!)?
       	{ #eventFilterExpression = #([EVENT_FILTER_EXPR,"eventFilterExpression"], #i, #c, #s); }
    ;

classIdentifier
		{ String identifier = ""; }
	:	i1:IDENT! { identifier = #i1.getText(); }
	    ( 
	    	// Ambigous since exit block may also be identifier, not yet seen a problem testing this
	    	// so suppressing warning here.
	    	options 
	    	{ 	
	    		warnWhenFollowAmbig=false; 
	    	} 
	    	: DOT! i2:IDENT! { identifier += "." + #i2.getText(); } 
	    )* 
	    { #classIdentifier = #([CLASS_IDENT,"classIdentifier"], #classIdentifier); 
	      #classIdentifier.setText(identifier);
	    }
	;
	
classIdentifierNonGreedy
		{ String identifier = ""; }
	:	i1:IDENT! { identifier = #i1.getText(); }
	    ( 
	    	// Ambigous since exit block may also be identifier, not yet seen a problem testing this
	    	// so suppressing warning here.
	    	options 
	    	{ 	
	    		warnWhenFollowAmbig=false; 
	    		greedy=false;
	    	} 
	    	: DOT! i2:IDENT! { identifier += "." + #i2.getText(); } 
	    )* 
	    { #classIdentifierNonGreedy = #([CLASS_IDENT,"classIdentifierNonGreedy"], #classIdentifierNonGreedy); 
	      #classIdentifierNonGreedy.setText(identifier);
	    }
	;	
	
filterParamSet
    :   filterParameter (COMMA! filterParameter)*
    ;
   
filterParameter
	:	eventProperty (filterParamConstant | filterParamRange)
		{ #filterParameter = #([EVENT_FILTER_PARAM,"filterParameter"], #filterParameter); }
	;
	
filterParamConstant 
	:	(EQUALS^ | LT^ | LE^ | GE^ | GT^) (constant | filterIdentifier)
	;

filterParamRange 
	: 	IN^ (l1:LPAREN! | l2:LBRACK!) (c:constant | f1:filterIdentifier) COLON! (constant | filterIdentifier) (r1:RPAREN! | r2:RBRACK!)
       { #filterParamRange = #(IN, #l1, #l2, #c, #f1, #r1, #r2); }
	;    

filterIdentifier
	:	IDENT DOT! IDENT
		{ #filterIdentifier = #([EVENT_FILTER_IDENT,"filterIdentifier"], #filterIdentifier); }
	;
	
eventProperty
	:	eventPropertyAtomic (DOT! eventPropertyAtomic)* 
		{ #eventProperty = #([EVENT_PROP_EXPR,"eventPropertyExpr"], #eventProperty); }
	;
	
eventPropertyAtomic
	:	IDENT 
		{ #eventPropertyAtomic = #([EVENT_PROP_SIMPLE,"eventPropertySimple"], #eventPropertyAtomic); }
	|	IDENT LBRACK! NUM_INT RBRACK!
		{ #eventPropertyAtomic = #([EVENT_PROP_INDEXED,"eventPropertyIndexed"], #eventPropertyAtomic); }
	|	IDENT LPAREN! (STRING_LITERAL | QUOTED_STRING_LITERAL) RPAREN!
		{ #eventPropertyAtomic = #([EVENT_PROP_MAPPED,"eventPropertyMapped"], #eventPropertyAtomic); }
	;
	
//----------------------------------------------------------------------------
// Using Java lexer here as Java tokens are applicable.
// No changes were made the orginial ANTLR 2.7.2 Java 1.3 grammer except the name used in the exportVocab option.
// Additional operators were also added.
//
// From ANTLR examples - The Java scanner
//----------------------------------------------------------------------------
class EQLStatementLexer extends Lexer;

options {
	exportVocab=Eql;      // call the vocabulary "Java"
	testLiterals=false;    // don't automatically test for literals
	k=4;                   // four characters of lookahead
	charVocabulary='\u0003'..'\u7FFE';
	// without inlining some bitset tests, couldn't do unicode;
	// I need to make ANTLR generate smaller bitsets; see
	// bottom of JavaLexer.java
	codeGenBitsetTestThreshold=20;
}

// Operators
FOLLOWED_BY		:	"->"	;
EQUALS			:	'='		;
SQL_NE			: 	"<>"	;

// OPERATORS
QUESTION		:	'?'		;
LPAREN			:	'('		;
RPAREN			:	')'		;
LBRACK			:	'['		;
RBRACK			:	']'		;
LCURLY			:	'{'		;
RCURLY			:	'}'		;
COLON			:	':'		;
COMMA			:	','		;
//DOT			    :	'.'		;
//ASSIGN			:	'='		;
EQUAL			:	"=="	;
LNOT			:	'!'		;
BNOT			:	'~'		;
NOT_EQUAL		:	"!="	;
DIV				:	'/'		;
DIV_ASSIGN		:	"/="	;
PLUS			:	'+'		;
PLUS_ASSIGN		:	"+="	;
INC				:	"++"	;
MINUS			:	'-'		;
MINUS_ASSIGN	:	"-="	;
DEC				:	"--"	;
STAR			:	'*'		;
STAR_ASSIGN		:	"*="	;
MOD				:	'%'		;
MOD_ASSIGN		:	"%="	;
SR				:	">>"	;
SR_ASSIGN		:	">>="	;
BSR				:	">>>"	;
BSR_ASSIGN		:	">>>="	;
GE				:	">="	;
GT				:	">"		;
SL				:	"<<"	;
SL_ASSIGN		:	"<<="	;
LE				:	"<="	;
LT				:	'<'		;
BXOR			:	'^'		;
BXOR_ASSIGN		:	"^="	;
BOR				:	'|'		;
BOR_ASSIGN		:	"|="	;
LOR				:	"||"	;
BAND			:	'&'		;
BAND_ASSIGN		:	"&="	;
LAND			:	"&&"	;
SEMI			:	';'		;


// Whitespace -- ignored
WS	:	(	' '
		|	'\t'
		|	'\f'
			// handle newlines
		|	(	options {generateAmbigWarnings=false;}
			:	"\r\n"  // Evil DOS
			|	'\r'    // Macintosh
			|	'\n'    // Unix (the right way)
			)
			{ newline(); }
		)+
		{ _ttype = Token.SKIP; }
	;

// Single-line comments
SL_COMMENT
	:	"//"
		(~('\n'|'\r'))* ('\n'|'\r'('\n')?)?
		{$setType(Token.SKIP); newline();}
	;

// multiple-line comments
ML_COMMENT
	:	"/*"
		(	/*	'\r' '\n' can be matched in one alternative or by matching
				'\r' in one iteration and '\n' in another.  I am trying to
				handle any flavor of newline that comes in, but the language
				that allows both "\r\n" and "\r" and "\n" to all be valid
				newline is ambiguous.  Consequently, the resulting grammar
				must be ambiguous.  I'm shutting this warning off.
			 */
			options {
				generateAmbigWarnings=false;
			}
		:
			{ LA(2)!='/' }? '*'
		|	'\r' '\n'		{newline();}
		|	'\r'			{newline();}
		|	'\n'			{newline();}
		|	~('*'|'\n'|'\r')
		)*
		"*/"
		{$setType(Token.SKIP);}
	;


// character literals - replaced by quoted string
//CHAR_LITERAL
//	:	'\'' ( ESC | ~('\''|'\n'|'\r'|'\\') ) '\''
//	;

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
protected
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
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
				(
					options {
						warnWhenFollowAmbig = false;
					}
				:	'0'..'7'
				)?
			)?
		|	'4'..'7'
			(
				options {
					warnWhenFollowAmbig = false;
				}
			:	'0'..'7'
			)?
		)
	;


// hexadecimal digit (again, note it's protected!)
protected
HEX_DIGIT
	:	('0'..'9'|'A'..'F'|'a'..'f')
	;


// an identifier.  Note that testLiterals is set to true!  This means
// that after we match the rule, we look in the literals table to see
// if it's a literal or really an identifer
IDENT
	options {testLiterals=true;}
	:	('a'..'z'|'A'..'Z'|'_'|'$') ('a'..'z'|'A'..'Z'|'_'|'0'..'9'|'$')*
	;


// a numeric literal
NUM_INT
	{boolean isDecimal=false; Token t=null;}
    :   '.' {_ttype = DOT;}
            (	('0'..'9')+ (EXPONENT)? (f1:FLOAT_SUFFIX {t=f1;})?
                {
				if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
                	_ttype = NUM_FLOAT;
				}
				else {
                	_ttype = NUM_DOUBLE; // assume double
				}
				}
            )?

	|	(	'0' {isDecimal = true;} // special case for just '0'
			(	('x'|'X')
				(											// hex
					// the 'e'|'E' and float suffix stuff look
					// like hex digits, hence the (...)+ doesn't
					// know when to stop: ambig.  ANTLR resolves
					// it correctly by matching immediately.  It
					// is therefor ok to hush warning.
					options {
						warnWhenFollowAmbig=false;
					}
				:	HEX_DIGIT
				)+

			|	//float or double with leading zero
				(('0'..'9')+ ('.'|EXPONENT|FLOAT_SUFFIX)) => ('0'..'9')+

			|	('0'..'7')+									// octal
			)?
		|	('1'..'9') ('0'..'9')*  {isDecimal=true;}		// non-zero decimal
		)
		(	('l'|'L') { _ttype = NUM_LONG; }

		// only check to see if it's a float if looks like decimal so far
		|	{isDecimal}?
            (   '.' ('0'..'9')* (EXPONENT)? (f2:FLOAT_SUFFIX {t=f2;})?
            |   EXPONENT (f3:FLOAT_SUFFIX {t=f3;})?
            |   f4:FLOAT_SUFFIX {t=f4;}
            )
            {
			if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
                _ttype = NUM_FLOAT;
			}
            else {
	           	_ttype = NUM_DOUBLE; // assume double
			}
			}
        )?
	;


// a couple protected methods to assist in matching floating point numbers
protected
EXPONENT
	:	('e'|'E') ('+'|'-')? ('0'..'9')+
	;


protected
FLOAT_SUFFIX
	:	'f'|'F'|'d'|'D'
	;
