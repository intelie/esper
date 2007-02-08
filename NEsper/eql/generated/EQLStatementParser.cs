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
		public const int NUMERIC_PARAM_RANGE = 59;
		public const int NUMERIC_PARAM_LIST = 60;
		public const int NUMERIC_PARAM_FREQUENCY = 61;
		public const int FOLLOWED_BY_EXPR = 62;
		public const int ARRAY_PARAM_LIST = 63;
		public const int EVENT_FILTER_EXPR = 64;
		public const int EVENT_FILTER_NAME_TAG = 65;
		public const int EVENT_FILTER_IDENT = 66;
		public const int EVENT_FILTER_PARAM = 67;
		public const int CLASS_IDENT = 68;
		public const int GUARD_EXPR = 69;
		public const int OBSERVER_EXPR = 70;
		public const int VIEW_EXPR = 71;
		public const int PATTERN_INCL_EXPR = 72;
		public const int DATABASE_JOIN_EXPR = 73;
		public const int WHERE_EXPR = 74;
		public const int HAVING_EXPR = 75;
		public const int EVAL_BITWISE_EXPR = 76;
		public const int EVAL_AND_EXPR = 77;
		public const int EVAL_OR_EXPR = 78;
		public const int EVAL_EQUALS_EXPR = 79;
		public const int EVAL_NOTEQUALS_EXPR = 80;
		public const int EVAL_IDENT = 81;
		public const int SELECTION_EXPR = 82;
		public const int SELECTION_ELEMENT_EXPR = 83;
		public const int STREAM_EXPR = 84;
		public const int OUTERJOIN_EXPR = 85;
		public const int LEFT_OUTERJOIN_EXPR = 86;
		public const int RIGHT_OUTERJOIN_EXPR = 87;
		public const int FULL_OUTERJOIN_EXPR = 88;
		public const int GROUP_BY_EXPR = 89;
		public const int ORDER_BY_EXPR = 90;
		public const int ORDER_ELEMENT_EXPR = 91;
		public const int EVENT_PROP_EXPR = 92;
		public const int EVENT_PROP_SIMPLE = 93;
		public const int EVENT_PROP_MAPPED = 94;
		public const int EVENT_PROP_INDEXED = 95;
		public const int EVENT_LIMIT_EXPR = 96;
		public const int SEC_LIMIT_EXPR = 97;
		public const int MIN_LIMIT_EXPR = 98;
		public const int INSERTINTO_EXPR = 99;
		public const int INSERTINTO_EXPRCOL = 100;
		public const int CONCAT = 101;
		public const int LIB_FUNCTION = 102;
		public const int UNARY_MINUS = 103;
		public const int TIME_PERIOD = 104;
		public const int DAY_PART = 105;
		public const int HOUR_PART = 106;
		public const int MINUTE_PART = 107;
		public const int SECOND_PART = 108;
		public const int MILLISECOND_PART = 109;
		public const int NOT_IN_SET = 110;
		public const int NOT_BETWEEN = 111;
		public const int NOT_LIKE = 112;
		public const int NOT_REGEXP = 113;
		public const int DBSELECT_EXPR = 114;
		public const int DBFROM_CLAUSE = 115;
		public const int DBWHERE_CLAUSE = 116;
		public const int INT_TYPE = 117;
		public const int LONG_TYPE = 118;
		public const int FLOAT_TYPE = 119;
		public const int DOUBLE_TYPE = 120;
		public const int STRING_TYPE = 121;
		public const int BOOL_TYPE = 122;
		public const int NULL_TYPE = 123;
		public const int NUM_INT = 124;
		public const int NUM_LONG = 125;
		public const int NUM_FLOAT = 126;
		public const int NUM_DOUBLE = 127;
		public const int MINUS = 128;
		public const int PLUS = 129;
		public const int LITERAL_true = 130;
		public const int LITERAL_false = 131;
		public const int LITERAL_null = 132;
		public const int STRING_LITERAL = 133;
		public const int QUOTED_STRING_LITERAL = 134;
		public const int IDENT = 135;
		public const int LPAREN = 136;
		public const int COMMA = 137;
		public const int RPAREN = 138;
		public const int EQUALS = 139;
		public const int STAR = 140;
		public const int DOT = 141;
		public const int LBRACK = 142;
		public const int RBRACK = 143;
		public const int COLON = 144;
		public const int BAND = 145;
		public const int BOR = 146;
		public const int BXOR = 147;
		public const int SQL_NE = 148;
		public const int NOT_EQUAL = 149;
		public const int LT_ = 150;
		public const int GT = 151;
		public const int LE = 152;
		public const int GE = 153;
		public const int LOR = 154;
		public const int DIV = 155;
		public const int MOD = 156;
		public const int FOLLOWED_BY = 157;
		public const int LCURLY = 158;
		public const int RCURLY = 159;
		public const int LITERAL_days = 160;
		public const int LITERAL_day = 161;
		public const int LITERAL_hours = 162;
		public const int LITERAL_hour = 163;
		public const int LITERAL_minute = 164;
		public const int LITERAL_second = 165;
		public const int LITERAL_sec = 166;
		public const int LITERAL_milliseconds = 167;
		public const int LITERAL_millisecond = 168;
		public const int LITERAL_msec = 169;
		public const int QUESTION = 170;
		public const int EQUAL = 171;
		public const int LNOT = 172;
		public const int BNOT = 173;
		public const int DIV_ASSIGN = 174;
		public const int PLUS_ASSIGN = 175;
		public const int INC = 176;
		public const int MINUS_ASSIGN = 177;
		public const int DEC = 178;
		public const int STAR_ASSIGN = 179;
		public const int MOD_ASSIGN = 180;
		public const int SR = 181;
		public const int SR_ASSIGN = 182;
		public const int BSR = 183;
		public const int BSR_ASSIGN = 184;
		public const int SL = 185;
		public const int SL_ASSIGN = 186;
		public const int BXOR_ASSIGN = 187;
		public const int BOR_ASSIGN = 188;
		public const int BAND_ASSIGN = 189;
		public const int LAND = 190;
		public const int SEMI = 191;
		public const int WS = 192;
		public const int SL_COMMENT = 193;
		public const int ML_COMMENT = 194;
		public const int ESC = 195;
		public const int HEX_DIGIT = 196;
		public const int EXPONENT = 197;
		public const int FLOAT_SUFFIX = 198;
		
		
		protected void Initialize()
		{
			tokenNames = tokenNames_;
			initializeFactory();
		}
		
		
		protected EQLStatementParser(TokenBuffer tokenBuf, int k) : base(tokenBuf, k)
		{
			Initialize();
		}
		
		public EQLStatementParser(TokenBuffer tokenBuf) : this(tokenBuf,3)
		{
		}
		
		protected EQLStatementParser(TokenStream lexer, int k) : base(lexer,k)
		{
			Initialize();
		}
		
		public EQLStatementParser(TokenStream lexer) : this(lexer,3)
		{
		}
		
		public EQLStatementParser(ParserSharedInputState state) : base(state,3)
		{
			Initialize();
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
					goto _loop210_breakloop;
				}
				
			}
