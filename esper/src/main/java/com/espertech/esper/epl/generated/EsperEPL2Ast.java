// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-08-08 07:47:46

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "BOR", "PLUS", "QUESTION", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=167;
    public static final int FLOAT_SUFFIX=315;
    public static final int STAR=256;
    public static final int NUMERIC_PARAM_LIST=107;
    public static final int ISTREAM=60;
    public static final int MOD=276;
    public static final int OUTERJOIN_EXPR=149;
    public static final int BSR=297;
    public static final int LIB_FUNCTION=173;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=93;
    public static final int FULL_OUTERJOIN_EXPR=153;
    public static final int MATCHREC_PATTERN_CONCAT=240;
    public static final int RPAREN=255;
    public static final int LNOT=286;
    public static final int INC=290;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=263;
    public static final int BSR_ASSIGN=298;
    public static final int CAST_EXPR=202;
    public static final int MATCHES=104;
    public static final int STREAM_EXPR=148;
    public static final int TIMEPERIOD_SECONDS=90;
    public static final int NOT_EQUAL=268;
    public static final int METADATASQL=67;
    public static final int EVENT_FILTER_PROPERTY_EXPR=116;
    public static final int LAST_AGGREG=226;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=110;
    public static final int FOLLOWED_BY=280;
    public static final int HOUR_PART=178;
    public static final int RBRACK=261;
    public static final int MATCHREC_PATTERN_NESTED=242;
    public static final int MATCH_UNTIL_RANGE_CLOSED=217;
    public static final int GE=272;
    public static final int METHOD_JOIN_EXPR=213;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=115;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=114;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=291;
    public static final int EVENT_FILTER_NOT_IN=126;
    public static final int INSERTINTO_STREAM_NAME=190;
    public static final int NUM_DOUBLE=236;
    public static final int UNARY_MINUS=174;
    public static final int TIMEPERIOD_MILLISEC=91;
    public static final int LCURLY=277;
    public static final int RETAINUNION=63;
    public static final int DBWHERE_CLAUSE=188;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=124;
    public static final int GROUP=44;
    public static final int EMAILAT=306;
    public static final int WS=307;
    public static final int SUBSELECT_GROUP_EXPR=194;
    public static final int ON_SELECT_INSERT_EXPR=208;
    public static final int ESCAPECHAR=281;
    public static final int SL_COMMENT=308;
    public static final int NULL_TYPE=235;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=215;
    public static final int GT=270;
    public static final int BNOT=287;
    public static final int WHERE_EXPR=135;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=150;
    public static final int LAND=304;
    public static final int NOT_REGEXP=185;
    public static final int MATCH_UNTIL_EXPR=214;
    public static final int EVENT_PROP_EXPR=157;
    public static final int LBRACK=260;
    public static final int VIEW_EXPR=132;
    public static final int ANNOTATION=222;
    public static final int LONG_TYPE=230;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=117;
    public static final int MATCHREC_PATTERN=238;
    public static final int TIMEPERIOD_SEC=88;
    public static final int ON_SELECT_EXPR=207;
    public static final int TICKED_STRING_LITERAL=282;
    public static final int MINUTE_PART=179;
    public static final int PATTERN_NOT_EXPR=113;
    public static final int SUM=18;
    public static final int SQL_NE=267;
    public static final int HexDigit=313;
    public static final int UPDATE_EXPR=227;
    public static final int LPAREN=254;
    public static final int IN_SUBSELECT_EXPR=196;
    public static final int AT=81;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=94;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=244;
    public static final int NOT_IN_RANGE=192;
    public static final int OFFSET=98;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=68;
    public static final int SECOND_PART=180;
    public static final int MATCH_RECOGNIZE=100;
    public static final int IDENT=251;
    public static final int DATABASE_JOIN_EXPR=134;
    public static final int BXOR=266;
    public static final int PLUS=258;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=82;
    public static final int EXISTS=70;
    public static final int EVENT_PROP_INDEXED=160;
    public static final int TIMEPERIOD_MILLISECOND=92;
    public static final int EVAL_NOTEQUALS_EXPR=141;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=216;
    public static final int CREATE_VARIABLE_EXPR=212;
    public static final int CREATE_WINDOW_COL_TYPE=220;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=245;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=111;
    public static final int RIGHT_OUTERJOIN_EXPR=152;
    public static final int NUMBERSETSTAR=221;
    public static final int LAST_OPERATOR=199;
    public static final int PATTERN_FILTER_EXPR=112;
    public static final int EVAL_AND_EXPR=138;
    public static final int LEFT_OUTERJOIN_EXPR=151;
    public static final int EPL_EXPR=237;
    public static final int GROUP_BY_EXPR=154;
    public static final int SET=78;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=73;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=158;
    public static final int MINUS=274;
    public static final int SEMI=305;
    public static final int STAR_ASSIGN=293;
    public static final int FIRST_AGGREG=225;
    public static final int COLON=262;
    public static final int EVAL_EQUALS_GROUP_EXPR=142;
    public static final int BAND_ASSIGN=303;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=168;
    public static final int VALUE_NULL=96;
    public static final int NOT_IN_SET=182;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=161;
    public static final int SL=299;
    public static final int NOT_IN_SUBSELECT_EXPR=197;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=130;
    public static final int SR=295;
    public static final int RCURLY=278;
    public static final int PLUS_ASSIGN=289;
    public static final int EXISTS_SUBSELECT_EXPR=195;
    public static final int DAY_PART=177;
    public static final int EVENT_FILTER_IN=125;
    public static final int DIV=275;
    public static final int OBJECT_PARAM_ORDERED_EXPR=109;
    public static final int OctalEscape=312;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=181;
    public static final int PRIOR=69;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=97;
    public static final int SELECTION_EXPR=145;
    public static final int LOR=273;
    public static final int CAST=74;
    public static final int LW=72;
    public static final int WILDCARD_SELECT=189;
    public static final int EXPONENT=314;
    public static final int LT=269;
    public static final int PATTERN_INCL_EXPR=133;
    public static final int ORDER_BY_EXPR=155;
    public static final int BOOL_TYPE=234;
    public static final int MOD_ASSIGN=294;
    public static final int ANNOTATION_ARRAY=223;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=198;
    public static final int EQUALS=252;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=64;
    public static final int DIV_ASSIGN=288;
    public static final int SL_ASSIGN=300;
    public static final int PATTERN=65;
    public static final int SQL=66;
    public static final int FULL=40;
    public static final int WEEKDAY=71;
    public static final int MATCHREC_AFTER_SKIP=243;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ARRAY_EXPR=176;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=95;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=143;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=127;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=89;
    public static final int FLOAT_TYPE=231;
    public static final int SUBSELECT_EXPR=193;
    public static final int ANNOTATION_VALUE=224;
    public static final int CONCAT=172;
    public static final int NUMERIC_PARAM_RANGE=106;
    public static final int CLASS_IDENT=129;
    public static final int MATCHREC_PATTERN_ALTER=241;
    public static final int ON_EXPR=205;
    public static final int CREATE_WINDOW_EXPR=203;
    public static final int PROPERTY_SELECTION_STREAM=119;
    public static final int ON_DELETE_EXPR=206;
    public static final int ON=41;
    public static final int NUM_LONG=283;
    public static final int TIME_PERIOD=175;
    public static final int DOUBLE_TYPE=232;
    public static final int DELETE=76;
    public static final int INT_TYPE=229;
    public static final int MATCHREC_PARTITION=249;
    public static final int EVAL_BITWISE_EXPR=137;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=156;
    public static final int TIMEPERIOD_HOURS=85;
    public static final int VARIABLE=79;
    public static final int SUBSTITUTION=201;
    public static final int UNTIL=80;
    public static final int STRING_TYPE=233;
    public static final int ON_SET_EXPR=211;
    public static final int MATCHREC_DEFINE_ITEM=246;
    public static final int NUM_INT=279;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=210;
    public static final int NUM_FLOAT=284;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=118;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=310;
    public static final int WEEKDAY_OPERATOR=200;
    public static final int WHERE=16;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=219;
    public static final int DEC=292;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=108;
    public static final int BXOR_ASSIGN=301;
    public static final int AFTER_LIMIT_EXPR=166;
    public static final int ORDER=56;
    public static final int SNAPSHOT=77;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=163;
    public static final int EVENT_FILTER_PARAM=122;
    public static final int IRSTREAM=61;
    public static final int UPDATE=99;
    public static final int MAX=20;
    public static final int DEFINE=102;
    public static final int TIMEPERIOD_DAYS=83;
    public static final int EVENT_FILTER_RANGE=123;
    public static final int ML_COMMENT=309;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=162;
    public static final int BOR_ASSIGN=302;
    public static final int COMMA=250;
    public static final int WHEN_LIMIT_EXPR=169;
    public static final int PARTITION=103;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=165;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=84;
    public static final int MATCHREC_MEASURE_ITEM=248;
    public static final int BOR=257;
    public static final int EQUAL=285;
    public static final int EVENT_FILTER_NOT_BETWEEN=128;
    public static final int IN_RANGE=191;
    public static final int DOT=253;
    public static final int CURRENT_TIMESTAMP=75;
    public static final int MATCHREC_MEASURES=247;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=120;
    public static final int INSERTINTO_EXPR=170;
    public static final int HAVING_EXPR=136;
    public static final int UNIDIRECTIONAL=62;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=218;
    public static final int EVAL_EQUALS_EXPR=140;
    public static final int TIMEPERIOD_MINUTES=87;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=184;
    public static final int EVENT_LIMIT_EXPR=164;
    public static final int NOT_BETWEEN=183;
    public static final int TIMEPERIOD_MINUTE=86;
    public static final int EVAL_OR_EXPR=139;
    public static final int ON_SELECT_INSERT_OUTPUT=209;
    public static final int AFTER=105;
    public static final int MEASURES=101;
    public static final int MATCHREC_PATTERN_ATOM=239;
    public static final int BAND=265;
    public static final int QUOTED_STRING_LITERAL=264;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=259;
    public static final int OBSERVER_EXPR=131;
    public static final int EVENT_FILTER_IDENT=121;
    public static final int EVENT_PROP_MAPPED=159;
    public static final int UnicodeEscape=311;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=186;
    public static final int SELECTION_ELEMENT_EXPR=146;
    public static final int CREATE_WINDOW_SELECT_EXPR=204;
    public static final int INSERTINTO_EXPRCOL=171;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=228;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=147;
    public static final int SR_ASSIGN=296;
    public static final int DBFROM_CLAUSE=187;
    public static final int LE=271;
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
    // EsperEPL2Ast.g:83:1: onExpr : ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:84:2: ( ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ) )
            // EsperEPL2Ast.g:84:4: ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr260); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:84:16: ( eventFilterExpr | patternInclusionExpression )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==EVENT_FILTER_EXPR) ) {
                alt7=1;
            }
            else if ( (LA7_0==PATTERN_INCL_EXPR) ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // EsperEPL2Ast.g:84:17: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_onExpr263);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:84:35: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onExpr267);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:84:63: ( IDENT )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==IDENT) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // EsperEPL2Ast.g:84:63: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExpr270); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:85:3: ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr )
            int alt12=3;
            switch ( input.LA(1) ) {
            case ON_DELETE_EXPR:
                {
                alt12=1;
                }
                break;
            case ON_SELECT_EXPR:
                {
                alt12=2;
                }
                break;
            case ON_SET_EXPR:
                {
                alt12=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }

            switch (alt12) {
                case 1 :
                    // EsperEPL2Ast.g:85:4: onDeleteExpr
                    {
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr277);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:85:19: onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr281);
                    onSelectExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:85:32: ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    int alt11=2;
                    int LA11_0 = input.LA(1);

                    if ( (LA11_0==ON_SELECT_INSERT_EXPR) ) {
                        alt11=1;
                    }
                    switch (alt11) {
                        case 1 :
                            // EsperEPL2Ast.g:85:33: ( onSelectInsertExpr )+ ( onSelectInsertOutput )?
                            {
                            // EsperEPL2Ast.g:85:33: ( onSelectInsertExpr )+
                            int cnt9=0;
                            loop9:
                            do {
                                int alt9=2;
                                int LA9_0 = input.LA(1);

                                if ( (LA9_0==ON_SELECT_INSERT_EXPR) ) {
                                    alt9=1;
                                }


                                switch (alt9) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:85:33: onSelectInsertExpr
                            	    {
                            	    pushFollow(FOLLOW_onSelectInsertExpr_in_onExpr284);
                            	    onSelectInsertExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    if ( cnt9 >= 1 ) break loop9;
                                        EarlyExitException eee =
                                            new EarlyExitException(9, input);
                                        throw eee;
                                }
                                cnt9++;
                            } while (true);

                            // EsperEPL2Ast.g:85:53: ( onSelectInsertOutput )?
                            int alt10=2;
                            int LA10_0 = input.LA(1);

                            if ( (LA10_0==ON_SELECT_INSERT_OUTPUT) ) {
                                alt10=1;
                            }
                            switch (alt10) {
                                case 1 :
                                    // EsperEPL2Ast.g:85:53: onSelectInsertOutput
                                    {
                                    pushFollow(FOLLOW_onSelectInsertOutput_in_onExpr287);
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
                case 3 :
                    // EsperEPL2Ast.g:85:79: onSetExpr
                    {
                    pushFollow(FOLLOW_onSetExpr_in_onExpr294);
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


    // $ANTLR start "updateExpr"
    // EsperEPL2Ast.g:89:1: updateExpr : ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) ;
    public final void updateExpr() throws RecognitionException {
        CommonTree u=null;

        try {
            // EsperEPL2Ast.g:90:2: ( ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:90:4: ^(u= UPDATE_EXPR CLASS_IDENT ( IDENT )? ( onSetAssignment )+ ( whereClause[false] )? )
            {
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr316); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr318); 
            // EsperEPL2Ast.g:90:32: ( IDENT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==IDENT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:90:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr320); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:90:39: ( onSetAssignment )+
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
            	    // EsperEPL2Ast.g:90:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr323);
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

            // EsperEPL2Ast.g:90:56: ( whereClause[false] )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==WHERE_EXPR) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // EsperEPL2Ast.g:90:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr326);
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
    // EsperEPL2Ast.g:93:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:94:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:94:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause[true] )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr343); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr345);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:94:32: ( whereClause[true] )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE_EXPR) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:94:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr348);
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
    // EsperEPL2Ast.g:97:1: onSelectExpr : ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:98:2: ( ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) )
            // EsperEPL2Ast.g:98:4: ^(s= ON_SELECT_EXPR ( insertIntoExpr )? ( DISTINCT )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? )
            {
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr368); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:98:23: ( insertIntoExpr )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==INSERTINTO_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:98:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr370);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:39: ( DISTINCT )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==DISTINCT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:98:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr373); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr376);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:98:63: ( onExprFrom )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ON_EXPR_FROM) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:98:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr378);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:75: ( whereClause[true] )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==WHERE_EXPR) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // EsperEPL2Ast.g:98:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr381);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:94: ( groupByClause )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==GROUP_BY_EXPR) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:98:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr385);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:109: ( havingClause )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==HAVING_EXPR) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:98:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr388);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:123: ( orderByClause )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ORDER_BY_EXPR) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:98:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr391);
                    orderByClause();

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
    // EsperEPL2Ast.g:101:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:102:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:102:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr411); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr413);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr415);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:102:78: ( whereClause[true] )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==WHERE_EXPR) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:102:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr417);
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
    // EsperEPL2Ast.g:105:1: onSelectInsertOutput : ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) ;
    public final void onSelectInsertOutput() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:106:2: ( ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) )
            // EsperEPL2Ast.g:106:4: ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) )
            {
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput434); 

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
    // EsperEPL2Ast.g:109:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:110:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? ) )
            // EsperEPL2Ast.g:110:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ( whereClause[false] )? )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr454); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr456);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:110:34: ( onSetAssignment )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( (LA25_0==ON_SET_EXPR_ITEM) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // EsperEPL2Ast.g:110:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr459);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);

            // EsperEPL2Ast.g:110:53: ( whereClause[false] )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==WHERE_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:110:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr463);
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


    // $ANTLR start "onSetAssignment"
    // EsperEPL2Ast.g:113:1: onSetAssignment : ^( ON_SET_EXPR_ITEM IDENT valueExpr ) ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:114:2: ( ^( ON_SET_EXPR_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:114:4: ^( ON_SET_EXPR_ITEM IDENT valueExpr )
            {
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment478); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onSetAssignment480); 
            pushFollow(FOLLOW_valueExpr_in_onSetAssignment482);
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
    // EsperEPL2Ast.g:117:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:118:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:118:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom496); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom498); 
            // EsperEPL2Ast.g:118:25: ( IDENT )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==IDENT) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:118:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom501); 

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
    // EsperEPL2Ast.g:121:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:122:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:122:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr519); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr521); 
            // EsperEPL2Ast.g:122:33: ( viewListExpr )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==VIEW_EXPR) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:122:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr524);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:122:49: ( RETAINUNION )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RETAINUNION) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:122:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr528); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:122:62: ( RETAININTERSECTION )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==RETAININTERSECTION) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:122:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr531); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:123:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==CLASS_IDENT||LA32_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt32=1;
            }
            else if ( (LA32_0==CREATE_WINDOW_COL_TYPE_LIST) ) {
                alt32=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 32, 0, input);

                throw nvae;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:124:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:124:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:124:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:124:6: ( createSelectionList )?
                    int alt31=2;
                    int LA31_0 = input.LA(1);

                    if ( (LA31_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt31=1;
                    }
                    switch (alt31) {
                        case 1 :
                            // EsperEPL2Ast.g:124:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr545);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr548); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:126:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:126:12: ( createColTypeList )
                    // EsperEPL2Ast.g:126:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr577);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:128:4: ( createWindowExprInsert )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==INSERT) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:128:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr588);
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
    // EsperEPL2Ast.g:132:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:133:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:133:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert606); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:133:13: ( valueExpr )?
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>=IN_SET && LA34_0<=REGEXP)||LA34_0==NOT_EXPR||(LA34_0>=SUM && LA34_0<=AVG)||(LA34_0>=COALESCE && LA34_0<=COUNT)||(LA34_0>=CASE && LA34_0<=CASE2)||(LA34_0>=PREVIOUS && LA34_0<=EXISTS)||(LA34_0>=INSTANCEOF && LA34_0<=CURRENT_TIMESTAMP)||(LA34_0>=EVAL_AND_EXPR && LA34_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA34_0==EVENT_PROP_EXPR||(LA34_0>=CONCAT && LA34_0<=LIB_FUNCTION)||LA34_0==ARRAY_EXPR||(LA34_0>=NOT_IN_SET && LA34_0<=NOT_REGEXP)||(LA34_0>=IN_RANGE && LA34_0<=SUBSELECT_EXPR)||(LA34_0>=EXISTS_SUBSELECT_EXPR && LA34_0<=NOT_IN_SUBSELECT_EXPR)||LA34_0==SUBSTITUTION||(LA34_0>=FIRST_AGGREG && LA34_0<=LAST_AGGREG)||(LA34_0>=INT_TYPE && LA34_0<=NULL_TYPE)||(LA34_0>=STAR && LA34_0<=PLUS)||(LA34_0>=BAND && LA34_0<=BXOR)||(LA34_0>=LT && LA34_0<=GE)||(LA34_0>=MINUS && LA34_0<=MOD)) ) {
                    alt34=1;
                }
                switch (alt34) {
                    case 1 :
                        // EsperEPL2Ast.g:133:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert608);
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
    // EsperEPL2Ast.g:136:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:137:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:137:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList625); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList627);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:137:61: ( createSelectionListElement )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==SELECTION_ELEMENT_EXPR||LA35_0==WILDCARD_SELECT) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // EsperEPL2Ast.g:137:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList630);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop35;
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
    // EsperEPL2Ast.g:140:1: createColTypeList : ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:141:2: ( ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:141:4: ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList649); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList651);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:141:59: ( createColTypeListElement )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==CREATE_WINDOW_COL_TYPE) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // EsperEPL2Ast.g:141:60: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList654);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop36;
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
    // EsperEPL2Ast.g:144:1: createColTypeListElement : ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:145:2: ( ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) )
            // EsperEPL2Ast.g:145:4: ^( CREATE_WINDOW_COL_TYPE IDENT IDENT )
            {
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement669); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement671); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement673); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:148:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:149:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==WILDCARD_SELECT) ) {
                alt39=1;
            }
            else if ( (LA39_0==SELECTION_ELEMENT_EXPR) ) {
                alt39=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 39, 0, input);

                throw nvae;
            }
            switch (alt39) {
                case 1 :
                    // EsperEPL2Ast.g:149:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement687); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:150:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement697); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:150:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==EVENT_PROP_EXPR) ) {
                        alt38=1;
                    }
                    else if ( ((LA38_0>=INT_TYPE && LA38_0<=NULL_TYPE)) ) {
                        alt38=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 38, 0, input);

                        throw nvae;
                    }
                    switch (alt38) {
                        case 1 :
                            // EsperEPL2Ast.g:151:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:151:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:151:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement717);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:151:41: ( IDENT )?
                            int alt37=2;
                            int LA37_0 = input.LA(1);

                            if ( (LA37_0==IDENT) ) {
                                alt37=1;
                            }
                            switch (alt37) {
                                case 1 :
                                    // EsperEPL2Ast.g:151:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement721); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:152:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:152:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:152:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement743);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement746); 

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
    // EsperEPL2Ast.g:156:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:157:2: ( ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:157:4: ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr782); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr784); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr786); 
            // EsperEPL2Ast.g:157:41: ( valueExpr )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=IN_SET && LA40_0<=REGEXP)||LA40_0==NOT_EXPR||(LA40_0>=SUM && LA40_0<=AVG)||(LA40_0>=COALESCE && LA40_0<=COUNT)||(LA40_0>=CASE && LA40_0<=CASE2)||(LA40_0>=PREVIOUS && LA40_0<=EXISTS)||(LA40_0>=INSTANCEOF && LA40_0<=CURRENT_TIMESTAMP)||(LA40_0>=EVAL_AND_EXPR && LA40_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA40_0==EVENT_PROP_EXPR||(LA40_0>=CONCAT && LA40_0<=LIB_FUNCTION)||LA40_0==ARRAY_EXPR||(LA40_0>=NOT_IN_SET && LA40_0<=NOT_REGEXP)||(LA40_0>=IN_RANGE && LA40_0<=SUBSELECT_EXPR)||(LA40_0>=EXISTS_SUBSELECT_EXPR && LA40_0<=NOT_IN_SUBSELECT_EXPR)||LA40_0==SUBSTITUTION||(LA40_0>=FIRST_AGGREG && LA40_0<=LAST_AGGREG)||(LA40_0>=INT_TYPE && LA40_0<=NULL_TYPE)||(LA40_0>=STAR && LA40_0<=PLUS)||(LA40_0>=BAND && LA40_0<=BXOR)||(LA40_0>=LT && LA40_0<=GE)||(LA40_0>=MINUS && LA40_0<=MOD)) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:157:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr789);
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
    // EsperEPL2Ast.g:160:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:161:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:161:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:161:4: ( insertIntoExpr )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==INSERTINTO_EXPR) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:161:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr807);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr813);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr818);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:164:3: ( matchRecogClause )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==MATCH_RECOGNIZE) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:164:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr823);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:165:3: ( whereClause[true] )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==WHERE_EXPR) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:165:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr830);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:166:3: ( groupByClause )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==GROUP_BY_EXPR) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:166:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr838);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:167:3: ( havingClause )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==HAVING_EXPR) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:167:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr845);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:168:3: ( outputLimitExpr )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( ((LA46_0>=EVENT_LIMIT_EXPR && LA46_0<=CRONTAB_LIMIT_EXPR)||LA46_0==WHEN_LIMIT_EXPR) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:168:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr852);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:169:3: ( orderByClause )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==ORDER_BY_EXPR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:169:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr859);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:170:3: ( rowLimitClause )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==ROW_LIMIT_EXPR) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:170:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr866);
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
    // EsperEPL2Ast.g:173:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:174:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:174:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr883); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:174:24: ( ISTREAM | RSTREAM )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( ((LA49_0>=RSTREAM && LA49_0<=ISTREAM)) ) {
                alt49=1;
            }
            switch (alt49) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr894); 
            // EsperEPL2Ast.g:174:51: ( insertIntoExprCol )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==INSERTINTO_EXPRCOL) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // EsperEPL2Ast.g:174:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr897);
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
    // EsperEPL2Ast.g:177:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:178:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:178:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol916); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol918); 
            // EsperEPL2Ast.g:178:31: ( IDENT )*
            loop51:
            do {
                int alt51=2;
                int LA51_0 = input.LA(1);

                if ( (LA51_0==IDENT) ) {
                    alt51=1;
                }


                switch (alt51) {
            	case 1 :
            	    // EsperEPL2Ast.g:178:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol921); 

            	    }
            	    break;

            	default :
            	    break loop51;
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
    // EsperEPL2Ast.g:181:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:182:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) )
            // EsperEPL2Ast.g:182:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause939); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:182:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RSTREAM && LA52_0<=IRSTREAM)) ) {
                alt52=1;
            }
            switch (alt52) {
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

            // EsperEPL2Ast.g:182:55: ( DISTINCT )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==DISTINCT) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:182:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause954); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause957);
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
    // EsperEPL2Ast.g:185:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:186:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:186:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause971);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:186:21: ( streamExpression ( outerJoin )* )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( (LA55_0==STREAM_EXPR) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // EsperEPL2Ast.g:186:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause974);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:186:39: ( outerJoin )*
            	    loop54:
            	    do {
            	        int alt54=2;
            	        int LA54_0 = input.LA(1);

            	        if ( ((LA54_0>=INNERJOIN_EXPR && LA54_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt54=1;
            	        }


            	        switch (alt54) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:186:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause977);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop54;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // EsperEPL2Ast.g:189:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:190:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:190:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause997); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:190:24: ( matchRecogPartitionBy )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==MATCHREC_PARTITION) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:190:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause999);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1006);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:192:4: ( ALL )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ALL) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:192:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1012); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:193:4: ( matchRecogMatchesAfterSkip )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==MATCHREC_AFTER_SKIP) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // EsperEPL2Ast.g:193:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1018);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1024);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:195:4: ( matchRecogMatchesInterval )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==MATCHREC_INTERVAL) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:195:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1030);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1036);
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
    // EsperEPL2Ast.g:199:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:200:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:200:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1054); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:200:27: ( valueExpr )+
            int cnt60=0;
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( ((LA60_0>=IN_SET && LA60_0<=REGEXP)||LA60_0==NOT_EXPR||(LA60_0>=SUM && LA60_0<=AVG)||(LA60_0>=COALESCE && LA60_0<=COUNT)||(LA60_0>=CASE && LA60_0<=CASE2)||(LA60_0>=PREVIOUS && LA60_0<=EXISTS)||(LA60_0>=INSTANCEOF && LA60_0<=CURRENT_TIMESTAMP)||(LA60_0>=EVAL_AND_EXPR && LA60_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA60_0==EVENT_PROP_EXPR||(LA60_0>=CONCAT && LA60_0<=LIB_FUNCTION)||LA60_0==ARRAY_EXPR||(LA60_0>=NOT_IN_SET && LA60_0<=NOT_REGEXP)||(LA60_0>=IN_RANGE && LA60_0<=SUBSELECT_EXPR)||(LA60_0>=EXISTS_SUBSELECT_EXPR && LA60_0<=NOT_IN_SUBSELECT_EXPR)||LA60_0==SUBSTITUTION||(LA60_0>=FIRST_AGGREG && LA60_0<=LAST_AGGREG)||(LA60_0>=INT_TYPE && LA60_0<=NULL_TYPE)||(LA60_0>=STAR && LA60_0<=PLUS)||(LA60_0>=BAND && LA60_0<=BXOR)||(LA60_0>=LT && LA60_0<=GE)||(LA60_0>=MINUS && LA60_0<=MOD)) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // EsperEPL2Ast.g:200:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1056);
            	    valueExpr();

            	    state._fsp--;


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
    // EsperEPL2Ast.g:203:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT IDENT IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:204:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT IDENT IDENT ) )
            // EsperEPL2Ast.g:204:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT IDENT IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1073); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1075); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1077); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1079); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1081); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1083); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:207:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:208:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:208:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1098); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1100); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1102);
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
    // EsperEPL2Ast.g:211:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:212:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:212:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1118); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:212:26: ( matchRecogMeasureListElement )*
                loop61:
                do {
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==MATCHREC_MEASURE_ITEM) ) {
                        alt61=1;
                    }


                    switch (alt61) {
                	case 1 :
                	    // EsperEPL2Ast.g:212:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1120);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop61;
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
    // EsperEPL2Ast.g:215:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:216:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:216:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1137); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1139);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:216:40: ( IDENT )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==IDENT) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:216:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1141); 

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
    // EsperEPL2Ast.g:219:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:220:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:220:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1161); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:220:25: ( matchRecogPatternAlteration )+
            int cnt63=0;
            loop63:
            do {
                int alt63=2;
                int LA63_0 = input.LA(1);

                if ( ((LA63_0>=MATCHREC_PATTERN_CONCAT && LA63_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt63=1;
                }


                switch (alt63) {
            	case 1 :
            	    // EsperEPL2Ast.g:220:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1163);
            	    matchRecogPatternAlteration();

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
    // $ANTLR end "matchRecogPattern"


    // $ANTLR start "matchRecogPatternAlteration"
    // EsperEPL2Ast.g:223:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:224:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==MATCHREC_PATTERN_CONCAT) ) {
                alt65=1;
            }
            else if ( (LA65_0==MATCHREC_PATTERN_ALTER) ) {
                alt65=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:224:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1178);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:225:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1186); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1188);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:225:55: ( matchRecogPatternConcat )+
                    int cnt64=0;
                    loop64:
                    do {
                        int alt64=2;
                        int LA64_0 = input.LA(1);

                        if ( (LA64_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt64=1;
                        }


                        switch (alt64) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:225:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1190);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt64 >= 1 ) break loop64;
                                EarlyExitException eee =
                                    new EarlyExitException(64, input);
                                throw eee;
                        }
                        cnt64++;
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
    // EsperEPL2Ast.g:228:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:229:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:229:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1208); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:229:32: ( matchRecogPatternUnary )+
            int cnt66=0;
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( (LA66_0==MATCHREC_PATTERN_ATOM||LA66_0==MATCHREC_PATTERN_NESTED) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // EsperEPL2Ast.g:229:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1210);
            	    matchRecogPatternUnary();

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
    // $ANTLR end "matchRecogPatternConcat"


    // $ANTLR start "matchRecogPatternUnary"
    // EsperEPL2Ast.g:232:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:233:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==MATCHREC_PATTERN_NESTED) ) {
                alt67=1;
            }
            else if ( (LA67_0==MATCHREC_PATTERN_ATOM) ) {
                alt67=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 67, 0, input);

                throw nvae;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:233:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1225);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:234:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1230);
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
    // EsperEPL2Ast.g:237:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:238:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:238:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1245); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1247);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:238:60: ( PLUS | STAR | QUESTION )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==STAR||(LA68_0>=PLUS && LA68_0<=QUESTION)) ) {
                alt68=1;
            }
            switch (alt68) {
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
    // EsperEPL2Ast.g:241:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:242:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:242:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1278); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1280); 
            // EsperEPL2Ast.g:242:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==STAR||(LA70_0>=PLUS && LA70_0<=QUESTION)) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:242:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:242:63: ( QUESTION )?
                    int alt69=2;
                    int LA69_0 = input.LA(1);

                    if ( (LA69_0==QUESTION) ) {
                        alt69=1;
                    }
                    switch (alt69) {
                        case 1 :
                            // EsperEPL2Ast.g:242:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1296); 

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
    // EsperEPL2Ast.g:245:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:246:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:246:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1318); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:246:24: ( matchRecogDefineItem )+
            int cnt71=0;
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( (LA71_0==MATCHREC_DEFINE_ITEM) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // EsperEPL2Ast.g:246:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1320);
            	    matchRecogDefineItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt71 >= 1 ) break loop71;
                        EarlyExitException eee =
                            new EarlyExitException(71, input);
                        throw eee;
                }
                cnt71++;
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
    // EsperEPL2Ast.g:249:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:250:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:250:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1337); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1339); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1341);
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
    // EsperEPL2Ast.g:254:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:255:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:255:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1358);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:255:25: ( selectionListElement )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=SELECTION_ELEMENT_EXPR && LA72_0<=SELECTION_STREAM)||LA72_0==WILDCARD_SELECT) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // EsperEPL2Ast.g:255:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1361);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop72;
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
    // EsperEPL2Ast.g:258:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:259:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt75=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt75=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt75=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt75=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 75, 0, input);

                throw nvae;
            }

            switch (alt75) {
                case 1 :
                    // EsperEPL2Ast.g:259:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1377); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:260:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1387); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1389);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:260:41: ( IDENT )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==IDENT) ) {
                        alt73=1;
                    }
                    switch (alt73) {
                        case 1 :
                            // EsperEPL2Ast.g:260:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1392); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:261:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1406); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1408); 
                    // EsperEPL2Ast.g:261:31: ( IDENT )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==IDENT) ) {
                        alt74=1;
                    }
                    switch (alt74) {
                        case 1 :
                            // EsperEPL2Ast.g:261:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1411); 

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
    // EsperEPL2Ast.g:264:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:265:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:265:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1430);
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
    // EsperEPL2Ast.g:268:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:269:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt80=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt80=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt80=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt80=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt80=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:269:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1444); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1446);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1449);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:269:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( (LA76_0==EVENT_PROP_EXPR) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:269:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1453);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1456);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop76;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:270:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1471); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1473);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1476);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:270:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==EVENT_PROP_EXPR) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:270:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1480);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1483);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop77;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:271:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1498); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1500);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1503);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:271:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop78:
                    do {
                        int alt78=2;
                        int LA78_0 = input.LA(1);

                        if ( (LA78_0==EVENT_PROP_EXPR) ) {
                            alt78=1;
                        }


                        switch (alt78) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:271:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1507);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1510);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop78;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:272:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1525); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1527);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1530);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:272:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop79:
                    do {
                        int alt79=2;
                        int LA79_0 = input.LA(1);

                        if ( (LA79_0==EVENT_PROP_EXPR) ) {
                            alt79=1;
                        }


                        switch (alt79) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:272:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1534);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1537);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop79;
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
    // EsperEPL2Ast.g:275:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:276:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:276:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1558); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:276:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt81=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt81=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt81=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt81=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt81=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:276:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1561);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:276:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1565);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:276:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1569);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:276:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1573);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:276:115: ( viewListExpr )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==VIEW_EXPR) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:276:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1577);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:276:131: ( IDENT )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==IDENT) ) {
                alt83=1;
            }
            switch (alt83) {
                case 1 :
                    // EsperEPL2Ast.g:276:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1582); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:276:140: ( UNIDIRECTIONAL )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==UNIDIRECTIONAL) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:276:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1587); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:276:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( ((LA85_0>=RETAINUNION && LA85_0<=RETAININTERSECTION)) ) {
                alt85=1;
            }
            switch (alt85) {
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
    // EsperEPL2Ast.g:279:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:280:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:280:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1615); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:280:27: ( IDENT )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==IDENT) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:280:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1617); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1620); 
            // EsperEPL2Ast.g:280:46: ( propertyExpression )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:280:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1622);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:280:66: ( valueExpr )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( ((LA88_0>=IN_SET && LA88_0<=REGEXP)||LA88_0==NOT_EXPR||(LA88_0>=SUM && LA88_0<=AVG)||(LA88_0>=COALESCE && LA88_0<=COUNT)||(LA88_0>=CASE && LA88_0<=CASE2)||(LA88_0>=PREVIOUS && LA88_0<=EXISTS)||(LA88_0>=INSTANCEOF && LA88_0<=CURRENT_TIMESTAMP)||(LA88_0>=EVAL_AND_EXPR && LA88_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA88_0==EVENT_PROP_EXPR||(LA88_0>=CONCAT && LA88_0<=LIB_FUNCTION)||LA88_0==ARRAY_EXPR||(LA88_0>=NOT_IN_SET && LA88_0<=NOT_REGEXP)||(LA88_0>=IN_RANGE && LA88_0<=SUBSELECT_EXPR)||(LA88_0>=EXISTS_SUBSELECT_EXPR && LA88_0<=NOT_IN_SUBSELECT_EXPR)||LA88_0==SUBSTITUTION||(LA88_0>=FIRST_AGGREG && LA88_0<=LAST_AGGREG)||(LA88_0>=INT_TYPE && LA88_0<=NULL_TYPE)||(LA88_0>=STAR && LA88_0<=PLUS)||(LA88_0>=BAND && LA88_0<=BXOR)||(LA88_0>=LT && LA88_0<=GE)||(LA88_0>=MINUS && LA88_0<=MOD)) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // EsperEPL2Ast.g:280:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1626);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop88;
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
    // EsperEPL2Ast.g:283:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:284:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:284:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1646); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:284:34: ( propertyExpressionAtom )*
                loop89:
                do {
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt89=1;
                    }


                    switch (alt89) {
                	case 1 :
                	    // EsperEPL2Ast.g:284:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1648);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop89;
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
    // EsperEPL2Ast.g:287:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:288:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:288:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1667); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:288:41: ( propertySelectionListElement )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( ((LA90_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA90_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // EsperEPL2Ast.g:288:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1669);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop90;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1672);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:288:96: ( IDENT )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==IDENT) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:288:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1675); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1679); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:288:116: ( valueExpr )?
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( ((LA92_0>=IN_SET && LA92_0<=REGEXP)||LA92_0==NOT_EXPR||(LA92_0>=SUM && LA92_0<=AVG)||(LA92_0>=COALESCE && LA92_0<=COUNT)||(LA92_0>=CASE && LA92_0<=CASE2)||(LA92_0>=PREVIOUS && LA92_0<=EXISTS)||(LA92_0>=INSTANCEOF && LA92_0<=CURRENT_TIMESTAMP)||(LA92_0>=EVAL_AND_EXPR && LA92_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA92_0==EVENT_PROP_EXPR||(LA92_0>=CONCAT && LA92_0<=LIB_FUNCTION)||LA92_0==ARRAY_EXPR||(LA92_0>=NOT_IN_SET && LA92_0<=NOT_REGEXP)||(LA92_0>=IN_RANGE && LA92_0<=SUBSELECT_EXPR)||(LA92_0>=EXISTS_SUBSELECT_EXPR && LA92_0<=NOT_IN_SUBSELECT_EXPR)||LA92_0==SUBSTITUTION||(LA92_0>=FIRST_AGGREG && LA92_0<=LAST_AGGREG)||(LA92_0>=INT_TYPE && LA92_0<=NULL_TYPE)||(LA92_0>=STAR && LA92_0<=PLUS)||(LA92_0>=BAND && LA92_0<=BXOR)||(LA92_0>=LT && LA92_0<=GE)||(LA92_0>=MINUS && LA92_0<=MOD)) ) {
                    alt92=1;
                }
                switch (alt92) {
                    case 1 :
                        // EsperEPL2Ast.g:288:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1681);
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
    // EsperEPL2Ast.g:291:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:292:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt95=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt95=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt95=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt95=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }

            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:292:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1701); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:293:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1711); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1713);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:293:50: ( IDENT )?
                    int alt93=2;
                    int LA93_0 = input.LA(1);

                    if ( (LA93_0==IDENT) ) {
                        alt93=1;
                    }
                    switch (alt93) {
                        case 1 :
                            // EsperEPL2Ast.g:293:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1716); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:294:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1730); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1732); 
                    // EsperEPL2Ast.g:294:40: ( IDENT )?
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==IDENT) ) {
                        alt94=1;
                    }
                    switch (alt94) {
                        case 1 :
                            // EsperEPL2Ast.g:294:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1735); 

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
    // EsperEPL2Ast.g:297:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:298:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:298:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1756); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1758);
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
    // EsperEPL2Ast.g:301:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:302:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:302:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1775); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1777); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:302:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( ((LA96_0>=STRING_LITERAL && LA96_0<=QUOTED_STRING_LITERAL)) ) {
                alt96=1;
            }
            switch (alt96) {
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
    // EsperEPL2Ast.g:305:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:306:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:306:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1808); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1810); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1812); 
            // EsperEPL2Ast.g:306:41: ( valueExpr )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=INSTANCEOF && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||LA97_0==ARRAY_EXPR||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=SUBSELECT_EXPR)||(LA97_0>=EXISTS_SUBSELECT_EXPR && LA97_0<=NOT_IN_SUBSELECT_EXPR)||LA97_0==SUBSTITUTION||(LA97_0>=FIRST_AGGREG && LA97_0<=LAST_AGGREG)||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||(LA97_0>=STAR && LA97_0<=PLUS)||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=MINUS && LA97_0<=MOD)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // EsperEPL2Ast.g:306:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1815);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop97;
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
    // EsperEPL2Ast.g:309:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:310:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:310:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1829);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:310:13: ( viewExpr )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( (LA98_0==VIEW_EXPR) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // EsperEPL2Ast.g:310:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1832);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop98;
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
    // EsperEPL2Ast.g:313:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:314:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:314:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1849); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1851); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1853); 
            // EsperEPL2Ast.g:314:30: ( valueExprWithTime )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( ((LA99_0>=IN_SET && LA99_0<=REGEXP)||LA99_0==NOT_EXPR||(LA99_0>=SUM && LA99_0<=AVG)||(LA99_0>=COALESCE && LA99_0<=COUNT)||(LA99_0>=CASE && LA99_0<=CASE2)||LA99_0==LAST||(LA99_0>=PREVIOUS && LA99_0<=EXISTS)||(LA99_0>=LW && LA99_0<=CURRENT_TIMESTAMP)||(LA99_0>=NUMERIC_PARAM_RANGE && LA99_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA99_0>=EVAL_AND_EXPR && LA99_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA99_0==EVENT_PROP_EXPR||(LA99_0>=CONCAT && LA99_0<=LIB_FUNCTION)||(LA99_0>=TIME_PERIOD && LA99_0<=ARRAY_EXPR)||(LA99_0>=NOT_IN_SET && LA99_0<=NOT_REGEXP)||(LA99_0>=IN_RANGE && LA99_0<=SUBSELECT_EXPR)||(LA99_0>=EXISTS_SUBSELECT_EXPR && LA99_0<=NOT_IN_SUBSELECT_EXPR)||(LA99_0>=LAST_OPERATOR && LA99_0<=SUBSTITUTION)||LA99_0==NUMBERSETSTAR||(LA99_0>=FIRST_AGGREG && LA99_0<=LAST_AGGREG)||(LA99_0>=INT_TYPE && LA99_0<=NULL_TYPE)||(LA99_0>=STAR && LA99_0<=PLUS)||(LA99_0>=BAND && LA99_0<=BXOR)||(LA99_0>=LT && LA99_0<=GE)||(LA99_0>=MINUS && LA99_0<=MOD)) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // EsperEPL2Ast.g:314:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1856);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop99;
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
    // EsperEPL2Ast.g:317:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:318:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:318:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1878); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1880);
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
    // EsperEPL2Ast.g:321:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:322:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:322:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1898); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1900);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:322:32: ( valueExpr )*
            loop100:
            do {
                int alt100=2;
                int LA100_0 = input.LA(1);

                if ( ((LA100_0>=IN_SET && LA100_0<=REGEXP)||LA100_0==NOT_EXPR||(LA100_0>=SUM && LA100_0<=AVG)||(LA100_0>=COALESCE && LA100_0<=COUNT)||(LA100_0>=CASE && LA100_0<=CASE2)||(LA100_0>=PREVIOUS && LA100_0<=EXISTS)||(LA100_0>=INSTANCEOF && LA100_0<=CURRENT_TIMESTAMP)||(LA100_0>=EVAL_AND_EXPR && LA100_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA100_0==EVENT_PROP_EXPR||(LA100_0>=CONCAT && LA100_0<=LIB_FUNCTION)||LA100_0==ARRAY_EXPR||(LA100_0>=NOT_IN_SET && LA100_0<=NOT_REGEXP)||(LA100_0>=IN_RANGE && LA100_0<=SUBSELECT_EXPR)||(LA100_0>=EXISTS_SUBSELECT_EXPR && LA100_0<=NOT_IN_SUBSELECT_EXPR)||LA100_0==SUBSTITUTION||(LA100_0>=FIRST_AGGREG && LA100_0<=LAST_AGGREG)||(LA100_0>=INT_TYPE && LA100_0<=NULL_TYPE)||(LA100_0>=STAR && LA100_0<=PLUS)||(LA100_0>=BAND && LA100_0<=BXOR)||(LA100_0>=LT && LA100_0<=GE)||(LA100_0>=MINUS && LA100_0<=MOD)) ) {
                    alt100=1;
                }


                switch (alt100) {
            	case 1 :
            	    // EsperEPL2Ast.g:322:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1903);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop100;
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
    // EsperEPL2Ast.g:325:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:326:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:326:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1921); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1923);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:326:35: ( orderByElement )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( (LA101_0==ORDER_ELEMENT_EXPR) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // EsperEPL2Ast.g:326:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1926);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop101;
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
    // EsperEPL2Ast.g:329:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:330:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:330:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1946); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1948);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:330:38: ( ASC | DESC )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( ((LA102_0>=ASC && LA102_0<=DESC)) ) {
                alt102=1;
            }
            switch (alt102) {
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
    // EsperEPL2Ast.g:333:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:334:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:334:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1973); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1975);
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
    // EsperEPL2Ast.g:337:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:338:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt113=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt113=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt113=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt113=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt113=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt113=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:338:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1993); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:338:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt103=2;
                    int LA103_0 = input.LA(1);

                    if ( (LA103_0==ALL||(LA103_0>=FIRST && LA103_0<=LAST)||LA103_0==SNAPSHOT) ) {
                        alt103=1;
                    }
                    switch (alt103) {
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

                    // EsperEPL2Ast.g:338:52: ( number | IDENT )
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( ((LA104_0>=INT_TYPE && LA104_0<=DOUBLE_TYPE)) ) {
                        alt104=1;
                    }
                    else if ( (LA104_0==IDENT) ) {
                        alt104=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 104, 0, input);

                        throw nvae;
                    }
                    switch (alt104) {
                        case 1 :
                            // EsperEPL2Ast.g:338:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2007);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:338:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2009); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:338:67: ( outputLimitAfter )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==AFTER) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // EsperEPL2Ast.g:338:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2012);
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
                    // EsperEPL2Ast.g:339:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2029); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:339:34: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2042);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:339:72: ( outputLimitAfter )?
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( (LA107_0==AFTER) ) {
                        alt107=1;
                    }
                    switch (alt107) {
                        case 1 :
                            // EsperEPL2Ast.g:339:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2044);
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
                    // EsperEPL2Ast.g:340:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2060); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:340:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt108=2;
                    int LA108_0 = input.LA(1);

                    if ( (LA108_0==ALL||(LA108_0>=FIRST && LA108_0<=LAST)||LA108_0==SNAPSHOT) ) {
                        alt108=1;
                    }
                    switch (alt108) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2073);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:340:85: ( outputLimitAfter )?
                    int alt109=2;
                    int LA109_0 = input.LA(1);

                    if ( (LA109_0==AFTER) ) {
                        alt109=1;
                    }
                    switch (alt109) {
                        case 1 :
                            // EsperEPL2Ast.g:340:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2075);
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
                    // EsperEPL2Ast.g:341:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2091); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:341:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==ALL||(LA110_0>=FIRST && LA110_0<=LAST)||LA110_0==SNAPSHOT) ) {
                        alt110=1;
                    }
                    switch (alt110) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2104);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:341:67: ( onSetExpr )?
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( (LA111_0==ON_SET_EXPR) ) {
                        alt111=1;
                    }
                    switch (alt111) {
                        case 1 :
                            // EsperEPL2Ast.g:341:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2106);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:341:78: ( outputLimitAfter )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==AFTER) ) {
                        alt112=1;
                    }
                    switch (alt112) {
                        case 1 :
                            // EsperEPL2Ast.g:341:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2109);
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
                    // EsperEPL2Ast.g:342:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2122); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2124);
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
    // EsperEPL2Ast.g:345:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:346:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:346:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2139); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:346:12: ( timePeriod )?
                int alt114=2;
                int LA114_0 = input.LA(1);

                if ( (LA114_0==TIME_PERIOD) ) {
                    alt114=1;
                }
                switch (alt114) {
                    case 1 :
                        // EsperEPL2Ast.g:346:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2141);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:346:24: ( number )?
                int alt115=2;
                int LA115_0 = input.LA(1);

                if ( ((LA115_0>=INT_TYPE && LA115_0<=DOUBLE_TYPE)) ) {
                    alt115=1;
                }
                switch (alt115) {
                    case 1 :
                        // EsperEPL2Ast.g:346:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2144);
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
    // EsperEPL2Ast.g:349:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:350:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:350:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2160); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:350:23: ( number | IDENT )
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( ((LA116_0>=INT_TYPE && LA116_0<=DOUBLE_TYPE)) ) {
                alt116=1;
            }
            else if ( (LA116_0==IDENT) ) {
                alt116=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }
            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:350:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2163);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:350:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2165); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:350:38: ( number | IDENT )?
            int alt117=3;
            int LA117_0 = input.LA(1);

            if ( ((LA117_0>=INT_TYPE && LA117_0<=DOUBLE_TYPE)) ) {
                alt117=1;
            }
            else if ( (LA117_0==IDENT) ) {
                alt117=2;
            }
            switch (alt117) {
                case 1 :
                    // EsperEPL2Ast.g:350:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2169);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:350:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2171); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:350:54: ( COMMA )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==COMMA) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // EsperEPL2Ast.g:350:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2175); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:350:61: ( OFFSET )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==OFFSET) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // EsperEPL2Ast.g:350:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2178); 

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
    // EsperEPL2Ast.g:353:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:354:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:354:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2196); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2198);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2200);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2202);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2204);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2206);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:354:121: ( valueExprWithTime )?
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( ((LA120_0>=IN_SET && LA120_0<=REGEXP)||LA120_0==NOT_EXPR||(LA120_0>=SUM && LA120_0<=AVG)||(LA120_0>=COALESCE && LA120_0<=COUNT)||(LA120_0>=CASE && LA120_0<=CASE2)||LA120_0==LAST||(LA120_0>=PREVIOUS && LA120_0<=EXISTS)||(LA120_0>=LW && LA120_0<=CURRENT_TIMESTAMP)||(LA120_0>=NUMERIC_PARAM_RANGE && LA120_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA120_0>=EVAL_AND_EXPR && LA120_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA120_0==EVENT_PROP_EXPR||(LA120_0>=CONCAT && LA120_0<=LIB_FUNCTION)||(LA120_0>=TIME_PERIOD && LA120_0<=ARRAY_EXPR)||(LA120_0>=NOT_IN_SET && LA120_0<=NOT_REGEXP)||(LA120_0>=IN_RANGE && LA120_0<=SUBSELECT_EXPR)||(LA120_0>=EXISTS_SUBSELECT_EXPR && LA120_0<=NOT_IN_SUBSELECT_EXPR)||(LA120_0>=LAST_OPERATOR && LA120_0<=SUBSTITUTION)||LA120_0==NUMBERSETSTAR||(LA120_0>=FIRST_AGGREG && LA120_0<=LAST_AGGREG)||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)||(LA120_0>=STAR && LA120_0<=PLUS)||(LA120_0>=BAND && LA120_0<=BXOR)||(LA120_0>=LT && LA120_0<=GE)||(LA120_0>=MINUS && LA120_0<=MOD)) ) {
                alt120=1;
            }
            switch (alt120) {
                case 1 :
                    // EsperEPL2Ast.g:354:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2208);
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
    // EsperEPL2Ast.g:357:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:358:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt121=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt121=1;
                }
                break;
            case GT:
                {
                alt121=2;
                }
                break;
            case LE:
                {
                alt121=3;
                }
                break;
            case GE:
                {
                alt121=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 121, 0, input);

                throw nvae;
            }

            switch (alt121) {
                case 1 :
                    // EsperEPL2Ast.g:358:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2225); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2227);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:359:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2240); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2242);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:360:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2255); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2257);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:361:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2269); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2271);
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
    // EsperEPL2Ast.g:364:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:365:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:365:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:365:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:366:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2293);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:367:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt124=2;
            int LA124_0 = input.LA(1);

            if ( ((LA124_0>=IN_SET && LA124_0<=REGEXP)||LA124_0==NOT_EXPR||(LA124_0>=SUM && LA124_0<=AVG)||(LA124_0>=COALESCE && LA124_0<=COUNT)||(LA124_0>=CASE && LA124_0<=CASE2)||(LA124_0>=PREVIOUS && LA124_0<=EXISTS)||(LA124_0>=INSTANCEOF && LA124_0<=CURRENT_TIMESTAMP)||(LA124_0>=EVAL_AND_EXPR && LA124_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA124_0==EVENT_PROP_EXPR||(LA124_0>=CONCAT && LA124_0<=LIB_FUNCTION)||LA124_0==ARRAY_EXPR||(LA124_0>=NOT_IN_SET && LA124_0<=NOT_REGEXP)||(LA124_0>=IN_RANGE && LA124_0<=SUBSELECT_EXPR)||(LA124_0>=EXISTS_SUBSELECT_EXPR && LA124_0<=NOT_IN_SUBSELECT_EXPR)||LA124_0==SUBSTITUTION||(LA124_0>=FIRST_AGGREG && LA124_0<=LAST_AGGREG)||(LA124_0>=INT_TYPE && LA124_0<=NULL_TYPE)||(LA124_0>=STAR && LA124_0<=PLUS)||(LA124_0>=BAND && LA124_0<=BXOR)||(LA124_0>=LT && LA124_0<=GE)||(LA124_0>=MINUS && LA124_0<=MOD)) ) {
                alt124=1;
            }
            else if ( ((LA124_0>=ALL && LA124_0<=SOME)) ) {
                alt124=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }
            switch (alt124) {
                case 1 :
                    // EsperEPL2Ast.g:367:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2303);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:369:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:369:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( (LA123_0==UP||(LA123_0>=IN_SET && LA123_0<=REGEXP)||LA123_0==NOT_EXPR||(LA123_0>=SUM && LA123_0<=AVG)||(LA123_0>=COALESCE && LA123_0<=COUNT)||(LA123_0>=CASE && LA123_0<=CASE2)||(LA123_0>=PREVIOUS && LA123_0<=EXISTS)||(LA123_0>=INSTANCEOF && LA123_0<=CURRENT_TIMESTAMP)||(LA123_0>=EVAL_AND_EXPR && LA123_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA123_0==EVENT_PROP_EXPR||(LA123_0>=CONCAT && LA123_0<=LIB_FUNCTION)||LA123_0==ARRAY_EXPR||(LA123_0>=NOT_IN_SET && LA123_0<=NOT_REGEXP)||(LA123_0>=IN_RANGE && LA123_0<=SUBSELECT_EXPR)||(LA123_0>=EXISTS_SUBSELECT_EXPR && LA123_0<=NOT_IN_SUBSELECT_EXPR)||LA123_0==SUBSTITUTION||(LA123_0>=FIRST_AGGREG && LA123_0<=LAST_AGGREG)||(LA123_0>=INT_TYPE && LA123_0<=NULL_TYPE)||(LA123_0>=STAR && LA123_0<=PLUS)||(LA123_0>=BAND && LA123_0<=BXOR)||(LA123_0>=LT && LA123_0<=GE)||(LA123_0>=MINUS && LA123_0<=MOD)) ) {
                        alt123=1;
                    }
                    else if ( (LA123_0==SUBSELECT_GROUP_EXPR) ) {
                        alt123=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 123, 0, input);

                        throw nvae;
                    }
                    switch (alt123) {
                        case 1 :
                            // EsperEPL2Ast.g:369:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:369:22: ( valueExpr )*
                            loop122:
                            do {
                                int alt122=2;
                                int LA122_0 = input.LA(1);

                                if ( ((LA122_0>=IN_SET && LA122_0<=REGEXP)||LA122_0==NOT_EXPR||(LA122_0>=SUM && LA122_0<=AVG)||(LA122_0>=COALESCE && LA122_0<=COUNT)||(LA122_0>=CASE && LA122_0<=CASE2)||(LA122_0>=PREVIOUS && LA122_0<=EXISTS)||(LA122_0>=INSTANCEOF && LA122_0<=CURRENT_TIMESTAMP)||(LA122_0>=EVAL_AND_EXPR && LA122_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA122_0==EVENT_PROP_EXPR||(LA122_0>=CONCAT && LA122_0<=LIB_FUNCTION)||LA122_0==ARRAY_EXPR||(LA122_0>=NOT_IN_SET && LA122_0<=NOT_REGEXP)||(LA122_0>=IN_RANGE && LA122_0<=SUBSELECT_EXPR)||(LA122_0>=EXISTS_SUBSELECT_EXPR && LA122_0<=NOT_IN_SUBSELECT_EXPR)||LA122_0==SUBSTITUTION||(LA122_0>=FIRST_AGGREG && LA122_0<=LAST_AGGREG)||(LA122_0>=INT_TYPE && LA122_0<=NULL_TYPE)||(LA122_0>=STAR && LA122_0<=PLUS)||(LA122_0>=BAND && LA122_0<=BXOR)||(LA122_0>=LT && LA122_0<=GE)||(LA122_0>=MINUS && LA122_0<=MOD)) ) {
                                    alt122=1;
                                }


                                switch (alt122) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:369:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2327);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop122;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:369:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2332);
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
    // EsperEPL2Ast.g:374:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:375:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt131=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt131=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt131=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt131=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt131=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt131=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt131=6;
                }
                break;
            case NOT_EXPR:
                {
                alt131=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt131=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 131, 0, input);

                throw nvae;
            }

            switch (alt131) {
                case 1 :
                    // EsperEPL2Ast.g:375:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2358); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2360);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2362);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:375:42: ( valueExpr )*
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( ((LA125_0>=IN_SET && LA125_0<=REGEXP)||LA125_0==NOT_EXPR||(LA125_0>=SUM && LA125_0<=AVG)||(LA125_0>=COALESCE && LA125_0<=COUNT)||(LA125_0>=CASE && LA125_0<=CASE2)||(LA125_0>=PREVIOUS && LA125_0<=EXISTS)||(LA125_0>=INSTANCEOF && LA125_0<=CURRENT_TIMESTAMP)||(LA125_0>=EVAL_AND_EXPR && LA125_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA125_0==EVENT_PROP_EXPR||(LA125_0>=CONCAT && LA125_0<=LIB_FUNCTION)||LA125_0==ARRAY_EXPR||(LA125_0>=NOT_IN_SET && LA125_0<=NOT_REGEXP)||(LA125_0>=IN_RANGE && LA125_0<=SUBSELECT_EXPR)||(LA125_0>=EXISTS_SUBSELECT_EXPR && LA125_0<=NOT_IN_SUBSELECT_EXPR)||LA125_0==SUBSTITUTION||(LA125_0>=FIRST_AGGREG && LA125_0<=LAST_AGGREG)||(LA125_0>=INT_TYPE && LA125_0<=NULL_TYPE)||(LA125_0>=STAR && LA125_0<=PLUS)||(LA125_0>=BAND && LA125_0<=BXOR)||(LA125_0>=LT && LA125_0<=GE)||(LA125_0>=MINUS && LA125_0<=MOD)) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:375:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2365);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop125;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:376:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2379); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2381);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2383);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:376:43: ( valueExpr )*
                    loop126:
                    do {
                        int alt126=2;
                        int LA126_0 = input.LA(1);

                        if ( ((LA126_0>=IN_SET && LA126_0<=REGEXP)||LA126_0==NOT_EXPR||(LA126_0>=SUM && LA126_0<=AVG)||(LA126_0>=COALESCE && LA126_0<=COUNT)||(LA126_0>=CASE && LA126_0<=CASE2)||(LA126_0>=PREVIOUS && LA126_0<=EXISTS)||(LA126_0>=INSTANCEOF && LA126_0<=CURRENT_TIMESTAMP)||(LA126_0>=EVAL_AND_EXPR && LA126_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA126_0==EVENT_PROP_EXPR||(LA126_0>=CONCAT && LA126_0<=LIB_FUNCTION)||LA126_0==ARRAY_EXPR||(LA126_0>=NOT_IN_SET && LA126_0<=NOT_REGEXP)||(LA126_0>=IN_RANGE && LA126_0<=SUBSELECT_EXPR)||(LA126_0>=EXISTS_SUBSELECT_EXPR && LA126_0<=NOT_IN_SUBSELECT_EXPR)||LA126_0==SUBSTITUTION||(LA126_0>=FIRST_AGGREG && LA126_0<=LAST_AGGREG)||(LA126_0>=INT_TYPE && LA126_0<=NULL_TYPE)||(LA126_0>=STAR && LA126_0<=PLUS)||(LA126_0>=BAND && LA126_0<=BXOR)||(LA126_0>=LT && LA126_0<=GE)||(LA126_0>=MINUS && LA126_0<=MOD)) ) {
                            alt126=1;
                        }


                        switch (alt126) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:376:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2386);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop126;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:377:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2400); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2402);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2404);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:378:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2416); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2418);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2420);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:379:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2432); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2434);
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

                    // EsperEPL2Ast.g:379:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt128=2;
                    int LA128_0 = input.LA(1);

                    if ( (LA128_0==UP||(LA128_0>=IN_SET && LA128_0<=REGEXP)||LA128_0==NOT_EXPR||(LA128_0>=SUM && LA128_0<=AVG)||(LA128_0>=COALESCE && LA128_0<=COUNT)||(LA128_0>=CASE && LA128_0<=CASE2)||(LA128_0>=PREVIOUS && LA128_0<=EXISTS)||(LA128_0>=INSTANCEOF && LA128_0<=CURRENT_TIMESTAMP)||(LA128_0>=EVAL_AND_EXPR && LA128_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA128_0==EVENT_PROP_EXPR||(LA128_0>=CONCAT && LA128_0<=LIB_FUNCTION)||LA128_0==ARRAY_EXPR||(LA128_0>=NOT_IN_SET && LA128_0<=NOT_REGEXP)||(LA128_0>=IN_RANGE && LA128_0<=SUBSELECT_EXPR)||(LA128_0>=EXISTS_SUBSELECT_EXPR && LA128_0<=NOT_IN_SUBSELECT_EXPR)||LA128_0==SUBSTITUTION||(LA128_0>=FIRST_AGGREG && LA128_0<=LAST_AGGREG)||(LA128_0>=INT_TYPE && LA128_0<=NULL_TYPE)||(LA128_0>=STAR && LA128_0<=PLUS)||(LA128_0>=BAND && LA128_0<=BXOR)||(LA128_0>=LT && LA128_0<=GE)||(LA128_0>=MINUS && LA128_0<=MOD)) ) {
                        alt128=1;
                    }
                    else if ( (LA128_0==SUBSELECT_GROUP_EXPR) ) {
                        alt128=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 128, 0, input);

                        throw nvae;
                    }
                    switch (alt128) {
                        case 1 :
                            // EsperEPL2Ast.g:379:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:379:59: ( valueExpr )*
                            loop127:
                            do {
                                int alt127=2;
                                int LA127_0 = input.LA(1);

                                if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA127_0==EVENT_PROP_EXPR||(LA127_0>=CONCAT && LA127_0<=LIB_FUNCTION)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=SUBSELECT_EXPR)||(LA127_0>=EXISTS_SUBSELECT_EXPR && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=FIRST_AGGREG && LA127_0<=LAST_AGGREG)||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||(LA127_0>=STAR && LA127_0<=PLUS)||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=MINUS && LA127_0<=MOD)) ) {
                                    alt127=1;
                                }


                                switch (alt127) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:379:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2445);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop127;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:379:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2450);
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
                    // EsperEPL2Ast.g:380:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2463); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2465);
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

                    // EsperEPL2Ast.g:380:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( (LA130_0==UP||(LA130_0>=IN_SET && LA130_0<=REGEXP)||LA130_0==NOT_EXPR||(LA130_0>=SUM && LA130_0<=AVG)||(LA130_0>=COALESCE && LA130_0<=COUNT)||(LA130_0>=CASE && LA130_0<=CASE2)||(LA130_0>=PREVIOUS && LA130_0<=EXISTS)||(LA130_0>=INSTANCEOF && LA130_0<=CURRENT_TIMESTAMP)||(LA130_0>=EVAL_AND_EXPR && LA130_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA130_0==EVENT_PROP_EXPR||(LA130_0>=CONCAT && LA130_0<=LIB_FUNCTION)||LA130_0==ARRAY_EXPR||(LA130_0>=NOT_IN_SET && LA130_0<=NOT_REGEXP)||(LA130_0>=IN_RANGE && LA130_0<=SUBSELECT_EXPR)||(LA130_0>=EXISTS_SUBSELECT_EXPR && LA130_0<=NOT_IN_SUBSELECT_EXPR)||LA130_0==SUBSTITUTION||(LA130_0>=FIRST_AGGREG && LA130_0<=LAST_AGGREG)||(LA130_0>=INT_TYPE && LA130_0<=NULL_TYPE)||(LA130_0>=STAR && LA130_0<=PLUS)||(LA130_0>=BAND && LA130_0<=BXOR)||(LA130_0>=LT && LA130_0<=GE)||(LA130_0>=MINUS && LA130_0<=MOD)) ) {
                        alt130=1;
                    }
                    else if ( (LA130_0==SUBSELECT_GROUP_EXPR) ) {
                        alt130=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 130, 0, input);

                        throw nvae;
                    }
                    switch (alt130) {
                        case 1 :
                            // EsperEPL2Ast.g:380:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:380:63: ( valueExpr )*
                            loop129:
                            do {
                                int alt129=2;
                                int LA129_0 = input.LA(1);

                                if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=INSTANCEOF && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA129_0==EVENT_PROP_EXPR||(LA129_0>=CONCAT && LA129_0<=LIB_FUNCTION)||LA129_0==ARRAY_EXPR||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=SUBSELECT_EXPR)||(LA129_0>=EXISTS_SUBSELECT_EXPR && LA129_0<=NOT_IN_SUBSELECT_EXPR)||LA129_0==SUBSTITUTION||(LA129_0>=FIRST_AGGREG && LA129_0<=LAST_AGGREG)||(LA129_0>=INT_TYPE && LA129_0<=NULL_TYPE)||(LA129_0>=STAR && LA129_0<=PLUS)||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=MINUS && LA129_0<=MOD)) ) {
                                    alt129=1;
                                }


                                switch (alt129) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:380:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2476);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop129;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:380:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2481);
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
                    // EsperEPL2Ast.g:381:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2494); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2496);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:382:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2507);
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
    // EsperEPL2Ast.g:385:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:386:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt132=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt132=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt132=2;
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
                alt132=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt132=4;
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
                alt132=5;
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
                alt132=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt132=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt132=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt132=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt132=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt132=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt132=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt132=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt132=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt132=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt132=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }

            switch (alt132) {
                case 1 :
                    // EsperEPL2Ast.g:386:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2520);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:387:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2526);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:388:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2532);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:389:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2539);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:390:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2548);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:391:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2553);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:392:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2561);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:393:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2566);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:394:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2571);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:395:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2577);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:396:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2582);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:397:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2587);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:398:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2592);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:399:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2597);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:400:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2603);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:401:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2610);
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
    // EsperEPL2Ast.g:404:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:405:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt134=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt134=1;
                }
                break;
            case LW:
                {
                alt134=2;
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
                alt134=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt134=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt134=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt134=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt134=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt134=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt134=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt134=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt134=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 134, 0, input);

                throw nvae;
            }

            switch (alt134) {
                case 1 :
                    // EsperEPL2Ast.g:405:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2623); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:406:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2632); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:407:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2639);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:408:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2647); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2649);
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
                    // EsperEPL2Ast.g:409:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2664);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:410:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2670);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:411:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2675);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:412:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2680);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:413:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2690); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:413:29: ( numericParameterList )+
                    int cnt133=0;
                    loop133:
                    do {
                        int alt133=2;
                        int LA133_0 = input.LA(1);

                        if ( (LA133_0==NUMERIC_PARAM_RANGE||LA133_0==NUMERIC_PARAM_FREQUENCY||(LA133_0>=INT_TYPE && LA133_0<=NULL_TYPE)) ) {
                            alt133=1;
                        }


                        switch (alt133) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:413:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2692);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt133 >= 1 ) break loop133;
                                EarlyExitException eee =
                                    new EarlyExitException(133, input);
                                throw eee;
                        }
                        cnt133++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:414:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2703); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:415:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2710);
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
    // EsperEPL2Ast.g:418:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:419:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt135=3;
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
            case NUMERIC_PARAM_RANGE:
                {
                alt135=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt135=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 135, 0, input);

                throw nvae;
            }

            switch (alt135) {
                case 1 :
                    // EsperEPL2Ast.g:419:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2723);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:420:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2730);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:421:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2736);
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
    // EsperEPL2Ast.g:424:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:425:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:425:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2752); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:425:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt136=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt136=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt136=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt136=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 136, 0, input);

                throw nvae;
            }

            switch (alt136) {
                case 1 :
                    // EsperEPL2Ast.g:425:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2755);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:425:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2758);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:425:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2761);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:425:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt137=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt137=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt137=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt137=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 137, 0, input);

                throw nvae;
            }

            switch (alt137) {
                case 1 :
                    // EsperEPL2Ast.g:425:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2765);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:425:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2768);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:425:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2771);
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
    // EsperEPL2Ast.g:428:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:429:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:429:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2792); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:429:33: ( constant[true] | eventPropertyExpr[true] | substitution )
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
            case EVENT_PROP_EXPR:
                {
                alt138=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:429:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2795);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:429:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2798);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:429:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2801);
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
    // EsperEPL2Ast.g:432:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:433:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:433:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2820); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:433:23: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:433:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2823);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:433:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2826);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:433:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2829);
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
    // EsperEPL2Ast.g:436:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:437:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:437:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2848); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:437:26: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:437:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2851);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:437:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2854);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:437:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2857);
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
    // EsperEPL2Ast.g:440:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:441:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:441:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2878); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr2880);
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
    // EsperEPL2Ast.g:444:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:445:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:445:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2899); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2901);
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
    // EsperEPL2Ast.g:448:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:449:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:449:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2920); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2922);
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
    // EsperEPL2Ast.g:452:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:453:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==IN_SUBSELECT_EXPR) ) {
                alt141=1;
            }
            else if ( (LA141_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt141=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 141, 0, input);

                throw nvae;
            }
            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:453:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2941); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2943);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2945);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:454:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2957); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2959);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2961);
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
    // EsperEPL2Ast.g:457:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:458:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:458:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2980); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2982);
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
    // EsperEPL2Ast.g:461:1: subQueryExpr : ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:462:2: ( ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:462:4: ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:462:4: ( DISTINCT )?
            int alt142=2;
            int LA142_0 = input.LA(1);

            if ( (LA142_0==DISTINCT) ) {
                alt142=1;
            }
            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:462:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr2998); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr3001);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3003);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:462:55: ( whereClause[true] )?
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( (LA143_0==WHERE_EXPR) ) {
                alt143=1;
            }
            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:462:56: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3006);
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
    // EsperEPL2Ast.g:465:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:466:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:466:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3024); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3026);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:466:36: ( viewListExpr )?
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( (LA144_0==VIEW_EXPR) ) {
                alt144=1;
            }
            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:466:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3029);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:466:52: ( IDENT )?
            int alt145=2;
            int LA145_0 = input.LA(1);

            if ( (LA145_0==IDENT) ) {
                alt145=1;
            }
            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:466:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3034); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:466:61: ( RETAINUNION )?
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( (LA146_0==RETAINUNION) ) {
                alt146=1;
            }
            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:466:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3038); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:466:74: ( RETAININTERSECTION )?
            int alt147=2;
            int LA147_0 = input.LA(1);

            if ( (LA147_0==RETAININTERSECTION) ) {
                alt147=1;
            }
            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:466:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3041); 

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
    // EsperEPL2Ast.g:469:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:470:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( (LA150_0==CASE) ) {
                alt150=1;
            }
            else if ( (LA150_0==CASE2) ) {
                alt150=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }
            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:470:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3061); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:470:13: ( valueExpr )*
                        loop148:
                        do {
                            int alt148=2;
                            int LA148_0 = input.LA(1);

                            if ( ((LA148_0>=IN_SET && LA148_0<=REGEXP)||LA148_0==NOT_EXPR||(LA148_0>=SUM && LA148_0<=AVG)||(LA148_0>=COALESCE && LA148_0<=COUNT)||(LA148_0>=CASE && LA148_0<=CASE2)||(LA148_0>=PREVIOUS && LA148_0<=EXISTS)||(LA148_0>=INSTANCEOF && LA148_0<=CURRENT_TIMESTAMP)||(LA148_0>=EVAL_AND_EXPR && LA148_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA148_0==EVENT_PROP_EXPR||(LA148_0>=CONCAT && LA148_0<=LIB_FUNCTION)||LA148_0==ARRAY_EXPR||(LA148_0>=NOT_IN_SET && LA148_0<=NOT_REGEXP)||(LA148_0>=IN_RANGE && LA148_0<=SUBSELECT_EXPR)||(LA148_0>=EXISTS_SUBSELECT_EXPR && LA148_0<=NOT_IN_SUBSELECT_EXPR)||LA148_0==SUBSTITUTION||(LA148_0>=FIRST_AGGREG && LA148_0<=LAST_AGGREG)||(LA148_0>=INT_TYPE && LA148_0<=NULL_TYPE)||(LA148_0>=STAR && LA148_0<=PLUS)||(LA148_0>=BAND && LA148_0<=BXOR)||(LA148_0>=LT && LA148_0<=GE)||(LA148_0>=MINUS && LA148_0<=MOD)) ) {
                                alt148=1;
                            }


                            switch (alt148) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:470:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3064);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop148;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:471:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3077); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:471:14: ( valueExpr )*
                        loop149:
                        do {
                            int alt149=2;
                            int LA149_0 = input.LA(1);

                            if ( ((LA149_0>=IN_SET && LA149_0<=REGEXP)||LA149_0==NOT_EXPR||(LA149_0>=SUM && LA149_0<=AVG)||(LA149_0>=COALESCE && LA149_0<=COUNT)||(LA149_0>=CASE && LA149_0<=CASE2)||(LA149_0>=PREVIOUS && LA149_0<=EXISTS)||(LA149_0>=INSTANCEOF && LA149_0<=CURRENT_TIMESTAMP)||(LA149_0>=EVAL_AND_EXPR && LA149_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA149_0==EVENT_PROP_EXPR||(LA149_0>=CONCAT && LA149_0<=LIB_FUNCTION)||LA149_0==ARRAY_EXPR||(LA149_0>=NOT_IN_SET && LA149_0<=NOT_REGEXP)||(LA149_0>=IN_RANGE && LA149_0<=SUBSELECT_EXPR)||(LA149_0>=EXISTS_SUBSELECT_EXPR && LA149_0<=NOT_IN_SUBSELECT_EXPR)||LA149_0==SUBSTITUTION||(LA149_0>=FIRST_AGGREG && LA149_0<=LAST_AGGREG)||(LA149_0>=INT_TYPE && LA149_0<=NULL_TYPE)||(LA149_0>=STAR && LA149_0<=PLUS)||(LA149_0>=BAND && LA149_0<=BXOR)||(LA149_0>=LT && LA149_0<=GE)||(LA149_0>=MINUS && LA149_0<=MOD)) ) {
                                alt149=1;
                            }


                            switch (alt149) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:471:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3080);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop149;
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
    // EsperEPL2Ast.g:474:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:475:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt153=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt153=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt153=2;
                }
                break;
            case IN_RANGE:
                {
                alt153=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt153=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 153, 0, input);

                throw nvae;
            }

            switch (alt153) {
                case 1 :
                    // EsperEPL2Ast.g:475:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3100); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3102);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3110);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:475:51: ( valueExpr )*
                    loop151:
                    do {
                        int alt151=2;
                        int LA151_0 = input.LA(1);

                        if ( ((LA151_0>=IN_SET && LA151_0<=REGEXP)||LA151_0==NOT_EXPR||(LA151_0>=SUM && LA151_0<=AVG)||(LA151_0>=COALESCE && LA151_0<=COUNT)||(LA151_0>=CASE && LA151_0<=CASE2)||(LA151_0>=PREVIOUS && LA151_0<=EXISTS)||(LA151_0>=INSTANCEOF && LA151_0<=CURRENT_TIMESTAMP)||(LA151_0>=EVAL_AND_EXPR && LA151_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA151_0==EVENT_PROP_EXPR||(LA151_0>=CONCAT && LA151_0<=LIB_FUNCTION)||LA151_0==ARRAY_EXPR||(LA151_0>=NOT_IN_SET && LA151_0<=NOT_REGEXP)||(LA151_0>=IN_RANGE && LA151_0<=SUBSELECT_EXPR)||(LA151_0>=EXISTS_SUBSELECT_EXPR && LA151_0<=NOT_IN_SUBSELECT_EXPR)||LA151_0==SUBSTITUTION||(LA151_0>=FIRST_AGGREG && LA151_0<=LAST_AGGREG)||(LA151_0>=INT_TYPE && LA151_0<=NULL_TYPE)||(LA151_0>=STAR && LA151_0<=PLUS)||(LA151_0>=BAND && LA151_0<=BXOR)||(LA151_0>=LT && LA151_0<=GE)||(LA151_0>=MINUS && LA151_0<=MOD)) ) {
                            alt151=1;
                        }


                        switch (alt151) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:475:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3113);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop151;
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
                    // EsperEPL2Ast.g:476:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3132); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3134);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3142);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:476:55: ( valueExpr )*
                    loop152:
                    do {
                        int alt152=2;
                        int LA152_0 = input.LA(1);

                        if ( ((LA152_0>=IN_SET && LA152_0<=REGEXP)||LA152_0==NOT_EXPR||(LA152_0>=SUM && LA152_0<=AVG)||(LA152_0>=COALESCE && LA152_0<=COUNT)||(LA152_0>=CASE && LA152_0<=CASE2)||(LA152_0>=PREVIOUS && LA152_0<=EXISTS)||(LA152_0>=INSTANCEOF && LA152_0<=CURRENT_TIMESTAMP)||(LA152_0>=EVAL_AND_EXPR && LA152_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA152_0==EVENT_PROP_EXPR||(LA152_0>=CONCAT && LA152_0<=LIB_FUNCTION)||LA152_0==ARRAY_EXPR||(LA152_0>=NOT_IN_SET && LA152_0<=NOT_REGEXP)||(LA152_0>=IN_RANGE && LA152_0<=SUBSELECT_EXPR)||(LA152_0>=EXISTS_SUBSELECT_EXPR && LA152_0<=NOT_IN_SUBSELECT_EXPR)||LA152_0==SUBSTITUTION||(LA152_0>=FIRST_AGGREG && LA152_0<=LAST_AGGREG)||(LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)||(LA152_0>=STAR && LA152_0<=PLUS)||(LA152_0>=BAND && LA152_0<=BXOR)||(LA152_0>=LT && LA152_0<=GE)||(LA152_0>=MINUS && LA152_0<=MOD)) ) {
                            alt152=1;
                        }


                        switch (alt152) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:476:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3145);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop152;
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
                    // EsperEPL2Ast.g:477:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3164); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3166);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3174);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3176);
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
                    // EsperEPL2Ast.g:478:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3193); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3195);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3203);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3205);
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
    // EsperEPL2Ast.g:481:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:482:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt155=2;
            int LA155_0 = input.LA(1);

            if ( (LA155_0==BETWEEN) ) {
                alt155=1;
            }
            else if ( (LA155_0==NOT_BETWEEN) ) {
                alt155=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 155, 0, input);

                throw nvae;
            }
            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:482:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3230); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3232);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3234);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3236);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:483:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3247); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3249);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3251);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:483:40: ( valueExpr )*
                    loop154:
                    do {
                        int alt154=2;
                        int LA154_0 = input.LA(1);

                        if ( ((LA154_0>=IN_SET && LA154_0<=REGEXP)||LA154_0==NOT_EXPR||(LA154_0>=SUM && LA154_0<=AVG)||(LA154_0>=COALESCE && LA154_0<=COUNT)||(LA154_0>=CASE && LA154_0<=CASE2)||(LA154_0>=PREVIOUS && LA154_0<=EXISTS)||(LA154_0>=INSTANCEOF && LA154_0<=CURRENT_TIMESTAMP)||(LA154_0>=EVAL_AND_EXPR && LA154_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA154_0==EVENT_PROP_EXPR||(LA154_0>=CONCAT && LA154_0<=LIB_FUNCTION)||LA154_0==ARRAY_EXPR||(LA154_0>=NOT_IN_SET && LA154_0<=NOT_REGEXP)||(LA154_0>=IN_RANGE && LA154_0<=SUBSELECT_EXPR)||(LA154_0>=EXISTS_SUBSELECT_EXPR && LA154_0<=NOT_IN_SUBSELECT_EXPR)||LA154_0==SUBSTITUTION||(LA154_0>=FIRST_AGGREG && LA154_0<=LAST_AGGREG)||(LA154_0>=INT_TYPE && LA154_0<=NULL_TYPE)||(LA154_0>=STAR && LA154_0<=PLUS)||(LA154_0>=BAND && LA154_0<=BXOR)||(LA154_0>=LT && LA154_0<=GE)||(LA154_0>=MINUS && LA154_0<=MOD)) ) {
                            alt154=1;
                        }


                        switch (alt154) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:483:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3254);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop154;
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
    // EsperEPL2Ast.g:486:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:487:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==LIKE) ) {
                alt158=1;
            }
            else if ( (LA158_0==NOT_LIKE) ) {
                alt158=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }
            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:487:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3274); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3276);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3278);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:487:33: ( valueExpr )?
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( ((LA156_0>=IN_SET && LA156_0<=REGEXP)||LA156_0==NOT_EXPR||(LA156_0>=SUM && LA156_0<=AVG)||(LA156_0>=COALESCE && LA156_0<=COUNT)||(LA156_0>=CASE && LA156_0<=CASE2)||(LA156_0>=PREVIOUS && LA156_0<=EXISTS)||(LA156_0>=INSTANCEOF && LA156_0<=CURRENT_TIMESTAMP)||(LA156_0>=EVAL_AND_EXPR && LA156_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA156_0==EVENT_PROP_EXPR||(LA156_0>=CONCAT && LA156_0<=LIB_FUNCTION)||LA156_0==ARRAY_EXPR||(LA156_0>=NOT_IN_SET && LA156_0<=NOT_REGEXP)||(LA156_0>=IN_RANGE && LA156_0<=SUBSELECT_EXPR)||(LA156_0>=EXISTS_SUBSELECT_EXPR && LA156_0<=NOT_IN_SUBSELECT_EXPR)||LA156_0==SUBSTITUTION||(LA156_0>=FIRST_AGGREG && LA156_0<=LAST_AGGREG)||(LA156_0>=INT_TYPE && LA156_0<=NULL_TYPE)||(LA156_0>=STAR && LA156_0<=PLUS)||(LA156_0>=BAND && LA156_0<=BXOR)||(LA156_0>=LT && LA156_0<=GE)||(LA156_0>=MINUS && LA156_0<=MOD)) ) {
                        alt156=1;
                    }
                    switch (alt156) {
                        case 1 :
                            // EsperEPL2Ast.g:487:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3281);
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
                    // EsperEPL2Ast.g:488:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3294); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3296);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3298);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:488:37: ( valueExpr )?
                    int alt157=2;
                    int LA157_0 = input.LA(1);

                    if ( ((LA157_0>=IN_SET && LA157_0<=REGEXP)||LA157_0==NOT_EXPR||(LA157_0>=SUM && LA157_0<=AVG)||(LA157_0>=COALESCE && LA157_0<=COUNT)||(LA157_0>=CASE && LA157_0<=CASE2)||(LA157_0>=PREVIOUS && LA157_0<=EXISTS)||(LA157_0>=INSTANCEOF && LA157_0<=CURRENT_TIMESTAMP)||(LA157_0>=EVAL_AND_EXPR && LA157_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA157_0==EVENT_PROP_EXPR||(LA157_0>=CONCAT && LA157_0<=LIB_FUNCTION)||LA157_0==ARRAY_EXPR||(LA157_0>=NOT_IN_SET && LA157_0<=NOT_REGEXP)||(LA157_0>=IN_RANGE && LA157_0<=SUBSELECT_EXPR)||(LA157_0>=EXISTS_SUBSELECT_EXPR && LA157_0<=NOT_IN_SUBSELECT_EXPR)||LA157_0==SUBSTITUTION||(LA157_0>=FIRST_AGGREG && LA157_0<=LAST_AGGREG)||(LA157_0>=INT_TYPE && LA157_0<=NULL_TYPE)||(LA157_0>=STAR && LA157_0<=PLUS)||(LA157_0>=BAND && LA157_0<=BXOR)||(LA157_0>=LT && LA157_0<=GE)||(LA157_0>=MINUS && LA157_0<=MOD)) ) {
                        alt157=1;
                    }
                    switch (alt157) {
                        case 1 :
                            // EsperEPL2Ast.g:488:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3301);
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
    // EsperEPL2Ast.g:491:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:492:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==REGEXP) ) {
                alt159=1;
            }
            else if ( (LA159_0==NOT_REGEXP) ) {
                alt159=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 159, 0, input);

                throw nvae;
            }
            switch (alt159) {
                case 1 :
                    // EsperEPL2Ast.g:492:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3320); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3322);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3324);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:493:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3335); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3337);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3339);
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
    // EsperEPL2Ast.g:496:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:497:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt172=15;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt172=1;
                }
                break;
            case AVG:
                {
                alt172=2;
                }
                break;
            case COUNT:
                {
                alt172=3;
                }
                break;
            case MEDIAN:
                {
                alt172=4;
                }
                break;
            case STDDEV:
                {
                alt172=5;
                }
                break;
            case AVEDEV:
                {
                alt172=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt172=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt172=8;
                }
                break;
            case COALESCE:
                {
                alt172=9;
                }
                break;
            case PREVIOUS:
                {
                alt172=10;
                }
                break;
            case PRIOR:
                {
                alt172=11;
                }
                break;
            case INSTANCEOF:
                {
                alt172=12;
                }
                break;
            case CAST:
                {
                alt172=13;
                }
                break;
            case EXISTS:
                {
                alt172=14;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt172=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 172, 0, input);

                throw nvae;
            }

            switch (alt172) {
                case 1 :
                    // EsperEPL2Ast.g:497:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3358); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:497:13: ( DISTINCT )?
                    int alt160=2;
                    int LA160_0 = input.LA(1);

                    if ( (LA160_0==DISTINCT) ) {
                        alt160=1;
                    }
                    switch (alt160) {
                        case 1 :
                            // EsperEPL2Ast.g:497:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3361); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3365);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:498:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3376); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:498:12: ( DISTINCT )?
                    int alt161=2;
                    int LA161_0 = input.LA(1);

                    if ( (LA161_0==DISTINCT) ) {
                        alt161=1;
                    }
                    switch (alt161) {
                        case 1 :
                            // EsperEPL2Ast.g:498:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3379); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3383);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:499:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3394); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:499:14: ( ( DISTINCT )? valueExpr )?
                        int alt163=2;
                        int LA163_0 = input.LA(1);

                        if ( ((LA163_0>=IN_SET && LA163_0<=REGEXP)||LA163_0==NOT_EXPR||(LA163_0>=SUM && LA163_0<=AVG)||(LA163_0>=COALESCE && LA163_0<=COUNT)||(LA163_0>=CASE && LA163_0<=CASE2)||LA163_0==DISTINCT||(LA163_0>=PREVIOUS && LA163_0<=EXISTS)||(LA163_0>=INSTANCEOF && LA163_0<=CURRENT_TIMESTAMP)||(LA163_0>=EVAL_AND_EXPR && LA163_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA163_0==EVENT_PROP_EXPR||(LA163_0>=CONCAT && LA163_0<=LIB_FUNCTION)||LA163_0==ARRAY_EXPR||(LA163_0>=NOT_IN_SET && LA163_0<=NOT_REGEXP)||(LA163_0>=IN_RANGE && LA163_0<=SUBSELECT_EXPR)||(LA163_0>=EXISTS_SUBSELECT_EXPR && LA163_0<=NOT_IN_SUBSELECT_EXPR)||LA163_0==SUBSTITUTION||(LA163_0>=FIRST_AGGREG && LA163_0<=LAST_AGGREG)||(LA163_0>=INT_TYPE && LA163_0<=NULL_TYPE)||(LA163_0>=STAR && LA163_0<=PLUS)||(LA163_0>=BAND && LA163_0<=BXOR)||(LA163_0>=LT && LA163_0<=GE)||(LA163_0>=MINUS && LA163_0<=MOD)) ) {
                            alt163=1;
                        }
                        switch (alt163) {
                            case 1 :
                                // EsperEPL2Ast.g:499:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:499:15: ( DISTINCT )?
                                int alt162=2;
                                int LA162_0 = input.LA(1);

                                if ( (LA162_0==DISTINCT) ) {
                                    alt162=1;
                                }
                                switch (alt162) {
                                    case 1 :
                                        // EsperEPL2Ast.g:499:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3398); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3402);
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
                    // EsperEPL2Ast.g:500:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3416); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:500:15: ( DISTINCT )?
                    int alt164=2;
                    int LA164_0 = input.LA(1);

                    if ( (LA164_0==DISTINCT) ) {
                        alt164=1;
                    }
                    switch (alt164) {
                        case 1 :
                            // EsperEPL2Ast.g:500:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3419); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3423);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:501:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3434); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:501:15: ( DISTINCT )?
                    int alt165=2;
                    int LA165_0 = input.LA(1);

                    if ( (LA165_0==DISTINCT) ) {
                        alt165=1;
                    }
                    switch (alt165) {
                        case 1 :
                            // EsperEPL2Ast.g:501:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3437); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3441);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:502:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3452); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:502:15: ( DISTINCT )?
                    int alt166=2;
                    int LA166_0 = input.LA(1);

                    if ( (LA166_0==DISTINCT) ) {
                        alt166=1;
                    }
                    switch (alt166) {
                        case 1 :
                            // EsperEPL2Ast.g:502:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3455); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3459);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:503:4: ^(f= LAST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3470); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:503:20: ( DISTINCT )?
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( (LA167_0==DISTINCT) ) {
                        alt167=1;
                    }
                    switch (alt167) {
                        case 1 :
                            // EsperEPL2Ast.g:503:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3473); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3477);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:504:4: ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3488); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:504:21: ( DISTINCT )?
                    int alt168=2;
                    int LA168_0 = input.LA(1);

                    if ( (LA168_0==DISTINCT) ) {
                        alt168=1;
                    }
                    switch (alt168) {
                        case 1 :
                            // EsperEPL2Ast.g:504:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3491); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3495);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:505:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3507); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3509);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3511);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:505:38: ( valueExpr )*
                    loop169:
                    do {
                        int alt169=2;
                        int LA169_0 = input.LA(1);

                        if ( ((LA169_0>=IN_SET && LA169_0<=REGEXP)||LA169_0==NOT_EXPR||(LA169_0>=SUM && LA169_0<=AVG)||(LA169_0>=COALESCE && LA169_0<=COUNT)||(LA169_0>=CASE && LA169_0<=CASE2)||(LA169_0>=PREVIOUS && LA169_0<=EXISTS)||(LA169_0>=INSTANCEOF && LA169_0<=CURRENT_TIMESTAMP)||(LA169_0>=EVAL_AND_EXPR && LA169_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA169_0==EVENT_PROP_EXPR||(LA169_0>=CONCAT && LA169_0<=LIB_FUNCTION)||LA169_0==ARRAY_EXPR||(LA169_0>=NOT_IN_SET && LA169_0<=NOT_REGEXP)||(LA169_0>=IN_RANGE && LA169_0<=SUBSELECT_EXPR)||(LA169_0>=EXISTS_SUBSELECT_EXPR && LA169_0<=NOT_IN_SUBSELECT_EXPR)||LA169_0==SUBSTITUTION||(LA169_0>=FIRST_AGGREG && LA169_0<=LAST_AGGREG)||(LA169_0>=INT_TYPE && LA169_0<=NULL_TYPE)||(LA169_0>=STAR && LA169_0<=PLUS)||(LA169_0>=BAND && LA169_0<=BXOR)||(LA169_0>=LT && LA169_0<=GE)||(LA169_0>=MINUS && LA169_0<=MOD)) ) {
                            alt169=1;
                        }


                        switch (alt169) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:505:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3514);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop169;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:506:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3529); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3531);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:506:28: ( valueExpr )?
                    int alt170=2;
                    int LA170_0 = input.LA(1);

                    if ( ((LA170_0>=IN_SET && LA170_0<=REGEXP)||LA170_0==NOT_EXPR||(LA170_0>=SUM && LA170_0<=AVG)||(LA170_0>=COALESCE && LA170_0<=COUNT)||(LA170_0>=CASE && LA170_0<=CASE2)||(LA170_0>=PREVIOUS && LA170_0<=EXISTS)||(LA170_0>=INSTANCEOF && LA170_0<=CURRENT_TIMESTAMP)||(LA170_0>=EVAL_AND_EXPR && LA170_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA170_0==EVENT_PROP_EXPR||(LA170_0>=CONCAT && LA170_0<=LIB_FUNCTION)||LA170_0==ARRAY_EXPR||(LA170_0>=NOT_IN_SET && LA170_0<=NOT_REGEXP)||(LA170_0>=IN_RANGE && LA170_0<=SUBSELECT_EXPR)||(LA170_0>=EXISTS_SUBSELECT_EXPR && LA170_0<=NOT_IN_SUBSELECT_EXPR)||LA170_0==SUBSTITUTION||(LA170_0>=FIRST_AGGREG && LA170_0<=LAST_AGGREG)||(LA170_0>=INT_TYPE && LA170_0<=NULL_TYPE)||(LA170_0>=STAR && LA170_0<=PLUS)||(LA170_0>=BAND && LA170_0<=BXOR)||(LA170_0>=LT && LA170_0<=GE)||(LA170_0>=MINUS && LA170_0<=MOD)) ) {
                        alt170=1;
                    }
                    switch (alt170) {
                        case 1 :
                            // EsperEPL2Ast.g:506:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3533);
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
                    // EsperEPL2Ast.g:507:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3546); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3550); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3552);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:508:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3565); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3567);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3569); 
                    // EsperEPL2Ast.g:508:42: ( CLASS_IDENT )*
                    loop171:
                    do {
                        int alt171=2;
                        int LA171_0 = input.LA(1);

                        if ( (LA171_0==CLASS_IDENT) ) {
                            alt171=1;
                        }


                        switch (alt171) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:508:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3572); 

                    	    }
                    	    break;

                    	default :
                    	    break loop171;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:509:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3586); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3588);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3590); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:510:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3602); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3604);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:511:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3616); 



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
    // EsperEPL2Ast.g:514:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:515:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:515:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3636); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:515:19: ( valueExpr )*
                loop173:
                do {
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( ((LA173_0>=IN_SET && LA173_0<=REGEXP)||LA173_0==NOT_EXPR||(LA173_0>=SUM && LA173_0<=AVG)||(LA173_0>=COALESCE && LA173_0<=COUNT)||(LA173_0>=CASE && LA173_0<=CASE2)||(LA173_0>=PREVIOUS && LA173_0<=EXISTS)||(LA173_0>=INSTANCEOF && LA173_0<=CURRENT_TIMESTAMP)||(LA173_0>=EVAL_AND_EXPR && LA173_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA173_0==EVENT_PROP_EXPR||(LA173_0>=CONCAT && LA173_0<=LIB_FUNCTION)||LA173_0==ARRAY_EXPR||(LA173_0>=NOT_IN_SET && LA173_0<=NOT_REGEXP)||(LA173_0>=IN_RANGE && LA173_0<=SUBSELECT_EXPR)||(LA173_0>=EXISTS_SUBSELECT_EXPR && LA173_0<=NOT_IN_SUBSELECT_EXPR)||LA173_0==SUBSTITUTION||(LA173_0>=FIRST_AGGREG && LA173_0<=LAST_AGGREG)||(LA173_0>=INT_TYPE && LA173_0<=NULL_TYPE)||(LA173_0>=STAR && LA173_0<=PLUS)||(LA173_0>=BAND && LA173_0<=BXOR)||(LA173_0>=LT && LA173_0<=GE)||(LA173_0>=MINUS && LA173_0<=MOD)) ) {
                        alt173=1;
                    }


                    switch (alt173) {
                	case 1 :
                	    // EsperEPL2Ast.g:515:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3639);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop173;
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
    // EsperEPL2Ast.g:518:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:519:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt175=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt175=1;
                }
                break;
            case MINUS:
                {
                alt175=2;
                }
                break;
            case DIV:
                {
                alt175=3;
                }
                break;
            case STAR:
                {
                alt175=4;
                }
                break;
            case MOD:
                {
                alt175=5;
                }
                break;
            case BAND:
                {
                alt175=6;
                }
                break;
            case BOR:
                {
                alt175=7;
                }
                break;
            case BXOR:
                {
                alt175=8;
                }
                break;
            case CONCAT:
                {
                alt175=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                throw nvae;
            }

            switch (alt175) {
                case 1 :
                    // EsperEPL2Ast.g:519:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3660); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3662);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3664);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:520:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3676); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3678);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3680);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:521:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3692); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3694);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3696);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:522:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3707); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3709);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3711);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:523:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3723); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3725);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3727);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:524:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3738); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3740);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3742);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:525:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3753); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3755);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3757);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:526:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3768); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3770);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3772);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:527:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3784); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3786);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3788);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:527:36: ( valueExpr )*
                    loop174:
                    do {
                        int alt174=2;
                        int LA174_0 = input.LA(1);

                        if ( ((LA174_0>=IN_SET && LA174_0<=REGEXP)||LA174_0==NOT_EXPR||(LA174_0>=SUM && LA174_0<=AVG)||(LA174_0>=COALESCE && LA174_0<=COUNT)||(LA174_0>=CASE && LA174_0<=CASE2)||(LA174_0>=PREVIOUS && LA174_0<=EXISTS)||(LA174_0>=INSTANCEOF && LA174_0<=CURRENT_TIMESTAMP)||(LA174_0>=EVAL_AND_EXPR && LA174_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA174_0==EVENT_PROP_EXPR||(LA174_0>=CONCAT && LA174_0<=LIB_FUNCTION)||LA174_0==ARRAY_EXPR||(LA174_0>=NOT_IN_SET && LA174_0<=NOT_REGEXP)||(LA174_0>=IN_RANGE && LA174_0<=SUBSELECT_EXPR)||(LA174_0>=EXISTS_SUBSELECT_EXPR && LA174_0<=NOT_IN_SUBSELECT_EXPR)||LA174_0==SUBSTITUTION||(LA174_0>=FIRST_AGGREG && LA174_0<=LAST_AGGREG)||(LA174_0>=INT_TYPE && LA174_0<=NULL_TYPE)||(LA174_0>=STAR && LA174_0<=PLUS)||(LA174_0>=BAND && LA174_0<=BXOR)||(LA174_0>=LT && LA174_0<=GE)||(LA174_0>=MINUS && LA174_0<=MOD)) ) {
                            alt174=1;
                        }


                        switch (alt174) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:527:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3791);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop174;
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
    // EsperEPL2Ast.g:530:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:531:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:531:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3812); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:531:22: ( CLASS_IDENT )?
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( (LA176_0==CLASS_IDENT) ) {
                alt176=1;
            }
            switch (alt176) {
                case 1 :
                    // EsperEPL2Ast.g:531:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3815); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3819); 
            // EsperEPL2Ast.g:531:43: ( DISTINCT )?
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==DISTINCT) ) {
                alt177=1;
            }
            switch (alt177) {
                case 1 :
                    // EsperEPL2Ast.g:531:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3822); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:531:55: ( valueExpr )*
            loop178:
            do {
                int alt178=2;
                int LA178_0 = input.LA(1);

                if ( ((LA178_0>=IN_SET && LA178_0<=REGEXP)||LA178_0==NOT_EXPR||(LA178_0>=SUM && LA178_0<=AVG)||(LA178_0>=COALESCE && LA178_0<=COUNT)||(LA178_0>=CASE && LA178_0<=CASE2)||(LA178_0>=PREVIOUS && LA178_0<=EXISTS)||(LA178_0>=INSTANCEOF && LA178_0<=CURRENT_TIMESTAMP)||(LA178_0>=EVAL_AND_EXPR && LA178_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA178_0==EVENT_PROP_EXPR||(LA178_0>=CONCAT && LA178_0<=LIB_FUNCTION)||LA178_0==ARRAY_EXPR||(LA178_0>=NOT_IN_SET && LA178_0<=NOT_REGEXP)||(LA178_0>=IN_RANGE && LA178_0<=SUBSELECT_EXPR)||(LA178_0>=EXISTS_SUBSELECT_EXPR && LA178_0<=NOT_IN_SUBSELECT_EXPR)||LA178_0==SUBSTITUTION||(LA178_0>=FIRST_AGGREG && LA178_0<=LAST_AGGREG)||(LA178_0>=INT_TYPE && LA178_0<=NULL_TYPE)||(LA178_0>=STAR && LA178_0<=PLUS)||(LA178_0>=BAND && LA178_0<=BXOR)||(LA178_0>=LT && LA178_0<=GE)||(LA178_0>=MINUS && LA178_0<=MOD)) ) {
                    alt178=1;
                }


                switch (alt178) {
            	case 1 :
            	    // EsperEPL2Ast.g:531:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3827);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop178;
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
    // EsperEPL2Ast.g:537:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:538:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:538:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:538:4: ( annotation[true] )*
            loop179:
            do {
                int alt179=2;
                int LA179_0 = input.LA(1);

                if ( (LA179_0==ANNOTATION) ) {
                    alt179=1;
                }


                switch (alt179) {
            	case 1 :
            	    // EsperEPL2Ast.g:538:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule3847);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop179;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3851);
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
    // EsperEPL2Ast.g:541:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:542:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt183=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt183=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt183=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt183=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt183=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt183=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt183=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt183=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 183, 0, input);

                throw nvae;
            }

            switch (alt183) {
                case 1 :
                    // EsperEPL2Ast.g:542:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3865);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:543:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3870);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:544:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3880); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3882);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:545:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3896); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice3898);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3900);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:546:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3914); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3916);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:547:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3930); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3932);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3934); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3936); 
                    // EsperEPL2Ast.g:547:44: ( valueExprWithTime )*
                    loop180:
                    do {
                        int alt180=2;
                        int LA180_0 = input.LA(1);

                        if ( ((LA180_0>=IN_SET && LA180_0<=REGEXP)||LA180_0==NOT_EXPR||(LA180_0>=SUM && LA180_0<=AVG)||(LA180_0>=COALESCE && LA180_0<=COUNT)||(LA180_0>=CASE && LA180_0<=CASE2)||LA180_0==LAST||(LA180_0>=PREVIOUS && LA180_0<=EXISTS)||(LA180_0>=LW && LA180_0<=CURRENT_TIMESTAMP)||(LA180_0>=NUMERIC_PARAM_RANGE && LA180_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA180_0>=EVAL_AND_EXPR && LA180_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA180_0==EVENT_PROP_EXPR||(LA180_0>=CONCAT && LA180_0<=LIB_FUNCTION)||(LA180_0>=TIME_PERIOD && LA180_0<=ARRAY_EXPR)||(LA180_0>=NOT_IN_SET && LA180_0<=NOT_REGEXP)||(LA180_0>=IN_RANGE && LA180_0<=SUBSELECT_EXPR)||(LA180_0>=EXISTS_SUBSELECT_EXPR && LA180_0<=NOT_IN_SUBSELECT_EXPR)||(LA180_0>=LAST_OPERATOR && LA180_0<=SUBSTITUTION)||LA180_0==NUMBERSETSTAR||(LA180_0>=FIRST_AGGREG && LA180_0<=LAST_AGGREG)||(LA180_0>=INT_TYPE && LA180_0<=NULL_TYPE)||(LA180_0>=STAR && LA180_0<=PLUS)||(LA180_0>=BAND && LA180_0<=BXOR)||(LA180_0>=LT && LA180_0<=GE)||(LA180_0>=MINUS && LA180_0<=MOD)) ) {
                            alt180=1;
                        }


                        switch (alt180) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:547:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3938);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop180;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:548:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3952); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:548:26: ( matchUntilRange )?
                    int alt181=2;
                    int LA181_0 = input.LA(1);

                    if ( ((LA181_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA181_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt181=1;
                    }
                    switch (alt181) {
                        case 1 :
                            // EsperEPL2Ast.g:548:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice3954);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3957);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:548:54: ( exprChoice )?
                    int alt182=2;
                    int LA182_0 = input.LA(1);

                    if ( ((LA182_0>=OR_EXPR && LA182_0<=AND_EXPR)||(LA182_0>=EVERY_EXPR && LA182_0<=EVERY_DISTINCT_EXPR)||LA182_0==FOLLOWED_BY_EXPR||(LA182_0>=PATTERN_FILTER_EXPR && LA182_0<=PATTERN_NOT_EXPR)||(LA182_0>=GUARD_EXPR && LA182_0<=OBSERVER_EXPR)||LA182_0==MATCH_UNTIL_EXPR) ) {
                        alt182=1;
                    }
                    switch (alt182) {
                        case 1 :
                            // EsperEPL2Ast.g:548:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice3959);
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
    // EsperEPL2Ast.g:552:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:553:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:553:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3980); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:553:35: ( valueExpr )+
            int cnt184=0;
            loop184:
            do {
                int alt184=2;
                int LA184_0 = input.LA(1);

                if ( ((LA184_0>=IN_SET && LA184_0<=REGEXP)||LA184_0==NOT_EXPR||(LA184_0>=SUM && LA184_0<=AVG)||(LA184_0>=COALESCE && LA184_0<=COUNT)||(LA184_0>=CASE && LA184_0<=CASE2)||(LA184_0>=PREVIOUS && LA184_0<=EXISTS)||(LA184_0>=INSTANCEOF && LA184_0<=CURRENT_TIMESTAMP)||(LA184_0>=EVAL_AND_EXPR && LA184_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA184_0==EVENT_PROP_EXPR||(LA184_0>=CONCAT && LA184_0<=LIB_FUNCTION)||LA184_0==ARRAY_EXPR||(LA184_0>=NOT_IN_SET && LA184_0<=NOT_REGEXP)||(LA184_0>=IN_RANGE && LA184_0<=SUBSELECT_EXPR)||(LA184_0>=EXISTS_SUBSELECT_EXPR && LA184_0<=NOT_IN_SUBSELECT_EXPR)||LA184_0==SUBSTITUTION||(LA184_0>=FIRST_AGGREG && LA184_0<=LAST_AGGREG)||(LA184_0>=INT_TYPE && LA184_0<=NULL_TYPE)||(LA184_0>=STAR && LA184_0<=PLUS)||(LA184_0>=BAND && LA184_0<=BXOR)||(LA184_0>=LT && LA184_0<=GE)||(LA184_0>=MINUS && LA184_0<=MOD)) ) {
                    alt184=1;
                }


                switch (alt184) {
            	case 1 :
            	    // EsperEPL2Ast.g:553:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions3982);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt184 >= 1 ) break loop184;
                        EarlyExitException eee =
                            new EarlyExitException(184, input);
                        throw eee;
                }
                cnt184++;
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
    // EsperEPL2Ast.g:556:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:557:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt188=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt188=1;
                }
                break;
            case OR_EXPR:
                {
                alt188=2;
                }
                break;
            case AND_EXPR:
                {
                alt188=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 188, 0, input);

                throw nvae;
            }

            switch (alt188) {
                case 1 :
                    // EsperEPL2Ast.g:557:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4001); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4003);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4005);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:557:48: ( exprChoice )*
                    loop185:
                    do {
                        int alt185=2;
                        int LA185_0 = input.LA(1);

                        if ( ((LA185_0>=OR_EXPR && LA185_0<=AND_EXPR)||(LA185_0>=EVERY_EXPR && LA185_0<=EVERY_DISTINCT_EXPR)||LA185_0==FOLLOWED_BY_EXPR||(LA185_0>=PATTERN_FILTER_EXPR && LA185_0<=PATTERN_NOT_EXPR)||(LA185_0>=GUARD_EXPR && LA185_0<=OBSERVER_EXPR)||LA185_0==MATCH_UNTIL_EXPR) ) {
                            alt185=1;
                        }


                        switch (alt185) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:557:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4008);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop185;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:558:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4024); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4026);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4028);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:558:40: ( exprChoice )*
                    loop186:
                    do {
                        int alt186=2;
                        int LA186_0 = input.LA(1);

                        if ( ((LA186_0>=OR_EXPR && LA186_0<=AND_EXPR)||(LA186_0>=EVERY_EXPR && LA186_0<=EVERY_DISTINCT_EXPR)||LA186_0==FOLLOWED_BY_EXPR||(LA186_0>=PATTERN_FILTER_EXPR && LA186_0<=PATTERN_NOT_EXPR)||(LA186_0>=GUARD_EXPR && LA186_0<=OBSERVER_EXPR)||LA186_0==MATCH_UNTIL_EXPR) ) {
                            alt186=1;
                        }


                        switch (alt186) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:558:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4031);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop186;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:559:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4047); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4049);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4051);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:559:41: ( exprChoice )*
                    loop187:
                    do {
                        int alt187=2;
                        int LA187_0 = input.LA(1);

                        if ( ((LA187_0>=OR_EXPR && LA187_0<=AND_EXPR)||(LA187_0>=EVERY_EXPR && LA187_0<=EVERY_DISTINCT_EXPR)||LA187_0==FOLLOWED_BY_EXPR||(LA187_0>=PATTERN_FILTER_EXPR && LA187_0<=PATTERN_NOT_EXPR)||(LA187_0>=GUARD_EXPR && LA187_0<=OBSERVER_EXPR)||LA187_0==MATCH_UNTIL_EXPR) ) {
                            alt187=1;
                        }


                        switch (alt187) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:559:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4054);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop187;
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
    // EsperEPL2Ast.g:562:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:563:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt190=2;
            int LA190_0 = input.LA(1);

            if ( (LA190_0==PATTERN_FILTER_EXPR) ) {
                alt190=1;
            }
            else if ( (LA190_0==OBSERVER_EXPR) ) {
                alt190=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 190, 0, input);

                throw nvae;
            }
            switch (alt190) {
                case 1 :
                    // EsperEPL2Ast.g:563:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4073);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:564:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4085); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4087); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4089); 
                    // EsperEPL2Ast.g:564:39: ( valueExprWithTime )*
                    loop189:
                    do {
                        int alt189=2;
                        int LA189_0 = input.LA(1);

                        if ( ((LA189_0>=IN_SET && LA189_0<=REGEXP)||LA189_0==NOT_EXPR||(LA189_0>=SUM && LA189_0<=AVG)||(LA189_0>=COALESCE && LA189_0<=COUNT)||(LA189_0>=CASE && LA189_0<=CASE2)||LA189_0==LAST||(LA189_0>=PREVIOUS && LA189_0<=EXISTS)||(LA189_0>=LW && LA189_0<=CURRENT_TIMESTAMP)||(LA189_0>=NUMERIC_PARAM_RANGE && LA189_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA189_0>=EVAL_AND_EXPR && LA189_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA189_0==EVENT_PROP_EXPR||(LA189_0>=CONCAT && LA189_0<=LIB_FUNCTION)||(LA189_0>=TIME_PERIOD && LA189_0<=ARRAY_EXPR)||(LA189_0>=NOT_IN_SET && LA189_0<=NOT_REGEXP)||(LA189_0>=IN_RANGE && LA189_0<=SUBSELECT_EXPR)||(LA189_0>=EXISTS_SUBSELECT_EXPR && LA189_0<=NOT_IN_SUBSELECT_EXPR)||(LA189_0>=LAST_OPERATOR && LA189_0<=SUBSTITUTION)||LA189_0==NUMBERSETSTAR||(LA189_0>=FIRST_AGGREG && LA189_0<=LAST_AGGREG)||(LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)||(LA189_0>=STAR && LA189_0<=PLUS)||(LA189_0>=BAND && LA189_0<=BXOR)||(LA189_0>=LT && LA189_0<=GE)||(LA189_0>=MINUS && LA189_0<=MOD)) ) {
                            alt189=1;
                        }


                        switch (alt189) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:564:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4091);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop189;
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
    // EsperEPL2Ast.g:567:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:568:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:568:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4111); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:568:29: ( IDENT )?
            int alt191=2;
            int LA191_0 = input.LA(1);

            if ( (LA191_0==IDENT) ) {
                alt191=1;
            }
            switch (alt191) {
                case 1 :
                    // EsperEPL2Ast.g:568:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4113); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4116); 
            // EsperEPL2Ast.g:568:48: ( propertyExpression )?
            int alt192=2;
            int LA192_0 = input.LA(1);

            if ( (LA192_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt192=1;
            }
            switch (alt192) {
                case 1 :
                    // EsperEPL2Ast.g:568:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4118);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:568:68: ( valueExpr )*
            loop193:
            do {
                int alt193=2;
                int LA193_0 = input.LA(1);

                if ( ((LA193_0>=IN_SET && LA193_0<=REGEXP)||LA193_0==NOT_EXPR||(LA193_0>=SUM && LA193_0<=AVG)||(LA193_0>=COALESCE && LA193_0<=COUNT)||(LA193_0>=CASE && LA193_0<=CASE2)||(LA193_0>=PREVIOUS && LA193_0<=EXISTS)||(LA193_0>=INSTANCEOF && LA193_0<=CURRENT_TIMESTAMP)||(LA193_0>=EVAL_AND_EXPR && LA193_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA193_0==EVENT_PROP_EXPR||(LA193_0>=CONCAT && LA193_0<=LIB_FUNCTION)||LA193_0==ARRAY_EXPR||(LA193_0>=NOT_IN_SET && LA193_0<=NOT_REGEXP)||(LA193_0>=IN_RANGE && LA193_0<=SUBSELECT_EXPR)||(LA193_0>=EXISTS_SUBSELECT_EXPR && LA193_0<=NOT_IN_SUBSELECT_EXPR)||LA193_0==SUBSTITUTION||(LA193_0>=FIRST_AGGREG && LA193_0<=LAST_AGGREG)||(LA193_0>=INT_TYPE && LA193_0<=NULL_TYPE)||(LA193_0>=STAR && LA193_0<=PLUS)||(LA193_0>=BAND && LA193_0<=BXOR)||(LA193_0>=LT && LA193_0<=GE)||(LA193_0>=MINUS && LA193_0<=MOD)) ) {
                    alt193=1;
                }


                switch (alt193) {
            	case 1 :
            	    // EsperEPL2Ast.g:568:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4122);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop193;
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
    // EsperEPL2Ast.g:571:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:572:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt194=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt194=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt194=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt194=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt194=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 194, 0, input);

                throw nvae;
            }

            switch (alt194) {
                case 1 :
                    // EsperEPL2Ast.g:572:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4140); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4142);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4144);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:573:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4152); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4154);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:574:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4162); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4164);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:575:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4171); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4173);
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
    // EsperEPL2Ast.g:578:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:579:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:583:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:584:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:584:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4202); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4204);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:584:35: ( valueExpr )*
            loop195:
            do {
                int alt195=2;
                int LA195_0 = input.LA(1);

                if ( ((LA195_0>=IN_SET && LA195_0<=REGEXP)||LA195_0==NOT_EXPR||(LA195_0>=SUM && LA195_0<=AVG)||(LA195_0>=COALESCE && LA195_0<=COUNT)||(LA195_0>=CASE && LA195_0<=CASE2)||(LA195_0>=PREVIOUS && LA195_0<=EXISTS)||(LA195_0>=INSTANCEOF && LA195_0<=CURRENT_TIMESTAMP)||(LA195_0>=EVAL_AND_EXPR && LA195_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA195_0==EVENT_PROP_EXPR||(LA195_0>=CONCAT && LA195_0<=LIB_FUNCTION)||LA195_0==ARRAY_EXPR||(LA195_0>=NOT_IN_SET && LA195_0<=NOT_REGEXP)||(LA195_0>=IN_RANGE && LA195_0<=SUBSELECT_EXPR)||(LA195_0>=EXISTS_SUBSELECT_EXPR && LA195_0<=NOT_IN_SUBSELECT_EXPR)||LA195_0==SUBSTITUTION||(LA195_0>=FIRST_AGGREG && LA195_0<=LAST_AGGREG)||(LA195_0>=INT_TYPE && LA195_0<=NULL_TYPE)||(LA195_0>=STAR && LA195_0<=PLUS)||(LA195_0>=BAND && LA195_0<=BXOR)||(LA195_0>=LT && LA195_0<=GE)||(LA195_0>=MINUS && LA195_0<=MOD)) ) {
                    alt195=1;
                }


                switch (alt195) {
            	case 1 :
            	    // EsperEPL2Ast.g:584:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4207);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop195;
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
    // EsperEPL2Ast.g:587:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:588:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt208=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt208=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt208=2;
                }
                break;
            case LT:
                {
                alt208=3;
                }
                break;
            case LE:
                {
                alt208=4;
                }
                break;
            case GT:
                {
                alt208=5;
                }
                break;
            case GE:
                {
                alt208=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt208=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt208=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt208=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt208=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt208=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt208=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 208, 0, input);

                throw nvae;
            }

            switch (alt208) {
                case 1 :
                    // EsperEPL2Ast.g:588:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4223); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4225);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:589:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4232); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4234);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:590:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4241); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4243);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:591:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4250); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4252);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:592:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4259); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4261);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:593:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4268); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4270);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:594:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4277); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:594:41: ( constant[false] | filterIdentifier )
                    int alt196=2;
                    int LA196_0 = input.LA(1);

                    if ( ((LA196_0>=INT_TYPE && LA196_0<=NULL_TYPE)) ) {
                        alt196=1;
                    }
                    else if ( (LA196_0==EVENT_FILTER_IDENT) ) {
                        alt196=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 196, 0, input);

                        throw nvae;
                    }
                    switch (alt196) {
                        case 1 :
                            // EsperEPL2Ast.g:594:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4286);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:594:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4289);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:594:76: ( constant[false] | filterIdentifier )
                    int alt197=2;
                    int LA197_0 = input.LA(1);

                    if ( ((LA197_0>=INT_TYPE && LA197_0<=NULL_TYPE)) ) {
                        alt197=1;
                    }
                    else if ( (LA197_0==EVENT_FILTER_IDENT) ) {
                        alt197=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 197, 0, input);

                        throw nvae;
                    }
                    switch (alt197) {
                        case 1 :
                            // EsperEPL2Ast.g:594:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4293);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:594:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4296);
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
                    // EsperEPL2Ast.g:595:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4310); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:595:45: ( constant[false] | filterIdentifier )
                    int alt198=2;
                    int LA198_0 = input.LA(1);

                    if ( ((LA198_0>=INT_TYPE && LA198_0<=NULL_TYPE)) ) {
                        alt198=1;
                    }
                    else if ( (LA198_0==EVENT_FILTER_IDENT) ) {
                        alt198=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 198, 0, input);

                        throw nvae;
                    }
                    switch (alt198) {
                        case 1 :
                            // EsperEPL2Ast.g:595:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4319);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:595:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4322);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:595:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:595:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4326);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:595:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4329);
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
                    // EsperEPL2Ast.g:596:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4343); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:596:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:596:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4352);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:596:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4355);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:596:73: ( constant[false] | filterIdentifier )*
                    loop201:
                    do {
                        int alt201=3;
                        int LA201_0 = input.LA(1);

                        if ( ((LA201_0>=INT_TYPE && LA201_0<=NULL_TYPE)) ) {
                            alt201=1;
                        }
                        else if ( (LA201_0==EVENT_FILTER_IDENT) ) {
                            alt201=2;
                        }


                        switch (alt201) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:596:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4359);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:596:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4362);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop201;
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
                    // EsperEPL2Ast.g:597:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4377); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:597:42: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:597:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4386);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:597:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4389);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:597:77: ( constant[false] | filterIdentifier )*
                    loop203:
                    do {
                        int alt203=3;
                        int LA203_0 = input.LA(1);

                        if ( ((LA203_0>=INT_TYPE && LA203_0<=NULL_TYPE)) ) {
                            alt203=1;
                        }
                        else if ( (LA203_0==EVENT_FILTER_IDENT) ) {
                            alt203=2;
                        }


                        switch (alt203) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:597:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4393);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:597:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4396);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop203;
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
                    // EsperEPL2Ast.g:598:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4411); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:598:27: ( constant[false] | filterIdentifier )
                    int alt204=2;
                    int LA204_0 = input.LA(1);

                    if ( ((LA204_0>=INT_TYPE && LA204_0<=NULL_TYPE)) ) {
                        alt204=1;
                    }
                    else if ( (LA204_0==EVENT_FILTER_IDENT) ) {
                        alt204=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 204, 0, input);

                        throw nvae;
                    }
                    switch (alt204) {
                        case 1 :
                            // EsperEPL2Ast.g:598:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4414);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:598:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4417);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:598:62: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:598:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4421);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:598:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4424);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:599:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4432); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:599:31: ( constant[false] | filterIdentifier )
                    int alt206=2;
                    int LA206_0 = input.LA(1);

                    if ( ((LA206_0>=INT_TYPE && LA206_0<=NULL_TYPE)) ) {
                        alt206=1;
                    }
                    else if ( (LA206_0==EVENT_FILTER_IDENT) ) {
                        alt206=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 206, 0, input);

                        throw nvae;
                    }
                    switch (alt206) {
                        case 1 :
                            // EsperEPL2Ast.g:599:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4435);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:599:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4438);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:599:66: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:599:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4442);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:599:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4445);
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
    // EsperEPL2Ast.g:602:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:603:2: ( constant[false] | filterIdentifier )
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
                    // EsperEPL2Ast.g:603:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4459);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:604:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4465);
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
    // EsperEPL2Ast.g:606:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:607:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:607:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4476); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4478); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4480);
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
    // EsperEPL2Ast.g:610:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:611:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:611:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4499); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4501);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:611:44: ( eventPropertyAtomic )*
            loop210:
            do {
                int alt210=2;
                int LA210_0 = input.LA(1);

                if ( ((LA210_0>=EVENT_PROP_SIMPLE && LA210_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt210=1;
                }


                switch (alt210) {
            	case 1 :
            	    // EsperEPL2Ast.g:611:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4504);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop210;
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
    // EsperEPL2Ast.g:614:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:615:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt211=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt211=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt211=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt211=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt211=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt211=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt211=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 211, 0, input);

                throw nvae;
            }

            switch (alt211) {
                case 1 :
                    // EsperEPL2Ast.g:615:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4523); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4525); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:616:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4532); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4534); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4536); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:617:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4543); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4545); 
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
                    // EsperEPL2Ast.g:618:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4560); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4562); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:619:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4569); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4571); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4573); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:620:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4580); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4582); 
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
    // EsperEPL2Ast.g:623:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:624:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:624:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4609); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4611);
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
    // EsperEPL2Ast.g:627:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:628:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt222=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt222=1;
                }
                break;
            case HOUR_PART:
                {
                alt222=2;
                }
                break;
            case MINUTE_PART:
                {
                alt222=3;
                }
                break;
            case SECOND_PART:
                {
                alt222=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt222=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 222, 0, input);

                throw nvae;
            }

            switch (alt222) {
                case 1 :
                    // EsperEPL2Ast.g:628:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4627);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:628:13: ( hourPart )?
                    int alt212=2;
                    int LA212_0 = input.LA(1);

                    if ( (LA212_0==HOUR_PART) ) {
                        alt212=1;
                    }
                    switch (alt212) {
                        case 1 :
                            // EsperEPL2Ast.g:628:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4630);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:628:25: ( minutePart )?
                    int alt213=2;
                    int LA213_0 = input.LA(1);

                    if ( (LA213_0==MINUTE_PART) ) {
                        alt213=1;
                    }
                    switch (alt213) {
                        case 1 :
                            // EsperEPL2Ast.g:628:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4635);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:628:39: ( secondPart )?
                    int alt214=2;
                    int LA214_0 = input.LA(1);

                    if ( (LA214_0==SECOND_PART) ) {
                        alt214=1;
                    }
                    switch (alt214) {
                        case 1 :
                            // EsperEPL2Ast.g:628:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4640);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:628:53: ( millisecondPart )?
                    int alt215=2;
                    int LA215_0 = input.LA(1);

                    if ( (LA215_0==MILLISECOND_PART) ) {
                        alt215=1;
                    }
                    switch (alt215) {
                        case 1 :
                            // EsperEPL2Ast.g:628:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4645);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:629:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4652);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:629:13: ( minutePart )?
                    int alt216=2;
                    int LA216_0 = input.LA(1);

                    if ( (LA216_0==MINUTE_PART) ) {
                        alt216=1;
                    }
                    switch (alt216) {
                        case 1 :
                            // EsperEPL2Ast.g:629:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4655);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:629:27: ( secondPart )?
                    int alt217=2;
                    int LA217_0 = input.LA(1);

                    if ( (LA217_0==SECOND_PART) ) {
                        alt217=1;
                    }
                    switch (alt217) {
                        case 1 :
                            // EsperEPL2Ast.g:629:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4660);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:629:41: ( millisecondPart )?
                    int alt218=2;
                    int LA218_0 = input.LA(1);

                    if ( (LA218_0==MILLISECOND_PART) ) {
                        alt218=1;
                    }
                    switch (alt218) {
                        case 1 :
                            // EsperEPL2Ast.g:629:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4665);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:630:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4672);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:630:15: ( secondPart )?
                    int alt219=2;
                    int LA219_0 = input.LA(1);

                    if ( (LA219_0==SECOND_PART) ) {
                        alt219=1;
                    }
                    switch (alt219) {
                        case 1 :
                            // EsperEPL2Ast.g:630:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4675);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:630:29: ( millisecondPart )?
                    int alt220=2;
                    int LA220_0 = input.LA(1);

                    if ( (LA220_0==MILLISECOND_PART) ) {
                        alt220=1;
                    }
                    switch (alt220) {
                        case 1 :
                            // EsperEPL2Ast.g:630:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4680);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:631:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4687);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:631:15: ( millisecondPart )?
                    int alt221=2;
                    int LA221_0 = input.LA(1);

                    if ( (LA221_0==MILLISECOND_PART) ) {
                        alt221=1;
                    }
                    switch (alt221) {
                        case 1 :
                            // EsperEPL2Ast.g:631:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4690);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:632:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4697);
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
    // EsperEPL2Ast.g:635:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:636:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:636:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4711); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4713);
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
    // EsperEPL2Ast.g:639:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:640:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:640:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4728); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4730);
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
    // EsperEPL2Ast.g:643:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:644:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:644:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4745); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4747);
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
    // EsperEPL2Ast.g:647:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:648:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:648:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4762); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4764);
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
    // EsperEPL2Ast.g:651:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:652:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:652:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4779); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4781);
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
    // EsperEPL2Ast.g:655:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:656:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:656:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4796); 
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
    // EsperEPL2Ast.g:659:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:660:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt223=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt223=1;
                }
                break;
            case LONG_TYPE:
                {
                alt223=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt223=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt223=4;
                }
                break;
            case STRING_TYPE:
                {
                alt223=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt223=6;
                }
                break;
            case NULL_TYPE:
                {
                alt223=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 223, 0, input);

                throw nvae;
            }

            switch (alt223) {
                case 1 :
                    // EsperEPL2Ast.g:660:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4812); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:661:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4821); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:662:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4830); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:663:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4839); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:664:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4855); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:665:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4871); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:666:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4884); 
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
    // EsperEPL2Ast.g:669:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:670:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x00000FE1C0000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x00000FE1C0000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L,0x00000FE0C0000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x00000FE0C0000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000020000L,0x0000000840102800L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x080000000008C000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x080000000008C000L});
    public static final BitSet FOLLOW_IDENT_in_onExpr270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x080000000008C000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr281 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr284 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr287 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr316 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800001000000000L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr320 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800001000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr323 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x0800001000000000L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr326 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr343 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr345 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr348 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr370 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr373 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr376 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000C000180L,0x0000000000040000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr378 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000C000180L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr381 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000C000100L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr385 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000008000100L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr388 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr391 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr411 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr413 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr415 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr417 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput434 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput436 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr454 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr456 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x0800001000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr459 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x0800001000000000L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr463 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment478 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment480 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment482 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom496 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom498 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom501 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr519 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr521 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000008001000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr524 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000008001000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr528 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000008001000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr531 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000008001000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr545 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr548 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr577 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr588 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert606 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert608 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList627 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x2000000000040000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList630 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x2000000000040000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList649 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList651 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList654 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement669 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement671 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement673 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement717 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement743 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement746 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr782 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr784 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr786 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr789 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr807 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000020000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr813 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr818 = new BitSet(new long[]{0x0000000000000002L,0x0000001200000000L,0x000002F00C000180L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr823 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F00C000180L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr830 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F00C000100L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr838 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F008000100L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr845 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000002F008000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr852 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr859 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr883 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr885 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr894 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr897 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol916 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol918 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol921 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause939 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause941 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause954 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause957 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause971 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause974 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000003D00000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause977 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000003D00000L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause997 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause999 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1006 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0008400000000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1012 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0008400000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1018 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0008400000000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1024 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1030 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1036 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1054 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1056 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1073 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1077 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1079 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1081 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1083 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1098 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1100 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1102 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1118 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1120 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1137 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1139 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1141 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1161 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1163 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0003000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1186 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1188 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1190 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1208 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1210 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0004800000000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1245 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1247 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000000000000DL});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1249 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1278 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1280 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000000000000DL});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1284 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1296 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1318 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1320 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1337 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1339 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1341 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1358 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1361 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1377 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1387 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1389 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1392 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1406 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1408 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1411 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1446 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1449 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1453 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1456 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1476 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1480 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1483 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1498 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1500 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1503 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1507 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1510 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1525 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1527 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1530 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1534 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1537 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1561 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0800000000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1565 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0800000000000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1569 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0800000000000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1573 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0800000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1577 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1582 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1587 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_streamExpression1591 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1615 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1617 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1620 = new BitSet(new long[]{0x0000000037CC23C8L,0x0010000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1622 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1626 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1646 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1648 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1667 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1669 = new BitSet(new long[]{0x0000000000000000L,0x01C0000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1679 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1681 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1711 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1713 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1716 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1730 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1732 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1735 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1756 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1758 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1775 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1777 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1779 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1787 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1808 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1810 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1812 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1815 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1829 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1832 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1849 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1851 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1853 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1856 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1878 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1880 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1898 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1900 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1903 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1923 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1926 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1946 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1948 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1950 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1973 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1975 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1993 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1995 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x080001E000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2007 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2009 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2012 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2029 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2031 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2042 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2044 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2060 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2062 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2073 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2075 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2091 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2093 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2104 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L,0x0000000000000000L,0x080000000008C000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2106 = new BitSet(new long[]{0x0000000000000008L,0x0000020000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2109 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2122 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2124 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2139 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2141 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x000001E000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2144 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2160 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2163 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0C0001E000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2165 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0C0001E000000000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2169 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2171 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2175 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2178 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2196 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2198 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2200 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2202 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2204 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2206 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2225 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2227 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2242 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2255 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2257 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2269 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2271 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2293 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2318 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023FL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2327 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2360 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2362 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2365 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2379 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2381 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2383 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2386 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2400 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2402 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2404 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2418 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2420 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2432 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2434 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2436 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023FL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2445 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2450 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2463 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2465 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2467 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023FL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2476 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2481 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2494 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2496 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2507 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2520 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2526 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2571 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2623 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2647 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2649 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2651 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2670 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2692 = new BitSet(new long[]{0x0000000000000008L,0x0000140000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2703 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2710 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2752 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2755 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x00000FE000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2758 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x00000FE000000200L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2761 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x00000FE000000200L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2765 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2768 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2771 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2795 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2798 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2801 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2820 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2823 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2826 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2829 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2848 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2851 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2854 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2857 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2878 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr2880 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2899 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2901 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2920 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2922 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2941 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2945 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2957 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2959 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2961 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2980 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2982 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr2998 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x20000000000C0000L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr3001 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3003 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3006 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3024 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3026 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0800000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3029 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3034 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3038 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3041 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3061 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3064 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3077 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3080 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3100 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3102 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_inExpr3104 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3110 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x80000FE60000023BL,0x00000000001DE627L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3113 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x80000FE60000023BL,0x00000000001DE627L});
    public static final BitSet FOLLOW_set_in_inExpr3117 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3132 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3134 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_inExpr3136 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3142 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x80000FE60000023BL,0x00000000001DE627L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3145 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x80000FE60000023BL,0x00000000001DE627L});
    public static final BitSet FOLLOW_set_in_inExpr3149 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3164 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3166 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_inExpr3168 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3174 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3176 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_inExpr3178 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3193 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3195 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_inExpr3197 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3203 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3205 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_inExpr3207 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3230 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3232 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3234 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3236 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3247 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3249 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3251 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3254 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3274 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3276 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3278 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3281 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3294 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3296 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3298 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3301 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3320 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3322 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3324 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3335 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3337 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3339 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3361 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3365 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3376 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3379 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3383 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3394 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3398 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3402 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3419 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3423 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3434 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3437 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3452 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3455 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3459 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3470 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3473 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3477 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3488 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3491 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3495 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3507 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3509 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3511 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3514 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3529 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3531 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3533 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3546 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3550 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3552 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3565 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3567 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3569 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3572 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3586 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3588 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3590 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3602 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3604 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3616 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3636 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3639 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3660 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3662 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3664 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3676 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3678 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3680 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3692 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3694 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3696 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3707 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3709 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3711 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3723 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3725 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3727 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3738 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3740 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3742 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3753 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3755 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3757 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3768 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3770 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3772 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3784 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3786 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3788 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3791 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3812 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3815 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3819 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3822 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3827 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule3847 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000040400000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3865 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3870 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3880 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3882 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3896 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice3898 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3900 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3914 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3916 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3930 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3932 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3934 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3936 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3938 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3952 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice3954 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3957 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3959 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3980 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions3982 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4001 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4003 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4005 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4008 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4024 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4026 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4028 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4031 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4047 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4049 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4051 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4054 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000400000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4073 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4085 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4087 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4089 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4091 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0x83C1B0002000FC00L,0x00000FE6200003BBL,0x00000000001DE607L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4111 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4113 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4116 = new BitSet(new long[]{0x0000000037CC23C8L,0x0010000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4118 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4122 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4140 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4144 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4152 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4154 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4162 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4173 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4204 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4207 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x83C130002000FC00L,0x00000FE60000023BL,0x00000000001DE607L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4223 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4225 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4232 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4234 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4241 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4243 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4252 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4259 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4261 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4270 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4277 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4279 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4286 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4289 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4293 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4296 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4299 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4312 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4319 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4322 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4326 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4329 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4332 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4343 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4345 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4352 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4355 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4359 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4362 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4366 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4377 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4379 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4386 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4389 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4393 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4396 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x80000FE000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4400 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4411 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4414 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4417 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4421 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4424 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4432 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4435 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4438 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x00000FE000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4442 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4445 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4476 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4478 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4480 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4499 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4501 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000FC0000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4504 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000FC0000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4523 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4525 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4532 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4534 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4536 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4543 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4545 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4547 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4560 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4562 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4571 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4573 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4580 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4582 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000180L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4584 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4609 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4611 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4627 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x003C000000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4630 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0038000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4635 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4640 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4652 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0038000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4655 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4660 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4665 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4672 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0030000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4675 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4687 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4711 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4713 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4728 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4730 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4745 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4747 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4762 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4764 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4779 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4781 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4796 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4821 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4839 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4884 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}