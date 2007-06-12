// $ANTLR 2.7.7 (20060930): "eql_tree.g" -> "EQLBaseWalker.cs"$

using System.Collections;
using org.apache.commons.logging;

namespace net.esper.eql.generated
{
	// Generate header specific to the tree-parser CSharp file
	using System;
	
	using TreeParser = antlr.TreeParser;
	using Token                    = antlr.Token;
	using IToken                   = antlr.IToken;
	using AST                      = antlr.collections.AST;
	using RecognitionException     = antlr.RecognitionException;
	using ANTLRException           = antlr.ANTLRException;
	using NoViableAltException     = antlr.NoViableAltException;
	using MismatchedTokenException = antlr.MismatchedTokenException;
	using SemanticException        = antlr.SemanticException;
	using BitSet                   = antlr.collections.impl.BitSet;
	using ASTPair                  = antlr.ASTPair;
	using ASTFactory               = antlr.ASTFactory;
	using ASTArray                 = antlr.collections.impl.ASTArray;
	
	
	public 	class EQLBaseWalker : antlr.TreeParser
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
		public const int BOGUS = 218;
		public const int NUMERIC_PARAM_FREQUENCE = 219;
		
		
	private static Log log = LogFactory.GetLog(typeof(EQLBaseWalker));

	// For pattern processing within EQL and for create pattern
	protected virtual void setIsPatternWalk(bool isPatternWalk) {}
	protected virtual void endPattern() {}
	
	protected virtual void pushStmtContext() {}
	protected virtual void leaveNode(AST node) {}
	protected virtual void end() {}
		public EQLBaseWalker()
		{
			tokenNames = tokenNames_;
		}
		
	public void startEQLExpressionRule(AST _t) //throws RecognitionException
{
		
		AST startEQLExpressionRule_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST startEQLExpressionRule_AST = null;
		
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case INSERTINTO_EXPR:
			{
				insertIntoExpr(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case SELECTION_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		selectClause(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		fromClause(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case WHERE_EXPR:
			{
				whereClause(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			case HAVING_EXPR:
			case GROUP_BY_EXPR:
			case ORDER_BY_EXPR:
			case EVENT_LIMIT_EXPR:
			case SEC_LIMIT_EXPR:
			case MIN_LIMIT_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case GROUP_BY_EXPR:
			{
				groupByClause(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			case HAVING_EXPR:
			case ORDER_BY_EXPR:
			case EVENT_LIMIT_EXPR:
			case SEC_LIMIT_EXPR:
			case MIN_LIMIT_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case HAVING_EXPR:
			{
				havingClause(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			case ORDER_BY_EXPR:
			case EVENT_LIMIT_EXPR:
			case SEC_LIMIT_EXPR:
			case MIN_LIMIT_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case EVENT_LIMIT_EXPR:
			case SEC_LIMIT_EXPR:
			case MIN_LIMIT_EXPR:
			{
				outputLimitExpr(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			case ORDER_BY_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case ORDER_BY_EXPR:
			{
				orderByClause(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		end();
		startEQLExpressionRule_AST = currentAST.root;
		returnAST = startEQLExpressionRule_AST;
		retTree_ = _t;
	}
	
	public void insertIntoExpr(AST _t) //throws RecognitionException
{
		
		AST insertIntoExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST insertIntoExpr_AST = null;
		AST i = null;
		AST i_AST = null;
		
		AST __t9 = _t;
		i = (ASTNULL == _t) ? null : (AST)_t;
		AST i_AST_in = null;
		i_AST = astFactory.create(i);
		astFactory.addASTChild(ref currentAST, i_AST);
		ASTPair __currentAST9 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,INSERTINTO_EXPR);
		_t = _t.getFirstChild();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case ISTREAM:
			{
				AST tmp1_AST = null;
				AST tmp1_AST_in = null;
				tmp1_AST = astFactory.create(_t);
				tmp1_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp1_AST);
				match(_t,ISTREAM);
				_t = _t.getNextSibling();
				break;
			}
			case RSTREAM:
			{
				AST tmp2_AST = null;
				AST tmp2_AST_in = null;
				tmp2_AST = astFactory.create(_t);
				tmp2_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp2_AST);
				match(_t,RSTREAM);
				_t = _t.getNextSibling();
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		AST tmp3_AST = null;
		AST tmp3_AST_in = null;
		tmp3_AST = astFactory.create(_t);
		tmp3_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp3_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case INSERTINTO_EXPRCOL:
			{
				insertIntoExprCol(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		leaveNode(i_AST);
		currentAST = __currentAST9;
		_t = __t9;
		_t = _t.getNextSibling();
		insertIntoExpr_AST = currentAST.root;
		returnAST = insertIntoExpr_AST;
		retTree_ = _t;
	}
	
	public void selectClause(AST _t) //throws RecognitionException
{
		
		AST selectClause_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectClause_AST = null;
		AST s = null;
		AST s_AST = null;
		
		AST __t17 = _t;
		s = (ASTNULL == _t) ? null : (AST)_t;
		AST s_AST_in = null;
		s_AST = astFactory.create(s);
		astFactory.addASTChild(ref currentAST, s_AST);
		ASTPair __currentAST17 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,SELECTION_EXPR);
		_t = _t.getFirstChild();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case RSTREAM:
			{
				AST tmp4_AST = null;
				AST tmp4_AST_in = null;
				tmp4_AST = astFactory.create(_t);
				tmp4_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp4_AST);
				match(_t,RSTREAM);
				_t = _t.getNextSibling();
				break;
			}
			case ISTREAM:
			{
				AST tmp5_AST = null;
				AST tmp5_AST_in = null;
				tmp5_AST = astFactory.create(_t);
				tmp5_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp5_AST);
				match(_t,ISTREAM);
				_t = _t.getNextSibling();
				break;
			}
			case SELECTION_ELEMENT_EXPR:
			case WILDCARD_SELECT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		selectionList(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		leaveNode(s_AST);
		currentAST = __currentAST17;
		_t = __t17;
		_t = _t.getNextSibling();
		selectClause_AST = currentAST.root;
		returnAST = selectClause_AST;
		retTree_ = _t;
	}
	
	public void fromClause(AST _t) //throws RecognitionException
{
		
		AST fromClause_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST fromClause_AST = null;
		
		streamExpression(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((_t.Type==STREAM_EXPR))
				{
					streamExpression(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					{    // ( ... )*
						for (;;)
						{
							if (_t == null)
								_t = ASTNULL;
							if (((_t.Type >= LEFT_OUTERJOIN_EXPR && _t.Type <= FULL_OUTERJOIN_EXPR)))
							{
								outerJoin(_t);
								_t = retTree_;
								astFactory.addASTChild(ref currentAST, returnAST);
							}
							else
							{
								goto _loop22_breakloop;
							}
							
						}
_loop22_breakloop:						;
					}    // ( ... )*
				}
				else
				{
					goto _loop23_breakloop;
				}
				
			}
_loop23_breakloop:			;
		}    // ( ... )*
		fromClause_AST = currentAST.root;
		returnAST = fromClause_AST;
		retTree_ = _t;
	}
	
	public void whereClause(AST _t) //throws RecognitionException
{
		
		AST whereClause_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST whereClause_AST = null;
		AST n = null;
		AST n_AST = null;
		
		AST __t53 = _t;
		n = (ASTNULL == _t) ? null : (AST)_t;
		AST n_AST_in = null;
		n_AST = astFactory.create(n);
		astFactory.addASTChild(ref currentAST, n_AST);
		ASTPair __currentAST53 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,WHERE_EXPR);
		_t = _t.getFirstChild();
		valueExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		leaveNode(n_AST);
		currentAST = __currentAST53;
		_t = __t53;
		_t = _t.getNextSibling();
		whereClause_AST = currentAST.root;
		returnAST = whereClause_AST;
		retTree_ = _t;
	}
	
	public void groupByClause(AST _t) //throws RecognitionException
{
		
		AST groupByClause_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST groupByClause_AST = null;
		AST g = null;
		AST g_AST = null;
		
		AST __t55 = _t;
		g = (ASTNULL == _t) ? null : (AST)_t;
		AST g_AST_in = null;
		g_AST = astFactory.create(g);
		astFactory.addASTChild(ref currentAST, g_AST);
		ASTPair __currentAST55 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,GROUP_BY_EXPR);
		_t = _t.getFirstChild();
		valueExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((tokenSet_0_.member(_t.Type)))
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop57_breakloop;
				}
				
			}
_loop57_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST55;
		_t = __t55;
		_t = _t.getNextSibling();
		leaveNode(g_AST);
		groupByClause_AST = currentAST.root;
		returnAST = groupByClause_AST;
		retTree_ = _t;
	}
	
	public void havingClause(AST _t) //throws RecognitionException
{
		
		AST havingClause_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST havingClause_AST = null;
		AST n = null;
		AST n_AST = null;
		
		AST __t66 = _t;
		n = (ASTNULL == _t) ? null : (AST)_t;
		AST n_AST_in = null;
		n_AST = astFactory.create(n);
		astFactory.addASTChild(ref currentAST, n_AST);
		ASTPair __currentAST66 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,HAVING_EXPR);
		_t = _t.getFirstChild();
		valueExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		leaveNode(n_AST);
		currentAST = __currentAST66;
		_t = __t66;
		_t = _t.getNextSibling();
		havingClause_AST = currentAST.root;
		returnAST = havingClause_AST;
		retTree_ = _t;
	}
	
	public void outputLimitExpr(AST _t) //throws RecognitionException
{
		
		AST outputLimitExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outputLimitExpr_AST = null;
		AST e = null;
		AST e_AST = null;
		AST sec = null;
		AST sec_AST = null;
		AST min = null;
		AST min_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case EVENT_LIMIT_EXPR:
		{
			AST __t68 = _t;
			e = (ASTNULL == _t) ? null : (AST)_t;
			AST e_AST_in = null;
			e_AST = astFactory.create(e);
			astFactory.addASTChild(ref currentAST, e_AST);
			ASTPair __currentAST68 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_LIMIT_EXPR);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case ALL:
				{
					AST tmp6_AST = null;
					AST tmp6_AST_in = null;
					tmp6_AST = astFactory.create(_t);
					tmp6_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp6_AST);
					match(_t,ALL);
					_t = _t.getNextSibling();
					break;
				}
				case FIRST:
				{
					AST tmp7_AST = null;
					AST tmp7_AST_in = null;
					tmp7_AST = astFactory.create(_t);
					tmp7_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp7_AST);
					match(_t,FIRST);
					_t = _t.getNextSibling();
					break;
				}
				case LAST:
				{
					AST tmp8_AST = null;
					AST tmp8_AST_in = null;
					tmp8_AST = astFactory.create(_t);
					tmp8_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp8_AST);
					match(_t,LAST);
					_t = _t.getNextSibling();
					break;
				}
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			number(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(e_AST);
			currentAST = __currentAST68;
			_t = __t68;
			_t = _t.getNextSibling();
			outputLimitExpr_AST = currentAST.root;
			break;
		}
		case SEC_LIMIT_EXPR:
		{
			AST __t70 = _t;
			sec = (ASTNULL == _t) ? null : (AST)_t;
			AST sec_AST_in = null;
			sec_AST = astFactory.create(sec);
			astFactory.addASTChild(ref currentAST, sec_AST);
			ASTPair __currentAST70 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,SEC_LIMIT_EXPR);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case ALL:
				{
					AST tmp9_AST = null;
					AST tmp9_AST_in = null;
					tmp9_AST = astFactory.create(_t);
					tmp9_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp9_AST);
					match(_t,ALL);
					_t = _t.getNextSibling();
					break;
				}
				case FIRST:
				{
					AST tmp10_AST = null;
					AST tmp10_AST_in = null;
					tmp10_AST = astFactory.create(_t);
					tmp10_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp10_AST);
					match(_t,FIRST);
					_t = _t.getNextSibling();
					break;
				}
				case LAST:
				{
					AST tmp11_AST = null;
					AST tmp11_AST_in = null;
					tmp11_AST = astFactory.create(_t);
					tmp11_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp11_AST);
					match(_t,LAST);
					_t = _t.getNextSibling();
					break;
				}
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			number(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(sec_AST);
			currentAST = __currentAST70;
			_t = __t70;
			_t = _t.getNextSibling();
			outputLimitExpr_AST = currentAST.root;
			break;
		}
		case MIN_LIMIT_EXPR:
		{
			AST __t72 = _t;
			min = (ASTNULL == _t) ? null : (AST)_t;
			AST min_AST_in = null;
			min_AST = astFactory.create(min);
			astFactory.addASTChild(ref currentAST, min_AST);
			ASTPair __currentAST72 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,MIN_LIMIT_EXPR);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case ALL:
				{
					AST tmp12_AST = null;
					AST tmp12_AST_in = null;
					tmp12_AST = astFactory.create(_t);
					tmp12_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp12_AST);
					match(_t,ALL);
					_t = _t.getNextSibling();
					break;
				}
				case FIRST:
				{
					AST tmp13_AST = null;
					AST tmp13_AST_in = null;
					tmp13_AST = astFactory.create(_t);
					tmp13_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp13_AST);
					match(_t,FIRST);
					_t = _t.getNextSibling();
					break;
				}
				case LAST:
				{
					AST tmp14_AST = null;
					AST tmp14_AST_in = null;
					tmp14_AST = astFactory.create(_t);
					tmp14_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp14_AST);
					match(_t,LAST);
					_t = _t.getNextSibling();
					break;
				}
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			number(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(min_AST);
			currentAST = __currentAST72;
			_t = __t72;
			_t = _t.getNextSibling();
			outputLimitExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = outputLimitExpr_AST;
		retTree_ = _t;
	}
	
	public void orderByClause(AST _t) //throws RecognitionException
{
		
		AST orderByClause_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orderByClause_AST = null;
		AST s = null;
		AST s_AST = null;
		
		AST __t59 = _t;
		s = (ASTNULL == _t) ? null : (AST)_t;
		AST s_AST_in = null;
		s_AST = astFactory.create(s);
		astFactory.addASTChild(ref currentAST, s_AST);
		ASTPair __currentAST59 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,ORDER_BY_EXPR);
		_t = _t.getFirstChild();
		orderByElement(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((_t.Type==ORDER_ELEMENT_EXPR))
				{
					orderByElement(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop61_breakloop;
				}
				
			}
_loop61_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST59;
		_t = __t59;
		_t = _t.getNextSibling();
		orderByClause_AST = currentAST.root;
		returnAST = orderByClause_AST;
		retTree_ = _t;
	}
	
	public void insertIntoExprCol(AST _t) //throws RecognitionException
{
		
		AST insertIntoExprCol_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST insertIntoExprCol_AST = null;
		AST i = null;
		AST i_AST = null;
		
		AST __t13 = _t;
		i = (ASTNULL == _t) ? null : (AST)_t;
		AST i_AST_in = null;
		i_AST = astFactory.create(i);
		astFactory.addASTChild(ref currentAST, i_AST);
		ASTPair __currentAST13 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,INSERTINTO_EXPRCOL);
		_t = _t.getFirstChild();
		AST tmp15_AST = null;
		AST tmp15_AST_in = null;
		tmp15_AST = astFactory.create(_t);
		tmp15_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp15_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((_t.Type==IDENT))
				{
					AST tmp16_AST = null;
					AST tmp16_AST_in = null;
					tmp16_AST = astFactory.create(_t);
					tmp16_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp16_AST);
					match(_t,IDENT);
					_t = _t.getNextSibling();
				}
				else
				{
					goto _loop15_breakloop;
				}
				
			}
