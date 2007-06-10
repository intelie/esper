// $ANTLR 2.7.7 (20060930): "eql.g" -> "EQLStatementParser.cs"$


namespace net.esper.eql.generated
{
	// Generate the header common to all output files.
	using System;
	
	using TokenBuffer              = antlr.TokenBuffer;
	using TokenStreamException     = antlr.TokenStreamException;
	using TokenStreamIOException   = antlr.TokenStreamIOException;
	using ANTLRException           = antlr.ANTLRException;
	using LLkParser = antlr.LLkParser;
	using Token                    = antlr.Token;
	using IToken                   = antlr.IToken;
	using TokenStream              = antlr.TokenStream;
	using RecognitionException     = antlr.RecognitionException;
	using NoViableAltException     = antlr.NoViableAltException;
	using MismatchedTokenException = antlr.MismatchedTokenException;
	using SemanticException        = antlr.SemanticException;
	using ParserSharedInputState   = antlr.ParserSharedInputState;
	using BitSet                   = antlr.collections.impl.BitSet;
	using AST                      = antlr.collections.AST;
	using ASTPair                  = antlr.ASTPair;
	using ASTFactory               = antlr.ASTFactory;
	using ASTArray                 = antlr.collections.impl.ASTArray;
	
	public 	class EQLStatementParser : antlr.LLkParser
	{
		public const int EOF = 1;
		public const int NULL_TREE_LOOKAHEAD = 3;
		public const int IN_SET = 4;
		public const int BETWEEN = 5;
		public const int LIKE = 6;
		public const int REGEXP = 7;
		public const int ESCAPE = 8;
		public const int OR_EXPR = 9;
		public const int AND_EXPR = 10;
		public const int NOT_EXPR = 11;
		public const int EVERY_EXPR = 12;
		public const int WHERE = 13;
		public const int AS = 14;
		public const int SUM = 15;
		public const int AVG = 16;
		public const int MAX = 17;
		public const int MIN = 18;
		public const int COALESCE = 19;
		public const int MEDIAN = 20;
		public const int STDDEV = 21;
		public const int AVEDEV = 22;
		public const int COUNT = 23;
		public const int SELECT = 24;
		public const int CASE = 25;
		public const int CASE2 = 26;
		public const int ELSE = 27;
		public const int WHEN = 28;
		public const int THEN = 29;
		public const int END = 30;
		public const int FROM = 31;
		public const int OUTER = 32;
		public const int JOIN = 33;
		public const int LEFT = 34;
		public const int RIGHT = 35;
		public const int FULL = 36;
		public const int ON = 37;
		public const int IS_ = 38;
		public const int BY = 39;
		public const int GROUP = 40;
		public const int HAVING = 41;
		public const int DISTINCT = 42;
		public const int ALL = 43;
		public const int OUTPUT = 44;
		public const int EVENTS = 45;
		public const int SECONDS = 46;
		public const int MINUTES = 47;
		public const int FIRST = 48;
		public const int LAST = 49;
		public const int INSERT = 50;
		public const int INTO = 51;
		public const int ORDER = 52;
		public const int ASC = 53;
		public const int DESC = 54;
		public const int RSTREAM = 55;
		public const int ISTREAM = 56;
		public const int PATTERN = 57;
		public const int SQL = 58;
		public const int PREVIOUS = 59;
		public const int PRIOR = 60;
		public const int EXISTS = 61;
		public const int NUMERIC_PARAM_RANGE = 62;
		public const int NUMERIC_PARAM_LIST = 63;
		public const int NUMERIC_PARAM_FREQUENCY = 64;
		public const int FOLLOWED_BY_EXPR = 65;
		public const int ARRAY_PARAM_LIST = 66;
		public const int EVENT_FILTER_EXPR = 67;
		public const int EVENT_FILTER_NAME_TAG = 68;
		public const int EVENT_FILTER_IDENT = 69;
		public const int EVENT_FILTER_PARAM = 70;
		public const int EVENT_FILTER_RANGE = 71;
		public const int EVENT_FILTER_NOT_RANGE = 72;
		public const int EVENT_FILTER_IN = 73;
		public const int EVENT_FILTER_NOT_IN = 74;
		public const int EVENT_FILTER_BETWEEN = 75;
		public const int EVENT_FILTER_NOT_BETWEEN = 76;
		public const int CLASS_IDENT = 77;
		public const int GUARD_EXPR = 78;
		public const int OBSERVER_EXPR = 79;
		public const int VIEW_EXPR = 80;
		public const int PATTERN_INCL_EXPR = 81;
		public const int DATABASE_JOIN_EXPR = 82;
		public const int WHERE_EXPR = 83;
		public const int HAVING_EXPR = 84;
		public const int EVAL_BITWISE_EXPR = 85;
		public const int EVAL_AND_EXPR = 86;
		public const int EVAL_OR_EXPR = 87;
		public const int EVAL_EQUALS_EXPR = 88;
		public const int EVAL_NOTEQUALS_EXPR = 89;
		public const int EVAL_IDENT = 90;
		public const int SELECTION_EXPR = 91;
		public const int SELECTION_ELEMENT_EXPR = 92;
		public const int STREAM_EXPR = 93;
		public const int OUTERJOIN_EXPR = 94;
		public const int LEFT_OUTERJOIN_EXPR = 95;
		public const int RIGHT_OUTERJOIN_EXPR = 96;
		public const int FULL_OUTERJOIN_EXPR = 97;
		public const int GROUP_BY_EXPR = 98;
		public const int ORDER_BY_EXPR = 99;
		public const int ORDER_ELEMENT_EXPR = 100;
		public const int EVENT_PROP_EXPR = 101;
		public const int EVENT_PROP_SIMPLE = 102;
		public const int EVENT_PROP_MAPPED = 103;
		public const int EVENT_PROP_INDEXED = 104;
		public const int EVENT_LIMIT_EXPR = 105;
		public const int SEC_LIMIT_EXPR = 106;
		public const int MIN_LIMIT_EXPR = 107;
		public const int INSERTINTO_EXPR = 108;
		public const int INSERTINTO_EXPRCOL = 109;
		public const int CONCAT = 110;
		public const int LIB_FUNCTION = 111;
		public const int UNARY_MINUS = 112;
		public const int TIME_PERIOD = 113;
		public const int ARRAY_EXPR = 114;
		public const int DAY_PART = 115;
		public const int HOUR_PART = 116;
		public const int MINUTE_PART = 117;
		public const int SECOND_PART = 118;
		public const int MILLISECOND_PART = 119;
		public const int NOT_IN_SET = 120;
		public const int NOT_BETWEEN = 121;
		public const int NOT_LIKE = 122;
		public const int NOT_REGEXP = 123;
		public const int DBSELECT_EXPR = 124;
		public const int DBFROM_CLAUSE = 125;
		public const int DBWHERE_CLAUSE = 126;
		public const int WILDCARD_SELECT = 127;
		public const int INSERTINTO_STREAM_NAME = 128;
		public const int IN_RANGE = 129;
		public const int NOT_IN_RANGE = 130;
		public const int SUBSELECT_EXPR = 131;
		public const int EXISTS_SUBSELECT_EXPR = 132;
		public const int IN_SUBSELECT_EXPR = 133;
		public const int NOT_IN_SUBSELECT_EXPR = 134;
		public const int IN_SUBSELECT_QUERY_EXPR = 135;
		public const int INT_TYPE = 136;
		public const int LONG_TYPE = 137;
		public const int FLOAT_TYPE = 138;
		public const int DOUBLE_TYPE = 139;
		public const int STRING_TYPE = 140;
		public const int BOOL_TYPE = 141;
		public const int NULL_TYPE = 142;
		public const int NUM_INT = 143;
		public const int NUM_LONG = 144;
		public const int NUM_FLOAT = 145;
		public const int NUM_DOUBLE = 146;
		public const int MINUS = 147;
		public const int PLUS = 148;
		public const int LITERAL_true = 149;
		public const int LITERAL_false = 150;
		public const int LITERAL_null = 151;
		public const int STRING_LITERAL = 152;
		public const int QUOTED_STRING_LITERAL = 153;
		public const int IDENT = 154;
		public const int LPAREN = 155;
		public const int COMMA = 156;
		public const int RPAREN = 157;
		public const int EQUALS = 158;
		public const int STAR = 159;
		public const int DOT = 160;
		public const int LBRACK = 161;
		public const int RBRACK = 162;
		public const int COLON = 163;
		public const int BAND = 164;
		public const int BOR = 165;
		public const int BXOR = 166;
		public const int SQL_NE = 167;
		public const int NOT_EQUAL = 168;
		public const int LT_ = 169;
		public const int GT = 170;
		public const int LE = 171;
		public const int GE = 172;
		public const int LOR = 173;
		public const int DIV = 174;
		public const int MOD = 175;
		public const int LCURLY = 176;
		public const int RCURLY = 177;
		public const int FOLLOWED_BY = 178;
		public const int LITERAL_days = 179;
		public const int LITERAL_day = 180;
		public const int LITERAL_hours = 181;
		public const int LITERAL_hour = 182;
		public const int LITERAL_minute = 183;
		public const int LITERAL_second = 184;
		public const int LITERAL_sec = 185;
		public const int LITERAL_milliseconds = 186;
		public const int LITERAL_millisecond = 187;
		public const int LITERAL_msec = 188;
		public const int QUESTION = 189;
		public const int EQUAL = 190;
		public const int LNOT = 191;
		public const int BNOT = 192;
		public const int DIV_ASSIGN = 193;
		public const int PLUS_ASSIGN = 194;
		public const int INC = 195;
		public const int MINUS_ASSIGN = 196;
		public const int DEC = 197;
		public const int STAR_ASSIGN = 198;
		public const int MOD_ASSIGN = 199;
		public const int SR = 200;
		public const int SR_ASSIGN = 201;
		public const int BSR = 202;
		public const int BSR_ASSIGN = 203;
		public const int SL = 204;
		public const int SL_ASSIGN = 205;
		public const int BXOR_ASSIGN = 206;
		public const int BOR_ASSIGN = 207;
		public const int BAND_ASSIGN = 208;
		public const int LAND = 209;
		public const int SEMI = 210;
		public const int WS = 211;
		public const int SL_COMMENT = 212;
		public const int ML_COMMENT = 213;
		public const int ESC = 214;
		public const int HEX_DIGIT = 215;
		public const int EXPONENT = 216;
		public const int FLOAT_SUFFIX = 217;
		
		
		protected void initialize()
		{
			tokenNames = tokenNames_;
			initializeFactory();
		}
		
		
		protected EQLStatementParser(TokenBuffer tokenBuf, int k) : base(tokenBuf, k)
		{
			initialize();
		}
		
		public EQLStatementParser(TokenBuffer tokenBuf) : this(tokenBuf,4)
		{
		}
		
		protected EQLStatementParser(TokenStream lexer, int k) : base(lexer,k)
		{
			initialize();
		}
		
		public EQLStatementParser(TokenStream lexer) : this(lexer,4)
		{
		}
		
		public EQLStatementParser(ParserSharedInputState state) : base(state,4)
		{
			initialize();
		}
		
	public void startPatternExpressionRule() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST startPatternExpressionRule_AST = null;
		
		patternExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(Token.EOF_TYPE);
		startPatternExpressionRule_AST = currentAST.root;
		returnAST = startPatternExpressionRule_AST;
	}
	
	public void patternExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST patternExpression_AST = null;
		
		followedByExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		patternExpression_AST = currentAST.root;
		returnAST = patternExpression_AST;
	}
	
	public void startEQLExpressionRule() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST startEQLExpressionRule_AST = null;
		
		eqlExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(Token.EOF_TYPE);
		startEQLExpressionRule_AST = currentAST.root;
		returnAST = startEQLExpressionRule_AST;
	}
	
	public void eqlExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eqlExpression_AST = null;
		
		{
			switch ( LA(1) )
			{
			case INSERT:
			{
				match(INSERT);
				insertIntoExpr();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case SELECT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(SELECT);
		selectClause();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(FROM);
		streamExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case EOF:
			case WHERE:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			case COMMA:
			{
				regularJoin();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case LEFT:
			case RIGHT:
			case FULL:
			{
				outerJoinList();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case WHERE:
			{
				match(WHERE);
				whereClause();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case EOF:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case GROUP:
			{
				match(GROUP);
				match(BY);
				groupByListExpr();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case EOF:
			case HAVING:
			case OUTPUT:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case HAVING:
			{
				match(HAVING);
				havingClause();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case EOF:
			case OUTPUT:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case OUTPUT:
			{
				match(OUTPUT);
				outputLimit();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case EOF:
			case ORDER:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case ORDER:
			{
				match(ORDER);
				match(BY);
				orderByListExpr();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case EOF:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		eqlExpression_AST = currentAST.root;
		returnAST = eqlExpression_AST;
	}
	
	public void startEventPropertyRule() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST startEventPropertyRule_AST = null;
		
		eventProperty();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(Token.EOF_TYPE);
		startEventPropertyRule_AST = currentAST.root;
		returnAST = startEventPropertyRule_AST;
	}
	
	public void eventProperty() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventProperty_AST = null;
		
		eventPropertyAtomic();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==DOT))
				{
					match(DOT);
					eventPropertyAtomic();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop217_breakloop;
				}
				
			}
_loop217_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			eventProperty_AST = (AST)currentAST.root;
			eventProperty_AST = (AST) astFactory.make(astFactory.create(EVENT_PROP_EXPR,"eventPropertyExpr"), eventProperty_AST);
			currentAST.root = eventProperty_AST;
			if ( (null != eventProperty_AST) && (null != eventProperty_AST.getFirstChild()) )
				currentAST.child = eventProperty_AST.getFirstChild();
			else
				currentAST.child = eventProperty_AST;
			currentAST.advanceChildToEnd();
		}
		eventProperty_AST = currentAST.root;
		returnAST = eventProperty_AST;
	}
	
	public void number() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST number_AST = null;
		
		switch ( LA(1) )
		{
		case NUM_INT:
		{
			AST tmp15_AST = null;
			tmp15_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp15_AST);
			match(NUM_INT);
			if (0==inputState.guessing)
			{
				number_AST = (AST)currentAST.root;
				number_AST.setType(INT_TYPE);
			}
			number_AST = currentAST.root;
			break;
		}
		case NUM_LONG:
		{
			AST tmp16_AST = null;
			tmp16_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp16_AST);
			match(NUM_LONG);
			if (0==inputState.guessing)
			{
				number_AST = (AST)currentAST.root;
				number_AST.setType(LONG_TYPE);
			}
			number_AST = currentAST.root;
			break;
		}
		case NUM_FLOAT:
		{
			AST tmp17_AST = null;
			tmp17_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp17_AST);
			match(NUM_FLOAT);
			if (0==inputState.guessing)
			{
				number_AST = (AST)currentAST.root;
				number_AST.setType(FLOAT_TYPE);
			}
			number_AST = currentAST.root;
			break;
		}
		case NUM_DOUBLE:
		{
			AST tmp18_AST = null;
			tmp18_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp18_AST);
			match(NUM_DOUBLE);
			if (0==inputState.guessing)
			{
				number_AST = (AST)currentAST.root;
				number_AST.setType(DOUBLE_TYPE);
			}
			number_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = number_AST;
	}
	
	public void constant() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_AST = null;
		IToken  m = null;
		AST m_AST = null;
		AST n_AST = null;
		
		switch ( LA(1) )
		{
		case NUM_INT:
		case NUM_LONG:
		case NUM_FLOAT:
		case NUM_DOUBLE:
		case MINUS:
		case PLUS:
		{
			{
				switch ( LA(1) )
				{
				case MINUS:
				{
					m = LT(1);
					m_AST = astFactory.create(m);
					match(MINUS);
					break;
				}
				case PLUS:
				{
					match(PLUS);
					break;
				}
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			number();
			if (0 == inputState.guessing)
			{
				n_AST = (AST)returnAST;
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			if (0==inputState.guessing)
			{
				constant_AST = (AST)currentAST.root;
				constant_AST.setType(n_AST.getType()); 
					                                   constant_AST.setText( (m == null) ? n_AST.getText() : "-" + n_AST.getText()); 
					
			}
			constant_AST = currentAST.root;
			break;
		}
		case STRING_LITERAL:
		case QUOTED_STRING_LITERAL:
		{
			stringconstant();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			constant_AST = currentAST.root;
			break;
		}
		case LITERAL_true:
		{
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp20_AST);
			match(LITERAL_true);
			if (0==inputState.guessing)
			{
				constant_AST = (AST)currentAST.root;
				constant_AST.setType(BOOL_TYPE);
			}
			constant_AST = currentAST.root;
			break;
		}
		case LITERAL_false:
		{
			AST tmp21_AST = null;
			tmp21_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp21_AST);
			match(LITERAL_false);
			if (0==inputState.guessing)
			{
				constant_AST = (AST)currentAST.root;
				constant_AST.setType(BOOL_TYPE);
			}
			constant_AST = currentAST.root;
			break;
		}
		case LITERAL_null:
		{
			AST tmp22_AST = null;
			tmp22_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp22_AST);
			match(LITERAL_null);
			if (0==inputState.guessing)
			{
				constant_AST = (AST)currentAST.root;
				constant_AST.setType(NULL_TYPE); constant_AST.setText(null);
			}
			constant_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = constant_AST;
	}
	
	public void stringconstant() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST stringconstant_AST = null;
		
		switch ( LA(1) )
		{
		case STRING_LITERAL:
		{
			AST tmp23_AST = null;
			tmp23_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp23_AST);
			match(STRING_LITERAL);
			if (0==inputState.guessing)
			{
				stringconstant_AST = (AST)currentAST.root;
				stringconstant_AST.setType(STRING_TYPE);
			}
			stringconstant_AST = currentAST.root;
			break;
		}
		case QUOTED_STRING_LITERAL:
		{
			AST tmp24_AST = null;
			tmp24_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp24_AST);
			match(QUOTED_STRING_LITERAL);
			if (0==inputState.guessing)
			{
				stringconstant_AST = (AST)currentAST.root;
				stringconstant_AST.setType(STRING_TYPE);
			}
			stringconstant_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = stringconstant_AST;
	}
	
	public void insertIntoExpr() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST insertIntoExpr_AST = null;
		
		{
			switch ( LA(1) )
			{
			case ISTREAM:
			{
				AST tmp25_AST = null;
				tmp25_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp25_AST);
				match(ISTREAM);
				break;
			}
			case RSTREAM:
			{
				AST tmp26_AST = null;
				tmp26_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp26_AST);
				match(RSTREAM);
				break;
			}
			case INTO:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(INTO);
		AST tmp28_AST = null;
		tmp28_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp28_AST);
		match(IDENT);
		{
			switch ( LA(1) )
			{
			case LPAREN:
			{
				insertIntoColumnList();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case SELECT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			insertIntoExpr_AST = (AST)currentAST.root;
			insertIntoExpr_AST = (AST) astFactory.make(astFactory.create(INSERTINTO_EXPR,"insertIntoExpr"), insertIntoExpr_AST);
			currentAST.root = insertIntoExpr_AST;
			if ( (null != insertIntoExpr_AST) && (null != insertIntoExpr_AST.getFirstChild()) )
				currentAST.child = insertIntoExpr_AST.getFirstChild();
			else
				currentAST.child = insertIntoExpr_AST;
			currentAST.advanceChildToEnd();
		}
		insertIntoExpr_AST = currentAST.root;
		returnAST = insertIntoExpr_AST;
	}
	
	public void selectClause() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectClause_AST = null;
		IToken  r = null;
		AST r_AST = null;
		IToken  i = null;
		AST i_AST = null;
		AST l_AST = null;
		
		{
			switch ( LA(1) )
			{
			case RSTREAM:
			{
				r = LT(1);
				r_AST = astFactory.create(r);
				match(RSTREAM);
				break;
			}
			case ISTREAM:
			{
				i = LT(1);
				i_AST = astFactory.create(i);
				match(ISTREAM);
				break;
			}
			case NOT_EXPR:
			case SUM:
			case AVG:
			case MAX:
			case MIN:
			case COALESCE:
			case MEDIAN:
			case STDDEV:
			case AVEDEV:
			case COUNT:
			case CASE:
			case PREVIOUS:
			case PRIOR:
			case EXISTS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case IDENT:
			case LPAREN:
			case STAR:
			case LCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		selectionList();
		if (0 == inputState.guessing)
		{
			l_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			selectClause_AST = (AST)currentAST.root;
			selectClause_AST = (AST) astFactory.make(astFactory.create(SELECTION_EXPR,"selectClause"), r_AST, i_AST, l_AST);
			currentAST.root = selectClause_AST;
			if ( (null != selectClause_AST) && (null != selectClause_AST.getFirstChild()) )
				currentAST.child = selectClause_AST.getFirstChild();
			else
				currentAST.child = selectClause_AST;
			currentAST.advanceChildToEnd();
		}
		selectClause_AST = currentAST.root;
		returnAST = selectClause_AST;
	}
	
	public void streamExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST streamExpression_AST = null;
		
		{
			switch ( LA(1) )
			{
			case IDENT:
			{
				eventFilterExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case PATTERN:
			{
				patternInclusionExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case SQL:
			{
				databaseJoinExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case DOT:
			{
				match(DOT);
				viewExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{    // ( ... )*
					for (;;)
					{
						if ((LA(1)==DOT))
						{
							match(DOT);
							viewExpression();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						else
						{
							goto _loop43_breakloop;
						}
						
					}
_loop43_breakloop:					;
				}    // ( ... )*
				break;
			}
			case EOF:
			case WHERE:
			case AS:
			case LEFT:
			case RIGHT:
			case FULL:
			case ON:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			case IDENT:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case AS:
			{
				match(AS);
				AST tmp32_AST = null;
				tmp32_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp32_AST);
				match(IDENT);
				break;
			}
			case IDENT:
			{
				AST tmp33_AST = null;
				tmp33_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp33_AST);
				match(IDENT);
				break;
			}
			case EOF:
			case WHERE:
			case LEFT:
			case RIGHT:
			case FULL:
			case ON:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			streamExpression_AST = (AST)currentAST.root;
			streamExpression_AST = (AST) astFactory.make(astFactory.create(STREAM_EXPR,"streamExpression"), streamExpression_AST);
			currentAST.root = streamExpression_AST;
			if ( (null != streamExpression_AST) && (null != streamExpression_AST.getFirstChild()) )
				currentAST.child = streamExpression_AST.getFirstChild();
			else
				currentAST.child = streamExpression_AST;
			currentAST.advanceChildToEnd();
		}
		streamExpression_AST = currentAST.root;
		returnAST = streamExpression_AST;
	}
	
	public void regularJoin() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST regularJoin_AST = null;
		
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					streamExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop24_breakloop;
				}
				
			}
_loop24_breakloop:			;
		}    // ( ... )*
		regularJoin_AST = currentAST.root;
		returnAST = regularJoin_AST;
	}
	
	public void outerJoinList() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outerJoinList_AST = null;
		
		outerJoin();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if (((LA(1) >= LEFT && LA(1) <= FULL)))
				{
					outerJoin();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop27_breakloop;
				}
				
			}
_loop27_breakloop:			;
		}    // ( ... )*
		outerJoinList_AST = currentAST.root;
		returnAST = outerJoinList_AST;
	}
	
	public void whereClause() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST whereClause_AST = null;
		
		evalOrExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			whereClause_AST = (AST)currentAST.root;
			whereClause_AST = (AST) astFactory.make(astFactory.create(WHERE_EXPR,"whereClause"), whereClause_AST);
			currentAST.root = whereClause_AST;
			if ( (null != whereClause_AST) && (null != whereClause_AST.getFirstChild()) )
				currentAST.child = whereClause_AST.getFirstChild();
			else
				currentAST.child = whereClause_AST;
			currentAST.advanceChildToEnd();
		}
		whereClause_AST = currentAST.root;
		returnAST = whereClause_AST;
	}
	
	public void groupByListExpr() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST groupByListExpr_AST = null;
		
		expression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					expression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop52_breakloop;
				}
				
			}
_loop52_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			groupByListExpr_AST = (AST)currentAST.root;
			groupByListExpr_AST = (AST) astFactory.make(astFactory.create(GROUP_BY_EXPR,"groupByListExpr"), groupByListExpr_AST);
			currentAST.root = groupByListExpr_AST;
			if ( (null != groupByListExpr_AST) && (null != groupByListExpr_AST.getFirstChild()) )
				currentAST.child = groupByListExpr_AST.getFirstChild();
			else
				currentAST.child = groupByListExpr_AST;
			currentAST.advanceChildToEnd();
		}
		groupByListExpr_AST = currentAST.root;
		returnAST = groupByListExpr_AST;
	}
	
	public void havingClause() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST havingClause_AST = null;
		
		evalOrExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			havingClause_AST = (AST)currentAST.root;
			havingClause_AST = (AST) astFactory.make(astFactory.create(HAVING_EXPR,"havingClause"), havingClause_AST);
			currentAST.root = havingClause_AST;
			if ( (null != havingClause_AST) && (null != havingClause_AST.getFirstChild()) )
				currentAST.child = havingClause_AST.getFirstChild();
			else
				currentAST.child = havingClause_AST;
			currentAST.advanceChildToEnd();
		}
		havingClause_AST = currentAST.root;
		returnAST = havingClause_AST;
	}
	
	public void outputLimit() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outputLimit_AST = null;
		IToken  e = null;
		AST e_AST = null;
		IToken  sec = null;
		AST sec_AST = null;
		IToken  min = null;
		AST min_AST = null;
		
		{
			switch ( LA(1) )
			{
			case ALL:
			{
				AST tmp36_AST = null;
				tmp36_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp36_AST);
				match(ALL);
				break;
			}
			case FIRST:
			{
				AST tmp37_AST = null;
				tmp37_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp37_AST);
				match(FIRST);
				break;
			}
			case LAST:
			{
				AST tmp38_AST = null;
				tmp38_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp38_AST);
				match(LAST);
				break;
			}
			case EVERY_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(EVERY_EXPR);
		number();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case EVENTS:
			{
				e = LT(1);
				e_AST = astFactory.create(e);
				match(EVENTS);
				break;
			}
			case SECONDS:
			{
				sec = LT(1);
				sec_AST = astFactory.create(sec);
				match(SECONDS);
				break;
			}
			case MINUTES:
			{
				min = LT(1);
				min_AST = astFactory.create(min);
				match(MINUTES);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			outputLimit_AST = (AST)currentAST.root;
			
						if (e != null) outputLimit_AST = (AST) astFactory.make(astFactory.create(EVENT_LIMIT_EXPR,"outputLimitEvent"), outputLimit_AST); 
						if (sec != null) outputLimit_AST = (AST) astFactory.make(astFactory.create(SEC_LIMIT_EXPR,"outputLimitSec"), outputLimit_AST); 
						if (min != null) outputLimit_AST = (AST) astFactory.make(astFactory.create(MIN_LIMIT_EXPR,"outputLimitMin"), outputLimit_AST);
					
			currentAST.root = outputLimit_AST;
			if ( (null != outputLimit_AST) && (null != outputLimit_AST.getFirstChild()) )
				currentAST.child = outputLimit_AST.getFirstChild();
			else
				currentAST.child = outputLimit_AST;
			currentAST.advanceChildToEnd();
		}
		outputLimit_AST = currentAST.root;
		returnAST = outputLimit_AST;
	}
	
	public void orderByListExpr() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orderByListExpr_AST = null;
		
		orderByListElement();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					orderByListElement();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop55_breakloop;
				}
				
			}
_loop55_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			orderByListExpr_AST = (AST)currentAST.root;
			orderByListExpr_AST = (AST) astFactory.make(astFactory.create(ORDER_BY_EXPR,"orderByListExpr"), orderByListExpr_AST);
			currentAST.root = orderByListExpr_AST;
			if ( (null != orderByListExpr_AST) && (null != orderByListExpr_AST.getFirstChild()) )
				currentAST.child = orderByListExpr_AST.getFirstChild();
			else
				currentAST.child = orderByListExpr_AST;
			currentAST.advanceChildToEnd();
		}
		orderByListExpr_AST = currentAST.root;
		returnAST = orderByListExpr_AST;
	}
	
	public void insertIntoColumnList() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST insertIntoColumnList_AST = null;
		
		match(LPAREN);
		AST tmp42_AST = null;
		tmp42_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp42_AST);
		match(IDENT);
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					AST tmp44_AST = null;
					tmp44_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp44_AST);
					match(IDENT);
				}
				else
				{
					goto _loop21_breakloop;
				}
				
			}
_loop21_breakloop:			;
		}    // ( ... )*
		match(RPAREN);
		if (0==inputState.guessing)
		{
			insertIntoColumnList_AST = (AST)currentAST.root;
			insertIntoColumnList_AST = (AST) astFactory.make(astFactory.create(INSERTINTO_EXPRCOL,"insertIntoColumnList"), insertIntoColumnList_AST);
			currentAST.root = insertIntoColumnList_AST;
			if ( (null != insertIntoColumnList_AST) && (null != insertIntoColumnList_AST.getFirstChild()) )
				currentAST.child = insertIntoColumnList_AST.getFirstChild();
			else
				currentAST.child = insertIntoColumnList_AST;
			currentAST.advanceChildToEnd();
		}
		insertIntoColumnList_AST = currentAST.root;
		returnAST = insertIntoColumnList_AST;
	}
	
	public void outerJoin() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outerJoin_AST = null;
		IToken  tl = null;
		AST tl_AST = null;
		IToken  tr = null;
		AST tr_AST = null;
		IToken  tf = null;
		AST tf_AST = null;
		AST i_AST = null;
		
		{
			switch ( LA(1) )
			{
			case LEFT:
			{
				tl = LT(1);
				tl_AST = astFactory.create(tl);
				match(LEFT);
				break;
			}
			case RIGHT:
			{
				tr = LT(1);
				tr_AST = astFactory.create(tr);
				match(RIGHT);
				break;
			}
			case FULL:
			{
				tf = LT(1);
				tf_AST = astFactory.create(tf);
				match(FULL);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(OUTER);
		match(JOIN);
		streamExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		outerJoinIdent();
		if (0 == inputState.guessing)
		{
			i_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			
						if (tl!=null) i_AST.setType(LEFT_OUTERJOIN_EXPR);
						if (tr!=null) i_AST.setType(RIGHT_OUTERJOIN_EXPR);
						if (tf!=null) i_AST.setType(FULL_OUTERJOIN_EXPR);
					
		}
		outerJoin_AST = currentAST.root;
		returnAST = outerJoin_AST;
	}
	
	public void outerJoinIdent() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outerJoinIdent_AST = null;
		
		match(ON);
		eventProperty();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(EQUALS);
		eventProperty();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			outerJoinIdent_AST = (AST)currentAST.root;
			outerJoinIdent_AST = (AST) astFactory.make(astFactory.create(OUTERJOIN_EXPR,"outerJoinIdent"), outerJoinIdent_AST);
			currentAST.root = outerJoinIdent_AST;
			if ( (null != outerJoinIdent_AST) && (null != outerJoinIdent_AST.getFirstChild()) )
				currentAST.child = outerJoinIdent_AST.getFirstChild();
			else
				currentAST.child = outerJoinIdent_AST;
			currentAST.advanceChildToEnd();
		}
		outerJoinIdent_AST = currentAST.root;
		returnAST = outerJoinIdent_AST;
	}
	
	public void evalOrExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST evalOrExpression_AST = null;
		IToken  op = null;
		AST op_AST = null;
		
		evalAndExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==OR_EXPR))
				{
					op = LT(1);
					op_AST = astFactory.create(op);
					match(OR_EXPR);
					evalAndExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop76_breakloop;
				}
				
			}
