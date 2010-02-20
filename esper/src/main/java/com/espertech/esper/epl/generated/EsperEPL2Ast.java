// $ANTLR 3.1.1 EsperEPL2Ast.g 2010-02-03 09:30:39

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_STREAM", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_UPDATE_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "BOR", "PLUS", "QUESTION", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=167;
    public static final int FLOAT_SUFFIX=317;
    public static final int STAR=258;
    public static final int NUMERIC_PARAM_LIST=107;
    public static final int ISTREAM=60;
    public static final int MOD=278;
    public static final int OUTERJOIN_EXPR=149;
    public static final int BSR=299;
    public static final int LIB_FUNCTION=173;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=93;
    public static final int FULL_OUTERJOIN_EXPR=153;
    public static final int MATCHREC_PATTERN_CONCAT=242;
    public static final int RPAREN=257;
    public static final int LNOT=288;
    public static final int INC=292;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=265;
    public static final int BSR_ASSIGN=300;
    public static final int CAST_EXPR=202;
    public static final int MATCHES=104;
    public static final int STREAM_EXPR=148;
    public static final int TIMEPERIOD_SECONDS=90;
    public static final int NOT_EQUAL=270;
    public static final int METADATASQL=67;
    public static final int EVENT_FILTER_PROPERTY_EXPR=116;
    public static final int LAST_AGGREG=228;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=110;
    public static final int FOLLOWED_BY=282;
    public static final int HOUR_PART=178;
    public static final int RBRACK=263;
    public static final int MATCHREC_PATTERN_NESTED=244;
    public static final int MATCH_UNTIL_RANGE_CLOSED=219;
    public static final int GE=274;
    public static final int METHOD_JOIN_EXPR=215;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=115;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=114;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=293;
    public static final int EVENT_FILTER_NOT_IN=126;
    public static final int INSERTINTO_STREAM_NAME=190;
    public static final int NUM_DOUBLE=238;
    public static final int UNARY_MINUS=174;
    public static final int TIMEPERIOD_MILLISEC=91;
    public static final int LCURLY=279;
    public static final int RETAINUNION=63;
    public static final int DBWHERE_CLAUSE=188;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=124;
    public static final int GROUP=44;
    public static final int EMAILAT=308;
    public static final int WS=309;
    public static final int SUBSELECT_GROUP_EXPR=194;
    public static final int ON_SELECT_INSERT_EXPR=210;
    public static final int ESCAPECHAR=283;
    public static final int SL_COMMENT=310;
    public static final int NULL_TYPE=237;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=217;
    public static final int GT=272;
    public static final int BNOT=289;
    public static final int WHERE_EXPR=135;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=150;
    public static final int LAND=306;
    public static final int NOT_REGEXP=185;
    public static final int MATCH_UNTIL_EXPR=216;
    public static final int EVENT_PROP_EXPR=157;
    public static final int LBRACK=262;
    public static final int VIEW_EXPR=132;
    public static final int ANNOTATION=224;
    public static final int LONG_TYPE=232;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=117;
    public static final int MATCHREC_PATTERN=240;
    public static final int TIMEPERIOD_SEC=88;
    public static final int ON_SELECT_EXPR=208;
    public static final int TICKED_STRING_LITERAL=284;
    public static final int MINUTE_PART=179;
    public static final int PATTERN_NOT_EXPR=113;
    public static final int SUM=18;
    public static final int SQL_NE=269;
    public static final int HexDigit=315;
    public static final int UPDATE_EXPR=229;
    public static final int LPAREN=256;
    public static final int IN_SUBSELECT_EXPR=196;
    public static final int AT=81;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=94;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=246;
    public static final int NOT_IN_RANGE=192;
    public static final int OFFSET=98;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=68;
    public static final int SECOND_PART=180;
    public static final int MATCH_RECOGNIZE=100;
    public static final int IDENT=253;
    public static final int DATABASE_JOIN_EXPR=134;
    public static final int BXOR=268;
    public static final int PLUS=260;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=82;
    public static final int EXISTS=70;
    public static final int EVENT_PROP_INDEXED=160;
    public static final int TIMEPERIOD_MILLISECOND=92;
    public static final int EVAL_NOTEQUALS_EXPR=141;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=218;
    public static final int CREATE_VARIABLE_EXPR=214;
    public static final int CREATE_WINDOW_COL_TYPE=222;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=247;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=111;
    public static final int RIGHT_OUTERJOIN_EXPR=152;
    public static final int NUMBERSETSTAR=223;
    public static final int LAST_OPERATOR=199;
    public static final int PATTERN_FILTER_EXPR=112;
    public static final int EVAL_AND_EXPR=138;
    public static final int LEFT_OUTERJOIN_EXPR=151;
    public static final int EPL_EXPR=239;
    public static final int GROUP_BY_EXPR=154;
    public static final int SET=78;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=73;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=158;
    public static final int MINUS=276;
    public static final int SEMI=307;
    public static final int STAR_ASSIGN=295;
    public static final int FIRST_AGGREG=227;
    public static final int COLON=264;
    public static final int EVAL_EQUALS_GROUP_EXPR=142;
    public static final int BAND_ASSIGN=305;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=168;
    public static final int VALUE_NULL=96;
    public static final int NOT_IN_SET=182;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=161;
    public static final int SL=301;
    public static final int NOT_IN_SUBSELECT_EXPR=197;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=130;
    public static final int SR=297;
    public static final int RCURLY=280;
    public static final int PLUS_ASSIGN=291;
    public static final int EXISTS_SUBSELECT_EXPR=195;
    public static final int DAY_PART=177;
    public static final int EVENT_FILTER_IN=125;
    public static final int DIV=277;
    public static final int OBJECT_PARAM_ORDERED_EXPR=109;
    public static final int OctalEscape=314;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=181;
    public static final int PRIOR=69;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=97;
    public static final int SELECTION_EXPR=145;
    public static final int LOR=275;
    public static final int CAST=74;
    public static final int LW=72;
    public static final int WILDCARD_SELECT=189;
    public static final int EXPONENT=316;
    public static final int LT=271;
    public static final int PATTERN_INCL_EXPR=133;
    public static final int ORDER_BY_EXPR=155;
    public static final int BOOL_TYPE=236;
    public static final int MOD_ASSIGN=296;
    public static final int ANNOTATION_ARRAY=225;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=198;
    public static final int EQUALS=254;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=64;
    public static final int DIV_ASSIGN=290;
    public static final int SL_ASSIGN=302;
    public static final int PATTERN=65;
    public static final int SQL=66;
    public static final int FULL=40;
    public static final int WEEKDAY=71;
    public static final int MATCHREC_AFTER_SKIP=245;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=209;
    public static final int ARRAY_EXPR=176;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=95;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=143;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=127;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=89;
    public static final int FLOAT_TYPE=233;
    public static final int SUBSELECT_EXPR=193;
    public static final int ANNOTATION_VALUE=226;
    public static final int CONCAT=172;
    public static final int NUMERIC_PARAM_RANGE=106;
    public static final int CLASS_IDENT=129;
    public static final int MATCHREC_PATTERN_ALTER=243;
    public static final int ON_EXPR=205;
    public static final int CREATE_WINDOW_EXPR=203;
    public static final int PROPERTY_SELECTION_STREAM=119;
    public static final int ON_DELETE_EXPR=207;
    public static final int ON=41;
    public static final int NUM_LONG=285;
    public static final int TIME_PERIOD=175;
    public static final int DOUBLE_TYPE=234;
    public static final int DELETE=76;
    public static final int INT_TYPE=231;
    public static final int MATCHREC_PARTITION=251;
    public static final int EVAL_BITWISE_EXPR=137;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=156;
    public static final int TIMEPERIOD_HOURS=85;
    public static final int VARIABLE=79;
    public static final int SUBSTITUTION=201;
    public static final int UNTIL=80;
    public static final int STRING_TYPE=235;
    public static final int ON_SET_EXPR=213;
    public static final int MATCHREC_DEFINE_ITEM=248;
    public static final int NUM_INT=281;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=212;
    public static final int NUM_FLOAT=286;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=118;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=312;
    public static final int WEEKDAY_OPERATOR=200;
    public static final int WHERE=16;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=221;
    public static final int DEC=294;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=108;
    public static final int BXOR_ASSIGN=303;
    public static final int AFTER_LIMIT_EXPR=166;
    public static final int ORDER=56;
    public static final int SNAPSHOT=77;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=163;
    public static final int EVENT_FILTER_PARAM=122;
    public static final int IRSTREAM=61;
    public static final int UPDATE=99;
    public static final int MAX=20;
    public static final int ON_STREAM=206;
    public static final int DEFINE=102;
    public static final int TIMEPERIOD_DAYS=83;
    public static final int EVENT_FILTER_RANGE=123;
    public static final int ML_COMMENT=311;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=162;
    public static final int BOR_ASSIGN=304;
    public static final int COMMA=252;
    public static final int WHEN_LIMIT_EXPR=169;
    public static final int PARTITION=103;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=165;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=84;
    public static final int MATCHREC_MEASURE_ITEM=250;
    public static final int BOR=259;
    public static final int EQUAL=287;
    public static final int EVENT_FILTER_NOT_BETWEEN=128;
    public static final int IN_RANGE=191;
    public static final int DOT=255;
    public static final int CURRENT_TIMESTAMP=75;
    public static final int MATCHREC_MEASURES=249;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=120;
    public static final int INSERTINTO_EXPR=170;
    public static final int HAVING_EXPR=136;
    public static final int UNIDIRECTIONAL=62;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=220;
    public static final int EVAL_EQUALS_EXPR=140;
    public static final int TIMEPERIOD_MINUTES=87;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=184;
    public static final int EVENT_LIMIT_EXPR=164;
    public static final int NOT_BETWEEN=183;
    public static final int TIMEPERIOD_MINUTE=86;
    public static final int EVAL_OR_EXPR=139;
    public static final int ON_SELECT_INSERT_OUTPUT=211;
    public static final int AFTER=105;
    public static final int MEASURES=101;
    public static final int MATCHREC_PATTERN_ATOM=241;
    public static final int BAND=267;
    public static final int QUOTED_STRING_LITERAL=266;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=261;
    public static final int OBSERVER_EXPR=131;
    public static final int EVENT_FILTER_IDENT=121;
    public static final int EVENT_PROP_MAPPED=159;
    public static final int UnicodeEscape=313;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=186;
    public static final int SELECTION_ELEMENT_EXPR=146;
    public static final int CREATE_WINDOW_SELECT_EXPR=204;
    public static final int INSERTINTO_EXPRCOL=171;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=230;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=147;
    public static final int SR_ASSIGN=298;
    public static final int DBFROM_CLAUSE=187;
    public static final int LE=273;
    public static final int EVAL_IDENT=144;

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
    // EsperEPL2Ast.g:79:1: eplExpressionRule : ( selectExpr | createWindowExpr | createVariableExpr | onExpr | updateExpr ) ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:80:2: ( ( selectExpr | createWindowExpr | createVariableExpr | onExpr | updateExpr ) )
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr | updateExpr )
            {
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr | updateExpr )
            int alt6=5;
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
            case CREATE_VARIABLE_EXPR:
                {
                alt6=3;
                }
                break;
            case ON_EXPR:
                {
                alt6=4;
                }
                break;
            case UPDATE_EXPR:
                {
                alt6=5;
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
                    // EsperEPL2Ast.g:80:37: createVariableExpr
                    {
                    pushFollow(FOLLOW_createVariableExpr_in_eplExpressionRule233);
                    createVariableExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:80:58: onExpr
                    {
                    pushFollow(FOLLOW_onExpr_in_eplExpressionRule237);
                    onExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:80:67: updateExpr
                    {
                    pushFollow(FOLLOW_updateExpr_in_eplExpressionRule241);
                    updateExpr();

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
    // EsperEPL2Ast.g:83:1: onExpr : ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:84:2: ( ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ) )
            // EsperEPL2Ast.g:84:4: ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr260); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onStreamExpr_in_onExpr262);
            onStreamExpr();

            state._fsp--;

            // EsperEPL2Ast.g:85:3: ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr )
            int alt10=4;
            switch ( input.LA(1) ) {
            case ON_DELETE_EXPR:
                {
                alt10=1;
                }
                break;
            case ON_UPDATE_EXPR:
                {
                alt10=2;
                }
                break;
            case ON_SELECT_EXPR:
                {
                alt10=3;
                }
                break;
            case ON_SET_EXPR:
                {
                alt10=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // EsperEPL2Ast.g:85:4: onDeleteExpr
                    {
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr267);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:85:19: onUpdateExpr
                    {
                    pushFollow(FOLLOW_onUpdateExpr_in_onExpr271);
                    onUpdateExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:85:34: onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr275);
                    onSelectExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:85:47: ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    int alt9=2;
                    int LA9_0 = input.LA(1);

                    if ( (LA9_0==ON_SELECT_INSERT_EXPR) ) {
                        alt9=1;
                    }
                    switch (alt9) {
                        case 1 :
                            // EsperEPL2Ast.g:85:48: ( onSelectInsertExpr )+ ( onSelectInsertOutput )?
                            {
                            // EsperEPL2Ast.g:85:48: ( onSelectInsertExpr )+
                            int cnt7=0;
                            loop7:
                            do {
                                int alt7=2;
                                int LA7_0 = input.LA(1);

                                if ( (LA7_0==ON_SELECT_INSERT_EXPR) ) {
                                    alt7=1;
                                }


                                switch (alt7) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:85:48: onSelectInsertExpr
                            	    {
                            	    pushFollow(FOLLOW_onSelectInsertExpr_in_onExpr278);
                            	    onSelectInsertExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt7 >= 1 ) break loop7;
                                        EarlyExitException eee =
                                            new EarlyExitException(7, input);
                                        throw eee;
                                }
                                cnt7++;
                            } while (true);

                            // EsperEPL2Ast.g:85:68: ( onSelectInsertOutput )?
                            int alt8=2;
                            int LA8_0 = input.LA(1);

                            if ( (LA8_0==ON_SELECT_INSERT_OUTPUT) ) {
                                alt8=1;
                            }
                            switch (alt8) {
                                case 1 :
                                    // EsperEPL2Ast.g:85:68: onSelectInsertOutput
                                    {
                                    pushFollow(FOLLOW_onSelectInsertOutput_in_onExpr281);
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
                    pushFollow(FOLLOW_onSetExpr_in_onExpr288);
                    onSetExpr();

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
            s=(CommonTree)match(input,ON_STREAM,FOLLOW_ON_STREAM_in_onStreamExpr310); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:90:18: ( eventFilterExpr | patternInclusionExpression )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==EVENT_FILTER_EXPR) ) {
                alt11=1;
            }
            else if ( (LA11_0==PATTERN_INCL_EXPR) ) {
                alt11=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // EsperEPL2Ast.g:90:19: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_onStreamExpr313);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:90:37: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onStreamExpr317);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:90:65: ( IDENT )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==IDENT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // EsperEPL2Ast.g:90:65: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onStreamExpr320); 

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


    // $ANTLR start "updateExpr"
    // EsperEPL2Ast.g:93:1: updateExpr : ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void updateExpr() throws RecognitionException {
        CommonTree u=null;

        try {
            // EsperEPL2Ast.g:94:2: ( ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:94:4: ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? )
            {
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr339); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr341); 
            // EsperEPL2Ast.g:94:32: ( IDENT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IDENT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:94:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr343); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:94:39: ( onSetAssignment )+
            int cnt14=0;
            loop14:
            do {
                int alt14=2;
                int LA14_0 = input.LA(1);

                if ( (LA14_0==ON_SET_EXPR_ITEM) ) {
                    alt14=1;
                }


                switch (alt14) {
            	case 1 :
            	    // EsperEPL2Ast.g:94:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr346);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt14 >= 1 ) break loop14;
                        EarlyExitException eee =
                            new EarlyExitException(14, input);
                        throw eee;
                }
                cnt14++;
            } while (true);

            // EsperEPL2Ast.g:94:56: ( whereClause[false] )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==WHERE_EXPR) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // EsperEPL2Ast.g:94:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr349);
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
    // EsperEPL2Ast.g:97:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:98:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:98:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr366); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr368);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:98:32: ( whereClause[true] )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE_EXPR) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:98:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr371);
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
    // EsperEPL2Ast.g:101:1: onSelectExpr : ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:102:2: ( ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? ) )
            // EsperEPL2Ast.g:102:4: ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ( rowLimitClause )? )
            {
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr391); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:102:23: ( insertIntoExpr )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==INSERTINTO_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:102:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr393);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:39: ( DISTINCT )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==DISTINCT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:102:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr396); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr399);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:102:63: ( onExprFrom )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ON_EXPR_FROM) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:102:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr401);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:75: ( whereClause[true] )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==WHERE_EXPR) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // EsperEPL2Ast.g:102:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr404);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:94: ( groupByClause )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==GROUP_BY_EXPR) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:102:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr408);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:109: ( havingClause )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==HAVING_EXPR) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:102:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr411);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:123: ( orderByClause )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ORDER_BY_EXPR) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:102:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr414);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:138: ( rowLimitClause )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==ROW_LIMIT_EXPR) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:102:138: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr417);
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
    // EsperEPL2Ast.g:105:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:106:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:106:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr437); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr439);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr441);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:106:78: ( whereClause[true] )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==WHERE_EXPR) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:106:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr443);
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
    // EsperEPL2Ast.g:109:1: onSelectInsertOutput : ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) ;
    public final void onSelectInsertOutput() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:110:2: ( ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) )
            // EsperEPL2Ast.g:110:4: ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) )
            {
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput460); 

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
    // EsperEPL2Ast.g:113:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:114:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:114:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr480); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr482);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:114:34: ( onSetAssignment )*
            loop26:
            do {
                int alt26=2;
                int LA26_0 = input.LA(1);

                if ( (LA26_0==ON_SET_EXPR_ITEM) ) {
                    alt26=1;
                }


                switch (alt26) {
            	case 1 :
            	    // EsperEPL2Ast.g:114:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr485);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop26;
                }
            } while (true);

            // EsperEPL2Ast.g:114:53: ( whereClause[false] )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==WHERE_EXPR) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:114:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr489);
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
    // EsperEPL2Ast.g:117:1: onUpdateExpr : ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void onUpdateExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:118:2: ( ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:118:4: ^( ON_UPDATE_EXPR onExprFrom ( onSetAssignment )+ ( whereClause[false] )? )
            {
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr504); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr506);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:118:32: ( onSetAssignment )+
            int cnt28=0;
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==ON_SET_EXPR_ITEM) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // EsperEPL2Ast.g:118:32: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr508);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt28 >= 1 ) break loop28;
                        EarlyExitException eee =
                            new EarlyExitException(28, input);
                        throw eee;
                }
                cnt28++;
            } while (true);

            // EsperEPL2Ast.g:118:49: ( whereClause[false] )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==WHERE_EXPR) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:118:49: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr511);
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
    // EsperEPL2Ast.g:121:1: onSetAssignment : ^( ON_SET_EXPR_ITEM IDENT valueExpr ) ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:122:2: ( ^( ON_SET_EXPR_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:122:4: ^( ON_SET_EXPR_ITEM IDENT valueExpr )
            {
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment526); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onSetAssignment528); 
            pushFollow(FOLLOW_valueExpr_in_onSetAssignment530);
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
    // EsperEPL2Ast.g:125:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:126:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:126:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom544); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom546); 
            // EsperEPL2Ast.g:126:25: ( IDENT )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==IDENT) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:126:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom549); 

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
    // EsperEPL2Ast.g:129:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:130:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:130:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr567); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr569); 
            // EsperEPL2Ast.g:130:33: ( viewListExpr )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==VIEW_EXPR) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:130:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr572);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:130:49: ( RETAINUNION )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==RETAINUNION) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:130:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr576); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:130:62: ( RETAININTERSECTION )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RETAININTERSECTION) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:130:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr579); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:131:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==CLASS_IDENT||LA35_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt35=1;
            }
            else if ( (LA35_0==CREATE_WINDOW_COL_TYPE_LIST) ) {
                alt35=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 35, 0, input);

                throw nvae;
            }
            switch (alt35) {
                case 1 :
                    // EsperEPL2Ast.g:132:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:132:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:132:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:132:6: ( createSelectionList )?
                    int alt34=2;
                    int LA34_0 = input.LA(1);

                    if ( (LA34_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt34=1;
                    }
                    switch (alt34) {
                        case 1 :
                            // EsperEPL2Ast.g:132:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr593);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr596); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:134:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:134:12: ( createColTypeList )
                    // EsperEPL2Ast.g:134:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr625);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:136:4: ( createWindowExprInsert )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==INSERT) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:136:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr636);
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


    // $ANTLR start "createWindowExprInsert"
    // EsperEPL2Ast.g:140:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:141:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:141:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert654); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:141:13: ( valueExpr )?
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( ((LA37_0>=IN_SET && LA37_0<=REGEXP)||LA37_0==NOT_EXPR||(LA37_0>=SUM && LA37_0<=AVG)||(LA37_0>=COALESCE && LA37_0<=COUNT)||(LA37_0>=CASE && LA37_0<=CASE2)||(LA37_0>=PREVIOUS && LA37_0<=EXISTS)||(LA37_0>=INSTANCEOF && LA37_0<=CURRENT_TIMESTAMP)||(LA37_0>=EVAL_AND_EXPR && LA37_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA37_0==EVENT_PROP_EXPR||(LA37_0>=CONCAT && LA37_0<=LIB_FUNCTION)||LA37_0==ARRAY_EXPR||(LA37_0>=NOT_IN_SET && LA37_0<=NOT_REGEXP)||(LA37_0>=IN_RANGE && LA37_0<=SUBSELECT_EXPR)||(LA37_0>=EXISTS_SUBSELECT_EXPR && LA37_0<=NOT_IN_SUBSELECT_EXPR)||LA37_0==SUBSTITUTION||(LA37_0>=FIRST_AGGREG && LA37_0<=LAST_AGGREG)||(LA37_0>=INT_TYPE && LA37_0<=NULL_TYPE)||(LA37_0>=STAR && LA37_0<=PLUS)||(LA37_0>=BAND && LA37_0<=BXOR)||(LA37_0>=LT && LA37_0<=GE)||(LA37_0>=MINUS && LA37_0<=MOD)) ) {
                    alt37=1;
                }
                switch (alt37) {
                    case 1 :
                        // EsperEPL2Ast.g:141:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert656);
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
    // EsperEPL2Ast.g:144:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:145:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:145:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList673); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList675);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:145:61: ( createSelectionListElement )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==SELECTION_ELEMENT_EXPR||LA38_0==WILDCARD_SELECT) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // EsperEPL2Ast.g:145:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList678);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop38;
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
    // EsperEPL2Ast.g:148:1: createColTypeList : ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:149:2: ( ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:149:4: ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList697); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList699);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:149:59: ( createColTypeListElement )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==CREATE_WINDOW_COL_TYPE) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // EsperEPL2Ast.g:149:60: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList702);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop39;
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
    // EsperEPL2Ast.g:152:1: createColTypeListElement : ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:153:2: ( ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) )
            // EsperEPL2Ast.g:153:4: ^( CREATE_WINDOW_COL_TYPE IDENT IDENT )
            {
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement717); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement719); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement721); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:156:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:157:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==WILDCARD_SELECT) ) {
                alt42=1;
            }
            else if ( (LA42_0==SELECTION_ELEMENT_EXPR) ) {
                alt42=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:157:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement735); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:158:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement745); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:158:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==EVENT_PROP_EXPR) ) {
                        alt41=1;
                    }
                    else if ( ((LA41_0>=INT_TYPE && LA41_0<=NULL_TYPE)) ) {
                        alt41=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 41, 0, input);

                        throw nvae;
                    }
                    switch (alt41) {
                        case 1 :
                            // EsperEPL2Ast.g:159:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:159:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:159:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement765);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:159:41: ( IDENT )?
                            int alt40=2;
                            int LA40_0 = input.LA(1);

                            if ( (LA40_0==IDENT) ) {
                                alt40=1;
                            }
                            switch (alt40) {
                                case 1 :
                                    // EsperEPL2Ast.g:159:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement769); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:160:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:160:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:160:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement791);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement794); 

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
    // EsperEPL2Ast.g:164:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:165:2: ( ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:165:4: ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr830); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr832); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr834); 
            // EsperEPL2Ast.g:165:41: ( valueExpr )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=IN_SET && LA43_0<=REGEXP)||LA43_0==NOT_EXPR||(LA43_0>=SUM && LA43_0<=AVG)||(LA43_0>=COALESCE && LA43_0<=COUNT)||(LA43_0>=CASE && LA43_0<=CASE2)||(LA43_0>=PREVIOUS && LA43_0<=EXISTS)||(LA43_0>=INSTANCEOF && LA43_0<=CURRENT_TIMESTAMP)||(LA43_0>=EVAL_AND_EXPR && LA43_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA43_0==EVENT_PROP_EXPR||(LA43_0>=CONCAT && LA43_0<=LIB_FUNCTION)||LA43_0==ARRAY_EXPR||(LA43_0>=NOT_IN_SET && LA43_0<=NOT_REGEXP)||(LA43_0>=IN_RANGE && LA43_0<=SUBSELECT_EXPR)||(LA43_0>=EXISTS_SUBSELECT_EXPR && LA43_0<=NOT_IN_SUBSELECT_EXPR)||LA43_0==SUBSTITUTION||(LA43_0>=FIRST_AGGREG && LA43_0<=LAST_AGGREG)||(LA43_0>=INT_TYPE && LA43_0<=NULL_TYPE)||(LA43_0>=STAR && LA43_0<=PLUS)||(LA43_0>=BAND && LA43_0<=BXOR)||(LA43_0>=LT && LA43_0<=GE)||(LA43_0>=MINUS && LA43_0<=MOD)) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:165:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr837);
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


    // $ANTLR start "selectExpr"
    // EsperEPL2Ast.g:168:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:169:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:169:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:169:4: ( insertIntoExpr )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==INSERTINTO_EXPR) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:169:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr855);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr861);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr866);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:172:3: ( matchRecogClause )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==MATCH_RECOGNIZE) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:172:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr871);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:173:3: ( whereClause[true] )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==WHERE_EXPR) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:173:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr878);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:174:3: ( groupByClause )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==GROUP_BY_EXPR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:174:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr886);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:175:3: ( havingClause )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==HAVING_EXPR) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:175:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr893);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:176:3: ( outputLimitExpr )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( ((LA49_0>=EVENT_LIMIT_EXPR && LA49_0<=CRONTAB_LIMIT_EXPR)||LA49_0==WHEN_LIMIT_EXPR) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:176:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr900);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:177:3: ( orderByClause )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==ORDER_BY_EXPR) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // EsperEPL2Ast.g:177:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr907);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:178:3: ( rowLimitClause )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==ROW_LIMIT_EXPR) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:178:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr914);
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
    // EsperEPL2Ast.g:181:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:182:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:182:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr931); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:182:24: ( ISTREAM | RSTREAM )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RSTREAM && LA52_0<=ISTREAM)) ) {
                alt52=1;
            }
            switch (alt52) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr942); 
            // EsperEPL2Ast.g:182:51: ( insertIntoExprCol )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==INSERTINTO_EXPRCOL) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:182:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr945);
                    insertIntoExprCol();

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


    // $ANTLR start "insertIntoExprCol"
    // EsperEPL2Ast.g:185:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:186:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:186:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol964); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol966); 
            // EsperEPL2Ast.g:186:31: ( IDENT )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( (LA54_0==IDENT) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // EsperEPL2Ast.g:186:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol969); 

            	    }
            	    break;

            	default :
            	    break loop54;
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
    // $ANTLR end "insertIntoExprCol"


    // $ANTLR start "selectClause"
    // EsperEPL2Ast.g:189:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:190:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) )
            // EsperEPL2Ast.g:190:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause987); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:190:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=RSTREAM && LA55_0<=IRSTREAM)) ) {
                alt55=1;
            }
            switch (alt55) {
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

            // EsperEPL2Ast.g:190:55: ( DISTINCT )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==DISTINCT) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:190:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1002); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1005);
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
    // EsperEPL2Ast.g:193:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:194:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:194:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause1019);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:194:21: ( streamExpression ( outerJoin )* )*
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( (LA58_0==STREAM_EXPR) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // EsperEPL2Ast.g:194:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1022);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:194:39: ( outerJoin )*
            	    loop57:
            	    do {
            	        int alt57=2;
            	        int LA57_0 = input.LA(1);

            	        if ( ((LA57_0>=INNERJOIN_EXPR && LA57_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt57=1;
            	        }


            	        switch (alt57) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:194:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1025);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop57;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop58;
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


    // $ANTLR start "matchRecogClause"
    // EsperEPL2Ast.g:197:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:198:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:198:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1045); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:198:24: ( matchRecogPartitionBy )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==MATCHREC_PARTITION) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:198:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1047);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1054);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:200:4: ( ALL )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==ALL) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:200:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1060); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:201:4: ( matchRecogMatchesAfterSkip )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==MATCHREC_AFTER_SKIP) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // EsperEPL2Ast.g:201:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1066);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1072);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:203:4: ( matchRecogMatchesInterval )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==MATCHREC_INTERVAL) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:203:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1078);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1084);
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
    // EsperEPL2Ast.g:207:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:208:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:208:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1102); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:208:27: ( valueExpr )+
            int cnt63=0;
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( ((LA63_0>=IN_SET && LA63_0<=REGEXP)||LA63_0==NOT_EXPR||(LA63_0>=SUM && LA63_0<=AVG)||(LA63_0>=COALESCE && LA63_0<=COUNT)||(LA63_0>=CASE && LA63_0<=CASE2)||(LA63_0>=PREVIOUS && LA63_0<=EXISTS)||(LA63_0>=INSTANCEOF && LA63_0<=CURRENT_TIMESTAMP)||(LA63_0>=EVAL_AND_EXPR && LA63_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA63_0==EVENT_PROP_EXPR||(LA63_0>=CONCAT && LA63_0<=LIB_FUNCTION)||LA63_0==ARRAY_EXPR||(LA63_0>=NOT_IN_SET && LA63_0<=NOT_REGEXP)||(LA63_0>=IN_RANGE && LA63_0<=SUBSELECT_EXPR)||(LA63_0>=EXISTS_SUBSELECT_EXPR && LA63_0<=NOT_IN_SUBSELECT_EXPR)||LA63_0==SUBSTITUTION||(LA63_0>=FIRST_AGGREG && LA63_0<=LAST_AGGREG)||(LA63_0>=INT_TYPE && LA63_0<=NULL_TYPE)||(LA63_0>=STAR && LA63_0<=PLUS)||(LA63_0>=BAND && LA63_0<=BXOR)||(LA63_0>=LT && LA63_0<=GE)||(LA63_0>=MINUS && LA63_0<=MOD)) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // EsperEPL2Ast.g:208:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1104);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt63 >= 1 ) break loop63;
                        EarlyExitException eee =
                            new EarlyExitException(63, input);
                        throw eee;
                }
                cnt63++;
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
    // EsperEPL2Ast.g:211:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:212:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) )
            // EsperEPL2Ast.g:212:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1121); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1123); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1125); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1127); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1135); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:215:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:216:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:216:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1150); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1152); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1154);
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
    // EsperEPL2Ast.g:219:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:220:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:220:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1170); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:220:26: ( matchRecogMeasureListElement )*
                loop64:
                do {
                    int alt64=2;
                    int LA64_0 = input.LA(1);

                    if ( (LA64_0==MATCHREC_MEASURE_ITEM) ) {
                        alt64=1;
                    }


                    switch (alt64) {
                	case 1 :
                	    // EsperEPL2Ast.g:220:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1172);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop64;
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
    // EsperEPL2Ast.g:223:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:224:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:224:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1189); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1191);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:224:40: ( IDENT )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==IDENT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:224:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1193); 

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
    // EsperEPL2Ast.g:227:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:228:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:228:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1213); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:228:25: ( matchRecogPatternAlteration )+
            int cnt66=0;
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=MATCHREC_PATTERN_CONCAT && LA66_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // EsperEPL2Ast.g:228:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1215);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt66 >= 1 ) break loop66;
                        EarlyExitException eee =
                            new EarlyExitException(66, input);
                        throw eee;
                }
                cnt66++;
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
    // EsperEPL2Ast.g:231:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:232:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==MATCHREC_PATTERN_CONCAT) ) {
                alt68=1;
            }
            else if ( (LA68_0==MATCHREC_PATTERN_ALTER) ) {
                alt68=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:232:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1230);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:233:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1238); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1240);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:233:55: ( matchRecogPatternConcat )+
                    int cnt67=0;
                    loop67:
                    do {
                        int alt67=2;
                        int LA67_0 = input.LA(1);

                        if ( (LA67_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt67=1;
                        }


                        switch (alt67) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:233:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1242);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt67 >= 1 ) break loop67;
                                EarlyExitException eee =
                                    new EarlyExitException(67, input);
                                throw eee;
                        }
                        cnt67++;
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
    // EsperEPL2Ast.g:236:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:237:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:237:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1260); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:237:32: ( matchRecogPatternUnary )+
            int cnt69=0;
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==MATCHREC_PATTERN_ATOM||LA69_0==MATCHREC_PATTERN_NESTED) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // EsperEPL2Ast.g:237:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1262);
            	    matchRecogPatternUnary();

            	    state._fsp--;


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
    // EsperEPL2Ast.g:240:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:241:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==MATCHREC_PATTERN_NESTED) ) {
                alt70=1;
            }
            else if ( (LA70_0==MATCHREC_PATTERN_ATOM) ) {
                alt70=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:241:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1277);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:242:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1282);
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
    // EsperEPL2Ast.g:245:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:246:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:246:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1297); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1299);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:246:60: ( PLUS | STAR | QUESTION )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==STAR||(LA71_0>=PLUS && LA71_0<=QUESTION)) ) {
                alt71=1;
            }
            switch (alt71) {
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
    // EsperEPL2Ast.g:249:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:250:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:250:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1330); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1332); 
            // EsperEPL2Ast.g:250:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==STAR||(LA73_0>=PLUS && LA73_0<=QUESTION)) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:250:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:250:63: ( QUESTION )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==QUESTION) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // EsperEPL2Ast.g:250:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1348); 

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
    // EsperEPL2Ast.g:253:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:254:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:254:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1370); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:254:24: ( matchRecogDefineItem )+
            int cnt74=0;
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( (LA74_0==MATCHREC_DEFINE_ITEM) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // EsperEPL2Ast.g:254:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1372);
            	    matchRecogDefineItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt74 >= 1 ) break loop74;
                        EarlyExitException eee =
                            new EarlyExitException(74, input);
                        throw eee;
                }
                cnt74++;
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
    // EsperEPL2Ast.g:257:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:258:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:258:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1389); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1391); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1393);
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
    // EsperEPL2Ast.g:262:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:263:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:263:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1410);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:263:25: ( selectionListElement )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( ((LA75_0>=SELECTION_ELEMENT_EXPR && LA75_0<=SELECTION_STREAM)||LA75_0==WILDCARD_SELECT) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // EsperEPL2Ast.g:263:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1413);
            	    selectionListElement();

            	    state._fsp--;


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
    // $ANTLR end "selectionList"


    // $ANTLR start "selectionListElement"
    // EsperEPL2Ast.g:266:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:267:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt78=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt78=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt78=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt78=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:267:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1429); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:268:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1439); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1441);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:268:41: ( IDENT )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==IDENT) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // EsperEPL2Ast.g:268:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1444); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:269:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1458); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1460); 
                    // EsperEPL2Ast.g:269:31: ( IDENT )?
                    int alt77=2;
                    int LA77_0 = input.LA(1);

                    if ( (LA77_0==IDENT) ) {
                        alt77=1;
                    }
                    switch (alt77) {
                        case 1 :
                            // EsperEPL2Ast.g:269:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1463); 

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
    // EsperEPL2Ast.g:272:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:273:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:273:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1482);
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
    // EsperEPL2Ast.g:276:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:277:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt83=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt83=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt83=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt83=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt83=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }

            switch (alt83) {
                case 1 :
                    // EsperEPL2Ast.g:277:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1496); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1498);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1501);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:277:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==EVENT_PROP_EXPR) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:277:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1505);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1508);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop79;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:278:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1523); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1525);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1528);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:278:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop80:
                    do {
                        int alt80=2;
                        int LA80_0 = input.LA(1);

                        if ( (LA80_0==EVENT_PROP_EXPR) ) {
                            alt80=1;
                        }


                        switch (alt80) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:278:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1532);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1535);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop80;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:279:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1550); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1552);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1555);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:279:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop81:
                    do {
                        int alt81=2;
                        int LA81_0 = input.LA(1);

                        if ( (LA81_0==EVENT_PROP_EXPR) ) {
                            alt81=1;
                        }


                        switch (alt81) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:279:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1559);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1562);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop81;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:280:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1577); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1579);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1582);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:280:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop82:
                    do {
                        int alt82=2;
                        int LA82_0 = input.LA(1);

                        if ( (LA82_0==EVENT_PROP_EXPR) ) {
                            alt82=1;
                        }


                        switch (alt82) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:280:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1586);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1589);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop82;
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
    // EsperEPL2Ast.g:283:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:284:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:284:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1610); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:284:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt84=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt84=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt84=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt84=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt84=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:284:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1613);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:284:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1617);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:284:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1621);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:284:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1625);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:284:115: ( viewListExpr )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==VIEW_EXPR) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // EsperEPL2Ast.g:284:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1629);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:284:131: ( IDENT )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==IDENT) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:284:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1634); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:284:140: ( UNIDIRECTIONAL )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==UNIDIRECTIONAL) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:284:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1639); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:284:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( ((LA88_0>=RETAINUNION && LA88_0<=RETAININTERSECTION)) ) {
                alt88=1;
            }
            switch (alt88) {
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
    // EsperEPL2Ast.g:287:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:288:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:288:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1667); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:288:27: ( IDENT )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==IDENT) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:288:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1669); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1672); 
            // EsperEPL2Ast.g:288:46: ( propertyExpression )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:288:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1674);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:288:66: ( valueExpr )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( ((LA91_0>=IN_SET && LA91_0<=REGEXP)||LA91_0==NOT_EXPR||(LA91_0>=SUM && LA91_0<=AVG)||(LA91_0>=COALESCE && LA91_0<=COUNT)||(LA91_0>=CASE && LA91_0<=CASE2)||(LA91_0>=PREVIOUS && LA91_0<=EXISTS)||(LA91_0>=INSTANCEOF && LA91_0<=CURRENT_TIMESTAMP)||(LA91_0>=EVAL_AND_EXPR && LA91_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA91_0==EVENT_PROP_EXPR||(LA91_0>=CONCAT && LA91_0<=LIB_FUNCTION)||LA91_0==ARRAY_EXPR||(LA91_0>=NOT_IN_SET && LA91_0<=NOT_REGEXP)||(LA91_0>=IN_RANGE && LA91_0<=SUBSELECT_EXPR)||(LA91_0>=EXISTS_SUBSELECT_EXPR && LA91_0<=NOT_IN_SUBSELECT_EXPR)||LA91_0==SUBSTITUTION||(LA91_0>=FIRST_AGGREG && LA91_0<=LAST_AGGREG)||(LA91_0>=INT_TYPE && LA91_0<=NULL_TYPE)||(LA91_0>=STAR && LA91_0<=PLUS)||(LA91_0>=BAND && LA91_0<=BXOR)||(LA91_0>=LT && LA91_0<=GE)||(LA91_0>=MINUS && LA91_0<=MOD)) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // EsperEPL2Ast.g:288:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1678);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop91;
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
    // EsperEPL2Ast.g:291:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:292:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:292:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1698); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:292:34: ( propertyExpressionAtom )*
                loop92:
                do {
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt92=1;
                    }


                    switch (alt92) {
                	case 1 :
                	    // EsperEPL2Ast.g:292:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1700);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop92;
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
    // EsperEPL2Ast.g:295:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:296:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:296:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1719); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:296:41: ( propertySelectionListElement )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA93_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // EsperEPL2Ast.g:296:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1721);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop93;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1724);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:296:96: ( IDENT )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==IDENT) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // EsperEPL2Ast.g:296:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1727); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1731); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:296:116: ( valueExpr )?
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=IN_SET && LA95_0<=REGEXP)||LA95_0==NOT_EXPR||(LA95_0>=SUM && LA95_0<=AVG)||(LA95_0>=COALESCE && LA95_0<=COUNT)||(LA95_0>=CASE && LA95_0<=CASE2)||(LA95_0>=PREVIOUS && LA95_0<=EXISTS)||(LA95_0>=INSTANCEOF && LA95_0<=CURRENT_TIMESTAMP)||(LA95_0>=EVAL_AND_EXPR && LA95_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA95_0==EVENT_PROP_EXPR||(LA95_0>=CONCAT && LA95_0<=LIB_FUNCTION)||LA95_0==ARRAY_EXPR||(LA95_0>=NOT_IN_SET && LA95_0<=NOT_REGEXP)||(LA95_0>=IN_RANGE && LA95_0<=SUBSELECT_EXPR)||(LA95_0>=EXISTS_SUBSELECT_EXPR && LA95_0<=NOT_IN_SUBSELECT_EXPR)||LA95_0==SUBSTITUTION||(LA95_0>=FIRST_AGGREG && LA95_0<=LAST_AGGREG)||(LA95_0>=INT_TYPE && LA95_0<=NULL_TYPE)||(LA95_0>=STAR && LA95_0<=PLUS)||(LA95_0>=BAND && LA95_0<=BXOR)||(LA95_0>=LT && LA95_0<=GE)||(LA95_0>=MINUS && LA95_0<=MOD)) ) {
                    alt95=1;
                }
                switch (alt95) {
                    case 1 :
                        // EsperEPL2Ast.g:296:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1733);
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
    // EsperEPL2Ast.g:299:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:300:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt98=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt98=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt98=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
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
                    // EsperEPL2Ast.g:300:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1753); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:301:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1763); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1765);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:301:50: ( IDENT )?
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==IDENT) ) {
                        alt96=1;
                    }
                    switch (alt96) {
                        case 1 :
                            // EsperEPL2Ast.g:301:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1768); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:302:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1782); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1784); 
                    // EsperEPL2Ast.g:302:40: ( IDENT )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( (LA97_0==IDENT) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // EsperEPL2Ast.g:302:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1787); 

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
    // EsperEPL2Ast.g:305:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:306:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:306:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1808); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1810);
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
    // EsperEPL2Ast.g:309:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:310:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:310:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1827); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1829); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:310:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( ((LA99_0>=STRING_LITERAL && LA99_0<=QUOTED_STRING_LITERAL)) ) {
                alt99=1;
            }
            switch (alt99) {
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
    // EsperEPL2Ast.g:313:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:314:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:314:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1860); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1862); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1864); 
            // EsperEPL2Ast.g:314:41: ( valueExpr )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( ((LA100_0>=IN_SET && LA100_0<=REGEXP)||LA100_0==NOT_EXPR||(LA100_0>=SUM && LA100_0<=AVG)||(LA100_0>=COALESCE && LA100_0<=COUNT)||(LA100_0>=CASE && LA100_0<=CASE2)||(LA100_0>=PREVIOUS && LA100_0<=EXISTS)||(LA100_0>=INSTANCEOF && LA100_0<=CURRENT_TIMESTAMP)||(LA100_0>=EVAL_AND_EXPR && LA100_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA100_0==EVENT_PROP_EXPR||(LA100_0>=CONCAT && LA100_0<=LIB_FUNCTION)||LA100_0==ARRAY_EXPR||(LA100_0>=NOT_IN_SET && LA100_0<=NOT_REGEXP)||(LA100_0>=IN_RANGE && LA100_0<=SUBSELECT_EXPR)||(LA100_0>=EXISTS_SUBSELECT_EXPR && LA100_0<=NOT_IN_SUBSELECT_EXPR)||LA100_0==SUBSTITUTION||(LA100_0>=FIRST_AGGREG && LA100_0<=LAST_AGGREG)||(LA100_0>=INT_TYPE && LA100_0<=NULL_TYPE)||(LA100_0>=STAR && LA100_0<=PLUS)||(LA100_0>=BAND && LA100_0<=BXOR)||(LA100_0>=LT && LA100_0<=GE)||(LA100_0>=MINUS && LA100_0<=MOD)) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // EsperEPL2Ast.g:314:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1867);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop100;
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
    // EsperEPL2Ast.g:317:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:318:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:318:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1881);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:318:13: ( viewExpr )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==VIEW_EXPR) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // EsperEPL2Ast.g:318:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1884);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop101;
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
    // EsperEPL2Ast.g:321:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:322:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:322:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1901); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1903); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1905); 
            // EsperEPL2Ast.g:322:30: ( valueExprWithTime )*
            loop102:
            do {
                int alt102=2;
                int LA102_0 = input.LA(1);

                if ( ((LA102_0>=IN_SET && LA102_0<=REGEXP)||LA102_0==NOT_EXPR||(LA102_0>=SUM && LA102_0<=AVG)||(LA102_0>=COALESCE && LA102_0<=COUNT)||(LA102_0>=CASE && LA102_0<=CASE2)||LA102_0==LAST||(LA102_0>=PREVIOUS && LA102_0<=EXISTS)||(LA102_0>=LW && LA102_0<=CURRENT_TIMESTAMP)||(LA102_0>=NUMERIC_PARAM_RANGE && LA102_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA102_0>=EVAL_AND_EXPR && LA102_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA102_0==EVENT_PROP_EXPR||(LA102_0>=CONCAT && LA102_0<=LIB_FUNCTION)||(LA102_0>=TIME_PERIOD && LA102_0<=ARRAY_EXPR)||(LA102_0>=NOT_IN_SET && LA102_0<=NOT_REGEXP)||(LA102_0>=IN_RANGE && LA102_0<=SUBSELECT_EXPR)||(LA102_0>=EXISTS_SUBSELECT_EXPR && LA102_0<=NOT_IN_SUBSELECT_EXPR)||(LA102_0>=LAST_OPERATOR && LA102_0<=SUBSTITUTION)||LA102_0==NUMBERSETSTAR||(LA102_0>=FIRST_AGGREG && LA102_0<=LAST_AGGREG)||(LA102_0>=INT_TYPE && LA102_0<=NULL_TYPE)||(LA102_0>=STAR && LA102_0<=PLUS)||(LA102_0>=BAND && LA102_0<=BXOR)||(LA102_0>=LT && LA102_0<=GE)||(LA102_0>=MINUS && LA102_0<=MOD)) ) {
                    alt102=1;
                }


                switch (alt102) {
            	case 1 :
            	    // EsperEPL2Ast.g:322:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1908);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop102;
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
    // EsperEPL2Ast.g:325:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:326:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:326:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1930); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1932);
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
    // EsperEPL2Ast.g:329:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:330:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:330:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1950); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1952);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:330:32: ( valueExpr )*
            loop103:
            do {
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( ((LA103_0>=IN_SET && LA103_0<=REGEXP)||LA103_0==NOT_EXPR||(LA103_0>=SUM && LA103_0<=AVG)||(LA103_0>=COALESCE && LA103_0<=COUNT)||(LA103_0>=CASE && LA103_0<=CASE2)||(LA103_0>=PREVIOUS && LA103_0<=EXISTS)||(LA103_0>=INSTANCEOF && LA103_0<=CURRENT_TIMESTAMP)||(LA103_0>=EVAL_AND_EXPR && LA103_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA103_0==EVENT_PROP_EXPR||(LA103_0>=CONCAT && LA103_0<=LIB_FUNCTION)||LA103_0==ARRAY_EXPR||(LA103_0>=NOT_IN_SET && LA103_0<=NOT_REGEXP)||(LA103_0>=IN_RANGE && LA103_0<=SUBSELECT_EXPR)||(LA103_0>=EXISTS_SUBSELECT_EXPR && LA103_0<=NOT_IN_SUBSELECT_EXPR)||LA103_0==SUBSTITUTION||(LA103_0>=FIRST_AGGREG && LA103_0<=LAST_AGGREG)||(LA103_0>=INT_TYPE && LA103_0<=NULL_TYPE)||(LA103_0>=STAR && LA103_0<=PLUS)||(LA103_0>=BAND && LA103_0<=BXOR)||(LA103_0>=LT && LA103_0<=GE)||(LA103_0>=MINUS && LA103_0<=MOD)) ) {
                    alt103=1;
                }


                switch (alt103) {
            	case 1 :
            	    // EsperEPL2Ast.g:330:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1955);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop103;
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
    // EsperEPL2Ast.g:333:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:334:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:334:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1973); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1975);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:334:35: ( orderByElement )*
            loop104:
            do {
                int alt104=2;
                int LA104_0 = input.LA(1);

                if ( (LA104_0==ORDER_ELEMENT_EXPR) ) {
                    alt104=1;
                }


                switch (alt104) {
            	case 1 :
            	    // EsperEPL2Ast.g:334:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1978);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop104;
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
    // EsperEPL2Ast.g:337:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:338:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:338:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1998); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2000);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:338:38: ( ASC | DESC )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( ((LA105_0>=ASC && LA105_0<=DESC)) ) {
                alt105=1;
            }
            switch (alt105) {
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
    // EsperEPL2Ast.g:341:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:342:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:342:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2025); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2027);
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
    // EsperEPL2Ast.g:345:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:346:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt116=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt116=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt116=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt116=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt116=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt116=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }

            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:346:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2045); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:346:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==ALL||(LA106_0>=FIRST && LA106_0<=LAST)||LA106_0==SNAPSHOT) ) {
                        alt106=1;
                    }
                    switch (alt106) {
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

                    // EsperEPL2Ast.g:346:52: ( number | IDENT )
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( ((LA107_0>=INT_TYPE && LA107_0<=DOUBLE_TYPE)) ) {
                        alt107=1;
                    }
                    else if ( (LA107_0==IDENT) ) {
                        alt107=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 107, 0, input);

                        throw nvae;
                    }
                    switch (alt107) {
                        case 1 :
                            // EsperEPL2Ast.g:346:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2059);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:346:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2061); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:346:67: ( outputLimitAfter )?
                    int alt108=2;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==AFTER) ) {
                        alt108=1;
                    }
                    switch (alt108) {
                        case 1 :
                            // EsperEPL2Ast.g:346:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2064);
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
                    // EsperEPL2Ast.g:347:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2081); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:347:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt109=2;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==ALL||(LA109_0>=FIRST && LA109_0<=LAST)||LA109_0==SNAPSHOT) ) {
                        alt109=1;
                    }
                    switch (alt109) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2094);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:347:72: ( outputLimitAfter )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==AFTER) ) {
                        alt110=1;
                    }
                    switch (alt110) {
                        case 1 :
                            // EsperEPL2Ast.g:347:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2096);
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
                    // EsperEPL2Ast.g:348:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2112); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:348:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==ALL||(LA111_0>=FIRST && LA111_0<=LAST)||LA111_0==SNAPSHOT) ) {
                        alt111=1;
                    }
                    switch (alt111) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2125);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:348:85: ( outputLimitAfter )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==AFTER) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // EsperEPL2Ast.g:348:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2127);
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
                    // EsperEPL2Ast.g:349:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2143); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:349:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt113=2;
                    int LA113_0 = input.LA(1);

                    if ( (LA113_0==ALL||(LA113_0>=FIRST && LA113_0<=LAST)||LA113_0==SNAPSHOT) ) {
                        alt113=1;
                    }
                    switch (alt113) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2156);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:349:67: ( onSetExpr )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==ON_SET_EXPR) ) {
                        alt114=1;
                    }
                    switch (alt114) {
                        case 1 :
                            // EsperEPL2Ast.g:349:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2158);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:349:78: ( outputLimitAfter )?
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==AFTER) ) {
                        alt115=1;
                    }
                    switch (alt115) {
                        case 1 :
                            // EsperEPL2Ast.g:349:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2161);
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
                    // EsperEPL2Ast.g:350:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2174); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2176);
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
    // EsperEPL2Ast.g:353:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:354:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:354:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2191); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:354:12: ( timePeriod )?
                int alt117=2;
                int LA117_0 = input.LA(1);

                if ( (LA117_0==TIME_PERIOD) ) {
                    alt117=1;
                }
                switch (alt117) {
                    case 1 :
                        // EsperEPL2Ast.g:354:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2193);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:354:24: ( number )?
                int alt118=2;
                int LA118_0 = input.LA(1);

                if ( ((LA118_0>=INT_TYPE && LA118_0<=DOUBLE_TYPE)) ) {
                    alt118=1;
                }
                switch (alt118) {
                    case 1 :
                        // EsperEPL2Ast.g:354:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2196);
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
    // EsperEPL2Ast.g:357:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:358:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:358:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2212); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:358:23: ( number | IDENT )
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( ((LA119_0>=INT_TYPE && LA119_0<=DOUBLE_TYPE)) ) {
                alt119=1;
            }
            else if ( (LA119_0==IDENT) ) {
                alt119=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }
            switch (alt119) {
                case 1 :
                    // EsperEPL2Ast.g:358:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2215);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:358:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2217); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:358:38: ( number | IDENT )?
            int alt120=3;
            int LA120_0 = input.LA(1);

            if ( ((LA120_0>=INT_TYPE && LA120_0<=DOUBLE_TYPE)) ) {
                alt120=1;
            }
            else if ( (LA120_0==IDENT) ) {
                alt120=2;
            }
            switch (alt120) {
                case 1 :
                    // EsperEPL2Ast.g:358:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2221);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:358:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2223); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:358:54: ( COMMA )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==COMMA) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // EsperEPL2Ast.g:358:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2227); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:358:61: ( OFFSET )?
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==OFFSET) ) {
                alt122=1;
            }
            switch (alt122) {
                case 1 :
                    // EsperEPL2Ast.g:358:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2230); 

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
    // EsperEPL2Ast.g:361:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:362:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:362:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2248); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2250);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2252);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2254);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2256);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2258);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:362:121: ( valueExprWithTime )?
            int alt123=2;
            int LA123_0 = input.LA(1);

            if ( ((LA123_0>=IN_SET && LA123_0<=REGEXP)||LA123_0==NOT_EXPR||(LA123_0>=SUM && LA123_0<=AVG)||(LA123_0>=COALESCE && LA123_0<=COUNT)||(LA123_0>=CASE && LA123_0<=CASE2)||LA123_0==LAST||(LA123_0>=PREVIOUS && LA123_0<=EXISTS)||(LA123_0>=LW && LA123_0<=CURRENT_TIMESTAMP)||(LA123_0>=NUMERIC_PARAM_RANGE && LA123_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA123_0>=EVAL_AND_EXPR && LA123_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA123_0==EVENT_PROP_EXPR||(LA123_0>=CONCAT && LA123_0<=LIB_FUNCTION)||(LA123_0>=TIME_PERIOD && LA123_0<=ARRAY_EXPR)||(LA123_0>=NOT_IN_SET && LA123_0<=NOT_REGEXP)||(LA123_0>=IN_RANGE && LA123_0<=SUBSELECT_EXPR)||(LA123_0>=EXISTS_SUBSELECT_EXPR && LA123_0<=NOT_IN_SUBSELECT_EXPR)||(LA123_0>=LAST_OPERATOR && LA123_0<=SUBSTITUTION)||LA123_0==NUMBERSETSTAR||(LA123_0>=FIRST_AGGREG && LA123_0<=LAST_AGGREG)||(LA123_0>=INT_TYPE && LA123_0<=NULL_TYPE)||(LA123_0>=STAR && LA123_0<=PLUS)||(LA123_0>=BAND && LA123_0<=BXOR)||(LA123_0>=LT && LA123_0<=GE)||(LA123_0>=MINUS && LA123_0<=MOD)) ) {
                alt123=1;
            }
            switch (alt123) {
                case 1 :
                    // EsperEPL2Ast.g:362:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2260);
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
    // EsperEPL2Ast.g:365:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:366:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt124=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt124=1;
                }
                break;
            case GT:
                {
                alt124=2;
                }
                break;
            case LE:
                {
                alt124=3;
                }
                break;
            case GE:
                {
                alt124=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }

            switch (alt124) {
                case 1 :
                    // EsperEPL2Ast.g:366:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2277); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2279);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:367:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2292); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2294);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:368:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2307); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2309);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:369:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2321); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2323);
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
    // EsperEPL2Ast.g:372:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:373:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:373:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:373:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:374:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2345);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:375:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA127_0==EVENT_PROP_EXPR||(LA127_0>=CONCAT && LA127_0<=LIB_FUNCTION)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=SUBSELECT_EXPR)||(LA127_0>=EXISTS_SUBSELECT_EXPR && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=FIRST_AGGREG && LA127_0<=LAST_AGGREG)||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||(LA127_0>=STAR && LA127_0<=PLUS)||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=MINUS && LA127_0<=MOD)) ) {
                alt127=1;
            }
            else if ( ((LA127_0>=ALL && LA127_0<=SOME)) ) {
                alt127=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 127, 0, input);

                throw nvae;
            }
            switch (alt127) {
                case 1 :
                    // EsperEPL2Ast.g:375:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2355);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:377:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:377:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt126=2;
                    int LA126_0 = input.LA(1);

                    if ( (LA126_0==UP||(LA126_0>=IN_SET && LA126_0<=REGEXP)||LA126_0==NOT_EXPR||(LA126_0>=SUM && LA126_0<=AVG)||(LA126_0>=COALESCE && LA126_0<=COUNT)||(LA126_0>=CASE && LA126_0<=CASE2)||(LA126_0>=PREVIOUS && LA126_0<=EXISTS)||(LA126_0>=INSTANCEOF && LA126_0<=CURRENT_TIMESTAMP)||(LA126_0>=EVAL_AND_EXPR && LA126_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA126_0==EVENT_PROP_EXPR||(LA126_0>=CONCAT && LA126_0<=LIB_FUNCTION)||LA126_0==ARRAY_EXPR||(LA126_0>=NOT_IN_SET && LA126_0<=NOT_REGEXP)||(LA126_0>=IN_RANGE && LA126_0<=SUBSELECT_EXPR)||(LA126_0>=EXISTS_SUBSELECT_EXPR && LA126_0<=NOT_IN_SUBSELECT_EXPR)||LA126_0==SUBSTITUTION||(LA126_0>=FIRST_AGGREG && LA126_0<=LAST_AGGREG)||(LA126_0>=INT_TYPE && LA126_0<=NULL_TYPE)||(LA126_0>=STAR && LA126_0<=PLUS)||(LA126_0>=BAND && LA126_0<=BXOR)||(LA126_0>=LT && LA126_0<=GE)||(LA126_0>=MINUS && LA126_0<=MOD)) ) {
                        alt126=1;
                    }
                    else if ( (LA126_0==SUBSELECT_GROUP_EXPR) ) {
                        alt126=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 126, 0, input);

                        throw nvae;
                    }
                    switch (alt126) {
                        case 1 :
                            // EsperEPL2Ast.g:377:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:377:22: ( valueExpr )*
                            loop125:
                            do {
                                int alt125=2;
                                int LA125_0 = input.LA(1);

                                if ( ((LA125_0>=IN_SET && LA125_0<=REGEXP)||LA125_0==NOT_EXPR||(LA125_0>=SUM && LA125_0<=AVG)||(LA125_0>=COALESCE && LA125_0<=COUNT)||(LA125_0>=CASE && LA125_0<=CASE2)||(LA125_0>=PREVIOUS && LA125_0<=EXISTS)||(LA125_0>=INSTANCEOF && LA125_0<=CURRENT_TIMESTAMP)||(LA125_0>=EVAL_AND_EXPR && LA125_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA125_0==EVENT_PROP_EXPR||(LA125_0>=CONCAT && LA125_0<=LIB_FUNCTION)||LA125_0==ARRAY_EXPR||(LA125_0>=NOT_IN_SET && LA125_0<=NOT_REGEXP)||(LA125_0>=IN_RANGE && LA125_0<=SUBSELECT_EXPR)||(LA125_0>=EXISTS_SUBSELECT_EXPR && LA125_0<=NOT_IN_SUBSELECT_EXPR)||LA125_0==SUBSTITUTION||(LA125_0>=FIRST_AGGREG && LA125_0<=LAST_AGGREG)||(LA125_0>=INT_TYPE && LA125_0<=NULL_TYPE)||(LA125_0>=STAR && LA125_0<=PLUS)||(LA125_0>=BAND && LA125_0<=BXOR)||(LA125_0>=LT && LA125_0<=GE)||(LA125_0>=MINUS && LA125_0<=MOD)) ) {
                                    alt125=1;
                                }


                                switch (alt125) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:377:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2379);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop125;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:377:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2384);
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
    // EsperEPL2Ast.g:382:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:383:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt134=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt134=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt134=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt134=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt134=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt134=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt134=6;
                }
                break;
            case NOT_EXPR:
                {
                alt134=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt134=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 134, 0, input);

                throw nvae;
            }

            switch (alt134) {
                case 1 :
                    // EsperEPL2Ast.g:383:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2410); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2412);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2414);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:383:42: ( valueExpr )*
                    loop128:
                    do {
                        int alt128=2;
                        int LA128_0 = input.LA(1);

                        if ( ((LA128_0>=IN_SET && LA128_0<=REGEXP)||LA128_0==NOT_EXPR||(LA128_0>=SUM && LA128_0<=AVG)||(LA128_0>=COALESCE && LA128_0<=COUNT)||(LA128_0>=CASE && LA128_0<=CASE2)||(LA128_0>=PREVIOUS && LA128_0<=EXISTS)||(LA128_0>=INSTANCEOF && LA128_0<=CURRENT_TIMESTAMP)||(LA128_0>=EVAL_AND_EXPR && LA128_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA128_0==EVENT_PROP_EXPR||(LA128_0>=CONCAT && LA128_0<=LIB_FUNCTION)||LA128_0==ARRAY_EXPR||(LA128_0>=NOT_IN_SET && LA128_0<=NOT_REGEXP)||(LA128_0>=IN_RANGE && LA128_0<=SUBSELECT_EXPR)||(LA128_0>=EXISTS_SUBSELECT_EXPR && LA128_0<=NOT_IN_SUBSELECT_EXPR)||LA128_0==SUBSTITUTION||(LA128_0>=FIRST_AGGREG && LA128_0<=LAST_AGGREG)||(LA128_0>=INT_TYPE && LA128_0<=NULL_TYPE)||(LA128_0>=STAR && LA128_0<=PLUS)||(LA128_0>=BAND && LA128_0<=BXOR)||(LA128_0>=LT && LA128_0<=GE)||(LA128_0>=MINUS && LA128_0<=MOD)) ) {
                            alt128=1;
                        }


                        switch (alt128) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:383:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2417);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop128;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:384:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2431); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2433);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2435);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:384:43: ( valueExpr )*
                    loop129:
                    do {
                        int alt129=2;
                        int LA129_0 = input.LA(1);

                        if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=INSTANCEOF && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA129_0==EVENT_PROP_EXPR||(LA129_0>=CONCAT && LA129_0<=LIB_FUNCTION)||LA129_0==ARRAY_EXPR||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=SUBSELECT_EXPR)||(LA129_0>=EXISTS_SUBSELECT_EXPR && LA129_0<=NOT_IN_SUBSELECT_EXPR)||LA129_0==SUBSTITUTION||(LA129_0>=FIRST_AGGREG && LA129_0<=LAST_AGGREG)||(LA129_0>=INT_TYPE && LA129_0<=NULL_TYPE)||(LA129_0>=STAR && LA129_0<=PLUS)||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=MINUS && LA129_0<=MOD)) ) {
                            alt129=1;
                        }


                        switch (alt129) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:384:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2438);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop129;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:385:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2452); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2454);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2456);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:386:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2468); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2470);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2472);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:387:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2484); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2486);
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

                    // EsperEPL2Ast.g:387:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==UP||(LA131_0>=IN_SET && LA131_0<=REGEXP)||LA131_0==NOT_EXPR||(LA131_0>=SUM && LA131_0<=AVG)||(LA131_0>=COALESCE && LA131_0<=COUNT)||(LA131_0>=CASE && LA131_0<=CASE2)||(LA131_0>=PREVIOUS && LA131_0<=EXISTS)||(LA131_0>=INSTANCEOF && LA131_0<=CURRENT_TIMESTAMP)||(LA131_0>=EVAL_AND_EXPR && LA131_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA131_0==EVENT_PROP_EXPR||(LA131_0>=CONCAT && LA131_0<=LIB_FUNCTION)||LA131_0==ARRAY_EXPR||(LA131_0>=NOT_IN_SET && LA131_0<=NOT_REGEXP)||(LA131_0>=IN_RANGE && LA131_0<=SUBSELECT_EXPR)||(LA131_0>=EXISTS_SUBSELECT_EXPR && LA131_0<=NOT_IN_SUBSELECT_EXPR)||LA131_0==SUBSTITUTION||(LA131_0>=FIRST_AGGREG && LA131_0<=LAST_AGGREG)||(LA131_0>=INT_TYPE && LA131_0<=NULL_TYPE)||(LA131_0>=STAR && LA131_0<=PLUS)||(LA131_0>=BAND && LA131_0<=BXOR)||(LA131_0>=LT && LA131_0<=GE)||(LA131_0>=MINUS && LA131_0<=MOD)) ) {
                        alt131=1;
                    }
                    else if ( (LA131_0==SUBSELECT_GROUP_EXPR) ) {
                        alt131=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 131, 0, input);

                        throw nvae;
                    }
                    switch (alt131) {
                        case 1 :
                            // EsperEPL2Ast.g:387:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:387:59: ( valueExpr )*
                            loop130:
                            do {
                                int alt130=2;
                                int LA130_0 = input.LA(1);

                                if ( ((LA130_0>=IN_SET && LA130_0<=REGEXP)||LA130_0==NOT_EXPR||(LA130_0>=SUM && LA130_0<=AVG)||(LA130_0>=COALESCE && LA130_0<=COUNT)||(LA130_0>=CASE && LA130_0<=CASE2)||(LA130_0>=PREVIOUS && LA130_0<=EXISTS)||(LA130_0>=INSTANCEOF && LA130_0<=CURRENT_TIMESTAMP)||(LA130_0>=EVAL_AND_EXPR && LA130_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA130_0==EVENT_PROP_EXPR||(LA130_0>=CONCAT && LA130_0<=LIB_FUNCTION)||LA130_0==ARRAY_EXPR||(LA130_0>=NOT_IN_SET && LA130_0<=NOT_REGEXP)||(LA130_0>=IN_RANGE && LA130_0<=SUBSELECT_EXPR)||(LA130_0>=EXISTS_SUBSELECT_EXPR && LA130_0<=NOT_IN_SUBSELECT_EXPR)||LA130_0==SUBSTITUTION||(LA130_0>=FIRST_AGGREG && LA130_0<=LAST_AGGREG)||(LA130_0>=INT_TYPE && LA130_0<=NULL_TYPE)||(LA130_0>=STAR && LA130_0<=PLUS)||(LA130_0>=BAND && LA130_0<=BXOR)||(LA130_0>=LT && LA130_0<=GE)||(LA130_0>=MINUS && LA130_0<=MOD)) ) {
                                    alt130=1;
                                }


                                switch (alt130) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:387:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2497);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop130;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:387:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2502);
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
                    // EsperEPL2Ast.g:388:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2515); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2517);
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

                    // EsperEPL2Ast.g:388:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt133=2;
                    int LA133_0 = input.LA(1);

                    if ( (LA133_0==UP||(LA133_0>=IN_SET && LA133_0<=REGEXP)||LA133_0==NOT_EXPR||(LA133_0>=SUM && LA133_0<=AVG)||(LA133_0>=COALESCE && LA133_0<=COUNT)||(LA133_0>=CASE && LA133_0<=CASE2)||(LA133_0>=PREVIOUS && LA133_0<=EXISTS)||(LA133_0>=INSTANCEOF && LA133_0<=CURRENT_TIMESTAMP)||(LA133_0>=EVAL_AND_EXPR && LA133_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA133_0==EVENT_PROP_EXPR||(LA133_0>=CONCAT && LA133_0<=LIB_FUNCTION)||LA133_0==ARRAY_EXPR||(LA133_0>=NOT_IN_SET && LA133_0<=NOT_REGEXP)||(LA133_0>=IN_RANGE && LA133_0<=SUBSELECT_EXPR)||(LA133_0>=EXISTS_SUBSELECT_EXPR && LA133_0<=NOT_IN_SUBSELECT_EXPR)||LA133_0==SUBSTITUTION||(LA133_0>=FIRST_AGGREG && LA133_0<=LAST_AGGREG)||(LA133_0>=INT_TYPE && LA133_0<=NULL_TYPE)||(LA133_0>=STAR && LA133_0<=PLUS)||(LA133_0>=BAND && LA133_0<=BXOR)||(LA133_0>=LT && LA133_0<=GE)||(LA133_0>=MINUS && LA133_0<=MOD)) ) {
                        alt133=1;
                    }
                    else if ( (LA133_0==SUBSELECT_GROUP_EXPR) ) {
                        alt133=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 133, 0, input);

                        throw nvae;
                    }
                    switch (alt133) {
                        case 1 :
                            // EsperEPL2Ast.g:388:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:388:63: ( valueExpr )*
                            loop132:
                            do {
                                int alt132=2;
                                int LA132_0 = input.LA(1);

                                if ( ((LA132_0>=IN_SET && LA132_0<=REGEXP)||LA132_0==NOT_EXPR||(LA132_0>=SUM && LA132_0<=AVG)||(LA132_0>=COALESCE && LA132_0<=COUNT)||(LA132_0>=CASE && LA132_0<=CASE2)||(LA132_0>=PREVIOUS && LA132_0<=EXISTS)||(LA132_0>=INSTANCEOF && LA132_0<=CURRENT_TIMESTAMP)||(LA132_0>=EVAL_AND_EXPR && LA132_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA132_0==EVENT_PROP_EXPR||(LA132_0>=CONCAT && LA132_0<=LIB_FUNCTION)||LA132_0==ARRAY_EXPR||(LA132_0>=NOT_IN_SET && LA132_0<=NOT_REGEXP)||(LA132_0>=IN_RANGE && LA132_0<=SUBSELECT_EXPR)||(LA132_0>=EXISTS_SUBSELECT_EXPR && LA132_0<=NOT_IN_SUBSELECT_EXPR)||LA132_0==SUBSTITUTION||(LA132_0>=FIRST_AGGREG && LA132_0<=LAST_AGGREG)||(LA132_0>=INT_TYPE && LA132_0<=NULL_TYPE)||(LA132_0>=STAR && LA132_0<=PLUS)||(LA132_0>=BAND && LA132_0<=BXOR)||(LA132_0>=LT && LA132_0<=GE)||(LA132_0>=MINUS && LA132_0<=MOD)) ) {
                                    alt132=1;
                                }


                                switch (alt132) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:388:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2528);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop132;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:388:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2533);
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
                    // EsperEPL2Ast.g:389:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2546); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2548);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:390:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2559);
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
    // EsperEPL2Ast.g:393:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:394:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt135=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt135=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt135=2;
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
                alt135=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt135=4;
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
                alt135=5;
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
            case PRIOR:
            case EXISTS:
            case INSTANCEOF:
            case CAST:
            case CURRENT_TIMESTAMP:
            case FIRST_AGGREG:
            case LAST_AGGREG:
                {
                alt135=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt135=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt135=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt135=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt135=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt135=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt135=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt135=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt135=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt135=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt135=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 135, 0, input);

                throw nvae;
            }

            switch (alt135) {
                case 1 :
                    // EsperEPL2Ast.g:394:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2572);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:395:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2578);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:396:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2584);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:397:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2591);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:398:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2600);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:399:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2605);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:400:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2613);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:401:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2618);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:402:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2623);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:403:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2629);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:404:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2634);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:405:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2639);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:406:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2644);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:407:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2649);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:408:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2655);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:409:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2662);
                    subSelectExistsExpr();

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
    // EsperEPL2Ast.g:412:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:413:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt137=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt137=1;
                }
                break;
            case LW:
                {
                alt137=2;
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
            case SUBSTITUTION:
            case FIRST_AGGREG:
            case LAST_AGGREG:
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
                alt137=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt137=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt137=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt137=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt137=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt137=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt137=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt137=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt137=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 137, 0, input);

                throw nvae;
            }

            switch (alt137) {
                case 1 :
                    // EsperEPL2Ast.g:413:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2675); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:414:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2684); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:415:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2691);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:416:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2699); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2701);
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
                    // EsperEPL2Ast.g:417:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2716);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:418:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2722);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:419:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2727);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:420:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2732);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:421:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2742); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:421:29: ( numericParameterList )+
                    int cnt136=0;
                    loop136:
                    do {
                        int alt136=2;
                        int LA136_0 = input.LA(1);

                        if ( (LA136_0==NUMERIC_PARAM_RANGE||LA136_0==NUMERIC_PARAM_FREQUENCY||(LA136_0>=INT_TYPE && LA136_0<=NULL_TYPE)) ) {
                            alt136=1;
                        }


                        switch (alt136) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:421:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2744);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt136 >= 1 ) break loop136;
                                EarlyExitException eee =
                                    new EarlyExitException(136, input);
                                throw eee;
                        }
                        cnt136++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:422:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2755); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:423:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2762);
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
    // EsperEPL2Ast.g:426:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:427:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt138=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt138=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt138=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt138=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 138, 0, input);

                throw nvae;
            }

            switch (alt138) {
                case 1 :
                    // EsperEPL2Ast.g:427:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2775);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:428:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2782);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:429:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2788);
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
    // EsperEPL2Ast.g:432:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:433:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:433:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2804); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:433:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt139=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt139=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt139=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt139=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 139, 0, input);

                throw nvae;
            }

            switch (alt139) {
                case 1 :
                    // EsperEPL2Ast.g:433:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2807);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:433:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2810);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:433:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2813);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:433:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt140=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt140=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt140=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt140=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 140, 0, input);

                throw nvae;
            }

            switch (alt140) {
                case 1 :
                    // EsperEPL2Ast.g:433:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2817);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:433:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2820);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:433:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2823);
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
    // EsperEPL2Ast.g:436:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:437:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:437:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2844); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:437:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt141=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt141=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt141=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt141=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 141, 0, input);

                throw nvae;
            }

            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:437:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2847);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:437:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2850);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:437:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2853);
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
    // EsperEPL2Ast.g:440:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:441:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:441:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2872); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:441:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt142=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt142=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt142=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt142=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 142, 0, input);

                throw nvae;
            }

            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:441:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2875);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:441:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2878);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:441:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2881);
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
    // EsperEPL2Ast.g:444:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:445:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:445:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2900); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:445:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt143=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt143=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt143=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt143=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }

            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:445:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2903);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:445:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2906);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:445:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2909);
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
    // EsperEPL2Ast.g:448:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:449:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:449:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2930); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr2932);
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
    // EsperEPL2Ast.g:452:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:453:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:453:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2951); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2953);
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
    // EsperEPL2Ast.g:456:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:457:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:457:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2972); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2974);
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
    // EsperEPL2Ast.g:460:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:461:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( (LA144_0==IN_SUBSELECT_EXPR) ) {
                alt144=1;
            }
            else if ( (LA144_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt144=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }
            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:461:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2993); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2995);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2997);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:462:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3009); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3011);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3013);
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
    // EsperEPL2Ast.g:465:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:466:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:466:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3032); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3034);
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
    // EsperEPL2Ast.g:469:1: subQueryExpr : ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:470:2: ( ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:470:4: ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:470:4: ( DISTINCT )?
            int alt145=2;
            int LA145_0 = input.LA(1);

            if ( (LA145_0==DISTINCT) ) {
                alt145=1;
            }
            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:470:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3050); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr3053);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3055);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:470:55: ( whereClause[true] )?
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( (LA146_0==WHERE_EXPR) ) {
                alt146=1;
            }
            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:470:56: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3058);
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
    // EsperEPL2Ast.g:473:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:474:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:474:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3076); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3078);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:474:36: ( viewListExpr )?
            int alt147=2;
            int LA147_0 = input.LA(1);

            if ( (LA147_0==VIEW_EXPR) ) {
                alt147=1;
            }
            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:474:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3081);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:474:52: ( IDENT )?
            int alt148=2;
            int LA148_0 = input.LA(1);

            if ( (LA148_0==IDENT) ) {
                alt148=1;
            }
            switch (alt148) {
                case 1 :
                    // EsperEPL2Ast.g:474:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3086); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:474:61: ( RETAINUNION )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==RETAINUNION) ) {
                alt149=1;
            }
            switch (alt149) {
                case 1 :
                    // EsperEPL2Ast.g:474:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3090); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:474:74: ( RETAININTERSECTION )?
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( (LA150_0==RETAININTERSECTION) ) {
                alt150=1;
            }
            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:474:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3093); 

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
    // EsperEPL2Ast.g:477:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:478:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt153=2;
            int LA153_0 = input.LA(1);

            if ( (LA153_0==CASE) ) {
                alt153=1;
            }
            else if ( (LA153_0==CASE2) ) {
                alt153=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 153, 0, input);

                throw nvae;
            }
            switch (alt153) {
                case 1 :
                    // EsperEPL2Ast.g:478:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3113); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:478:13: ( valueExpr )*
                        loop151:
                        do {
                            int alt151=2;
                            int LA151_0 = input.LA(1);

                            if ( ((LA151_0>=IN_SET && LA151_0<=REGEXP)||LA151_0==NOT_EXPR||(LA151_0>=SUM && LA151_0<=AVG)||(LA151_0>=COALESCE && LA151_0<=COUNT)||(LA151_0>=CASE && LA151_0<=CASE2)||(LA151_0>=PREVIOUS && LA151_0<=EXISTS)||(LA151_0>=INSTANCEOF && LA151_0<=CURRENT_TIMESTAMP)||(LA151_0>=EVAL_AND_EXPR && LA151_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA151_0==EVENT_PROP_EXPR||(LA151_0>=CONCAT && LA151_0<=LIB_FUNCTION)||LA151_0==ARRAY_EXPR||(LA151_0>=NOT_IN_SET && LA151_0<=NOT_REGEXP)||(LA151_0>=IN_RANGE && LA151_0<=SUBSELECT_EXPR)||(LA151_0>=EXISTS_SUBSELECT_EXPR && LA151_0<=NOT_IN_SUBSELECT_EXPR)||LA151_0==SUBSTITUTION||(LA151_0>=FIRST_AGGREG && LA151_0<=LAST_AGGREG)||(LA151_0>=INT_TYPE && LA151_0<=NULL_TYPE)||(LA151_0>=STAR && LA151_0<=PLUS)||(LA151_0>=BAND && LA151_0<=BXOR)||(LA151_0>=LT && LA151_0<=GE)||(LA151_0>=MINUS && LA151_0<=MOD)) ) {
                                alt151=1;
                            }


                            switch (alt151) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:478:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3116);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop151;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:479:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3129); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:479:14: ( valueExpr )*
                        loop152:
                        do {
                            int alt152=2;
                            int LA152_0 = input.LA(1);

                            if ( ((LA152_0>=IN_SET && LA152_0<=REGEXP)||LA152_0==NOT_EXPR||(LA152_0>=SUM && LA152_0<=AVG)||(LA152_0>=COALESCE && LA152_0<=COUNT)||(LA152_0>=CASE && LA152_0<=CASE2)||(LA152_0>=PREVIOUS && LA152_0<=EXISTS)||(LA152_0>=INSTANCEOF && LA152_0<=CURRENT_TIMESTAMP)||(LA152_0>=EVAL_AND_EXPR && LA152_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA152_0==EVENT_PROP_EXPR||(LA152_0>=CONCAT && LA152_0<=LIB_FUNCTION)||LA152_0==ARRAY_EXPR||(LA152_0>=NOT_IN_SET && LA152_0<=NOT_REGEXP)||(LA152_0>=IN_RANGE && LA152_0<=SUBSELECT_EXPR)||(LA152_0>=EXISTS_SUBSELECT_EXPR && LA152_0<=NOT_IN_SUBSELECT_EXPR)||LA152_0==SUBSTITUTION||(LA152_0>=FIRST_AGGREG && LA152_0<=LAST_AGGREG)||(LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)||(LA152_0>=STAR && LA152_0<=PLUS)||(LA152_0>=BAND && LA152_0<=BXOR)||(LA152_0>=LT && LA152_0<=GE)||(LA152_0>=MINUS && LA152_0<=MOD)) ) {
                                alt152=1;
                            }


                            switch (alt152) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:479:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3132);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop152;
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
    // EsperEPL2Ast.g:482:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:483:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt156=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt156=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt156=2;
                }
                break;
            case IN_RANGE:
                {
                alt156=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt156=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 156, 0, input);

                throw nvae;
            }

            switch (alt156) {
                case 1 :
                    // EsperEPL2Ast.g:483:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3152); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3154);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3162);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:483:51: ( valueExpr )*
                    loop154:
                    do {
                        int alt154=2;
                        int LA154_0 = input.LA(1);

                        if ( ((LA154_0>=IN_SET && LA154_0<=REGEXP)||LA154_0==NOT_EXPR||(LA154_0>=SUM && LA154_0<=AVG)||(LA154_0>=COALESCE && LA154_0<=COUNT)||(LA154_0>=CASE && LA154_0<=CASE2)||(LA154_0>=PREVIOUS && LA154_0<=EXISTS)||(LA154_0>=INSTANCEOF && LA154_0<=CURRENT_TIMESTAMP)||(LA154_0>=EVAL_AND_EXPR && LA154_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA154_0==EVENT_PROP_EXPR||(LA154_0>=CONCAT && LA154_0<=LIB_FUNCTION)||LA154_0==ARRAY_EXPR||(LA154_0>=NOT_IN_SET && LA154_0<=NOT_REGEXP)||(LA154_0>=IN_RANGE && LA154_0<=SUBSELECT_EXPR)||(LA154_0>=EXISTS_SUBSELECT_EXPR && LA154_0<=NOT_IN_SUBSELECT_EXPR)||LA154_0==SUBSTITUTION||(LA154_0>=FIRST_AGGREG && LA154_0<=LAST_AGGREG)||(LA154_0>=INT_TYPE && LA154_0<=NULL_TYPE)||(LA154_0>=STAR && LA154_0<=PLUS)||(LA154_0>=BAND && LA154_0<=BXOR)||(LA154_0>=LT && LA154_0<=GE)||(LA154_0>=MINUS && LA154_0<=MOD)) ) {
                            alt154=1;
                        }


                        switch (alt154) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:483:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3165);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop154;
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
                    // EsperEPL2Ast.g:484:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3184); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3186);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3194);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:484:55: ( valueExpr )*
                    loop155:
                    do {
                        int alt155=2;
                        int LA155_0 = input.LA(1);

                        if ( ((LA155_0>=IN_SET && LA155_0<=REGEXP)||LA155_0==NOT_EXPR||(LA155_0>=SUM && LA155_0<=AVG)||(LA155_0>=COALESCE && LA155_0<=COUNT)||(LA155_0>=CASE && LA155_0<=CASE2)||(LA155_0>=PREVIOUS && LA155_0<=EXISTS)||(LA155_0>=INSTANCEOF && LA155_0<=CURRENT_TIMESTAMP)||(LA155_0>=EVAL_AND_EXPR && LA155_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA155_0==EVENT_PROP_EXPR||(LA155_0>=CONCAT && LA155_0<=LIB_FUNCTION)||LA155_0==ARRAY_EXPR||(LA155_0>=NOT_IN_SET && LA155_0<=NOT_REGEXP)||(LA155_0>=IN_RANGE && LA155_0<=SUBSELECT_EXPR)||(LA155_0>=EXISTS_SUBSELECT_EXPR && LA155_0<=NOT_IN_SUBSELECT_EXPR)||LA155_0==SUBSTITUTION||(LA155_0>=FIRST_AGGREG && LA155_0<=LAST_AGGREG)||(LA155_0>=INT_TYPE && LA155_0<=NULL_TYPE)||(LA155_0>=STAR && LA155_0<=PLUS)||(LA155_0>=BAND && LA155_0<=BXOR)||(LA155_0>=LT && LA155_0<=GE)||(LA155_0>=MINUS && LA155_0<=MOD)) ) {
                            alt155=1;
                        }


                        switch (alt155) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:484:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3197);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop155;
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
                    // EsperEPL2Ast.g:485:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3216); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3218);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3226);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3228);
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
                    // EsperEPL2Ast.g:486:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3245); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3247);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3255);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3257);
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
    // EsperEPL2Ast.g:489:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:490:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==BETWEEN) ) {
                alt158=1;
            }
            else if ( (LA158_0==NOT_BETWEEN) ) {
                alt158=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }
            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:490:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3282); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3284);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3286);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3288);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:491:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3299); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3301);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3303);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:491:40: ( valueExpr )*
                    loop157:
                    do {
                        int alt157=2;
                        int LA157_0 = input.LA(1);

                        if ( ((LA157_0>=IN_SET && LA157_0<=REGEXP)||LA157_0==NOT_EXPR||(LA157_0>=SUM && LA157_0<=AVG)||(LA157_0>=COALESCE && LA157_0<=COUNT)||(LA157_0>=CASE && LA157_0<=CASE2)||(LA157_0>=PREVIOUS && LA157_0<=EXISTS)||(LA157_0>=INSTANCEOF && LA157_0<=CURRENT_TIMESTAMP)||(LA157_0>=EVAL_AND_EXPR && LA157_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA157_0==EVENT_PROP_EXPR||(LA157_0>=CONCAT && LA157_0<=LIB_FUNCTION)||LA157_0==ARRAY_EXPR||(LA157_0>=NOT_IN_SET && LA157_0<=NOT_REGEXP)||(LA157_0>=IN_RANGE && LA157_0<=SUBSELECT_EXPR)||(LA157_0>=EXISTS_SUBSELECT_EXPR && LA157_0<=NOT_IN_SUBSELECT_EXPR)||LA157_0==SUBSTITUTION||(LA157_0>=FIRST_AGGREG && LA157_0<=LAST_AGGREG)||(LA157_0>=INT_TYPE && LA157_0<=NULL_TYPE)||(LA157_0>=STAR && LA157_0<=PLUS)||(LA157_0>=BAND && LA157_0<=BXOR)||(LA157_0>=LT && LA157_0<=GE)||(LA157_0>=MINUS && LA157_0<=MOD)) ) {
                            alt157=1;
                        }


                        switch (alt157) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:491:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3306);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop157;
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
    // EsperEPL2Ast.g:494:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:495:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt161=2;
            int LA161_0 = input.LA(1);

            if ( (LA161_0==LIKE) ) {
                alt161=1;
            }
            else if ( (LA161_0==NOT_LIKE) ) {
                alt161=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }
            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:495:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3326); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3328);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3330);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:495:33: ( valueExpr )?
                    int alt159=2;
                    int LA159_0 = input.LA(1);

                    if ( ((LA159_0>=IN_SET && LA159_0<=REGEXP)||LA159_0==NOT_EXPR||(LA159_0>=SUM && LA159_0<=AVG)||(LA159_0>=COALESCE && LA159_0<=COUNT)||(LA159_0>=CASE && LA159_0<=CASE2)||(LA159_0>=PREVIOUS && LA159_0<=EXISTS)||(LA159_0>=INSTANCEOF && LA159_0<=CURRENT_TIMESTAMP)||(LA159_0>=EVAL_AND_EXPR && LA159_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA159_0==EVENT_PROP_EXPR||(LA159_0>=CONCAT && LA159_0<=LIB_FUNCTION)||LA159_0==ARRAY_EXPR||(LA159_0>=NOT_IN_SET && LA159_0<=NOT_REGEXP)||(LA159_0>=IN_RANGE && LA159_0<=SUBSELECT_EXPR)||(LA159_0>=EXISTS_SUBSELECT_EXPR && LA159_0<=NOT_IN_SUBSELECT_EXPR)||LA159_0==SUBSTITUTION||(LA159_0>=FIRST_AGGREG && LA159_0<=LAST_AGGREG)||(LA159_0>=INT_TYPE && LA159_0<=NULL_TYPE)||(LA159_0>=STAR && LA159_0<=PLUS)||(LA159_0>=BAND && LA159_0<=BXOR)||(LA159_0>=LT && LA159_0<=GE)||(LA159_0>=MINUS && LA159_0<=MOD)) ) {
                        alt159=1;
                    }
                    switch (alt159) {
                        case 1 :
                            // EsperEPL2Ast.g:495:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3333);
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
                    // EsperEPL2Ast.g:496:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3346); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3348);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3350);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:496:37: ( valueExpr )?
                    int alt160=2;
                    int LA160_0 = input.LA(1);

                    if ( ((LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||(LA160_0>=CONCAT && LA160_0<=LIB_FUNCTION)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=FIRST_AGGREG && LA160_0<=LAST_AGGREG)||(LA160_0>=INT_TYPE && LA160_0<=NULL_TYPE)||(LA160_0>=STAR && LA160_0<=PLUS)||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=MINUS && LA160_0<=MOD)) ) {
                        alt160=1;
                    }
                    switch (alt160) {
                        case 1 :
                            // EsperEPL2Ast.g:496:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3353);
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
    // EsperEPL2Ast.g:499:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:500:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==REGEXP) ) {
                alt162=1;
            }
            else if ( (LA162_0==NOT_REGEXP) ) {
                alt162=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }
            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:500:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3372); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3374);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3376);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:501:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3387); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3389);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3391);
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
    // EsperEPL2Ast.g:504:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:505:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt175=15;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt175=1;
                }
                break;
            case AVG:
                {
                alt175=2;
                }
                break;
            case COUNT:
                {
                alt175=3;
                }
                break;
            case MEDIAN:
                {
                alt175=4;
                }
                break;
            case STDDEV:
                {
                alt175=5;
                }
                break;
            case AVEDEV:
                {
                alt175=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt175=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt175=8;
                }
                break;
            case COALESCE:
                {
                alt175=9;
                }
                break;
            case PREVIOUS:
                {
                alt175=10;
                }
                break;
            case PRIOR:
                {
                alt175=11;
                }
                break;
            case INSTANCEOF:
                {
                alt175=12;
                }
                break;
            case CAST:
                {
                alt175=13;
                }
                break;
            case EXISTS:
                {
                alt175=14;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt175=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                throw nvae;
            }

            switch (alt175) {
                case 1 :
                    // EsperEPL2Ast.g:505:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3410); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:505:13: ( DISTINCT )?
                    int alt163=2;
                    int LA163_0 = input.LA(1);

                    if ( (LA163_0==DISTINCT) ) {
                        alt163=1;
                    }
                    switch (alt163) {
                        case 1 :
                            // EsperEPL2Ast.g:505:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3413); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3417);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:506:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3428); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:506:12: ( DISTINCT )?
                    int alt164=2;
                    int LA164_0 = input.LA(1);

                    if ( (LA164_0==DISTINCT) ) {
                        alt164=1;
                    }
                    switch (alt164) {
                        case 1 :
                            // EsperEPL2Ast.g:506:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3431); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3435);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:507:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3446); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:507:14: ( ( DISTINCT )? valueExpr )?
                        int alt166=2;
                        int LA166_0 = input.LA(1);

                        if ( ((LA166_0>=IN_SET && LA166_0<=REGEXP)||LA166_0==NOT_EXPR||(LA166_0>=SUM && LA166_0<=AVG)||(LA166_0>=COALESCE && LA166_0<=COUNT)||(LA166_0>=CASE && LA166_0<=CASE2)||LA166_0==DISTINCT||(LA166_0>=PREVIOUS && LA166_0<=EXISTS)||(LA166_0>=INSTANCEOF && LA166_0<=CURRENT_TIMESTAMP)||(LA166_0>=EVAL_AND_EXPR && LA166_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA166_0==EVENT_PROP_EXPR||(LA166_0>=CONCAT && LA166_0<=LIB_FUNCTION)||LA166_0==ARRAY_EXPR||(LA166_0>=NOT_IN_SET && LA166_0<=NOT_REGEXP)||(LA166_0>=IN_RANGE && LA166_0<=SUBSELECT_EXPR)||(LA166_0>=EXISTS_SUBSELECT_EXPR && LA166_0<=NOT_IN_SUBSELECT_EXPR)||LA166_0==SUBSTITUTION||(LA166_0>=FIRST_AGGREG && LA166_0<=LAST_AGGREG)||(LA166_0>=INT_TYPE && LA166_0<=NULL_TYPE)||(LA166_0>=STAR && LA166_0<=PLUS)||(LA166_0>=BAND && LA166_0<=BXOR)||(LA166_0>=LT && LA166_0<=GE)||(LA166_0>=MINUS && LA166_0<=MOD)) ) {
                            alt166=1;
                        }
                        switch (alt166) {
                            case 1 :
                                // EsperEPL2Ast.g:507:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:507:15: ( DISTINCT )?
                                int alt165=2;
                                int LA165_0 = input.LA(1);

                                if ( (LA165_0==DISTINCT) ) {
                                    alt165=1;
                                }
                                switch (alt165) {
                                    case 1 :
                                        // EsperEPL2Ast.g:507:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3450); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3454);
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
                    // EsperEPL2Ast.g:508:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3468); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:508:15: ( DISTINCT )?
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( (LA167_0==DISTINCT) ) {
                        alt167=1;
                    }
                    switch (alt167) {
                        case 1 :
                            // EsperEPL2Ast.g:508:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3471); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3475);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:509:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3486); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:509:15: ( DISTINCT )?
                    int alt168=2;
                    int LA168_0 = input.LA(1);

                    if ( (LA168_0==DISTINCT) ) {
                        alt168=1;
                    }
                    switch (alt168) {
                        case 1 :
                            // EsperEPL2Ast.g:509:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3489); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3493);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:510:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3504); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:510:15: ( DISTINCT )?
                    int alt169=2;
                    int LA169_0 = input.LA(1);

                    if ( (LA169_0==DISTINCT) ) {
                        alt169=1;
                    }
                    switch (alt169) {
                        case 1 :
                            // EsperEPL2Ast.g:510:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3507); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3511);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:511:4: ^(f= LAST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3522); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:511:20: ( DISTINCT )?
                    int alt170=2;
                    int LA170_0 = input.LA(1);

                    if ( (LA170_0==DISTINCT) ) {
                        alt170=1;
                    }
                    switch (alt170) {
                        case 1 :
                            // EsperEPL2Ast.g:511:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3525); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3529);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:512:4: ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3540); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:512:21: ( DISTINCT )?
                    int alt171=2;
                    int LA171_0 = input.LA(1);

                    if ( (LA171_0==DISTINCT) ) {
                        alt171=1;
                    }
                    switch (alt171) {
                        case 1 :
                            // EsperEPL2Ast.g:512:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3543); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3547);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:513:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3559); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3561);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3563);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:513:38: ( valueExpr )*
                    loop172:
                    do {
                        int alt172=2;
                        int LA172_0 = input.LA(1);

                        if ( ((LA172_0>=IN_SET && LA172_0<=REGEXP)||LA172_0==NOT_EXPR||(LA172_0>=SUM && LA172_0<=AVG)||(LA172_0>=COALESCE && LA172_0<=COUNT)||(LA172_0>=CASE && LA172_0<=CASE2)||(LA172_0>=PREVIOUS && LA172_0<=EXISTS)||(LA172_0>=INSTANCEOF && LA172_0<=CURRENT_TIMESTAMP)||(LA172_0>=EVAL_AND_EXPR && LA172_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA172_0==EVENT_PROP_EXPR||(LA172_0>=CONCAT && LA172_0<=LIB_FUNCTION)||LA172_0==ARRAY_EXPR||(LA172_0>=NOT_IN_SET && LA172_0<=NOT_REGEXP)||(LA172_0>=IN_RANGE && LA172_0<=SUBSELECT_EXPR)||(LA172_0>=EXISTS_SUBSELECT_EXPR && LA172_0<=NOT_IN_SUBSELECT_EXPR)||LA172_0==SUBSTITUTION||(LA172_0>=FIRST_AGGREG && LA172_0<=LAST_AGGREG)||(LA172_0>=INT_TYPE && LA172_0<=NULL_TYPE)||(LA172_0>=STAR && LA172_0<=PLUS)||(LA172_0>=BAND && LA172_0<=BXOR)||(LA172_0>=LT && LA172_0<=GE)||(LA172_0>=MINUS && LA172_0<=MOD)) ) {
                            alt172=1;
                        }


                        switch (alt172) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:513:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3566);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop172;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:514:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3581); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3583);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:514:28: ( valueExpr )?
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( ((LA173_0>=IN_SET && LA173_0<=REGEXP)||LA173_0==NOT_EXPR||(LA173_0>=SUM && LA173_0<=AVG)||(LA173_0>=COALESCE && LA173_0<=COUNT)||(LA173_0>=CASE && LA173_0<=CASE2)||(LA173_0>=PREVIOUS && LA173_0<=EXISTS)||(LA173_0>=INSTANCEOF && LA173_0<=CURRENT_TIMESTAMP)||(LA173_0>=EVAL_AND_EXPR && LA173_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA173_0==EVENT_PROP_EXPR||(LA173_0>=CONCAT && LA173_0<=LIB_FUNCTION)||LA173_0==ARRAY_EXPR||(LA173_0>=NOT_IN_SET && LA173_0<=NOT_REGEXP)||(LA173_0>=IN_RANGE && LA173_0<=SUBSELECT_EXPR)||(LA173_0>=EXISTS_SUBSELECT_EXPR && LA173_0<=NOT_IN_SUBSELECT_EXPR)||LA173_0==SUBSTITUTION||(LA173_0>=FIRST_AGGREG && LA173_0<=LAST_AGGREG)||(LA173_0>=INT_TYPE && LA173_0<=NULL_TYPE)||(LA173_0>=STAR && LA173_0<=PLUS)||(LA173_0>=BAND && LA173_0<=BXOR)||(LA173_0>=LT && LA173_0<=GE)||(LA173_0>=MINUS && LA173_0<=MOD)) ) {
                        alt173=1;
                    }
                    switch (alt173) {
                        case 1 :
                            // EsperEPL2Ast.g:514:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3585);
                            valueExpr();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:515:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3598); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3602); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3604);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:516:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3617); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3619);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3621); 
                    // EsperEPL2Ast.g:516:42: ( CLASS_IDENT )*
                    loop174:
                    do {
                        int alt174=2;
                        int LA174_0 = input.LA(1);

                        if ( (LA174_0==CLASS_IDENT) ) {
                            alt174=1;
                        }


                        switch (alt174) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:516:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3624); 

                    	    }
                    	    break;

                    	default :
                    	    break loop174;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:517:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3638); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3640);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3642); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:518:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3654); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3656);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:519:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3668); 



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


    // $ANTLR start "arrayExpr"
    // EsperEPL2Ast.g:522:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:523:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:523:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3688); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:523:19: ( valueExpr )*
                loop176:
                do {
                    int alt176=2;
                    int LA176_0 = input.LA(1);

                    if ( ((LA176_0>=IN_SET && LA176_0<=REGEXP)||LA176_0==NOT_EXPR||(LA176_0>=SUM && LA176_0<=AVG)||(LA176_0>=COALESCE && LA176_0<=COUNT)||(LA176_0>=CASE && LA176_0<=CASE2)||(LA176_0>=PREVIOUS && LA176_0<=EXISTS)||(LA176_0>=INSTANCEOF && LA176_0<=CURRENT_TIMESTAMP)||(LA176_0>=EVAL_AND_EXPR && LA176_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA176_0==EVENT_PROP_EXPR||(LA176_0>=CONCAT && LA176_0<=LIB_FUNCTION)||LA176_0==ARRAY_EXPR||(LA176_0>=NOT_IN_SET && LA176_0<=NOT_REGEXP)||(LA176_0>=IN_RANGE && LA176_0<=SUBSELECT_EXPR)||(LA176_0>=EXISTS_SUBSELECT_EXPR && LA176_0<=NOT_IN_SUBSELECT_EXPR)||LA176_0==SUBSTITUTION||(LA176_0>=FIRST_AGGREG && LA176_0<=LAST_AGGREG)||(LA176_0>=INT_TYPE && LA176_0<=NULL_TYPE)||(LA176_0>=STAR && LA176_0<=PLUS)||(LA176_0>=BAND && LA176_0<=BXOR)||(LA176_0>=LT && LA176_0<=GE)||(LA176_0>=MINUS && LA176_0<=MOD)) ) {
                        alt176=1;
                    }


                    switch (alt176) {
                	case 1 :
                	    // EsperEPL2Ast.g:523:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3691);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop176;
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
    // EsperEPL2Ast.g:526:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:527:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt178=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt178=1;
                }
                break;
            case MINUS:
                {
                alt178=2;
                }
                break;
            case DIV:
                {
                alt178=3;
                }
                break;
            case STAR:
                {
                alt178=4;
                }
                break;
            case MOD:
                {
                alt178=5;
                }
                break;
            case BAND:
                {
                alt178=6;
                }
                break;
            case BOR:
                {
                alt178=7;
                }
                break;
            case BXOR:
                {
                alt178=8;
                }
                break;
            case CONCAT:
                {
                alt178=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 178, 0, input);

                throw nvae;
            }

            switch (alt178) {
                case 1 :
                    // EsperEPL2Ast.g:527:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3712); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3714);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3716);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:528:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3728); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3730);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3732);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:529:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3744); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3746);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3748);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:530:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3759); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3761);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3763);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:531:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3775); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3777);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3779);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:532:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3790); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3792);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3794);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:533:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3805); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3807);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3809);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:534:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3820); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3822);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3824);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:535:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3836); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3838);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3840);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:535:36: ( valueExpr )*
                    loop177:
                    do {
                        int alt177=2;
                        int LA177_0 = input.LA(1);

                        if ( ((LA177_0>=IN_SET && LA177_0<=REGEXP)||LA177_0==NOT_EXPR||(LA177_0>=SUM && LA177_0<=AVG)||(LA177_0>=COALESCE && LA177_0<=COUNT)||(LA177_0>=CASE && LA177_0<=CASE2)||(LA177_0>=PREVIOUS && LA177_0<=EXISTS)||(LA177_0>=INSTANCEOF && LA177_0<=CURRENT_TIMESTAMP)||(LA177_0>=EVAL_AND_EXPR && LA177_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA177_0==EVENT_PROP_EXPR||(LA177_0>=CONCAT && LA177_0<=LIB_FUNCTION)||LA177_0==ARRAY_EXPR||(LA177_0>=NOT_IN_SET && LA177_0<=NOT_REGEXP)||(LA177_0>=IN_RANGE && LA177_0<=SUBSELECT_EXPR)||(LA177_0>=EXISTS_SUBSELECT_EXPR && LA177_0<=NOT_IN_SUBSELECT_EXPR)||LA177_0==SUBSTITUTION||(LA177_0>=FIRST_AGGREG && LA177_0<=LAST_AGGREG)||(LA177_0>=INT_TYPE && LA177_0<=NULL_TYPE)||(LA177_0>=STAR && LA177_0<=PLUS)||(LA177_0>=BAND && LA177_0<=BXOR)||(LA177_0>=LT && LA177_0<=GE)||(LA177_0>=MINUS && LA177_0<=MOD)) ) {
                            alt177=1;
                        }


                        switch (alt177) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:535:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3843);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop177;
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


    // $ANTLR start "libFunc"
    // EsperEPL2Ast.g:538:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:539:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:539:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3864); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:539:22: ( CLASS_IDENT )?
            int alt179=2;
            int LA179_0 = input.LA(1);

            if ( (LA179_0==CLASS_IDENT) ) {
                alt179=1;
            }
            switch (alt179) {
                case 1 :
                    // EsperEPL2Ast.g:539:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3867); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3871); 
            // EsperEPL2Ast.g:539:43: ( DISTINCT )?
            int alt180=2;
            int LA180_0 = input.LA(1);

            if ( (LA180_0==DISTINCT) ) {
                alt180=1;
            }
            switch (alt180) {
                case 1 :
                    // EsperEPL2Ast.g:539:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3874); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:539:55: ( valueExpr )*
            loop181:
            do {
                int alt181=2;
                int LA181_0 = input.LA(1);

                if ( ((LA181_0>=IN_SET && LA181_0<=REGEXP)||LA181_0==NOT_EXPR||(LA181_0>=SUM && LA181_0<=AVG)||(LA181_0>=COALESCE && LA181_0<=COUNT)||(LA181_0>=CASE && LA181_0<=CASE2)||(LA181_0>=PREVIOUS && LA181_0<=EXISTS)||(LA181_0>=INSTANCEOF && LA181_0<=CURRENT_TIMESTAMP)||(LA181_0>=EVAL_AND_EXPR && LA181_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA181_0==EVENT_PROP_EXPR||(LA181_0>=CONCAT && LA181_0<=LIB_FUNCTION)||LA181_0==ARRAY_EXPR||(LA181_0>=NOT_IN_SET && LA181_0<=NOT_REGEXP)||(LA181_0>=IN_RANGE && LA181_0<=SUBSELECT_EXPR)||(LA181_0>=EXISTS_SUBSELECT_EXPR && LA181_0<=NOT_IN_SUBSELECT_EXPR)||LA181_0==SUBSTITUTION||(LA181_0>=FIRST_AGGREG && LA181_0<=LAST_AGGREG)||(LA181_0>=INT_TYPE && LA181_0<=NULL_TYPE)||(LA181_0>=STAR && LA181_0<=PLUS)||(LA181_0>=BAND && LA181_0<=BXOR)||(LA181_0>=LT && LA181_0<=GE)||(LA181_0>=MINUS && LA181_0<=MOD)) ) {
                    alt181=1;
                }


                switch (alt181) {
            	case 1 :
            	    // EsperEPL2Ast.g:539:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3879);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop181;
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
    // $ANTLR end "libFunc"


    // $ANTLR start "startPatternExpressionRule"
    // EsperEPL2Ast.g:545:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:546:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:546:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:546:4: ( annotation[true] )*
            loop182:
            do {
                int alt182=2;
                int LA182_0 = input.LA(1);

                if ( (LA182_0==ANNOTATION) ) {
                    alt182=1;
                }


                switch (alt182) {
            	case 1 :
            	    // EsperEPL2Ast.g:546:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule3899);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop182;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3903);
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
    // EsperEPL2Ast.g:549:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:550:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt186=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt186=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt186=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt186=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt186=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt186=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt186=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt186=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 186, 0, input);

                throw nvae;
            }

            switch (alt186) {
                case 1 :
                    // EsperEPL2Ast.g:550:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3917);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:551:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3922);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:552:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3932); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3934);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:553:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3948); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice3950);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3952);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:554:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3966); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3968);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:555:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3982); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3984);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3986); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3988); 
                    // EsperEPL2Ast.g:555:44: ( valueExprWithTime )*
                    loop183:
                    do {
                        int alt183=2;
                        int LA183_0 = input.LA(1);

                        if ( ((LA183_0>=IN_SET && LA183_0<=REGEXP)||LA183_0==NOT_EXPR||(LA183_0>=SUM && LA183_0<=AVG)||(LA183_0>=COALESCE && LA183_0<=COUNT)||(LA183_0>=CASE && LA183_0<=CASE2)||LA183_0==LAST||(LA183_0>=PREVIOUS && LA183_0<=EXISTS)||(LA183_0>=LW && LA183_0<=CURRENT_TIMESTAMP)||(LA183_0>=NUMERIC_PARAM_RANGE && LA183_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA183_0>=EVAL_AND_EXPR && LA183_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA183_0==EVENT_PROP_EXPR||(LA183_0>=CONCAT && LA183_0<=LIB_FUNCTION)||(LA183_0>=TIME_PERIOD && LA183_0<=ARRAY_EXPR)||(LA183_0>=NOT_IN_SET && LA183_0<=NOT_REGEXP)||(LA183_0>=IN_RANGE && LA183_0<=SUBSELECT_EXPR)||(LA183_0>=EXISTS_SUBSELECT_EXPR && LA183_0<=NOT_IN_SUBSELECT_EXPR)||(LA183_0>=LAST_OPERATOR && LA183_0<=SUBSTITUTION)||LA183_0==NUMBERSETSTAR||(LA183_0>=FIRST_AGGREG && LA183_0<=LAST_AGGREG)||(LA183_0>=INT_TYPE && LA183_0<=NULL_TYPE)||(LA183_0>=STAR && LA183_0<=PLUS)||(LA183_0>=BAND && LA183_0<=BXOR)||(LA183_0>=LT && LA183_0<=GE)||(LA183_0>=MINUS && LA183_0<=MOD)) ) {
                            alt183=1;
                        }


                        switch (alt183) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:555:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3990);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop183;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:556:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4004); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:556:26: ( matchUntilRange )?
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( ((LA184_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA184_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt184=1;
                    }
                    switch (alt184) {
                        case 1 :
                            // EsperEPL2Ast.g:556:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4006);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4009);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:556:54: ( exprChoice )?
                    int alt185=2;
                    int LA185_0 = input.LA(1);

                    if ( ((LA185_0>=OR_EXPR && LA185_0<=AND_EXPR)||(LA185_0>=EVERY_EXPR && LA185_0<=EVERY_DISTINCT_EXPR)||LA185_0==FOLLOWED_BY_EXPR||(LA185_0>=PATTERN_FILTER_EXPR && LA185_0<=PATTERN_NOT_EXPR)||(LA185_0>=GUARD_EXPR && LA185_0<=OBSERVER_EXPR)||LA185_0==MATCH_UNTIL_EXPR) ) {
                        alt185=1;
                    }
                    switch (alt185) {
                        case 1 :
                            // EsperEPL2Ast.g:556:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4011);
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
    // EsperEPL2Ast.g:560:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:561:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:561:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4032); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:561:35: ( valueExpr )+
            int cnt187=0;
            loop187:
            do {
                int alt187=2;
                int LA187_0 = input.LA(1);

                if ( ((LA187_0>=IN_SET && LA187_0<=REGEXP)||LA187_0==NOT_EXPR||(LA187_0>=SUM && LA187_0<=AVG)||(LA187_0>=COALESCE && LA187_0<=COUNT)||(LA187_0>=CASE && LA187_0<=CASE2)||(LA187_0>=PREVIOUS && LA187_0<=EXISTS)||(LA187_0>=INSTANCEOF && LA187_0<=CURRENT_TIMESTAMP)||(LA187_0>=EVAL_AND_EXPR && LA187_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA187_0==EVENT_PROP_EXPR||(LA187_0>=CONCAT && LA187_0<=LIB_FUNCTION)||LA187_0==ARRAY_EXPR||(LA187_0>=NOT_IN_SET && LA187_0<=NOT_REGEXP)||(LA187_0>=IN_RANGE && LA187_0<=SUBSELECT_EXPR)||(LA187_0>=EXISTS_SUBSELECT_EXPR && LA187_0<=NOT_IN_SUBSELECT_EXPR)||LA187_0==SUBSTITUTION||(LA187_0>=FIRST_AGGREG && LA187_0<=LAST_AGGREG)||(LA187_0>=INT_TYPE && LA187_0<=NULL_TYPE)||(LA187_0>=STAR && LA187_0<=PLUS)||(LA187_0>=BAND && LA187_0<=BXOR)||(LA187_0>=LT && LA187_0<=GE)||(LA187_0>=MINUS && LA187_0<=MOD)) ) {
                    alt187=1;
                }


                switch (alt187) {
            	case 1 :
            	    // EsperEPL2Ast.g:561:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions4034);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt187 >= 1 ) break loop187;
                        EarlyExitException eee =
                            new EarlyExitException(187, input);
                        throw eee;
                }
                cnt187++;
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
    // EsperEPL2Ast.g:564:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:565:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt191=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt191=1;
                }
                break;
            case OR_EXPR:
                {
                alt191=2;
                }
                break;
            case AND_EXPR:
                {
                alt191=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 191, 0, input);

                throw nvae;
            }

            switch (alt191) {
                case 1 :
                    // EsperEPL2Ast.g:565:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4053); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4055);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4057);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:565:48: ( exprChoice )*
                    loop188:
                    do {
                        int alt188=2;
                        int LA188_0 = input.LA(1);

                        if ( ((LA188_0>=OR_EXPR && LA188_0<=AND_EXPR)||(LA188_0>=EVERY_EXPR && LA188_0<=EVERY_DISTINCT_EXPR)||LA188_0==FOLLOWED_BY_EXPR||(LA188_0>=PATTERN_FILTER_EXPR && LA188_0<=PATTERN_NOT_EXPR)||(LA188_0>=GUARD_EXPR && LA188_0<=OBSERVER_EXPR)||LA188_0==MATCH_UNTIL_EXPR) ) {
                            alt188=1;
                        }


                        switch (alt188) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:565:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4060);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop188;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:566:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4076); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4078);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4080);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:566:40: ( exprChoice )*
                    loop189:
                    do {
                        int alt189=2;
                        int LA189_0 = input.LA(1);

                        if ( ((LA189_0>=OR_EXPR && LA189_0<=AND_EXPR)||(LA189_0>=EVERY_EXPR && LA189_0<=EVERY_DISTINCT_EXPR)||LA189_0==FOLLOWED_BY_EXPR||(LA189_0>=PATTERN_FILTER_EXPR && LA189_0<=PATTERN_NOT_EXPR)||(LA189_0>=GUARD_EXPR && LA189_0<=OBSERVER_EXPR)||LA189_0==MATCH_UNTIL_EXPR) ) {
                            alt189=1;
                        }


                        switch (alt189) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:566:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4083);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop189;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:567:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4099); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4101);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4103);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:567:41: ( exprChoice )*
                    loop190:
                    do {
                        int alt190=2;
                        int LA190_0 = input.LA(1);

                        if ( ((LA190_0>=OR_EXPR && LA190_0<=AND_EXPR)||(LA190_0>=EVERY_EXPR && LA190_0<=EVERY_DISTINCT_EXPR)||LA190_0==FOLLOWED_BY_EXPR||(LA190_0>=PATTERN_FILTER_EXPR && LA190_0<=PATTERN_NOT_EXPR)||(LA190_0>=GUARD_EXPR && LA190_0<=OBSERVER_EXPR)||LA190_0==MATCH_UNTIL_EXPR) ) {
                            alt190=1;
                        }


                        switch (alt190) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:567:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4106);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop190;
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
    // EsperEPL2Ast.g:570:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:571:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt193=2;
            int LA193_0 = input.LA(1);

            if ( (LA193_0==PATTERN_FILTER_EXPR) ) {
                alt193=1;
            }
            else if ( (LA193_0==OBSERVER_EXPR) ) {
                alt193=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 193, 0, input);

                throw nvae;
            }
            switch (alt193) {
                case 1 :
                    // EsperEPL2Ast.g:571:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4125);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:572:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4137); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4139); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4141); 
                    // EsperEPL2Ast.g:572:39: ( valueExprWithTime )*
                    loop192:
                    do {
                        int alt192=2;
                        int LA192_0 = input.LA(1);

                        if ( ((LA192_0>=IN_SET && LA192_0<=REGEXP)||LA192_0==NOT_EXPR||(LA192_0>=SUM && LA192_0<=AVG)||(LA192_0>=COALESCE && LA192_0<=COUNT)||(LA192_0>=CASE && LA192_0<=CASE2)||LA192_0==LAST||(LA192_0>=PREVIOUS && LA192_0<=EXISTS)||(LA192_0>=LW && LA192_0<=CURRENT_TIMESTAMP)||(LA192_0>=NUMERIC_PARAM_RANGE && LA192_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA192_0>=EVAL_AND_EXPR && LA192_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA192_0==EVENT_PROP_EXPR||(LA192_0>=CONCAT && LA192_0<=LIB_FUNCTION)||(LA192_0>=TIME_PERIOD && LA192_0<=ARRAY_EXPR)||(LA192_0>=NOT_IN_SET && LA192_0<=NOT_REGEXP)||(LA192_0>=IN_RANGE && LA192_0<=SUBSELECT_EXPR)||(LA192_0>=EXISTS_SUBSELECT_EXPR && LA192_0<=NOT_IN_SUBSELECT_EXPR)||(LA192_0>=LAST_OPERATOR && LA192_0<=SUBSTITUTION)||LA192_0==NUMBERSETSTAR||(LA192_0>=FIRST_AGGREG && LA192_0<=LAST_AGGREG)||(LA192_0>=INT_TYPE && LA192_0<=NULL_TYPE)||(LA192_0>=STAR && LA192_0<=PLUS)||(LA192_0>=BAND && LA192_0<=BXOR)||(LA192_0>=LT && LA192_0<=GE)||(LA192_0>=MINUS && LA192_0<=MOD)) ) {
                            alt192=1;
                        }


                        switch (alt192) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:572:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4143);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop192;
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
    // EsperEPL2Ast.g:575:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:576:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:576:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4163); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:576:29: ( IDENT )?
            int alt194=2;
            int LA194_0 = input.LA(1);

            if ( (LA194_0==IDENT) ) {
                alt194=1;
            }
            switch (alt194) {
                case 1 :
                    // EsperEPL2Ast.g:576:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4165); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4168); 
            // EsperEPL2Ast.g:576:48: ( propertyExpression )?
            int alt195=2;
            int LA195_0 = input.LA(1);

            if ( (LA195_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt195=1;
            }
            switch (alt195) {
                case 1 :
                    // EsperEPL2Ast.g:576:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4170);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:576:68: ( valueExpr )*
            loop196:
            do {
                int alt196=2;
                int LA196_0 = input.LA(1);

                if ( ((LA196_0>=IN_SET && LA196_0<=REGEXP)||LA196_0==NOT_EXPR||(LA196_0>=SUM && LA196_0<=AVG)||(LA196_0>=COALESCE && LA196_0<=COUNT)||(LA196_0>=CASE && LA196_0<=CASE2)||(LA196_0>=PREVIOUS && LA196_0<=EXISTS)||(LA196_0>=INSTANCEOF && LA196_0<=CURRENT_TIMESTAMP)||(LA196_0>=EVAL_AND_EXPR && LA196_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA196_0==EVENT_PROP_EXPR||(LA196_0>=CONCAT && LA196_0<=LIB_FUNCTION)||LA196_0==ARRAY_EXPR||(LA196_0>=NOT_IN_SET && LA196_0<=NOT_REGEXP)||(LA196_0>=IN_RANGE && LA196_0<=SUBSELECT_EXPR)||(LA196_0>=EXISTS_SUBSELECT_EXPR && LA196_0<=NOT_IN_SUBSELECT_EXPR)||LA196_0==SUBSTITUTION||(LA196_0>=FIRST_AGGREG && LA196_0<=LAST_AGGREG)||(LA196_0>=INT_TYPE && LA196_0<=NULL_TYPE)||(LA196_0>=STAR && LA196_0<=PLUS)||(LA196_0>=BAND && LA196_0<=BXOR)||(LA196_0>=LT && LA196_0<=GE)||(LA196_0>=MINUS && LA196_0<=MOD)) ) {
                    alt196=1;
                }


                switch (alt196) {
            	case 1 :
            	    // EsperEPL2Ast.g:576:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4174);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop196;
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
    // EsperEPL2Ast.g:579:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:580:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt197=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt197=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt197=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt197=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt197=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 197, 0, input);

                throw nvae;
            }

            switch (alt197) {
                case 1 :
                    // EsperEPL2Ast.g:580:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4192); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4194);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4196);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:581:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4204); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4206);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:582:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4214); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4216);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:583:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4223); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4225);
                    matchUntilRangeParam();

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


    // $ANTLR start "matchUntilRangeParam"
    // EsperEPL2Ast.g:586:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:587:2: ( NUM_DOUBLE | NUM_INT )
            // EsperEPL2Ast.g:
            {
            if ( input.LA(1)==NUM_DOUBLE||input.LA(1)==NUM_INT ) {
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
    // $ANTLR end "matchUntilRangeParam"


    // $ANTLR start "filterParam"
    // EsperEPL2Ast.g:591:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:592:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:592:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4254); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4256);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:592:35: ( valueExpr )*
            loop198:
            do {
                int alt198=2;
                int LA198_0 = input.LA(1);

                if ( ((LA198_0>=IN_SET && LA198_0<=REGEXP)||LA198_0==NOT_EXPR||(LA198_0>=SUM && LA198_0<=AVG)||(LA198_0>=COALESCE && LA198_0<=COUNT)||(LA198_0>=CASE && LA198_0<=CASE2)||(LA198_0>=PREVIOUS && LA198_0<=EXISTS)||(LA198_0>=INSTANCEOF && LA198_0<=CURRENT_TIMESTAMP)||(LA198_0>=EVAL_AND_EXPR && LA198_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA198_0==EVENT_PROP_EXPR||(LA198_0>=CONCAT && LA198_0<=LIB_FUNCTION)||LA198_0==ARRAY_EXPR||(LA198_0>=NOT_IN_SET && LA198_0<=NOT_REGEXP)||(LA198_0>=IN_RANGE && LA198_0<=SUBSELECT_EXPR)||(LA198_0>=EXISTS_SUBSELECT_EXPR && LA198_0<=NOT_IN_SUBSELECT_EXPR)||LA198_0==SUBSTITUTION||(LA198_0>=FIRST_AGGREG && LA198_0<=LAST_AGGREG)||(LA198_0>=INT_TYPE && LA198_0<=NULL_TYPE)||(LA198_0>=STAR && LA198_0<=PLUS)||(LA198_0>=BAND && LA198_0<=BXOR)||(LA198_0>=LT && LA198_0<=GE)||(LA198_0>=MINUS && LA198_0<=MOD)) ) {
                    alt198=1;
                }


                switch (alt198) {
            	case 1 :
            	    // EsperEPL2Ast.g:592:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4259);
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
    // EsperEPL2Ast.g:595:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:596:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt211=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt211=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt211=2;
                }
                break;
            case LT:
                {
                alt211=3;
                }
                break;
            case LE:
                {
                alt211=4;
                }
                break;
            case GT:
                {
                alt211=5;
                }
                break;
            case GE:
                {
                alt211=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt211=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt211=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt211=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt211=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt211=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt211=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 211, 0, input);

                throw nvae;
            }

            switch (alt211) {
                case 1 :
                    // EsperEPL2Ast.g:596:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4275); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4277);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:597:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4284); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4286);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:598:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4293); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4295);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:599:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4302); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4304);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:600:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4311); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4313);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:601:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4320); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4322);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:602:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4329); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:602:41: ( constant[false] | filterIdentifier )
                    int alt199=2;
                    int LA199_0 = input.LA(1);

                    if ( ((LA199_0>=INT_TYPE && LA199_0<=NULL_TYPE)) ) {
                        alt199=1;
                    }
                    else if ( (LA199_0==EVENT_FILTER_IDENT) ) {
                        alt199=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 199, 0, input);

                        throw nvae;
                    }
                    switch (alt199) {
                        case 1 :
                            // EsperEPL2Ast.g:602:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4338);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:602:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4341);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:602:76: ( constant[false] | filterIdentifier )
                    int alt200=2;
                    int LA200_0 = input.LA(1);

                    if ( ((LA200_0>=INT_TYPE && LA200_0<=NULL_TYPE)) ) {
                        alt200=1;
                    }
                    else if ( (LA200_0==EVENT_FILTER_IDENT) ) {
                        alt200=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 200, 0, input);

                        throw nvae;
                    }
                    switch (alt200) {
                        case 1 :
                            // EsperEPL2Ast.g:602:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4345);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:602:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4348);
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
                    // EsperEPL2Ast.g:603:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4362); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:603:45: ( constant[false] | filterIdentifier )
                    int alt201=2;
                    int LA201_0 = input.LA(1);

                    if ( ((LA201_0>=INT_TYPE && LA201_0<=NULL_TYPE)) ) {
                        alt201=1;
                    }
                    else if ( (LA201_0==EVENT_FILTER_IDENT) ) {
                        alt201=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 201, 0, input);

                        throw nvae;
                    }
                    switch (alt201) {
                        case 1 :
                            // EsperEPL2Ast.g:603:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4371);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:603:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4374);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:603:80: ( constant[false] | filterIdentifier )
                    int alt202=2;
                    int LA202_0 = input.LA(1);

                    if ( ((LA202_0>=INT_TYPE && LA202_0<=NULL_TYPE)) ) {
                        alt202=1;
                    }
                    else if ( (LA202_0==EVENT_FILTER_IDENT) ) {
                        alt202=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 202, 0, input);

                        throw nvae;
                    }
                    switch (alt202) {
                        case 1 :
                            // EsperEPL2Ast.g:603:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4378);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:603:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4381);
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
                    // EsperEPL2Ast.g:604:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4395); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:604:38: ( constant[false] | filterIdentifier )
                    int alt203=2;
                    int LA203_0 = input.LA(1);

                    if ( ((LA203_0>=INT_TYPE && LA203_0<=NULL_TYPE)) ) {
                        alt203=1;
                    }
                    else if ( (LA203_0==EVENT_FILTER_IDENT) ) {
                        alt203=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 203, 0, input);

                        throw nvae;
                    }
                    switch (alt203) {
                        case 1 :
                            // EsperEPL2Ast.g:604:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4404);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:604:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4407);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:604:73: ( constant[false] | filterIdentifier )*
                    loop204:
                    do {
                        int alt204=3;
                        int LA204_0 = input.LA(1);

                        if ( ((LA204_0>=INT_TYPE && LA204_0<=NULL_TYPE)) ) {
                            alt204=1;
                        }
                        else if ( (LA204_0==EVENT_FILTER_IDENT) ) {
                            alt204=2;
                        }


                        switch (alt204) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:604:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4411);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:604:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4414);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop204;
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
                    // EsperEPL2Ast.g:605:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4429); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:605:42: ( constant[false] | filterIdentifier )
                    int alt205=2;
                    int LA205_0 = input.LA(1);

                    if ( ((LA205_0>=INT_TYPE && LA205_0<=NULL_TYPE)) ) {
                        alt205=1;
                    }
                    else if ( (LA205_0==EVENT_FILTER_IDENT) ) {
                        alt205=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 205, 0, input);

                        throw nvae;
                    }
                    switch (alt205) {
                        case 1 :
                            // EsperEPL2Ast.g:605:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4438);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:605:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4441);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:605:77: ( constant[false] | filterIdentifier )*
                    loop206:
                    do {
                        int alt206=3;
                        int LA206_0 = input.LA(1);

                        if ( ((LA206_0>=INT_TYPE && LA206_0<=NULL_TYPE)) ) {
                            alt206=1;
                        }
                        else if ( (LA206_0==EVENT_FILTER_IDENT) ) {
                            alt206=2;
                        }


                        switch (alt206) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:605:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4445);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:605:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4448);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop206;
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
                    // EsperEPL2Ast.g:606:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4463); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:606:27: ( constant[false] | filterIdentifier )
                    int alt207=2;
                    int LA207_0 = input.LA(1);

                    if ( ((LA207_0>=INT_TYPE && LA207_0<=NULL_TYPE)) ) {
                        alt207=1;
                    }
                    else if ( (LA207_0==EVENT_FILTER_IDENT) ) {
                        alt207=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 207, 0, input);

                        throw nvae;
                    }
                    switch (alt207) {
                        case 1 :
                            // EsperEPL2Ast.g:606:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4466);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:606:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4469);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:606:62: ( constant[false] | filterIdentifier )
                    int alt208=2;
                    int LA208_0 = input.LA(1);

                    if ( ((LA208_0>=INT_TYPE && LA208_0<=NULL_TYPE)) ) {
                        alt208=1;
                    }
                    else if ( (LA208_0==EVENT_FILTER_IDENT) ) {
                        alt208=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 208, 0, input);

                        throw nvae;
                    }
                    switch (alt208) {
                        case 1 :
                            // EsperEPL2Ast.g:606:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4473);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:606:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4476);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:607:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4484); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:607:31: ( constant[false] | filterIdentifier )
                    int alt209=2;
                    int LA209_0 = input.LA(1);

                    if ( ((LA209_0>=INT_TYPE && LA209_0<=NULL_TYPE)) ) {
                        alt209=1;
                    }
                    else if ( (LA209_0==EVENT_FILTER_IDENT) ) {
                        alt209=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 209, 0, input);

                        throw nvae;
                    }
                    switch (alt209) {
                        case 1 :
                            // EsperEPL2Ast.g:607:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4487);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:607:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4490);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:607:66: ( constant[false] | filterIdentifier )
                    int alt210=2;
                    int LA210_0 = input.LA(1);

                    if ( ((LA210_0>=INT_TYPE && LA210_0<=NULL_TYPE)) ) {
                        alt210=1;
                    }
                    else if ( (LA210_0==EVENT_FILTER_IDENT) ) {
                        alt210=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 210, 0, input);

                        throw nvae;
                    }
                    switch (alt210) {
                        case 1 :
                            // EsperEPL2Ast.g:607:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4494);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:607:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4497);
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
    // EsperEPL2Ast.g:610:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:611:2: ( constant[false] | filterIdentifier )
            int alt212=2;
            int LA212_0 = input.LA(1);

            if ( ((LA212_0>=INT_TYPE && LA212_0<=NULL_TYPE)) ) {
                alt212=1;
            }
            else if ( (LA212_0==EVENT_FILTER_IDENT) ) {
                alt212=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 212, 0, input);

                throw nvae;
            }
            switch (alt212) {
                case 1 :
                    // EsperEPL2Ast.g:611:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4511);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:612:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4517);
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
    // EsperEPL2Ast.g:614:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:615:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:615:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4528); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4530); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4532);
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
    // EsperEPL2Ast.g:618:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:619:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:619:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4551); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4553);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:619:44: ( eventPropertyAtomic )*
            loop213:
            do {
                int alt213=2;
                int LA213_0 = input.LA(1);

                if ( ((LA213_0>=EVENT_PROP_SIMPLE && LA213_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt213=1;
                }


                switch (alt213) {
            	case 1 :
            	    // EsperEPL2Ast.g:619:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4556);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop213;
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
    // EsperEPL2Ast.g:622:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:623:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt214=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt214=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt214=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt214=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt214=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt214=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt214=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 214, 0, input);

                throw nvae;
            }

            switch (alt214) {
                case 1 :
                    // EsperEPL2Ast.g:623:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4575); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4577); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:624:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4584); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4586); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4588); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:625:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4595); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4597); 
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
                    // EsperEPL2Ast.g:626:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4612); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4614); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:627:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4621); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4623); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4625); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:628:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4632); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4634); 
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
    // EsperEPL2Ast.g:631:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:632:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:632:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4661); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4663);
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
    // EsperEPL2Ast.g:635:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:636:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt225=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt225=1;
                }
                break;
            case HOUR_PART:
                {
                alt225=2;
                }
                break;
            case MINUTE_PART:
                {
                alt225=3;
                }
                break;
            case SECOND_PART:
                {
                alt225=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt225=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 225, 0, input);

                throw nvae;
            }

            switch (alt225) {
                case 1 :
                    // EsperEPL2Ast.g:636:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4679);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:636:13: ( hourPart )?
                    int alt215=2;
                    int LA215_0 = input.LA(1);

                    if ( (LA215_0==HOUR_PART) ) {
                        alt215=1;
                    }
                    switch (alt215) {
                        case 1 :
                            // EsperEPL2Ast.g:636:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4682);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:636:25: ( minutePart )?
                    int alt216=2;
                    int LA216_0 = input.LA(1);

                    if ( (LA216_0==MINUTE_PART) ) {
                        alt216=1;
                    }
                    switch (alt216) {
                        case 1 :
                            // EsperEPL2Ast.g:636:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4687);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:636:39: ( secondPart )?
                    int alt217=2;
                    int LA217_0 = input.LA(1);

                    if ( (LA217_0==SECOND_PART) ) {
                        alt217=1;
                    }
                    switch (alt217) {
                        case 1 :
                            // EsperEPL2Ast.g:636:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4692);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:636:53: ( millisecondPart )?
                    int alt218=2;
                    int LA218_0 = input.LA(1);

                    if ( (LA218_0==MILLISECOND_PART) ) {
                        alt218=1;
                    }
                    switch (alt218) {
                        case 1 :
                            // EsperEPL2Ast.g:636:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4697);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:637:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4704);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:637:13: ( minutePart )?
                    int alt219=2;
                    int LA219_0 = input.LA(1);

                    if ( (LA219_0==MINUTE_PART) ) {
                        alt219=1;
                    }
                    switch (alt219) {
                        case 1 :
                            // EsperEPL2Ast.g:637:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4707);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:637:27: ( secondPart )?
                    int alt220=2;
                    int LA220_0 = input.LA(1);

                    if ( (LA220_0==SECOND_PART) ) {
                        alt220=1;
                    }
                    switch (alt220) {
                        case 1 :
                            // EsperEPL2Ast.g:637:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4712);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:637:41: ( millisecondPart )?
                    int alt221=2;
                    int LA221_0 = input.LA(1);

                    if ( (LA221_0==MILLISECOND_PART) ) {
                        alt221=1;
                    }
                    switch (alt221) {
                        case 1 :
                            // EsperEPL2Ast.g:637:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4717);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:638:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4724);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:638:15: ( secondPart )?
                    int alt222=2;
                    int LA222_0 = input.LA(1);

                    if ( (LA222_0==SECOND_PART) ) {
                        alt222=1;
                    }
                    switch (alt222) {
                        case 1 :
                            // EsperEPL2Ast.g:638:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4727);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:638:29: ( millisecondPart )?
                    int alt223=2;
                    int LA223_0 = input.LA(1);

                    if ( (LA223_0==MILLISECOND_PART) ) {
                        alt223=1;
                    }
                    switch (alt223) {
                        case 1 :
                            // EsperEPL2Ast.g:638:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4732);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:639:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4739);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:639:15: ( millisecondPart )?
                    int alt224=2;
                    int LA224_0 = input.LA(1);

                    if ( (LA224_0==MILLISECOND_PART) ) {
                        alt224=1;
                    }
                    switch (alt224) {
                        case 1 :
                            // EsperEPL2Ast.g:639:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4742);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:640:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4749);
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
    // EsperEPL2Ast.g:643:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:644:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:644:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4763); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4765);
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
    // EsperEPL2Ast.g:647:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:648:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:648:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4780); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4782);
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
    // EsperEPL2Ast.g:651:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:652:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:652:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4797); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4799);
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
    // EsperEPL2Ast.g:655:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:656:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:656:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4814); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4816);
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
    // EsperEPL2Ast.g:659:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:660:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:660:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4831); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4833);
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
    // EsperEPL2Ast.g:663:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:664:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:664:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4848); 
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
    // EsperEPL2Ast.g:667:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:668:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt226=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt226=1;
                }
                break;
            case LONG_TYPE:
                {
                alt226=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt226=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt226=4;
                }
                break;
            case STRING_TYPE:
                {
                alt226=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt226=6;
                }
                break;
            case NULL_TYPE:
                {
                alt226=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 226, 0, input);

                throw nvae;
            }

            switch (alt226) {
                case 1 :
                    // EsperEPL2Ast.g:668:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4864); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:669:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4873); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:670:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4882); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:671:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4891); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:672:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4907); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:673:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4923); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:674:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4936); 
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
    // EsperEPL2Ast.g:677:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:678:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x00003F8700000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x00003F8700000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L,0x00003F8300000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x00003F8300000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000020000L,0x0000002100402800L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onStreamExpr_in_onExpr262 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000238000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr267 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onUpdateExpr_in_onExpr271 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr275 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr278 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr281 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr288 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_STREAM_in_onStreamExpr310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onStreamExpr313 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onStreamExpr317 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_onStreamExpr320 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr339 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr341 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr343 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000004000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr346 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x2000004000000000L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr349 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr368 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr371 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr391 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr393 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr396 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr399 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x000000000C000180L,0x0000000000100000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr401 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x000000000C000180L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr404 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x000000000C000100L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr408 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x0000000008000100L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr411 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr414 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr417 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr439 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr441 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr443 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput460 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput462 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr480 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr482 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x2000004000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr485 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x2000004000000000L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr489 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr506 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000004000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr508 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x2000004000000000L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr511 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment526 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment528 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment530 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom544 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom546 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom549 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr567 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr569 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000020001000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr572 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000020001000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr576 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000020001000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr579 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000020001000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr593 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr596 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr625 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr636 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert656 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList673 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList675 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x2000000000040000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList678 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x2000000000040000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList699 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList702 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement717 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement719 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement735 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement745 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement765 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement769 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement791 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement794 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr830 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr832 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr834 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr837 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr855 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000020000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr861 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr866 = new BitSet(new long[]{0x0000000000000002L,0x0000001200000000L,0x000002F00C000180L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr871 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F00C000180L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr878 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F00C000100L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr886 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F008000100L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr893 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F008000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr900 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr907 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr931 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr942 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr945 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol964 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol966 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol969 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause987 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause989 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1002 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1005 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1019 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1022 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000003D00000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1025 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000003D00000L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1045 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1047 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1054 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0021000000000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1060 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0021000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1066 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0021000000000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1072 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1078 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1084 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1102 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1104 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1121 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1123 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1125 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1127 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1129 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1135 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1150 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1152 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1154 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1170 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1172 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1191 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1193 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1213 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1215 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x000C000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1238 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1240 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1242 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1262 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0012000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1297 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1299 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000034L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1301 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1332 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000034L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1336 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1348 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1370 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1372 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1389 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1391 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1393 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1410 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1413 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1429 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1439 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1441 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1444 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1458 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1460 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1463 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1496 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1498 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1501 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1505 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1508 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1523 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1525 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1528 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1532 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1535 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1550 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1552 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1555 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1559 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1562 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1577 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1579 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1582 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1586 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1589 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1610 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1613 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x2000000000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1617 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x2000000000000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1621 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x2000000000000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1625 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x2000000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1629 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1634 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1639 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_streamExpression1643 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1667 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1672 = new BitSet(new long[]{0x0000000037CC23C8L,0x0010000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1674 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1678 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1698 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1700 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1719 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1721 = new BitSet(new long[]{0x0000000000000000L,0x01C0000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1724 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1727 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1731 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1733 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1753 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1763 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1765 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1768 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1782 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1784 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1787 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1808 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1810 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1827 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1831 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1839 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1860 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1862 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1864 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1867 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1881 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1884 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1901 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1905 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1908 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1930 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1932 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1952 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1955 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1973 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1975 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1978 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1998 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2000 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2002 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2025 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2027 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2045 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2047 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000078000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2059 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2061 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2064 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2081 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2094 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2096 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2112 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2114 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2125 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2127 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2143 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2145 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2156 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L,0x0000000000000000L,0x0000000000238000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2158 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2161 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2174 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2176 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2191 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2193 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000078000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2196 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2212 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2215 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x3000078000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2217 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x3000078000000000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2221 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2223 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x1000000000000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2227 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2248 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2250 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2252 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2254 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2256 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2258 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2260 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2277 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2279 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2292 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2309 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2321 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2323 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2345 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2355 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2370 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023FL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2379 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2410 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2412 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2414 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2417 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2431 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2433 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2435 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2438 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2452 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2454 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2456 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2470 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2472 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2484 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2486 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2488 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023FL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2497 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2502 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2517 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2519 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023FL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2528 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2533 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2546 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2548 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2572 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2600 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2618 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2629 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2649 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2699 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2701 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2703 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2722 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2727 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2742 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2744 = new BitSet(new long[]{0x0000000000000008L,0x0000140000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2755 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2782 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2804 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2807 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x00003F8000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2810 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x00003F8000000200L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x00003F8000000200L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2817 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2820 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2823 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2844 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2847 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2850 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2853 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2875 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2878 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2881 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2900 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2903 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2906 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2909 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2930 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr2932 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2951 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2953 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2972 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2974 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2993 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2995 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2997 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3009 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3011 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3013 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3032 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3034 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3050 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr3053 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3055 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3058 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3076 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3078 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000010L,0x2000000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3081 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3086 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3090 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3093 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3113 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3116 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3129 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3132 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3152 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3154 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000041L});
    public static final BitSet FOLLOW_set_in_inExpr3156 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3162 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077989EL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3165 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077989EL});
    public static final BitSet FOLLOW_set_in_inExpr3169 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3186 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000041L});
    public static final BitSet FOLLOW_set_in_inExpr3188 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3194 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077989EL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3197 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077989EL});
    public static final BitSet FOLLOW_set_in_inExpr3201 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3218 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000041L});
    public static final BitSet FOLLOW_set_in_inExpr3220 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3226 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3228 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_set_in_inExpr3230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3245 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3247 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000041L});
    public static final BitSet FOLLOW_set_in_inExpr3249 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3255 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3257 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_set_in_inExpr3259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3282 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3284 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3286 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3288 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3299 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3301 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3303 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3306 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3326 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3328 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3330 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3333 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3346 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3348 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3350 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3353 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3372 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3374 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3376 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3387 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3389 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3391 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3410 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3413 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3417 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3428 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3431 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3435 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3446 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3450 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3454 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3471 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3475 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3486 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3489 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3493 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3504 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3507 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3511 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3522 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3525 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3529 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3540 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3543 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3547 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3559 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3561 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3563 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3566 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3581 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3583 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3585 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3598 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3602 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3604 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3621 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3624 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3638 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3640 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3642 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3656 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3668 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3688 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3691 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3714 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3716 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3728 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3730 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3732 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3744 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3746 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3748 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3759 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3761 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3763 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3775 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3777 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3779 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3790 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3792 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3794 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3805 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3807 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3809 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3820 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3822 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3824 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3836 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3838 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3840 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3843 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3864 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3867 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3871 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3874 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3879 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule3899 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000101000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3903 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3932 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3934 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3948 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice3950 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3952 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3966 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3968 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3982 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3984 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3986 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3988 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3990 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4004 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4006 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4009 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4011 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4032 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions4034 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4053 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4055 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4057 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4060 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4076 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4078 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4080 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4083 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4099 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4101 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4103 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4106 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000001000000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4137 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4139 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4141 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4143 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00003F98800003BBL,0x000000000077981CL});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4163 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4168 = new BitSet(new long[]{0x0000000037CC23C8L,0x0010000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4170 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4174 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4192 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4194 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000400000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4196 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4204 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4206 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4214 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4216 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4223 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4254 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4256 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4259 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00003F980000023BL,0x000000000077981CL});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4275 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4286 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4293 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4295 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4302 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4304 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4311 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4313 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4320 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4322 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4331 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4338 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4341 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4345 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4348 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4362 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4364 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4371 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4374 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4378 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4381 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4384 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4395 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4397 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4404 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4407 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4411 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4414 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4418 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4429 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4431 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4438 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4441 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4445 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4448 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L,0x0000000000000082L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4452 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4463 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4466 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4469 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4473 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4476 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4484 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4487 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4490 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00003F8000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4494 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4497 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4517 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4528 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4532 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4551 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4553 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000FC0000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4556 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000FC0000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4575 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4577 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4584 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4586 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4588 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4595 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4597 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4599 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4612 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4614 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4621 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4623 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000002000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4625 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4632 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4634 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4636 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4661 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4663 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4679 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x003C000000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4682 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0038000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4687 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4692 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4704 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0038000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4707 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4712 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4724 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4727 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4732 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4739 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4763 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4765 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4780 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4782 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4797 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4814 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4816 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4831 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4833 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4848 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4864 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4873 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4882 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4936 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}