_loop15_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST13;
		_t = __t13;
		_t = _t.getNextSibling();
		insertIntoExprCol_AST = currentAST.root;
		returnAST = insertIntoExprCol_AST;
		retTree_ = _t;
	}
	
	public void selectionList(AST _t) //throws RecognitionException
{
		
		AST selectionList_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectionList_AST = null;
		
		selectionListElement(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((_t.Type==SELECTION_ELEMENT_EXPR||_t.Type==WILDCARD_SELECT))
				{
					selectionListElement(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop26_breakloop;
				}
				
			}
_loop26_breakloop:			;
		}    // ( ... )*
		selectionList_AST = currentAST.root;
		returnAST = selectionList_AST;
		retTree_ = _t;
	}
	
	public void streamExpression(AST _t) //throws RecognitionException
{
		
		AST streamExpression_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST streamExpression_AST = null;
		AST v = null;
		AST v_AST = null;
		
		AST __t36 = _t;
		v = (ASTNULL == _t) ? null : (AST)_t;
		AST v_AST_in = null;
		v_AST = astFactory.create(v);
		astFactory.addASTChild(ref currentAST, v_AST);
		ASTPair __currentAST36 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,STREAM_EXPR);
		_t = _t.getFirstChild();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case EVENT_FILTER_EXPR:
			{
				eventFilterExpr(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case PATTERN_INCL_EXPR:
			{
				patternInclusionExpression(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case DATABASE_JOIN_EXPR:
			{
				databaseJoinExpression(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case VIEW_EXPR:
			{
				viewListExpr(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case IDENT:
			{
				AST tmp17_AST = null;
				AST tmp17_AST_in = null;
				tmp17_AST = astFactory.create(_t);
				tmp17_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp17_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		leaveNode(v_AST);
		currentAST = __currentAST36;
		_t = __t36;
		_t = _t.getNextSibling();
		streamExpression_AST = currentAST.root;
		returnAST = streamExpression_AST;
		retTree_ = _t;
	}
	
	public void outerJoin(AST _t) //throws RecognitionException
{
		
		AST outerJoin_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outerJoin_AST = null;
		
		outerJoinIdent(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		outerJoin_AST = currentAST.root;
		returnAST = outerJoin_AST;
		retTree_ = _t;
	}
	
	public void selectionListElement(AST _t) //throws RecognitionException
{
		
		AST selectionListElement_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST selectionListElement_AST = null;
		AST w = null;
		AST w_AST = null;
		AST s = null;
		AST s_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case WILDCARD_SELECT:
		{
			w = _t;
			AST w_AST_in = null;
			w_AST = astFactory.create(w);
			astFactory.addASTChild(ref currentAST, w_AST);
			match(_t,WILDCARD_SELECT);
			_t = _t.getNextSibling();
			leaveNode(w_AST);
			selectionListElement_AST = currentAST.root;
			break;
		}
		case SELECTION_ELEMENT_EXPR:
		{
			AST __t28 = _t;
			s = (ASTNULL == _t) ? null : (AST)_t;
			AST s_AST_in = null;
			s_AST = astFactory.create(s);
			astFactory.addASTChild(ref currentAST, s_AST);
			ASTPair __currentAST28 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,SELECTION_ELEMENT_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case IDENT:
				{
					AST tmp18_AST = null;
					AST tmp18_AST_in = null;
					tmp18_AST = astFactory.create(_t);
					tmp18_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp18_AST);
					match(_t,IDENT);
					_t = _t.getNextSibling();
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			leaveNode(s_AST);
			currentAST = __currentAST28;
			_t = __t28;
			_t = _t.getNextSibling();
			selectionListElement_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = selectionListElement_AST;
		retTree_ = _t;
	}
	
	public void valueExpr(AST _t) //throws RecognitionException
{
		
		AST valueExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST valueExpr_AST = null;
		AST c_AST = null;
		AST c = null;
		AST a_AST = null;
		AST a = null;
		AST f_AST = null;
		AST f = null;
		AST l_AST = null;
		AST l = null;
		AST cs_AST = null;
		AST cs = null;
		AST in__AST = null;
		AST in_ = null;
		AST b_AST = null;
		AST b = null;
		AST li_AST = null;
		AST li = null;
		AST r_AST = null;
		AST r = null;
		AST arr_AST = null;
		AST arr = null;
		AST subin_AST = null;
		AST subin = null;
		AST subrow_AST = null;
		AST subrow = null;
		AST subexists_AST = null;
		AST subexists = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case INT_TYPE:
		case LONG_TYPE:
		case FLOAT_TYPE:
		case DOUBLE_TYPE:
		case STRING_TYPE:
		case BOOL_TYPE:
		case NULL_TYPE:
		{
			c = _t==ASTNULL ? null : _t;
			constant(_t);
			_t = retTree_;
			c_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(c_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case CONCAT:
		case MINUS:
		case PLUS:
		case STAR:
		case BAND:
		case BOR:
		case BXOR:
		case DIV:
		case MOD:
		{
			a = _t==ASTNULL ? null : _t;
			arithmeticExpr(_t);
			_t = retTree_;
			a_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(a_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case EVENT_PROP_EXPR:
		{
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case NOT_EXPR:
		case EVAL_AND_EXPR:
		case EVAL_OR_EXPR:
		case EVAL_EQUALS_EXPR:
		case EVAL_NOTEQUALS_EXPR:
		case LT_:
		case GT:
		case LE:
		case GE:
		{
			evalExprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr_AST = currentAST.root;
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
			f = _t==ASTNULL ? null : _t;
			builtinFunc(_t);
			_t = retTree_;
			f_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(f_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case LIB_FUNCTION:
		{
			l = _t==ASTNULL ? null : _t;
			libFunc(_t);
			_t = retTree_;
			l_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(l_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case CASE:
		case CASE2:
		{
			cs = _t==ASTNULL ? null : _t;
			caseExpr(_t);
			_t = retTree_;
			cs_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(cs_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case IN_SET:
		case NOT_IN_SET:
		case IN_RANGE:
		case NOT_IN_RANGE:
		{
			in_ = _t==ASTNULL ? null : _t;
			inExpr(_t);
			_t = retTree_;
			in__AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(in__AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case BETWEEN:
		case NOT_BETWEEN:
		{
			b = _t==ASTNULL ? null : _t;
			betweenExpr(_t);
			_t = retTree_;
			b_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(b_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case LIKE:
		case NOT_LIKE:
		{
			li = _t==ASTNULL ? null : _t;
			likeExpr(_t);
			_t = retTree_;
			li_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(li_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case REGEXP:
		case NOT_REGEXP:
		{
			r = _t==ASTNULL ? null : _t;
			regExpExpr(_t);
			_t = retTree_;
			r_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(r_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case ARRAY_EXPR:
		{
			arr = _t==ASTNULL ? null : _t;
			arrayExpr(_t);
			_t = retTree_;
			arr_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(arr_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case IN_SUBSELECT_EXPR:
		case NOT_IN_SUBSELECT_EXPR:
		{
			subin = _t==ASTNULL ? null : _t;
			subSelectInExpr(_t);
			_t = retTree_;
			subin_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(subin_AST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case SUBSELECT_EXPR:
		{
			subrow = _t==ASTNULL ? null : _t;
			subSelectRowExpr(_t);
			_t = retTree_;
			subrow_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr_AST = currentAST.root;
			break;
		}
		case EXISTS_SUBSELECT_EXPR:
		{
			subexists = _t==ASTNULL ? null : _t;
			subSelectExistsExpr(_t);
			_t = retTree_;
			subexists_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = valueExpr_AST;
		retTree_ = _t;
	}
	
	public void outerJoinIdent(AST _t) //throws RecognitionException
{
		
		AST outerJoinIdent_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST outerJoinIdent_AST = null;
		AST tl = null;
		AST tl_AST = null;
		AST tr = null;
		AST tr_AST = null;
		AST tf = null;
		AST tf_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case LEFT_OUTERJOIN_EXPR:
		{
			AST __t32 = _t;
			tl = (ASTNULL == _t) ? null : (AST)_t;
			AST tl_AST_in = null;
			tl_AST = astFactory.create(tl);
			astFactory.addASTChild(ref currentAST, tl_AST);
			ASTPair __currentAST32 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LEFT_OUTERJOIN_EXPR);
			_t = _t.getFirstChild();
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(tl_AST);
			currentAST = __currentAST32;
			_t = __t32;
			_t = _t.getNextSibling();
			outerJoinIdent_AST = currentAST.root;
			break;
		}
		case RIGHT_OUTERJOIN_EXPR:
		{
			AST __t33 = _t;
			tr = (ASTNULL == _t) ? null : (AST)_t;
			AST tr_AST_in = null;
			tr_AST = astFactory.create(tr);
			astFactory.addASTChild(ref currentAST, tr_AST);
			ASTPair __currentAST33 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,RIGHT_OUTERJOIN_EXPR);
			_t = _t.getFirstChild();
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(tr_AST);
			currentAST = __currentAST33;
			_t = __t33;
			_t = _t.getNextSibling();
			outerJoinIdent_AST = currentAST.root;
			break;
		}
		case FULL_OUTERJOIN_EXPR:
		{
			AST __t34 = _t;
			tf = (ASTNULL == _t) ? null : (AST)_t;
			AST tf_AST_in = null;
			tf_AST = astFactory.create(tf);
			astFactory.addASTChild(ref currentAST, tf_AST);
			ASTPair __currentAST34 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,FULL_OUTERJOIN_EXPR);
			_t = _t.getFirstChild();
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(tf_AST);
			currentAST = __currentAST34;
			_t = __t34;
			_t = _t.getNextSibling();
			outerJoinIdent_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = outerJoinIdent_AST;
		retTree_ = _t;
	}
	
	public void eventPropertyExpr(AST _t) //throws RecognitionException
{
		
		AST eventPropertyExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventPropertyExpr_AST = null;
		AST p = null;
		AST p_AST = null;
		
		AST __t255 = _t;
		p = (ASTNULL == _t) ? null : (AST)_t;
		AST p_AST_in = null;
		p_AST = astFactory.create(p);
		astFactory.addASTChild(ref currentAST, p_AST);
		ASTPair __currentAST255 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,EVENT_PROP_EXPR);
		_t = _t.getFirstChild();
		eventPropertyAtomic(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if (((_t.Type >= EVENT_PROP_SIMPLE && _t.Type <= EVENT_PROP_INDEXED)))
				{
					eventPropertyAtomic(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop257_breakloop;
				}
				
			}
_loop257_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST255;
		_t = __t255;
		_t = _t.getNextSibling();
		leaveNode(p_AST);
		eventPropertyExpr_AST = currentAST.root;
		returnAST = eventPropertyExpr_AST;
		retTree_ = _t;
	}
	
	public void eventFilterExpr(AST _t) //throws RecognitionException
{
		
		AST eventFilterExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventFilterExpr_AST = null;
		AST f = null;
		AST f_AST = null;
		
		AST __t208 = _t;
		f = (ASTNULL == _t) ? null : (AST)_t;
		AST f_AST_in = null;
		f_AST = astFactory.create(f);
		astFactory.addASTChild(ref currentAST, f_AST);
		ASTPair __currentAST208 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,EVENT_FILTER_EXPR);
		_t = _t.getFirstChild();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case EVENT_FILTER_NAME_TAG:
			{
				AST tmp19_AST = null;
				AST tmp19_AST_in = null;
				tmp19_AST = astFactory.create(_t);
				tmp19_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp19_AST);
				match(_t,EVENT_FILTER_NAME_TAG);
				_t = _t.getNextSibling();
				break;
			}
			case CLASS_IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		AST tmp20_AST = null;
		AST tmp20_AST_in = null;
		tmp20_AST = astFactory.create(_t);
		tmp20_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp20_AST);
		match(_t,CLASS_IDENT);
		_t = _t.getNextSibling();
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((tokenSet_0_.member(_t.Type)))
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop211_breakloop;
				}
				
			}
_loop211_breakloop:			;
		}    // ( ... )*
		leaveNode(f_AST);
		currentAST = __currentAST208;
		_t = __t208;
		_t = _t.getNextSibling();
		eventFilterExpr_AST = currentAST.root;
		returnAST = eventFilterExpr_AST;
		retTree_ = _t;
	}
	
	public void patternInclusionExpression(AST _t) //throws RecognitionException
{
		
		AST patternInclusionExpression_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST patternInclusionExpression_AST = null;
		AST p = null;
		AST p_AST = null;
		
		AST __t41 = _t;
		p = (ASTNULL == _t) ? null : (AST)_t;
		AST p_AST_in = null;
		p_AST = astFactory.create(p);
		astFactory.addASTChild(ref currentAST, p_AST);
		ASTPair __currentAST41 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,PATTERN_INCL_EXPR);
		_t = _t.getFirstChild();
		setIsPatternWalk(true);
		exprChoice(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		setIsPatternWalk(false); leaveNode(p_AST);
		currentAST = __currentAST41;
		_t = __t41;
		_t = _t.getNextSibling();
		patternInclusionExpression_AST = currentAST.root;
		returnAST = patternInclusionExpression_AST;
		retTree_ = _t;
	}
	
	public void databaseJoinExpression(AST _t) //throws RecognitionException
{
		
		AST databaseJoinExpression_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST databaseJoinExpression_AST = null;
		AST d = null;
		AST d_AST = null;
		
		AST __t43 = _t;
		d = (ASTNULL == _t) ? null : (AST)_t;
		AST d_AST_in = null;
		d_AST = astFactory.create(d);
		astFactory.addASTChild(ref currentAST, d_AST);
		ASTPair __currentAST43 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,DATABASE_JOIN_EXPR);
		_t = _t.getFirstChild();
		AST tmp21_AST = null;
		AST tmp21_AST_in = null;
		tmp21_AST = astFactory.create(_t);
		tmp21_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp21_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case STRING_LITERAL:
			{
				AST tmp22_AST = null;
				AST tmp22_AST_in = null;
				tmp22_AST = astFactory.create(_t);
				tmp22_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp22_AST);
				match(_t,STRING_LITERAL);
				_t = _t.getNextSibling();
				break;
			}
			case QUOTED_STRING_LITERAL:
			{
				AST tmp23_AST = null;
				AST tmp23_AST_in = null;
				tmp23_AST = astFactory.create(_t);
				tmp23_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp23_AST);
				match(_t,QUOTED_STRING_LITERAL);
				_t = _t.getNextSibling();
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		currentAST = __currentAST43;
		_t = __t43;
		_t = _t.getNextSibling();
		databaseJoinExpression_AST = currentAST.root;
		returnAST = databaseJoinExpression_AST;
		retTree_ = _t;
	}
	
	public void viewListExpr(AST _t) //throws RecognitionException
{
		
		AST viewListExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST viewListExpr_AST = null;
		
		viewExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((_t.Type==VIEW_EXPR))
				{
					viewExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop47_breakloop;
				}
				
			}
_loop47_breakloop:			;
		}    // ( ... )*
		viewListExpr_AST = currentAST.root;
		returnAST = viewListExpr_AST;
		retTree_ = _t;
	}
	
	public void exprChoice(AST _t) //throws RecognitionException
{
		
		AST exprChoice_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST exprChoice_AST = null;
		AST a = null;
		AST a_AST = null;
		AST n = null;
		AST n_AST = null;
		AST g = null;
		AST g_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case EVENT_FILTER_EXPR:
		case OBSERVER_EXPR:
		{
			atomicExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			exprChoice_AST = currentAST.root;
			break;
		}
		case OR_EXPR:
		case AND_EXPR:
		case FOLLOWED_BY_EXPR:
		{
			patternOp(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			exprChoice_AST = currentAST.root;
			break;
		}
		case EVERY_EXPR:
		{
			AST __t188 = _t;
			a = (ASTNULL == _t) ? null : (AST)_t;
			AST a_AST_in = null;
			a_AST = astFactory.create(a);
			astFactory.addASTChild(ref currentAST, a_AST);
			ASTPair __currentAST188 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVERY_EXPR);
			_t = _t.getFirstChild();
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(a_AST);
			currentAST = __currentAST188;
			_t = __t188;
			_t = _t.getNextSibling();
			exprChoice_AST = currentAST.root;
			break;
		}
		case NOT_EXPR:
		{
			AST __t189 = _t;
			n = (ASTNULL == _t) ? null : (AST)_t;
			AST n_AST_in = null;
			n_AST = astFactory.create(n);
			astFactory.addASTChild(ref currentAST, n_AST);
			ASTPair __currentAST189 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_EXPR);
			_t = _t.getFirstChild();
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(n_AST);
			currentAST = __currentAST189;
			_t = __t189;
			_t = _t.getNextSibling();
			exprChoice_AST = currentAST.root;
			break;
		}
		case GUARD_EXPR:
		{
			AST __t190 = _t;
			g = (ASTNULL == _t) ? null : (AST)_t;
			AST g_AST_in = null;
			g_AST = astFactory.create(g);
			astFactory.addASTChild(ref currentAST, g_AST);
			ASTPair __currentAST190 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,GUARD_EXPR);
			_t = _t.getFirstChild();
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			AST tmp24_AST = null;
			AST tmp24_AST_in = null;
			tmp24_AST = astFactory.create(_t);
			tmp24_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp24_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			AST tmp25_AST = null;
			AST tmp25_AST_in = null;
			tmp25_AST = astFactory.create(_t);
			tmp25_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp25_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			{    // ( ... )*
				for (;;)
				{
					if (null == _t)
						_t = ASTNULL;
					switch ( _t.Type )
					{
					case INT_TYPE:
					case LONG_TYPE:
					case FLOAT_TYPE:
					case DOUBLE_TYPE:
					case STRING_TYPE:
					case BOOL_TYPE:
					case NULL_TYPE:
					{
						constant(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
						break;
					}
					case TIME_PERIOD:
					{
						time_period(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
						break;
					}
					default:
					{
						goto _loop192_breakloop;
					}
					 }
				}
_loop192_breakloop:				;
			}    // ( ... )*
			leaveNode(g_AST);
			currentAST = __currentAST190;
			_t = __t190;
			_t = _t.getNextSibling();
			exprChoice_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = exprChoice_AST;
		retTree_ = _t;
	}
	
	public void viewExpr(AST _t) //throws RecognitionException
{
		
		AST viewExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST viewExpr_AST = null;
		AST n = null;
		AST n_AST = null;
		
		AST __t49 = _t;
		n = (ASTNULL == _t) ? null : (AST)_t;
		AST n_AST_in = null;
		n_AST = astFactory.create(n);
		astFactory.addASTChild(ref currentAST, n_AST);
		ASTPair __currentAST49 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,VIEW_EXPR);
		_t = _t.getFirstChild();
		AST tmp26_AST = null;
		AST tmp26_AST_in = null;
		tmp26_AST = astFactory.create(_t);
		tmp26_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp26_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		AST tmp27_AST = null;
		AST tmp27_AST_in = null;
		tmp27_AST = astFactory.create(_t);
		tmp27_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp27_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((tokenSet_1_.member(_t.Type)))
				{
					parameter(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop51_breakloop;
				}
				
			}
_loop51_breakloop:			;
		}    // ( ... )*
		leaveNode(n_AST);
		currentAST = __currentAST49;
		_t = __t49;
		_t = _t.getNextSibling();
		viewExpr_AST = currentAST.root;
		returnAST = viewExpr_AST;
		retTree_ = _t;
	}
	
	public void parameter(AST _t) //throws RecognitionException
{
		
		AST parameter_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST parameter_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case NUMERIC_PARAM_RANGE:
		case NUMERIC_PARAM_FREQUENCY:
		case TIME_PERIOD:
		case INT_TYPE:
		case LONG_TYPE:
		case FLOAT_TYPE:
		case DOUBLE_TYPE:
		case STRING_TYPE:
		case BOOL_TYPE:
		case NULL_TYPE:
		case STAR:
		{
			singleParameter(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			parameter_AST = currentAST.root;
			break;
		}
		case NUMERIC_PARAM_LIST:
		{
			AST __t264 = _t;
			AST tmp28_AST = null;
			AST tmp28_AST_in = null;
			tmp28_AST = astFactory.create(_t);
			tmp28_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp28_AST);
			ASTPair __currentAST264 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NUMERIC_PARAM_LIST);
			_t = _t.getFirstChild();
			{ // ( ... )+
				int _cnt266=0;
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((_t.Type==NUMERIC_PARAM_RANGE||_t.Type==NUM_INT||_t.Type==NUMERIC_PARAM_FREQUENCE))
					{
						numericParameterList(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						if (_cnt266 >= 1) { goto _loop266_breakloop; } else { throw new NoViableAltException(_t);; }
					}
					
					_cnt266++;
				}
_loop266_breakloop:				;
			}    // ( ... )+
			currentAST = __currentAST264;
			_t = __t264;
			_t = _t.getNextSibling();
			parameter_AST = currentAST.root;
			break;
		}
		case ARRAY_PARAM_LIST:
		{
			AST __t267 = _t;
			AST tmp29_AST = null;
			AST tmp29_AST_in = null;
			tmp29_AST = astFactory.create(_t);
			tmp29_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp29_AST);
			ASTPair __currentAST267 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,ARRAY_PARAM_LIST);
			_t = _t.getFirstChild();
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if (((_t.Type >= INT_TYPE && _t.Type <= NULL_TYPE)))
					{
						constant(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop269_breakloop;
					}
					
				}
_loop269_breakloop:				;
			}    // ( ... )*
			currentAST = __currentAST267;
			_t = __t267;
			_t = _t.getNextSibling();
			parameter_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = parameter_AST;
		retTree_ = _t;
	}
	
	public void orderByElement(AST _t) //throws RecognitionException
{
		
		AST orderByElement_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST orderByElement_AST = null;
		AST e = null;
		AST e_AST = null;
		
		AST __t63 = _t;
		e = (ASTNULL == _t) ? null : (AST)_t;
		AST e_AST_in = null;
		e_AST = astFactory.create(e);
		astFactory.addASTChild(ref currentAST, e_AST);
		ASTPair __currentAST63 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,ORDER_ELEMENT_EXPR);
		_t = _t.getFirstChild();
		valueExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case ASC:
			{
				AST tmp30_AST = null;
				AST tmp30_AST_in = null;
				tmp30_AST = astFactory.create(_t);
				tmp30_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp30_AST);
				match(_t,ASC);
				_t = _t.getNextSibling();
				break;
			}
			case DESC:
			{
				AST tmp31_AST = null;
				AST tmp31_AST_in = null;
				tmp31_AST = astFactory.create(_t);
				tmp31_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp31_AST);
				match(_t,DESC);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		leaveNode(e_AST);
		currentAST = __currentAST63;
		_t = __t63;
		_t = _t.getNextSibling();
		orderByElement_AST = currentAST.root;
		returnAST = orderByElement_AST;
		retTree_ = _t;
	}
	
	public void number(AST _t) //throws RecognitionException
{
		
		AST number_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST number_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case INT_TYPE:
		{
			AST tmp32_AST = null;
			AST tmp32_AST_in = null;
			tmp32_AST = astFactory.create(_t);
			tmp32_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp32_AST);
			match(_t,INT_TYPE);
			_t = _t.getNextSibling();
			number_AST = currentAST.root;
			break;
		}
		case LONG_TYPE:
		{
			AST tmp33_AST = null;
			AST tmp33_AST_in = null;
			tmp33_AST = astFactory.create(_t);
			tmp33_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp33_AST);
			match(_t,LONG_TYPE);
			_t = _t.getNextSibling();
			number_AST = currentAST.root;
			break;
		}
		case FLOAT_TYPE:
		{
			AST tmp34_AST = null;
			AST tmp34_AST_in = null;
			tmp34_AST = astFactory.create(_t);
			tmp34_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp34_AST);
			match(_t,FLOAT_TYPE);
			_t = _t.getNextSibling();
			number_AST = currentAST.root;
			break;
		}
		case DOUBLE_TYPE:
		{
			AST tmp35_AST = null;
			AST tmp35_AST_in = null;
			tmp35_AST = astFactory.create(_t);
			tmp35_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp35_AST);
			match(_t,DOUBLE_TYPE);
			_t = _t.getNextSibling();
			number_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = number_AST;
		retTree_ = _t;
	}
	
	public void relationalExpr(AST _t) //throws RecognitionException
{
		
		AST relationalExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST relationalExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case LT_:
		{
			AST __t75 = _t;
			AST tmp36_AST = null;
			AST tmp36_AST_in = null;
			tmp36_AST = astFactory.create(_t);
			tmp36_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp36_AST);
			ASTPair __currentAST75 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LT_);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST75;
			_t = __t75;
			_t = _t.getNextSibling();
			relationalExpr_AST = currentAST.root;
			break;
		}
		case GT:
		{
			AST __t76 = _t;
			AST tmp37_AST = null;
			AST tmp37_AST_in = null;
			tmp37_AST = astFactory.create(_t);
			tmp37_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp37_AST);
			ASTPair __currentAST76 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,GT);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST76;
			_t = __t76;
			_t = _t.getNextSibling();
			relationalExpr_AST = currentAST.root;
			break;
		}
		case LE:
		{
			AST __t77 = _t;
			AST tmp38_AST = null;
			AST tmp38_AST_in = null;
			tmp38_AST = astFactory.create(_t);
			tmp38_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp38_AST);
			ASTPair __currentAST77 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST77;
			_t = __t77;
			_t = _t.getNextSibling();
			relationalExpr_AST = currentAST.root;
			break;
		}
		case GE:
		{
			AST __t78 = _t;
			AST tmp39_AST = null;
			AST tmp39_AST_in = null;
			tmp39_AST = astFactory.create(_t);
			tmp39_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp39_AST);
			ASTPair __currentAST78 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,GE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST78;
			_t = __t78;
			_t = _t.getNextSibling();
			relationalExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = relationalExpr_AST;
		retTree_ = _t;
	}
	
	public void evalExprChoice(AST _t) //throws RecognitionException
{
		
		AST evalExprChoice_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST evalExprChoice_AST = null;
		AST jo = null;
		AST jo_AST = null;
		AST ja = null;
		AST ja_AST = null;
		AST je = null;
		AST je_AST = null;
		AST jne = null;
		AST jne_AST = null;
		AST n = null;
		AST n_AST = null;
		AST r_AST = null;
		AST r = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case EVAL_OR_EXPR:
		{
			AST __t80 = _t;
			jo = (ASTNULL == _t) ? null : (AST)_t;
			AST jo_AST_in = null;
			jo_AST = astFactory.create(jo);
			astFactory.addASTChild(ref currentAST, jo_AST);
			ASTPair __currentAST80 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVAL_OR_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop82_breakloop;
					}
					
				}
_loop82_breakloop:				;
			}    // ( ... )*
			leaveNode(jo_AST);
			currentAST = __currentAST80;
			_t = __t80;
			_t = _t.getNextSibling();
			evalExprChoice_AST = currentAST.root;
			break;
		}
		case EVAL_AND_EXPR:
		{
			AST __t83 = _t;
			ja = (ASTNULL == _t) ? null : (AST)_t;
			AST ja_AST_in = null;
			ja_AST = astFactory.create(ja);
			astFactory.addASTChild(ref currentAST, ja_AST);
			ASTPair __currentAST83 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVAL_AND_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop85_breakloop;
					}
					
				}
_loop85_breakloop:				;
			}    // ( ... )*
			leaveNode(ja_AST);
			currentAST = __currentAST83;
			_t = __t83;
			_t = _t.getNextSibling();
			evalExprChoice_AST = currentAST.root;
			break;
		}
		case EVAL_EQUALS_EXPR:
		{
			AST __t86 = _t;
			je = (ASTNULL == _t) ? null : (AST)_t;
			AST je_AST_in = null;
			je_AST = astFactory.create(je);
			astFactory.addASTChild(ref currentAST, je_AST);
			ASTPair __currentAST86 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVAL_EQUALS_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(je_AST);
			currentAST = __currentAST86;
			_t = __t86;
			_t = _t.getNextSibling();
			evalExprChoice_AST = currentAST.root;
			break;
		}
		case EVAL_NOTEQUALS_EXPR:
		{
			AST __t87 = _t;
			jne = (ASTNULL == _t) ? null : (AST)_t;
			AST jne_AST_in = null;
			jne_AST = astFactory.create(jne);
			astFactory.addASTChild(ref currentAST, jne_AST);
			ASTPair __currentAST87 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVAL_NOTEQUALS_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(jne_AST);
			currentAST = __currentAST87;
			_t = __t87;
			_t = _t.getNextSibling();
			evalExprChoice_AST = currentAST.root;
			break;
		}
		case NOT_EXPR:
		{
			AST __t88 = _t;
			n = (ASTNULL == _t) ? null : (AST)_t;
			AST n_AST_in = null;
			n_AST = astFactory.create(n);
			astFactory.addASTChild(ref currentAST, n_AST);
			ASTPair __currentAST88 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(n_AST);
			currentAST = __currentAST88;
			_t = __t88;
			_t = _t.getNextSibling();
			evalExprChoice_AST = currentAST.root;
			break;
		}
		case LT_:
		case GT:
		case LE:
		case GE:
		{
			r = _t==ASTNULL ? null : _t;
			relationalExpr(_t);
			_t = retTree_;
			r_AST = (AST)returnAST;
			astFactory.addASTChild(ref currentAST, returnAST);
			leaveNode(r_AST);
			evalExprChoice_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = evalExprChoice_AST;
		retTree_ = _t;
	}
	
	public void constant(AST _t) //throws RecognitionException
{
		
		AST constant_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST constant_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case INT_TYPE:
		{
			AST tmp40_AST = null;
			AST tmp40_AST_in = null;
			tmp40_AST = astFactory.create(_t);
			tmp40_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp40_AST);
			match(_t,INT_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		case LONG_TYPE:
		{
			AST tmp41_AST = null;
			AST tmp41_AST_in = null;
			tmp41_AST = astFactory.create(_t);
			tmp41_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp41_AST);
			match(_t,LONG_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		case FLOAT_TYPE:
		{
			AST tmp42_AST = null;
			AST tmp42_AST_in = null;
			tmp42_AST = astFactory.create(_t);
			tmp42_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp42_AST);
			match(_t,FLOAT_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		case DOUBLE_TYPE:
		{
			AST tmp43_AST = null;
			AST tmp43_AST_in = null;
			tmp43_AST = astFactory.create(_t);
			tmp43_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp43_AST);
			match(_t,DOUBLE_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		case STRING_TYPE:
		{
			AST tmp44_AST = null;
			AST tmp44_AST_in = null;
			tmp44_AST = astFactory.create(_t);
			tmp44_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp44_AST);
			match(_t,STRING_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		case BOOL_TYPE:
		{
			AST tmp45_AST = null;
			AST tmp45_AST_in = null;
			tmp45_AST = astFactory.create(_t);
			tmp45_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp45_AST);
			match(_t,BOOL_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		case NULL_TYPE:
		{
			AST tmp46_AST = null;
			AST tmp46_AST_in = null;
			tmp46_AST = astFactory.create(_t);
			tmp46_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp46_AST);
			match(_t,NULL_TYPE);
			_t = _t.getNextSibling();
			constant_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = constant_AST;
		retTree_ = _t;
	}
	
	public void arithmeticExpr(AST _t) //throws RecognitionException
{
		
		AST arithmeticExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arithmeticExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case PLUS:
		{
			AST __t169 = _t;
			AST tmp47_AST = null;
			AST tmp47_AST_in = null;
			tmp47_AST = astFactory.create(_t);
			tmp47_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp47_AST);
			ASTPair __currentAST169 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,PLUS);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST169;
			_t = __t169;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case MINUS:
		{
			AST __t170 = _t;
			AST tmp48_AST = null;
			AST tmp48_AST_in = null;
			tmp48_AST = astFactory.create(_t);
			tmp48_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp48_AST);
			ASTPair __currentAST170 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,MINUS);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST170;
			_t = __t170;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case DIV:
		{
			AST __t171 = _t;
			AST tmp49_AST = null;
			AST tmp49_AST_in = null;
			tmp49_AST = astFactory.create(_t);
			tmp49_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp49_AST);
			ASTPair __currentAST171 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,DIV);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST171;
			_t = __t171;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case STAR:
		{
			AST __t172 = _t;
			AST tmp50_AST = null;
			AST tmp50_AST_in = null;
			tmp50_AST = astFactory.create(_t);
			tmp50_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp50_AST);
			ASTPair __currentAST172 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,STAR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST172;
			_t = __t172;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case MOD:
		{
			AST __t173 = _t;
			AST tmp51_AST = null;
			AST tmp51_AST_in = null;
			tmp51_AST = astFactory.create(_t);
			tmp51_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp51_AST);
			ASTPair __currentAST173 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,MOD);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST173;
			_t = __t173;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case BAND:
		{
			AST __t174 = _t;
			AST tmp52_AST = null;
			AST tmp52_AST_in = null;
			tmp52_AST = astFactory.create(_t);
			tmp52_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp52_AST);
			ASTPair __currentAST174 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BAND);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST174;
			_t = __t174;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case BOR:
		{
			AST __t175 = _t;
			AST tmp53_AST = null;
			AST tmp53_AST_in = null;
			tmp53_AST = astFactory.create(_t);
			tmp53_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp53_AST);
			ASTPair __currentAST175 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BOR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST175;
			_t = __t175;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case BXOR:
		{
			AST __t176 = _t;
			AST tmp54_AST = null;
			AST tmp54_AST_in = null;
			tmp54_AST = astFactory.create(_t);
			tmp54_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp54_AST);
			ASTPair __currentAST176 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BXOR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST176;
			_t = __t176;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		case CONCAT:
		{
			AST __t177 = _t;
			AST tmp55_AST = null;
			AST tmp55_AST_in = null;
			tmp55_AST = astFactory.create(_t);
			tmp55_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp55_AST);
			ASTPair __currentAST177 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,CONCAT);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop179_breakloop;
					}
					
				}
_loop179_breakloop:				;
			}    // ( ... )*
			currentAST = __currentAST177;
			_t = __t177;
			_t = _t.getNextSibling();
			arithmeticExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = arithmeticExpr_AST;
		retTree_ = _t;
	}
	
	public void builtinFunc(AST _t) //throws RecognitionException
{
		
		AST builtinFunc_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST builtinFunc_AST = null;
		AST c = null;
		AST c_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case SUM:
		{
			AST __t146 = _t;
			AST tmp56_AST = null;
			AST tmp56_AST_in = null;
			tmp56_AST = astFactory.create(_t);
			tmp56_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp56_AST);
			ASTPair __currentAST146 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,SUM);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case DISTINCT:
				{
					AST tmp57_AST = null;
					AST tmp57_AST_in = null;
					tmp57_AST = astFactory.create(_t);
					tmp57_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp57_AST);
					match(_t,DISTINCT);
					_t = _t.getNextSibling();
					break;
				}
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST146;
			_t = __t146;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case AVG:
		{
			AST __t148 = _t;
			AST tmp58_AST = null;
			AST tmp58_AST_in = null;
			tmp58_AST = astFactory.create(_t);
			tmp58_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp58_AST);
			ASTPair __currentAST148 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,AVG);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case DISTINCT:
				{
					AST tmp59_AST = null;
					AST tmp59_AST_in = null;
					tmp59_AST = astFactory.create(_t);
					tmp59_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp59_AST);
					match(_t,DISTINCT);
					_t = _t.getNextSibling();
					break;
				}
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST148;
			_t = __t148;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case COUNT:
		{
			AST __t150 = _t;
			AST tmp60_AST = null;
			AST tmp60_AST_in = null;
			tmp60_AST = astFactory.create(_t);
			tmp60_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp60_AST);
			ASTPair __currentAST150 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,COUNT);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case DISTINCT:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					{
						if (null == _t)
							_t = ASTNULL;
						switch ( _t.Type )
						{
						case DISTINCT:
						{
							AST tmp61_AST = null;
							AST tmp61_AST_in = null;
							tmp61_AST = astFactory.create(_t);
							tmp61_AST_in = _t;
							astFactory.addASTChild(ref currentAST, tmp61_AST);
							match(_t,DISTINCT);
							_t = _t.getNextSibling();
							break;
						}
						case IN_SET:
						case BETWEEN:
						case LIKE:
						case REGEXP:
						case NOT_EXPR:
						case SUM:
						case AVG:
						case COALESCE:
						case MEDIAN:
						case STDDEV:
						case AVEDEV:
						case COUNT:
						case CASE:
						case CASE2:
						case PREVIOUS:
						case PRIOR:
						case EVAL_AND_EXPR:
						case EVAL_OR_EXPR:
						case EVAL_EQUALS_EXPR:
						case EVAL_NOTEQUALS_EXPR:
						case EVENT_PROP_EXPR:
						case CONCAT:
						case LIB_FUNCTION:
						case ARRAY_EXPR:
						case NOT_IN_SET:
						case NOT_BETWEEN:
						case NOT_LIKE:
						case NOT_REGEXP:
						case IN_RANGE:
						case NOT_IN_RANGE:
						case SUBSELECT_EXPR:
						case EXISTS_SUBSELECT_EXPR:
						case IN_SUBSELECT_EXPR:
						case NOT_IN_SUBSELECT_EXPR:
						case INT_TYPE:
						case LONG_TYPE:
						case FLOAT_TYPE:
						case DOUBLE_TYPE:
						case STRING_TYPE:
						case BOOL_TYPE:
						case NULL_TYPE:
						case MINUS:
						case PLUS:
						case STAR:
						case BAND:
						case BOR:
						case BXOR:
						case LT_:
						case GT:
						case LE:
						case GE:
						case DIV:
						case MOD:
						{
							break;
						}
						default:
						{
							throw new NoViableAltException(_t);
						}
						 }
					}
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST150;
			_t = __t150;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case MEDIAN:
		{
			AST __t153 = _t;
			AST tmp62_AST = null;
			AST tmp62_AST_in = null;
			tmp62_AST = astFactory.create(_t);
			tmp62_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp62_AST);
			ASTPair __currentAST153 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,MEDIAN);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case DISTINCT:
				{
					AST tmp63_AST = null;
					AST tmp63_AST_in = null;
					tmp63_AST = astFactory.create(_t);
					tmp63_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp63_AST);
					match(_t,DISTINCT);
					_t = _t.getNextSibling();
					break;
				}
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST153;
			_t = __t153;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case STDDEV:
		{
			AST __t155 = _t;
			AST tmp64_AST = null;
			AST tmp64_AST_in = null;
			tmp64_AST = astFactory.create(_t);
			tmp64_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp64_AST);
			ASTPair __currentAST155 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,STDDEV);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case DISTINCT:
				{
					AST tmp65_AST = null;
					AST tmp65_AST_in = null;
					tmp65_AST = astFactory.create(_t);
					tmp65_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp65_AST);
					match(_t,DISTINCT);
					_t = _t.getNextSibling();
					break;
				}
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST155;
			_t = __t155;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case AVEDEV:
		{
			AST __t157 = _t;
			AST tmp66_AST = null;
			AST tmp66_AST_in = null;
			tmp66_AST = astFactory.create(_t);
			tmp66_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp66_AST);
			ASTPair __currentAST157 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,AVEDEV);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case DISTINCT:
				{
					AST tmp67_AST = null;
					AST tmp67_AST_in = null;
					tmp67_AST = astFactory.create(_t);
					tmp67_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp67_AST);
					match(_t,DISTINCT);
					_t = _t.getNextSibling();
					break;
				}
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST157;
			_t = __t157;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case COALESCE:
		{
			AST __t159 = _t;
			AST tmp68_AST = null;
			AST tmp68_AST_in = null;
			tmp68_AST = astFactory.create(_t);
			tmp68_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp68_AST);
			ASTPair __currentAST159 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,COALESCE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop161_breakloop;
					}
					
				}