_loop76_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			evalOrExpression_AST = (AST)currentAST.root;
			if (op != null)
					  evalOrExpression_AST = (AST) astFactory.make(astFactory.create(EVAL_OR_EXPR,"evalOrExpression"), evalOrExpression_AST);
					
			currentAST.root = evalOrExpression_AST;
			if ( (null != evalOrExpression_AST) && (null != evalOrExpression_AST.getFirstChild()) )
				currentAST.child = evalOrExpression_AST.getFirstChild();
			else
				currentAST.child = evalOrExpression_AST;
			currentAST.advanceChildToEnd();
		}
		evalOrExpression_AST = currentAST.root;
		returnAST = evalOrExpression_AST;
	}
	
	public void selectionList() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectionList_AST = null;
		
		selectionListElement();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					selectionListElement();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop36_breakloop;
				}
				
			}
_loop36_breakloop:			;
		}    // ( ... )*
		selectionList_AST = currentAST.root;
		returnAST = selectionList_AST;
	}
	
	public void selectionListElement() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectionListElement_AST = null;
		
		switch ( LA(1) )
		{
		case STAR:
		{
			AST tmp51_AST = null;
			tmp51_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp51_AST);
			match(STAR);
			if (0==inputState.guessing)
			{
				selectionListElement_AST = (AST)currentAST.root;
				selectionListElement_AST = astFactory.create(WILDCARD_SELECT,"wildcard-select");
				currentAST.root = selectionListElement_AST;
				if ( (null != selectionListElement_AST) && (null != selectionListElement_AST.getFirstChild()) )
					currentAST.child = selectionListElement_AST.getFirstChild();
				else
					currentAST.child = selectionListElement_AST;
				currentAST.advanceChildToEnd();
			}
			selectionListElement_AST = currentAST.root;
			break;
		}
		case NOT_EXPR:
		case SUM:
		case AVG:
		case MAX:
		case MIN:
		case COALESCE:
		case MEDIAN:
		case STDDEV:
		case AVEDEV:
		case COUNT:
		case CASE:
		case PREVIOUS:
		case PRIOR:
		case EXISTS:
		case NUM_INT:
		case NUM_LONG:
		case NUM_FLOAT:
		case NUM_DOUBLE:
		case MINUS:
		case PLUS:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case STRING_LITERAL:
		case QUOTED_STRING_LITERAL:
		case IDENT:
		case LPAREN:
		case LCURLY:
		{
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			{
				switch ( LA(1) )
				{
				case AS:
				{
					match(AS);
					AST tmp53_AST = null;
					tmp53_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp53_AST);
					match(IDENT);
					break;
				}
				case FROM:
				case COMMA:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			if (0==inputState.guessing)
			{
				selectionListElement_AST = (AST)currentAST.root;
				selectionListElement_AST = (AST) astFactory.make(astFactory.create(SELECTION_ELEMENT_EXPR,"selectionListElement"), selectionListElement_AST);
				currentAST.root = selectionListElement_AST;
				if ( (null != selectionListElement_AST) && (null != selectionListElement_AST.getFirstChild()) )
					currentAST.child = selectionListElement_AST.getFirstChild();
				else
					currentAST.child = selectionListElement_AST;
				currentAST.advanceChildToEnd();
			}
			selectionListElement_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = selectionListElement_AST;
	}
	
	public void expression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expression_AST = null;
		
		caseExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		expression_AST = currentAST.root;
		returnAST = expression_AST;
	}
	
	public void eventFilterExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventFilterExpression_AST = null;
		IToken  i = null;
		AST i_AST = null;
		AST c_AST = null;
		AST s_AST = null;
		
		{
			if ((LA(1)==IDENT) && (LA(2)==EQUALS))
			{
				i = LT(1);
				i_AST = astFactory.create(i);
				match(IDENT);
				match(EQUALS);
				if (0==inputState.guessing)
				{
					i_AST.setType(EVENT_FILTER_NAME_TAG);
				}
			}
			else if ((LA(1)==IDENT) && (tokenSet_0_.member(LA(2)))) {
			}
			else
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		{
			classIdentifier();
			if (0 == inputState.guessing)
			{
				c_AST = (AST)returnAST;
			}
		}
		{
			switch ( LA(1) )
			{
			case LPAREN:
			{
				match(LPAREN);
				{
					switch ( LA(1) )
					{
					case NOT_EXPR:
					case SUM:
					case AVG:
					case MAX:
					case MIN:
					case COALESCE:
					case MEDIAN:
					case STDDEV:
					case AVEDEV:
					case COUNT:
					case CASE:
					case PREVIOUS:
					case PRIOR:
					case EXISTS:
					case NUM_INT:
					case NUM_LONG:
					case NUM_FLOAT:
					case NUM_DOUBLE:
					case MINUS:
					case PLUS:
					case LITERAL_true:
					case LITERAL_false:
					case LITERAL_null:
					case STRING_LITERAL:
					case QUOTED_STRING_LITERAL:
					case IDENT:
					case LPAREN:
					case LCURLY:
					{
						filterParamSet();
						if (0 == inputState.guessing)
						{
							s_AST = (AST)returnAST;
						}
						break;
					}
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
				}
				match(RPAREN);
				break;
			}
			case EOF:
			case OR_EXPR:
			case AND_EXPR:
			case WHERE:
			case AS:
			case LEFT:
			case RIGHT:
			case FULL:
			case ON:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			case IDENT:
			case COMMA:
			case RPAREN:
			case DOT:
			case RBRACK:
			case FOLLOWED_BY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			eventFilterExpression_AST = (AST)currentAST.root;
			eventFilterExpression_AST = (AST) astFactory.make(astFactory.create(EVENT_FILTER_EXPR,"eventFilterExpression"), i_AST, c_AST, s_AST);
			currentAST.root = eventFilterExpression_AST;
			if ( (null != eventFilterExpression_AST) && (null != eventFilterExpression_AST.getFirstChild()) )
				currentAST.child = eventFilterExpression_AST.getFirstChild();
			else
				currentAST.child = eventFilterExpression_AST;
			currentAST.advanceChildToEnd();
		}
		eventFilterExpression_AST = currentAST.root;
		returnAST = eventFilterExpression_AST;
	}
	
	public void patternInclusionExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST patternInclusionExpression_AST = null;
		
		match(PATTERN);
		match(LBRACK);
		patternExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(RBRACK);
		if (0==inputState.guessing)
		{
			patternInclusionExpression_AST = (AST)currentAST.root;
			patternInclusionExpression_AST = (AST) astFactory.make(astFactory.create(PATTERN_INCL_EXPR,"patternInclusionExpression"), patternInclusionExpression_AST);
			currentAST.root = patternInclusionExpression_AST;
			if ( (null != patternInclusionExpression_AST) && (null != patternInclusionExpression_AST.getFirstChild()) )
				currentAST.child = patternInclusionExpression_AST.getFirstChild();
			else
				currentAST.child = patternInclusionExpression_AST;
			currentAST.advanceChildToEnd();
		}
		patternInclusionExpression_AST = currentAST.root;
		returnAST = patternInclusionExpression_AST;
	}
	
	public void databaseJoinExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST databaseJoinExpression_AST = null;
		
		match(SQL);
		match(COLON);
		AST tmp62_AST = null;
		tmp62_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp62_AST);
		match(IDENT);
		match(LBRACK);
		{
			switch ( LA(1) )
			{
			case STRING_LITERAL:
			{
				AST tmp64_AST = null;
				tmp64_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp64_AST);
				match(STRING_LITERAL);
				break;
			}
			case QUOTED_STRING_LITERAL:
			{
				AST tmp65_AST = null;
				tmp65_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp65_AST);
				match(QUOTED_STRING_LITERAL);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RBRACK);
		if (0==inputState.guessing)
		{
			databaseJoinExpression_AST = (AST)currentAST.root;
			databaseJoinExpression_AST = (AST) astFactory.make(astFactory.create(DATABASE_JOIN_EXPR,"databaseJoinExpression"), databaseJoinExpression_AST);
			currentAST.root = databaseJoinExpression_AST;
			if ( (null != databaseJoinExpression_AST) && (null != databaseJoinExpression_AST.getFirstChild()) )
				currentAST.child = databaseJoinExpression_AST.getFirstChild();
			else
				currentAST.child = databaseJoinExpression_AST;
			currentAST.advanceChildToEnd();
		}
		databaseJoinExpression_AST = currentAST.root;
		returnAST = databaseJoinExpression_AST;
	}
	
	public void viewExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST viewExpression_AST = null;
		
		AST tmp67_AST = null;
		tmp67_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp67_AST);
		match(IDENT);
		match(COLON);
		AST tmp69_AST = null;
		tmp69_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp69_AST);
		match(IDENT);
		match(LPAREN);
		{
			switch ( LA(1) )
			{
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case STAR:
			case LBRACK:
			case LCURLY:
			{
				parameterSet();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RPAREN);
		if (0==inputState.guessing)
		{
			viewExpression_AST = (AST)currentAST.root;
			
					 	viewExpression_AST = (AST) astFactory.make(astFactory.create(VIEW_EXPR,"viewExpression"), viewExpression_AST); 
					
			currentAST.root = viewExpression_AST;
			if ( (null != viewExpression_AST) && (null != viewExpression_AST.getFirstChild()) )
				currentAST.child = viewExpression_AST.getFirstChild();
			else
				currentAST.child = viewExpression_AST;
			currentAST.advanceChildToEnd();
		}
		viewExpression_AST = currentAST.root;
		returnAST = viewExpression_AST;
	}
	
	public void parameterSet() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameterSet_AST = null;
		
		parameter();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					parameter();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop188_breakloop;
				}
				
			}
_loop188_breakloop:			;
		}    // ( ... )*
		parameterSet_AST = currentAST.root;
		returnAST = parameterSet_AST;
	}
	
	public void orderByListElement() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orderByListElement_AST = null;
		
		expression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case ASC:
			{
				AST tmp73_AST = null;
				tmp73_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp73_AST);
				match(ASC);
				break;
			}
			case DESC:
			{
				AST tmp74_AST = null;
				tmp74_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp74_AST);
				match(DESC);
				break;
			}
			case EOF:
			case COMMA:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			orderByListElement_AST = (AST)currentAST.root;
			orderByListElement_AST = (AST) astFactory.make(astFactory.create(ORDER_ELEMENT_EXPR,"orderByListElement"), orderByListElement_AST);
			currentAST.root = orderByListElement_AST;
			if ( (null != orderByListElement_AST) && (null != orderByListElement_AST.getFirstChild()) )
				currentAST.child = orderByListElement_AST.getFirstChild();
			else
				currentAST.child = orderByListElement_AST;
			currentAST.advanceChildToEnd();
		}
		orderByListElement_AST = currentAST.root;
		returnAST = orderByListElement_AST;
	}
	
	public void caseExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST caseExpression_AST = null;
		
		if ((LA(1)==CASE) && (LA(2)==WHEN))
		{
			AST tmp75_AST = null;
			tmp75_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp75_AST);
			match(CASE);
			{ // ( ... )+
				int _cnt65=0;
				for (;;)
				{
					if ((LA(1)==WHEN))
					{
						whenClause();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else
					{
						if (_cnt65 >= 1) { goto _loop65_breakloop; } else { throw new NoViableAltException(LT(1), getFilename());; }
					}
					
					_cnt65++;
				}
_loop65_breakloop:				;
			}    // ( ... )+
			{
				switch ( LA(1) )
				{
				case ELSE:
				{
					elseClause();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					break;
				}
				case END:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			match(END);
			caseExpression_AST = currentAST.root;
		}
		else if ((LA(1)==CASE) && (tokenSet_1_.member(LA(2)))) {
			AST tmp77_AST = null;
			tmp77_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp77_AST);
			match(CASE);
			if (0==inputState.guessing)
			{
				tmp77_AST.setType(CASE2);
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			{ // ( ... )+
				int _cnt68=0;
				for (;;)
				{
					if ((LA(1)==WHEN))
					{
						whenClause();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else
					{
						if (_cnt68 >= 1) { goto _loop68_breakloop; } else { throw new NoViableAltException(LT(1), getFilename());; }
					}
					
					_cnt68++;
				}
_loop68_breakloop:				;
			}    // ( ... )+
			{
				switch ( LA(1) )
				{
				case ELSE:
				{
					elseClause();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					break;
				}
				case END:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			match(END);
			caseExpression_AST = currentAST.root;
		}
		else if ((tokenSet_2_.member(LA(1)))) {
			evalOrExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			caseExpression_AST = currentAST.root;
		}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = caseExpression_AST;
	}
	
	public void whenClause() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST whenClause_AST = null;
		
		{
			match(WHEN);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(THEN);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
		}
		whenClause_AST = currentAST.root;
		returnAST = whenClause_AST;
	}
	
	public void elseClause() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST elseClause_AST = null;
		
		{
			match(ELSE);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
		}
		elseClause_AST = currentAST.root;
		returnAST = elseClause_AST;
	}
	
	public void evalAndExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST evalAndExpression_AST = null;
		IToken  op = null;
		AST op_AST = null;
		
		bitWiseExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==AND_EXPR))
				{
					op = LT(1);
					op_AST = astFactory.create(op);
					match(AND_EXPR);
					bitWiseExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop79_breakloop;
				}
				
			}
