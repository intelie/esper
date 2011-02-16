// $ANTLR 3.2 Sep 23, 2009 12:02:23 EsperEPL2Ast.g 2011-02-15 13:09:15

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "SCHEMA", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PREVIOUSTAIL", "PREVIOUSCOUNT", "PREVIOUSWINDOW", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "TYPEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "INDEX", "TIMEPERIOD_YEAR", "TIMEPERIOD_YEARS", "TIMEPERIOD_MONTH", "TIMEPERIOD_MONTHS", "TIMEPERIOD_WEEK", "TIMEPERIOD_WEEKS", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "FOR", "WHILE", "USING", "MERGE", "MATCHED", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "FOLLOWED_BY_ITEM", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "EXPRCOL", "INDEXCOL", "CONCAT", "LIB_FUNCTION", "LIB_FUNC_CHAIN", "DOT_EXPR", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "YEAR_PART", "MONTH_PART", "WEEK_PART", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_INDEX_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_STREAM", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_UPDATE_EXPR", "ON_MERGE_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_COL_TYPE_LIST", "CREATE_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "WINDOW_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "CREATE_SCHEMA_EXPR", "CREATE_SCHEMA_EXPR_QUAL", "CREATE_SCHEMA_EXPR_INH", "VARIANT_LIST", "MERGE_UPD", "MERGE_INS", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "LPAREN", "RPAREN", "EQUALS", "DOT", "LBRACK", "RBRACK", "STAR", "BOR", "PLUS", "QUESTION", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "FOLLOWMAX_BEGIN", "FOLLOWMAX_END", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=185;
    public static final int FLOAT_SUFFIX=352;
    public static final int STAR=293;
    public static final int DOT_EXPR=194;
    public static final int NUMERIC_PARAM_LIST=124;
    public static final int ISTREAM=60;
    public static final int MOD=311;
    public static final int OUTERJOIN_EXPR=167;
    public static final int LIB_FUNC_CHAIN=193;
    public static final int CREATE_COL_TYPE_LIST=247;
    public static final int MONTH_PART=199;
    public static final int MERGE_INS=263;
    public static final int BSR=334;
    public static final int LIB_FUNCTION=192;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=105;
    public static final int FULL_OUTERJOIN_EXPR=171;
    public static final int MATCHREC_PATTERN_CONCAT=275;
    public static final int INC=327;
    public static final int LNOT=323;
    public static final int RPAREN=288;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=298;
    public static final int BSR_ASSIGN=335;
    public static final int CAST_EXPR=226;
    public static final int MATCHES=116;
    public static final int USING=120;
    public static final int STREAM_EXPR=166;
    public static final int TIMEPERIOD_SECONDS=102;
    public static final int NOT_EQUAL=303;
    public static final int METADATASQL=68;
    public static final int EVENT_FILTER_PROPERTY_EXPR=134;
    public static final int LAST_AGGREG=254;
    public static final int REGEXP=9;
    public static final int MATCHED=122;
    public static final int FOLLOWED_BY_EXPR=127;
    public static final int FOLLOWED_BY=315;
    public static final int HOUR_PART=202;
    public static final int RBRACK=292;
    public static final int MATCHREC_PATTERN_NESTED=277;
    public static final int MATCH_UNTIL_RANGE_CLOSED=245;
    public static final int GE=307;
    public static final int METHOD_JOIN_EXPR=241;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=133;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=132;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=328;
    public static final int EVENT_FILTER_NOT_IN=144;
    public static final int INSERTINTO_STREAM_NAME=214;
    public static final int NUM_DOUBLE=271;
    public static final int TIMEPERIOD_MILLISEC=103;
    public static final int UNARY_MINUS=195;
    public static final int LCURLY=312;
    public static final int RETAINUNION=64;
    public static final int DBWHERE_CLAUSE=212;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=142;
    public static final int GROUP=44;
    public static final int EMAILAT=343;
    public static final int WS=344;
    public static final int SUBSELECT_GROUP_EXPR=218;
    public static final int FOLLOWED_BY_ITEM=128;
    public static final int YEAR_PART=198;
    public static final int ON_SELECT_INSERT_EXPR=236;
    public static final int TYPEOF=78;
    public static final int ESCAPECHAR=318;
    public static final int EXPRCOL=189;
    public static final int SL_COMMENT=345;
    public static final int NULL_TYPE=270;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=243;
    public static final int GT=305;
    public static final int BNOT=324;
    public static final int WHERE_EXPR=153;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=168;
    public static final int LAND=341;
    public static final int NOT_REGEXP=209;
    public static final int MATCH_UNTIL_EXPR=242;
    public static final int EVENT_PROP_EXPR=175;
    public static final int LBRACK=291;
    public static final int VIEW_EXPR=150;
    public static final int MERGE_UPD=262;
    public static final int ANNOTATION=250;
    public static final int LONG_TYPE=265;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=135;
    public static final int MATCHREC_PATTERN=273;
    public static final int ON_MERGE_EXPR=235;
    public static final int TIMEPERIOD_SEC=100;
    public static final int TICKED_STRING_LITERAL=319;
    public static final int ON_SELECT_EXPR=233;
    public static final int MINUTE_PART=203;
    public static final int PATTERN_NOT_EXPR=131;
    public static final int SQL_NE=302;
    public static final int SUM=18;
    public static final int HexDigit=350;
    public static final int UPDATE_EXPR=256;
    public static final int LPAREN=287;
    public static final int IN_SUBSELECT_EXPR=220;
    public static final int AT=86;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=106;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=279;
    public static final int NOT_IN_RANGE=216;
    public static final int TIMEPERIOD_MONTH=90;
    public static final int OFFSET=110;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int SECOND_PART=204;
    public static final int PREVIOUS=69;
    public static final int PREVIOUSWINDOW=72;
    public static final int MATCH_RECOGNIZE=112;
    public static final int IDENT=286;
    public static final int DATABASE_JOIN_EXPR=152;
    public static final int BXOR=301;
    public static final int PLUS=295;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=94;
    public static final int CREATE_SCHEMA_EXPR=258;
    public static final int EXISTS=74;
    public static final int EVENT_PROP_INDEXED=178;
    public static final int CREATE_INDEX_EXPR=227;
    public static final int TIMEPERIOD_MILLISECOND=104;
    public static final int EVAL_NOTEQUALS_EXPR=159;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=244;
    public static final int CREATE_VARIABLE_EXPR=240;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=280;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=129;
    public static final int RIGHT_OUTERJOIN_EXPR=170;
    public static final int NUMBERSETSTAR=249;
    public static final int LAST_OPERATOR=223;
    public static final int PATTERN_FILTER_EXPR=130;
    public static final int MERGE=121;
    public static final int FOLLOWMAX_END=317;
    public static final int EVAL_AND_EXPR=156;
    public static final int LEFT_OUTERJOIN_EXPR=169;
    public static final int EPL_EXPR=272;
    public static final int GROUP_BY_EXPR=172;
    public static final int SET=83;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=77;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=176;
    public static final int MINUS=309;
    public static final int SEMI=342;
    public static final int INDEXCOL=190;
    public static final int STAR_ASSIGN=330;
    public static final int PREVIOUSCOUNT=71;
    public static final int VARIANT_LIST=261;
    public static final int FIRST_AGGREG=253;
    public static final int COLON=297;
    public static final int EVAL_EQUALS_GROUP_EXPR=160;
    public static final int BAND_ASSIGN=340;
    public static final int PREVIOUSTAIL=70;
    public static final int SCHEMA=62;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=186;
    public static final int NOT_IN_SET=206;
    public static final int VALUE_NULL=108;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=179;
    public static final int SL=336;
    public static final int NOT_IN_SUBSELECT_EXPR=221;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=148;
    public static final int SR=332;
    public static final int RCURLY=313;
    public static final int PLUS_ASSIGN=326;
    public static final int EXISTS_SUBSELECT_EXPR=219;
    public static final int DAY_PART=201;
    public static final int EVENT_FILTER_IN=143;
    public static final int DIV=310;
    public static final int WEEK_PART=200;
    public static final int OBJECT_PARAM_ORDERED_EXPR=126;
    public static final int OctalEscape=349;
    public static final int MILLISECOND_PART=205;
    public static final int BETWEEN=7;
    public static final int ROW_LIMIT_EXPR=109;
    public static final int FIRST=52;
    public static final int PRIOR=73;
    public static final int SELECTION_EXPR=163;
    public static final int LW=76;
    public static final int CAST=79;
    public static final int LOR=308;
    public static final int WILDCARD_SELECT=213;
    public static final int LT=304;
    public static final int EXPONENT=351;
    public static final int PATTERN_INCL_EXPR=151;
    public static final int WHILE=119;
    public static final int ORDER_BY_EXPR=173;
    public static final int BOOL_TYPE=269;
    public static final int ANNOTATION_ARRAY=251;
    public static final int MOD_ASSIGN=331;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=222;
    public static final int COUNT=26;
    public static final int EQUALS=289;
    public static final int RETAININTERSECTION=65;
    public static final int WINDOW_AGGREG=255;
    public static final int DIV_ASSIGN=325;
    public static final int SL_ASSIGN=337;
    public static final int TIMEPERIOD_WEEKS=93;
    public static final int PATTERN=66;
    public static final int SQL=67;
    public static final int FULL=40;
    public static final int WEEKDAY=75;
    public static final int MATCHREC_AFTER_SKIP=278;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=234;
    public static final int ARRAY_EXPR=197;
    public static final int CREATE_COL_TYPE=248;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=107;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=161;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=145;
    public static final int TIMEPERIOD_SECOND=101;
    public static final int COALESCE=22;
    public static final int FLOAT_TYPE=266;
    public static final int SUBSELECT_EXPR=217;
    public static final int ANNOTATION_VALUE=252;
    public static final int NUMERIC_PARAM_RANGE=123;
    public static final int CONCAT=191;
    public static final int CLASS_IDENT=147;
    public static final int MATCHREC_PATTERN_ALTER=276;
    public static final int ON_EXPR=230;
    public static final int CREATE_WINDOW_EXPR=228;
    public static final int PROPERTY_SELECTION_STREAM=137;
    public static final int ON_DELETE_EXPR=232;
    public static final int ON=41;
    public static final int NUM_LONG=320;
    public static final int TIME_PERIOD=196;
    public static final int DOUBLE_TYPE=267;
    public static final int DELETE=81;
    public static final int INT_TYPE=264;
    public static final int MATCHREC_PARTITION=284;
    public static final int EVAL_BITWISE_EXPR=155;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=174;
    public static final int TIMEPERIOD_HOURS=97;
    public static final int VARIABLE=84;
    public static final int SUBSTITUTION=225;
    public static final int UNTIL=85;
    public static final int STRING_TYPE=268;
    public static final int ON_SET_EXPR=239;
    public static final int MATCHREC_DEFINE_ITEM=281;
    public static final int NUM_INT=314;
    public static final int STDDEV=24;
    public static final int CREATE_SCHEMA_EXPR_INH=260;
    public static final int ON_EXPR_FROM=238;
    public static final int NUM_FLOAT=321;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=136;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=347;
    public static final int WEEKDAY_OPERATOR=224;
    public static final int WHERE=16;
    public static final int DEC=329;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=125;
    public static final int BXOR_ASSIGN=338;
    public static final int AFTER_LIMIT_EXPR=184;
    public static final int ORDER=56;
    public static final int SNAPSHOT=82;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=181;
    public static final int EVENT_FILTER_PARAM=140;
    public static final int IRSTREAM=61;
    public static final int UPDATE=111;
    public static final int MAX=20;
    public static final int FOR=118;
    public static final int ON_STREAM=231;
    public static final int DEFINE=114;
    public static final int TIMEPERIOD_YEARS=89;
    public static final int TIMEPERIOD_DAYS=95;
    public static final int EVENT_FILTER_RANGE=141;
    public static final int INDEX=87;
    public static final int ML_COMMENT=346;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=180;
    public static final int BOR_ASSIGN=339;
    public static final int COMMA=285;
    public static final int WHEN_LIMIT_EXPR=187;
    public static final int PARTITION=115;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=183;
    public static final int SOME=49;
    public static final int TIMEPERIOD_HOUR=96;
    public static final int ALL=47;
    public static final int MATCHREC_MEASURE_ITEM=283;
    public static final int BOR=294;
    public static final int EQUAL=322;
    public static final int EVENT_FILTER_NOT_BETWEEN=146;
    public static final int IN_RANGE=215;
    public static final int DOT=290;
    public static final int CURRENT_TIMESTAMP=80;
    public static final int MATCHREC_MEASURES=282;
    public static final int TIMEPERIOD_WEEK=92;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=138;
    public static final int INSERTINTO_EXPR=188;
    public static final int HAVING_EXPR=154;
    public static final int UNIDIRECTIONAL=63;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=246;
    public static final int EVAL_EQUALS_EXPR=158;
    public static final int TIMEPERIOD_MINUTES=99;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=208;
    public static final int EVENT_LIMIT_EXPR=182;
    public static final int TIMEPERIOD_MINUTE=98;
    public static final int NOT_BETWEEN=207;
    public static final int EVAL_OR_EXPR=157;
    public static final int ON_SELECT_INSERT_OUTPUT=237;
    public static final int AFTER=117;
    public static final int MEASURES=113;
    public static final int MATCHREC_PATTERN_ATOM=274;
    public static final int BAND=300;
    public static final int QUOTED_STRING_LITERAL=299;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=296;
    public static final int OBSERVER_EXPR=149;
    public static final int EVENT_FILTER_IDENT=139;
    public static final int CREATE_SCHEMA_EXPR_QUAL=259;
    public static final int EVENT_PROP_MAPPED=177;
    public static final int UnicodeEscape=348;
    public static final int TIMEPERIOD_YEAR=88;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=210;
    public static final int TIMEPERIOD_MONTHS=91;
    public static final int FOLLOWMAX_BEGIN=316;
    public static final int SELECTION_ELEMENT_EXPR=164;
    public static final int CREATE_WINDOW_SELECT_EXPR=229;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=257;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=165;
    public static final int SR_ASSIGN=333;
    public static final int DBFROM_CLAUSE=211;
    public static final int LE=306;
    public static final int EVAL_IDENT=162;

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


    // $ANTLR start "startEPLExpressionRule"
    // EsperEPL2Ast.g:75:1: startEPLExpressionRule : ^( EPL_EXPR ( annotation[true] )* eplExpressionRule ) ;
    public final void startEPLExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:76:2: ( ^( EPL_EXPR ( annotation[true] )* eplExpressionRule ) )
            // EsperEPL2Ast.g:76:4: ^( EPL_EXPR ( annotation[true] )* eplExpressionRule )
            {
            match(input,EPL_EXPR,FOLLOW_EPL_EXPR_in_startEPLExpressionRule202); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:76:15: ( annotation[true] )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==ANNOTATION) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // EsperEPL2Ast.g:76:15: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startEPLExpressionRule204);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            pushFollow(FOLLOW_eplExpressionRule_in_startEPLExpressionRule208);
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
    // EsperEPL2Ast.g:79:1: eplExpressionRule : ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ( forExpr )? ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:80:2: ( ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ( forExpr )? )
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ( forExpr )?
            {
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr )
            int alt6=7;
            switch ( input.LA(1) ) {
            case SELECTION_EXPR:
            case INSERTINTO_EXPR:
                {
                alt6=1;
                }
                break;
            case CREATE_WINDOW_EXPR:
                {
                alt6=2;
                }
                break;
            case CREATE_INDEX_EXPR:
                {
                alt6=3;
                }
                break;
            case CREATE_VARIABLE_EXPR:
                {
                alt6=4;
                }
                break;
            case CREATE_SCHEMA_EXPR:
                {
                alt6=5;
                }
                break;
            case ON_EXPR:
                {
                alt6=6;
                }
                break;
            case UPDATE_EXPR:
                {
                alt6=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // EsperEPL2Ast.g:80:5: selectExpr
                    {
                    pushFollow(FOLLOW_selectExpr_in_eplExpressionRule225);
                    selectExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:80:18: createWindowExpr
                    {
                    pushFollow(FOLLOW_createWindowExpr_in_eplExpressionRule229);
                    createWindowExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:80:37: createIndexExpr
                    {
                    pushFollow(FOLLOW_createIndexExpr_in_eplExpressionRule233);
                    createIndexExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:80:55: createVariableExpr
                    {
                    pushFollow(FOLLOW_createVariableExpr_in_eplExpressionRule237);
                    createVariableExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:80:76: createSchemaExpr
                    {
                    pushFollow(FOLLOW_createSchemaExpr_in_eplExpressionRule241);
                    createSchemaExpr();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:80:95: onExpr
                    {
                    pushFollow(FOLLOW_onExpr_in_eplExpressionRule245);
                    onExpr();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:80:104: updateExpr
                    {
                    pushFollow(FOLLOW_updateExpr_in_eplExpressionRule249);
                    updateExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:80:116: ( forExpr )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==FOR) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // EsperEPL2Ast.g:80:116: forExpr
                    {
                    pushFollow(FOLLOW_forExpr_in_eplExpressionRule252);
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
    // EsperEPL2Ast.g:83:1: onExpr : ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:84:2: ( ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr ) ) )
            // EsperEPL2Ast.g:84:4: ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr271); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onStreamExpr_in_onExpr273);
            onStreamExpr();

            state._fsp--;

            // EsperEPL2Ast.g:85:3: ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr | onMergeExpr )
            int alt11=5;
            switch ( input.LA(1) ) {
            case ON_DELETE_EXPR:
                {
                alt11=1;
                }
                break;
            case ON_UPDATE_EXPR:
                {
                alt11=2;
                }
                break;
            case ON_SELECT_EXPR:
                {
                alt11=3;
                }
                break;
            case ON_SET_EXPR:
                {
                alt11=4;
                }
                break;
            case ON_MERGE_EXPR:
                {
                alt11=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // EsperEPL2Ast.g:85:4: onDeleteExpr
                    {
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr278);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:85:19: onUpdateExpr
                    {
                    pushFollow(FOLLOW_onUpdateExpr_in_onExpr282);
                    onUpdateExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:85:34: onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr286);
                    onSelectExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:85:47: ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    int alt10=2;
                    int LA10_0 = input.LA(1);

                    if ( (LA10_0==ON_SELECT_INSERT_EXPR) ) {
                        alt10=1;
                    }
                    switch (alt10) {
                        case 1 :
                            // EsperEPL2Ast.g:85:48: ( onSelectInsertExpr )+ ( onSelectInsertOutput )?
                            {
                            // EsperEPL2Ast.g:85:48: ( onSelectInsertExpr )+
                            int cnt8=0;
                            loop8:
                            do {
                                int alt8=2;
                                int LA8_0 = input.LA(1);

                                if ( (LA8_0==ON_SELECT_INSERT_EXPR) ) {
                                    alt8=1;
                                }


                                switch (alt8) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:85:48: onSelectInsertExpr
                            	    {
                            	    pushFollow(FOLLOW_onSelectInsertExpr_in_onExpr289);
                            	    onSelectInsertExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt8 >= 1 ) break loop8;
                                        EarlyExitException eee =
                                            new EarlyExitException(8, input);
                                        throw eee;
                                }
                                cnt8++;
                            } while (true);

                            // EsperEPL2Ast.g:85:68: ( onSelectInsertOutput )?
                            int alt9=2;
                            int LA9_0 = input.LA(1);

                            if ( (LA9_0==ON_SELECT_INSERT_OUTPUT) ) {
                                alt9=1;
                            }
                            switch (alt9) {
                                case 1 :
                                    // EsperEPL2Ast.g:85:68: onSelectInsertOutput
                                    {
                                    pushFollow(FOLLOW_onSelectInsertOutput_in_onExpr292);
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
                    // EsperEPL2Ast.g:85:94: onSetExpr
                    {
                    pushFollow(FOLLOW_onSetExpr_in_onExpr299);
                    onSetExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:85:106: onMergeExpr
                    {
                    pushFollow(FOLLOW_onMergeExpr_in_onExpr303);
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
    // EsperEPL2Ast.g:89:1: onStreamExpr : ^(s= ON_STREAM ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ) ;
    public final void onStreamExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:90:2: ( ^(s= ON_STREAM ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ) )
            // EsperEPL2Ast.g:90:4: ^(s= ON_STREAM ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? )
            {
            s=(CommonTree)match(input,ON_STREAM,FOLLOW_ON_STREAM_in_onStreamExpr325); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:90:18: ( eventFilterExpr | patternInclusionExpression )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==EVENT_FILTER_EXPR) ) {
                alt12=1;
            }
            else if ( (LA12_0==PATTERN_INCL_EXPR) ) {
                alt12=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // EsperEPL2Ast.g:90:19: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_onStreamExpr328);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:90:37: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onStreamExpr332);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:90:65: ( IDENT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IDENT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:90:65: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onStreamExpr335); 

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
    // EsperEPL2Ast.g:93:1: onMergeExpr : ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeItem )+ ( whereClause[true] )? ) ;
    public final void onMergeExpr() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:94:2: ( ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeItem )+ ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:94:4: ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeItem )+ ( whereClause[true] )? )
            {
            m=(CommonTree)match(input,ON_MERGE_EXPR,FOLLOW_ON_MERGE_EXPR_in_onMergeExpr353); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onMergeExpr355); 
            // EsperEPL2Ast.g:94:28: ( IDENT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==IDENT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // EsperEPL2Ast.g:94:28: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onMergeExpr357); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:94:35: ( mergeItem )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( ((LA15_0>=MERGE_UPD && LA15_0<=MERGE_INS)) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // EsperEPL2Ast.g:94:35: mergeItem
            	    {
            	    pushFollow(FOLLOW_mergeItem_in_onMergeExpr360);
            	    mergeItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);

            // EsperEPL2Ast.g:94:46: ( whereClause[true] )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE_EXPR) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:94:46: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onMergeExpr363);
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
    // EsperEPL2Ast.g:97:1: mergeItem : ( mergeMatched | mergeUnmatched ) ;
    public final void mergeItem() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:98:2: ( ( mergeMatched | mergeUnmatched ) )
            // EsperEPL2Ast.g:98:4: ( mergeMatched | mergeUnmatched )
            {
            // EsperEPL2Ast.g:98:4: ( mergeMatched | mergeUnmatched )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==MERGE_UPD) ) {
                alt17=1;
            }
            else if ( (LA17_0==MERGE_INS) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:98:5: mergeMatched
                    {
                    pushFollow(FOLLOW_mergeMatched_in_mergeItem379);
                    mergeMatched();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:98:20: mergeUnmatched
                    {
                    pushFollow(FOLLOW_mergeUnmatched_in_mergeItem383);
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
    // EsperEPL2Ast.g:101:1: mergeMatched : ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* ) ;
    public final void mergeMatched() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:102:2: ( ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* ) )
            // EsperEPL2Ast.g:102:4: ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* )
            {
            m=(CommonTree)match(input,MERGE_UPD,FOLLOW_MERGE_UPD_in_mergeMatched398); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:102:18: ( valueExpr )?
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=IN_SET && LA18_0<=REGEXP)||LA18_0==NOT_EXPR||(LA18_0>=SUM && LA18_0<=AVG)||(LA18_0>=COALESCE && LA18_0<=COUNT)||(LA18_0>=CASE && LA18_0<=CASE2)||(LA18_0>=PREVIOUS && LA18_0<=EXISTS)||(LA18_0>=INSTANCEOF && LA18_0<=CURRENT_TIMESTAMP)||(LA18_0>=EVAL_AND_EXPR && LA18_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA18_0==EVENT_PROP_EXPR||LA18_0==CONCAT||(LA18_0>=LIB_FUNC_CHAIN && LA18_0<=DOT_EXPR)||LA18_0==ARRAY_EXPR||(LA18_0>=NOT_IN_SET && LA18_0<=NOT_REGEXP)||(LA18_0>=IN_RANGE && LA18_0<=SUBSELECT_EXPR)||(LA18_0>=EXISTS_SUBSELECT_EXPR && LA18_0<=NOT_IN_SUBSELECT_EXPR)||LA18_0==SUBSTITUTION||(LA18_0>=FIRST_AGGREG && LA18_0<=WINDOW_AGGREG)||(LA18_0>=INT_TYPE && LA18_0<=NULL_TYPE)||(LA18_0>=STAR && LA18_0<=PLUS)||(LA18_0>=BAND && LA18_0<=BXOR)||(LA18_0>=LT && LA18_0<=GE)||(LA18_0>=MINUS && LA18_0<=MOD)) ) {
                    alt18=1;
                }
                switch (alt18) {
                    case 1 :
                        // EsperEPL2Ast.g:102:18: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_mergeMatched400);
                        valueExpr();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:102:29: ( UPDATE )?
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==UPDATE) ) {
                    alt19=1;
                }
                switch (alt19) {
                    case 1 :
                        // EsperEPL2Ast.g:102:29: UPDATE
                        {
                        match(input,UPDATE,FOLLOW_UPDATE_in_mergeMatched403); 

                        }
                        break;

                }

                // EsperEPL2Ast.g:102:37: ( DELETE )?
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==DELETE) ) {
                    alt20=1;
                }
                switch (alt20) {
                    case 1 :
                        // EsperEPL2Ast.g:102:37: DELETE
                        {
                        match(input,DELETE,FOLLOW_DELETE_in_mergeMatched406); 

                        }
                        break;

                }

                // EsperEPL2Ast.g:102:45: ( onSetAssignment )*
                loop21:
                do {
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==ON_SET_EXPR_ITEM) ) {
                        alt21=1;
                    }


                    switch (alt21) {
                	case 1 :
                	    // EsperEPL2Ast.g:102:45: onSetAssignment
                	    {
                	    pushFollow(FOLLOW_onSetAssignment_in_mergeMatched409);
                	    onSetAssignment();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop21;
                    }
                } while (true);

                 leaveNode(m); 

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
    // $ANTLR end "mergeMatched"


    // $ANTLR start "mergeUnmatched"
    // EsperEPL2Ast.g:105:1: mergeUnmatched : ^(um= MERGE_INS selectionList ( exprCol )? ( valueExpr )? ) ;
    public final void mergeUnmatched() throws RecognitionException {
        CommonTree um=null;

        try {
            // EsperEPL2Ast.g:106:2: ( ^(um= MERGE_INS selectionList ( exprCol )? ( valueExpr )? ) )
            // EsperEPL2Ast.g:106:4: ^(um= MERGE_INS selectionList ( exprCol )? ( valueExpr )? )
            {
            um=(CommonTree)match(input,MERGE_INS,FOLLOW_MERGE_INS_in_mergeUnmatched427); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_selectionList_in_mergeUnmatched429);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:106:33: ( exprCol )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==EXPRCOL) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:106:33: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_mergeUnmatched431);
                    exprCol();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:106:42: ( valueExpr )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=IN_SET && LA23_0<=REGEXP)||LA23_0==NOT_EXPR||(LA23_0>=SUM && LA23_0<=AVG)||(LA23_0>=COALESCE && LA23_0<=COUNT)||(LA23_0>=CASE && LA23_0<=CASE2)||(LA23_0>=PREVIOUS && LA23_0<=EXISTS)||(LA23_0>=INSTANCEOF && LA23_0<=CURRENT_TIMESTAMP)||(LA23_0>=EVAL_AND_EXPR && LA23_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA23_0==EVENT_PROP_EXPR||LA23_0==CONCAT||(LA23_0>=LIB_FUNC_CHAIN && LA23_0<=DOT_EXPR)||LA23_0==ARRAY_EXPR||(LA23_0>=NOT_IN_SET && LA23_0<=NOT_REGEXP)||(LA23_0>=IN_RANGE && LA23_0<=SUBSELECT_EXPR)||(LA23_0>=EXISTS_SUBSELECT_EXPR && LA23_0<=NOT_IN_SUBSELECT_EXPR)||LA23_0==SUBSTITUTION||(LA23_0>=FIRST_AGGREG && LA23_0<=WINDOW_AGGREG)||(LA23_0>=INT_TYPE && LA23_0<=NULL_TYPE)||(LA23_0>=STAR && LA23_0<=PLUS)||(LA23_0>=BAND && LA23_0<=BXOR)||(LA23_0>=LT && LA23_0<=GE)||(LA23_0>=MINUS && LA23_0<=MOD)) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:106:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_mergeUnmatched434);
                    valueExpr();

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
    // $ANTLR end "mergeUnmatched"


    // $ANTLR start "updateExpr"
    // EsperEPL2Ast.g:109:1: updateExpr : ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void updateExpr() throws RecognitionException {
        CommonTree u=null;

        try {
            // EsperEPL2Ast.g:110:2: ( ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:110:4: ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? )
            {
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr453); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr455); 
            // EsperEPL2Ast.g:110:32: ( IDENT )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==IDENT) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:110:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr457); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:110:39: ( onSetAssignment )+
            int cnt25=0;
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==ON_SET_EXPR_ITEM) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // EsperEPL2Ast.g:110:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr460);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt25 >= 1 ) break loop25;
                        EarlyExitException eee =
                            new EarlyExitException(25, input);
                        throw eee;
                }
                cnt25++;
            } while (true);

            // EsperEPL2Ast.g:110:56: ( whereClause[false] )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==WHERE_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:110:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr463);
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
    // EsperEPL2Ast.g:113:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:114:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:114:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr480); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr482);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:114:32: ( whereClause[true] )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==WHERE_EXPR) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:114:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr485);
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
    // EsperEPL2Ast.g:117:1: onSelectExpr : ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:118:2: ( ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) )
            // EsperEPL2Ast.g:118:4: ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? )
            {
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr505); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:118:23: ( insertIntoExpr )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==INSERTINTO_EXPR) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:118:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr507);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:39: ( DISTINCT )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==DISTINCT) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:118:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr510); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr513);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:118:63: ( onExprFrom )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ON_EXPR_FROM) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:118:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr515);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:75: ( whereClause[true] )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==WHERE_EXPR) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:118:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr518);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:94: ( groupByClause )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==GROUP_BY_EXPR) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:118:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr522);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:109: ( havingClause )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==HAVING_EXPR) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:118:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr525);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:123: ( orderByClause )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ORDER_BY_EXPR) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:118:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr528);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:138: ( rowLimitClause )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ROW_LIMIT_EXPR) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // EsperEPL2Ast.g:118:138: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr531);
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
    // EsperEPL2Ast.g:121:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:122:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:122:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr551); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr553);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr555);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:122:78: ( whereClause[true] )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==WHERE_EXPR) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:122:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr557);
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
    // EsperEPL2Ast.g:125:1: onSelectInsertOutput : ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) ;
    public final void onSelectInsertOutput() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:126:2: ( ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) )
            // EsperEPL2Ast.g:126:4: ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) )
            {
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput574); 

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
    // EsperEPL2Ast.g:129:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:130:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:130:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr594); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr596);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:130:34: ( onSetAssignment )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==ON_SET_EXPR_ITEM) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // EsperEPL2Ast.g:130:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr599);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

            // EsperEPL2Ast.g:130:53: ( whereClause[false] )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WHERE_EXPR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:130:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr603);
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
    // EsperEPL2Ast.g:133:1: onUpdateExpr : ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void onUpdateExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:134:2: ( ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:134:4: ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? )
            {
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr618); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr620);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:134:32: ( onSetAssignment )+
            int cnt39=0;
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==ON_SET_EXPR_ITEM) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // EsperEPL2Ast.g:134:32: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr622);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt39 >= 1 ) break loop39;
                        EarlyExitException eee =
                            new EarlyExitException(39, input);
                        throw eee;
                }
                cnt39++;
            } while (true);

            // EsperEPL2Ast.g:134:49: ( whereClause[false] )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==WHERE_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:134:49: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr625);
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
    // EsperEPL2Ast.g:137:1: onSetAssignment : ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:138:2: ( ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) )
            // EsperEPL2Ast.g:138:4: ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr )
            {
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment640); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_onSetAssignment642);
            eventPropertyExpr(false);

            state._fsp--;

            pushFollow(FOLLOW_valueExpr_in_onSetAssignment645);
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
    // EsperEPL2Ast.g:141:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:142:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:142:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom659); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom661); 
            // EsperEPL2Ast.g:142:25: ( IDENT )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==IDENT) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:142:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom664); 

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
    // EsperEPL2Ast.g:145:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:146:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:146:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr682); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr684); 
            // EsperEPL2Ast.g:146:33: ( viewListExpr )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==VIEW_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:146:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr687);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:146:49: ( RETAINUNION )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RETAINUNION) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:146:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr691); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:146:62: ( RETAININTERSECTION )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==RETAININTERSECTION) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:146:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr694); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:147:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==CLASS_IDENT||LA46_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt46=1;
            }
            else if ( (LA46_0==CREATE_COL_TYPE_LIST) ) {
                alt46=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:148:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:148:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:148:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:148:6: ( createSelectionList )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // EsperEPL2Ast.g:148:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr708);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr711); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:150:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:150:12: ( createColTypeList )
                    // EsperEPL2Ast.g:150:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr740);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:152:4: ( createWindowExprInsert )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==INSERT) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:152:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr751);
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
    // EsperEPL2Ast.g:156:1: createIndexExpr : ^(i= CREATE_INDEX_EXPR IDENT IDENT indexColList ) ;
    public final void createIndexExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:157:2: ( ^(i= CREATE_INDEX_EXPR IDENT IDENT indexColList ) )
            // EsperEPL2Ast.g:157:4: ^(i= CREATE_INDEX_EXPR IDENT IDENT indexColList )
            {
            i=(CommonTree)match(input,CREATE_INDEX_EXPR,FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr771); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr773); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr775); 
            pushFollow(FOLLOW_indexColList_in_createIndexExpr777);
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
    // EsperEPL2Ast.g:160:1: indexColList : ^( INDEXCOL ( indexCol )+ ) ;
    public final void indexColList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:161:2: ( ^( INDEXCOL ( indexCol )+ ) )
            // EsperEPL2Ast.g:161:4: ^( INDEXCOL ( indexCol )+ )
            {
            match(input,INDEXCOL,FOLLOW_INDEXCOL_in_indexColList792); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:161:15: ( indexCol )+
            int cnt48=0;
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==INDEXCOL) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // EsperEPL2Ast.g:161:15: indexCol
            	    {
            	    pushFollow(FOLLOW_indexCol_in_indexColList794);
            	    indexCol();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt48 >= 1 ) break loop48;
                        EarlyExitException eee =
                            new EarlyExitException(48, input);
                        throw eee;
                }
                cnt48++;
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
    // EsperEPL2Ast.g:164:1: indexCol : ^( INDEXCOL IDENT ( IDENT )? ) ;
    public final void indexCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:165:2: ( ^( INDEXCOL IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:165:4: ^( INDEXCOL IDENT ( IDENT )? )
            {
            match(input,INDEXCOL,FOLLOW_INDEXCOL_in_indexCol809); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_indexCol811); 
            // EsperEPL2Ast.g:165:21: ( IDENT )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==IDENT) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:165:21: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_indexCol813); 

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
    // EsperEPL2Ast.g:168:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:169:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:169:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert827); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:169:13: ( valueExpr )?
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=IN_SET && LA50_0<=REGEXP)||LA50_0==NOT_EXPR||(LA50_0>=SUM && LA50_0<=AVG)||(LA50_0>=COALESCE && LA50_0<=COUNT)||(LA50_0>=CASE && LA50_0<=CASE2)||(LA50_0>=PREVIOUS && LA50_0<=EXISTS)||(LA50_0>=INSTANCEOF && LA50_0<=CURRENT_TIMESTAMP)||(LA50_0>=EVAL_AND_EXPR && LA50_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA50_0==EVENT_PROP_EXPR||LA50_0==CONCAT||(LA50_0>=LIB_FUNC_CHAIN && LA50_0<=DOT_EXPR)||LA50_0==ARRAY_EXPR||(LA50_0>=NOT_IN_SET && LA50_0<=NOT_REGEXP)||(LA50_0>=IN_RANGE && LA50_0<=SUBSELECT_EXPR)||(LA50_0>=EXISTS_SUBSELECT_EXPR && LA50_0<=NOT_IN_SUBSELECT_EXPR)||LA50_0==SUBSTITUTION||(LA50_0>=FIRST_AGGREG && LA50_0<=WINDOW_AGGREG)||(LA50_0>=INT_TYPE && LA50_0<=NULL_TYPE)||(LA50_0>=STAR && LA50_0<=PLUS)||(LA50_0>=BAND && LA50_0<=BXOR)||(LA50_0>=LT && LA50_0<=GE)||(LA50_0>=MINUS && LA50_0<=MOD)) ) {
                    alt50=1;
                }
                switch (alt50) {
                    case 1 :
                        // EsperEPL2Ast.g:169:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert829);
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
    // EsperEPL2Ast.g:172:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:173:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:173:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList846); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList848);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:173:61: ( createSelectionListElement )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==SELECTION_ELEMENT_EXPR||LA51_0==WILDCARD_SELECT) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // EsperEPL2Ast.g:173:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList851);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop51;
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
    // EsperEPL2Ast.g:176:1: createColTypeList : ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:177:2: ( ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:177:4: ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_COL_TYPE_LIST,FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList870); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList872);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:177:52: ( createColTypeListElement )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( (LA52_0==CREATE_COL_TYPE) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // EsperEPL2Ast.g:177:53: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList875);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop52;
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
    // EsperEPL2Ast.g:180:1: createColTypeListElement : ^( CREATE_COL_TYPE CLASS_IDENT CLASS_IDENT ( LBRACK )? ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:181:2: ( ^( CREATE_COL_TYPE CLASS_IDENT CLASS_IDENT ( LBRACK )? ) )
            // EsperEPL2Ast.g:181:4: ^( CREATE_COL_TYPE CLASS_IDENT CLASS_IDENT ( LBRACK )? )
            {
            match(input,CREATE_COL_TYPE,FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement890); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement892); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement894); 
            // EsperEPL2Ast.g:181:46: ( LBRACK )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==LBRACK) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:181:46: LBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_createColTypeListElement896); 

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
    // EsperEPL2Ast.g:184:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:185:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==WILDCARD_SELECT) ) {
                alt56=1;
            }
            else if ( (LA56_0==SELECTION_ELEMENT_EXPR) ) {
                alt56=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 56, 0, input);

                throw nvae;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:185:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement911); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:186:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement921); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:186:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==EVENT_PROP_EXPR) ) {
                        alt55=1;
                    }
                    else if ( ((LA55_0>=INT_TYPE && LA55_0<=NULL_TYPE)) ) {
                        alt55=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 55, 0, input);

                        throw nvae;
                    }
                    switch (alt55) {
                        case 1 :
                            // EsperEPL2Ast.g:187:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:187:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:187:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement941);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:187:41: ( IDENT )?
                            int alt54=2;
                            int LA54_0 = input.LA(1);

                            if ( (LA54_0==IDENT) ) {
                                alt54=1;
                            }
                            switch (alt54) {
                                case 1 :
                                    // EsperEPL2Ast.g:187:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement945); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:188:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:188:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:188:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement967);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement970); 

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
    // EsperEPL2Ast.g:192:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:193:2: ( ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:193:4: ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr1006); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createVariableExpr1008); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr1010); 
            // EsperEPL2Ast.g:193:47: ( valueExpr )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( ((LA57_0>=IN_SET && LA57_0<=REGEXP)||LA57_0==NOT_EXPR||(LA57_0>=SUM && LA57_0<=AVG)||(LA57_0>=COALESCE && LA57_0<=COUNT)||(LA57_0>=CASE && LA57_0<=CASE2)||(LA57_0>=PREVIOUS && LA57_0<=EXISTS)||(LA57_0>=INSTANCEOF && LA57_0<=CURRENT_TIMESTAMP)||(LA57_0>=EVAL_AND_EXPR && LA57_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA57_0==EVENT_PROP_EXPR||LA57_0==CONCAT||(LA57_0>=LIB_FUNC_CHAIN && LA57_0<=DOT_EXPR)||LA57_0==ARRAY_EXPR||(LA57_0>=NOT_IN_SET && LA57_0<=NOT_REGEXP)||(LA57_0>=IN_RANGE && LA57_0<=SUBSELECT_EXPR)||(LA57_0>=EXISTS_SUBSELECT_EXPR && LA57_0<=NOT_IN_SUBSELECT_EXPR)||LA57_0==SUBSTITUTION||(LA57_0>=FIRST_AGGREG && LA57_0<=WINDOW_AGGREG)||(LA57_0>=INT_TYPE && LA57_0<=NULL_TYPE)||(LA57_0>=STAR && LA57_0<=PLUS)||(LA57_0>=BAND && LA57_0<=BXOR)||(LA57_0>=LT && LA57_0<=GE)||(LA57_0>=MINUS && LA57_0<=MOD)) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:193:48: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr1013);
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
    // EsperEPL2Ast.g:196:1: createSchemaExpr : ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? ) ;
    public final void createSchemaExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:197:2: ( ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? ) )
            // EsperEPL2Ast.g:197:4: ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? )
            {
            s=(CommonTree)match(input,CREATE_SCHEMA_EXPR,FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr1033); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1035); 
            // EsperEPL2Ast.g:197:33: ( variantList | ( createColTypeList )? )
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==VARIANT_LIST) ) {
                alt59=1;
            }
            else if ( (LA59_0==UP||LA59_0==CREATE_COL_TYPE_LIST||(LA59_0>=CREATE_SCHEMA_EXPR_QUAL && LA59_0<=CREATE_SCHEMA_EXPR_INH)) ) {
                alt59=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 59, 0, input);

                throw nvae;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:197:34: variantList
                    {
                    pushFollow(FOLLOW_variantList_in_createSchemaExpr1038);
                    variantList();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:197:46: ( createColTypeList )?
                    {
                    // EsperEPL2Ast.g:197:46: ( createColTypeList )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==CREATE_COL_TYPE_LIST) ) {
                        alt58=1;
                    }
                    switch (alt58) {
                        case 1 :
                            // EsperEPL2Ast.g:197:46: createColTypeList
                            {
                            pushFollow(FOLLOW_createColTypeList_in_createSchemaExpr1040);
                            createColTypeList();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:198:5: ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==CREATE_SCHEMA_EXPR_QUAL) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:198:6: ^( CREATE_SCHEMA_EXPR_QUAL IDENT )
                    {
                    match(input,CREATE_SCHEMA_EXPR_QUAL,FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr1051); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1053); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:199:5: ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==CREATE_SCHEMA_EXPR_INH) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // EsperEPL2Ast.g:199:6: ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol )
                    {
                    match(input,CREATE_SCHEMA_EXPR_INH,FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr1064); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1066); 
                    pushFollow(FOLLOW_exprCol_in_createSchemaExpr1068);
                    exprCol();

                    state._fsp--;


                    match(input, Token.UP, null); 

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
    // $ANTLR end "createSchemaExpr"


    // $ANTLR start "variantList"
    // EsperEPL2Ast.g:202:1: variantList : ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) ;
    public final void variantList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:203:2: ( ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) )
            // EsperEPL2Ast.g:203:4: ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ )
            {
            match(input,VARIANT_LIST,FOLLOW_VARIANT_LIST_in_variantList1089); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:203:19: ( STAR | CLASS_IDENT )+
            int cnt62=0;
            loop62:
            do {
                int alt62=2;
                int LA62_0 = input.LA(1);

                if ( (LA62_0==CLASS_IDENT||LA62_0==STAR) ) {
                    alt62=1;
                }


                switch (alt62) {
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
            	    if ( cnt62 >= 1 ) break loop62;
                        EarlyExitException eee =
                            new EarlyExitException(62, input);
                        throw eee;
                }
                cnt62++;
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
    // EsperEPL2Ast.g:206:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:207:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:207:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:207:4: ( insertIntoExpr )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==INSERTINTO_EXPR) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:207:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr1109);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr1115);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr1120);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:210:3: ( matchRecogClause )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==MATCH_RECOGNIZE) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:210:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr1125);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:211:3: ( whereClause[true] )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==WHERE_EXPR) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:211:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr1132);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:212:3: ( groupByClause )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==GROUP_BY_EXPR) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:212:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr1140);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:213:3: ( havingClause )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==HAVING_EXPR) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:213:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr1147);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:214:3: ( outputLimitExpr )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( ((LA68_0>=EVENT_LIMIT_EXPR && LA68_0<=CRONTAB_LIMIT_EXPR)||LA68_0==WHEN_LIMIT_EXPR) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:214:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr1154);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:215:3: ( orderByClause )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==ORDER_BY_EXPR) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // EsperEPL2Ast.g:215:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr1161);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:216:3: ( rowLimitClause )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==ROW_LIMIT_EXPR) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:216:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr1168);
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
    // EsperEPL2Ast.g:219:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? CLASS_IDENT ( exprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:220:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? CLASS_IDENT ( exprCol )? ) )
            // EsperEPL2Ast.g:220:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? CLASS_IDENT ( exprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1185); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:220:24: ( ISTREAM | RSTREAM )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( ((LA71_0>=RSTREAM && LA71_0<=ISTREAM)) ) {
                alt71=1;
            }
            switch (alt71) {
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

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_insertIntoExpr1196); 
            // EsperEPL2Ast.g:220:57: ( exprCol )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( (LA72_0==EXPRCOL) ) {
                alt72=1;
            }
            switch (alt72) {
                case 1 :
                    // EsperEPL2Ast.g:220:58: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_insertIntoExpr1199);
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
    // EsperEPL2Ast.g:223:1: exprCol : ^( EXPRCOL IDENT ( IDENT )* ) ;
    public final void exprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:224:2: ( ^( EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:224:4: ^( EXPRCOL IDENT ( IDENT )* )
            {
            match(input,EXPRCOL,FOLLOW_EXPRCOL_in_exprCol1218); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_exprCol1220); 
            // EsperEPL2Ast.g:224:20: ( IDENT )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==IDENT) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // EsperEPL2Ast.g:224:21: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_exprCol1223); 

            	    }
            	    break;

            	default :
            	    break loop73;
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
    // EsperEPL2Ast.g:227:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:228:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) )
            // EsperEPL2Ast.g:228:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause1241); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:228:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( ((LA74_0>=RSTREAM && LA74_0<=IRSTREAM)) ) {
                alt74=1;
            }
            switch (alt74) {
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

            // EsperEPL2Ast.g:228:55: ( DISTINCT )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==DISTINCT) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // EsperEPL2Ast.g:228:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1256); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1259);
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
    // EsperEPL2Ast.g:231:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:232:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:232:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause1273);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:232:21: ( streamExpression ( outerJoin )* )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==STREAM_EXPR) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // EsperEPL2Ast.g:232:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1276);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:232:39: ( outerJoin )*
            	    loop76:
            	    do {
            	        int alt76=2;
            	        int LA76_0 = input.LA(1);

            	        if ( ((LA76_0>=INNERJOIN_EXPR && LA76_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt76=1;
            	        }


            	        switch (alt76) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:232:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1279);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop76;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop77;
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
    // EsperEPL2Ast.g:235:1: forExpr : ^(f= FOR IDENT ( valueExpr )* ) ;
    public final void forExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:236:2: ( ^(f= FOR IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:236:4: ^(f= FOR IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,FOR,FOLLOW_FOR_in_forExpr1299); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_forExpr1301); 
            // EsperEPL2Ast.g:236:18: ( valueExpr )*
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( ((LA78_0>=IN_SET && LA78_0<=REGEXP)||LA78_0==NOT_EXPR||(LA78_0>=SUM && LA78_0<=AVG)||(LA78_0>=COALESCE && LA78_0<=COUNT)||(LA78_0>=CASE && LA78_0<=CASE2)||(LA78_0>=PREVIOUS && LA78_0<=EXISTS)||(LA78_0>=INSTANCEOF && LA78_0<=CURRENT_TIMESTAMP)||(LA78_0>=EVAL_AND_EXPR && LA78_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA78_0==EVENT_PROP_EXPR||LA78_0==CONCAT||(LA78_0>=LIB_FUNC_CHAIN && LA78_0<=DOT_EXPR)||LA78_0==ARRAY_EXPR||(LA78_0>=NOT_IN_SET && LA78_0<=NOT_REGEXP)||(LA78_0>=IN_RANGE && LA78_0<=SUBSELECT_EXPR)||(LA78_0>=EXISTS_SUBSELECT_EXPR && LA78_0<=NOT_IN_SUBSELECT_EXPR)||LA78_0==SUBSTITUTION||(LA78_0>=FIRST_AGGREG && LA78_0<=WINDOW_AGGREG)||(LA78_0>=INT_TYPE && LA78_0<=NULL_TYPE)||(LA78_0>=STAR && LA78_0<=PLUS)||(LA78_0>=BAND && LA78_0<=BXOR)||(LA78_0>=LT && LA78_0<=GE)||(LA78_0>=MINUS && LA78_0<=MOD)) ) {
                    alt78=1;
                }


                switch (alt78) {
            	case 1 :
            	    // EsperEPL2Ast.g:236:18: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_forExpr1303);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop78;
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
    // EsperEPL2Ast.g:239:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:240:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:240:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1322); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:240:24: ( matchRecogPartitionBy )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==MATCHREC_PARTITION) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:240:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1324);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1331);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:242:4: ( ALL )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==ALL) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:242:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1337); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:243:4: ( matchRecogMatchesAfterSkip )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==MATCHREC_AFTER_SKIP) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:243:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1343);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1349);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:245:4: ( matchRecogMatchesInterval )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==MATCHREC_INTERVAL) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:245:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1355);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1361);
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
    // EsperEPL2Ast.g:249:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:250:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:250:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1379); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:250:27: ( valueExpr )+
            int cnt83=0;
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( ((LA83_0>=IN_SET && LA83_0<=REGEXP)||LA83_0==NOT_EXPR||(LA83_0>=SUM && LA83_0<=AVG)||(LA83_0>=COALESCE && LA83_0<=COUNT)||(LA83_0>=CASE && LA83_0<=CASE2)||(LA83_0>=PREVIOUS && LA83_0<=EXISTS)||(LA83_0>=INSTANCEOF && LA83_0<=CURRENT_TIMESTAMP)||(LA83_0>=EVAL_AND_EXPR && LA83_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA83_0==EVENT_PROP_EXPR||LA83_0==CONCAT||(LA83_0>=LIB_FUNC_CHAIN && LA83_0<=DOT_EXPR)||LA83_0==ARRAY_EXPR||(LA83_0>=NOT_IN_SET && LA83_0<=NOT_REGEXP)||(LA83_0>=IN_RANGE && LA83_0<=SUBSELECT_EXPR)||(LA83_0>=EXISTS_SUBSELECT_EXPR && LA83_0<=NOT_IN_SUBSELECT_EXPR)||LA83_0==SUBSTITUTION||(LA83_0>=FIRST_AGGREG && LA83_0<=WINDOW_AGGREG)||(LA83_0>=INT_TYPE && LA83_0<=NULL_TYPE)||(LA83_0>=STAR && LA83_0<=PLUS)||(LA83_0>=BAND && LA83_0<=BXOR)||(LA83_0>=LT && LA83_0<=GE)||(LA83_0>=MINUS && LA83_0<=MOD)) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // EsperEPL2Ast.g:250:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1381);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt83 >= 1 ) break loop83;
                        EarlyExitException eee =
                            new EarlyExitException(83, input);
                        throw eee;
                }
                cnt83++;
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
    // EsperEPL2Ast.g:253:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:254:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) )
            // EsperEPL2Ast.g:254:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1398); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1400); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1402); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1404); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1412); 

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
    // EsperEPL2Ast.g:257:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:258:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:258:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1427); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1429); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1431);
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
    // EsperEPL2Ast.g:261:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:262:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:262:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1447); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:262:26: ( matchRecogMeasureListElement )*
                loop84:
                do {
                    int alt84=2;
                    int LA84_0 = input.LA(1);

                    if ( (LA84_0==MATCHREC_MEASURE_ITEM) ) {
                        alt84=1;
                    }


                    switch (alt84) {
                	case 1 :
                	    // EsperEPL2Ast.g:262:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1449);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop84;
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
    // EsperEPL2Ast.g:265:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:266:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:266:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1466); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1468);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:266:40: ( IDENT )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==IDENT) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // EsperEPL2Ast.g:266:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1470); 

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
    // EsperEPL2Ast.g:269:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:270:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:270:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1490); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:270:25: ( matchRecogPatternAlteration )+
            int cnt86=0;
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( ((LA86_0>=MATCHREC_PATTERN_CONCAT && LA86_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // EsperEPL2Ast.g:270:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1492);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt86 >= 1 ) break loop86;
                        EarlyExitException eee =
                            new EarlyExitException(86, input);
                        throw eee;
                }
                cnt86++;
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
    // EsperEPL2Ast.g:273:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:274:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==MATCHREC_PATTERN_CONCAT) ) {
                alt88=1;
            }
            else if ( (LA88_0==MATCHREC_PATTERN_ALTER) ) {
                alt88=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:274:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1507);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:275:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1515); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1517);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:275:55: ( matchRecogPatternConcat )+
                    int cnt87=0;
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( (LA87_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:275:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1519);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt87 >= 1 ) break loop87;
                                EarlyExitException eee =
                                    new EarlyExitException(87, input);
                                throw eee;
                        }
                        cnt87++;
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
    // EsperEPL2Ast.g:278:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:279:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:279:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1537); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:279:32: ( matchRecogPatternUnary )+
            int cnt89=0;
            loop89:
            do {
                int alt89=2;
                int LA89_0 = input.LA(1);

                if ( (LA89_0==MATCHREC_PATTERN_ATOM||LA89_0==MATCHREC_PATTERN_NESTED) ) {
                    alt89=1;
                }


                switch (alt89) {
            	case 1 :
            	    // EsperEPL2Ast.g:279:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1539);
            	    matchRecogPatternUnary();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt89 >= 1 ) break loop89;
                        EarlyExitException eee =
                            new EarlyExitException(89, input);
                        throw eee;
                }
                cnt89++;
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
    // EsperEPL2Ast.g:282:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:283:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==MATCHREC_PATTERN_NESTED) ) {
                alt90=1;
            }
            else if ( (LA90_0==MATCHREC_PATTERN_ATOM) ) {
                alt90=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }
            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:283:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1554);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:284:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1559);
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
    // EsperEPL2Ast.g:287:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:288:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:288:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1574); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1576);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:288:60: ( PLUS | STAR | QUESTION )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==STAR||(LA91_0>=PLUS && LA91_0<=QUESTION)) ) {
                alt91=1;
            }
            switch (alt91) {
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
    // EsperEPL2Ast.g:291:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:292:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:292:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1607); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1609); 
            // EsperEPL2Ast.g:292:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==STAR||(LA93_0>=PLUS && LA93_0<=QUESTION)) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // EsperEPL2Ast.g:292:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:292:63: ( QUESTION )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==QUESTION) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // EsperEPL2Ast.g:292:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1625); 

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
    // EsperEPL2Ast.g:295:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:296:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:296:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1647); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:296:24: ( matchRecogDefineItem )+
            int cnt94=0;
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==MATCHREC_DEFINE_ITEM) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // EsperEPL2Ast.g:296:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1649);
            	    matchRecogDefineItem();

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
    // EsperEPL2Ast.g:299:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:300:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:300:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1666); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1668); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1670);
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
    // EsperEPL2Ast.g:304:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:305:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:305:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1687);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:305:25: ( selectionListElement )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=SELECTION_ELEMENT_EXPR && LA95_0<=SELECTION_STREAM)||LA95_0==WILDCARD_SELECT) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // EsperEPL2Ast.g:305:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1690);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop95;
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
    // EsperEPL2Ast.g:308:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:309:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt98=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt98=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt98=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt98=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 98, 0, input);

                throw nvae;
            }

            switch (alt98) {
                case 1 :
                    // EsperEPL2Ast.g:309:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1706); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:310:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1716); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1718);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:310:41: ( IDENT )?
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==IDENT) ) {
                        alt96=1;
                    }
                    switch (alt96) {
                        case 1 :
                            // EsperEPL2Ast.g:310:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1721); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:311:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1735); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1737); 
                    // EsperEPL2Ast.g:311:31: ( IDENT )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==IDENT) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // EsperEPL2Ast.g:311:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1740); 

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
    // EsperEPL2Ast.g:314:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:315:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:315:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1759);
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
    // EsperEPL2Ast.g:318:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:319:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt103=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt103=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt103=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt103=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt103=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:319:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1773); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1775);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1778);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:319:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop99:
                    do {
                        int alt99=2;
                        int LA99_0 = input.LA(1);

                        if ( (LA99_0==EVENT_PROP_EXPR) ) {
                            alt99=1;
                        }


                        switch (alt99) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:319:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1782);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1785);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop99;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:320:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1800); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1802);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1805);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:320:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==EVENT_PROP_EXPR) ) {
                            alt100=1;
                        }


                        switch (alt100) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:320:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1809);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1812);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop100;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:321:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1827); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1829);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1832);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:321:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop101:
                    do {
                        int alt101=2;
                        int LA101_0 = input.LA(1);

                        if ( (LA101_0==EVENT_PROP_EXPR) ) {
                            alt101=1;
                        }


                        switch (alt101) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:321:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1836);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1839);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop101;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:322:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1854); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1856);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1859);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:322:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop102:
                    do {
                        int alt102=2;
                        int LA102_0 = input.LA(1);

                        if ( (LA102_0==EVENT_PROP_EXPR) ) {
                            alt102=1;
                        }


                        switch (alt102) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:322:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1863);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1866);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop102;
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
    // EsperEPL2Ast.g:325:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:326:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:326:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1887); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:326:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt104=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt104=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt104=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt104=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt104=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // EsperEPL2Ast.g:326:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1890);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:326:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1894);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:326:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1898);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:326:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1902);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:326:115: ( viewListExpr )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==VIEW_EXPR) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // EsperEPL2Ast.g:326:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1906);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:326:131: ( IDENT )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==IDENT) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // EsperEPL2Ast.g:326:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1911); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:326:140: ( UNIDIRECTIONAL )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==UNIDIRECTIONAL) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:326:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1916); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:326:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( ((LA108_0>=RETAINUNION && LA108_0<=RETAININTERSECTION)) ) {
                alt108=1;
            }
            switch (alt108) {
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
    // EsperEPL2Ast.g:329:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:330:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:330:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1944); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:330:27: ( IDENT )?
            int alt109=2;
            int LA109_0 = input.LA(1);

            if ( (LA109_0==IDENT) ) {
                alt109=1;
            }
            switch (alt109) {
                case 1 :
                    // EsperEPL2Ast.g:330:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1946); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1949); 
            // EsperEPL2Ast.g:330:46: ( propertyExpression )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:330:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1951);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:330:66: ( valueExpr )*
            loop111:
            do {
                int alt111=2;
                int LA111_0 = input.LA(1);

                if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA111_0==EVENT_PROP_EXPR||LA111_0==CONCAT||(LA111_0>=LIB_FUNC_CHAIN && LA111_0<=DOT_EXPR)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=SUBSELECT_EXPR)||(LA111_0>=EXISTS_SUBSELECT_EXPR && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=FIRST_AGGREG && LA111_0<=WINDOW_AGGREG)||(LA111_0>=INT_TYPE && LA111_0<=NULL_TYPE)||(LA111_0>=STAR && LA111_0<=PLUS)||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=MINUS && LA111_0<=MOD)) ) {
                    alt111=1;
                }


                switch (alt111) {
            	case 1 :
            	    // EsperEPL2Ast.g:330:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1955);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop111;
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
    // EsperEPL2Ast.g:333:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:334:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:334:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1975); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:334:34: ( propertyExpressionAtom )*
                loop112:
                do {
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt112=1;
                    }


                    switch (alt112) {
                	case 1 :
                	    // EsperEPL2Ast.g:334:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1977);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop112;
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
    // EsperEPL2Ast.g:337:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:338:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:338:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1996); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:338:41: ( propertySelectionListElement )*
            loop113:
            do {
                int alt113=2;
                int LA113_0 = input.LA(1);

                if ( ((LA113_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA113_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt113=1;
                }


                switch (alt113) {
            	case 1 :
            	    // EsperEPL2Ast.g:338:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1998);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop113;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom2001);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:338:96: ( IDENT )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==IDENT) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:338:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom2004); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom2008); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:338:116: ( valueExpr )?
                int alt115=2;
                int LA115_0 = input.LA(1);

                if ( ((LA115_0>=IN_SET && LA115_0<=REGEXP)||LA115_0==NOT_EXPR||(LA115_0>=SUM && LA115_0<=AVG)||(LA115_0>=COALESCE && LA115_0<=COUNT)||(LA115_0>=CASE && LA115_0<=CASE2)||(LA115_0>=PREVIOUS && LA115_0<=EXISTS)||(LA115_0>=INSTANCEOF && LA115_0<=CURRENT_TIMESTAMP)||(LA115_0>=EVAL_AND_EXPR && LA115_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA115_0==EVENT_PROP_EXPR||LA115_0==CONCAT||(LA115_0>=LIB_FUNC_CHAIN && LA115_0<=DOT_EXPR)||LA115_0==ARRAY_EXPR||(LA115_0>=NOT_IN_SET && LA115_0<=NOT_REGEXP)||(LA115_0>=IN_RANGE && LA115_0<=SUBSELECT_EXPR)||(LA115_0>=EXISTS_SUBSELECT_EXPR && LA115_0<=NOT_IN_SUBSELECT_EXPR)||LA115_0==SUBSTITUTION||(LA115_0>=FIRST_AGGREG && LA115_0<=WINDOW_AGGREG)||(LA115_0>=INT_TYPE && LA115_0<=NULL_TYPE)||(LA115_0>=STAR && LA115_0<=PLUS)||(LA115_0>=BAND && LA115_0<=BXOR)||(LA115_0>=LT && LA115_0<=GE)||(LA115_0>=MINUS && LA115_0<=MOD)) ) {
                    alt115=1;
                }
                switch (alt115) {
                    case 1 :
                        // EsperEPL2Ast.g:338:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom2010);
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
    // EsperEPL2Ast.g:341:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:342:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt118=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt118=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt118=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt118=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }

            switch (alt118) {
                case 1 :
                    // EsperEPL2Ast.g:342:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement2030); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:343:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement2040); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement2042);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:343:50: ( IDENT )?
                    int alt116=2;
                    int LA116_0 = input.LA(1);

                    if ( (LA116_0==IDENT) ) {
                        alt116=1;
                    }
                    switch (alt116) {
                        case 1 :
                            // EsperEPL2Ast.g:343:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2045); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:344:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2059); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2061); 
                    // EsperEPL2Ast.g:344:40: ( IDENT )?
                    int alt117=2;
                    int LA117_0 = input.LA(1);

                    if ( (LA117_0==IDENT) ) {
                        alt117=1;
                    }
                    switch (alt117) {
                        case 1 :
                            // EsperEPL2Ast.g:344:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2064); 

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
    // EsperEPL2Ast.g:347:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:348:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:348:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2085); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression2087);
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
    // EsperEPL2Ast.g:351:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:352:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:352:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2104); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression2106); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:352:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( ((LA119_0>=STRING_LITERAL && LA119_0<=QUOTED_STRING_LITERAL)) ) {
                alt119=1;
            }
            switch (alt119) {
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
    // EsperEPL2Ast.g:355:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:356:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:356:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2137); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression2139); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression2141); 
            // EsperEPL2Ast.g:356:41: ( valueExpr )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( ((LA120_0>=IN_SET && LA120_0<=REGEXP)||LA120_0==NOT_EXPR||(LA120_0>=SUM && LA120_0<=AVG)||(LA120_0>=COALESCE && LA120_0<=COUNT)||(LA120_0>=CASE && LA120_0<=CASE2)||(LA120_0>=PREVIOUS && LA120_0<=EXISTS)||(LA120_0>=INSTANCEOF && LA120_0<=CURRENT_TIMESTAMP)||(LA120_0>=EVAL_AND_EXPR && LA120_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA120_0==EVENT_PROP_EXPR||LA120_0==CONCAT||(LA120_0>=LIB_FUNC_CHAIN && LA120_0<=DOT_EXPR)||LA120_0==ARRAY_EXPR||(LA120_0>=NOT_IN_SET && LA120_0<=NOT_REGEXP)||(LA120_0>=IN_RANGE && LA120_0<=SUBSELECT_EXPR)||(LA120_0>=EXISTS_SUBSELECT_EXPR && LA120_0<=NOT_IN_SUBSELECT_EXPR)||LA120_0==SUBSTITUTION||(LA120_0>=FIRST_AGGREG && LA120_0<=WINDOW_AGGREG)||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)||(LA120_0>=STAR && LA120_0<=PLUS)||(LA120_0>=BAND && LA120_0<=BXOR)||(LA120_0>=LT && LA120_0<=GE)||(LA120_0>=MINUS && LA120_0<=MOD)) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // EsperEPL2Ast.g:356:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression2144);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop120;
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
    // EsperEPL2Ast.g:359:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:360:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:360:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr2158);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:360:13: ( viewExpr )*
            loop121:
            do {
                int alt121=2;
                int LA121_0 = input.LA(1);

                if ( (LA121_0==VIEW_EXPR) ) {
                    alt121=1;
                }


                switch (alt121) {
            	case 1 :
            	    // EsperEPL2Ast.g:360:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr2161);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop121;
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
    // EsperEPL2Ast.g:363:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:364:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:364:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr2178); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2180); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2182); 
            // EsperEPL2Ast.g:364:30: ( valueExprWithTime )*
            loop122:
            do {
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( ((LA122_0>=IN_SET && LA122_0<=REGEXP)||LA122_0==NOT_EXPR||(LA122_0>=SUM && LA122_0<=AVG)||(LA122_0>=COALESCE && LA122_0<=COUNT)||(LA122_0>=CASE && LA122_0<=CASE2)||LA122_0==LAST||(LA122_0>=PREVIOUS && LA122_0<=EXISTS)||(LA122_0>=LW && LA122_0<=CURRENT_TIMESTAMP)||(LA122_0>=NUMERIC_PARAM_RANGE && LA122_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA122_0>=EVAL_AND_EXPR && LA122_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA122_0==EVENT_PROP_EXPR||LA122_0==CONCAT||(LA122_0>=LIB_FUNC_CHAIN && LA122_0<=DOT_EXPR)||(LA122_0>=TIME_PERIOD && LA122_0<=ARRAY_EXPR)||(LA122_0>=NOT_IN_SET && LA122_0<=NOT_REGEXP)||(LA122_0>=IN_RANGE && LA122_0<=SUBSELECT_EXPR)||(LA122_0>=EXISTS_SUBSELECT_EXPR && LA122_0<=NOT_IN_SUBSELECT_EXPR)||(LA122_0>=LAST_OPERATOR && LA122_0<=SUBSTITUTION)||LA122_0==NUMBERSETSTAR||(LA122_0>=FIRST_AGGREG && LA122_0<=WINDOW_AGGREG)||(LA122_0>=INT_TYPE && LA122_0<=NULL_TYPE)||(LA122_0>=STAR && LA122_0<=PLUS)||(LA122_0>=BAND && LA122_0<=BXOR)||(LA122_0>=LT && LA122_0<=GE)||(LA122_0>=MINUS && LA122_0<=MOD)) ) {
                    alt122=1;
                }


                switch (alt122) {
            	case 1 :
            	    // EsperEPL2Ast.g:364:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr2185);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop122;
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
    // EsperEPL2Ast.g:367:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:368:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:368:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause2207); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause2209);
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
    // EsperEPL2Ast.g:371:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:372:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:372:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause2227); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause2229);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:372:32: ( valueExpr )*
            loop123:
            do {
                int alt123=2;
                int LA123_0 = input.LA(1);

                if ( ((LA123_0>=IN_SET && LA123_0<=REGEXP)||LA123_0==NOT_EXPR||(LA123_0>=SUM && LA123_0<=AVG)||(LA123_0>=COALESCE && LA123_0<=COUNT)||(LA123_0>=CASE && LA123_0<=CASE2)||(LA123_0>=PREVIOUS && LA123_0<=EXISTS)||(LA123_0>=INSTANCEOF && LA123_0<=CURRENT_TIMESTAMP)||(LA123_0>=EVAL_AND_EXPR && LA123_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA123_0==EVENT_PROP_EXPR||LA123_0==CONCAT||(LA123_0>=LIB_FUNC_CHAIN && LA123_0<=DOT_EXPR)||LA123_0==ARRAY_EXPR||(LA123_0>=NOT_IN_SET && LA123_0<=NOT_REGEXP)||(LA123_0>=IN_RANGE && LA123_0<=SUBSELECT_EXPR)||(LA123_0>=EXISTS_SUBSELECT_EXPR && LA123_0<=NOT_IN_SUBSELECT_EXPR)||LA123_0==SUBSTITUTION||(LA123_0>=FIRST_AGGREG && LA123_0<=WINDOW_AGGREG)||(LA123_0>=INT_TYPE && LA123_0<=NULL_TYPE)||(LA123_0>=STAR && LA123_0<=PLUS)||(LA123_0>=BAND && LA123_0<=BXOR)||(LA123_0>=LT && LA123_0<=GE)||(LA123_0>=MINUS && LA123_0<=MOD)) ) {
                    alt123=1;
                }


                switch (alt123) {
            	case 1 :
            	    // EsperEPL2Ast.g:372:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause2232);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop123;
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
    // EsperEPL2Ast.g:375:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:376:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:376:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause2250); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause2252);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:376:35: ( orderByElement )*
            loop124:
            do {
                int alt124=2;
                int LA124_0 = input.LA(1);

                if ( (LA124_0==ORDER_ELEMENT_EXPR) ) {
                    alt124=1;
                }


                switch (alt124) {
            	case 1 :
            	    // EsperEPL2Ast.g:376:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause2255);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop124;
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
    // EsperEPL2Ast.g:379:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:380:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:380:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2275); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2277);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:380:38: ( ASC | DESC )?
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( ((LA125_0>=ASC && LA125_0<=DESC)) ) {
                alt125=1;
            }
            switch (alt125) {
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
    // EsperEPL2Ast.g:383:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:384:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:384:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2302); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2304);
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
    // EsperEPL2Ast.g:387:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:388:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt136=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt136=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt136=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt136=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt136=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt136=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 136, 0, input);

                throw nvae;
            }

            switch (alt136) {
                case 1 :
                    // EsperEPL2Ast.g:388:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2322); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:388:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt126=2;
                    int LA126_0 = input.LA(1);

                    if ( (LA126_0==ALL||(LA126_0>=FIRST && LA126_0<=LAST)||LA126_0==SNAPSHOT) ) {
                        alt126=1;
                    }
                    switch (alt126) {
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

                    // EsperEPL2Ast.g:388:52: ( number | IDENT )
                    int alt127=2;
                    int LA127_0 = input.LA(1);

                    if ( ((LA127_0>=INT_TYPE && LA127_0<=DOUBLE_TYPE)) ) {
                        alt127=1;
                    }
                    else if ( (LA127_0==IDENT) ) {
                        alt127=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 127, 0, input);

                        throw nvae;
                    }
                    switch (alt127) {
                        case 1 :
                            // EsperEPL2Ast.g:388:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2336);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:388:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2338); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:388:67: ( outputLimitAfter )?
                    int alt128=2;
                    int LA128_0 = input.LA(1);

                    if ( (LA128_0==AFTER) ) {
                        alt128=1;
                    }
                    switch (alt128) {
                        case 1 :
                            // EsperEPL2Ast.g:388:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2341);
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
                    // EsperEPL2Ast.g:389:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2358); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:389:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt129=2;
                    int LA129_0 = input.LA(1);

                    if ( (LA129_0==ALL||(LA129_0>=FIRST && LA129_0<=LAST)||LA129_0==SNAPSHOT) ) {
                        alt129=1;
                    }
                    switch (alt129) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2371);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:389:72: ( outputLimitAfter )?
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( (LA130_0==AFTER) ) {
                        alt130=1;
                    }
                    switch (alt130) {
                        case 1 :
                            // EsperEPL2Ast.g:389:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2373);
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
                    // EsperEPL2Ast.g:390:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2389); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:390:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==ALL||(LA131_0>=FIRST && LA131_0<=LAST)||LA131_0==SNAPSHOT) ) {
                        alt131=1;
                    }
                    switch (alt131) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2402);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:390:85: ( outputLimitAfter )?
                    int alt132=2;
                    int LA132_0 = input.LA(1);

                    if ( (LA132_0==AFTER) ) {
                        alt132=1;
                    }
                    switch (alt132) {
                        case 1 :
                            // EsperEPL2Ast.g:390:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2404);
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
                    // EsperEPL2Ast.g:391:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2420); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:391:30: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2433);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:391:67: ( onSetExpr )?
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( (LA134_0==ON_SET_EXPR) ) {
                        alt134=1;
                    }
                    switch (alt134) {
                        case 1 :
                            // EsperEPL2Ast.g:391:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2435);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:391:78: ( outputLimitAfter )?
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( (LA135_0==AFTER) ) {
                        alt135=1;
                    }
                    switch (alt135) {
                        case 1 :
                            // EsperEPL2Ast.g:391:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2438);
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
                    // EsperEPL2Ast.g:392:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2451); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2453);
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
    // EsperEPL2Ast.g:395:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:396:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:396:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2468); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:396:12: ( timePeriod )?
                int alt137=2;
                int LA137_0 = input.LA(1);

                if ( (LA137_0==TIME_PERIOD) ) {
                    alt137=1;
                }
                switch (alt137) {
                    case 1 :
                        // EsperEPL2Ast.g:396:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2470);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:396:24: ( number )?
                int alt138=2;
                int LA138_0 = input.LA(1);

                if ( ((LA138_0>=INT_TYPE && LA138_0<=DOUBLE_TYPE)) ) {
                    alt138=1;
                }
                switch (alt138) {
                    case 1 :
                        // EsperEPL2Ast.g:396:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2473);
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
    // EsperEPL2Ast.g:399:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:400:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:400:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2489); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:400:23: ( number | IDENT )
            int alt139=2;
            int LA139_0 = input.LA(1);

            if ( ((LA139_0>=INT_TYPE && LA139_0<=DOUBLE_TYPE)) ) {
                alt139=1;
            }
            else if ( (LA139_0==IDENT) ) {
                alt139=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 139, 0, input);

                throw nvae;
            }
            switch (alt139) {
                case 1 :
                    // EsperEPL2Ast.g:400:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2492);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:400:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2494); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:400:38: ( number | IDENT )?
            int alt140=3;
            int LA140_0 = input.LA(1);

            if ( ((LA140_0>=INT_TYPE && LA140_0<=DOUBLE_TYPE)) ) {
                alt140=1;
            }
            else if ( (LA140_0==IDENT) ) {
                alt140=2;
            }
            switch (alt140) {
                case 1 :
                    // EsperEPL2Ast.g:400:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2498);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:400:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2500); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:400:54: ( COMMA )?
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==COMMA) ) {
                alt141=1;
            }
            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:400:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2504); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:400:61: ( OFFSET )?
            int alt142=2;
            int LA142_0 = input.LA(1);

            if ( (LA142_0==OFFSET) ) {
                alt142=1;
            }
            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:400:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2507); 

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
    // EsperEPL2Ast.g:403:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:404:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:404:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2525); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2527);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2529);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2531);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2533);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2535);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:404:121: ( valueExprWithTime )?
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( ((LA143_0>=IN_SET && LA143_0<=REGEXP)||LA143_0==NOT_EXPR||(LA143_0>=SUM && LA143_0<=AVG)||(LA143_0>=COALESCE && LA143_0<=COUNT)||(LA143_0>=CASE && LA143_0<=CASE2)||LA143_0==LAST||(LA143_0>=PREVIOUS && LA143_0<=EXISTS)||(LA143_0>=LW && LA143_0<=CURRENT_TIMESTAMP)||(LA143_0>=NUMERIC_PARAM_RANGE && LA143_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA143_0>=EVAL_AND_EXPR && LA143_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA143_0==EVENT_PROP_EXPR||LA143_0==CONCAT||(LA143_0>=LIB_FUNC_CHAIN && LA143_0<=DOT_EXPR)||(LA143_0>=TIME_PERIOD && LA143_0<=ARRAY_EXPR)||(LA143_0>=NOT_IN_SET && LA143_0<=NOT_REGEXP)||(LA143_0>=IN_RANGE && LA143_0<=SUBSELECT_EXPR)||(LA143_0>=EXISTS_SUBSELECT_EXPR && LA143_0<=NOT_IN_SUBSELECT_EXPR)||(LA143_0>=LAST_OPERATOR && LA143_0<=SUBSTITUTION)||LA143_0==NUMBERSETSTAR||(LA143_0>=FIRST_AGGREG && LA143_0<=WINDOW_AGGREG)||(LA143_0>=INT_TYPE && LA143_0<=NULL_TYPE)||(LA143_0>=STAR && LA143_0<=PLUS)||(LA143_0>=BAND && LA143_0<=BXOR)||(LA143_0>=LT && LA143_0<=GE)||(LA143_0>=MINUS && LA143_0<=MOD)) ) {
                alt143=1;
            }
            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:404:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2537);
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
    // EsperEPL2Ast.g:407:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:408:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt144=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt144=1;
                }
                break;
            case GT:
                {
                alt144=2;
                }
                break;
            case LE:
                {
                alt144=3;
                }
                break;
            case GE:
                {
                alt144=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }

            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:408:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2554); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2556);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:409:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2569); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2571);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:410:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2584); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2586);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:411:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2598); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2600);
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
    // EsperEPL2Ast.g:414:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:415:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:415:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:415:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:416:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2622);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:417:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt147=2;
            int LA147_0 = input.LA(1);

            if ( ((LA147_0>=IN_SET && LA147_0<=REGEXP)||LA147_0==NOT_EXPR||(LA147_0>=SUM && LA147_0<=AVG)||(LA147_0>=COALESCE && LA147_0<=COUNT)||(LA147_0>=CASE && LA147_0<=CASE2)||(LA147_0>=PREVIOUS && LA147_0<=EXISTS)||(LA147_0>=INSTANCEOF && LA147_0<=CURRENT_TIMESTAMP)||(LA147_0>=EVAL_AND_EXPR && LA147_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA147_0==EVENT_PROP_EXPR||LA147_0==CONCAT||(LA147_0>=LIB_FUNC_CHAIN && LA147_0<=DOT_EXPR)||LA147_0==ARRAY_EXPR||(LA147_0>=NOT_IN_SET && LA147_0<=NOT_REGEXP)||(LA147_0>=IN_RANGE && LA147_0<=SUBSELECT_EXPR)||(LA147_0>=EXISTS_SUBSELECT_EXPR && LA147_0<=NOT_IN_SUBSELECT_EXPR)||LA147_0==SUBSTITUTION||(LA147_0>=FIRST_AGGREG && LA147_0<=WINDOW_AGGREG)||(LA147_0>=INT_TYPE && LA147_0<=NULL_TYPE)||(LA147_0>=STAR && LA147_0<=PLUS)||(LA147_0>=BAND && LA147_0<=BXOR)||(LA147_0>=LT && LA147_0<=GE)||(LA147_0>=MINUS && LA147_0<=MOD)) ) {
                alt147=1;
            }
            else if ( ((LA147_0>=ALL && LA147_0<=SOME)) ) {
                alt147=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 147, 0, input);

                throw nvae;
            }
            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:417:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2632);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:419:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:419:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt146=2;
                    int LA146_0 = input.LA(1);

                    if ( (LA146_0==UP||(LA146_0>=IN_SET && LA146_0<=REGEXP)||LA146_0==NOT_EXPR||(LA146_0>=SUM && LA146_0<=AVG)||(LA146_0>=COALESCE && LA146_0<=COUNT)||(LA146_0>=CASE && LA146_0<=CASE2)||(LA146_0>=PREVIOUS && LA146_0<=EXISTS)||(LA146_0>=INSTANCEOF && LA146_0<=CURRENT_TIMESTAMP)||(LA146_0>=EVAL_AND_EXPR && LA146_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA146_0==EVENT_PROP_EXPR||LA146_0==CONCAT||(LA146_0>=LIB_FUNC_CHAIN && LA146_0<=DOT_EXPR)||LA146_0==ARRAY_EXPR||(LA146_0>=NOT_IN_SET && LA146_0<=NOT_REGEXP)||(LA146_0>=IN_RANGE && LA146_0<=SUBSELECT_EXPR)||(LA146_0>=EXISTS_SUBSELECT_EXPR && LA146_0<=NOT_IN_SUBSELECT_EXPR)||LA146_0==SUBSTITUTION||(LA146_0>=FIRST_AGGREG && LA146_0<=WINDOW_AGGREG)||(LA146_0>=INT_TYPE && LA146_0<=NULL_TYPE)||(LA146_0>=STAR && LA146_0<=PLUS)||(LA146_0>=BAND && LA146_0<=BXOR)||(LA146_0>=LT && LA146_0<=GE)||(LA146_0>=MINUS && LA146_0<=MOD)) ) {
                        alt146=1;
                    }
                    else if ( (LA146_0==SUBSELECT_GROUP_EXPR) ) {
                        alt146=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 146, 0, input);

                        throw nvae;
                    }
                    switch (alt146) {
                        case 1 :
                            // EsperEPL2Ast.g:419:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:419:22: ( valueExpr )*
                            loop145:
                            do {
                                int alt145=2;
                                int LA145_0 = input.LA(1);

                                if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||LA145_0==CONCAT||(LA145_0>=LIB_FUNC_CHAIN && LA145_0<=DOT_EXPR)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=SUBSELECT_EXPR)||(LA145_0>=EXISTS_SUBSELECT_EXPR && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=FIRST_AGGREG && LA145_0<=WINDOW_AGGREG)||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||(LA145_0>=STAR && LA145_0<=PLUS)||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=MINUS && LA145_0<=MOD)) ) {
                                    alt145=1;
                                }


                                switch (alt145) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:419:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2656);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop145;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:419:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2661);
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
    // EsperEPL2Ast.g:424:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:425:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt154=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt154=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt154=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt154=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt154=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt154=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt154=6;
                }
                break;
            case NOT_EXPR:
                {
                alt154=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt154=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 154, 0, input);

                throw nvae;
            }

            switch (alt154) {
                case 1 :
                    // EsperEPL2Ast.g:425:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2687); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2689);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2691);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:425:42: ( valueExpr )*
                    loop148:
                    do {
                        int alt148=2;
                        int LA148_0 = input.LA(1);

                        if ( ((LA148_0>=IN_SET && LA148_0<=REGEXP)||LA148_0==NOT_EXPR||(LA148_0>=SUM && LA148_0<=AVG)||(LA148_0>=COALESCE && LA148_0<=COUNT)||(LA148_0>=CASE && LA148_0<=CASE2)||(LA148_0>=PREVIOUS && LA148_0<=EXISTS)||(LA148_0>=INSTANCEOF && LA148_0<=CURRENT_TIMESTAMP)||(LA148_0>=EVAL_AND_EXPR && LA148_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA148_0==EVENT_PROP_EXPR||LA148_0==CONCAT||(LA148_0>=LIB_FUNC_CHAIN && LA148_0<=DOT_EXPR)||LA148_0==ARRAY_EXPR||(LA148_0>=NOT_IN_SET && LA148_0<=NOT_REGEXP)||(LA148_0>=IN_RANGE && LA148_0<=SUBSELECT_EXPR)||(LA148_0>=EXISTS_SUBSELECT_EXPR && LA148_0<=NOT_IN_SUBSELECT_EXPR)||LA148_0==SUBSTITUTION||(LA148_0>=FIRST_AGGREG && LA148_0<=WINDOW_AGGREG)||(LA148_0>=INT_TYPE && LA148_0<=NULL_TYPE)||(LA148_0>=STAR && LA148_0<=PLUS)||(LA148_0>=BAND && LA148_0<=BXOR)||(LA148_0>=LT && LA148_0<=GE)||(LA148_0>=MINUS && LA148_0<=MOD)) ) {
                            alt148=1;
                        }


                        switch (alt148) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:425:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2694);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop148;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:426:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2708); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2710);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2712);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:426:43: ( valueExpr )*
                    loop149:
                    do {
                        int alt149=2;
                        int LA149_0 = input.LA(1);

                        if ( ((LA149_0>=IN_SET && LA149_0<=REGEXP)||LA149_0==NOT_EXPR||(LA149_0>=SUM && LA149_0<=AVG)||(LA149_0>=COALESCE && LA149_0<=COUNT)||(LA149_0>=CASE && LA149_0<=CASE2)||(LA149_0>=PREVIOUS && LA149_0<=EXISTS)||(LA149_0>=INSTANCEOF && LA149_0<=CURRENT_TIMESTAMP)||(LA149_0>=EVAL_AND_EXPR && LA149_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA149_0==EVENT_PROP_EXPR||LA149_0==CONCAT||(LA149_0>=LIB_FUNC_CHAIN && LA149_0<=DOT_EXPR)||LA149_0==ARRAY_EXPR||(LA149_0>=NOT_IN_SET && LA149_0<=NOT_REGEXP)||(LA149_0>=IN_RANGE && LA149_0<=SUBSELECT_EXPR)||(LA149_0>=EXISTS_SUBSELECT_EXPR && LA149_0<=NOT_IN_SUBSELECT_EXPR)||LA149_0==SUBSTITUTION||(LA149_0>=FIRST_AGGREG && LA149_0<=WINDOW_AGGREG)||(LA149_0>=INT_TYPE && LA149_0<=NULL_TYPE)||(LA149_0>=STAR && LA149_0<=PLUS)||(LA149_0>=BAND && LA149_0<=BXOR)||(LA149_0>=LT && LA149_0<=GE)||(LA149_0>=MINUS && LA149_0<=MOD)) ) {
                            alt149=1;
                        }


                        switch (alt149) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:426:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2715);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop149;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:427:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2729); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2731);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2733);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:428:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2745); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2747);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2749);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:429:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2761); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2763);
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

                    // EsperEPL2Ast.g:429:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==UP||(LA151_0>=IN_SET && LA151_0<=REGEXP)||LA151_0==NOT_EXPR||(LA151_0>=SUM && LA151_0<=AVG)||(LA151_0>=COALESCE && LA151_0<=COUNT)||(LA151_0>=CASE && LA151_0<=CASE2)||(LA151_0>=PREVIOUS && LA151_0<=EXISTS)||(LA151_0>=INSTANCEOF && LA151_0<=CURRENT_TIMESTAMP)||(LA151_0>=EVAL_AND_EXPR && LA151_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA151_0==EVENT_PROP_EXPR||LA151_0==CONCAT||(LA151_0>=LIB_FUNC_CHAIN && LA151_0<=DOT_EXPR)||LA151_0==ARRAY_EXPR||(LA151_0>=NOT_IN_SET && LA151_0<=NOT_REGEXP)||(LA151_0>=IN_RANGE && LA151_0<=SUBSELECT_EXPR)||(LA151_0>=EXISTS_SUBSELECT_EXPR && LA151_0<=NOT_IN_SUBSELECT_EXPR)||LA151_0==SUBSTITUTION||(LA151_0>=FIRST_AGGREG && LA151_0<=WINDOW_AGGREG)||(LA151_0>=INT_TYPE && LA151_0<=NULL_TYPE)||(LA151_0>=STAR && LA151_0<=PLUS)||(LA151_0>=BAND && LA151_0<=BXOR)||(LA151_0>=LT && LA151_0<=GE)||(LA151_0>=MINUS && LA151_0<=MOD)) ) {
                        alt151=1;
                    }
                    else if ( (LA151_0==SUBSELECT_GROUP_EXPR) ) {
                        alt151=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 151, 0, input);

                        throw nvae;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:429:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:429:59: ( valueExpr )*
                            loop150:
                            do {
                                int alt150=2;
                                int LA150_0 = input.LA(1);

                                if ( ((LA150_0>=IN_SET && LA150_0<=REGEXP)||LA150_0==NOT_EXPR||(LA150_0>=SUM && LA150_0<=AVG)||(LA150_0>=COALESCE && LA150_0<=COUNT)||(LA150_0>=CASE && LA150_0<=CASE2)||(LA150_0>=PREVIOUS && LA150_0<=EXISTS)||(LA150_0>=INSTANCEOF && LA150_0<=CURRENT_TIMESTAMP)||(LA150_0>=EVAL_AND_EXPR && LA150_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA150_0==EVENT_PROP_EXPR||LA150_0==CONCAT||(LA150_0>=LIB_FUNC_CHAIN && LA150_0<=DOT_EXPR)||LA150_0==ARRAY_EXPR||(LA150_0>=NOT_IN_SET && LA150_0<=NOT_REGEXP)||(LA150_0>=IN_RANGE && LA150_0<=SUBSELECT_EXPR)||(LA150_0>=EXISTS_SUBSELECT_EXPR && LA150_0<=NOT_IN_SUBSELECT_EXPR)||LA150_0==SUBSTITUTION||(LA150_0>=FIRST_AGGREG && LA150_0<=WINDOW_AGGREG)||(LA150_0>=INT_TYPE && LA150_0<=NULL_TYPE)||(LA150_0>=STAR && LA150_0<=PLUS)||(LA150_0>=BAND && LA150_0<=BXOR)||(LA150_0>=LT && LA150_0<=GE)||(LA150_0>=MINUS && LA150_0<=MOD)) ) {
                                    alt150=1;
                                }


                                switch (alt150) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:429:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2774);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop150;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:429:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2779);
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
                    // EsperEPL2Ast.g:430:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2792); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2794);
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

                    // EsperEPL2Ast.g:430:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==UP||(LA153_0>=IN_SET && LA153_0<=REGEXP)||LA153_0==NOT_EXPR||(LA153_0>=SUM && LA153_0<=AVG)||(LA153_0>=COALESCE && LA153_0<=COUNT)||(LA153_0>=CASE && LA153_0<=CASE2)||(LA153_0>=PREVIOUS && LA153_0<=EXISTS)||(LA153_0>=INSTANCEOF && LA153_0<=CURRENT_TIMESTAMP)||(LA153_0>=EVAL_AND_EXPR && LA153_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA153_0==EVENT_PROP_EXPR||LA153_0==CONCAT||(LA153_0>=LIB_FUNC_CHAIN && LA153_0<=DOT_EXPR)||LA153_0==ARRAY_EXPR||(LA153_0>=NOT_IN_SET && LA153_0<=NOT_REGEXP)||(LA153_0>=IN_RANGE && LA153_0<=SUBSELECT_EXPR)||(LA153_0>=EXISTS_SUBSELECT_EXPR && LA153_0<=NOT_IN_SUBSELECT_EXPR)||LA153_0==SUBSTITUTION||(LA153_0>=FIRST_AGGREG && LA153_0<=WINDOW_AGGREG)||(LA153_0>=INT_TYPE && LA153_0<=NULL_TYPE)||(LA153_0>=STAR && LA153_0<=PLUS)||(LA153_0>=BAND && LA153_0<=BXOR)||(LA153_0>=LT && LA153_0<=GE)||(LA153_0>=MINUS && LA153_0<=MOD)) ) {
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
                            // EsperEPL2Ast.g:430:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:430:63: ( valueExpr )*
                            loop152:
                            do {
                                int alt152=2;
                                int LA152_0 = input.LA(1);

                                if ( ((LA152_0>=IN_SET && LA152_0<=REGEXP)||LA152_0==NOT_EXPR||(LA152_0>=SUM && LA152_0<=AVG)||(LA152_0>=COALESCE && LA152_0<=COUNT)||(LA152_0>=CASE && LA152_0<=CASE2)||(LA152_0>=PREVIOUS && LA152_0<=EXISTS)||(LA152_0>=INSTANCEOF && LA152_0<=CURRENT_TIMESTAMP)||(LA152_0>=EVAL_AND_EXPR && LA152_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA152_0==EVENT_PROP_EXPR||LA152_0==CONCAT||(LA152_0>=LIB_FUNC_CHAIN && LA152_0<=DOT_EXPR)||LA152_0==ARRAY_EXPR||(LA152_0>=NOT_IN_SET && LA152_0<=NOT_REGEXP)||(LA152_0>=IN_RANGE && LA152_0<=SUBSELECT_EXPR)||(LA152_0>=EXISTS_SUBSELECT_EXPR && LA152_0<=NOT_IN_SUBSELECT_EXPR)||LA152_0==SUBSTITUTION||(LA152_0>=FIRST_AGGREG && LA152_0<=WINDOW_AGGREG)||(LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)||(LA152_0>=STAR && LA152_0<=PLUS)||(LA152_0>=BAND && LA152_0<=BXOR)||(LA152_0>=LT && LA152_0<=GE)||(LA152_0>=MINUS && LA152_0<=MOD)) ) {
                                    alt152=1;
                                }


                                switch (alt152) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:430:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2805);
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
                            // EsperEPL2Ast.g:430:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2810);
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
                    // EsperEPL2Ast.g:431:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2823); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2825);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:432:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2836);
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
    // EsperEPL2Ast.g:435:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFuncChain | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr | dotExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:436:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFuncChain | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr | dotExpr )
            int alt155=17;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt155=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt155=2;
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
                alt155=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt155=4;
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
                alt155=5;
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
                alt155=6;
                }
                break;
            case LIB_FUNC_CHAIN:
                {
                alt155=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt155=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt155=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt155=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt155=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt155=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt155=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt155=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt155=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt155=16;
                }
                break;
            case DOT_EXPR:
                {
                alt155=17;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 155, 0, input);

                throw nvae;
            }

            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:436:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2849);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:437:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2855);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:438:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2861);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:439:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2868);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:440:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2877);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:441:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2882);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:442:7: libFuncChain
                    {
                    pushFollow(FOLLOW_libFuncChain_in_valueExpr2890);
                    libFuncChain();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:443:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2895);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:444:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2900);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:445:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2906);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:446:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2911);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:447:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2916);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:448:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2921);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:449:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2926);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:450:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2932);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:451:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2939);
                    subSelectExistsExpr();

                    state._fsp--;


                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:452:4: dotExpr
                    {
                    pushFollow(FOLLOW_dotExpr_in_valueExpr2944);
                    dotExpr();

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
    // EsperEPL2Ast.g:455:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:456:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt157=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt157=1;
                }
                break;
            case LW:
                {
                alt157=2;
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
                alt157=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt157=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt157=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt157=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt157=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt157=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt157=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt157=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt157=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 157, 0, input);

                throw nvae;
            }

            switch (alt157) {
                case 1 :
                    // EsperEPL2Ast.g:456:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2957); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:457:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2966); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:458:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2973);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:459:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2981); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2983);
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
                    // EsperEPL2Ast.g:460:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2998);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:461:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime3004);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:462:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime3009);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:463:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime3014);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:464:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime3024); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:464:29: ( numericParameterList )+
                    int cnt156=0;
                    loop156:
                    do {
                        int alt156=2;
                        int LA156_0 = input.LA(1);

                        if ( (LA156_0==NUMERIC_PARAM_RANGE||LA156_0==NUMERIC_PARAM_FREQUENCY||(LA156_0>=INT_TYPE && LA156_0<=NULL_TYPE)) ) {
                            alt156=1;
                        }


                        switch (alt156) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:464:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime3026);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt156 >= 1 ) break loop156;
                                EarlyExitException eee =
                                    new EarlyExitException(156, input);
                                throw eee;
                        }
                        cnt156++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:465:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime3037); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:466:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime3044);
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
    // EsperEPL2Ast.g:469:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:470:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt158=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt158=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt158=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt158=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }

            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:470:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList3057);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:471:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList3064);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:472:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList3070);
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
    // EsperEPL2Ast.g:475:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:476:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:476:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3086); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:476:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt159=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt159=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt159=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt159=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 159, 0, input);

                throw nvae;
            }

            switch (alt159) {
                case 1 :
                    // EsperEPL2Ast.g:476:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3089);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:476:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3092);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:476:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3095);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:476:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt160=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt160=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt160=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt160=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 160, 0, input);

                throw nvae;
            }

            switch (alt160) {
                case 1 :
                    // EsperEPL2Ast.g:476:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3099);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:476:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3102);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:476:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3105);
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
    // EsperEPL2Ast.g:479:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:480:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:480:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3126); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:480:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt161=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt161=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt161=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt161=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }

            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:480:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator3129);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:480:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator3132);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:480:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator3135);
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
    // EsperEPL2Ast.g:483:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:484:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:484:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator3154); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:484:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt162=3;
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
            case EVENT_PROP_EXPR:
                {
                alt162=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt162=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }

            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:484:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator3157);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:484:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator3160);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:484:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator3163);
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
    // EsperEPL2Ast.g:487:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:488:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:488:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3182); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:488:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt163=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt163=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt163=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt163=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 163, 0, input);

                throw nvae;
            }

            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:488:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator3185);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:488:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator3188);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:488:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator3191);
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
    // EsperEPL2Ast.g:491:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:492:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:492:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3212); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr3214);
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
    // EsperEPL2Ast.g:495:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:496:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:496:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3233); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr3235);
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
    // EsperEPL2Ast.g:499:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:500:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:500:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3254); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr3256);
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
    // EsperEPL2Ast.g:503:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:504:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==IN_SUBSELECT_EXPR) ) {
                alt164=1;
            }
            else if ( (LA164_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt164=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 164, 0, input);

                throw nvae;
            }
            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:504:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3275); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3277);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3279);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:505:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3291); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3293);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3295);
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
    // EsperEPL2Ast.g:508:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:509:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:509:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3314); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3316);
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
    // EsperEPL2Ast.g:512:1: subQueryExpr : ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:513:2: ( ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:513:4: ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:513:4: ( DISTINCT )?
            int alt165=2;
            int LA165_0 = input.LA(1);

            if ( (LA165_0==DISTINCT) ) {
                alt165=1;
            }
            switch (alt165) {
                case 1 :
                    // EsperEPL2Ast.g:513:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3332); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_subQueryExpr3335);
            selectionList();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3337);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:513:48: ( whereClause[true] )?
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==WHERE_EXPR) ) {
                alt166=1;
            }
            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:513:49: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3340);
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
    // EsperEPL2Ast.g:516:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:517:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:517:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3358); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3360);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:517:36: ( viewListExpr )?
            int alt167=2;
            int LA167_0 = input.LA(1);

            if ( (LA167_0==VIEW_EXPR) ) {
                alt167=1;
            }
            switch (alt167) {
                case 1 :
                    // EsperEPL2Ast.g:517:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3363);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:517:52: ( IDENT )?
            int alt168=2;
            int LA168_0 = input.LA(1);

            if ( (LA168_0==IDENT) ) {
                alt168=1;
            }
            switch (alt168) {
                case 1 :
                    // EsperEPL2Ast.g:517:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3368); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:517:61: ( RETAINUNION )?
            int alt169=2;
            int LA169_0 = input.LA(1);

            if ( (LA169_0==RETAINUNION) ) {
                alt169=1;
            }
            switch (alt169) {
                case 1 :
                    // EsperEPL2Ast.g:517:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3372); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:517:74: ( RETAININTERSECTION )?
            int alt170=2;
            int LA170_0 = input.LA(1);

            if ( (LA170_0==RETAININTERSECTION) ) {
                alt170=1;
            }
            switch (alt170) {
                case 1 :
                    // EsperEPL2Ast.g:517:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3375); 

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
    // EsperEPL2Ast.g:520:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:521:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt173=2;
            int LA173_0 = input.LA(1);

            if ( (LA173_0==CASE) ) {
                alt173=1;
            }
            else if ( (LA173_0==CASE2) ) {
                alt173=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 173, 0, input);

                throw nvae;
            }
            switch (alt173) {
                case 1 :
                    // EsperEPL2Ast.g:521:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3395); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:521:13: ( valueExpr )*
                        loop171:
                        do {
                            int alt171=2;
                            int LA171_0 = input.LA(1);

                            if ( ((LA171_0>=IN_SET && LA171_0<=REGEXP)||LA171_0==NOT_EXPR||(LA171_0>=SUM && LA171_0<=AVG)||(LA171_0>=COALESCE && LA171_0<=COUNT)||(LA171_0>=CASE && LA171_0<=CASE2)||(LA171_0>=PREVIOUS && LA171_0<=EXISTS)||(LA171_0>=INSTANCEOF && LA171_0<=CURRENT_TIMESTAMP)||(LA171_0>=EVAL_AND_EXPR && LA171_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA171_0==EVENT_PROP_EXPR||LA171_0==CONCAT||(LA171_0>=LIB_FUNC_CHAIN && LA171_0<=DOT_EXPR)||LA171_0==ARRAY_EXPR||(LA171_0>=NOT_IN_SET && LA171_0<=NOT_REGEXP)||(LA171_0>=IN_RANGE && LA171_0<=SUBSELECT_EXPR)||(LA171_0>=EXISTS_SUBSELECT_EXPR && LA171_0<=NOT_IN_SUBSELECT_EXPR)||LA171_0==SUBSTITUTION||(LA171_0>=FIRST_AGGREG && LA171_0<=WINDOW_AGGREG)||(LA171_0>=INT_TYPE && LA171_0<=NULL_TYPE)||(LA171_0>=STAR && LA171_0<=PLUS)||(LA171_0>=BAND && LA171_0<=BXOR)||(LA171_0>=LT && LA171_0<=GE)||(LA171_0>=MINUS && LA171_0<=MOD)) ) {
                                alt171=1;
                            }


                            switch (alt171) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:521:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3398);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop171;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:522:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3411); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:522:14: ( valueExpr )*
                        loop172:
                        do {
                            int alt172=2;
                            int LA172_0 = input.LA(1);

                            if ( ((LA172_0>=IN_SET && LA172_0<=REGEXP)||LA172_0==NOT_EXPR||(LA172_0>=SUM && LA172_0<=AVG)||(LA172_0>=COALESCE && LA172_0<=COUNT)||(LA172_0>=CASE && LA172_0<=CASE2)||(LA172_0>=PREVIOUS && LA172_0<=EXISTS)||(LA172_0>=INSTANCEOF && LA172_0<=CURRENT_TIMESTAMP)||(LA172_0>=EVAL_AND_EXPR && LA172_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA172_0==EVENT_PROP_EXPR||LA172_0==CONCAT||(LA172_0>=LIB_FUNC_CHAIN && LA172_0<=DOT_EXPR)||LA172_0==ARRAY_EXPR||(LA172_0>=NOT_IN_SET && LA172_0<=NOT_REGEXP)||(LA172_0>=IN_RANGE && LA172_0<=SUBSELECT_EXPR)||(LA172_0>=EXISTS_SUBSELECT_EXPR && LA172_0<=NOT_IN_SUBSELECT_EXPR)||LA172_0==SUBSTITUTION||(LA172_0>=FIRST_AGGREG && LA172_0<=WINDOW_AGGREG)||(LA172_0>=INT_TYPE && LA172_0<=NULL_TYPE)||(LA172_0>=STAR && LA172_0<=PLUS)||(LA172_0>=BAND && LA172_0<=BXOR)||(LA172_0>=LT && LA172_0<=GE)||(LA172_0>=MINUS && LA172_0<=MOD)) ) {
                                alt172=1;
                            }


                            switch (alt172) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:522:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3414);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop172;
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
    // EsperEPL2Ast.g:525:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:526:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt176=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt176=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt176=2;
                }
                break;
            case IN_RANGE:
                {
                alt176=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt176=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 176, 0, input);

                throw nvae;
            }

            switch (alt176) {
                case 1 :
                    // EsperEPL2Ast.g:526:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3434); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3436);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3444);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:526:51: ( valueExpr )*
                    loop174:
                    do {
                        int alt174=2;
                        int LA174_0 = input.LA(1);

                        if ( ((LA174_0>=IN_SET && LA174_0<=REGEXP)||LA174_0==NOT_EXPR||(LA174_0>=SUM && LA174_0<=AVG)||(LA174_0>=COALESCE && LA174_0<=COUNT)||(LA174_0>=CASE && LA174_0<=CASE2)||(LA174_0>=PREVIOUS && LA174_0<=EXISTS)||(LA174_0>=INSTANCEOF && LA174_0<=CURRENT_TIMESTAMP)||(LA174_0>=EVAL_AND_EXPR && LA174_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA174_0==EVENT_PROP_EXPR||LA174_0==CONCAT||(LA174_0>=LIB_FUNC_CHAIN && LA174_0<=DOT_EXPR)||LA174_0==ARRAY_EXPR||(LA174_0>=NOT_IN_SET && LA174_0<=NOT_REGEXP)||(LA174_0>=IN_RANGE && LA174_0<=SUBSELECT_EXPR)||(LA174_0>=EXISTS_SUBSELECT_EXPR && LA174_0<=NOT_IN_SUBSELECT_EXPR)||LA174_0==SUBSTITUTION||(LA174_0>=FIRST_AGGREG && LA174_0<=WINDOW_AGGREG)||(LA174_0>=INT_TYPE && LA174_0<=NULL_TYPE)||(LA174_0>=STAR && LA174_0<=PLUS)||(LA174_0>=BAND && LA174_0<=BXOR)||(LA174_0>=LT && LA174_0<=GE)||(LA174_0>=MINUS && LA174_0<=MOD)) ) {
                            alt174=1;
                        }


                        switch (alt174) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:526:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3447);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop174;
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
                    // EsperEPL2Ast.g:527:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3466); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3468);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3476);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:527:55: ( valueExpr )*
                    loop175:
                    do {
                        int alt175=2;
                        int LA175_0 = input.LA(1);

                        if ( ((LA175_0>=IN_SET && LA175_0<=REGEXP)||LA175_0==NOT_EXPR||(LA175_0>=SUM && LA175_0<=AVG)||(LA175_0>=COALESCE && LA175_0<=COUNT)||(LA175_0>=CASE && LA175_0<=CASE2)||(LA175_0>=PREVIOUS && LA175_0<=EXISTS)||(LA175_0>=INSTANCEOF && LA175_0<=CURRENT_TIMESTAMP)||(LA175_0>=EVAL_AND_EXPR && LA175_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA175_0==EVENT_PROP_EXPR||LA175_0==CONCAT||(LA175_0>=LIB_FUNC_CHAIN && LA175_0<=DOT_EXPR)||LA175_0==ARRAY_EXPR||(LA175_0>=NOT_IN_SET && LA175_0<=NOT_REGEXP)||(LA175_0>=IN_RANGE && LA175_0<=SUBSELECT_EXPR)||(LA175_0>=EXISTS_SUBSELECT_EXPR && LA175_0<=NOT_IN_SUBSELECT_EXPR)||LA175_0==SUBSTITUTION||(LA175_0>=FIRST_AGGREG && LA175_0<=WINDOW_AGGREG)||(LA175_0>=INT_TYPE && LA175_0<=NULL_TYPE)||(LA175_0>=STAR && LA175_0<=PLUS)||(LA175_0>=BAND && LA175_0<=BXOR)||(LA175_0>=LT && LA175_0<=GE)||(LA175_0>=MINUS && LA175_0<=MOD)) ) {
                            alt175=1;
                        }


                        switch (alt175) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:527:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3479);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop175;
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
                    // EsperEPL2Ast.g:528:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3498); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3500);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3508);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3510);
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
                    // EsperEPL2Ast.g:529:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3527); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3529);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3537);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3539);
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
    // EsperEPL2Ast.g:532:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:533:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt178=2;
            int LA178_0 = input.LA(1);

            if ( (LA178_0==BETWEEN) ) {
                alt178=1;
            }
            else if ( (LA178_0==NOT_BETWEEN) ) {
                alt178=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 178, 0, input);

                throw nvae;
            }
            switch (alt178) {
                case 1 :
                    // EsperEPL2Ast.g:533:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3564); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3566);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3568);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3570);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:534:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3581); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3583);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3585);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:534:40: ( valueExpr )*
                    loop177:
                    do {
                        int alt177=2;
                        int LA177_0 = input.LA(1);

                        if ( ((LA177_0>=IN_SET && LA177_0<=REGEXP)||LA177_0==NOT_EXPR||(LA177_0>=SUM && LA177_0<=AVG)||(LA177_0>=COALESCE && LA177_0<=COUNT)||(LA177_0>=CASE && LA177_0<=CASE2)||(LA177_0>=PREVIOUS && LA177_0<=EXISTS)||(LA177_0>=INSTANCEOF && LA177_0<=CURRENT_TIMESTAMP)||(LA177_0>=EVAL_AND_EXPR && LA177_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA177_0==EVENT_PROP_EXPR||LA177_0==CONCAT||(LA177_0>=LIB_FUNC_CHAIN && LA177_0<=DOT_EXPR)||LA177_0==ARRAY_EXPR||(LA177_0>=NOT_IN_SET && LA177_0<=NOT_REGEXP)||(LA177_0>=IN_RANGE && LA177_0<=SUBSELECT_EXPR)||(LA177_0>=EXISTS_SUBSELECT_EXPR && LA177_0<=NOT_IN_SUBSELECT_EXPR)||LA177_0==SUBSTITUTION||(LA177_0>=FIRST_AGGREG && LA177_0<=WINDOW_AGGREG)||(LA177_0>=INT_TYPE && LA177_0<=NULL_TYPE)||(LA177_0>=STAR && LA177_0<=PLUS)||(LA177_0>=BAND && LA177_0<=BXOR)||(LA177_0>=LT && LA177_0<=GE)||(LA177_0>=MINUS && LA177_0<=MOD)) ) {
                            alt177=1;
                        }


                        switch (alt177) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:534:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3588);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop177;
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
    // EsperEPL2Ast.g:537:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:538:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt181=2;
            int LA181_0 = input.LA(1);

            if ( (LA181_0==LIKE) ) {
                alt181=1;
            }
            else if ( (LA181_0==NOT_LIKE) ) {
                alt181=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 181, 0, input);

                throw nvae;
            }
            switch (alt181) {
                case 1 :
                    // EsperEPL2Ast.g:538:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3608); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3610);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3612);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:538:33: ( valueExpr )?
                    int alt179=2;
                    int LA179_0 = input.LA(1);

                    if ( ((LA179_0>=IN_SET && LA179_0<=REGEXP)||LA179_0==NOT_EXPR||(LA179_0>=SUM && LA179_0<=AVG)||(LA179_0>=COALESCE && LA179_0<=COUNT)||(LA179_0>=CASE && LA179_0<=CASE2)||(LA179_0>=PREVIOUS && LA179_0<=EXISTS)||(LA179_0>=INSTANCEOF && LA179_0<=CURRENT_TIMESTAMP)||(LA179_0>=EVAL_AND_EXPR && LA179_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA179_0==EVENT_PROP_EXPR||LA179_0==CONCAT||(LA179_0>=LIB_FUNC_CHAIN && LA179_0<=DOT_EXPR)||LA179_0==ARRAY_EXPR||(LA179_0>=NOT_IN_SET && LA179_0<=NOT_REGEXP)||(LA179_0>=IN_RANGE && LA179_0<=SUBSELECT_EXPR)||(LA179_0>=EXISTS_SUBSELECT_EXPR && LA179_0<=NOT_IN_SUBSELECT_EXPR)||LA179_0==SUBSTITUTION||(LA179_0>=FIRST_AGGREG && LA179_0<=WINDOW_AGGREG)||(LA179_0>=INT_TYPE && LA179_0<=NULL_TYPE)||(LA179_0>=STAR && LA179_0<=PLUS)||(LA179_0>=BAND && LA179_0<=BXOR)||(LA179_0>=LT && LA179_0<=GE)||(LA179_0>=MINUS && LA179_0<=MOD)) ) {
                        alt179=1;
                    }
                    switch (alt179) {
                        case 1 :
                            // EsperEPL2Ast.g:538:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3615);
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
                    // EsperEPL2Ast.g:539:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3628); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3630);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3632);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:539:37: ( valueExpr )?
                    int alt180=2;
                    int LA180_0 = input.LA(1);

                    if ( ((LA180_0>=IN_SET && LA180_0<=REGEXP)||LA180_0==NOT_EXPR||(LA180_0>=SUM && LA180_0<=AVG)||(LA180_0>=COALESCE && LA180_0<=COUNT)||(LA180_0>=CASE && LA180_0<=CASE2)||(LA180_0>=PREVIOUS && LA180_0<=EXISTS)||(LA180_0>=INSTANCEOF && LA180_0<=CURRENT_TIMESTAMP)||(LA180_0>=EVAL_AND_EXPR && LA180_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA180_0==EVENT_PROP_EXPR||LA180_0==CONCAT||(LA180_0>=LIB_FUNC_CHAIN && LA180_0<=DOT_EXPR)||LA180_0==ARRAY_EXPR||(LA180_0>=NOT_IN_SET && LA180_0<=NOT_REGEXP)||(LA180_0>=IN_RANGE && LA180_0<=SUBSELECT_EXPR)||(LA180_0>=EXISTS_SUBSELECT_EXPR && LA180_0<=NOT_IN_SUBSELECT_EXPR)||LA180_0==SUBSTITUTION||(LA180_0>=FIRST_AGGREG && LA180_0<=WINDOW_AGGREG)||(LA180_0>=INT_TYPE && LA180_0<=NULL_TYPE)||(LA180_0>=STAR && LA180_0<=PLUS)||(LA180_0>=BAND && LA180_0<=BXOR)||(LA180_0>=LT && LA180_0<=GE)||(LA180_0>=MINUS && LA180_0<=MOD)) ) {
                        alt180=1;
                    }
                    switch (alt180) {
                        case 1 :
                            // EsperEPL2Ast.g:539:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3635);
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
    // EsperEPL2Ast.g:542:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:543:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt182=2;
            int LA182_0 = input.LA(1);

            if ( (LA182_0==REGEXP) ) {
                alt182=1;
            }
            else if ( (LA182_0==NOT_REGEXP) ) {
                alt182=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 182, 0, input);

                throw nvae;
            }
            switch (alt182) {
                case 1 :
                    // EsperEPL2Ast.g:543:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3654); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3656);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3658);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:544:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3669); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3671);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3673);
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
    // EsperEPL2Ast.g:547:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= FIRST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= WINDOW_AGGREG ( DISTINCT )? accessValueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? ) | ^(f= PREVIOUSCOUNT valueExpr ) | ^(f= PREVIOUSWINDOW valueExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= TYPEOF valueExpr ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:548:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= FIRST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= WINDOW_AGGREG ( DISTINCT )? accessValueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? ) | ^(f= PREVIOUSCOUNT valueExpr ) | ^(f= PREVIOUSWINDOW valueExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= TYPEOF valueExpr ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt199=20;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt199=1;
                }
                break;
            case AVG:
                {
                alt199=2;
                }
                break;
            case COUNT:
                {
                alt199=3;
                }
                break;
            case MEDIAN:
                {
                alt199=4;
                }
                break;
            case STDDEV:
                {
                alt199=5;
                }
                break;
            case AVEDEV:
                {
                alt199=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt199=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt199=8;
                }
                break;
            case WINDOW_AGGREG:
                {
                alt199=9;
                }
                break;
            case COALESCE:
                {
                alt199=10;
                }
                break;
            case PREVIOUS:
                {
                alt199=11;
                }
                break;
            case PREVIOUSTAIL:
                {
                alt199=12;
                }
                break;
            case PREVIOUSCOUNT:
                {
                alt199=13;
                }
                break;
            case PREVIOUSWINDOW:
                {
                alt199=14;
                }
                break;
            case PRIOR:
                {
                alt199=15;
                }
                break;
            case INSTANCEOF:
                {
                alt199=16;
                }
                break;
            case TYPEOF:
                {
                alt199=17;
                }
                break;
            case CAST:
                {
                alt199=18;
                }
                break;
            case EXISTS:
                {
                alt199=19;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt199=20;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 199, 0, input);

                throw nvae;
            }

            switch (alt199) {
                case 1 :
                    // EsperEPL2Ast.g:548:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3692); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:548:13: ( DISTINCT )?
                    int alt183=2;
                    int LA183_0 = input.LA(1);

                    if ( (LA183_0==DISTINCT) ) {
                        alt183=1;
                    }
                    switch (alt183) {
                        case 1 :
                            // EsperEPL2Ast.g:548:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3695); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3699);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:549:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3710); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:549:12: ( DISTINCT )?
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( (LA184_0==DISTINCT) ) {
                        alt184=1;
                    }
                    switch (alt184) {
                        case 1 :
                            // EsperEPL2Ast.g:549:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3713); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3717);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:550:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3728); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:550:14: ( ( DISTINCT )? valueExpr )?
                        int alt186=2;
                        int LA186_0 = input.LA(1);

                        if ( ((LA186_0>=IN_SET && LA186_0<=REGEXP)||LA186_0==NOT_EXPR||(LA186_0>=SUM && LA186_0<=AVG)||(LA186_0>=COALESCE && LA186_0<=COUNT)||(LA186_0>=CASE && LA186_0<=CASE2)||LA186_0==DISTINCT||(LA186_0>=PREVIOUS && LA186_0<=EXISTS)||(LA186_0>=INSTANCEOF && LA186_0<=CURRENT_TIMESTAMP)||(LA186_0>=EVAL_AND_EXPR && LA186_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA186_0==EVENT_PROP_EXPR||LA186_0==CONCAT||(LA186_0>=LIB_FUNC_CHAIN && LA186_0<=DOT_EXPR)||LA186_0==ARRAY_EXPR||(LA186_0>=NOT_IN_SET && LA186_0<=NOT_REGEXP)||(LA186_0>=IN_RANGE && LA186_0<=SUBSELECT_EXPR)||(LA186_0>=EXISTS_SUBSELECT_EXPR && LA186_0<=NOT_IN_SUBSELECT_EXPR)||LA186_0==SUBSTITUTION||(LA186_0>=FIRST_AGGREG && LA186_0<=WINDOW_AGGREG)||(LA186_0>=INT_TYPE && LA186_0<=NULL_TYPE)||(LA186_0>=STAR && LA186_0<=PLUS)||(LA186_0>=BAND && LA186_0<=BXOR)||(LA186_0>=LT && LA186_0<=GE)||(LA186_0>=MINUS && LA186_0<=MOD)) ) {
                            alt186=1;
                        }
                        switch (alt186) {
                            case 1 :
                                // EsperEPL2Ast.g:550:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:550:15: ( DISTINCT )?
                                int alt185=2;
                                int LA185_0 = input.LA(1);

                                if ( (LA185_0==DISTINCT) ) {
                                    alt185=1;
                                }
                                switch (alt185) {
                                    case 1 :
                                        // EsperEPL2Ast.g:550:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3732); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3736);
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
                case 4 :
                    // EsperEPL2Ast.g:551:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3750); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:551:15: ( DISTINCT )?
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( (LA187_0==DISTINCT) ) {
                        alt187=1;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:551:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3753); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3757);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:552:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3768); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:552:15: ( DISTINCT )?
                    int alt188=2;
                    int LA188_0 = input.LA(1);

                    if ( (LA188_0==DISTINCT) ) {
                        alt188=1;
                    }
                    switch (alt188) {
                        case 1 :
                            // EsperEPL2Ast.g:552:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3771); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3775);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:553:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3786); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:553:15: ( DISTINCT )?
                    int alt189=2;
                    int LA189_0 = input.LA(1);

                    if ( (LA189_0==DISTINCT) ) {
                        alt189=1;
                    }
                    switch (alt189) {
                        case 1 :
                            // EsperEPL2Ast.g:553:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3789); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3793);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:554:4: ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3804); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:554:20: ( DISTINCT )?
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( (LA190_0==DISTINCT) ) {
                        alt190=1;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:554:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3807); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3811);
                    accessValueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:554:48: ( valueExpr )?
                    int alt191=2;
                    int LA191_0 = input.LA(1);

                    if ( ((LA191_0>=IN_SET && LA191_0<=REGEXP)||LA191_0==NOT_EXPR||(LA191_0>=SUM && LA191_0<=AVG)||(LA191_0>=COALESCE && LA191_0<=COUNT)||(LA191_0>=CASE && LA191_0<=CASE2)||(LA191_0>=PREVIOUS && LA191_0<=EXISTS)||(LA191_0>=INSTANCEOF && LA191_0<=CURRENT_TIMESTAMP)||(LA191_0>=EVAL_AND_EXPR && LA191_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA191_0==EVENT_PROP_EXPR||LA191_0==CONCAT||(LA191_0>=LIB_FUNC_CHAIN && LA191_0<=DOT_EXPR)||LA191_0==ARRAY_EXPR||(LA191_0>=NOT_IN_SET && LA191_0<=NOT_REGEXP)||(LA191_0>=IN_RANGE && LA191_0<=SUBSELECT_EXPR)||(LA191_0>=EXISTS_SUBSELECT_EXPR && LA191_0<=NOT_IN_SUBSELECT_EXPR)||LA191_0==SUBSTITUTION||(LA191_0>=FIRST_AGGREG && LA191_0<=WINDOW_AGGREG)||(LA191_0>=INT_TYPE && LA191_0<=NULL_TYPE)||(LA191_0>=STAR && LA191_0<=PLUS)||(LA191_0>=BAND && LA191_0<=BXOR)||(LA191_0>=LT && LA191_0<=GE)||(LA191_0>=MINUS && LA191_0<=MOD)) ) {
                        alt191=1;
                    }
                    switch (alt191) {
                        case 1 :
                            // EsperEPL2Ast.g:554:48: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3813);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:555:4: ^(f= FIRST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3825); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:555:21: ( DISTINCT )?
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( (LA192_0==DISTINCT) ) {
                        alt192=1;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:555:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3828); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3832);
                    accessValueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:555:49: ( valueExpr )?
                    int alt193=2;
                    int LA193_0 = input.LA(1);

                    if ( ((LA193_0>=IN_SET && LA193_0<=REGEXP)||LA193_0==NOT_EXPR||(LA193_0>=SUM && LA193_0<=AVG)||(LA193_0>=COALESCE && LA193_0<=COUNT)||(LA193_0>=CASE && LA193_0<=CASE2)||(LA193_0>=PREVIOUS && LA193_0<=EXISTS)||(LA193_0>=INSTANCEOF && LA193_0<=CURRENT_TIMESTAMP)||(LA193_0>=EVAL_AND_EXPR && LA193_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA193_0==EVENT_PROP_EXPR||LA193_0==CONCAT||(LA193_0>=LIB_FUNC_CHAIN && LA193_0<=DOT_EXPR)||LA193_0==ARRAY_EXPR||(LA193_0>=NOT_IN_SET && LA193_0<=NOT_REGEXP)||(LA193_0>=IN_RANGE && LA193_0<=SUBSELECT_EXPR)||(LA193_0>=EXISTS_SUBSELECT_EXPR && LA193_0<=NOT_IN_SUBSELECT_EXPR)||LA193_0==SUBSTITUTION||(LA193_0>=FIRST_AGGREG && LA193_0<=WINDOW_AGGREG)||(LA193_0>=INT_TYPE && LA193_0<=NULL_TYPE)||(LA193_0>=STAR && LA193_0<=PLUS)||(LA193_0>=BAND && LA193_0<=BXOR)||(LA193_0>=LT && LA193_0<=GE)||(LA193_0>=MINUS && LA193_0<=MOD)) ) {
                        alt193=1;
                    }
                    switch (alt193) {
                        case 1 :
                            // EsperEPL2Ast.g:555:49: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3834);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:556:4: ^(f= WINDOW_AGGREG ( DISTINCT )? accessValueExpr )
                    {
                    f=(CommonTree)match(input,WINDOW_AGGREG,FOLLOW_WINDOW_AGGREG_in_builtinFunc3846); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:556:22: ( DISTINCT )?
                    int alt194=2;
                    int LA194_0 = input.LA(1);

                    if ( (LA194_0==DISTINCT) ) {
                        alt194=1;
                    }
                    switch (alt194) {
                        case 1 :
                            // EsperEPL2Ast.g:556:23: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3849); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3853);
                    accessValueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:557:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3865); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3867);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3869);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:557:38: ( valueExpr )*
                    loop195:
                    do {
                        int alt195=2;
                        int LA195_0 = input.LA(1);

                        if ( ((LA195_0>=IN_SET && LA195_0<=REGEXP)||LA195_0==NOT_EXPR||(LA195_0>=SUM && LA195_0<=AVG)||(LA195_0>=COALESCE && LA195_0<=COUNT)||(LA195_0>=CASE && LA195_0<=CASE2)||(LA195_0>=PREVIOUS && LA195_0<=EXISTS)||(LA195_0>=INSTANCEOF && LA195_0<=CURRENT_TIMESTAMP)||(LA195_0>=EVAL_AND_EXPR && LA195_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA195_0==EVENT_PROP_EXPR||LA195_0==CONCAT||(LA195_0>=LIB_FUNC_CHAIN && LA195_0<=DOT_EXPR)||LA195_0==ARRAY_EXPR||(LA195_0>=NOT_IN_SET && LA195_0<=NOT_REGEXP)||(LA195_0>=IN_RANGE && LA195_0<=SUBSELECT_EXPR)||(LA195_0>=EXISTS_SUBSELECT_EXPR && LA195_0<=NOT_IN_SUBSELECT_EXPR)||LA195_0==SUBSTITUTION||(LA195_0>=FIRST_AGGREG && LA195_0<=WINDOW_AGGREG)||(LA195_0>=INT_TYPE && LA195_0<=NULL_TYPE)||(LA195_0>=STAR && LA195_0<=PLUS)||(LA195_0>=BAND && LA195_0<=BXOR)||(LA195_0>=LT && LA195_0<=GE)||(LA195_0>=MINUS && LA195_0<=MOD)) ) {
                            alt195=1;
                        }


                        switch (alt195) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:557:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3872);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop195;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:558:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3887); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3889);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:558:28: ( valueExpr )?
                    int alt196=2;
                    int LA196_0 = input.LA(1);

                    if ( ((LA196_0>=IN_SET && LA196_0<=REGEXP)||LA196_0==NOT_EXPR||(LA196_0>=SUM && LA196_0<=AVG)||(LA196_0>=COALESCE && LA196_0<=COUNT)||(LA196_0>=CASE && LA196_0<=CASE2)||(LA196_0>=PREVIOUS && LA196_0<=EXISTS)||(LA196_0>=INSTANCEOF && LA196_0<=CURRENT_TIMESTAMP)||(LA196_0>=EVAL_AND_EXPR && LA196_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA196_0==EVENT_PROP_EXPR||LA196_0==CONCAT||(LA196_0>=LIB_FUNC_CHAIN && LA196_0<=DOT_EXPR)||LA196_0==ARRAY_EXPR||(LA196_0>=NOT_IN_SET && LA196_0<=NOT_REGEXP)||(LA196_0>=IN_RANGE && LA196_0<=SUBSELECT_EXPR)||(LA196_0>=EXISTS_SUBSELECT_EXPR && LA196_0<=NOT_IN_SUBSELECT_EXPR)||LA196_0==SUBSTITUTION||(LA196_0>=FIRST_AGGREG && LA196_0<=WINDOW_AGGREG)||(LA196_0>=INT_TYPE && LA196_0<=NULL_TYPE)||(LA196_0>=STAR && LA196_0<=PLUS)||(LA196_0>=BAND && LA196_0<=BXOR)||(LA196_0>=LT && LA196_0<=GE)||(LA196_0>=MINUS && LA196_0<=MOD)) ) {
                        alt196=1;
                    }
                    switch (alt196) {
                        case 1 :
                            // EsperEPL2Ast.g:558:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3891);
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
                    // EsperEPL2Ast.g:559:5: ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUSTAIL,FOLLOW_PREVIOUSTAIL_in_builtinFunc3904); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3906);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:559:32: ( valueExpr )?
                    int alt197=2;
                    int LA197_0 = input.LA(1);

                    if ( ((LA197_0>=IN_SET && LA197_0<=REGEXP)||LA197_0==NOT_EXPR||(LA197_0>=SUM && LA197_0<=AVG)||(LA197_0>=COALESCE && LA197_0<=COUNT)||(LA197_0>=CASE && LA197_0<=CASE2)||(LA197_0>=PREVIOUS && LA197_0<=EXISTS)||(LA197_0>=INSTANCEOF && LA197_0<=CURRENT_TIMESTAMP)||(LA197_0>=EVAL_AND_EXPR && LA197_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA197_0==EVENT_PROP_EXPR||LA197_0==CONCAT||(LA197_0>=LIB_FUNC_CHAIN && LA197_0<=DOT_EXPR)||LA197_0==ARRAY_EXPR||(LA197_0>=NOT_IN_SET && LA197_0<=NOT_REGEXP)||(LA197_0>=IN_RANGE && LA197_0<=SUBSELECT_EXPR)||(LA197_0>=EXISTS_SUBSELECT_EXPR && LA197_0<=NOT_IN_SUBSELECT_EXPR)||LA197_0==SUBSTITUTION||(LA197_0>=FIRST_AGGREG && LA197_0<=WINDOW_AGGREG)||(LA197_0>=INT_TYPE && LA197_0<=NULL_TYPE)||(LA197_0>=STAR && LA197_0<=PLUS)||(LA197_0>=BAND && LA197_0<=BXOR)||(LA197_0>=LT && LA197_0<=GE)||(LA197_0>=MINUS && LA197_0<=MOD)) ) {
                        alt197=1;
                    }
                    switch (alt197) {
                        case 1 :
                            // EsperEPL2Ast.g:559:32: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3908);
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
                    // EsperEPL2Ast.g:560:5: ^(f= PREVIOUSCOUNT valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSCOUNT,FOLLOW_PREVIOUSCOUNT_in_builtinFunc3921); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3923);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:561:5: ^(f= PREVIOUSWINDOW valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSWINDOW,FOLLOW_PREVIOUSWINDOW_in_builtinFunc3935); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3937);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:562:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3949); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3953); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3955);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:563:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3968); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3970);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3972); 
                    // EsperEPL2Ast.g:563:42: ( CLASS_IDENT )*
                    loop198:
                    do {
                        int alt198=2;
                        int LA198_0 = input.LA(1);

                        if ( (LA198_0==CLASS_IDENT) ) {
                            alt198=1;
                        }


                        switch (alt198) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:563:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3975); 

                    	    }
                    	    break;

                    	default :
                    	    break loop198;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:564:5: ^(f= TYPEOF valueExpr )
                    {
                    f=(CommonTree)match(input,TYPEOF,FOLLOW_TYPEOF_in_builtinFunc3989); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3991);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 18 :
                    // EsperEPL2Ast.g:565:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc4003); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc4005);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc4007); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 19 :
                    // EsperEPL2Ast.g:566:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc4019); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc4021);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 20 :
                    // EsperEPL2Ast.g:567:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc4033); 



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


    // $ANTLR start "accessValueExpr"
    // EsperEPL2Ast.g:570:1: accessValueExpr : ( PROPERTY_WILDCARD_SELECT | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) | valueExpr );
    public final void accessValueExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:571:2: ( PROPERTY_WILDCARD_SELECT | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) | valueExpr )
            int alt201=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt201=1;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt201=2;
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
                alt201=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 201, 0, input);

                throw nvae;
            }

            switch (alt201) {
                case 1 :
                    // EsperEPL2Ast.g:571:5: PROPERTY_WILDCARD_SELECT
                    {
                    match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExpr4050); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:571:32: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExpr4057); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_accessValueExpr4059); 
                    // EsperEPL2Ast.g:571:68: ( IDENT )?
                    int alt200=2;
                    int LA200_0 = input.LA(1);

                    if ( (LA200_0==IDENT) ) {
                        alt200=1;
                    }
                    switch (alt200) {
                        case 1 :
                            // EsperEPL2Ast.g:571:68: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_accessValueExpr4061); 

                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:571:78: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_accessValueExpr4067);
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
    // $ANTLR end "accessValueExpr"


    // $ANTLR start "arrayExpr"
    // EsperEPL2Ast.g:574:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:575:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:575:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr4084); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:575:19: ( valueExpr )*
                loop202:
                do {
                    int alt202=2;
                    int LA202_0 = input.LA(1);

                    if ( ((LA202_0>=IN_SET && LA202_0<=REGEXP)||LA202_0==NOT_EXPR||(LA202_0>=SUM && LA202_0<=AVG)||(LA202_0>=COALESCE && LA202_0<=COUNT)||(LA202_0>=CASE && LA202_0<=CASE2)||(LA202_0>=PREVIOUS && LA202_0<=EXISTS)||(LA202_0>=INSTANCEOF && LA202_0<=CURRENT_TIMESTAMP)||(LA202_0>=EVAL_AND_EXPR && LA202_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA202_0==EVENT_PROP_EXPR||LA202_0==CONCAT||(LA202_0>=LIB_FUNC_CHAIN && LA202_0<=DOT_EXPR)||LA202_0==ARRAY_EXPR||(LA202_0>=NOT_IN_SET && LA202_0<=NOT_REGEXP)||(LA202_0>=IN_RANGE && LA202_0<=SUBSELECT_EXPR)||(LA202_0>=EXISTS_SUBSELECT_EXPR && LA202_0<=NOT_IN_SUBSELECT_EXPR)||LA202_0==SUBSTITUTION||(LA202_0>=FIRST_AGGREG && LA202_0<=WINDOW_AGGREG)||(LA202_0>=INT_TYPE && LA202_0<=NULL_TYPE)||(LA202_0>=STAR && LA202_0<=PLUS)||(LA202_0>=BAND && LA202_0<=BXOR)||(LA202_0>=LT && LA202_0<=GE)||(LA202_0>=MINUS && LA202_0<=MOD)) ) {
                        alt202=1;
                    }


                    switch (alt202) {
                	case 1 :
                	    // EsperEPL2Ast.g:575:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr4087);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop202;
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
    // EsperEPL2Ast.g:578:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:579:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt204=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt204=1;
                }
                break;
            case MINUS:
                {
                alt204=2;
                }
                break;
            case DIV:
                {
                alt204=3;
                }
                break;
            case STAR:
                {
                alt204=4;
                }
                break;
            case MOD:
                {
                alt204=5;
                }
                break;
            case BAND:
                {
                alt204=6;
                }
                break;
            case BOR:
                {
                alt204=7;
                }
                break;
            case BXOR:
                {
                alt204=8;
                }
                break;
            case CONCAT:
                {
                alt204=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 204, 0, input);

                throw nvae;
            }

            switch (alt204) {
                case 1 :
                    // EsperEPL2Ast.g:579:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr4108); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4110);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4112);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:580:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr4124); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4126);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4128);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:581:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr4140); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4142);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4144);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:582:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr4155); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4157);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4159);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:583:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr4171); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4173);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4175);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:584:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr4186); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4188);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4190);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:585:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr4201); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4203);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4205);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:586:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr4216); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4218);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4220);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:587:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr4232); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4234);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4236);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:587:36: ( valueExpr )*
                    loop203:
                    do {
                        int alt203=2;
                        int LA203_0 = input.LA(1);

                        if ( ((LA203_0>=IN_SET && LA203_0<=REGEXP)||LA203_0==NOT_EXPR||(LA203_0>=SUM && LA203_0<=AVG)||(LA203_0>=COALESCE && LA203_0<=COUNT)||(LA203_0>=CASE && LA203_0<=CASE2)||(LA203_0>=PREVIOUS && LA203_0<=EXISTS)||(LA203_0>=INSTANCEOF && LA203_0<=CURRENT_TIMESTAMP)||(LA203_0>=EVAL_AND_EXPR && LA203_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA203_0==EVENT_PROP_EXPR||LA203_0==CONCAT||(LA203_0>=LIB_FUNC_CHAIN && LA203_0<=DOT_EXPR)||LA203_0==ARRAY_EXPR||(LA203_0>=NOT_IN_SET && LA203_0<=NOT_REGEXP)||(LA203_0>=IN_RANGE && LA203_0<=SUBSELECT_EXPR)||(LA203_0>=EXISTS_SUBSELECT_EXPR && LA203_0<=NOT_IN_SUBSELECT_EXPR)||LA203_0==SUBSTITUTION||(LA203_0>=FIRST_AGGREG && LA203_0<=WINDOW_AGGREG)||(LA203_0>=INT_TYPE && LA203_0<=NULL_TYPE)||(LA203_0>=STAR && LA203_0<=PLUS)||(LA203_0>=BAND && LA203_0<=BXOR)||(LA203_0>=LT && LA203_0<=GE)||(LA203_0>=MINUS && LA203_0<=MOD)) ) {
                            alt203=1;
                        }


                        switch (alt203) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:587:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4239);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop203;
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
    // EsperEPL2Ast.g:590:1: dotExpr : ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* ) ;
    public final void dotExpr() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:591:2: ( ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* ) )
            // EsperEPL2Ast.g:591:4: ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* )
            {
            d=(CommonTree)match(input,DOT_EXPR,FOLLOW_DOT_EXPR_in_dotExpr4259); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dotExpr4261);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:591:27: ( libFunctionWithClass )*
            loop205:
            do {
                int alt205=2;
                int LA205_0 = input.LA(1);

                if ( (LA205_0==LIB_FUNCTION) ) {
                    alt205=1;
                }


                switch (alt205) {
            	case 1 :
            	    // EsperEPL2Ast.g:591:27: libFunctionWithClass
            	    {
            	    pushFollow(FOLLOW_libFunctionWithClass_in_dotExpr4263);
            	    libFunctionWithClass();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop205;
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


    // $ANTLR start "libFuncChain"
    // EsperEPL2Ast.g:594:1: libFuncChain : ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* ) ;
    public final void libFuncChain() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:595:2: ( ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* ) )
            // EsperEPL2Ast.g:595:6: ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* )
            {
            l=(CommonTree)match(input,LIB_FUNC_CHAIN,FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4283); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_libFunctionWithClass_in_libFuncChain4285);
            libFunctionWithClass();

            state._fsp--;

            // EsperEPL2Ast.g:595:46: ( libOrPropFunction )*
            loop206:
            do {
                int alt206=2;
                int LA206_0 = input.LA(1);

                if ( (LA206_0==EVENT_PROP_EXPR||LA206_0==LIB_FUNCTION) ) {
                    alt206=1;
                }


                switch (alt206) {
            	case 1 :
            	    // EsperEPL2Ast.g:595:46: libOrPropFunction
            	    {
            	    pushFollow(FOLLOW_libOrPropFunction_in_libFuncChain4287);
            	    libOrPropFunction();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop206;
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
    // EsperEPL2Ast.g:598:1: libFunctionWithClass : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunctionWithClass() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:599:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:599:6: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4307); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:599:23: ( CLASS_IDENT )?
            int alt207=2;
            int LA207_0 = input.LA(1);

            if ( (LA207_0==CLASS_IDENT) ) {
                alt207=1;
            }
            switch (alt207) {
                case 1 :
                    // EsperEPL2Ast.g:599:24: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunctionWithClass4310); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunctionWithClass4314); 
            // EsperEPL2Ast.g:599:44: ( DISTINCT )?
            int alt208=2;
            int LA208_0 = input.LA(1);

            if ( (LA208_0==DISTINCT) ) {
                alt208=1;
            }
            switch (alt208) {
                case 1 :
                    // EsperEPL2Ast.g:599:45: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunctionWithClass4317); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:599:56: ( valueExpr )*
            loop209:
            do {
                int alt209=2;
                int LA209_0 = input.LA(1);

                if ( ((LA209_0>=IN_SET && LA209_0<=REGEXP)||LA209_0==NOT_EXPR||(LA209_0>=SUM && LA209_0<=AVG)||(LA209_0>=COALESCE && LA209_0<=COUNT)||(LA209_0>=CASE && LA209_0<=CASE2)||(LA209_0>=PREVIOUS && LA209_0<=EXISTS)||(LA209_0>=INSTANCEOF && LA209_0<=CURRENT_TIMESTAMP)||(LA209_0>=EVAL_AND_EXPR && LA209_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA209_0==EVENT_PROP_EXPR||LA209_0==CONCAT||(LA209_0>=LIB_FUNC_CHAIN && LA209_0<=DOT_EXPR)||LA209_0==ARRAY_EXPR||(LA209_0>=NOT_IN_SET && LA209_0<=NOT_REGEXP)||(LA209_0>=IN_RANGE && LA209_0<=SUBSELECT_EXPR)||(LA209_0>=EXISTS_SUBSELECT_EXPR && LA209_0<=NOT_IN_SUBSELECT_EXPR)||LA209_0==SUBSTITUTION||(LA209_0>=FIRST_AGGREG && LA209_0<=WINDOW_AGGREG)||(LA209_0>=INT_TYPE && LA209_0<=NULL_TYPE)||(LA209_0>=STAR && LA209_0<=PLUS)||(LA209_0>=BAND && LA209_0<=BXOR)||(LA209_0>=LT && LA209_0<=GE)||(LA209_0>=MINUS && LA209_0<=MOD)) ) {
                    alt209=1;
                }


                switch (alt209) {
            	case 1 :
            	    // EsperEPL2Ast.g:599:57: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunctionWithClass4322);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop209;
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
    // $ANTLR end "libFunctionWithClass"


    // $ANTLR start "libOrPropFunction"
    // EsperEPL2Ast.g:602:1: libOrPropFunction : ( eventPropertyExpr[false] | libFunctionWithClass );
    public final void libOrPropFunction() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:603:2: ( eventPropertyExpr[false] | libFunctionWithClass )
            int alt210=2;
            int LA210_0 = input.LA(1);

            if ( (LA210_0==EVENT_PROP_EXPR) ) {
                alt210=1;
            }
            else if ( (LA210_0==LIB_FUNCTION) ) {
                alt210=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 210, 0, input);

                throw nvae;
            }
            switch (alt210) {
                case 1 :
                    // EsperEPL2Ast.g:603:7: eventPropertyExpr[false]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_libOrPropFunction4340);
                    eventPropertyExpr(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:604:7: libFunctionWithClass
                    {
                    pushFollow(FOLLOW_libFunctionWithClass_in_libOrPropFunction4350);
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
    // EsperEPL2Ast.g:610:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:611:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:611:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:611:4: ( annotation[true] )*
            loop211:
            do {
                int alt211=2;
                int LA211_0 = input.LA(1);

                if ( (LA211_0==ANNOTATION) ) {
                    alt211=1;
                }


                switch (alt211) {
            	case 1 :
            	    // EsperEPL2Ast.g:611:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule4365);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop211;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule4369);
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
    // EsperEPL2Ast.g:614:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:615:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt216=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt216=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt216=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt216=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt216=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt216=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt216=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt216=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 216, 0, input);

                throw nvae;
            }

            switch (alt216) {
                case 1 :
                    // EsperEPL2Ast.g:615:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice4383);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:616:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice4388);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:617:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice4398); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4400);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:618:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4414); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice4416);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4418);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:619:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4432); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4434);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:620:5: ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice4448); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4450);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:620:32: ( IDENT IDENT ( valueExprWithTime )* | valueExpr )
                    int alt213=2;
                    int LA213_0 = input.LA(1);

                    if ( (LA213_0==IDENT) ) {
                        alt213=1;
                    }
                    else if ( ((LA213_0>=IN_SET && LA213_0<=REGEXP)||LA213_0==NOT_EXPR||(LA213_0>=SUM && LA213_0<=AVG)||(LA213_0>=COALESCE && LA213_0<=COUNT)||(LA213_0>=CASE && LA213_0<=CASE2)||(LA213_0>=PREVIOUS && LA213_0<=EXISTS)||(LA213_0>=INSTANCEOF && LA213_0<=CURRENT_TIMESTAMP)||(LA213_0>=EVAL_AND_EXPR && LA213_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA213_0==EVENT_PROP_EXPR||LA213_0==CONCAT||(LA213_0>=LIB_FUNC_CHAIN && LA213_0<=DOT_EXPR)||LA213_0==ARRAY_EXPR||(LA213_0>=NOT_IN_SET && LA213_0<=NOT_REGEXP)||(LA213_0>=IN_RANGE && LA213_0<=SUBSELECT_EXPR)||(LA213_0>=EXISTS_SUBSELECT_EXPR && LA213_0<=NOT_IN_SUBSELECT_EXPR)||LA213_0==SUBSTITUTION||(LA213_0>=FIRST_AGGREG && LA213_0<=WINDOW_AGGREG)||(LA213_0>=INT_TYPE && LA213_0<=NULL_TYPE)||(LA213_0>=STAR && LA213_0<=PLUS)||(LA213_0>=BAND && LA213_0<=BXOR)||(LA213_0>=LT && LA213_0<=GE)||(LA213_0>=MINUS && LA213_0<=MOD)) ) {
                        alt213=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 213, 0, input);

                        throw nvae;
                    }
                    switch (alt213) {
                        case 1 :
                            // EsperEPL2Ast.g:620:33: IDENT IDENT ( valueExprWithTime )*
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4453); 
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4455); 
                            // EsperEPL2Ast.g:620:45: ( valueExprWithTime )*
                            loop212:
                            do {
                                int alt212=2;
                                int LA212_0 = input.LA(1);

                                if ( ((LA212_0>=IN_SET && LA212_0<=REGEXP)||LA212_0==NOT_EXPR||(LA212_0>=SUM && LA212_0<=AVG)||(LA212_0>=COALESCE && LA212_0<=COUNT)||(LA212_0>=CASE && LA212_0<=CASE2)||LA212_0==LAST||(LA212_0>=PREVIOUS && LA212_0<=EXISTS)||(LA212_0>=LW && LA212_0<=CURRENT_TIMESTAMP)||(LA212_0>=NUMERIC_PARAM_RANGE && LA212_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA212_0>=EVAL_AND_EXPR && LA212_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA212_0==EVENT_PROP_EXPR||LA212_0==CONCAT||(LA212_0>=LIB_FUNC_CHAIN && LA212_0<=DOT_EXPR)||(LA212_0>=TIME_PERIOD && LA212_0<=ARRAY_EXPR)||(LA212_0>=NOT_IN_SET && LA212_0<=NOT_REGEXP)||(LA212_0>=IN_RANGE && LA212_0<=SUBSELECT_EXPR)||(LA212_0>=EXISTS_SUBSELECT_EXPR && LA212_0<=NOT_IN_SUBSELECT_EXPR)||(LA212_0>=LAST_OPERATOR && LA212_0<=SUBSTITUTION)||LA212_0==NUMBERSETSTAR||(LA212_0>=FIRST_AGGREG && LA212_0<=WINDOW_AGGREG)||(LA212_0>=INT_TYPE && LA212_0<=NULL_TYPE)||(LA212_0>=STAR && LA212_0<=PLUS)||(LA212_0>=BAND && LA212_0<=BXOR)||(LA212_0>=LT && LA212_0<=GE)||(LA212_0>=MINUS && LA212_0<=MOD)) ) {
                                    alt212=1;
                                }


                                switch (alt212) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:620:45: valueExprWithTime
                            	    {
                            	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice4457);
                            	    valueExprWithTime();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop212;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:620:66: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_exprChoice4462);
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
                    // EsperEPL2Ast.g:621:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4476); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:621:26: ( matchUntilRange )?
                    int alt214=2;
                    int LA214_0 = input.LA(1);

                    if ( ((LA214_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA214_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt214=1;
                    }
                    switch (alt214) {
                        case 1 :
                            // EsperEPL2Ast.g:621:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4478);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4481);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:621:54: ( exprChoice )?
                    int alt215=2;
                    int LA215_0 = input.LA(1);

                    if ( ((LA215_0>=OR_EXPR && LA215_0<=AND_EXPR)||(LA215_0>=EVERY_EXPR && LA215_0<=EVERY_DISTINCT_EXPR)||LA215_0==FOLLOWED_BY_EXPR||(LA215_0>=PATTERN_FILTER_EXPR && LA215_0<=PATTERN_NOT_EXPR)||(LA215_0>=GUARD_EXPR && LA215_0<=OBSERVER_EXPR)||LA215_0==MATCH_UNTIL_EXPR) ) {
                        alt215=1;
                    }
                    switch (alt215) {
                        case 1 :
                            // EsperEPL2Ast.g:621:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4483);
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
    // EsperEPL2Ast.g:625:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExprWithTime )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:626:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExprWithTime )+ ) )
            // EsperEPL2Ast.g:626:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExprWithTime )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4504); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:626:35: ( valueExprWithTime )+
            int cnt217=0;
            loop217:
            do {
                int alt217=2;
                int LA217_0 = input.LA(1);

                if ( ((LA217_0>=IN_SET && LA217_0<=REGEXP)||LA217_0==NOT_EXPR||(LA217_0>=SUM && LA217_0<=AVG)||(LA217_0>=COALESCE && LA217_0<=COUNT)||(LA217_0>=CASE && LA217_0<=CASE2)||LA217_0==LAST||(LA217_0>=PREVIOUS && LA217_0<=EXISTS)||(LA217_0>=LW && LA217_0<=CURRENT_TIMESTAMP)||(LA217_0>=NUMERIC_PARAM_RANGE && LA217_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA217_0>=EVAL_AND_EXPR && LA217_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA217_0==EVENT_PROP_EXPR||LA217_0==CONCAT||(LA217_0>=LIB_FUNC_CHAIN && LA217_0<=DOT_EXPR)||(LA217_0>=TIME_PERIOD && LA217_0<=ARRAY_EXPR)||(LA217_0>=NOT_IN_SET && LA217_0<=NOT_REGEXP)||(LA217_0>=IN_RANGE && LA217_0<=SUBSELECT_EXPR)||(LA217_0>=EXISTS_SUBSELECT_EXPR && LA217_0<=NOT_IN_SUBSELECT_EXPR)||(LA217_0>=LAST_OPERATOR && LA217_0<=SUBSTITUTION)||LA217_0==NUMBERSETSTAR||(LA217_0>=FIRST_AGGREG && LA217_0<=WINDOW_AGGREG)||(LA217_0>=INT_TYPE && LA217_0<=NULL_TYPE)||(LA217_0>=STAR && LA217_0<=PLUS)||(LA217_0>=BAND && LA217_0<=BXOR)||(LA217_0>=LT && LA217_0<=GE)||(LA217_0>=MINUS && LA217_0<=MOD)) ) {
                    alt217=1;
                }


                switch (alt217) {
            	case 1 :
            	    // EsperEPL2Ast.g:626:35: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_distinctExpressions4506);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt217 >= 1 ) break loop217;
                        EarlyExitException eee =
                            new EarlyExitException(217, input);
                        throw eee;
                }
                cnt217++;
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
    // EsperEPL2Ast.g:629:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR followedByItem followedByItem ( followedByItem )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:630:2: ( ^(f= FOLLOWED_BY_EXPR followedByItem followedByItem ( followedByItem )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt221=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt221=1;
                }
                break;
            case OR_EXPR:
                {
                alt221=2;
                }
                break;
            case AND_EXPR:
                {
                alt221=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 221, 0, input);

                throw nvae;
            }

            switch (alt221) {
                case 1 :
                    // EsperEPL2Ast.g:630:4: ^(f= FOLLOWED_BY_EXPR followedByItem followedByItem ( followedByItem )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4525); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_followedByItem_in_patternOp4527);
                    followedByItem();

                    state._fsp--;

                    pushFollow(FOLLOW_followedByItem_in_patternOp4529);
                    followedByItem();

                    state._fsp--;

                    // EsperEPL2Ast.g:630:56: ( followedByItem )*
                    loop218:
                    do {
                        int alt218=2;
                        int LA218_0 = input.LA(1);

                        if ( (LA218_0==FOLLOWED_BY_ITEM) ) {
                            alt218=1;
                        }


                        switch (alt218) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:630:57: followedByItem
                    	    {
                    	    pushFollow(FOLLOW_followedByItem_in_patternOp4532);
                    	    followedByItem();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop218;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:631:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4548); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4550);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4552);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:631:40: ( exprChoice )*
                    loop219:
                    do {
                        int alt219=2;
                        int LA219_0 = input.LA(1);

                        if ( ((LA219_0>=OR_EXPR && LA219_0<=AND_EXPR)||(LA219_0>=EVERY_EXPR && LA219_0<=EVERY_DISTINCT_EXPR)||LA219_0==FOLLOWED_BY_EXPR||(LA219_0>=PATTERN_FILTER_EXPR && LA219_0<=PATTERN_NOT_EXPR)||(LA219_0>=GUARD_EXPR && LA219_0<=OBSERVER_EXPR)||LA219_0==MATCH_UNTIL_EXPR) ) {
                            alt219=1;
                        }


                        switch (alt219) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:631:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4555);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop219;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:632:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4571); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4573);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4575);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:632:41: ( exprChoice )*
                    loop220:
                    do {
                        int alt220=2;
                        int LA220_0 = input.LA(1);

                        if ( ((LA220_0>=OR_EXPR && LA220_0<=AND_EXPR)||(LA220_0>=EVERY_EXPR && LA220_0<=EVERY_DISTINCT_EXPR)||LA220_0==FOLLOWED_BY_EXPR||(LA220_0>=PATTERN_FILTER_EXPR && LA220_0<=PATTERN_NOT_EXPR)||(LA220_0>=GUARD_EXPR && LA220_0<=OBSERVER_EXPR)||LA220_0==MATCH_UNTIL_EXPR) ) {
                            alt220=1;
                        }


                        switch (alt220) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:632:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4578);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop220;
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
    // EsperEPL2Ast.g:635:1: followedByItem : ^( FOLLOWED_BY_ITEM ( valueExpr )? exprChoice ) ;
    public final void followedByItem() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:636:2: ( ^( FOLLOWED_BY_ITEM ( valueExpr )? exprChoice ) )
            // EsperEPL2Ast.g:636:4: ^( FOLLOWED_BY_ITEM ( valueExpr )? exprChoice )
            {
            match(input,FOLLOWED_BY_ITEM,FOLLOW_FOLLOWED_BY_ITEM_in_followedByItem4599); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:636:24: ( valueExpr )?
            int alt222=2;
            int LA222_0 = input.LA(1);

            if ( ((LA222_0>=IN_SET && LA222_0<=REGEXP)||LA222_0==NOT_EXPR||(LA222_0>=SUM && LA222_0<=AVG)||(LA222_0>=COALESCE && LA222_0<=COUNT)||(LA222_0>=CASE && LA222_0<=CASE2)||(LA222_0>=PREVIOUS && LA222_0<=EXISTS)||(LA222_0>=INSTANCEOF && LA222_0<=CURRENT_TIMESTAMP)||(LA222_0>=EVAL_AND_EXPR && LA222_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA222_0==EVENT_PROP_EXPR||LA222_0==CONCAT||(LA222_0>=LIB_FUNC_CHAIN && LA222_0<=DOT_EXPR)||LA222_0==ARRAY_EXPR||(LA222_0>=NOT_IN_SET && LA222_0<=NOT_REGEXP)||(LA222_0>=IN_RANGE && LA222_0<=SUBSELECT_EXPR)||(LA222_0>=EXISTS_SUBSELECT_EXPR && LA222_0<=NOT_IN_SUBSELECT_EXPR)||LA222_0==SUBSTITUTION||(LA222_0>=FIRST_AGGREG && LA222_0<=WINDOW_AGGREG)||(LA222_0>=INT_TYPE && LA222_0<=NULL_TYPE)||(LA222_0>=STAR && LA222_0<=PLUS)||(LA222_0>=BAND && LA222_0<=BXOR)||(LA222_0>=LT && LA222_0<=GE)||(LA222_0>=MINUS && LA222_0<=MOD)) ) {
                alt222=1;
            }
            switch (alt222) {
                case 1 :
                    // EsperEPL2Ast.g:636:24: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_followedByItem4601);
                    valueExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_exprChoice_in_followedByItem4604);
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
    // EsperEPL2Ast.g:639:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:640:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt224=2;
            int LA224_0 = input.LA(1);

            if ( (LA224_0==PATTERN_FILTER_EXPR) ) {
                alt224=1;
            }
            else if ( (LA224_0==OBSERVER_EXPR) ) {
                alt224=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 224, 0, input);

                throw nvae;
            }
            switch (alt224) {
                case 1 :
                    // EsperEPL2Ast.g:640:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4618);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:641:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4630); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4632); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4634); 
                    // EsperEPL2Ast.g:641:39: ( valueExprWithTime )*
                    loop223:
                    do {
                        int alt223=2;
                        int LA223_0 = input.LA(1);

                        if ( ((LA223_0>=IN_SET && LA223_0<=REGEXP)||LA223_0==NOT_EXPR||(LA223_0>=SUM && LA223_0<=AVG)||(LA223_0>=COALESCE && LA223_0<=COUNT)||(LA223_0>=CASE && LA223_0<=CASE2)||LA223_0==LAST||(LA223_0>=PREVIOUS && LA223_0<=EXISTS)||(LA223_0>=LW && LA223_0<=CURRENT_TIMESTAMP)||(LA223_0>=NUMERIC_PARAM_RANGE && LA223_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA223_0>=EVAL_AND_EXPR && LA223_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA223_0==EVENT_PROP_EXPR||LA223_0==CONCAT||(LA223_0>=LIB_FUNC_CHAIN && LA223_0<=DOT_EXPR)||(LA223_0>=TIME_PERIOD && LA223_0<=ARRAY_EXPR)||(LA223_0>=NOT_IN_SET && LA223_0<=NOT_REGEXP)||(LA223_0>=IN_RANGE && LA223_0<=SUBSELECT_EXPR)||(LA223_0>=EXISTS_SUBSELECT_EXPR && LA223_0<=NOT_IN_SUBSELECT_EXPR)||(LA223_0>=LAST_OPERATOR && LA223_0<=SUBSTITUTION)||LA223_0==NUMBERSETSTAR||(LA223_0>=FIRST_AGGREG && LA223_0<=WINDOW_AGGREG)||(LA223_0>=INT_TYPE && LA223_0<=NULL_TYPE)||(LA223_0>=STAR && LA223_0<=PLUS)||(LA223_0>=BAND && LA223_0<=BXOR)||(LA223_0>=LT && LA223_0<=GE)||(LA223_0>=MINUS && LA223_0<=MOD)) ) {
                            alt223=1;
                        }


                        switch (alt223) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:641:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4636);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop223;
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
    // EsperEPL2Ast.g:644:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:645:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:645:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4656); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:645:29: ( IDENT )?
            int alt225=2;
            int LA225_0 = input.LA(1);

            if ( (LA225_0==IDENT) ) {
                alt225=1;
            }
            switch (alt225) {
                case 1 :
                    // EsperEPL2Ast.g:645:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4658); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4661); 
            // EsperEPL2Ast.g:645:48: ( propertyExpression )?
            int alt226=2;
            int LA226_0 = input.LA(1);

            if ( (LA226_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt226=1;
            }
            switch (alt226) {
                case 1 :
                    // EsperEPL2Ast.g:645:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4663);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:645:68: ( valueExpr )*
            loop227:
            do {
                int alt227=2;
                int LA227_0 = input.LA(1);

                if ( ((LA227_0>=IN_SET && LA227_0<=REGEXP)||LA227_0==NOT_EXPR||(LA227_0>=SUM && LA227_0<=AVG)||(LA227_0>=COALESCE && LA227_0<=COUNT)||(LA227_0>=CASE && LA227_0<=CASE2)||(LA227_0>=PREVIOUS && LA227_0<=EXISTS)||(LA227_0>=INSTANCEOF && LA227_0<=CURRENT_TIMESTAMP)||(LA227_0>=EVAL_AND_EXPR && LA227_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA227_0==EVENT_PROP_EXPR||LA227_0==CONCAT||(LA227_0>=LIB_FUNC_CHAIN && LA227_0<=DOT_EXPR)||LA227_0==ARRAY_EXPR||(LA227_0>=NOT_IN_SET && LA227_0<=NOT_REGEXP)||(LA227_0>=IN_RANGE && LA227_0<=SUBSELECT_EXPR)||(LA227_0>=EXISTS_SUBSELECT_EXPR && LA227_0<=NOT_IN_SUBSELECT_EXPR)||LA227_0==SUBSTITUTION||(LA227_0>=FIRST_AGGREG && LA227_0<=WINDOW_AGGREG)||(LA227_0>=INT_TYPE && LA227_0<=NULL_TYPE)||(LA227_0>=STAR && LA227_0<=PLUS)||(LA227_0>=BAND && LA227_0<=BXOR)||(LA227_0>=LT && LA227_0<=GE)||(LA227_0>=MINUS && LA227_0<=MOD)) ) {
                    alt227=1;
                }


                switch (alt227) {
            	case 1 :
            	    // EsperEPL2Ast.g:645:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4667);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop227;
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


    // $ANTLR start "matchUntilRange"
    // EsperEPL2Ast.g:648:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr ) | ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:649:2: ( ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr ) | ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr ) )
            int alt228=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt228=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt228=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt228=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt228=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 228, 0, input);

                throw nvae;
            }

            switch (alt228) {
                case 1 :
                    // EsperEPL2Ast.g:649:4: ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4685); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4687);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4689);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:650:5: ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4697); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4699);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:651:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4707); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4709);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:652:4: ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4716); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4718);
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
    // EsperEPL2Ast.g:655:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:656:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:656:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4731); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4733);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:656:35: ( valueExpr )*
            loop229:
            do {
                int alt229=2;
                int LA229_0 = input.LA(1);

                if ( ((LA229_0>=IN_SET && LA229_0<=REGEXP)||LA229_0==NOT_EXPR||(LA229_0>=SUM && LA229_0<=AVG)||(LA229_0>=COALESCE && LA229_0<=COUNT)||(LA229_0>=CASE && LA229_0<=CASE2)||(LA229_0>=PREVIOUS && LA229_0<=EXISTS)||(LA229_0>=INSTANCEOF && LA229_0<=CURRENT_TIMESTAMP)||(LA229_0>=EVAL_AND_EXPR && LA229_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA229_0==EVENT_PROP_EXPR||LA229_0==CONCAT||(LA229_0>=LIB_FUNC_CHAIN && LA229_0<=DOT_EXPR)||LA229_0==ARRAY_EXPR||(LA229_0>=NOT_IN_SET && LA229_0<=NOT_REGEXP)||(LA229_0>=IN_RANGE && LA229_0<=SUBSELECT_EXPR)||(LA229_0>=EXISTS_SUBSELECT_EXPR && LA229_0<=NOT_IN_SUBSELECT_EXPR)||LA229_0==SUBSTITUTION||(LA229_0>=FIRST_AGGREG && LA229_0<=WINDOW_AGGREG)||(LA229_0>=INT_TYPE && LA229_0<=NULL_TYPE)||(LA229_0>=STAR && LA229_0<=PLUS)||(LA229_0>=BAND && LA229_0<=BXOR)||(LA229_0>=LT && LA229_0<=GE)||(LA229_0>=MINUS && LA229_0<=MOD)) ) {
                    alt229=1;
                }


                switch (alt229) {
            	case 1 :
            	    // EsperEPL2Ast.g:656:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4736);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop229;
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
    // EsperEPL2Ast.g:659:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:660:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt242=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt242=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt242=2;
                }
                break;
            case LT:
                {
                alt242=3;
                }
                break;
            case LE:
                {
                alt242=4;
                }
                break;
            case GT:
                {
                alt242=5;
                }
                break;
            case GE:
                {
                alt242=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt242=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt242=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt242=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt242=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt242=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt242=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 242, 0, input);

                throw nvae;
            }

            switch (alt242) {
                case 1 :
                    // EsperEPL2Ast.g:660:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4752); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4754);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:661:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4761); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4763);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:662:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4770); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4772);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:663:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4779); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4781);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:664:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4788); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4790);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:665:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4797); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4799);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:666:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4806); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:666:41: ( constant[false] | filterIdentifier )
                    int alt230=2;
                    int LA230_0 = input.LA(1);

                    if ( ((LA230_0>=INT_TYPE && LA230_0<=NULL_TYPE)) ) {
                        alt230=1;
                    }
                    else if ( (LA230_0==EVENT_FILTER_IDENT) ) {
                        alt230=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 230, 0, input);

                        throw nvae;
                    }
                    switch (alt230) {
                        case 1 :
                            // EsperEPL2Ast.g:666:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4815);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:666:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4818);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:666:76: ( constant[false] | filterIdentifier )
                    int alt231=2;
                    int LA231_0 = input.LA(1);

                    if ( ((LA231_0>=INT_TYPE && LA231_0<=NULL_TYPE)) ) {
                        alt231=1;
                    }
                    else if ( (LA231_0==EVENT_FILTER_IDENT) ) {
                        alt231=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 231, 0, input);

                        throw nvae;
                    }
                    switch (alt231) {
                        case 1 :
                            // EsperEPL2Ast.g:666:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4822);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:666:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4825);
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
                    // EsperEPL2Ast.g:667:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4839); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:667:45: ( constant[false] | filterIdentifier )
                    int alt232=2;
                    int LA232_0 = input.LA(1);

                    if ( ((LA232_0>=INT_TYPE && LA232_0<=NULL_TYPE)) ) {
                        alt232=1;
                    }
                    else if ( (LA232_0==EVENT_FILTER_IDENT) ) {
                        alt232=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 232, 0, input);

                        throw nvae;
                    }
                    switch (alt232) {
                        case 1 :
                            // EsperEPL2Ast.g:667:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4848);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:667:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4851);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:667:80: ( constant[false] | filterIdentifier )
                    int alt233=2;
                    int LA233_0 = input.LA(1);

                    if ( ((LA233_0>=INT_TYPE && LA233_0<=NULL_TYPE)) ) {
                        alt233=1;
                    }
                    else if ( (LA233_0==EVENT_FILTER_IDENT) ) {
                        alt233=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 233, 0, input);

                        throw nvae;
                    }
                    switch (alt233) {
                        case 1 :
                            // EsperEPL2Ast.g:667:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4855);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:667:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4858);
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
                    // EsperEPL2Ast.g:668:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4872); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:668:38: ( constant[false] | filterIdentifier )
                    int alt234=2;
                    int LA234_0 = input.LA(1);

                    if ( ((LA234_0>=INT_TYPE && LA234_0<=NULL_TYPE)) ) {
                        alt234=1;
                    }
                    else if ( (LA234_0==EVENT_FILTER_IDENT) ) {
                        alt234=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 234, 0, input);

                        throw nvae;
                    }
                    switch (alt234) {
                        case 1 :
                            // EsperEPL2Ast.g:668:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4881);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:668:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4884);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:668:73: ( constant[false] | filterIdentifier )*
                    loop235:
                    do {
                        int alt235=3;
                        int LA235_0 = input.LA(1);

                        if ( ((LA235_0>=INT_TYPE && LA235_0<=NULL_TYPE)) ) {
                            alt235=1;
                        }
                        else if ( (LA235_0==EVENT_FILTER_IDENT) ) {
                            alt235=2;
                        }


                        switch (alt235) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:668:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4888);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:668:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4891);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop235;
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
                    // EsperEPL2Ast.g:669:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4906); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:669:42: ( constant[false] | filterIdentifier )
                    int alt236=2;
                    int LA236_0 = input.LA(1);

                    if ( ((LA236_0>=INT_TYPE && LA236_0<=NULL_TYPE)) ) {
                        alt236=1;
                    }
                    else if ( (LA236_0==EVENT_FILTER_IDENT) ) {
                        alt236=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 236, 0, input);

                        throw nvae;
                    }
                    switch (alt236) {
                        case 1 :
                            // EsperEPL2Ast.g:669:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4915);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:669:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4918);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:669:77: ( constant[false] | filterIdentifier )*
                    loop237:
                    do {
                        int alt237=3;
                        int LA237_0 = input.LA(1);

                        if ( ((LA237_0>=INT_TYPE && LA237_0<=NULL_TYPE)) ) {
                            alt237=1;
                        }
                        else if ( (LA237_0==EVENT_FILTER_IDENT) ) {
                            alt237=2;
                        }


                        switch (alt237) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:669:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4922);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:669:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4925);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop237;
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
                    // EsperEPL2Ast.g:670:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4940); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:670:27: ( constant[false] | filterIdentifier )
                    int alt238=2;
                    int LA238_0 = input.LA(1);

                    if ( ((LA238_0>=INT_TYPE && LA238_0<=NULL_TYPE)) ) {
                        alt238=1;
                    }
                    else if ( (LA238_0==EVENT_FILTER_IDENT) ) {
                        alt238=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 238, 0, input);

                        throw nvae;
                    }
                    switch (alt238) {
                        case 1 :
                            // EsperEPL2Ast.g:670:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4943);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:670:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4946);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:670:62: ( constant[false] | filterIdentifier )
                    int alt239=2;
                    int LA239_0 = input.LA(1);

                    if ( ((LA239_0>=INT_TYPE && LA239_0<=NULL_TYPE)) ) {
                        alt239=1;
                    }
                    else if ( (LA239_0==EVENT_FILTER_IDENT) ) {
                        alt239=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 239, 0, input);

                        throw nvae;
                    }
                    switch (alt239) {
                        case 1 :
                            // EsperEPL2Ast.g:670:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4950);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:670:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4953);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:671:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4961); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:671:31: ( constant[false] | filterIdentifier )
                    int alt240=2;
                    int LA240_0 = input.LA(1);

                    if ( ((LA240_0>=INT_TYPE && LA240_0<=NULL_TYPE)) ) {
                        alt240=1;
                    }
                    else if ( (LA240_0==EVENT_FILTER_IDENT) ) {
                        alt240=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 240, 0, input);

                        throw nvae;
                    }
                    switch (alt240) {
                        case 1 :
                            // EsperEPL2Ast.g:671:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4964);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:671:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4967);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:671:66: ( constant[false] | filterIdentifier )
                    int alt241=2;
                    int LA241_0 = input.LA(1);

                    if ( ((LA241_0>=INT_TYPE && LA241_0<=NULL_TYPE)) ) {
                        alt241=1;
                    }
                    else if ( (LA241_0==EVENT_FILTER_IDENT) ) {
                        alt241=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 241, 0, input);

                        throw nvae;
                    }
                    switch (alt241) {
                        case 1 :
                            // EsperEPL2Ast.g:671:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4971);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:671:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4974);
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
    // EsperEPL2Ast.g:674:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:675:2: ( constant[false] | filterIdentifier )
            int alt243=2;
            int LA243_0 = input.LA(1);

            if ( ((LA243_0>=INT_TYPE && LA243_0<=NULL_TYPE)) ) {
                alt243=1;
            }
            else if ( (LA243_0==EVENT_FILTER_IDENT) ) {
                alt243=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 243, 0, input);

                throw nvae;
            }
            switch (alt243) {
                case 1 :
                    // EsperEPL2Ast.g:675:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4988);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:676:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4994);
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
    // EsperEPL2Ast.g:678:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:679:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:679:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier5005); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier5007); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier5009);
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
    // EsperEPL2Ast.g:682:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:683:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:683:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr5028); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5030);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:683:44: ( eventPropertyAtomic )*
            loop244:
            do {
                int alt244=2;
                int LA244_0 = input.LA(1);

                if ( ((LA244_0>=EVENT_PROP_SIMPLE && LA244_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt244=1;
                }


                switch (alt244) {
            	case 1 :
            	    // EsperEPL2Ast.g:683:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5033);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop244;
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
    // EsperEPL2Ast.g:686:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:687:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt245=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt245=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt245=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt245=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt245=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt245=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt245=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 245, 0, input);

                throw nvae;
            }

            switch (alt245) {
                case 1 :
                    // EsperEPL2Ast.g:687:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic5052); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5054); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:688:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic5061); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5063); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic5065); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:689:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic5072); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5074); 
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
                    // EsperEPL2Ast.g:690:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5089); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5091); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:691:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5098); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5100); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic5102); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:692:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5109); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5111); 
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
    // EsperEPL2Ast.g:695:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:696:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:696:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod5138); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod5140);
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
    // EsperEPL2Ast.g:699:1: timePeriodDef : ( yearPart ( monthPart )? ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | monthPart ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | weekPart ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:700:2: ( yearPart ( monthPart )? ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | monthPart ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | weekPart ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt274=8;
            switch ( input.LA(1) ) {
            case YEAR_PART:
                {
                alt274=1;
                }
                break;
            case MONTH_PART:
                {
                alt274=2;
                }
                break;
            case WEEK_PART:
                {
                alt274=3;
                }
                break;
            case DAY_PART:
                {
                alt274=4;
                }
                break;
            case HOUR_PART:
                {
                alt274=5;
                }
                break;
            case MINUTE_PART:
                {
                alt274=6;
                }
                break;
            case SECOND_PART:
                {
                alt274=7;
                }
                break;
            case MILLISECOND_PART:
                {
                alt274=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 274, 0, input);

                throw nvae;
            }

            switch (alt274) {
                case 1 :
                    // EsperEPL2Ast.g:700:5: yearPart ( monthPart )? ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_yearPart_in_timePeriodDef5156);
                    yearPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:700:14: ( monthPart )?
                    int alt246=2;
                    int LA246_0 = input.LA(1);

                    if ( (LA246_0==MONTH_PART) ) {
                        alt246=1;
                    }
                    switch (alt246) {
                        case 1 :
                            // EsperEPL2Ast.g:700:15: monthPart
                            {
                            pushFollow(FOLLOW_monthPart_in_timePeriodDef5159);
                            monthPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:700:27: ( weekPart )?
                    int alt247=2;
                    int LA247_0 = input.LA(1);

                    if ( (LA247_0==WEEK_PART) ) {
                        alt247=1;
                    }
                    switch (alt247) {
                        case 1 :
                            // EsperEPL2Ast.g:700:28: weekPart
                            {
                            pushFollow(FOLLOW_weekPart_in_timePeriodDef5164);
                            weekPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:700:39: ( dayPart )?
                    int alt248=2;
                    int LA248_0 = input.LA(1);

                    if ( (LA248_0==DAY_PART) ) {
                        alt248=1;
                    }
                    switch (alt248) {
                        case 1 :
                            // EsperEPL2Ast.g:700:40: dayPart
                            {
                            pushFollow(FOLLOW_dayPart_in_timePeriodDef5169);
                            dayPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:700:50: ( hourPart )?
                    int alt249=2;
                    int LA249_0 = input.LA(1);

                    if ( (LA249_0==HOUR_PART) ) {
                        alt249=1;
                    }
                    switch (alt249) {
                        case 1 :
                            // EsperEPL2Ast.g:700:51: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5174);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:700:62: ( minutePart )?
                    int alt250=2;
                    int LA250_0 = input.LA(1);

                    if ( (LA250_0==MINUTE_PART) ) {
                        alt250=1;
                    }
                    switch (alt250) {
                        case 1 :
                            // EsperEPL2Ast.g:700:63: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5179);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:700:76: ( secondPart )?
                    int alt251=2;
                    int LA251_0 = input.LA(1);

                    if ( (LA251_0==SECOND_PART) ) {
                        alt251=1;
                    }
                    switch (alt251) {
                        case 1 :
                            // EsperEPL2Ast.g:700:77: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5184);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:700:90: ( millisecondPart )?
                    int alt252=2;
                    int LA252_0 = input.LA(1);

                    if ( (LA252_0==MILLISECOND_PART) ) {
                        alt252=1;
                    }
                    switch (alt252) {
                        case 1 :
                            // EsperEPL2Ast.g:700:91: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5189);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:701:5: monthPart ( weekPart )? ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_monthPart_in_timePeriodDef5197);
                    monthPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:701:15: ( weekPart )?
                    int alt253=2;
                    int LA253_0 = input.LA(1);

                    if ( (LA253_0==WEEK_PART) ) {
                        alt253=1;
                    }
                    switch (alt253) {
                        case 1 :
                            // EsperEPL2Ast.g:701:16: weekPart
                            {
                            pushFollow(FOLLOW_weekPart_in_timePeriodDef5200);
                            weekPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:701:27: ( dayPart )?
                    int alt254=2;
                    int LA254_0 = input.LA(1);

                    if ( (LA254_0==DAY_PART) ) {
                        alt254=1;
                    }
                    switch (alt254) {
                        case 1 :
                            // EsperEPL2Ast.g:701:28: dayPart
                            {
                            pushFollow(FOLLOW_dayPart_in_timePeriodDef5205);
                            dayPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:701:38: ( hourPart )?
                    int alt255=2;
                    int LA255_0 = input.LA(1);

                    if ( (LA255_0==HOUR_PART) ) {
                        alt255=1;
                    }
                    switch (alt255) {
                        case 1 :
                            // EsperEPL2Ast.g:701:39: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5210);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:701:50: ( minutePart )?
                    int alt256=2;
                    int LA256_0 = input.LA(1);

                    if ( (LA256_0==MINUTE_PART) ) {
                        alt256=1;
                    }
                    switch (alt256) {
                        case 1 :
                            // EsperEPL2Ast.g:701:51: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5215);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:701:64: ( secondPart )?
                    int alt257=2;
                    int LA257_0 = input.LA(1);

                    if ( (LA257_0==SECOND_PART) ) {
                        alt257=1;
                    }
                    switch (alt257) {
                        case 1 :
                            // EsperEPL2Ast.g:701:65: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5220);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:701:78: ( millisecondPart )?
                    int alt258=2;
                    int LA258_0 = input.LA(1);

                    if ( (LA258_0==MILLISECOND_PART) ) {
                        alt258=1;
                    }
                    switch (alt258) {
                        case 1 :
                            // EsperEPL2Ast.g:701:79: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5225);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:702:5: weekPart ( dayPart )? ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_weekPart_in_timePeriodDef5233);
                    weekPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:702:14: ( dayPart )?
                    int alt259=2;
                    int LA259_0 = input.LA(1);

                    if ( (LA259_0==DAY_PART) ) {
                        alt259=1;
                    }
                    switch (alt259) {
                        case 1 :
                            // EsperEPL2Ast.g:702:15: dayPart
                            {
                            pushFollow(FOLLOW_dayPart_in_timePeriodDef5236);
                            dayPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:702:25: ( hourPart )?
                    int alt260=2;
                    int LA260_0 = input.LA(1);

                    if ( (LA260_0==HOUR_PART) ) {
                        alt260=1;
                    }
                    switch (alt260) {
                        case 1 :
                            // EsperEPL2Ast.g:702:26: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5241);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:702:37: ( minutePart )?
                    int alt261=2;
                    int LA261_0 = input.LA(1);

                    if ( (LA261_0==MINUTE_PART) ) {
                        alt261=1;
                    }
                    switch (alt261) {
                        case 1 :
                            // EsperEPL2Ast.g:702:38: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5246);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:702:51: ( secondPart )?
                    int alt262=2;
                    int LA262_0 = input.LA(1);

                    if ( (LA262_0==SECOND_PART) ) {
                        alt262=1;
                    }
                    switch (alt262) {
                        case 1 :
                            // EsperEPL2Ast.g:702:52: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5251);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:702:65: ( millisecondPart )?
                    int alt263=2;
                    int LA263_0 = input.LA(1);

                    if ( (LA263_0==MILLISECOND_PART) ) {
                        alt263=1;
                    }
                    switch (alt263) {
                        case 1 :
                            // EsperEPL2Ast.g:702:66: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5256);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:703:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef5264);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:703:13: ( hourPart )?
                    int alt264=2;
                    int LA264_0 = input.LA(1);

                    if ( (LA264_0==HOUR_PART) ) {
                        alt264=1;
                    }
                    switch (alt264) {
                        case 1 :
                            // EsperEPL2Ast.g:703:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5267);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:703:25: ( minutePart )?
                    int alt265=2;
                    int LA265_0 = input.LA(1);

                    if ( (LA265_0==MINUTE_PART) ) {
                        alt265=1;
                    }
                    switch (alt265) {
                        case 1 :
                            // EsperEPL2Ast.g:703:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5272);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:703:39: ( secondPart )?
                    int alt266=2;
                    int LA266_0 = input.LA(1);

                    if ( (LA266_0==SECOND_PART) ) {
                        alt266=1;
                    }
                    switch (alt266) {
                        case 1 :
                            // EsperEPL2Ast.g:703:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5277);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:703:53: ( millisecondPart )?
                    int alt267=2;
                    int LA267_0 = input.LA(1);

                    if ( (LA267_0==MILLISECOND_PART) ) {
                        alt267=1;
                    }
                    switch (alt267) {
                        case 1 :
                            // EsperEPL2Ast.g:703:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5282);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:704:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef5289);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:704:13: ( minutePart )?
                    int alt268=2;
                    int LA268_0 = input.LA(1);

                    if ( (LA268_0==MINUTE_PART) ) {
                        alt268=1;
                    }
                    switch (alt268) {
                        case 1 :
                            // EsperEPL2Ast.g:704:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5292);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:704:27: ( secondPart )?
                    int alt269=2;
                    int LA269_0 = input.LA(1);

                    if ( (LA269_0==SECOND_PART) ) {
                        alt269=1;
                    }
                    switch (alt269) {
                        case 1 :
                            // EsperEPL2Ast.g:704:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5297);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:704:41: ( millisecondPart )?
                    int alt270=2;
                    int LA270_0 = input.LA(1);

                    if ( (LA270_0==MILLISECOND_PART) ) {
                        alt270=1;
                    }
                    switch (alt270) {
                        case 1 :
                            // EsperEPL2Ast.g:704:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5302);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:705:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef5309);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:705:15: ( secondPart )?
                    int alt271=2;
                    int LA271_0 = input.LA(1);

                    if ( (LA271_0==SECOND_PART) ) {
                        alt271=1;
                    }
                    switch (alt271) {
                        case 1 :
                            // EsperEPL2Ast.g:705:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5312);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:705:29: ( millisecondPart )?
                    int alt272=2;
                    int LA272_0 = input.LA(1);

                    if ( (LA272_0==MILLISECOND_PART) ) {
                        alt272=1;
                    }
                    switch (alt272) {
                        case 1 :
                            // EsperEPL2Ast.g:705:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5317);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:706:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef5324);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:706:15: ( millisecondPart )?
                    int alt273=2;
                    int LA273_0 = input.LA(1);

                    if ( (LA273_0==MILLISECOND_PART) ) {
                        alt273=1;
                    }
                    switch (alt273) {
                        case 1 :
                            // EsperEPL2Ast.g:706:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5327);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:707:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5334);
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
    // EsperEPL2Ast.g:710:1: yearPart : ^( YEAR_PART valueExpr ) ;
    public final void yearPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:711:2: ( ^( YEAR_PART valueExpr ) )
            // EsperEPL2Ast.g:711:4: ^( YEAR_PART valueExpr )
            {
            match(input,YEAR_PART,FOLLOW_YEAR_PART_in_yearPart5348); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_yearPart5350);
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
    // EsperEPL2Ast.g:714:1: monthPart : ^( MONTH_PART valueExpr ) ;
    public final void monthPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:715:2: ( ^( MONTH_PART valueExpr ) )
            // EsperEPL2Ast.g:715:4: ^( MONTH_PART valueExpr )
            {
            match(input,MONTH_PART,FOLLOW_MONTH_PART_in_monthPart5365); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_monthPart5367);
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
    // EsperEPL2Ast.g:718:1: weekPart : ^( WEEK_PART valueExpr ) ;
    public final void weekPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:719:2: ( ^( WEEK_PART valueExpr ) )
            // EsperEPL2Ast.g:719:4: ^( WEEK_PART valueExpr )
            {
            match(input,WEEK_PART,FOLLOW_WEEK_PART_in_weekPart5382); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_weekPart5384);
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
    // EsperEPL2Ast.g:722:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:723:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:723:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart5399); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart5401);
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
    // EsperEPL2Ast.g:726:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:727:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:727:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart5416); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart5418);
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
    // EsperEPL2Ast.g:730:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:731:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:731:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart5433); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart5435);
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
    // EsperEPL2Ast.g:734:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:735:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:735:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart5450); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart5452);
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
    // EsperEPL2Ast.g:738:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:739:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:739:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart5467); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart5469);
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
    // EsperEPL2Ast.g:742:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:743:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:743:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution5484); 
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
    // EsperEPL2Ast.g:746:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:747:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt275=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt275=1;
                }
                break;
            case LONG_TYPE:
                {
                alt275=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt275=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt275=4;
                }
                break;
            case STRING_TYPE:
                {
                alt275=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt275=6;
                }
                break;
            case NULL_TYPE:
                {
                alt275=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 275, 0, input);

                throw nvae;
            }

            switch (alt275) {
                case 1 :
                    // EsperEPL2Ast.g:747:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant5500); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:748:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant5509); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:749:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant5518); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:750:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant5527); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:751:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant5543); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:752:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant5559); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:753:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant5572); 
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
    // EsperEPL2Ast.g:756:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:757:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L,0x1C00000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L,0x1C00000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L,0x0C00000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L,0x0C00000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000800000000L,0x0401005800000000L,0x0000000000000005L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createIndexExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_createSchemaExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule245 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule249 = new BitSet(new long[]{0x0000000000000002L,0x0040000000000000L});
    public static final BitSet FOLLOW_forExpr_in_eplExpressionRule252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr271 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onStreamExpr_in_onExpr273 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00008F0000000000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr278 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onUpdateExpr_in_onExpr282 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr289 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr299 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onMergeExpr_in_onExpr303 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_STREAM_in_onStreamExpr325 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onStreamExpr328 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onStreamExpr332 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_onStreamExpr335 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_MERGE_EXPR_in_onMergeExpr353 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr355 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000400000C0L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr357 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000400000C0L});
    public static final BitSet FOLLOW_mergeItem_in_onMergeExpr360 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L,0x0000000000000000L,0x00000000400000C0L});
    public static final BitSet FOLLOW_whereClause_in_onMergeExpr363 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_mergeMatched_in_mergeItem379 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mergeUnmatched_in_mergeItem383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MERGE_UPD_in_mergeMatched398 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_mergeMatched400 = new BitSet(new long[]{0x0000000000000008L,0x0000800000020000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_UPDATE_in_mergeMatched403 = new BitSet(new long[]{0x0000000000000008L,0x0000000000020000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_DELETE_in_mergeMatched406 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_onSetAssignment_in_mergeMatched409 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_MERGE_INS_in_mergeUnmatched427 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_selectionList_in_mergeUnmatched429 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0xA0008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_exprCol_in_mergeUnmatched431 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_mergeUnmatched434 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr453 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr455 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000002L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr457 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr460 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr463 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr480 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr482 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr485 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr505 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr507 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr510 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr513 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000300006000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr515 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000300006000000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr518 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000300004000000L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr522 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000200004000000L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr525 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr528 = new BitSet(new long[]{0x0000000000000008L,0x0000200000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr531 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr551 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr553 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr555 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr557 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr594 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr596 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr599 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr603 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr618 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr620 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr622 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000002000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr625 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment640 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_onSetAssignment642 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment645 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom659 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom661 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom664 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr682 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr684 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000480000L,0x0080002000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr687 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000480000L,0x0080002000000000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000480000L,0x0080002000000000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr694 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000480000L,0x0080002000000000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr708 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr711 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr740 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr751 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr771 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr773 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr775 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_indexColList_in_createIndexExpr777 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INDEXCOL_in_indexColList792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_indexCol_in_indexColList794 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_INDEXCOL_in_indexCol809 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_indexCol811 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_indexCol813 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert827 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert829 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList846 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList848 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000001000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList851 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000001000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList870 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList872 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList875 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement890 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement892 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement894 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_LBRACK_in_createColTypeListElement896 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement941 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement945 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement967 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement970 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr1006 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createVariableExpr1008 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr1010 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr1013 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr1033 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1035 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000480000L,0x0080002000000000L,0x0000000000000038L});
    public static final BitSet FOLLOW_variantList_in_createSchemaExpr1038 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_createColTypeList_in_createSchemaExpr1040 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr1051 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1053 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr1064 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1066 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_exprCol_in_createSchemaExpr1068 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIANT_LIST_in_variantList1089 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_variantList1091 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr1109 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x1000000800000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr1115 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr1120 = new BitSet(new long[]{0x0000000000000002L,0x0001200000000000L,0x0BC0300006000000L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr1125 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x0BC0300006000000L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr1132 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x0BC0300004000000L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr1140 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x0BC0200004000000L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr1147 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x0BC0200000000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr1154 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr1161 = new BitSet(new long[]{0x0000000000000002L,0x0000200000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr1168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1185 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr1187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_insertIntoExpr1196 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_exprCol_in_insertIntoExpr1199 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRCOL_in_exprCol1218 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1220 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1223 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause1241 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause1243 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1256 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1273 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1276 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000F4000000000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1279 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000F4000000000L});
    public static final BitSet FOLLOW_FOR_in_forExpr1299 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_forExpr1301 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_forExpr1303 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1322 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1324 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1331 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1337 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1343 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000420000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1349 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1355 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1361 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1379 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1381 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1398 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1400 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1402 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1404 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1406 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1412 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1427 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1429 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1431 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1447 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1449 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1466 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1468 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1470 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1490 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1492 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000180000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1517 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1519 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1537 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1539 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000240000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1554 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1576 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000001A000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1578 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1607 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1609 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000001A000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1613 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1625 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1647 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1649 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1666 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1668 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1687 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1690 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1716 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1718 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1735 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1737 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1740 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1775 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1778 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1782 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1785 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1800 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1802 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1805 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1809 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1812 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1827 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1832 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1839 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1854 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1856 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1859 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1863 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1866 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1887 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1890 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000400000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1894 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000400000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1898 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000400000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1902 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000400000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1906 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1911 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1916 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_streamExpression1920 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1944 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1946 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1949 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000040L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1951 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1955 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1975 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1977 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1996 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1998 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000700L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom2001 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom2004 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom2008 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom2010 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement2030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement2040 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement2042 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2045 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2059 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2061 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2064 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2085 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression2087 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression2106 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2108 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2116 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2137 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression2139 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression2141 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression2144 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2158 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2161 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr2178 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2180 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2182 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr2185 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause2207 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause2209 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause2227 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2229 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2232 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause2250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2252 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2255 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2275 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2277 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2279 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2302 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2304 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2322 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2324 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000F00L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2336 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2338 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2341 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2360 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2371 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2373 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2389 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2391 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2402 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2404 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2420 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2422 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2433 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2435 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2438 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2451 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2453 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2470 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000F00L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2473 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2489 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2492 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000060000F00L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2494 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000060000F00L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2498 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2500 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2504 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2507 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2525 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2527 = new BitSet(new long[]{0x0020000037CC23C0L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2529 = new BitSet(new long[]{0x0020000037CC23C0L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2531 = new BitSet(new long[]{0x0020000037CC23C0L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2533 = new BitSet(new long[]{0x0020000037CC23C0L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2535 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2537 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2554 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2571 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2584 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2586 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2598 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2600 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2622 = new BitSet(new long[]{0x0003800037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2647 = new BitSet(new long[]{0x0000000037CC23C2L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023F83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2656 = new BitSet(new long[]{0x0000000037CC23C2L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2661 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2687 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2689 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2691 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2694 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2708 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2710 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2712 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2715 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2729 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2731 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2733 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2745 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2747 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2749 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2761 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2763 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2765 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023F83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2774 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2779 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2794 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2796 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023F83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2805 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2810 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2823 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2825 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2849 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFuncChain_in_valueExpr2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2900 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2926 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2932 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2939 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotExpr_in_valueExpr2944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2957 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2966 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2973 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2981 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2983 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2985 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime3004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime3009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime3014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime3024 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime3026 = new BitSet(new long[]{0x0000000000000008L,0x2800000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime3037 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime3044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList3057 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList3064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList3070 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3086 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3089 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L,0x0000000200000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3092 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L,0x0000000200000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3095 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L,0x0000000200000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3099 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3102 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3105 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3126 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator3129 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator3132 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator3135 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator3154 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator3157 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator3160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator3163 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3182 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator3185 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator3188 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator3191 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3212 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr3214 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3233 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr3235 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3254 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr3256 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3275 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3277 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3279 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3291 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3293 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3295 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3314 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3316 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3332 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000003000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_selectionList_in_subQueryExpr3335 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3337 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3360 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000400000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3363 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3368 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3372 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3375 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3395 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3398 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3411 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3414 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3434 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3436 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_set_in_inExpr3438 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3444 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30F100007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3447 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30F100007F00L});
    public static final BitSet FOLLOW_set_in_inExpr3451 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3466 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3468 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_set_in_inExpr3470 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3476 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30F100007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3479 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30F100007F00L});
    public static final BitSet FOLLOW_set_in_inExpr3483 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3498 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3500 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_set_in_inExpr3502 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3508 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3510 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_set_in_inExpr3512 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3527 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3529 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000880000000L});
    public static final BitSet FOLLOW_set_in_inExpr3531 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3537 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3539 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_set_in_inExpr3541 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3564 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3566 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3568 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3570 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3581 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3583 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3585 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3588 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3608 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3610 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3612 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3615 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3628 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3630 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3632 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3635 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3656 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3658 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3669 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3671 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3673 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3692 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3695 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3710 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3713 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3717 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3728 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3732 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3736 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3750 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3753 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3757 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3768 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3771 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3775 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3786 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3789 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3793 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3804 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3807 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000600L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3811 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3813 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3825 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3828 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000600L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3832 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3834 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WINDOW_AGGREG_in_builtinFunc3846 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3849 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000600L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3853 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3865 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3867 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3869 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3872 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3887 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3889 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3891 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSTAIL_in_builtinFunc3904 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3906 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3908 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSCOUNT_in_builtinFunc3921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3923 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSWINDOW_in_builtinFunc3935 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3937 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3949 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3953 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3955 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3968 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3970 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3972 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3975 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_TYPEOF_in_builtinFunc3989 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3991 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc4003 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc4005 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc4007 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc4019 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc4021 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc4033 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExpr4050 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExpr4057 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExpr4059 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExpr4061 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_accessValueExpr4067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr4084 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr4087 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr4108 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4110 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4112 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr4124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4126 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4128 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr4140 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4142 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4144 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr4155 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4157 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4159 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr4171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4173 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4175 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr4186 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4188 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4190 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr4201 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4203 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4205 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr4216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4218 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4220 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr4232 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4234 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4236 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4239 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_DOT_EXPR_in_dotExpr4259 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dotExpr4261 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_dotExpr4263 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4283 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libFuncChain4285 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_libOrPropFunction_in_libFuncChain4287 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunctionWithClass4310 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunctionWithClass4314 = new BitSet(new long[]{0x0000400037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunctionWithClass4317 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_libFunctionWithClass4322 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_libOrPropFunction4340 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libOrPropFunction4350 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule4365 = new BitSet(new long[]{0x000000000000D800L,0x8000000000000000L,0x000000000030000CL,0x0404000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule4369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice4383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice4388 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice4398 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4400 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4414 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice4416 = new BitSet(new long[]{0x000000000000D800L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4418 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4432 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4434 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice4448 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4450 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E040007F00L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4453 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4455 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice4457 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_exprChoice4462 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4476 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4478 = new BitSet(new long[]{0x000000000000D800L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4481 = new BitSet(new long[]{0x000000000000D808L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4483 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_distinctExpressions4506 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4525 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_followedByItem_in_patternOp4527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_followedByItem_in_patternOp4529 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_followedByItem_in_patternOp4532 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4548 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4550 = new BitSet(new long[]{0x000000000000D800L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4552 = new BitSet(new long[]{0x000000000000D808L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4555 = new BitSet(new long[]{0x000000000000D808L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4571 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4573 = new BitSet(new long[]{0x000000000000D800L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4575 = new BitSet(new long[]{0x000000000000D808L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4578 = new BitSet(new long[]{0x000000000000D808L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_FOLLOWED_BY_ITEM_in_followedByItem4599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_followedByItem4601 = new BitSet(new long[]{0x000000000000D800L,0x8000000000000000L,0x000000000030000CL,0x0004000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_followedByItem4604 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4630 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4632 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4634 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4636 = new BitSet(new long[]{0x0020000037CC23C8L,0x780000000001F7E0L,0x80008003F0000000L,0xE2000003BB83C036L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4661 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000040L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4663 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4667 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4685 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4687 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4689 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4707 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4709 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4716 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4718 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4731 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4733 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4736 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000001E7E0L,0x80008003F0000000L,0xE00000023B83C026L,0x00EF30E000007F00L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4752 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4754 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4761 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4763 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4770 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4772 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4779 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4781 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4788 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4790 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4797 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4806 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4808 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4815 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4818 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4822 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4825 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4828 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4839 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4841 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4848 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4851 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4855 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4858 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000001100000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4861 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4884 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4888 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4891 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4895 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4906 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4908 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4915 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4918 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4922 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4925 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000001100007F00L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4929 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4940 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4946 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4950 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4953 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4961 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4964 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4967 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000007F00L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4971 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4974 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4988 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier5005 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier5007 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier5009 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr5028 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5030 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x003F000000000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr5033 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x003F000000000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic5052 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5054 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic5061 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5063 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic5065 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic5072 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5074 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic5076 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5089 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5098 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5100 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic5102 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5109 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic5113 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod5138 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod5140 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_yearPart_in_timePeriodDef5156 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003F80L});
    public static final BitSet FOLLOW_monthPart_in_timePeriodDef5159 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003F00L});
    public static final BitSet FOLLOW_weekPart_in_timePeriodDef5164 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003E00L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5169 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003C00L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5174 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003800L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5179 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5184 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5189 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_monthPart_in_timePeriodDef5197 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003F00L});
    public static final BitSet FOLLOW_weekPart_in_timePeriodDef5200 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003E00L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5205 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003C00L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5210 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003800L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5215 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5220 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekPart_in_timePeriodDef5233 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003E00L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5236 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003C00L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5241 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003800L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5246 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5251 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5264 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003C00L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5267 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003800L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5272 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5277 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5289 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003800L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5292 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5297 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5309 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000003000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5312 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5317 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5324 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_YEAR_PART_in_yearPart5348 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_yearPart5350 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MONTH_PART_in_monthPart5365 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_monthPart5367 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEK_PART_in_weekPart5382 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_weekPart5384 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart5399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart5401 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart5416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart5418 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart5433 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart5435 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart5450 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart5452 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart5467 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart5469 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution5484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant5500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant5509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant5518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant5527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant5543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant5559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant5572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}