_loop210_breakloop:			;
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
				constant_AST.setType(n_AST.Type); 
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
		IToken  s = null;
		AST s_AST = null;
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
			case STAR:
			{
				s = LT(1);
				s_AST = astFactory.create(s);
				astFactory.addASTChild(ref currentAST, s_AST);
				match(STAR);
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
			{
				selectionList();
				if (0 == inputState.guessing)
				{
					l_AST = (AST)returnAST;
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
		if (0==inputState.guessing)
		{
			selectClause_AST = (AST)currentAST.root;
			selectClause_AST = (AST) astFactory.make(astFactory.create(SELECTION_EXPR,"selectClause"), r_AST, i_AST, s_AST, l_AST);
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
							goto _loop44_breakloop;
						}
						
					}
_loop44_breakloop:					;
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
					goto _loop53_breakloop;
				}
				
			}
_loop53_breakloop:			;
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
					goto _loop56_breakloop;
				}
				
			}
_loop56_breakloop:			;
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
					goto _loop77_breakloop;
				}
				
			}
_loop77_breakloop:			;
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
					goto _loop37_breakloop;
				}
				
			}
_loop37_breakloop:			;
		}    // ( ... )*
		selectionList_AST = currentAST.root;
		returnAST = selectionList_AST;
	}
	
	public void selectionListElement() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectionListElement_AST = null;
		
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
				AST tmp52_AST = null;
				tmp52_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp52_AST);
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
					case IDENT:
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
		AST tmp61_AST = null;
		tmp61_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp61_AST);
		match(IDENT);
		match(LBRACK);
		{
			switch ( LA(1) )
			{
			case STRING_LITERAL:
			{
				AST tmp63_AST = null;
				tmp63_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp63_AST);
				match(STRING_LITERAL);
				break;
			}
			case QUOTED_STRING_LITERAL:
			{
				AST tmp64_AST = null;
				tmp64_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp64_AST);
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
		
		AST tmp66_AST = null;
		tmp66_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp66_AST);
		match(IDENT);
		match(COLON);
		AST tmp68_AST = null;
		tmp68_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp68_AST);
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
					goto _loop170_breakloop;
				}
				
			}