_loop79_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			evalAndExpression_AST = (AST)currentAST.root;
			if (op != null)
					  evalAndExpression_AST = (AST) astFactory.make(astFactory.create(EVAL_AND_EXPR,"evalAndExpression"), evalAndExpression_AST);
					
			currentAST.root = evalAndExpression_AST;
			if ( (null != evalAndExpression_AST) && (null != evalAndExpression_AST.getFirstChild()) )
				currentAST.child = evalAndExpression_AST.getFirstChild();
			else
				currentAST.child = evalAndExpression_AST;
			currentAST.advanceChildToEnd();
		}
		evalAndExpression_AST = currentAST.root;
		returnAST = evalAndExpression_AST;
	}
	
	public void bitWiseExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST bitWiseExpression_AST = null;
		
		negatedExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if (((LA(1) >= BAND && LA(1) <= BXOR)))
				{
					{
						switch ( LA(1) )
						{
						case BAND:
						{
							AST tmp82_AST = null;
							tmp82_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp82_AST);
							match(BAND);
							break;
						}
						case BOR:
						{
							AST tmp83_AST = null;
							tmp83_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp83_AST);
							match(BOR);
							break;
						}
						case BXOR:
						{
							AST tmp84_AST = null;
							tmp84_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp84_AST);
							match(BXOR);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						 }
					}
					negatedExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop83_breakloop;
				}
				
			}
_loop83_breakloop:			;
		}    // ( ... )*
		bitWiseExpression_AST = currentAST.root;
		returnAST = bitWiseExpression_AST;
	}
	
	public void negatedExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST negatedExpression_AST = null;
		
		switch ( LA(1) )
		{
		case SUM:
		case AVG:
		case MAX:
		case MIN:
		case COALESCE:
		case MEDIAN:
		case STDDEV:
		case AVEDEV:
		case COUNT:
		case PREVIOUS:
		case PRIOR:
		case EXISTS:
		case NUM_INT:
		case NUM_LONG:
		case NUM_FLOAT:
		case NUM_DOUBLE:
		case MINUS:
		case PLUS:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case STRING_LITERAL:
		case QUOTED_STRING_LITERAL:
		case IDENT:
		case LPAREN:
		case LCURLY:
		{
			evalEqualsExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			negatedExpression_AST = currentAST.root;
			break;
		}
		case NOT_EXPR:
		{
			AST tmp85_AST = null;
			tmp85_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp85_AST);
			match(NOT_EXPR);
			evalEqualsExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			negatedExpression_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = negatedExpression_AST;
	}
	
	public void evalEqualsExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST evalEqualsExpression_AST = null;
		IToken  eq = null;
		AST eq_AST = null;
		IToken  is_ = null;
		AST is__AST = null;
		IToken  isnot = null;
		AST isnot_AST = null;
		IToken  sqlne = null;
		AST sqlne_AST = null;
		IToken  ne = null;
		AST ne_AST = null;
		
		evalRelationalExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((tokenSet_3_.member(LA(1))))
				{
					{
						switch ( LA(1) )
						{
						case EQUALS:
						{
							eq = LT(1);
							eq_AST = astFactory.create(eq);
							match(EQUALS);
							break;
						}
						case SQL_NE:
						{
							sqlne = LT(1);
							sqlne_AST = astFactory.create(sqlne);
							match(SQL_NE);
							break;
						}
						case NOT_EQUAL:
						{
							ne = LT(1);
							ne_AST = astFactory.create(ne);
							match(NOT_EQUAL);
							break;
						}
						default:
							if ((LA(1)==IS_) && (tokenSet_4_.member(LA(2))))
							{
								is_ = LT(1);
								is__AST = astFactory.create(is_);
								match(IS_);
							}
							else if ((LA(1)==IS_) && (LA(2)==NOT_EXPR)) {
								isnot = LT(1);
								isnot_AST = astFactory.create(isnot);
								match(IS_);
								match(NOT_EXPR);
							}
						else
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						break; }
					}
					evalRelationalExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop88_breakloop;
				}
				
			}
_loop88_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			evalEqualsExpression_AST = (AST)currentAST.root;
			
						if ((eq != null) || (is_ != null))
						{
							evalEqualsExpression_AST = (AST) astFactory.make(astFactory.create(EVAL_EQUALS_EXPR,"evalEqualsExpression"), evalEqualsExpression_AST); 
						}
						if ((isnot != null) || (sqlne != null) || (ne != null))
						{
							evalEqualsExpression_AST = (AST) astFactory.make(astFactory.create(EVAL_NOTEQUALS_EXPR,"evalEqualsExpression"), evalEqualsExpression_AST); 
					  	}
					
			currentAST.root = evalEqualsExpression_AST;
			if ( (null != evalEqualsExpression_AST) && (null != evalEqualsExpression_AST.getFirstChild()) )
				currentAST.child = evalEqualsExpression_AST.getFirstChild();
			else
				currentAST.child = evalEqualsExpression_AST;
			currentAST.advanceChildToEnd();
		}
		evalEqualsExpression_AST = currentAST.root;
		returnAST = evalEqualsExpression_AST;
	}
	
	public void evalRelationalExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST evalRelationalExpression_AST = null;
		IToken  n = null;
		AST n_AST = null;
		IToken  i = null;
		AST i_AST = null;
		IToken  col = null;
		AST col_AST = null;
		AST s_AST = null;
		IToken  b = null;
		AST b_AST = null;
		IToken  l = null;
		AST l_AST = null;
		IToken  r = null;
		AST r_AST = null;
		
		concatenationExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case EOF:
			case OR_EXPR:
			case AND_EXPR:
			case AS:
			case ELSE:
			case WHEN:
			case THEN:
			case END:
			case FROM:
			case IS_:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			case ASC:
			case DESC:
			case COMMA:
			case RPAREN:
			case EQUALS:
			case RBRACK:
			case COLON:
			case BAND:
			case BOR:
			case BXOR:
			case SQL_NE:
			case NOT_EQUAL:
			case LT_:
			case GT:
			case LE:
			case GE:
			case RCURLY:
			{
				{
					{    // ( ... )*
						for (;;)
						{
							if (((LA(1) >= LT_ && LA(1) <= GE)))
							{
								{
									switch ( LA(1) )
									{
									case LT_:
									{
										AST tmp87_AST = null;
										tmp87_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp87_AST);
										match(LT_);
										break;
									}
									case GT:
									{
										AST tmp88_AST = null;
										tmp88_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp88_AST);
										match(GT);
										break;
									}
									case LE:
									{
										AST tmp89_AST = null;
										tmp89_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp89_AST);
										match(LE);
										break;
									}
									case GE:
									{
										AST tmp90_AST = null;
										tmp90_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp90_AST);
										match(GE);
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									 }
								}
								concatenationExpr();
								if (0 == inputState.guessing)
								{
									astFactory.addASTChild(ref currentAST, returnAST);
								}
							}
							else
							{
								goto _loop94_breakloop;
							}
							
						}
_loop94_breakloop:						;
					}    // ( ... )*
				}
				break;
			}
			case IN_SET:
			case BETWEEN:
			case LIKE:
			case REGEXP:
			case NOT_EXPR:
			{
				{
					switch ( LA(1) )
					{
					case NOT_EXPR:
					{
						n = LT(1);
						n_AST = astFactory.create(n);
						match(NOT_EXPR);
						break;
					}
					case IN_SET:
					case BETWEEN:
					case LIKE:
					case REGEXP:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
				}
				{
					switch ( LA(1) )
					{
					case BETWEEN:
					{
						{
							b = LT(1);
							b_AST = astFactory.create(b);
							astFactory.makeASTRoot(ref currentAST, b_AST);
							match(BETWEEN);
							if (0==inputState.guessing)
							{
								
														b_AST.setType( (n == null) ? BETWEEN : NOT_BETWEEN);
														b_AST.setText( (n == null) ? "between" : "not between");
													
							}
							betweenList();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						break;
					}
					case LIKE:
					{
						{
							l = LT(1);
							l_AST = astFactory.create(l);
							astFactory.makeASTRoot(ref currentAST, l_AST);
							match(LIKE);
							if (0==inputState.guessing)
							{
								
													l_AST.setType( (n == null) ? LIKE : NOT_LIKE);
														l_AST.setText( (n == null) ? "like" : "not like");
													
							}
							concatenationExpr();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
							{
								switch ( LA(1) )
								{
								case ESCAPE:
								{
									match(ESCAPE);
									stringconstant();
									if (0 == inputState.guessing)
									{
										astFactory.addASTChild(ref currentAST, returnAST);
									}
									break;
								}
								case EOF:
								case OR_EXPR:
								case AND_EXPR:
								case AS:
								case ELSE:
								case WHEN:
								case THEN:
								case END:
								case FROM:
								case IS_:
								case GROUP:
								case HAVING:
								case OUTPUT:
								case ORDER:
								case ASC:
								case DESC:
								case COMMA:
								case RPAREN:
								case EQUALS:
								case RBRACK:
								case COLON:
								case BAND:
								case BOR:
								case BXOR:
								case SQL_NE:
								case NOT_EQUAL:
								case RCURLY:
								{
									break;
								}
								default:
								{
									throw new NoViableAltException(LT(1), getFilename());
								}
								 }
							}
						}
						break;
					}
					case REGEXP:
					{
						{
							r = LT(1);
							r_AST = astFactory.create(r);
							astFactory.makeASTRoot(ref currentAST, r_AST);
							match(REGEXP);
							if (0==inputState.guessing)
							{
								
													r_AST.setType( (n == null) ? REGEXP : NOT_REGEXP);
														r_AST.setText( (n == null) ? "regexp" : "not regexp");
													
							}
							concatenationExpr();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						break;
					}
					default:
						if ((LA(1)==IN_SET) && (LA(2)==LPAREN||LA(2)==LBRACK) && (tokenSet_1_.member(LA(3))))
						{
							{
								i = LT(1);
								i_AST = astFactory.create(i);
								astFactory.makeASTRoot(ref currentAST, i_AST);
								match(IN_SET);
								{
									switch ( LA(1) )
									{
									case LPAREN:
									{
										AST tmp92_AST = null;
										tmp92_AST = astFactory.create(LT(1));
										astFactory.addASTChild(ref currentAST, tmp92_AST);
										match(LPAREN);
										break;
									}
									case LBRACK:
									{
										AST tmp93_AST = null;
										tmp93_AST = astFactory.create(LT(1));
										astFactory.addASTChild(ref currentAST, tmp93_AST);
										match(LBRACK);
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									 }
								}
								expression();
								if (0 == inputState.guessing)
								{
									astFactory.addASTChild(ref currentAST, returnAST);
								}
								{
									switch ( LA(1) )
									{
									case COLON:
									{
										{
											col = LT(1);
											col_AST = astFactory.create(col);
											match(COLON);
											{
												expression();
												if (0 == inputState.guessing)
												{
													astFactory.addASTChild(ref currentAST, returnAST);
												}
											}
										}
										break;
									}
									case COMMA:
									case RPAREN:
									case RBRACK:
									{
										{
											{    // ( ... )*
												for (;;)
												{
													if ((LA(1)==COMMA))
													{
														match(COMMA);
														expression();
														if (0 == inputState.guessing)
														{
															astFactory.addASTChild(ref currentAST, returnAST);
														}
													}
													else
													{
														goto _loop104_breakloop;
													}
													
												}
_loop104_breakloop:												;
											}    // ( ... )*
										}
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									 }
								}
								{
									switch ( LA(1) )
									{
									case RPAREN:
									{
										AST tmp95_AST = null;
										tmp95_AST = astFactory.create(LT(1));
										astFactory.addASTChild(ref currentAST, tmp95_AST);
										match(RPAREN);
										break;
									}
									case RBRACK:
									{
										AST tmp96_AST = null;
										tmp96_AST = astFactory.create(LT(1));
										astFactory.addASTChild(ref currentAST, tmp96_AST);
										match(RBRACK);
										break;
									}
									default:
									{
										throw new NoViableAltException(LT(1), getFilename());
									}
									 }
								}
								if (0==inputState.guessing)
								{
									
															if (col == null)
															{
																i_AST.setType( (n == null) ? IN_SET : NOT_IN_SET);
																i_AST.setText( (n == null) ? "in" : "not in");
															}
															else
															{
																i_AST.setType( (n == null) ? IN_RANGE : NOT_IN_RANGE);
																i_AST.setText( (n == null) ? "in range" : "not in range");
															}
														
								}
							}
						}
						else if ((LA(1)==IN_SET) && (LA(2)==LPAREN) && (LA(3)==SELECT)) {
							match(IN_SET);
							inSubSelectQuery();
							if (0 == inputState.guessing)
							{
								s_AST = (AST)returnAST;
								astFactory.addASTChild(ref currentAST, returnAST);
							}
							if (0==inputState.guessing)
							{
								evalRelationalExpression_AST = (AST)currentAST.root;
									if (n == null) evalRelationalExpression_AST = (AST) astFactory.make(astFactory.create(IN_SUBSELECT_EXPR,"inSubselectExpr"), evalRelationalExpression_AST); 
													    else evalRelationalExpression_AST = (AST) astFactory.make(astFactory.create(NOT_IN_SUBSELECT_EXPR,"notInSubselectExpr"), evalRelationalExpression_AST); 
													
								currentAST.root = evalRelationalExpression_AST;
								if ( (null != evalRelationalExpression_AST) && (null != evalRelationalExpression_AST.getFirstChild()) )
									currentAST.child = evalRelationalExpression_AST.getFirstChild();
								else
									currentAST.child = evalRelationalExpression_AST;
								currentAST.advanceChildToEnd();
							}
						}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					break; }
				}
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		evalRelationalExpression_AST = currentAST.root;
		returnAST = evalRelationalExpression_AST;
	}
	
	public void concatenationExpr() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST concatenationExpr_AST = null;
		IToken  c = null;
		AST c_AST = null;
		
		additiveExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case LOR:
			{
				c = LT(1);
				c_AST = astFactory.create(c);
				match(LOR);
				additiveExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{    // ( ... )*
					for (;;)
					{
						if ((LA(1)==LOR))
						{
							match(LOR);
							additiveExpression();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						else
						{
							goto _loop114_breakloop;
						}
						
					}
_loop114_breakloop:					;
				}    // ( ... )*
				break;
			}
			case EOF:
			case IN_SET:
			case BETWEEN:
			case LIKE:
			case REGEXP:
			case ESCAPE:
			case OR_EXPR:
			case AND_EXPR:
			case NOT_EXPR:
			case AS:
			case ELSE:
			case WHEN:
			case THEN:
			case END:
			case FROM:
			case IS_:
			case GROUP:
			case HAVING:
			case OUTPUT:
			case ORDER:
			case ASC:
			case DESC:
			case COMMA:
			case RPAREN:
			case EQUALS:
			case RBRACK:
			case COLON:
			case BAND:
			case BOR:
			case BXOR:
			case SQL_NE:
			case NOT_EQUAL:
			case LT_:
			case GT:
			case LE:
			case GE:
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			concatenationExpr_AST = (AST)currentAST.root;
			
						if (c != null) concatenationExpr_AST = (AST) astFactory.make(astFactory.create(CONCAT,"concatenationExpr"), concatenationExpr_AST); 
					
			currentAST.root = concatenationExpr_AST;
			if ( (null != concatenationExpr_AST) && (null != concatenationExpr_AST.getFirstChild()) )
				currentAST.child = concatenationExpr_AST.getFirstChild();
			else
				currentAST.child = concatenationExpr_AST;
			currentAST.advanceChildToEnd();
		}
		concatenationExpr_AST = currentAST.root;
		returnAST = concatenationExpr_AST;
	}
	
	public void inSubSelectQuery() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inSubSelectQuery_AST = null;
		
		subQueryExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			inSubSelectQuery_AST = (AST)currentAST.root;
			inSubSelectQuery_AST = (AST) astFactory.make(astFactory.create(IN_SUBSELECT_QUERY_EXPR,"inSubSelectQuery"), inSubSelectQuery_AST);
			currentAST.root = inSubSelectQuery_AST;
			if ( (null != inSubSelectQuery_AST) && (null != inSubSelectQuery_AST.getFirstChild()) )
				currentAST.child = inSubSelectQuery_AST.getFirstChild();
			else
				currentAST.child = inSubSelectQuery_AST;
			currentAST.advanceChildToEnd();
		}
		inSubSelectQuery_AST = currentAST.root;
		returnAST = inSubSelectQuery_AST;
	}
	
	public void betweenList() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST betweenList_AST = null;
		
		concatenationExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(AND_EXPR);
		concatenationExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		betweenList_AST = currentAST.root;
		returnAST = betweenList_AST;
	}
	
	public void subQueryExpr() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subQueryExpr_AST = null;
		
		match(LPAREN);
		match(SELECT);
		selectionListElement();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(FROM);
		subSelectFilterExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case WHERE:
			{
				match(WHERE);
				whereClause();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RPAREN);
		subQueryExpr_AST = currentAST.root;
		returnAST = subQueryExpr_AST;
	}
	
	public void additiveExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST additiveExpression_AST = null;
		
		multiplyExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==MINUS||LA(1)==PLUS))
				{
					{
						switch ( LA(1) )
						{
						case PLUS:
						{
							AST tmp105_AST = null;
							tmp105_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp105_AST);
							match(PLUS);
							break;
						}
						case MINUS:
						{
							AST tmp106_AST = null;
							tmp106_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp106_AST);
							match(MINUS);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						 }
					}
					multiplyExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop118_breakloop;
				}
				
			}