_loop161_breakloop:				;
			}    // ( ... )*
			currentAST = __currentAST159;
			_t = __t159;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case PREVIOUS:
		{
			AST __t162 = _t;
			AST tmp69_AST = null;
			AST tmp69_AST_in = null;
			tmp69_AST = astFactory.create(_t);
			tmp69_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp69_AST);
			ASTPair __currentAST162 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,PREVIOUS);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST162;
			_t = __t162;
			_t = _t.getNextSibling();
			builtinFunc_AST = currentAST.root;
			break;
		}
		case PRIOR:
		{
			AST __t163 = _t;
			AST tmp70_AST = null;
			AST tmp70_AST_in = null;
			tmp70_AST = astFactory.create(_t);
			tmp70_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp70_AST);
			ASTPair __currentAST163 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,PRIOR);
			_t = _t.getFirstChild();
			c = _t;
			AST c_AST_in = null;
			c_AST = astFactory.create(c);
			astFactory.addASTChild(ref currentAST, c_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			eventPropertyExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST163;
			_t = __t163;
			_t = _t.getNextSibling();
			leaveNode(c_AST);
			builtinFunc_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = builtinFunc_AST;
		retTree_ = _t;
	}
	
	public void libFunc(AST _t) //throws RecognitionException
{
		
		AST libFunc_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST libFunc_AST = null;
		
		AST __t181 = _t;
		AST tmp71_AST = null;
		AST tmp71_AST_in = null;
		tmp71_AST = astFactory.create(_t);
		tmp71_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp71_AST);
		ASTPair __currentAST181 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,LIB_FUNCTION);
		_t = _t.getFirstChild();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case CLASS_IDENT:
			{
				AST tmp72_AST = null;
				AST tmp72_AST_in = null;
				tmp72_AST = astFactory.create(_t);
				tmp72_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp72_AST);
				match(_t,CLASS_IDENT);
				_t = _t.getNextSibling();
				break;
			}
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		AST tmp73_AST = null;
		AST tmp73_AST_in = null;
		tmp73_AST = astFactory.create(_t);
		tmp73_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp73_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case DISTINCT:
			{
				AST tmp74_AST = null;
				AST tmp74_AST_in = null;
				tmp74_AST = astFactory.create(_t);
				tmp74_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp74_AST);
				match(_t,DISTINCT);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			case IN_SET:
			case BETWEEN:
			case LIKE:
			case REGEXP:
			case NOT_EXPR:
			case SUM:
			case AVG:
			case COALESCE:
			case MEDIAN:
			case STDDEV:
			case AVEDEV:
			case COUNT:
			case CASE:
			case CASE2:
			case PREVIOUS:
			case PRIOR:
			case EVAL_AND_EXPR:
			case EVAL_OR_EXPR:
			case EVAL_EQUALS_EXPR:
			case EVAL_NOTEQUALS_EXPR:
			case EVENT_PROP_EXPR:
			case CONCAT:
			case LIB_FUNCTION:
			case ARRAY_EXPR:
			case NOT_IN_SET:
			case NOT_BETWEEN:
			case NOT_LIKE:
			case NOT_REGEXP:
			case IN_RANGE:
			case NOT_IN_RANGE:
			case SUBSELECT_EXPR:
			case EXISTS_SUBSELECT_EXPR:
			case IN_SUBSELECT_EXPR:
			case NOT_IN_SUBSELECT_EXPR:
			case INT_TYPE:
			case LONG_TYPE:
			case FLOAT_TYPE:
			case DOUBLE_TYPE:
			case STRING_TYPE:
			case BOOL_TYPE:
			case NULL_TYPE:
			case MINUS:
			case PLUS:
			case STAR:
			case BAND:
			case BOR:
			case BXOR:
			case LT_:
			case GT:
			case LE:
			case GE:
			case DIV:
			case MOD:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((tokenSet_0_.member(_t.Type)))
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop185_breakloop;
				}
				
			}