_loop170_breakloop:			;
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
				AST tmp72_AST = null;
				tmp72_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp72_AST);
				match(ASC);
				break;
			}
			case DESC:
			{
				AST tmp73_AST = null;
				tmp73_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp73_AST);
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
			AST tmp74_AST = null;
			tmp74_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp74_AST);
			match(CASE);
			{ // ( ... )+
				int _cnt66=0;
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
						if (_cnt66 >= 1) { goto _loop66_breakloop; } else { throw new NoViableAltException(LT(1), getFilename());; }
					}
					
					_cnt66++;
				}
_loop66_breakloop:				;
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
			AST tmp76_AST = null;
			tmp76_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp76_AST);
			match(CASE);
			if (0==inputState.guessing)
			{
				tmp76_AST.setType(CASE2);
			}
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			{ // ( ... )+
				int _cnt69=0;
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
						if (_cnt69 >= 1) { goto _loop69_breakloop; } else { throw new NoViableAltException(LT(1), getFilename());; }
					}
					
					_cnt69++;
				}
_loop69_breakloop:				;
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
					goto _loop80_breakloop;
				}
				
			}
_loop80_breakloop:			;
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
							AST tmp81_AST = null;
							tmp81_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp81_AST);
							match(BAND);
							break;
						}
						case BOR:
						{
							AST tmp82_AST = null;
							tmp82_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp82_AST);
							match(BOR);
							break;
						}
						case BXOR:
						{
							AST tmp83_AST = null;
							tmp83_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp83_AST);
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
					goto _loop84_breakloop;
				}
				
			}
_loop84_breakloop:			;
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
			AST tmp84_AST = null;
			tmp84_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp84_AST);
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
					goto _loop89_breakloop;
				}
				
			}
_loop89_breakloop:			;
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
			case BAND:
			case BOR:
			case BXOR:
			case SQL_NE:
			case NOT_EQUAL:
			case LT_:
			case GT:
			case LE:
			case GE:
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
										AST tmp86_AST = null;
										tmp86_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp86_AST);
										match(LT_);
										break;
									}
									case GT:
									{
										AST tmp87_AST = null;
										tmp87_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp87_AST);
										match(GT);
										break;
									}
									case LE:
									{
										AST tmp88_AST = null;
										tmp88_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp88_AST);
										match(LE);
										break;
									}
									case GE:
									{
										AST tmp89_AST = null;
										tmp89_AST = astFactory.create(LT(1));
										astFactory.makeASTRoot(ref currentAST, tmp89_AST);
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
								goto _loop95_breakloop;
							}
							
						}
_loop95_breakloop:						;
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
					case IN_SET:
					{
						{
							i = LT(1);
							i_AST = astFactory.create(i);
							astFactory.makeASTRoot(ref currentAST, i_AST);
							match(IN_SET);
							if (0==inputState.guessing)
							{
								
														i_AST.setType( (n == null) ? IN_SET : NOT_IN_SET);
														i_AST.setText( (n == null) ? "in" : "not in");
													
							}
							{
								match(LPAREN);
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
											goto _loop101_breakloop;
										}
										
									}
_loop101_breakloop:									;
								}    // ( ... )*
								match(RPAREN);
							}
						}
						break;
					}
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
								case BAND:
								case BOR:
								case BXOR:
								case SQL_NE:
								case NOT_EQUAL:
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
					{
						throw new NoViableAltException(LT(1), getFilename());
					}
					 }
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
							goto _loop109_breakloop;
						}
						
					}