_loop118_breakloop:			;
		}    // ( ... )*
		additiveExpression_AST = currentAST.root;
		returnAST = additiveExpression_AST;
	}
	
	public void multiplyExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST multiplyExpression_AST = null;
		
		unaryExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==STAR||LA(1)==DIV||LA(1)==MOD))
				{
					{
						switch ( LA(1) )
						{
						case STAR:
						{
							AST tmp107_AST = null;
							tmp107_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp107_AST);
							match(STAR);
							break;
						}
						case DIV:
						{
							AST tmp108_AST = null;
							tmp108_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp108_AST);
							match(DIV);
							break;
						}
						case MOD:
						{
							AST tmp109_AST = null;
							tmp109_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp109_AST);
							match(MOD);
							break;
						}
						default:
						{
							throw new NoViableAltException(LT(1), getFilename());
						}
						 }
					}
					unaryExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop122_breakloop;
				}
				
			}
_loop122_breakloop:			;
		}    // ( ... )*
		multiplyExpression_AST = currentAST.root;
		returnAST = multiplyExpression_AST;
	}
	
	public void unaryExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST unaryExpression_AST = null;
		
		switch ( LA(1) )
		{
		case MAX:
		case MIN:
		case IDENT:
		{
			eventPropertyOrLibFunction();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			unaryExpression_AST = currentAST.root;
			break;
		}
		case SUM:
		case AVG:
		case COALESCE:
		case MEDIAN:
		case STDDEV:
		case AVEDEV:
		case COUNT:
		case PREVIOUS:
		case PRIOR:
		{
			builtinFunc();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			unaryExpression_AST = currentAST.root;
			break;
		}
		case LCURLY:
		{
			arrayExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			unaryExpression_AST = currentAST.root;
			break;
		}
		case EXISTS:
		{
			existsSubSelectExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			unaryExpression_AST = currentAST.root;
			break;
		}
		default:
			if ((LA(1)==MINUS) && (LA(2)==IDENT))
			{
				AST tmp110_AST = null;
				tmp110_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp110_AST);
				match(MINUS);
				if (0==inputState.guessing)
				{
					tmp110_AST.setType(UNARY_MINUS);
				}
				eventProperty();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				unaryExpression_AST = currentAST.root;
			}
			else if (((LA(1) >= NUM_INT && LA(1) <= QUOTED_STRING_LITERAL)) && (tokenSet_5_.member(LA(2)))) {
				constant();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				unaryExpression_AST = currentAST.root;
			}
			else if ((LA(1)==LPAREN) && (tokenSet_1_.member(LA(2)))) {
				match(LPAREN);
				expression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				match(RPAREN);
				unaryExpression_AST = currentAST.root;
			}
			else if ((LA(1)==LPAREN) && (LA(2)==SELECT)) {
				subSelectExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				unaryExpression_AST = currentAST.root;
			}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		break; }
		returnAST = unaryExpression_AST;
	}
	
	public void eventPropertyOrLibFunction() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventPropertyOrLibFunction_AST = null;
		
		bool synPredMatched156 = false;
		if (((LA(1)==IDENT) && (tokenSet_6_.member(LA(2))) && (tokenSet_7_.member(LA(3))) && (tokenSet_8_.member(LA(4)))))
		{
			int _m156 = mark();
			synPredMatched156 = true;
			inputState.guessing++;
			try {
				{
					eventProperty();
				}
			}
			catch (RecognitionException)
			{
				synPredMatched156 = false;
			}
			rewind(_m156);
			inputState.guessing--;
		}
		if ( synPredMatched156 )
		{
			eventProperty();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			eventPropertyOrLibFunction_AST = currentAST.root;
		}
		else if ((LA(1)==MAX||LA(1)==MIN||LA(1)==IDENT) && (LA(2)==LPAREN||LA(2)==DOT) && (tokenSet_9_.member(LA(3))) && (tokenSet_10_.member(LA(4)))) {
			libFunction();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			eventPropertyOrLibFunction_AST = currentAST.root;
		}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = eventPropertyOrLibFunction_AST;
	}
	
	public void builtinFunc() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST builtinFunc_AST = null;
		
		switch ( LA(1) )
		{
		case SUM:
		{
			AST tmp113_AST = null;
			tmp113_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp113_AST);
			match(SUM);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case ALL:
				{
					match(ALL);
					break;
				}
				case DISTINCT:
				{
					AST tmp116_AST = null;
					tmp116_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp116_AST);
					match(DISTINCT);
					break;
				}
				case NOT_EXPR:
				case SUM:
				case AVG:
				case MAX:
				case MIN:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case PREVIOUS:
				case PRIOR:
				case EXISTS:
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				case MINUS:
				case PLUS:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case STRING_LITERAL:
				case QUOTED_STRING_LITERAL:
				case IDENT:
				case LPAREN:
				case LCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case AVG:
		{
			AST tmp118_AST = null;
			tmp118_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp118_AST);
			match(AVG);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case ALL:
				{
					match(ALL);
					break;
				}
				case DISTINCT:
				{
					AST tmp121_AST = null;
					tmp121_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp121_AST);
					match(DISTINCT);
					break;
				}
				case NOT_EXPR:
				case SUM:
				case AVG:
				case MAX:
				case MIN:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case PREVIOUS:
				case PRIOR:
				case EXISTS:
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				case MINUS:
				case PLUS:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case STRING_LITERAL:
				case QUOTED_STRING_LITERAL:
				case IDENT:
				case LPAREN:
				case LCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case COUNT:
		{
			AST tmp123_AST = null;
			tmp123_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp123_AST);
			match(COUNT);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case NOT_EXPR:
				case SUM:
				case AVG:
				case MAX:
				case MIN:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case DISTINCT:
				case ALL:
				case PREVIOUS:
				case PRIOR:
				case EXISTS:
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				case MINUS:
				case PLUS:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case STRING_LITERAL:
				case QUOTED_STRING_LITERAL:
				case IDENT:
				case LPAREN:
				case LCURLY:
				{
					{
						{
							switch ( LA(1) )
							{
							case ALL:
							{
								match(ALL);
								break;
							}
							case DISTINCT:
							{
								AST tmp126_AST = null;
								tmp126_AST = astFactory.create(LT(1));
								astFactory.addASTChild(ref currentAST, tmp126_AST);
								match(DISTINCT);
								break;
							}
							case NOT_EXPR:
							case SUM:
							case AVG:
							case MAX:
							case MIN:
							case COALESCE:
							case MEDIAN:
							case STDDEV:
							case AVEDEV:
							case COUNT:
							case CASE:
							case PREVIOUS:
							case PRIOR:
							case EXISTS:
							case NUM_INT:
							case NUM_LONG:
							case NUM_FLOAT:
							case NUM_DOUBLE:
							case MINUS:
							case PLUS:
							case LITERAL_true:
							case LITERAL_false:
							case LITERAL_null:
							case STRING_LITERAL:
							case QUOTED_STRING_LITERAL:
							case IDENT:
							case LPAREN:
							case LCURLY:
							{
								break;
							}
							default:
							{
								throw new NoViableAltException(LT(1), getFilename());
							}
							 }
						}
						expression();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					break;
				}
				case STAR:
				{
					{
						match(STAR);
					}
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case MEDIAN:
		{
			AST tmp129_AST = null;
			tmp129_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp129_AST);
			match(MEDIAN);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case ALL:
				{
					match(ALL);
					break;
				}
				case DISTINCT:
				{
					AST tmp132_AST = null;
					tmp132_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp132_AST);
					match(DISTINCT);
					break;
				}
				case NOT_EXPR:
				case SUM:
				case AVG:
				case MAX:
				case MIN:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case PREVIOUS:
				case PRIOR:
				case EXISTS:
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				case MINUS:
				case PLUS:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case STRING_LITERAL:
				case QUOTED_STRING_LITERAL:
				case IDENT:
				case LPAREN:
				case LCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case STDDEV:
		{
			AST tmp134_AST = null;
			tmp134_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp134_AST);
			match(STDDEV);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case ALL:
				{
					match(ALL);
					break;
				}
				case DISTINCT:
				{
					AST tmp137_AST = null;
					tmp137_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp137_AST);
					match(DISTINCT);
					break;
				}
				case NOT_EXPR:
				case SUM:
				case AVG:
				case MAX:
				case MIN:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case PREVIOUS:
				case PRIOR:
				case EXISTS:
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				case MINUS:
				case PLUS:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case STRING_LITERAL:
				case QUOTED_STRING_LITERAL:
				case IDENT:
				case LPAREN:
				case LCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case AVEDEV:
		{
			AST tmp139_AST = null;
			tmp139_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp139_AST);
			match(AVEDEV);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case ALL:
				{
					match(ALL);
					break;
				}
				case DISTINCT:
				{
					AST tmp142_AST = null;
					tmp142_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp142_AST);
					match(DISTINCT);
					break;
				}
				case NOT_EXPR:
				case SUM:
				case AVG:
				case MAX:
				case MIN:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case PREVIOUS:
				case PRIOR:
				case EXISTS:
				case NUM_INT:
				case NUM_LONG:
				case NUM_FLOAT:
				case NUM_DOUBLE:
				case MINUS:
				case PLUS:
				case LITERAL_true:
				case LITERAL_false:
				case LITERAL_null:
				case STRING_LITERAL:
				case QUOTED_STRING_LITERAL:
				case IDENT:
				case LPAREN:
				case LCURLY:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case COALESCE:
		{
			AST tmp144_AST = null;
			tmp144_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp144_AST);
			match(COALESCE);
			match(LPAREN);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(COMMA);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			{    // ( ... )*
				for (;;)
				{
					if ((LA(1)==COMMA))
					{
						match(COMMA);
						expression();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else
					{
						goto _loop148_breakloop;
					}
					
				}
_loop148_breakloop:				;
			}    // ( ... )*
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case PREVIOUS:
		{
			AST tmp149_AST = null;
			tmp149_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp149_AST);
			match(PREVIOUS);
			match(LPAREN);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(COMMA);
			eventProperty();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		case PRIOR:
		{
			AST tmp153_AST = null;
			tmp153_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp153_AST);
			match(PRIOR);
			match(LPAREN);
			AST tmp155_AST = null;
			tmp155_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp155_AST);
			match(NUM_INT);
			match(COMMA);
			eventProperty();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			builtinFunc_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = builtinFunc_AST;
	}
	
	public void arrayExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arrayExpression_AST = null;
		
		match(LCURLY);
		{
			switch ( LA(1) )
			{
			case NOT_EXPR:
			case SUM:
			case AVG:
			case MAX:
			case MIN:
			case COALESCE:
			case MEDIAN:
			case STDDEV:
			case AVEDEV:
			case COUNT:
			case CASE:
			case PREVIOUS:
			case PRIOR:
			case EXISTS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case IDENT:
			case LPAREN:
			case LCURLY:
			{
				expression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{    // ( ... )*
					for (;;)
					{
						if ((LA(1)==COMMA))
						{
							match(COMMA);
							expression();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						else
						{
							goto _loop136_breakloop;
						}
						
					}
_loop136_breakloop:					;
				}    // ( ... )*
				break;
			}
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RCURLY);
		if (0==inputState.guessing)
		{
			arrayExpression_AST = (AST)currentAST.root;
			arrayExpression_AST = (AST) astFactory.make(astFactory.create(ARRAY_EXPR,"arrayExpression"), arrayExpression_AST);
			currentAST.root = arrayExpression_AST;
			if ( (null != arrayExpression_AST) && (null != arrayExpression_AST.getFirstChild()) )
				currentAST.child = arrayExpression_AST.getFirstChild();
			else
				currentAST.child = arrayExpression_AST;
			currentAST.advanceChildToEnd();
		}
		arrayExpression_AST = currentAST.root;
		returnAST = arrayExpression_AST;
	}
	
	public void subSelectExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectExpression_AST = null;
		
		subQueryExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			subSelectExpression_AST = (AST)currentAST.root;
			subSelectExpression_AST = (AST) astFactory.make(astFactory.create(SUBSELECT_EXPR,"subSelectExpression"), subSelectExpression_AST);
			currentAST.root = subSelectExpression_AST;
			if ( (null != subSelectExpression_AST) && (null != subSelectExpression_AST.getFirstChild()) )
				currentAST.child = subSelectExpression_AST.getFirstChild();
			else
				currentAST.child = subSelectExpression_AST;
			currentAST.advanceChildToEnd();
		}
		subSelectExpression_AST = currentAST.root;
		returnAST = subSelectExpression_AST;
	}
	
	public void existsSubSelectExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST existsSubSelectExpression_AST = null;
		
		match(EXISTS);
		subQueryExpr();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			existsSubSelectExpression_AST = (AST)currentAST.root;
			existsSubSelectExpression_AST = (AST) astFactory.make(astFactory.create(EXISTS_SUBSELECT_EXPR,"existsSubSelectExpression"), existsSubSelectExpression_AST);
			currentAST.root = existsSubSelectExpression_AST;
			if ( (null != existsSubSelectExpression_AST) && (null != existsSubSelectExpression_AST.getFirstChild()) )
				currentAST.child = existsSubSelectExpression_AST.getFirstChild();
			else
				currentAST.child = existsSubSelectExpression_AST;
			currentAST.advanceChildToEnd();
		}
		existsSubSelectExpression_AST = currentAST.root;
		returnAST = existsSubSelectExpression_AST;
	}
	
	public void subSelectFilterExpr() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectFilterExpr_AST = null;
		
		eventFilterExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case DOT:
			{
				match(DOT);
				viewExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{    // ( ... )*
					for (;;)
					{
						if ((LA(1)==DOT))
						{
							match(DOT);
							viewExpression();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						else
						{
							goto _loop131_breakloop;
						}
						
					}
_loop131_breakloop:					;
				}    // ( ... )*
				break;
			}
			case WHERE:
			case AS:
			case IDENT:
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case AS:
			{
				match(AS);
				AST tmp165_AST = null;
				tmp165_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp165_AST);
				match(IDENT);
				break;
			}
			case IDENT:
			{
				AST tmp166_AST = null;
				tmp166_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp166_AST);
				match(IDENT);
				break;
			}
			case WHERE:
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			subSelectFilterExpr_AST = (AST)currentAST.root;
			subSelectFilterExpr_AST = (AST) astFactory.make(astFactory.create(STREAM_EXPR,"subSelectFilterExpr"), subSelectFilterExpr_AST);
			currentAST.root = subSelectFilterExpr_AST;
			if ( (null != subSelectFilterExpr_AST) && (null != subSelectFilterExpr_AST.getFirstChild()) )
				currentAST.child = subSelectFilterExpr_AST.getFirstChild();
			else
				currentAST.child = subSelectFilterExpr_AST;
			currentAST.advanceChildToEnd();
		}
		subSelectFilterExpr_AST = currentAST.root;
		returnAST = subSelectFilterExpr_AST;
	}
	
	public void maxFunc() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST maxFunc_AST = null;
		
		{
			switch ( LA(1) )
			{
			case MAX:
			{
				AST tmp167_AST = null;
				tmp167_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp167_AST);
				match(MAX);
				break;
			}
			case MIN:
			{
				AST tmp168_AST = null;
				tmp168_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp168_AST);
				match(MIN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(LPAREN);
		expression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case COMMA:
			{
				match(COMMA);
				expression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{    // ( ... )*
					for (;;)
					{
						if ((LA(1)==COMMA))
						{
							match(COMMA);
							expression();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						else
						{
							goto _loop153_breakloop;
						}
						
					}
_loop153_breakloop:					;
				}    // ( ... )*
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RPAREN);
		maxFunc_AST = currentAST.root;
		returnAST = maxFunc_AST;
	}
	
	public void libFunction() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST libFunction_AST = null;
		
		{
			if ((LA(1)==IDENT) && (LA(2)==DOT))
			{
				classIdentifierNonGreedy();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				match(DOT);
			}
			else if ((LA(1)==MAX||LA(1)==MIN||LA(1)==IDENT) && (LA(2)==LPAREN)) {
			}
			else
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		funcIdent();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		match(LPAREN);
		{
			switch ( LA(1) )
			{
			case NOT_EXPR:
			case SUM:
			case AVG:
			case MAX:
			case MIN:
			case COALESCE:
			case MEDIAN:
			case STDDEV:
			case AVEDEV:
			case COUNT:
			case CASE:
			case DISTINCT:
			case ALL:
			case PREVIOUS:
			case PRIOR:
			case EXISTS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case IDENT:
			case LPAREN:
			case LCURLY:
			{
				libFunctionArgs();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RPAREN);
		if (0==inputState.guessing)
		{
			libFunction_AST = (AST)currentAST.root;
			libFunction_AST = (AST) astFactory.make(astFactory.create(LIB_FUNCTION,"libFunction"), libFunction_AST);
			currentAST.root = libFunction_AST;
			if ( (null != libFunction_AST) && (null != libFunction_AST.getFirstChild()) )
				currentAST.child = libFunction_AST.getFirstChild();
			else
				currentAST.child = libFunction_AST;
			currentAST.advanceChildToEnd();
		}
		libFunction_AST = currentAST.root;
		returnAST = libFunction_AST;
	}
	
	public void classIdentifierNonGreedy() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST classIdentifierNonGreedy_AST = null;
		IToken  i1 = null;
		AST i1_AST = null;
		IToken  i2 = null;
		AST i2_AST = null;
		String identifier = "";
		
		i1 = LT(1);
		i1_AST = astFactory.create(i1);
		match(IDENT);
		if (0==inputState.guessing)
		{
			identifier = i1_AST.getText();
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==DOT) && (LA(2)==IDENT) && (LA(3)==DOT))
				{
					match(DOT);
					i2 = LT(1);
					i2_AST = astFactory.create(i2);
					match(IDENT);
					if (0==inputState.guessing)
					{
						identifier += "." + i2_AST.getText();
					}
				}
				else
				{
					goto _loop211_breakloop;
				}
				
			}
_loop211_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			classIdentifierNonGreedy_AST = (AST)currentAST.root;
			classIdentifierNonGreedy_AST = (AST) astFactory.make(astFactory.create(CLASS_IDENT,"classIdentifierNonGreedy"), classIdentifierNonGreedy_AST); 
				      classIdentifierNonGreedy_AST.setText(identifier);
				
			currentAST.root = classIdentifierNonGreedy_AST;
			if ( (null != classIdentifierNonGreedy_AST) && (null != classIdentifierNonGreedy_AST.getFirstChild()) )
				currentAST.child = classIdentifierNonGreedy_AST.getFirstChild();
			else
				currentAST.child = classIdentifierNonGreedy_AST;
			currentAST.advanceChildToEnd();
		}
		classIdentifierNonGreedy_AST = currentAST.root;
		returnAST = classIdentifierNonGreedy_AST;
	}
	
	public void funcIdent() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST funcIdent_AST = null;
		
		switch ( LA(1) )
		{
		case IDENT:
		{
			AST tmp177_AST = null;
			tmp177_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp177_AST);
			match(IDENT);
			funcIdent_AST = currentAST.root;
			break;
		}
		case MAX:
		{
			AST tmp178_AST = null;
			tmp178_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp178_AST);
			match(MAX);
			if (0==inputState.guessing)
			{
				tmp178_AST.setType(IDENT);
			}
			funcIdent_AST = currentAST.root;
			break;
		}
		case MIN:
		{
			AST tmp179_AST = null;
			tmp179_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp179_AST);
			match(MIN);
			if (0==inputState.guessing)
			{
				tmp179_AST.setType(IDENT);
			}
			funcIdent_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = funcIdent_AST;
	}
	
	public void libFunctionArgs() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST libFunctionArgs_AST = null;
		
		{
			switch ( LA(1) )
			{
			case ALL:
			{
				match(ALL);
				break;
			}
			case DISTINCT:
			{
				AST tmp181_AST = null;
				tmp181_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp181_AST);
				match(DISTINCT);
				break;
			}
			case NOT_EXPR:
			case SUM:
			case AVG:
			case MAX:
			case MIN:
			case COALESCE:
			case MEDIAN:
			case STDDEV:
			case AVEDEV:
			case COUNT:
			case CASE:
			case PREVIOUS:
			case PRIOR:
			case EXISTS:
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case IDENT:
			case LPAREN:
			case LCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		expression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					expression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop164_breakloop;
				}
				
			}
