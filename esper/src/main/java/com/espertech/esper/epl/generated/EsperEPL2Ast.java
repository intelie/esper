// $ANTLR 3.2 Sep 23, 2009 12:02:23 EsperEPL2Ast.g 2011-07-13 15:59:00

  package com.espertech.esper.epl.generated;
  import java.util.Stack;
  import org.apache.commons.logging.Log;
  import org.apache.commons.logging.LogFactory;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class EsperEPL2Ast extends TreeParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "SCHEMA", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PREVIOUSTAIL", "PREVIOUSCOUNT", "PREVIOUSWINDOW", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "TYPEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "INDEX", "TIMEPERIOD_YEAR", "TIMEPERIOD_YEARS", "TIMEPERIOD_MONTH", "TIMEPERIOD_MONTHS", "TIMEPERIOD_WEEK", "TIMEPERIOD_WEEKS", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "FOR", "WHILE", "USING", "MERGE", "MATCHED", "EXPRESSIONDECL", "NEWKW", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "FOLLOWED_BY_ITEM", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "EXPRCOL", "INDEXCOL", "CONCAT", "LIB_FUNCTION", "LIB_FUNC_CHAIN", "DOT_EXPR", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "YEAR_PART", "MONTH_PART", "WEEK_PART", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_INDEX_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_STREAM", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_UPDATE_EXPR", "ON_MERGE_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_COL_TYPE_LIST", "CREATE_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "WINDOW_AGGREG", "ACCESS_AGG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "CREATE_SCHEMA_EXPR", "CREATE_SCHEMA_EXPR_QUAL", "CREATE_SCHEMA_EXPR_VAR", "VARIANT_LIST", "MERGE_UNM", "MERGE_MAT", "MERGE_UPD", "MERGE_INS", "MERGE_DEL", "NEW_ITEM", "AGG_FILTER_EXPR", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "IDENT", "LCURLY", "RCURLY", "LPAREN", "RPAREN", "GOES", "COMMA", "EQUALS", "DOT", "LBRACK", "RBRACK", "STAR", "BOR", "PLUS", "QUESTION", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "NUM_INT", "FOLLOWED_BY", "FOLLOWMAX_BEGIN", "FOLLOWMAX_END", "ATCHAR", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=186;
    public static final int FLOAT_SUFFIX=360;
    public static final int STAR=303;
    public static final int DOT_EXPR=195;
    public static final int NUMERIC_PARAM_LIST=126;
    public static final int ISTREAM=60;
    public static final int MOD=321;
    public static final int OUTERJOIN_EXPR=168;
    public static final int LIB_FUNC_CHAIN=194;
    public static final int CREATE_COL_TYPE_LIST=248;
    public static final int MONTH_PART=200;
    public static final int MERGE_INS=267;
    public static final int BSR=343;
    public static final int LIB_FUNCTION=193;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=105;
    public static final int FULL_OUTERJOIN_EXPR=172;
    public static final int MATCHREC_PATTERN_CONCAT=282;
    public static final int INC=336;
    public static final int LNOT=332;
    public static final int RPAREN=296;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=308;
    public static final int BSR_ASSIGN=344;
    public static final int CAST_EXPR=227;
    public static final int MATCHES=116;
    public static final int USING=120;
    public static final int STREAM_EXPR=167;
    public static final int TIMEPERIOD_SECONDS=102;
    public static final int NOT_EQUAL=313;
    public static final int METADATASQL=68;
    public static final int EVENT_FILTER_PROPERTY_EXPR=135;
    public static final int LAST_AGGREG=255;
    public static final int GOES=297;
    public static final int REGEXP=9;
    public static final int MATCHED=122;
    public static final int FOLLOWED_BY_EXPR=129;
    public static final int NEWKW=124;
    public static final int FOLLOWED_BY=323;
    public static final int HOUR_PART=203;
    public static final int MATCH_UNTIL_RANGE_CLOSED=246;
    public static final int MATCHREC_PATTERN_NESTED=284;
    public static final int RBRACK=302;
    public static final int GE=317;
    public static final int METHOD_JOIN_EXPR=242;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=134;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=133;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=337;
    public static final int EVENT_FILTER_NOT_IN=145;
    public static final int INSERTINTO_STREAM_NAME=215;
    public static final int NUM_DOUBLE=278;
    public static final int TIMEPERIOD_MILLISEC=103;
    public static final int UNARY_MINUS=196;
    public static final int LCURLY=293;
    public static final int RETAINUNION=64;
    public static final int DBWHERE_CLAUSE=213;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=143;
    public static final int GROUP=44;
    public static final int WS=352;
    public static final int SUBSELECT_GROUP_EXPR=219;
    public static final int FOLLOWED_BY_ITEM=130;
    public static final int YEAR_PART=199;
    public static final int ON_SELECT_INSERT_EXPR=237;
    public static final int TYPEOF=78;
    public static final int ESCAPECHAR=327;
    public static final int EXPRCOL=190;
    public static final int SL_COMMENT=353;
    public static final int NULL_TYPE=277;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=244;
    public static final int GT=315;
    public static final int BNOT=333;
    public static final int WHERE_EXPR=154;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=169;
    public static final int LAND=350;
    public static final int NOT_REGEXP=210;
    public static final int MATCH_UNTIL_EXPR=243;
    public static final int EVENT_PROP_EXPR=176;
    public static final int LBRACK=301;
    public static final int VIEW_EXPR=151;
    public static final int MERGE_UPD=266;
    public static final int ANNOTATION=251;
    public static final int LONG_TYPE=272;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=136;
    public static final int MATCHREC_PATTERN=280;
    public static final int ON_MERGE_EXPR=236;
    public static final int ATCHAR=326;
    public static final int TIMEPERIOD_SEC=100;
    public static final int TICKED_STRING_LITERAL=328;
    public static final int ON_SELECT_EXPR=234;
    public static final int MINUTE_PART=204;
    public static final int PATTERN_NOT_EXPR=132;
    public static final int SQL_NE=312;
    public static final int SUM=18;
    public static final int HexDigit=358;
    public static final int UPDATE_EXPR=258;
    public static final int LPAREN=295;
    public static final int IN_SUBSELECT_EXPR=221;
    public static final int AT=86;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=106;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=286;
    public static final int NOT_IN_RANGE=217;
    public static final int TIMEPERIOD_MONTH=90;
    public static final int OFFSET=110;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int SECOND_PART=205;
    public static final int PREVIOUS=69;
    public static final int PREVIOUSWINDOW=72;
    public static final int MATCH_RECOGNIZE=112;
    public static final int IDENT=292;
    public static final int DATABASE_JOIN_EXPR=153;
    public static final int BXOR=311;
    public static final int PLUS=305;
    public static final int CASE2=29;
    public static final int MERGE_MAT=265;
    public static final int TIMEPERIOD_DAY=94;
    public static final int CREATE_SCHEMA_EXPR=260;
    public static final int EXISTS=74;
    public static final int EVENT_PROP_INDEXED=179;
    public static final int CREATE_INDEX_EXPR=228;
    public static final int TIMEPERIOD_MILLISECOND=104;
    public static final int ACCESS_AGG=257;
    public static final int EVAL_NOTEQUALS_EXPR=160;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=245;
    public static final int CREATE_VARIABLE_EXPR=241;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=287;
    public static final int BY=43;
    public static final int RIGHT_OUTERJOIN_EXPR=171;
    public static final int NUMBERSETSTAR=250;
    public static final int LAST_OPERATOR=224;
    public static final int PATTERN_FILTER_EXPR=131;
    public static final int MERGE=121;
    public static final int FOLLOWMAX_END=325;
    public static final int MERGE_UNM=264;
    public static final int EVAL_AND_EXPR=157;
    public static final int LEFT_OUTERJOIN_EXPR=170;
    public static final int EPL_EXPR=279;
    public static final int GROUP_BY_EXPR=173;
    public static final int SET=83;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=77;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=177;
    public static final int MINUS=319;
    public static final int SEMI=351;
    public static final int INDEXCOL=191;
    public static final int STAR_ASSIGN=339;
    public static final int PREVIOUSCOUNT=71;
    public static final int VARIANT_LIST=263;
    public static final int FIRST_AGGREG=254;
    public static final int COLON=307;
    public static final int EVAL_EQUALS_GROUP_EXPR=161;
    public static final int BAND_ASSIGN=349;
    public static final int PREVIOUSTAIL=70;
    public static final int SCHEMA=62;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=187;
    public static final int NOT_IN_SET=207;
    public static final int VALUE_NULL=108;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=180;
    public static final int SL=345;
    public static final int NOT_IN_SUBSELECT_EXPR=222;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=149;
    public static final int SR=341;
    public static final int RCURLY=294;
    public static final int PLUS_ASSIGN=335;
    public static final int EXISTS_SUBSELECT_EXPR=220;
    public static final int DAY_PART=202;
    public static final int EVENT_FILTER_IN=144;
    public static final int DIV=320;
    public static final int OBJECT_PARAM_ORDERED_EXPR=128;
    public static final int WEEK_PART=201;
    public static final int EXPRESSIONDECL=123;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=206;
    public static final int OctalEscape=357;
    public static final int ROW_LIMIT_EXPR=109;
    public static final int FIRST=52;
    public static final int PRIOR=73;
    public static final int SELECTION_EXPR=164;
    public static final int LW=76;
    public static final int CAST=79;
    public static final int LOR=318;
    public static final int WILDCARD_SELECT=214;
    public static final int LT=314;
    public static final int EXPONENT=359;
    public static final int PATTERN_INCL_EXPR=152;
    public static final int WHILE=119;
    public static final int ORDER_BY_EXPR=174;
    public static final int NEW_ITEM=269;
    public static final int BOOL_TYPE=276;
    public static final int ANNOTATION_ARRAY=252;
    public static final int MOD_ASSIGN=340;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=223;
    public static final int COUNT=26;
    public static final int EQUALS=299;
    public static final int RETAININTERSECTION=65;
    public static final int WINDOW_AGGREG=256;
    public static final int DIV_ASSIGN=334;
    public static final int SL_ASSIGN=346;
    public static final int TIMEPERIOD_WEEKS=93;
    public static final int PATTERN=66;
    public static final int SQL=67;
    public static final int FULL=40;
    public static final int WEEKDAY=75;
    public static final int MATCHREC_AFTER_SKIP=285;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=235;
    public static final int ARRAY_EXPR=198;
    public static final int CREATE_COL_TYPE=249;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=107;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=162;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=146;
    public static final int TIMEPERIOD_SECOND=101;
    public static final int COALESCE=22;
    public static final int FLOAT_TYPE=273;
    public static final int SUBSELECT_EXPR=218;
    public static final int ANNOTATION_VALUE=253;
    public static final int NUMERIC_PARAM_RANGE=125;
    public static final int CONCAT=192;
    public static final int CLASS_IDENT=148;
    public static final int MATCHREC_PATTERN_ALTER=283;
    public static final int ON_EXPR=231;
    public static final int CREATE_WINDOW_EXPR=229;
    public static final int PROPERTY_SELECTION_STREAM=138;
    public static final int ON_DELETE_EXPR=233;
    public static final int ON=41;
    public static final int NUM_LONG=329;
    public static final int TIME_PERIOD=197;
    public static final int DOUBLE_TYPE=274;
    public static final int DELETE=81;
    public static final int INT_TYPE=271;
    public static final int MATCHREC_PARTITION=291;
    public static final int EVAL_BITWISE_EXPR=156;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=175;
    public static final int TIMEPERIOD_HOURS=97;
    public static final int VARIABLE=84;
    public static final int SUBSTITUTION=226;
    public static final int UNTIL=85;
    public static final int STRING_TYPE=275;
    public static final int ON_SET_EXPR=240;
    public static final int MATCHREC_DEFINE_ITEM=288;
    public static final int NUM_INT=322;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=239;
    public static final int NUM_FLOAT=330;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=137;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=355;
    public static final int WEEKDAY_OPERATOR=225;
    public static final int WHERE=16;
    public static final int DEC=338;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=127;
    public static final int BXOR_ASSIGN=347;
    public static final int AFTER_LIMIT_EXPR=185;
    public static final int ORDER=56;
    public static final int SNAPSHOT=82;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=182;
    public static final int EVENT_FILTER_PARAM=141;
    public static final int IRSTREAM=61;
    public static final int UPDATE=111;
    public static final int MAX=20;
    public static final int FOR=118;
    public static final int ON_STREAM=232;
    public static final int DEFINE=114;
    public static final int TIMEPERIOD_YEARS=89;
    public static final int TIMEPERIOD_DAYS=95;
    public static final int EVENT_FILTER_RANGE=142;
    public static final int INDEX=87;
    public static final int ML_COMMENT=354;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=181;
    public static final int BOR_ASSIGN=348;
    public static final int COMMA=298;
    public static final int PARTITION=115;
    public static final int IS=42;
    public static final int WHEN_LIMIT_EXPR=188;
    public static final int TIMEPERIOD_LIMIT_EXPR=184;
    public static final int SOME=49;
    public static final int TIMEPERIOD_HOUR=96;
    public static final int ALL=47;
    public static final int MATCHREC_MEASURE_ITEM=290;
    public static final int BOR=304;
    public static final int EQUAL=331;
    public static final int CREATE_SCHEMA_EXPR_VAR=262;
    public static final int EVENT_FILTER_NOT_BETWEEN=147;
    public static final int IN_RANGE=216;
    public static final int DOT=300;
    public static final int CURRENT_TIMESTAMP=80;
    public static final int MATCHREC_MEASURES=289;
    public static final int TIMEPERIOD_WEEK=92;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=139;
    public static final int INSERTINTO_EXPR=189;
    public static final int HAVING_EXPR=155;
    public static final int UNIDIRECTIONAL=63;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=247;
    public static final int MERGE_DEL=268;
    public static final int EVAL_EQUALS_EXPR=159;
    public static final int TIMEPERIOD_MINUTES=99;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=209;
    public static final int EVENT_LIMIT_EXPR=183;
    public static final int TIMEPERIOD_MINUTE=98;
    public static final int NOT_BETWEEN=208;
    public static final int EVAL_OR_EXPR=158;
    public static final int ON_SELECT_INSERT_OUTPUT=238;
    public static final int AFTER=117;
    public static final int MEASURES=113;
    public static final int AGG_FILTER_EXPR=270;
    public static final int MATCHREC_PATTERN_ATOM=281;
    public static final int BAND=310;
    public static final int QUOTED_STRING_LITERAL=309;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=306;
    public static final int OBSERVER_EXPR=150;
    public static final int EVENT_FILTER_IDENT=140;
    public static final int CREATE_SCHEMA_EXPR_QUAL=261;
    public static final int EVENT_PROP_MAPPED=178;
    public static final int UnicodeEscape=356;
    public static final int TIMEPERIOD_YEAR=88;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=211;
    public static final int TIMEPERIOD_MONTHS=91;
    public static final int FOLLOWMAX_BEGIN=324;
    public static final int SELECTION_ELEMENT_EXPR=165;
    public static final int CREATE_WINDOW_SELECT_EXPR=230;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=259;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=166;
    public static final int SR_ASSIGN=342;
    public static final int DBFROM_CLAUSE=212;
    public static final int LE=316;
    public static final int EVAL_IDENT=163;

    // delegates
    // delegators


        public EsperEPL2Ast(TreeNodeStream input) {
            this(input, new RecognizerSharedState());
        }
        public EsperEPL2Ast(TreeNodeStream input, RecognizerSharedState state) {
            super(input, state);
             
        }
        

    public String[] getTokenNames() { return EsperEPL2Ast.tokenNames; }
    public String getGrammarFileName() { return "EsperEPL2Ast.g"; }


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



    // $ANTLR start "annotation"
    // EsperEPL2Ast.g:57:1: annotation[boolean isLeaveNode] : ^(a= ANNOTATION CLASS_IDENT ( elementValuePair )* ( elementValue )? ) ;
    public final void annotation(boolean isLeaveNode) throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:58:2: ( ^(a= ANNOTATION CLASS_IDENT ( elementValuePair )* ( elementValue )? ) )
            // EsperEPL2Ast.g:58:4: ^(a= ANNOTATION CLASS_IDENT ( elementValuePair )* ( elementValue )? )
            {
            a=(CommonTree)match(input,ANNOTATION,FOLLOW_ANNOTATION_in_annotation92); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_annotation94); 
            // EsperEPL2Ast.g:58:31: ( elementValuePair )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==ANNOTATION_VALUE) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // EsperEPL2Ast.g:58:31: elementValuePair
            	    {
            	    pushFollow(FOLLOW_elementValuePair_in_annotation96);
            	    elementValuePair();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // EsperEPL2Ast.g:58:49: ( elementValue )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==CLASS_IDENT||(LA2_0>=ANNOTATION && LA2_0<=ANNOTATION_ARRAY)||(LA2_0>=INT_TYPE && LA2_0<=NULL_TYPE)) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // EsperEPL2Ast.g:58:49: elementValue
                    {
                    pushFollow(FOLLOW_elementValue_in_annotation99);
                    elementValue();

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 
             if (isLeaveNode) leaveNode(a); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "annotation"


    // $ANTLR start "elementValuePair"
    // EsperEPL2Ast.g:61:1: elementValuePair : ^(a= ANNOTATION_VALUE IDENT elementValue ) ;
    public final void elementValuePair() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:62:2: ( ^(a= ANNOTATION_VALUE IDENT elementValue ) )
            // EsperEPL2Ast.g:62:4: ^(a= ANNOTATION_VALUE IDENT elementValue )
            {
            a=(CommonTree)match(input,ANNOTATION_VALUE,FOLLOW_ANNOTATION_VALUE_in_elementValuePair117); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_elementValuePair119); 
            pushFollow(FOLLOW_elementValue_in_elementValuePair121);
            elementValue();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "elementValuePair"


    // $ANTLR start "elementValue"
    // EsperEPL2Ast.g:65:1: elementValue : ( annotation[false] | ^( ANNOTATION_ARRAY ( elementValue )* ) | constant[false] | CLASS_IDENT );
    public final void elementValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:66:6: ( annotation[false] | ^( ANNOTATION_ARRAY ( elementValue )* ) | constant[false] | CLASS_IDENT )
            int alt4=4;
            switch ( input.LA(1) ) {
            case ANNOTATION:
                {
                alt4=1;
                }
                break;
            case ANNOTATION_ARRAY:
                {
                alt4=2;
                }
                break;
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt4=3;
                }
                break;
            case CLASS_IDENT:
                {
                alt4=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // EsperEPL2Ast.g:66:11: annotation[false]
                    {
                    pushFollow(FOLLOW_annotation_in_elementValue148);
                    annotation(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:67:5: ^( ANNOTATION_ARRAY ( elementValue )* )
                    {
                    match(input,ANNOTATION_ARRAY,FOLLOW_ANNOTATION_ARRAY_in_elementValue156); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:67:24: ( elementValue )*
                        loop3:
                        do {
                            int alt3=2;
                            int LA3_0 = input.LA(1);

                            if ( (LA3_0==CLASS_IDENT||(LA3_0>=ANNOTATION && LA3_0<=ANNOTATION_ARRAY)||(LA3_0>=INT_TYPE && LA3_0<=NULL_TYPE)) ) {
                                alt3=1;
                            }


                            switch (alt3) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:67:24: elementValue
                        	    {
                        	    pushFollow(FOLLOW_elementValue_in_elementValue158);
                        	    elementValue();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop3;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:68:8: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_elementValue169);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:69:8: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_elementValue179); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "elementValue"


    // $ANTLR start "expressionDecl"
    // EsperEPL2Ast.g:75:1: expressionDecl : ^(e= EXPRESSIONDECL IDENT valueExpr ( expressionLambdaDecl )? ) ;
    public final void expressionDecl() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:76:2: ( ^(e= EXPRESSIONDECL IDENT valueExpr ( expressionLambdaDecl )? ) )
            // EsperEPL2Ast.g:76:4: ^(e= EXPRESSIONDECL IDENT valueExpr ( expressionLambdaDecl )? )
            {
            e=(CommonTree)match(input,EXPRESSIONDECL,FOLLOW_EXPRESSIONDECL_in_expressionDecl204); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_expressionDecl206); 
            pushFollow(FOLLOW_valueExpr_in_expressionDecl208);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:76:39: ( expressionLambdaDecl )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==GOES) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // EsperEPL2Ast.g:76:39: expressionLambdaDecl
                    {
                    pushFollow(FOLLOW_expressionLambdaDecl_in_expressionDecl210);
                    expressionLambdaDecl();

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 
             leaveNode(e); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "expressionDecl"


    // $ANTLR start "expressionLambdaDecl"
    // EsperEPL2Ast.g:79:1: expressionLambdaDecl : ^( GOES ( IDENT | exprCol ) ) ;
    public final void expressionLambdaDecl() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:80:2: ( ^( GOES ( IDENT | exprCol ) ) )
            // EsperEPL2Ast.g:80:4: ^( GOES ( IDENT | exprCol ) )
            {
            match(input,GOES,FOLLOW_GOES_in_expressionLambdaDecl227); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:80:11: ( IDENT | exprCol )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==IDENT) ) {
                alt6=1;
            }
            else if ( (LA6_0==EXPRCOL) ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // EsperEPL2Ast.g:80:12: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_expressionLambdaDecl230); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:80:20: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_expressionLambdaDecl234);
                    exprCol();

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "expressionLambdaDecl"


    // $ANTLR start "startEPLExpressionRule"
    // EsperEPL2Ast.g:86:1: startEPLExpressionRule : ^( EPL_EXPR ( annotation[true] | expressionDecl )* eplExpressionRule ) ;
    public final void startEPLExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:87:2: ( ^( EPL_EXPR ( annotation[true] | expressionDecl )* eplExpressionRule ) )
            // EsperEPL2Ast.g:87:4: ^( EPL_EXPR ( annotation[true] | expressionDecl )* eplExpressionRule )
            {
            match(input,EPL_EXPR,FOLLOW_EPL_EXPR_in_startEPLExpressionRule251); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:87:15: ( annotation[true] | expressionDecl )*
            loop7:
            do {
                int alt7=3;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==ANNOTATION) ) {
                    alt7=1;
                }
                else if ( (LA7_0==EXPRESSIONDECL) ) {
                    alt7=2;
                }


                switch (alt7) {
            	case 1 :
            	    // EsperEPL2Ast.g:87:16: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startEPLExpressionRule254);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;
            	case 2 :
            	    // EsperEPL2Ast.g:87:35: expressionDecl
            	    {
            	    pushFollow(FOLLOW_expressionDecl_in_startEPLExpressionRule259);
            	    expressionDecl();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            pushFollow(FOLLOW_eplExpressionRule_in_startEPLExpressionRule263);
            eplExpressionRule();

            state._fsp--;


            match(input, Token.UP, null); 
             end(); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "startEPLExpressionRule"


    // $ANTLR start "eplExpressionRule"
    // EsperEPL2Ast.g:90:1: eplExpressionRule : ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ( forExpr )? ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:91:2: ( ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ( forExpr )? )
            // EsperEPL2Ast.g:91:4: ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ( forExpr )?
            {
            // EsperEPL2Ast.g:91:4: ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr )
            int alt8=7;
            switch ( input.LA(1) ) {
            case SELECTION_EXPR:
            case INSERTINTO_EXPR:
                {
                alt8=1;
                }
                break;
            case CREATE_WINDOW_EXPR:
                {
                alt8=2;
                }
                break;
            case CREATE_INDEX_EXPR:
                {
                alt8=3;
                }
                break;
            case CREATE_VARIABLE_EXPR:
                {
                alt8=4;
                }
                break;
            case CREATE_SCHEMA_EXPR:
                {
                alt8=5;
                }
                break;
            case ON_EXPR:
                {
                alt8=6;
                }
                break;
            case UPDATE_EXPR:
                {
                alt8=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }

            switch (alt8) {
                case 1 :
                    // EsperEPL2Ast.g:91:5: selectExpr
                    {
                    pushFollow(FOLLOW_selectExpr_in_eplExpressionRule280);
                    selectExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:91:18: createWindowExpr
                    {
                    pushFollow(FOLLOW_createWindowExpr_in_eplExpressionRule284);
                    createWindowExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:91:37: createIndexExpr
                    {
                    pushFollow(FOLLOW_createIndexExpr_in_eplExpressionRule288);
                    createIndexExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:91:55: createVariableExpr
                    {
                    pushFollow(FOLLOW_createVariableExpr_in_eplExpressionRule292);
                    createVariableExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:91:76: createSchemaExpr
                    {
                    pushFollow(FOLLOW_createSchemaExpr_in_eplExpressionRule296);
                    createSchemaExpr();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:91:95: onExpr
                    {
                    pushFollow(FOLLOW_onExpr_in_eplExpressionRule300);
                    onExpr();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:91:104: updateExpr
                    {
                    pushFollow(FOLLOW_updateExpr_in_eplExpressionRule304);
                    updateExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:91:116: ( forExpr )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==FOR) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // EsperEPL2Ast.g:91:116: forExpr
                    {
                    pushFollow(FOLLOW_forExpr_in_eplExpressionRule307);
                    forExpr();

                    state._fsp--;


                    }
                    break;

            }


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "eplExpressionRule"


    // $ANTLR start "onExpr"
    // EsperEPL2Ast.g:94:1: onExpr : ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:95:2: ( ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr ) ) )
            // EsperEPL2Ast.g:95:4: ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr326); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onStreamExpr_in_onExpr328);
            onStreamExpr();

            state._fsp--;

            // EsperEPL2Ast.g:96:3: ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr )
            int alt13=5;
            switch ( input.LA(1) ) {
            case ON_DELETE_EXPR:
                {
                alt13=1;
                }
                break;
            case ON_UPDATE_EXPR:
                {
                alt13=2;
                }
                break;
            case ON_SELECT_EXPR:
                {
                alt13=3;
                }
                break;
            case ON_SET_EXPR:
                {
                alt13=4;
                }
                break;
            case ON_MERGE_EXPR:
                {
                alt13=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:96:4: onDeleteExpr
                    {
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr333);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:96:19: onUpdateExpr
                    {
                    pushFollow(FOLLOW_onUpdateExpr_in_onExpr337);
                    onUpdateExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:96:34: onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr341);
                    onSelectExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:96:47: ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0==ON_SELECT_INSERT_EXPR) ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // EsperEPL2Ast.g:96:48: ( onSelectInsertExpr )+ ( onSelectInsertOutput )?
                            {
                            // EsperEPL2Ast.g:96:48: ( onSelectInsertExpr )+
                            int cnt10=0;
                            loop10:
                            do {
                                int alt10=2;
                                int LA10_0 = input.LA(1);

                                if ( (LA10_0==ON_SELECT_INSERT_EXPR) ) {
                                    alt10=1;
                                }


                                switch (alt10) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:96:48: onSelectInsertExpr
                            	    {
                            	    pushFollow(FOLLOW_onSelectInsertExpr_in_onExpr344);
                            	    onSelectInsertExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt10 >= 1 ) break loop10;
                                        EarlyExitException eee =
                                            new EarlyExitException(10, input);
                                        throw eee;
                                }
                                cnt10++;
                            } while (true);

                            // EsperEPL2Ast.g:96:68: ( onSelectInsertOutput )?
                            int alt11=2;
                            int LA11_0 = input.LA(1);

                            if ( (LA11_0==ON_SELECT_INSERT_OUTPUT) ) {
                                alt11=1;
                            }
                            switch (alt11) {
                                case 1 :
                                    // EsperEPL2Ast.g:96:68: onSelectInsertOutput
                                    {
                                    pushFollow(FOLLOW_onSelectInsertOutput_in_onExpr347);
                                    onSelectInsertOutput();

                                    state._fsp--;


                                    }
                                    break;

                            }


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:96:94: onSetExpr
                    {
                    pushFollow(FOLLOW_onSetExpr_in_onExpr354);
                    onSetExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:96:106: onMergeExpr
                    {
                    pushFollow(FOLLOW_onMergeExpr_in_onExpr358);
                    onMergeExpr();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(i); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onExpr"


    // $ANTLR start "onStreamExpr"
    // EsperEPL2Ast.g:100:1: onStreamExpr : ^(s= ON_STREAM ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ) ;
    public final void onStreamExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:101:2: ( ^(s= ON_STREAM ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ) )
            // EsperEPL2Ast.g:101:4: ^(s= ON_STREAM ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? )
            {
            s=(CommonTree)match(input,ON_STREAM,FOLLOW_ON_STREAM_in_onStreamExpr380); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:101:18: ( eventFilterExpr | patternInclusionExpression )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==EVENT_FILTER_EXPR) ) {
                alt14=1;
            }
            else if ( (LA14_0==PATTERN_INCL_EXPR) ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // EsperEPL2Ast.g:101:19: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_onStreamExpr383);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:101:37: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onStreamExpr387);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:101:65: ( IDENT )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==IDENT) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // EsperEPL2Ast.g:101:65: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onStreamExpr390); 

                    }
                    break;

            }

             leaveNode(s); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onStreamExpr"


    // $ANTLR start "onMergeExpr"
    // EsperEPL2Ast.g:104:1: onMergeExpr : ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeItem )+ ( whereClause[true] )? ) ;
    public final void onMergeExpr() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:105:2: ( ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeItem )+ ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:105:4: ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeItem )+ ( whereClause[true] )? )
            {
            m=(CommonTree)match(input,ON_MERGE_EXPR,FOLLOW_ON_MERGE_EXPR_in_onMergeExpr408); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onMergeExpr410); 
            // EsperEPL2Ast.g:105:28: ( IDENT )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==IDENT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:105:28: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onMergeExpr412); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:105:35: ( mergeItem )+
            int cnt17=0;
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=MERGE_UNM && LA17_0<=MERGE_MAT)) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // EsperEPL2Ast.g:105:35: mergeItem
            	    {
            	    pushFollow(FOLLOW_mergeItem_in_onMergeExpr415);
            	    mergeItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt17 >= 1 ) break loop17;
                        EarlyExitException eee =
                            new EarlyExitException(17, input);
                        throw eee;
                }
                cnt17++;
            } while (true);

            // EsperEPL2Ast.g:105:46: ( whereClause[true] )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==WHERE_EXPR) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:105:46: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onMergeExpr418);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onMergeExpr"


    // $ANTLR start "mergeItem"
    // EsperEPL2Ast.g:108:1: mergeItem : ( mergeMatched | mergeUnmatched ) ;
    public final void mergeItem() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:109:2: ( ( mergeMatched | mergeUnmatched ) )
            // EsperEPL2Ast.g:109:4: ( mergeMatched | mergeUnmatched )
            {
            // EsperEPL2Ast.g:109:4: ( mergeMatched | mergeUnmatched )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==MERGE_MAT) ) {
                alt19=1;
            }
            else if ( (LA19_0==MERGE_UNM) ) {
                alt19=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:109:5: mergeMatched
                    {
                    pushFollow(FOLLOW_mergeMatched_in_mergeItem434);
                    mergeMatched();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:109:20: mergeUnmatched
                    {
                    pushFollow(FOLLOW_mergeUnmatched_in_mergeItem438);
                    mergeUnmatched();

                    state._fsp--;


                    }
                    break;

            }


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "mergeItem"


    // $ANTLR start "mergeMatched"
    // EsperEPL2Ast.g:112:1: mergeMatched : ^(m= MERGE_MAT ( mergeMatchedItem )+ ( valueExpr )? ) ;
    public final void mergeMatched() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:113:2: ( ^(m= MERGE_MAT ( mergeMatchedItem )+ ( valueExpr )? ) )
            // EsperEPL2Ast.g:113:4: ^(m= MERGE_MAT ( mergeMatchedItem )+ ( valueExpr )? )
            {
            m=(CommonTree)match(input,MERGE_MAT,FOLLOW_MERGE_MAT_in_mergeMatched453); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:113:18: ( mergeMatchedItem )+
            int cnt20=0;
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>=MERGE_UPD && LA20_0<=MERGE_DEL)) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // EsperEPL2Ast.g:113:18: mergeMatchedItem
            	    {
            	    pushFollow(FOLLOW_mergeMatchedItem_in_mergeMatched455);
            	    mergeMatchedItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt20 >= 1 ) break loop20;
                        EarlyExitException eee =
                            new EarlyExitException(20, input);
                        throw eee;
                }
                cnt20++;
            } while (true);

            // EsperEPL2Ast.g:113:36: ( valueExpr )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( ((LA21_0>=IN_SET && LA21_0<=REGEXP)||LA21_0==NOT_EXPR||(LA21_0>=SUM && LA21_0<=AVG)||(LA21_0>=COALESCE && LA21_0<=COUNT)||(LA21_0>=CASE && LA21_0<=CASE2)||(LA21_0>=PREVIOUS && LA21_0<=EXISTS)||(LA21_0>=INSTANCEOF && LA21_0<=CURRENT_TIMESTAMP)||LA21_0==NEWKW||(LA21_0>=EVAL_AND_EXPR && LA21_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA21_0==EVENT_PROP_EXPR||LA21_0==CONCAT||(LA21_0>=LIB_FUNC_CHAIN && LA21_0<=DOT_EXPR)||LA21_0==ARRAY_EXPR||(LA21_0>=NOT_IN_SET && LA21_0<=NOT_REGEXP)||(LA21_0>=IN_RANGE && LA21_0<=SUBSELECT_EXPR)||(LA21_0>=EXISTS_SUBSELECT_EXPR && LA21_0<=NOT_IN_SUBSELECT_EXPR)||LA21_0==SUBSTITUTION||(LA21_0>=FIRST_AGGREG && LA21_0<=WINDOW_AGGREG)||(LA21_0>=INT_TYPE && LA21_0<=NULL_TYPE)||(LA21_0>=STAR && LA21_0<=PLUS)||(LA21_0>=BAND && LA21_0<=BXOR)||(LA21_0>=LT && LA21_0<=GE)||(LA21_0>=MINUS && LA21_0<=MOD)) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:113:36: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_mergeMatched458);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(m); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "mergeMatched"


    // $ANTLR start "mergeMatchedItem"
    // EsperEPL2Ast.g:116:1: mergeMatchedItem : ( ^(m= MERGE_UPD ( onSetAssignment )* ( whereClause[false] )? ) | ^(d= MERGE_DEL ( whereClause[false] )? INT_TYPE ) | mergeInsert );
    public final void mergeMatchedItem() throws RecognitionException {
        CommonTree m=null;
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:117:2: ( ^(m= MERGE_UPD ( onSetAssignment )* ( whereClause[false] )? ) | ^(d= MERGE_DEL ( whereClause[false] )? INT_TYPE ) | mergeInsert )
            int alt25=3;
            switch ( input.LA(1) ) {
            case MERGE_UPD:
                {
                alt25=1;
                }
                break;
            case MERGE_DEL:
                {
                alt25=2;
                }
                break;
            case MERGE_INS:
                {
                alt25=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }

            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:117:4: ^(m= MERGE_UPD ( onSetAssignment )* ( whereClause[false] )? )
                    {
                    m=(CommonTree)match(input,MERGE_UPD,FOLLOW_MERGE_UPD_in_mergeMatchedItem476); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:117:18: ( onSetAssignment )*
                        loop22:
                        do {
                            int alt22=2;
                            int LA22_0 = input.LA(1);

                            if ( (LA22_0==ON_SET_EXPR_ITEM) ) {
                                alt22=1;
                            }


                            switch (alt22) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:117:18: onSetAssignment
                        	    {
                        	    pushFollow(FOLLOW_onSetAssignment_in_mergeMatchedItem478);
                        	    onSetAssignment();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop22;
                            }
                        } while (true);

                        // EsperEPL2Ast.g:117:35: ( whereClause[false] )?
                        int alt23=2;
                        int LA23_0 = input.LA(1);

                        if ( (LA23_0==WHERE_EXPR) ) {
                            alt23=1;
                        }
                        switch (alt23) {
                            case 1 :
                                // EsperEPL2Ast.g:117:35: whereClause[false]
                                {
                                pushFollow(FOLLOW_whereClause_in_mergeMatchedItem481);
                                whereClause(false);

                                state._fsp--;


                                }
                                break;

                        }

                         leaveNode(m); 

                        match(input, Token.UP, null); 
                    }

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:118:4: ^(d= MERGE_DEL ( whereClause[false] )? INT_TYPE )
                    {
                    d=(CommonTree)match(input,MERGE_DEL,FOLLOW_MERGE_DEL_in_mergeMatchedItem494); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:118:18: ( whereClause[false] )?
                    int alt24=2;
                    int LA24_0 = input.LA(1);

                    if ( (LA24_0==WHERE_EXPR) ) {
                        alt24=1;
                    }
                    switch (alt24) {
                        case 1 :
                            // EsperEPL2Ast.g:118:18: whereClause[false]
                            {
                            pushFollow(FOLLOW_whereClause_in_mergeMatchedItem496);
                            whereClause(false);

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,INT_TYPE,FOLLOW_INT_TYPE_in_mergeMatchedItem500); 
                     leaveNode(d); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:119:4: mergeInsert
                    {
                    pushFollow(FOLLOW_mergeInsert_in_mergeMatchedItem508);
                    mergeInsert();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "mergeMatchedItem"


    // $ANTLR start "mergeUnmatched"
    // EsperEPL2Ast.g:122:1: mergeUnmatched : ^(m= MERGE_UNM ( mergeInsert )+ ( valueExpr )? ) ;
    public final void mergeUnmatched() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:123:2: ( ^(m= MERGE_UNM ( mergeInsert )+ ( valueExpr )? ) )
            // EsperEPL2Ast.g:123:4: ^(m= MERGE_UNM ( mergeInsert )+ ( valueExpr )? )
            {
            m=(CommonTree)match(input,MERGE_UNM,FOLLOW_MERGE_UNM_in_mergeUnmatched522); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:123:18: ( mergeInsert )+
            int cnt26=0;
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==MERGE_INS) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // EsperEPL2Ast.g:123:18: mergeInsert
            	    {
            	    pushFollow(FOLLOW_mergeInsert_in_mergeUnmatched524);
            	    mergeInsert();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt26 >= 1 ) break loop26;
                        EarlyExitException eee =
                            new EarlyExitException(26, input);
                        throw eee;
                }
                cnt26++;
            } while (true);

            // EsperEPL2Ast.g:123:31: ( valueExpr )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=IN_SET && LA27_0<=REGEXP)||LA27_0==NOT_EXPR||(LA27_0>=SUM && LA27_0<=AVG)||(LA27_0>=COALESCE && LA27_0<=COUNT)||(LA27_0>=CASE && LA27_0<=CASE2)||(LA27_0>=PREVIOUS && LA27_0<=EXISTS)||(LA27_0>=INSTANCEOF && LA27_0<=CURRENT_TIMESTAMP)||LA27_0==NEWKW||(LA27_0>=EVAL_AND_EXPR && LA27_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA27_0==EVENT_PROP_EXPR||LA27_0==CONCAT||(LA27_0>=LIB_FUNC_CHAIN && LA27_0<=DOT_EXPR)||LA27_0==ARRAY_EXPR||(LA27_0>=NOT_IN_SET && LA27_0<=NOT_REGEXP)||(LA27_0>=IN_RANGE && LA27_0<=SUBSELECT_EXPR)||(LA27_0>=EXISTS_SUBSELECT_EXPR && LA27_0<=NOT_IN_SUBSELECT_EXPR)||LA27_0==SUBSTITUTION||(LA27_0>=FIRST_AGGREG && LA27_0<=WINDOW_AGGREG)||(LA27_0>=INT_TYPE && LA27_0<=NULL_TYPE)||(LA27_0>=STAR && LA27_0<=PLUS)||(LA27_0>=BAND && LA27_0<=BXOR)||(LA27_0>=LT && LA27_0<=GE)||(LA27_0>=MINUS && LA27_0<=MOD)) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:123:31: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_mergeUnmatched527);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(m); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "mergeUnmatched"


    // $ANTLR start "mergeInsert"
    // EsperEPL2Ast.g:126:1: mergeInsert : ^(um= MERGE_INS selectionList ( CLASS_IDENT )? ( exprCol )? ( whereClause[false] )? ) ;
    public final void mergeInsert() throws RecognitionException {
        CommonTree um=null;

        try {
            // EsperEPL2Ast.g:127:2: ( ^(um= MERGE_INS selectionList ( CLASS_IDENT )? ( exprCol )? ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:127:4: ^(um= MERGE_INS selectionList ( CLASS_IDENT )? ( exprCol )? ( whereClause[false] )? )
            {
            um=(CommonTree)match(input,MERGE_INS,FOLLOW_MERGE_INS_in_mergeInsert546); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_selectionList_in_mergeInsert548);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:127:33: ( CLASS_IDENT )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==CLASS_IDENT) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:127:33: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_mergeInsert550); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:127:46: ( exprCol )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==EXPRCOL) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:127:46: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_mergeInsert553);
                    exprCol();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:127:55: ( whereClause[false] )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==WHERE_EXPR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:127:55: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_mergeInsert556);
                    whereClause(false);

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(um); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "mergeInsert"


    // $ANTLR start "updateExpr"
    // EsperEPL2Ast.g:130:1: updateExpr : ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void updateExpr() throws RecognitionException {
        CommonTree u=null;

        try {
            // EsperEPL2Ast.g:131:2: ( ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:131:4: ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? )
            {
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr576); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr578); 
            // EsperEPL2Ast.g:131:32: ( IDENT )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==IDENT) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:131:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr580); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:131:39: ( onSetAssignment )+
            int cnt32=0;
            loop32:
            do {
                int alt32=2;
                int LA32_0 = input.LA(1);

                if ( (LA32_0==ON_SET_EXPR_ITEM) ) {
                    alt32=1;
                }


                switch (alt32) {
            	case 1 :
            	    // EsperEPL2Ast.g:131:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr583);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt32 >= 1 ) break loop32;
                        EarlyExitException eee =
                            new EarlyExitException(32, input);
                        throw eee;
                }
                cnt32++;
            } while (true);

            // EsperEPL2Ast.g:131:56: ( whereClause[false] )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==WHERE_EXPR) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:131:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr586);
                    whereClause(false);

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(u); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "updateExpr"


    // $ANTLR start "onDeleteExpr"
    // EsperEPL2Ast.g:134:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:135:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:135:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr603); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr605);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:135:32: ( whereClause[true] )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==WHERE_EXPR) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:135:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr608);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onDeleteExpr"


    // $ANTLR start "onSelectExpr"
    // EsperEPL2Ast.g:138:1: onSelectExpr : ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:139:2: ( ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) )
            // EsperEPL2Ast.g:139:4: ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? )
            {
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr628); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:139:23: ( insertIntoExpr )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==INSERTINTO_EXPR) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // EsperEPL2Ast.g:139:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr630);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:39: ( DISTINCT )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==DISTINCT) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:139:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr633); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr636);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:139:63: ( onExprFrom )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==ON_EXPR_FROM) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // EsperEPL2Ast.g:139:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr638);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:75: ( whereClause[true] )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WHERE_EXPR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:139:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr641);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:94: ( groupByClause )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==GROUP_BY_EXPR) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // EsperEPL2Ast.g:139:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr645);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:109: ( havingClause )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==HAVING_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:139:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr648);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:123: ( orderByClause )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==ORDER_BY_EXPR) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:139:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr651);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:138: ( rowLimitClause )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==ROW_LIMIT_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:139:138: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr654);
                    rowLimitClause();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(s); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onSelectExpr"


    // $ANTLR start "onSelectInsertExpr"
    // EsperEPL2Ast.g:142:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:143:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:143:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr674); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr676);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr678);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:143:78: ( whereClause[true] )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==WHERE_EXPR) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:143:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr680);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onSelectInsertExpr"


    // $ANTLR start "onSelectInsertOutput"
    // EsperEPL2Ast.g:146:1: onSelectInsertOutput : ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) ;
    public final void onSelectInsertOutput() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:147:2: ( ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) )
            // EsperEPL2Ast.g:147:4: ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) )
            {
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput697); 

            match(input, Token.DOWN, null); 
            if ( input.LA(1)==ALL||input.LA(1)==FIRST ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onSelectInsertOutput"


    // $ANTLR start "onSetExpr"
    // EsperEPL2Ast.g:150:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:151:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:151:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr717); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr719);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:151:34: ( onSetAssignment )*
            loop44:
            do {
                int alt44=2;
                int LA44_0 = input.LA(1);

                if ( (LA44_0==ON_SET_EXPR_ITEM) ) {
                    alt44=1;
                }


                switch (alt44) {
            	case 1 :
            	    // EsperEPL2Ast.g:151:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr722);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop44;
                }
            } while (true);

            // EsperEPL2Ast.g:151:53: ( whereClause[false] )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==WHERE_EXPR) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:151:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr726);
                    whereClause(false);

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onSetExpr"


    // $ANTLR start "onUpdateExpr"
    // EsperEPL2Ast.g:154:1: onUpdateExpr : ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void onUpdateExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:155:2: ( ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:155:4: ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? )
            {
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr741); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr743);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:155:32: ( onSetAssignment )+
            int cnt46=0;
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==ON_SET_EXPR_ITEM) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // EsperEPL2Ast.g:155:32: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr745);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt46 >= 1 ) break loop46;
                        EarlyExitException eee =
                            new EarlyExitException(46, input);
                        throw eee;
                }
                cnt46++;
            } while (true);

            // EsperEPL2Ast.g:155:49: ( whereClause[false] )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==WHERE_EXPR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:155:49: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr748);
                    whereClause(false);

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onUpdateExpr"


    // $ANTLR start "onSetAssignment"
    // EsperEPL2Ast.g:158:1: onSetAssignment : ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:159:2: ( ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) )
            // EsperEPL2Ast.g:159:4: ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr )
            {
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment763); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_onSetAssignment765);
            eventPropertyExpr(false);

            state._fsp--;

            pushFollow(FOLLOW_valueExpr_in_onSetAssignment768);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onSetAssignment"


    // $ANTLR start "onExprFrom"
    // EsperEPL2Ast.g:162:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:163:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:163:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom782); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom784); 
            // EsperEPL2Ast.g:163:25: ( IDENT )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==IDENT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:163:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom787); 

                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "onExprFrom"


    // $ANTLR start "createWindowExpr"
    // EsperEPL2Ast.g:166:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:167:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:167:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr805); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr807); 
            // EsperEPL2Ast.g:167:33: ( viewListExpr )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==VIEW_EXPR) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:167:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr810);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:167:49: ( RETAINUNION )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==RETAINUNION) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // EsperEPL2Ast.g:167:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr814); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:167:62: ( RETAININTERSECTION )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==RETAININTERSECTION) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:167:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr817); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:168:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==CLASS_IDENT||LA53_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt53=1;
            }
            else if ( (LA53_0==CREATE_COL_TYPE_LIST) ) {
                alt53=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 53, 0, input);

                throw nvae;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:169:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:169:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:169:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:169:6: ( createSelectionList )?
                    int alt52=2;
                    int LA52_0 = input.LA(1);

                    if ( (LA52_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt52=1;
                    }
                    switch (alt52) {
                        case 1 :
                            // EsperEPL2Ast.g:169:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr831);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr834); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:171:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:171:12: ( createColTypeList )
                    // EsperEPL2Ast.g:171:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr863);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:173:4: ( createWindowExprInsert )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==INSERT) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // EsperEPL2Ast.g:173:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr874);
                    createWindowExprInsert();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(i); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createWindowExpr"


    // $ANTLR start "createIndexExpr"
    // EsperEPL2Ast.g:177:1: createIndexExpr : ^(i= CREATE_INDEX_EXPR IDENT IDENT indexColList ) ;
    public final void createIndexExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:178:2: ( ^(i= CREATE_INDEX_EXPR IDENT IDENT indexColList ) )
            // EsperEPL2Ast.g:178:4: ^(i= CREATE_INDEX_EXPR IDENT IDENT indexColList )
            {
            i=(CommonTree)match(input,CREATE_INDEX_EXPR,FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr894); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr896); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr898); 
            pushFollow(FOLLOW_indexColList_in_createIndexExpr900);
            indexColList();

            state._fsp--;

             leaveNode(i); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createIndexExpr"


    // $ANTLR start "indexColList"
    // EsperEPL2Ast.g:181:1: indexColList : ^( INDEXCOL ( indexCol )+ ) ;
    public final void indexColList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:182:2: ( ^( INDEXCOL ( indexCol )+ ) )
            // EsperEPL2Ast.g:182:4: ^( INDEXCOL ( indexCol )+ )
            {
            match(input,INDEXCOL,FOLLOW_INDEXCOL_in_indexColList915); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:182:15: ( indexCol )+
            int cnt55=0;
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==INDEXCOL) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // EsperEPL2Ast.g:182:15: indexCol
            	    {
            	    pushFollow(FOLLOW_indexCol_in_indexColList917);
            	    indexCol();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt55 >= 1 ) break loop55;
                        EarlyExitException eee =
                            new EarlyExitException(55, input);
                        throw eee;
                }
                cnt55++;
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "indexColList"


    // $ANTLR start "indexCol"
    // EsperEPL2Ast.g:185:1: indexCol : ^( INDEXCOL IDENT ( IDENT )? ) ;
    public final void indexCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:186:2: ( ^( INDEXCOL IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:186:4: ^( INDEXCOL IDENT ( IDENT )? )
            {
            match(input,INDEXCOL,FOLLOW_INDEXCOL_in_indexCol932); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_indexCol934); 
            // EsperEPL2Ast.g:186:21: ( IDENT )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==IDENT) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:186:21: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_indexCol936); 

                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "indexCol"


    // $ANTLR start "createWindowExprInsert"
    // EsperEPL2Ast.g:189:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:190:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:190:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert950); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:190:13: ( valueExpr )?
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( ((LA57_0>=IN_SET && LA57_0<=REGEXP)||LA57_0==NOT_EXPR||(LA57_0>=SUM && LA57_0<=AVG)||(LA57_0>=COALESCE && LA57_0<=COUNT)||(LA57_0>=CASE && LA57_0<=CASE2)||(LA57_0>=PREVIOUS && LA57_0<=EXISTS)||(LA57_0>=INSTANCEOF && LA57_0<=CURRENT_TIMESTAMP)||LA57_0==NEWKW||(LA57_0>=EVAL_AND_EXPR && LA57_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA57_0==EVENT_PROP_EXPR||LA57_0==CONCAT||(LA57_0>=LIB_FUNC_CHAIN && LA57_0<=DOT_EXPR)||LA57_0==ARRAY_EXPR||(LA57_0>=NOT_IN_SET && LA57_0<=NOT_REGEXP)||(LA57_0>=IN_RANGE && LA57_0<=SUBSELECT_EXPR)||(LA57_0>=EXISTS_SUBSELECT_EXPR && LA57_0<=NOT_IN_SUBSELECT_EXPR)||LA57_0==SUBSTITUTION||(LA57_0>=FIRST_AGGREG && LA57_0<=WINDOW_AGGREG)||(LA57_0>=INT_TYPE && LA57_0<=NULL_TYPE)||(LA57_0>=STAR && LA57_0<=PLUS)||(LA57_0>=BAND && LA57_0<=BXOR)||(LA57_0>=LT && LA57_0<=GE)||(LA57_0>=MINUS && LA57_0<=MOD)) ) {
                    alt57=1;
                }
                switch (alt57) {
                    case 1 :
                        // EsperEPL2Ast.g:190:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert952);
                        valueExpr();

                        state._fsp--;


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createWindowExprInsert"


    // $ANTLR start "createSelectionList"
    // EsperEPL2Ast.g:193:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:194:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:194:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList969); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList971);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:194:61: ( createSelectionListElement )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==SELECTION_ELEMENT_EXPR||LA58_0==WILDCARD_SELECT) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // EsperEPL2Ast.g:194:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList974);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop58;
                }
            } while (true);

             leaveNode(s); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createSelectionList"


    // $ANTLR start "createColTypeList"
    // EsperEPL2Ast.g:197:1: createColTypeList : ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:198:2: ( ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:198:4: ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_COL_TYPE_LIST,FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList993); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList995);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:198:52: ( createColTypeListElement )*
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==CREATE_COL_TYPE) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // EsperEPL2Ast.g:198:53: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList998);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop59;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createColTypeList"


    // $ANTLR start "createColTypeListElement"
    // EsperEPL2Ast.g:201:1: createColTypeListElement : ^( CREATE_COL_TYPE CLASS_IDENT CLASS_IDENT ( LBRACK )? ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:202:2: ( ^( CREATE_COL_TYPE CLASS_IDENT CLASS_IDENT ( LBRACK )? ) )
            // EsperEPL2Ast.g:202:4: ^( CREATE_COL_TYPE CLASS_IDENT CLASS_IDENT ( LBRACK )? )
            {
            match(input,CREATE_COL_TYPE,FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement1013); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement1015); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement1017); 
            // EsperEPL2Ast.g:202:46: ( LBRACK )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==LBRACK) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:202:46: LBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_createColTypeListElement1019); 

                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createColTypeListElement"


    // $ANTLR start "createSelectionListElement"
    // EsperEPL2Ast.g:205:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:206:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==WILDCARD_SELECT) ) {
                alt63=1;
            }
            else if ( (LA63_0==SELECTION_ELEMENT_EXPR) ) {
                alt63=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:206:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement1034); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:207:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement1044); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:207:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==EVENT_PROP_EXPR) ) {
                        alt62=1;
                    }
                    else if ( ((LA62_0>=INT_TYPE && LA62_0<=NULL_TYPE)) ) {
                        alt62=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 62, 0, input);

                        throw nvae;
                    }
                    switch (alt62) {
                        case 1 :
                            // EsperEPL2Ast.g:208:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:208:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:208:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement1064);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:208:41: ( IDENT )?
                            int alt61=2;
                            int LA61_0 = input.LA(1);

                            if ( (LA61_0==IDENT) ) {
                                alt61=1;
                            }
                            switch (alt61) {
                                case 1 :
                                    // EsperEPL2Ast.g:208:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement1068); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:209:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:209:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:209:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement1090);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement1093); 

                            }


                            }
                            break;

                    }

                     leaveNode(s); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createSelectionListElement"


    // $ANTLR start "createVariableExpr"
    // EsperEPL2Ast.g:213:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:214:2: ( ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:214:4: ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr1129); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createVariableExpr1131); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr1133); 
            // EsperEPL2Ast.g:214:47: ( valueExpr )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=IN_SET && LA64_0<=REGEXP)||LA64_0==NOT_EXPR||(LA64_0>=SUM && LA64_0<=AVG)||(LA64_0>=COALESCE && LA64_0<=COUNT)||(LA64_0>=CASE && LA64_0<=CASE2)||(LA64_0>=PREVIOUS && LA64_0<=EXISTS)||(LA64_0>=INSTANCEOF && LA64_0<=CURRENT_TIMESTAMP)||LA64_0==NEWKW||(LA64_0>=EVAL_AND_EXPR && LA64_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA64_0==EVENT_PROP_EXPR||LA64_0==CONCAT||(LA64_0>=LIB_FUNC_CHAIN && LA64_0<=DOT_EXPR)||LA64_0==ARRAY_EXPR||(LA64_0>=NOT_IN_SET && LA64_0<=NOT_REGEXP)||(LA64_0>=IN_RANGE && LA64_0<=SUBSELECT_EXPR)||(LA64_0>=EXISTS_SUBSELECT_EXPR && LA64_0<=NOT_IN_SUBSELECT_EXPR)||LA64_0==SUBSTITUTION||(LA64_0>=FIRST_AGGREG && LA64_0<=WINDOW_AGGREG)||(LA64_0>=INT_TYPE && LA64_0<=NULL_TYPE)||(LA64_0>=STAR && LA64_0<=PLUS)||(LA64_0>=BAND && LA64_0<=BXOR)||(LA64_0>=LT && LA64_0<=GE)||(LA64_0>=MINUS && LA64_0<=MOD)) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:214:48: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr1136);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(i); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createVariableExpr"


    // $ANTLR start "createSchemaExpr"
    // EsperEPL2Ast.g:217:1: createSchemaExpr : ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_VAR IDENT ) )? ( createSchemaQual )* ) ;
    public final void createSchemaExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:218:2: ( ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_VAR IDENT ) )? ( createSchemaQual )* ) )
            // EsperEPL2Ast.g:218:4: ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_VAR IDENT ) )? ( createSchemaQual )* )
            {
            s=(CommonTree)match(input,CREATE_SCHEMA_EXPR,FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr1156); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1158); 
            // EsperEPL2Ast.g:218:33: ( variantList | ( createColTypeList )? )
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==VARIANT_LIST) ) {
                alt66=1;
            }
            else if ( (LA66_0==UP||LA66_0==CREATE_COL_TYPE_LIST||(LA66_0>=CREATE_SCHEMA_EXPR_QUAL && LA66_0<=CREATE_SCHEMA_EXPR_VAR)) ) {
                alt66=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:218:34: variantList
                    {
                    pushFollow(FOLLOW_variantList_in_createSchemaExpr1161);
                    variantList();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:218:46: ( createColTypeList )?
                    {
                    // EsperEPL2Ast.g:218:46: ( createColTypeList )?
                    int alt65=2;
                    int LA65_0 = input.LA(1);

                    if ( (LA65_0==CREATE_COL_TYPE_LIST) ) {
                        alt65=1;
                    }
                    switch (alt65) {
                        case 1 :
                            // EsperEPL2Ast.g:218:46: createColTypeList
                            {
                            pushFollow(FOLLOW_createColTypeList_in_createSchemaExpr1163);
                            createColTypeList();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:218:66: ( ^( CREATE_SCHEMA_EXPR_VAR IDENT ) )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==CREATE_SCHEMA_EXPR_VAR) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:218:67: ^( CREATE_SCHEMA_EXPR_VAR IDENT )
                    {
                    match(input,CREATE_SCHEMA_EXPR_VAR,FOLLOW_CREATE_SCHEMA_EXPR_VAR_in_createSchemaExpr1169); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1171); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:218:101: ( createSchemaQual )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==CREATE_SCHEMA_EXPR_QUAL) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // EsperEPL2Ast.g:218:101: createSchemaQual
            	    {
            	    pushFollow(FOLLOW_createSchemaQual_in_createSchemaExpr1176);
            	    createSchemaQual();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop68;
                }
            } while (true);

             leaveNode(s); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createSchemaExpr"


    // $ANTLR start "createSchemaQual"
    // EsperEPL2Ast.g:221:1: createSchemaQual : ^( CREATE_SCHEMA_EXPR_QUAL IDENT exprCol ) ;
    public final void createSchemaQual() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:222:2: ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT exprCol ) )
            // EsperEPL2Ast.g:222:4: ^( CREATE_SCHEMA_EXPR_QUAL IDENT exprCol )
            {
            match(input,CREATE_SCHEMA_EXPR_QUAL,FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaQual1194); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaQual1196); 
            pushFollow(FOLLOW_exprCol_in_createSchemaQual1198);
            exprCol();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "createSchemaQual"


    // $ANTLR start "variantList"
    // EsperEPL2Ast.g:225:1: variantList : ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) ;
    public final void variantList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:226:2: ( ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) )
            // EsperEPL2Ast.g:226:4: ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ )
            {
            match(input,VARIANT_LIST,FOLLOW_VARIANT_LIST_in_variantList1214); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:226:19: ( STAR | CLASS_IDENT )+
            int cnt69=0;
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==CLASS_IDENT||LA69_0==STAR) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // EsperEPL2Ast.g:
            	    {
            	    if ( input.LA(1)==CLASS_IDENT||input.LA(1)==STAR ) {
            	        input.consume();
            	        state.errorRecovery=false;
            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    if ( cnt69 >= 1 ) break loop69;
                        EarlyExitException eee =
                            new EarlyExitException(69, input);
                        throw eee;
                }
                cnt69++;
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "variantList"


    // $ANTLR start "selectExpr"
    // EsperEPL2Ast.g:229:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:230:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:230:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:230:4: ( insertIntoExpr )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==INSERTINTO_EXPR) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:230:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr1234);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr1240);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr1245);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:233:3: ( matchRecogClause )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==MATCH_RECOGNIZE) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // EsperEPL2Ast.g:233:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr1250);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:234:3: ( whereClause[true] )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==WHERE_EXPR) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // EsperEPL2Ast.g:234:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr1257);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:235:3: ( groupByClause )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==GROUP_BY_EXPR) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:235:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr1265);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:236:3: ( havingClause )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==HAVING_EXPR) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // EsperEPL2Ast.g:236:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr1272);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:237:3: ( outputLimitExpr )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( ((LA75_0>=EVENT_LIMIT_EXPR && LA75_0<=CRONTAB_LIMIT_EXPR)||LA75_0==WHEN_LIMIT_EXPR) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // EsperEPL2Ast.g:237:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr1279);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:238:3: ( orderByClause )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==ORDER_BY_EXPR) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:238:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr1286);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:239:3: ( rowLimitClause )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==ROW_LIMIT_EXPR) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:239:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr1293);
                    rowLimitClause();

                    state._fsp--;


                    }
                    break;

            }


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "selectExpr"


    // $ANTLR start "insertIntoExpr"
    // EsperEPL2Ast.g:242:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? CLASS_IDENT ( exprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:243:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? CLASS_IDENT ( exprCol )? ) )
            // EsperEPL2Ast.g:243:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? CLASS_IDENT ( exprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1310); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:243:24: ( ISTREAM | RSTREAM )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( ((LA78_0>=RSTREAM && LA78_0<=ISTREAM)) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:
                    {
                    if ( (input.LA(1)>=RSTREAM && input.LA(1)<=ISTREAM) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_insertIntoExpr1321); 
            // EsperEPL2Ast.g:243:57: ( exprCol )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==EXPRCOL) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:243:58: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_insertIntoExpr1324);
                    exprCol();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(i); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "insertIntoExpr"


    // $ANTLR start "exprCol"
    // EsperEPL2Ast.g:246:1: exprCol : ^( EXPRCOL IDENT ( IDENT )* ) ;
    public final void exprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:247:2: ( ^( EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:247:4: ^( EXPRCOL IDENT ( IDENT )* )
            {
            match(input,EXPRCOL,FOLLOW_EXPRCOL_in_exprCol1343); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_exprCol1345); 
            // EsperEPL2Ast.g:247:20: ( IDENT )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==IDENT) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // EsperEPL2Ast.g:247:21: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_exprCol1348); 

            	    }
            	    break;

            	default :
            	    break loop80;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "exprCol"


    // $ANTLR start "selectClause"
    // EsperEPL2Ast.g:250:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:251:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) )
            // EsperEPL2Ast.g:251:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause1366); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:251:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( ((LA81_0>=RSTREAM && LA81_0<=IRSTREAM)) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:
                    {
                    if ( (input.LA(1)>=RSTREAM && input.LA(1)<=IRSTREAM) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:251:55: ( DISTINCT )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==DISTINCT) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:251:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1381); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1384);
            selectionList();

            state._fsp--;

             leaveNode(s); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "selectClause"


    // $ANTLR start "fromClause"
    // EsperEPL2Ast.g:254:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:255:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:255:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause1398);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:255:21: ( streamExpression ( outerJoin )* )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==STREAM_EXPR) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // EsperEPL2Ast.g:255:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1401);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:255:39: ( outerJoin )*
            	    loop83:
            	    do {
            	        int alt83=2;
            	        int LA83_0 = input.LA(1);

            	        if ( ((LA83_0>=INNERJOIN_EXPR && LA83_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt83=1;
            	        }


            	        switch (alt83) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:255:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1404);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop83;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "fromClause"


    // $ANTLR start "forExpr"
    // EsperEPL2Ast.g:258:1: forExpr : ^(f= FOR IDENT ( valueExpr )* ) ;
    public final void forExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:259:2: ( ^(f= FOR IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:259:4: ^(f= FOR IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,FOR,FOLLOW_FOR_in_forExpr1424); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_forExpr1426); 
            // EsperEPL2Ast.g:259:18: ( valueExpr )*
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( ((LA85_0>=IN_SET && LA85_0<=REGEXP)||LA85_0==NOT_EXPR||(LA85_0>=SUM && LA85_0<=AVG)||(LA85_0>=COALESCE && LA85_0<=COUNT)||(LA85_0>=CASE && LA85_0<=CASE2)||(LA85_0>=PREVIOUS && LA85_0<=EXISTS)||(LA85_0>=INSTANCEOF && LA85_0<=CURRENT_TIMESTAMP)||LA85_0==NEWKW||(LA85_0>=EVAL_AND_EXPR && LA85_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA85_0==EVENT_PROP_EXPR||LA85_0==CONCAT||(LA85_0>=LIB_FUNC_CHAIN && LA85_0<=DOT_EXPR)||LA85_0==ARRAY_EXPR||(LA85_0>=NOT_IN_SET && LA85_0<=NOT_REGEXP)||(LA85_0>=IN_RANGE && LA85_0<=SUBSELECT_EXPR)||(LA85_0>=EXISTS_SUBSELECT_EXPR && LA85_0<=NOT_IN_SUBSELECT_EXPR)||LA85_0==SUBSTITUTION||(LA85_0>=FIRST_AGGREG && LA85_0<=WINDOW_AGGREG)||(LA85_0>=INT_TYPE && LA85_0<=NULL_TYPE)||(LA85_0>=STAR && LA85_0<=PLUS)||(LA85_0>=BAND && LA85_0<=BXOR)||(LA85_0>=LT && LA85_0<=GE)||(LA85_0>=MINUS && LA85_0<=MOD)) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // EsperEPL2Ast.g:259:18: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_forExpr1428);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop85;
                }
            } while (true);

             leaveNode(f); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "forExpr"


    // $ANTLR start "matchRecogClause"
    // EsperEPL2Ast.g:262:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:263:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:263:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1447); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:263:24: ( matchRecogPartitionBy )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==MATCHREC_PARTITION) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:263:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1449);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1456);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:265:4: ( ALL )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==ALL) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:265:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1462); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:266:4: ( matchRecogMatchesAfterSkip )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==MATCHREC_AFTER_SKIP) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:266:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1468);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1474);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:268:4: ( matchRecogMatchesInterval )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==MATCHREC_INTERVAL) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:268:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1480);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1486);
            matchRecogDefine();

            state._fsp--;

             leaveNode(m); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogClause"


    // $ANTLR start "matchRecogPartitionBy"
    // EsperEPL2Ast.g:272:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:273:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:273:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1504); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:273:27: ( valueExpr )+
            int cnt90=0;
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( ((LA90_0>=IN_SET && LA90_0<=REGEXP)||LA90_0==NOT_EXPR||(LA90_0>=SUM && LA90_0<=AVG)||(LA90_0>=COALESCE && LA90_0<=COUNT)||(LA90_0>=CASE && LA90_0<=CASE2)||(LA90_0>=PREVIOUS && LA90_0<=EXISTS)||(LA90_0>=INSTANCEOF && LA90_0<=CURRENT_TIMESTAMP)||LA90_0==NEWKW||(LA90_0>=EVAL_AND_EXPR && LA90_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA90_0==EVENT_PROP_EXPR||LA90_0==CONCAT||(LA90_0>=LIB_FUNC_CHAIN && LA90_0<=DOT_EXPR)||LA90_0==ARRAY_EXPR||(LA90_0>=NOT_IN_SET && LA90_0<=NOT_REGEXP)||(LA90_0>=IN_RANGE && LA90_0<=SUBSELECT_EXPR)||(LA90_0>=EXISTS_SUBSELECT_EXPR && LA90_0<=NOT_IN_SUBSELECT_EXPR)||LA90_0==SUBSTITUTION||(LA90_0>=FIRST_AGGREG && LA90_0<=WINDOW_AGGREG)||(LA90_0>=INT_TYPE && LA90_0<=NULL_TYPE)||(LA90_0>=STAR && LA90_0<=PLUS)||(LA90_0>=BAND && LA90_0<=BXOR)||(LA90_0>=LT && LA90_0<=GE)||(LA90_0>=MINUS && LA90_0<=MOD)) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // EsperEPL2Ast.g:273:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1506);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt90 >= 1 ) break loop90;
                        EarlyExitException eee =
                            new EarlyExitException(90, input);
                        throw eee;
                }
                cnt90++;
            } while (true);

             leaveNode(p); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPartitionBy"


    // $ANTLR start "matchRecogMatchesAfterSkip"
    // EsperEPL2Ast.g:276:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:277:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) )
            // EsperEPL2Ast.g:277:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1523); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1525); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1527); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1529); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1537); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogMatchesAfterSkip"


    // $ANTLR start "matchRecogMatchesInterval"
    // EsperEPL2Ast.g:280:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:281:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:281:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1552); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1554); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1556);
            timePeriod();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogMatchesInterval"


    // $ANTLR start "matchRecogMeasures"
    // EsperEPL2Ast.g:284:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:285:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:285:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1572); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:285:26: ( matchRecogMeasureListElement )*
                loop91:
                do {
                    int alt91=2;
                    int LA91_0 = input.LA(1);

                    if ( (LA91_0==MATCHREC_MEASURE_ITEM) ) {
                        alt91=1;
                    }


                    switch (alt91) {
                	case 1 :
                	    // EsperEPL2Ast.g:285:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1574);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop91;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogMeasures"


    // $ANTLR start "matchRecogMeasureListElement"
    // EsperEPL2Ast.g:288:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:289:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:289:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1591); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1593);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:289:40: ( IDENT )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==IDENT) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // EsperEPL2Ast.g:289:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1595); 

                    }
                    break;

            }

             leaveNode(m); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogMeasureListElement"


    // $ANTLR start "matchRecogPattern"
    // EsperEPL2Ast.g:292:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:293:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:293:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1615); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:293:25: ( matchRecogPatternAlteration )+
            int cnt93=0;
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=MATCHREC_PATTERN_CONCAT && LA93_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // EsperEPL2Ast.g:293:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1617);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt93 >= 1 ) break loop93;
                        EarlyExitException eee =
                            new EarlyExitException(93, input);
                        throw eee;
                }
                cnt93++;
            } while (true);

             leaveNode(p); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPattern"


    // $ANTLR start "matchRecogPatternAlteration"
    // EsperEPL2Ast.g:296:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:297:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==MATCHREC_PATTERN_CONCAT) ) {
                alt95=1;
            }
            else if ( (LA95_0==MATCHREC_PATTERN_ALTER) ) {
                alt95=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:297:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1632);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1640); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1642);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:298:55: ( matchRecogPatternConcat )+
                    int cnt94=0;
                    loop94:
                    do {
                        int alt94=2;
                        int LA94_0 = input.LA(1);

                        if ( (LA94_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt94=1;
                        }


                        switch (alt94) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:298:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1644);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt94 >= 1 ) break loop94;
                                EarlyExitException eee =
                                    new EarlyExitException(94, input);
                                throw eee;
                        }
                        cnt94++;
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPatternAlteration"


    // $ANTLR start "matchRecogPatternConcat"
    // EsperEPL2Ast.g:301:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:302:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:302:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1662); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:302:32: ( matchRecogPatternUnary )+
            int cnt96=0;
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==MATCHREC_PATTERN_ATOM||LA96_0==MATCHREC_PATTERN_NESTED) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // EsperEPL2Ast.g:302:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1664);
            	    matchRecogPatternUnary();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt96 >= 1 ) break loop96;
                        EarlyExitException eee =
                            new EarlyExitException(96, input);
                        throw eee;
                }
                cnt96++;
            } while (true);

             leaveNode(p); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPatternConcat"


    // $ANTLR start "matchRecogPatternUnary"
    // EsperEPL2Ast.g:305:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:306:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==MATCHREC_PATTERN_NESTED) ) {
                alt97=1;
            }
            else if ( (LA97_0==MATCHREC_PATTERN_ATOM) ) {
                alt97=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }
            switch (alt97) {
                case 1 :
                    // EsperEPL2Ast.g:306:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1679);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:307:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1684);
                    matchRecogPatternAtom();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPatternUnary"


    // $ANTLR start "matchRecogPatternNested"
    // EsperEPL2Ast.g:310:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:311:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:311:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1699); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1701);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:311:60: ( PLUS | STAR | QUESTION )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==STAR||(LA98_0>=PLUS && LA98_0<=QUESTION)) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // EsperEPL2Ast.g:
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

             leaveNode(p); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPatternNested"


    // $ANTLR start "matchRecogPatternAtom"
    // EsperEPL2Ast.g:314:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:315:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:315:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1732); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1734); 
            // EsperEPL2Ast.g:315:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==STAR||(LA100_0>=PLUS && LA100_0<=QUESTION)) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:315:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:315:63: ( QUESTION )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==QUESTION) ) {
                        alt99=1;
                    }
                    switch (alt99) {
                        case 1 :
                            // EsperEPL2Ast.g:315:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1750); 

                            }
                            break;

                    }


                    }
                    break;

            }

             leaveNode(p); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogPatternAtom"


    // $ANTLR start "matchRecogDefine"
    // EsperEPL2Ast.g:318:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:319:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:319:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1772); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:319:24: ( matchRecogDefineItem )+
            int cnt101=0;
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==MATCHREC_DEFINE_ITEM) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // EsperEPL2Ast.g:319:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1774);
            	    matchRecogDefineItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt101 >= 1 ) break loop101;
                        EarlyExitException eee =
                            new EarlyExitException(101, input);
                        throw eee;
                }
                cnt101++;
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogDefine"


    // $ANTLR start "matchRecogDefineItem"
    // EsperEPL2Ast.g:322:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:323:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:323:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1791); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1793); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1795);
            valueExpr();

            state._fsp--;

             leaveNode(d); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchRecogDefineItem"


    // $ANTLR start "selectionList"
    // EsperEPL2Ast.g:327:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:328:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:328:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1812);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:328:25: ( selectionListElement )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( ((LA102_0>=SELECTION_ELEMENT_EXPR && LA102_0<=SELECTION_STREAM)||LA102_0==WILDCARD_SELECT) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // EsperEPL2Ast.g:328:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1815);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop102;
                }
            } while (true);


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "selectionList"


    // $ANTLR start "selectionListElement"
    // EsperEPL2Ast.g:331:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:332:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt105=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt105=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt105=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt105=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }

            switch (alt105) {
                case 1 :
                    // EsperEPL2Ast.g:332:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1831); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:333:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1841); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1843);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:333:41: ( IDENT )?
                    int alt103=2;
                    int LA103_0 = input.LA(1);

                    if ( (LA103_0==IDENT) ) {
                        alt103=1;
                    }
                    switch (alt103) {
                        case 1 :
                            // EsperEPL2Ast.g:333:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1846); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:334:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1860); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1862); 
                    // EsperEPL2Ast.g:334:31: ( IDENT )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==IDENT) ) {
                        alt104=1;
                    }
                    switch (alt104) {
                        case 1 :
                            // EsperEPL2Ast.g:334:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1865); 

                            }
                            break;

                    }

                     leaveNode(s); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "selectionListElement"


    // $ANTLR start "outerJoin"
    // EsperEPL2Ast.g:337:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:338:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:338:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1884);
            outerJoinIdent();

            state._fsp--;


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "outerJoin"


    // $ANTLR start "outerJoinIdent"
    // EsperEPL2Ast.g:341:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:342:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt110=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt110=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt110=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt110=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt110=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }

            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:342:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1898); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1900);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1903);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:342:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop106:
                    do {
                        int alt106=2;
                        int LA106_0 = input.LA(1);

                        if ( (LA106_0==EVENT_PROP_EXPR) ) {
                            alt106=1;
                        }


                        switch (alt106) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:342:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1907);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1910);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop106;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:343:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1925); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1927);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1930);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:343:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop107:
                    do {
                        int alt107=2;
                        int LA107_0 = input.LA(1);

                        if ( (LA107_0==EVENT_PROP_EXPR) ) {
                            alt107=1;
                        }


                        switch (alt107) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:343:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1934);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1937);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop107;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:344:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1952); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1954);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1957);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:344:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop108:
                    do {
                        int alt108=2;
                        int LA108_0 = input.LA(1);

                        if ( (LA108_0==EVENT_PROP_EXPR) ) {
                            alt108=1;
                        }


                        switch (alt108) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:344:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1961);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1964);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop108;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:345:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1979); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1981);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1984);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:345:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==EVENT_PROP_EXPR) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:345:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1988);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1991);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);

                     leaveNode(i); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "outerJoinIdent"


    // $ANTLR start "streamExpression"
    // EsperEPL2Ast.g:348:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:349:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:349:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression2012); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:349:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt111=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt111=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt111=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt111=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt111=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }

            switch (alt111) {
                case 1 :
                    // EsperEPL2Ast.g:349:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression2015);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:349:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression2019);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:349:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression2023);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:349:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression2027);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:349:115: ( viewListExpr )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==VIEW_EXPR) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:349:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression2031);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:349:131: ( IDENT )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==IDENT) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:349:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression2036); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:349:140: ( UNIDIRECTIONAL )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==UNIDIRECTIONAL) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:349:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression2041); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:349:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( ((LA115_0>=RETAINUNION && LA115_0<=RETAININTERSECTION)) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // EsperEPL2Ast.g:
                    {
                    if ( (input.LA(1)>=RETAINUNION && input.LA(1)<=RETAININTERSECTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

             leaveNode(v); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "streamExpression"


    // $ANTLR start "eventFilterExpr"
    // EsperEPL2Ast.g:352:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:353:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:353:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr2069); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:353:27: ( IDENT )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==IDENT) ) {
                alt116=1;
            }
            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:353:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr2071); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr2074); 
            // EsperEPL2Ast.g:353:46: ( propertyExpression )?
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt117=1;
            }
            switch (alt117) {
                case 1 :
                    // EsperEPL2Ast.g:353:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr2076);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:353:66: ( valueExpr )*
            loop118:
            do {
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( ((LA118_0>=IN_SET && LA118_0<=REGEXP)||LA118_0==NOT_EXPR||(LA118_0>=SUM && LA118_0<=AVG)||(LA118_0>=COALESCE && LA118_0<=COUNT)||(LA118_0>=CASE && LA118_0<=CASE2)||(LA118_0>=PREVIOUS && LA118_0<=EXISTS)||(LA118_0>=INSTANCEOF && LA118_0<=CURRENT_TIMESTAMP)||LA118_0==NEWKW||(LA118_0>=EVAL_AND_EXPR && LA118_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA118_0==EVENT_PROP_EXPR||LA118_0==CONCAT||(LA118_0>=LIB_FUNC_CHAIN && LA118_0<=DOT_EXPR)||LA118_0==ARRAY_EXPR||(LA118_0>=NOT_IN_SET && LA118_0<=NOT_REGEXP)||(LA118_0>=IN_RANGE && LA118_0<=SUBSELECT_EXPR)||(LA118_0>=EXISTS_SUBSELECT_EXPR && LA118_0<=NOT_IN_SUBSELECT_EXPR)||LA118_0==SUBSTITUTION||(LA118_0>=FIRST_AGGREG && LA118_0<=WINDOW_AGGREG)||(LA118_0>=INT_TYPE && LA118_0<=NULL_TYPE)||(LA118_0>=STAR && LA118_0<=PLUS)||(LA118_0>=BAND && LA118_0<=BXOR)||(LA118_0>=LT && LA118_0<=GE)||(LA118_0>=MINUS && LA118_0<=MOD)) ) {
                    alt118=1;
                }


                switch (alt118) {
            	case 1 :
            	    // EsperEPL2Ast.g:353:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr2080);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop118;
                }
            } while (true);

             leaveNode(f); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "eventFilterExpr"


    // $ANTLR start "propertyExpression"
    // EsperEPL2Ast.g:356:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:357:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:357:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression2100); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:357:34: ( propertyExpressionAtom )*
                loop119:
                do {
                    int alt119=2;
                    int LA119_0 = input.LA(1);

                    if ( (LA119_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt119=1;
                    }


                    switch (alt119) {
                	case 1 :
                	    // EsperEPL2Ast.g:357:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression2102);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop119;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "propertyExpression"


    // $ANTLR start "propertyExpressionAtom"
    // EsperEPL2Ast.g:360:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:361:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:361:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom2121); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:361:41: ( propertySelectionListElement )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( ((LA120_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA120_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // EsperEPL2Ast.g:361:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom2123);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop120;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom2126);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:361:96: ( IDENT )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==IDENT) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // EsperEPL2Ast.g:361:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom2129); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom2133); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:361:116: ( valueExpr )?
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( ((LA122_0>=IN_SET && LA122_0<=REGEXP)||LA122_0==NOT_EXPR||(LA122_0>=SUM && LA122_0<=AVG)||(LA122_0>=COALESCE && LA122_0<=COUNT)||(LA122_0>=CASE && LA122_0<=CASE2)||(LA122_0>=PREVIOUS && LA122_0<=EXISTS)||(LA122_0>=INSTANCEOF && LA122_0<=CURRENT_TIMESTAMP)||LA122_0==NEWKW||(LA122_0>=EVAL_AND_EXPR && LA122_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA122_0==EVENT_PROP_EXPR||LA122_0==CONCAT||(LA122_0>=LIB_FUNC_CHAIN && LA122_0<=DOT_EXPR)||LA122_0==ARRAY_EXPR||(LA122_0>=NOT_IN_SET && LA122_0<=NOT_REGEXP)||(LA122_0>=IN_RANGE && LA122_0<=SUBSELECT_EXPR)||(LA122_0>=EXISTS_SUBSELECT_EXPR && LA122_0<=NOT_IN_SUBSELECT_EXPR)||LA122_0==SUBSTITUTION||(LA122_0>=FIRST_AGGREG && LA122_0<=WINDOW_AGGREG)||(LA122_0>=INT_TYPE && LA122_0<=NULL_TYPE)||(LA122_0>=STAR && LA122_0<=PLUS)||(LA122_0>=BAND && LA122_0<=BXOR)||(LA122_0>=LT && LA122_0<=GE)||(LA122_0>=MINUS && LA122_0<=MOD)) ) {
                    alt122=1;
                }
                switch (alt122) {
                    case 1 :
                        // EsperEPL2Ast.g:361:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom2135);
                        valueExpr();

                        state._fsp--;


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }
             leaveNode(a); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "propertyExpressionAtom"


    // $ANTLR start "propertySelectionListElement"
    // EsperEPL2Ast.g:364:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:365:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt125=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt125=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt125=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt125=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 125, 0, input);

                throw nvae;
            }

            switch (alt125) {
                case 1 :
                    // EsperEPL2Ast.g:365:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement2155); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:366:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement2165); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement2167);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:366:50: ( IDENT )?
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( (LA123_0==IDENT) ) {
                        alt123=1;
                    }
                    switch (alt123) {
                        case 1 :
                            // EsperEPL2Ast.g:366:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2170); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:367:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2184); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2186); 
                    // EsperEPL2Ast.g:367:40: ( IDENT )?
                    int alt124=2;
                    int LA124_0 = input.LA(1);

                    if ( (LA124_0==IDENT) ) {
                        alt124=1;
                    }
                    switch (alt124) {
                        case 1 :
                            // EsperEPL2Ast.g:367:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2189); 

                            }
                            break;

                    }

                     leaveNode(s); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "propertySelectionListElement"


    // $ANTLR start "patternInclusionExpression"
    // EsperEPL2Ast.g:370:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:371:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:371:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2210); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression2212);
            exprChoice();

            state._fsp--;

             leaveNode(p); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "patternInclusionExpression"


    // $ANTLR start "databaseJoinExpression"
    // EsperEPL2Ast.g:374:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:375:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:375:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2229); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression2231); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:375:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( ((LA126_0>=STRING_LITERAL && LA126_0<=QUOTED_STRING_LITERAL)) ) {
                alt126=1;
            }
            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:
                    {
                    if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "databaseJoinExpression"


    // $ANTLR start "methodJoinExpression"
    // EsperEPL2Ast.g:378:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:379:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:379:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2262); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression2264); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression2266); 
            // EsperEPL2Ast.g:379:41: ( valueExpr )*
            loop127:
            do {
                int alt127=2;
                int LA127_0 = input.LA(1);

                if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||LA127_0==NEWKW||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA127_0==EVENT_PROP_EXPR||LA127_0==CONCAT||(LA127_0>=LIB_FUNC_CHAIN && LA127_0<=DOT_EXPR)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=SUBSELECT_EXPR)||(LA127_0>=EXISTS_SUBSELECT_EXPR && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=FIRST_AGGREG && LA127_0<=WINDOW_AGGREG)||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||(LA127_0>=STAR && LA127_0<=PLUS)||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=MINUS && LA127_0<=MOD)) ) {
                    alt127=1;
                }


                switch (alt127) {
            	case 1 :
            	    // EsperEPL2Ast.g:379:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression2269);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop127;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "methodJoinExpression"


    // $ANTLR start "viewListExpr"
    // EsperEPL2Ast.g:382:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:383:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:383:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr2283);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:383:13: ( viewExpr )*
            loop128:
            do {
                int alt128=2;
                int LA128_0 = input.LA(1);

                if ( (LA128_0==VIEW_EXPR) ) {
                    alt128=1;
                }


                switch (alt128) {
            	case 1 :
            	    // EsperEPL2Ast.g:383:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr2286);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop128;
                }
            } while (true);


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "viewListExpr"


    // $ANTLR start "viewExpr"
    // EsperEPL2Ast.g:386:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:387:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:387:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr2303); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2305); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2307); 
            // EsperEPL2Ast.g:387:30: ( valueExprWithTime )*
            loop129:
            do {
                int alt129=2;
                int LA129_0 = input.LA(1);

                if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||LA129_0==LAST||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=LW && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=NEWKW && LA129_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA129_0==EVENT_PROP_EXPR||LA129_0==CONCAT||(LA129_0>=LIB_FUNC_CHAIN && LA129_0<=DOT_EXPR)||(LA129_0>=TIME_PERIOD && LA129_0<=ARRAY_EXPR)||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=SUBSELECT_EXPR)||(LA129_0>=EXISTS_SUBSELECT_EXPR && LA129_0<=NOT_IN_SUBSELECT_EXPR)||(LA129_0>=LAST_OPERATOR && LA129_0<=SUBSTITUTION)||LA129_0==NUMBERSETSTAR||(LA129_0>=FIRST_AGGREG && LA129_0<=WINDOW_AGGREG)||(LA129_0>=INT_TYPE && LA129_0<=NULL_TYPE)||(LA129_0>=STAR && LA129_0<=PLUS)||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=MINUS && LA129_0<=MOD)) ) {
                    alt129=1;
                }


                switch (alt129) {
            	case 1 :
            	    // EsperEPL2Ast.g:387:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr2310);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop129;
                }
            } while (true);

             leaveNode(n); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "viewExpr"


    // $ANTLR start "whereClause"
    // EsperEPL2Ast.g:390:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:391:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:391:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause2332); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause2334);
            valueExpr();

            state._fsp--;

             if (isLeaveNode) leaveNode(n); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "whereClause"


    // $ANTLR start "groupByClause"
    // EsperEPL2Ast.g:394:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:395:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:395:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause2352); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause2354);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:395:32: ( valueExpr )*
            loop130:
            do {
                int alt130=2;
                int LA130_0 = input.LA(1);

                if ( ((LA130_0>=IN_SET && LA130_0<=REGEXP)||LA130_0==NOT_EXPR||(LA130_0>=SUM && LA130_0<=AVG)||(LA130_0>=COALESCE && LA130_0<=COUNT)||(LA130_0>=CASE && LA130_0<=CASE2)||(LA130_0>=PREVIOUS && LA130_0<=EXISTS)||(LA130_0>=INSTANCEOF && LA130_0<=CURRENT_TIMESTAMP)||LA130_0==NEWKW||(LA130_0>=EVAL_AND_EXPR && LA130_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA130_0==EVENT_PROP_EXPR||LA130_0==CONCAT||(LA130_0>=LIB_FUNC_CHAIN && LA130_0<=DOT_EXPR)||LA130_0==ARRAY_EXPR||(LA130_0>=NOT_IN_SET && LA130_0<=NOT_REGEXP)||(LA130_0>=IN_RANGE && LA130_0<=SUBSELECT_EXPR)||(LA130_0>=EXISTS_SUBSELECT_EXPR && LA130_0<=NOT_IN_SUBSELECT_EXPR)||LA130_0==SUBSTITUTION||(LA130_0>=FIRST_AGGREG && LA130_0<=WINDOW_AGGREG)||(LA130_0>=INT_TYPE && LA130_0<=NULL_TYPE)||(LA130_0>=STAR && LA130_0<=PLUS)||(LA130_0>=BAND && LA130_0<=BXOR)||(LA130_0>=LT && LA130_0<=GE)||(LA130_0>=MINUS && LA130_0<=MOD)) ) {
                    alt130=1;
                }


                switch (alt130) {
            	case 1 :
            	    // EsperEPL2Ast.g:395:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause2357);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop130;
                }
            } while (true);


            match(input, Token.UP, null); 
             leaveNode(g); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "groupByClause"


    // $ANTLR start "orderByClause"
    // EsperEPL2Ast.g:398:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:399:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:399:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause2375); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause2377);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:399:35: ( orderByElement )*
            loop131:
            do {
                int alt131=2;
                int LA131_0 = input.LA(1);

                if ( (LA131_0==ORDER_ELEMENT_EXPR) ) {
                    alt131=1;
                }


                switch (alt131) {
            	case 1 :
            	    // EsperEPL2Ast.g:399:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause2380);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop131;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "orderByClause"


    // $ANTLR start "orderByElement"
    // EsperEPL2Ast.g:402:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:403:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:403:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2400); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2402);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:403:38: ( ASC | DESC )?
            int alt132=2;
            int LA132_0 = input.LA(1);

            if ( ((LA132_0>=ASC && LA132_0<=DESC)) ) {
                alt132=1;
            }
            switch (alt132) {
                case 1 :
                    // EsperEPL2Ast.g:
                    {
                    if ( (input.LA(1)>=ASC && input.LA(1)<=DESC) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    }
                    break;

            }

             leaveNode(e); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "orderByElement"


    // $ANTLR start "havingClause"
    // EsperEPL2Ast.g:406:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:407:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:407:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2427); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2429);
            valueExpr();

            state._fsp--;

             leaveNode(n); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "havingClause"


    // $ANTLR start "outputLimitExpr"
    // EsperEPL2Ast.g:410:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:411:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt143=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt143=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt143=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt143=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt143=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt143=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }

            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:411:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2447); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:411:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt133=2;
                    int LA133_0 = input.LA(1);

                    if ( (LA133_0==ALL||(LA133_0>=FIRST && LA133_0<=LAST)||LA133_0==SNAPSHOT) ) {
                        alt133=1;
                    }
                    switch (alt133) {
                        case 1 :
                            // EsperEPL2Ast.g:
                            {
                            if ( input.LA(1)==ALL||(input.LA(1)>=FIRST && input.LA(1)<=LAST)||input.LA(1)==SNAPSHOT ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:411:52: ( number | IDENT )
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( ((LA134_0>=INT_TYPE && LA134_0<=DOUBLE_TYPE)) ) {
                        alt134=1;
                    }
                    else if ( (LA134_0==IDENT) ) {
                        alt134=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 134, 0, input);

                        throw nvae;
                    }
                    switch (alt134) {
                        case 1 :
                            // EsperEPL2Ast.g:411:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2461);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:411:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2463); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:411:67: ( outputLimitAfter )?
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( (LA135_0==AFTER) ) {
                        alt135=1;
                    }
                    switch (alt135) {
                        case 1 :
                            // EsperEPL2Ast.g:411:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2466);
                            outputLimitAfter();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:412:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2483); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:412:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt136=2;
                    int LA136_0 = input.LA(1);

                    if ( (LA136_0==ALL||(LA136_0>=FIRST && LA136_0<=LAST)||LA136_0==SNAPSHOT) ) {
                        alt136=1;
                    }
                    switch (alt136) {
                        case 1 :
                            // EsperEPL2Ast.g:
                            {
                            if ( input.LA(1)==ALL||(input.LA(1)>=FIRST && input.LA(1)<=LAST)||input.LA(1)==SNAPSHOT ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2496);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:412:72: ( outputLimitAfter )?
                    int alt137=2;
                    int LA137_0 = input.LA(1);

                    if ( (LA137_0==AFTER) ) {
                        alt137=1;
                    }
                    switch (alt137) {
                        case 1 :
                            // EsperEPL2Ast.g:412:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2498);
                            outputLimitAfter();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:413:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2514); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:413:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt138=2;
                    int LA138_0 = input.LA(1);

                    if ( (LA138_0==ALL||(LA138_0>=FIRST && LA138_0<=LAST)||LA138_0==SNAPSHOT) ) {
                        alt138=1;
                    }
                    switch (alt138) {
                        case 1 :
                            // EsperEPL2Ast.g:
                            {
                            if ( input.LA(1)==ALL||(input.LA(1)>=FIRST && input.LA(1)<=LAST)||input.LA(1)==SNAPSHOT ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2527);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:413:85: ( outputLimitAfter )?
                    int alt139=2;
                    int LA139_0 = input.LA(1);

                    if ( (LA139_0==AFTER) ) {
                        alt139=1;
                    }
                    switch (alt139) {
                        case 1 :
                            // EsperEPL2Ast.g:413:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2529);
                            outputLimitAfter();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:414:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2545); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:414:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt140=2;
                    int LA140_0 = input.LA(1);

                    if ( (LA140_0==ALL||(LA140_0>=FIRST && LA140_0<=LAST)||LA140_0==SNAPSHOT) ) {
                        alt140=1;
                    }
                    switch (alt140) {
                        case 1 :
                            // EsperEPL2Ast.g:
                            {
                            if ( input.LA(1)==ALL||(input.LA(1)>=FIRST && input.LA(1)<=LAST)||input.LA(1)==SNAPSHOT ) {
                                input.consume();
                                state.errorRecovery=false;
                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                throw mse;
                            }


                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2558);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:414:67: ( onSetExpr )?
                    int alt141=2;
                    int LA141_0 = input.LA(1);

                    if ( (LA141_0==ON_SET_EXPR) ) {
                        alt141=1;
                    }
                    switch (alt141) {
                        case 1 :
                            // EsperEPL2Ast.g:414:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2560);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:414:78: ( outputLimitAfter )?
                    int alt142=2;
                    int LA142_0 = input.LA(1);

                    if ( (LA142_0==AFTER) ) {
                        alt142=1;
                    }
                    switch (alt142) {
                        case 1 :
                            // EsperEPL2Ast.g:414:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2563);
                            outputLimitAfter();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(when); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:415:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2576); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2578);
                    outputLimitAfter();

                    state._fsp--;

                     leaveNode(after); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "outputLimitExpr"


    // $ANTLR start "outputLimitAfter"
    // EsperEPL2Ast.g:418:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:419:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:419:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2593); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:419:12: ( timePeriod )?
                int alt144=2;
                int LA144_0 = input.LA(1);

                if ( (LA144_0==TIME_PERIOD) ) {
                    alt144=1;
                }
                switch (alt144) {
                    case 1 :
                        // EsperEPL2Ast.g:419:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2595);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:419:24: ( number )?
                int alt145=2;
                int LA145_0 = input.LA(1);

                if ( ((LA145_0>=INT_TYPE && LA145_0<=DOUBLE_TYPE)) ) {
                    alt145=1;
                }
                switch (alt145) {
                    case 1 :
                        // EsperEPL2Ast.g:419:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2598);
                        number();

                        state._fsp--;


                        }
                        break;

                }


                match(input, Token.UP, null); 
            }

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "outputLimitAfter"


    // $ANTLR start "rowLimitClause"
    // EsperEPL2Ast.g:422:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:423:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:423:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2614); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:423:23: ( number | IDENT )
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( ((LA146_0>=INT_TYPE && LA146_0<=DOUBLE_TYPE)) ) {
                alt146=1;
            }
            else if ( (LA146_0==IDENT) ) {
                alt146=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 146, 0, input);

                throw nvae;
            }
            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:423:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2617);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:423:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2619); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:423:38: ( number | IDENT )?
            int alt147=3;
            int LA147_0 = input.LA(1);

            if ( ((LA147_0>=INT_TYPE && LA147_0<=DOUBLE_TYPE)) ) {
                alt147=1;
            }
            else if ( (LA147_0==IDENT) ) {
                alt147=2;
            }
            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:423:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2623);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:423:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2625); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:423:54: ( COMMA )?
            int alt148=2;
            int LA148_0 = input.LA(1);

            if ( (LA148_0==COMMA) ) {
                alt148=1;
            }
            switch (alt148) {
                case 1 :
                    // EsperEPL2Ast.g:423:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2629); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:423:61: ( OFFSET )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==OFFSET) ) {
                alt149=1;
            }
            switch (alt149) {
                case 1 :
                    // EsperEPL2Ast.g:423:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2632); 

                    }
                    break;

            }

             leaveNode(e); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "rowLimitClause"


    // $ANTLR start "crontabLimitParameterSet"
    // EsperEPL2Ast.g:426:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:427:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:427:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2650); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2652);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2654);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2656);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2658);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2660);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:427:121: ( valueExprWithTime )?
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( ((LA150_0>=IN_SET && LA150_0<=REGEXP)||LA150_0==NOT_EXPR||(LA150_0>=SUM && LA150_0<=AVG)||(LA150_0>=COALESCE && LA150_0<=COUNT)||(LA150_0>=CASE && LA150_0<=CASE2)||LA150_0==LAST||(LA150_0>=PREVIOUS && LA150_0<=EXISTS)||(LA150_0>=LW && LA150_0<=CURRENT_TIMESTAMP)||(LA150_0>=NEWKW && LA150_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA150_0>=EVAL_AND_EXPR && LA150_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA150_0==EVENT_PROP_EXPR||LA150_0==CONCAT||(LA150_0>=LIB_FUNC_CHAIN && LA150_0<=DOT_EXPR)||(LA150_0>=TIME_PERIOD && LA150_0<=ARRAY_EXPR)||(LA150_0>=NOT_IN_SET && LA150_0<=NOT_REGEXP)||(LA150_0>=IN_RANGE && LA150_0<=SUBSELECT_EXPR)||(LA150_0>=EXISTS_SUBSELECT_EXPR && LA150_0<=NOT_IN_SUBSELECT_EXPR)||(LA150_0>=LAST_OPERATOR && LA150_0<=SUBSTITUTION)||LA150_0==NUMBERSETSTAR||(LA150_0>=FIRST_AGGREG && LA150_0<=WINDOW_AGGREG)||(LA150_0>=INT_TYPE && LA150_0<=NULL_TYPE)||(LA150_0>=STAR && LA150_0<=PLUS)||(LA150_0>=BAND && LA150_0<=BXOR)||(LA150_0>=LT && LA150_0<=GE)||(LA150_0>=MINUS && LA150_0<=MOD)) ) {
                alt150=1;
            }
            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:427:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2662);
                    valueExprWithTime();

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "crontabLimitParameterSet"


    // $ANTLR start "relationalExpr"
    // EsperEPL2Ast.g:430:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:431:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt151=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt151=1;
                }
                break;
            case GT:
                {
                alt151=2;
                }
                break;
            case LE:
                {
                alt151=3;
                }
                break;
            case GE:
                {
                alt151=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 151, 0, input);

                throw nvae;
            }

            switch (alt151) {
                case 1 :
                    // EsperEPL2Ast.g:431:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2679); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2681);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:432:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2694); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2696);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:433:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2709); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2711);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:434:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2723); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2725);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "relationalExpr"


    // $ANTLR start "relationalExprValue"
    // EsperEPL2Ast.g:437:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:438:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:438:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:438:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:439:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2747);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:440:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt154=2;
            int LA154_0 = input.LA(1);

            if ( ((LA154_0>=IN_SET && LA154_0<=REGEXP)||LA154_0==NOT_EXPR||(LA154_0>=SUM && LA154_0<=AVG)||(LA154_0>=COALESCE && LA154_0<=COUNT)||(LA154_0>=CASE && LA154_0<=CASE2)||(LA154_0>=PREVIOUS && LA154_0<=EXISTS)||(LA154_0>=INSTANCEOF && LA154_0<=CURRENT_TIMESTAMP)||LA154_0==NEWKW||(LA154_0>=EVAL_AND_EXPR && LA154_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA154_0==EVENT_PROP_EXPR||LA154_0==CONCAT||(LA154_0>=LIB_FUNC_CHAIN && LA154_0<=DOT_EXPR)||LA154_0==ARRAY_EXPR||(LA154_0>=NOT_IN_SET && LA154_0<=NOT_REGEXP)||(LA154_0>=IN_RANGE && LA154_0<=SUBSELECT_EXPR)||(LA154_0>=EXISTS_SUBSELECT_EXPR && LA154_0<=NOT_IN_SUBSELECT_EXPR)||LA154_0==SUBSTITUTION||(LA154_0>=FIRST_AGGREG && LA154_0<=WINDOW_AGGREG)||(LA154_0>=INT_TYPE && LA154_0<=NULL_TYPE)||(LA154_0>=STAR && LA154_0<=PLUS)||(LA154_0>=BAND && LA154_0<=BXOR)||(LA154_0>=LT && LA154_0<=GE)||(LA154_0>=MINUS && LA154_0<=MOD)) ) {
                alt154=1;
            }
            else if ( ((LA154_0>=ALL && LA154_0<=SOME)) ) {
                alt154=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 154, 0, input);

                throw nvae;
            }
            switch (alt154) {
                case 1 :
                    // EsperEPL2Ast.g:440:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2757);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:442:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:442:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==UP||(LA153_0>=IN_SET && LA153_0<=REGEXP)||LA153_0==NOT_EXPR||(LA153_0>=SUM && LA153_0<=AVG)||(LA153_0>=COALESCE && LA153_0<=COUNT)||(LA153_0>=CASE && LA153_0<=CASE2)||(LA153_0>=PREVIOUS && LA153_0<=EXISTS)||(LA153_0>=INSTANCEOF && LA153_0<=CURRENT_TIMESTAMP)||LA153_0==NEWKW||(LA153_0>=EVAL_AND_EXPR && LA153_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA153_0==EVENT_PROP_EXPR||LA153_0==CONCAT||(LA153_0>=LIB_FUNC_CHAIN && LA153_0<=DOT_EXPR)||LA153_0==ARRAY_EXPR||(LA153_0>=NOT_IN_SET && LA153_0<=NOT_REGEXP)||(LA153_0>=IN_RANGE && LA153_0<=SUBSELECT_EXPR)||(LA153_0>=EXISTS_SUBSELECT_EXPR && LA153_0<=NOT_IN_SUBSELECT_EXPR)||LA153_0==SUBSTITUTION||(LA153_0>=FIRST_AGGREG && LA153_0<=WINDOW_AGGREG)||(LA153_0>=INT_TYPE && LA153_0<=NULL_TYPE)||(LA153_0>=STAR && LA153_0<=PLUS)||(LA153_0>=BAND && LA153_0<=BXOR)||(LA153_0>=LT && LA153_0<=GE)||(LA153_0>=MINUS && LA153_0<=MOD)) ) {
                        alt153=1;
                    }
                    else if ( (LA153_0==SUBSELECT_GROUP_EXPR) ) {
                        alt153=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 153, 0, input);

                        throw nvae;
                    }
                    switch (alt153) {
                        case 1 :
                            // EsperEPL2Ast.g:442:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:442:22: ( valueExpr )*
                            loop152:
                            do {
                                int alt152=2;
                                int LA152_0 = input.LA(1);

                                if ( ((LA152_0>=IN_SET && LA152_0<=REGEXP)||LA152_0==NOT_EXPR||(LA152_0>=SUM && LA152_0<=AVG)||(LA152_0>=COALESCE && LA152_0<=COUNT)||(LA152_0>=CASE && LA152_0<=CASE2)||(LA152_0>=PREVIOUS && LA152_0<=EXISTS)||(LA152_0>=INSTANCEOF && LA152_0<=CURRENT_TIMESTAMP)||LA152_0==NEWKW||(LA152_0>=EVAL_AND_EXPR && LA152_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA152_0==EVENT_PROP_EXPR||LA152_0==CONCAT||(LA152_0>=LIB_FUNC_CHAIN && LA152_0<=DOT_EXPR)||LA152_0==ARRAY_EXPR||(LA152_0>=NOT_IN_SET && LA152_0<=NOT_REGEXP)||(LA152_0>=IN_RANGE && LA152_0<=SUBSELECT_EXPR)||(LA152_0>=EXISTS_SUBSELECT_EXPR && LA152_0<=NOT_IN_SUBSELECT_EXPR)||LA152_0==SUBSTITUTION||(LA152_0>=FIRST_AGGREG && LA152_0<=WINDOW_AGGREG)||(LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)||(LA152_0>=STAR && LA152_0<=PLUS)||(LA152_0>=BAND && LA152_0<=BXOR)||(LA152_0>=LT && LA152_0<=GE)||(LA152_0>=MINUS && LA152_0<=MOD)) ) {
                                    alt152=1;
                                }


                                switch (alt152) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:442:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2781);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop152;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:442:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2786);
                            subSelectGroupExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }


            }


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "relationalExprValue"


    // $ANTLR start "evalExprChoice"
    // EsperEPL2Ast.g:447:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:448:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt161=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt161=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt161=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt161=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt161=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt161=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt161=6;
                }
                break;
            case NOT_EXPR:
                {
                alt161=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt161=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }

            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:448:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2812); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2814);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2816);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:448:42: ( valueExpr )*
                    loop155:
                    do {
                        int alt155=2;
                        int LA155_0 = input.LA(1);

                        if ( ((LA155_0>=IN_SET && LA155_0<=REGEXP)||LA155_0==NOT_EXPR||(LA155_0>=SUM && LA155_0<=AVG)||(LA155_0>=COALESCE && LA155_0<=COUNT)||(LA155_0>=CASE && LA155_0<=CASE2)||(LA155_0>=PREVIOUS && LA155_0<=EXISTS)||(LA155_0>=INSTANCEOF && LA155_0<=CURRENT_TIMESTAMP)||LA155_0==NEWKW||(LA155_0>=EVAL_AND_EXPR && LA155_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA155_0==EVENT_PROP_EXPR||LA155_0==CONCAT||(LA155_0>=LIB_FUNC_CHAIN && LA155_0<=DOT_EXPR)||LA155_0==ARRAY_EXPR||(LA155_0>=NOT_IN_SET && LA155_0<=NOT_REGEXP)||(LA155_0>=IN_RANGE && LA155_0<=SUBSELECT_EXPR)||(LA155_0>=EXISTS_SUBSELECT_EXPR && LA155_0<=NOT_IN_SUBSELECT_EXPR)||LA155_0==SUBSTITUTION||(LA155_0>=FIRST_AGGREG && LA155_0<=WINDOW_AGGREG)||(LA155_0>=INT_TYPE && LA155_0<=NULL_TYPE)||(LA155_0>=STAR && LA155_0<=PLUS)||(LA155_0>=BAND && LA155_0<=BXOR)||(LA155_0>=LT && LA155_0<=GE)||(LA155_0>=MINUS && LA155_0<=MOD)) ) {
                            alt155=1;
                        }


                        switch (alt155) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:448:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2819);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop155;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:449:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2833); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2835);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2837);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:449:43: ( valueExpr )*
                    loop156:
                    do {
                        int alt156=2;
                        int LA156_0 = input.LA(1);

                        if ( ((LA156_0>=IN_SET && LA156_0<=REGEXP)||LA156_0==NOT_EXPR||(LA156_0>=SUM && LA156_0<=AVG)||(LA156_0>=COALESCE && LA156_0<=COUNT)||(LA156_0>=CASE && LA156_0<=CASE2)||(LA156_0>=PREVIOUS && LA156_0<=EXISTS)||(LA156_0>=INSTANCEOF && LA156_0<=CURRENT_TIMESTAMP)||LA156_0==NEWKW||(LA156_0>=EVAL_AND_EXPR && LA156_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA156_0==EVENT_PROP_EXPR||LA156_0==CONCAT||(LA156_0>=LIB_FUNC_CHAIN && LA156_0<=DOT_EXPR)||LA156_0==ARRAY_EXPR||(LA156_0>=NOT_IN_SET && LA156_0<=NOT_REGEXP)||(LA156_0>=IN_RANGE && LA156_0<=SUBSELECT_EXPR)||(LA156_0>=EXISTS_SUBSELECT_EXPR && LA156_0<=NOT_IN_SUBSELECT_EXPR)||LA156_0==SUBSTITUTION||(LA156_0>=FIRST_AGGREG && LA156_0<=WINDOW_AGGREG)||(LA156_0>=INT_TYPE && LA156_0<=NULL_TYPE)||(LA156_0>=STAR && LA156_0<=PLUS)||(LA156_0>=BAND && LA156_0<=BXOR)||(LA156_0>=LT && LA156_0<=GE)||(LA156_0>=MINUS && LA156_0<=MOD)) ) {
                            alt156=1;
                        }


                        switch (alt156) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:449:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2840);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop156;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:450:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2854); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2856);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2858);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:451:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2870); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2872);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2874);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:452:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2886); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2888);
                    valueExpr();

                    state._fsp--;

                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:452:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt158=2;
                    int LA158_0 = input.LA(1);

                    if ( (LA158_0==UP||(LA158_0>=IN_SET && LA158_0<=REGEXP)||LA158_0==NOT_EXPR||(LA158_0>=SUM && LA158_0<=AVG)||(LA158_0>=COALESCE && LA158_0<=COUNT)||(LA158_0>=CASE && LA158_0<=CASE2)||(LA158_0>=PREVIOUS && LA158_0<=EXISTS)||(LA158_0>=INSTANCEOF && LA158_0<=CURRENT_TIMESTAMP)||LA158_0==NEWKW||(LA158_0>=EVAL_AND_EXPR && LA158_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA158_0==EVENT_PROP_EXPR||LA158_0==CONCAT||(LA158_0>=LIB_FUNC_CHAIN && LA158_0<=DOT_EXPR)||LA158_0==ARRAY_EXPR||(LA158_0>=NOT_IN_SET && LA158_0<=NOT_REGEXP)||(LA158_0>=IN_RANGE && LA158_0<=SUBSELECT_EXPR)||(LA158_0>=EXISTS_SUBSELECT_EXPR && LA158_0<=NOT_IN_SUBSELECT_EXPR)||LA158_0==SUBSTITUTION||(LA158_0>=FIRST_AGGREG && LA158_0<=WINDOW_AGGREG)||(LA158_0>=INT_TYPE && LA158_0<=NULL_TYPE)||(LA158_0>=STAR && LA158_0<=PLUS)||(LA158_0>=BAND && LA158_0<=BXOR)||(LA158_0>=LT && LA158_0<=GE)||(LA158_0>=MINUS && LA158_0<=MOD)) ) {
                        alt158=1;
                    }
                    else if ( (LA158_0==SUBSELECT_GROUP_EXPR) ) {
                        alt158=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 158, 0, input);

                        throw nvae;
                    }
                    switch (alt158) {
                        case 1 :
                            // EsperEPL2Ast.g:452:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:452:59: ( valueExpr )*
                            loop157:
                            do {
                                int alt157=2;
                                int LA157_0 = input.LA(1);

                                if ( ((LA157_0>=IN_SET && LA157_0<=REGEXP)||LA157_0==NOT_EXPR||(LA157_0>=SUM && LA157_0<=AVG)||(LA157_0>=COALESCE && LA157_0<=COUNT)||(LA157_0>=CASE && LA157_0<=CASE2)||(LA157_0>=PREVIOUS && LA157_0<=EXISTS)||(LA157_0>=INSTANCEOF && LA157_0<=CURRENT_TIMESTAMP)||LA157_0==NEWKW||(LA157_0>=EVAL_AND_EXPR && LA157_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA157_0==EVENT_PROP_EXPR||LA157_0==CONCAT||(LA157_0>=LIB_FUNC_CHAIN && LA157_0<=DOT_EXPR)||LA157_0==ARRAY_EXPR||(LA157_0>=NOT_IN_SET && LA157_0<=NOT_REGEXP)||(LA157_0>=IN_RANGE && LA157_0<=SUBSELECT_EXPR)||(LA157_0>=EXISTS_SUBSELECT_EXPR && LA157_0<=NOT_IN_SUBSELECT_EXPR)||LA157_0==SUBSTITUTION||(LA157_0>=FIRST_AGGREG && LA157_0<=WINDOW_AGGREG)||(LA157_0>=INT_TYPE && LA157_0<=NULL_TYPE)||(LA157_0>=STAR && LA157_0<=PLUS)||(LA157_0>=BAND && LA157_0<=BXOR)||(LA157_0>=LT && LA157_0<=GE)||(LA157_0>=MINUS && LA157_0<=MOD)) ) {
                                    alt157=1;
                                }


                                switch (alt157) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:452:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2899);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop157;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:452:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2904);
                            subSelectGroupExpr();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(jge); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:453:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2917); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2919);
                    valueExpr();

                    state._fsp--;

                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:453:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt160=2;
                    int LA160_0 = input.LA(1);

                    if ( (LA160_0==UP||(LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||LA160_0==NEWKW||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||LA160_0==CONCAT||(LA160_0>=LIB_FUNC_CHAIN && LA160_0<=DOT_EXPR)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=FIRST_AGGREG && LA160_0<=WINDOW_AGGREG)||(LA160_0>=INT_TYPE && LA160_0<=NULL_TYPE)||(LA160_0>=STAR && LA160_0<=PLUS)||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=MINUS && LA160_0<=MOD)) ) {
                        alt160=1;
                    }
                    else if ( (LA160_0==SUBSELECT_GROUP_EXPR) ) {
                        alt160=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 160, 0, input);

                        throw nvae;
                    }
                    switch (alt160) {
                        case 1 :
                            // EsperEPL2Ast.g:453:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:453:63: ( valueExpr )*
                            loop159:
                            do {
                                int alt159=2;
                                int LA159_0 = input.LA(1);

                                if ( ((LA159_0>=IN_SET && LA159_0<=REGEXP)||LA159_0==NOT_EXPR||(LA159_0>=SUM && LA159_0<=AVG)||(LA159_0>=COALESCE && LA159_0<=COUNT)||(LA159_0>=CASE && LA159_0<=CASE2)||(LA159_0>=PREVIOUS && LA159_0<=EXISTS)||(LA159_0>=INSTANCEOF && LA159_0<=CURRENT_TIMESTAMP)||LA159_0==NEWKW||(LA159_0>=EVAL_AND_EXPR && LA159_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA159_0==EVENT_PROP_EXPR||LA159_0==CONCAT||(LA159_0>=LIB_FUNC_CHAIN && LA159_0<=DOT_EXPR)||LA159_0==ARRAY_EXPR||(LA159_0>=NOT_IN_SET && LA159_0<=NOT_REGEXP)||(LA159_0>=IN_RANGE && LA159_0<=SUBSELECT_EXPR)||(LA159_0>=EXISTS_SUBSELECT_EXPR && LA159_0<=NOT_IN_SUBSELECT_EXPR)||LA159_0==SUBSTITUTION||(LA159_0>=FIRST_AGGREG && LA159_0<=WINDOW_AGGREG)||(LA159_0>=INT_TYPE && LA159_0<=NULL_TYPE)||(LA159_0>=STAR && LA159_0<=PLUS)||(LA159_0>=BAND && LA159_0<=BXOR)||(LA159_0>=LT && LA159_0<=GE)||(LA159_0>=MINUS && LA159_0<=MOD)) ) {
                                    alt159=1;
                                }


                                switch (alt159) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:453:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2930);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop159;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:453:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2935);
                            subSelectGroupExpr();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(jgne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:454:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2948); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2950);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:455:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2961);
                    relationalExpr();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "evalExprChoice"


    // $ANTLR start "valueExpr"
    // EsperEPL2Ast.g:458:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFuncChain | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr | dotExpr | newExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:459:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFuncChain | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr | dotExpr | newExpr )
            int alt162=18;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt162=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt162=2;
                }
                break;
            case CONCAT:
            case STAR:
            case BOR:
            case PLUS:
            case BAND:
            case BXOR:
            case MINUS:
            case DIV:
            case MOD:
                {
                alt162=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt162=4;
                }
                break;
            case NOT_EXPR:
            case EVAL_AND_EXPR:
            case EVAL_OR_EXPR:
            case EVAL_EQUALS_EXPR:
            case EVAL_NOTEQUALS_EXPR:
            case EVAL_EQUALS_GROUP_EXPR:
            case EVAL_NOTEQUALS_GROUP_EXPR:
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt162=5;
                }
                break;
            case SUM:
            case AVG:
            case COALESCE:
            case MEDIAN:
            case STDDEV:
            case AVEDEV:
            case COUNT:
            case PREVIOUS:
            case PREVIOUSTAIL:
            case PREVIOUSCOUNT:
            case PREVIOUSWINDOW:
            case PRIOR:
            case EXISTS:
            case INSTANCEOF:
            case TYPEOF:
            case CAST:
            case CURRENT_TIMESTAMP:
            case FIRST_AGGREG:
            case LAST_AGGREG:
            case WINDOW_AGGREG:
                {
                alt162=6;
                }
                break;
            case LIB_FUNC_CHAIN:
                {
                alt162=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt162=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt162=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt162=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt162=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt162=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt162=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt162=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt162=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt162=16;
                }
                break;
            case DOT_EXPR:
                {
                alt162=17;
                }
                break;
            case NEWKW:
                {
                alt162=18;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }

            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:459:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2974);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:460:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2980);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:461:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2986);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:462:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2993);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:463:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr3002);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:464:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr3007);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:465:7: libFuncChain
                    {
                    pushFollow(FOLLOW_libFuncChain_in_valueExpr3015);
                    libFuncChain();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:466:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr3020);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:467:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr3025);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:468:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr3031);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:469:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr3036);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:470:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr3041);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:471:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr3046);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:472:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr3051);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:473:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr3057);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:474:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr3064);
                    subSelectExistsExpr();

                    state._fsp--;


                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:475:4: dotExpr
                    {
                    pushFollow(FOLLOW_dotExpr_in_valueExpr3069);
                    dotExpr();

                    state._fsp--;


                    }
                    break;
                case 18 :
                    // EsperEPL2Ast.g:476:4: newExpr
                    {
                    pushFollow(FOLLOW_newExpr_in_valueExpr3074);
                    newExpr();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "valueExpr"


    // $ANTLR start "valueExprWithTime"
    // EsperEPL2Ast.g:479:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:480:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt164=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt164=1;
                }
                break;
            case LW:
                {
                alt164=2;
                }
                break;
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
            case PREVIOUSTAIL:
            case PREVIOUSCOUNT:
            case PREVIOUSWINDOW:
            case PRIOR:
            case EXISTS:
            case INSTANCEOF:
            case TYPEOF:
            case CAST:
            case CURRENT_TIMESTAMP:
            case NEWKW:
            case EVAL_AND_EXPR:
            case EVAL_OR_EXPR:
            case EVAL_EQUALS_EXPR:
            case EVAL_NOTEQUALS_EXPR:
            case EVAL_EQUALS_GROUP_EXPR:
            case EVAL_NOTEQUALS_GROUP_EXPR:
            case EVENT_PROP_EXPR:
            case CONCAT:
            case LIB_FUNC_CHAIN:
            case DOT_EXPR:
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
            case SUBSTITUTION:
            case FIRST_AGGREG:
            case LAST_AGGREG:
            case WINDOW_AGGREG:
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
            case STAR:
            case BOR:
            case PLUS:
            case BAND:
            case BXOR:
            case LT:
            case GT:
            case LE:
            case GE:
            case MINUS:
            case DIV:
            case MOD:
                {
                alt164=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt164=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt164=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt164=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt164=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt164=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt164=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt164=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt164=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 164, 0, input);

                throw nvae;
            }

            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:480:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime3087); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:481:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime3096); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:482:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime3103);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:483:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime3111); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime3113);
                    valueExpr();

                    state._fsp--;

                    if ( (input.LA(1)>=ASC && input.LA(1)<=DESC) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                     leaveNode(ordered); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:484:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime3128);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:485:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime3134);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:486:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime3139);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:487:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime3144);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:488:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime3154); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:488:29: ( numericParameterList )+
                    int cnt163=0;
                    loop163:
                    do {
                        int alt163=2;
                        int LA163_0 = input.LA(1);

                        if ( (LA163_0==NUMERIC_PARAM_RANGE||LA163_0==NUMERIC_PARAM_FREQUENCY||(LA163_0>=INT_TYPE && LA163_0<=NULL_TYPE)) ) {
                            alt163=1;
                        }


                        switch (alt163) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:488:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime3156);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt163 >= 1 ) break loop163;
                                EarlyExitException eee =
                                    new EarlyExitException(163, input);
                                throw eee;
                        }
                        cnt163++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:489:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime3167); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:490:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime3174);
                    timePeriod();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "valueExprWithTime"


    // $ANTLR start "numericParameterList"
    // EsperEPL2Ast.g:493:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:494:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt165=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt165=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt165=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt165=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 165, 0, input);

                throw nvae;
            }

            switch (alt165) {
                case 1 :
                    // EsperEPL2Ast.g:494:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList3187);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:495:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList3194);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:496:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList3200);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "numericParameterList"


    // $ANTLR start "rangeOperator"
    // EsperEPL2Ast.g:499:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:500:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:500:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3216); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:500:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt166=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt166=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt166=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt166=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 166, 0, input);

                throw nvae;
            }

            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:500:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3219);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:500:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3222);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:500:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3225);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:500:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt167=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt167=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt167=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt167=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 167, 0, input);

                throw nvae;
            }

            switch (alt167) {
                case 1 :
                    // EsperEPL2Ast.g:500:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3229);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:500:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3232);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:500:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3235);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(r); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "rangeOperator"


    // $ANTLR start "frequencyOperator"
    // EsperEPL2Ast.g:503:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:504:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:504:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3256); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:504:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt168=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt168=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt168=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt168=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 168, 0, input);

                throw nvae;
            }

            switch (alt168) {
                case 1 :
                    // EsperEPL2Ast.g:504:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator3259);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:504:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator3262);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:504:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator3265);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(f); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "frequencyOperator"


    // $ANTLR start "lastOperator"
    // EsperEPL2Ast.g:507:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:508:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:508:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator3284); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:508:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt169=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt169=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt169=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt169=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 169, 0, input);

                throw nvae;
            }

            switch (alt169) {
                case 1 :
                    // EsperEPL2Ast.g:508:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator3287);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:508:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator3290);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:508:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator3293);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(l); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "lastOperator"


    // $ANTLR start "weekDayOperator"
    // EsperEPL2Ast.g:511:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:512:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:512:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3312); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:512:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt170=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt170=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt170=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt170=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 170, 0, input);

                throw nvae;
            }

            switch (alt170) {
                case 1 :
                    // EsperEPL2Ast.g:512:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator3315);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:512:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator3318);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:512:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator3321);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

             leaveNode(w); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "weekDayOperator"


    // $ANTLR start "subSelectGroupExpr"
    // EsperEPL2Ast.g:515:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:516:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:516:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3342); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr3344);
            subQueryExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subSelectGroupExpr"


    // $ANTLR start "subSelectRowExpr"
    // EsperEPL2Ast.g:519:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:520:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:520:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3363); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr3365);
            subQueryExpr();

            state._fsp--;


            match(input, Token.UP, null); 
            leaveNode(s);

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subSelectRowExpr"


    // $ANTLR start "subSelectExistsExpr"
    // EsperEPL2Ast.g:523:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:524:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:524:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3384); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr3386);
            subQueryExpr();

            state._fsp--;


            match(input, Token.UP, null); 
            leaveNode(e);

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subSelectExistsExpr"


    // $ANTLR start "subSelectInExpr"
    // EsperEPL2Ast.g:527:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:528:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt171=2;
            int LA171_0 = input.LA(1);

            if ( (LA171_0==IN_SUBSELECT_EXPR) ) {
                alt171=1;
            }
            else if ( (LA171_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt171=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 171, 0, input);

                throw nvae;
            }
            switch (alt171) {
                case 1 :
                    // EsperEPL2Ast.g:528:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3405); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3407);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3409);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:529:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3421); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3423);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3425);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subSelectInExpr"


    // $ANTLR start "subSelectInQueryExpr"
    // EsperEPL2Ast.g:532:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:533:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:533:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3444); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3446);
            subQueryExpr();

            state._fsp--;


            match(input, Token.UP, null); 
            leaveNode(i);

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subSelectInQueryExpr"


    // $ANTLR start "subQueryExpr"
    // EsperEPL2Ast.g:536:1: subQueryExpr : ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:537:2: ( ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:537:4: ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:537:4: ( DISTINCT )?
            int alt172=2;
            int LA172_0 = input.LA(1);

            if ( (LA172_0==DISTINCT) ) {
                alt172=1;
            }
            switch (alt172) {
                case 1 :
                    // EsperEPL2Ast.g:537:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3462); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_subQueryExpr3465);
            selectionList();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3467);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:537:48: ( whereClause[true] )?
            int alt173=2;
            int LA173_0 = input.LA(1);

            if ( (LA173_0==WHERE_EXPR) ) {
                alt173=1;
            }
            switch (alt173) {
                case 1 :
                    // EsperEPL2Ast.g:537:49: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3470);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subQueryExpr"


    // $ANTLR start "subSelectFilterExpr"
    // EsperEPL2Ast.g:540:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:541:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:541:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3488); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3490);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:541:36: ( viewListExpr )?
            int alt174=2;
            int LA174_0 = input.LA(1);

            if ( (LA174_0==VIEW_EXPR) ) {
                alt174=1;
            }
            switch (alt174) {
                case 1 :
                    // EsperEPL2Ast.g:541:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3493);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:541:52: ( IDENT )?
            int alt175=2;
            int LA175_0 = input.LA(1);

            if ( (LA175_0==IDENT) ) {
                alt175=1;
            }
            switch (alt175) {
                case 1 :
                    // EsperEPL2Ast.g:541:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3498); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:541:61: ( RETAINUNION )?
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( (LA176_0==RETAINUNION) ) {
                alt176=1;
            }
            switch (alt176) {
                case 1 :
                    // EsperEPL2Ast.g:541:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3502); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:541:74: ( RETAININTERSECTION )?
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==RETAININTERSECTION) ) {
                alt177=1;
            }
            switch (alt177) {
                case 1 :
                    // EsperEPL2Ast.g:541:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3505); 

                    }
                    break;

            }

             leaveNode(v); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "subSelectFilterExpr"


    // $ANTLR start "caseExpr"
    // EsperEPL2Ast.g:544:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:545:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt180=2;
            int LA180_0 = input.LA(1);

            if ( (LA180_0==CASE) ) {
                alt180=1;
            }
            else if ( (LA180_0==CASE2) ) {
                alt180=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 180, 0, input);

                throw nvae;
            }
            switch (alt180) {
                case 1 :
                    // EsperEPL2Ast.g:545:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3525); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:545:13: ( valueExpr )*
                        loop178:
                        do {
                            int alt178=2;
                            int LA178_0 = input.LA(1);

                            if ( ((LA178_0>=IN_SET && LA178_0<=REGEXP)||LA178_0==NOT_EXPR||(LA178_0>=SUM && LA178_0<=AVG)||(LA178_0>=COALESCE && LA178_0<=COUNT)||(LA178_0>=CASE && LA178_0<=CASE2)||(LA178_0>=PREVIOUS && LA178_0<=EXISTS)||(LA178_0>=INSTANCEOF && LA178_0<=CURRENT_TIMESTAMP)||LA178_0==NEWKW||(LA178_0>=EVAL_AND_EXPR && LA178_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA178_0==EVENT_PROP_EXPR||LA178_0==CONCAT||(LA178_0>=LIB_FUNC_CHAIN && LA178_0<=DOT_EXPR)||LA178_0==ARRAY_EXPR||(LA178_0>=NOT_IN_SET && LA178_0<=NOT_REGEXP)||(LA178_0>=IN_RANGE && LA178_0<=SUBSELECT_EXPR)||(LA178_0>=EXISTS_SUBSELECT_EXPR && LA178_0<=NOT_IN_SUBSELECT_EXPR)||LA178_0==SUBSTITUTION||(LA178_0>=FIRST_AGGREG && LA178_0<=WINDOW_AGGREG)||(LA178_0>=INT_TYPE && LA178_0<=NULL_TYPE)||(LA178_0>=STAR && LA178_0<=PLUS)||(LA178_0>=BAND && LA178_0<=BXOR)||(LA178_0>=LT && LA178_0<=GE)||(LA178_0>=MINUS && LA178_0<=MOD)) ) {
                                alt178=1;
                            }


                            switch (alt178) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:545:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3528);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop178;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:546:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3541); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:546:14: ( valueExpr )*
                        loop179:
                        do {
                            int alt179=2;
                            int LA179_0 = input.LA(1);

                            if ( ((LA179_0>=IN_SET && LA179_0<=REGEXP)||LA179_0==NOT_EXPR||(LA179_0>=SUM && LA179_0<=AVG)||(LA179_0>=COALESCE && LA179_0<=COUNT)||(LA179_0>=CASE && LA179_0<=CASE2)||(LA179_0>=PREVIOUS && LA179_0<=EXISTS)||(LA179_0>=INSTANCEOF && LA179_0<=CURRENT_TIMESTAMP)||LA179_0==NEWKW||(LA179_0>=EVAL_AND_EXPR && LA179_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA179_0==EVENT_PROP_EXPR||LA179_0==CONCAT||(LA179_0>=LIB_FUNC_CHAIN && LA179_0<=DOT_EXPR)||LA179_0==ARRAY_EXPR||(LA179_0>=NOT_IN_SET && LA179_0<=NOT_REGEXP)||(LA179_0>=IN_RANGE && LA179_0<=SUBSELECT_EXPR)||(LA179_0>=EXISTS_SUBSELECT_EXPR && LA179_0<=NOT_IN_SUBSELECT_EXPR)||LA179_0==SUBSTITUTION||(LA179_0>=FIRST_AGGREG && LA179_0<=WINDOW_AGGREG)||(LA179_0>=INT_TYPE && LA179_0<=NULL_TYPE)||(LA179_0>=STAR && LA179_0<=PLUS)||(LA179_0>=BAND && LA179_0<=BXOR)||(LA179_0>=LT && LA179_0<=GE)||(LA179_0>=MINUS && LA179_0<=MOD)) ) {
                                alt179=1;
                            }


                            switch (alt179) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:546:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3544);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop179;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "caseExpr"


    // $ANTLR start "inExpr"
    // EsperEPL2Ast.g:549:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:550:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt183=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt183=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt183=2;
                }
                break;
            case IN_RANGE:
                {
                alt183=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt183=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 183, 0, input);

                throw nvae;
            }

            switch (alt183) {
                case 1 :
                    // EsperEPL2Ast.g:550:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3564); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3566);
                    valueExpr();

                    state._fsp--;

                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_valueExpr_in_inExpr3574);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:550:51: ( valueExpr )*
                    loop181:
                    do {
                        int alt181=2;
                        int LA181_0 = input.LA(1);

                        if ( ((LA181_0>=IN_SET && LA181_0<=REGEXP)||LA181_0==NOT_EXPR||(LA181_0>=SUM && LA181_0<=AVG)||(LA181_0>=COALESCE && LA181_0<=COUNT)||(LA181_0>=CASE && LA181_0<=CASE2)||(LA181_0>=PREVIOUS && LA181_0<=EXISTS)||(LA181_0>=INSTANCEOF && LA181_0<=CURRENT_TIMESTAMP)||LA181_0==NEWKW||(LA181_0>=EVAL_AND_EXPR && LA181_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA181_0==EVENT_PROP_EXPR||LA181_0==CONCAT||(LA181_0>=LIB_FUNC_CHAIN && LA181_0<=DOT_EXPR)||LA181_0==ARRAY_EXPR||(LA181_0>=NOT_IN_SET && LA181_0<=NOT_REGEXP)||(LA181_0>=IN_RANGE && LA181_0<=SUBSELECT_EXPR)||(LA181_0>=EXISTS_SUBSELECT_EXPR && LA181_0<=NOT_IN_SUBSELECT_EXPR)||LA181_0==SUBSTITUTION||(LA181_0>=FIRST_AGGREG && LA181_0<=WINDOW_AGGREG)||(LA181_0>=INT_TYPE && LA181_0<=NULL_TYPE)||(LA181_0>=STAR && LA181_0<=PLUS)||(LA181_0>=BAND && LA181_0<=BXOR)||(LA181_0>=LT && LA181_0<=GE)||(LA181_0>=MINUS && LA181_0<=MOD)) ) {
                            alt181=1;
                        }


                        switch (alt181) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:550:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3577);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop181;
                        }
                    } while (true);

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 
                     leaveNode(i); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:551:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3596); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3598);
                    valueExpr();

                    state._fsp--;

                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_valueExpr_in_inExpr3606);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:551:55: ( valueExpr )*
                    loop182:
                    do {
                        int alt182=2;
                        int LA182_0 = input.LA(1);

                        if ( ((LA182_0>=IN_SET && LA182_0<=REGEXP)||LA182_0==NOT_EXPR||(LA182_0>=SUM && LA182_0<=AVG)||(LA182_0>=COALESCE && LA182_0<=COUNT)||(LA182_0>=CASE && LA182_0<=CASE2)||(LA182_0>=PREVIOUS && LA182_0<=EXISTS)||(LA182_0>=INSTANCEOF && LA182_0<=CURRENT_TIMESTAMP)||LA182_0==NEWKW||(LA182_0>=EVAL_AND_EXPR && LA182_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA182_0==EVENT_PROP_EXPR||LA182_0==CONCAT||(LA182_0>=LIB_FUNC_CHAIN && LA182_0<=DOT_EXPR)||LA182_0==ARRAY_EXPR||(LA182_0>=NOT_IN_SET && LA182_0<=NOT_REGEXP)||(LA182_0>=IN_RANGE && LA182_0<=SUBSELECT_EXPR)||(LA182_0>=EXISTS_SUBSELECT_EXPR && LA182_0<=NOT_IN_SUBSELECT_EXPR)||LA182_0==SUBSTITUTION||(LA182_0>=FIRST_AGGREG && LA182_0<=WINDOW_AGGREG)||(LA182_0>=INT_TYPE && LA182_0<=NULL_TYPE)||(LA182_0>=STAR && LA182_0<=PLUS)||(LA182_0>=BAND && LA182_0<=BXOR)||(LA182_0>=LT && LA182_0<=GE)||(LA182_0>=MINUS && LA182_0<=MOD)) ) {
                            alt182=1;
                        }


                        switch (alt182) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:551:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3609);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop182;
                        }
                    } while (true);

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 
                     leaveNode(i); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:552:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3628); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3630);
                    valueExpr();

                    state._fsp--;

                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_valueExpr_in_inExpr3638);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3640);
                    valueExpr();

                    state._fsp--;

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 
                     leaveNode(i); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:553:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3657); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3659);
                    valueExpr();

                    state._fsp--;

                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    pushFollow(FOLLOW_valueExpr_in_inExpr3667);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3669);
                    valueExpr();

                    state._fsp--;

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 
                     leaveNode(i); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "inExpr"


    // $ANTLR start "betweenExpr"
    // EsperEPL2Ast.g:556:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:557:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt185=2;
            int LA185_0 = input.LA(1);

            if ( (LA185_0==BETWEEN) ) {
                alt185=1;
            }
            else if ( (LA185_0==NOT_BETWEEN) ) {
                alt185=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 185, 0, input);

                throw nvae;
            }
            switch (alt185) {
                case 1 :
                    // EsperEPL2Ast.g:557:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3694); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3696);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3698);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3700);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:558:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3711); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3713);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3715);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:558:40: ( valueExpr )*
                    loop184:
                    do {
                        int alt184=2;
                        int LA184_0 = input.LA(1);

                        if ( ((LA184_0>=IN_SET && LA184_0<=REGEXP)||LA184_0==NOT_EXPR||(LA184_0>=SUM && LA184_0<=AVG)||(LA184_0>=COALESCE && LA184_0<=COUNT)||(LA184_0>=CASE && LA184_0<=CASE2)||(LA184_0>=PREVIOUS && LA184_0<=EXISTS)||(LA184_0>=INSTANCEOF && LA184_0<=CURRENT_TIMESTAMP)||LA184_0==NEWKW||(LA184_0>=EVAL_AND_EXPR && LA184_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA184_0==EVENT_PROP_EXPR||LA184_0==CONCAT||(LA184_0>=LIB_FUNC_CHAIN && LA184_0<=DOT_EXPR)||LA184_0==ARRAY_EXPR||(LA184_0>=NOT_IN_SET && LA184_0<=NOT_REGEXP)||(LA184_0>=IN_RANGE && LA184_0<=SUBSELECT_EXPR)||(LA184_0>=EXISTS_SUBSELECT_EXPR && LA184_0<=NOT_IN_SUBSELECT_EXPR)||LA184_0==SUBSTITUTION||(LA184_0>=FIRST_AGGREG && LA184_0<=WINDOW_AGGREG)||(LA184_0>=INT_TYPE && LA184_0<=NULL_TYPE)||(LA184_0>=STAR && LA184_0<=PLUS)||(LA184_0>=BAND && LA184_0<=BXOR)||(LA184_0>=LT && LA184_0<=GE)||(LA184_0>=MINUS && LA184_0<=MOD)) ) {
                            alt184=1;
                        }


                        switch (alt184) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:558:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3718);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop184;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "betweenExpr"


    // $ANTLR start "likeExpr"
    // EsperEPL2Ast.g:561:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:562:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt188=2;
            int LA188_0 = input.LA(1);

            if ( (LA188_0==LIKE) ) {
                alt188=1;
            }
            else if ( (LA188_0==NOT_LIKE) ) {
                alt188=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 188, 0, input);

                throw nvae;
            }
            switch (alt188) {
                case 1 :
                    // EsperEPL2Ast.g:562:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3738); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3740);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3742);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:562:33: ( valueExpr )?
                    int alt186=2;
                    int LA186_0 = input.LA(1);

                    if ( ((LA186_0>=IN_SET && LA186_0<=REGEXP)||LA186_0==NOT_EXPR||(LA186_0>=SUM && LA186_0<=AVG)||(LA186_0>=COALESCE && LA186_0<=COUNT)||(LA186_0>=CASE && LA186_0<=CASE2)||(LA186_0>=PREVIOUS && LA186_0<=EXISTS)||(LA186_0>=INSTANCEOF && LA186_0<=CURRENT_TIMESTAMP)||LA186_0==NEWKW||(LA186_0>=EVAL_AND_EXPR && LA186_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA186_0==EVENT_PROP_EXPR||LA186_0==CONCAT||(LA186_0>=LIB_FUNC_CHAIN && LA186_0<=DOT_EXPR)||LA186_0==ARRAY_EXPR||(LA186_0>=NOT_IN_SET && LA186_0<=NOT_REGEXP)||(LA186_0>=IN_RANGE && LA186_0<=SUBSELECT_EXPR)||(LA186_0>=EXISTS_SUBSELECT_EXPR && LA186_0<=NOT_IN_SUBSELECT_EXPR)||LA186_0==SUBSTITUTION||(LA186_0>=FIRST_AGGREG && LA186_0<=WINDOW_AGGREG)||(LA186_0>=INT_TYPE && LA186_0<=NULL_TYPE)||(LA186_0>=STAR && LA186_0<=PLUS)||(LA186_0>=BAND && LA186_0<=BXOR)||(LA186_0>=LT && LA186_0<=GE)||(LA186_0>=MINUS && LA186_0<=MOD)) ) {
                        alt186=1;
                    }
                    switch (alt186) {
                        case 1 :
                            // EsperEPL2Ast.g:562:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3745);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:563:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3758); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3760);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3762);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:563:37: ( valueExpr )?
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( ((LA187_0>=IN_SET && LA187_0<=REGEXP)||LA187_0==NOT_EXPR||(LA187_0>=SUM && LA187_0<=AVG)||(LA187_0>=COALESCE && LA187_0<=COUNT)||(LA187_0>=CASE && LA187_0<=CASE2)||(LA187_0>=PREVIOUS && LA187_0<=EXISTS)||(LA187_0>=INSTANCEOF && LA187_0<=CURRENT_TIMESTAMP)||LA187_0==NEWKW||(LA187_0>=EVAL_AND_EXPR && LA187_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA187_0==EVENT_PROP_EXPR||LA187_0==CONCAT||(LA187_0>=LIB_FUNC_CHAIN && LA187_0<=DOT_EXPR)||LA187_0==ARRAY_EXPR||(LA187_0>=NOT_IN_SET && LA187_0<=NOT_REGEXP)||(LA187_0>=IN_RANGE && LA187_0<=SUBSELECT_EXPR)||(LA187_0>=EXISTS_SUBSELECT_EXPR && LA187_0<=NOT_IN_SUBSELECT_EXPR)||LA187_0==SUBSTITUTION||(LA187_0>=FIRST_AGGREG && LA187_0<=WINDOW_AGGREG)||(LA187_0>=INT_TYPE && LA187_0<=NULL_TYPE)||(LA187_0>=STAR && LA187_0<=PLUS)||(LA187_0>=BAND && LA187_0<=BXOR)||(LA187_0>=LT && LA187_0<=GE)||(LA187_0>=MINUS && LA187_0<=MOD)) ) {
                        alt187=1;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:563:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3765);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(l); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "likeExpr"


    // $ANTLR start "regExpExpr"
    // EsperEPL2Ast.g:566:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:567:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt189=2;
            int LA189_0 = input.LA(1);

            if ( (LA189_0==REGEXP) ) {
                alt189=1;
            }
            else if ( (LA189_0==NOT_REGEXP) ) {
                alt189=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 189, 0, input);

                throw nvae;
            }
            switch (alt189) {
                case 1 :
                    // EsperEPL2Ast.g:567:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3784); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3786);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3788);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:568:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3799); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3801);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3803);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "regExpExpr"


    // $ANTLR start "builtinFunc"
    // EsperEPL2Ast.g:571:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= AVG ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ( aggregationFilterExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= STDDEV ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= LAST_AGGREG ( DISTINCT )? ( accessValueExpr )? ( valueExpr )? ) | ^(f= FIRST_AGGREG ( DISTINCT )? ( accessValueExpr )? ( valueExpr )? ) | ^(f= WINDOW_AGGREG ( DISTINCT )? ( accessValueExpr )? ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? ) | ^(f= PREVIOUSCOUNT valueExpr ) | ^(f= PREVIOUSWINDOW valueExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= TYPEOF valueExpr ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:572:2: ( ^(f= SUM ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= AVG ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ( aggregationFilterExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= STDDEV ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ( aggregationFilterExpr )? ) | ^(f= LAST_AGGREG ( DISTINCT )? ( accessValueExpr )? ( valueExpr )? ) | ^(f= FIRST_AGGREG ( DISTINCT )? ( accessValueExpr )? ( valueExpr )? ) | ^(f= WINDOW_AGGREG ( DISTINCT )? ( accessValueExpr )? ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? ) | ^(f= PREVIOUSCOUNT valueExpr ) | ^(f= PREVIOUSWINDOW valueExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= TYPEOF valueExpr ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt215=20;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt215=1;
                }
                break;
            case AVG:
                {
                alt215=2;
                }
                break;
            case COUNT:
                {
                alt215=3;
                }
                break;
            case MEDIAN:
                {
                alt215=4;
                }
                break;
            case STDDEV:
                {
                alt215=5;
                }
                break;
            case AVEDEV:
                {
                alt215=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt215=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt215=8;
                }
                break;
            case WINDOW_AGGREG:
                {
                alt215=9;
                }
                break;
            case COALESCE:
                {
                alt215=10;
                }
                break;
            case PREVIOUS:
                {
                alt215=11;
                }
                break;
            case PREVIOUSTAIL:
                {
                alt215=12;
                }
                break;
            case PREVIOUSCOUNT:
                {
                alt215=13;
                }
                break;
            case PREVIOUSWINDOW:
                {
                alt215=14;
                }
                break;
            case PRIOR:
                {
                alt215=15;
                }
                break;
            case INSTANCEOF:
                {
                alt215=16;
                }
                break;
            case TYPEOF:
                {
                alt215=17;
                }
                break;
            case CAST:
                {
                alt215=18;
                }
                break;
            case EXISTS:
                {
                alt215=19;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt215=20;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 215, 0, input);

                throw nvae;
            }

            switch (alt215) {
                case 1 :
                    // EsperEPL2Ast.g:572:5: ^(f= SUM ( DISTINCT )? valueExpr ( aggregationFilterExpr )? )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3822); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:572:13: ( DISTINCT )?
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( (LA190_0==DISTINCT) ) {
                        alt190=1;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:572:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3825); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3829);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:572:35: ( aggregationFilterExpr )?
                    int alt191=2;
                    int LA191_0 = input.LA(1);

                    if ( (LA191_0==AGG_FILTER_EXPR) ) {
                        alt191=1;
                    }
                    switch (alt191) {
                        case 1 :
                            // EsperEPL2Ast.g:572:35: aggregationFilterExpr
                            {
                            pushFollow(FOLLOW_aggregationFilterExpr_in_builtinFunc3831);
                            aggregationFilterExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:573:4: ^(f= AVG ( DISTINCT )? valueExpr ( aggregationFilterExpr )? )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3843); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:573:12: ( DISTINCT )?
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( (LA192_0==DISTINCT) ) {
                        alt192=1;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:573:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3846); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3850);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:573:34: ( aggregationFilterExpr )?
                    int alt193=2;
                    int LA193_0 = input.LA(1);

                    if ( (LA193_0==AGG_FILTER_EXPR) ) {
                        alt193=1;
                    }
                    switch (alt193) {
                        case 1 :
                            // EsperEPL2Ast.g:573:34: aggregationFilterExpr
                            {
                            pushFollow(FOLLOW_aggregationFilterExpr_in_builtinFunc3852);
                            aggregationFilterExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:574:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? ( aggregationFilterExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3864); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:574:14: ( ( DISTINCT )? valueExpr )?
                        int alt195=2;
                        int LA195_0 = input.LA(1);

                        if ( ((LA195_0>=IN_SET && LA195_0<=REGEXP)||LA195_0==NOT_EXPR||(LA195_0>=SUM && LA195_0<=AVG)||(LA195_0>=COALESCE && LA195_0<=COUNT)||(LA195_0>=CASE && LA195_0<=CASE2)||LA195_0==DISTINCT||(LA195_0>=PREVIOUS && LA195_0<=EXISTS)||(LA195_0>=INSTANCEOF && LA195_0<=CURRENT_TIMESTAMP)||LA195_0==NEWKW||(LA195_0>=EVAL_AND_EXPR && LA195_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA195_0==EVENT_PROP_EXPR||LA195_0==CONCAT||(LA195_0>=LIB_FUNC_CHAIN && LA195_0<=DOT_EXPR)||LA195_0==ARRAY_EXPR||(LA195_0>=NOT_IN_SET && LA195_0<=NOT_REGEXP)||(LA195_0>=IN_RANGE && LA195_0<=SUBSELECT_EXPR)||(LA195_0>=EXISTS_SUBSELECT_EXPR && LA195_0<=NOT_IN_SUBSELECT_EXPR)||LA195_0==SUBSTITUTION||(LA195_0>=FIRST_AGGREG && LA195_0<=WINDOW_AGGREG)||(LA195_0>=INT_TYPE && LA195_0<=NULL_TYPE)||(LA195_0>=STAR && LA195_0<=PLUS)||(LA195_0>=BAND && LA195_0<=BXOR)||(LA195_0>=LT && LA195_0<=GE)||(LA195_0>=MINUS && LA195_0<=MOD)) ) {
                            alt195=1;
                        }
                        switch (alt195) {
                            case 1 :
                                // EsperEPL2Ast.g:574:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:574:15: ( DISTINCT )?
                                int alt194=2;
                                int LA194_0 = input.LA(1);

                                if ( (LA194_0==DISTINCT) ) {
                                    alt194=1;
                                }
                                switch (alt194) {
                                    case 1 :
                                        // EsperEPL2Ast.g:574:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3868); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3872);
                                valueExpr();

                                state._fsp--;


                                }
                                break;

                        }

                        // EsperEPL2Ast.g:574:39: ( aggregationFilterExpr )?
                        int alt196=2;
                        int LA196_0 = input.LA(1);

                        if ( (LA196_0==AGG_FILTER_EXPR) ) {
                            alt196=1;
                        }
                        switch (alt196) {
                            case 1 :
                                // EsperEPL2Ast.g:574:39: aggregationFilterExpr
                                {
                                pushFollow(FOLLOW_aggregationFilterExpr_in_builtinFunc3876);
                                aggregationFilterExpr();

                                state._fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                     leaveNode(f); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:575:4: ^(f= MEDIAN ( DISTINCT )? valueExpr ( aggregationFilterExpr )? )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3888); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:575:15: ( DISTINCT )?
                    int alt197=2;
                    int LA197_0 = input.LA(1);

                    if ( (LA197_0==DISTINCT) ) {
                        alt197=1;
                    }
                    switch (alt197) {
                        case 1 :
                            // EsperEPL2Ast.g:575:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3891); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3895);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:575:37: ( aggregationFilterExpr )?
                    int alt198=2;
                    int LA198_0 = input.LA(1);

                    if ( (LA198_0==AGG_FILTER_EXPR) ) {
                        alt198=1;
                    }
                    switch (alt198) {
                        case 1 :
                            // EsperEPL2Ast.g:575:37: aggregationFilterExpr
                            {
                            pushFollow(FOLLOW_aggregationFilterExpr_in_builtinFunc3897);
                            aggregationFilterExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:576:4: ^(f= STDDEV ( DISTINCT )? valueExpr ( aggregationFilterExpr )? )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3909); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:576:15: ( DISTINCT )?
                    int alt199=2;
                    int LA199_0 = input.LA(1);

                    if ( (LA199_0==DISTINCT) ) {
                        alt199=1;
                    }
                    switch (alt199) {
                        case 1 :
                            // EsperEPL2Ast.g:576:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3912); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3916);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:576:37: ( aggregationFilterExpr )?
                    int alt200=2;
                    int LA200_0 = input.LA(1);

                    if ( (LA200_0==AGG_FILTER_EXPR) ) {
                        alt200=1;
                    }
                    switch (alt200) {
                        case 1 :
                            // EsperEPL2Ast.g:576:37: aggregationFilterExpr
                            {
                            pushFollow(FOLLOW_aggregationFilterExpr_in_builtinFunc3918);
                            aggregationFilterExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:577:4: ^(f= AVEDEV ( DISTINCT )? valueExpr ( aggregationFilterExpr )? )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3930); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:577:15: ( DISTINCT )?
                    int alt201=2;
                    int LA201_0 = input.LA(1);

                    if ( (LA201_0==DISTINCT) ) {
                        alt201=1;
                    }
                    switch (alt201) {
                        case 1 :
                            // EsperEPL2Ast.g:577:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3933); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3937);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:577:37: ( aggregationFilterExpr )?
                    int alt202=2;
                    int LA202_0 = input.LA(1);

                    if ( (LA202_0==AGG_FILTER_EXPR) ) {
                        alt202=1;
                    }
                    switch (alt202) {
                        case 1 :
                            // EsperEPL2Ast.g:577:37: aggregationFilterExpr
                            {
                            pushFollow(FOLLOW_aggregationFilterExpr_in_builtinFunc3939);
                            aggregationFilterExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:578:4: ^(f= LAST_AGGREG ( DISTINCT )? ( accessValueExpr )? ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3951); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:578:20: ( DISTINCT )?
                        int alt203=2;
                        int LA203_0 = input.LA(1);

                        if ( (LA203_0==DISTINCT) ) {
                            alt203=1;
                        }
                        switch (alt203) {
                            case 1 :
                                // EsperEPL2Ast.g:578:21: DISTINCT
                                {
                                match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3954); 

                                }
                                break;

                        }

                        // EsperEPL2Ast.g:578:32: ( accessValueExpr )?
                        int alt204=2;
                        int LA204_0 = input.LA(1);

                        if ( (LA204_0==ACCESS_AGG) ) {
                            alt204=1;
                        }
                        switch (alt204) {
                            case 1 :
                                // EsperEPL2Ast.g:578:32: accessValueExpr
                                {
                                pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3958);
                                accessValueExpr();

                                state._fsp--;


                                }
                                break;

                        }

                        // EsperEPL2Ast.g:578:49: ( valueExpr )?
                        int alt205=2;
                        int LA205_0 = input.LA(1);

                        if ( ((LA205_0>=IN_SET && LA205_0<=REGEXP)||LA205_0==NOT_EXPR||(LA205_0>=SUM && LA205_0<=AVG)||(LA205_0>=COALESCE && LA205_0<=COUNT)||(LA205_0>=CASE && LA205_0<=CASE2)||(LA205_0>=PREVIOUS && LA205_0<=EXISTS)||(LA205_0>=INSTANCEOF && LA205_0<=CURRENT_TIMESTAMP)||LA205_0==NEWKW||(LA205_0>=EVAL_AND_EXPR && LA205_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA205_0==EVENT_PROP_EXPR||LA205_0==CONCAT||(LA205_0>=LIB_FUNC_CHAIN && LA205_0<=DOT_EXPR)||LA205_0==ARRAY_EXPR||(LA205_0>=NOT_IN_SET && LA205_0<=NOT_REGEXP)||(LA205_0>=IN_RANGE && LA205_0<=SUBSELECT_EXPR)||(LA205_0>=EXISTS_SUBSELECT_EXPR && LA205_0<=NOT_IN_SUBSELECT_EXPR)||LA205_0==SUBSTITUTION||(LA205_0>=FIRST_AGGREG && LA205_0<=WINDOW_AGGREG)||(LA205_0>=INT_TYPE && LA205_0<=NULL_TYPE)||(LA205_0>=STAR && LA205_0<=PLUS)||(LA205_0>=BAND && LA205_0<=BXOR)||(LA205_0>=LT && LA205_0<=GE)||(LA205_0>=MINUS && LA205_0<=MOD)) ) {
                            alt205=1;
                        }
                        switch (alt205) {
                            case 1 :
                                // EsperEPL2Ast.g:578:49: valueExpr
                                {
                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3961);
                                valueExpr();

                                state._fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:579:4: ^(f= FIRST_AGGREG ( DISTINCT )? ( accessValueExpr )? ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3973); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:579:21: ( DISTINCT )?
                        int alt206=2;
                        int LA206_0 = input.LA(1);

                        if ( (LA206_0==DISTINCT) ) {
                            alt206=1;
                        }
                        switch (alt206) {
                            case 1 :
                                // EsperEPL2Ast.g:579:22: DISTINCT
                                {
                                match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3976); 

                                }
                                break;

                        }

                        // EsperEPL2Ast.g:579:33: ( accessValueExpr )?
                        int alt207=2;
                        int LA207_0 = input.LA(1);

                        if ( (LA207_0==ACCESS_AGG) ) {
                            alt207=1;
                        }
                        switch (alt207) {
                            case 1 :
                                // EsperEPL2Ast.g:579:33: accessValueExpr
                                {
                                pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3980);
                                accessValueExpr();

                                state._fsp--;


                                }
                                break;

                        }

                        // EsperEPL2Ast.g:579:50: ( valueExpr )?
                        int alt208=2;
                        int LA208_0 = input.LA(1);

                        if ( ((LA208_0>=IN_SET && LA208_0<=REGEXP)||LA208_0==NOT_EXPR||(LA208_0>=SUM && LA208_0<=AVG)||(LA208_0>=COALESCE && LA208_0<=COUNT)||(LA208_0>=CASE && LA208_0<=CASE2)||(LA208_0>=PREVIOUS && LA208_0<=EXISTS)||(LA208_0>=INSTANCEOF && LA208_0<=CURRENT_TIMESTAMP)||LA208_0==NEWKW||(LA208_0>=EVAL_AND_EXPR && LA208_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA208_0==EVENT_PROP_EXPR||LA208_0==CONCAT||(LA208_0>=LIB_FUNC_CHAIN && LA208_0<=DOT_EXPR)||LA208_0==ARRAY_EXPR||(LA208_0>=NOT_IN_SET && LA208_0<=NOT_REGEXP)||(LA208_0>=IN_RANGE && LA208_0<=SUBSELECT_EXPR)||(LA208_0>=EXISTS_SUBSELECT_EXPR && LA208_0<=NOT_IN_SUBSELECT_EXPR)||LA208_0==SUBSTITUTION||(LA208_0>=FIRST_AGGREG && LA208_0<=WINDOW_AGGREG)||(LA208_0>=INT_TYPE && LA208_0<=NULL_TYPE)||(LA208_0>=STAR && LA208_0<=PLUS)||(LA208_0>=BAND && LA208_0<=BXOR)||(LA208_0>=LT && LA208_0<=GE)||(LA208_0>=MINUS && LA208_0<=MOD)) ) {
                            alt208=1;
                        }
                        switch (alt208) {
                            case 1 :
                                // EsperEPL2Ast.g:579:50: valueExpr
                                {
                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3983);
                                valueExpr();

                                state._fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:580:4: ^(f= WINDOW_AGGREG ( DISTINCT )? ( accessValueExpr )? )
                    {
                    f=(CommonTree)match(input,WINDOW_AGGREG,FOLLOW_WINDOW_AGGREG_in_builtinFunc3995); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:580:22: ( DISTINCT )?
                        int alt209=2;
                        int LA209_0 = input.LA(1);

                        if ( (LA209_0==DISTINCT) ) {
                            alt209=1;
                        }
                        switch (alt209) {
                            case 1 :
                                // EsperEPL2Ast.g:580:23: DISTINCT
                                {
                                match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3998); 

                                }
                                break;

                        }

                        // EsperEPL2Ast.g:580:34: ( accessValueExpr )?
                        int alt210=2;
                        int LA210_0 = input.LA(1);

                        if ( (LA210_0==ACCESS_AGG) ) {
                            alt210=1;
                        }
                        switch (alt210) {
                            case 1 :
                                // EsperEPL2Ast.g:580:34: accessValueExpr
                                {
                                pushFollow(FOLLOW_accessValueExpr_in_builtinFunc4002);
                                accessValueExpr();

                                state._fsp--;


                                }
                                break;

                        }


                        match(input, Token.UP, null); 
                    }
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:581:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc4015); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4017);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4019);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:581:38: ( valueExpr )*
                    loop211:
                    do {
                        int alt211=2;
                        int LA211_0 = input.LA(1);

                        if ( ((LA211_0>=IN_SET && LA211_0<=REGEXP)||LA211_0==NOT_EXPR||(LA211_0>=SUM && LA211_0<=AVG)||(LA211_0>=COALESCE && LA211_0<=COUNT)||(LA211_0>=CASE && LA211_0<=CASE2)||(LA211_0>=PREVIOUS && LA211_0<=EXISTS)||(LA211_0>=INSTANCEOF && LA211_0<=CURRENT_TIMESTAMP)||LA211_0==NEWKW||(LA211_0>=EVAL_AND_EXPR && LA211_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA211_0==EVENT_PROP_EXPR||LA211_0==CONCAT||(LA211_0>=LIB_FUNC_CHAIN && LA211_0<=DOT_EXPR)||LA211_0==ARRAY_EXPR||(LA211_0>=NOT_IN_SET && LA211_0<=NOT_REGEXP)||(LA211_0>=IN_RANGE && LA211_0<=SUBSELECT_EXPR)||(LA211_0>=EXISTS_SUBSELECT_EXPR && LA211_0<=NOT_IN_SUBSELECT_EXPR)||LA211_0==SUBSTITUTION||(LA211_0>=FIRST_AGGREG && LA211_0<=WINDOW_AGGREG)||(LA211_0>=INT_TYPE && LA211_0<=NULL_TYPE)||(LA211_0>=STAR && LA211_0<=PLUS)||(LA211_0>=BAND && LA211_0<=BXOR)||(LA211_0>=LT && LA211_0<=GE)||(LA211_0>=MINUS && LA211_0<=MOD)) ) {
                            alt211=1;
                        }


                        switch (alt211) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:581:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc4022);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop211;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:582:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc4037); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4039);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:582:28: ( valueExpr )?
                    int alt212=2;
                    int LA212_0 = input.LA(1);

                    if ( ((LA212_0>=IN_SET && LA212_0<=REGEXP)||LA212_0==NOT_EXPR||(LA212_0>=SUM && LA212_0<=AVG)||(LA212_0>=COALESCE && LA212_0<=COUNT)||(LA212_0>=CASE && LA212_0<=CASE2)||(LA212_0>=PREVIOUS && LA212_0<=EXISTS)||(LA212_0>=INSTANCEOF && LA212_0<=CURRENT_TIMESTAMP)||LA212_0==NEWKW||(LA212_0>=EVAL_AND_EXPR && LA212_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA212_0==EVENT_PROP_EXPR||LA212_0==CONCAT||(LA212_0>=LIB_FUNC_CHAIN && LA212_0<=DOT_EXPR)||LA212_0==ARRAY_EXPR||(LA212_0>=NOT_IN_SET && LA212_0<=NOT_REGEXP)||(LA212_0>=IN_RANGE && LA212_0<=SUBSELECT_EXPR)||(LA212_0>=EXISTS_SUBSELECT_EXPR && LA212_0<=NOT_IN_SUBSELECT_EXPR)||LA212_0==SUBSTITUTION||(LA212_0>=FIRST_AGGREG && LA212_0<=WINDOW_AGGREG)||(LA212_0>=INT_TYPE && LA212_0<=NULL_TYPE)||(LA212_0>=STAR && LA212_0<=PLUS)||(LA212_0>=BAND && LA212_0<=BXOR)||(LA212_0>=LT && LA212_0<=GE)||(LA212_0>=MINUS && LA212_0<=MOD)) ) {
                        alt212=1;
                    }
                    switch (alt212) {
                        case 1 :
                            // EsperEPL2Ast.g:582:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc4041);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:583:5: ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUSTAIL,FOLLOW_PREVIOUSTAIL_in_builtinFunc4054); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4056);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:583:32: ( valueExpr )?
                    int alt213=2;
                    int LA213_0 = input.LA(1);

                    if ( ((LA213_0>=IN_SET && LA213_0<=REGEXP)||LA213_0==NOT_EXPR||(LA213_0>=SUM && LA213_0<=AVG)||(LA213_0>=COALESCE && LA213_0<=COUNT)||(LA213_0>=CASE && LA213_0<=CASE2)||(LA213_0>=PREVIOUS && LA213_0<=EXISTS)||(LA213_0>=INSTANCEOF && LA213_0<=CURRENT_TIMESTAMP)||LA213_0==NEWKW||(LA213_0>=EVAL_AND_EXPR && LA213_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA213_0==EVENT_PROP_EXPR||LA213_0==CONCAT||(LA213_0>=LIB_FUNC_CHAIN && LA213_0<=DOT_EXPR)||LA213_0==ARRAY_EXPR||(LA213_0>=NOT_IN_SET && LA213_0<=NOT_REGEXP)||(LA213_0>=IN_RANGE && LA213_0<=SUBSELECT_EXPR)||(LA213_0>=EXISTS_SUBSELECT_EXPR && LA213_0<=NOT_IN_SUBSELECT_EXPR)||LA213_0==SUBSTITUTION||(LA213_0>=FIRST_AGGREG && LA213_0<=WINDOW_AGGREG)||(LA213_0>=INT_TYPE && LA213_0<=NULL_TYPE)||(LA213_0>=STAR && LA213_0<=PLUS)||(LA213_0>=BAND && LA213_0<=BXOR)||(LA213_0>=LT && LA213_0<=GE)||(LA213_0>=MINUS && LA213_0<=MOD)) ) {
                        alt213=1;
                    }
                    switch (alt213) {
                        case 1 :
                            // EsperEPL2Ast.g:583:32: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc4058);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:584:5: ^(f= PREVIOUSCOUNT valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSCOUNT,FOLLOW_PREVIOUSCOUNT_in_builtinFunc4071); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4073);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:585:5: ^(f= PREVIOUSWINDOW valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSWINDOW,FOLLOW_PREVIOUSWINDOW_in_builtinFunc4085); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4087);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:586:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc4099); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc4103); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc4105);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:587:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc4118); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4120);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc4122); 
                    // EsperEPL2Ast.g:587:42: ( CLASS_IDENT )*
                    loop214:
                    do {
                        int alt214=2;
                        int LA214_0 = input.LA(1);

                        if ( (LA214_0==CLASS_IDENT) ) {
                            alt214=1;
                        }


                        switch (alt214) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:587:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc4125); 

                    	    }
                    	    break;

                    	default :
                    	    break loop214;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:588:5: ^(f= TYPEOF valueExpr )
                    {
                    f=(CommonTree)match(input,TYPEOF,FOLLOW_TYPEOF_in_builtinFunc4139); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4141);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 18 :
                    // EsperEPL2Ast.g:589:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc4153); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4155);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc4157); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 19 :
                    // EsperEPL2Ast.g:590:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc4169); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc4171);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 20 :
                    // EsperEPL2Ast.g:591:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc4183); 



                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        match(input, Token.UP, null); 
                    }
                     leaveNode(f); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "builtinFunc"


    // $ANTLR start "aggregationFilterExpr"
    // EsperEPL2Ast.g:594:1: aggregationFilterExpr : ^( AGG_FILTER_EXPR valueExpr ) ;
    public final void aggregationFilterExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:595:2: ( ^( AGG_FILTER_EXPR valueExpr ) )
            // EsperEPL2Ast.g:595:4: ^( AGG_FILTER_EXPR valueExpr )
            {
            match(input,AGG_FILTER_EXPR,FOLLOW_AGG_FILTER_EXPR_in_aggregationFilterExpr4200); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_aggregationFilterExpr4202);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "aggregationFilterExpr"


    // $ANTLR start "accessValueExpr"
    // EsperEPL2Ast.g:598:1: accessValueExpr : ^( ACCESS_AGG accessValueExprChoice ) ;
    public final void accessValueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:599:2: ( ^( ACCESS_AGG accessValueExprChoice ) )
            // EsperEPL2Ast.g:599:5: ^( ACCESS_AGG accessValueExprChoice )
            {
            match(input,ACCESS_AGG,FOLLOW_ACCESS_AGG_in_accessValueExpr4216); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_accessValueExprChoice_in_accessValueExpr4218);
            accessValueExprChoice();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "accessValueExpr"


    // $ANTLR start "accessValueExprChoice"
    // EsperEPL2Ast.g:602:1: accessValueExprChoice : ( PROPERTY_WILDCARD_SELECT | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) | valueExpr );
    public final void accessValueExprChoice() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:603:2: ( PROPERTY_WILDCARD_SELECT | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) | valueExpr )
            int alt217=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt217=1;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt217=2;
                }
                break;
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
            case PREVIOUSTAIL:
            case PREVIOUSCOUNT:
            case PREVIOUSWINDOW:
            case PRIOR:
            case EXISTS:
            case INSTANCEOF:
            case TYPEOF:
            case CAST:
            case CURRENT_TIMESTAMP:
            case NEWKW:
            case EVAL_AND_EXPR:
            case EVAL_OR_EXPR:
            case EVAL_EQUALS_EXPR:
            case EVAL_NOTEQUALS_EXPR:
            case EVAL_EQUALS_GROUP_EXPR:
            case EVAL_NOTEQUALS_GROUP_EXPR:
            case EVENT_PROP_EXPR:
            case CONCAT:
            case LIB_FUNC_CHAIN:
            case DOT_EXPR:
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
            case SUBSTITUTION:
            case FIRST_AGGREG:
            case LAST_AGGREG:
            case WINDOW_AGGREG:
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
            case STAR:
            case BOR:
            case PLUS:
            case BAND:
            case BXOR:
            case LT:
            case GT:
            case LE:
            case GE:
            case MINUS:
            case DIV:
            case MOD:
                {
                alt217=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 217, 0, input);

                throw nvae;
            }

            switch (alt217) {
                case 1 :
                    // EsperEPL2Ast.g:603:4: PROPERTY_WILDCARD_SELECT
                    {
                    match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExprChoice4233); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:603:31: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExprChoice4240); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_accessValueExprChoice4242); 
                    // EsperEPL2Ast.g:603:67: ( IDENT )?
                    int alt216=2;
                    int LA216_0 = input.LA(1);

                    if ( (LA216_0==IDENT) ) {
                        alt216=1;
                    }
                    switch (alt216) {
                        case 1 :
                            // EsperEPL2Ast.g:603:67: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_accessValueExprChoice4244); 

                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:603:77: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_accessValueExprChoice4250);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "accessValueExprChoice"


    // $ANTLR start "arrayExpr"
    // EsperEPL2Ast.g:606:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:607:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:607:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr4266); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:607:19: ( valueExpr )*
                loop218:
                do {
                    int alt218=2;
                    int LA218_0 = input.LA(1);

                    if ( ((LA218_0>=IN_SET && LA218_0<=REGEXP)||LA218_0==NOT_EXPR||(LA218_0>=SUM && LA218_0<=AVG)||(LA218_0>=COALESCE && LA218_0<=COUNT)||(LA218_0>=CASE && LA218_0<=CASE2)||(LA218_0>=PREVIOUS && LA218_0<=EXISTS)||(LA218_0>=INSTANCEOF && LA218_0<=CURRENT_TIMESTAMP)||LA218_0==NEWKW||(LA218_0>=EVAL_AND_EXPR && LA218_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA218_0==EVENT_PROP_EXPR||LA218_0==CONCAT||(LA218_0>=LIB_FUNC_CHAIN && LA218_0<=DOT_EXPR)||LA218_0==ARRAY_EXPR||(LA218_0>=NOT_IN_SET && LA218_0<=NOT_REGEXP)||(LA218_0>=IN_RANGE && LA218_0<=SUBSELECT_EXPR)||(LA218_0>=EXISTS_SUBSELECT_EXPR && LA218_0<=NOT_IN_SUBSELECT_EXPR)||LA218_0==SUBSTITUTION||(LA218_0>=FIRST_AGGREG && LA218_0<=WINDOW_AGGREG)||(LA218_0>=INT_TYPE && LA218_0<=NULL_TYPE)||(LA218_0>=STAR && LA218_0<=PLUS)||(LA218_0>=BAND && LA218_0<=BXOR)||(LA218_0>=LT && LA218_0<=GE)||(LA218_0>=MINUS && LA218_0<=MOD)) ) {
                        alt218=1;
                    }


                    switch (alt218) {
                	case 1 :
                	    // EsperEPL2Ast.g:607:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr4269);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop218;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
             leaveNode(a); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "arrayExpr"


    // $ANTLR start "arithmeticExpr"
    // EsperEPL2Ast.g:610:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:611:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt220=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt220=1;
                }
                break;
            case MINUS:
                {
                alt220=2;
                }
                break;
            case DIV:
                {
                alt220=3;
                }
                break;
            case STAR:
                {
                alt220=4;
                }
                break;
            case MOD:
                {
                alt220=5;
                }
                break;
            case BAND:
                {
                alt220=6;
                }
                break;
            case BOR:
                {
                alt220=7;
                }
                break;
            case BXOR:
                {
                alt220=8;
                }
                break;
            case CONCAT:
                {
                alt220=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 220, 0, input);

                throw nvae;
            }

            switch (alt220) {
                case 1 :
                    // EsperEPL2Ast.g:611:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr4290); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4292);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4294);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:612:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr4306); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4308);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4310);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:613:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr4322); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4324);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4326);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:614:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr4337); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4339);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4341);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:615:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr4353); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4355);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4357);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:616:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr4368); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4370);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4372);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:617:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr4383); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4385);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4387);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:618:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr4398); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4400);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4402);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:619:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr4414); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4416);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4418);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:619:36: ( valueExpr )*
                    loop219:
                    do {
                        int alt219=2;
                        int LA219_0 = input.LA(1);

                        if ( ((LA219_0>=IN_SET && LA219_0<=REGEXP)||LA219_0==NOT_EXPR||(LA219_0>=SUM && LA219_0<=AVG)||(LA219_0>=COALESCE && LA219_0<=COUNT)||(LA219_0>=CASE && LA219_0<=CASE2)||(LA219_0>=PREVIOUS && LA219_0<=EXISTS)||(LA219_0>=INSTANCEOF && LA219_0<=CURRENT_TIMESTAMP)||LA219_0==NEWKW||(LA219_0>=EVAL_AND_EXPR && LA219_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA219_0==EVENT_PROP_EXPR||LA219_0==CONCAT||(LA219_0>=LIB_FUNC_CHAIN && LA219_0<=DOT_EXPR)||LA219_0==ARRAY_EXPR||(LA219_0>=NOT_IN_SET && LA219_0<=NOT_REGEXP)||(LA219_0>=IN_RANGE && LA219_0<=SUBSELECT_EXPR)||(LA219_0>=EXISTS_SUBSELECT_EXPR && LA219_0<=NOT_IN_SUBSELECT_EXPR)||LA219_0==SUBSTITUTION||(LA219_0>=FIRST_AGGREG && LA219_0<=WINDOW_AGGREG)||(LA219_0>=INT_TYPE && LA219_0<=NULL_TYPE)||(LA219_0>=STAR && LA219_0<=PLUS)||(LA219_0>=BAND && LA219_0<=BXOR)||(LA219_0>=LT && LA219_0<=GE)||(LA219_0>=MINUS && LA219_0<=MOD)) ) {
                            alt219=1;
                        }


                        switch (alt219) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:619:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4421);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop219;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "arithmeticExpr"


    // $ANTLR start "dotExpr"
    // EsperEPL2Ast.g:622:1: dotExpr : ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* ) ;
    public final void dotExpr() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:623:2: ( ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* ) )
            // EsperEPL2Ast.g:623:4: ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* )
            {
            d=(CommonTree)match(input,DOT_EXPR,FOLLOW_DOT_EXPR_in_dotExpr4441); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dotExpr4443);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:623:27: ( libFunctionWithClass )*
            loop221:
            do {
                int alt221=2;
                int LA221_0 = input.LA(1);

                if ( (LA221_0==LIB_FUNCTION) ) {
                    alt221=1;
                }


                switch (alt221) {
            	case 1 :
            	    // EsperEPL2Ast.g:623:27: libFunctionWithClass
            	    {
            	    pushFollow(FOLLOW_libFunctionWithClass_in_dotExpr4445);
            	    libFunctionWithClass();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop221;
                }
            } while (true);


            match(input, Token.UP, null); 
             leaveNode(d); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "dotExpr"


    // $ANTLR start "newExpr"
    // EsperEPL2Ast.g:626:1: newExpr : ^(n= NEWKW ( newAssign )* ) ;
    public final void newExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:626:9: ( ^(n= NEWKW ( newAssign )* ) )
            // EsperEPL2Ast.g:626:11: ^(n= NEWKW ( newAssign )* )
            {
            n=(CommonTree)match(input,NEWKW,FOLLOW_NEWKW_in_newExpr4463); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:626:21: ( newAssign )*
                loop222:
                do {
                    int alt222=2;
                    int LA222_0 = input.LA(1);

                    if ( (LA222_0==NEW_ITEM) ) {
                        alt222=1;
                    }


                    switch (alt222) {
                	case 1 :
                	    // EsperEPL2Ast.g:626:21: newAssign
                	    {
                	    pushFollow(FOLLOW_newAssign_in_newExpr4465);
                	    newAssign();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop222;
                    }
                } while (true);


                match(input, Token.UP, null); 
            }
             leaveNode(n); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "newExpr"


    // $ANTLR start "newAssign"
    // EsperEPL2Ast.g:629:1: newAssign : ^( NEW_ITEM eventPropertyExpr[false] ( valueExpr )? ) ;
    public final void newAssign() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:630:2: ( ^( NEW_ITEM eventPropertyExpr[false] ( valueExpr )? ) )
            // EsperEPL2Ast.g:630:4: ^( NEW_ITEM eventPropertyExpr[false] ( valueExpr )? )
            {
            match(input,NEW_ITEM,FOLLOW_NEW_ITEM_in_newAssign4481); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_newAssign4483);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:630:40: ( valueExpr )?
            int alt223=2;
            int LA223_0 = input.LA(1);

            if ( ((LA223_0>=IN_SET && LA223_0<=REGEXP)||LA223_0==NOT_EXPR||(LA223_0>=SUM && LA223_0<=AVG)||(LA223_0>=COALESCE && LA223_0<=COUNT)||(LA223_0>=CASE && LA223_0<=CASE2)||(LA223_0>=PREVIOUS && LA223_0<=EXISTS)||(LA223_0>=INSTANCEOF && LA223_0<=CURRENT_TIMESTAMP)||LA223_0==NEWKW||(LA223_0>=EVAL_AND_EXPR && LA223_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA223_0==EVENT_PROP_EXPR||LA223_0==CONCAT||(LA223_0>=LIB_FUNC_CHAIN && LA223_0<=DOT_EXPR)||LA223_0==ARRAY_EXPR||(LA223_0>=NOT_IN_SET && LA223_0<=NOT_REGEXP)||(LA223_0>=IN_RANGE && LA223_0<=SUBSELECT_EXPR)||(LA223_0>=EXISTS_SUBSELECT_EXPR && LA223_0<=NOT_IN_SUBSELECT_EXPR)||LA223_0==SUBSTITUTION||(LA223_0>=FIRST_AGGREG && LA223_0<=WINDOW_AGGREG)||(LA223_0>=INT_TYPE && LA223_0<=NULL_TYPE)||(LA223_0>=STAR && LA223_0<=PLUS)||(LA223_0>=BAND && LA223_0<=BXOR)||(LA223_0>=LT && LA223_0<=GE)||(LA223_0>=MINUS && LA223_0<=MOD)) ) {
                alt223=1;
            }
            switch (alt223) {
                case 1 :
                    // EsperEPL2Ast.g:630:40: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_newAssign4486);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "newAssign"


    // $ANTLR start "libFuncChain"
    // EsperEPL2Ast.g:633:1: libFuncChain : ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* ) ;
    public final void libFuncChain() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:634:2: ( ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* ) )
            // EsperEPL2Ast.g:634:6: ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* )
            {
            l=(CommonTree)match(input,LIB_FUNC_CHAIN,FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4504); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_libFunctionWithClass_in_libFuncChain4506);
            libFunctionWithClass();

            state._fsp--;

            // EsperEPL2Ast.g:634:46: ( libOrPropFunction )*
            loop224:
            do {
                int alt224=2;
                int LA224_0 = input.LA(1);

                if ( (LA224_0==EVENT_PROP_EXPR||LA224_0==LIB_FUNCTION) ) {
                    alt224=1;
                }


                switch (alt224) {
            	case 1 :
            	    // EsperEPL2Ast.g:634:46: libOrPropFunction
            	    {
            	    pushFollow(FOLLOW_libOrPropFunction_in_libFuncChain4508);
            	    libOrPropFunction();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop224;
                }
            } while (true);


            match(input, Token.UP, null); 
             leaveNode(l); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "libFuncChain"


    // $ANTLR start "libFunctionWithClass"
    // EsperEPL2Ast.g:637:1: libFunctionWithClass : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( libFunctionArgItem )* ( LPAREN )? ) ;
    public final void libFunctionWithClass() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:638:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( libFunctionArgItem )* ( LPAREN )? ) )
            // EsperEPL2Ast.g:638:6: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( libFunctionArgItem )* ( LPAREN )? )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4528); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:638:23: ( CLASS_IDENT )?
            int alt225=2;
            int LA225_0 = input.LA(1);

            if ( (LA225_0==CLASS_IDENT) ) {
                alt225=1;
            }
            switch (alt225) {
                case 1 :
                    // EsperEPL2Ast.g:638:24: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunctionWithClass4531); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunctionWithClass4535); 
            // EsperEPL2Ast.g:638:44: ( DISTINCT )?
            int alt226=2;
            int LA226_0 = input.LA(1);

            if ( (LA226_0==DISTINCT) ) {
                alt226=1;
            }
            switch (alt226) {
                case 1 :
                    // EsperEPL2Ast.g:638:45: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunctionWithClass4538); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:638:56: ( libFunctionArgItem )*
            loop227:
            do {
                int alt227=2;
                int LA227_0 = input.LA(1);

                if ( ((LA227_0>=IN_SET && LA227_0<=REGEXP)||LA227_0==NOT_EXPR||(LA227_0>=SUM && LA227_0<=AVG)||(LA227_0>=COALESCE && LA227_0<=COUNT)||(LA227_0>=CASE && LA227_0<=CASE2)||LA227_0==LAST||(LA227_0>=PREVIOUS && LA227_0<=EXISTS)||(LA227_0>=LW && LA227_0<=CURRENT_TIMESTAMP)||(LA227_0>=NEWKW && LA227_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA227_0>=EVAL_AND_EXPR && LA227_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA227_0==EVENT_PROP_EXPR||LA227_0==CONCAT||(LA227_0>=LIB_FUNC_CHAIN && LA227_0<=DOT_EXPR)||(LA227_0>=TIME_PERIOD && LA227_0<=ARRAY_EXPR)||(LA227_0>=NOT_IN_SET && LA227_0<=NOT_REGEXP)||(LA227_0>=IN_RANGE && LA227_0<=SUBSELECT_EXPR)||(LA227_0>=EXISTS_SUBSELECT_EXPR && LA227_0<=NOT_IN_SUBSELECT_EXPR)||(LA227_0>=LAST_OPERATOR && LA227_0<=SUBSTITUTION)||LA227_0==NUMBERSETSTAR||(LA227_0>=FIRST_AGGREG && LA227_0<=WINDOW_AGGREG)||(LA227_0>=INT_TYPE && LA227_0<=NULL_TYPE)||LA227_0==GOES||(LA227_0>=STAR && LA227_0<=PLUS)||(LA227_0>=BAND && LA227_0<=BXOR)||(LA227_0>=LT && LA227_0<=GE)||(LA227_0>=MINUS && LA227_0<=MOD)) ) {
                    alt227=1;
                }


                switch (alt227) {
            	case 1 :
            	    // EsperEPL2Ast.g:638:56: libFunctionArgItem
            	    {
            	    pushFollow(FOLLOW_libFunctionArgItem_in_libFunctionWithClass4542);
            	    libFunctionArgItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop227;
                }
            } while (true);

            // EsperEPL2Ast.g:638:76: ( LPAREN )?
            int alt228=2;
            int LA228_0 = input.LA(1);

            if ( (LA228_0==LPAREN) ) {
                alt228=1;
            }
            switch (alt228) {
                case 1 :
                    // EsperEPL2Ast.g:638:76: LPAREN
                    {
                    match(input,LPAREN,FOLLOW_LPAREN_in_libFunctionWithClass4545); 

                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "libFunctionWithClass"


    // $ANTLR start "libFunctionArgItem"
    // EsperEPL2Ast.g:641:1: libFunctionArgItem : ( expressionLambdaDecl | valueExprWithTime );
    public final void libFunctionArgItem() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:642:2: ( expressionLambdaDecl | valueExprWithTime )
            int alt229=2;
            int LA229_0 = input.LA(1);

            if ( (LA229_0==GOES) ) {
                alt229=1;
            }
            else if ( ((LA229_0>=IN_SET && LA229_0<=REGEXP)||LA229_0==NOT_EXPR||(LA229_0>=SUM && LA229_0<=AVG)||(LA229_0>=COALESCE && LA229_0<=COUNT)||(LA229_0>=CASE && LA229_0<=CASE2)||LA229_0==LAST||(LA229_0>=PREVIOUS && LA229_0<=EXISTS)||(LA229_0>=LW && LA229_0<=CURRENT_TIMESTAMP)||(LA229_0>=NEWKW && LA229_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA229_0>=EVAL_AND_EXPR && LA229_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA229_0==EVENT_PROP_EXPR||LA229_0==CONCAT||(LA229_0>=LIB_FUNC_CHAIN && LA229_0<=DOT_EXPR)||(LA229_0>=TIME_PERIOD && LA229_0<=ARRAY_EXPR)||(LA229_0>=NOT_IN_SET && LA229_0<=NOT_REGEXP)||(LA229_0>=IN_RANGE && LA229_0<=SUBSELECT_EXPR)||(LA229_0>=EXISTS_SUBSELECT_EXPR && LA229_0<=NOT_IN_SUBSELECT_EXPR)||(LA229_0>=LAST_OPERATOR && LA229_0<=SUBSTITUTION)||LA229_0==NUMBERSETSTAR||(LA229_0>=FIRST_AGGREG && LA229_0<=WINDOW_AGGREG)||(LA229_0>=INT_TYPE && LA229_0<=NULL_TYPE)||(LA229_0>=STAR && LA229_0<=PLUS)||(LA229_0>=BAND && LA229_0<=BXOR)||(LA229_0>=LT && LA229_0<=GE)||(LA229_0>=MINUS && LA229_0<=MOD)) ) {
                alt229=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 229, 0, input);

                throw nvae;
            }
            switch (alt229) {
                case 1 :
                    // EsperEPL2Ast.g:642:4: expressionLambdaDecl
                    {
                    pushFollow(FOLLOW_expressionLambdaDecl_in_libFunctionArgItem4559);
                    expressionLambdaDecl();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:642:27: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_libFunctionArgItem4563);
                    valueExprWithTime();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "libFunctionArgItem"


    // $ANTLR start "libOrPropFunction"
    // EsperEPL2Ast.g:645:1: libOrPropFunction : ( eventPropertyExpr[false] | libFunctionWithClass );
    public final void libOrPropFunction() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:646:2: ( eventPropertyExpr[false] | libFunctionWithClass )
            int alt230=2;
            int LA230_0 = input.LA(1);

            if ( (LA230_0==EVENT_PROP_EXPR) ) {
                alt230=1;
            }
            else if ( (LA230_0==LIB_FUNCTION) ) {
                alt230=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 230, 0, input);

                throw nvae;
            }
            switch (alt230) {
                case 1 :
                    // EsperEPL2Ast.g:646:7: eventPropertyExpr[false]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_libOrPropFunction4578);
                    eventPropertyExpr(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:647:7: libFunctionWithClass
                    {
                    pushFollow(FOLLOW_libFunctionWithClass_in_libOrPropFunction4588);
                    libFunctionWithClass();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "libOrPropFunction"


    // $ANTLR start "startPatternExpressionRule"
    // EsperEPL2Ast.g:653:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:654:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:654:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:654:4: ( annotation[true] )*
            loop231:
            do {
                int alt231=2;
                int LA231_0 = input.LA(1);

                if ( (LA231_0==ANNOTATION) ) {
                    alt231=1;
                }


                switch (alt231) {
            	case 1 :
            	    // EsperEPL2Ast.g:654:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule4603);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop231;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule4607);
            exprChoice();

            state._fsp--;

             endPattern(); end(); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "startPatternExpressionRule"


    // $ANTLR start "exprChoice"
    // EsperEPL2Ast.g:657:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:658:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt236=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt236=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt236=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt236=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt236=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt236=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt236=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt236=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 236, 0, input);

                throw nvae;
            }

            switch (alt236) {
                case 1 :
                    // EsperEPL2Ast.g:658:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice4621);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:659:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice4626);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:660:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice4636); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4638);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:661:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4652); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice4654);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4656);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:662:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4670); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4672);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:663:5: ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice4686); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4688);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:663:32: ( IDENT IDENT ( valueExprWithTime )* | valueExpr )
                    int alt233=2;
                    int LA233_0 = input.LA(1);

                    if ( (LA233_0==IDENT) ) {
                        alt233=1;
                    }
                    else if ( ((LA233_0>=IN_SET && LA233_0<=REGEXP)||LA233_0==NOT_EXPR||(LA233_0>=SUM && LA233_0<=AVG)||(LA233_0>=COALESCE && LA233_0<=COUNT)||(LA233_0>=CASE && LA233_0<=CASE2)||(LA233_0>=PREVIOUS && LA233_0<=EXISTS)||(LA233_0>=INSTANCEOF && LA233_0<=CURRENT_TIMESTAMP)||LA233_0==NEWKW||(LA233_0>=EVAL_AND_EXPR && LA233_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA233_0==EVENT_PROP_EXPR||LA233_0==CONCAT||(LA233_0>=LIB_FUNC_CHAIN && LA233_0<=DOT_EXPR)||LA233_0==ARRAY_EXPR||(LA233_0>=NOT_IN_SET && LA233_0<=NOT_REGEXP)||(LA233_0>=IN_RANGE && LA233_0<=SUBSELECT_EXPR)||(LA233_0>=EXISTS_SUBSELECT_EXPR && LA233_0<=NOT_IN_SUBSELECT_EXPR)||LA233_0==SUBSTITUTION||(LA233_0>=FIRST_AGGREG && LA233_0<=WINDOW_AGGREG)||(LA233_0>=INT_TYPE && LA233_0<=NULL_TYPE)||(LA233_0>=STAR && LA233_0<=PLUS)||(LA233_0>=BAND && LA233_0<=BXOR)||(LA233_0>=LT && LA233_0<=GE)||(LA233_0>=MINUS && LA233_0<=MOD)) ) {
                        alt233=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 233, 0, input);

                        throw nvae;
                    }
                    switch (alt233) {
                        case 1 :
                            // EsperEPL2Ast.g:663:33: IDENT IDENT ( valueExprWithTime )*
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4691); 
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4693); 
                            // EsperEPL2Ast.g:663:45: ( valueExprWithTime )*
                            loop232:
                            do {
                                int alt232=2;
                                int LA232_0 = input.LA(1);

                                if ( ((LA232_0>=IN_SET && LA232_0<=REGEXP)||LA232_0==NOT_EXPR||(LA232_0>=SUM && LA232_0<=AVG)||(LA232_0>=COALESCE && LA232_0<=COUNT)||(LA232_0>=CASE && LA232_0<=CASE2)||LA232_0==LAST||(LA232_0>=PREVIOUS && LA232_0<=EXISTS)||(LA232_0>=LW && LA232_0<=CURRENT_TIMESTAMP)||(LA232_0>=NEWKW && LA232_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA232_0>=EVAL_AND_EXPR && LA232_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA232_0==EVENT_PROP_EXPR||LA232_0==CONCAT||(LA232_0>=LIB_FUNC_CHAIN && LA232_0<=DOT_EXPR)||(LA232_0>=TIME_PERIOD && LA232_0<=ARRAY_EXPR)||(LA232_0>=NOT_IN_SET && LA232_0<=NOT_REGEXP)||(LA232_0>=IN_RANGE && LA232_0<=SUBSELECT_EXPR)||(LA232_0>=EXISTS_SUBSELECT_EXPR && LA232_0<=NOT_IN_SUBSELECT_EXPR)||(LA232_0>=LAST_OPERATOR && LA232_0<=SUBSTITUTION)||LA232_0==NUMBERSETSTAR||(LA232_0>=FIRST_AGGREG && LA232_0<=WINDOW_AGGREG)||(LA232_0>=INT_TYPE && LA232_0<=NULL_TYPE)||(LA232_0>=STAR && LA232_0<=PLUS)||(LA232_0>=BAND && LA232_0<=BXOR)||(LA232_0>=LT && LA232_0<=GE)||(LA232_0>=MINUS && LA232_0<=MOD)) ) {
                                    alt232=1;
                                }


                                switch (alt232) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:663:45: valueExprWithTime
                            	    {
                            	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice4695);
                            	    valueExprWithTime();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop232;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:663:66: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_exprChoice4700);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:664:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4714); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:664:26: ( matchUntilRange )?
                    int alt234=2;
                    int LA234_0 = input.LA(1);

                    if ( ((LA234_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA234_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt234=1;
                    }
                    switch (alt234) {
                        case 1 :
                            // EsperEPL2Ast.g:664:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4716);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4719);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:664:54: ( exprChoice )?
                    int alt235=2;
                    int LA235_0 = input.LA(1);

                    if ( ((LA235_0>=OR_EXPR && LA235_0<=AND_EXPR)||(LA235_0>=EVERY_EXPR && LA235_0<=EVERY_DISTINCT_EXPR)||LA235_0==FOLLOWED_BY_EXPR||(LA235_0>=PATTERN_FILTER_EXPR && LA235_0<=PATTERN_NOT_EXPR)||(LA235_0>=GUARD_EXPR && LA235_0<=OBSERVER_EXPR)||LA235_0==MATCH_UNTIL_EXPR) ) {
                        alt235=1;
                    }
                    switch (alt235) {
                        case 1 :
                            // EsperEPL2Ast.g:664:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4721);
                            exprChoice();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(m); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "exprChoice"


    // $ANTLR start "distinctExpressions"
    // EsperEPL2Ast.g:668:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExprWithTime )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:669:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExprWithTime )+ ) )
            // EsperEPL2Ast.g:669:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExprWithTime )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4742); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:669:35: ( valueExprWithTime )+
            int cnt237=0;
            loop237:
            do {
                int alt237=2;
                int LA237_0 = input.LA(1);

                if ( ((LA237_0>=IN_SET && LA237_0<=REGEXP)||LA237_0==NOT_EXPR||(LA237_0>=SUM && LA237_0<=AVG)||(LA237_0>=COALESCE && LA237_0<=COUNT)||(LA237_0>=CASE && LA237_0<=CASE2)||LA237_0==LAST||(LA237_0>=PREVIOUS && LA237_0<=EXISTS)||(LA237_0>=LW && LA237_0<=CURRENT_TIMESTAMP)||(LA237_0>=NEWKW && LA237_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA237_0>=EVAL_AND_EXPR && LA237_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA237_0==EVENT_PROP_EXPR||LA237_0==CONCAT||(LA237_0>=LIB_FUNC_CHAIN && LA237_0<=DOT_EXPR)||(LA237_0>=TIME_PERIOD && LA237_0<=ARRAY_EXPR)||(LA237_0>=NOT_IN_SET && LA237_0<=NOT_REGEXP)||(LA237_0>=IN_RANGE && LA237_0<=SUBSELECT_EXPR)||(LA237_0>=EXISTS_SUBSELECT_EXPR && LA237_0<=NOT_IN_SUBSELECT_EXPR)||(LA237_0>=LAST_OPERATOR && LA237_0<=SUBSTITUTION)||LA237_0==NUMBERSETSTAR||(LA237_0>=FIRST_AGGREG && LA237_0<=WINDOW_AGGREG)||(LA237_0>=INT_TYPE && LA237_0<=NULL_TYPE)||(LA237_0>=STAR && LA237_0<=PLUS)||(LA237_0>=BAND && LA237_0<=BXOR)||(LA237_0>=LT && LA237_0<=GE)||(LA237_0>=MINUS && LA237_0<=MOD)) ) {
                    alt237=1;
                }


                switch (alt237) {
            	case 1 :
            	    // EsperEPL2Ast.g:669:35: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_distinctExpressions4744);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt237 >= 1 ) break loop237;
                        EarlyExitException eee =
                            new EarlyExitException(237, input);
                        throw eee;
                }
                cnt237++;
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "distinctExpressions"


    // $ANTLR start "patternOp"
    // EsperEPL2Ast.g:672:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR followedByItem followedByItem ( followedByItem )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:673:2: ( ^(f= FOLLOWED_BY_EXPR followedByItem followedByItem ( followedByItem )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt241=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt241=1;
                }
                break;
            case OR_EXPR:
                {
                alt241=2;
                }
                break;
            case AND_EXPR:
                {
                alt241=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 241, 0, input);

                throw nvae;
            }

            switch (alt241) {
                case 1 :
                    // EsperEPL2Ast.g:673:4: ^(f= FOLLOWED_BY_EXPR followedByItem followedByItem ( followedByItem )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4763); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_followedByItem_in_patternOp4765);
                    followedByItem();

                    state._fsp--;

                    pushFollow(FOLLOW_followedByItem_in_patternOp4767);
                    followedByItem();

                    state._fsp--;

                    // EsperEPL2Ast.g:673:56: ( followedByItem )*
                    loop238:
                    do {
                        int alt238=2;
                        int LA238_0 = input.LA(1);

                        if ( (LA238_0==FOLLOWED_BY_ITEM) ) {
                            alt238=1;
                        }


                        switch (alt238) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:673:57: followedByItem
                    	    {
                    	    pushFollow(FOLLOW_followedByItem_in_patternOp4770);
                    	    followedByItem();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop238;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:674:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4786); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4788);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4790);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:674:40: ( exprChoice )*
                    loop239:
                    do {
                        int alt239=2;
                        int LA239_0 = input.LA(1);

                        if ( ((LA239_0>=OR_EXPR && LA239_0<=AND_EXPR)||(LA239_0>=EVERY_EXPR && LA239_0<=EVERY_DISTINCT_EXPR)||LA239_0==FOLLOWED_BY_EXPR||(LA239_0>=PATTERN_FILTER_EXPR && LA239_0<=PATTERN_NOT_EXPR)||(LA239_0>=GUARD_EXPR && LA239_0<=OBSERVER_EXPR)||LA239_0==MATCH_UNTIL_EXPR) ) {
                            alt239=1;
                        }


                        switch (alt239) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:674:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4793);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop239;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:675:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4809); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4811);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4813);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:675:41: ( exprChoice )*
                    loop240:
                    do {
                        int alt240=2;
                        int LA240_0 = input.LA(1);

                        if ( ((LA240_0>=OR_EXPR && LA240_0<=AND_EXPR)||(LA240_0>=EVERY_EXPR && LA240_0<=EVERY_DISTINCT_EXPR)||LA240_0==FOLLOWED_BY_EXPR||(LA240_0>=PATTERN_FILTER_EXPR && LA240_0<=PATTERN_NOT_EXPR)||(LA240_0>=GUARD_EXPR && LA240_0<=OBSERVER_EXPR)||LA240_0==MATCH_UNTIL_EXPR) ) {
                            alt240=1;
                        }


                        switch (alt240) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:675:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4816);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop240;
                        }
                    } while (true);

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "patternOp"


    // $ANTLR start "followedByItem"
    // EsperEPL2Ast.g:678:1: followedByItem : ^( FOLLOWED_BY_ITEM ( valueExpr )? exprChoice ) ;
    public final void followedByItem() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:679:2: ( ^( FOLLOWED_BY_ITEM ( valueExpr )? exprChoice ) )
            // EsperEPL2Ast.g:679:4: ^( FOLLOWED_BY_ITEM ( valueExpr )? exprChoice )
            {
            match(input,FOLLOWED_BY_ITEM,FOLLOW_FOLLOWED_BY_ITEM_in_followedByItem4837); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:679:24: ( valueExpr )?
            int alt242=2;
            int LA242_0 = input.LA(1);

            if ( ((LA242_0>=IN_SET && LA242_0<=REGEXP)||LA242_0==NOT_EXPR||(LA242_0>=SUM && LA242_0<=AVG)||(LA242_0>=COALESCE && LA242_0<=COUNT)||(LA242_0>=CASE && LA242_0<=CASE2)||(LA242_0>=PREVIOUS && LA242_0<=EXISTS)||(LA242_0>=INSTANCEOF && LA242_0<=CURRENT_TIMESTAMP)||LA242_0==NEWKW||(LA242_0>=EVAL_AND_EXPR && LA242_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA242_0==EVENT_PROP_EXPR||LA242_0==CONCAT||(LA242_0>=LIB_FUNC_CHAIN && LA242_0<=DOT_EXPR)||LA242_0==ARRAY_EXPR||(LA242_0>=NOT_IN_SET && LA242_0<=NOT_REGEXP)||(LA242_0>=IN_RANGE && LA242_0<=SUBSELECT_EXPR)||(LA242_0>=EXISTS_SUBSELECT_EXPR && LA242_0<=NOT_IN_SUBSELECT_EXPR)||LA242_0==SUBSTITUTION||(LA242_0>=FIRST_AGGREG && LA242_0<=WINDOW_AGGREG)||(LA242_0>=INT_TYPE && LA242_0<=NULL_TYPE)||(LA242_0>=STAR && LA242_0<=PLUS)||(LA242_0>=BAND && LA242_0<=BXOR)||(LA242_0>=LT && LA242_0<=GE)||(LA242_0>=MINUS && LA242_0<=MOD)) ) {
                alt242=1;
            }
            switch (alt242) {
                case 1 :
                    // EsperEPL2Ast.g:679:24: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_followedByItem4839);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_exprChoice_in_followedByItem4842);
            exprChoice();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "followedByItem"


    // $ANTLR start "atomicExpr"
    // EsperEPL2Ast.g:682:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:683:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt244=2;
            int LA244_0 = input.LA(1);

            if ( (LA244_0==PATTERN_FILTER_EXPR) ) {
                alt244=1;
            }
            else if ( (LA244_0==OBSERVER_EXPR) ) {
                alt244=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 244, 0, input);

                throw nvae;
            }
            switch (alt244) {
                case 1 :
                    // EsperEPL2Ast.g:683:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4856);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:684:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4868); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4870); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4872); 
                    // EsperEPL2Ast.g:684:39: ( valueExprWithTime )*
                    loop243:
                    do {
                        int alt243=2;
                        int LA243_0 = input.LA(1);

                        if ( ((LA243_0>=IN_SET && LA243_0<=REGEXP)||LA243_0==NOT_EXPR||(LA243_0>=SUM && LA243_0<=AVG)||(LA243_0>=COALESCE && LA243_0<=COUNT)||(LA243_0>=CASE && LA243_0<=CASE2)||LA243_0==LAST||(LA243_0>=PREVIOUS && LA243_0<=EXISTS)||(LA243_0>=LW && LA243_0<=CURRENT_TIMESTAMP)||(LA243_0>=NEWKW && LA243_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA243_0>=EVAL_AND_EXPR && LA243_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA243_0==EVENT_PROP_EXPR||LA243_0==CONCAT||(LA243_0>=LIB_FUNC_CHAIN && LA243_0<=DOT_EXPR)||(LA243_0>=TIME_PERIOD && LA243_0<=ARRAY_EXPR)||(LA243_0>=NOT_IN_SET && LA243_0<=NOT_REGEXP)||(LA243_0>=IN_RANGE && LA243_0<=SUBSELECT_EXPR)||(LA243_0>=EXISTS_SUBSELECT_EXPR && LA243_0<=NOT_IN_SUBSELECT_EXPR)||(LA243_0>=LAST_OPERATOR && LA243_0<=SUBSTITUTION)||LA243_0==NUMBERSETSTAR||(LA243_0>=FIRST_AGGREG && LA243_0<=WINDOW_AGGREG)||(LA243_0>=INT_TYPE && LA243_0<=NULL_TYPE)||(LA243_0>=STAR && LA243_0<=PLUS)||(LA243_0>=BAND && LA243_0<=BXOR)||(LA243_0>=LT && LA243_0<=GE)||(LA243_0>=MINUS && LA243_0<=MOD)) ) {
                            alt243=1;
                        }


                        switch (alt243) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:684:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4874);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop243;
                        }
                    } while (true);

                     leaveNode(ac); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "atomicExpr"


    // $ANTLR start "patternFilterExpr"
    // EsperEPL2Ast.g:687:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( patternFilterAnno )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:688:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( patternFilterAnno )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:688:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( patternFilterAnno )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4894); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:688:29: ( IDENT )?
            int alt245=2;
            int LA245_0 = input.LA(1);

            if ( (LA245_0==IDENT) ) {
                alt245=1;
            }
            switch (alt245) {
                case 1 :
                    // EsperEPL2Ast.g:688:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4896); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4899); 
            // EsperEPL2Ast.g:688:48: ( propertyExpression )?
            int alt246=2;
            int LA246_0 = input.LA(1);

            if ( (LA246_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt246=1;
            }
            switch (alt246) {
                case 1 :
                    // EsperEPL2Ast.g:688:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4901);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:688:68: ( patternFilterAnno )?
            int alt247=2;
            int LA247_0 = input.LA(1);

            if ( (LA247_0==ATCHAR) ) {
                alt247=1;
            }
            switch (alt247) {
                case 1 :
                    // EsperEPL2Ast.g:688:68: patternFilterAnno
                    {
                    pushFollow(FOLLOW_patternFilterAnno_in_patternFilterExpr4904);
                    patternFilterAnno();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:688:87: ( valueExpr )*
            loop248:
            do {
                int alt248=2;
                int LA248_0 = input.LA(1);

                if ( ((LA248_0>=IN_SET && LA248_0<=REGEXP)||LA248_0==NOT_EXPR||(LA248_0>=SUM && LA248_0<=AVG)||(LA248_0>=COALESCE && LA248_0<=COUNT)||(LA248_0>=CASE && LA248_0<=CASE2)||(LA248_0>=PREVIOUS && LA248_0<=EXISTS)||(LA248_0>=INSTANCEOF && LA248_0<=CURRENT_TIMESTAMP)||LA248_0==NEWKW||(LA248_0>=EVAL_AND_EXPR && LA248_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA248_0==EVENT_PROP_EXPR||LA248_0==CONCAT||(LA248_0>=LIB_FUNC_CHAIN && LA248_0<=DOT_EXPR)||LA248_0==ARRAY_EXPR||(LA248_0>=NOT_IN_SET && LA248_0<=NOT_REGEXP)||(LA248_0>=IN_RANGE && LA248_0<=SUBSELECT_EXPR)||(LA248_0>=EXISTS_SUBSELECT_EXPR && LA248_0<=NOT_IN_SUBSELECT_EXPR)||LA248_0==SUBSTITUTION||(LA248_0>=FIRST_AGGREG && LA248_0<=WINDOW_AGGREG)||(LA248_0>=INT_TYPE && LA248_0<=NULL_TYPE)||(LA248_0>=STAR && LA248_0<=PLUS)||(LA248_0>=BAND && LA248_0<=BXOR)||(LA248_0>=LT && LA248_0<=GE)||(LA248_0>=MINUS && LA248_0<=MOD)) ) {
                    alt248=1;
                }


                switch (alt248) {
            	case 1 :
            	    // EsperEPL2Ast.g:688:88: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4908);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop248;
                }
            } while (true);

             leaveNode(f); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "patternFilterExpr"


    // $ANTLR start "patternFilterAnno"
    // EsperEPL2Ast.g:691:1: patternFilterAnno : ^( ATCHAR IDENT ( number )? ) ;
    public final void patternFilterAnno() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:692:2: ( ^( ATCHAR IDENT ( number )? ) )
            // EsperEPL2Ast.g:692:4: ^( ATCHAR IDENT ( number )? )
            {
            match(input,ATCHAR,FOLLOW_ATCHAR_in_patternFilterAnno4928); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_patternFilterAnno4930); 
            // EsperEPL2Ast.g:692:20: ( number )?
            int alt249=2;
            int LA249_0 = input.LA(1);

            if ( ((LA249_0>=INT_TYPE && LA249_0<=DOUBLE_TYPE)) ) {
                alt249=1;
            }
            switch (alt249) {
                case 1 :
                    // EsperEPL2Ast.g:692:20: number
                    {
                    pushFollow(FOLLOW_number_in_patternFilterAnno4932);
                    number();

                    state._fsp--;


                    }
                    break;

            }


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "patternFilterAnno"


    // $ANTLR start "matchUntilRange"
    // EsperEPL2Ast.g:695:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr ) | ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:696:2: ( ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr ) | ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr ) )
            int alt250=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt250=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt250=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt250=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt250=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 250, 0, input);

                throw nvae;
            }

            switch (alt250) {
                case 1 :
                    // EsperEPL2Ast.g:696:4: ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4947); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4949);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4951);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:697:5: ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4959); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4961);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:698:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4969); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4971);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:699:4: ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4978); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4980);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "matchUntilRange"


    // $ANTLR start "filterParam"
    // EsperEPL2Ast.g:702:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:703:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:703:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4993); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4995);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:703:35: ( valueExpr )*
            loop251:
            do {
                int alt251=2;
                int LA251_0 = input.LA(1);

                if ( ((LA251_0>=IN_SET && LA251_0<=REGEXP)||LA251_0==NOT_EXPR||(LA251_0>=SUM && LA251_0<=AVG)||(LA251_0>=COALESCE && LA251_0<=COUNT)||(LA251_0>=CASE && LA251_0<=CASE2)||(LA251_0>=PREVIOUS && LA251_0<=EXISTS)||(LA251_0>=INSTANCEOF && LA251_0<=CURRENT_TIMESTAMP)||LA251_0==NEWKW||(LA251_0>=EVAL_AND_EXPR && LA251_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA251_0==EVENT_PROP_EXPR||LA251_0==CONCAT||(LA251_0>=LIB_FUNC_CHAIN && LA251_0<=DOT_EXPR)||LA251_0==ARRAY_EXPR||(LA251_0>=NOT_IN_SET && LA251_0<=NOT_REGEXP)||(LA251_0>=IN_RANGE && LA251_0<=SUBSELECT_EXPR)||(LA251_0>=EXISTS_SUBSELECT_EXPR && LA251_0<=NOT_IN_SUBSELECT_EXPR)||LA251_0==SUBSTITUTION||(LA251_0>=FIRST_AGGREG && LA251_0<=WINDOW_AGGREG)||(LA251_0>=INT_TYPE && LA251_0<=NULL_TYPE)||(LA251_0>=STAR && LA251_0<=PLUS)||(LA251_0>=BAND && LA251_0<=BXOR)||(LA251_0>=LT && LA251_0<=GE)||(LA251_0>=MINUS && LA251_0<=MOD)) ) {
                    alt251=1;
                }


                switch (alt251) {
            	case 1 :
            	    // EsperEPL2Ast.g:703:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4998);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop251;
                }
            } while (true);


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "filterParam"


    // $ANTLR start "filterParamComparator"
    // EsperEPL2Ast.g:706:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:707:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt264=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt264=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt264=2;
                }
                break;
            case LT:
                {
                alt264=3;
                }
                break;
            case LE:
                {
                alt264=4;
                }
                break;
            case GT:
                {
                alt264=5;
                }
                break;
            case GE:
                {
                alt264=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt264=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt264=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt264=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt264=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt264=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt264=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 264, 0, input);

                throw nvae;
            }

            switch (alt264) {
                case 1 :
                    // EsperEPL2Ast.g:707:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator5014); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator5016);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:708:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator5023); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator5025);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:709:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator5032); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator5034);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:710:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator5041); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator5043);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:711:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator5050); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator5052);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:712:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator5059); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator5061);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:713:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator5068); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:713:41: ( constant[false] | filterIdentifier )
                    int alt252=2;
                    int LA252_0 = input.LA(1);

                    if ( ((LA252_0>=INT_TYPE && LA252_0<=NULL_TYPE)) ) {
                        alt252=1;
                    }
                    else if ( (LA252_0==EVENT_FILTER_IDENT) ) {
                        alt252=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 252, 0, input);

                        throw nvae;
                    }
                    switch (alt252) {
                        case 1 :
                            // EsperEPL2Ast.g:713:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5077);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:713:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5080);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:713:76: ( constant[false] | filterIdentifier )
                    int alt253=2;
                    int LA253_0 = input.LA(1);

                    if ( ((LA253_0>=INT_TYPE && LA253_0<=NULL_TYPE)) ) {
                        alt253=1;
                    }
                    else if ( (LA253_0==EVENT_FILTER_IDENT) ) {
                        alt253=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 253, 0, input);

                        throw nvae;
                    }
                    switch (alt253) {
                        case 1 :
                            // EsperEPL2Ast.g:713:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5084);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:713:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5087);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:714:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator5101); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:714:45: ( constant[false] | filterIdentifier )
                    int alt254=2;
                    int LA254_0 = input.LA(1);

                    if ( ((LA254_0>=INT_TYPE && LA254_0<=NULL_TYPE)) ) {
                        alt254=1;
                    }
                    else if ( (LA254_0==EVENT_FILTER_IDENT) ) {
                        alt254=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 254, 0, input);

                        throw nvae;
                    }
                    switch (alt254) {
                        case 1 :
                            // EsperEPL2Ast.g:714:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5110);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:714:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5113);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:714:80: ( constant[false] | filterIdentifier )
                    int alt255=2;
                    int LA255_0 = input.LA(1);

                    if ( ((LA255_0>=INT_TYPE && LA255_0<=NULL_TYPE)) ) {
                        alt255=1;
                    }
                    else if ( (LA255_0==EVENT_FILTER_IDENT) ) {
                        alt255=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 255, 0, input);

                        throw nvae;
                    }
                    switch (alt255) {
                        case 1 :
                            // EsperEPL2Ast.g:714:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5117);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:714:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5120);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:715:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator5134); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:715:38: ( constant[false] | filterIdentifier )
                    int alt256=2;
                    int LA256_0 = input.LA(1);

                    if ( ((LA256_0>=INT_TYPE && LA256_0<=NULL_TYPE)) ) {
                        alt256=1;
                    }
                    else if ( (LA256_0==EVENT_FILTER_IDENT) ) {
                        alt256=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 256, 0, input);

                        throw nvae;
                    }
                    switch (alt256) {
                        case 1 :
                            // EsperEPL2Ast.g:715:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5143);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:715:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5146);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:715:73: ( constant[false] | filterIdentifier )*
                    loop257:
                    do {
                        int alt257=3;
                        int LA257_0 = input.LA(1);

                        if ( ((LA257_0>=INT_TYPE && LA257_0<=NULL_TYPE)) ) {
                            alt257=1;
                        }
                        else if ( (LA257_0==EVENT_FILTER_IDENT) ) {
                            alt257=2;
                        }


                        switch (alt257) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:715:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator5150);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:715:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5153);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop257;
                        }
                    } while (true);

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:716:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator5168); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:716:42: ( constant[false] | filterIdentifier )
                    int alt258=2;
                    int LA258_0 = input.LA(1);

                    if ( ((LA258_0>=INT_TYPE && LA258_0<=NULL_TYPE)) ) {
                        alt258=1;
                    }
                    else if ( (LA258_0==EVENT_FILTER_IDENT) ) {
                        alt258=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 258, 0, input);

                        throw nvae;
                    }
                    switch (alt258) {
                        case 1 :
                            // EsperEPL2Ast.g:716:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5177);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:716:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5180);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:716:77: ( constant[false] | filterIdentifier )*
                    loop259:
                    do {
                        int alt259=3;
                        int LA259_0 = input.LA(1);

                        if ( ((LA259_0>=INT_TYPE && LA259_0<=NULL_TYPE)) ) {
                            alt259=1;
                        }
                        else if ( (LA259_0==EVENT_FILTER_IDENT) ) {
                            alt259=2;
                        }


                        switch (alt259) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:716:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator5184);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:716:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5187);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop259;
                        }
                    } while (true);

                    if ( input.LA(1)==RPAREN||input.LA(1)==RBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:717:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator5202); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:717:27: ( constant[false] | filterIdentifier )
                    int alt260=2;
                    int LA260_0 = input.LA(1);

                    if ( ((LA260_0>=INT_TYPE && LA260_0<=NULL_TYPE)) ) {
                        alt260=1;
                    }
                    else if ( (LA260_0==EVENT_FILTER_IDENT) ) {
                        alt260=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 260, 0, input);

                        throw nvae;
                    }
                    switch (alt260) {
                        case 1 :
                            // EsperEPL2Ast.g:717:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5205);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:717:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5208);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:717:62: ( constant[false] | filterIdentifier )
                    int alt261=2;
                    int LA261_0 = input.LA(1);

                    if ( ((LA261_0>=INT_TYPE && LA261_0<=NULL_TYPE)) ) {
                        alt261=1;
                    }
                    else if ( (LA261_0==EVENT_FILTER_IDENT) ) {
                        alt261=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 261, 0, input);

                        throw nvae;
                    }
                    switch (alt261) {
                        case 1 :
                            // EsperEPL2Ast.g:717:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5212);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:717:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5215);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:718:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator5223); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:718:31: ( constant[false] | filterIdentifier )
                    int alt262=2;
                    int LA262_0 = input.LA(1);

                    if ( ((LA262_0>=INT_TYPE && LA262_0<=NULL_TYPE)) ) {
                        alt262=1;
                    }
                    else if ( (LA262_0==EVENT_FILTER_IDENT) ) {
                        alt262=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 262, 0, input);

                        throw nvae;
                    }
                    switch (alt262) {
                        case 1 :
                            // EsperEPL2Ast.g:718:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5226);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:718:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5229);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:718:66: ( constant[false] | filterIdentifier )
                    int alt263=2;
                    int LA263_0 = input.LA(1);

                    if ( ((LA263_0>=INT_TYPE && LA263_0<=NULL_TYPE)) ) {
                        alt263=1;
                    }
                    else if ( (LA263_0==EVENT_FILTER_IDENT) ) {
                        alt263=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 263, 0, input);

                        throw nvae;
                    }
                    switch (alt263) {
                        case 1 :
                            // EsperEPL2Ast.g:718:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator5233);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:718:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator5236);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "filterParamComparator"


    // $ANTLR start "filterAtom"
    // EsperEPL2Ast.g:721:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:722:2: ( constant[false] | filterIdentifier )
            int alt265=2;
            int LA265_0 = input.LA(1);

            if ( ((LA265_0>=INT_TYPE && LA265_0<=NULL_TYPE)) ) {
                alt265=1;
            }
            else if ( (LA265_0==EVENT_FILTER_IDENT) ) {
                alt265=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 265, 0, input);

                throw nvae;
            }
            switch (alt265) {
                case 1 :
                    // EsperEPL2Ast.g:722:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom5250);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:723:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom5256);
                    filterIdentifier();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "filterAtom"


    // $ANTLR start "filterIdentifier"
    // EsperEPL2Ast.g:725:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:726:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:726:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier5267); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier5269); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier5271);
            eventPropertyExpr(true);

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "filterIdentifier"


    // $ANTLR start "eventPropertyExpr"
    // EsperEPL2Ast.g:729:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:730:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:730:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr5290); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5292);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:730:44: ( eventPropertyAtomic )*
            loop266:
            do {
                int alt266=2;
                int LA266_0 = input.LA(1);

                if ( ((LA266_0>=EVENT_PROP_SIMPLE && LA266_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt266=1;
                }


                switch (alt266) {
            	case 1 :
            	    // EsperEPL2Ast.g:730:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5295);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop266;
                }
            } while (true);


            match(input, Token.UP, null); 
             if (isLeaveNode) leaveNode(p); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "eventPropertyExpr"


    // $ANTLR start "eventPropertyAtomic"
    // EsperEPL2Ast.g:733:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:734:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt267=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt267=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt267=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt267=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt267=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt267=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt267=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 267, 0, input);

                throw nvae;
            }

            switch (alt267) {
                case 1 :
                    // EsperEPL2Ast.g:734:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic5314); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5316); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:735:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic5323); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5325); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic5327); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:736:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic5334); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5336); 
                    if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:737:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5351); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5353); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:738:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5360); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5362); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic5364); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:739:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5371); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5373); 
                    if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }


                    match(input, Token.UP, null); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "eventPropertyAtomic"


    // $ANTLR start "timePeriod"
    // EsperEPL2Ast.g:742:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:743:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:743:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod5400); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod5402);
            timePeriodDef();

            state._fsp--;

             leaveNode(t); 

            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "timePeriod"


    // $ANTLR start "timePeriodDef"
    // EsperEPL2Ast.g:746:1: timePeriodDef : ( yearPart ( monthPart )? ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | monthPart ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | weekPart ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:747:2: ( yearPart ( monthPart )? ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | monthPart ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | weekPart ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt296=8;
            switch ( input.LA(1) ) {
            case YEAR_PART:
                {
                alt296=1;
                }
                break;
            case MONTH_PART:
                {
                alt296=2;
                }
                break;
            case WEEK_PART:
                {
                alt296=3;
                }
                break;
            case DAY_PART:
                {
                alt296=4;
                }
                break;
            case HOUR_PART:
                {
                alt296=5;
                }
                break;
            case MINUTE_PART:
                {
                alt296=6;
                }
                break;
            case SECOND_PART:
                {
                alt296=7;
                }
                break;
            case MILLISECOND_PART:
                {
                alt296=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 296, 0, input);

                throw nvae;
            }

            switch (alt296) {
                case 1 :
                    // EsperEPL2Ast.g:747:5: yearPart ( monthPart )? ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_yearPart_in_timePeriodDef5418);
                    yearPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:747:14: ( monthPart )?
                    int alt268=2;
                    int LA268_0 = input.LA(1);

                    if ( (LA268_0==MONTH_PART) ) {
                        alt268=1;
                    }
                    switch (alt268) {
                        case 1 :
                            // EsperEPL2Ast.g:747:15: monthPart
                            {
                            pushFollow(FOLLOW_monthPart_in_timePeriodDef5421);
                            monthPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:747:27: ( weekPart )?
                    int alt269=2;
                    int LA269_0 = input.LA(1);

                    if ( (LA269_0==WEEK_PART) ) {
                        alt269=1;
                    }
                    switch (alt269) {
                        case 1 :
                            // EsperEPL2Ast.g:747:28: weekPart
                            {
                            pushFollow(FOLLOW_weekPart_in_timePeriodDef5426);
                            weekPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:747:39: ( dayPart )?
                    int alt270=2;
                    int LA270_0 = input.LA(1);

                    if ( (LA270_0==DAY_PART) ) {
                        alt270=1;
                    }
                    switch (alt270) {
                        case 1 :
                            // EsperEPL2Ast.g:747:40: dayPart
                            {
                            pushFollow(FOLLOW_dayPart_in_timePeriodDef5431);
                            dayPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:747:50: ( hourPart )?
                    int alt271=2;
                    int LA271_0 = input.LA(1);

                    if ( (LA271_0==HOUR_PART) ) {
                        alt271=1;
                    }
                    switch (alt271) {
                        case 1 :
                            // EsperEPL2Ast.g:747:51: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5436);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:747:62: ( minutePart )?
                    int alt272=2;
                    int LA272_0 = input.LA(1);

                    if ( (LA272_0==MINUTE_PART) ) {
                        alt272=1;
                    }
                    switch (alt272) {
                        case 1 :
                            // EsperEPL2Ast.g:747:63: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5441);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:747:76: ( secondPart )?
                    int alt273=2;
                    int LA273_0 = input.LA(1);

                    if ( (LA273_0==SECOND_PART) ) {
                        alt273=1;
                    }
                    switch (alt273) {
                        case 1 :
                            // EsperEPL2Ast.g:747:77: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5446);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:747:90: ( millisecondPart )?
                    int alt274=2;
                    int LA274_0 = input.LA(1);

                    if ( (LA274_0==MILLISECOND_PART) ) {
                        alt274=1;
                    }
                    switch (alt274) {
                        case 1 :
                            // EsperEPL2Ast.g:747:91: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5451);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:748:5: monthPart ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_monthPart_in_timePeriodDef5459);
                    monthPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:748:15: ( weekPart )?
                    int alt275=2;
                    int LA275_0 = input.LA(1);

                    if ( (LA275_0==WEEK_PART) ) {
                        alt275=1;
                    }
                    switch (alt275) {
                        case 1 :
                            // EsperEPL2Ast.g:748:16: weekPart
                            {
                            pushFollow(FOLLOW_weekPart_in_timePeriodDef5462);
                            weekPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:748:27: ( dayPart )?
                    int alt276=2;
                    int LA276_0 = input.LA(1);

                    if ( (LA276_0==DAY_PART) ) {
                        alt276=1;
                    }
                    switch (alt276) {
                        case 1 :
                            // EsperEPL2Ast.g:748:28: dayPart
                            {
                            pushFollow(FOLLOW_dayPart_in_timePeriodDef5467);
                            dayPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:748:38: ( hourPart )?
                    int alt277=2;
                    int LA277_0 = input.LA(1);

                    if ( (LA277_0==HOUR_PART) ) {
                        alt277=1;
                    }
                    switch (alt277) {
                        case 1 :
                            // EsperEPL2Ast.g:748:39: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5472);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:748:50: ( minutePart )?
                    int alt278=2;
                    int LA278_0 = input.LA(1);

                    if ( (LA278_0==MINUTE_PART) ) {
                        alt278=1;
                    }
                    switch (alt278) {
                        case 1 :
                            // EsperEPL2Ast.g:748:51: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5477);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:748:64: ( secondPart )?
                    int alt279=2;
                    int LA279_0 = input.LA(1);

                    if ( (LA279_0==SECOND_PART) ) {
                        alt279=1;
                    }
                    switch (alt279) {
                        case 1 :
                            // EsperEPL2Ast.g:748:65: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5482);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:748:78: ( millisecondPart )?
                    int alt280=2;
                    int LA280_0 = input.LA(1);

                    if ( (LA280_0==MILLISECOND_PART) ) {
                        alt280=1;
                    }
                    switch (alt280) {
                        case 1 :
                            // EsperEPL2Ast.g:748:79: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5487);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:749:5: weekPart ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_weekPart_in_timePeriodDef5495);
                    weekPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:749:14: ( dayPart )?
                    int alt281=2;
                    int LA281_0 = input.LA(1);

                    if ( (LA281_0==DAY_PART) ) {
                        alt281=1;
                    }
                    switch (alt281) {
                        case 1 :
                            // EsperEPL2Ast.g:749:15: dayPart
                            {
                            pushFollow(FOLLOW_dayPart_in_timePeriodDef5498);
                            dayPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:749:25: ( hourPart )?
                    int alt282=2;
                    int LA282_0 = input.LA(1);

                    if ( (LA282_0==HOUR_PART) ) {
                        alt282=1;
                    }
                    switch (alt282) {
                        case 1 :
                            // EsperEPL2Ast.g:749:26: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5503);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:749:37: ( minutePart )?
                    int alt283=2;
                    int LA283_0 = input.LA(1);

                    if ( (LA283_0==MINUTE_PART) ) {
                        alt283=1;
                    }
                    switch (alt283) {
                        case 1 :
                            // EsperEPL2Ast.g:749:38: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5508);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:749:51: ( secondPart )?
                    int alt284=2;
                    int LA284_0 = input.LA(1);

                    if ( (LA284_0==SECOND_PART) ) {
                        alt284=1;
                    }
                    switch (alt284) {
                        case 1 :
                            // EsperEPL2Ast.g:749:52: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5513);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:749:65: ( millisecondPart )?
                    int alt285=2;
                    int LA285_0 = input.LA(1);

                    if ( (LA285_0==MILLISECOND_PART) ) {
                        alt285=1;
                    }
                    switch (alt285) {
                        case 1 :
                            // EsperEPL2Ast.g:749:66: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5518);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:750:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef5526);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:750:13: ( hourPart )?
                    int alt286=2;
                    int LA286_0 = input.LA(1);

                    if ( (LA286_0==HOUR_PART) ) {
                        alt286=1;
                    }
                    switch (alt286) {
                        case 1 :
                            // EsperEPL2Ast.g:750:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5529);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:750:25: ( minutePart )?
                    int alt287=2;
                    int LA287_0 = input.LA(1);

                    if ( (LA287_0==MINUTE_PART) ) {
                        alt287=1;
                    }
                    switch (alt287) {
                        case 1 :
                            // EsperEPL2Ast.g:750:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5534);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:750:39: ( secondPart )?
                    int alt288=2;
                    int LA288_0 = input.LA(1);

                    if ( (LA288_0==SECOND_PART) ) {
                        alt288=1;
                    }
                    switch (alt288) {
                        case 1 :
                            // EsperEPL2Ast.g:750:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5539);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:750:53: ( millisecondPart )?
                    int alt289=2;
                    int LA289_0 = input.LA(1);

                    if ( (LA289_0==MILLISECOND_PART) ) {
                        alt289=1;
                    }
                    switch (alt289) {
                        case 1 :
                            // EsperEPL2Ast.g:750:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5544);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:751:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef5551);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:751:13: ( minutePart )?
                    int alt290=2;
                    int LA290_0 = input.LA(1);

                    if ( (LA290_0==MINUTE_PART) ) {
                        alt290=1;
                    }
                    switch (alt290) {
                        case 1 :
                            // EsperEPL2Ast.g:751:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5554);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:751:27: ( secondPart )?
                    int alt291=2;
                    int LA291_0 = input.LA(1);

                    if ( (LA291_0==SECOND_PART) ) {
                        alt291=1;
                    }
                    switch (alt291) {
                        case 1 :
                            // EsperEPL2Ast.g:751:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5559);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:751:41: ( millisecondPart )?
                    int alt292=2;
                    int LA292_0 = input.LA(1);

                    if ( (LA292_0==MILLISECOND_PART) ) {
                        alt292=1;
                    }
                    switch (alt292) {
                        case 1 :
                            // EsperEPL2Ast.g:751:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5564);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:752:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef5571);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:752:15: ( secondPart )?
                    int alt293=2;
                    int LA293_0 = input.LA(1);

                    if ( (LA293_0==SECOND_PART) ) {
                        alt293=1;
                    }
                    switch (alt293) {
                        case 1 :
                            // EsperEPL2Ast.g:752:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5574);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:752:29: ( millisecondPart )?
                    int alt294=2;
                    int LA294_0 = input.LA(1);

                    if ( (LA294_0==MILLISECOND_PART) ) {
                        alt294=1;
                    }
                    switch (alt294) {
                        case 1 :
                            // EsperEPL2Ast.g:752:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5579);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:753:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef5586);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:753:15: ( millisecondPart )?
                    int alt295=2;
                    int LA295_0 = input.LA(1);

                    if ( (LA295_0==MILLISECOND_PART) ) {
                        alt295=1;
                    }
                    switch (alt295) {
                        case 1 :
                            // EsperEPL2Ast.g:753:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5589);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:754:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5596);
                    millisecondPart();

                    state._fsp--;


                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "timePeriodDef"


    // $ANTLR start "yearPart"
    // EsperEPL2Ast.g:757:1: yearPart : ^( YEAR_PART valueExpr ) ;
    public final void yearPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:758:2: ( ^( YEAR_PART valueExpr ) )
            // EsperEPL2Ast.g:758:4: ^( YEAR_PART valueExpr )
            {
            match(input,YEAR_PART,FOLLOW_YEAR_PART_in_yearPart5610); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_yearPart5612);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "yearPart"


    // $ANTLR start "monthPart"
    // EsperEPL2Ast.g:761:1: monthPart : ^( MONTH_PART valueExpr ) ;
    public final void monthPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:762:2: ( ^( MONTH_PART valueExpr ) )
            // EsperEPL2Ast.g:762:4: ^( MONTH_PART valueExpr )
            {
            match(input,MONTH_PART,FOLLOW_MONTH_PART_in_monthPart5627); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_monthPart5629);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "monthPart"


    // $ANTLR start "weekPart"
    // EsperEPL2Ast.g:765:1: weekPart : ^( WEEK_PART valueExpr ) ;
    public final void weekPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:766:2: ( ^( WEEK_PART valueExpr ) )
            // EsperEPL2Ast.g:766:4: ^( WEEK_PART valueExpr )
            {
            match(input,WEEK_PART,FOLLOW_WEEK_PART_in_weekPart5644); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_weekPart5646);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "weekPart"


    // $ANTLR start "dayPart"
    // EsperEPL2Ast.g:769:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:770:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:770:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart5661); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart5663);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "dayPart"


    // $ANTLR start "hourPart"
    // EsperEPL2Ast.g:773:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:774:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:774:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart5678); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart5680);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "hourPart"


    // $ANTLR start "minutePart"
    // EsperEPL2Ast.g:777:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:778:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:778:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart5695); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart5697);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "minutePart"


    // $ANTLR start "secondPart"
    // EsperEPL2Ast.g:781:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:782:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:782:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart5712); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart5714);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "secondPart"


    // $ANTLR start "millisecondPart"
    // EsperEPL2Ast.g:785:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:786:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:786:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart5729); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart5731);
            valueExpr();

            state._fsp--;


            match(input, Token.UP, null); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "millisecondPart"


    // $ANTLR start "substitution"
    // EsperEPL2Ast.g:789:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:790:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:790:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution5746); 
             leaveNode(s); 

            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "substitution"


    // $ANTLR start "constant"
    // EsperEPL2Ast.g:793:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:794:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt297=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt297=1;
                }
                break;
            case LONG_TYPE:
                {
                alt297=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt297=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt297=4;
                }
                break;
            case STRING_TYPE:
                {
                alt297=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt297=6;
                }
                break;
            case NULL_TYPE:
                {
                alt297=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 297, 0, input);

                throw nvae;
            }

            switch (alt297) {
                case 1 :
                    // EsperEPL2Ast.g:794:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant5762); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:795:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant5771); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:796:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant5780); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:797:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant5789); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:798:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant5805); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:799:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant5821); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:800:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant5834); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;

            }
        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "constant"


    // $ANTLR start "number"
    // EsperEPL2Ast.g:803:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:804:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
            // EsperEPL2Ast.g:
            {
            if ( (input.LA(1)>=INT_TYPE && input.LA(1)<=DOUBLE_TYPE) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }


            }

        }

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "number"

    // Delegated rules


 

    public static final BitSet FOLLOW_ANNOTATION_in_annotation92 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L,0x3800000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L,0x3800000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L,0x1800000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L,0x1800000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EXPRESSIONDECL_in_expressionDecl204 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_expressionDecl206 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_expressionDecl208 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_expressionLambdaDecl_in_expressionDecl210 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GOES_in_expressionLambdaDecl227 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_expressionLambdaDecl230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_exprCol_in_expressionLambdaDecl234 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule251 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule254 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x2000001000000000L,0x080200B000000000L,0x0000000000000014L});
    public static final BitSet FOLLOW_expressionDecl_in_startEPLExpressionRule259 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x2000001000000000L,0x080200B000000000L,0x0000000000000014L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule263 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule280 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule284 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createIndexExpr_in_eplExpressionRule288 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule292 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createSchemaExpr_in_eplExpressionRule296 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule300 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule304 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_forExpr_in_eplExpressionRule307 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr326 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onStreamExpr_in_onExpr328 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00011E0000000000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr333 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onUpdateExpr_in_onExpr337 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr341 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr344 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr347 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr354 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onMergeExpr_in_onExpr358 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_STREAM_in_onStreamExpr380 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onStreamExpr383 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onStreamExpr387 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_onStreamExpr390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_MERGE_EXPR_in_onMergeExpr408 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr410 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000300L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr412 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000300L});
    public static final BitSet FOLLOW_mergeItem_in_onMergeExpr415 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000001000000300L});
    public static final BitSet FOLLOW_whereClause_in_onMergeExpr418 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_mergeMatched_in_mergeItem434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mergeUnmatched_in_mergeItem438 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MERGE_MAT_in_mergeMatched453 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_mergeMatchedItem_in_mergeMatched455 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F9C01L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_mergeMatched458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MERGE_UPD_in_mergeMatchedItem476 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_mergeMatchedItem478 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_whereClause_in_mergeMatchedItem481 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MERGE_DEL_in_mergeMatchedItem494 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_whereClause_in_mergeMatchedItem496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_INT_TYPE_in_mergeMatchedItem500 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_mergeInsert_in_mergeMatchedItem508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MERGE_UNM_in_mergeUnmatched522 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_mergeInsert_in_mergeUnmatched524 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F9C01L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_mergeUnmatched527 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MERGE_INS_in_mergeInsert546 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_selectionList_in_mergeInsert548 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x4000000004100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_mergeInsert550 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x4000000004000000L});
    public static final BitSet FOLLOW_exprCol_in_mergeInsert553 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_whereClause_in_mergeInsert556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr576 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr578 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000001000000008L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr580 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr583 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr586 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr603 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr605 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr608 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr628 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr630 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr633 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr636 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x000060000C000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr638 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x000060000C000000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr641 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000600008000000L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr645 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000400008000000L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr648 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr651 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr654 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr674 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr676 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr678 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr680 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr717 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr719 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr722 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr726 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr741 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr743 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr745 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr748 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment763 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_onSetAssignment765 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment768 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom782 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom784 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom787 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr805 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr807 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000900000L,0x0100004000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr810 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000900000L,0x0100004000000000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr814 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000900000L,0x0100004000000000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr817 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000900000L,0x0100004000000000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr831 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr834 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr863 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr874 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr894 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr898 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_indexColList_in_createIndexExpr900 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INDEXCOL_in_indexColList915 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_indexCol_in_indexColList917 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_INDEXCOL_in_indexCol932 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_indexCol934 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_indexCol936 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert952 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList969 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList971 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000002000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList974 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000002000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList993 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList995 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList998 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement1013 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement1015 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement1017 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_LBRACK_in_createColTypeListElement1019 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement1034 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement1044 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement1064 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement1068 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement1090 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement1093 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr1129 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createVariableExpr1131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr1133 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr1136 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr1156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000900000L,0x0100004000000000L,0x00000000000000E0L});
    public static final BitSet FOLLOW_variantList_in_createSchemaExpr1161 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_createColTypeList_in_createSchemaExpr1163 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_VAR_in_createSchemaExpr1169 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1171 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_createSchemaQual_in_createSchemaExpr1176 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaQual1194 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaQual1196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprCol_in_createSchemaQual1198 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIANT_LIST_in_variantList1214 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_variantList1216 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr1234 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2000001000000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr1240 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr1245 = new BitSet(new long[]{0x0000000000000002L,0x0001200000000000L,0x178060000C000000L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr1250 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x178060000C000000L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr1257 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x1780600008000000L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr1265 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x1780400008000000L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr1272 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x1780400000000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr1279 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr1286 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr1293 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr1312 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_insertIntoExpr1321 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprCol_in_insertIntoExpr1324 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRCOL_in_exprCol1343 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1345 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1348 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause1366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause1368 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1381 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1384 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1398 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1401 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00001E8000000000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1404 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00001E8000000000L});
    public static final BitSet FOLLOW_FOR_in_forExpr1424 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_forExpr1426 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_forExpr1428 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1447 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1449 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1456 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000021000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1462 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000021000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1468 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000021000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1474 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1480 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1486 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1506 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1523 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1525 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1529 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1537 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1552 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1554 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1572 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1574 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1591 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1593 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1595 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1615 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1617 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000000C000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1640 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1642 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1644 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1662 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1664 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000012000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1699 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1701 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0006800000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1703 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1732 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1734 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0006800000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1738 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1750 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1772 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1774 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1791 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1793 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1795 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1812 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1815 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1841 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1843 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1846 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1860 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1862 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1865 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1898 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1900 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1903 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1907 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1910 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1925 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1927 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1930 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1934 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1937 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1952 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1954 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1957 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1961 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1964 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1979 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1981 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1984 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1988 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1991 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression2012 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression2015 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000800000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression2019 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000800000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression2023 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000800000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression2027 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000800000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression2031 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression2036 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression2041 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_streamExpression2045 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr2069 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr2071 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr2074 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000080L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr2076 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr2080 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression2100 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression2102 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom2121 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom2123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000E00L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom2126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom2129 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom2133 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom2135 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement2165 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement2167 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2170 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2186 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2189 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2210 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression2212 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2229 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression2231 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2233 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2241 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2262 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression2264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression2266 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression2269 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2283 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2286 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr2303 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2305 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2307 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr2310 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause2332 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause2334 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause2352 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2354 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2357 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause2375 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2377 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2380 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2400 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2402 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2404 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2427 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2429 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2447 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2449 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000078000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2461 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2463 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2466 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2483 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2485 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2496 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2498 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2514 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2516 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2527 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2529 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2545 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2547 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2558 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2560 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2563 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2576 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2578 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2593 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2595 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000078000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2598 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2614 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2617 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000041000078000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2619 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000041000078000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2623 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2625 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2629 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2632 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2650 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2652 = new BitSet(new long[]{0x0020000037CC23C0L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2654 = new BitSet(new long[]{0x0020000037CC23C0L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2656 = new BitSet(new long[]{0x0020000037CC23C0L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2658 = new BitSet(new long[]{0x0020000037CC23C0L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2660 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2662 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2679 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2681 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2696 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2709 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2711 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2723 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2725 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2747 = new BitSet(new long[]{0x0003800037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2772 = new BitSet(new long[]{0x0000000037CC23C2L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047F07804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2781 = new BitSet(new long[]{0x0000000037CC23C2L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2812 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2814 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2816 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2819 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2833 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2835 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2837 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2840 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2854 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2856 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2858 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2870 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2872 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2874 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2886 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2888 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2890 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047F07804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2899 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2904 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2917 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2919 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2921 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047F07804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2930 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2935 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2948 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2950 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2974 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr3002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr3007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFuncChain_in_valueExpr3015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr3020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr3025 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr3031 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr3036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr3041 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr3046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr3051 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr3057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr3064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotExpr_in_valueExpr3069 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newExpr_in_valueExpr3074 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime3087 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime3096 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime3103 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime3111 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime3113 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime3115 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime3128 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime3134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime3139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime3144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime3154 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime3156 = new BitSet(new long[]{0x0000000000000008L,0xA000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime3167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime3174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList3187 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList3194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList3200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3219 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L,0x0000000400000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3222 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L,0x0000000400000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3225 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L,0x0000000400000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3229 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3232 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3235 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator3259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator3262 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator3265 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator3284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator3287 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator3290 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator3293 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3312 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator3315 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator3318 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator3321 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3342 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr3344 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3363 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr3365 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr3386 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3405 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3407 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3409 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3421 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3423 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3425 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3462 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000006000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_selectionList_in_subQueryExpr3465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3470 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3488 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3490 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000800000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3493 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3498 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3502 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3505 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3525 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3528 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3541 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3544 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3564 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3566 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000208000000000L});
    public static final BitSet FOLLOW_set_in_inExpr3568 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3574 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC3C100003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3577 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC3C100003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_inExpr3581 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3596 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3598 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000208000000000L});
    public static final BitSet FOLLOW_set_in_inExpr3600 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3606 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC3C100003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3609 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC3C100003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_inExpr3613 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3628 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3630 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000208000000000L});
    public static final BitSet FOLLOW_set_in_inExpr3632 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3638 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3640 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000410000000000L});
    public static final BitSet FOLLOW_set_in_inExpr3642 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3657 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3659 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000208000000000L});
    public static final BitSet FOLLOW_set_in_inExpr3661 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3667 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000410000000000L});
    public static final BitSet FOLLOW_set_in_inExpr3671 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3696 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3698 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3700 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3711 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3713 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3715 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3718 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3738 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3740 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3742 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3745 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3758 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3760 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3762 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3765 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3784 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3786 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3788 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3799 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3801 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3803 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3822 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3825 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3829 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_aggregationFilterExpr_in_builtinFunc3831 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3843 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3846 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3850 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_aggregationFilterExpr_in_builtinFunc3852 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3864 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3868 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3872 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_aggregationFilterExpr_in_builtinFunc3876 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3888 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3891 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3895 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_aggregationFilterExpr_in_builtinFunc3897 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3909 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3912 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3916 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_aggregationFilterExpr_in_builtinFunc3918 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3930 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3933 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3937 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_aggregationFilterExpr_in_builtinFunc3939 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3951 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3954 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8003L,0x0000000000000003L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3958 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3961 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3973 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3976 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8003L,0x0000000000000003L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3980 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3983 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WINDOW_AGGREG_in_builtinFunc3995 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3998 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc4002 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc4015 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4017 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4019 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4022 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc4037 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4039 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4041 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSTAIL_in_builtinFunc4054 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4056 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4058 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSCOUNT_in_builtinFunc4071 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4073 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSWINDOW_in_builtinFunc4085 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4087 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc4099 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc4103 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc4105 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc4118 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4120 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc4122 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc4125 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_TYPEOF_in_builtinFunc4139 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4141 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc4153 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4155 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc4157 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc4169 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc4171 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc4183 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_AGG_FILTER_EXPR_in_aggregationFilterExpr4200 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_aggregationFilterExpr4202 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ACCESS_AGG_in_accessValueExpr4216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_accessValueExprChoice_in_accessValueExpr4218 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExprChoice4233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExprChoice4240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExprChoice4242 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExprChoice4244 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_accessValueExprChoice4250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr4266 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr4269 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr4290 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4292 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr4306 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4308 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4310 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr4322 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4324 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4326 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr4337 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4339 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4341 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr4353 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4355 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4357 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr4368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4370 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4372 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr4383 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4385 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4387 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr4398 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4400 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4402 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr4414 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4416 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4418 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4421 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_DOT_EXPR_in_dotExpr4441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dotExpr4443 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_dotExpr4445 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_NEWKW_in_newExpr4463 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_newAssign_in_newExpr4465 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_NEW_ITEM_in_newAssign4481 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_newAssign4483 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_newAssign4486 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libFuncChain4506 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_libOrPropFunction_in_libFuncChain4508 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0001000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4528 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunctionWithClass4531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunctionWithClass4535 = new BitSet(new long[]{0x0020400037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38280003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunctionWithClass4538 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38280003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_libFunctionArgItem_in_libFunctionWithClass4542 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38280003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_LPAREN_in_libFunctionWithClass4545 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_expressionLambdaDecl_in_libFunctionArgItem4559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExprWithTime_in_libFunctionArgItem4563 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_libOrPropFunction4578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libOrPropFunction4588 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule4603 = new BitSet(new long[]{0x000000000000D800L,0x0000000000000000L,0x000000000060001AL,0x0808000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule4607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice4621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice4626 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice4636 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4638 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4652 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice4654 = new BitSet(new long[]{0x000000000000D800L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4656 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4670 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4672 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice4686 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4688 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38010003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4693 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice4695 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_exprChoice4700 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4714 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4716 = new BitSet(new long[]{0x000000000000D800L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4719 = new BitSet(new long[]{0x000000000000D808L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4742 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_distinctExpressions4744 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4763 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_followedByItem_in_patternOp4765 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_followedByItem_in_patternOp4767 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_followedByItem_in_patternOp4770 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4786 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4788 = new BitSet(new long[]{0x000000000000D800L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4790 = new BitSet(new long[]{0x000000000000D808L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4793 = new BitSet(new long[]{0x000000000000D808L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4809 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4811 = new BitSet(new long[]{0x000000000000D800L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4813 = new BitSet(new long[]{0x000000000000D808L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4816 = new BitSet(new long[]{0x000000000000D808L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_FOLLOWED_BY_ITEM_in_followedByItem4837 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_followedByItem4839 = new BitSet(new long[]{0x000000000000D800L,0x0000000000000000L,0x000000000060001AL,0x0008000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_followedByItem4842 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4868 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4870 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4872 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4874 = new BitSet(new long[]{0x0020000037CC23C8L,0xF00000000001F7E0L,0x00010007E0000001L,0xC40000077707806DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4894 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4899 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000080L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000043L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4901 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000043L});
    public static final BitSet FOLLOW_patternFilterAnno_in_patternFilterExpr4904 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4908 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_ATCHAR_in_patternFilterAnno4928 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterAnno4930 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000078000L});
    public static final BitSet FOLLOW_number_in_patternFilterAnno4932 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4947 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4949 = new BitSet(new long[]{0x0000000037CC23C0L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4951 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4959 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4961 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4969 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4971 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4978 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4993 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4995 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4998 = new BitSet(new long[]{0x0000000037CC23C8L,0x100000000001E7E0L,0x00010007E0000000L,0xC00000047707804DL,0xBCC38000003F8001L,0x0000000000000003L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator5014 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator5016 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator5023 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator5025 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator5032 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator5034 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator5041 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator5043 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator5050 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator5052 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator5059 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator5061 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator5068 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5077 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5080 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000410000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5087 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000410000000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5090 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator5101 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5103 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5110 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5113 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5117 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000410000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5120 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000410000000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5123 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator5134 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5136 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5143 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5153 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5157 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator5168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5170 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5177 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5180 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5184 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00004100003F8000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator5191 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator5202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5205 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5208 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5212 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5215 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator5223 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5226 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5229 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000001000L,0x0000000000000000L,0x00000000003F8000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator5233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator5236 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom5250 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom5256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier5267 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier5269 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier5271 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr5290 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5292 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x007E000000000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5295 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x007E000000000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic5314 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5316 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic5323 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic5327 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic5334 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5336 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic5338 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5351 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5353 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5360 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5362 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic5364 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5371 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5373 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic5375 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod5400 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod5402 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_yearPart_in_timePeriodDef5418 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_monthPart_in_timePeriodDef5421 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007E00L});
    public static final BitSet FOLLOW_weekPart_in_timePeriodDef5426 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007C00L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5431 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007800L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5436 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5441 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5446 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_monthPart_in_timePeriodDef5459 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007E00L});
    public static final BitSet FOLLOW_weekPart_in_timePeriodDef5462 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007C00L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5467 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007800L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5472 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5477 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5482 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5487 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekPart_in_timePeriodDef5495 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007C00L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5498 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007800L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5503 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5508 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5513 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5526 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007800L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5529 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5534 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5539 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5551 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000007000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5554 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5559 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5564 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5571 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000006000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5574 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5586 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5589 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YEAR_PART_in_yearPart5610 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_yearPart5612 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MONTH_PART_in_monthPart5627 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_monthPart5629 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEK_PART_in_weekPart5644 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_weekPart5646 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart5661 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart5663 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart5678 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart5680 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart5695 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart5697 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart5712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart5714 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart5729 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart5731 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution5746 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant5762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant5771 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant5780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant5789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant5805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant5821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant5834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}