_loop109_breakloop:					;
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
			case BAND:
			case BOR:
			case BXOR:
			case SQL_NE:
			case NOT_EQUAL:
			case LT_:
			case GT:
			case LE:
			case GE:
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
							AST tmp96_AST = null;
							tmp96_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp96_AST);
							match(PLUS);
							break;
						}
						case MINUS:
						{
							AST tmp97_AST = null;
							tmp97_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp97_AST);
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
					goto _loop113_breakloop;
				}
				
			}
_loop113_breakloop:			;
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
							AST tmp98_AST = null;
							tmp98_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp98_AST);
							match(STAR);
							break;
						}
						case DIV:
						{
							AST tmp99_AST = null;
							tmp99_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp99_AST);
							match(DIV);
							break;
						}
						case MOD:
						{
							AST tmp100_AST = null;
							tmp100_AST = astFactory.create(LT(1));
							astFactory.makeASTRoot(ref currentAST, tmp100_AST);
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
					goto _loop117_breakloop;
				}
				
			}
_loop117_breakloop:			;
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
		case LPAREN:
		{
			match(LPAREN);
			expression();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			match(RPAREN);
			unaryExpression_AST = currentAST.root;
			break;
		}
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
		{
			builtinFunc();
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
				AST tmp103_AST = null;
				tmp103_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp103_AST);
				match(MINUS);
				if (0==inputState.guessing)
				{
					tmp103_AST.setType(UNARY_MINUS);
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
		
		bool synPredMatched138 = false;
		if (((LA(1)==IDENT) && (tokenSet_6_.member(LA(2))) && (tokenSet_7_.member(LA(3)))))
		{
			int _m138 = mark();
			synPredMatched138 = true;
			inputState.guessing++;
			try {
				{
					eventProperty();
				}
			}
			catch (RecognitionException)
			{
				synPredMatched138 = false;
			}
			rewind(_m138);
			inputState.guessing--;
		}
		if ( synPredMatched138 )
		{
			eventProperty();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			eventPropertyOrLibFunction_AST = currentAST.root;
		}
		else if ((LA(1)==MAX||LA(1)==MIN||LA(1)==IDENT) && (LA(2)==LPAREN||LA(2)==DOT) && (tokenSet_8_.member(LA(3)))) {
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
			AST tmp104_AST = null;
			tmp104_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp104_AST);
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
					AST tmp107_AST = null;
					tmp107_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp107_AST);
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
			AST tmp109_AST = null;
			tmp109_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp109_AST);
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
					AST tmp112_AST = null;
					tmp112_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp112_AST);
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
			AST tmp114_AST = null;
			tmp114_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp114_AST);
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
								AST tmp117_AST = null;
								tmp117_AST = astFactory.create(LT(1));
								astFactory.addASTChild(ref currentAST, tmp117_AST);
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
			AST tmp120_AST = null;
			tmp120_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp120_AST);
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
					AST tmp123_AST = null;
					tmp123_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp123_AST);
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
			AST tmp125_AST = null;
			tmp125_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp125_AST);
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
					AST tmp128_AST = null;
					tmp128_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp128_AST);
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
			AST tmp130_AST = null;
			tmp130_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp130_AST);
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
					AST tmp133_AST = null;
					tmp133_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp133_AST);
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
			AST tmp135_AST = null;
			tmp135_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp135_AST);
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
						goto _loop130_breakloop;
					}
					
				}
_loop130_breakloop:				;
			}    // ( ... )*
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
				AST tmp140_AST = null;
				tmp140_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp140_AST);
				match(MAX);
				break;
			}
			case MIN:
			{
				AST tmp141_AST = null;
				tmp141_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp141_AST);
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
							goto _loop135_breakloop;
						}
						
					}