_loop164_breakloop:			;
		}    // ( ... )*
		libFunctionArgs_AST = currentAST.root;
		returnAST = libFunctionArgs_AST;
	}
	
	public void followedByExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST followedByExpression_AST = null;
		IToken  op = null;
		AST op_AST = null;
		
		orExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==FOLLOWED_BY))
				{
					op = LT(1);
					op_AST = astFactory.create(op);
					match(FOLLOWED_BY);
					orExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop169_breakloop;
				}
				
			}
_loop169_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			followedByExpression_AST = (AST)currentAST.root;
			if (op != null) 
					   followedByExpression_AST = (AST) astFactory.make(astFactory.create(FOLLOWED_BY_EXPR,"followedByExpression"), followedByExpression_AST);
					
			currentAST.root = followedByExpression_AST;
			if ( (null != followedByExpression_AST) && (null != followedByExpression_AST.getFirstChild()) )
				currentAST.child = followedByExpression_AST.getFirstChild();
			else
				currentAST.child = followedByExpression_AST;
			currentAST.advanceChildToEnd();
		}
		followedByExpression_AST = currentAST.root;
		returnAST = followedByExpression_AST;
	}
	
	public void orExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orExpression_AST = null;
		IToken  op = null;
		AST op_AST = null;
		
		andExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==OR_EXPR))
				{
					op = LT(1);
					op_AST = astFactory.create(op);
					match(OR_EXPR);
					andExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop172_breakloop;
				}
				
			}
_loop172_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			orExpression_AST = (AST)currentAST.root;
			if (op != null) 
					   orExpression_AST = (AST) astFactory.make(astFactory.create(OR_EXPR,"orExpression"), orExpression_AST);
					
			currentAST.root = orExpression_AST;
			if ( (null != orExpression_AST) && (null != orExpression_AST.getFirstChild()) )
				currentAST.child = orExpression_AST.getFirstChild();
			else
				currentAST.child = orExpression_AST;
			currentAST.advanceChildToEnd();
		}
		orExpression_AST = currentAST.root;
		returnAST = orExpression_AST;
	}
	
	public void andExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST andExpression_AST = null;
		IToken  op = null;
		AST op_AST = null;
		
		qualifyExpression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==AND_EXPR))
				{
					op = LT(1);
					op_AST = astFactory.create(op);
					match(AND_EXPR);
					qualifyExpression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop175_breakloop;
				}
				
			}
_loop175_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			andExpression_AST = (AST)currentAST.root;
			if (op != null)
					   andExpression_AST = (AST) astFactory.make(astFactory.create(AND_EXPR,"andExpression"), andExpression_AST);
					
			currentAST.root = andExpression_AST;
			if ( (null != andExpression_AST) && (null != andExpression_AST.getFirstChild()) )
				currentAST.child = andExpression_AST.getFirstChild();
			else
				currentAST.child = andExpression_AST;
			currentAST.advanceChildToEnd();
		}
		andExpression_AST = currentAST.root;
		returnAST = andExpression_AST;
	}
	
	public void qualifyExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST qualifyExpression_AST = null;
		
		{
			switch ( LA(1) )
			{
			case EVERY_EXPR:
			{
				AST tmp183_AST = null;
				tmp183_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp183_AST);
				match(EVERY_EXPR);
				break;
			}
			case NOT_EXPR:
			{
				AST tmp184_AST = null;
				tmp184_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp184_AST);
				match(NOT_EXPR);
				break;
			}
			case IDENT:
			case LPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		guardPostFix();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		qualifyExpression_AST = currentAST.root;
		returnAST = qualifyExpression_AST;
	}
	
	public void guardPostFix() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST guardPostFix_AST = null;
		IToken  op = null;
		AST op_AST = null;
		
		{
			switch ( LA(1) )
			{
			case IDENT:
			{
				atomicExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case LPAREN:
			{
				match(LPAREN);
				patternExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				match(RPAREN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		{
			switch ( LA(1) )
			{
			case WHERE:
			{
				op = LT(1);
				op_AST = astFactory.create(op);
				match(WHERE);
				guardExpression();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case EOF:
			case OR_EXPR:
			case AND_EXPR:
			case RPAREN:
			case RBRACK:
			case FOLLOWED_BY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			guardPostFix_AST = (AST)currentAST.root;
			if (op != null)
					   guardPostFix_AST = (AST) astFactory.make(astFactory.create(GUARD_EXPR,"guardPostFix"), guardPostFix_AST);
					
			currentAST.root = guardPostFix_AST;
			if ( (null != guardPostFix_AST) && (null != guardPostFix_AST.getFirstChild()) )
				currentAST.child = guardPostFix_AST.getFirstChild();
			else
				currentAST.child = guardPostFix_AST;
			currentAST.advanceChildToEnd();
		}
		guardPostFix_AST = currentAST.root;
		returnAST = guardPostFix_AST;
	}
	
	public void atomicExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atomicExpression_AST = null;
		
		if ((LA(1)==IDENT) && (LA(2)==COLON))
		{
			observerExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			atomicExpression_AST = currentAST.root;
		}
		else if ((LA(1)==IDENT) && (tokenSet_11_.member(LA(2)))) {
			eventFilterExpression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			atomicExpression_AST = currentAST.root;
		}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = atomicExpression_AST;
	}
	
	public void guardExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST guardExpression_AST = null;
		
		AST tmp187_AST = null;
		tmp187_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp187_AST);
		match(IDENT);
		match(COLON);
		AST tmp189_AST = null;
		tmp189_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp189_AST);
		match(IDENT);
		match(LPAREN);
		{
			switch ( LA(1) )
			{
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case STAR:
			case LBRACK:
			case LCURLY:
			{
				parameterSet();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RPAREN);
		guardExpression_AST = currentAST.root;
		returnAST = guardExpression_AST;
	}
	
	public void observerExpression() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST observerExpression_AST = null;
		
		AST tmp192_AST = null;
		tmp192_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp192_AST);
		match(IDENT);
		match(COLON);
		AST tmp194_AST = null;
		tmp194_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp194_AST);
		match(IDENT);
		match(LPAREN);
		{
			switch ( LA(1) )
			{
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			case STAR:
			case LBRACK:
			case LCURLY:
			{
				parameterSet();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case RPAREN:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RPAREN);
		if (0==inputState.guessing)
		{
			observerExpression_AST = (AST)currentAST.root;
			observerExpression_AST = (AST) astFactory.make(astFactory.create(OBSERVER_EXPR,"observerExpression"), observerExpression_AST);
			currentAST.root = observerExpression_AST;
			if ( (null != observerExpression_AST) && (null != observerExpression_AST.getFirstChild()) )
				currentAST.child = observerExpression_AST.getFirstChild();
			else
				currentAST.child = observerExpression_AST;
			currentAST.advanceChildToEnd();
		}
		observerExpression_AST = currentAST.root;
		returnAST = observerExpression_AST;
	}
	
	public void parameter() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_AST = null;
		
		switch ( LA(1) )
		{
		case NUM_INT:
		case NUM_LONG:
		case NUM_FLOAT:
		case NUM_DOUBLE:
		case MINUS:
		case PLUS:
		case LITERAL_true:
		case LITERAL_false:
		case LITERAL_null:
		case STRING_LITERAL:
		case QUOTED_STRING_LITERAL:
		case STAR:
		{
			singleParameter();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			parameter_AST = currentAST.root;
			break;
		}
		case LBRACK:
		{
			numericParameterList();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			parameter_AST = currentAST.root;
			break;
		}
		case LCURLY:
		{
			arrayParameterList();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			parameter_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		 }
		returnAST = parameter_AST;
	}
	
	public void singleParameter() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST singleParameter_AST = null;
		
		if ((LA(1)==NUM_INT) && (LA(2)==COLON))
		{
			rangeOperand();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			singleParameter_AST = currentAST.root;
		}
		else if ((LA(1)==STAR) && (LA(2)==DIV)) {
			frequencyOperand();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			singleParameter_AST = currentAST.root;
		}
		else if ((LA(1)==STAR) && (LA(2)==COMMA||LA(2)==RPAREN)) {
			AST tmp197_AST = null;
			tmp197_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp197_AST);
			match(STAR);
			singleParameter_AST = currentAST.root;
		}
		else if (((LA(1) >= NUM_INT && LA(1) <= QUOTED_STRING_LITERAL)) && (tokenSet_12_.member(LA(2)))) {
			constant();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			singleParameter_AST = currentAST.root;
		}
		else if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (tokenSet_13_.member(LA(2)))) {
			time_period();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			singleParameter_AST = currentAST.root;
		}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = singleParameter_AST;
	}
	
	public void numericParameterList() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST numericParameterList_AST = null;
		
		match(LBRACK);
		numericListParameter();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					numericListParameter();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop195_breakloop;
				}
				
			}
