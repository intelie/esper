// $ANTLR 3.2 Sep 23, 2009 12:02:23 EsperEPL2Ast.g 2010-11-16 17:17:55

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
    // EsperEPL2Ast.g:93:1: onMergeExpr : ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeMatched )* ( mergeUnmatched )* whereClause[true] ) ;
    public final void onMergeExpr() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:94:2: ( ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeMatched )* ( mergeUnmatched )* whereClause[true] ) )
            // EsperEPL2Ast.g:94:4: ^(m= ON_MERGE_EXPR IDENT ( IDENT )? ( mergeMatched )* ( mergeUnmatched )* whereClause[true] )
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

            pushFollow(FOLLOW_whereClause_in_onMergeExpr366);
            whereClause(true);

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
    // $ANTLR end "onMergeExpr"


    // $ANTLR start "mergeMatched"
    // EsperEPL2Ast.g:97:1: mergeMatched : ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* ) ;
    public final void mergeMatched() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:98:2: ( ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* ) )
            // EsperEPL2Ast.g:98:4: ^(m= MERGE_UPD ( valueExpr )? ( UPDATE )? ( DELETE )? ( onSetAssignment )* )
            {
            m=(CommonTree)match(input,MERGE_UPD,FOLLOW_MERGE_UPD_in_mergeMatched382); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:98:18: ( valueExpr )?
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=IN_SET && LA17_0<=REGEXP)||LA17_0==NOT_EXPR||(LA17_0>=SUM && LA17_0<=AVG)||(LA17_0>=COALESCE && LA17_0<=COUNT)||(LA17_0>=CASE && LA17_0<=CASE2)||(LA17_0>=PREVIOUS && LA17_0<=EXISTS)||(LA17_0>=INSTANCEOF && LA17_0<=CURRENT_TIMESTAMP)||(LA17_0>=EVAL_AND_EXPR && LA17_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA17_0==EVENT_PROP_EXPR||LA17_0==CONCAT||(LA17_0>=LIB_FUNC_CHAIN && LA17_0<=DOT_EXPR)||LA17_0==ARRAY_EXPR||(LA17_0>=NOT_IN_SET && LA17_0<=NOT_REGEXP)||(LA17_0>=IN_RANGE && LA17_0<=SUBSELECT_EXPR)||(LA17_0>=EXISTS_SUBSELECT_EXPR && LA17_0<=NOT_IN_SUBSELECT_EXPR)||LA17_0==SUBSTITUTION||(LA17_0>=FIRST_AGGREG && LA17_0<=WINDOW_AGGREG)||(LA17_0>=INT_TYPE && LA17_0<=NULL_TYPE)||(LA17_0>=STAR && LA17_0<=PLUS)||(LA17_0>=BAND && LA17_0<=BXOR)||(LA17_0>=LT && LA17_0<=GE)||(LA17_0>=MINUS && LA17_0<=MOD)) ) {
                    alt17=1;
                }
                switch (alt17) {
                    case 1 :
                        // EsperEPL2Ast.g:98:18: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_mergeMatched384);
                        valueExpr();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:98:29: ( UPDATE )?
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==UPDATE) ) {
                    alt18=1;
                }
                switch (alt18) {
                    case 1 :
                        // EsperEPL2Ast.g:98:29: UPDATE
                        {
                        match(input,UPDATE,FOLLOW_UPDATE_in_mergeMatched387); 

                        }
                        break;

                }

                // EsperEPL2Ast.g:98:37: ( DELETE )?
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==DELETE) ) {
                    alt19=1;
                }
                switch (alt19) {
                    case 1 :
                        // EsperEPL2Ast.g:98:37: DELETE
                        {
                        match(input,DELETE,FOLLOW_DELETE_in_mergeMatched390); 

                        }
                        break;

                }

                // EsperEPL2Ast.g:98:45: ( onSetAssignment )*
                loop20:
                do {
                    int alt20=2;
                    int LA20_0 = input.LA(1);

                    if ( (LA20_0==ON_SET_EXPR_ITEM) ) {
                        alt20=1;
                    }


                    switch (alt20) {
                	case 1 :
                	    // EsperEPL2Ast.g:98:45: onSetAssignment
                	    {
                	    pushFollow(FOLLOW_onSetAssignment_in_mergeMatched393);
                	    onSetAssignment();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop20;
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
    // EsperEPL2Ast.g:101:1: mergeUnmatched : ^(um= MERGE_INS ( exprCol )? selectionList ) ;
    public final void mergeUnmatched() throws RecognitionException {
        CommonTree um=null;

        try {
            // EsperEPL2Ast.g:102:2: ( ^(um= MERGE_INS ( exprCol )? selectionList ) )
            // EsperEPL2Ast.g:102:4: ^(um= MERGE_INS ( exprCol )? selectionList )
            {
            um=(CommonTree)match(input,MERGE_INS,FOLLOW_MERGE_INS_in_mergeUnmatched411); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:102:19: ( exprCol )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==EXPRCOL) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:102:19: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_mergeUnmatched413);
                    exprCol();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_mergeUnmatched416);
            selectionList();

            state._fsp--;

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
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr434); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr436); 
            // EsperEPL2Ast.g:106:32: ( IDENT )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==IDENT) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:106:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr438); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:106:39: ( onSetAssignment )+
            int cnt23=0;
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==ON_SET_EXPR_ITEM) ) {
                    alt23=1;
                }


                switch (alt23) {
            	case 1 :
            	    // EsperEPL2Ast.g:106:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr441);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt23 >= 1 ) break loop23;
                        EarlyExitException eee =
                            new EarlyExitException(23, input);
                        throw eee;
                }
                cnt23++;
            } while (true);

            // EsperEPL2Ast.g:106:56: ( whereClause[false] )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==WHERE_EXPR) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:106:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr444);
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
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr461); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr463);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:110:32: ( whereClause[true] )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==WHERE_EXPR) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:110:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr466);
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
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr486); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:114:23: ( insertIntoExpr )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==INSERTINTO_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:114:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr488);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:39: ( DISTINCT )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==DISTINCT) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:114:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr491); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr494);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:114:63: ( onExprFrom )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==ON_EXPR_FROM) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:114:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr496);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:75: ( whereClause[true] )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==WHERE_EXPR) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:114:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr499);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:94: ( groupByClause )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==GROUP_BY_EXPR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:114:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr503);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:109: ( havingClause )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==HAVING_EXPR) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:114:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr506);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:123: ( orderByClause )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==ORDER_BY_EXPR) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:114:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr509);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:114:138: ( rowLimitClause )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==ROW_LIMIT_EXPR) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:114:138: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr512);
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
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr532); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr534);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr536);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:118:78: ( whereClause[true] )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==WHERE_EXPR) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:118:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr538);
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
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput555); 

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
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr575); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr577);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:126:34: ( onSetAssignment )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==ON_SET_EXPR_ITEM) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // EsperEPL2Ast.g:126:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr580);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop35;
                }
            } while (true);

            // EsperEPL2Ast.g:126:53: ( whereClause[false] )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==WHERE_EXPR) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:126:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr584);
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
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr599); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr601);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:130:32: ( onSetAssignment )+
            int cnt37=0;
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( (LA37_0==ON_SET_EXPR_ITEM) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // EsperEPL2Ast.g:130:32: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr603);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt37 >= 1 ) break loop37;
                        EarlyExitException eee =
                            new EarlyExitException(37, input);
                        throw eee;
                }
                cnt37++;
            } while (true);

            // EsperEPL2Ast.g:130:49: ( whereClause[false] )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WHERE_EXPR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:130:49: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr606);
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
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment621); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_onSetAssignment623);
            eventPropertyExpr(false);

            state._fsp--;

            pushFollow(FOLLOW_valueExpr_in_onSetAssignment626);
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
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom640); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom642); 
            // EsperEPL2Ast.g:138:25: ( IDENT )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==IDENT) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // EsperEPL2Ast.g:138:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom645); 

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
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr663); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr665); 
            // EsperEPL2Ast.g:142:33: ( viewListExpr )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==VIEW_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:142:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr668);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:142:49: ( RETAINUNION )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==RETAINUNION) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:142:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr672); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:142:62: ( RETAININTERSECTION )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==RETAININTERSECTION) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:142:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr675); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:143:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==CLASS_IDENT||LA44_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt44=1;
            }
            else if ( (LA44_0==CREATE_COL_TYPE_LIST) ) {
                alt44=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:144:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:144:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:144:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:144:6: ( createSelectionList )?
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt43=1;
                    }
                    switch (alt43) {
                        case 1 :
                            // EsperEPL2Ast.g:144:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr689);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr692); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:146:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:146:12: ( createColTypeList )
                    // EsperEPL2Ast.g:146:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr721);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:148:4: ( createWindowExprInsert )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==INSERT) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:148:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr732);
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
            i=(CommonTree)match(input,CREATE_INDEX_EXPR,FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr752); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr754); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr756); 
            pushFollow(FOLLOW_exprCol_in_createIndexExpr758);
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
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert773); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:157:13: ( valueExpr )?
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( ((LA46_0>=IN_SET && LA46_0<=REGEXP)||LA46_0==NOT_EXPR||(LA46_0>=SUM && LA46_0<=AVG)||(LA46_0>=COALESCE && LA46_0<=COUNT)||(LA46_0>=CASE && LA46_0<=CASE2)||(LA46_0>=PREVIOUS && LA46_0<=EXISTS)||(LA46_0>=INSTANCEOF && LA46_0<=CURRENT_TIMESTAMP)||(LA46_0>=EVAL_AND_EXPR && LA46_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA46_0==EVENT_PROP_EXPR||LA46_0==CONCAT||(LA46_0>=LIB_FUNC_CHAIN && LA46_0<=DOT_EXPR)||LA46_0==ARRAY_EXPR||(LA46_0>=NOT_IN_SET && LA46_0<=NOT_REGEXP)||(LA46_0>=IN_RANGE && LA46_0<=SUBSELECT_EXPR)||(LA46_0>=EXISTS_SUBSELECT_EXPR && LA46_0<=NOT_IN_SUBSELECT_EXPR)||LA46_0==SUBSTITUTION||(LA46_0>=FIRST_AGGREG && LA46_0<=WINDOW_AGGREG)||(LA46_0>=INT_TYPE && LA46_0<=NULL_TYPE)||(LA46_0>=STAR && LA46_0<=PLUS)||(LA46_0>=BAND && LA46_0<=BXOR)||(LA46_0>=LT && LA46_0<=GE)||(LA46_0>=MINUS && LA46_0<=MOD)) ) {
                    alt46=1;
                }
                switch (alt46) {
                    case 1 :
                        // EsperEPL2Ast.g:157:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert775);
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
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList792); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList794);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:161:61: ( createSelectionListElement )*
            loop47:
            do {
                int alt47=2;
                int LA47_0 = input.LA(1);

                if ( (LA47_0==SELECTION_ELEMENT_EXPR||LA47_0==WILDCARD_SELECT) ) {
                    alt47=1;
                }


                switch (alt47) {
            	case 1 :
            	    // EsperEPL2Ast.g:161:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList797);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop47;
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
            match(input,CREATE_COL_TYPE_LIST,FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList816); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList818);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:165:52: ( createColTypeListElement )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==CREATE_COL_TYPE) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // EsperEPL2Ast.g:165:53: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList821);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop48;
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
            match(input,CREATE_COL_TYPE,FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement836); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement838); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement840); 
            // EsperEPL2Ast.g:169:40: ( LBRACK )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==LBRACK) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:169:40: LBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_createColTypeListElement842); 

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
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==WILDCARD_SELECT) ) {
                alt52=1;
            }
            else if ( (LA52_0==SELECTION_ELEMENT_EXPR) ) {
                alt52=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }
            switch (alt52) {
                case 1 :
                    // EsperEPL2Ast.g:173:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement857); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:174:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement867); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:174:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==EVENT_PROP_EXPR) ) {
                        alt51=1;
                    }
                    else if ( ((LA51_0>=INT_TYPE && LA51_0<=NULL_TYPE)) ) {
                        alt51=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 51, 0, input);

                        throw nvae;
                    }
                    switch (alt51) {
                        case 1 :
                            // EsperEPL2Ast.g:175:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:175:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:175:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement887);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:175:41: ( IDENT )?
                            int alt50=2;
                            int LA50_0 = input.LA(1);

                            if ( (LA50_0==IDENT) ) {
                                alt50=1;
                            }
                            switch (alt50) {
                                case 1 :
                                    // EsperEPL2Ast.g:175:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement891); 

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
                            pushFollow(FOLLOW_constant_in_createSelectionListElement913);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement916); 

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
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr952); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createVariableExpr954); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr956); 
            // EsperEPL2Ast.g:181:47: ( valueExpr )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( ((LA53_0>=IN_SET && LA53_0<=REGEXP)||LA53_0==NOT_EXPR||(LA53_0>=SUM && LA53_0<=AVG)||(LA53_0>=COALESCE && LA53_0<=COUNT)||(LA53_0>=CASE && LA53_0<=CASE2)||(LA53_0>=PREVIOUS && LA53_0<=EXISTS)||(LA53_0>=INSTANCEOF && LA53_0<=CURRENT_TIMESTAMP)||(LA53_0>=EVAL_AND_EXPR && LA53_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA53_0==EVENT_PROP_EXPR||LA53_0==CONCAT||(LA53_0>=LIB_FUNC_CHAIN && LA53_0<=DOT_EXPR)||LA53_0==ARRAY_EXPR||(LA53_0>=NOT_IN_SET && LA53_0<=NOT_REGEXP)||(LA53_0>=IN_RANGE && LA53_0<=SUBSELECT_EXPR)||(LA53_0>=EXISTS_SUBSELECT_EXPR && LA53_0<=NOT_IN_SUBSELECT_EXPR)||LA53_0==SUBSTITUTION||(LA53_0>=FIRST_AGGREG && LA53_0<=WINDOW_AGGREG)||(LA53_0>=INT_TYPE && LA53_0<=NULL_TYPE)||(LA53_0>=STAR && LA53_0<=PLUS)||(LA53_0>=BAND && LA53_0<=BXOR)||(LA53_0>=LT && LA53_0<=GE)||(LA53_0>=MINUS && LA53_0<=MOD)) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:181:48: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr959);
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
            s=(CommonTree)match(input,CREATE_SCHEMA_EXPR,FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr979); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr981); 
            // EsperEPL2Ast.g:185:33: ( variantList | ( createColTypeList )? )
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==VARIANT_LIST) ) {
                alt55=1;
            }
            else if ( (LA55_0==UP||LA55_0==CREATE_COL_TYPE_LIST||(LA55_0>=CREATE_SCHEMA_EXPR_QUAL && LA55_0<=CREATE_SCHEMA_EXPR_INH)) ) {
                alt55=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 55, 0, input);

                throw nvae;
            }
            switch (alt55) {
                case 1 :
                    // EsperEPL2Ast.g:185:34: variantList
                    {
                    pushFollow(FOLLOW_variantList_in_createSchemaExpr984);
                    variantList();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:185:46: ( createColTypeList )?
                    {
                    // EsperEPL2Ast.g:185:46: ( createColTypeList )?
                    int alt54=2;
                    int LA54_0 = input.LA(1);

                    if ( (LA54_0==CREATE_COL_TYPE_LIST) ) {
                        alt54=1;
                    }
                    switch (alt54) {
                        case 1 :
                            // EsperEPL2Ast.g:185:46: createColTypeList
                            {
                            pushFollow(FOLLOW_createColTypeList_in_createSchemaExpr986);
                            createColTypeList();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:186:5: ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==CREATE_SCHEMA_EXPR_QUAL) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:186:6: ^( CREATE_SCHEMA_EXPR_QUAL IDENT )
                    {
                    match(input,CREATE_SCHEMA_EXPR_QUAL,FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr997); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr999); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:187:5: ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==CREATE_SCHEMA_EXPR_INH) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:187:6: ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol )
                    {
                    match(input,CREATE_SCHEMA_EXPR_INH,FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr1010); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr1012); 
                    pushFollow(FOLLOW_exprCol_in_createSchemaExpr1014);
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
            match(input,VARIANT_LIST,FOLLOW_VARIANT_LIST_in_variantList1035); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:191:19: ( STAR | CLASS_IDENT )+
            int cnt58=0;
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==CLASS_IDENT||LA58_0==STAR) ) {
                    alt58=1;
                }


                switch (alt58) {
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
            	    if ( cnt58 >= 1 ) break loop58;
                        EarlyExitException eee =
                            new EarlyExitException(58, input);
                        throw eee;
                }
                cnt58++;
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
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==INSERTINTO_EXPR) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:195:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr1055);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr1061);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr1066);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:198:3: ( matchRecogClause )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==MATCH_RECOGNIZE) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:198:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr1071);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:199:3: ( whereClause[true] )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==WHERE_EXPR) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // EsperEPL2Ast.g:199:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr1078);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:200:3: ( groupByClause )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==GROUP_BY_EXPR) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:200:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr1086);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:201:3: ( havingClause )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==HAVING_EXPR) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:201:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr1093);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:202:3: ( outputLimitExpr )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( ((LA64_0>=EVENT_LIMIT_EXPR && LA64_0<=CRONTAB_LIMIT_EXPR)||LA64_0==WHEN_LIMIT_EXPR) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:202:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr1100);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:203:3: ( orderByClause )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==ORDER_BY_EXPR) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:203:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr1107);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:204:3: ( rowLimitClause )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==ROW_LIMIT_EXPR) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:204:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr1114);
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
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1131); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:208:24: ( ISTREAM | RSTREAM )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=RSTREAM && LA67_0<=ISTREAM)) ) {
                alt67=1;
            }
            switch (alt67) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr1142); 
            // EsperEPL2Ast.g:208:51: ( exprCol )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==EXPRCOL) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:208:52: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_insertIntoExpr1145);
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
            match(input,EXPRCOL,FOLLOW_EXPRCOL_in_exprCol1164); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_exprCol1166); 
            // EsperEPL2Ast.g:212:20: ( IDENT )*
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==IDENT) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // EsperEPL2Ast.g:212:21: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_exprCol1169); 

            	    }
            	    break;

            	default :
            	    break loop69;
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
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause1187); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:216:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( ((LA70_0>=RSTREAM && LA70_0<=IRSTREAM)) ) {
                alt70=1;
            }
            switch (alt70) {
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
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==DISTINCT) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // EsperEPL2Ast.g:216:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1202); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1205);
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
            pushFollow(FOLLOW_streamExpression_in_fromClause1219);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:220:21: ( streamExpression ( outerJoin )* )*
            loop73:
            do {
                int alt73=2;
                int LA73_0 = input.LA(1);

                if ( (LA73_0==STREAM_EXPR) ) {
                    alt73=1;
                }


                switch (alt73) {
            	case 1 :
            	    // EsperEPL2Ast.g:220:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1222);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:220:39: ( outerJoin )*
            	    loop72:
            	    do {
            	        int alt72=2;
            	        int LA72_0 = input.LA(1);

            	        if ( ((LA72_0>=INNERJOIN_EXPR && LA72_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt72=1;
            	        }


            	        switch (alt72) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:220:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1225);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop72;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop73;
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
            f=(CommonTree)match(input,FOR,FOLLOW_FOR_in_forExpr1245); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_forExpr1247); 
            // EsperEPL2Ast.g:224:18: ( valueExpr )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( ((LA74_0>=IN_SET && LA74_0<=REGEXP)||LA74_0==NOT_EXPR||(LA74_0>=SUM && LA74_0<=AVG)||(LA74_0>=COALESCE && LA74_0<=COUNT)||(LA74_0>=CASE && LA74_0<=CASE2)||(LA74_0>=PREVIOUS && LA74_0<=EXISTS)||(LA74_0>=INSTANCEOF && LA74_0<=CURRENT_TIMESTAMP)||(LA74_0>=EVAL_AND_EXPR && LA74_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA74_0==EVENT_PROP_EXPR||LA74_0==CONCAT||(LA74_0>=LIB_FUNC_CHAIN && LA74_0<=DOT_EXPR)||LA74_0==ARRAY_EXPR||(LA74_0>=NOT_IN_SET && LA74_0<=NOT_REGEXP)||(LA74_0>=IN_RANGE && LA74_0<=SUBSELECT_EXPR)||(LA74_0>=EXISTS_SUBSELECT_EXPR && LA74_0<=NOT_IN_SUBSELECT_EXPR)||LA74_0==SUBSTITUTION||(LA74_0>=FIRST_AGGREG && LA74_0<=WINDOW_AGGREG)||(LA74_0>=INT_TYPE && LA74_0<=NULL_TYPE)||(LA74_0>=STAR && LA74_0<=PLUS)||(LA74_0>=BAND && LA74_0<=BXOR)||(LA74_0>=LT && LA74_0<=GE)||(LA74_0>=MINUS && LA74_0<=MOD)) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // EsperEPL2Ast.g:224:18: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_forExpr1249);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop74;
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
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1268); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:228:24: ( matchRecogPartitionBy )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==MATCHREC_PARTITION) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // EsperEPL2Ast.g:228:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1270);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1277);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:230:4: ( ALL )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==ALL) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:230:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1283); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:231:4: ( matchRecogMatchesAfterSkip )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==MATCHREC_AFTER_SKIP) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:231:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1289);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1295);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:233:4: ( matchRecogMatchesInterval )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==MATCHREC_INTERVAL) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:233:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1301);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1307);
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
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1325); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:238:27: ( valueExpr )+
            int cnt79=0;
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( ((LA79_0>=IN_SET && LA79_0<=REGEXP)||LA79_0==NOT_EXPR||(LA79_0>=SUM && LA79_0<=AVG)||(LA79_0>=COALESCE && LA79_0<=COUNT)||(LA79_0>=CASE && LA79_0<=CASE2)||(LA79_0>=PREVIOUS && LA79_0<=EXISTS)||(LA79_0>=INSTANCEOF && LA79_0<=CURRENT_TIMESTAMP)||(LA79_0>=EVAL_AND_EXPR && LA79_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA79_0==EVENT_PROP_EXPR||LA79_0==CONCAT||(LA79_0>=LIB_FUNC_CHAIN && LA79_0<=DOT_EXPR)||LA79_0==ARRAY_EXPR||(LA79_0>=NOT_IN_SET && LA79_0<=NOT_REGEXP)||(LA79_0>=IN_RANGE && LA79_0<=SUBSELECT_EXPR)||(LA79_0>=EXISTS_SUBSELECT_EXPR && LA79_0<=NOT_IN_SUBSELECT_EXPR)||LA79_0==SUBSTITUTION||(LA79_0>=FIRST_AGGREG && LA79_0<=WINDOW_AGGREG)||(LA79_0>=INT_TYPE && LA79_0<=NULL_TYPE)||(LA79_0>=STAR && LA79_0<=PLUS)||(LA79_0>=BAND && LA79_0<=BXOR)||(LA79_0>=LT && LA79_0<=GE)||(LA79_0>=MINUS && LA79_0<=MOD)) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // EsperEPL2Ast.g:238:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1327);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt79 >= 1 ) break loop79;
                        EarlyExitException eee =
                            new EarlyExitException(79, input);
                        throw eee;
                }
                cnt79++;
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
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1344); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1346); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1348); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1350); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1358); 

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
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1373); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1375); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1377);
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
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1393); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:250:26: ( matchRecogMeasureListElement )*
                loop80:
                do {
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==MATCHREC_MEASURE_ITEM) ) {
                        alt80=1;
                    }


                    switch (alt80) {
                	case 1 :
                	    // EsperEPL2Ast.g:250:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1395);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop80;
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
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1412); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1414);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:254:40: ( IDENT )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==IDENT) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:254:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1416); 

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
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1436); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:258:25: ( matchRecogPatternAlteration )+
            int cnt82=0;
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( ((LA82_0>=MATCHREC_PATTERN_CONCAT && LA82_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // EsperEPL2Ast.g:258:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1438);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt82 >= 1 ) break loop82;
                        EarlyExitException eee =
                            new EarlyExitException(82, input);
                        throw eee;
                }
                cnt82++;
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
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==MATCHREC_PATTERN_CONCAT) ) {
                alt84=1;
            }
            else if ( (LA84_0==MATCHREC_PATTERN_ALTER) ) {
                alt84=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:262:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1453);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:263:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1461); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1463);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:263:55: ( matchRecogPatternConcat )+
                    int cnt83=0;
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( (LA83_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:263:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1465);
                    	    matchRecogPatternConcat();

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
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1483); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:267:32: ( matchRecogPatternUnary )+
            int cnt85=0;
            loop85:
            do {
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( (LA85_0==MATCHREC_PATTERN_ATOM||LA85_0==MATCHREC_PATTERN_NESTED) ) {
                    alt85=1;
                }


                switch (alt85) {
            	case 1 :
            	    // EsperEPL2Ast.g:267:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1485);
            	    matchRecogPatternUnary();

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
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==MATCHREC_PATTERN_NESTED) ) {
                alt86=1;
            }
            else if ( (LA86_0==MATCHREC_PATTERN_ATOM) ) {
                alt86=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:271:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1500);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:272:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1505);
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
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1520); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1522);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:276:60: ( PLUS | STAR | QUESTION )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==STAR||(LA87_0>=PLUS && LA87_0<=QUESTION)) ) {
                alt87=1;
            }
            switch (alt87) {
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
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1553); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1555); 
            // EsperEPL2Ast.g:280:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==STAR||(LA89_0>=PLUS && LA89_0<=QUESTION)) ) {
                alt89=1;
            }
            switch (alt89) {
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
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==QUESTION) ) {
                        alt88=1;
                    }
                    switch (alt88) {
                        case 1 :
                            // EsperEPL2Ast.g:280:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1571); 

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
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1593); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:284:24: ( matchRecogDefineItem )+
            int cnt90=0;
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( (LA90_0==MATCHREC_DEFINE_ITEM) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // EsperEPL2Ast.g:284:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1595);
            	    matchRecogDefineItem();

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
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1612); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1614); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1616);
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
            pushFollow(FOLLOW_selectionListElement_in_selectionList1633);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:293:25: ( selectionListElement )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( ((LA91_0>=SELECTION_ELEMENT_EXPR && LA91_0<=SELECTION_STREAM)||LA91_0==WILDCARD_SELECT) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // EsperEPL2Ast.g:293:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1636);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop91;
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
            int alt94=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt94=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt94=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt94=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }

            switch (alt94) {
                case 1 :
                    // EsperEPL2Ast.g:297:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1652); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1662); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1664);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:298:41: ( IDENT )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==IDENT) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // EsperEPL2Ast.g:298:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1667); 

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
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1681); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1683); 
                    // EsperEPL2Ast.g:299:31: ( IDENT )?
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==IDENT) ) {
                        alt93=1;
                    }
                    switch (alt93) {
                        case 1 :
                            // EsperEPL2Ast.g:299:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1686); 

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
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1705);
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
            int alt99=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt99=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt99=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt99=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt99=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }

            switch (alt99) {
                case 1 :
                    // EsperEPL2Ast.g:307:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1719); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1721);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1724);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:307:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop95:
                    do {
                        int alt95=2;
                        int LA95_0 = input.LA(1);

                        if ( (LA95_0==EVENT_PROP_EXPR) ) {
                            alt95=1;
                        }


                        switch (alt95) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:307:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1728);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1731);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop95;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:308:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1746); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1748);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1751);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:308:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( (LA96_0==EVENT_PROP_EXPR) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:308:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1755);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1758);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:309:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1773); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1775);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1778);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:309:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( (LA97_0==EVENT_PROP_EXPR) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:309:78: eventPropertyExpr[true] eventPropertyExpr[true]
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
                    	    break loop97;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:310:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1800); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1802);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1805);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:310:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop98:
                    do {
                        int alt98=2;
                        int LA98_0 = input.LA(1);

                        if ( (LA98_0==EVENT_PROP_EXPR) ) {
                            alt98=1;
                        }


                        switch (alt98) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:310:72: eventPropertyExpr[true] eventPropertyExpr[true]
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
                    	    break loop98;
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
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1833); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:314:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt100=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt100=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt100=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt100=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt100=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }

            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:314:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1836);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:314:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1840);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:314:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1844);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:314:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1848);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:314:115: ( viewListExpr )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==VIEW_EXPR) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // EsperEPL2Ast.g:314:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1852);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:314:131: ( IDENT )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==IDENT) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // EsperEPL2Ast.g:314:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1857); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:314:140: ( UNIDIRECTIONAL )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==UNIDIRECTIONAL) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:314:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1862); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:314:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=RETAINUNION && LA104_0<=RETAININTERSECTION)) ) {
                alt104=1;
            }
            switch (alt104) {
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
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1890); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:318:27: ( IDENT )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==IDENT) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // EsperEPL2Ast.g:318:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1892); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1895); 
            // EsperEPL2Ast.g:318:46: ( propertyExpression )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // EsperEPL2Ast.g:318:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1897);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:318:66: ( valueExpr )*
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( ((LA107_0>=IN_SET && LA107_0<=REGEXP)||LA107_0==NOT_EXPR||(LA107_0>=SUM && LA107_0<=AVG)||(LA107_0>=COALESCE && LA107_0<=COUNT)||(LA107_0>=CASE && LA107_0<=CASE2)||(LA107_0>=PREVIOUS && LA107_0<=EXISTS)||(LA107_0>=INSTANCEOF && LA107_0<=CURRENT_TIMESTAMP)||(LA107_0>=EVAL_AND_EXPR && LA107_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA107_0==EVENT_PROP_EXPR||LA107_0==CONCAT||(LA107_0>=LIB_FUNC_CHAIN && LA107_0<=DOT_EXPR)||LA107_0==ARRAY_EXPR||(LA107_0>=NOT_IN_SET && LA107_0<=NOT_REGEXP)||(LA107_0>=IN_RANGE && LA107_0<=SUBSELECT_EXPR)||(LA107_0>=EXISTS_SUBSELECT_EXPR && LA107_0<=NOT_IN_SUBSELECT_EXPR)||LA107_0==SUBSTITUTION||(LA107_0>=FIRST_AGGREG && LA107_0<=WINDOW_AGGREG)||(LA107_0>=INT_TYPE && LA107_0<=NULL_TYPE)||(LA107_0>=STAR && LA107_0<=PLUS)||(LA107_0>=BAND && LA107_0<=BXOR)||(LA107_0>=LT && LA107_0<=GE)||(LA107_0>=MINUS && LA107_0<=MOD)) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // EsperEPL2Ast.g:318:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1901);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop107;
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
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1921); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:322:34: ( propertyExpressionAtom )*
                loop108:
                do {
                    int alt108=2;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt108=1;
                    }


                    switch (alt108) {
                	case 1 :
                	    // EsperEPL2Ast.g:322:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1923);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop108;
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
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1942); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:326:41: ( propertySelectionListElement )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( ((LA109_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA109_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // EsperEPL2Ast.g:326:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1944);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop109;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1947);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:326:96: ( IDENT )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==IDENT) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:326:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1950); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1954); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:326:116: ( valueExpr )?
                int alt111=2;
                int LA111_0 = input.LA(1);

                if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA111_0==EVENT_PROP_EXPR||LA111_0==CONCAT||(LA111_0>=LIB_FUNC_CHAIN && LA111_0<=DOT_EXPR)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=SUBSELECT_EXPR)||(LA111_0>=EXISTS_SUBSELECT_EXPR && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=FIRST_AGGREG && LA111_0<=WINDOW_AGGREG)||(LA111_0>=INT_TYPE && LA111_0<=NULL_TYPE)||(LA111_0>=STAR && LA111_0<=PLUS)||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=MINUS && LA111_0<=MOD)) ) {
                    alt111=1;
                }
                switch (alt111) {
                    case 1 :
                        // EsperEPL2Ast.g:326:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1956);
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
            int alt114=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt114=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt114=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt114=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 114, 0, input);

                throw nvae;
            }

            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:330:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1976); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:331:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1986); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1988);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:331:50: ( IDENT )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==IDENT) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // EsperEPL2Ast.g:331:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1991); 

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
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2005); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2007); 
                    // EsperEPL2Ast.g:332:40: ( IDENT )?
                    int alt113=2;
                    int LA113_0 = input.LA(1);

                    if ( (LA113_0==IDENT) ) {
                        alt113=1;
                    }
                    switch (alt113) {
                        case 1 :
                            // EsperEPL2Ast.g:332:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement2010); 

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
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2031); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression2033);
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
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2050); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression2052); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:340:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( ((LA115_0>=STRING_LITERAL && LA115_0<=QUOTED_STRING_LITERAL)) ) {
                alt115=1;
            }
            switch (alt115) {
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
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2083); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression2085); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression2087); 
            // EsperEPL2Ast.g:344:41: ( valueExpr )*
            loop116:
            do {
                int alt116=2;
                int LA116_0 = input.LA(1);

                if ( ((LA116_0>=IN_SET && LA116_0<=REGEXP)||LA116_0==NOT_EXPR||(LA116_0>=SUM && LA116_0<=AVG)||(LA116_0>=COALESCE && LA116_0<=COUNT)||(LA116_0>=CASE && LA116_0<=CASE2)||(LA116_0>=PREVIOUS && LA116_0<=EXISTS)||(LA116_0>=INSTANCEOF && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=EVAL_AND_EXPR && LA116_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA116_0==EVENT_PROP_EXPR||LA116_0==CONCAT||(LA116_0>=LIB_FUNC_CHAIN && LA116_0<=DOT_EXPR)||LA116_0==ARRAY_EXPR||(LA116_0>=NOT_IN_SET && LA116_0<=NOT_REGEXP)||(LA116_0>=IN_RANGE && LA116_0<=SUBSELECT_EXPR)||(LA116_0>=EXISTS_SUBSELECT_EXPR && LA116_0<=NOT_IN_SUBSELECT_EXPR)||LA116_0==SUBSTITUTION||(LA116_0>=FIRST_AGGREG && LA116_0<=WINDOW_AGGREG)||(LA116_0>=INT_TYPE && LA116_0<=NULL_TYPE)||(LA116_0>=STAR && LA116_0<=PLUS)||(LA116_0>=BAND && LA116_0<=BXOR)||(LA116_0>=LT && LA116_0<=GE)||(LA116_0>=MINUS && LA116_0<=MOD)) ) {
                    alt116=1;
                }


                switch (alt116) {
            	case 1 :
            	    // EsperEPL2Ast.g:344:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression2090);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop116;
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
            pushFollow(FOLLOW_viewExpr_in_viewListExpr2104);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:348:13: ( viewExpr )*
            loop117:
            do {
                int alt117=2;
                int LA117_0 = input.LA(1);

                if ( (LA117_0==VIEW_EXPR) ) {
                    alt117=1;
                }


                switch (alt117) {
            	case 1 :
            	    // EsperEPL2Ast.g:348:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr2107);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop117;
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
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr2124); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2126); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2128); 
            // EsperEPL2Ast.g:352:30: ( valueExprWithTime )*
            loop118:
            do {
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( ((LA118_0>=IN_SET && LA118_0<=REGEXP)||LA118_0==NOT_EXPR||(LA118_0>=SUM && LA118_0<=AVG)||(LA118_0>=COALESCE && LA118_0<=COUNT)||(LA118_0>=CASE && LA118_0<=CASE2)||LA118_0==LAST||(LA118_0>=PREVIOUS && LA118_0<=EXISTS)||(LA118_0>=LW && LA118_0<=CURRENT_TIMESTAMP)||(LA118_0>=NUMERIC_PARAM_RANGE && LA118_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA118_0>=EVAL_AND_EXPR && LA118_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA118_0==EVENT_PROP_EXPR||LA118_0==CONCAT||(LA118_0>=LIB_FUNC_CHAIN && LA118_0<=DOT_EXPR)||(LA118_0>=TIME_PERIOD && LA118_0<=ARRAY_EXPR)||(LA118_0>=NOT_IN_SET && LA118_0<=NOT_REGEXP)||(LA118_0>=IN_RANGE && LA118_0<=SUBSELECT_EXPR)||(LA118_0>=EXISTS_SUBSELECT_EXPR && LA118_0<=NOT_IN_SUBSELECT_EXPR)||(LA118_0>=LAST_OPERATOR && LA118_0<=SUBSTITUTION)||LA118_0==NUMBERSETSTAR||(LA118_0>=FIRST_AGGREG && LA118_0<=WINDOW_AGGREG)||(LA118_0>=INT_TYPE && LA118_0<=NULL_TYPE)||(LA118_0>=STAR && LA118_0<=PLUS)||(LA118_0>=BAND && LA118_0<=BXOR)||(LA118_0>=LT && LA118_0<=GE)||(LA118_0>=MINUS && LA118_0<=MOD)) ) {
                    alt118=1;
                }


                switch (alt118) {
            	case 1 :
            	    // EsperEPL2Ast.g:352:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr2131);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop118;
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
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause2153); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause2155);
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
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause2173); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause2175);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:360:32: ( valueExpr )*
            loop119:
            do {
                int alt119=2;
                int LA119_0 = input.LA(1);

                if ( ((LA119_0>=IN_SET && LA119_0<=REGEXP)||LA119_0==NOT_EXPR||(LA119_0>=SUM && LA119_0<=AVG)||(LA119_0>=COALESCE && LA119_0<=COUNT)||(LA119_0>=CASE && LA119_0<=CASE2)||(LA119_0>=PREVIOUS && LA119_0<=EXISTS)||(LA119_0>=INSTANCEOF && LA119_0<=CURRENT_TIMESTAMP)||(LA119_0>=EVAL_AND_EXPR && LA119_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA119_0==EVENT_PROP_EXPR||LA119_0==CONCAT||(LA119_0>=LIB_FUNC_CHAIN && LA119_0<=DOT_EXPR)||LA119_0==ARRAY_EXPR||(LA119_0>=NOT_IN_SET && LA119_0<=NOT_REGEXP)||(LA119_0>=IN_RANGE && LA119_0<=SUBSELECT_EXPR)||(LA119_0>=EXISTS_SUBSELECT_EXPR && LA119_0<=NOT_IN_SUBSELECT_EXPR)||LA119_0==SUBSTITUTION||(LA119_0>=FIRST_AGGREG && LA119_0<=WINDOW_AGGREG)||(LA119_0>=INT_TYPE && LA119_0<=NULL_TYPE)||(LA119_0>=STAR && LA119_0<=PLUS)||(LA119_0>=BAND && LA119_0<=BXOR)||(LA119_0>=LT && LA119_0<=GE)||(LA119_0>=MINUS && LA119_0<=MOD)) ) {
                    alt119=1;
                }


                switch (alt119) {
            	case 1 :
            	    // EsperEPL2Ast.g:360:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause2178);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop119;
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
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause2196); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause2198);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:364:35: ( orderByElement )*
            loop120:
            do {
                int alt120=2;
                int LA120_0 = input.LA(1);

                if ( (LA120_0==ORDER_ELEMENT_EXPR) ) {
                    alt120=1;
                }


                switch (alt120) {
            	case 1 :
            	    // EsperEPL2Ast.g:364:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause2201);
            	    orderByElement();

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
    // $ANTLR end "orderByClause"


    // $ANTLR start "orderByElement"
    // EsperEPL2Ast.g:367:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:368:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:368:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2221); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2223);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:368:38: ( ASC | DESC )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( ((LA121_0>=ASC && LA121_0<=DESC)) ) {
                alt121=1;
            }
            switch (alt121) {
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
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2248); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2250);
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
            int alt132=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt132=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt132=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt132=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt132=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt132=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }

            switch (alt132) {
                case 1 :
                    // EsperEPL2Ast.g:376:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2268); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:376:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==ALL||(LA122_0>=FIRST && LA122_0<=LAST)||LA122_0==SNAPSHOT) ) {
                        alt122=1;
                    }
                    switch (alt122) {
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
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( ((LA123_0>=INT_TYPE && LA123_0<=DOUBLE_TYPE)) ) {
                        alt123=1;
                    }
                    else if ( (LA123_0==IDENT) ) {
                        alt123=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 123, 0, input);

                        throw nvae;
                    }
                    switch (alt123) {
                        case 1 :
                            // EsperEPL2Ast.g:376:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2282);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:376:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2284); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:376:67: ( outputLimitAfter )?
                    int alt124=2;
                    int LA124_0 = input.LA(1);

                    if ( (LA124_0==AFTER) ) {
                        alt124=1;
                    }
                    switch (alt124) {
                        case 1 :
                            // EsperEPL2Ast.g:376:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2287);
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
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2304); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:377:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt125=2;
                    int LA125_0 = input.LA(1);

                    if ( (LA125_0==ALL||(LA125_0>=FIRST && LA125_0<=LAST)||LA125_0==SNAPSHOT) ) {
                        alt125=1;
                    }
                    switch (alt125) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2317);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:377:72: ( outputLimitAfter )?
                    int alt126=2;
                    int LA126_0 = input.LA(1);

                    if ( (LA126_0==AFTER) ) {
                        alt126=1;
                    }
                    switch (alt126) {
                        case 1 :
                            // EsperEPL2Ast.g:377:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2319);
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
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2335); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:378:33: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2348);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:378:85: ( outputLimitAfter )?
                    int alt128=2;
                    int LA128_0 = input.LA(1);

                    if ( (LA128_0==AFTER) ) {
                        alt128=1;
                    }
                    switch (alt128) {
                        case 1 :
                            // EsperEPL2Ast.g:378:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2350);
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
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2366); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:379:30: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2379);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:379:67: ( onSetExpr )?
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( (LA130_0==ON_SET_EXPR) ) {
                        alt130=1;
                    }
                    switch (alt130) {
                        case 1 :
                            // EsperEPL2Ast.g:379:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2381);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:379:78: ( outputLimitAfter )?
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==AFTER) ) {
                        alt131=1;
                    }
                    switch (alt131) {
                        case 1 :
                            // EsperEPL2Ast.g:379:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2384);
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
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2397); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2399);
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
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2414); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:384:12: ( timePeriod )?
                int alt133=2;
                int LA133_0 = input.LA(1);

                if ( (LA133_0==TIME_PERIOD) ) {
                    alt133=1;
                }
                switch (alt133) {
                    case 1 :
                        // EsperEPL2Ast.g:384:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2416);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:384:24: ( number )?
                int alt134=2;
                int LA134_0 = input.LA(1);

                if ( ((LA134_0>=INT_TYPE && LA134_0<=DOUBLE_TYPE)) ) {
                    alt134=1;
                }
                switch (alt134) {
                    case 1 :
                        // EsperEPL2Ast.g:384:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2419);
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
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2435); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:388:23: ( number | IDENT )
            int alt135=2;
            int LA135_0 = input.LA(1);

            if ( ((LA135_0>=INT_TYPE && LA135_0<=DOUBLE_TYPE)) ) {
                alt135=1;
            }
            else if ( (LA135_0==IDENT) ) {
                alt135=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 135, 0, input);

                throw nvae;
            }
            switch (alt135) {
                case 1 :
                    // EsperEPL2Ast.g:388:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2438);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:388:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2440); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:388:38: ( number | IDENT )?
            int alt136=3;
            int LA136_0 = input.LA(1);

            if ( ((LA136_0>=INT_TYPE && LA136_0<=DOUBLE_TYPE)) ) {
                alt136=1;
            }
            else if ( (LA136_0==IDENT) ) {
                alt136=2;
            }
            switch (alt136) {
                case 1 :
                    // EsperEPL2Ast.g:388:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2444);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:388:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2446); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:388:54: ( COMMA )?
            int alt137=2;
            int LA137_0 = input.LA(1);

            if ( (LA137_0==COMMA) ) {
                alt137=1;
            }
            switch (alt137) {
                case 1 :
                    // EsperEPL2Ast.g:388:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2450); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:388:61: ( OFFSET )?
            int alt138=2;
            int LA138_0 = input.LA(1);

            if ( (LA138_0==OFFSET) ) {
                alt138=1;
            }
            switch (alt138) {
                case 1 :
                    // EsperEPL2Ast.g:388:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2453); 

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
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2471); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2473);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2475);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2477);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2479);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2481);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:392:121: ( valueExprWithTime )?
            int alt139=2;
            int LA139_0 = input.LA(1);

            if ( ((LA139_0>=IN_SET && LA139_0<=REGEXP)||LA139_0==NOT_EXPR||(LA139_0>=SUM && LA139_0<=AVG)||(LA139_0>=COALESCE && LA139_0<=COUNT)||(LA139_0>=CASE && LA139_0<=CASE2)||LA139_0==LAST||(LA139_0>=PREVIOUS && LA139_0<=EXISTS)||(LA139_0>=LW && LA139_0<=CURRENT_TIMESTAMP)||(LA139_0>=NUMERIC_PARAM_RANGE && LA139_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA139_0>=EVAL_AND_EXPR && LA139_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA139_0==EVENT_PROP_EXPR||LA139_0==CONCAT||(LA139_0>=LIB_FUNC_CHAIN && LA139_0<=DOT_EXPR)||(LA139_0>=TIME_PERIOD && LA139_0<=ARRAY_EXPR)||(LA139_0>=NOT_IN_SET && LA139_0<=NOT_REGEXP)||(LA139_0>=IN_RANGE && LA139_0<=SUBSELECT_EXPR)||(LA139_0>=EXISTS_SUBSELECT_EXPR && LA139_0<=NOT_IN_SUBSELECT_EXPR)||(LA139_0>=LAST_OPERATOR && LA139_0<=SUBSTITUTION)||LA139_0==NUMBERSETSTAR||(LA139_0>=FIRST_AGGREG && LA139_0<=WINDOW_AGGREG)||(LA139_0>=INT_TYPE && LA139_0<=NULL_TYPE)||(LA139_0>=STAR && LA139_0<=PLUS)||(LA139_0>=BAND && LA139_0<=BXOR)||(LA139_0>=LT && LA139_0<=GE)||(LA139_0>=MINUS && LA139_0<=MOD)) ) {
                alt139=1;
            }
            switch (alt139) {
                case 1 :
                    // EsperEPL2Ast.g:392:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2483);
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
            int alt140=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt140=1;
                }
                break;
            case GT:
                {
                alt140=2;
                }
                break;
            case LE:
                {
                alt140=3;
                }
                break;
            case GE:
                {
                alt140=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 140, 0, input);

                throw nvae;
            }

            switch (alt140) {
                case 1 :
                    // EsperEPL2Ast.g:396:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2500); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2502);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:397:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2515); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2517);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:398:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2530); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2532);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:399:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2544); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2546);
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
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2568);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:405:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( ((LA143_0>=IN_SET && LA143_0<=REGEXP)||LA143_0==NOT_EXPR||(LA143_0>=SUM && LA143_0<=AVG)||(LA143_0>=COALESCE && LA143_0<=COUNT)||(LA143_0>=CASE && LA143_0<=CASE2)||(LA143_0>=PREVIOUS && LA143_0<=EXISTS)||(LA143_0>=INSTANCEOF && LA143_0<=CURRENT_TIMESTAMP)||(LA143_0>=EVAL_AND_EXPR && LA143_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA143_0==EVENT_PROP_EXPR||LA143_0==CONCAT||(LA143_0>=LIB_FUNC_CHAIN && LA143_0<=DOT_EXPR)||LA143_0==ARRAY_EXPR||(LA143_0>=NOT_IN_SET && LA143_0<=NOT_REGEXP)||(LA143_0>=IN_RANGE && LA143_0<=SUBSELECT_EXPR)||(LA143_0>=EXISTS_SUBSELECT_EXPR && LA143_0<=NOT_IN_SUBSELECT_EXPR)||LA143_0==SUBSTITUTION||(LA143_0>=FIRST_AGGREG && LA143_0<=WINDOW_AGGREG)||(LA143_0>=INT_TYPE && LA143_0<=NULL_TYPE)||(LA143_0>=STAR && LA143_0<=PLUS)||(LA143_0>=BAND && LA143_0<=BXOR)||(LA143_0>=LT && LA143_0<=GE)||(LA143_0>=MINUS && LA143_0<=MOD)) ) {
                alt143=1;
            }
            else if ( ((LA143_0>=ALL && LA143_0<=SOME)) ) {
                alt143=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }
            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:405:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2578);
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
                    int alt142=2;
                    int LA142_0 = input.LA(1);

                    if ( (LA142_0==UP||(LA142_0>=IN_SET && LA142_0<=REGEXP)||LA142_0==NOT_EXPR||(LA142_0>=SUM && LA142_0<=AVG)||(LA142_0>=COALESCE && LA142_0<=COUNT)||(LA142_0>=CASE && LA142_0<=CASE2)||(LA142_0>=PREVIOUS && LA142_0<=EXISTS)||(LA142_0>=INSTANCEOF && LA142_0<=CURRENT_TIMESTAMP)||(LA142_0>=EVAL_AND_EXPR && LA142_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA142_0==EVENT_PROP_EXPR||LA142_0==CONCAT||(LA142_0>=LIB_FUNC_CHAIN && LA142_0<=DOT_EXPR)||LA142_0==ARRAY_EXPR||(LA142_0>=NOT_IN_SET && LA142_0<=NOT_REGEXP)||(LA142_0>=IN_RANGE && LA142_0<=SUBSELECT_EXPR)||(LA142_0>=EXISTS_SUBSELECT_EXPR && LA142_0<=NOT_IN_SUBSELECT_EXPR)||LA142_0==SUBSTITUTION||(LA142_0>=FIRST_AGGREG && LA142_0<=WINDOW_AGGREG)||(LA142_0>=INT_TYPE && LA142_0<=NULL_TYPE)||(LA142_0>=STAR && LA142_0<=PLUS)||(LA142_0>=BAND && LA142_0<=BXOR)||(LA142_0>=LT && LA142_0<=GE)||(LA142_0>=MINUS && LA142_0<=MOD)) ) {
                        alt142=1;
                    }
                    else if ( (LA142_0==SUBSELECT_GROUP_EXPR) ) {
                        alt142=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 142, 0, input);

                        throw nvae;
                    }
                    switch (alt142) {
                        case 1 :
                            // EsperEPL2Ast.g:407:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:407:22: ( valueExpr )*
                            loop141:
                            do {
                                int alt141=2;
                                int LA141_0 = input.LA(1);

                                if ( ((LA141_0>=IN_SET && LA141_0<=REGEXP)||LA141_0==NOT_EXPR||(LA141_0>=SUM && LA141_0<=AVG)||(LA141_0>=COALESCE && LA141_0<=COUNT)||(LA141_0>=CASE && LA141_0<=CASE2)||(LA141_0>=PREVIOUS && LA141_0<=EXISTS)||(LA141_0>=INSTANCEOF && LA141_0<=CURRENT_TIMESTAMP)||(LA141_0>=EVAL_AND_EXPR && LA141_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA141_0==EVENT_PROP_EXPR||LA141_0==CONCAT||(LA141_0>=LIB_FUNC_CHAIN && LA141_0<=DOT_EXPR)||LA141_0==ARRAY_EXPR||(LA141_0>=NOT_IN_SET && LA141_0<=NOT_REGEXP)||(LA141_0>=IN_RANGE && LA141_0<=SUBSELECT_EXPR)||(LA141_0>=EXISTS_SUBSELECT_EXPR && LA141_0<=NOT_IN_SUBSELECT_EXPR)||LA141_0==SUBSTITUTION||(LA141_0>=FIRST_AGGREG && LA141_0<=WINDOW_AGGREG)||(LA141_0>=INT_TYPE && LA141_0<=NULL_TYPE)||(LA141_0>=STAR && LA141_0<=PLUS)||(LA141_0>=BAND && LA141_0<=BXOR)||(LA141_0>=LT && LA141_0<=GE)||(LA141_0>=MINUS && LA141_0<=MOD)) ) {
                                    alt141=1;
                                }


                                switch (alt141) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:407:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2602);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop141;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:407:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2607);
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
            int alt150=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt150=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt150=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt150=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt150=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt150=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt150=6;
                }
                break;
            case NOT_EXPR:
                {
                alt150=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt150=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }

            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:413:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2633); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2635);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2637);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:413:42: ( valueExpr )*
                    loop144:
                    do {
                        int alt144=2;
                        int LA144_0 = input.LA(1);

                        if ( ((LA144_0>=IN_SET && LA144_0<=REGEXP)||LA144_0==NOT_EXPR||(LA144_0>=SUM && LA144_0<=AVG)||(LA144_0>=COALESCE && LA144_0<=COUNT)||(LA144_0>=CASE && LA144_0<=CASE2)||(LA144_0>=PREVIOUS && LA144_0<=EXISTS)||(LA144_0>=INSTANCEOF && LA144_0<=CURRENT_TIMESTAMP)||(LA144_0>=EVAL_AND_EXPR && LA144_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA144_0==EVENT_PROP_EXPR||LA144_0==CONCAT||(LA144_0>=LIB_FUNC_CHAIN && LA144_0<=DOT_EXPR)||LA144_0==ARRAY_EXPR||(LA144_0>=NOT_IN_SET && LA144_0<=NOT_REGEXP)||(LA144_0>=IN_RANGE && LA144_0<=SUBSELECT_EXPR)||(LA144_0>=EXISTS_SUBSELECT_EXPR && LA144_0<=NOT_IN_SUBSELECT_EXPR)||LA144_0==SUBSTITUTION||(LA144_0>=FIRST_AGGREG && LA144_0<=WINDOW_AGGREG)||(LA144_0>=INT_TYPE && LA144_0<=NULL_TYPE)||(LA144_0>=STAR && LA144_0<=PLUS)||(LA144_0>=BAND && LA144_0<=BXOR)||(LA144_0>=LT && LA144_0<=GE)||(LA144_0>=MINUS && LA144_0<=MOD)) ) {
                            alt144=1;
                        }


                        switch (alt144) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:413:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2640);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop144;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:414:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2654); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2656);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2658);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:414:43: ( valueExpr )*
                    loop145:
                    do {
                        int alt145=2;
                        int LA145_0 = input.LA(1);

                        if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||LA145_0==CONCAT||(LA145_0>=LIB_FUNC_CHAIN && LA145_0<=DOT_EXPR)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=SUBSELECT_EXPR)||(LA145_0>=EXISTS_SUBSELECT_EXPR && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=FIRST_AGGREG && LA145_0<=WINDOW_AGGREG)||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||(LA145_0>=STAR && LA145_0<=PLUS)||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=MINUS && LA145_0<=MOD)) ) {
                            alt145=1;
                        }


                        switch (alt145) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:414:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2661);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop145;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:415:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2675); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2677);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2679);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:416:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2691); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2693);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2695);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:417:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2707); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2709);
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
                    int alt147=2;
                    int LA147_0 = input.LA(1);

                    if ( (LA147_0==UP||(LA147_0>=IN_SET && LA147_0<=REGEXP)||LA147_0==NOT_EXPR||(LA147_0>=SUM && LA147_0<=AVG)||(LA147_0>=COALESCE && LA147_0<=COUNT)||(LA147_0>=CASE && LA147_0<=CASE2)||(LA147_0>=PREVIOUS && LA147_0<=EXISTS)||(LA147_0>=INSTANCEOF && LA147_0<=CURRENT_TIMESTAMP)||(LA147_0>=EVAL_AND_EXPR && LA147_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA147_0==EVENT_PROP_EXPR||LA147_0==CONCAT||(LA147_0>=LIB_FUNC_CHAIN && LA147_0<=DOT_EXPR)||LA147_0==ARRAY_EXPR||(LA147_0>=NOT_IN_SET && LA147_0<=NOT_REGEXP)||(LA147_0>=IN_RANGE && LA147_0<=SUBSELECT_EXPR)||(LA147_0>=EXISTS_SUBSELECT_EXPR && LA147_0<=NOT_IN_SUBSELECT_EXPR)||LA147_0==SUBSTITUTION||(LA147_0>=FIRST_AGGREG && LA147_0<=WINDOW_AGGREG)||(LA147_0>=INT_TYPE && LA147_0<=NULL_TYPE)||(LA147_0>=STAR && LA147_0<=PLUS)||(LA147_0>=BAND && LA147_0<=BXOR)||(LA147_0>=LT && LA147_0<=GE)||(LA147_0>=MINUS && LA147_0<=MOD)) ) {
                        alt147=1;
                    }
                    else if ( (LA147_0==SUBSELECT_GROUP_EXPR) ) {
                        alt147=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 147, 0, input);

                        throw nvae;
                    }
                    switch (alt147) {
                        case 1 :
                            // EsperEPL2Ast.g:417:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:417:59: ( valueExpr )*
                            loop146:
                            do {
                                int alt146=2;
                                int LA146_0 = input.LA(1);

                                if ( ((LA146_0>=IN_SET && LA146_0<=REGEXP)||LA146_0==NOT_EXPR||(LA146_0>=SUM && LA146_0<=AVG)||(LA146_0>=COALESCE && LA146_0<=COUNT)||(LA146_0>=CASE && LA146_0<=CASE2)||(LA146_0>=PREVIOUS && LA146_0<=EXISTS)||(LA146_0>=INSTANCEOF && LA146_0<=CURRENT_TIMESTAMP)||(LA146_0>=EVAL_AND_EXPR && LA146_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA146_0==EVENT_PROP_EXPR||LA146_0==CONCAT||(LA146_0>=LIB_FUNC_CHAIN && LA146_0<=DOT_EXPR)||LA146_0==ARRAY_EXPR||(LA146_0>=NOT_IN_SET && LA146_0<=NOT_REGEXP)||(LA146_0>=IN_RANGE && LA146_0<=SUBSELECT_EXPR)||(LA146_0>=EXISTS_SUBSELECT_EXPR && LA146_0<=NOT_IN_SUBSELECT_EXPR)||LA146_0==SUBSTITUTION||(LA146_0>=FIRST_AGGREG && LA146_0<=WINDOW_AGGREG)||(LA146_0>=INT_TYPE && LA146_0<=NULL_TYPE)||(LA146_0>=STAR && LA146_0<=PLUS)||(LA146_0>=BAND && LA146_0<=BXOR)||(LA146_0>=LT && LA146_0<=GE)||(LA146_0>=MINUS && LA146_0<=MOD)) ) {
                                    alt146=1;
                                }


                                switch (alt146) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:417:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2720);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop146;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:417:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2725);
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
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2738); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2740);
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
                            // EsperEPL2Ast.g:418:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:418:63: ( valueExpr )*
                            loop148:
                            do {
                                int alt148=2;
                                int LA148_0 = input.LA(1);

                                if ( ((LA148_0>=IN_SET && LA148_0<=REGEXP)||LA148_0==NOT_EXPR||(LA148_0>=SUM && LA148_0<=AVG)||(LA148_0>=COALESCE && LA148_0<=COUNT)||(LA148_0>=CASE && LA148_0<=CASE2)||(LA148_0>=PREVIOUS && LA148_0<=EXISTS)||(LA148_0>=INSTANCEOF && LA148_0<=CURRENT_TIMESTAMP)||(LA148_0>=EVAL_AND_EXPR && LA148_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA148_0==EVENT_PROP_EXPR||LA148_0==CONCAT||(LA148_0>=LIB_FUNC_CHAIN && LA148_0<=DOT_EXPR)||LA148_0==ARRAY_EXPR||(LA148_0>=NOT_IN_SET && LA148_0<=NOT_REGEXP)||(LA148_0>=IN_RANGE && LA148_0<=SUBSELECT_EXPR)||(LA148_0>=EXISTS_SUBSELECT_EXPR && LA148_0<=NOT_IN_SUBSELECT_EXPR)||LA148_0==SUBSTITUTION||(LA148_0>=FIRST_AGGREG && LA148_0<=WINDOW_AGGREG)||(LA148_0>=INT_TYPE && LA148_0<=NULL_TYPE)||(LA148_0>=STAR && LA148_0<=PLUS)||(LA148_0>=BAND && LA148_0<=BXOR)||(LA148_0>=LT && LA148_0<=GE)||(LA148_0>=MINUS && LA148_0<=MOD)) ) {
                                    alt148=1;
                                }


                                switch (alt148) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:418:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2751);
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
                            // EsperEPL2Ast.g:418:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2756);
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
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2769); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2771);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:420:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2782);
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
            int alt151=17;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt151=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt151=2;
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
                alt151=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt151=4;
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
                alt151=5;
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
                alt151=6;
                }
                break;
            case LIB_FUNC_CHAIN:
                {
                alt151=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt151=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt151=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt151=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt151=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt151=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt151=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt151=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt151=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt151=16;
                }
                break;
            case DOT_EXPR:
                {
                alt151=17;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 151, 0, input);

                throw nvae;
            }

            switch (alt151) {
                case 1 :
                    // EsperEPL2Ast.g:424:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2795);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:425:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2801);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:426:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2807);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:427:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2814);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:428:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2823);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:429:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2828);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:430:7: libFuncChain
                    {
                    pushFollow(FOLLOW_libFuncChain_in_valueExpr2836);
                    libFuncChain();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:431:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2841);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:432:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2846);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:433:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2852);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:434:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2857);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:435:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2862);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:436:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2867);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:437:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2872);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:438:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2878);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:439:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2885);
                    subSelectExistsExpr();

                    state._fsp--;


                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:440:4: dotExpr
                    {
                    pushFollow(FOLLOW_dotExpr_in_valueExpr2890);
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
            int alt153=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt153=1;
                }
                break;
            case LW:
                {
                alt153=2;
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
                alt153=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt153=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt153=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt153=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt153=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt153=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt153=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt153=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt153=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 153, 0, input);

                throw nvae;
            }

            switch (alt153) {
                case 1 :
                    // EsperEPL2Ast.g:444:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2903); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:445:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2912); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:446:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2919);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:447:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2927); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2929);
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
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2944);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:449:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2950);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:450:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2955);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:451:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2960);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:452:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2970); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:452:29: ( numericParameterList )+
                    int cnt152=0;
                    loop152:
                    do {
                        int alt152=2;
                        int LA152_0 = input.LA(1);

                        if ( (LA152_0==NUMERIC_PARAM_RANGE||LA152_0==NUMERIC_PARAM_FREQUENCY||(LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)) ) {
                            alt152=1;
                        }


                        switch (alt152) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:452:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2972);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt152 >= 1 ) break loop152;
                                EarlyExitException eee =
                                    new EarlyExitException(152, input);
                                throw eee;
                        }
                        cnt152++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:453:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2983); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:454:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2990);
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
            int alt154=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt154=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt154=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt154=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 154, 0, input);

                throw nvae;
            }

            switch (alt154) {
                case 1 :
                    // EsperEPL2Ast.g:458:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList3003);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:459:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList3010);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:460:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList3016);
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
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3032); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:464:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt155=3;
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
            case EVENT_PROP_EXPR:
                {
                alt155=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt155=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 155, 0, input);

                throw nvae;
            }

            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:464:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3035);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:464:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3038);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:464:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3041);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:464:83: ( constant[true] | eventPropertyExpr[true] | substitution )
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
            case EVENT_PROP_EXPR:
                {
                alt156=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:464:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator3045);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:464:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator3048);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:464:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator3051);
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
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3072); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:468:33: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:468:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator3075);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:468:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator3078);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:468:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator3081);
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
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator3100); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:472:23: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:472:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator3103);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:472:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator3106);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:472:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator3109);
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
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3128); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:476:26: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:476:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator3131);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:476:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator3134);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:476:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator3137);
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
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3158); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr3160);
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
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3179); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr3181);
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
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3200); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr3202);
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
            int alt160=2;
            int LA160_0 = input.LA(1);

            if ( (LA160_0==IN_SUBSELECT_EXPR) ) {
                alt160=1;
            }
            else if ( (LA160_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt160=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 160, 0, input);

                throw nvae;
            }
            switch (alt160) {
                case 1 :
                    // EsperEPL2Ast.g:492:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3221); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3223);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3225);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:493:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3237); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3239);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3241);
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
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3260); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3262);
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
            int alt161=2;
            int LA161_0 = input.LA(1);

            if ( (LA161_0==DISTINCT) ) {
                alt161=1;
            }
            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:501:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3278); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_subQueryExpr3281);
            selectionList();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3283);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:501:48: ( whereClause[true] )?
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==WHERE_EXPR) ) {
                alt162=1;
            }
            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:501:49: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3286);
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
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3304); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3306);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:505:36: ( viewListExpr )?
            int alt163=2;
            int LA163_0 = input.LA(1);

            if ( (LA163_0==VIEW_EXPR) ) {
                alt163=1;
            }
            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:505:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3309);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:505:52: ( IDENT )?
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==IDENT) ) {
                alt164=1;
            }
            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:505:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3314); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:505:61: ( RETAINUNION )?
            int alt165=2;
            int LA165_0 = input.LA(1);

            if ( (LA165_0==RETAINUNION) ) {
                alt165=1;
            }
            switch (alt165) {
                case 1 :
                    // EsperEPL2Ast.g:505:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3318); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:505:74: ( RETAININTERSECTION )?
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==RETAININTERSECTION) ) {
                alt166=1;
            }
            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:505:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3321); 

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
            int alt169=2;
            int LA169_0 = input.LA(1);

            if ( (LA169_0==CASE) ) {
                alt169=1;
            }
            else if ( (LA169_0==CASE2) ) {
                alt169=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 169, 0, input);

                throw nvae;
            }
            switch (alt169) {
                case 1 :
                    // EsperEPL2Ast.g:509:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3341); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:509:13: ( valueExpr )*
                        loop167:
                        do {
                            int alt167=2;
                            int LA167_0 = input.LA(1);

                            if ( ((LA167_0>=IN_SET && LA167_0<=REGEXP)||LA167_0==NOT_EXPR||(LA167_0>=SUM && LA167_0<=AVG)||(LA167_0>=COALESCE && LA167_0<=COUNT)||(LA167_0>=CASE && LA167_0<=CASE2)||(LA167_0>=PREVIOUS && LA167_0<=EXISTS)||(LA167_0>=INSTANCEOF && LA167_0<=CURRENT_TIMESTAMP)||(LA167_0>=EVAL_AND_EXPR && LA167_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA167_0==EVENT_PROP_EXPR||LA167_0==CONCAT||(LA167_0>=LIB_FUNC_CHAIN && LA167_0<=DOT_EXPR)||LA167_0==ARRAY_EXPR||(LA167_0>=NOT_IN_SET && LA167_0<=NOT_REGEXP)||(LA167_0>=IN_RANGE && LA167_0<=SUBSELECT_EXPR)||(LA167_0>=EXISTS_SUBSELECT_EXPR && LA167_0<=NOT_IN_SUBSELECT_EXPR)||LA167_0==SUBSTITUTION||(LA167_0>=FIRST_AGGREG && LA167_0<=WINDOW_AGGREG)||(LA167_0>=INT_TYPE && LA167_0<=NULL_TYPE)||(LA167_0>=STAR && LA167_0<=PLUS)||(LA167_0>=BAND && LA167_0<=BXOR)||(LA167_0>=LT && LA167_0<=GE)||(LA167_0>=MINUS && LA167_0<=MOD)) ) {
                                alt167=1;
                            }


                            switch (alt167) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:509:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3344);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop167;
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
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3357); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:510:14: ( valueExpr )*
                        loop168:
                        do {
                            int alt168=2;
                            int LA168_0 = input.LA(1);

                            if ( ((LA168_0>=IN_SET && LA168_0<=REGEXP)||LA168_0==NOT_EXPR||(LA168_0>=SUM && LA168_0<=AVG)||(LA168_0>=COALESCE && LA168_0<=COUNT)||(LA168_0>=CASE && LA168_0<=CASE2)||(LA168_0>=PREVIOUS && LA168_0<=EXISTS)||(LA168_0>=INSTANCEOF && LA168_0<=CURRENT_TIMESTAMP)||(LA168_0>=EVAL_AND_EXPR && LA168_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA168_0==EVENT_PROP_EXPR||LA168_0==CONCAT||(LA168_0>=LIB_FUNC_CHAIN && LA168_0<=DOT_EXPR)||LA168_0==ARRAY_EXPR||(LA168_0>=NOT_IN_SET && LA168_0<=NOT_REGEXP)||(LA168_0>=IN_RANGE && LA168_0<=SUBSELECT_EXPR)||(LA168_0>=EXISTS_SUBSELECT_EXPR && LA168_0<=NOT_IN_SUBSELECT_EXPR)||LA168_0==SUBSTITUTION||(LA168_0>=FIRST_AGGREG && LA168_0<=WINDOW_AGGREG)||(LA168_0>=INT_TYPE && LA168_0<=NULL_TYPE)||(LA168_0>=STAR && LA168_0<=PLUS)||(LA168_0>=BAND && LA168_0<=BXOR)||(LA168_0>=LT && LA168_0<=GE)||(LA168_0>=MINUS && LA168_0<=MOD)) ) {
                                alt168=1;
                            }


                            switch (alt168) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:510:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3360);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop168;
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
            int alt172=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt172=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt172=2;
                }
                break;
            case IN_RANGE:
                {
                alt172=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt172=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 172, 0, input);

                throw nvae;
            }

            switch (alt172) {
                case 1 :
                    // EsperEPL2Ast.g:514:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3380); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3382);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3390);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:514:51: ( valueExpr )*
                    loop170:
                    do {
                        int alt170=2;
                        int LA170_0 = input.LA(1);

                        if ( ((LA170_0>=IN_SET && LA170_0<=REGEXP)||LA170_0==NOT_EXPR||(LA170_0>=SUM && LA170_0<=AVG)||(LA170_0>=COALESCE && LA170_0<=COUNT)||(LA170_0>=CASE && LA170_0<=CASE2)||(LA170_0>=PREVIOUS && LA170_0<=EXISTS)||(LA170_0>=INSTANCEOF && LA170_0<=CURRENT_TIMESTAMP)||(LA170_0>=EVAL_AND_EXPR && LA170_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA170_0==EVENT_PROP_EXPR||LA170_0==CONCAT||(LA170_0>=LIB_FUNC_CHAIN && LA170_0<=DOT_EXPR)||LA170_0==ARRAY_EXPR||(LA170_0>=NOT_IN_SET && LA170_0<=NOT_REGEXP)||(LA170_0>=IN_RANGE && LA170_0<=SUBSELECT_EXPR)||(LA170_0>=EXISTS_SUBSELECT_EXPR && LA170_0<=NOT_IN_SUBSELECT_EXPR)||LA170_0==SUBSTITUTION||(LA170_0>=FIRST_AGGREG && LA170_0<=WINDOW_AGGREG)||(LA170_0>=INT_TYPE && LA170_0<=NULL_TYPE)||(LA170_0>=STAR && LA170_0<=PLUS)||(LA170_0>=BAND && LA170_0<=BXOR)||(LA170_0>=LT && LA170_0<=GE)||(LA170_0>=MINUS && LA170_0<=MOD)) ) {
                            alt170=1;
                        }


                        switch (alt170) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:514:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3393);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop170;
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
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3412); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3414);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3422);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:515:55: ( valueExpr )*
                    loop171:
                    do {
                        int alt171=2;
                        int LA171_0 = input.LA(1);

                        if ( ((LA171_0>=IN_SET && LA171_0<=REGEXP)||LA171_0==NOT_EXPR||(LA171_0>=SUM && LA171_0<=AVG)||(LA171_0>=COALESCE && LA171_0<=COUNT)||(LA171_0>=CASE && LA171_0<=CASE2)||(LA171_0>=PREVIOUS && LA171_0<=EXISTS)||(LA171_0>=INSTANCEOF && LA171_0<=CURRENT_TIMESTAMP)||(LA171_0>=EVAL_AND_EXPR && LA171_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA171_0==EVENT_PROP_EXPR||LA171_0==CONCAT||(LA171_0>=LIB_FUNC_CHAIN && LA171_0<=DOT_EXPR)||LA171_0==ARRAY_EXPR||(LA171_0>=NOT_IN_SET && LA171_0<=NOT_REGEXP)||(LA171_0>=IN_RANGE && LA171_0<=SUBSELECT_EXPR)||(LA171_0>=EXISTS_SUBSELECT_EXPR && LA171_0<=NOT_IN_SUBSELECT_EXPR)||LA171_0==SUBSTITUTION||(LA171_0>=FIRST_AGGREG && LA171_0<=WINDOW_AGGREG)||(LA171_0>=INT_TYPE && LA171_0<=NULL_TYPE)||(LA171_0>=STAR && LA171_0<=PLUS)||(LA171_0>=BAND && LA171_0<=BXOR)||(LA171_0>=LT && LA171_0<=GE)||(LA171_0>=MINUS && LA171_0<=MOD)) ) {
                            alt171=1;
                        }


                        switch (alt171) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:515:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3425);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop171;
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
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3444); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3446);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3454);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3456);
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
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3473); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3475);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3483);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3485);
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
            int alt174=2;
            int LA174_0 = input.LA(1);

            if ( (LA174_0==BETWEEN) ) {
                alt174=1;
            }
            else if ( (LA174_0==NOT_BETWEEN) ) {
                alt174=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }
            switch (alt174) {
                case 1 :
                    // EsperEPL2Ast.g:521:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3510); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3512);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3514);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3516);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:522:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3527); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3529);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3531);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:522:40: ( valueExpr )*
                    loop173:
                    do {
                        int alt173=2;
                        int LA173_0 = input.LA(1);

                        if ( ((LA173_0>=IN_SET && LA173_0<=REGEXP)||LA173_0==NOT_EXPR||(LA173_0>=SUM && LA173_0<=AVG)||(LA173_0>=COALESCE && LA173_0<=COUNT)||(LA173_0>=CASE && LA173_0<=CASE2)||(LA173_0>=PREVIOUS && LA173_0<=EXISTS)||(LA173_0>=INSTANCEOF && LA173_0<=CURRENT_TIMESTAMP)||(LA173_0>=EVAL_AND_EXPR && LA173_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA173_0==EVENT_PROP_EXPR||LA173_0==CONCAT||(LA173_0>=LIB_FUNC_CHAIN && LA173_0<=DOT_EXPR)||LA173_0==ARRAY_EXPR||(LA173_0>=NOT_IN_SET && LA173_0<=NOT_REGEXP)||(LA173_0>=IN_RANGE && LA173_0<=SUBSELECT_EXPR)||(LA173_0>=EXISTS_SUBSELECT_EXPR && LA173_0<=NOT_IN_SUBSELECT_EXPR)||LA173_0==SUBSTITUTION||(LA173_0>=FIRST_AGGREG && LA173_0<=WINDOW_AGGREG)||(LA173_0>=INT_TYPE && LA173_0<=NULL_TYPE)||(LA173_0>=STAR && LA173_0<=PLUS)||(LA173_0>=BAND && LA173_0<=BXOR)||(LA173_0>=LT && LA173_0<=GE)||(LA173_0>=MINUS && LA173_0<=MOD)) ) {
                            alt173=1;
                        }


                        switch (alt173) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:522:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3534);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop173;
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
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==LIKE) ) {
                alt177=1;
            }
            else if ( (LA177_0==NOT_LIKE) ) {
                alt177=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 177, 0, input);

                throw nvae;
            }
            switch (alt177) {
                case 1 :
                    // EsperEPL2Ast.g:526:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3554); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3556);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3558);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:526:33: ( valueExpr )?
                    int alt175=2;
                    int LA175_0 = input.LA(1);

                    if ( ((LA175_0>=IN_SET && LA175_0<=REGEXP)||LA175_0==NOT_EXPR||(LA175_0>=SUM && LA175_0<=AVG)||(LA175_0>=COALESCE && LA175_0<=COUNT)||(LA175_0>=CASE && LA175_0<=CASE2)||(LA175_0>=PREVIOUS && LA175_0<=EXISTS)||(LA175_0>=INSTANCEOF && LA175_0<=CURRENT_TIMESTAMP)||(LA175_0>=EVAL_AND_EXPR && LA175_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA175_0==EVENT_PROP_EXPR||LA175_0==CONCAT||(LA175_0>=LIB_FUNC_CHAIN && LA175_0<=DOT_EXPR)||LA175_0==ARRAY_EXPR||(LA175_0>=NOT_IN_SET && LA175_0<=NOT_REGEXP)||(LA175_0>=IN_RANGE && LA175_0<=SUBSELECT_EXPR)||(LA175_0>=EXISTS_SUBSELECT_EXPR && LA175_0<=NOT_IN_SUBSELECT_EXPR)||LA175_0==SUBSTITUTION||(LA175_0>=FIRST_AGGREG && LA175_0<=WINDOW_AGGREG)||(LA175_0>=INT_TYPE && LA175_0<=NULL_TYPE)||(LA175_0>=STAR && LA175_0<=PLUS)||(LA175_0>=BAND && LA175_0<=BXOR)||(LA175_0>=LT && LA175_0<=GE)||(LA175_0>=MINUS && LA175_0<=MOD)) ) {
                        alt175=1;
                    }
                    switch (alt175) {
                        case 1 :
                            // EsperEPL2Ast.g:526:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3561);
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
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3574); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3576);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3578);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:527:37: ( valueExpr )?
                    int alt176=2;
                    int LA176_0 = input.LA(1);

                    if ( ((LA176_0>=IN_SET && LA176_0<=REGEXP)||LA176_0==NOT_EXPR||(LA176_0>=SUM && LA176_0<=AVG)||(LA176_0>=COALESCE && LA176_0<=COUNT)||(LA176_0>=CASE && LA176_0<=CASE2)||(LA176_0>=PREVIOUS && LA176_0<=EXISTS)||(LA176_0>=INSTANCEOF && LA176_0<=CURRENT_TIMESTAMP)||(LA176_0>=EVAL_AND_EXPR && LA176_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA176_0==EVENT_PROP_EXPR||LA176_0==CONCAT||(LA176_0>=LIB_FUNC_CHAIN && LA176_0<=DOT_EXPR)||LA176_0==ARRAY_EXPR||(LA176_0>=NOT_IN_SET && LA176_0<=NOT_REGEXP)||(LA176_0>=IN_RANGE && LA176_0<=SUBSELECT_EXPR)||(LA176_0>=EXISTS_SUBSELECT_EXPR && LA176_0<=NOT_IN_SUBSELECT_EXPR)||LA176_0==SUBSTITUTION||(LA176_0>=FIRST_AGGREG && LA176_0<=WINDOW_AGGREG)||(LA176_0>=INT_TYPE && LA176_0<=NULL_TYPE)||(LA176_0>=STAR && LA176_0<=PLUS)||(LA176_0>=BAND && LA176_0<=BXOR)||(LA176_0>=LT && LA176_0<=GE)||(LA176_0>=MINUS && LA176_0<=MOD)) ) {
                        alt176=1;
                    }
                    switch (alt176) {
                        case 1 :
                            // EsperEPL2Ast.g:527:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3581);
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
            int alt178=2;
            int LA178_0 = input.LA(1);

            if ( (LA178_0==REGEXP) ) {
                alt178=1;
            }
            else if ( (LA178_0==NOT_REGEXP) ) {
                alt178=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 178, 0, input);

                throw nvae;
            }
            switch (alt178) {
                case 1 :
                    // EsperEPL2Ast.g:531:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3600); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3602);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3604);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:532:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3615); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3617);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3619);
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
            int alt195=19;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt195=1;
                }
                break;
            case AVG:
                {
                alt195=2;
                }
                break;
            case COUNT:
                {
                alt195=3;
                }
                break;
            case MEDIAN:
                {
                alt195=4;
                }
                break;
            case STDDEV:
                {
                alt195=5;
                }
                break;
            case AVEDEV:
                {
                alt195=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt195=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt195=8;
                }
                break;
            case WINDOW_AGGREG:
                {
                alt195=9;
                }
                break;
            case COALESCE:
                {
                alt195=10;
                }
                break;
            case PREVIOUS:
                {
                alt195=11;
                }
                break;
            case PREVIOUSTAIL:
                {
                alt195=12;
                }
                break;
            case PREVIOUSCOUNT:
                {
                alt195=13;
                }
                break;
            case PREVIOUSWINDOW:
                {
                alt195=14;
                }
                break;
            case PRIOR:
                {
                alt195=15;
                }
                break;
            case INSTANCEOF:
                {
                alt195=16;
                }
                break;
            case CAST:
                {
                alt195=17;
                }
                break;
            case EXISTS:
                {
                alt195=18;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt195=19;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 195, 0, input);

                throw nvae;
            }

            switch (alt195) {
                case 1 :
                    // EsperEPL2Ast.g:536:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3638); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:536:13: ( DISTINCT )?
                    int alt179=2;
                    int LA179_0 = input.LA(1);

                    if ( (LA179_0==DISTINCT) ) {
                        alt179=1;
                    }
                    switch (alt179) {
                        case 1 :
                            // EsperEPL2Ast.g:536:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3641); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3645);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:537:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3656); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:537:12: ( DISTINCT )?
                    int alt180=2;
                    int LA180_0 = input.LA(1);

                    if ( (LA180_0==DISTINCT) ) {
                        alt180=1;
                    }
                    switch (alt180) {
                        case 1 :
                            // EsperEPL2Ast.g:537:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3659); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3663);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:538:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3674); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:538:14: ( ( DISTINCT )? valueExpr )?
                        int alt182=2;
                        int LA182_0 = input.LA(1);

                        if ( ((LA182_0>=IN_SET && LA182_0<=REGEXP)||LA182_0==NOT_EXPR||(LA182_0>=SUM && LA182_0<=AVG)||(LA182_0>=COALESCE && LA182_0<=COUNT)||(LA182_0>=CASE && LA182_0<=CASE2)||LA182_0==DISTINCT||(LA182_0>=PREVIOUS && LA182_0<=EXISTS)||(LA182_0>=INSTANCEOF && LA182_0<=CURRENT_TIMESTAMP)||(LA182_0>=EVAL_AND_EXPR && LA182_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA182_0==EVENT_PROP_EXPR||LA182_0==CONCAT||(LA182_0>=LIB_FUNC_CHAIN && LA182_0<=DOT_EXPR)||LA182_0==ARRAY_EXPR||(LA182_0>=NOT_IN_SET && LA182_0<=NOT_REGEXP)||(LA182_0>=IN_RANGE && LA182_0<=SUBSELECT_EXPR)||(LA182_0>=EXISTS_SUBSELECT_EXPR && LA182_0<=NOT_IN_SUBSELECT_EXPR)||LA182_0==SUBSTITUTION||(LA182_0>=FIRST_AGGREG && LA182_0<=WINDOW_AGGREG)||(LA182_0>=INT_TYPE && LA182_0<=NULL_TYPE)||(LA182_0>=STAR && LA182_0<=PLUS)||(LA182_0>=BAND && LA182_0<=BXOR)||(LA182_0>=LT && LA182_0<=GE)||(LA182_0>=MINUS && LA182_0<=MOD)) ) {
                            alt182=1;
                        }
                        switch (alt182) {
                            case 1 :
                                // EsperEPL2Ast.g:538:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:538:15: ( DISTINCT )?
                                int alt181=2;
                                int LA181_0 = input.LA(1);

                                if ( (LA181_0==DISTINCT) ) {
                                    alt181=1;
                                }
                                switch (alt181) {
                                    case 1 :
                                        // EsperEPL2Ast.g:538:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3678); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3682);
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
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3696); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:539:15: ( DISTINCT )?
                    int alt183=2;
                    int LA183_0 = input.LA(1);

                    if ( (LA183_0==DISTINCT) ) {
                        alt183=1;
                    }
                    switch (alt183) {
                        case 1 :
                            // EsperEPL2Ast.g:539:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3699); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3703);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:540:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3714); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:540:15: ( DISTINCT )?
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( (LA184_0==DISTINCT) ) {
                        alt184=1;
                    }
                    switch (alt184) {
                        case 1 :
                            // EsperEPL2Ast.g:540:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3717); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3721);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:541:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3732); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:541:15: ( DISTINCT )?
                    int alt185=2;
                    int LA185_0 = input.LA(1);

                    if ( (LA185_0==DISTINCT) ) {
                        alt185=1;
                    }
                    switch (alt185) {
                        case 1 :
                            // EsperEPL2Ast.g:541:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3735); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3739);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:542:4: ^(f= LAST_AGGREG ( DISTINCT )? accessValueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3750); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:542:20: ( DISTINCT )?
                    int alt186=2;
                    int LA186_0 = input.LA(1);

                    if ( (LA186_0==DISTINCT) ) {
                        alt186=1;
                    }
                    switch (alt186) {
                        case 1 :
                            // EsperEPL2Ast.g:542:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3753); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3757);
                    accessValueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:542:48: ( valueExpr )?
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( ((LA187_0>=IN_SET && LA187_0<=REGEXP)||LA187_0==NOT_EXPR||(LA187_0>=SUM && LA187_0<=AVG)||(LA187_0>=COALESCE && LA187_0<=COUNT)||(LA187_0>=CASE && LA187_0<=CASE2)||(LA187_0>=PREVIOUS && LA187_0<=EXISTS)||(LA187_0>=INSTANCEOF && LA187_0<=CURRENT_TIMESTAMP)||(LA187_0>=EVAL_AND_EXPR && LA187_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA187_0==EVENT_PROP_EXPR||LA187_0==CONCAT||(LA187_0>=LIB_FUNC_CHAIN && LA187_0<=DOT_EXPR)||LA187_0==ARRAY_EXPR||(LA187_0>=NOT_IN_SET && LA187_0<=NOT_REGEXP)||(LA187_0>=IN_RANGE && LA187_0<=SUBSELECT_EXPR)||(LA187_0>=EXISTS_SUBSELECT_EXPR && LA187_0<=NOT_IN_SUBSELECT_EXPR)||LA187_0==SUBSTITUTION||(LA187_0>=FIRST_AGGREG && LA187_0<=WINDOW_AGGREG)||(LA187_0>=INT_TYPE && LA187_0<=NULL_TYPE)||(LA187_0>=STAR && LA187_0<=PLUS)||(LA187_0>=BAND && LA187_0<=BXOR)||(LA187_0>=LT && LA187_0<=GE)||(LA187_0>=MINUS && LA187_0<=MOD)) ) {
                        alt187=1;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:542:48: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3759);
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
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3771); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:543:21: ( DISTINCT )?
                    int alt188=2;
                    int LA188_0 = input.LA(1);

                    if ( (LA188_0==DISTINCT) ) {
                        alt188=1;
                    }
                    switch (alt188) {
                        case 1 :
                            // EsperEPL2Ast.g:543:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3774); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3778);
                    accessValueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:543:49: ( valueExpr )?
                    int alt189=2;
                    int LA189_0 = input.LA(1);

                    if ( ((LA189_0>=IN_SET && LA189_0<=REGEXP)||LA189_0==NOT_EXPR||(LA189_0>=SUM && LA189_0<=AVG)||(LA189_0>=COALESCE && LA189_0<=COUNT)||(LA189_0>=CASE && LA189_0<=CASE2)||(LA189_0>=PREVIOUS && LA189_0<=EXISTS)||(LA189_0>=INSTANCEOF && LA189_0<=CURRENT_TIMESTAMP)||(LA189_0>=EVAL_AND_EXPR && LA189_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA189_0==EVENT_PROP_EXPR||LA189_0==CONCAT||(LA189_0>=LIB_FUNC_CHAIN && LA189_0<=DOT_EXPR)||LA189_0==ARRAY_EXPR||(LA189_0>=NOT_IN_SET && LA189_0<=NOT_REGEXP)||(LA189_0>=IN_RANGE && LA189_0<=SUBSELECT_EXPR)||(LA189_0>=EXISTS_SUBSELECT_EXPR && LA189_0<=NOT_IN_SUBSELECT_EXPR)||LA189_0==SUBSTITUTION||(LA189_0>=FIRST_AGGREG && LA189_0<=WINDOW_AGGREG)||(LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)||(LA189_0>=STAR && LA189_0<=PLUS)||(LA189_0>=BAND && LA189_0<=BXOR)||(LA189_0>=LT && LA189_0<=GE)||(LA189_0>=MINUS && LA189_0<=MOD)) ) {
                        alt189=1;
                    }
                    switch (alt189) {
                        case 1 :
                            // EsperEPL2Ast.g:543:49: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3780);
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
                    f=(CommonTree)match(input,WINDOW_AGGREG,FOLLOW_WINDOW_AGGREG_in_builtinFunc3792); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:544:22: ( DISTINCT )?
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( (LA190_0==DISTINCT) ) {
                        alt190=1;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:544:23: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3795); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_accessValueExpr_in_builtinFunc3799);
                    accessValueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:545:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3811); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3813);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3815);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:545:38: ( valueExpr )*
                    loop191:
                    do {
                        int alt191=2;
                        int LA191_0 = input.LA(1);

                        if ( ((LA191_0>=IN_SET && LA191_0<=REGEXP)||LA191_0==NOT_EXPR||(LA191_0>=SUM && LA191_0<=AVG)||(LA191_0>=COALESCE && LA191_0<=COUNT)||(LA191_0>=CASE && LA191_0<=CASE2)||(LA191_0>=PREVIOUS && LA191_0<=EXISTS)||(LA191_0>=INSTANCEOF && LA191_0<=CURRENT_TIMESTAMP)||(LA191_0>=EVAL_AND_EXPR && LA191_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA191_0==EVENT_PROP_EXPR||LA191_0==CONCAT||(LA191_0>=LIB_FUNC_CHAIN && LA191_0<=DOT_EXPR)||LA191_0==ARRAY_EXPR||(LA191_0>=NOT_IN_SET && LA191_0<=NOT_REGEXP)||(LA191_0>=IN_RANGE && LA191_0<=SUBSELECT_EXPR)||(LA191_0>=EXISTS_SUBSELECT_EXPR && LA191_0<=NOT_IN_SUBSELECT_EXPR)||LA191_0==SUBSTITUTION||(LA191_0>=FIRST_AGGREG && LA191_0<=WINDOW_AGGREG)||(LA191_0>=INT_TYPE && LA191_0<=NULL_TYPE)||(LA191_0>=STAR && LA191_0<=PLUS)||(LA191_0>=BAND && LA191_0<=BXOR)||(LA191_0>=LT && LA191_0<=GE)||(LA191_0>=MINUS && LA191_0<=MOD)) ) {
                            alt191=1;
                        }


                        switch (alt191) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:545:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3818);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop191;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:546:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3833); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3835);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:546:28: ( valueExpr )?
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( ((LA192_0>=IN_SET && LA192_0<=REGEXP)||LA192_0==NOT_EXPR||(LA192_0>=SUM && LA192_0<=AVG)||(LA192_0>=COALESCE && LA192_0<=COUNT)||(LA192_0>=CASE && LA192_0<=CASE2)||(LA192_0>=PREVIOUS && LA192_0<=EXISTS)||(LA192_0>=INSTANCEOF && LA192_0<=CURRENT_TIMESTAMP)||(LA192_0>=EVAL_AND_EXPR && LA192_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA192_0==EVENT_PROP_EXPR||LA192_0==CONCAT||(LA192_0>=LIB_FUNC_CHAIN && LA192_0<=DOT_EXPR)||LA192_0==ARRAY_EXPR||(LA192_0>=NOT_IN_SET && LA192_0<=NOT_REGEXP)||(LA192_0>=IN_RANGE && LA192_0<=SUBSELECT_EXPR)||(LA192_0>=EXISTS_SUBSELECT_EXPR && LA192_0<=NOT_IN_SUBSELECT_EXPR)||LA192_0==SUBSTITUTION||(LA192_0>=FIRST_AGGREG && LA192_0<=WINDOW_AGGREG)||(LA192_0>=INT_TYPE && LA192_0<=NULL_TYPE)||(LA192_0>=STAR && LA192_0<=PLUS)||(LA192_0>=BAND && LA192_0<=BXOR)||(LA192_0>=LT && LA192_0<=GE)||(LA192_0>=MINUS && LA192_0<=MOD)) ) {
                        alt192=1;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:546:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3837);
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
                    f=(CommonTree)match(input,PREVIOUSTAIL,FOLLOW_PREVIOUSTAIL_in_builtinFunc3850); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3852);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:547:32: ( valueExpr )?
                    int alt193=2;
                    int LA193_0 = input.LA(1);

                    if ( ((LA193_0>=IN_SET && LA193_0<=REGEXP)||LA193_0==NOT_EXPR||(LA193_0>=SUM && LA193_0<=AVG)||(LA193_0>=COALESCE && LA193_0<=COUNT)||(LA193_0>=CASE && LA193_0<=CASE2)||(LA193_0>=PREVIOUS && LA193_0<=EXISTS)||(LA193_0>=INSTANCEOF && LA193_0<=CURRENT_TIMESTAMP)||(LA193_0>=EVAL_AND_EXPR && LA193_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA193_0==EVENT_PROP_EXPR||LA193_0==CONCAT||(LA193_0>=LIB_FUNC_CHAIN && LA193_0<=DOT_EXPR)||LA193_0==ARRAY_EXPR||(LA193_0>=NOT_IN_SET && LA193_0<=NOT_REGEXP)||(LA193_0>=IN_RANGE && LA193_0<=SUBSELECT_EXPR)||(LA193_0>=EXISTS_SUBSELECT_EXPR && LA193_0<=NOT_IN_SUBSELECT_EXPR)||LA193_0==SUBSTITUTION||(LA193_0>=FIRST_AGGREG && LA193_0<=WINDOW_AGGREG)||(LA193_0>=INT_TYPE && LA193_0<=NULL_TYPE)||(LA193_0>=STAR && LA193_0<=PLUS)||(LA193_0>=BAND && LA193_0<=BXOR)||(LA193_0>=LT && LA193_0<=GE)||(LA193_0>=MINUS && LA193_0<=MOD)) ) {
                        alt193=1;
                    }
                    switch (alt193) {
                        case 1 :
                            // EsperEPL2Ast.g:547:32: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3854);
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
                    f=(CommonTree)match(input,PREVIOUSCOUNT,FOLLOW_PREVIOUSCOUNT_in_builtinFunc3867); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3869);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:549:5: ^(f= PREVIOUSWINDOW valueExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUSWINDOW,FOLLOW_PREVIOUSWINDOW_in_builtinFunc3881); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3883);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:550:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3895); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3899); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3901);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:551:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3914); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3916);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3918); 
                    // EsperEPL2Ast.g:551:42: ( CLASS_IDENT )*
                    loop194:
                    do {
                        int alt194=2;
                        int LA194_0 = input.LA(1);

                        if ( (LA194_0==CLASS_IDENT) ) {
                            alt194=1;
                        }


                        switch (alt194) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:551:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3921); 

                    	    }
                    	    break;

                    	default :
                    	    break loop194;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 17 :
                    // EsperEPL2Ast.g:552:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3935); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3937);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3939); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 18 :
                    // EsperEPL2Ast.g:553:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3951); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3953);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 19 :
                    // EsperEPL2Ast.g:554:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3965); 



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
            int alt197=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt197=1;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt197=2;
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
                alt197=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 197, 0, input);

                throw nvae;
            }

            switch (alt197) {
                case 1 :
                    // EsperEPL2Ast.g:558:5: PROPERTY_WILDCARD_SELECT
                    {
                    match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExpr3982); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:558:32: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExpr3989); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_accessValueExpr3991); 
                    // EsperEPL2Ast.g:558:68: ( IDENT )?
                    int alt196=2;
                    int LA196_0 = input.LA(1);

                    if ( (LA196_0==IDENT) ) {
                        alt196=1;
                    }
                    switch (alt196) {
                        case 1 :
                            // EsperEPL2Ast.g:558:68: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_accessValueExpr3993); 

                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:558:78: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_accessValueExpr3999);
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
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr4016); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:562:19: ( valueExpr )*
                loop198:
                do {
                    int alt198=2;
                    int LA198_0 = input.LA(1);

                    if ( ((LA198_0>=IN_SET && LA198_0<=REGEXP)||LA198_0==NOT_EXPR||(LA198_0>=SUM && LA198_0<=AVG)||(LA198_0>=COALESCE && LA198_0<=COUNT)||(LA198_0>=CASE && LA198_0<=CASE2)||(LA198_0>=PREVIOUS && LA198_0<=EXISTS)||(LA198_0>=INSTANCEOF && LA198_0<=CURRENT_TIMESTAMP)||(LA198_0>=EVAL_AND_EXPR && LA198_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA198_0==EVENT_PROP_EXPR||LA198_0==CONCAT||(LA198_0>=LIB_FUNC_CHAIN && LA198_0<=DOT_EXPR)||LA198_0==ARRAY_EXPR||(LA198_0>=NOT_IN_SET && LA198_0<=NOT_REGEXP)||(LA198_0>=IN_RANGE && LA198_0<=SUBSELECT_EXPR)||(LA198_0>=EXISTS_SUBSELECT_EXPR && LA198_0<=NOT_IN_SUBSELECT_EXPR)||LA198_0==SUBSTITUTION||(LA198_0>=FIRST_AGGREG && LA198_0<=WINDOW_AGGREG)||(LA198_0>=INT_TYPE && LA198_0<=NULL_TYPE)||(LA198_0>=STAR && LA198_0<=PLUS)||(LA198_0>=BAND && LA198_0<=BXOR)||(LA198_0>=LT && LA198_0<=GE)||(LA198_0>=MINUS && LA198_0<=MOD)) ) {
                        alt198=1;
                    }


                    switch (alt198) {
                	case 1 :
                	    // EsperEPL2Ast.g:562:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr4019);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop198;
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
            int alt200=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt200=1;
                }
                break;
            case MINUS:
                {
                alt200=2;
                }
                break;
            case DIV:
                {
                alt200=3;
                }
                break;
            case STAR:
                {
                alt200=4;
                }
                break;
            case MOD:
                {
                alt200=5;
                }
                break;
            case BAND:
                {
                alt200=6;
                }
                break;
            case BOR:
                {
                alt200=7;
                }
                break;
            case BXOR:
                {
                alt200=8;
                }
                break;
            case CONCAT:
                {
                alt200=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 200, 0, input);

                throw nvae;
            }

            switch (alt200) {
                case 1 :
                    // EsperEPL2Ast.g:566:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr4040); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4042);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4044);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:567:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr4056); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4058);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4060);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:568:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr4072); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4074);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4076);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:569:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr4087); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4089);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4091);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:570:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr4103); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4105);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4107);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:571:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr4118); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4120);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4122);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:572:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr4133); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4135);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4137);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:573:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr4148); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4150);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4152);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:574:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr4164); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4166);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4168);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:574:36: ( valueExpr )*
                    loop199:
                    do {
                        int alt199=2;
                        int LA199_0 = input.LA(1);

                        if ( ((LA199_0>=IN_SET && LA199_0<=REGEXP)||LA199_0==NOT_EXPR||(LA199_0>=SUM && LA199_0<=AVG)||(LA199_0>=COALESCE && LA199_0<=COUNT)||(LA199_0>=CASE && LA199_0<=CASE2)||(LA199_0>=PREVIOUS && LA199_0<=EXISTS)||(LA199_0>=INSTANCEOF && LA199_0<=CURRENT_TIMESTAMP)||(LA199_0>=EVAL_AND_EXPR && LA199_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA199_0==EVENT_PROP_EXPR||LA199_0==CONCAT||(LA199_0>=LIB_FUNC_CHAIN && LA199_0<=DOT_EXPR)||LA199_0==ARRAY_EXPR||(LA199_0>=NOT_IN_SET && LA199_0<=NOT_REGEXP)||(LA199_0>=IN_RANGE && LA199_0<=SUBSELECT_EXPR)||(LA199_0>=EXISTS_SUBSELECT_EXPR && LA199_0<=NOT_IN_SUBSELECT_EXPR)||LA199_0==SUBSTITUTION||(LA199_0>=FIRST_AGGREG && LA199_0<=WINDOW_AGGREG)||(LA199_0>=INT_TYPE && LA199_0<=NULL_TYPE)||(LA199_0>=STAR && LA199_0<=PLUS)||(LA199_0>=BAND && LA199_0<=BXOR)||(LA199_0>=LT && LA199_0<=GE)||(LA199_0>=MINUS && LA199_0<=MOD)) ) {
                            alt199=1;
                        }


                        switch (alt199) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:574:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr4171);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop199;
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
            d=(CommonTree)match(input,DOT_EXPR,FOLLOW_DOT_EXPR_in_dotExpr4191); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dotExpr4193);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:578:27: ( libFunctionWithClass )*
            loop201:
            do {
                int alt201=2;
                int LA201_0 = input.LA(1);

                if ( (LA201_0==LIB_FUNCTION) ) {
                    alt201=1;
                }


                switch (alt201) {
            	case 1 :
            	    // EsperEPL2Ast.g:578:27: libFunctionWithClass
            	    {
            	    pushFollow(FOLLOW_libFunctionWithClass_in_dotExpr4195);
            	    libFunctionWithClass();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop201;
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
            l=(CommonTree)match(input,LIB_FUNC_CHAIN,FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4215); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_libFunctionWithClass_in_libFuncChain4217);
            libFunctionWithClass();

            state._fsp--;

            // EsperEPL2Ast.g:582:46: ( libOrPropFunction )*
            loop202:
            do {
                int alt202=2;
                int LA202_0 = input.LA(1);

                if ( (LA202_0==EVENT_PROP_EXPR||LA202_0==LIB_FUNCTION) ) {
                    alt202=1;
                }


                switch (alt202) {
            	case 1 :
            	    // EsperEPL2Ast.g:582:46: libOrPropFunction
            	    {
            	    pushFollow(FOLLOW_libOrPropFunction_in_libFuncChain4219);
            	    libOrPropFunction();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop202;
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
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4239); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:586:23: ( CLASS_IDENT )?
            int alt203=2;
            int LA203_0 = input.LA(1);

            if ( (LA203_0==CLASS_IDENT) ) {
                alt203=1;
            }
            switch (alt203) {
                case 1 :
                    // EsperEPL2Ast.g:586:24: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunctionWithClass4242); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunctionWithClass4246); 
            // EsperEPL2Ast.g:586:44: ( DISTINCT )?
            int alt204=2;
            int LA204_0 = input.LA(1);

            if ( (LA204_0==DISTINCT) ) {
                alt204=1;
            }
            switch (alt204) {
                case 1 :
                    // EsperEPL2Ast.g:586:45: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunctionWithClass4249); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:586:56: ( valueExpr )*
            loop205:
            do {
                int alt205=2;
                int LA205_0 = input.LA(1);

                if ( ((LA205_0>=IN_SET && LA205_0<=REGEXP)||LA205_0==NOT_EXPR||(LA205_0>=SUM && LA205_0<=AVG)||(LA205_0>=COALESCE && LA205_0<=COUNT)||(LA205_0>=CASE && LA205_0<=CASE2)||(LA205_0>=PREVIOUS && LA205_0<=EXISTS)||(LA205_0>=INSTANCEOF && LA205_0<=CURRENT_TIMESTAMP)||(LA205_0>=EVAL_AND_EXPR && LA205_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA205_0==EVENT_PROP_EXPR||LA205_0==CONCAT||(LA205_0>=LIB_FUNC_CHAIN && LA205_0<=DOT_EXPR)||LA205_0==ARRAY_EXPR||(LA205_0>=NOT_IN_SET && LA205_0<=NOT_REGEXP)||(LA205_0>=IN_RANGE && LA205_0<=SUBSELECT_EXPR)||(LA205_0>=EXISTS_SUBSELECT_EXPR && LA205_0<=NOT_IN_SUBSELECT_EXPR)||LA205_0==SUBSTITUTION||(LA205_0>=FIRST_AGGREG && LA205_0<=WINDOW_AGGREG)||(LA205_0>=INT_TYPE && LA205_0<=NULL_TYPE)||(LA205_0>=STAR && LA205_0<=PLUS)||(LA205_0>=BAND && LA205_0<=BXOR)||(LA205_0>=LT && LA205_0<=GE)||(LA205_0>=MINUS && LA205_0<=MOD)) ) {
                    alt205=1;
                }


                switch (alt205) {
            	case 1 :
            	    // EsperEPL2Ast.g:586:57: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunctionWithClass4254);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop205;
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
            int alt206=2;
            int LA206_0 = input.LA(1);

            if ( (LA206_0==EVENT_PROP_EXPR) ) {
                alt206=1;
            }
            else if ( (LA206_0==LIB_FUNCTION) ) {
                alt206=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 206, 0, input);

                throw nvae;
            }
            switch (alt206) {
                case 1 :
                    // EsperEPL2Ast.g:590:7: eventPropertyExpr[false]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_libOrPropFunction4272);
                    eventPropertyExpr(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:591:7: libFunctionWithClass
                    {
                    pushFollow(FOLLOW_libFunctionWithClass_in_libOrPropFunction4282);
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
            loop207:
            do {
                int alt207=2;
                int LA207_0 = input.LA(1);

                if ( (LA207_0==ANNOTATION) ) {
                    alt207=1;
                }


                switch (alt207) {
            	case 1 :
            	    // EsperEPL2Ast.g:598:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule4297);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop207;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule4301);
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
            int alt212=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt212=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt212=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt212=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt212=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt212=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt212=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt212=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 212, 0, input);

                throw nvae;
            }

            switch (alt212) {
                case 1 :
                    // EsperEPL2Ast.g:602:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice4315);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:603:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice4320);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:604:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice4330); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4332);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:605:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4346); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice4348);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4350);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:606:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4364); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4366);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:607:5: ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice4380); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4382);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:607:32: ( IDENT IDENT ( valueExprWithTime )* | valueExpr )
                    int alt209=2;
                    int LA209_0 = input.LA(1);

                    if ( (LA209_0==IDENT) ) {
                        alt209=1;
                    }
                    else if ( ((LA209_0>=IN_SET && LA209_0<=REGEXP)||LA209_0==NOT_EXPR||(LA209_0>=SUM && LA209_0<=AVG)||(LA209_0>=COALESCE && LA209_0<=COUNT)||(LA209_0>=CASE && LA209_0<=CASE2)||(LA209_0>=PREVIOUS && LA209_0<=EXISTS)||(LA209_0>=INSTANCEOF && LA209_0<=CURRENT_TIMESTAMP)||(LA209_0>=EVAL_AND_EXPR && LA209_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA209_0==EVENT_PROP_EXPR||LA209_0==CONCAT||(LA209_0>=LIB_FUNC_CHAIN && LA209_0<=DOT_EXPR)||LA209_0==ARRAY_EXPR||(LA209_0>=NOT_IN_SET && LA209_0<=NOT_REGEXP)||(LA209_0>=IN_RANGE && LA209_0<=SUBSELECT_EXPR)||(LA209_0>=EXISTS_SUBSELECT_EXPR && LA209_0<=NOT_IN_SUBSELECT_EXPR)||LA209_0==SUBSTITUTION||(LA209_0>=FIRST_AGGREG && LA209_0<=WINDOW_AGGREG)||(LA209_0>=INT_TYPE && LA209_0<=NULL_TYPE)||(LA209_0>=STAR && LA209_0<=PLUS)||(LA209_0>=BAND && LA209_0<=BXOR)||(LA209_0>=LT && LA209_0<=GE)||(LA209_0>=MINUS && LA209_0<=MOD)) ) {
                        alt209=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 209, 0, input);

                        throw nvae;
                    }
                    switch (alt209) {
                        case 1 :
                            // EsperEPL2Ast.g:607:33: IDENT IDENT ( valueExprWithTime )*
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4385); 
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4387); 
                            // EsperEPL2Ast.g:607:45: ( valueExprWithTime )*
                            loop208:
                            do {
                                int alt208=2;
                                int LA208_0 = input.LA(1);

                                if ( ((LA208_0>=IN_SET && LA208_0<=REGEXP)||LA208_0==NOT_EXPR||(LA208_0>=SUM && LA208_0<=AVG)||(LA208_0>=COALESCE && LA208_0<=COUNT)||(LA208_0>=CASE && LA208_0<=CASE2)||LA208_0==LAST||(LA208_0>=PREVIOUS && LA208_0<=EXISTS)||(LA208_0>=LW && LA208_0<=CURRENT_TIMESTAMP)||(LA208_0>=NUMERIC_PARAM_RANGE && LA208_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA208_0>=EVAL_AND_EXPR && LA208_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA208_0==EVENT_PROP_EXPR||LA208_0==CONCAT||(LA208_0>=LIB_FUNC_CHAIN && LA208_0<=DOT_EXPR)||(LA208_0>=TIME_PERIOD && LA208_0<=ARRAY_EXPR)||(LA208_0>=NOT_IN_SET && LA208_0<=NOT_REGEXP)||(LA208_0>=IN_RANGE && LA208_0<=SUBSELECT_EXPR)||(LA208_0>=EXISTS_SUBSELECT_EXPR && LA208_0<=NOT_IN_SUBSELECT_EXPR)||(LA208_0>=LAST_OPERATOR && LA208_0<=SUBSTITUTION)||LA208_0==NUMBERSETSTAR||(LA208_0>=FIRST_AGGREG && LA208_0<=WINDOW_AGGREG)||(LA208_0>=INT_TYPE && LA208_0<=NULL_TYPE)||(LA208_0>=STAR && LA208_0<=PLUS)||(LA208_0>=BAND && LA208_0<=BXOR)||(LA208_0>=LT && LA208_0<=GE)||(LA208_0>=MINUS && LA208_0<=MOD)) ) {
                                    alt208=1;
                                }


                                switch (alt208) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:607:45: valueExprWithTime
                            	    {
                            	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice4389);
                            	    valueExprWithTime();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop208;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:607:66: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_exprChoice4394);
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
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4408); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:608:26: ( matchUntilRange )?
                    int alt210=2;
                    int LA210_0 = input.LA(1);

                    if ( ((LA210_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA210_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt210=1;
                    }
                    switch (alt210) {
                        case 1 :
                            // EsperEPL2Ast.g:608:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4410);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4413);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:608:54: ( exprChoice )?
                    int alt211=2;
                    int LA211_0 = input.LA(1);

                    if ( ((LA211_0>=OR_EXPR && LA211_0<=AND_EXPR)||(LA211_0>=EVERY_EXPR && LA211_0<=EVERY_DISTINCT_EXPR)||LA211_0==FOLLOWED_BY_EXPR||(LA211_0>=PATTERN_FILTER_EXPR && LA211_0<=PATTERN_NOT_EXPR)||(LA211_0>=GUARD_EXPR && LA211_0<=OBSERVER_EXPR)||LA211_0==MATCH_UNTIL_EXPR) ) {
                        alt211=1;
                    }
                    switch (alt211) {
                        case 1 :
                            // EsperEPL2Ast.g:608:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4415);
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
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4436); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:613:35: ( valueExpr )+
            int cnt213=0;
            loop213:
            do {
                int alt213=2;
                int LA213_0 = input.LA(1);

                if ( ((LA213_0>=IN_SET && LA213_0<=REGEXP)||LA213_0==NOT_EXPR||(LA213_0>=SUM && LA213_0<=AVG)||(LA213_0>=COALESCE && LA213_0<=COUNT)||(LA213_0>=CASE && LA213_0<=CASE2)||(LA213_0>=PREVIOUS && LA213_0<=EXISTS)||(LA213_0>=INSTANCEOF && LA213_0<=CURRENT_TIMESTAMP)||(LA213_0>=EVAL_AND_EXPR && LA213_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA213_0==EVENT_PROP_EXPR||LA213_0==CONCAT||(LA213_0>=LIB_FUNC_CHAIN && LA213_0<=DOT_EXPR)||LA213_0==ARRAY_EXPR||(LA213_0>=NOT_IN_SET && LA213_0<=NOT_REGEXP)||(LA213_0>=IN_RANGE && LA213_0<=SUBSELECT_EXPR)||(LA213_0>=EXISTS_SUBSELECT_EXPR && LA213_0<=NOT_IN_SUBSELECT_EXPR)||LA213_0==SUBSTITUTION||(LA213_0>=FIRST_AGGREG && LA213_0<=WINDOW_AGGREG)||(LA213_0>=INT_TYPE && LA213_0<=NULL_TYPE)||(LA213_0>=STAR && LA213_0<=PLUS)||(LA213_0>=BAND && LA213_0<=BXOR)||(LA213_0>=LT && LA213_0<=GE)||(LA213_0>=MINUS && LA213_0<=MOD)) ) {
                    alt213=1;
                }


                switch (alt213) {
            	case 1 :
            	    // EsperEPL2Ast.g:613:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions4438);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt213 >= 1 ) break loop213;
                        EarlyExitException eee =
                            new EarlyExitException(213, input);
                        throw eee;
                }
                cnt213++;
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
            int alt217=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt217=1;
                }
                break;
            case OR_EXPR:
                {
                alt217=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:617:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4457); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4459);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4461);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:617:48: ( exprChoice )*
                    loop214:
                    do {
                        int alt214=2;
                        int LA214_0 = input.LA(1);

                        if ( ((LA214_0>=OR_EXPR && LA214_0<=AND_EXPR)||(LA214_0>=EVERY_EXPR && LA214_0<=EVERY_DISTINCT_EXPR)||LA214_0==FOLLOWED_BY_EXPR||(LA214_0>=PATTERN_FILTER_EXPR && LA214_0<=PATTERN_NOT_EXPR)||(LA214_0>=GUARD_EXPR && LA214_0<=OBSERVER_EXPR)||LA214_0==MATCH_UNTIL_EXPR) ) {
                            alt214=1;
                        }


                        switch (alt214) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:617:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4464);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop214;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:618:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4480); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4482);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4484);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:618:40: ( exprChoice )*
                    loop215:
                    do {
                        int alt215=2;
                        int LA215_0 = input.LA(1);

                        if ( ((LA215_0>=OR_EXPR && LA215_0<=AND_EXPR)||(LA215_0>=EVERY_EXPR && LA215_0<=EVERY_DISTINCT_EXPR)||LA215_0==FOLLOWED_BY_EXPR||(LA215_0>=PATTERN_FILTER_EXPR && LA215_0<=PATTERN_NOT_EXPR)||(LA215_0>=GUARD_EXPR && LA215_0<=OBSERVER_EXPR)||LA215_0==MATCH_UNTIL_EXPR) ) {
                            alt215=1;
                        }


                        switch (alt215) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:618:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4487);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop215;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:619:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4503); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4505);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4507);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:619:41: ( exprChoice )*
                    loop216:
                    do {
                        int alt216=2;
                        int LA216_0 = input.LA(1);

                        if ( ((LA216_0>=OR_EXPR && LA216_0<=AND_EXPR)||(LA216_0>=EVERY_EXPR && LA216_0<=EVERY_DISTINCT_EXPR)||LA216_0==FOLLOWED_BY_EXPR||(LA216_0>=PATTERN_FILTER_EXPR && LA216_0<=PATTERN_NOT_EXPR)||(LA216_0>=GUARD_EXPR && LA216_0<=OBSERVER_EXPR)||LA216_0==MATCH_UNTIL_EXPR) ) {
                            alt216=1;
                        }


                        switch (alt216) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:619:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4510);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop216;
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
            int alt219=2;
            int LA219_0 = input.LA(1);

            if ( (LA219_0==PATTERN_FILTER_EXPR) ) {
                alt219=1;
            }
            else if ( (LA219_0==OBSERVER_EXPR) ) {
                alt219=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 219, 0, input);

                throw nvae;
            }
            switch (alt219) {
                case 1 :
                    // EsperEPL2Ast.g:623:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4529);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:624:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4541); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4543); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4545); 
                    // EsperEPL2Ast.g:624:39: ( valueExprWithTime )*
                    loop218:
                    do {
                        int alt218=2;
                        int LA218_0 = input.LA(1);

                        if ( ((LA218_0>=IN_SET && LA218_0<=REGEXP)||LA218_0==NOT_EXPR||(LA218_0>=SUM && LA218_0<=AVG)||(LA218_0>=COALESCE && LA218_0<=COUNT)||(LA218_0>=CASE && LA218_0<=CASE2)||LA218_0==LAST||(LA218_0>=PREVIOUS && LA218_0<=EXISTS)||(LA218_0>=LW && LA218_0<=CURRENT_TIMESTAMP)||(LA218_0>=NUMERIC_PARAM_RANGE && LA218_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA218_0>=EVAL_AND_EXPR && LA218_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA218_0==EVENT_PROP_EXPR||LA218_0==CONCAT||(LA218_0>=LIB_FUNC_CHAIN && LA218_0<=DOT_EXPR)||(LA218_0>=TIME_PERIOD && LA218_0<=ARRAY_EXPR)||(LA218_0>=NOT_IN_SET && LA218_0<=NOT_REGEXP)||(LA218_0>=IN_RANGE && LA218_0<=SUBSELECT_EXPR)||(LA218_0>=EXISTS_SUBSELECT_EXPR && LA218_0<=NOT_IN_SUBSELECT_EXPR)||(LA218_0>=LAST_OPERATOR && LA218_0<=SUBSTITUTION)||LA218_0==NUMBERSETSTAR||(LA218_0>=FIRST_AGGREG && LA218_0<=WINDOW_AGGREG)||(LA218_0>=INT_TYPE && LA218_0<=NULL_TYPE)||(LA218_0>=STAR && LA218_0<=PLUS)||(LA218_0>=BAND && LA218_0<=BXOR)||(LA218_0>=LT && LA218_0<=GE)||(LA218_0>=MINUS && LA218_0<=MOD)) ) {
                            alt218=1;
                        }


                        switch (alt218) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:624:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4547);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop218;
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
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4567); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:628:29: ( IDENT )?
            int alt220=2;
            int LA220_0 = input.LA(1);

            if ( (LA220_0==IDENT) ) {
                alt220=1;
            }
            switch (alt220) {
                case 1 :
                    // EsperEPL2Ast.g:628:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4569); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4572); 
            // EsperEPL2Ast.g:628:48: ( propertyExpression )?
            int alt221=2;
            int LA221_0 = input.LA(1);

            if ( (LA221_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt221=1;
            }
            switch (alt221) {
                case 1 :
                    // EsperEPL2Ast.g:628:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4574);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:628:68: ( valueExpr )*
            loop222:
            do {
                int alt222=2;
                int LA222_0 = input.LA(1);

                if ( ((LA222_0>=IN_SET && LA222_0<=REGEXP)||LA222_0==NOT_EXPR||(LA222_0>=SUM && LA222_0<=AVG)||(LA222_0>=COALESCE && LA222_0<=COUNT)||(LA222_0>=CASE && LA222_0<=CASE2)||(LA222_0>=PREVIOUS && LA222_0<=EXISTS)||(LA222_0>=INSTANCEOF && LA222_0<=CURRENT_TIMESTAMP)||(LA222_0>=EVAL_AND_EXPR && LA222_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA222_0==EVENT_PROP_EXPR||LA222_0==CONCAT||(LA222_0>=LIB_FUNC_CHAIN && LA222_0<=DOT_EXPR)||LA222_0==ARRAY_EXPR||(LA222_0>=NOT_IN_SET && LA222_0<=NOT_REGEXP)||(LA222_0>=IN_RANGE && LA222_0<=SUBSELECT_EXPR)||(LA222_0>=EXISTS_SUBSELECT_EXPR && LA222_0<=NOT_IN_SUBSELECT_EXPR)||LA222_0==SUBSTITUTION||(LA222_0>=FIRST_AGGREG && LA222_0<=WINDOW_AGGREG)||(LA222_0>=INT_TYPE && LA222_0<=NULL_TYPE)||(LA222_0>=STAR && LA222_0<=PLUS)||(LA222_0>=BAND && LA222_0<=BXOR)||(LA222_0>=LT && LA222_0<=GE)||(LA222_0>=MINUS && LA222_0<=MOD)) ) {
                    alt222=1;
                }


                switch (alt222) {
            	case 1 :
            	    // EsperEPL2Ast.g:628:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4578);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop222;
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
            int alt223=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt223=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt223=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt223=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt223=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 223, 0, input);

                throw nvae;
            }

            switch (alt223) {
                case 1 :
                    // EsperEPL2Ast.g:632:4: ^( MATCH_UNTIL_RANGE_CLOSED valueExpr valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4596); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4598);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4600);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:633:5: ^( MATCH_UNTIL_RANGE_BOUNDED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4608); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4610);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:634:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4618); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4620);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:635:4: ^( MATCH_UNTIL_RANGE_HALFOPEN valueExpr )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4627); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_matchUntilRange4629);
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
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4642); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4644);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:639:35: ( valueExpr )*
            loop224:
            do {
                int alt224=2;
                int LA224_0 = input.LA(1);

                if ( ((LA224_0>=IN_SET && LA224_0<=REGEXP)||LA224_0==NOT_EXPR||(LA224_0>=SUM && LA224_0<=AVG)||(LA224_0>=COALESCE && LA224_0<=COUNT)||(LA224_0>=CASE && LA224_0<=CASE2)||(LA224_0>=PREVIOUS && LA224_0<=EXISTS)||(LA224_0>=INSTANCEOF && LA224_0<=CURRENT_TIMESTAMP)||(LA224_0>=EVAL_AND_EXPR && LA224_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA224_0==EVENT_PROP_EXPR||LA224_0==CONCAT||(LA224_0>=LIB_FUNC_CHAIN && LA224_0<=DOT_EXPR)||LA224_0==ARRAY_EXPR||(LA224_0>=NOT_IN_SET && LA224_0<=NOT_REGEXP)||(LA224_0>=IN_RANGE && LA224_0<=SUBSELECT_EXPR)||(LA224_0>=EXISTS_SUBSELECT_EXPR && LA224_0<=NOT_IN_SUBSELECT_EXPR)||LA224_0==SUBSTITUTION||(LA224_0>=FIRST_AGGREG && LA224_0<=WINDOW_AGGREG)||(LA224_0>=INT_TYPE && LA224_0<=NULL_TYPE)||(LA224_0>=STAR && LA224_0<=PLUS)||(LA224_0>=BAND && LA224_0<=BXOR)||(LA224_0>=LT && LA224_0<=GE)||(LA224_0>=MINUS && LA224_0<=MOD)) ) {
                    alt224=1;
                }


                switch (alt224) {
            	case 1 :
            	    // EsperEPL2Ast.g:639:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4647);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop224;
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
            int alt237=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt237=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt237=2;
                }
                break;
            case LT:
                {
                alt237=3;
                }
                break;
            case LE:
                {
                alt237=4;
                }
                break;
            case GT:
                {
                alt237=5;
                }
                break;
            case GE:
                {
                alt237=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt237=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt237=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt237=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt237=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt237=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt237=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 237, 0, input);

                throw nvae;
            }

            switch (alt237) {
                case 1 :
                    // EsperEPL2Ast.g:643:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4663); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4665);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:644:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4672); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4674);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:645:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4681); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4683);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:646:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4690); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4692);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:647:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4699); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4701);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:648:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4708); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4710);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:649:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4717); 

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
                    int alt225=2;
                    int LA225_0 = input.LA(1);

                    if ( ((LA225_0>=INT_TYPE && LA225_0<=NULL_TYPE)) ) {
                        alt225=1;
                    }
                    else if ( (LA225_0==EVENT_FILTER_IDENT) ) {
                        alt225=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 225, 0, input);

                        throw nvae;
                    }
                    switch (alt225) {
                        case 1 :
                            // EsperEPL2Ast.g:649:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4726);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:649:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4729);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:649:76: ( constant[false] | filterIdentifier )
                    int alt226=2;
                    int LA226_0 = input.LA(1);

                    if ( ((LA226_0>=INT_TYPE && LA226_0<=NULL_TYPE)) ) {
                        alt226=1;
                    }
                    else if ( (LA226_0==EVENT_FILTER_IDENT) ) {
                        alt226=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 226, 0, input);

                        throw nvae;
                    }
                    switch (alt226) {
                        case 1 :
                            // EsperEPL2Ast.g:649:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4733);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:649:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4736);
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
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4750); 

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
                            // EsperEPL2Ast.g:650:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4759);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:650:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4762);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:650:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:650:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4766);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:650:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4769);
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
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4783); 

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
                            // EsperEPL2Ast.g:651:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4792);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:651:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4795);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:651:73: ( constant[false] | filterIdentifier )*
                    loop230:
                    do {
                        int alt230=3;
                        int LA230_0 = input.LA(1);

                        if ( ((LA230_0>=INT_TYPE && LA230_0<=NULL_TYPE)) ) {
                            alt230=1;
                        }
                        else if ( (LA230_0==EVENT_FILTER_IDENT) ) {
                            alt230=2;
                        }


                        switch (alt230) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:651:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4799);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:651:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4802);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop230;
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
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4817); 

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
                            // EsperEPL2Ast.g:652:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4826);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:652:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4829);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:652:77: ( constant[false] | filterIdentifier )*
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
                    	    // EsperEPL2Ast.g:652:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4833);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:652:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4836);
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
                case 11 :
                    // EsperEPL2Ast.g:653:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4851); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:653:27: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:653:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4854);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:653:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4857);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:653:62: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:653:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4861);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:653:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4864);
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
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4872); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:654:31: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:654:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4875);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:654:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4878);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:654:66: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:654:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4882);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:654:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4885);
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
                    // EsperEPL2Ast.g:658:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4899);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:659:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4905);
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
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4916); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4918); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4920);
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
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4939); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4941);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:666:44: ( eventPropertyAtomic )*
            loop239:
            do {
                int alt239=2;
                int LA239_0 = input.LA(1);

                if ( ((LA239_0>=EVENT_PROP_SIMPLE && LA239_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt239=1;
                }


                switch (alt239) {
            	case 1 :
            	    // EsperEPL2Ast.g:666:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4944);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop239;
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
            int alt240=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt240=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt240=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt240=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt240=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt240=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt240=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 240, 0, input);

                throw nvae;
            }

            switch (alt240) {
                case 1 :
                    // EsperEPL2Ast.g:670:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4963); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4965); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:671:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4972); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4974); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4976); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:672:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4983); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4985); 
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
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5000); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5002); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:674:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5009); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5011); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic5013); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:675:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5020); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic5022); 
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
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod5049); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod5051);
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
            int alt251=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt251=1;
                }
                break;
            case HOUR_PART:
                {
                alt251=2;
                }
                break;
            case MINUTE_PART:
                {
                alt251=3;
                }
                break;
            case SECOND_PART:
                {
                alt251=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt251=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 251, 0, input);

                throw nvae;
            }

            switch (alt251) {
                case 1 :
                    // EsperEPL2Ast.g:683:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef5067);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:683:13: ( hourPart )?
                    int alt241=2;
                    int LA241_0 = input.LA(1);

                    if ( (LA241_0==HOUR_PART) ) {
                        alt241=1;
                    }
                    switch (alt241) {
                        case 1 :
                            // EsperEPL2Ast.g:683:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef5070);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:683:25: ( minutePart )?
                    int alt242=2;
                    int LA242_0 = input.LA(1);

                    if ( (LA242_0==MINUTE_PART) ) {
                        alt242=1;
                    }
                    switch (alt242) {
                        case 1 :
                            // EsperEPL2Ast.g:683:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5075);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:683:39: ( secondPart )?
                    int alt243=2;
                    int LA243_0 = input.LA(1);

                    if ( (LA243_0==SECOND_PART) ) {
                        alt243=1;
                    }
                    switch (alt243) {
                        case 1 :
                            // EsperEPL2Ast.g:683:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5080);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:683:53: ( millisecondPart )?
                    int alt244=2;
                    int LA244_0 = input.LA(1);

                    if ( (LA244_0==MILLISECOND_PART) ) {
                        alt244=1;
                    }
                    switch (alt244) {
                        case 1 :
                            // EsperEPL2Ast.g:683:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5085);
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
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef5092);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:684:13: ( minutePart )?
                    int alt245=2;
                    int LA245_0 = input.LA(1);

                    if ( (LA245_0==MINUTE_PART) ) {
                        alt245=1;
                    }
                    switch (alt245) {
                        case 1 :
                            // EsperEPL2Ast.g:684:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef5095);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:684:27: ( secondPart )?
                    int alt246=2;
                    int LA246_0 = input.LA(1);

                    if ( (LA246_0==SECOND_PART) ) {
                        alt246=1;
                    }
                    switch (alt246) {
                        case 1 :
                            // EsperEPL2Ast.g:684:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5100);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:684:41: ( millisecondPart )?
                    int alt247=2;
                    int LA247_0 = input.LA(1);

                    if ( (LA247_0==MILLISECOND_PART) ) {
                        alt247=1;
                    }
                    switch (alt247) {
                        case 1 :
                            // EsperEPL2Ast.g:684:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5105);
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
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef5112);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:685:15: ( secondPart )?
                    int alt248=2;
                    int LA248_0 = input.LA(1);

                    if ( (LA248_0==SECOND_PART) ) {
                        alt248=1;
                    }
                    switch (alt248) {
                        case 1 :
                            // EsperEPL2Ast.g:685:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef5115);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:685:29: ( millisecondPart )?
                    int alt249=2;
                    int LA249_0 = input.LA(1);

                    if ( (LA249_0==MILLISECOND_PART) ) {
                        alt249=1;
                    }
                    switch (alt249) {
                        case 1 :
                            // EsperEPL2Ast.g:685:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5120);
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
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef5127);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:686:15: ( millisecondPart )?
                    int alt250=2;
                    int LA250_0 = input.LA(1);

                    if ( (LA250_0==MILLISECOND_PART) ) {
                        alt250=1;
                    }
                    switch (alt250) {
                        case 1 :
                            // EsperEPL2Ast.g:686:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5130);
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
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef5137);
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
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart5151); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart5153);
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
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart5168); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart5170);
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
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart5185); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart5187);
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
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart5202); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart5204);
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
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart5219); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart5221);
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
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution5236); 
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
            int alt252=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt252=1;
                }
                break;
            case LONG_TYPE:
                {
                alt252=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt252=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt252=4;
                }
                break;
            case STRING_TYPE:
                {
                alt252=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt252=6;
                }
                break;
            case NULL_TYPE:
                {
                alt252=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 252, 0, input);

                throw nvae;
            }

            switch (alt252) {
                case 1 :
                    // EsperEPL2Ast.g:715:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant5252); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:716:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant5261); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:717:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant5270); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:718:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant5279); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:719:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant5295); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:720:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant5311); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:721:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant5324); 
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
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr355 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_onMergeExpr357 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_mergeMatched_in_onMergeExpr360 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_mergeUnmatched_in_onMergeExpr363 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_onMergeExpr366 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MERGE_UPD_in_mergeMatched382 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_mergeMatched384 = new BitSet(new long[]{0x0000000000000008L,0x0000010000010000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_UPDATE_in_mergeMatched387 = new BitSet(new long[]{0x0000000000000008L,0x0000000000010000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_DELETE_in_mergeMatched390 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_mergeMatched393 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_MERGE_INS_in_mergeUnmatched411 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprCol_in_mergeUnmatched413 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_mergeUnmatched416 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr434 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr436 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr438 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr441 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C20000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr444 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr461 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr463 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr466 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr486 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr488 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr491 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr494 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000003000060000L,0x0C00000400000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr496 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000003000060000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr499 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000003000040000L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr503 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000002000040000L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr506 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr509 = new BitSet(new long[]{0x0000000000000008L,0x0000004000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr512 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr532 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr534 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr536 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr538 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput555 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput557 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr575 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr577 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C20000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr580 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C20000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr584 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr603 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000020000L,0x0C20000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr606 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment621 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_onSetAssignment623 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment626 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom640 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom642 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom645 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr663 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr665 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr668 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000004800L,0x0000080002000000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr689 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr692 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr721 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr732 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr752 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr754 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr756 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_exprCol_in_createIndexExpr758 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert775 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList794 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList797 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList816 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList818 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList821 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement836 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement838 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement840 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_LBRACK_in_createColTypeListElement842 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement867 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement887 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement891 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement913 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement916 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr952 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createVariableExpr954 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr956 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr959 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr979 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr981 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000004800L,0x0380080002000000L});
    public static final BitSet FOLLOW_variantList_in_createSchemaExpr984 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0180000000000000L});
    public static final BitSet FOLLOW_createColTypeList_in_createSchemaExpr986 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0180000000000000L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr997 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr999 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr1010 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr1012 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_exprCol_in_createSchemaExpr1014 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIANT_LIST_in_variantList1035 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_variantList1037 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr1055 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0010000008000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr1061 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr1066 = new BitSet(new long[]{0x0000000000000002L,0x0000024000000000L,0x000BC03000060000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr1071 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC03000060000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr1078 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC03000040000L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr1086 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC02000040000L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr1093 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x000BC02000000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr1100 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr1107 = new BitSet(new long[]{0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr1114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1131 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr1133 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr1142 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_exprCol_in_insertIntoExpr1145 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRCOL_in_exprCol1164 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1166 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1169 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause1187 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause1189 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1202 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1205 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1219 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1222 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000F40000000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1225 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000F40000000L});
    public static final BitSet FOLLOW_FOR_in_forExpr1245 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_forExpr1247 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_forExpr1249 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1277 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000420L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1283 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000420L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1289 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000420L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1301 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001800L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1307 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1325 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1327 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1344 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1346 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1348 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1350 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1352 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1358 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1373 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1375 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1377 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1393 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1395 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1414 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1416 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1436 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1438 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1461 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1463 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1465 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1483 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1485 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000240L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1500 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1505 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1520 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1522 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000001A000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1524 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1553 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1555 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000001A000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1559 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1571 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1593 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1595 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1612 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1614 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1616 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1633 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1636 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1652 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1662 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1664 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1667 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1681 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1683 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1686 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1705 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1719 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1721 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1724 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1728 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1731 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1746 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1748 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1751 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1755 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1758 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1773 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1775 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1778 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1782 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1785 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1800 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1802 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1805 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1809 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1812 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1833 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1836 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1840 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1844 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1848 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1852 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1857 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1862 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_streamExpression1866 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1890 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1892 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1895 = new BitSet(new long[]{0x0000000037CC23C8L,0x400000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1897 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1901 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1923 = new BitSet(new long[]{0x0000000000000008L,0x8000000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1942 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1944 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000007L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1947 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1950 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1954 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1956 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1976 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1986 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1988 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1991 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement2005 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2007 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement2010 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression2031 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression2033 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression2050 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression2052 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2054 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression2062 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression2083 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression2085 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression2087 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression2090 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2104 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2107 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr2124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2128 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr2131 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause2153 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause2155 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause2173 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2175 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2178 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause2196 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2198 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2201 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2223 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2248 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2250 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2282 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2284 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2287 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2306 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2317 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2319 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2335 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2337 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2348 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2350 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2368 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2379 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2381 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2384 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2397 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2399 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2414 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2416 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0xF000000000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2419 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2435 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2438 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2440 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2444 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2446 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000020000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2450 = new BitSet(new long[]{0x0000000000000008L,0x0000008000000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2453 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2473 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2475 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2477 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2479 = new BitSet(new long[]{0x0020000037CC23C0L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2481 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2483 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2500 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2502 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2517 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2530 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2532 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2544 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2546 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2568 = new BitSet(new long[]{0x0003800037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2593 = new BitSet(new long[]{0x0000000037CC23C2L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023F83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2602 = new BitSet(new long[]{0x0000000037CC23C2L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2633 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2635 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2637 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2640 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2656 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2658 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2661 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2675 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2677 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2679 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2691 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2693 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2695 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2707 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2709 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2711 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023F83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2720 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2725 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2738 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2740 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2742 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023F83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2751 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2756 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2769 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2771 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFuncChain_in_valueExpr2836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2846 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2852 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2867 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2872 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2885 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_dotExpr_in_valueExpr2890 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2919 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2927 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2929 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2931 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2944 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2955 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2960 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2970 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2972 = new BitSet(new long[]{0x0000000000000008L,0x0050000000000000L,0x0000000000000000L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2983 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2990 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList3003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList3010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList3016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator3032 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3035 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0xF000000000200000L,0x0000000000000007L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3038 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0xF000000000200000L,0x0000000000000007L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3041 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L,0xF000000000200000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_rangeOperator3045 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator3048 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator3051 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator3072 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator3075 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator3078 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator3081 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator3100 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator3103 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator3106 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator3109 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3128 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator3131 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator3134 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator3137 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr3160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3179 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr3181 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3200 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr3202 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3223 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3239 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3241 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3262 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000030000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_selectionList_in_subQueryExpr3281 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3283 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000020000L,0x0C00000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3286 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3306 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000004000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3309 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3314 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3318 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3321 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3341 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3344 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3357 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3360 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3380 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3382 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3384 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3390 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3393 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_set_in_inExpr3397 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3414 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3416 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3422 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3425 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30F100007L});
    public static final BitSet FOLLOW_set_in_inExpr3429 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3448 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3454 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3456 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_inExpr3458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3473 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3475 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000880000L});
    public static final BitSet FOLLOW_set_in_inExpr3477 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3483 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3485 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_inExpr3487 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3510 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3512 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3514 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3516 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3527 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3529 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3531 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3534 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3554 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3556 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3558 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3561 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3576 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3578 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3581 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3600 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3602 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3604 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3615 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3617 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3619 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3638 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3641 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3645 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3659 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3663 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3674 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3678 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3682 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3696 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3699 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3703 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3714 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3717 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3732 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3735 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3739 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3750 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3753 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00006L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3757 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3759 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3771 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3774 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00006L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3778 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3780 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WINDOW_AGGREG_in_builtinFunc3792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3795 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00006L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_accessValueExpr_in_builtinFunc3799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3811 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3813 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3815 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3818 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3833 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3835 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3837 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSTAIL_in_builtinFunc3850 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3852 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3854 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSCOUNT_in_builtinFunc3867 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3869 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PREVIOUSWINDOW_in_builtinFunc3881 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3883 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3895 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3899 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3901 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3914 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3916 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3918 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3921 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3935 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3939 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3951 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3953 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3965 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_accessValueExpr3982 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_accessValueExpr3989 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExpr3991 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_accessValueExpr3993 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_accessValueExpr3999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr4016 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr4019 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr4040 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4042 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4044 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr4056 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4058 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4060 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr4072 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4074 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4076 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr4087 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4089 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr4103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4105 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4107 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr4118 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4120 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4122 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr4133 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4135 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4137 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr4148 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4150 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4152 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr4164 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4166 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4168 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr4171 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_DOT_EXPR_in_dotExpr4191 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dotExpr4193 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_dotExpr4195 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_LIB_FUNC_CHAIN_in_libFuncChain4215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libFuncChain4217 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080008000000000L});
    public static final BitSet FOLLOW_libOrPropFunction_in_libFuncChain4219 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0080008000000000L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunctionWithClass4239 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunctionWithClass4242 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_libFunctionWithClass4246 = new BitSet(new long[]{0x0000400037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunctionWithClass4249 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_libFunctionWithClass4254 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_libOrPropFunction4272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunctionWithClass_in_libOrPropFunction4282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule4297 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000404000000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule4301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice4315 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice4320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice4330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4332 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4346 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice4348 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4350 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4364 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4366 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice4380 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4382 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E040007L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4385 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4387 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice4389 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_exprChoice4394 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4408 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4410 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4413 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4415 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4436 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions4438 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4457 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4459 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4461 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4464 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4480 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4482 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4484 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4487 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4503 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4505 = new BitSet(new long[]{0x000000000000D800L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4507 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4510 = new BitSet(new long[]{0x000000000000D808L,0x0D00000000000000L,0x0000000000003000L,0x0000004000000000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4529 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4541 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4543 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4545 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4547 = new BitSet(new long[]{0x0020000037CC23C8L,0x00F000000000F7E0L,0x1B40008003F00000L,0xF00E2000003BB83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4567 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4569 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4572 = new BitSet(new long[]{0x0000000037CC23C8L,0x400000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4574 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4578 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4596 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4598 = new BitSet(new long[]{0x0000000037CC23C0L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4600 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4608 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4618 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4620 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4627 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchUntilRange4629 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4642 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4644 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4647 = new BitSet(new long[]{0x0000000037CC23C8L,0x000000000000E7E0L,0x1340008003F00000L,0xF00E00000023B83CL,0x00000EF30E000007L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4663 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4665 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4672 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4674 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4681 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4683 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4692 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4699 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4701 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4708 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4710 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4717 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4719 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4726 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4729 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4733 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4736 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4739 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4750 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4752 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4759 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4762 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4769 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000001100000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4772 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4783 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4785 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4792 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4795 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4799 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4802 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4806 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4817 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4819 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4826 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4833 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4836 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000001100007L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4840 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4851 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4854 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4861 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4864 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4875 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4878 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0xF000000000000000L,0x0000000000000007L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4882 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4885 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4899 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4905 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4916 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4918 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4920 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4939 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4941 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00003F0000000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4944 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00003F0000000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4963 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4965 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4972 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4974 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4976 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4983 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4985 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4987 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic5000 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5002 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic5009 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5011 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic5013 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic5020 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic5022 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000C0000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic5024 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod5049 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod5051 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef5067 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0xC000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5070 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5075 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5080 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef5092 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x8000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5095 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5100 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef5112 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000003L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5115 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef5127 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef5137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart5151 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart5153 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart5168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart5170 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart5185 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart5187 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart5202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart5204 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart5219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart5221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution5236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant5252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant5261 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant5270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant5279 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant5295 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant5311 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant5324 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}