_loop185_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST181;
		_t = __t181;
		_t = _t.getNextSibling();
		libFunc_AST = currentAST.root;
		returnAST = libFunc_AST;
		retTree_ = _t;
	}
	
	public void caseExpr(AST _t) //throws RecognitionException
{
		
		AST caseExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST caseExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case CASE:
		{
			AST __t109 = _t;
			AST tmp75_AST = null;
			AST tmp75_AST_in = null;
			tmp75_AST = astFactory.create(_t);
			tmp75_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp75_AST);
			ASTPair __currentAST109 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,CASE);
			_t = _t.getFirstChild();
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop111_breakloop;
					}
					
				}
_loop111_breakloop:				;
			}    // ( ... )*
			currentAST = __currentAST109;
			_t = __t109;
			_t = _t.getNextSibling();
			caseExpr_AST = currentAST.root;
			break;
		}
		case CASE2:
		{
			AST __t112 = _t;
			AST tmp76_AST = null;
			AST tmp76_AST_in = null;
			tmp76_AST = astFactory.create(_t);
			tmp76_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp76_AST);
			ASTPair __currentAST112 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,CASE2);
			_t = _t.getFirstChild();
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop114_breakloop;
					}
					
				}
_loop114_breakloop:				;
			}    // ( ... )*
			currentAST = __currentAST112;
			_t = __t112;
			_t = _t.getNextSibling();
			caseExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = caseExpr_AST;
		retTree_ = _t;
	}
	
	public void inExpr(AST _t) //throws RecognitionException
{
		
		AST inExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST inExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case IN_SET:
		{
			AST __t116 = _t;
			AST tmp77_AST = null;
			AST tmp77_AST_in = null;
			tmp77_AST = astFactory.create(_t);
			tmp77_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp77_AST);
			ASTPair __currentAST116 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,IN_SET);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp78_AST = null;
					AST tmp78_AST_in = null;
					tmp78_AST = astFactory.create(_t);
					tmp78_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp78_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp79_AST = null;
					AST tmp79_AST_in = null;
					tmp79_AST = astFactory.create(_t);
					tmp79_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp79_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop119_breakloop;
					}
					
				}