_loop135_breakloop:					;
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
					goto _loop193_breakloop;
				}
				
			}
_loop193_breakloop:			;
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
			AST tmp150_AST = null;
			tmp150_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp150_AST);
			match(IDENT);
			funcIdent_AST = currentAST.root;
			break;
		}
		case MAX:
		{
			AST tmp151_AST = null;
			tmp151_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp151_AST);
			match(MAX);
			if (0==inputState.guessing)
			{
				tmp151_AST.setType(IDENT);
			}
			funcIdent_AST = currentAST.root;
			break;
		}
		case MIN:
		{
			AST tmp152_AST = null;
			tmp152_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp152_AST);
			match(MIN);
			if (0==inputState.guessing)
			{
				tmp152_AST.setType(IDENT);
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
				AST tmp154_AST = null;
				tmp154_AST = astFactory.create(LT(1));
				astFactory.addASTChild(ref currentAST, tmp154_AST);
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
					goto _loop146_breakloop;
				}
				
			}
_loop146_breakloop:			;
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
					goto _loop151_breakloop;
				}
				
			}
_loop151_breakloop:			;
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
					goto _loop154_breakloop;
				}
				
			}
_loop154_breakloop:			;
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
					goto _loop157_breakloop;
				}
				
			}
_loop157_breakloop:			;
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
				AST tmp156_AST = null;
				tmp156_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp156_AST);
				match(EVERY_EXPR);
				break;
			}
			case NOT_EXPR:
			{
				AST tmp157_AST = null;
				tmp157_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp157_AST);
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
		else if ((LA(1)==IDENT) && (tokenSet_9_.member(LA(2)))) {
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
		
		AST tmp160_AST = null;
		tmp160_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp160_AST);
		match(IDENT);
		match(COLON);
		AST tmp162_AST = null;
		tmp162_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp162_AST);
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
		
		AST tmp165_AST = null;
		tmp165_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp165_AST);
		match(IDENT);
		match(COLON);
		AST tmp167_AST = null;
		tmp167_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp167_AST);
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
			AST tmp170_AST = null;
			tmp170_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(ref currentAST, tmp170_AST);
			match(STAR);
			singleParameter_AST = currentAST.root;
		}
		else if (((LA(1) >= NUM_INT && LA(1) <= QUOTED_STRING_LITERAL)) && (tokenSet_10_.member(LA(2)))) {
			constant();
			if (0 == inputState.guessing)
			{
				astFactory.addASTChild(ref currentAST, returnAST);
			}
			singleParameter_AST = currentAST.root;
		}
		else if (((LA(1) >= NUM_INT && LA(1) <= NUM_DOUBLE)) && (tokenSet_11_.member(LA(2)))) {
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
					goto _loop177_breakloop;
				}
				
			}
_loop177_breakloop:			;
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
							goto _loop182_breakloop;
						}
						
					}
