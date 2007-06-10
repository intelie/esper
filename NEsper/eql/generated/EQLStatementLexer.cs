// $ANTLR 2.7.7 (20060930): "eql.g" -> "EQLStatementLexer.cs"$


namespace net.esper.eql.generated
{
	// Generate header specific to lexer CSharp file
	using System;
	using Stream                          = System.IO.Stream;
	using TextReader                      = System.IO.TextReader;
	using Hashtable                       = System.Collections.Hashtable;
	using Comparer                        = System.Collections.Comparer;
	using CaseInsensitiveHashCodeProvider = System.Collections.CaseInsensitiveHashCodeProvider;
	using CaseInsensitiveComparer         = System.Collections.CaseInsensitiveComparer;
	
	using TokenStreamException            = antlr.TokenStreamException;
	using TokenStreamIOException          = antlr.TokenStreamIOException;
	using TokenStreamRecognitionException = antlr.TokenStreamRecognitionException;
	using CharStreamException             = antlr.CharStreamException;
	using CharStreamIOException           = antlr.CharStreamIOException;
	using ANTLRException                  = antlr.ANTLRException;
	using CharScanner                     = antlr.CharScanner;
	using InputBuffer                     = antlr.InputBuffer;
	using ByteBuffer                      = antlr.ByteBuffer;
	using CharBuffer                      = antlr.CharBuffer;
	using Token                           = antlr.Token;
	using IToken                          = antlr.IToken;
	using CommonToken                     = antlr.CommonToken;
	using SemanticException               = antlr.SemanticException;
	using RecognitionException            = antlr.RecognitionException;
	using NoViableAltForCharException     = antlr.NoViableAltForCharException;
	using MismatchedCharException         = antlr.MismatchedCharException;
	using TokenStream                     = antlr.TokenStream;
	using LexerSharedInputState           = antlr.LexerSharedInputState;
	using BitSet                          = antlr.collections.impl.BitSet;
	
	public 	class EQLStatementLexer : antlr.CharScanner	, TokenStream
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
		
		public EQLStatementLexer(Stream ins) : this(new ByteBuffer(ins))
		{
		}
		
		public EQLStatementLexer(TextReader r) : this(new CharBuffer(r))
		{
		}
		
		public EQLStatementLexer(InputBuffer ib)		 : this(new LexerSharedInputState(ib))
		{
		}
		
		public EQLStatementLexer(LexerSharedInputState state) : base(state)
		{
			initialize();
		}
		private void initialize()
		{
			caseSensitiveLiterals = false;
			setCaseSensitive(false);
			literals = new Hashtable(100, (float) 0.4, CaseInsensitiveHashCodeProvider.Default, CaseInsensitiveComparer.Default);
			literals.Add("between", 5);
			literals.Add("rstream", 55);
			literals.Add("case", 25);
			literals.Add("end", 30);
			literals.Add("insert", 50);
			literals.Add("distinct", 42);
			literals.Add("seconds", 46);
			literals.Add("where", 13);
			literals.Add("minutes", 47);
			literals.Add("then", 29);
			literals.Add("select", 24);
			literals.Add("regexp", 7);
			literals.Add("and", 10);
			literals.Add("outer", 32);
			literals.Add("not", 11);
			literals.Add("events", 45);
			literals.Add("from", 31);
			literals.Add("null", 151);
			literals.Add("count", 23);
			literals.Add("last", 49);
			literals.Add("like", 6);
			literals.Add("output", 44);
			literals.Add("when", 28);
			literals.Add("coalesce", 19);
			literals.Add("istream", 56);
			literals.Add("escape", 8);
			literals.Add("msec", 188);
			literals.Add("join", 33);
			literals.Add("is", 38);
			literals.Add("or", 9);
			literals.Add("avedev", 22);
			literals.Add("full", 36);
			literals.Add("min", 18);
			literals.Add("as", 14);
			literals.Add("first", 48);
			literals.Add("by", 39);
			literals.Add("millisecond", 187);
			literals.Add("days", 179);
			literals.Add("second", 184);
			literals.Add("all", 43);
			literals.Add("order", 52);
			literals.Add("hour", 182);
			literals.Add("hours", 181);
			literals.Add("pattern", 57);
			literals.Add("false", 150);
			literals.Add("exists", 61);
			literals.Add("milliseconds", 186);
			literals.Add("asc", 53);
			literals.Add("minute", 183);
			literals.Add("sec", 185);
			literals.Add("left", 34);
			literals.Add("day", 180);
			literals.Add("desc", 54);
			literals.Add("max", 17);
			literals.Add("prev", 59);
			literals.Add("sum", 15);
			literals.Add("on", 37);
			literals.Add("prior", 60);
			literals.Add("into", 51);
			literals.Add("else", 27);
			literals.Add("right", 35);
			literals.Add("in", 4);
			literals.Add("avg", 16);
			literals.Add("median", 20);
			literals.Add("every", 12);
			literals.Add("true", 149);
			literals.Add("stddev", 21);
			literals.Add("group", 40);
			literals.Add("having", 41);
			literals.Add("sql", 58);
		}
		