_loop119_breakloop:				;
			}    // ( ... )*
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp80_AST = null;
					AST tmp80_AST_in = null;
					tmp80_AST = astFactory.create(_t);
					tmp80_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp80_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp81_AST = null;
					AST tmp81_AST_in = null;
					tmp81_AST = astFactory.create(_t);
					tmp81_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp81_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST116;
			_t = __t116;
			_t = _t.getNextSibling();
			inExpr_AST = currentAST.root;
			break;
		}
		case NOT_IN_SET:
		{
			AST __t121 = _t;
			AST tmp82_AST = null;
			AST tmp82_AST_in = null;
			tmp82_AST = astFactory.create(_t);
			tmp82_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp82_AST);
			ASTPair __currentAST121 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_IN_SET);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp83_AST = null;
					AST tmp83_AST_in = null;
					tmp83_AST = astFactory.create(_t);
					tmp83_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp83_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp84_AST = null;
					AST tmp84_AST_in = null;
					tmp84_AST = astFactory.create(_t);
					tmp84_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp84_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop124_breakloop;
					}
					
				}
_loop124_breakloop:				;
			}    // ( ... )*
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp85_AST = null;
					AST tmp85_AST_in = null;
					tmp85_AST = astFactory.create(_t);
					tmp85_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp85_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp86_AST = null;
					AST tmp86_AST_in = null;
					tmp86_AST = astFactory.create(_t);
					tmp86_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp86_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST121;
			_t = __t121;
			_t = _t.getNextSibling();
			inExpr_AST = currentAST.root;
			break;
		}
		case IN_RANGE:
		{
			AST __t126 = _t;
			AST tmp87_AST = null;
			AST tmp87_AST_in = null;
			tmp87_AST = astFactory.create(_t);
			tmp87_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp87_AST);
			ASTPair __currentAST126 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,IN_RANGE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp88_AST = null;
					AST tmp88_AST_in = null;
					tmp88_AST = astFactory.create(_t);
					tmp88_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp88_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp89_AST = null;
					AST tmp89_AST_in = null;
					tmp89_AST = astFactory.create(_t);
					tmp89_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp89_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp90_AST = null;
					AST tmp90_AST_in = null;
					tmp90_AST = astFactory.create(_t);
					tmp90_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp90_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp91_AST = null;
					AST tmp91_AST_in = null;
					tmp91_AST = astFactory.create(_t);
					tmp91_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp91_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST126;
			_t = __t126;
			_t = _t.getNextSibling();
			inExpr_AST = currentAST.root;
			break;
		}
		case NOT_IN_RANGE:
		{
			AST __t129 = _t;
			AST tmp92_AST = null;
			AST tmp92_AST_in = null;
			tmp92_AST = astFactory.create(_t);
			tmp92_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp92_AST);
			ASTPair __currentAST129 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_IN_RANGE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp93_AST = null;
					AST tmp93_AST_in = null;
					tmp93_AST = astFactory.create(_t);
					tmp93_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp93_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp94_AST = null;
					AST tmp94_AST_in = null;
					tmp94_AST = astFactory.create(_t);
					tmp94_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp94_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp95_AST = null;
					AST tmp95_AST_in = null;
					tmp95_AST = astFactory.create(_t);
					tmp95_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp95_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp96_AST = null;
					AST tmp96_AST_in = null;
					tmp96_AST = astFactory.create(_t);
					tmp96_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp96_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST129;
			_t = __t129;
			_t = _t.getNextSibling();
			inExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = inExpr_AST;
		retTree_ = _t;
	}
	
	public void betweenExpr(AST _t) //throws RecognitionException
{
		
		AST betweenExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST betweenExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case BETWEEN:
		{
			AST __t133 = _t;
			AST tmp97_AST = null;
			AST tmp97_AST_in = null;
			tmp97_AST = astFactory.create(_t);
			tmp97_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp97_AST);
			ASTPair __currentAST133 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,BETWEEN);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST133;
			_t = __t133;
			_t = _t.getNextSibling();
			betweenExpr_AST = currentAST.root;
			break;
		}
		case NOT_BETWEEN:
		{
			AST __t134 = _t;
			AST tmp98_AST = null;
			AST tmp98_AST_in = null;
			tmp98_AST = astFactory.create(_t);
			tmp98_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp98_AST);
			ASTPair __currentAST134 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_BETWEEN);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_0_.member(_t.Type)))
					{
						valueExpr(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop136_breakloop;
					}
					
				}