_loop195_breakloop:			;
		}    // ( ... )*
		match(RBRACK);
		if (0==inputState.guessing)
		{
			numericParameterList_AST = (AST)currentAST.root;
			numericParameterList_AST = (AST) astFactory.make(astFactory.create(NUMERIC_PARAM_LIST,"numericParameterList"), numericParameterList_AST);
			currentAST.root = numericParameterList_AST;
			if ( (null != numericParameterList_AST) && (null != numericParameterList_AST.getFirstChild()) )
				currentAST.child = numericParameterList_AST.getFirstChild();
			else
				currentAST.child = numericParameterList_AST;
			currentAST.advanceChildToEnd();
		}
		numericParameterList_AST = currentAST.root;
		returnAST = numericParameterList_AST;
	}
	
	public void arrayParameterList() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arrayParameterList_AST = null;
		
		match(LCURLY);
		{
			switch ( LA(1) )
			{
			case NUM_INT:
			case NUM_LONG:
			case NUM_FLOAT:
			case NUM_DOUBLE:
			case MINUS:
			case PLUS:
			case LITERAL_true:
			case LITERAL_false:
			case LITERAL_null:
			case STRING_LITERAL:
			case QUOTED_STRING_LITERAL:
			{
				constant();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{    // ( ... )*
					for (;;)
					{
						if ((LA(1)==COMMA))
						{
							match(COMMA);
							constant();
							if (0 == inputState.guessing)
							{
								astFactory.addASTChild(ref currentAST, returnAST);
							}
						}
						else
						{
							goto _loop200_breakloop;
						}
						
					}
_loop200_breakloop:					;
				}    // ( ... )*
				break;
			}
			case RCURLY:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		match(RCURLY);
		if (0==inputState.guessing)
		{
			arrayParameterList_AST = (AST)currentAST.root;
			arrayParameterList_AST = (AST) astFactory.make(astFactory.create(ARRAY_PARAM_LIST,"arrayParameterList"), arrayParameterList_AST);
			currentAST.root = arrayParameterList_AST;
			if ( (null != arrayParameterList_AST) && (null != arrayParameterList_AST.getFirstChild()) )
				currentAST.child = arrayParameterList_AST.getFirstChild();
			else
				currentAST.child = arrayParameterList_AST;
			currentAST.advanceChildToEnd();
		}
		arrayParameterList_AST = currentAST.root;
		returnAST = arrayParameterList_AST;
	}
	
	public void rangeOperand() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST rangeOperand_AST = null;
		
		AST tmp204_AST = null;
		tmp204_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp204_AST);
		match(NUM_INT);
		match(COLON);
		AST tmp206_AST = null;
		tmp206_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp206_AST);
		match(NUM_INT);
		if (0==inputState.guessing)
		{
			rangeOperand_AST = (AST)currentAST.root;
			rangeOperand_AST = (AST) astFactory.make(astFactory.create(NUMERIC_PARAM_RANGE,"rangeOperand"), rangeOperand_AST);
			currentAST.root = rangeOperand_AST;
			if ( (null != rangeOperand_AST) && (null != rangeOperand_AST.getFirstChild()) )
				currentAST.child = rangeOperand_AST.getFirstChild();
			else
				currentAST.child = rangeOperand_AST;
			currentAST.advanceChildToEnd();
		}
		rangeOperand_AST = currentAST.root;
		returnAST = rangeOperand_AST;
	}
	
	public void frequencyOperand() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST frequencyOperand_AST = null;
		
		match(STAR);
		match(DIV);
		AST tmp209_AST = null;
		tmp209_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp209_AST);
		match(NUM_INT);
		if (0==inputState.guessing)
		{
			frequencyOperand_AST = (AST)currentAST.root;
			frequencyOperand_AST = (AST) astFactory.make(astFactory.create(NUMERIC_PARAM_FREQUENCY,"frequencyOperand"), frequencyOperand_AST);
			currentAST.root = frequencyOperand_AST;
			if ( (null != frequencyOperand_AST) && (null != frequencyOperand_AST.getFirstChild()) )
				currentAST.child = frequencyOperand_AST.getFirstChild();
			else
				currentAST.child = frequencyOperand_AST;
			currentAST.advanceChildToEnd();
		}
		frequencyOperand_AST = currentAST.root;
		returnAST = frequencyOperand_AST;
	}
	
	public void time_period() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_period_AST = null;
		
		{
			if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==LITERAL_days||LA(2)==LITERAL_day))
			{
				dayPart();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{
					if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==LITERAL_hours||LA(2)==LITERAL_hour))
					{
						hourPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else if ((tokenSet_12_.member(LA(1))) && (tokenSet_14_.member(LA(2)))) {
					}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					
				}
				{
					if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==MIN||LA(2)==MINUTES||LA(2)==LITERAL_minute))
					{
						minutePart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else if ((tokenSet_12_.member(LA(1))) && (tokenSet_15_.member(LA(2)))) {
					}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					
				}
				{
					if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==SECONDS||LA(2)==LITERAL_second||LA(2)==LITERAL_sec))
					{
						secondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else if ((tokenSet_12_.member(LA(1))) && (tokenSet_16_.member(LA(2)))) {
					}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					
				}
				{
					switch ( LA(1) )
					{
					case NUM_INT:
					case NUM_LONG:
					case NUM_FLOAT:
					case NUM_DOUBLE:
					{
						millisecondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
						break;
					}
					case COMMA:
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
				}
			}
			else if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==LITERAL_hours||LA(2)==LITERAL_hour)) {
				hourPart();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{
					if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==MIN||LA(2)==MINUTES||LA(2)==LITERAL_minute))
					{
						minutePart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else if ((tokenSet_12_.member(LA(1))) && (tokenSet_15_.member(LA(2)))) {
					}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					
				}
				{
					if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==SECONDS||LA(2)==LITERAL_second||LA(2)==LITERAL_sec))
					{
						secondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else if ((tokenSet_12_.member(LA(1))) && (tokenSet_16_.member(LA(2)))) {
					}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					
				}
				{
					switch ( LA(1) )
					{
					case NUM_INT:
					case NUM_LONG:
					case NUM_FLOAT:
					case NUM_DOUBLE:
					{
						millisecondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
						break;
					}
					case COMMA:
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
				}
			}
			else if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==MIN||LA(2)==MINUTES||LA(2)==LITERAL_minute)) {
				minutePart();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{
					if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==SECONDS||LA(2)==LITERAL_second||LA(2)==LITERAL_sec))
					{
						secondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
					}
					else if ((tokenSet_12_.member(LA(1))) && (tokenSet_16_.member(LA(2)))) {
					}
					else
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					
				}
				{
					switch ( LA(1) )
					{
					case NUM_INT:
					case NUM_LONG:
					case NUM_FLOAT:
					case NUM_DOUBLE:
					{
						millisecondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
						break;
					}
					case COMMA:
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
				}
			}
			else if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (LA(2)==SECONDS||LA(2)==LITERAL_second||LA(2)==LITERAL_sec)) {
				secondPart();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				{
					switch ( LA(1) )
					{
					case NUM_INT:
					case NUM_LONG:
					case NUM_FLOAT:
					case NUM_DOUBLE:
					{
						millisecondPart();
						if (0 == inputState.guessing)
						{
							astFactory.addASTChild(ref currentAST, returnAST);
						}
						break;
					}
					case COMMA:
					case RPAREN:
					{
						break;
					}
					default:
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
				}
			}
			else if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && ((LA(2) >= LITERAL_milliseconds && LA(2) <= LITERAL_msec))) {
				millisecondPart();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
			}
			else
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			
		}
		if (0==inputState.guessing)
		{
			time_period_AST = (AST)currentAST.root;
			time_period_AST = (AST) astFactory.make(astFactory.create(TIME_PERIOD,"time_period"), time_period_AST);
			currentAST.root = time_period_AST;
			if ( (null != time_period_AST) && (null != time_period_AST.getFirstChild()) )
				currentAST.child = time_period_AST.getFirstChild();
			else
				currentAST.child = time_period_AST;
			currentAST.advanceChildToEnd();
		}
		time_period_AST = currentAST.root;
		returnAST = time_period_AST;
	}
	
	public void numericListParameter() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST numericListParameter_AST = null;
		
		if ((LA(1)==NUM_INT) && (LA(2)==COLON))
		{
			rangeOperand();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			numericListParameter_AST = currentAST.root;
		}
		else if ((LA(1)==STAR)) {
			frequencyOperand();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			numericListParameter_AST = currentAST.root;
		}
		else if ((LA(1)==NUM_INT) && (LA(2)==COMMA||LA(2)==RBRACK)) {
			AST tmp210_AST = null;
			tmp210_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp210_AST);
			match(NUM_INT);
			numericListParameter_AST = currentAST.root;
		}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = numericListParameter_AST;
	}
	
	public void classIdentifier() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST classIdentifier_AST = null;
		IToken  i1 = null;
		AST i1_AST = null;
		IToken  i2 = null;
		AST i2_AST = null;
		String identifier = "";
		
		i1 = LT(1);
		i1_AST = astFactory.create(i1);
		match(IDENT);
		if (0==inputState.guessing)
		{
			identifier = i1_AST.getText();
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==DOT) && (LA(2)==IDENT) && (tokenSet_0_.member(LA(3))) && (tokenSet_17_.member(LA(4))))
				{
					match(DOT);
					i2 = LT(1);
					i2_AST = astFactory.create(i2);
					match(IDENT);
					if (0==inputState.guessing)
					{
						identifier += "." + i2_AST.getText();
					}
				}
				else
				{
					goto _loop208_breakloop;
				}
				
			}