		override public IToken nextToken()			//throws TokenStreamException
		{
			IToken theRetToken = null;
tryAgain:
			for (;;)
			{
				IToken _token = null;
				int _ttype = Token.INVALID_TYPE;
				resetText();
				try     // for char stream error handling
				{
					try     // for lexical error handling
					{
						switch ( cached_LA1 )
						{
						case '?':
						{
							mQUESTION(true);
							theRetToken = returnToken_;
							break;
						}
						case '(':
						{
							mLPAREN(true);
							theRetToken = returnToken_;
							break;
						}
						case ')':
						{
							mRPAREN(true);
							theRetToken = returnToken_;
							break;
						}
						case '[':
						{
							mLBRACK(true);
							theRetToken = returnToken_;
							break;
						}
						case ']':
						{
							mRBRACK(true);
							theRetToken = returnToken_;
							break;
						}
						case '{':
						{
							mLCURLY(true);
							theRetToken = returnToken_;
							break;
						}
						case '}':
						{
							mRCURLY(true);
							theRetToken = returnToken_;
							break;
						}
						case ':':
						{
							mCOLON(true);
							theRetToken = returnToken_;
							break;
						}
						case ',':
						{
							mCOMMA(true);
							theRetToken = returnToken_;
							break;
						}
						case '~':
						{
							mBNOT(true);
							theRetToken = returnToken_;
							break;
						}
						case ';':
						{
							mSEMI(true);
							theRetToken = returnToken_;
							break;
						}
						case '\t':  case '\n':  case '\u000c':  case '\r':
						case ' ':
						{
							mWS(true);
							theRetToken = returnToken_;
							break;
						}
						case '"':
						{
							mSTRING_LITERAL(true);
							theRetToken = returnToken_;
							break;
						}
						case '\'':
						{
							mQUOTED_STRING_LITERAL(true);
							theRetToken = returnToken_;
							break;
						}
						case '$':  case '_':  case 'a':  case 'b':
						case 'c':  case 'd':  case 'e':  case 'f':
						case 'g':  case 'h':  case 'i':  case 'j':
						case 'k':  case 'l':  case 'm':  case 'n':
						case 'o':  case 'p':  case 'q':  case 'r':
						case 's':  case 't':  case 'u':  case 'v':
						case 'w':  case 'x':  case 'y':  case 'z':
						{
							mIDENT(true);
							theRetToken = returnToken_;
							break;
						}
						case '.':  case '0':  case '1':  case '2':
						case '3':  case '4':  case '5':  case '6':
						case '7':  case '8':  case '9':
						{
							mNUM_INT(true);
							theRetToken = returnToken_;
							break;
						}
						default:
							if ((cached_LA1=='>') && (cached_LA2=='>') && (LA(3)=='>') && (LA(4)=='='))
							{
								mBSR_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='>') && (cached_LA2=='>') && (LA(3)=='=')) {
								mSR_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='>') && (cached_LA2=='>') && (LA(3)=='>') && (true)) {
								mBSR(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='<') && (cached_LA2=='<') && (LA(3)=='=')) {
								mSL_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='-') && (cached_LA2=='>')) {
								mFOLLOWED_BY(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='<') && (cached_LA2=='>')) {
								mSQL_NE(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='=') && (cached_LA2=='=')) {
								mEQUAL(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='!') && (cached_LA2=='=')) {
								mNOT_EQUAL(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='/') && (cached_LA2=='=')) {
								mDIV_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='+') && (cached_LA2=='=')) {
								mPLUS_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='+') && (cached_LA2=='+')) {
								mINC(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='-') && (cached_LA2=='=')) {
								mMINUS_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='-') && (cached_LA2=='-')) {
								mDEC(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='*') && (cached_LA2=='=')) {
								mSTAR_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='%') && (cached_LA2=='=')) {
								mMOD_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='>') && (cached_LA2=='>') && (true)) {
								mSR(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='>') && (cached_LA2=='=')) {
								mGE(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='<') && (cached_LA2=='<') && (true)) {
								mSL(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='<') && (cached_LA2=='=')) {
								mLE(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='^') && (cached_LA2=='=')) {
								mBXOR_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='|') && (cached_LA2=='=')) {
								mBOR_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='|') && (cached_LA2=='|')) {
								mLOR(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='&') && (cached_LA2=='=')) {
								mBAND_ASSIGN(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='&') && (cached_LA2=='&')) {
								mLAND(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='/') && (cached_LA2=='/')) {
								mSL_COMMENT(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='/') && (cached_LA2=='*')) {
								mML_COMMENT(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='=') && (true)) {
								mEQUALS(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='!') && (true)) {
								mLNOT(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='/') && (true)) {
								mDIV(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='+') && (true)) {
								mPLUS(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='-') && (true)) {
								mMINUS(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='*') && (true)) {
								mSTAR(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='%') && (true)) {
								mMOD(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='>') && (true)) {
								mGT(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='<') && (true)) {
								mLT_(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='^') && (true)) {
								mBXOR(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='|') && (true)) {
								mBOR(true);
								theRetToken = returnToken_;
							}
							else if ((cached_LA1=='&') && (true)) {
								mBAND(true);
								theRetToken = returnToken_;
							}
						else
						{
							if (cached_LA1==EOF_CHAR) { uponEOF(); returnToken_ = makeToken(Token.EOF_TYPE); }
				else {throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());}
						}
						break; }
						if ( null==returnToken_ ) goto tryAgain; // found SKIP token
						_ttype = returnToken_.Type;
						returnToken_.Type = _ttype;
						return returnToken_;
					}
					catch (RecognitionException e) {
							throw new TokenStreamRecognitionException(e);
					}
				}
				catch (CharStreamException cse) {
					if ( cse is CharStreamIOException ) {
						throw new TokenStreamIOException(((CharStreamIOException)cse).io);
					}
					else {
						throw new TokenStreamException(cse.Message);
					}
				}
			}
		}
		
	public void mFOLLOWED_BY(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = FOLLOWED_BY;
		
		match("->");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mEQUALS(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = EQUALS;
		
		match('=');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSQL_NE(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SQL_NE;
		
		match("<>");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mQUESTION(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = QUESTION;
		
		match('?');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLPAREN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LPAREN;
		
		match('(');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mRPAREN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = RPAREN;
		
		match(')');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLBRACK(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LBRACK;
		
		match('[');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mRBRACK(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = RBRACK;
		
		match(']');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLCURLY(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LCURLY;
		
		match('{');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mRCURLY(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = RCURLY;
		
		match('}');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mCOLON(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = COLON;
		
		match(':');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mCOMMA(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = COMMA;
		
		match(',');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mEQUAL(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = EQUAL;
		
		match("==");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLNOT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LNOT;
		
		match('!');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBNOT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BNOT;
		
		match('~');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mNOT_EQUAL(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = NOT_EQUAL;
		
		match("!=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mDIV(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = DIV;
		
		match('/');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mDIV_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = DIV_ASSIGN;
		
		match("/=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mPLUS(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = PLUS;
		
		match('+');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mPLUS_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = PLUS_ASSIGN;
		
		match("+=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mINC(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = INC;
		
		match("++");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mMINUS(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = MINUS;
		
		match('-');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mMINUS_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = MINUS_ASSIGN;
		
		match("-=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mDEC(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = DEC;
		
		match("--");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSTAR(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = STAR;
		
		match('*');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSTAR_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = STAR_ASSIGN;
		
		match("*=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mMOD(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = MOD;
		
		match('%');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mMOD_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = MOD_ASSIGN;
		
		match("%=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSR(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SR;
		
		match(">>");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSR_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SR_ASSIGN;
		
		match(">>=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBSR(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BSR;
		
		match(">>>");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBSR_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BSR_ASSIGN;
		
		match(">>>=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mGE(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = GE;
		
		match(">=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mGT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = GT;
		
		match(">");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSL(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SL;
		
		match("<<");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSL_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SL_ASSIGN;
		
		match("<<=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLE(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LE;
		
		match("<=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLT_(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LT_;
		
		match('<');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBXOR(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BXOR;
		
		match('^');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBXOR_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BXOR_ASSIGN;
		
		match("^=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBOR(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BOR;
		
		match('|');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBOR_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BOR_ASSIGN;
		
		match("|=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLOR(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LOR;
		
		match("||");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBAND(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BAND;
		
		match('&');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mBAND_ASSIGN(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = BAND_ASSIGN;
		
		match("&=");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mLAND(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = LAND;
		
		match("&&");
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSEMI(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SEMI;
		
		match(';');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mWS(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = WS;
		
		{ // ( ... )+
			int _cnt292=0;
			for (;;)
			{
				switch ( cached_LA1 )
				{
				case ' ':
				{
					match(' ');
					break;
				}
				case '\t':
				{
					match('\t');
					break;
				}
				case '\u000c':
				{
					match('\f');
					break;
				}
				case '\n':  case '\r':
				{
					{
						if ((cached_LA1=='\r') && (cached_LA2=='\n') && (true) && (true))
						{
							match("\r\n");
						}
						else if ((cached_LA1=='\r') && (true) && (true) && (true)) {
							match('\r');
						}
						else if ((cached_LA1=='\n')) {
							match('\n');
						}
						else
						{
							throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
						}
						
					}
					if (0==inputState.guessing)
					{
						newline();
					}
					break;
				}
				default:
				{
					if (_cnt292 >= 1) { goto _loop292_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
				}
				break; }
				_cnt292++;
			}
_loop292_breakloop:			;
		}    // ( ... )+
		if (0==inputState.guessing)
		{
			_ttype = Token.SKIP;
		}
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSL_COMMENT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = SL_COMMENT;
		
		match("//");
		{    // ( ... )*
			for (;;)
			{
				if ((tokenSet_0_.member(cached_LA1)))
				{
					{
						match(tokenSet_0_);
					}
				}
				else
				{
					goto _loop296_breakloop;
				}
				
			}
_loop296_breakloop:			;
		}    // ( ... )*
		{
			switch ( cached_LA1 )
			{
			case '\n':
			{
				match('\n');
				break;
			}
			case '\r':
			{
				match('\r');
				{
					if ((cached_LA1=='\n'))
					{
						match('\n');
					}
					else {
					}
					
				}
				break;
			}
			default:
				{
				}
			break; }
		}
		if (0==inputState.guessing)
		{
			_ttype = Token.SKIP; newline();
		}
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mML_COMMENT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = ML_COMMENT;
		
		match("/*");
		{    // ( ... )*
			for (;;)
			{
				if ((cached_LA1=='\r') && (cached_LA2=='\n') && ((LA(3) >= '\u0003' && LA(3) <= '\u7ffe')) && ((LA(4) >= '\u0003' && LA(4) <= '\u7ffe')))
				{
					match('\r');
					match('\n');
					if (0==inputState.guessing)
					{
						newline();
					}
				}
				else if (((cached_LA1=='*') && ((cached_LA2 >= '\u0003' && cached_LA2 <= '\u7ffe')) && ((LA(3) >= '\u0003' && LA(3) <= '\u7ffe')))&&( LA(2)!='/' )) {
					match('*');
				}
				else if ((cached_LA1=='\r') && ((cached_LA2 >= '\u0003' && cached_LA2 <= '\u7ffe')) && ((LA(3) >= '\u0003' && LA(3) <= '\u7ffe')) && (true)) {
					match('\r');
					if (0==inputState.guessing)
					{
						newline();
					}
				}
				else if ((cached_LA1=='\n')) {
					match('\n');
					if (0==inputState.guessing)
					{
						newline();
					}
				}
				else if ((tokenSet_1_.member(cached_LA1))) {
					{
						match(tokenSet_1_);
					}
				}
				else
				{
					goto _loop302_breakloop;
				}
				
			}
_loop302_breakloop:			;
		}    // ( ... )*
		match("*/");
		if (0==inputState.guessing)
		{
			_ttype = Token.SKIP;
		}
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mSTRING_LITERAL(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = STRING_LITERAL;
		
		match('"');
		{    // ( ... )*
			for (;;)
			{
				if ((cached_LA1=='\\'))
				{
					mESC(false);
				}
				else if ((tokenSet_2_.member(cached_LA1))) {
					{
						match(tokenSet_2_);
					}
				}
				else
				{
					goto _loop306_breakloop;
				}
				
			}
_loop306_breakloop:			;
		}    // ( ... )*
		match('"');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	protected void mESC(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = ESC;
		
		match('\\');
		{
			switch ( cached_LA1 )
			{
			case 'n':
			{
				match('n');
				break;
			}
			case 'r':
			{
				match('r');
				break;
			}
			case 't':
			{
				match('t');
				break;
			}
			case 'b':
			{
				match('b');
				break;
			}
			case 'f':
			{
				match('f');
				break;
			}
			case '"':
			{
				match('"');
				break;
			}
			case '\'':
			{
				match('\'');
				break;
			}
			case '\\':
			{
				match('\\');
				break;
			}
			case 'u':
			{
				{ // ( ... )+
					int _cnt314=0;
					for (;;)
					{
						if ((cached_LA1=='u'))
						{
							match('u');
						}
						else
						{
							if (_cnt314 >= 1) { goto _loop314_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
						}
						
						_cnt314++;
					}
_loop314_breakloop:					;
				}    // ( ... )+
				mHEX_DIGIT(false);
				mHEX_DIGIT(false);
				mHEX_DIGIT(false);
				mHEX_DIGIT(false);
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			{
				matchRange('0','3');
				{
					if (((cached_LA1 >= '0' && cached_LA1 <= '7')) && (tokenSet_0_.member(cached_LA2)) && (true) && (true))
					{
						matchRange('0','7');
						{
							if (((cached_LA1 >= '0' && cached_LA1 <= '7')) && (tokenSet_0_.member(cached_LA2)) && (true) && (true))
							{
								matchRange('0','7');
							}
							else if ((tokenSet_0_.member(cached_LA1)) && (true) && (true) && (true)) {
							}
							else
							{
								throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
							}
							
						}
					}
					else if ((tokenSet_0_.member(cached_LA1)) && (true) && (true) && (true)) {
					}
					else
					{
						throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
					}
					
				}
				break;
			}
			case '4':  case '5':  case '6':  case '7':
			{
				matchRange('4','7');
				{
					if (((cached_LA1 >= '0' && cached_LA1 <= '7')) && (tokenSet_0_.member(cached_LA2)) && (true) && (true))
					{
						matchRange('0','7');
					}
					else if ((tokenSet_0_.member(cached_LA1)) && (true) && (true) && (true)) {
					}
					else
					{
						throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
					}
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
			}
			 }
		}
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mQUOTED_STRING_LITERAL(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = QUOTED_STRING_LITERAL;
		
		match('\'');
		{    // ( ... )*
			for (;;)
			{
				if ((cached_LA1=='\\'))
				{
					mESC(false);
				}
				else if ((tokenSet_3_.member(cached_LA1))) {
					{
						match(tokenSet_3_);
					}
				}
				else
				{
					goto _loop310_breakloop;
				}
				
			}
_loop310_breakloop:			;
		}    // ( ... )*
		match('\'');
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	protected void mHEX_DIGIT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = HEX_DIGIT;
		
		{
			switch ( cached_LA1 )
			{
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				matchRange('0','9');
				break;
			}
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':
			{
				matchRange('a','f');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
			}
			 }
		}
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mIDENT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = IDENT;
		
		{
			switch ( cached_LA1 )
			{
			case 'a':  case 'b':  case 'c':  case 'd':
			case 'e':  case 'f':  case 'g':  case 'h':
			case 'i':  case 'j':  case 'k':  case 'l':
			case 'm':  case 'n':  case 'o':  case 'p':
			case 'q':  case 'r':  case 's':  case 't':
			case 'u':  case 'v':  case 'w':  case 'x':
			case 'y':  case 'z':
			{
				matchRange('a','z');
				break;
			}
			case '_':
			{
				match('_');
				break;
			}
			case '$':
			{
				match('$');
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
			}
			 }
		}
		{    // ( ... )*
			for (;;)
			{
				switch ( cached_LA1 )
				{
				case 'a':  case 'b':  case 'c':  case 'd':
				case 'e':  case 'f':  case 'g':  case 'h':
				case 'i':  case 'j':  case 'k':  case 'l':
				case 'm':  case 'n':  case 'o':  case 'p':
				case 'q':  case 'r':  case 's':  case 't':
				case 'u':  case 'v':  case 'w':  case 'x':
				case 'y':  case 'z':
				{
					matchRange('a','z');
					break;
				}
				case '_':
				{
					match('_');
					break;
				}
				case '0':  case '1':  case '2':  case '3':
				case '4':  case '5':  case '6':  case '7':
				case '8':  case '9':
				{
					matchRange('0','9');
					break;
				}
				case '$':
				{
					match('$');
					break;
				}
				default:
				{
					goto _loop323_breakloop;
				}
				 }
			}
_loop323_breakloop:			;
		}    // ( ... )*
		_ttype = testLiteralsTable(_ttype);
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	public void mNUM_INT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = NUM_INT;
		IToken f1 = null;
		IToken f2 = null;
		IToken f3 = null;
		IToken f4 = null;
		bool isDecimal=false; Token t=null;
		
		switch ( cached_LA1 )
		{
		case '.':
		{
			match('.');
			if (0==inputState.guessing)
			{
				_ttype = DOT;
			}
			{
				if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
				{
					{ // ( ... )+
						int _cnt327=0;
						for (;;)
						{
							if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
							{
								matchRange('0','9');
							}
							else
							{
								if (_cnt327 >= 1) { goto _loop327_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
							}
							
							_cnt327++;
						}
_loop327_breakloop:						;
					}    // ( ... )+
					{
						if ((cached_LA1=='e'))
						{
							mEXPONENT(false);
						}
						else {
						}
						
					}
					{
						if ((cached_LA1=='d'||cached_LA1=='f'))
						{
							mFLOAT_SUFFIX(true);
							f1 = returnToken_;
							if (0==inputState.guessing)
							{
								t=f1;
							}
						}
						else {
						}
						
					}
					if (0==inputState.guessing)
					{
						
										if (t != null && t.getText().toUpperCase().indexOf('F')>=0) {
							_ttype = NUM_FLOAT;
										}
										else {
							_ttype = NUM_DOUBLE; // assume double
										}
										
					}
				}
				else {
				}
				
			}
			break;
		}
		case '0':  case '1':  case '2':  case '3':
		case '4':  case '5':  case '6':  case '7':
		case '8':  case '9':
		{
			{
				switch ( cached_LA1 )
				{
				case '0':
				{
					match('0');
					if (0==inputState.guessing)
					{
						isDecimal = true;
					}
					{
						if ((cached_LA1=='x'))
						{
							{
								match('x');
							}
							{ // ( ... )+
								int _cnt334=0;
								for (;;)
								{
									if ((cached_LA1=='0'||cached_LA1=='1'||cached_LA1=='2'||cached_LA1=='3'||cached_LA1=='4'||cached_LA1=='5'||cached_LA1=='6'||cached_LA1=='7'||cached_LA1=='8'||cached_LA1=='9'||cached_LA1=='a'||cached_LA1=='b'||cached_LA1=='c'||cached_LA1=='d'||cached_LA1=='e'||cached_LA1=='f') && (true) && (true) && (true))
									{
										mHEX_DIGIT(false);
									}
									else
									{
										if (_cnt334 >= 1) { goto _loop334_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
									}
									
									_cnt334++;
								}
_loop334_breakloop:								;
							}    // ( ... )+
						}
						else {
							bool synPredMatched339 = false;
							if ((((cached_LA1 >= '0' && cached_LA1 <= '9')) && (true) && (true) && (true)))
							{
								int _m339 = mark();
								synPredMatched339 = true;
								inputState.guessing++;
								try {
									{
										{ // ( ... )+
											int _cnt337=0;
											for (;;)
											{
												if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
												{
													matchRange('0','9');
												}
												else
												{
													if (_cnt337 >= 1) { goto _loop337_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
												}
												
												_cnt337++;
											}
_loop337_breakloop:											;
										}    // ( ... )+
										{
											switch ( cached_LA1 )
											{
											case '.':
											{
												match('.');
												break;
											}
											case 'e':
											{
												mEXPONENT(false);
												break;
											}
											case 'd':  case 'f':
											{
												mFLOAT_SUFFIX(false);
												break;
											}
											default:
											{
												throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
											}
											 }
										}
									}
								}
								catch (RecognitionException)
								{
									synPredMatched339 = false;
								}
								rewind(_m339);
								inputState.guessing--;
							}
							if ( synPredMatched339 )
							{
								{ // ( ... )+
									int _cnt341=0;
									for (;;)
									{
										if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
										{
											matchRange('0','9');
										}
										else
										{
											if (_cnt341 >= 1) { goto _loop341_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
										}
										
										_cnt341++;
									}
_loop341_breakloop:									;
								}    // ( ... )+
							}
							else if (((cached_LA1 >= '0' && cached_LA1 <= '7')) && (true) && (true) && (true)) {
								{ // ( ... )+
									int _cnt343=0;
									for (;;)
									{
										if (((cached_LA1 >= '0' && cached_LA1 <= '7')))
										{
											matchRange('0','7');
										}
										else
										{
											if (_cnt343 >= 1) { goto _loop343_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
										}
										
										_cnt343++;
									}
_loop343_breakloop:									;
								}    // ( ... )+
							}
							else {
							}
							}
						}
						break;
					}
					case '1':  case '2':  case '3':  case '4':
					case '5':  case '6':  case '7':  case '8':
					case '9':
					{
						{
							matchRange('1','9');
						}
						{    // ( ... )*
							for (;;)
							{
								if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
								{
									matchRange('0','9');
								}
								else
								{
									goto _loop346_breakloop;
								}
								
							}
_loop346_breakloop:							;
						}    // ( ... )*
						if (0==inputState.guessing)
						{
							isDecimal=true;
						}
						break;
					}
					default:
					{
						throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
					}
					 }
				}
				{
					if ((cached_LA1=='l'))
					{
						{
							match('l');
						}
						if (0==inputState.guessing)
						{
							_ttype = NUM_LONG;
						}
					}
					else if (((cached_LA1=='.'||cached_LA1=='d'||cached_LA1=='e'||cached_LA1=='f'))&&(isDecimal)) {
						{
							switch ( cached_LA1 )
							{
							case '.':
							{
								match('.');
								{    // ( ... )*
									for (;;)
									{
										if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
										{
											matchRange('0','9');
										}
										else
										{
											goto _loop351_breakloop;
										}
										
									}
_loop351_breakloop:									;
								}    // ( ... )*
								{
									if ((cached_LA1=='e'))
									{
										mEXPONENT(false);
									}
									else {
									}
									
								}
								{
									if ((cached_LA1=='d'||cached_LA1=='f'))
									{
										mFLOAT_SUFFIX(true);
										f2 = returnToken_;
										if (0==inputState.guessing)
										{
											t=f2;
										}
									}
									else {
									}
									
								}
								break;
							}
							case 'e':
							{
								mEXPONENT(false);
								{
									if ((cached_LA1=='d'||cached_LA1=='f'))
									{
										mFLOAT_SUFFIX(true);
										f3 = returnToken_;
										if (0==inputState.guessing)
										{
											t=f3;
										}
									}
									else {
									}
									
								}
								break;
							}
							case 'd':  case 'f':
							{
								mFLOAT_SUFFIX(true);
								f4 = returnToken_;
								if (0==inputState.guessing)
								{
									t=f4;
								}
								break;
							}
							default:
							{
								throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
							}
							 }
						}
						if (0==inputState.guessing)
						{
							
										if (t != null && t.getText().toUpperCase() .indexOf('F') >= 0) {
							_ttype = NUM_FLOAT;
										}
							else {
								           	_ttype = NUM_DOUBLE; // assume double
										}
										
						}
					}
					else {
					}
					
				}
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
			}
			 }
			if (_createToken && (null == _token) && (_ttype != Token.SKIP))
			{
				_token = makeToken(_ttype);
				_token.setText(text.ToString(_begin, text.Length-_begin));
			}
			returnToken_ = _token;
		}
		
	protected void mEXPONENT(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = EXPONENT;
		
		{
			match('e');
		}
		{
			switch ( cached_LA1 )
			{
			case '+':
			{
				match('+');
				break;
			}
			case '-':
			{
				match('-');
				break;
			}
			case '0':  case '1':  case '2':  case '3':
			case '4':  case '5':  case '6':  case '7':
			case '8':  case '9':
			{
				break;
			}
			default:
			{
				throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
			}
			 }
		}
		{ // ( ... )+
			int _cnt359=0;
			for (;;)
			{
				if (((cached_LA1 >= '0' && cached_LA1 <= '9')))
				{
					matchRange('0','9');
				}
				else
				{
					if (_cnt359 >= 1) { goto _loop359_breakloop; } else { throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());; }
				}
				
				_cnt359++;
			}
_loop359_breakloop:			;
		}    // ( ... )+
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	protected void mFLOAT_SUFFIX(bool _createToken) //throws RecognitionException, CharStreamException, TokenStreamException
{
		int _ttype; IToken _token=null; int _begin=text.Length;
		_ttype = FLOAT_SUFFIX;
		
		switch ( cached_LA1 )
		{
		case 'f':
		{
			match('f');
			break;
		}
		case 'd':
		{
			match('d');
			break;
		}
		default:
		{
			throw new NoViableAltForCharException(cached_LA1, getFilename(), getLine(), getColumn());
		}
		 }
		if (_createToken && (null == _token) && (_ttype != Token.SKIP))
		{
			_token = makeToken(_ttype);
			_token.setText(text.ToString(_begin, text.Length-_begin));
		}
		returnToken_ = _token;
	}
	
	
	private static long[] mk_tokenSet_0_()
	{
		long[] data = new long[1024];
		data[0]=-9224L;
		for (int i = 1; i<=510; i++) { data[i]=-1L; }
		data[511]=9223372036854775807L;
		for (int i = 512; i<=1023; i++) { data[i]=0L; }
		return data;
	}
	public static readonly BitSet tokenSet_0_ = new BitSet(mk_tokenSet_0_());
	private static long[] mk_tokenSet_1_()
	{
		long[] data = new long[1024];
		data[0]=-4398046520328L;
		for (int i = 1; i<=510; i++) { data[i]=-1L; }
		data[511]=9223372036854775807L;
		for (int i = 512; i<=1023; i++) { data[i]=0L; }
		return data;
	}
	public static readonly BitSet tokenSet_1_ = new BitSet(mk_tokenSet_1_());
	private static long[] mk_tokenSet_2_()
	{
		long[] data = new long[1024];
		data[0]=-17179878408L;
		data[1]=-268435457L;
		for (int i = 2; i<=510; i++) { data[i]=-1L; }
		data[511]=9223372036854775807L;
		for (int i = 512; i<=1023; i++) { data[i]=0L; }
		return data;
	}
	public static readonly BitSet tokenSet_2_ = new BitSet(mk_tokenSet_2_());
	private static long[] mk_tokenSet_3_()
	{
		long[] data = new long[1024];
		data[0]=-549755823112L;
		data[1]=-268435457L;
		for (int i = 2; i<=510; i++) { data[i]=-1L; }
		data[511]=9223372036854775807L;
		for (int i = 512; i<=1023; i++) { data[i]=0L; }
		return data;
	}
	public static readonly BitSet tokenSet_3_ = new BitSet(mk_tokenSet_3_());
	
}
}