_loop136_breakloop:				;
			}    // ( ... )*
			currentAST = __currentAST134;
			_t = __t134;
			_t = _t.getNextSibling();
			betweenExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = betweenExpr_AST;
		retTree_ = _t;
	}
	
	public void likeExpr(AST _t) //throws RecognitionException
{
		
		AST likeExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST likeExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case LIKE:
		{
			AST __t138 = _t;
			AST tmp99_AST = null;
			AST tmp99_AST_in = null;
			tmp99_AST = astFactory.create(_t);
			tmp99_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp99_AST);
			ASTPair __currentAST138 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LIKE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST138;
			_t = __t138;
			_t = _t.getNextSibling();
			likeExpr_AST = currentAST.root;
			break;
		}
		case NOT_LIKE:
		{
			AST __t140 = _t;
			AST tmp100_AST = null;
			AST tmp100_AST_in = null;
			tmp100_AST = astFactory.create(_t);
			tmp100_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp100_AST);
			ASTPair __currentAST140 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_LIKE);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case IN_SET:
				case BETWEEN:
				case LIKE:
				case REGEXP:
				case NOT_EXPR:
				case SUM:
				case AVG:
				case COALESCE:
				case MEDIAN:
				case STDDEV:
				case AVEDEV:
				case COUNT:
				case CASE:
				case CASE2:
				case PREVIOUS:
				case PRIOR:
				case EVAL_AND_EXPR:
				case EVAL_OR_EXPR:
				case EVAL_EQUALS_EXPR:
				case EVAL_NOTEQUALS_EXPR:
				case EVENT_PROP_EXPR:
				case CONCAT:
				case LIB_FUNCTION:
				case ARRAY_EXPR:
				case NOT_IN_SET:
				case NOT_BETWEEN:
				case NOT_LIKE:
				case NOT_REGEXP:
				case IN_RANGE:
				case NOT_IN_RANGE:
				case SUBSELECT_EXPR:
				case EXISTS_SUBSELECT_EXPR:
				case IN_SUBSELECT_EXPR:
				case NOT_IN_SUBSELECT_EXPR:
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				case MINUS:
				case PLUS:
				case STAR:
				case BAND:
				case BOR:
				case BXOR:
				case LT_:
				case GT:
				case LE:
				case GE:
				case DIV:
				case MOD:
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST140;
			_t = __t140;
			_t = _t.getNextSibling();
			likeExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = likeExpr_AST;
		retTree_ = _t;
	}
	
	public void regExpExpr(AST _t) //throws RecognitionException
{
		
		AST regExpExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST regExpExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case REGEXP:
		{
			AST __t143 = _t;
			AST tmp101_AST = null;
			AST tmp101_AST_in = null;
			tmp101_AST = astFactory.create(_t);
			tmp101_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp101_AST);
			ASTPair __currentAST143 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,REGEXP);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST143;
			_t = __t143;
			_t = _t.getNextSibling();
			regExpExpr_AST = currentAST.root;
			break;
		}
		case NOT_REGEXP:
		{
			AST __t144 = _t;
			AST tmp102_AST = null;
			AST tmp102_AST_in = null;
			tmp102_AST = astFactory.create(_t);
			tmp102_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp102_AST);
			ASTPair __currentAST144 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_REGEXP);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST144;
			_t = __t144;
			_t = _t.getNextSibling();
			regExpExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = regExpExpr_AST;
		retTree_ = _t;
	}
	
	public void arrayExpr(AST _t) //throws RecognitionException
{
		
		AST arrayExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST arrayExpr_AST = null;
		
		AST __t165 = _t;
		AST tmp103_AST = null;
		AST tmp103_AST_in = null;
		tmp103_AST = astFactory.create(_t);
		tmp103_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp103_AST);
		ASTPair __currentAST165 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,ARRAY_EXPR);
		_t = _t.getFirstChild();
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((tokenSet_0_.member(_t.Type)))
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop167_breakloop;
				}
				
			}
_loop167_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST165;
		_t = __t165;
		_t = _t.getNextSibling();
		arrayExpr_AST = currentAST.root;
		returnAST = arrayExpr_AST;
		retTree_ = _t;
	}
	
	public void subSelectInExpr(AST _t) //throws RecognitionException
{
		
		AST subSelectInExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectInExpr_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case IN_SUBSELECT_EXPR:
		{
			AST __t95 = _t;
			AST tmp104_AST = null;
			AST tmp104_AST_in = null;
			tmp104_AST = astFactory.create(_t);
			tmp104_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp104_AST);
			ASTPair __currentAST95 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,IN_SUBSELECT_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			subSelectInQueryExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST95;
			_t = __t95;
			_t = _t.getNextSibling();
			subSelectInExpr_AST = currentAST.root;
			break;
		}
		case NOT_IN_SUBSELECT_EXPR:
		{
			AST __t96 = _t;
			AST tmp105_AST = null;
			AST tmp105_AST_in = null;
			tmp105_AST = astFactory.create(_t);
			tmp105_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp105_AST);
			ASTPair __currentAST96 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_IN_SUBSELECT_EXPR);
			_t = _t.getFirstChild();
			valueExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			subSelectInQueryExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST96;
			_t = __t96;
			_t = _t.getNextSibling();
			subSelectInExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = subSelectInExpr_AST;
		retTree_ = _t;
	}
	
	public void subSelectRowExpr(AST _t) //throws RecognitionException
{
		
		AST subSelectRowExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectRowExpr_AST = null;
		
		pushStmtContext();
		AST __t91 = _t;
		AST tmp106_AST = null;
		AST tmp106_AST_in = null;
		tmp106_AST = astFactory.create(_t);
		tmp106_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp106_AST);
		ASTPair __currentAST91 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,SUBSELECT_EXPR);
		_t = _t.getFirstChild();
		subQueryExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST91;
		_t = __t91;
		_t = _t.getNextSibling();
		subSelectRowExpr_AST = (AST)currentAST.root;
		leaveNode(subSelectRowExpr_AST);
		subSelectRowExpr_AST = currentAST.root;
		returnAST = subSelectRowExpr_AST;
		retTree_ = _t;
	}
	
	public void subSelectExistsExpr(AST _t) //throws RecognitionException
{
		
		AST subSelectExistsExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectExistsExpr_AST = null;
		
		pushStmtContext();
		AST __t93 = _t;
		AST tmp107_AST = null;
		AST tmp107_AST_in = null;
		tmp107_AST = astFactory.create(_t);
		tmp107_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp107_AST);
		ASTPair __currentAST93 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,EXISTS_SUBSELECT_EXPR);
		_t = _t.getFirstChild();
		subQueryExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST93;
		_t = __t93;
		_t = _t.getNextSibling();
		subSelectExistsExpr_AST = (AST)currentAST.root;
		leaveNode(subSelectExistsExpr_AST);
		subSelectExistsExpr_AST = currentAST.root;
		returnAST = subSelectExistsExpr_AST;
		retTree_ = _t;
	}
	
	public void subQueryExpr(AST _t) //throws RecognitionException
{
		
		AST subQueryExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subQueryExpr_AST = null;
		
		selectionListElement(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		subSelectFilterExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((_t.Type==VIEW_EXPR))
				{
					viewExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop101_breakloop;
				}
				
			}
_loop101_breakloop:			;
		}    // ( ... )*
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case IDENT:
			{
				AST tmp108_AST = null;
				AST tmp108_AST_in = null;
				tmp108_AST = astFactory.create(_t);
				tmp108_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp108_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			case WHERE_EXPR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case WHERE_EXPR:
			{
				whereClause(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		subQueryExpr_AST = currentAST.root;
		returnAST = subQueryExpr_AST;
		retTree_ = _t;
	}
	
	public void subSelectInQueryExpr(AST _t) //throws RecognitionException
{
		
		AST subSelectInQueryExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectInQueryExpr_AST = null;
		
		pushStmtContext();
		AST __t98 = _t;
		AST tmp109_AST = null;
		AST tmp109_AST_in = null;
		tmp109_AST = astFactory.create(_t);
		tmp109_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp109_AST);
		ASTPair __currentAST98 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,IN_SUBSELECT_QUERY_EXPR);
		_t = _t.getFirstChild();
		subQueryExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST98;
		_t = __t98;
		_t = _t.getNextSibling();
		subSelectInQueryExpr_AST = (AST)currentAST.root;
		leaveNode(subSelectInQueryExpr_AST);
		subSelectInQueryExpr_AST = currentAST.root;
		returnAST = subSelectInQueryExpr_AST;
		retTree_ = _t;
	}
	
	public void subSelectFilterExpr(AST _t) //throws RecognitionException
{
		
		AST subSelectFilterExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST subSelectFilterExpr_AST = null;
		AST v = null;
		AST v_AST = null;
		
		AST __t105 = _t;
		v = (ASTNULL == _t) ? null : (AST)_t;
		AST v_AST_in = null;
		v_AST = astFactory.create(v);
		astFactory.addASTChild(ref currentAST, v_AST);
		ASTPair __currentAST105 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,STREAM_EXPR);
		_t = _t.getFirstChild();
		eventFilterExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case VIEW_EXPR:
			{
				viewListExpr(_t);
				_t = retTree_;
				astFactory.addASTChild(ref currentAST, returnAST);
				break;
			}
			case 3:
			case IDENT:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		{
			if (null == _t)
				_t = ASTNULL;
			switch ( _t.Type )
			{
			case IDENT:
			{
				AST tmp110_AST = null;
				AST tmp110_AST_in = null;
				tmp110_AST = astFactory.create(_t);
				tmp110_AST_in = _t;
				astFactory.addASTChild(ref currentAST, tmp110_AST);
				match(_t,IDENT);
				_t = _t.getNextSibling();
				break;
			}
			case 3:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(_t);
			}
			 }
		}
		leaveNode(v_AST);
		currentAST = __currentAST105;
		_t = __t105;
		_t = _t.getNextSibling();
		subSelectFilterExpr_AST = currentAST.root;
		returnAST = subSelectFilterExpr_AST;
		retTree_ = _t;
	}
	
	public void startPatternExpressionRule(AST _t) //throws RecognitionException
{
		
		AST startPatternExpressionRule_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST startPatternExpressionRule_AST = null;
		
		setIsPatternWalk(true);
		exprChoice(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		endPattern(); end();
		startPatternExpressionRule_AST = currentAST.root;
		returnAST = startPatternExpressionRule_AST;
		retTree_ = _t;
	}
	
	public void atomicExpr(AST _t) //throws RecognitionException
{
		
		AST atomicExpr_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST atomicExpr_AST = null;
		AST ac = null;
		AST ac_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case EVENT_FILTER_EXPR:
		{
			eventFilterExpr(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			atomicExpr_AST = currentAST.root;
			break;
		}
		case OBSERVER_EXPR:
		{
			AST __t204 = _t;
			ac = (ASTNULL == _t) ? null : (AST)_t;
			AST ac_AST_in = null;
			ac_AST = astFactory.create(ac);
			astFactory.addASTChild(ref currentAST, ac_AST);
			ASTPair __currentAST204 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,OBSERVER_EXPR);
			_t = _t.getFirstChild();
			AST tmp111_AST = null;
			AST tmp111_AST_in = null;
			tmp111_AST = astFactory.create(_t);
			tmp111_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp111_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			AST tmp112_AST = null;
			AST tmp112_AST_in = null;
			tmp112_AST = astFactory.create(_t);
			tmp112_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp112_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_1_.member(_t.Type)))
					{
						parameter(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop206_breakloop;
					}
					
				}
_loop206_breakloop:				;
			}    // ( ... )*
			leaveNode(ac_AST);
			currentAST = __currentAST204;
			_t = __t204;
			_t = _t.getNextSibling();
			atomicExpr_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = atomicExpr_AST;
		retTree_ = _t;
	}
	
	public void patternOp(AST _t) //throws RecognitionException
{
		
		AST patternOp_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST patternOp_AST = null;
		AST f = null;
		AST f_AST = null;
		AST o = null;
		AST o_AST = null;
		AST a = null;
		AST a_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case FOLLOWED_BY_EXPR:
		{
			AST __t194 = _t;
			f = (ASTNULL == _t) ? null : (AST)_t;
			AST f_AST_in = null;
			f_AST = astFactory.create(f);
			astFactory.addASTChild(ref currentAST, f_AST);
			ASTPair __currentAST194 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,FOLLOWED_BY_EXPR);
			_t = _t.getFirstChild();
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_2_.member(_t.Type)))
					{
						exprChoice(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop196_breakloop;
					}
					
				}
_loop196_breakloop:				;
			}    // ( ... )*
			leaveNode(f_AST);
			currentAST = __currentAST194;
			_t = __t194;
			_t = _t.getNextSibling();
			patternOp_AST = currentAST.root;
			break;
		}
		case OR_EXPR:
		{
			AST __t197 = _t;
			o = (ASTNULL == _t) ? null : (AST)_t;
			AST o_AST_in = null;
			o_AST = astFactory.create(o);
			astFactory.addASTChild(ref currentAST, o_AST);
			ASTPair __currentAST197 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,OR_EXPR);
			_t = _t.getFirstChild();
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_2_.member(_t.Type)))
					{
						exprChoice(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop199_breakloop;
					}
					
				}