_loop182_breakloop:					;
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
		
		AST tmp177_AST = null;
		tmp177_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp177_AST);
		match(NUM_INT);
		match(COLON);
		AST tmp179_AST = null;
		tmp179_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp179_AST);
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
		AST tmp182_AST = null;
		tmp182_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp182_AST);
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
					else if ((tokenSet_10_.member(LA(1))) && (tokenSet_12_.member(LA(2)))) {
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
					else if ((tokenSet_10_.member(LA(1))) && (tokenSet_13_.member(LA(2)))) {
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
					else if ((tokenSet_10_.member(LA(1))) && (tokenSet_14_.member(LA(2)))) {
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
					else if ((tokenSet_10_.member(LA(1))) && (tokenSet_13_.member(LA(2)))) {
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
					else if ((tokenSet_10_.member(LA(1))) && (tokenSet_14_.member(LA(2)))) {
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
					else if ((tokenSet_10_.member(LA(1))) && (tokenSet_14_.member(LA(2)))) {
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
			AST tmp183_AST = null;
			tmp183_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp183_AST);
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
				if ((LA(1)==DOT) && (LA(2)==IDENT) && (tokenSet_0_.member(LA(3))))
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
					goto _loop190_breakloop;
				}
				
			}
_loop190_breakloop:			;
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
		
		filterParameter();
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
					filterParameter();
					if (0 == inputState.guessing)
					{
						astFactory.addASTChild(ref currentAST, returnAST);
					}
				}
				else
				{
					goto _loop196_breakloop;
				}
				
			}
_loop196_breakloop:			;
		}    // ( ... )*
		filterParamSet_AST = currentAST.root;
		returnAST = filterParamSet_AST;
	}
	
	public void filterParameter() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterParameter_AST = null;
		
		eventProperty();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		{
			switch ( LA(1) )
			{
			case EQUALS:
			case NOT_EQUAL:
			case LT_:
			case GT:
			case LE:
			case GE:
			{
				filterParamConstant();
				if (0 == inputState.guessing)
				{
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case IN_SET:
			{
				filterParamRange();
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
		if (0==inputState.guessing)
		{
			filterParameter_AST = (AST)currentAST.root;
			filterParameter_AST = (AST) astFactory.make(astFactory.create(EVENT_FILTER_PARAM,"filterParameter"), filterParameter_AST);
			currentAST.root = filterParameter_AST;
			if ( (null != filterParameter_AST) && (null != filterParameter_AST.getFirstChild()) )
				currentAST.child = filterParameter_AST.getFirstChild();
			else
				currentAST.child = filterParameter_AST;
			currentAST.advanceChildToEnd();
		}
		filterParameter_AST = currentAST.root;
		returnAST = filterParameter_AST;
	}
	
	public void filterParamConstant() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterParamConstant_AST = null;
		
		{
			switch ( LA(1) )
			{
			case EQUALS:
			{
				AST tmp186_AST = null;
				tmp186_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp186_AST);
				match(EQUALS);
				break;
			}
			case NOT_EQUAL:
			{
				AST tmp187_AST = null;
				tmp187_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp187_AST);
				match(NOT_EQUAL);
				break;
			}
			case LT_:
			{
				AST tmp188_AST = null;
				tmp188_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp188_AST);
				match(LT_);
				break;
			}
			case LE:
			{
				AST tmp189_AST = null;
				tmp189_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp189_AST);
				match(LE);
				break;
			}
			case GE:
			{
				AST tmp190_AST = null;
				tmp190_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp190_AST);
				match(GE);
				break;
			}
			case GT:
			{
				AST tmp191_AST = null;
				tmp191_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(ref currentAST, tmp191_AST);
				match(GT);
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
				break;
			}
			case IDENT:
			{
				filterIdentifier();
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
		filterParamConstant_AST = currentAST.root;
		returnAST = filterParamConstant_AST;
	}
	
	public void filterParamRange() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterParamRange_AST = null;
		IToken  l1 = null;
		AST l1_AST = null;
		IToken  l2 = null;
		AST l2_AST = null;
		AST c_AST = null;
		AST f1_AST = null;
		IToken  r1 = null;
		AST r1_AST = null;
		IToken  r2 = null;
		AST r2_AST = null;
		
		AST tmp192_AST = null;
		tmp192_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(ref currentAST, tmp192_AST);
		match(IN_SET);
		{
			switch ( LA(1) )
			{
			case LPAREN:
			{
				l1 = LT(1);
				l1_AST = astFactory.create(l1);
				match(LPAREN);
				break;
			}
			case LBRACK:
			{
				l2 = LT(1);
				l2_AST = astFactory.create(l2);
				match(LBRACK);
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
					c_AST = (AST)returnAST;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				break;
			}
			case IDENT:
			{
				filterIdentifier();
				if (0 == inputState.guessing)
				{
					f1_AST = (AST)returnAST;
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
		match(COLON);
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
				break;
			}
			case IDENT:
			{
				filterIdentifier();
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
			case RPAREN:
			{
				r1 = LT(1);
				r1_AST = astFactory.create(r1);
				match(RPAREN);
				break;
			}
			case RBRACK:
			{
				r2 = LT(1);
				r2_AST = astFactory.create(r2);
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
			filterParamRange_AST = (AST)currentAST.root;
			filterParamRange_AST = (AST) astFactory.make(tmp192_AST, l1_AST, l2_AST, c_AST, f1_AST, r1_AST, r2_AST);
			currentAST.root = filterParamRange_AST;
			if ( (null != filterParamRange_AST) && (null != filterParamRange_AST.getFirstChild()) )
				currentAST.child = filterParamRange_AST.getFirstChild();
			else
				currentAST.child = filterParamRange_AST;
			currentAST.advanceChildToEnd();
		}
		filterParamRange_AST = currentAST.root;
		returnAST = filterParamRange_AST;
	}
	
	public void filterIdentifier() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterIdentifier_AST = null;
		
		AST tmp194_AST = null;
		tmp194_AST = astFactory.create(LT(1));
		astFactory.addASTChild(ref currentAST, tmp194_AST);
		match(IDENT);
		match(DOT);
		eventProperty();
		if (0 == inputState.guessing)
		{
			astFactory.addASTChild(ref currentAST, returnAST);
		}
		if (0==inputState.guessing)
		{
			filterIdentifier_AST = (AST)currentAST.root;
			filterIdentifier_AST = (AST) astFactory.make(astFactory.create(EVENT_FILTER_IDENT,"filterIdentifier"), filterIdentifier_AST);
			currentAST.root = filterIdentifier_AST;
			if ( (null != filterIdentifier_AST) && (null != filterIdentifier_AST.getFirstChild()) )
				currentAST.child = filterIdentifier_AST.getFirstChild();
			else
				currentAST.child = filterIdentifier_AST;
			currentAST.advanceChildToEnd();
		}
		filterIdentifier_AST = currentAST.root;
		returnAST = filterIdentifier_AST;
	}
	
	public void eventPropertyAtomic() //throws RecognitionException, TokenStreamException
{
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventPropertyAtomic_AST = null;
		
		if ((LA(1)==IDENT) && (tokenSet_15_.member(LA(2))))
		{
			AST tmp196_AST = null;
			tmp196_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp196_AST);
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
			AST tmp197_AST = null;
			tmp197_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp197_AST);
			match(IDENT);
			match(LBRACK);
			AST tmp199_AST = null;
			tmp199_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp199_AST);
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
			AST tmp201_AST = null;
			tmp201_AST = astFactory.create(LT(1));
			astFactory.addASTChild(ref currentAST, tmp201_AST);
			match(IDENT);
			match(LPAREN);
			{
				switch ( LA(1) )
				{
				case STRING_LITERAL:
				{
					AST tmp203_AST = null;
					tmp203_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp203_AST);
					match(STRING_LITERAL);
					break;
				}
				case QUOTED_STRING_LITERAL:
				{
					AST tmp204_AST = null;
					tmp204_AST = astFactory.create(LT(1));
					astFactory.addASTChild(ref currentAST, tmp204_AST);
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
		factory.setMaxNodeType(198);
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
		@"""NUMERIC_PARAM_RANGE""",
		@"""NUMERIC_PARAM_LIST""",
		@"""NUMERIC_PARAM_FREQUENCY""",
		@"""FOLLOWED_BY_EXPR""",
		@"""ARRAY_PARAM_LIST""",
		@"""EVENT_FILTER_EXPR""",
		@"""EVENT_FILTER_NAME_TAG""",
		@"""EVENT_FILTER_IDENT""",
		@"""EVENT_FILTER_PARAM""",
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
		@"""INT_TYPE""",
		@"""LONG_TYPE""",
		@"""FLOAT_TYPE""",
		@"""DOUBLE_TYPE""",
		@"""STRING_TYPE""",
		@"""BOOL_TYPE""",
		@"""NULL_TYPE""",
		@"""NUM_INT""",
		@"""NUM_LONG""",
		@"""NUM_FLOAT""",
		@"""NUM_DOUBLE""",
		@"""MINUS""",
		@"""PLUS""",
		@"""true""",
		@"""false""",
		@"""null""",
		@"""STRING_LITERAL""",
		@"""QUOTED_STRING_LITERAL""",
		@"""IDENT""",
		@"""LPAREN""",
		@"""COMMA""",
		@"""RPAREN""",
		@"""EQUALS""",
		@"""STAR""",
		@"""DOT""",
		@"""LBRACK""",
		@"""RBRACK""",
		@"""COLON""",
		@"""BAND""",
		@"""BOR""",
		@"""BXOR""",
		@"""SQL_NE""",
		@"""NOT_EQUAL""",
		@"""LT_""",
		@"""GT""",
		@"""LE""",
		@"""GE""",
		@"""LOR""",
		@"""DIV""",
		@"""MOD""",
		@"""FOLLOWED_BY""",
		@"""LCURLY""",
		@"""RCURLY""",
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
		@"""QUESTION""",
		@"""EQUAL""",
		@"""LNOT""",
		@"""BNOT""",
		@"""DIV_ASSIGN""",
		@"""PLUS_ASSIGN""",
		@"""INC""",
		@"""MINUS_ASSIGN""",
		@"""DEC""",
		@"""STAR_ASSIGN""",
		@"""MOD_ASSIGN""",
		@"""SR""",
		@"""SR_ASSIGN""",
		@"""BSR""",
		@"""BSR_ASSIGN""",
		@"""SL""",
		@"""SL_ASSIGN""",
		@"""BXOR_ASSIGN""",
		@"""BOR_ASSIGN""",
		@"""BAND_ASSIGN""",
		@"""LAND""",
		@"""SEMI""",
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
		long[] data = { 4524748046362114L, 0L, 536913792L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_0_ = new BitSet(mk_tokenSet_0_());
	private static long[] mk_tokenSet_1_()
	{
		long[] data = { 50300928L, -1152921504606846976L, 511L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_1_ = new BitSet(mk_tokenSet_1_());
	private static long[] mk_tokenSet_2_()
	{
		long[] data = { 16746496L, -1152921504606846976L, 511L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_2_ = new BitSet(mk_tokenSet_2_());
	private static long[] mk_tokenSet_3_()
	{
		long[] data = { 274877906944L, 0L, 3147776L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_3_ = new BitSet(mk_tokenSet_3_());
	private static long[] mk_tokenSet_4_()
	{
		long[] data = { 16744448L, -1152921504606846976L, 511L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_4_ = new BitSet(mk_tokenSet_4_());
	private static long[] mk_tokenSet_5_()
	{
		long[] data = { 31546367151198194L, -1152921504606846976L, 536747523L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_5_ = new BitSet(mk_tokenSet_5_());
	private static long[] mk_tokenSet_6_()
	{
		long[] data = { 31546367151198194L, 0L, 536772355L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_6_ = new BitSet(mk_tokenSet_6_());
	private static long[] mk_tokenSet_7_()
	{
		long[] data = { 464745702208036850L, -1152921504606846976L, 536748031L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_7_ = new BitSet(mk_tokenSet_7_());
	private static long[] mk_tokenSet_8_()
	{
		long[] data = { 13194189834240L, -1152921504606846976L, 1535L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_8_ = new BitSet(mk_tokenSet_8_());
	private static long[] mk_tokenSet_9_()
	{
		long[] data = { 9730L, 0L, 536915200L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_9_ = new BitSet(mk_tokenSet_9_());
	private static long[] mk_tokenSet_10_()
	{
		long[] data = { 0L, -1152921504606846976L, 1536L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_10_ = new BitSet(mk_tokenSet_10_());
	private static long[] mk_tokenSet_11_()
	{
		long[] data = { 211106232795136L, 0L, 4393751543808L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_11_ = new BitSet(mk_tokenSet_11_());
	private static long[] mk_tokenSet_12_()
	{
		long[] data = { 4735854279157250L, -1152921504606846976L, 4330937710335L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_12_ = new BitSet(mk_tokenSet_12_());
	private static long[] mk_tokenSet_13_()
	{
		long[] data = { 4595116790539778L, -1152921504606846976L, 4262218233599L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_13_ = new BitSet(mk_tokenSet_13_());
	private static long[] mk_tokenSet_14_()
	{
		long[] data = { 4524748046362114L, -1152921504606846976L, 3849901373183L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_14_ = new BitSet(mk_tokenSet_14_());
	private static long[] mk_tokenSet_15_()
	{
		long[] data = { 31546487410290674L, 0L, 536854019L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_15_ = new BitSet(mk_tokenSet_15_());
	
}
}
