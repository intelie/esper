// $ANTLR 2.7.7 (20060930): "eql.g" -> "EQLStatementParser.cs"$


namespace net.esper.eql.generated
{
	public class EqlTokenTypes
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
		
	}
}