_loop208_breakloop:			;
		}    // ( ... )*
		if (0==inputState.guessing)
		{
			classIdentifier_AST = (AST)currentAST.root;
			classIdentifier_AST = (AST) astFactory.make(astFactory.create(CLASS_IDENT,"classIdentifier"), classIdentifier_AST); 
				      classIdentifier_AST.setText(identifier);
				
			currentAST.root = classIdentifier_AST;
			if ( (null != classIdentifier_AST) && (null != classIdentifier_AST.getFirstChild()) )
				currentAST.child = classIdentifier_AST.getFirstChild();
			else
				currentAST.child = classIdentifier_AST;
			currentAST.advanceChildToEnd();
		}
		classIdentifier_AST = currentAST.root;
		returnAST = classIdentifier_AST;
	}
	
	public void filterParamSet() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterParamSet_AST = null;
		
		expression();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{    // ( ... )*
			for (;;)
			{
				if ((LA(1)==COMMA))
				{
					match(COMMA);
					expression();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop214_breakloop;
				}
				
			}
_loop214_breakloop:			;
		}    // ( ... )*
		filterParamSet_AST = currentAST.root;
		returnAST = filterParamSet_AST;
	}
	
	public void eventPropertyAtomic() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventPropertyAtomic_AST = null;
		
		if ((LA(1)==IDENT) && (tokenSet_18_.member(LA(2))))
		{
			AST tmp213_AST = null;
			tmp213_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp213_AST);
			match(IDENT);
			if (0==inputState.guessing)
			{
				eventPropertyAtomic_AST = (AST)currentAST.root;
				eventPropertyAtomic_AST = (AST) astFactory.make(astFactory.create(EVENT_PROP_SIMPLE,"eventPropertySimple"), eventPropertyAtomic_AST);
				currentAST.root = eventPropertyAtomic_AST;
				if ( (null != eventPropertyAtomic_AST) && (null != eventPropertyAtomic_AST.getFirstChild()) )
					currentAST.child = eventPropertyAtomic_AST.getFirstChild();
				else
					currentAST.child = eventPropertyAtomic_AST;
				currentAST.advanceChildToEnd();
			}
			eventPropertyAtomic_AST = currentAST.root;
		}
		else if ((LA(1)==IDENT) && (LA(2)==LBRACK)) {
			AST tmp214_AST = null;
			tmp214_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp214_AST);
			match(IDENT);
			match(LBRACK);
			AST tmp216_AST = null;
			tmp216_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp216_AST);
			match(NUM_INT);
			match(RBRACK);
			if (0==inputState.guessing)
			{
				eventPropertyAtomic_AST = (AST)currentAST.root;
				eventPropertyAtomic_AST = (AST) astFactory.make(astFactory.create(EVENT_PROP_INDEXED,"eventPropertyIndexed"), eventPropertyAtomic_AST);
				currentAST.root = eventPropertyAtomic_AST;
				if ( (null != eventPropertyAtomic_AST) && (null != eventPropertyAtomic_AST.getFirstChild()) )
					currentAST.child = eventPropertyAtomic_AST.getFirstChild();
				else
					currentAST.child = eventPropertyAtomic_AST;
				currentAST.advanceChildToEnd();
			}
			eventPropertyAtomic_AST = currentAST.root;
		}
		else if ((LA(1)==IDENT) && (LA(2)==LPAREN)) {
			AST tmp218_AST = null;
			tmp218_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp218_AST);
			match(IDENT);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case STRING_LITERAL:
				{
					AST tmp220_AST = null;
					tmp220_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp220_AST);
					match(STRING_LITERAL);
					break;
				}
				case QUOTED_STRING_LITERAL:
				{
					AST tmp221_AST = null;
					tmp221_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp221_AST);
					match(QUOTED_STRING_LITERAL);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				 }
			}
			match(RPAREN);
			if (0==inputState.guessing)
			{
				eventPropertyAtomic_AST = (AST)currentAST.root;
				eventPropertyAtomic_AST = (AST) astFactory.make(astFactory.create(EVENT_PROP_MAPPED,"eventPropertyMapped"), eventPropertyAtomic_AST);
				currentAST.root = eventPropertyAtomic_AST;
				if ( (null != eventPropertyAtomic_AST) && (null != eventPropertyAtomic_AST.getFirstChild()) )
					currentAST.child = eventPropertyAtomic_AST.getFirstChild();
				else
					currentAST.child = eventPropertyAtomic_AST;
				currentAST.advanceChildToEnd();
			}
			eventPropertyAtomic_AST = currentAST.root;
		}
		else
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		
		returnAST = eventPropertyAtomic_AST;
	}
	
	public void dayPart() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dayPart_AST = null;
		
		number();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case LITERAL_days:
			{
				match(LITERAL_days);
				break;
			}
			case LITERAL_day:
			{
				match(LITERAL_day);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			dayPart_AST = (AST)currentAST.root;
			dayPart_AST = (AST) astFactory.make(astFactory.create(DAY_PART,"dayPart"), dayPart_AST);
			currentAST.root = dayPart_AST;
			if ( (null != dayPart_AST) && (null != dayPart_AST.getFirstChild()) )
				currentAST.child = dayPart_AST.getFirstChild();
			else
				currentAST.child = dayPart_AST;
			currentAST.advanceChildToEnd();
		}
		dayPart_AST = currentAST.root;
		returnAST = dayPart_AST;
	}
	
	public void hourPart() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hourPart_AST = null;
		
		number();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case LITERAL_hours:
			{
				match(LITERAL_hours);
				break;
			}
			case LITERAL_hour:
			{
				match(LITERAL_hour);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			hourPart_AST = (AST)currentAST.root;
			hourPart_AST = (AST) astFactory.make(astFactory.create(HOUR_PART,"hourPart"), hourPart_AST);
			currentAST.root = hourPart_AST;
			if ( (null != hourPart_AST) && (null != hourPart_AST.getFirstChild()) )
				currentAST.child = hourPart_AST.getFirstChild();
			else
				currentAST.child = hourPart_AST;
			currentAST.advanceChildToEnd();
		}
		hourPart_AST = currentAST.root;
		returnAST = hourPart_AST;
	}
	
	public void minutePart() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST minutePart_AST = null;
		
		number();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case MINUTES:
			{
				match(MINUTES);
				break;
			}
			case LITERAL_minute:
			{
				match(LITERAL_minute);
				break;
			}
			case MIN:
			{
				match(MIN);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			minutePart_AST = (AST)currentAST.root;
			minutePart_AST = (AST) astFactory.make(astFactory.create(MINUTE_PART,"minutePart"), minutePart_AST);
			currentAST.root = minutePart_AST;
			if ( (null != minutePart_AST) && (null != minutePart_AST.getFirstChild()) )
				currentAST.child = minutePart_AST.getFirstChild();
			else
				currentAST.child = minutePart_AST;
			currentAST.advanceChildToEnd();
		}
		minutePart_AST = currentAST.root;
		returnAST = minutePart_AST;
	}
	
	public void secondPart() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST secondPart_AST = null;
		
		number();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case SECONDS:
			{
				match(SECONDS);
				break;
			}
			case LITERAL_second:
			{
				match(LITERAL_second);
				break;
			}
			case LITERAL_sec:
			{
				match(LITERAL_sec);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			secondPart_AST = (AST)currentAST.root;
			secondPart_AST = (AST) astFactory.make(astFactory.create(SECOND_PART,"secondPart"), secondPart_AST);
			currentAST.root = secondPart_AST;
			if ( (null != secondPart_AST) && (null != secondPart_AST.getFirstChild()) )
				currentAST.child = secondPart_AST.getFirstChild();
			else
				currentAST.child = secondPart_AST;
			currentAST.advanceChildToEnd();
		}
		secondPart_AST = currentAST.root;
		returnAST = secondPart_AST;
	}
	
	public void millisecondPart() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST millisecondPart_AST = null;
		
		number();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case LITERAL_milliseconds:
			{
				match(LITERAL_milliseconds);
				break;
			}
			case LITERAL_millisecond:
			{
				match(LITERAL_millisecond);
				break;
			}
			case LITERAL_msec:
			{
				match(LITERAL_msec);
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			 }
		}
		if (0==inputState.guessing)
		{
			millisecondPart_AST = (AST)currentAST.root;
			millisecondPart_AST = (AST) astFactory.make(astFactory.create(MILLISECOND_PART,"millisecondPart"), millisecondPart_AST);
			currentAST.root = millisecondPart_AST;
			if ( (null != millisecondPart_AST) && (null != millisecondPart_AST.getFirstChild()) )
				currentAST.child = millisecondPart_AST.getFirstChild();
			else
				currentAST.child = millisecondPart_AST;
			currentAST.advanceChildToEnd();
		}
		millisecondPart_AST = currentAST.root;
		returnAST = millisecondPart_AST;
	}
	
	private void initializeFactory()
	{
		if (astFactory == null)
		{
			astFactory = new ASTFactory();
		}
		initializeASTFactory( astFactory );
	}
	static public void initializeASTFactory( ASTFactory factory )
	{
		factory.setMaxNodeType(217);
	}
	
	public static readonly string[] tokenNames_ = new string[] {
		@"""<0>""",
		@"""EOF""",
		@"""<2>""",
		@"""NULL_TREE_LOOKAHEAD""",
		@"""in""",
		@"""between""",
		@"""like""",
		@"""regexp""",
		@"""escape""",
		@"""or""",
		@"""and""",
		@"""not""",
		@"""every""",
		@"""where""",
		@"""as""",
		@"""sum""",
		@"""avg""",
		@"""max""",
		@"""min""",
		@"""coalesce""",
		@"""median""",
		@"""stddev""",
		@"""avedev""",
		@"""count""",
		@"""select""",
		@"""case""",
		@"""CASE2""",
		@"""else""",
		@"""when""",
		@"""then""",
		@"""end""",
		@"""from""",
		@"""outer""",
		@"""join""",
		@"""left""",
		@"""right""",
		@"""full""",
		@"""on""",
		@"""is""",
		@"""by""",
		@"""group""",
		@"""having""",
		@"""distinct""",
		@"""all""",
		@"""output""",
		@"""events""",
		@"""seconds""",
		@"""minutes""",
		@"""first""",
		@"""last""",
		@"""insert""",
		@"""into""",
		@"""order""",
		@"""asc""",
		@"""desc""",
		@"""rstream""",
		@"""istream""",
		@"""pattern""",
		@"""sql""",
		@"""prev""",
		@"""prior""",
		@"""exists""",
		@"""NUMERIC_PARAM_RANGE""",
		@"""NUMERIC_PARAM_LIST""",
		@"""NUMERIC_PARAM_FREQUENCY""",
		@"""FOLLOWED_BY_EXPR""",
		@"""ARRAY_PARAM_LIST""",
		@"""EVENT_FILTER_EXPR""",
		@"""EVENT_FILTER_NAME_TAG""",
		@"""EVENT_FILTER_IDENT""",
		@"""EVENT_FILTER_PARAM""",
		@"""EVENT_FILTER_RANGE""",
		@"""EVENT_FILTER_NOT_RANGE""",
		@"""EVENT_FILTER_IN""",
		@"""EVENT_FILTER_NOT_IN""",
		@"""EVENT_FILTER_BETWEEN""",
		@"""EVENT_FILTER_NOT_BETWEEN""",
		@"""CLASS_IDENT""",
		@"""GUARD_EXPR""",
		@"""OBSERVER_EXPR""",
		@"""VIEW_EXPR""",
		@"""PATTERN_INCL_EXPR""",
		@"""DATABASE_JOIN_EXPR""",
		@"""WHERE_EXPR""",
		@"""HAVING_EXPR""",
		@"""EVAL_BITWISE_EXPR""",
		@"""EVAL_AND_EXPR""",
		@"""EVAL_OR_EXPR""",
		@"""EVAL_EQUALS_EXPR""",
		@"""EVAL_NOTEQUALS_EXPR""",
		@"""EVAL_IDENT""",
		@"""SELECTION_EXPR""",
		@"""SELECTION_ELEMENT_EXPR""",
		@"""STREAM_EXPR""",
		@"""OUTERJOIN_EXPR""",
		@"""LEFT_OUTERJOIN_EXPR""",
		@"""RIGHT_OUTERJOIN_EXPR""",
		@"""FULL_OUTERJOIN_EXPR""",
		@"""GROUP_BY_EXPR""",
		@"""ORDER_BY_EXPR""",
		@"""ORDER_ELEMENT_EXPR""",
		@"""EVENT_PROP_EXPR""",
		@"""EVENT_PROP_SIMPLE""",
		@"""EVENT_PROP_MAPPED""",
		@"""EVENT_PROP_INDEXED""",
		@"""EVENT_LIMIT_EXPR""",
		@"""SEC_LIMIT_EXPR""",
		@"""MIN_LIMIT_EXPR""",
		@"""INSERTINTO_EXPR""",
		@"""INSERTINTO_EXPRCOL""",
		@"""CONCAT""",
		@"""LIB_FUNCTION""",
		@"""UNARY_MINUS""",
		@"""TIME_PERIOD""",
		@"""ARRAY_EXPR""",
		@"""DAY_PART""",
		@"""HOUR_PART""",
		@"""MINUTE_PART""",
		@"""SECOND_PART""",
		@"""MILLISECOND_PART""",
		@"""NOT_IN_SET""",
		@"""NOT_BETWEEN""",
		@"""NOT_LIKE""",
		@"""NOT_REGEXP""",
		@"""DBSELECT_EXPR""",
		@"""DBFROM_CLAUSE""",
		@"""DBWHERE_CLAUSE""",
		@"""WILDCARD_SELECT""",
		@"""INSERTINTO_STREAM_NAME""",
		@"""IN_RANGE""",
		@"""NOT_IN_RANGE""",
		@"""SUBSELECT_EXPR""",
		@"""EXISTS_SUBSELECT_EXPR""",
		@"""IN_SUBSELECT_EXPR""",
		@"""NOT_IN_SUBSELECT_EXPR""",
		@"""IN_SUBSELECT_QUERY_EXPR""",
		@"""INT_TYPE""",
		@"""LONG_TYPE""",
		@"""FLOAT_TYPE""",
		@"""DOUBLE_TYPE""",
		@"""STRING_TYPE""",
		@"""BOOL_TYPE""",
		@"""NULL_TYPE""",
		@"""a numeric literal""",
		@"""NUM_LONG""",
		@"""NUM_FLOAT""",
		@"""NUM_DOUBLE""",
		@"""a minus '-'""",
		@"""a plus operator '+'""",
		@"""true""",
		@"""false""",
		@"""null""",
		@"""STRING_LITERAL""",
		@"""QUOTED_STRING_LITERAL""",
		@"""an identifier""",
		@"""an opening parenthesis '('""",
		@"""a comma ','""",
		@"""a closing parenthesis ')'""",
		@"""an equals '='""",
		@"""a star '*'""",
		@"""DOT""",
		@"""a left angle bracket '['""",
		@"""a right angle bracket ']'""",
		@"""a colon ':'""",
		@"""a binary and '&'""",
		@"""a binary or '|'""",
		@"""a binary xor '^'""",
		@"""a sql-style not equals '<>'""",
		@"""a not equals '!='""",
		@"""a lesser then '<'""",
		@"""a greater then '>'""",
		@"""a less equals '<='""",
		@"""a greater equals '>='""",
		@"""a logical or '||'""",
		@"""a division operator '\\'""",
		@"""a modulo '%'""",
		@"""a left curly bracket '{'""",
		@"""a right curly bracket '}'""",
		@"""an followed-by '->'""",
		@"""days""",
		@"""day""",
		@"""hours""",
		@"""hour""",
		@"""minute""",
		@"""second""",
		@"""sec""",
		@"""milliseconds""",
		@"""millisecond""",
		@"""msec""",
		@"""a questionmark '?'""",
		@"""an equals compare '=='""",
		@"""a not '!'""",
		@"""a binary not '~'""",
		@"""a division assign '/='""",
		@"""a plus assign '+='""",
		@"""an increment operator '++'""",
		@"""a minus assign '-='""",
		@"""a decrement operator '--'""",
		@"""a star assign '*='""",
		@"""a module assign '%='""",
		@"""a shift right '>>'""",
		@"""a shift right assign '>>='""",
		@"""a binary shift right '>>>'""",
		@"""a binary shift right assign '>>>='""",
		@"""a shift left '<<'""",
		@"""a shift left assign '<<='""",
		@"""a binary xor assign '^='""",
		@"""a binary or assign '|='""",
		@"""a binary and assign '&='""",
		@"""a logical and '&&'""",
		@"""a semicolon ';'""",
		@"""WS""",
		@"""SL_COMMENT""",
		@"""ML_COMMENT""",
		@"""ESC""",
		@"""HEX_DIGIT""",
		@"""EXPONENT""",
		@"""FLOAT_SUFFIX"""
	};
	
	private static long[] mk_tokenSet_0_()
	{
		long[] data = { 4524748046362114L, 0L, 1125922388312064L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_0_ = new BitSet(mk_tokenSet_0_());
	private static long[] mk_tokenSet_1_()
	{
		long[] data = { 4035225266174265344L, 0L, 281475245113344L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_1_ = new BitSet(mk_tokenSet_1_());
	private static long[] mk_tokenSet_2_()
	{
		long[] data = { 4035225266140710912L, 0L, 281475245113344L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_2_ = new BitSet(mk_tokenSet_2_());
	private static long[] mk_tokenSet_3_()
	{
		long[] data = { 274877906944L, 0L, 1650341183488L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_3_ = new BitSet(mk_tokenSet_3_());
	private static long[] mk_tokenSet_4_()
	{
		long[] data = { 4035225266140708864L, 0L, 281475245113344L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_4_ = new BitSet(mk_tokenSet_4_());
	private static long[] mk_tokenSet_5_()
	{
		long[] data = { 31546367151198194L, 0L, 844411778859008L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_5_ = new BitSet(mk_tokenSet_5_());
	private static long[] mk_tokenSet_6_()
	{
		long[] data = { 31546367151198194L, 0L, 844424797487104L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_6_ = new BitSet(mk_tokenSet_6_());
	private static long[] mk_tokenSet_7_()
	{
		long[] data = { 4499971226030047218L, 0L, 2251799813652480L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_7_ = new BitSet(mk_tokenSet_7_());
	private static long[] mk_tokenSet_8_()
	{
		long[] data = { 4499971230341791730L, 0L, 2251799813652480L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_8_ = new BitSet(mk_tokenSet_8_());
	private static long[] mk_tokenSet_9_()
	{
		long[] data = { 4035238460313798656L, 0L, 281475781984256L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_9_ = new BitSet(mk_tokenSet_9_());
	private static long[] mk_tokenSet_10_()
	{
		long[] data = { 4066771633342238706L, 0L, 1125899906809856L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_10_ = new BitSet(mk_tokenSet_10_());
	private static long[] mk_tokenSet_11_()
	{
		long[] data = { 9730L, 0L, 1125923126509568L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_11_ = new BitSet(mk_tokenSet_11_());
	private static long[] mk_tokenSet_12_()
	{
		long[] data = { 0L, 0L, 805797888L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_12_ = new BitSet(mk_tokenSet_12_());
	private static long[] mk_tokenSet_13_()
	{
		long[] data = { 211106232795136L, 0L, 2303591209400008704L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_13_ = new BitSet(mk_tokenSet_13_());
	private static long[] mk_tokenSet_14_()
	{
		long[] data = { 4735854279157250L, 0L, 2271221620230029312L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_14_ = new BitSet(mk_tokenSet_14_());
	private static long[] mk_tokenSet_15_()
	{
		long[] data = { 4595116790539778L, 0L, 2235192823211065344L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_15_ = new BitSet(mk_tokenSet_15_());
	private static long[] mk_tokenSet_16_()
	{
		long[] data = { 4524748046362114L, 0L, 2019020041097281536L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_16_ = new BitSet(mk_tokenSet_16_());
	private static long[] mk_tokenSet_17_()
	{
		long[] data = { 4499971230325014514L, 0L, 2251791223717888L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_17_ = new BitSet(mk_tokenSet_17_());
	private static long[] mk_tokenSet_18_()
	{
		long[] data = { 31546487410290674L, 0L, 844416073334784L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_18_ = new BitSet(mk_tokenSet_18_());
	
}
}
