// $ANTLR 3.2 Sep 23, 2009 12:02:23 EsperEPL2Ast.g 2010-11-18 10:32:24

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "SCHEMA", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PREVIOUSTAIL", "PREVIOUSCOUNT", "PREVIOUSWINDOW", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "INDEX", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "FOR", "WHILE", "USING", "MERGE", "MATCHED", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "EXPRCOL", "CONCAT", "LIB_FUNCTION", "LIB_FUNC_CHAIN", "DOT_EXPR", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_INDEX_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_STREAM", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_UPDATE_EXPR", "ON_MERGE_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_COL_TYPE_LIST", "CREATE_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "WINDOW_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "CREATE_SCHEMA_EXPR", "CREATE_SCHEMA_EXPR_QUAL", "CREATE_SCHEMA_EXPR_INH", "VARIANT_LIST", "MERGE_UPD", "MERGE_INS", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "LPAREN", "RPAREN", "EQUALS", "DOT", "LBRACK", "RBRACK", "STAR", "BOR", "PLUS", "QUESTION", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=177;
    public static final int FLOAT_SUFFIX=338;
    public static final int STAR=281;
    public static final int DOT_EXPR=185;
    public static final int NUMERIC_PARAM_LIST=117;
    public static final int ISTREAM=60;
    public static final int MOD=299;
    public static final int LIB_FUNC_CHAIN=184;
    public static final int OUTERJOIN_EXPR=159;
    public static final int CREATE_COL_TYPE_LIST=235;
    public static final int MERGE_INS=251;
    public static final int BSR=320;
    public static final int LIB_FUNCTION=183;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=98;
    public static final int FULL_OUTERJOIN_EXPR=163;
    public static final int MATCHREC_PATTERN_CONCAT=263;
    public static final int INC=313;
    public static final int LNOT=309;
    public static final int RPAREN=276;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=286;
    public static final int BSR_ASSIGN=321;
    public static final int CAST_EXPR=214;
    public static final int MATCHES=109;
    public static final int USING=113;
    public static final int STREAM_EXPR=158;
    public static final int TIMEPERIOD_SECONDS=95;
    public static final int NOT_EQUAL=291;
    public static final int METADATASQL=68;
    public static final int EVENT_FILTER_PROPERTY_EXPR=126;
    public static final int LAST_AGGREG=242;
    public static final int REGEXP=9;
    public static final int MATCHED=115;
    public static final int FOLLOWED_BY_EXPR=120;
    public static final int FOLLOWED_BY=303;
    public static final int HOUR_PART=190;
    public static final int RBRACK=280;
    public static final int MATCHREC_PATTERN_NESTED=265;
    public static final int MATCH_UNTIL_RANGE_CLOSED=233;
    public static final int GE=295;
    public static final int METHOD_JOIN_EXPR=229;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=125;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=124;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=314;
    public static final int EVENT_FILTER_NOT_IN=136;
    public static final int INSERTINTO_STREAM_NAME=202;
    public static final int NUM_DOUBLE=259;
    public static final int UNARY_MINUS=186;
    public static final int TIMEPERIOD_MILLISEC=96;
    public static final int LCURLY=300;
    public static final int RETAINUNION=64;
    public static final int DBWHERE_CLAUSE=200;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=134;
    public static final int GROUP=44;
    public static final int EMAILAT=329;
    public static final int WS=330;
    public static final int SUBSELECT_GROUP_EXPR=206;
    public static final int ON_SELECT_INSERT_EXPR=224;
    public static final int ESCAPECHAR=304;
    public static final int EXPRCOL=181;
    public static final int SL_COMMENT=331;
    public static final int NULL_TYPE=258;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=231;
    public static final int GT=293;
    public static final int BNOT=310;
    public static final int WHERE_EXPR=145;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=160;
    public static final int LAND=327;
    public static final int NOT_REGEXP=197;
    public static final int MATCH_UNTIL_EXPR=230;
    public static final int EVENT_PROP_EXPR=167;
    public static final int LBRACK=279;
    public static final int VIEW_EXPR=142;
    public static final int MERGE_UPD=250;
    public static final int ANNOTATION=238;
    public static final int LONG_TYPE=253;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=127;
    public static final int MATCHREC_PATTERN=261;
    public static final int ON_MERGE_EXPR=223;
    public static final int TIMEPERIOD_SEC=93;
    public static final int TICKED_STRING_LITERAL=305;
    public static final int ON_SELECT_EXPR=221;
    public static final int MINUTE_PART=191;
    public static final int PATTERN_NOT_EXPR=123;
    public static final int SQL_NE=290;
    public static final int SUM=18;
    public static final int HexDigit=336;
    public static final int UPDATE_EXPR=244;
    public static final int LPAREN=275;
    public static final int IN_SUBSELECT_EXPR=208;
    public static final int AT=85;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=99;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=267;
    public static final int NOT_IN_RANGE=204;
    public static final int OFFSET=103;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int SECOND_PART=192;
    public static final int PREVIOUS=69;
    public static final int PREVIOUSWINDOW=72;
    public static final int MATCH_RECOGNIZE=105;
    public static final int IDENT=274;
    public static final int DATABASE_JOIN_EXPR=144;
    public static final int BXOR=289;
    public static final int PLUS=283;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=87;
    public static final int CREATE_SCHEMA_EXPR=246;
    public static final int EXISTS=74;
    public static final int EVENT_PROP_INDEXED=170;
    public static final int CREATE_INDEX_EXPR=215;
    public static final int TIMEPERIOD_MILLISECOND=97;
    public static final int EVAL_NOTEQUALS_EXPR=151;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=232;
    public static final int CREATE_VARIABLE_EXPR=228;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=268;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=121;
    public static final int RIGHT_OUTERJOIN_EXPR=162;
    public static final int NUMBERSETSTAR=237;
    public static final int LAST_OPERATOR=211;
    public static final int PATTERN_FILTER_EXPR=122;
    public static final int MERGE=114;
    public static final int EVAL_AND_EXPR=148;
    public static final int LEFT_OUTERJOIN_EXPR=161;
    public static final int EPL_EXPR=260;
    public static final int GROUP_BY_EXPR=164;
    public static final int SET=82;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=77;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=168;
    public static final int MINUS=297;
    public static final int SEMI=328;
    public static final int STAR_ASSIGN=316;
    public static final int PREVIOUSCOUNT=71;
    public static final int VARIANT_LIST=249;
    public static final int FIRST_AGGREG=241;
    public static final int COLON=285;
    public static final int EVAL_EQUALS_GROUP_EXPR=152;
    public static final int BAND_ASSIGN=326;
    public static final int PREVIOUSTAIL=70;
    public static final int SCHEMA=62;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=178;
    public static final int NOT_IN_SET=194;
    public static final int VALUE_NULL=101;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=171;
    public static final int SL=322;
    public static final int NOT_IN_SUBSELECT_EXPR=209;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=140;
    public static final int SR=318;
    public static final int RCURLY=301;
    public static final int PLUS_ASSIGN=312;
    public static final int EXISTS_SUBSELECT_EXPR=207;
    public static final int DAY_PART=189;
    public static final int EVENT_FILTER_IN=135;
    public static final int DIV=298;
    public static final int OBJECT_PARAM_ORDERED_EXPR=119;
    public static final int OctalEscape=335;
    public static final int MILLISECOND_PART=193;
    public static final int BETWEEN=7;
    public static final int PRIOR=73;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=102;
    public static final int SELECTION_EXPR=155;
    public static final int LOR=296;
    public static final int CAST=78;
    public static final int LW=76;
    public static final int WILDCARD_SELECT=201;
    public static final int LT=292;
    public static final int EXPONENT=337;
    public static final int PATTERN_INCL_EXPR=143;
    public static final int WHILE=112;
    public static final int ORDER_BY_EXPR=165;
    public static final int BOOL_TYPE=257;
    public static final int ANNOTATION_ARRAY=239;
    public static final int MOD_ASSIGN=317;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=210;
    public static final int COUNT=26;
    public static final int EQUALS=277;
    public static final int RETAININTERSECTION=65;
    public static final int WINDOW_AGGREG=243;
    public static final int DIV_ASSIGN=311;
    public static final int SL_ASSIGN=323;
    public static final int PATTERN=66;
    public static final int SQL=67;
    public static final int FULL=40;
    public static final int WEEKDAY=75;
    public static final int MATCHREC_AFTER_SKIP=266;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=222;
    public static final int ARRAY_EXPR=188;
    public static final int CREATE_COL_TYPE=236;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=100;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=153;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=137;
    public static final int TIMEPERIOD_SECOND=94;
    public static final int COALESCE=22;
    public static final int FLOAT_TYPE=254;
    public static final int SUBSELECT_EXPR=205;
    public static final int ANNOTATION_VALUE=240;
    public static final int CONCAT=182;
    public static final int NUMERIC_PARAM_RANGE=116;
    public static final int CLASS_IDENT=139;
    public static final int MATCHREC_PATTERN_ALTER=264;
    public static final int ON_EXPR=218;
    public static final int CREATE_WINDOW_EXPR=216;
    public static final int PROPERTY_SELECTION_STREAM=129;
    public static final int ON_DELETE_EXPR=220;
    public static final int ON=41;
    public static final int NUM_LONG=306;
    public static final int TIME_PERIOD=187;
    public static final int DOUBLE_TYPE=255;
    public static final int DELETE=80;
    public static final int INT_TYPE=252;
    public static final int MATCHREC_PARTITION=272;
    public static final int EVAL_BITWISE_EXPR=147;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=166;
    public static final int TIMEPERIOD_HOURS=90;
    public static final int VARIABLE=83;
    public static final int SUBSTITUTION=213;
    public static final int UNTIL=84;
    public static final int STRING_TYPE=256;
    public static final int ON_SET_EXPR=227;
    public static final int MATCHREC_DEFINE_ITEM=269;
    public static final int NUM_INT=302;
    public static final int STDDEV=24;
    public static final int CREATE_SCHEMA_EXPR_INH=248;
    public static final int ON_EXPR_FROM=226;
    public static final int NUM_FLOAT=307;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=128;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=333;
    public static final int WEEKDAY_OPERATOR=212;
    public static final int WHERE=16;
    public static final int DEC=315;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=118;
    public static final int BXOR_ASSIGN=324;
    public static final int AFTER_LIMIT_EXPR=176;
    public static final int ORDER=56;
    public static final int SNAPSHOT=81;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=173;
    public static final int EVENT_FILTER_PARAM=132;
    public static final int IRSTREAM=61;
    public static final int UPDATE=104;
    public static final int MAX=20;
    public static final int FOR=111;
    public static final int ON_STREAM=219;
    public static final int DEFINE=107;
    public static final int TIMEPERIOD_DAYS=88;
    public static final int EVENT_FILTER_RANGE=133;
    public static final int INDEX=86;
    public static final int ML_COMMENT=332;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=172;
    public static final int BOR_ASSIGN=325;
    public static final int COMMA=273;
    public static final int WHEN_LIMIT_EXPR=179;
    public static final int PARTITION=108;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=175;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=89;
    public static final int MATCHREC_MEASURE_ITEM=271;
    public static final int BOR=282;
    public static final int EQUAL=308;
    public static final int EVENT_FILTER_NOT_BETWEEN=138;
    public static final int IN_RANGE=203;
    public static final int DOT=278;
    public static final int CURRENT_TIMESTAMP=79;
    public static final int MATCHREC_MEASURES=270;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=130;
    public static final int INSERTINTO_EXPR=180;
    public static final int HAVING_EXPR=146;
    public static final int UNIDIRECTIONAL=63;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=234;
    public static final int EVAL_EQUALS_EXPR=150;
    public static final int TIMEPERIOD_MINUTES=92;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=196;
    public static final int EVENT_LIMIT_EXPR=174;
    public static final int TIMEPERIOD_MINUTE=91;
    public static final int NOT_BETWEEN=195;
    public static final int EVAL_OR_EXPR=149;
    public static final int ON_SELECT_INSERT_OUTPUT=225;
    public static final int AFTER=110;
    public static final int MEASURES=106;
    public static final int MATCHREC_PATTERN_ATOM=262;
    public static final int BAND=288;
    public static final int QUOTED_STRING_LITERAL=287;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=284;
    public static final int OBSERVER_EXPR=141;
    public static final int EVENT_FILTER_IDENT=131;
    public static final int CREATE_SCHEMA_EXPR_QUAL=247;
    public static final int EVENT_PROP_MAPPED=169;
    public static final int UnicodeEscape=334;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=198;
    public static final int SELECTION_ELEMENT_EXPR=156;
    public static final int CREATE_WINDOW_SELECT_EXPR=217;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=245;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=157;
    public static final int SR_ASSIGN=319;
    public static final int DBFROM_CLAUSE=199;
    public static final int LE=294;
    public static final int EVAL_IDENT=154;

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
    // EsperEPL2Ast.g:93:1: onMergeExpr : ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeMatched )* ( mergeUnmatched )* ( whereClause[true] )? ) ;
    public final void onMergeExpr() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:94:2: ( ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeMatched )* ( mergeUnmatched )* ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:94:4: ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeMatched )* ( mergeUnmatched )* ( whereClause[true] )? )
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

            // EsperEPL2Ast.g:94:35: ( mergeMatched )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==MERGE_UPD) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // EsperEPL2Ast.g:94:35: mergeMatched
            	    {
            	    pushFollow(FOLLOW_mergeMatched_in_onMergeExpr360);
            	    mergeMatched();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);

            // EsperEPL2Ast.g:94:49: ( mergeUnmatched )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0==MERGE_INS) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // EsperEPL2Ast.g:94:49: mergeUnmatched
            	    {
            	    pushFollow(FOLLOW_mergeUnmatched_in_onMergeExpr363);
            	    mergeUnmatched();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);

            // EsperEPL2Ast.g:94:65: ( whereClause[true] )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==WHERE_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:94:65: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onMergeExpr366);
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


    // $ANTLR start "mergeMatched"
    // EsperEPL2Ast.g:97:1: mergeMatched : ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* ) ;
    public final void mergeMatched() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:98:2: ( ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* ) )
            // EsperEPL2Ast.g:98:4: ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* )
            {
            m=(CommonTree)match(input,MERGE_UPD,FOLLOW_MERGE_UPD_in_mergeMatched383); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:98:18: ( valueExpr )?
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>=IN_SET && LA18_0<=REGEXP)||LA18_0==NOT_EXPR||(LA18_0>=SUM && LA18_0<=AVG)||(LA18_0>=COALESCE && LA18_0<=COUNT)||(LA18_0>=CASE && LA18_0<=CASE2)||(LA18_0>=PREVIOUS && LA18_0<=EXISTS)||(LA18_0>=INSTANCEOF && LA18_0<=CURRENT_TIMESTAMP)||(LA18_0>=EVAL_AND_EXPR && LA18_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA18_0==EVENT_PROP_EXPR||LA18_0==CONCAT||(LA18_0>=LIB_FUNC_CHAIN && LA18_0<=DOT_EXPR)||LA18_0==ARRAY_EXPR||(LA18_0>=NOT_IN_SET && LA18_0<=NOT_REGEXP)||(LA18_0>=IN_RANGE && LA18_0<=SUBSELECT_EXPR)||(LA18_0>=EXISTS_SUBSELECT_EXPR && LA18_0<=NOT_IN_SUBSELECT_EXPR)||LA18_0==SUBSTITUTION||(LA18_0>=FIRST_AGGREG && LA18_0<=WINDOW_AGGREG)||(LA18_0>=INT_TYPE && LA18_0<=NULL_TYPE)||(LA18_0>=STAR && LA18_0<=PLUS)||(LA18_0>=BAND && LA18_0<=BXOR)||(LA18_0>=LT && LA18_0<=GE)||(LA18_0>=MINUS && LA18_0<=MOD)) ) {
                    alt18=1;
                }
                switch (alt18) {
                    case 1 :
                        // EsperEPL2Ast.g:98:18: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_mergeMatched385);
                        valueExpr();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:98:29: ( UPDATE )?
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==UPDATE) ) {
                    alt19=1;
                }
                switch (alt19) {
                    case 1 :
                        // EsperEPL2Ast.g:98:29: UPDATE
                        {
                        match(input,UPDATE,FOLLOW_UPDATE_in_mergeMatched388); 

                        }
                        break;

                }

                // EsperEPL2Ast.g:98:37: ( DELETE )?
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==DELETE) ) {
                    alt20=1;
                }
                switch (alt20) {
                    case 1 :
                        // EsperEPL2Ast.g:98:37: DELETE
                        {
                        match(input,DELETE,FOLLOW_DELETE_in_mergeMatched391); 

                        }
                        break;

                }

                // EsperEPL2Ast.g:98:45: ( onSetAssignment )*
                loop21:
                do {
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==ON_SET_EXPR_ITEM) ) {
                        alt21=1;
                    }


                    switch (alt21) {
                	case 1 :
                	    // EsperEPL2Ast.g:98:45: onSetAssignment
                	    {
                	    pushFollow(FOLLOW_onSetAssignment_in_mergeMatched394);
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
    // EsperEPL2Ast.g:101:1: mergeUnmatched : ^(um= MERGE_INS selectionList ( exprCol )? ( valueExpr )? ) ;
    public final void mergeUnmatched() throws RecognitionException {
        CommonTree um=null;

        try {
            // EsperEPL2Ast.g:102:2: ( ^(um= MERGE_INS selectionList ( exprCol )? ( valueExpr )? ) )
            // EsperEPL2Ast.g:102:4: ^(um= MERGE_INS selectionList ( exprCol )? ( valueExpr )? )
            {
            um=(CommonTree)match(input,MERGE_INS,FOLLOW_MERGE_INS_in_mergeUnmatched412); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_selectionList_in_mergeUnmatched414);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:102:33: ( exprCol )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==EXPRCOL) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:102:33: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_mergeUnmatched416);
                    exprCol();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:42: ( valueExpr )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=IN_SET && LA23_0<=REGEXP)||LA23_0==NOT_EXPR||(LA23_0>=SUM && LA23_0<=AVG)||(LA23_0>=COALESCE && LA23_0<=COUNT)||(LA23_0>=CASE && LA23_0<=CASE2)||(LA23_0>=PREVIOUS && LA23_0<=EXISTS)||(LA23_0>=INSTANCEOF && LA23_0<=CURRENT_TIMESTAMP)||(LA23_0>=EVAL_AND_EXPR && LA23_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA23_0==EVENT_PROP_EXPR||LA23_0==CONCAT||(LA23_0>=LIB_FUNC_CHAIN && LA23_0<=DOT_EXPR)||LA23_0==ARRAY_EXPR||(LA23_0>=NOT_IN_SET && LA23_0<=NOT_REGEXP)||(LA23_0>=IN_RANGE && LA23_0<=SUBSELECT_EXPR)||(LA23_0>=EXISTS_SUBSELECT_EXPR && LA23_0<=NOT_IN_SUBSELECT_EXPR)||LA23_0==SUBSTITUTION||(LA23_0>=FIRST_AGGREG && LA23_0<=WINDOW_AGGREG)||(LA23_0>=INT_TYPE && LA23_0<=NULL_TYPE)||(LA23_0>=STAR && LA23_0<=PLUS)||(LA23_0>=BAND && LA23_0<=BXOR)||(LA23_0>=LT && LA23_0<=GE)||(LA23_0>=MINUS && LA23_0<=MOD)) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:102:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_mergeUnmatched419);
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
    // EsperEPL2Ast.g:105:1: updateExpr : ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void updateExpr() throws RecognitionException {
        CommonTree u=null;

        try {
            // EsperEPL2Ast.g:106:2: ( ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:106:4: ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? )
            {
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr438); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr440); 
            // EsperEPL2Ast.g:106:32: ( IDENT )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==IDENT) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:106:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr442); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:106:39: ( onSetAssignment )+
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
            	    // EsperEPL2Ast.g:106:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr445);
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

            // EsperEPL2Ast.g:106:56: ( whereClause[false] )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==WHERE_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:106:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr448);
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
    // EsperEPL2Ast.g:109:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:110:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:110:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr465); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr467);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:110:32: ( whereClause[true] )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==WHERE_EXPR) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:110:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr470);
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
    // EsperEPL2Ast.g:113:1: onSelectExpr : ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:114:2: ( ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) )
            // EsperEPL2Ast.g:114:4: ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? )
            {
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr490); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:114:23: ( insertIntoExpr )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==INSERTINTO_EXPR) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:114:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr492);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:39: ( DISTINCT )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==DISTINCT) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:114:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr495); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr498);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:114:63: ( onExprFrom )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ON_EXPR_FROM) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:114:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr500);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:75: ( whereClause[true] )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==WHERE_EXPR) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:114:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr503);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:94: ( groupByClause )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==GROUP_BY_EXPR) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:114:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr507);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:109: ( havingClause )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==HAVING_EXPR) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:114:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr510);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:123: ( orderByClause )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==ORDER_BY_EXPR) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:114:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr513);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:138: ( rowLimitClause )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==ROW_LIMIT_EXPR) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // EsperEPL2Ast.g:114:138: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr516);
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
    // EsperEPL2Ast.g:117:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:118:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:118:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr536); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr538);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr540);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:118:78: ( whereClause[true] )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==WHERE_EXPR) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:118:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr542);
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
    // EsperEPL2Ast.g:121:1: onSelectInsertOutput : ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) ;
    public final void onSelectInsertOutput() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:122:2: ( ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) )
            // EsperEPL2Ast.g:122:4: ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) )
            {
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput559); 

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
    // EsperEPL2Ast.g:125:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:126:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:126:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr579); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr581);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:126:34: ( onSetAssignment )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==ON_SET_EXPR_ITEM) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // EsperEPL2Ast.g:126:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr584);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop37;
                }
            } while (true);

            // EsperEPL2Ast.g:126:53: ( whereClause[false] )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WHERE_EXPR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:126:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr588);
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
    // EsperEPL2Ast.g:129:1: onUpdateExpr : ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void onUpdateExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:130:2: ( ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:130:4: ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? )
            {
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr603); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr605);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:130:32: ( onSetAssignment )+
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
            	    // EsperEPL2Ast.g:130:32: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr607);
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

            // EsperEPL2Ast.g:130:49: ( whereClause[false] )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==WHERE_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:130:49: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr610);
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
    // EsperEPL2Ast.g:133:1: onSetAssignment : ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:134:2: ( ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) )
            // EsperEPL2Ast.g:134:4: ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr )
            {
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment625); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_onSetAssignment627);
            eventPropertyExpr(false);

            state._fsp--;

            pushFollow(FOLLOW_valueExpr_in_onSetAssignment630);
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
    // EsperEPL2Ast.g:137:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:138:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:138:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom644); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom646); 
            // EsperEPL2Ast.g:138:25: ( IDENT )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==IDENT) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:138:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom649); 

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
    // EsperEPL2Ast.g:141:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:142:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:142:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr667); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr669); 
            // EsperEPL2Ast.g:142:33: ( viewListExpr )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==VIEW_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:142:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr672);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:142:49: ( RETAINUNION )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==RETAINUNION) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:142:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr676); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:142:62: ( RETAININTERSECTION )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==RETAININTERSECTION) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:142:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr679); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:143:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
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
                    // EsperEPL2Ast.g:144:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:144:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:144:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:144:6: ( createSelectionList )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // EsperEPL2Ast.g:144:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr693);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr696); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:146:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:146:12: ( createColTypeList )
                    // EsperEPL2Ast.g:146:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr725);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:148:4: ( createWindowExprInsert )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==INSERT) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:148:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr736);
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
    // EsperEPL2Ast.g:152:1: createIndexExpr : ^(i= CREATE_INDEX_EXPR IDENT IDENT exprCol ) ;
    public final void createIndexExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:153:2: ( ^(i= CREATE_INDEX_EXPR IDENT IDENT exprCol ) )
            // EsperEPL2Ast.g:153:4: ^(i= CREATE_INDEX_EXPR IDENT IDENT exprCol )
            {
            i=(CommonTree)match(input,CREATE_INDEX_EXPR,FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr756); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr758); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr760); 
            pushFollow(FOLLOW_exprCol_in_createIndexExpr762);
            exprCol();

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


    // $ANTLR start "createWindowExprInsert"
    // EsperEPL2Ast.g:156:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:157:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:157:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert777); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:157:13: ( valueExpr )?
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=IN_SET && LA48_0<=REGEXP)||LA48_0==NOT_EXPR||(LA48_0>=SUM && LA48_0<=AVG)||(LA48_0>=COALESCE && LA48_0<=COUNT)||(LA48_0>=CASE && LA48_0<=CASE2)||(LA48_0>=PREVIOUS && LA48_0<=EXISTS)||(LA48_0>=INSTANCEOF && LA48_0<=CURRENT_TIMESTAMP)||(LA48_0>=EVAL_AND_EXPR && LA48_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA48_0==EVENT_PROP_EXPR||LA48_0==CONCAT||(LA48_0>=LIB_FUNC_CHAIN && LA48_0<=DOT_EXPR)||LA48_0==ARRAY_EXPR||(LA48_0>=NOT_IN_SET && LA48_0<=NOT_REGEXP)||(LA48_0>=IN_RANGE && LA48_0<=SUBSELECT_EXPR)||(LA48_0>=EXISTS_SUBSELECT_EXPR && LA48_0<=NOT_IN_SUBSELECT_EXPR)||LA48_0==SUBSTITUTION||(LA48_0>=FIRST_AGGREG && LA48_0<=WINDOW_AGGREG)||(LA48_0>=INT_TYPE && LA48_0<=NULL_TYPE)||(LA48_0>=STAR && LA48_0<=PLUS)||(LA48_0>=BAND && LA48_0<=BXOR)||(LA48_0>=LT && LA48_0<=GE)||(LA48_0>=MINUS && LA48_0<=MOD)) ) {
                    alt48=1;
                }
                switch (alt48) {
                    case 1 :
                        // EsperEPL2Ast.g:157:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert779);
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
    // EsperEPL2Ast.g:160:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:161:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:161:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList796); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList798);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:161:61: ( createSelectionListElement )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==SELECTION_ELEMENT_EXPR||LA49_0==WILDCARD_SELECT) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // EsperEPL2Ast.g:161:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList801);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop49;
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
    // EsperEPL2Ast.g:164:1: createColTypeList : ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:165:2: ( ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:165:4: ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_COL_TYPE_LIST,FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList820); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList822);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:165:52: ( createColTypeListElement )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==CREATE_COL_TYPE) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // EsperEPL2Ast.g:165:53: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList825);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop50;
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
    // EsperEPL2Ast.g:168:1: createColTypeListElement : ^( CREATE_COL_TYPE IDENT CLASS_IDENT ( LBRACK )? ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:169:2: ( ^( CREATE_COL_TYPE IDENT CLASS_IDENT ( LBRACK )? ) )
            // EsperEPL2Ast.g:169:4: ^( CREATE_COL_TYPE IDENT CLASS_IDENT ( LBRACK )? )
            {
            match(input,CREATE_COL_TYPE,FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement840); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement842); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement844); 
            // EsperEPL2Ast.g:169:40: ( LBRACK )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==LBRACK) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:169:40: LBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_createColTypeListElement846); 

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
    // EsperEPL2Ast.g:172:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:173:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==WILDCARD_SELECT) ) {
                alt54=1;
            }
            else if ( (LA54_0==SELECTION_ELEMENT_EXPR) ) {
                alt54=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 54, 0, input);

                throw nvae;
            }
            switch (alt54) {
                case 1 :
                    // EsperEPL2Ast.g:173:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement861); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:174:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement871); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:174:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==EVENT_PROP_EXPR) ) {
                        alt53=1;
                    }
                    else if ( ((LA53_0>=INT_TYPE && LA53_0<=NULL_TYPE)) ) {
                        alt53=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 53, 0, input);

                        throw nvae;
                    }
                    switch (alt53) {
                        case 1 :
                            // EsperEPL2Ast.g:175:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:175:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:175:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement891);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:175:41: ( IDENT )?
                            int alt52=2;
                            int LA52_0 = input.LA(1);

                            if ( (LA52_0==IDENT) ) {
                                alt52=1;
                            }
                            switch (alt52) {
                                case 1 :
                                    // EsperEPL2Ast.g:175:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement895); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:176:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:176:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:176:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement917);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement920); 

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
    // EsperEPL2Ast.g:180:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:181:2: ( ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:181:4: ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr956); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createVariableExpr958); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr960); 
            // EsperEPL2Ast.g:181:47: ( valueExpr )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=IN_SET && LA55_0<=REGEXP)||LA55_0==NOT_EXPR||(LA55_0>=SUM && LA55_0<=AVG)||(LA55_0>=COALESCE && LA55_0<=COUNT)||(LA55_0>=CASE && LA55_0<=CASE2)||(LA55_0>=PREVIOUS && LA55_0<=EXISTS)||(LA55_0>=INSTANCEOF && LA55_0<=CURRENT_TIMESTAMP)||(LA55_0>=EVAL_AND_EXPR && LA55_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA55_0==EVENT_PROP_EXPR||LA55_0==CONCAT||(LA55_0>=LIB_FUNC_CHAIN && LA55_0<=DOT_EXPR)||LA55_0==ARRAY_EXPR||(LA55_0>=NOT_IN_SET && LA55_0<=NOT_REGEXP)||(LA55_0>=IN_RANGE && LA55_0<=SUBSELECT_EXPR)||(LA55_0>=EXISTS_SUBSELECT_EXPR && LA55_0<=NOT_IN_SUBSELECT_EXPR)||LA55_0==SUBSTITUTION||(LA55_0>=FIRST_AGGREG && LA55_0<=WINDOW_AGGREG)||(LA55_0>=INT_TYPE && LA55_0<=NULL_TYPE)||(LA55_0>=STAR && LA55_0<=PLUS)||(LA55_0>=BAND && LA55_0<=BXOR)||(LA55_0>=LT && LA55_0<=GE)||(LA55_0>=MINUS && LA55_0<=MOD)) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // EsperEPL2Ast.g:181:48: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr963);
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
    // EsperEPL2Ast.g:184:1: createSchemaExpr : ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? ) ;
    public final void createSchemaExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:185:2: ( ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? ) )
            // EsperEPL2Ast.g:185:4: ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? )
            {
            s=(CommonTree)match(input,CREATE_SCHEMA_EXPR,FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr983); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr985); 
            // EsperEPL2Ast.g:185:33: ( variantList | ( createColTypeList )? )
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==VARIANT_LIST) ) {
                alt57=1;
            }
            else if ( (LA57_0==UP||LA57_0==CREATE_COL_TYPE_LIST||(LA57_0>=CREATE_SCHEMA_EXPR_QUAL && LA57_0<=CREATE_SCHEMA_EXPR_INH)) ) {
                alt57=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:185:34: variantList
                    {
                    pushFollow(FOLLOW_variantList_in_createSchemaExpr988);
                    variantList();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:185:46: ( createColTypeList )?
                    {
                    // EsperEPL2Ast.g:185:46: ( createColTypeList )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==CREATE_COL_TYPE_LIST) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // EsperEPL2Ast.g:185:46: createColTypeList
                            {
                            pushFollow(FOLLOW_createColTypeList_in_createSchemaExpr990);
                            createColTypeList();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:186:5: ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==CREATE_SCHEMA_EXPR_QUAL) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // EsperEPL2Ast.g:186:6: ^( CREATE_SCHEMA_EXPR_QUAL IDENT )
                    {
                    match(input,CREATE_SCHEMA_EXPR_QUAL,FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr1001); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1003); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:187:5: ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==CREATE_SCHEMA_EXPR_INH) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:187:6: ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol )
                    {
                    match(input,CREATE_SCHEMA_EXPR_INH,FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr1014); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1016); 
                    pushFollow(FOLLOW_exprCol_in_createSchemaExpr1018);
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
    // EsperEPL2Ast.g:190:1: variantList : ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) ;
    public final void variantList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:191:2: ( ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) )
            // EsperEPL2Ast.g:191:4: ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ )
            {
            match(input,VARIANT_LIST,FOLLOW_VARIANT_LIST_in_variantList1039); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:191:19: ( STAR | CLASS_IDENT )+
            int cnt60=0;
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==CLASS_IDENT||LA60_0==STAR) ) {
                    alt60=1;
                }


                switch (alt60) {
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
            	    if ( cnt60 >= 1 ) break loop60;
                        EarlyExitException eee =
                            new EarlyExitException(60, input);
                        throw eee;
                }
                cnt60++;
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
    // EsperEPL2Ast.g:194:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:195:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:195:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:195:4: ( insertIntoExpr )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==INSERTINTO_EXPR) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // EsperEPL2Ast.g:195:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr1059);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr1065);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr1070);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:198:3: ( matchRecogClause )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==MATCH_RECOGNIZE) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:198:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr1075);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:199:3: ( whereClause[true] )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==WHERE_EXPR) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:199:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr1082);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:200:3: ( groupByClause )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==GROUP_BY_EXPR) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:200:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr1090);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:201:3: ( havingClause )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==HAVING_EXPR) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:201:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr1097);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:202:3: ( outputLimitExpr )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( ((LA66_0>=EVENT_LIMIT_EXPR && LA66_0<=CRONTAB_LIMIT_EXPR)||LA66_0==WHEN_LIMIT_EXPR) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:202:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr1104);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:203:3: ( orderByClause )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==ORDER_BY_EXPR) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:203:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr1111);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:204:3: ( rowLimitClause )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==ROW_LIMIT_EXPR) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:204:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr1118);
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
    // EsperEPL2Ast.g:207:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( exprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:208:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( exprCol )? ) )
            // EsperEPL2Ast.g:208:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( exprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1135); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:208:24: ( ISTREAM | RSTREAM )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=RSTREAM && LA69_0<=ISTREAM)) ) {
                alt69=1;
            }
            switch (alt69) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr1146); 
            // EsperEPL2Ast.g:208:51: ( exprCol )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==EXPRCOL) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:208:52: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_insertIntoExpr1149);
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
    // EsperEPL2Ast.g:211:1: exprCol : ^( EXPRCOL IDENT ( IDENT )* ) ;
    public final void exprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:212:2: ( ^( EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:212:4: ^( EXPRCOL IDENT ( IDENT )* )
            {
            match(input,EXPRCOL,FOLLOW_EXPRCOL_in_exprCol1168); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_exprCol1170); 
            // EsperEPL2Ast.g:212:20: ( IDENT )*
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==IDENT) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // EsperEPL2Ast.g:212:21: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_exprCol1173); 

            	    }
            	    break;

            	default :
            	    break loop71;
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
    // EsperEPL2Ast.g:215:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:216:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) )
            // EsperEPL2Ast.g:216:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause1191); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:216:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt72=2;
            int LA72_0 = input.LA(1);

            if ( ((LA72_0>=RSTREAM && LA72_0<=IRSTREAM)) ) {
                alt72=1;
            }
            switch (alt72) {
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

            // EsperEPL2Ast.g:216:55: ( DISTINCT )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==DISTINCT) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:216:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1206); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1209);
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
    // EsperEPL2Ast.g:219:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:220:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:220:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause1223);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:220:21: ( streamExpression ( outerJoin )* )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==STREAM_EXPR) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // EsperEPL2Ast.g:220:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1226);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:220:39: ( outerJoin )*
            	    loop74:
            	    do {
            	        int alt74=2;
            	        int LA74_0 = input.LA(1);

            	        if ( ((LA74_0>=INNERJOIN_EXPR && LA74_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt74=1;
            	        }


            	        switch (alt74) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:220:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1229);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop74;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop75;
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
    // EsperEPL2Ast.g:223:1: forExpr : ^(f= FOR IDENT ( valueExpr )* ) ;
    public final void forExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:224:2: ( ^(f= FOR IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:224:4: ^(f= FOR IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,FOR,FOLLOW_FOR_in_forExpr1249); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_forExpr1251); 
            // EsperEPL2Ast.g:224:18: ( valueExpr )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( ((LA76_0>=IN_SET && LA76_0<=REGEXP)||LA76_0==NOT_EXPR||(LA76_0>=SUM && LA76_0<=AVG)||(LA76_0>=COALESCE && LA76_0<=COUNT)||(LA76_0>=CASE && LA76_0<=CASE2)||(LA76_0>=PREVIOUS && LA76_0<=EXISTS)||(LA76_0>=INSTANCEOF && LA76_0<=CURRENT_TIMESTAMP)||(LA76_0>=EVAL_AND_EXPR && LA76_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA76_0==EVENT_PROP_EXPR||LA76_0==CONCAT||(LA76_0>=LIB_FUNC_CHAIN && LA76_0<=DOT_EXPR)||LA76_0==ARRAY_EXPR||(LA76_0>=NOT_IN_SET && LA76_0<=NOT_REGEXP)||(LA76_0>=IN_RANGE && LA76_0<=SUBSELECT_EXPR)||(LA76_0>=EXISTS_SUBSELECT_EXPR && LA76_0<=NOT_IN_SUBSELECT_EXPR)||LA76_0==SUBSTITUTION||(LA76_0>=FIRST_AGGREG && LA76_0<=WINDOW_AGGREG)||(LA76_0>=INT_TYPE && LA76_0<=NULL_TYPE)||(LA76_0>=STAR && LA76_0<=PLUS)||(LA76_0>=BAND && LA76_0<=BXOR)||(LA76_0>=LT && LA76_0<=GE)||(LA76_0>=MINUS && LA76_0<=MOD)) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // EsperEPL2Ast.g:224:18: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_forExpr1253);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop76;
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
    // EsperEPL2Ast.g:227:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:228:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:228:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1272); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:228:24: ( matchRecogPartitionBy )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==MATCHREC_PARTITION) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:228:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1274);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1281);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:230:4: ( ALL )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==ALL) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:230:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1287); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:231:4: ( matchRecogMatchesAfterSkip )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==MATCHREC_AFTER_SKIP) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:231:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1293);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1299);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:233:4: ( matchRecogMatchesInterval )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==MATCHREC_INTERVAL) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:233:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1305);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1311);
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
    // EsperEPL2Ast.g:237:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:238:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:238:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1329); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:238:27: ( valueExpr )+
            int cnt81=0;
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( ((LA81_0>=IN_SET && LA81_0<=REGEXP)||LA81_0==NOT_EXPR||(LA81_0>=SUM && LA81_0<=AVG)||(LA81_0>=COALESCE && LA81_0<=COUNT)||(LA81_0>=CASE && LA81_0<=CASE2)||(LA81_0>=PREVIOUS && LA81_0<=EXISTS)||(LA81_0>=INSTANCEOF && LA81_0<=CURRENT_TIMESTAMP)||(LA81_0>=EVAL_AND_EXPR && LA81_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA81_0==EVENT_PROP_EXPR||LA81_0==CONCAT||(LA81_0>=LIB_FUNC_CHAIN && LA81_0<=DOT_EXPR)||LA81_0==ARRAY_EXPR||(LA81_0>=NOT_IN_SET && LA81_0<=NOT_REGEXP)||(LA81_0>=IN_RANGE && LA81_0<=SUBSELECT_EXPR)||(LA81_0>=EXISTS_SUBSELECT_EXPR && LA81_0<=NOT_IN_SUBSELECT_EXPR)||LA81_0==SUBSTITUTION||(LA81_0>=FIRST_AGGREG && LA81_0<=WINDOW_AGGREG)||(LA81_0>=INT_TYPE && LA81_0<=NULL_TYPE)||(LA81_0>=STAR && LA81_0<=PLUS)||(LA81_0>=BAND && LA81_0<=BXOR)||(LA81_0>=LT && LA81_0<=GE)||(LA81_0>=MINUS && LA81_0<=MOD)) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // EsperEPL2Ast.g:238:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1331);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt81 >= 1 ) break loop81;
                        EarlyExitException eee =
                            new EarlyExitException(81, input);
                        throw eee;
                }
                cnt81++;
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
    // EsperEPL2Ast.g:241:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:242:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) )
            // EsperEPL2Ast.g:242:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1348); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1350); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1352); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1354); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1362); 

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
    // EsperEPL2Ast.g:245:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:246:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:246:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1377); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1379); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1381);
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
    // EsperEPL2Ast.g:249:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:250:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:250:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1397); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:250:26: ( matchRecogMeasureListElement )*
                loop82:
                do {
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==MATCHREC_MEASURE_ITEM) ) {
                        alt82=1;
                    }


                    switch (alt82) {
                	case 1 :
                	    // EsperEPL2Ast.g:250:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1399);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop82;
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
    // EsperEPL2Ast.g:253:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:254:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:254:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1416); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1418);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:254:40: ( IDENT )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==IDENT) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // EsperEPL2Ast.g:254:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1420); 

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
    // EsperEPL2Ast.g:257:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:258:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:258:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1440); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:258:25: ( matchRecogPatternAlteration )+
            int cnt84=0;
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( ((LA84_0>=MATCHREC_PATTERN_CONCAT && LA84_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // EsperEPL2Ast.g:258:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1442);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt84 >= 1 ) break loop84;
                        EarlyExitException eee =
                            new EarlyExitException(84, input);
                        throw eee;
                }
                cnt84++;
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
    // EsperEPL2Ast.g:261:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:262:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==MATCHREC_PATTERN_CONCAT) ) {
                alt86=1;
            }
            else if ( (LA86_0==MATCHREC_PATTERN_ALTER) ) {
                alt86=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:262:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1457);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:263:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1465); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1467);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:263:55: ( matchRecogPatternConcat )+
                    int cnt85=0;
                    loop85:
                    do {
                        int alt85=2;
                        int LA85_0 = input.LA(1);

                        if ( (LA85_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt85=1;
                        }


                        switch (alt85) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:263:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1469);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt85 >= 1 ) break loop85;
                                EarlyExitException eee =
                                    new EarlyExitException(85, input);
                                throw eee;
                        }
                        cnt85++;
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
    // EsperEPL2Ast.g:266:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:267:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:267:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1487); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:267:32: ( matchRecogPatternUnary )+
            int cnt87=0;
            loop87:
            do {
                int alt87=2;
                int LA87_0 = input.LA(1);

                if ( (LA87_0==MATCHREC_PATTERN_ATOM||LA87_0==MATCHREC_PATTERN_NESTED) ) {
                    alt87=1;
                }


                switch (alt87) {
            	case 1 :
            	    // EsperEPL2Ast.g:267:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1489);
            	    matchRecogPatternUnary();

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
    // EsperEPL2Ast.g:270:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:271:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==MATCHREC_PATTERN_NESTED) ) {
                alt88=1;
            }
            else if ( (LA88_0==MATCHREC_PATTERN_ATOM) ) {
                alt88=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }
            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:271:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1504);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:272:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1509);
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
    // EsperEPL2Ast.g:275:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:276:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:276:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1524); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1526);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:276:60: ( PLUS | STAR | QUESTION )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==STAR||(LA89_0>=PLUS && LA89_0<=QUESTION)) ) {
                alt89=1;
            }
            switch (alt89) {
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
    // EsperEPL2Ast.g:279:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:280:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:280:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1557); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1559); 
            // EsperEPL2Ast.g:280:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==STAR||(LA91_0>=PLUS && LA91_0<=QUESTION)) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:280:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:280:63: ( QUESTION )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==QUESTION) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // EsperEPL2Ast.g:280:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1575); 

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
    // EsperEPL2Ast.g:283:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:284:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:284:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1597); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:284:24: ( matchRecogDefineItem )+
            int cnt92=0;
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( (LA92_0==MATCHREC_DEFINE_ITEM) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // EsperEPL2Ast.g:284:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1599);
            	    matchRecogDefineItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt92 >= 1 ) break loop92;
                        EarlyExitException eee =
                            new EarlyExitException(92, input);
                        throw eee;
                }
                cnt92++;
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
    // EsperEPL2Ast.g:287:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:288:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:288:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1616); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1618); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1620);
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
    // EsperEPL2Ast.g:292:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:293:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:293:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1637);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:293:25: ( selectionListElement )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=SELECTION_ELEMENT_EXPR && LA93_0<=SELECTION_STREAM)||LA93_0==WILDCARD_SELECT) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // EsperEPL2Ast.g:293:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1640);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop93;
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
    // EsperEPL2Ast.g:296:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:297:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt96=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt96=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt96=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt96=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }

            switch (alt96) {
                case 1 :
                    // EsperEPL2Ast.g:297:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1656); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1666); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1668);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:298:41: ( IDENT )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==IDENT) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // EsperEPL2Ast.g:298:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1671); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:299:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1685); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1687); 
                    // EsperEPL2Ast.g:299:31: ( IDENT )?
                    int alt95=2;
                    int LA95_0 = input.LA(1);

                    if ( (LA95_0==IDENT) ) {
                        alt95=1;
                    }
                    switch (alt95) {
                        case 1 :
                            // EsperEPL2Ast.g:299:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1690); 

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
    // EsperEPL2Ast.g:302:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:303:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:303:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1709);
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
    // EsperEPL2Ast.g:306:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:307:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt101=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt101=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt101=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt101=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt101=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 101, 0, input);

                throw nvae;
            }

            switch (alt101) {
                case 1 :
                    // EsperEPL2Ast.g:307:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1723); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1725);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1728);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:307:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( (LA97_0==EVENT_PROP_EXPR) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:307:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1732);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1735);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop97;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:308:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1750); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1752);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1755);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:308:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop98:
                    do {
                        int alt98=2;
                        int LA98_0 = input.LA(1);

                        if ( (LA98_0==EVENT_PROP_EXPR) ) {
                            alt98=1;
                        }


                        switch (alt98) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:308:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1759);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1762);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop98;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:309:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1777); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1779);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1782);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:309:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop99:
                    do {
                        int alt99=2;
                        int LA99_0 = input.LA(1);

                        if ( (LA99_0==EVENT_PROP_EXPR) ) {
                            alt99=1;
                        }


                        switch (alt99) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:309:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1786);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1789);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop99;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:310:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1804); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1806);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1809);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:310:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop100:
                    do {
                        int alt100=2;
                        int LA100_0 = input.LA(1);

                        if ( (LA100_0==EVENT_PROP_EXPR) ) {
                            alt100=1;
                        }


                        switch (alt100) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:310:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1813);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1816);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop100;
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
    // EsperEPL2Ast.g:313:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:314:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:314:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1837); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:314:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt102=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt102=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt102=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt102=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt102=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }

            switch (alt102) {
                case 1 :
                    // EsperEPL2Ast.g:314:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1840);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:314:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1844);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:314:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1848);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:314:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1852);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:314:115: ( viewListExpr )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==VIEW_EXPR) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:314:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1856);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:314:131: ( IDENT )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==IDENT) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // EsperEPL2Ast.g:314:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1861); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:314:140: ( UNIDIRECTIONAL )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==UNIDIRECTIONAL) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // EsperEPL2Ast.g:314:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1866); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:314:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( ((LA106_0>=RETAINUNION && LA106_0<=RETAININTERSECTION)) ) {
                alt106=1;
            }
            switch (alt106) {
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
    // EsperEPL2Ast.g:317:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:318:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:318:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1894); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:318:27: ( IDENT )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==IDENT) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:318:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1896); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1899); 
            // EsperEPL2Ast.g:318:46: ( propertyExpression )?
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( (LA108_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt108=1;
            }
            switch (alt108) {
                case 1 :
                    // EsperEPL2Ast.g:318:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1901);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:318:66: ( valueExpr )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( ((LA109_0>=IN_SET && LA109_0<=REGEXP)||LA109_0==NOT_EXPR||(LA109_0>=SUM && LA109_0<=AVG)||(LA109_0>=COALESCE && LA109_0<=COUNT)||(LA109_0>=CASE && LA109_0<=CASE2)||(LA109_0>=PREVIOUS && LA109_0<=EXISTS)||(LA109_0>=INSTANCEOF && LA109_0<=CURRENT_TIMESTAMP)||(LA109_0>=EVAL_AND_EXPR && LA109_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA109_0==EVENT_PROP_EXPR||LA109_0==CONCAT||(LA109_0>=LIB_FUNC_CHAIN && LA109_0<=DOT_EXPR)||LA109_0==ARRAY_EXPR||(LA109_0>=NOT_IN_SET && LA109_0<=NOT_REGEXP)||(LA109_0>=IN_RANGE && LA109_0<=SUBSELECT_EXPR)||(LA109_0>=EXISTS_SUBSELECT_EXPR && LA109_0<=NOT_IN_SUBSELECT_EXPR)||LA109_0==SUBSTITUTION||(LA109_0>=FIRST_AGGREG && LA109_0<=WINDOW_AGGREG)||(LA109_0>=INT_TYPE && LA109_0<=NULL_TYPE)||(LA109_0>=STAR && LA109_0<=PLUS)||(LA109_0>=BAND && LA109_0<=BXOR)||(LA109_0>=LT && LA109_0<=GE)||(LA109_0>=MINUS && LA109_0<=MOD)) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // EsperEPL2Ast.g:318:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1905);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop109;
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
    // EsperEPL2Ast.g:321:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:322:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:322:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1925); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:322:34: ( propertyExpressionAtom )*
                loop110:
                do {
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt110=1;
                    }


                    switch (alt110) {
                	case 1 :
                	    // EsperEPL2Ast.g:322:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1927);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop110;
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
    // EsperEPL2Ast.g:325:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:326:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:326:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1946); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:326:41: ( propertySelectionListElement )*
            loop111:
            do {
                int alt111=2;
                int LA111_0 = input.LA(1);

                if ( ((LA111_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA111_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt111=1;
                }


                switch (alt111) {
            	case 1 :
            	    // EsperEPL2Ast.g:326:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1948);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop111;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1951);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:326:96: ( IDENT )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==IDENT) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:326:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1954); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1958); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:326:116: ( valueExpr )?
                int alt113=2;
                int LA113_0 = input.LA(1);

                if ( ((LA113_0>=IN_SET && LA113_0<=REGEXP)||LA113_0==NOT_EXPR||(LA113_0>=SUM && LA113_0<=AVG)||(LA113_0>=COALESCE && LA113_0<=COUNT)||(LA113_0>=CASE && LA113_0<=CASE2)||(LA113_0>=PREVIOUS && LA113_0<=EXISTS)||(LA113_0>=INSTANCEOF && LA113_0<=CURRENT_TIMESTAMP)||(LA113_0>=EVAL_AND_EXPR && LA113_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA113_0==EVENT_PROP_EXPR||LA113_0==CONCAT||(LA113_0>=LIB_FUNC_CHAIN && LA113_0<=DOT_EXPR)||LA113_0==ARRAY_EXPR||(LA113_0>=NOT_IN_SET && LA113_0<=NOT_REGEXP)||(LA113_0>=IN_RANGE && LA113_0<=SUBSELECT_EXPR)||(LA113_0>=EXISTS_SUBSELECT_EXPR && LA113_0<=NOT_IN_SUBSELECT_EXPR)||LA113_0==SUBSTITUTION||(LA113_0>=FIRST_AGGREG && LA113_0<=WINDOW_AGGREG)||(LA113_0>=INT_TYPE && LA113_0<=NULL_TYPE)||(LA113_0>=STAR && LA113_0<=PLUS)||(LA113_0>=BAND && LA113_0<=BXOR)||(LA113_0>=LT && LA113_0<=GE)||(LA113_0>=MINUS && LA113_0<=MOD)) ) {
                    alt113=1;
                }
                switch (alt113) {
                    case 1 :
                        // EsperEPL2Ast.g:326:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1960);
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
    // EsperEPL2Ast.g:329:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:330:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt116=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt116=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt116=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt116=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:330:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1980); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:331:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1990); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1992);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:331:50: ( IDENT )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==IDENT) ) {
                        alt114=1;
                    }
                    switch (alt114) {
                        case 1 :
                            // EsperEPL2Ast.g:331:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1995); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:332:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2009); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2011); 
                    // EsperEPL2Ast.g:332:40: ( IDENT )?
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==IDENT) ) {
                        alt115=1;
                    }
                    switch (alt115) {
                        case 1 :
                            // EsperEPL2Ast.g:332:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2014); 

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
    // EsperEPL2Ast.g:335:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:336:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:336:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2035); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression2037);
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
    // EsperEPL2Ast.g:339:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:340:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:340:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2054); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression2056); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:340:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( ((LA117_0>=STRING_LITERAL && LA117_0<=QUOTED_STRING_LITERAL)) ) {
                alt117=1;
            }
            switch (alt117) {
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
    // EsperEPL2Ast.g:343:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:344:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:344:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2087); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression2089); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression2091); 
            // EsperEPL2Ast.g:344:41: ( valueExpr )*
            loop118:
            do {
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( ((LA118_0>=IN_SET && LA118_0<=REGEXP)||LA118_0==NOT_EXPR||(LA118_0>=SUM && LA118_0<=AVG)||(LA118_0>=COALESCE && LA118_0<=COUNT)||(LA118_0>=CASE && LA118_0<=CASE2)||(LA118_0>=PREVIOUS && LA118_0<=EXISTS)||(LA118_0>=INSTANCEOF && LA118_0<=CURRENT_TIMESTAMP)||(LA118_0>=EVAL_AND_EXPR && LA118_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA118_0==EVENT_PROP_EXPR||LA118_0==CONCAT||(LA118_0>=LIB_FUNC_CHAIN && LA118_0<=DOT_EXPR)||LA118_0==ARRAY_EXPR||(LA118_0>=NOT_IN_SET && LA118_0<=NOT_REGEXP)||(LA118_0>=IN_RANGE && LA118_0<=SUBSELECT_EXPR)||(LA118_0>=EXISTS_SUBSELECT_EXPR && LA118_0<=NOT_IN_SUBSELECT_EXPR)||LA118_0==SUBSTITUTION||(LA118_0>=FIRST_AGGREG && LA118_0<=WINDOW_AGGREG)||(LA118_0>=INT_TYPE && LA118_0<=NULL_TYPE)||(LA118_0>=STAR && LA118_0<=PLUS)||(LA118_0>=BAND && LA118_0<=BXOR)||(LA118_0>=LT && LA118_0<=GE)||(LA118_0>=MINUS && LA118_0<=MOD)) ) {
                    alt118=1;
                }


                switch (alt118) {
            	case 1 :
            	    // EsperEPL2Ast.g:344:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression2094);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop118;
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
    // EsperEPL2Ast.g:347:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:348:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:348:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr2108);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:348:13: ( viewExpr )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( (LA119_0==VIEW_EXPR) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // EsperEPL2Ast.g:348:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr2111);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop119;
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
    // EsperEPL2Ast.g:351:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:352:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:352:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr2128); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2130); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2132); 
            // EsperEPL2Ast.g:352:30: ( valueExprWithTime )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( ((LA120_0>=IN_SET && LA120_0<=REGEXP)||LA120_0==NOT_EXPR||(LA120_0>=SUM && LA120_0<=AVG)||(LA120_0>=COALESCE && LA120_0<=COUNT)||(LA120_0>=CASE && LA120_0<=CASE2)||LA120_0==LAST||(LA120_0>=PREVIOUS && LA120_0<=EXISTS)||(LA120_0>=LW && LA120_0<=CURRENT_TIMESTAMP)||(LA120_0>=NUMERIC_PARAM_RANGE && LA120_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA120_0>=EVAL_AND_EXPR && LA120_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA120_0==EVENT_PROP_EXPR||LA120_0==CONCAT||(LA120_0>=LIB_FUNC_CHAIN && LA120_0<=DOT_EXPR)||(LA120_0>=TIME_PERIOD && LA120_0<=ARRAY_EXPR)||(LA120_0>=NOT_IN_SET && LA120_0<=NOT_REGEXP)||(LA120_0>=IN_RANGE && LA120_0<=SUBSELECT_EXPR)||(LA120_0>=EXISTS_SUBSELECT_EXPR && LA120_0<=NOT_IN_SUBSELECT_EXPR)||(LA120_0>=LAST_OPERATOR && LA120_0<=SUBSTITUTION)||LA120_0==NUMBERSETSTAR||(LA120_0>=FIRST_AGGREG && LA120_0<=WINDOW_AGGREG)||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)||(LA120_0>=STAR && LA120_0<=PLUS)||(LA120_0>=BAND && LA120_0<=BXOR)||(LA120_0>=LT && LA120_0<=GE)||(LA120_0>=MINUS && LA120_0<=MOD)) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // EsperEPL2Ast.g:352:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr2135);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop120;
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
    // EsperEPL2Ast.g:355:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:356:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:356:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause2157); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause2159);
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
    // EsperEPL2Ast.g:359:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:360:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:360:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause2177); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause2179);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:360:32: ( valueExpr )*
            loop121:
            do {
                int alt121=2;
                int LA121_0 = input.LA(1);

                if ( ((LA121_0>=IN_SET && LA121_0<=REGEXP)||LA121_0==NOT_EXPR||(LA121_0>=SUM && LA121_0<=AVG)||(LA121_0>=COALESCE && LA121_0<=COUNT)||(LA121_0>=CASE && LA121_0<=CASE2)||(LA121_0>=PREVIOUS && LA121_0<=EXISTS)||(LA121_0>=INSTANCEOF && LA121_0<=CURRENT_TIMESTAMP)||(LA121_0>=EVAL_AND_EXPR && LA121_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA121_0==EVENT_PROP_EXPR||LA121_0==CONCAT||(LA121_0>=LIB_FUNC_CHAIN && LA121_0<=DOT_EXPR)||LA121_0==ARRAY_EXPR||(LA121_0>=NOT_IN_SET && LA121_0<=NOT_REGEXP)||(LA121_0>=IN_RANGE && LA121_0<=SUBSELECT_EXPR)||(LA121_0>=EXISTS_SUBSELECT_EXPR && LA121_0<=NOT_IN_SUBSELECT_EXPR)||LA121_0==SUBSTITUTION||(LA121_0>=FIRST_AGGREG && LA121_0<=WINDOW_AGGREG)||(LA121_0>=INT_TYPE && LA121_0<=NULL_TYPE)||(LA121_0>=STAR && LA121_0<=PLUS)||(LA121_0>=BAND && LA121_0<=BXOR)||(LA121_0>=LT && LA121_0<=GE)||(LA121_0>=MINUS && LA121_0<=MOD)) ) {
                    alt121=1;
                }


                switch (alt121) {
            	case 1 :
            	    // EsperEPL2Ast.g:360:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause2182);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop121;
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
    // EsperEPL2Ast.g:363:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:364:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:364:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause2200); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause2202);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:364:35: ( orderByElement )*
            loop122:
            do {
                int alt122=2;
                int LA122_0 = input.LA(1);

                if ( (LA122_0==ORDER_ELEMENT_EXPR) ) {
                    alt122=1;
                }


                switch (alt122) {
            	case 1 :
            	    // EsperEPL2Ast.g:364:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause2205);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop122;
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
    // EsperEPL2Ast.g:367:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:368:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:368:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2225); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2227);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:368:38: ( ASC | DESC )?
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( ((LA123_0>=ASC && LA123_0<=DESC)) ) {
                alt123=1;
            }
            switch (alt123) {
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
    // EsperEPL2Ast.g:371:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:372:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:372:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2252); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2254);
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
    // EsperEPL2Ast.g:375:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:376:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt134=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt134=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt134=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt134=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt134=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt134=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 134, 0, input);

                throw nvae;
            }

            switch (alt134) {
                case 1 :
                    // EsperEPL2Ast.g:376:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2272); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:376:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt124=2;
                    int LA124_0 = input.LA(1);

                    if ( (LA124_0==ALL||(LA124_0>=FIRST && LA124_0<=LAST)||LA124_0==SNAPSHOT) ) {
                        alt124=1;
                    }
                    switch (alt124) {
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

                    // EsperEPL2Ast.g:376:52: ( number | IDENT )
                    int alt125=2;
                    int LA125_0 = input.LA(1);

                    if ( ((LA125_0>=INT_TYPE && LA125_0<=DOUBLE_TYPE)) ) {
                        alt125=1;
                    }
                    else if ( (LA125_0==IDENT) ) {
                        alt125=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 125, 0, input);

                        throw nvae;
                    }
                    switch (alt125) {
                        case 1 :
                            // EsperEPL2Ast.g:376:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2286);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:376:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2288); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:376:67: ( outputLimitAfter )?
                    int alt126=2;
                    int LA126_0 = input.LA(1);

                    if ( (LA126_0==AFTER) ) {
                        alt126=1;
                    }
                    switch (alt126) {
                        case 1 :
                            // EsperEPL2Ast.g:376:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2291);
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
                    // EsperEPL2Ast.g:377:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2308); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:377:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt127=2;
                    int LA127_0 = input.LA(1);

                    if ( (LA127_0==ALL||(LA127_0>=FIRST && LA127_0<=LAST)||LA127_0==SNAPSHOT) ) {
                        alt127=1;
                    }
                    switch (alt127) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2321);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:377:72: ( outputLimitAfter )?
                    int alt128=2;
                    int LA128_0 = input.LA(1);

                    if ( (LA128_0==AFTER) ) {
                        alt128=1;
                    }
                    switch (alt128) {
                        case 1 :
                            // EsperEPL2Ast.g:377:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2323);
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
                    // EsperEPL2Ast.g:378:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2339); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:378:33: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2352);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:378:85: ( outputLimitAfter )?
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( (LA130_0==AFTER) ) {
                        alt130=1;
                    }
                    switch (alt130) {
                        case 1 :
                            // EsperEPL2Ast.g:378:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2354);
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
                    // EsperEPL2Ast.g:379:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2370); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:379:30: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2383);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:379:67: ( onSetExpr )?
                    int alt132=2;
                    int LA132_0 = input.LA(1);

                    if ( (LA132_0==ON_SET_EXPR) ) {
                        alt132=1;
                    }
                    switch (alt132) {
                        case 1 :
                            // EsperEPL2Ast.g:379:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2385);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:379:78: ( outputLimitAfter )?
                    int alt133=2;
                    int LA133_0 = input.LA(1);

                    if ( (LA133_0==AFTER) ) {
                        alt133=1;
                    }
                    switch (alt133) {
                        case 1 :
                            // EsperEPL2Ast.g:379:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2388);
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
                    // EsperEPL2Ast.g:380:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2401); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2403);
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
    // EsperEPL2Ast.g:383:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:384:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:384:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2418); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:384:12: ( timePeriod )?
                int alt135=2;
                int LA135_0 = input.LA(1);

                if ( (LA135_0==TIME_PERIOD) ) {
                    alt135=1;
                }
                switch (alt135) {
                    case 1 :
                        // EsperEPL2Ast.g:384:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2420);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:384:24: ( number )?
                int alt136=2;
                int LA136_0 = input.LA(1);

                if ( ((LA136_0>=INT_TYPE && LA136_0<=DOUBLE_TYPE)) ) {
                    alt136=1;
                }
                switch (alt136) {
                    case 1 :
                        // EsperEPL2Ast.g:384:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2423);
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
    // EsperEPL2Ast.g:387:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:388:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:388:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2439); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:388:23: ( number | IDENT )
            int alt137=2;
            int LA137_0 = input.LA(1);

            if ( ((LA137_0>=INT_TYPE && LA137_0<=DOUBLE_TYPE)) ) {
                alt137=1;
            }
            else if ( (LA137_0==IDENT) ) {
                alt137=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 137, 0, input);

                throw nvae;
            }
            switch (alt137) {
                case 1 :
                    // EsperEPL2Ast.g:388:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2442);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:388:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2444); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:388:38: ( number | IDENT )?
            int alt138=3;
            int LA138_0 = input.LA(1);

            if ( ((LA138_0>=INT_TYPE && LA138_0<=DOUBLE_TYPE)) ) {
                alt138=1;
            }
            else if ( (LA138_0==IDENT) ) {
                alt138=2;
            }
            switch (alt138) {
                case 1 :
                    // EsperEPL2Ast.g:388:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2448);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:388:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2450); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:388:54: ( COMMA )?
            int alt139=2;
            int LA139_0 = input.LA(1);

            if ( (LA139_0==COMMA) ) {
                alt139=1;
            }
            switch (alt139) {
                case 1 :
                    // EsperEPL2Ast.g:388:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2454); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:388:61: ( OFFSET )?
            int alt140=2;
            int LA140_0 = input.LA(1);

            if ( (LA140_0==OFFSET) ) {
                alt140=1;
            }
            switch (alt140) {
                case 1 :
                    // EsperEPL2Ast.g:388:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2457); 

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
    // EsperEPL2Ast.g:391:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:392:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:392:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2475); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2477);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2479);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2481);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2483);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2485);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:392:121: ( valueExprWithTime )?
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( ((LA141_0>=IN_SET && LA141_0<=REGEXP)||LA141_0==NOT_EXPR||(LA141_0>=SUM && LA141_0<=AVG)||(LA141_0>=COALESCE && LA141_0<=COUNT)||(LA141_0>=CASE && LA141_0<=CASE2)||LA141_0==LAST||(LA141_0>=PREVIOUS && LA141_0<=EXISTS)||(LA141_0>=LW && LA141_0<=CURRENT_TIMESTAMP)||(LA141_0>=NUMERIC_PARAM_RANGE && LA141_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA141_0>=EVAL_AND_EXPR && LA141_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA141_0==EVENT_PROP_EXPR||LA141_0==CONCAT||(LA141_0>=LIB_FUNC_CHAIN && LA141_0<=DOT_EXPR)||(LA141_0>=TIME_PERIOD && LA141_0<=ARRAY_EXPR)||(LA141_0>=NOT_IN_SET && LA141_0<=NOT_REGEXP)||(LA141_0>=IN_RANGE && LA141_0<=SUBSELECT_EXPR)||(LA141_0>=EXISTS_SUBSELECT_EXPR && LA141_0<=NOT_IN_SUBSELECT_EXPR)||(LA141_0>=LAST_OPERATOR && LA141_0<=SUBSTITUTION)||LA141_0==NUMBERSETSTAR||(LA141_0>=FIRST_AGGREG && LA141_0<=WINDOW_AGGREG)||(LA141_0>=INT_TYPE && LA141_0<=NULL_TYPE)||(LA141_0>=STAR && LA141_0<=PLUS)||(LA141_0>=BAND && LA141_0<=BXOR)||(LA141_0>=LT && LA141_0<=GE)||(LA141_0>=MINUS && LA141_0<=MOD)) ) {
                alt141=1;
            }
            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:392:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2487);
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
    // EsperEPL2Ast.g:395:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:396:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt142=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt142=1;
                }
                break;
            case GT:
                {
                alt142=2;
                }
                break;
            case LE:
                {
                alt142=3;
                }
                break;
            case GE:
                {
                alt142=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 142, 0, input);

                throw nvae;
            }

            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:396:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2504); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2506);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:397:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2519); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2521);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:398:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2534); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2536);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:399:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2548); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2550);
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
    // EsperEPL2Ast.g:402:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:403:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:403:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:403:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:404:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2572);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:405:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt145=2;
            int LA145_0 = input.LA(1);

            if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||LA145_0==CONCAT||(LA145_0>=LIB_FUNC_CHAIN && LA145_0<=DOT_EXPR)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=SUBSELECT_EXPR)||(LA145_0>=EXISTS_SUBSELECT_EXPR && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=FIRST_AGGREG && LA145_0<=WINDOW_AGGREG)||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||(LA145_0>=STAR && LA145_0<=PLUS)||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=MINUS && LA145_0<=MOD)) ) {
                alt145=1;
            }
            else if ( ((LA145_0>=ALL && LA145_0<=SOME)) ) {
                alt145=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }
            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:405:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2582);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:407:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:407:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt144=2;
                    int LA144_0 = input.LA(1);

                    if ( (LA144_0==UP||(LA144_0>=IN_SET && LA144_0<=REGEXP)||LA144_0==NOT_EXPR||(LA144_0>=SUM && LA144_0<=AVG)||(LA144_0>=COALESCE && LA144_0<=COUNT)||(LA144_0>=CASE && LA144_0<=CASE2)||(LA144_0>=PREVIOUS && LA144_0<=EXISTS)||(LA144_0>=INSTANCEOF && LA144_0<=CURRENT_TIMESTAMP)||(LA144_0>=EVAL_AND_EXPR && LA144_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA144_0==EVENT_PROP_EXPR||LA144_0==CONCAT||(LA144_0>=LIB_FUNC_CHAIN && LA144_0<=DOT_EXPR)||LA144_0==ARRAY_EXPR||(LA144_0>=NOT_IN_SET && LA144_0<=NOT_REGEXP)||(LA144_0>=IN_RANGE && LA144_0<=SUBSELECT_EXPR)||(LA144_0>=EXISTS_SUBSELECT_EXPR && LA144_0<=NOT_IN_SUBSELECT_EXPR)||LA144_0==SUBSTITUTION||(LA144_0>=FIRST_AGGREG && LA144_0<=WINDOW_AGGREG)||(LA144_0>=INT_TYPE && LA144_0<=NULL_TYPE)||(LA144_0>=STAR && LA144_0<=PLUS)||(LA144_0>=BAND && LA144_0<=BXOR)||(LA144_0>=LT && LA144_0<=GE)||(LA144_0>=MINUS && LA144_0<=MOD)) ) {
                        alt144=1;
                    }
                    else if ( (LA144_0==SUBSELECT_GROUP_EXPR) ) {
                        alt144=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 144, 0, input);

                        throw nvae;
                    }
                    switch (alt144) {
                        case 1 :
                            // EsperEPL2Ast.g:407:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:407:22: ( valueExpr )*
                            loop143:
                            do {
                                int alt143=2;
                                int LA143_0 = input.LA(1);

                                if ( ((LA143_0>=IN_SET && LA143_0<=REGEXP)||LA143_0==NOT_EXPR||(LA143_0>=SUM && LA143_0<=AVG)||(LA143_0>=COALESCE && LA143_0<=COUNT)||(LA143_0>=CASE && LA143_0<=CASE2)||(LA143_0>=PREVIOUS && LA143_0<=EXISTS)||(LA143_0>=INSTANCEOF && LA143_0<=CURRENT_TIMESTAMP)||(LA143_0>=EVAL_AND_EXPR && LA143_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA143_0==EVENT_PROP_EXPR||LA143_0==CONCAT||(LA143_0>=LIB_FUNC_CHAIN && LA143_0<=DOT_EXPR)||LA143_0==ARRAY_EXPR||(LA143_0>=NOT_IN_SET && LA143_0<=NOT_REGEXP)||(LA143_0>=IN_RANGE && LA143_0<=SUBSELECT_EXPR)||(LA143_0>=EXISTS_SUBSELECT_EXPR && LA143_0<=NOT_IN_SUBSELECT_EXPR)||LA143_0==SUBSTITUTION||(LA143_0>=FIRST_AGGREG && LA143_0<=WINDOW_AGGREG)||(LA143_0>=INT_TYPE && LA143_0<=NULL_TYPE)||(LA143_0>=STAR && LA143_0<=PLUS)||(LA143_0>=BAND && LA143_0<=BXOR)||(LA143_0>=LT && LA143_0<=GE)||(LA143_0>=MINUS && LA143_0<=MOD)) ) {
                                    alt143=1;
                                }


                                switch (alt143) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:407:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2606);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop143;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:407:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2611);
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
    // EsperEPL2Ast.g:412:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:413:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt152=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt152=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt152=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt152=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt152=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt152=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt152=6;
                }
                break;
            case NOT_EXPR:
                {
                alt152=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt152=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 152, 0, input);

                throw nvae;
            }

            switch (alt152) {
                case 1 :
                    // EsperEPL2Ast.g:413:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2637); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2639);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2641);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:413:42: ( valueExpr )*
                    loop146:
                    do {
                        int alt146=2;
                        int LA146_0 = input.LA(1);

                        if ( ((LA146_0>=IN_SET && LA146_0<=REGEXP)||LA146_0==NOT_EXPR||(LA146_0>=SUM && LA146_0<=AVG)||(LA146_0>=COALESCE && LA146_0<=COUNT)||(LA146_0>=CASE && LA146_0<=CASE2)||(LA146_0>=PREVIOUS && LA146_0<=EXISTS)||(LA146_0>=INSTANCEOF && LA146_0<=CURRENT_TIMESTAMP)||(LA146_0>=EVAL_AND_EXPR && LA146_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA146_0==EVENT_PROP_EXPR||LA146_0==CONCAT||(LA146_0>=LIB_FUNC_CHAIN && LA146_0<=DOT_EXPR)||LA146_0==ARRAY_EXPR||(LA146_0>=NOT_IN_SET && LA146_0<=NOT_REGEXP)||(LA146_0>=IN_RANGE && LA146_0<=SUBSELECT_EXPR)||(LA146_0>=EXISTS_SUBSELECT_EXPR && LA146_0<=NOT_IN_SUBSELECT_EXPR)||LA146_0==SUBSTITUTION||(LA146_0>=FIRST_AGGREG && LA146_0<=WINDOW_AGGREG)||(LA146_0>=INT_TYPE && LA146_0<=NULL_TYPE)||(LA146_0>=STAR && LA146_0<=PLUS)||(LA146_0>=BAND && LA146_0<=BXOR)||(LA146_0>=LT && LA146_0<=GE)||(LA146_0>=MINUS && LA146_0<=MOD)) ) {
                            alt146=1;
                        }


                        switch (alt146) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:413:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2644);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop146;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:414:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2658); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2660);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2662);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:414:43: ( valueExpr )*
                    loop147:
                    do {
                        int alt147=2;
                        int LA147_0 = input.LA(1);

                        if ( ((LA147_0>=IN_SET && LA147_0<=REGEXP)||LA147_0==NOT_EXPR||(LA147_0>=SUM && LA147_0<=AVG)||(LA147_0>=COALESCE && LA147_0<=COUNT)||(LA147_0>=CASE && LA147_0<=CASE2)||(LA147_0>=PREVIOUS && LA147_0<=EXISTS)||(LA147_0>=INSTANCEOF && LA147_0<=CURRENT_TIMESTAMP)||(LA147_0>=EVAL_AND_EXPR && LA147_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA147_0==EVENT_PROP_EXPR||LA147_0==CONCAT||(LA147_0>=LIB_FUNC_CHAIN && LA147_0<=DOT_EXPR)||LA147_0==ARRAY_EXPR||(LA147_0>=NOT_IN_SET && LA147_0<=NOT_REGEXP)||(LA147_0>=IN_RANGE && LA147_0<=SUBSELECT_EXPR)||(LA147_0>=EXISTS_SUBSELECT_EXPR && LA147_0<=NOT_IN_SUBSELECT_EXPR)||LA147_0==SUBSTITUTION||(LA147_0>=FIRST_AGGREG && LA147_0<=WINDOW_AGGREG)||(LA147_0>=INT_TYPE && LA147_0<=NULL_TYPE)||(LA147_0>=STAR && LA147_0<=PLUS)||(LA147_0>=BAND && LA147_0<=BXOR)||(LA147_0>=LT && LA147_0<=GE)||(LA147_0>=MINUS && LA147_0<=MOD)) ) {
                            alt147=1;
                        }


                        switch (alt147) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:414:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2665);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop147;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:415:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2679); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2681);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2683);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:416:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2695); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2697);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2699);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:417:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2711); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2713);
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

                    // EsperEPL2Ast.g:417:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt149=2;
                    int LA149_0 = input.LA(1);

                    if ( (LA149_0==UP||(LA149_0>=IN_SET && LA149_0<=REGEXP)||LA149_0==NOT_EXPR||(LA149_0>=SUM && LA149_0<=AVG)||(LA149_0>=COALESCE && LA149_0<=COUNT)||(LA149_0>=CASE && LA149_0<=CASE2)||(LA149_0>=PREVIOUS && LA149_0<=EXISTS)||(LA149_0>=INSTANCEOF && LA149_0<=CURRENT_TIMESTAMP)||(LA149_0>=EVAL_AND_EXPR && LA149_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA149_0==EVENT_PROP_EXPR||LA149_0==CONCAT||(LA149_0>=LIB_FUNC_CHAIN && LA149_0<=DOT_EXPR)||LA149_0==ARRAY_EXPR||(LA149_0>=NOT_IN_SET && LA149_0<=NOT_REGEXP)||(LA149_0>=IN_RANGE && LA149_0<=SUBSELECT_EXPR)||(LA149_0>=EXISTS_SUBSELECT_EXPR && LA149_0<=NOT_IN_SUBSELECT_EXPR)||LA149_0==SUBSTITUTION||(LA149_0>=FIRST_AGGREG && LA149_0<=WINDOW_AGGREG)||(LA149_0>=INT_TYPE && LA149_0<=NULL_TYPE)||(LA149_0>=STAR && LA149_0<=PLUS)||(LA149_0>=BAND && LA149_0<=BXOR)||(LA149_0>=LT && LA149_0<=GE)||(LA149_0>=MINUS && LA149_0<=MOD)) ) {
                        alt149=1;
                    }
                    else if ( (LA149_0==SUBSELECT_GROUP_EXPR) ) {
                        alt149=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 149, 0, input);

                        throw nvae;
                    }
                    switch (alt149) {
                        case 1 :
                            // EsperEPL2Ast.g:417:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:417:59: ( valueExpr )*
                            loop148:
                            do {
                                int alt148=2;
                                int LA148_0 = input.LA(1);

                                if ( ((LA148_0>=IN_SET && LA148_0<=REGEXP)||LA148_0==NOT_EXPR||(LA148_0>=SUM && LA148_0<=AVG)||(LA148_0>=COALESCE && LA148_0<=COUNT)||(LA148_0>=CASE && LA148_0<=CASE2)||(LA148_0>=PREVIOUS && LA148_0<=EXISTS)||(LA148_0>=INSTANCEOF && LA148_0<=CURRENT_TIMESTAMP)||(LA148_0>=EVAL_AND_EXPR && LA148_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA148_0==EVENT_PROP_EXPR||LA148_0==CONCAT||(LA148_0>=LIB_FUNC_CHAIN && LA148_0<=DOT_EXPR)||LA148_0==ARRAY_EXPR||(LA148_0>=NOT_IN_SET && LA148_0<=NOT_REGEXP)||(LA148_0>=IN_RANGE && LA148_0<=SUBSELECT_EXPR)||(LA148_0>=EXISTS_SUBSELECT_EXPR && LA148_0<=NOT_IN_SUBSELECT_EXPR)||LA148_0==SUBSTITUTION||(LA148_0>=FIRST_AGGREG && LA148_0<=WINDOW_AGGREG)||(LA148_0>=INT_TYPE && LA148_0<=NULL_TYPE)||(LA148_0>=STAR && LA148_0<=PLUS)||(LA148_0>=BAND && LA148_0<=BXOR)||(LA148_0>=LT && LA148_0<=GE)||(LA148_0>=MINUS && LA148_0<=MOD)) ) {
                                    alt148=1;
                                }


                                switch (alt148) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:417:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2724);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop148;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:417:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2729);
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
                    // EsperEPL2Ast.g:418:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2742); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2744);
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

                    // EsperEPL2Ast.g:418:62: ( ( valueExpr )* | subSelectGroupExpr )
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
                            // EsperEPL2Ast.g:418:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:418:63: ( valueExpr )*
                            loop150:
                            do {
                                int alt150=2;
                                int LA150_0 = input.LA(1);

                                if ( ((LA150_0>=IN_SET && LA150_0<=REGEXP)||LA150_0==NOT_EXPR||(LA150_0>=SUM && LA150_0<=AVG)||(LA150_0>=COALESCE && LA150_0<=COUNT)||(LA150_0>=CASE && LA150_0<=CASE2)||(LA150_0>=PREVIOUS && LA150_0<=EXISTS)||(LA150_0>=INSTANCEOF && LA150_0<=CURRENT_TIMESTAMP)||(LA150_0>=EVAL_AND_EXPR && LA150_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA150_0==EVENT_PROP_EXPR||LA150_0==CONCAT||(LA150_0>=LIB_FUNC_CHAIN && LA150_0<=DOT_EXPR)||LA150_0==ARRAY_EXPR||(LA150_0>=NOT_IN_SET && LA150_0<=NOT_REGEXP)||(LA150_0>=IN_RANGE && LA150_0<=SUBSELECT_EXPR)||(LA150_0>=EXISTS_SUBSELECT_EXPR && LA150_0<=NOT_IN_SUBSELECT_EXPR)||LA150_0==SUBSTITUTION||(LA150_0>=FIRST_AGGREG && LA150_0<=WINDOW_AGGREG)||(LA150_0>=INT_TYPE && LA150_0<=NULL_TYPE)||(LA150_0>=STAR && LA150_0<=PLUS)||(LA150_0>=BAND && LA150_0<=BXOR)||(LA150_0>=LT && LA150_0<=GE)||(LA150_0>=MINUS && LA150_0<=MOD)) ) {
                                    alt150=1;
                                }


                                switch (alt150) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:418:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2755);
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
                            // EsperEPL2Ast.g:418:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2760);
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
                    // EsperEPL2Ast.g:419:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2773); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2775);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:420:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2786);
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
    // EsperEPL2Ast.g:423:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFuncChain | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr | dotExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:424:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFuncChain | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr | dotExpr )
            int alt153=17;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt153=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt153=2;
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
                alt153=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt153=4;
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
                alt153=5;
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
            case CAST:
            case CURRENT_TIMESTAMP:
            case FIRST_AGGREG:
            case LAST_AGGREG:
            case WINDOW_AGGREG:
                {
                alt153=6;
                }
                break;
            case LIB_FUNC_CHAIN:
                {
                alt153=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt153=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt153=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt153=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt153=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt153=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt153=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt153=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt153=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt153=16;
                }
                break;
            case DOT_EXPR:
                {
                alt153=17;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 153, 0, input);

                throw nvae;
            }

            switch (alt153) {
                case 1 :
                    // EsperEPL2Ast.g:424:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2799);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:425:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2805);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:426:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2811);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:427:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2818);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:428:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2827);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:429:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2832);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:430:7: libFuncChain
                    {
                    pushFollow(FOLLOW_libFuncChain_in_valueExpr2840);
                    libFuncChain();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:431:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2845);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:432:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2850);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:433:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2856);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:434:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2861);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:435:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2866);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:436:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2871);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:437:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2876);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:438:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2882);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:439:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2889);
                    subSelectExistsExpr();

                    state._fsp--;


                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:440:4: dotExpr
                    {
                    pushFollow(FOLLOW_dotExpr_in_valueExpr2894);
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
    // EsperEPL2Ast.g:443:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:444:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt155=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt155=1;
                }
                break;
            case LW:
                {
                alt155=2;
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
                alt155=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt155=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt155=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt155=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt155=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt155=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt155=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt155=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt155=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 155, 0, input);

                throw nvae;
            }

            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:444:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2907); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:445:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2916); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:446:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2923);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:447:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2931); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2933);
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
                    // EsperEPL2Ast.g:448:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2948);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:449:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2954);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:450:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2959);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:451:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2964);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:452:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2974); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:452:29: ( numericParameterList )+
                    int cnt154=0;
                    loop154:
                    do {
                        int alt154=2;
                        int LA154_0 = input.LA(1);

                        if ( (LA154_0==NUMERIC_PARAM_RANGE||LA154_0==NUMERIC_PARAM_FREQUENCY||(LA154_0>=INT_TYPE && LA154_0<=NULL_TYPE)) ) {
                            alt154=1;
                        }


                        switch (alt154) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:452:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2976);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt154 >= 1 ) break loop154;
                                EarlyExitException eee =
                                    new EarlyExitException(154, input);
                                throw eee;
                        }
                        cnt154++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:453:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2987); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:454:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2994);
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
    // EsperEPL2Ast.g:457:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:458:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt156=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt156=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt156=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt156=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 156, 0, input);

                throw nvae;
            }

            switch (alt156) {
                case 1 :
                    // EsperEPL2Ast.g:458:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList3007);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:459:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList3014);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:460:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList3020);
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
    // EsperEPL2Ast.g:463:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:464:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:464:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3036); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:464:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt157=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt157=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt157=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt157=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 157, 0, input);

                throw nvae;
            }

            switch (alt157) {
                case 1 :
                    // EsperEPL2Ast.g:464:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3039);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:464:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3042);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:464:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3045);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:464:83: ( constant[true] | eventPropertyExpr[true] | substitution )
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
            case EVENT_PROP_EXPR:
                {
                alt158=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:464:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3049);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:464:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3052);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:464:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3055);
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
    // EsperEPL2Ast.g:467:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:468:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:468:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3076); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:468:33: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:468:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator3079);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:468:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator3082);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:468:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator3085);
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
    // EsperEPL2Ast.g:471:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:472:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:472:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator3104); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:472:23: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:472:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator3107);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:472:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator3110);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:472:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator3113);
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
    // EsperEPL2Ast.g:475:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:476:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:476:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3132); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:476:26: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:476:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator3135);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:476:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator3138);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:476:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator3141);
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
    // EsperEPL2Ast.g:479:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:480:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:480:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3162); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr3164);
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
    // EsperEPL2Ast.g:483:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:484:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:484:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3183); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr3185);
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
    // EsperEPL2Ast.g:487:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:488:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:488:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3204); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr3206);
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
    // EsperEPL2Ast.g:491:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:492:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==IN_SUBSELECT_EXPR) ) {
                alt162=1;
            }
            else if ( (LA162_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt162=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }
            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:492:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3225); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3227);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3229);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:493:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3241); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3243);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3245);
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
    // EsperEPL2Ast.g:496:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:497:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:497:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3264); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3266);
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
    // EsperEPL2Ast.g:500:1: subQueryExpr : ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:501:2: ( ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:501:4: ( DISTINCT )? selectionList subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:501:4: ( DISTINCT )?
            int alt163=2;
            int LA163_0 = input.LA(1);

            if ( (LA163_0==DISTINCT) ) {
                alt163=1;
            }
            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:501:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3282); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_subQueryExpr3285);
            selectionList();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3287);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:501:48: ( whereClause[true] )?
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==WHERE_EXPR) ) {
                alt164=1;
            }
            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:501:49: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3290);
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
    // EsperEPL2Ast.g:504:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:505:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:505:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3308); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3310);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:505:36: ( viewListExpr )?
            int alt165=2;
            int LA165_0 = input.LA(1);

            if ( (LA165_0==VIEW_EXPR) ) {
                alt165=1;
            }
            switch (alt165) {
                case 1 :
                    // EsperEPL2Ast.g:505:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3313);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:505:52: ( IDENT )?
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==IDENT) ) {
                alt166=1;
            }
            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:505:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3318); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:505:61: ( RETAINUNION )?
            int alt167=2;
            int LA167_0 = input.LA(1);

            if ( (LA167_0==RETAINUNION) ) {
                alt167=1;
            }
            switch (alt167) {
                case 1 :
                    // EsperEPL2Ast.g:505:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3322); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:505:74: ( RETAININTERSECTION )?
            int alt168=2;
            int LA168_0 = input.LA(1);

            if ( (LA168_0==RETAININTERSECTION) ) {
                alt168=1;
            }
            switch (alt168) {
                case 1 :
                    // EsperEPL2Ast.g:505:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3325); 

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
    // EsperEPL2Ast.g:508:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:509:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt171=2;
            int LA171_0 = input.LA(1);

            if ( (LA171_0==CASE) ) {
                alt171=1;
            }
            else if ( (LA171_0==CASE2) ) {
                alt171=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 171, 0, input);

                throw nvae;
            }
            switch (alt171) {
                case 1 :
                    // EsperEPL2Ast.g:509:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3345); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:509:13: ( valueExpr )*
                        loop169:
                        do {
                            int alt169=2;
                            int LA169_0 = input.LA(1);

                            if ( ((LA169_0>=IN_SET && LA169_0<=REGEXP)||LA169_0==NOT_EXPR||(LA169_0>=SUM && LA169_0<=AVG)||(LA169_0>=COALESCE && LA169_0<=COUNT)||(LA169_0>=CASE && LA169_0<=CASE2)||(LA169_0>=PREVIOUS && LA169_0<=EXISTS)||(LA169_0>=INSTANCEOF && LA169_0<=CURRENT_TIMESTAMP)||(LA169_0>=EVAL_AND_EXPR && LA169_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA169_0==EVENT_PROP_EXPR||LA169_0==CONCAT||(LA169_0>=LIB_FUNC_CHAIN && LA169_0<=DOT_EXPR)||LA169_0==ARRAY_EXPR||(LA169_0>=NOT_IN_SET && LA169_0<=NOT_REGEXP)||(LA169_0>=IN_RANGE && LA169_0<=SUBSELECT_EXPR)||(LA169_0>=EXISTS_SUBSELECT_EXPR && LA169_0<=NOT_IN_SUBSELECT_EXPR)||LA169_0==SUBSTITUTION||(LA169_0>=FIRST_AGGREG && LA169_0<=WINDOW_AGGREG)||(LA169_0>=INT_TYPE && LA169_0<=NULL_TYPE)||(LA169_0>=STAR && LA169_0<=PLUS)||(LA169_0>=BAND && LA169_0<=BXOR)||(LA169_0>=LT && LA169_0<=GE)||(LA169_0>=MINUS && LA169_0<=MOD)) ) {
                                alt169=1;
                            }


                            switch (alt169) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:509:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3348);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop169;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:510:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3361); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:510:14: ( valueExpr )*
                        loop170:
                        do {
                            int alt170=2;
                            int LA170_0 = input.LA(1);

                            if ( ((LA170_0>=IN_SET && LA170_0<=REGEXP)||LA170_0==NOT_EXPR||(LA170_0>=SUM && LA170_0<=AVG)||(LA170_0>=COALESCE && LA170_0<=COUNT)||(LA170_0>=CASE && LA170_0<=CASE2)||(LA170_0>=PREVIOUS && LA170_0<=EXISTS)||(LA170_0>=INSTANCEOF && LA170_0<=CURRENT_TIMESTAMP)||(LA170_0>=EVAL_AND_EXPR && LA170_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA170_0==EVENT_PROP_EXPR||LA170_0==CONCAT||(LA170_0>=LIB_FUNC_CHAIN && LA170_0<=DOT_EXPR)||LA170_0==ARRAY_EXPR||(LA170_0>=NOT_IN_SET && LA170_0<=NOT_REGEXP)||(LA170_0>=IN_RANGE && LA170_0<=SUBSELECT_EXPR)||(LA170_0>=EXISTS_SUBSELECT_EXPR && LA170_0<=NOT_IN_SUBSELECT_EXPR)||LA170_0==SUBSTITUTION||(LA170_0>=FIRST_AGGREG && LA170_0<=WINDOW_AGGREG)||(LA170_0>=INT_TYPE && LA170_0<=NULL_TYPE)||(LA170_0>=STAR && LA170_0<=PLUS)||(LA170_0>=BAND && LA170_0<=BXOR)||(LA170_0>=LT && LA170_0<=GE)||(LA170_0>=MINUS && LA170_0<=MOD)) ) {
                                alt170=1;
                            }


                            switch (alt170) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:510:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3364);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop170;
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
    // EsperEPL2Ast.g:513:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:514:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt174=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt174=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt174=2;
                }
                break;
            case IN_RANGE:
                {
                alt174=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt174=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }

            switch (alt174) {
                case 1 :
                    // EsperEPL2Ast.g:514:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3384); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3386);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3394);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:514:51: ( valueExpr )*
                    loop172:
                    do {
                        int alt172=2;
                        int LA172_0 = input.LA(1);

                        if ( ((LA172_0>=IN_SET && LA172_0<=REGEXP)||LA172_0==NOT_EXPR||(LA172_0>=SUM && LA172_0<=AVG)||(LA172_0>=COALESCE && LA172_0<=COUNT)||(LA172_0>=CASE && LA172_0<=CASE2)||(LA172_0>=PREVIOUS && LA172_0<=EXISTS)||(LA172_0>=INSTANCEOF && LA172_0<=CURRENT_TIMESTAMP)||(LA172_0>=EVAL_AND_EXPR && LA172_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA172_0==EVENT_PROP_EXPR||LA172_0==CONCAT||(LA172_0>=LIB_FUNC_CHAIN && LA172_0<=DOT_EXPR)||LA172_0==ARRAY_EXPR||(LA172_0>=NOT_IN_SET && LA172_0<=NOT_REGEXP)||(LA172_0>=IN_RANGE && LA172_0<=SUBSELECT_EXPR)||(LA172_0>=EXISTS_SUBSELECT_EXPR && LA172_0<=NOT_IN_SUBSELECT_EXPR)||LA172_0==SUBSTITUTION||(LA172_0>=FIRST_AGGREG && LA172_0<=WINDOW_AGGREG)||(LA172_0>=INT_TYPE && LA172_0<=NULL_TYPE)||(LA172_0>=STAR && LA172_0<=PLUS)||(LA172_0>=BAND && LA172_0<=BXOR)||(LA172_0>=LT && LA172_0<=GE)||(LA172_0>=MINUS && LA172_0<=MOD)) ) {
                            alt172=1;
                        }


                        switch (alt172) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:514:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3397);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop172;
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
                    // EsperEPL2Ast.g:515:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3416); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3418);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3426);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:515:55: ( valueExpr )*
                    loop173:
                    do {
                        int alt173=2;
                        int LA173_0 = input.LA(1);

                        if ( ((LA173_0>=IN_SET && LA173_0<=REGEXP)||LA173_0==NOT_EXPR||(LA173_0>=SUM && LA173_0<=AVG)||(LA173_0>=COALESCE && LA173_0<=COUNT)||(LA173_0>=CASE && LA173_0<=CASE2)||(LA173_0>=PREVIOUS && LA173_0<=EXISTS)||(LA173_0>=INSTANCEOF && LA173_0<=CURRENT_TIMESTAMP)||(LA173_0>=EVAL_AND_EXPR && LA173_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA173_0==EVENT_PROP_EXPR||LA173_0==CONCAT||(LA173_0>=LIB_FUNC_CHAIN && LA173_0<=DOT_EXPR)||LA173_0==ARRAY_EXPR||(LA173_0>=NOT_IN_SET && LA173_0<=NOT_REGEXP)||(LA173_0>=IN_RANGE && LA173_0<=SUBSELECT_EXPR)||(LA173_0>=EXISTS_SUBSELECT_EXPR && LA173_0<=NOT_IN_SUBSELECT_EXPR)||LA173_0==SUBSTITUTION||(LA173_0>=FIRST_AGGREG && LA173_0<=WINDOW_AGGREG)||(LA173_0>=INT_TYPE && LA173_0<=NULL_TYPE)||(LA173_0>=STAR && LA173_0<=PLUS)||(LA173_0>=BAND && LA173_0<=BXOR)||(LA173_0>=LT && LA173_0<=GE)||(LA173_0>=MINUS && LA173_0<=MOD)) ) {
                            alt173=1;
                        }


                        switch (alt173) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:515:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3429);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop173;
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
                    // EsperEPL2Ast.g:516:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3448); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3450);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3458);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3460);
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
                    // EsperEPL2Ast.g:517:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3477); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3479);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3487);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3489);
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
    // EsperEPL2Ast.g:520:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:521:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( (LA176_0==BETWEEN) ) {
                alt176=1;
            }
            else if ( (LA176_0==NOT_BETWEEN) ) {
                alt176=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 176, 0, input);

                throw nvae;
            }
            switch (alt176) {
                case 1 :
                    // EsperEPL2Ast.g:521:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3514); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3516);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3518);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3520);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:522:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3531); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3533);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3535);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:522:40: ( valueExpr )*
                    loop175:
                    do {
                        int alt175=2;
                        int LA175_0 = input.LA(1);

                        if ( ((LA175_0>=IN_SET && LA175_0<=REGEXP)||LA175_0==NOT_EXPR||(LA175_0>=SUM && LA175_0<=AVG)||(LA175_0>=COALESCE && LA175_0<=COUNT)||(LA175_0>=CASE && LA175_0<=CASE2)||(LA175_0>=PREVIOUS && LA175_0<=EXISTS)||(LA175_0>=INSTANCEOF && LA175_0<=CURRENT_TIMESTAMP)||(LA175_0>=EVAL_AND_EXPR && LA175_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA175_0==EVENT_PROP_EXPR||LA175_0==CONCAT||(LA175_0>=LIB_FUNC_CHAIN && LA175_0<=DOT_EXPR)||LA175_0==ARRAY_EXPR||(LA175_0>=NOT_IN_SET && LA175_0<=NOT_REGEXP)||(LA175_0>=IN_RANGE && LA175_0<=SUBSELECT_EXPR)||(LA175_0>=EXISTS_SUBSELECT_EXPR && LA175_0<=NOT_IN_SUBSELECT_EXPR)||LA175_0==SUBSTITUTION||(LA175_0>=FIRST_AGGREG && LA175_0<=WINDOW_AGGREG)||(LA175_0>=INT_TYPE && LA175_0<=NULL_TYPE)||(LA175_0>=STAR && LA175_0<=PLUS)||(LA175_0>=BAND && LA175_0<=BXOR)||(LA175_0>=LT && LA175_0<=GE)||(LA175_0>=MINUS && LA175_0<=MOD)) ) {
                            alt175=1;
                        }


                        switch (alt175) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:522:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3538);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop175;
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
    // EsperEPL2Ast.g:525:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:526:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt179=2;
            int LA179_0 = input.LA(1);

            if ( (LA179_0==LIKE) ) {
                alt179=1;
            }
            else if ( (LA179_0==NOT_LIKE) ) {
                alt179=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 179, 0, input);

                throw nvae;
            }
            switch (alt179) {
                case 1 :
                    // EsperEPL2Ast.g:526:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3558); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3560);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3562);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:526:33: ( valueExpr )?
                    int alt177=2;
                    int LA177_0 = input.LA(1);

                    if ( ((LA177_0>=IN_SET && LA177_0<=REGEXP)||LA177_0==NOT_EXPR||(LA177_0>=SUM && LA177_0<=AVG)||(LA177_0>=COALESCE && LA177_0<=COUNT)||(LA177_0>=CASE && LA177_0<=CASE2)||(LA177_0>=PREVIOUS && LA177_0<=EXISTS)||(LA177_0>=INSTANCEOF && LA177_0<=CURRENT_TIMESTAMP)||(LA177_0>=EVAL_AND_EXPR && LA177_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA177_0==EVENT_PROP_EXPR||LA177_0==CONCAT||(LA177_0>=LIB_FUNC_CHAIN && LA177_0<=DOT_EXPR)||LA177_0==ARRAY_EXPR||(LA177_0>=NOT_IN_SET && LA177_0<=NOT_REGEXP)||(LA177_0>=IN_RANGE && LA177_0<=SUBSELECT_EXPR)||(LA177_0>=EXISTS_SUBSELECT_EXPR && LA177_0<=NOT_IN_SUBSELECT_EXPR)||LA177_0==SUBSTITUTION||(LA177_0>=FIRST_AGGREG && LA177_0<=WINDOW_AGGREG)||(LA177_0>=INT_TYPE && LA177_0<=NULL_TYPE)||(LA177_0>=STAR && LA177_0<=PLUS)||(LA177_0>=BAND && LA177_0<=BXOR)||(LA177_0>=LT && LA177_0<=GE)||(LA177_0>=MINUS && LA177_0<=MOD)) ) {
                        alt177=1;
                    }
                    switch (alt177) {
                        case 1 :
                            // EsperEPL2Ast.g:526:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3565);
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
                    // EsperEPL2Ast.g:527:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3578); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3580);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3582);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:527:37: ( valueExpr )?
                    int alt178=2;
                    int LA178_0 = input.LA(1);

                    if ( ((LA178_0>=IN_SET && LA178_0<=REGEXP)||LA178_0==NOT_EXPR||(LA178_0>=SUM && LA178_0<=AVG)||(LA178_0>=COALESCE && LA178_0<=COUNT)||(LA178_0>=CASE && LA178_0<=CASE2)||(LA178_0>=PREVIOUS && LA178_0<=EXISTS)||(LA178_0>=INSTANCEOF && LA178_0<=CURRENT_TIMESTAMP)||(LA178_0>=EVAL_AND_EXPR && LA178_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA178_0==EVENT_PROP_EXPR||LA178_0==CONCAT||(LA178_0>=LIB_FUNC_CHAIN && LA178_0<=DOT_EXPR)||LA178_0==ARRAY_EXPR||(LA178_0>=NOT_IN_SET && LA178_0<=NOT_REGEXP)||(LA178_0>=IN_RANGE && LA178_0<=SUBSELECT_EXPR)||(LA178_0>=EXISTS_SUBSELECT_EXPR && LA178_0<=NOT_IN_SUBSELECT_EXPR)||LA178_0==SUBSTITUTION||(LA178_0>=FIRST_AGGREG && LA178_0<=WINDOW_AGGREG)||(LA178_0>=INT_TYPE && LA178_0<=NULL_TYPE)||(LA178_0>=STAR && LA178_0<=PLUS)||(LA178_0>=BAND && LA178_0<=BXOR)||(LA178_0>=LT && LA178_0<=GE)||(LA178_0>=MINUS && LA178_0<=MOD)) ) {
                        alt178=1;
                    }
                    switch (alt178) {
                        case 1 :
                            // EsperEPL2Ast.g:527:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3585);
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
    // EsperEPL2Ast.g:530:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:531:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt180=2;
            int LA180_0 = input.LA(1);

            if ( (LA180_0==REGEXP) ) {
                alt180=1;
            }
            else if ( (LA180_0==NOT_REGEXP) ) {
                alt180=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 180, 0, input);

                throw nvae;
            }
            switch (alt180) {
                case 1 :
                    // EsperEPL2Ast.g:531:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3604); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3606);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3608);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:532:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3619); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3621);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3623);
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
    // EsperEPL2Ast.g:535:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= FIRST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= WINDOW_AGGREG ( DISTINCT )? accessValueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? ) | ^(f= PREVIOUSCOUNT valueExpr ) | ^(f= PREVIOUSWINDOW valueExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:536:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= FIRST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? ) | ^(f= WINDOW_AGGREG ( DISTINCT )? accessValueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? ) | ^(f= PREVIOUSCOUNT valueExpr ) | ^(f= PREVIOUSWINDOW valueExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt197=19;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt197=1;
                }
                break;
            case AVG:
                {
                alt197=2;
                }
                break;
            case COUNT:
                {
                alt197=3;
                }
                break;
            case MEDIAN:
                {
                alt197=4;
                }
                break;
            case STDDEV:
                {
                alt197=5;
                }
                break;
            case AVEDEV:
                {
                alt197=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt197=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt197=8;
                }
                break;
            case WINDOW_AGGREG:
                {
                alt197=9;
                }
                break;
            case COALESCE:
                {
                alt197=10;
                }
                break;
            case PREVIOUS:
                {
                alt197=11;
                }
                break;
            case PREVIOUSTAIL:
                {
                alt197=12;
                }
                break;
            case PREVIOUSCOUNT:
                {
                alt197=13;
                }
                break;
            case PREVIOUSWINDOW:
                {
                alt197=14;
                }
                break;
            case PRIOR:
                {
                alt197=15;
                }
                break;
            case INSTANCEOF:
                {
                alt197=16;
                }
                break;
            case CAST:
                {
                alt197=17;
                }
                break;
            case EXISTS:
                {
                alt197=18;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt197=19;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 197, 0, input);

                throw nvae;
            }

            switch (alt197) {
                case 1 :
                    // EsperEPL2Ast.g:536:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3642); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:536:13: ( DISTINCT )?
                    int alt181=2;
                    int LA181_0 = input.LA(1);

                    if ( (LA181_0==DISTINCT) ) {
                        alt181=1;
                    }
                    switch (alt181) {
                        case 1 :
                            // EsperEPL2Ast.g:536:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3645); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3649);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:537:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3660); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:537:12: ( DISTINCT )?
                    int alt182=2;
                    int LA182_0 = input.LA(1);

                    if ( (LA182_0==DISTINCT) ) {
                        alt182=1;
                    }
                    switch (alt182) {
                        case 1 :
                            // EsperEPL2Ast.g:537:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3663); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3667);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:538:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3678); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:538:14: ( ( DISTINCT )? valueExpr )?
                        int alt184=2;
                        int LA184_0 = input.LA(1);

                        if ( ((LA184_0>=IN_SET && LA184_0<=REGEXP)||LA184_0==NOT_EXPR||(LA184_0>=SUM && LA184_0<=AVG)||(LA184_0>=COALESCE && LA184_0<=COUNT)||(LA184_0>=CASE && LA184_0<=CASE2)||LA184_0==DISTINCT||(LA184_0>=PREVIOUS && LA184_0<=EXISTS)||(LA184_0>=INSTANCEOF && LA184_0<=CURRENT_TIMESTAMP)||(LA184_0>=EVAL_AND_EXPR && LA184_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA184_0==EVENT_PROP_EXPR||LA184_0==CONCAT||(LA184_0>=LIB_FUNC_CHAIN && LA184_0<=DOT_EXPR)||LA184_0==ARRAY_EXPR||(LA184_0>=NOT_IN_SET && LA184_0<=NOT_REGEXP)||(LA184_0>=IN_RANGE && LA184_0<=SUBSELECT_EXPR)||(LA184_0>=EXISTS_SUBSELECT_EXPR && LA184_0<=NOT_IN_SUBSELECT_EXPR)||LA184_0==SUBSTITUTION||(LA184_0>=FIRST_AGGREG && LA184_0<=WINDOW_AGGREG)||(LA184_0>=INT_TYPE && LA184_0<=NULL_TYPE)||(LA184_0>=STAR && LA184_0<=PLUS)||(LA184_0>=BAND && LA184_0<=BXOR)||(LA184_0>=LT && LA184_0<=GE)||(LA184_0>=MINUS && LA184_0<=MOD)) ) {
                            alt184=1;
                        }
                        switch (alt184) {
                            case 1 :
                                // EsperEPL2Ast.g:538:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:538:15: ( DISTINCT )?
                                int alt183=2;
                                int LA183_0 = input.LA(1);

                                if ( (LA183_0==DISTINCT) ) {
                                    alt183=1;
                                }
                                switch (alt183) {
                                    case 1 :
                                        // EsperEPL2Ast.g:538:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3682); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3686);
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
                    // EsperEPL2Ast.g:539:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3700); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:539:15: ( DISTINCT )?
                    int alt185=2;
                    int LA185_0 = input.LA(1);

                    if ( (LA185_0==DISTINCT) ) {
                        alt185=1;
                    }
                    switch (alt185) {
                        case 1 :
                            // EsperEPL2Ast.g:539:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3703); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3707);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:540:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3718); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:540:15: ( DISTINCT )?
                    int alt186=2;
                    int LA186_0 = input.LA(1);

                    if ( (LA186_0==DISTINCT) ) {
                        alt186=1;
                    }
                    switch (alt186) {
                        case 1 :
                            // EsperEPL2Ast.g:540:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3721); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3725);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:541:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3736); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:541:15: ( DISTINCT )?
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( (LA187_0==DISTINCT) ) {
                        alt187=1;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:541:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3739); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3743);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:542:4: ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3754); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:542:20: ( DISTINCT )?
                    int alt188=2;
                    int LA188_0 = input.LA(1);

                    if ( (LA188_0==DISTINCT) ) {
                        alt188=1;
                    }
                    switch (alt188) {
                        case 1 :
                            // EsperEPL2Ast.g:542:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3757); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3761);
                    accessValueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:542:48: ( valueExpr )?
                    int alt189=2;
                    int LA189_0 = input.LA(1);

                    if ( ((LA189_0>=IN_SET && LA189_0<=REGEXP)||LA189_0==NOT_EXPR||(LA189_0>=SUM && LA189_0<=AVG)||(LA189_0>=COALESCE && LA189_0<=COUNT)||(LA189_0>=CASE && LA189_0<=CASE2)||(LA189_0>=PREVIOUS && LA189_0<=EXISTS)||(LA189_0>=INSTANCEOF && LA189_0<=CURRENT_TIMESTAMP)||(LA189_0>=EVAL_AND_EXPR && LA189_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA189_0==EVENT_PROP_EXPR||LA189_0==CONCAT||(LA189_0>=LIB_FUNC_CHAIN && LA189_0<=DOT_EXPR)||LA189_0==ARRAY_EXPR||(LA189_0>=NOT_IN_SET && LA189_0<=NOT_REGEXP)||(LA189_0>=IN_RANGE && LA189_0<=SUBSELECT_EXPR)||(LA189_0>=EXISTS_SUBSELECT_EXPR && LA189_0<=NOT_IN_SUBSELECT_EXPR)||LA189_0==SUBSTITUTION||(LA189_0>=FIRST_AGGREG && LA189_0<=WINDOW_AGGREG)||(LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)||(LA189_0>=STAR && LA189_0<=PLUS)||(LA189_0>=BAND && LA189_0<=BXOR)||(LA189_0>=LT && LA189_0<=GE)||(LA189_0>=MINUS && LA189_0<=MOD)) ) {
                        alt189=1;
                    }
                    switch (alt189) {
                        case 1 :
                            // EsperEPL2Ast.g:542:48: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3763);
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
                    // EsperEPL2Ast.g:543:4: ^(f= FIRST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3775); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:543:21: ( DISTINCT )?
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( (LA190_0==DISTINCT) ) {
                        alt190=1;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:543:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3778); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3782);
                    accessValueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:543:49: ( valueExpr )?
                    int alt191=2;
                    int LA191_0 = input.LA(1);

                    if ( ((LA191_0>=IN_SET && LA191_0<=REGEXP)||LA191_0==NOT_EXPR||(LA191_0>=SUM && LA191_0<=AVG)||(LA191_0>=COALESCE && LA191_0<=COUNT)||(LA191_0>=CASE && LA191_0<=CASE2)||(LA191_0>=PREVIOUS && LA191_0<=EXISTS)||(LA191_0>=INSTANCEOF && LA191_0<=CURRENT_TIMESTAMP)||(LA191_0>=EVAL_AND_EXPR && LA191_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA191_0==EVENT_PROP_EXPR||LA191_0==CONCAT||(LA191_0>=LIB_FUNC_CHAIN && LA191_0<=DOT_EXPR)||LA191_0==ARRAY_EXPR||(LA191_0>=NOT_IN_SET && LA191_0<=NOT_REGEXP)||(LA191_0>=IN_RANGE && LA191_0<=SUBSELECT_EXPR)||(LA191_0>=EXISTS_SUBSELECT_EXPR && LA191_0<=NOT_IN_SUBSELECT_EXPR)||LA191_0==SUBSTITUTION||(LA191_0>=FIRST_AGGREG && LA191_0<=WINDOW_AGGREG)||(LA191_0>=INT_TYPE && LA191_0<=NULL_TYPE)||(LA191_0>=STAR && LA191_0<=PLUS)||(LA191_0>=BAND && LA191_0<=BXOR)||(LA191_0>=LT && LA191_0<=GE)||(LA191_0>=MINUS && LA191_0<=MOD)) ) {
                        alt191=1;
                    }
                    switch (alt191) {
                        case 1 :
                            // EsperEPL2Ast.g:543:49: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3784);
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
                    // EsperEPL2Ast.g:544:4: ^(f= WINDOW_AGGREG ( DISTINCT )? accessValueExpr )
                    {
                    f=(CommonTree)match(input,WINDOW_AGGREG,FOLLOW_WINDOW_AGGREG_in_builtinFunc3796); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:544:22: ( DISTINCT )?
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( (LA192_0==DISTINCT) ) {
                        alt192=1;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:544:23: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3799); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3803);
                    accessValueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:545:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3815); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3817);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3819);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:545:38: ( valueExpr )*
                    loop193:
                    do {
                        int alt193=2;
                        int LA193_0 = input.LA(1);

                        if ( ((LA193_0>=IN_SET && LA193_0<=REGEXP)||LA193_0==NOT_EXPR||(LA193_0>=SUM && LA193_0<=AVG)||(LA193_0>=COALESCE && LA193_0<=COUNT)||(LA193_0>=CASE && LA193_0<=CASE2)||(LA193_0>=PREVIOUS && LA193_0<=EXISTS)||(LA193_0>=INSTANCEOF && LA193_0<=CURRENT_TIMESTAMP)||(LA193_0>=EVAL_AND_EXPR && LA193_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA193_0==EVENT_PROP_EXPR||LA193_0==CONCAT||(LA193_0>=LIB_FUNC_CHAIN && LA193_0<=DOT_EXPR)||LA193_0==ARRAY_EXPR||(LA193_0>=NOT_IN_SET && LA193_0<=NOT_REGEXP)||(LA193_0>=IN_RANGE && LA193_0<=SUBSELECT_EXPR)||(LA193_0>=EXISTS_SUBSELECT_EXPR && LA193_0<=NOT_IN_SUBSELECT_EXPR)||LA193_0==SUBSTITUTION||(LA193_0>=FIRST_AGGREG && LA193_0<=WINDOW_AGGREG)||(LA193_0>=INT_TYPE && LA193_0<=NULL_TYPE)||(LA193_0>=STAR && LA193_0<=PLUS)||(LA193_0>=BAND && LA193_0<=BXOR)||(LA193_0>=LT && LA193_0<=GE)||(LA193_0>=MINUS && LA193_0<=MOD)) ) {
                            alt193=1;
                        }


                        switch (alt193) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:545:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3822);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop193;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:546:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3837); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3839);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:546:28: ( valueExpr )?
                    int alt194=2;
                    int LA194_0 = input.LA(1);

                    if ( ((LA194_0>=IN_SET && LA194_0<=REGEXP)||LA194_0==NOT_EXPR||(LA194_0>=SUM && LA194_0<=AVG)||(LA194_0>=COALESCE && LA194_0<=COUNT)||(LA194_0>=CASE && LA194_0<=CASE2)||(LA194_0>=PREVIOUS && LA194_0<=EXISTS)||(LA194_0>=INSTANCEOF && LA194_0<=CURRENT_TIMESTAMP)||(LA194_0>=EVAL_AND_EXPR && LA194_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA194_0==EVENT_PROP_EXPR||LA194_0==CONCAT||(LA194_0>=LIB_FUNC_CHAIN && LA194_0<=DOT_EXPR)||LA194_0==ARRAY_EXPR||(LA194_0>=NOT_IN_SET && LA194_0<=NOT_REGEXP)||(LA194_0>=IN_RANGE && LA194_0<=SUBSELECT_EXPR)||(LA194_0>=EXISTS_SUBSELECT_EXPR && LA194_0<=NOT_IN_SUBSELECT_EXPR)||LA194_0==SUBSTITUTION||(LA194_0>=FIRST_AGGREG && LA194_0<=WINDOW_AGGREG)||(LA194_0>=INT_TYPE && LA194_0<=NULL_TYPE)||(LA194_0>=STAR && LA194_0<=PLUS)||(LA194_0>=BAND && LA194_0<=BXOR)||(LA194_0>=LT && LA194_0<=GE)||(LA194_0>=MINUS && LA194_0<=MOD)) ) {
                        alt194=1;
                    }
                    switch (alt194) {
                        case 1 :
                            // EsperEPL2Ast.g:546:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3841);
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
                    // EsperEPL2Ast.g:547:5: ^(f= PREVIOUSTAIL valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUSTAIL,FOLLOW_PREVIOUSTAIL_in_builtinFunc3854); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3856);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:547:32: ( valueExpr )?
                    int alt195=2;
                    int LA195_0 = input.LA(1);

                    if ( ((LA195_0>=IN_SET && LA195_0<=REGEXP)||LA195_0==NOT_EXPR||(LA195_0>=SUM && LA195_0<=AVG)||(LA195_0>=COALESCE && LA195_0<=COUNT)||(LA195_0>=CASE && LA195_0<=CASE2)||(LA195_0>=PREVIOUS && LA195_0<=EXISTS)||(LA195_0>=INSTANCEOF && LA195_0<=CURRENT_TIMESTAMP)||(LA195_0>=EVAL_AND_EXPR && LA195_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA195_0==EVENT_PROP_EXPR||LA195_0==CONCAT||(LA195_0>=LIB_FUNC_CHAIN && LA195_0<=DOT_EXPR)||LA195_0==ARRAY_EXPR||(LA195_0>=NOT_IN_SET && LA195_0<=NOT_REGEXP)||(LA195_0>=IN_RANGE && LA195_0<=SUBSELECT_EXPR)||(LA195_0>=EXISTS_SUBSELECT_EXPR && LA195_0<=NOT_IN_SUBSELECT_EXPR)||LA195_0==SUBSTITUTION||(LA195_0>=FIRST_AGGREG && LA195_0<=WINDOW_AGGREG)||(LA195_0>=INT_TYPE && LA195_0<=NULL_TYPE)||(LA195_0>=STAR && LA195_0<=PLUS)||(LA195_0>=BAND && LA195_0<=BXOR)||(LA195_0>=LT && LA195_0<=GE)||(LA195_0>=MINUS && LA195_0<=MOD)) ) {
                        alt195=1;
                    }
                    switch (alt195) {
                        case 1 :
                            // EsperEPL2Ast.g:547:32: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3858);
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
                    // EsperEPL2Ast.g:548:5: ^(f= PREVIOUSCOUNT valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSCOUNT,FOLLOW_PREVIOUSCOUNT_in_builtinFunc3871); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3873);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:549:5: ^(f= PREVIOUSWINDOW valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSWINDOW,FOLLOW_PREVIOUSWINDOW_in_builtinFunc3885); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3887);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:550:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3899); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3903); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3905);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:551:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3918); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3920);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3922); 
                    // EsperEPL2Ast.g:551:42: ( CLASS_IDENT )*
                    loop196:
                    do {
                        int alt196=2;
                        int LA196_0 = input.LA(1);

                        if ( (LA196_0==CLASS_IDENT) ) {
                            alt196=1;
                        }


                        switch (alt196) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:551:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3925); 

                    	    }
                    	    break;

                    	default :
                    	    break loop196;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:552:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3939); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3941);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3943); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 18 :
                    // EsperEPL2Ast.g:553:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3955); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3957);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 19 :
                    // EsperEPL2Ast.g:554:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3969); 



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
    // EsperEPL2Ast.g:557:1: accessValueExpr : ( PROPERTY_WILDCARD_SELECT | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) | valueExpr );
    public final void accessValueExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:558:2: ( PROPERTY_WILDCARD_SELECT | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) | valueExpr )
            int alt199=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt199=1;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt199=2;
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
                alt199=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 199, 0, input);

                throw nvae;
            }

            switch (alt199) {
                case 1 :
                    // EsperEPL2Ast.g:558:5: PROPERTY_WILDCARD_SELECT
                    {
                    match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExpr3986); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:558:32: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExpr3993); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_accessValueExpr3995); 
                    // EsperEPL2Ast.g:558:68: ( IDENT )?
                    int alt198=2;
                    int LA198_0 = input.LA(1);

                    if ( (LA198_0==IDENT) ) {
                        alt198=1;
                    }
                    switch (alt198) {
                        case 1 :
                            // EsperEPL2Ast.g:558:68: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_accessValueExpr3997); 

                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:558:78: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_accessValueExpr4003);
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
    // EsperEPL2Ast.g:561:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:562:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:562:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr4020); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:562:19: ( valueExpr )*
                loop200:
                do {
                    int alt200=2;
                    int LA200_0 = input.LA(1);

                    if ( ((LA200_0>=IN_SET && LA200_0<=REGEXP)||LA200_0==NOT_EXPR||(LA200_0>=SUM && LA200_0<=AVG)||(LA200_0>=COALESCE && LA200_0<=COUNT)||(LA200_0>=CASE && LA200_0<=CASE2)||(LA200_0>=PREVIOUS && LA200_0<=EXISTS)||(LA200_0>=INSTANCEOF && LA200_0<=CURRENT_TIMESTAMP)||(LA200_0>=EVAL_AND_EXPR && LA200_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA200_0==EVENT_PROP_EXPR||LA200_0==CONCAT||(LA200_0>=LIB_FUNC_CHAIN && LA200_0<=DOT_EXPR)||LA200_0==ARRAY_EXPR||(LA200_0>=NOT_IN_SET && LA200_0<=NOT_REGEXP)||(LA200_0>=IN_RANGE && LA200_0<=SUBSELECT_EXPR)||(LA200_0>=EXISTS_SUBSELECT_EXPR && LA200_0<=NOT_IN_SUBSELECT_EXPR)||LA200_0==SUBSTITUTION||(LA200_0>=FIRST_AGGREG && LA200_0<=WINDOW_AGGREG)||(LA200_0>=INT_TYPE && LA200_0<=NULL_TYPE)||(LA200_0>=STAR && LA200_0<=PLUS)||(LA200_0>=BAND && LA200_0<=BXOR)||(LA200_0>=LT && LA200_0<=GE)||(LA200_0>=MINUS && LA200_0<=MOD)) ) {
                        alt200=1;
                    }


                    switch (alt200) {
                	case 1 :
                	    // EsperEPL2Ast.g:562:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr4023);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop200;
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
    // EsperEPL2Ast.g:565:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:566:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt202=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt202=1;
                }
                break;
            case MINUS:
                {
                alt202=2;
                }
                break;
            case DIV:
                {
                alt202=3;
                }
                break;
            case STAR:
                {
                alt202=4;
                }
                break;
            case MOD:
                {
                alt202=5;
                }
                break;
            case BAND:
                {
                alt202=6;
                }
                break;
            case BOR:
                {
                alt202=7;
                }
                break;
            case BXOR:
                {
                alt202=8;
                }
                break;
            case CONCAT:
                {
                alt202=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 202, 0, input);

                throw nvae;
            }

            switch (alt202) {
                case 1 :
                    // EsperEPL2Ast.g:566:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr4044); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4046);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4048);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:567:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr4060); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4062);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4064);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:568:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr4076); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4078);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4080);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:569:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr4091); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4093);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4095);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:570:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr4107); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4109);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4111);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:571:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr4122); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4124);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4126);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:572:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr4137); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4139);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4141);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:573:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr4152); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4154);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4156);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:574:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr4168); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4170);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4172);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:574:36: ( valueExpr )*
                    loop201:
                    do {
                        int alt201=2;
                        int LA201_0 = input.LA(1);

                        if ( ((LA201_0>=IN_SET && LA201_0<=REGEXP)||LA201_0==NOT_EXPR||(LA201_0>=SUM && LA201_0<=AVG)||(LA201_0>=COALESCE && LA201_0<=COUNT)||(LA201_0>=CASE && LA201_0<=CASE2)||(LA201_0>=PREVIOUS && LA201_0<=EXISTS)||(LA201_0>=INSTANCEOF && LA201_0<=CURRENT_TIMESTAMP)||(LA201_0>=EVAL_AND_EXPR && LA201_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA201_0==EVENT_PROP_EXPR||LA201_0==CONCAT||(LA201_0>=LIB_FUNC_CHAIN && LA201_0<=DOT_EXPR)||LA201_0==ARRAY_EXPR||(LA201_0>=NOT_IN_SET && LA201_0<=NOT_REGEXP)||(LA201_0>=IN_RANGE && LA201_0<=SUBSELECT_EXPR)||(LA201_0>=EXISTS_SUBSELECT_EXPR && LA201_0<=NOT_IN_SUBSELECT_EXPR)||LA201_0==SUBSTITUTION||(LA201_0>=FIRST_AGGREG && LA201_0<=WINDOW_AGGREG)||(LA201_0>=INT_TYPE && LA201_0<=NULL_TYPE)||(LA201_0>=STAR && LA201_0<=PLUS)||(LA201_0>=BAND && LA201_0<=BXOR)||(LA201_0>=LT && LA201_0<=GE)||(LA201_0>=MINUS && LA201_0<=MOD)) ) {
                            alt201=1;
                        }


                        switch (alt201) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:574:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4175);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop201;
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
    // EsperEPL2Ast.g:577:1: dotExpr : ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* ) ;
    public final void dotExpr() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:578:2: ( ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* ) )
            // EsperEPL2Ast.g:578:4: ^(d= DOT_EXPR valueExpr ( libFunctionWithClass )* )
            {
            d=(CommonTree)match(input,DOT_EXPR,FOLLOW_DOT_EXPR_in_dotExpr4195); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dotExpr4197);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:578:27: ( libFunctionWithClass )*
            loop203:
            do {
                int alt203=2;
                int LA203_0 = input.LA(1);

                if ( (LA203_0==LIB_FUNCTION) ) {
                    alt203=1;
                }


                switch (alt203) {
            	case 1 :
            	    // EsperEPL2Ast.g:578:27: libFunctionWithClass
            	    {
            	    pushFollow(FOLLOW_libFunctionWithClass_in_dotExpr4199);
            	    libFunctionWithClass();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop203;
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
    // EsperEPL2Ast.g:581:1: libFuncChain : ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* ) ;
    public final void libFuncChain() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:582:2: ( ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* ) )
            // EsperEPL2Ast.g:582:6: ^(l= LIB_FUNC_CHAIN libFunctionWithClass ( libOrPropFunction )* )
            {
            l=(CommonTree)match(input,LIB_FUNC_CHAIN,FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4219); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_libFunctionWithClass_in_libFuncChain4221);
            libFunctionWithClass();

            state._fsp--;

            // EsperEPL2Ast.g:582:46: ( libOrPropFunction )*
            loop204:
            do {
                int alt204=2;
                int LA204_0 = input.LA(1);

                if ( (LA204_0==EVENT_PROP_EXPR||LA204_0==LIB_FUNCTION) ) {
                    alt204=1;
                }


                switch (alt204) {
            	case 1 :
            	    // EsperEPL2Ast.g:582:46: libOrPropFunction
            	    {
            	    pushFollow(FOLLOW_libOrPropFunction_in_libFuncChain4223);
            	    libOrPropFunction();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop204;
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
    // EsperEPL2Ast.g:585:1: libFunctionWithClass : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunctionWithClass() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:586:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:586:6: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4243); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:586:23: ( CLASS_IDENT )?
            int alt205=2;
            int LA205_0 = input.LA(1);

            if ( (LA205_0==CLASS_IDENT) ) {
                alt205=1;
            }
            switch (alt205) {
                case 1 :
                    // EsperEPL2Ast.g:586:24: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunctionWithClass4246); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunctionWithClass4250); 
            // EsperEPL2Ast.g:586:44: ( DISTINCT )?
            int alt206=2;
            int LA206_0 = input.LA(1);

            if ( (LA206_0==DISTINCT) ) {
                alt206=1;
            }
            switch (alt206) {
                case 1 :
                    // EsperEPL2Ast.g:586:45: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunctionWithClass4253); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:586:56: ( valueExpr )*
            loop207:
            do {
                int alt207=2;
                int LA207_0 = input.LA(1);

                if ( ((LA207_0>=IN_SET && LA207_0<=REGEXP)||LA207_0==NOT_EXPR||(LA207_0>=SUM && LA207_0<=AVG)||(LA207_0>=COALESCE && LA207_0<=COUNT)||(LA207_0>=CASE && LA207_0<=CASE2)||(LA207_0>=PREVIOUS && LA207_0<=EXISTS)||(LA207_0>=INSTANCEOF && LA207_0<=CURRENT_TIMESTAMP)||(LA207_0>=EVAL_AND_EXPR && LA207_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA207_0==EVENT_PROP_EXPR||LA207_0==CONCAT||(LA207_0>=LIB_FUNC_CHAIN && LA207_0<=DOT_EXPR)||LA207_0==ARRAY_EXPR||(LA207_0>=NOT_IN_SET && LA207_0<=NOT_REGEXP)||(LA207_0>=IN_RANGE && LA207_0<=SUBSELECT_EXPR)||(LA207_0>=EXISTS_SUBSELECT_EXPR && LA207_0<=NOT_IN_SUBSELECT_EXPR)||LA207_0==SUBSTITUTION||(LA207_0>=FIRST_AGGREG && LA207_0<=WINDOW_AGGREG)||(LA207_0>=INT_TYPE && LA207_0<=NULL_TYPE)||(LA207_0>=STAR && LA207_0<=PLUS)||(LA207_0>=BAND && LA207_0<=BXOR)||(LA207_0>=LT && LA207_0<=GE)||(LA207_0>=MINUS && LA207_0<=MOD)) ) {
                    alt207=1;
                }


                switch (alt207) {
            	case 1 :
            	    // EsperEPL2Ast.g:586:57: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunctionWithClass4258);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop207;
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
    // EsperEPL2Ast.g:589:1: libOrPropFunction : ( eventPropertyExpr[false] | libFunctionWithClass );
    public final void libOrPropFunction() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:590:2: ( eventPropertyExpr[false] | libFunctionWithClass )
            int alt208=2;
            int LA208_0 = input.LA(1);

            if ( (LA208_0==EVENT_PROP_EXPR) ) {
                alt208=1;
            }
            else if ( (LA208_0==LIB_FUNCTION) ) {
                alt208=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 208, 0, input);

                throw nvae;
            }
            switch (alt208) {
                case 1 :
                    // EsperEPL2Ast.g:590:7: eventPropertyExpr[false]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_libOrPropFunction4276);
                    eventPropertyExpr(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:591:7: libFunctionWithClass
                    {
                    pushFollow(FOLLOW_libFunctionWithClass_in_libOrPropFunction4286);
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
    // EsperEPL2Ast.g:597:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:598:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:598:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:598:4: ( annotation[true] )*
            loop209:
            do {
                int alt209=2;
                int LA209_0 = input.LA(1);

                if ( (LA209_0==ANNOTATION) ) {
                    alt209=1;
                }


                switch (alt209) {
            	case 1 :
            	    // EsperEPL2Ast.g:598:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule4301);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop209;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule4305);
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
    // EsperEPL2Ast.g:601:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:602:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt214=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt214=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt214=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt214=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt214=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt214=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt214=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt214=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 214, 0, input);

                throw nvae;
            }

            switch (alt214) {
                case 1 :
                    // EsperEPL2Ast.g:602:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice4319);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:603:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice4324);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:604:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice4334); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4336);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:605:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4350); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice4352);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4354);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:606:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4368); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4370);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:607:5: ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice4384); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4386);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:607:32: ( IDENT IDENT ( valueExprWithTime )* | valueExpr )
                    int alt211=2;
                    int LA211_0 = input.LA(1);

                    if ( (LA211_0==IDENT) ) {
                        alt211=1;
                    }
                    else if ( ((LA211_0>=IN_SET && LA211_0<=REGEXP)||LA211_0==NOT_EXPR||(LA211_0>=SUM && LA211_0<=AVG)||(LA211_0>=COALESCE && LA211_0<=COUNT)||(LA211_0>=CASE && LA211_0<=CASE2)||(LA211_0>=PREVIOUS && LA211_0<=EXISTS)||(LA211_0>=INSTANCEOF && LA211_0<=CURRENT_TIMESTAMP)||(LA211_0>=EVAL_AND_EXPR && LA211_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA211_0==EVENT_PROP_EXPR||LA211_0==CONCAT||(LA211_0>=LIB_FUNC_CHAIN && LA211_0<=DOT_EXPR)||LA211_0==ARRAY_EXPR||(LA211_0>=NOT_IN_SET && LA211_0<=NOT_REGEXP)||(LA211_0>=IN_RANGE && LA211_0<=SUBSELECT_EXPR)||(LA211_0>=EXISTS_SUBSELECT_EXPR && LA211_0<=NOT_IN_SUBSELECT_EXPR)||LA211_0==SUBSTITUTION||(LA211_0>=FIRST_AGGREG && LA211_0<=WINDOW_AGGREG)||(LA211_0>=INT_TYPE && LA211_0<=NULL_TYPE)||(LA211_0>=STAR && LA211_0<=PLUS)||(LA211_0>=BAND && LA211_0<=BXOR)||(LA211_0>=LT && LA211_0<=GE)||(LA211_0>=MINUS && LA211_0<=MOD)) ) {
                        alt211=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 211, 0, input);

                        throw nvae;
                    }
                    switch (alt211) {
                        case 1 :
                            // EsperEPL2Ast.g:607:33: IDENT IDENT ( valueExprWithTime )*
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4389); 
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4391); 
                            // EsperEPL2Ast.g:607:45: ( valueExprWithTime )*
                            loop210:
                            do {
                                int alt210=2;
                                int LA210_0 = input.LA(1);

                                if ( ((LA210_0>=IN_SET && LA210_0<=REGEXP)||LA210_0==NOT_EXPR||(LA210_0>=SUM && LA210_0<=AVG)||(LA210_0>=COALESCE && LA210_0<=COUNT)||(LA210_0>=CASE && LA210_0<=CASE2)||LA210_0==LAST||(LA210_0>=PREVIOUS && LA210_0<=EXISTS)||(LA210_0>=LW && LA210_0<=CURRENT_TIMESTAMP)||(LA210_0>=NUMERIC_PARAM_RANGE && LA210_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA210_0>=EVAL_AND_EXPR && LA210_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA210_0==EVENT_PROP_EXPR||LA210_0==CONCAT||(LA210_0>=LIB_FUNC_CHAIN && LA210_0<=DOT_EXPR)||(LA210_0>=TIME_PERIOD && LA210_0<=ARRAY_EXPR)||(LA210_0>=NOT_IN_SET && LA210_0<=NOT_REGEXP)||(LA210_0>=IN_RANGE && LA210_0<=SUBSELECT_EXPR)||(LA210_0>=EXISTS_SUBSELECT_EXPR && LA210_0<=NOT_IN_SUBSELECT_EXPR)||(LA210_0>=LAST_OPERATOR && LA210_0<=SUBSTITUTION)||LA210_0==NUMBERSETSTAR||(LA210_0>=FIRST_AGGREG && LA210_0<=WINDOW_AGGREG)||(LA210_0>=INT_TYPE && LA210_0<=NULL_TYPE)||(LA210_0>=STAR && LA210_0<=PLUS)||(LA210_0>=BAND && LA210_0<=BXOR)||(LA210_0>=LT && LA210_0<=GE)||(LA210_0>=MINUS && LA210_0<=MOD)) ) {
                                    alt210=1;
                                }


                                switch (alt210) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:607:45: valueExprWithTime
                            	    {
                            	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice4393);
                            	    valueExprWithTime();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop210;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:607:66: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_exprChoice4398);
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
                    // EsperEPL2Ast.g:608:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4412); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:608:26: ( matchUntilRange )?
                    int alt212=2;
                    int LA212_0 = input.LA(1);

                    if ( ((LA212_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA212_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt212=1;
                    }
                    switch (alt212) {
                        case 1 :
                            // EsperEPL2Ast.g:608:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4414);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4417);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:608:54: ( exprChoice )?
                    int alt213=2;
                    int LA213_0 = input.LA(1);

                    if ( ((LA213_0>=OR_EXPR && LA213_0<=AND_EXPR)||(LA213_0>=EVERY_EXPR && LA213_0<=EVERY_DISTINCT_EXPR)||LA213_0==FOLLOWED_BY_EXPR||(LA213_0>=PATTERN_FILTER_EXPR && LA213_0<=PATTERN_NOT_EXPR)||(LA213_0>=GUARD_EXPR && LA213_0<=OBSERVER_EXPR)||LA213_0==MATCH_UNTIL_EXPR) ) {
                        alt213=1;
                    }
                    switch (alt213) {
                        case 1 :
                            // EsperEPL2Ast.g:608:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4419);
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
    // EsperEPL2Ast.g:612:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:613:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:613:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4440); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:613:35: ( valueExpr )+
            int cnt215=0;
            loop215:
            do {
                int alt215=2;
                int LA215_0 = input.LA(1);

                if ( ((LA215_0>=IN_SET && LA215_0<=REGEXP)||LA215_0==NOT_EXPR||(LA215_0>=SUM && LA215_0<=AVG)||(LA215_0>=COALESCE && LA215_0<=COUNT)||(LA215_0>=CASE && LA215_0<=CASE2)||(LA215_0>=PREVIOUS && LA215_0<=EXISTS)||(LA215_0>=INSTANCEOF && LA215_0<=CURRENT_TIMESTAMP)||(LA215_0>=EVAL_AND_EXPR && LA215_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA215_0==EVENT_PROP_EXPR||LA215_0==CONCAT||(LA215_0>=LIB_FUNC_CHAIN && LA215_0<=DOT_EXPR)||LA215_0==ARRAY_EXPR||(LA215_0>=NOT_IN_SET && LA215_0<=NOT_REGEXP)||(LA215_0>=IN_RANGE && LA215_0<=SUBSELECT_EXPR)||(LA215_0>=EXISTS_SUBSELECT_EXPR && LA215_0<=NOT_IN_SUBSELECT_EXPR)||LA215_0==SUBSTITUTION||(LA215_0>=FIRST_AGGREG && LA215_0<=WINDOW_AGGREG)||(LA215_0>=INT_TYPE && LA215_0<=NULL_TYPE)||(LA215_0>=STAR && LA215_0<=PLUS)||(LA215_0>=BAND && LA215_0<=BXOR)||(LA215_0>=LT && LA215_0<=GE)||(LA215_0>=MINUS && LA215_0<=MOD)) ) {
                    alt215=1;
                }


                switch (alt215) {
            	case 1 :
            	    // EsperEPL2Ast.g:613:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions4442);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt215 >= 1 ) break loop215;
                        EarlyExitException eee =
                            new EarlyExitException(215, input);
                        throw eee;
                }
                cnt215++;
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
    // EsperEPL2Ast.g:616:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:617:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt219=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt219=1;
                }
                break;
            case OR_EXPR:
                {
                alt219=2;
                }
                break;
            case AND_EXPR:
                {
                alt219=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 219, 0, input);

                throw nvae;
            }

            switch (alt219) {
                case 1 :
                    // EsperEPL2Ast.g:617:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4461); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4463);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4465);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:617:48: ( exprChoice )*
                    loop216:
                    do {
                        int alt216=2;
                        int LA216_0 = input.LA(1);

                        if ( ((LA216_0>=OR_EXPR && LA216_0<=AND_EXPR)||(LA216_0>=EVERY_EXPR && LA216_0<=EVERY_DISTINCT_EXPR)||LA216_0==FOLLOWED_BY_EXPR||(LA216_0>=PATTERN_FILTER_EXPR && LA216_0<=PATTERN_NOT_EXPR)||(LA216_0>=GUARD_EXPR && LA216_0<=OBSERVER_EXPR)||LA216_0==MATCH_UNTIL_EXPR) ) {
                            alt216=1;
                        }


                        switch (alt216) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:617:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4468);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop216;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:618:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4484); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4486);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4488);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:618:40: ( exprChoice )*
                    loop217:
                    do {
                        int alt217=2;
                        int LA217_0 = input.LA(1);

                        if ( ((LA217_0>=OR_EXPR && LA217_0<=AND_EXPR)||(LA217_0>=EVERY_EXPR && LA217_0<=EVERY_DISTINCT_EXPR)||LA217_0==FOLLOWED_BY_EXPR||(LA217_0>=PATTERN_FILTER_EXPR && LA217_0<=PATTERN_NOT_EXPR)||(LA217_0>=GUARD_EXPR && LA217_0<=OBSERVER_EXPR)||LA217_0==MATCH_UNTIL_EXPR) ) {
                            alt217=1;
                        }


                        switch (alt217) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:618:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4491);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop217;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:619:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4507); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4509);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4511);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:619:41: ( exprChoice )*
                    loop218:
                    do {
                        int alt218=2;
                        int LA218_0 = input.LA(1);

                        if ( ((LA218_0>=OR_EXPR && LA218_0<=AND_EXPR)||(LA218_0>=EVERY_EXPR && LA218_0<=EVERY_DISTINCT_EXPR)||LA218_0==FOLLOWED_BY_EXPR||(LA218_0>=PATTERN_FILTER_EXPR && LA218_0<=PATTERN_NOT_EXPR)||(LA218_0>=GUARD_EXPR && LA218_0<=OBSERVER_EXPR)||LA218_0==MATCH_UNTIL_EXPR) ) {
                            alt218=1;
                        }


                        switch (alt218) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:619:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4514);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop218;
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


    // $ANTLR start "atomicExpr"
    // EsperEPL2Ast.g:622:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:623:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt221=2;
            int LA221_0 = input.LA(1);

            if ( (LA221_0==PATTERN_FILTER_EXPR) ) {
                alt221=1;
            }
            else if ( (LA221_0==OBSERVER_EXPR) ) {
                alt221=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 221, 0, input);

                throw nvae;
            }
            switch (alt221) {
                case 1 :
                    // EsperEPL2Ast.g:623:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4533);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:624:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4545); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4547); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4549); 
                    // EsperEPL2Ast.g:624:39: ( valueExprWithTime )*
                    loop220:
                    do {
                        int alt220=2;
                        int LA220_0 = input.LA(1);

                        if ( ((LA220_0>=IN_SET && LA220_0<=REGEXP)||LA220_0==NOT_EXPR||(LA220_0>=SUM && LA220_0<=AVG)||(LA220_0>=COALESCE && LA220_0<=COUNT)||(LA220_0>=CASE && LA220_0<=CASE2)||LA220_0==LAST||(LA220_0>=PREVIOUS && LA220_0<=EXISTS)||(LA220_0>=LW && LA220_0<=CURRENT_TIMESTAMP)||(LA220_0>=NUMERIC_PARAM_RANGE && LA220_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA220_0>=EVAL_AND_EXPR && LA220_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA220_0==EVENT_PROP_EXPR||LA220_0==CONCAT||(LA220_0>=LIB_FUNC_CHAIN && LA220_0<=DOT_EXPR)||(LA220_0>=TIME_PERIOD && LA220_0<=ARRAY_EXPR)||(LA220_0>=NOT_IN_SET && LA220_0<=NOT_REGEXP)||(LA220_0>=IN_RANGE && LA220_0<=SUBSELECT_EXPR)||(LA220_0>=EXISTS_SUBSELECT_EXPR && LA220_0<=NOT_IN_SUBSELECT_EXPR)||(LA220_0>=LAST_OPERATOR && LA220_0<=SUBSTITUTION)||LA220_0==NUMBERSETSTAR||(LA220_0>=FIRST_AGGREG && LA220_0<=WINDOW_AGGREG)||(LA220_0>=INT_TYPE && LA220_0<=NULL_TYPE)||(LA220_0>=STAR && LA220_0<=PLUS)||(LA220_0>=BAND && LA220_0<=BXOR)||(LA220_0>=LT && LA220_0<=GE)||(LA220_0>=MINUS && LA220_0<=MOD)) ) {
                            alt220=1;
                        }


                        switch (alt220) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:624:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4551);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop220;
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
    // EsperEPL2Ast.g:627:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:628:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:628:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4571); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:628:29: ( IDENT )?
            int alt222=2;
            int LA222_0 = input.LA(1);

            if ( (LA222_0==IDENT) ) {
                alt222=1;
            }
            switch (alt222) {
                case 1 :
                    // EsperEPL2Ast.g:628:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4573); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4576); 
            // EsperEPL2Ast.g:628:48: ( propertyExpression )?
            int alt223=2;
            int LA223_0 = input.LA(1);

            if ( (LA223_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt223=1;
            }
            switch (alt223) {
                case 1 :
                    // EsperEPL2Ast.g:628:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4578);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:628:68: ( valueExpr )*
            loop224:
            do {
                int alt224=2;
                int LA224_0 = input.LA(1);

                if ( ((LA224_0>=IN_SET && LA224_0<=REGEXP)||LA224_0==NOT_EXPR||(LA224_0>=SUM && LA224_0<=AVG)||(LA224_0>=COALESCE && LA224_0<=COUNT)||(LA224_0>=CASE && LA224_0<=CASE2)||(LA224_0>=PREVIOUS && LA224_0<=EXISTS)||(LA224_0>=INSTANCEOF && LA224_0<=CURRENT_TIMESTAMP)||(LA224_0>=EVAL_AND_EXPR && LA224_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA224_0==EVENT_PROP_EXPR||LA224_0==CONCAT||(LA224_0>=LIB_FUNC_CHAIN && LA224_0<=DOT_EXPR)||LA224_0==ARRAY_EXPR||(LA224_0>=NOT_IN_SET && LA224_0<=NOT_REGEXP)||(LA224_0>=IN_RANGE && LA224_0<=SUBSELECT_EXPR)||(LA224_0>=EXISTS_SUBSELECT_EXPR && LA224_0<=NOT_IN_SUBSELECT_EXPR)||LA224_0==SUBSTITUTION||(LA224_0>=FIRST_AGGREG && LA224_0<=WINDOW_AGGREG)||(LA224_0>=INT_TYPE && LA224_0<=NULL_TYPE)||(LA224_0>=STAR && LA224_0<=PLUS)||(LA224_0>=BAND && LA224_0<=BXOR)||(LA224_0>=LT && LA224_0<=GE)||(LA224_0>=MINUS && LA224_0<=MOD)) ) {
                    alt224=1;
                }


                switch (alt224) {
            	case 1 :
            	    // EsperEPL2Ast.g:628:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4582);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop224;
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
    // EsperEPL2Ast.g:631:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr ) | ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:632:2: ( ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr ) | ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr ) | ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr ) )
            int alt225=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt225=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt225=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt225=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt225=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 225, 0, input);

                throw nvae;
            }

            switch (alt225) {
                case 1 :
                    // EsperEPL2Ast.g:632:4: ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4600); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4602);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4604);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:633:5: ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4612); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4614);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:634:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4622); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4624);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:635:4: ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4631); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4633);
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
    // EsperEPL2Ast.g:638:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:639:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:639:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4646); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4648);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:639:35: ( valueExpr )*
            loop226:
            do {
                int alt226=2;
                int LA226_0 = input.LA(1);

                if ( ((LA226_0>=IN_SET && LA226_0<=REGEXP)||LA226_0==NOT_EXPR||(LA226_0>=SUM && LA226_0<=AVG)||(LA226_0>=COALESCE && LA226_0<=COUNT)||(LA226_0>=CASE && LA226_0<=CASE2)||(LA226_0>=PREVIOUS && LA226_0<=EXISTS)||(LA226_0>=INSTANCEOF && LA226_0<=CURRENT_TIMESTAMP)||(LA226_0>=EVAL_AND_EXPR && LA226_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA226_0==EVENT_PROP_EXPR||LA226_0==CONCAT||(LA226_0>=LIB_FUNC_CHAIN && LA226_0<=DOT_EXPR)||LA226_0==ARRAY_EXPR||(LA226_0>=NOT_IN_SET && LA226_0<=NOT_REGEXP)||(LA226_0>=IN_RANGE && LA226_0<=SUBSELECT_EXPR)||(LA226_0>=EXISTS_SUBSELECT_EXPR && LA226_0<=NOT_IN_SUBSELECT_EXPR)||LA226_0==SUBSTITUTION||(LA226_0>=FIRST_AGGREG && LA226_0<=WINDOW_AGGREG)||(LA226_0>=INT_TYPE && LA226_0<=NULL_TYPE)||(LA226_0>=STAR && LA226_0<=PLUS)||(LA226_0>=BAND && LA226_0<=BXOR)||(LA226_0>=LT && LA226_0<=GE)||(LA226_0>=MINUS && LA226_0<=MOD)) ) {
                    alt226=1;
                }


                switch (alt226) {
            	case 1 :
            	    // EsperEPL2Ast.g:639:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4651);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop226;
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
    // EsperEPL2Ast.g:642:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:643:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt239=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt239=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt239=2;
                }
                break;
            case LT:
                {
                alt239=3;
                }
                break;
            case LE:
                {
                alt239=4;
                }
                break;
            case GT:
                {
                alt239=5;
                }
                break;
            case GE:
                {
                alt239=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt239=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt239=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt239=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt239=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt239=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt239=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 239, 0, input);

                throw nvae;
            }

            switch (alt239) {
                case 1 :
                    // EsperEPL2Ast.g:643:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4667); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4669);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:644:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4676); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4678);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:645:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4685); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4687);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:646:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4694); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4696);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:647:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4703); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4705);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:648:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4712); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4714);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:649:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4721); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:649:41: ( constant[false] | filterIdentifier )
                    int alt227=2;
                    int LA227_0 = input.LA(1);

                    if ( ((LA227_0>=INT_TYPE && LA227_0<=NULL_TYPE)) ) {
                        alt227=1;
                    }
                    else if ( (LA227_0==EVENT_FILTER_IDENT) ) {
                        alt227=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 227, 0, input);

                        throw nvae;
                    }
                    switch (alt227) {
                        case 1 :
                            // EsperEPL2Ast.g:649:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4730);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:649:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4733);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:649:76: ( constant[false] | filterIdentifier )
                    int alt228=2;
                    int LA228_0 = input.LA(1);

                    if ( ((LA228_0>=INT_TYPE && LA228_0<=NULL_TYPE)) ) {
                        alt228=1;
                    }
                    else if ( (LA228_0==EVENT_FILTER_IDENT) ) {
                        alt228=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 228, 0, input);

                        throw nvae;
                    }
                    switch (alt228) {
                        case 1 :
                            // EsperEPL2Ast.g:649:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4737);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:649:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4740);
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
                    // EsperEPL2Ast.g:650:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4754); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:650:45: ( constant[false] | filterIdentifier )
                    int alt229=2;
                    int LA229_0 = input.LA(1);

                    if ( ((LA229_0>=INT_TYPE && LA229_0<=NULL_TYPE)) ) {
                        alt229=1;
                    }
                    else if ( (LA229_0==EVENT_FILTER_IDENT) ) {
                        alt229=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 229, 0, input);

                        throw nvae;
                    }
                    switch (alt229) {
                        case 1 :
                            // EsperEPL2Ast.g:650:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4763);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:650:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4766);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:650:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:650:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4770);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:650:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4773);
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
                    // EsperEPL2Ast.g:651:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4787); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:651:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:651:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4796);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:651:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4799);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:651:73: ( constant[false] | filterIdentifier )*
                    loop232:
                    do {
                        int alt232=3;
                        int LA232_0 = input.LA(1);

                        if ( ((LA232_0>=INT_TYPE && LA232_0<=NULL_TYPE)) ) {
                            alt232=1;
                        }
                        else if ( (LA232_0==EVENT_FILTER_IDENT) ) {
                            alt232=2;
                        }


                        switch (alt232) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:651:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4803);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:651:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4806);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop232;
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
                    // EsperEPL2Ast.g:652:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4821); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:652:42: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:652:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4830);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:652:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4833);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:652:77: ( constant[false] | filterIdentifier )*
                    loop234:
                    do {
                        int alt234=3;
                        int LA234_0 = input.LA(1);

                        if ( ((LA234_0>=INT_TYPE && LA234_0<=NULL_TYPE)) ) {
                            alt234=1;
                        }
                        else if ( (LA234_0==EVENT_FILTER_IDENT) ) {
                            alt234=2;
                        }


                        switch (alt234) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:652:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4837);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:652:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4840);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop234;
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
                    // EsperEPL2Ast.g:653:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4855); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:653:27: ( constant[false] | filterIdentifier )
                    int alt235=2;
                    int LA235_0 = input.LA(1);

                    if ( ((LA235_0>=INT_TYPE && LA235_0<=NULL_TYPE)) ) {
                        alt235=1;
                    }
                    else if ( (LA235_0==EVENT_FILTER_IDENT) ) {
                        alt235=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 235, 0, input);

                        throw nvae;
                    }
                    switch (alt235) {
                        case 1 :
                            // EsperEPL2Ast.g:653:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4858);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:653:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4861);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:653:62: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:653:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4865);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:653:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4868);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:654:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4876); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:654:31: ( constant[false] | filterIdentifier )
                    int alt237=2;
                    int LA237_0 = input.LA(1);

                    if ( ((LA237_0>=INT_TYPE && LA237_0<=NULL_TYPE)) ) {
                        alt237=1;
                    }
                    else if ( (LA237_0==EVENT_FILTER_IDENT) ) {
                        alt237=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 237, 0, input);

                        throw nvae;
                    }
                    switch (alt237) {
                        case 1 :
                            // EsperEPL2Ast.g:654:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4879);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:654:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4882);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:654:66: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:654:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4886);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:654:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4889);
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
    // EsperEPL2Ast.g:657:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:658:2: ( constant[false] | filterIdentifier )
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
                    // EsperEPL2Ast.g:658:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4903);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:659:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4909);
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
    // EsperEPL2Ast.g:661:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:662:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:662:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4920); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4922); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4924);
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
    // EsperEPL2Ast.g:665:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:666:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:666:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4943); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4945);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:666:44: ( eventPropertyAtomic )*
            loop241:
            do {
                int alt241=2;
                int LA241_0 = input.LA(1);

                if ( ((LA241_0>=EVENT_PROP_SIMPLE && LA241_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt241=1;
                }


                switch (alt241) {
            	case 1 :
            	    // EsperEPL2Ast.g:666:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4948);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop241;
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
    // EsperEPL2Ast.g:669:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:670:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt242=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt242=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt242=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt242=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt242=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt242=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt242=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 242, 0, input);

                throw nvae;
            }

            switch (alt242) {
                case 1 :
                    // EsperEPL2Ast.g:670:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4967); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4969); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:671:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4976); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4978); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4980); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:672:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4987); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4989); 
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
                    // EsperEPL2Ast.g:673:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5004); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5006); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:674:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5013); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5015); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic5017); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:675:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5024); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5026); 
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
    // EsperEPL2Ast.g:678:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:679:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:679:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod5053); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod5055);
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
    // EsperEPL2Ast.g:682:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:683:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt253=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt253=1;
                }
                break;
            case HOUR_PART:
                {
                alt253=2;
                }
                break;
            case MINUTE_PART:
                {
                alt253=3;
                }
                break;
            case SECOND_PART:
                {
                alt253=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt253=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 253, 0, input);

                throw nvae;
            }

            switch (alt253) {
                case 1 :
                    // EsperEPL2Ast.g:683:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef5071);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:683:13: ( hourPart )?
                    int alt243=2;
                    int LA243_0 = input.LA(1);

                    if ( (LA243_0==HOUR_PART) ) {
                        alt243=1;
                    }
                    switch (alt243) {
                        case 1 :
                            // EsperEPL2Ast.g:683:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5074);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:683:25: ( minutePart )?
                    int alt244=2;
                    int LA244_0 = input.LA(1);

                    if ( (LA244_0==MINUTE_PART) ) {
                        alt244=1;
                    }
                    switch (alt244) {
                        case 1 :
                            // EsperEPL2Ast.g:683:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5079);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:683:39: ( secondPart )?
                    int alt245=2;
                    int LA245_0 = input.LA(1);

                    if ( (LA245_0==SECOND_PART) ) {
                        alt245=1;
                    }
                    switch (alt245) {
                        case 1 :
                            // EsperEPL2Ast.g:683:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5084);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:683:53: ( millisecondPart )?
                    int alt246=2;
                    int LA246_0 = input.LA(1);

                    if ( (LA246_0==MILLISECOND_PART) ) {
                        alt246=1;
                    }
                    switch (alt246) {
                        case 1 :
                            // EsperEPL2Ast.g:683:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5089);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:684:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef5096);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:684:13: ( minutePart )?
                    int alt247=2;
                    int LA247_0 = input.LA(1);

                    if ( (LA247_0==MINUTE_PART) ) {
                        alt247=1;
                    }
                    switch (alt247) {
                        case 1 :
                            // EsperEPL2Ast.g:684:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5099);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:684:27: ( secondPart )?
                    int alt248=2;
                    int LA248_0 = input.LA(1);

                    if ( (LA248_0==SECOND_PART) ) {
                        alt248=1;
                    }
                    switch (alt248) {
                        case 1 :
                            // EsperEPL2Ast.g:684:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5104);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:684:41: ( millisecondPart )?
                    int alt249=2;
                    int LA249_0 = input.LA(1);

                    if ( (LA249_0==MILLISECOND_PART) ) {
                        alt249=1;
                    }
                    switch (alt249) {
                        case 1 :
                            // EsperEPL2Ast.g:684:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5109);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:685:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef5116);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:685:15: ( secondPart )?
                    int alt250=2;
                    int LA250_0 = input.LA(1);

                    if ( (LA250_0==SECOND_PART) ) {
                        alt250=1;
                    }
                    switch (alt250) {
                        case 1 :
                            // EsperEPL2Ast.g:685:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5119);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:685:29: ( millisecondPart )?
                    int alt251=2;
                    int LA251_0 = input.LA(1);

                    if ( (LA251_0==MILLISECOND_PART) ) {
                        alt251=1;
                    }
                    switch (alt251) {
                        case 1 :
                            // EsperEPL2Ast.g:685:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5124);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:686:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef5131);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:686:15: ( millisecondPart )?
                    int alt252=2;
                    int LA252_0 = input.LA(1);

                    if ( (LA252_0==MILLISECOND_PART) ) {
                        alt252=1;
                    }
                    switch (alt252) {
                        case 1 :
                            // EsperEPL2Ast.g:686:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5134);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:687:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5141);
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


    // $ANTLR start "dayPart"
    // EsperEPL2Ast.g:690:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:691:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:691:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart5155); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart5157);
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
    // EsperEPL2Ast.g:694:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:695:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:695:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart5172); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart5174);
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
    // EsperEPL2Ast.g:698:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:699:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:699:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart5189); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart5191);
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
    // EsperEPL2Ast.g:702:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:703:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:703:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart5206); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart5208);
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
    // EsperEPL2Ast.g:706:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:707:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:707:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart5223); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart5225);
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
    // EsperEPL2Ast.g:710:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:711:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:711:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution5240); 
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
    // EsperEPL2Ast.g:714:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:715:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt254=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt254=1;
                }
                break;
            case LONG_TYPE:
                {
                alt254=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt254=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt254=4;
                }
                break;
            case STRING_TYPE:
                {
                alt254=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt254=6;
                }
                break;
            case NULL_TYPE:
                {
                alt254=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 254, 0, input);

                throw nvae;
            }

            switch (alt254) {
                case 1 :
                    // EsperEPL2Ast.g:715:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant5256); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:716:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant5265); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:717:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant5274); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:718:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant5283); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:719:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant5299); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:720:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant5315); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:721:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant5328); 
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
    // EsperEPL2Ast.g:724:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:725:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0xF001C00000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0xF001C00000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0xF000C00000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0xF000C00000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000008000000L,0x0050401005800000L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_createIndexExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_createSchemaExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule245 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule249 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_forExpr_in_eplExpressionRule252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr271 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onStreamExpr_in_onExpr273 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000008F0000000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr278 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onUpdateExpr_in_onExpr282 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr289 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000300000000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr299 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onMergeExpr_in_onExpr303 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_STREAM_in_onStreamExpr325 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onStreamExpr328 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onStreamExpr332 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_onStreamExpr335 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_MERGE_EXPR_in_onMergeExpr353 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr355 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr357 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L});
    public static final BitSet FOLLOW_mergeMatched_in_onMergeExpr360 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L});
    public static final BitSet FOLLOW_mergeUnmatched_in_onMergeExpr363 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0800000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onMergeExpr366 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MERGE_UPD_in_mergeMatched383 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_mergeMatched385 = new BitSet(new long[]{0x0000000000000008L,0x0000010000010000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_UPDATE_in_mergeMatched388 = new BitSet(new long[]{0x0000000000000008L,0x0000000000010000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_DELETE_in_mergeMatched391 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_mergeMatched394 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_MERGE_INS_in_mergeUnmatched412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_selectionList_in_mergeUnmatched414 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1360008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_exprCol_in_mergeUnmatched416 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_mergeUnmatched419 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr440 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr442 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr445 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0020000000000000L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr448 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr465 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr467 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr470 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr490 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr492 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr495 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr498 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000003000060000L,0x0000000400000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr500 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000003000060000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr503 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000003000040000L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr507 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000002000040000L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr510 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr513 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr516 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr538 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr540 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr542 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput559 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput561 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr579 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr581 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr584 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0020000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr588 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr603 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr605 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr607 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0020000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_onSetAssignment627 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment630 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom644 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom646 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom649 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr667 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr676 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr679 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr693 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr696 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr725 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr736 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr756 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr758 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr760 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_exprCol_in_createIndexExpr762 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert777 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert779 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList796 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList798 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList801 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList820 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList822 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList825 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement840 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement842 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement844 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_LBRACK_in_createColTypeListElement846 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement871 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement891 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement895 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement917 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement920 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr956 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createVariableExpr958 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr960 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr963 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr983 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr985 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000004800L,0x0380080002000000L});
    public static final BitSet FOLLOW_variantList_in_createSchemaExpr988 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0180000000000000L});
    public static final BitSet FOLLOW_createColTypeList_in_createSchemaExpr990 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0180000000000000L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr1001 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1003 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr1014 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1016 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_exprCol_in_createSchemaExpr1018 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIANT_LIST_in_variantList1039 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_variantList1041 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr1059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000008000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr1065 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr1070 = new BitSet(new long[]{0x0000000000000002L,0x0000024000000000L,0x000BC03000060000L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr1075 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC03000060000L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr1082 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC03000040000L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr1090 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC02000040000L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr1097 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC02000000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr1104 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr1111 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr1118 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr1137 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr1146 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_exprCol_in_insertIntoExpr1149 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRCOL_in_exprCol1168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1170 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1173 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause1191 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause1193 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1206 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1209 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1223 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1226 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000F40000000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1229 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000F40000000L});
    public static final BitSet FOLLOW_FOR_in_forExpr1249 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_forExpr1251 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_forExpr1253 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1274 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1281 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000420L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1287 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000420L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1293 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000420L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1299 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1305 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1311 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1331 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1348 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1350 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1352 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1354 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1356 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1377 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1379 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1381 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1397 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1399 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1418 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1420 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1440 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1442 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1465 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1467 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1469 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1487 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1489 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000240L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1524 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1526 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000001A000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1528 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1557 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1559 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000001A000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1563 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1575 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1597 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1599 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1616 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1618 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1620 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1637 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1640 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1666 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1668 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1671 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1685 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1687 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1690 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1723 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1725 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1728 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1732 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1735 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1750 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1752 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1755 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1759 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1762 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1777 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1779 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1782 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1786 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1789 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1804 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1806 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1809 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1816 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1837 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1840 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1844 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1848 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1852 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1856 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1861 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1866 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_streamExpression1870 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1894 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1899 = new BitSet(new long[]{0x0000000037CC23C8L,0x400000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1901 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1905 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1925 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1927 = new BitSet(new long[]{0x0000000000000008L,0x8000000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1946 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1948 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000007L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1951 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1954 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1958 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1960 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1980 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1990 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1992 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1995 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2009 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2011 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2014 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2035 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression2037 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2054 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression2056 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2058 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2066 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2087 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression2089 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression2091 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression2094 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2108 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2111 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr2128 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2130 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2132 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr2135 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause2157 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause2159 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause2177 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2179 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2182 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause2200 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2202 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2205 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2225 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2227 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2229 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2252 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2254 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2274 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2286 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2288 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2291 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2308 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2310 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2321 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2323 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2339 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2341 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2352 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2354 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2370 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2372 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2383 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2385 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2388 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2401 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2403 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2418 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2420 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0xF000000000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2423 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2439 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2442 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2444 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2448 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2450 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2454 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2457 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2477 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2479 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2481 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2483 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2485 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2487 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2506 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2519 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2521 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2534 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2536 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2548 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2550 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2572 = new BitSet(new long[]{0x0003800037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2597 = new BitSet(new long[]{0x0000000037CC23C2L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023F83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2606 = new BitSet(new long[]{0x0000000037CC23C2L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2611 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2637 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2639 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2641 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2644 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2658 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2660 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2662 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2665 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2679 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2681 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2683 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2695 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2697 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2711 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2713 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2715 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023F83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2724 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2729 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2742 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2744 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2746 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023F83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2755 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2760 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2775 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2786 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2799 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2805 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2811 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2818 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2827 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2832 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFuncChain_in_valueExpr2840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2876 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2889 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotExpr_in_valueExpr2894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2931 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2933 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2935 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2964 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2974 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2976 = new BitSet(new long[]{0x0000000000000008L,0x0050000000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList3007 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList3014 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList3020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3036 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3039 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0xF000000000200000L,0x0000000000000007L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3042 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0xF000000000200000L,0x0000000000000007L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3045 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0xF000000000200000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3049 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3052 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3055 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3076 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator3079 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator3082 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator3085 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator3104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator3107 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator3110 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator3113 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3132 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator3135 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator3138 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator3141 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3162 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr3164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3183 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr3185 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3204 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr3206 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3225 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3229 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3241 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3243 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3245 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3264 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3266 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3282 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_subQueryExpr3285 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3287 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3290 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3308 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3310 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3313 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3318 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3322 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3325 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3345 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3348 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3361 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3364 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3386 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3388 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3394 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3397 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_set_in_inExpr3401 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3418 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3420 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3426 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3429 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_set_in_inExpr3433 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3448 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3450 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3452 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3458 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3460 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_inExpr3462 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3477 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3479 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3481 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3487 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3489 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_inExpr3491 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3514 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3516 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3518 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3520 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3531 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3533 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3535 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3538 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3560 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3562 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3565 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3578 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3580 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3582 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3585 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3604 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3606 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3608 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3619 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3621 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3623 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3642 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3645 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3649 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3660 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3663 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3667 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3678 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3682 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3686 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3700 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3703 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3707 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3718 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3721 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3725 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3736 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3739 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3743 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3754 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3757 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00006L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3761 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3763 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3775 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3778 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00006L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3782 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3784 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WINDOW_AGGREG_in_builtinFunc3796 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3799 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00006L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3803 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3815 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3817 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3819 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3822 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3837 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3839 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3841 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSTAIL_in_builtinFunc3854 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3856 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3858 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSCOUNT_in_builtinFunc3871 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3873 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSWINDOW_in_builtinFunc3885 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3887 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3899 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3905 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3918 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3920 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3922 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3925 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3939 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3941 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3943 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3955 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3957 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3969 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExpr3986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExpr3993 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExpr3995 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExpr3997 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_accessValueExpr4003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr4020 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr4023 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr4044 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4046 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4048 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr4060 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4062 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4064 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr4076 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4078 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4080 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr4091 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4093 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4095 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr4107 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4109 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4111 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr4122 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4124 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4126 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr4137 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4139 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4141 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr4152 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4154 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr4168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4170 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4172 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4175 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_DOT_EXPR_in_dotExpr4195 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dotExpr4197 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_dotExpr4199 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libFuncChain4221 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080008000000000L});
    public static final BitSet FOLLOW_libOrPropFunction_in_libFuncChain4223 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080008000000000L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4243 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunctionWithClass4246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_libFunctionWithClass4250 = new BitSet(new long[]{0x0000400037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunctionWithClass4253 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_libFunctionWithClass4258 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_libOrPropFunction4276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libOrPropFunction4286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule4301 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000404000000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule4305 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice4319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice4324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice4334 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4336 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4350 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice4352 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4354 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice4384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4386 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E040007L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4389 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4391 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice4393 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_exprChoice4398 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4414 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4417 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4419 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4440 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions4442 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4461 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4463 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4465 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4468 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4484 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4486 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4488 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4491 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4507 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4509 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4511 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4514 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4533 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4545 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4547 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4549 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4551 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4571 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4573 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4576 = new BitSet(new long[]{0x0000000037CC23C8L,0x400000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4578 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4582 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4600 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4602 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4604 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4612 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4614 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4622 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4624 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4631 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4633 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4646 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4648 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4651 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4667 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4676 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4678 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4685 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4687 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4696 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4703 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4705 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4714 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4721 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4723 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4730 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4733 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4737 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4740 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4743 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4754 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4756 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4763 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4770 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4773 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4776 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4787 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4789 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4796 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4799 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4803 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4806 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4810 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4821 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4823 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4830 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4833 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4837 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4840 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4844 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4855 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4858 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4861 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4865 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4868 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4876 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4879 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4882 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4886 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4889 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4909 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4920 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4922 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4924 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4943 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4945 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00003F0000000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4948 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00003F0000000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4967 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4969 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4976 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4978 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4987 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4989 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4991 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5004 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5006 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5013 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5015 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic5017 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5024 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5026 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic5028 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod5053 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod5055 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5071 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5074 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5079 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5084 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5096 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5099 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5104 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5109 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5116 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5119 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5131 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5134 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5141 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart5155 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart5157 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart5172 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart5174 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart5189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart5191 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart5206 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart5208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart5223 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart5225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution5240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant5256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant5265 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant5274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant5283 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant5299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant5315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant5328 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}