_loop199_breakloop:				;
			}    // ( ... )*
			leaveNode(o_AST);
			currentAST = __currentAST197;
			_t = __t197;
			_t = _t.getNextSibling();
			patternOp_AST = currentAST.root;
			break;
		}
		case AND_EXPR:
		{
			AST __t200 = _t;
			a = (ASTNULL == _t) ? null : (AST)_t;
			AST a_AST_in = null;
			a_AST = astFactory.create(a);
			astFactory.addASTChild(ref currentAST, a_AST);
			ASTPair __currentAST200 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,AND_EXPR);
			_t = _t.getFirstChild();
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			exprChoice(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{    // ( ... )*
				for (;;)
				{
					if (_t == null)
						_t = ASTNULL;
					if ((tokenSet_2_.member(_t.Type)))
					{
						exprChoice(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
					}
					else
					{
						goto _loop202_breakloop;
					}
					
				}
_loop202_breakloop:				;
			}    // ( ... )*
			leaveNode(a_AST);
			currentAST = __currentAST200;
			_t = __t200;
			_t = _t.getNextSibling();
			patternOp_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = patternOp_AST;
		retTree_ = _t;
	}
	
	public void time_period(AST _t) //throws RecognitionException
{
		
		AST time_period_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST time_period_AST = null;
		
		AST __t277 = _t;
		AST tmp113_AST = null;
		AST tmp113_AST_in = null;
		tmp113_AST = astFactory.create(_t);
		tmp113_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp113_AST);
		ASTPair __currentAST277 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,TIME_PERIOD);
		_t = _t.getFirstChild();
		timePeriodDef(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST277;
		_t = __t277;
		_t = _t.getNextSibling();
		time_period_AST = currentAST.root;
		returnAST = time_period_AST;
		retTree_ = _t;
	}
	
	public void filterParam(AST _t) //throws RecognitionException
{
		
		AST filterParam_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterParam_AST = null;
		
		AST __t213 = _t;
		AST tmp114_AST = null;
		AST tmp114_AST_in = null;
		tmp114_AST = astFactory.create(_t);
		tmp114_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp114_AST);
		ASTPair __currentAST213 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,EVENT_FILTER_PARAM);
		_t = _t.getFirstChild();
		valueExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		{    // ( ... )*
			for (;;)
			{
				if (_t == null)
					_t = ASTNULL;
				if ((tokenSet_0_.member(_t.Type)))
				{
					valueExpr(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
				}
				else
				{
					goto _loop215_breakloop;
				}
				
			}
_loop215_breakloop:			;
		}    // ( ... )*
		currentAST = __currentAST213;
		_t = __t213;
		_t = _t.getNextSibling();
		filterParam_AST = currentAST.root;
		returnAST = filterParam_AST;
		retTree_ = _t;
	}
	
	public void filterParamComparator(AST _t) //throws RecognitionException
{
		
		AST filterParamComparator_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterParamComparator_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case EQUALS:
		{
			AST __t217 = _t;
			AST tmp115_AST = null;
			AST tmp115_AST_in = null;
			tmp115_AST = astFactory.create(_t);
			tmp115_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp115_AST);
			ASTPair __currentAST217 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EQUALS);
			_t = _t.getFirstChild();
			filterAtom(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST217;
			_t = __t217;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case NOT_EQUAL:
		{
			AST __t218 = _t;
			AST tmp116_AST = null;
			AST tmp116_AST_in = null;
			tmp116_AST = astFactory.create(_t);
			tmp116_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp116_AST);
			ASTPair __currentAST218 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NOT_EQUAL);
			_t = _t.getFirstChild();
			filterAtom(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST218;
			_t = __t218;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case LT_:
		{
			AST __t219 = _t;
			AST tmp117_AST = null;
			AST tmp117_AST_in = null;
			tmp117_AST = astFactory.create(_t);
			tmp117_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp117_AST);
			ASTPair __currentAST219 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LT_);
			_t = _t.getFirstChild();
			filterAtom(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST219;
			_t = __t219;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case LE:
		{
			AST __t220 = _t;
			AST tmp118_AST = null;
			AST tmp118_AST_in = null;
			tmp118_AST = astFactory.create(_t);
			tmp118_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp118_AST);
			ASTPair __currentAST220 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,LE);
			_t = _t.getFirstChild();
			filterAtom(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST220;
			_t = __t220;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case GT:
		{
			AST __t221 = _t;
			AST tmp119_AST = null;
			AST tmp119_AST_in = null;
			tmp119_AST = astFactory.create(_t);
			tmp119_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp119_AST);
			ASTPair __currentAST221 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,GT);
			_t = _t.getFirstChild();
			filterAtom(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST221;
			_t = __t221;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case GE:
		{
			AST __t222 = _t;
			AST tmp120_AST = null;
			AST tmp120_AST_in = null;
			tmp120_AST = astFactory.create(_t);
			tmp120_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp120_AST);
			ASTPair __currentAST222 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,GE);
			_t = _t.getFirstChild();
			filterAtom(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			currentAST = __currentAST222;
			_t = __t222;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_RANGE:
		{
			AST __t223 = _t;
			AST tmp121_AST = null;
			AST tmp121_AST_in = null;
			tmp121_AST = astFactory.create(_t);
			tmp121_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp121_AST);
			ASTPair __currentAST223 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_FILTER_RANGE);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp122_AST = null;
					AST tmp122_AST_in = null;
					tmp122_AST = astFactory.create(_t);
					tmp122_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp122_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp123_AST = null;
					AST tmp123_AST_in = null;
					tmp123_AST = astFactory.create(_t);
					tmp123_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp123_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp124_AST = null;
					AST tmp124_AST_in = null;
					tmp124_AST = astFactory.create(_t);
					tmp124_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp124_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp125_AST = null;
					AST tmp125_AST_in = null;
					tmp125_AST = astFactory.create(_t);
					tmp125_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp125_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST223;
			_t = __t223;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_NOT_RANGE:
		{
			AST __t228 = _t;
			AST tmp126_AST = null;
			AST tmp126_AST_in = null;
			tmp126_AST = astFactory.create(_t);
			tmp126_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp126_AST);
			ASTPair __currentAST228 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_FILTER_NOT_RANGE);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp127_AST = null;
					AST tmp127_AST_in = null;
					tmp127_AST = astFactory.create(_t);
					tmp127_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp127_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp128_AST = null;
					AST tmp128_AST_in = null;
					tmp128_AST = astFactory.create(_t);
					tmp128_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp128_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp129_AST = null;
					AST tmp129_AST_in = null;
					tmp129_AST = astFactory.create(_t);
					tmp129_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp129_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp130_AST = null;
					AST tmp130_AST_in = null;
					tmp130_AST = astFactory.create(_t);
					tmp130_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp130_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST228;
			_t = __t228;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_IN:
		{
			AST __t233 = _t;
			AST tmp131_AST = null;
			AST tmp131_AST_in = null;
			tmp131_AST = astFactory.create(_t);
			tmp131_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp131_AST);
			ASTPair __currentAST233 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_FILTER_IN);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp132_AST = null;
					AST tmp132_AST_in = null;
					tmp132_AST = astFactory.create(_t);
					tmp132_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp132_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp133_AST = null;
					AST tmp133_AST_in = null;
					tmp133_AST = astFactory.create(_t);
					tmp133_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp133_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{    // ( ... )*
				for (;;)
				{
					if (null == _t)
						_t = ASTNULL;
					switch ( _t.Type )
					{
					case INT_TYPE:
					case LONG_TYPE:
					case FLOAT_TYPE:
					case DOUBLE_TYPE:
					case STRING_TYPE:
					case BOOL_TYPE:
					case NULL_TYPE:
					{
						constant(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
						break;
					}
					case EVENT_FILTER_IDENT:
					{
						filterIdentifier(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
						break;
					}
					default:
					{
						goto _loop237_breakloop;
					}
					 }
				}
_loop237_breakloop:				;
			}    // ( ... )*
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp134_AST = null;
					AST tmp134_AST_in = null;
					tmp134_AST = astFactory.create(_t);
					tmp134_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp134_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp135_AST = null;
					AST tmp135_AST_in = null;
					tmp135_AST = astFactory.create(_t);
					tmp135_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp135_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST233;
			_t = __t233;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_NOT_IN:
		{
			AST __t239 = _t;
			AST tmp136_AST = null;
			AST tmp136_AST_in = null;
			tmp136_AST = astFactory.create(_t);
			tmp136_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp136_AST);
			ASTPair __currentAST239 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_FILTER_NOT_IN);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case LPAREN:
				{
					AST tmp137_AST = null;
					AST tmp137_AST_in = null;
					tmp137_AST = astFactory.create(_t);
					tmp137_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp137_AST);
					match(_t,LPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case LBRACK:
				{
					AST tmp138_AST = null;
					AST tmp138_AST_in = null;
					tmp138_AST = astFactory.create(_t);
					tmp138_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp138_AST);
					match(_t,LBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{    // ( ... )*
				for (;;)
				{
					if (null == _t)
						_t = ASTNULL;
					switch ( _t.Type )
					{
					case INT_TYPE:
					case LONG_TYPE:
					case FLOAT_TYPE:
					case DOUBLE_TYPE:
					case STRING_TYPE:
					case BOOL_TYPE:
					case NULL_TYPE:
					{
						constant(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
						break;
					}
					case EVENT_FILTER_IDENT:
					{
						filterIdentifier(_t);
						_t = retTree_;
						astFactory.addASTChild(ref currentAST, returnAST);
						break;
					}
					default:
					{
						goto _loop243_breakloop;
					}
					 }
				}
_loop243_breakloop:				;
			}    // ( ... )*
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case RPAREN:
				{
					AST tmp139_AST = null;
					AST tmp139_AST_in = null;
					tmp139_AST = astFactory.create(_t);
					tmp139_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp139_AST);
					match(_t,RPAREN);
					_t = _t.getNextSibling();
					break;
				}
				case RBRACK:
				{
					AST tmp140_AST = null;
					AST tmp140_AST_in = null;
					tmp140_AST = astFactory.create(_t);
					tmp140_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp140_AST);
					match(_t,RBRACK);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST239;
			_t = __t239;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_BETWEEN:
		{
			AST __t245 = _t;
			AST tmp141_AST = null;
			AST tmp141_AST_in = null;
			tmp141_AST = astFactory.create(_t);
			tmp141_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp141_AST);
			ASTPair __currentAST245 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_FILTER_BETWEEN);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST245;
			_t = __t245;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_NOT_BETWEEN:
		{
			AST __t248 = _t;
			AST tmp142_AST = null;
			AST tmp142_AST_in = null;
			tmp142_AST = astFactory.create(_t);
			tmp142_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp142_AST);
			ASTPair __currentAST248 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_FILTER_NOT_BETWEEN);
			_t = _t.getFirstChild();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case INT_TYPE:
				case LONG_TYPE:
				case FLOAT_TYPE:
				case DOUBLE_TYPE:
				case STRING_TYPE:
				case BOOL_TYPE:
				case NULL_TYPE:
				{
					constant(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case EVENT_FILTER_IDENT:
				{
					filterIdentifier(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST248;
			_t = __t248;
			_t = _t.getNextSibling();
			filterParamComparator_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = filterParamComparator_AST;
		retTree_ = _t;
	}
	
	public void filterAtom(AST _t) //throws RecognitionException
{
		
		AST filterAtom_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterAtom_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case INT_TYPE:
		case LONG_TYPE:
		case FLOAT_TYPE:
		case DOUBLE_TYPE:
		case STRING_TYPE:
		case BOOL_TYPE:
		case NULL_TYPE:
		{
			constant(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			filterAtom_AST = currentAST.root;
			break;
		}
		case EVENT_FILTER_IDENT:
		{
			filterIdentifier(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			filterAtom_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = filterAtom_AST;
		retTree_ = _t;
	}
	
	public void filterIdentifier(AST _t) //throws RecognitionException
{
		
		AST filterIdentifier_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST filterIdentifier_AST = null;
		
		AST __t253 = _t;
		AST tmp143_AST = null;
		AST tmp143_AST_in = null;
		tmp143_AST = astFactory.create(_t);
		tmp143_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp143_AST);
		ASTPair __currentAST253 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,EVENT_FILTER_IDENT);
		_t = _t.getFirstChild();
		AST tmp144_AST = null;
		AST tmp144_AST_in = null;
		tmp144_AST = astFactory.create(_t);
		tmp144_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp144_AST);
		match(_t,IDENT);
		_t = _t.getNextSibling();
		eventPropertyExpr(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST253;
		_t = __t253;
		_t = _t.getNextSibling();
		filterIdentifier_AST = currentAST.root;
		returnAST = filterIdentifier_AST;
		retTree_ = _t;
	}
	
	public void eventPropertyAtomic(AST _t) //throws RecognitionException
{
		
		AST eventPropertyAtomic_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST eventPropertyAtomic_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case EVENT_PROP_SIMPLE:
		{
			AST __t259 = _t;
			AST tmp145_AST = null;
			AST tmp145_AST_in = null;
			tmp145_AST = astFactory.create(_t);
			tmp145_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp145_AST);
			ASTPair __currentAST259 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_PROP_SIMPLE);
			_t = _t.getFirstChild();
			AST tmp146_AST = null;
			AST tmp146_AST_in = null;
			tmp146_AST = astFactory.create(_t);
			tmp146_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp146_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			currentAST = __currentAST259;
			_t = __t259;
			_t = _t.getNextSibling();
			eventPropertyAtomic_AST = currentAST.root;
			break;
		}
		case EVENT_PROP_INDEXED:
		{
			AST __t260 = _t;
			AST tmp147_AST = null;
			AST tmp147_AST_in = null;
			tmp147_AST = astFactory.create(_t);
			tmp147_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp147_AST);
			ASTPair __currentAST260 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_PROP_INDEXED);
			_t = _t.getFirstChild();
			AST tmp148_AST = null;
			AST tmp148_AST_in = null;
			tmp148_AST = astFactory.create(_t);
			tmp148_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp148_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			AST tmp149_AST = null;
			AST tmp149_AST_in = null;
			tmp149_AST = astFactory.create(_t);
			tmp149_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp149_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			currentAST = __currentAST260;
			_t = __t260;
			_t = _t.getNextSibling();
			eventPropertyAtomic_AST = currentAST.root;
			break;
		}
		case EVENT_PROP_MAPPED:
		{
			AST __t261 = _t;
			AST tmp150_AST = null;
			AST tmp150_AST_in = null;
			tmp150_AST = astFactory.create(_t);
			tmp150_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp150_AST);
			ASTPair __currentAST261 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,EVENT_PROP_MAPPED);
			_t = _t.getFirstChild();
			AST tmp151_AST = null;
			AST tmp151_AST_in = null;
			tmp151_AST = astFactory.create(_t);
			tmp151_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp151_AST);
			match(_t,IDENT);
			_t = _t.getNextSibling();
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case STRING_LITERAL:
				{
					AST tmp152_AST = null;
					AST tmp152_AST_in = null;
					tmp152_AST = astFactory.create(_t);
					tmp152_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp152_AST);
					match(_t,STRING_LITERAL);
					_t = _t.getNextSibling();
					break;
				}
				case QUOTED_STRING_LITERAL:
				{
					AST tmp153_AST = null;
					AST tmp153_AST_in = null;
					tmp153_AST = astFactory.create(_t);
					tmp153_AST_in = _t;
					astFactory.addASTChild(ref currentAST, tmp153_AST);
					match(_t,QUOTED_STRING_LITERAL);
					_t = _t.getNextSibling();
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			currentAST = __currentAST261;
			_t = __t261;
			_t = _t.getNextSibling();
			eventPropertyAtomic_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = eventPropertyAtomic_AST;
		retTree_ = _t;
	}
	
	public void singleParameter(AST _t) //throws RecognitionException
{
		
		AST singleParameter_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST singleParameter_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case STAR:
		{
			AST tmp154_AST = null;
			AST tmp154_AST_in = null;
			tmp154_AST = astFactory.create(_t);
			tmp154_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp154_AST);
			match(_t,STAR);
			_t = _t.getNextSibling();
			singleParameter_AST = currentAST.root;
			break;
		}
		case INT_TYPE:
		case LONG_TYPE:
		case FLOAT_TYPE:
		case DOUBLE_TYPE:
		case STRING_TYPE:
		case BOOL_TYPE:
		case NULL_TYPE:
		{
			constant(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			singleParameter_AST = currentAST.root;
			break;
		}
		case NUMERIC_PARAM_RANGE:
		{
			AST __t271 = _t;
			AST tmp155_AST = null;
			AST tmp155_AST_in = null;
			tmp155_AST = astFactory.create(_t);
			tmp155_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp155_AST);
			ASTPair __currentAST271 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NUMERIC_PARAM_RANGE);
			_t = _t.getFirstChild();
			AST tmp156_AST = null;
			AST tmp156_AST_in = null;
			tmp156_AST = astFactory.create(_t);
			tmp156_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp156_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			AST tmp157_AST = null;
			AST tmp157_AST_in = null;
			tmp157_AST = astFactory.create(_t);
			tmp157_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp157_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			currentAST = __currentAST271;
			_t = __t271;
			_t = _t.getNextSibling();
			singleParameter_AST = currentAST.root;
			break;
		}
		case NUMERIC_PARAM_FREQUENCY:
		{
			AST __t272 = _t;
			AST tmp158_AST = null;
			AST tmp158_AST_in = null;
			tmp158_AST = astFactory.create(_t);
			tmp158_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp158_AST);
			ASTPair __currentAST272 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NUMERIC_PARAM_FREQUENCY);
			_t = _t.getFirstChild();
			AST tmp159_AST = null;
			AST tmp159_AST_in = null;
			tmp159_AST = astFactory.create(_t);
			tmp159_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp159_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			currentAST = __currentAST272;
			_t = __t272;
			_t = _t.getNextSibling();
			singleParameter_AST = currentAST.root;
			break;
		}
		case TIME_PERIOD:
		{
			time_period(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			singleParameter_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = singleParameter_AST;
		retTree_ = _t;
	}
	
	public void numericParameterList(AST _t) //throws RecognitionException
{
		
		AST numericParameterList_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST numericParameterList_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case NUM_INT:
		{
			AST tmp160_AST = null;
			AST tmp160_AST_in = null;
			tmp160_AST = astFactory.create(_t);
			tmp160_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp160_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			numericParameterList_AST = currentAST.root;
			break;
		}
		case NUMERIC_PARAM_RANGE:
		{
			AST __t274 = _t;
			AST tmp161_AST = null;
			AST tmp161_AST_in = null;
			tmp161_AST = astFactory.create(_t);
			tmp161_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp161_AST);
			ASTPair __currentAST274 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NUMERIC_PARAM_RANGE);
			_t = _t.getFirstChild();
			AST tmp162_AST = null;
			AST tmp162_AST_in = null;
			tmp162_AST = astFactory.create(_t);
			tmp162_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp162_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			AST tmp163_AST = null;
			AST tmp163_AST_in = null;
			tmp163_AST = astFactory.create(_t);
			tmp163_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp163_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			currentAST = __currentAST274;
			_t = __t274;
			_t = _t.getNextSibling();
			numericParameterList_AST = currentAST.root;
			break;
		}
		case NUMERIC_PARAM_FREQUENCE:
		{
			AST __t275 = _t;
			AST tmp164_AST = null;
			AST tmp164_AST_in = null;
			tmp164_AST = astFactory.create(_t);
			tmp164_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp164_AST);
			ASTPair __currentAST275 = currentAST.copy();
			currentAST.root = currentAST.child;
			currentAST.child = null;
			match(_t,NUMERIC_PARAM_FREQUENCE);
			_t = _t.getFirstChild();
			AST tmp165_AST = null;
			AST tmp165_AST_in = null;
			tmp165_AST = astFactory.create(_t);
			tmp165_AST_in = _t;
			astFactory.addASTChild(ref currentAST, tmp165_AST);
			match(_t,NUM_INT);
			_t = _t.getNextSibling();
			currentAST = __currentAST275;
			_t = __t275;
			_t = _t.getNextSibling();
			numericParameterList_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = numericParameterList_AST;
		retTree_ = _t;
	}
	
	public void timePeriodDef(AST _t) //throws RecognitionException
{
		
		AST timePeriodDef_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST timePeriodDef_AST = null;
		
		if (null == _t)
			_t = ASTNULL;
		switch ( _t.Type )
		{
		case DAY_PART:
		{
			dayPart(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case HOUR_PART:
				{
					hourPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				case MINUTE_PART:
				case SECOND_PART:
				case MILLISECOND_PART:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case MINUTE_PART:
				{
					minutePart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				case SECOND_PART:
				case MILLISECOND_PART:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case SECOND_PART:
				{
					secondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				case MILLISECOND_PART:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case MILLISECOND_PART:
				{
					millisecondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			timePeriodDef_AST = currentAST.root;
			break;
		}
		case HOUR_PART:
		{
			hourPart(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case MINUTE_PART:
				{
					minutePart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				case SECOND_PART:
				case MILLISECOND_PART:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case SECOND_PART:
				{
					secondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				case MILLISECOND_PART:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case MILLISECOND_PART:
				{
					millisecondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			timePeriodDef_AST = currentAST.root;
			break;
		}
		case MINUTE_PART:
		{
			minutePart(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case SECOND_PART:
				{
					secondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				case MILLISECOND_PART:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case MILLISECOND_PART:
				{
					millisecondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			timePeriodDef_AST = currentAST.root;
			break;
		}
		case SECOND_PART:
		{
			secondPart(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			{
				if (null == _t)
					_t = ASTNULL;
				switch ( _t.Type )
				{
				case MILLISECOND_PART:
				{
					millisecondPart(_t);
					_t = retTree_;
					astFactory.addASTChild(ref currentAST, returnAST);
					break;
				}
				case 3:
				{
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				 }
			}
			timePeriodDef_AST = currentAST.root;
			break;
		}
		case MILLISECOND_PART:
		{
			millisecondPart(_t);
			_t = retTree_;
			astFactory.addASTChild(ref currentAST, returnAST);
			timePeriodDef_AST = currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		 }
		returnAST = timePeriodDef_AST;
		retTree_ = _t;
	}
	
	public void dayPart(AST _t) //throws RecognitionException
{
		
		AST dayPart_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dayPart_AST = null;
		
		AST __t290 = _t;
		AST tmp166_AST = null;
		AST tmp166_AST_in = null;
		tmp166_AST = astFactory.create(_t);
		tmp166_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp166_AST);
		ASTPair __currentAST290 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,DAY_PART);
		_t = _t.getFirstChild();
		number(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST290;
		_t = __t290;
		_t = _t.getNextSibling();
		dayPart_AST = currentAST.root;
		returnAST = dayPart_AST;
		retTree_ = _t;
	}
	
	public void hourPart(AST _t) //throws RecognitionException
{
		
		AST hourPart_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hourPart_AST = null;
		
		AST __t292 = _t;
		AST tmp167_AST = null;
		AST tmp167_AST_in = null;
		tmp167_AST = astFactory.create(_t);
		tmp167_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp167_AST);
		ASTPair __currentAST292 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,HOUR_PART);
		_t = _t.getFirstChild();
		number(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST292;
		_t = __t292;
		_t = _t.getNextSibling();
		hourPart_AST = currentAST.root;
		returnAST = hourPart_AST;
		retTree_ = _t;
	}
	
	public void minutePart(AST _t) //throws RecognitionException
{
		
		AST minutePart_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST minutePart_AST = null;
		
		AST __t294 = _t;
		AST tmp168_AST = null;
		AST tmp168_AST_in = null;
		tmp168_AST = astFactory.create(_t);
		tmp168_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp168_AST);
		ASTPair __currentAST294 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,MINUTE_PART);
		_t = _t.getFirstChild();
		number(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST294;
		_t = __t294;
		_t = _t.getNextSibling();
		minutePart_AST = currentAST.root;
		returnAST = minutePart_AST;
		retTree_ = _t;
	}
	
	public void secondPart(AST _t) //throws RecognitionException
{
		
		AST secondPart_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST secondPart_AST = null;
		
		AST __t296 = _t;
		AST tmp169_AST = null;
		AST tmp169_AST_in = null;
		tmp169_AST = astFactory.create(_t);
		tmp169_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp169_AST);
		ASTPair __currentAST296 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,SECOND_PART);
		_t = _t.getFirstChild();
		number(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST296;
		_t = __t296;
		_t = _t.getNextSibling();
		secondPart_AST = currentAST.root;
		returnAST = secondPart_AST;
		retTree_ = _t;
	}
	
	public void millisecondPart(AST _t) //throws RecognitionException
{
		
		AST millisecondPart_AST_in = (AST)_t;
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST millisecondPart_AST = null;
		
		AST __t298 = _t;
		AST tmp170_AST = null;
		AST tmp170_AST_in = null;
		tmp170_AST = astFactory.create(_t);
		tmp170_AST_in = _t;
		astFactory.addASTChild(ref currentAST, tmp170_AST);
		ASTPair __currentAST298 = currentAST.copy();
		currentAST.root = currentAST.child;
		currentAST.child = null;
		match(_t,MILLISECOND_PART);
		_t = _t.getFirstChild();
		number(_t);
		_t = retTree_;
		astFactory.addASTChild(ref currentAST, returnAST);
		currentAST = __currentAST298;
		_t = __t298;
		_t = _t.getNextSibling();
		millisecondPart_AST = currentAST.root;
		returnAST = millisecondPart_AST;
		retTree_ = _t;
	}
	
	static public void initializeASTFactory( ASTFactory factory )
	{
		factory.setMaxNodeType(219);
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
		@"""FLOAT_SUFFIX""",
		@"""BOGUS""",
		@"""NUMERIC_PARAM_FREQUENCE"""
	};
	
	private static long[] mk_tokenSet_0_()
	{
		long[] data = { 1729382257027287280L, 1082201054210162688L, 244574766792574L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_0_ = new BitSet(mk_tokenSet_0_());
	private static long[] mk_tokenSet_1_()
	{
		long[] data = { -4611686018427387904L, 562949953421317L, 2147516160L, 0L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_1_ = new BitSet(mk_tokenSet_1_());
	private static long[] mk_tokenSet_2_()
	{
		long[] data = { 7680L, 49162L, 0L, 0L};
		return data;
	}
	public static readonly BitSet tokenSet_2_ = new BitSet(mk_tokenSet_2_());
}

}
