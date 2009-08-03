// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-07-23 16:33:48

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "BOR", "PLUS", "QUESTION", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=165;
    public static final int FLOAT_SUFFIX=311;
    public static final int STAR=252;
    public static final int NUMERIC_PARAM_LIST=106;
    public static final int ISTREAM=60;
    public static final int MOD=272;
    public static final int OUTERJOIN_EXPR=148;
    public static final int BSR=293;
    public static final int LIB_FUNCTION=171;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=93;
    public static final int FULL_OUTERJOIN_EXPR=152;
    public static final int MATCHREC_PATTERN_CONCAT=236;
    public static final int RPAREN=251;
    public static final int LNOT=282;
    public static final int INC=286;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=259;
    public static final int BSR_ASSIGN=294;
    public static final int CAST_EXPR=200;
    public static final int MATCHES=103;
    public static final int STREAM_EXPR=147;
    public static final int TIMEPERIOD_SECONDS=90;
    public static final int NOT_EQUAL=264;
    public static final int METADATASQL=67;
    public static final int EVENT_FILTER_PROPERTY_EXPR=115;
    public static final int LAST_AGGREG=224;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=109;
    public static final int FOLLOWED_BY=276;
    public static final int HOUR_PART=176;
    public static final int RBRACK=257;
    public static final int MATCHREC_PATTERN_NESTED=238;
    public static final int MATCH_UNTIL_RANGE_CLOSED=215;
    public static final int GE=268;
    public static final int METHOD_JOIN_EXPR=211;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=114;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=113;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=287;
    public static final int EVENT_FILTER_NOT_IN=125;
    public static final int INSERTINTO_STREAM_NAME=188;
    public static final int NUM_DOUBLE=232;
    public static final int UNARY_MINUS=172;
    public static final int TIMEPERIOD_MILLISEC=91;
    public static final int LCURLY=273;
    public static final int RETAINUNION=63;
    public static final int DBWHERE_CLAUSE=186;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=123;
    public static final int GROUP=44;
    public static final int EMAILAT=302;
    public static final int WS=303;
    public static final int SUBSELECT_GROUP_EXPR=192;
    public static final int ON_SELECT_INSERT_EXPR=206;
    public static final int ESCAPECHAR=277;
    public static final int SL_COMMENT=304;
    public static final int NULL_TYPE=231;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=213;
    public static final int GT=266;
    public static final int BNOT=283;
    public static final int WHERE_EXPR=134;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=149;
    public static final int LAND=300;
    public static final int NOT_REGEXP=183;
    public static final int MATCH_UNTIL_EXPR=212;
    public static final int EVENT_PROP_EXPR=156;
    public static final int LBRACK=256;
    public static final int VIEW_EXPR=131;
    public static final int ANNOTATION=220;
    public static final int LONG_TYPE=226;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=116;
    public static final int MATCHREC_PATTERN=234;
    public static final int TIMEPERIOD_SEC=88;
    public static final int ON_SELECT_EXPR=205;
    public static final int TICKED_STRING_LITERAL=278;
    public static final int MINUTE_PART=177;
    public static final int PATTERN_NOT_EXPR=112;
    public static final int SUM=18;
    public static final int SQL_NE=263;
    public static final int HexDigit=309;
    public static final int LPAREN=250;
    public static final int AT=81;
    public static final int IN_SUBSELECT_EXPR=194;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=94;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=240;
    public static final int NOT_IN_RANGE=190;
    public static final int OFFSET=98;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=68;
    public static final int SECOND_PART=178;
    public static final int MATCH_RECOGNIZE=99;
    public static final int IDENT=247;
    public static final int DATABASE_JOIN_EXPR=133;
    public static final int BXOR=262;
    public static final int PLUS=254;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=82;
    public static final int EXISTS=70;
    public static final int EVENT_PROP_INDEXED=159;
    public static final int TIMEPERIOD_MILLISECOND=92;
    public static final int EVAL_NOTEQUALS_EXPR=140;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=214;
    public static final int CREATE_VARIABLE_EXPR=210;
    public static final int CREATE_WINDOW_COL_TYPE=218;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=241;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=110;
    public static final int RIGHT_OUTERJOIN_EXPR=151;
    public static final int NUMBERSETSTAR=219;
    public static final int LAST_OPERATOR=197;
    public static final int PATTERN_FILTER_EXPR=111;
    public static final int EVAL_AND_EXPR=137;
    public static final int LEFT_OUTERJOIN_EXPR=150;
    public static final int EPL_EXPR=233;
    public static final int GROUP_BY_EXPR=153;
    public static final int SET=78;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=73;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=157;
    public static final int MINUS=270;
    public static final int SEMI=301;
    public static final int STAR_ASSIGN=289;
    public static final int FIRST_AGGREG=223;
    public static final int COLON=258;
    public static final int EVAL_EQUALS_GROUP_EXPR=141;
    public static final int BAND_ASSIGN=299;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=166;
    public static final int VALUE_NULL=96;
    public static final int NOT_IN_SET=180;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=160;
    public static final int SL=295;
    public static final int NOT_IN_SUBSELECT_EXPR=195;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=129;
    public static final int SR=291;
    public static final int RCURLY=274;
    public static final int PLUS_ASSIGN=285;
    public static final int EXISTS_SUBSELECT_EXPR=193;
    public static final int DAY_PART=175;
    public static final int EVENT_FILTER_IN=124;
    public static final int DIV=271;
    public static final int OBJECT_PARAM_ORDERED_EXPR=108;
    public static final int OctalEscape=308;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=179;
    public static final int PRIOR=69;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=97;
    public static final int SELECTION_EXPR=144;
    public static final int LOR=269;
    public static final int CAST=74;
    public static final int LW=72;
    public static final int WILDCARD_SELECT=187;
    public static final int EXPONENT=310;
    public static final int LT=265;
    public static final int PATTERN_INCL_EXPR=132;
    public static final int ORDER_BY_EXPR=154;
    public static final int BOOL_TYPE=230;
    public static final int MOD_ASSIGN=290;
    public static final int ANNOTATION_ARRAY=221;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=196;
    public static final int EQUALS=248;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=64;
    public static final int DIV_ASSIGN=284;
    public static final int SL_ASSIGN=296;
    public static final int PATTERN=65;
    public static final int SQL=66;
    public static final int FULL=40;
    public static final int MATCHREC_AFTER_SKIP=239;
    public static final int WEEKDAY=71;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ARRAY_EXPR=174;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=95;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=142;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=126;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=89;
    public static final int FLOAT_TYPE=227;
    public static final int SUBSELECT_EXPR=191;
    public static final int ANNOTATION_VALUE=222;
    public static final int CONCAT=170;
    public static final int NUMERIC_PARAM_RANGE=105;
    public static final int CLASS_IDENT=128;
    public static final int MATCHREC_PATTERN_ALTER=237;
    public static final int ON_EXPR=203;
    public static final int CREATE_WINDOW_EXPR=201;
    public static final int PROPERTY_SELECTION_STREAM=118;
    public static final int ON_DELETE_EXPR=204;
    public static final int ON=41;
    public static final int NUM_LONG=279;
    public static final int TIME_PERIOD=173;
    public static final int DOUBLE_TYPE=228;
    public static final int DELETE=76;
    public static final int INT_TYPE=225;
    public static final int MATCHREC_PARTITION=245;
    public static final int EVAL_BITWISE_EXPR=136;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=155;
    public static final int TIMEPERIOD_HOURS=85;
    public static final int VARIABLE=79;
    public static final int SUBSTITUTION=199;
    public static final int UNTIL=80;
    public static final int STRING_TYPE=229;
    public static final int ON_SET_EXPR=209;
    public static final int MATCHREC_DEFINE_ITEM=242;
    public static final int NUM_INT=275;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=208;
    public static final int NUM_FLOAT=280;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=117;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=306;
    public static final int WEEKDAY_OPERATOR=198;
    public static final int WHERE=16;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=217;
    public static final int DEC=288;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=107;
    public static final int BXOR_ASSIGN=297;
    public static final int ORDER=56;
    public static final int SNAPSHOT=77;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=162;
    public static final int EVENT_FILTER_PARAM=121;
    public static final int IRSTREAM=61;
    public static final int MAX=20;
    public static final int DEFINE=101;
    public static final int TIMEPERIOD_DAYS=83;
    public static final int EVENT_FILTER_RANGE=122;
    public static final int ML_COMMENT=305;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=161;
    public static final int BOR_ASSIGN=298;
    public static final int COMMA=246;
    public static final int WHEN_LIMIT_EXPR=167;
    public static final int PARTITION=102;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=164;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=84;
    public static final int MATCHREC_MEASURE_ITEM=244;
    public static final int BOR=253;
    public static final int EQUAL=281;
    public static final int EVENT_FILTER_NOT_BETWEEN=127;
    public static final int IN_RANGE=189;
    public static final int DOT=249;
    public static final int CURRENT_TIMESTAMP=75;
    public static final int MATCHREC_MEASURES=243;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=119;
    public static final int INSERTINTO_EXPR=168;
    public static final int HAVING_EXPR=135;
    public static final int UNIDIRECTIONAL=62;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=216;
    public static final int EVAL_EQUALS_EXPR=139;
    public static final int TIMEPERIOD_MINUTES=87;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=182;
    public static final int EVENT_LIMIT_EXPR=163;
    public static final int NOT_BETWEEN=181;
    public static final int TIMEPERIOD_MINUTE=86;
    public static final int EVAL_OR_EXPR=138;
    public static final int ON_SELECT_INSERT_OUTPUT=207;
    public static final int AFTER=104;
    public static final int MEASURES=100;
    public static final int MATCHREC_PATTERN_ATOM=235;
    public static final int BAND=261;
    public static final int QUOTED_STRING_LITERAL=260;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=255;
    public static final int OBSERVER_EXPR=130;
    public static final int EVENT_FILTER_IDENT=120;
    public static final int EVENT_PROP_MAPPED=158;
    public static final int UnicodeEscape=307;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=184;
    public static final int SELECTION_ELEMENT_EXPR=145;
    public static final int CREATE_WINDOW_SELECT_EXPR=202;
    public static final int INSERTINTO_EXPRCOL=169;
    public static final int WINDOW=5;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=146;
    public static final int SR_ASSIGN=292;
    public static final int DBFROM_CLAUSE=185;
    public static final int LE=267;
    public static final int EVAL_IDENT=143;

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
    // EsperEPL2Ast.g:79:1: eplExpressionRule : ( selectExpr | createWindowExpr | createVariableExpr | onExpr ) ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:80:2: ( ( selectExpr | createWindowExpr | createVariableExpr | onExpr ) )
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr )
            {
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr )
            int alt6=4;
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
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr256); 

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
                    pushFollow(FOLLOW_eventFilterExpr_in_onExpr259);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:84:35: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onExpr263);
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
                    match(input,IDENT,FOLLOW_IDENT_in_onExpr266); 

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
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr273);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:85:19: onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr277);
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
                            	    pushFollow(FOLLOW_onSelectInsertExpr_in_onExpr280);
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
                                    pushFollow(FOLLOW_onSelectInsertOutput_in_onExpr283);
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
                    pushFollow(FOLLOW_onSetExpr_in_onExpr290);
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


    // $ANTLR start "onDeleteExpr"
    // EsperEPL2Ast.g:89:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:90:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause )? ) )
            // EsperEPL2Ast.g:90:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr310); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr312);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:90:32: ( whereClause )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==WHERE_EXPR) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:90:33: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr315);
                    whereClause();

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
    // EsperEPL2Ast.g:93:1: onSelectExpr : ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList ( onExprFrom )? ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:94:2: ( ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList ( onExprFrom )? ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) )
            // EsperEPL2Ast.g:94:4: ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList ( onExprFrom )? ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? )
            {
            match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr332); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:94:21: ( insertIntoExpr )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==INSERTINTO_EXPR) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // EsperEPL2Ast.g:94:21: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr334);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr337);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:94:51: ( onExprFrom )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==ON_EXPR_FROM) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // EsperEPL2Ast.g:94:51: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr339);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:94:63: ( whereClause )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE_EXPR) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:94:63: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr342);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:94:76: ( groupByClause )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==GROUP_BY_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:94:76: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr345);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:94:91: ( havingClause )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==HAVING_EXPR) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:94:91: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr348);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:94:105: ( orderByClause )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ORDER_BY_EXPR) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:94:105: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr351);
                    orderByClause();

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
    // $ANTLR end "onSelectExpr"


    // $ANTLR start "onSelectInsertExpr"
    // EsperEPL2Ast.g:97:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:98:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause )? ) )
            // EsperEPL2Ast.g:98:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr368); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr370);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr372);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:98:78: ( whereClause )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==WHERE_EXPR) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // EsperEPL2Ast.g:98:78: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr374);
                    whereClause();

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
    // EsperEPL2Ast.g:101:1: onSelectInsertOutput : ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) ;
    public final void onSelectInsertOutput() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:102:2: ( ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) ) )
            // EsperEPL2Ast.g:102:4: ^( ON_SELECT_INSERT_OUTPUT ( ALL | FIRST ) )
            {
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput390); 

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
    // EsperEPL2Ast.g:105:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:106:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ) )
            // EsperEPL2Ast.g:106:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr410); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr412);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:106:34: ( onSetAssignment )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==IDENT) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // EsperEPL2Ast.g:106:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr415);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop21;
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
    // $ANTLR end "onSetExpr"


    // $ANTLR start "onSetAssignment"
    // EsperEPL2Ast.g:109:1: onSetAssignment : IDENT valueExpr ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:110:2: ( IDENT valueExpr )
            // EsperEPL2Ast.g:110:4: IDENT valueExpr
            {
            match(input,IDENT,FOLLOW_IDENT_in_onSetAssignment430); 
            pushFollow(FOLLOW_valueExpr_in_onSetAssignment432);
            valueExpr();

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
    // $ANTLR end "onSetAssignment"


    // $ANTLR start "onExprFrom"
    // EsperEPL2Ast.g:113:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:114:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:114:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom444); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom446); 
            // EsperEPL2Ast.g:114:25: ( IDENT )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==IDENT) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:114:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom449); 

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
    // EsperEPL2Ast.g:117:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:118:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:118:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr467); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr469); 
            // EsperEPL2Ast.g:118:33: ( viewListExpr )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==VIEW_EXPR) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:118:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr472);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:118:49: ( RETAINUNION )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==RETAINUNION) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:118:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr476); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:118:62: ( RETAININTERSECTION )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==RETAININTERSECTION) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:118:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr479); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:119:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==CLASS_IDENT||LA27_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt27=1;
            }
            else if ( (LA27_0==CREATE_WINDOW_COL_TYPE_LIST) ) {
                alt27=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 27, 0, input);

                throw nvae;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:120:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:120:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:120:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:120:6: ( createSelectionList )?
                    int alt26=2;
                    int LA26_0 = input.LA(1);

                    if ( (LA26_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt26=1;
                    }
                    switch (alt26) {
                        case 1 :
                            // EsperEPL2Ast.g:120:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr493);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr496); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:122:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:122:12: ( createColTypeList )
                    // EsperEPL2Ast.g:122:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr525);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:124:4: ( createWindowExprInsert )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==INSERT) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:124:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr536);
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
    // EsperEPL2Ast.g:128:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:129:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:129:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert554); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:129:13: ( valueExpr )?
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( ((LA29_0>=IN_SET && LA29_0<=REGEXP)||LA29_0==NOT_EXPR||(LA29_0>=SUM && LA29_0<=AVG)||(LA29_0>=COALESCE && LA29_0<=COUNT)||(LA29_0>=CASE && LA29_0<=CASE2)||(LA29_0>=PREVIOUS && LA29_0<=EXISTS)||(LA29_0>=INSTANCEOF && LA29_0<=CURRENT_TIMESTAMP)||(LA29_0>=EVAL_AND_EXPR && LA29_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA29_0==EVENT_PROP_EXPR||(LA29_0>=CONCAT && LA29_0<=LIB_FUNCTION)||LA29_0==ARRAY_EXPR||(LA29_0>=NOT_IN_SET && LA29_0<=NOT_REGEXP)||(LA29_0>=IN_RANGE && LA29_0<=SUBSELECT_EXPR)||(LA29_0>=EXISTS_SUBSELECT_EXPR && LA29_0<=NOT_IN_SUBSELECT_EXPR)||LA29_0==SUBSTITUTION||(LA29_0>=FIRST_AGGREG && LA29_0<=NULL_TYPE)||(LA29_0>=STAR && LA29_0<=PLUS)||(LA29_0>=BAND && LA29_0<=BXOR)||(LA29_0>=LT && LA29_0<=GE)||(LA29_0>=MINUS && LA29_0<=MOD)) ) {
                    alt29=1;
                }
                switch (alt29) {
                    case 1 :
                        // EsperEPL2Ast.g:129:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert556);
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
    // EsperEPL2Ast.g:132:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:133:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:133:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList573); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList575);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:133:61: ( createSelectionListElement )*
            loop30:
            do {
                int alt30=2;
                int LA30_0 = input.LA(1);

                if ( (LA30_0==SELECTION_ELEMENT_EXPR||LA30_0==WILDCARD_SELECT) ) {
                    alt30=1;
                }


                switch (alt30) {
            	case 1 :
            	    // EsperEPL2Ast.g:133:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList578);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop30;
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
    // EsperEPL2Ast.g:136:1: createColTypeList : ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:137:2: ( ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:137:4: ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList597); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList599);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:137:59: ( createColTypeListElement )*
            loop31:
            do {
                int alt31=2;
                int LA31_0 = input.LA(1);

                if ( (LA31_0==CREATE_WINDOW_COL_TYPE) ) {
                    alt31=1;
                }


                switch (alt31) {
            	case 1 :
            	    // EsperEPL2Ast.g:137:60: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList602);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop31;
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
    // EsperEPL2Ast.g:140:1: createColTypeListElement : ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:141:2: ( ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) )
            // EsperEPL2Ast.g:141:4: ^( CREATE_WINDOW_COL_TYPE IDENT IDENT )
            {
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement617); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement619); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement621); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:144:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:145:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==WILDCARD_SELECT) ) {
                alt34=1;
            }
            else if ( (LA34_0==SELECTION_ELEMENT_EXPR) ) {
                alt34=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 34, 0, input);

                throw nvae;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:145:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement635); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:146:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement645); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:146:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt33=2;
                    int LA33_0 = input.LA(1);

                    if ( (LA33_0==EVENT_PROP_EXPR) ) {
                        alt33=1;
                    }
                    else if ( ((LA33_0>=INT_TYPE && LA33_0<=NULL_TYPE)) ) {
                        alt33=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 33, 0, input);

                        throw nvae;
                    }
                    switch (alt33) {
                        case 1 :
                            // EsperEPL2Ast.g:147:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:147:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:147:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement665);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:147:41: ( IDENT )?
                            int alt32=2;
                            int LA32_0 = input.LA(1);

                            if ( (LA32_0==IDENT) ) {
                                alt32=1;
                            }
                            switch (alt32) {
                                case 1 :
                                    // EsperEPL2Ast.g:147:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement669); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:148:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:148:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:148:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement691);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement694); 

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
    // EsperEPL2Ast.g:152:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:153:2: ( ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:153:4: ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr730); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr732); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr734); 
            // EsperEPL2Ast.g:153:41: ( valueExpr )?
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( ((LA35_0>=IN_SET && LA35_0<=REGEXP)||LA35_0==NOT_EXPR||(LA35_0>=SUM && LA35_0<=AVG)||(LA35_0>=COALESCE && LA35_0<=COUNT)||(LA35_0>=CASE && LA35_0<=CASE2)||(LA35_0>=PREVIOUS && LA35_0<=EXISTS)||(LA35_0>=INSTANCEOF && LA35_0<=CURRENT_TIMESTAMP)||(LA35_0>=EVAL_AND_EXPR && LA35_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA35_0==EVENT_PROP_EXPR||(LA35_0>=CONCAT && LA35_0<=LIB_FUNCTION)||LA35_0==ARRAY_EXPR||(LA35_0>=NOT_IN_SET && LA35_0<=NOT_REGEXP)||(LA35_0>=IN_RANGE && LA35_0<=SUBSELECT_EXPR)||(LA35_0>=EXISTS_SUBSELECT_EXPR && LA35_0<=NOT_IN_SUBSELECT_EXPR)||LA35_0==SUBSTITUTION||(LA35_0>=FIRST_AGGREG && LA35_0<=NULL_TYPE)||(LA35_0>=STAR && LA35_0<=PLUS)||(LA35_0>=BAND && LA35_0<=BXOR)||(LA35_0>=LT && LA35_0<=GE)||(LA35_0>=MINUS && LA35_0<=MOD)) ) {
                alt35=1;
            }
            switch (alt35) {
                case 1 :
                    // EsperEPL2Ast.g:153:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr737);
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
    // EsperEPL2Ast.g:156:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:157:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:157:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:157:4: ( insertIntoExpr )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==INSERTINTO_EXPR) ) {
                alt36=1;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:157:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr755);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr761);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr766);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:160:3: ( matchRecogClause )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==MATCH_RECOGNIZE) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // EsperEPL2Ast.g:160:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr771);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:161:3: ( whereClause )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WHERE_EXPR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:161:4: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr778);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:162:3: ( groupByClause )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==GROUP_BY_EXPR) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // EsperEPL2Ast.g:162:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr785);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:163:3: ( havingClause )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==HAVING_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:163:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr792);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:164:3: ( outputLimitExpr )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( ((LA41_0>=EVENT_LIMIT_EXPR && LA41_0<=CRONTAB_LIMIT_EXPR)||LA41_0==WHEN_LIMIT_EXPR) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:164:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr799);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:165:3: ( orderByClause )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==ORDER_BY_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:165:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr806);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:166:3: ( rowLimitClause )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==ROW_LIMIT_EXPR) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:166:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr813);
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
    // EsperEPL2Ast.g:169:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:170:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:170:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr830); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:170:24: ( ISTREAM | RSTREAM )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=RSTREAM && LA44_0<=ISTREAM)) ) {
                alt44=1;
            }
            switch (alt44) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr841); 
            // EsperEPL2Ast.g:170:51: ( insertIntoExprCol )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( (LA45_0==INSERTINTO_EXPRCOL) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:170:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr844);
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
    // EsperEPL2Ast.g:173:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:174:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:174:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol863); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol865); 
            // EsperEPL2Ast.g:174:31: ( IDENT )*
            loop46:
            do {
                int alt46=2;
                int LA46_0 = input.LA(1);

                if ( (LA46_0==IDENT) ) {
                    alt46=1;
                }


                switch (alt46) {
            	case 1 :
            	    // EsperEPL2Ast.g:174:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol868); 

            	    }
            	    break;

            	default :
            	    break loop46;
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
    // EsperEPL2Ast.g:177:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:178:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) )
            // EsperEPL2Ast.g:178:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause886); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:178:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( ((LA47_0>=RSTREAM && LA47_0<=IRSTREAM)) ) {
                alt47=1;
            }
            switch (alt47) {
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

            pushFollow(FOLLOW_selectionList_in_selectClause901);
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
    // EsperEPL2Ast.g:181:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:182:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:182:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause915);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:182:21: ( streamExpression ( outerJoin )* )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==STREAM_EXPR) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // EsperEPL2Ast.g:182:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause918);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:182:39: ( outerJoin )*
            	    loop48:
            	    do {
            	        int alt48=2;
            	        int LA48_0 = input.LA(1);

            	        if ( ((LA48_0>=INNERJOIN_EXPR && LA48_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt48=1;
            	        }


            	        switch (alt48) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:182:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause921);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop48;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop49;
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
    // EsperEPL2Ast.g:185:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:186:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:186:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause941); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:186:24: ( matchRecogPartitionBy )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==MATCHREC_PARTITION) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // EsperEPL2Ast.g:186:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause943);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause950);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:188:4: ( ALL )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==ALL) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:188:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause956); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:189:4: ( matchRecogMatchesAfterSkip )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==MATCHREC_AFTER_SKIP) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // EsperEPL2Ast.g:189:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause962);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause968);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:191:4: ( matchRecogMatchesInterval )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==MATCHREC_INTERVAL) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:191:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause974);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause980);
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
    // EsperEPL2Ast.g:195:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:196:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:196:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy998); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:196:27: ( valueExpr )+
            int cnt54=0;
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=IN_SET && LA54_0<=REGEXP)||LA54_0==NOT_EXPR||(LA54_0>=SUM && LA54_0<=AVG)||(LA54_0>=COALESCE && LA54_0<=COUNT)||(LA54_0>=CASE && LA54_0<=CASE2)||(LA54_0>=PREVIOUS && LA54_0<=EXISTS)||(LA54_0>=INSTANCEOF && LA54_0<=CURRENT_TIMESTAMP)||(LA54_0>=EVAL_AND_EXPR && LA54_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA54_0==EVENT_PROP_EXPR||(LA54_0>=CONCAT && LA54_0<=LIB_FUNCTION)||LA54_0==ARRAY_EXPR||(LA54_0>=NOT_IN_SET && LA54_0<=NOT_REGEXP)||(LA54_0>=IN_RANGE && LA54_0<=SUBSELECT_EXPR)||(LA54_0>=EXISTS_SUBSELECT_EXPR && LA54_0<=NOT_IN_SUBSELECT_EXPR)||LA54_0==SUBSTITUTION||(LA54_0>=FIRST_AGGREG && LA54_0<=NULL_TYPE)||(LA54_0>=STAR && LA54_0<=PLUS)||(LA54_0>=BAND && LA54_0<=BXOR)||(LA54_0>=LT && LA54_0<=GE)||(LA54_0>=MINUS && LA54_0<=MOD)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // EsperEPL2Ast.g:196:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1000);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt54 >= 1 ) break loop54;
                        EarlyExitException eee =
                            new EarlyExitException(54, input);
                        throw eee;
                }
                cnt54++;
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
    // EsperEPL2Ast.g:199:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT IDENT IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:200:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT IDENT IDENT ) )
            // EsperEPL2Ast.g:200:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT IDENT IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1017); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1019); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1021); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1023); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1025); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1027); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:203:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:204:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:204:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1042); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1044); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1046);
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
    // EsperEPL2Ast.g:207:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:208:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:208:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1062); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:208:26: ( matchRecogMeasureListElement )*
                loop55:
                do {
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==MATCHREC_MEASURE_ITEM) ) {
                        alt55=1;
                    }


                    switch (alt55) {
                	case 1 :
                	    // EsperEPL2Ast.g:208:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1064);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop55;
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
    // EsperEPL2Ast.g:211:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr IDENT ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:212:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr IDENT ) )
            // EsperEPL2Ast.g:212:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr IDENT )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1081); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1083);
            valueExpr();

            state._fsp--;

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1085); 
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
    // EsperEPL2Ast.g:215:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:216:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:216:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1104); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:216:25: ( matchRecogPatternAlteration )+
            int cnt56=0;
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( ((LA56_0>=MATCHREC_PATTERN_CONCAT && LA56_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // EsperEPL2Ast.g:216:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1106);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt56 >= 1 ) break loop56;
                        EarlyExitException eee =
                            new EarlyExitException(56, input);
                        throw eee;
                }
                cnt56++;
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
    // EsperEPL2Ast.g:219:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:220:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==MATCHREC_PATTERN_CONCAT) ) {
                alt58=1;
            }
            else if ( (LA58_0==MATCHREC_PATTERN_ALTER) ) {
                alt58=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }
            switch (alt58) {
                case 1 :
                    // EsperEPL2Ast.g:220:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1121);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:221:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1129); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1131);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:221:55: ( matchRecogPatternConcat )+
                    int cnt57=0;
                    loop57:
                    do {
                        int alt57=2;
                        int LA57_0 = input.LA(1);

                        if ( (LA57_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt57=1;
                        }


                        switch (alt57) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:221:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1133);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt57 >= 1 ) break loop57;
                                EarlyExitException eee =
                                    new EarlyExitException(57, input);
                                throw eee;
                        }
                        cnt57++;
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
    // EsperEPL2Ast.g:224:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:225:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:225:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1151); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:225:32: ( matchRecogPatternUnary )+
            int cnt59=0;
            loop59:
            do {
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( (LA59_0==MATCHREC_PATTERN_ATOM||LA59_0==MATCHREC_PATTERN_NESTED) ) {
                    alt59=1;
                }


                switch (alt59) {
            	case 1 :
            	    // EsperEPL2Ast.g:225:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1153);
            	    matchRecogPatternUnary();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt59 >= 1 ) break loop59;
                        EarlyExitException eee =
                            new EarlyExitException(59, input);
                        throw eee;
                }
                cnt59++;
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
    // EsperEPL2Ast.g:228:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:229:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==MATCHREC_PATTERN_NESTED) ) {
                alt60=1;
            }
            else if ( (LA60_0==MATCHREC_PATTERN_ATOM) ) {
                alt60=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 60, 0, input);

                throw nvae;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:229:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1168);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:230:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1173);
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
    // EsperEPL2Ast.g:233:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:234:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:234:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1188); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1190);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:234:60: ( PLUS | STAR | QUESTION )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==STAR||(LA61_0>=PLUS && LA61_0<=QUESTION)) ) {
                alt61=1;
            }
            switch (alt61) {
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
    // EsperEPL2Ast.g:237:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:238:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:238:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1221); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1223); 
            // EsperEPL2Ast.g:238:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==STAR||(LA63_0>=PLUS && LA63_0<=QUESTION)) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:238:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:238:63: ( QUESTION )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==QUESTION) ) {
                        alt62=1;
                    }
                    switch (alt62) {
                        case 1 :
                            // EsperEPL2Ast.g:238:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1239); 

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
    // EsperEPL2Ast.g:241:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:242:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:242:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1261); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:242:24: ( matchRecogDefineItem )+
            int cnt64=0;
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==MATCHREC_DEFINE_ITEM) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // EsperEPL2Ast.g:242:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1263);
            	    matchRecogDefineItem();

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


            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:245:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:246:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:246:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1280); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1282); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1284);
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
    // EsperEPL2Ast.g:250:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:251:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:251:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1301);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:251:25: ( selectionListElement )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( ((LA65_0>=SELECTION_ELEMENT_EXPR && LA65_0<=SELECTION_STREAM)||LA65_0==WILDCARD_SELECT) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // EsperEPL2Ast.g:251:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1304);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop65;
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
    // EsperEPL2Ast.g:254:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:255:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt68=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt68=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt68=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt68=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }

            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:255:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1320); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:256:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1330); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1332);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:256:41: ( IDENT )?
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==IDENT) ) {
                        alt66=1;
                    }
                    switch (alt66) {
                        case 1 :
                            // EsperEPL2Ast.g:256:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1335); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:257:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1349); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1351); 
                    // EsperEPL2Ast.g:257:31: ( IDENT )?
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==IDENT) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // EsperEPL2Ast.g:257:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1354); 

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
    // EsperEPL2Ast.g:260:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:261:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:261:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1373);
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
    // EsperEPL2Ast.g:264:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:265:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt73=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt73=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt73=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt73=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt73=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }

            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:265:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1387); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1389);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1392);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:265:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop69:
                    do {
                        int alt69=2;
                        int LA69_0 = input.LA(1);

                        if ( (LA69_0==EVENT_PROP_EXPR) ) {
                            alt69=1;
                        }


                        switch (alt69) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:265:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1396);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1399);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop69;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:266:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1414); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1416);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1419);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:266:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop70:
                    do {
                        int alt70=2;
                        int LA70_0 = input.LA(1);

                        if ( (LA70_0==EVENT_PROP_EXPR) ) {
                            alt70=1;
                        }


                        switch (alt70) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:266:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1423);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1426);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop70;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:267:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1441); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1443);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1446);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:267:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( (LA71_0==EVENT_PROP_EXPR) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:267:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1450);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1453);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:268:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1468); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1470);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1473);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:268:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( (LA72_0==EVENT_PROP_EXPR) ) {
                            alt72=1;
                        }


                        switch (alt72) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:268:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1477);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1480);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop72;
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
    // EsperEPL2Ast.g:271:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:272:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:272:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1501); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:272:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt74=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt74=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt74=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt74=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt74=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // EsperEPL2Ast.g:272:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1504);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:272:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1508);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:272:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1512);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:272:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1516);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:272:115: ( viewListExpr )?
            int alt75=2;
            int LA75_0 = input.LA(1);

            if ( (LA75_0==VIEW_EXPR) ) {
                alt75=1;
            }
            switch (alt75) {
                case 1 :
                    // EsperEPL2Ast.g:272:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1520);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:272:131: ( IDENT )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==IDENT) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:272:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1525); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:272:140: ( UNIDIRECTIONAL )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==UNIDIRECTIONAL) ) {
                alt77=1;
            }
            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:272:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1530); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:272:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( ((LA78_0>=RETAINUNION && LA78_0<=RETAININTERSECTION)) ) {
                alt78=1;
            }
            switch (alt78) {
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
    // EsperEPL2Ast.g:275:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:276:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:276:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1558); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:276:27: ( IDENT )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==IDENT) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:276:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1560); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1563); 
            // EsperEPL2Ast.g:276:46: ( propertyExpression )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:276:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1565);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:276:66: ( valueExpr )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( ((LA81_0>=IN_SET && LA81_0<=REGEXP)||LA81_0==NOT_EXPR||(LA81_0>=SUM && LA81_0<=AVG)||(LA81_0>=COALESCE && LA81_0<=COUNT)||(LA81_0>=CASE && LA81_0<=CASE2)||(LA81_0>=PREVIOUS && LA81_0<=EXISTS)||(LA81_0>=INSTANCEOF && LA81_0<=CURRENT_TIMESTAMP)||(LA81_0>=EVAL_AND_EXPR && LA81_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA81_0==EVENT_PROP_EXPR||(LA81_0>=CONCAT && LA81_0<=LIB_FUNCTION)||LA81_0==ARRAY_EXPR||(LA81_0>=NOT_IN_SET && LA81_0<=NOT_REGEXP)||(LA81_0>=IN_RANGE && LA81_0<=SUBSELECT_EXPR)||(LA81_0>=EXISTS_SUBSELECT_EXPR && LA81_0<=NOT_IN_SUBSELECT_EXPR)||LA81_0==SUBSTITUTION||(LA81_0>=FIRST_AGGREG && LA81_0<=NULL_TYPE)||(LA81_0>=STAR && LA81_0<=PLUS)||(LA81_0>=BAND && LA81_0<=BXOR)||(LA81_0>=LT && LA81_0<=GE)||(LA81_0>=MINUS && LA81_0<=MOD)) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // EsperEPL2Ast.g:276:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1569);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop81;
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
    // EsperEPL2Ast.g:279:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:280:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:280:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1589); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:280:34: ( propertyExpressionAtom )*
                loop82:
                do {
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt82=1;
                    }


                    switch (alt82) {
                	case 1 :
                	    // EsperEPL2Ast.g:280:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1591);
                	    propertyExpressionAtom();

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
    // $ANTLR end "propertyExpression"


    // $ANTLR start "propertyExpressionAtom"
    // EsperEPL2Ast.g:283:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:284:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:284:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1610); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:284:41: ( propertySelectionListElement )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( ((LA83_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA83_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // EsperEPL2Ast.g:284:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1612);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop83;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1615);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:284:96: ( IDENT )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==IDENT) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:284:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1618); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1622); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:284:116: ( valueExpr )?
                int alt85=2;
                int LA85_0 = input.LA(1);

                if ( ((LA85_0>=IN_SET && LA85_0<=REGEXP)||LA85_0==NOT_EXPR||(LA85_0>=SUM && LA85_0<=AVG)||(LA85_0>=COALESCE && LA85_0<=COUNT)||(LA85_0>=CASE && LA85_0<=CASE2)||(LA85_0>=PREVIOUS && LA85_0<=EXISTS)||(LA85_0>=INSTANCEOF && LA85_0<=CURRENT_TIMESTAMP)||(LA85_0>=EVAL_AND_EXPR && LA85_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA85_0==EVENT_PROP_EXPR||(LA85_0>=CONCAT && LA85_0<=LIB_FUNCTION)||LA85_0==ARRAY_EXPR||(LA85_0>=NOT_IN_SET && LA85_0<=NOT_REGEXP)||(LA85_0>=IN_RANGE && LA85_0<=SUBSELECT_EXPR)||(LA85_0>=EXISTS_SUBSELECT_EXPR && LA85_0<=NOT_IN_SUBSELECT_EXPR)||LA85_0==SUBSTITUTION||(LA85_0>=FIRST_AGGREG && LA85_0<=NULL_TYPE)||(LA85_0>=STAR && LA85_0<=PLUS)||(LA85_0>=BAND && LA85_0<=BXOR)||(LA85_0>=LT && LA85_0<=GE)||(LA85_0>=MINUS && LA85_0<=MOD)) ) {
                    alt85=1;
                }
                switch (alt85) {
                    case 1 :
                        // EsperEPL2Ast.g:284:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1624);
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
    // EsperEPL2Ast.g:287:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:288:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt88=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt88=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt88=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt88=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 88, 0, input);

                throw nvae;
            }

            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:288:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1644); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:289:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1654); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1656);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:289:50: ( IDENT )?
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( (LA86_0==IDENT) ) {
                        alt86=1;
                    }
                    switch (alt86) {
                        case 1 :
                            // EsperEPL2Ast.g:289:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1659); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:290:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1673); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1675); 
                    // EsperEPL2Ast.g:290:40: ( IDENT )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==IDENT) ) {
                        alt87=1;
                    }
                    switch (alt87) {
                        case 1 :
                            // EsperEPL2Ast.g:290:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1678); 

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
    // EsperEPL2Ast.g:293:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:294:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:294:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1699); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1701);
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
    // EsperEPL2Ast.g:297:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:298:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:298:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1718); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1720); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:298:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( ((LA89_0>=STRING_LITERAL && LA89_0<=QUOTED_STRING_LITERAL)) ) {
                alt89=1;
            }
            switch (alt89) {
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
    // EsperEPL2Ast.g:301:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:302:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:302:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1751); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1753); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1755); 
            // EsperEPL2Ast.g:302:41: ( valueExpr )*
            loop90:
            do {
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( ((LA90_0>=IN_SET && LA90_0<=REGEXP)||LA90_0==NOT_EXPR||(LA90_0>=SUM && LA90_0<=AVG)||(LA90_0>=COALESCE && LA90_0<=COUNT)||(LA90_0>=CASE && LA90_0<=CASE2)||(LA90_0>=PREVIOUS && LA90_0<=EXISTS)||(LA90_0>=INSTANCEOF && LA90_0<=CURRENT_TIMESTAMP)||(LA90_0>=EVAL_AND_EXPR && LA90_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA90_0==EVENT_PROP_EXPR||(LA90_0>=CONCAT && LA90_0<=LIB_FUNCTION)||LA90_0==ARRAY_EXPR||(LA90_0>=NOT_IN_SET && LA90_0<=NOT_REGEXP)||(LA90_0>=IN_RANGE && LA90_0<=SUBSELECT_EXPR)||(LA90_0>=EXISTS_SUBSELECT_EXPR && LA90_0<=NOT_IN_SUBSELECT_EXPR)||LA90_0==SUBSTITUTION||(LA90_0>=FIRST_AGGREG && LA90_0<=NULL_TYPE)||(LA90_0>=STAR && LA90_0<=PLUS)||(LA90_0>=BAND && LA90_0<=BXOR)||(LA90_0>=LT && LA90_0<=GE)||(LA90_0>=MINUS && LA90_0<=MOD)) ) {
                    alt90=1;
                }


                switch (alt90) {
            	case 1 :
            	    // EsperEPL2Ast.g:302:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1758);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop90;
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
    // EsperEPL2Ast.g:305:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:306:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:306:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1772);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:306:13: ( viewExpr )*
            loop91:
            do {
                int alt91=2;
                int LA91_0 = input.LA(1);

                if ( (LA91_0==VIEW_EXPR) ) {
                    alt91=1;
                }


                switch (alt91) {
            	case 1 :
            	    // EsperEPL2Ast.g:306:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1775);
            	    viewExpr();

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
    // $ANTLR end "viewListExpr"


    // $ANTLR start "viewExpr"
    // EsperEPL2Ast.g:309:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:310:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:310:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1792); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1794); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1796); 
            // EsperEPL2Ast.g:310:30: ( valueExprWithTime )*
            loop92:
            do {
                int alt92=2;
                int LA92_0 = input.LA(1);

                if ( ((LA92_0>=IN_SET && LA92_0<=REGEXP)||LA92_0==NOT_EXPR||(LA92_0>=SUM && LA92_0<=AVG)||(LA92_0>=COALESCE && LA92_0<=COUNT)||(LA92_0>=CASE && LA92_0<=CASE2)||LA92_0==LAST||(LA92_0>=PREVIOUS && LA92_0<=EXISTS)||(LA92_0>=LW && LA92_0<=CURRENT_TIMESTAMP)||(LA92_0>=NUMERIC_PARAM_RANGE && LA92_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA92_0>=EVAL_AND_EXPR && LA92_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA92_0==EVENT_PROP_EXPR||(LA92_0>=CONCAT && LA92_0<=LIB_FUNCTION)||(LA92_0>=TIME_PERIOD && LA92_0<=ARRAY_EXPR)||(LA92_0>=NOT_IN_SET && LA92_0<=NOT_REGEXP)||(LA92_0>=IN_RANGE && LA92_0<=SUBSELECT_EXPR)||(LA92_0>=EXISTS_SUBSELECT_EXPR && LA92_0<=NOT_IN_SUBSELECT_EXPR)||(LA92_0>=LAST_OPERATOR && LA92_0<=SUBSTITUTION)||LA92_0==NUMBERSETSTAR||(LA92_0>=FIRST_AGGREG && LA92_0<=NULL_TYPE)||(LA92_0>=STAR && LA92_0<=PLUS)||(LA92_0>=BAND && LA92_0<=BXOR)||(LA92_0>=LT && LA92_0<=GE)||(LA92_0>=MINUS && LA92_0<=MOD)) ) {
                    alt92=1;
                }


                switch (alt92) {
            	case 1 :
            	    // EsperEPL2Ast.g:310:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1799);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop92;
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
    // EsperEPL2Ast.g:313:1: whereClause : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:314:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:314:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1820); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1822);
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
    // $ANTLR end "whereClause"


    // $ANTLR start "groupByClause"
    // EsperEPL2Ast.g:317:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:318:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:318:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1840); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1842);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:318:32: ( valueExpr )*
            loop93:
            do {
                int alt93=2;
                int LA93_0 = input.LA(1);

                if ( ((LA93_0>=IN_SET && LA93_0<=REGEXP)||LA93_0==NOT_EXPR||(LA93_0>=SUM && LA93_0<=AVG)||(LA93_0>=COALESCE && LA93_0<=COUNT)||(LA93_0>=CASE && LA93_0<=CASE2)||(LA93_0>=PREVIOUS && LA93_0<=EXISTS)||(LA93_0>=INSTANCEOF && LA93_0<=CURRENT_TIMESTAMP)||(LA93_0>=EVAL_AND_EXPR && LA93_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA93_0==EVENT_PROP_EXPR||(LA93_0>=CONCAT && LA93_0<=LIB_FUNCTION)||LA93_0==ARRAY_EXPR||(LA93_0>=NOT_IN_SET && LA93_0<=NOT_REGEXP)||(LA93_0>=IN_RANGE && LA93_0<=SUBSELECT_EXPR)||(LA93_0>=EXISTS_SUBSELECT_EXPR && LA93_0<=NOT_IN_SUBSELECT_EXPR)||LA93_0==SUBSTITUTION||(LA93_0>=FIRST_AGGREG && LA93_0<=NULL_TYPE)||(LA93_0>=STAR && LA93_0<=PLUS)||(LA93_0>=BAND && LA93_0<=BXOR)||(LA93_0>=LT && LA93_0<=GE)||(LA93_0>=MINUS && LA93_0<=MOD)) ) {
                    alt93=1;
                }


                switch (alt93) {
            	case 1 :
            	    // EsperEPL2Ast.g:318:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1845);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop93;
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
    // EsperEPL2Ast.g:321:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:322:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:322:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1863); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1865);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:322:35: ( orderByElement )*
            loop94:
            do {
                int alt94=2;
                int LA94_0 = input.LA(1);

                if ( (LA94_0==ORDER_ELEMENT_EXPR) ) {
                    alt94=1;
                }


                switch (alt94) {
            	case 1 :
            	    // EsperEPL2Ast.g:322:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1868);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop94;
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
    // EsperEPL2Ast.g:325:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:326:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:326:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1888); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1890);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:326:38: ( ASC | DESC )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( ((LA95_0>=ASC && LA95_0<=DESC)) ) {
                alt95=1;
            }
            switch (alt95) {
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
    // EsperEPL2Ast.g:329:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:330:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:330:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1915); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1917);
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
    // EsperEPL2Ast.g:333:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:334:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt102=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt102=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt102=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt102=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
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
                    // EsperEPL2Ast.g:334:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1935); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:334:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt96=2;
                    int LA96_0 = input.LA(1);

                    if ( (LA96_0==ALL||(LA96_0>=FIRST && LA96_0<=LAST)||LA96_0==SNAPSHOT) ) {
                        alt96=1;
                    }
                    switch (alt96) {
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

                    // EsperEPL2Ast.g:334:52: ( number | IDENT )
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( ((LA97_0>=INT_TYPE && LA97_0<=DOUBLE_TYPE)) ) {
                        alt97=1;
                    }
                    else if ( (LA97_0==IDENT) ) {
                        alt97=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 97, 0, input);

                        throw nvae;
                    }
                    switch (alt97) {
                        case 1 :
                            // EsperEPL2Ast.g:334:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1949);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:334:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1951); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:335:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1968); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:335:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==ALL||(LA98_0>=FIRST && LA98_0<=LAST)||LA98_0==SNAPSHOT) ) {
                        alt98=1;
                    }
                    switch (alt98) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1981);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:336:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1996); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:336:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==ALL||(LA99_0>=FIRST && LA99_0<=LAST)||LA99_0==SNAPSHOT) ) {
                        alt99=1;
                    }
                    switch (alt99) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2009);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:337:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2024); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:337:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==ALL||(LA100_0>=FIRST && LA100_0<=LAST)||LA100_0==SNAPSHOT) ) {
                        alt100=1;
                    }
                    switch (alt100) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2037);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:337:67: ( onSetExpr )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==ON_SET_EXPR) ) {
                        alt101=1;
                    }
                    switch (alt101) {
                        case 1 :
                            // EsperEPL2Ast.g:337:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2039);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                     leaveNode(when); 

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


    // $ANTLR start "rowLimitClause"
    // EsperEPL2Ast.g:340:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:341:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:341:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2058); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:341:23: ( number | IDENT )
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( ((LA103_0>=INT_TYPE && LA103_0<=DOUBLE_TYPE)) ) {
                alt103=1;
            }
            else if ( (LA103_0==IDENT) ) {
                alt103=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }
            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:341:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2061);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:341:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2063); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:341:38: ( number | IDENT )?
            int alt104=3;
            int LA104_0 = input.LA(1);

            if ( ((LA104_0>=INT_TYPE && LA104_0<=DOUBLE_TYPE)) ) {
                alt104=1;
            }
            else if ( (LA104_0==IDENT) ) {
                alt104=2;
            }
            switch (alt104) {
                case 1 :
                    // EsperEPL2Ast.g:341:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2067);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:341:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2069); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:341:54: ( COMMA )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( (LA105_0==COMMA) ) {
                alt105=1;
            }
            switch (alt105) {
                case 1 :
                    // EsperEPL2Ast.g:341:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2073); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:341:61: ( OFFSET )?
            int alt106=2;
            int LA106_0 = input.LA(1);

            if ( (LA106_0==OFFSET) ) {
                alt106=1;
            }
            switch (alt106) {
                case 1 :
                    // EsperEPL2Ast.g:341:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2076); 

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
    // EsperEPL2Ast.g:344:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:345:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:345:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2094); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2096);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2098);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2100);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2102);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2104);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:345:121: ( valueExprWithTime )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( ((LA107_0>=IN_SET && LA107_0<=REGEXP)||LA107_0==NOT_EXPR||(LA107_0>=SUM && LA107_0<=AVG)||(LA107_0>=COALESCE && LA107_0<=COUNT)||(LA107_0>=CASE && LA107_0<=CASE2)||LA107_0==LAST||(LA107_0>=PREVIOUS && LA107_0<=EXISTS)||(LA107_0>=LW && LA107_0<=CURRENT_TIMESTAMP)||(LA107_0>=NUMERIC_PARAM_RANGE && LA107_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA107_0>=EVAL_AND_EXPR && LA107_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA107_0==EVENT_PROP_EXPR||(LA107_0>=CONCAT && LA107_0<=LIB_FUNCTION)||(LA107_0>=TIME_PERIOD && LA107_0<=ARRAY_EXPR)||(LA107_0>=NOT_IN_SET && LA107_0<=NOT_REGEXP)||(LA107_0>=IN_RANGE && LA107_0<=SUBSELECT_EXPR)||(LA107_0>=EXISTS_SUBSELECT_EXPR && LA107_0<=NOT_IN_SUBSELECT_EXPR)||(LA107_0>=LAST_OPERATOR && LA107_0<=SUBSTITUTION)||LA107_0==NUMBERSETSTAR||(LA107_0>=FIRST_AGGREG && LA107_0<=NULL_TYPE)||(LA107_0>=STAR && LA107_0<=PLUS)||(LA107_0>=BAND && LA107_0<=BXOR)||(LA107_0>=LT && LA107_0<=GE)||(LA107_0>=MINUS && LA107_0<=MOD)) ) {
                alt107=1;
            }
            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:345:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2106);
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
    // EsperEPL2Ast.g:348:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:349:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt108=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt108=1;
                }
                break;
            case GT:
                {
                alt108=2;
                }
                break;
            case LE:
                {
                alt108=3;
                }
                break;
            case GE:
                {
                alt108=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }

            switch (alt108) {
                case 1 :
                    // EsperEPL2Ast.g:349:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2123); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2125);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:350:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2138); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2140);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:351:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2153); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2155);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:352:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2167); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2169);
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
    // EsperEPL2Ast.g:355:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:356:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:356:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:356:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:357:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2191);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:358:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA111_0==EVENT_PROP_EXPR||(LA111_0>=CONCAT && LA111_0<=LIB_FUNCTION)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=SUBSELECT_EXPR)||(LA111_0>=EXISTS_SUBSELECT_EXPR && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=FIRST_AGGREG && LA111_0<=NULL_TYPE)||(LA111_0>=STAR && LA111_0<=PLUS)||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=MINUS && LA111_0<=MOD)) ) {
                alt111=1;
            }
            else if ( ((LA111_0>=ALL && LA111_0<=SOME)) ) {
                alt111=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }
            switch (alt111) {
                case 1 :
                    // EsperEPL2Ast.g:358:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2201);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:360:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:360:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt110=2;
                    int LA110_0 = input.LA(1);

                    if ( (LA110_0==UP||(LA110_0>=IN_SET && LA110_0<=REGEXP)||LA110_0==NOT_EXPR||(LA110_0>=SUM && LA110_0<=AVG)||(LA110_0>=COALESCE && LA110_0<=COUNT)||(LA110_0>=CASE && LA110_0<=CASE2)||(LA110_0>=PREVIOUS && LA110_0<=EXISTS)||(LA110_0>=INSTANCEOF && LA110_0<=CURRENT_TIMESTAMP)||(LA110_0>=EVAL_AND_EXPR && LA110_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA110_0==EVENT_PROP_EXPR||(LA110_0>=CONCAT && LA110_0<=LIB_FUNCTION)||LA110_0==ARRAY_EXPR||(LA110_0>=NOT_IN_SET && LA110_0<=NOT_REGEXP)||(LA110_0>=IN_RANGE && LA110_0<=SUBSELECT_EXPR)||(LA110_0>=EXISTS_SUBSELECT_EXPR && LA110_0<=NOT_IN_SUBSELECT_EXPR)||LA110_0==SUBSTITUTION||(LA110_0>=FIRST_AGGREG && LA110_0<=NULL_TYPE)||(LA110_0>=STAR && LA110_0<=PLUS)||(LA110_0>=BAND && LA110_0<=BXOR)||(LA110_0>=LT && LA110_0<=GE)||(LA110_0>=MINUS && LA110_0<=MOD)) ) {
                        alt110=1;
                    }
                    else if ( (LA110_0==SUBSELECT_GROUP_EXPR) ) {
                        alt110=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 110, 0, input);

                        throw nvae;
                    }
                    switch (alt110) {
                        case 1 :
                            // EsperEPL2Ast.g:360:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:360:22: ( valueExpr )*
                            loop109:
                            do {
                                int alt109=2;
                                int LA109_0 = input.LA(1);

                                if ( ((LA109_0>=IN_SET && LA109_0<=REGEXP)||LA109_0==NOT_EXPR||(LA109_0>=SUM && LA109_0<=AVG)||(LA109_0>=COALESCE && LA109_0<=COUNT)||(LA109_0>=CASE && LA109_0<=CASE2)||(LA109_0>=PREVIOUS && LA109_0<=EXISTS)||(LA109_0>=INSTANCEOF && LA109_0<=CURRENT_TIMESTAMP)||(LA109_0>=EVAL_AND_EXPR && LA109_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA109_0==EVENT_PROP_EXPR||(LA109_0>=CONCAT && LA109_0<=LIB_FUNCTION)||LA109_0==ARRAY_EXPR||(LA109_0>=NOT_IN_SET && LA109_0<=NOT_REGEXP)||(LA109_0>=IN_RANGE && LA109_0<=SUBSELECT_EXPR)||(LA109_0>=EXISTS_SUBSELECT_EXPR && LA109_0<=NOT_IN_SUBSELECT_EXPR)||LA109_0==SUBSTITUTION||(LA109_0>=FIRST_AGGREG && LA109_0<=NULL_TYPE)||(LA109_0>=STAR && LA109_0<=PLUS)||(LA109_0>=BAND && LA109_0<=BXOR)||(LA109_0>=LT && LA109_0<=GE)||(LA109_0>=MINUS && LA109_0<=MOD)) ) {
                                    alt109=1;
                                }


                                switch (alt109) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:360:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2225);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop109;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:360:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2230);
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
    // EsperEPL2Ast.g:365:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:366:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt118=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt118=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt118=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt118=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt118=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt118=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt118=6;
                }
                break;
            case NOT_EXPR:
                {
                alt118=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt118=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 118, 0, input);

                throw nvae;
            }

            switch (alt118) {
                case 1 :
                    // EsperEPL2Ast.g:366:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2256); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2258);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2260);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:366:42: ( valueExpr )*
                    loop112:
                    do {
                        int alt112=2;
                        int LA112_0 = input.LA(1);

                        if ( ((LA112_0>=IN_SET && LA112_0<=REGEXP)||LA112_0==NOT_EXPR||(LA112_0>=SUM && LA112_0<=AVG)||(LA112_0>=COALESCE && LA112_0<=COUNT)||(LA112_0>=CASE && LA112_0<=CASE2)||(LA112_0>=PREVIOUS && LA112_0<=EXISTS)||(LA112_0>=INSTANCEOF && LA112_0<=CURRENT_TIMESTAMP)||(LA112_0>=EVAL_AND_EXPR && LA112_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA112_0==EVENT_PROP_EXPR||(LA112_0>=CONCAT && LA112_0<=LIB_FUNCTION)||LA112_0==ARRAY_EXPR||(LA112_0>=NOT_IN_SET && LA112_0<=NOT_REGEXP)||(LA112_0>=IN_RANGE && LA112_0<=SUBSELECT_EXPR)||(LA112_0>=EXISTS_SUBSELECT_EXPR && LA112_0<=NOT_IN_SUBSELECT_EXPR)||LA112_0==SUBSTITUTION||(LA112_0>=FIRST_AGGREG && LA112_0<=NULL_TYPE)||(LA112_0>=STAR && LA112_0<=PLUS)||(LA112_0>=BAND && LA112_0<=BXOR)||(LA112_0>=LT && LA112_0<=GE)||(LA112_0>=MINUS && LA112_0<=MOD)) ) {
                            alt112=1;
                        }


                        switch (alt112) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:366:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2263);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop112;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:367:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2277); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2279);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2281);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:367:43: ( valueExpr )*
                    loop113:
                    do {
                        int alt113=2;
                        int LA113_0 = input.LA(1);

                        if ( ((LA113_0>=IN_SET && LA113_0<=REGEXP)||LA113_0==NOT_EXPR||(LA113_0>=SUM && LA113_0<=AVG)||(LA113_0>=COALESCE && LA113_0<=COUNT)||(LA113_0>=CASE && LA113_0<=CASE2)||(LA113_0>=PREVIOUS && LA113_0<=EXISTS)||(LA113_0>=INSTANCEOF && LA113_0<=CURRENT_TIMESTAMP)||(LA113_0>=EVAL_AND_EXPR && LA113_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA113_0==EVENT_PROP_EXPR||(LA113_0>=CONCAT && LA113_0<=LIB_FUNCTION)||LA113_0==ARRAY_EXPR||(LA113_0>=NOT_IN_SET && LA113_0<=NOT_REGEXP)||(LA113_0>=IN_RANGE && LA113_0<=SUBSELECT_EXPR)||(LA113_0>=EXISTS_SUBSELECT_EXPR && LA113_0<=NOT_IN_SUBSELECT_EXPR)||LA113_0==SUBSTITUTION||(LA113_0>=FIRST_AGGREG && LA113_0<=NULL_TYPE)||(LA113_0>=STAR && LA113_0<=PLUS)||(LA113_0>=BAND && LA113_0<=BXOR)||(LA113_0>=LT && LA113_0<=GE)||(LA113_0>=MINUS && LA113_0<=MOD)) ) {
                            alt113=1;
                        }


                        switch (alt113) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:367:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2284);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop113;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:368:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2298); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2300);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2302);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:369:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2314); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2316);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2318);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:370:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2330); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2332);
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

                    // EsperEPL2Ast.g:370:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==UP||(LA115_0>=IN_SET && LA115_0<=REGEXP)||LA115_0==NOT_EXPR||(LA115_0>=SUM && LA115_0<=AVG)||(LA115_0>=COALESCE && LA115_0<=COUNT)||(LA115_0>=CASE && LA115_0<=CASE2)||(LA115_0>=PREVIOUS && LA115_0<=EXISTS)||(LA115_0>=INSTANCEOF && LA115_0<=CURRENT_TIMESTAMP)||(LA115_0>=EVAL_AND_EXPR && LA115_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA115_0==EVENT_PROP_EXPR||(LA115_0>=CONCAT && LA115_0<=LIB_FUNCTION)||LA115_0==ARRAY_EXPR||(LA115_0>=NOT_IN_SET && LA115_0<=NOT_REGEXP)||(LA115_0>=IN_RANGE && LA115_0<=SUBSELECT_EXPR)||(LA115_0>=EXISTS_SUBSELECT_EXPR && LA115_0<=NOT_IN_SUBSELECT_EXPR)||LA115_0==SUBSTITUTION||(LA115_0>=FIRST_AGGREG && LA115_0<=NULL_TYPE)||(LA115_0>=STAR && LA115_0<=PLUS)||(LA115_0>=BAND && LA115_0<=BXOR)||(LA115_0>=LT && LA115_0<=GE)||(LA115_0>=MINUS && LA115_0<=MOD)) ) {
                        alt115=1;
                    }
                    else if ( (LA115_0==SUBSELECT_GROUP_EXPR) ) {
                        alt115=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 115, 0, input);

                        throw nvae;
                    }
                    switch (alt115) {
                        case 1 :
                            // EsperEPL2Ast.g:370:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:370:59: ( valueExpr )*
                            loop114:
                            do {
                                int alt114=2;
                                int LA114_0 = input.LA(1);

                                if ( ((LA114_0>=IN_SET && LA114_0<=REGEXP)||LA114_0==NOT_EXPR||(LA114_0>=SUM && LA114_0<=AVG)||(LA114_0>=COALESCE && LA114_0<=COUNT)||(LA114_0>=CASE && LA114_0<=CASE2)||(LA114_0>=PREVIOUS && LA114_0<=EXISTS)||(LA114_0>=INSTANCEOF && LA114_0<=CURRENT_TIMESTAMP)||(LA114_0>=EVAL_AND_EXPR && LA114_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA114_0==EVENT_PROP_EXPR||(LA114_0>=CONCAT && LA114_0<=LIB_FUNCTION)||LA114_0==ARRAY_EXPR||(LA114_0>=NOT_IN_SET && LA114_0<=NOT_REGEXP)||(LA114_0>=IN_RANGE && LA114_0<=SUBSELECT_EXPR)||(LA114_0>=EXISTS_SUBSELECT_EXPR && LA114_0<=NOT_IN_SUBSELECT_EXPR)||LA114_0==SUBSTITUTION||(LA114_0>=FIRST_AGGREG && LA114_0<=NULL_TYPE)||(LA114_0>=STAR && LA114_0<=PLUS)||(LA114_0>=BAND && LA114_0<=BXOR)||(LA114_0>=LT && LA114_0<=GE)||(LA114_0>=MINUS && LA114_0<=MOD)) ) {
                                    alt114=1;
                                }


                                switch (alt114) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:370:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2343);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop114;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:370:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2348);
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
                    // EsperEPL2Ast.g:371:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2361); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2363);
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

                    // EsperEPL2Ast.g:371:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt117=2;
                    int LA117_0 = input.LA(1);

                    if ( (LA117_0==UP||(LA117_0>=IN_SET && LA117_0<=REGEXP)||LA117_0==NOT_EXPR||(LA117_0>=SUM && LA117_0<=AVG)||(LA117_0>=COALESCE && LA117_0<=COUNT)||(LA117_0>=CASE && LA117_0<=CASE2)||(LA117_0>=PREVIOUS && LA117_0<=EXISTS)||(LA117_0>=INSTANCEOF && LA117_0<=CURRENT_TIMESTAMP)||(LA117_0>=EVAL_AND_EXPR && LA117_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA117_0==EVENT_PROP_EXPR||(LA117_0>=CONCAT && LA117_0<=LIB_FUNCTION)||LA117_0==ARRAY_EXPR||(LA117_0>=NOT_IN_SET && LA117_0<=NOT_REGEXP)||(LA117_0>=IN_RANGE && LA117_0<=SUBSELECT_EXPR)||(LA117_0>=EXISTS_SUBSELECT_EXPR && LA117_0<=NOT_IN_SUBSELECT_EXPR)||LA117_0==SUBSTITUTION||(LA117_0>=FIRST_AGGREG && LA117_0<=NULL_TYPE)||(LA117_0>=STAR && LA117_0<=PLUS)||(LA117_0>=BAND && LA117_0<=BXOR)||(LA117_0>=LT && LA117_0<=GE)||(LA117_0>=MINUS && LA117_0<=MOD)) ) {
                        alt117=1;
                    }
                    else if ( (LA117_0==SUBSELECT_GROUP_EXPR) ) {
                        alt117=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 117, 0, input);

                        throw nvae;
                    }
                    switch (alt117) {
                        case 1 :
                            // EsperEPL2Ast.g:371:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:371:63: ( valueExpr )*
                            loop116:
                            do {
                                int alt116=2;
                                int LA116_0 = input.LA(1);

                                if ( ((LA116_0>=IN_SET && LA116_0<=REGEXP)||LA116_0==NOT_EXPR||(LA116_0>=SUM && LA116_0<=AVG)||(LA116_0>=COALESCE && LA116_0<=COUNT)||(LA116_0>=CASE && LA116_0<=CASE2)||(LA116_0>=PREVIOUS && LA116_0<=EXISTS)||(LA116_0>=INSTANCEOF && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=EVAL_AND_EXPR && LA116_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA116_0==EVENT_PROP_EXPR||(LA116_0>=CONCAT && LA116_0<=LIB_FUNCTION)||LA116_0==ARRAY_EXPR||(LA116_0>=NOT_IN_SET && LA116_0<=NOT_REGEXP)||(LA116_0>=IN_RANGE && LA116_0<=SUBSELECT_EXPR)||(LA116_0>=EXISTS_SUBSELECT_EXPR && LA116_0<=NOT_IN_SUBSELECT_EXPR)||LA116_0==SUBSTITUTION||(LA116_0>=FIRST_AGGREG && LA116_0<=NULL_TYPE)||(LA116_0>=STAR && LA116_0<=PLUS)||(LA116_0>=BAND && LA116_0<=BXOR)||(LA116_0>=LT && LA116_0<=GE)||(LA116_0>=MINUS && LA116_0<=MOD)) ) {
                                    alt116=1;
                                }


                                switch (alt116) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:371:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2374);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop116;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:371:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2379);
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
                    // EsperEPL2Ast.g:372:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2392); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2394);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:373:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2405);
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
    // EsperEPL2Ast.g:376:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:377:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt119=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt119=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt119=2;
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
                alt119=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt119=4;
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
                alt119=5;
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
                alt119=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt119=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt119=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt119=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt119=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt119=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt119=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt119=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt119=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt119=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt119=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 119, 0, input);

                throw nvae;
            }

            switch (alt119) {
                case 1 :
                    // EsperEPL2Ast.g:377:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2418);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:378:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2424);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:379:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2430);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:380:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2437);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:381:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2446);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:382:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2451);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:383:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2459);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:384:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2464);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:385:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2469);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:386:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2475);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:387:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2480);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:388:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2485);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:389:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2490);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:390:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2495);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:391:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2501);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:392:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2508);
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
    // EsperEPL2Ast.g:395:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:396:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt121=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt121=1;
                }
                break;
            case LW:
                {
                alt121=2;
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
                alt121=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt121=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt121=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt121=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt121=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt121=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt121=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt121=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt121=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 121, 0, input);

                throw nvae;
            }

            switch (alt121) {
                case 1 :
                    // EsperEPL2Ast.g:396:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2521); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:397:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2530); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:398:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2537);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:399:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2545); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2547);
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
                    // EsperEPL2Ast.g:400:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2562);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:401:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2568);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:402:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2573);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:403:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2578);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:404:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2588); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:404:29: ( numericParameterList )+
                    int cnt120=0;
                    loop120:
                    do {
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( (LA120_0==NUMERIC_PARAM_RANGE||LA120_0==NUMERIC_PARAM_FREQUENCY||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)) ) {
                            alt120=1;
                        }


                        switch (alt120) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:404:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2590);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt120 >= 1 ) break loop120;
                                EarlyExitException eee =
                                    new EarlyExitException(120, input);
                                throw eee;
                        }
                        cnt120++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:405:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2601); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:406:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2608);
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
    // EsperEPL2Ast.g:409:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:410:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt122=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt122=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt122=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt122=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 122, 0, input);

                throw nvae;
            }

            switch (alt122) {
                case 1 :
                    // EsperEPL2Ast.g:410:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2621);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:411:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2628);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:412:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2634);
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
    // EsperEPL2Ast.g:415:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:416:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:416:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2650); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:416:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt123=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt123=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt123=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt123=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 123, 0, input);

                throw nvae;
            }

            switch (alt123) {
                case 1 :
                    // EsperEPL2Ast.g:416:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2653);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:416:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2656);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:416:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2659);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:416:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt124=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt124=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt124=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt124=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }

            switch (alt124) {
                case 1 :
                    // EsperEPL2Ast.g:416:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2663);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:416:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2666);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:416:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2669);
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
    // EsperEPL2Ast.g:419:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:420:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:420:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2690); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:420:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt125=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt125=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt125=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:420:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2693);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:420:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2696);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:420:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2699);
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
    // EsperEPL2Ast.g:423:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:424:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:424:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2718); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:424:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt126=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt126=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt126=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt126=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                throw nvae;
            }

            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:424:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2721);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:424:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2724);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:424:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2727);
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
    // EsperEPL2Ast.g:427:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:428:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:428:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2746); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:428:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt127=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt127=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt127=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt127=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 127, 0, input);

                throw nvae;
            }

            switch (alt127) {
                case 1 :
                    // EsperEPL2Ast.g:428:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2749);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:428:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2752);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:428:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2755);
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
    // EsperEPL2Ast.g:431:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:432:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:432:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2776); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr2778);
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
    // EsperEPL2Ast.g:435:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:436:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:436:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2797); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2799);
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
    // EsperEPL2Ast.g:439:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:440:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:440:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2818); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2820);
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
    // EsperEPL2Ast.g:443:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:444:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( (LA128_0==IN_SUBSELECT_EXPR) ) {
                alt128=1;
            }
            else if ( (LA128_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt128=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 128, 0, input);

                throw nvae;
            }
            switch (alt128) {
                case 1 :
                    // EsperEPL2Ast.g:444:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2839); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2841);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2843);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:445:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2855); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2857);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2859);
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
    // EsperEPL2Ast.g:448:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:449:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:449:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2878); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2880);
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
    // EsperEPL2Ast.g:452:1: subQueryExpr : selectionListElement subSelectFilterExpr ( whereClause )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:453:2: ( selectionListElement subSelectFilterExpr ( whereClause )? )
            // EsperEPL2Ast.g:453:4: selectionListElement subSelectFilterExpr ( whereClause )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2896);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2898);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:453:45: ( whereClause )?
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( (LA129_0==WHERE_EXPR) ) {
                alt129=1;
            }
            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:453:46: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2901);
                    whereClause();

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
    // EsperEPL2Ast.g:456:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:457:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:457:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2918); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2920);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:457:36: ( viewListExpr )?
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==VIEW_EXPR) ) {
                alt130=1;
            }
            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:457:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2923);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:457:52: ( IDENT )?
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( (LA131_0==IDENT) ) {
                alt131=1;
            }
            switch (alt131) {
                case 1 :
                    // EsperEPL2Ast.g:457:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2928); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:457:61: ( RETAINUNION )?
            int alt132=2;
            int LA132_0 = input.LA(1);

            if ( (LA132_0==RETAINUNION) ) {
                alt132=1;
            }
            switch (alt132) {
                case 1 :
                    // EsperEPL2Ast.g:457:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr2932); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:457:74: ( RETAININTERSECTION )?
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==RETAININTERSECTION) ) {
                alt133=1;
            }
            switch (alt133) {
                case 1 :
                    // EsperEPL2Ast.g:457:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2935); 

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
    // EsperEPL2Ast.g:460:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:461:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt136=2;
            int LA136_0 = input.LA(1);

            if ( (LA136_0==CASE) ) {
                alt136=1;
            }
            else if ( (LA136_0==CASE2) ) {
                alt136=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 136, 0, input);

                throw nvae;
            }
            switch (alt136) {
                case 1 :
                    // EsperEPL2Ast.g:461:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2955); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:461:13: ( valueExpr )*
                        loop134:
                        do {
                            int alt134=2;
                            int LA134_0 = input.LA(1);

                            if ( ((LA134_0>=IN_SET && LA134_0<=REGEXP)||LA134_0==NOT_EXPR||(LA134_0>=SUM && LA134_0<=AVG)||(LA134_0>=COALESCE && LA134_0<=COUNT)||(LA134_0>=CASE && LA134_0<=CASE2)||(LA134_0>=PREVIOUS && LA134_0<=EXISTS)||(LA134_0>=INSTANCEOF && LA134_0<=CURRENT_TIMESTAMP)||(LA134_0>=EVAL_AND_EXPR && LA134_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA134_0==EVENT_PROP_EXPR||(LA134_0>=CONCAT && LA134_0<=LIB_FUNCTION)||LA134_0==ARRAY_EXPR||(LA134_0>=NOT_IN_SET && LA134_0<=NOT_REGEXP)||(LA134_0>=IN_RANGE && LA134_0<=SUBSELECT_EXPR)||(LA134_0>=EXISTS_SUBSELECT_EXPR && LA134_0<=NOT_IN_SUBSELECT_EXPR)||LA134_0==SUBSTITUTION||(LA134_0>=FIRST_AGGREG && LA134_0<=NULL_TYPE)||(LA134_0>=STAR && LA134_0<=PLUS)||(LA134_0>=BAND && LA134_0<=BXOR)||(LA134_0>=LT && LA134_0<=GE)||(LA134_0>=MINUS && LA134_0<=MOD)) ) {
                                alt134=1;
                            }


                            switch (alt134) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:461:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2958);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop134;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:462:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2971); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:462:14: ( valueExpr )*
                        loop135:
                        do {
                            int alt135=2;
                            int LA135_0 = input.LA(1);

                            if ( ((LA135_0>=IN_SET && LA135_0<=REGEXP)||LA135_0==NOT_EXPR||(LA135_0>=SUM && LA135_0<=AVG)||(LA135_0>=COALESCE && LA135_0<=COUNT)||(LA135_0>=CASE && LA135_0<=CASE2)||(LA135_0>=PREVIOUS && LA135_0<=EXISTS)||(LA135_0>=INSTANCEOF && LA135_0<=CURRENT_TIMESTAMP)||(LA135_0>=EVAL_AND_EXPR && LA135_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA135_0==EVENT_PROP_EXPR||(LA135_0>=CONCAT && LA135_0<=LIB_FUNCTION)||LA135_0==ARRAY_EXPR||(LA135_0>=NOT_IN_SET && LA135_0<=NOT_REGEXP)||(LA135_0>=IN_RANGE && LA135_0<=SUBSELECT_EXPR)||(LA135_0>=EXISTS_SUBSELECT_EXPR && LA135_0<=NOT_IN_SUBSELECT_EXPR)||LA135_0==SUBSTITUTION||(LA135_0>=FIRST_AGGREG && LA135_0<=NULL_TYPE)||(LA135_0>=STAR && LA135_0<=PLUS)||(LA135_0>=BAND && LA135_0<=BXOR)||(LA135_0>=LT && LA135_0<=GE)||(LA135_0>=MINUS && LA135_0<=MOD)) ) {
                                alt135=1;
                            }


                            switch (alt135) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:462:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2974);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop135;
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
    // EsperEPL2Ast.g:465:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:466:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt139=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt139=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt139=2;
                }
                break;
            case IN_RANGE:
                {
                alt139=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt139=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 139, 0, input);

                throw nvae;
            }

            switch (alt139) {
                case 1 :
                    // EsperEPL2Ast.g:466:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2994); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2996);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3004);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:466:51: ( valueExpr )*
                    loop137:
                    do {
                        int alt137=2;
                        int LA137_0 = input.LA(1);

                        if ( ((LA137_0>=IN_SET && LA137_0<=REGEXP)||LA137_0==NOT_EXPR||(LA137_0>=SUM && LA137_0<=AVG)||(LA137_0>=COALESCE && LA137_0<=COUNT)||(LA137_0>=CASE && LA137_0<=CASE2)||(LA137_0>=PREVIOUS && LA137_0<=EXISTS)||(LA137_0>=INSTANCEOF && LA137_0<=CURRENT_TIMESTAMP)||(LA137_0>=EVAL_AND_EXPR && LA137_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA137_0==EVENT_PROP_EXPR||(LA137_0>=CONCAT && LA137_0<=LIB_FUNCTION)||LA137_0==ARRAY_EXPR||(LA137_0>=NOT_IN_SET && LA137_0<=NOT_REGEXP)||(LA137_0>=IN_RANGE && LA137_0<=SUBSELECT_EXPR)||(LA137_0>=EXISTS_SUBSELECT_EXPR && LA137_0<=NOT_IN_SUBSELECT_EXPR)||LA137_0==SUBSTITUTION||(LA137_0>=FIRST_AGGREG && LA137_0<=NULL_TYPE)||(LA137_0>=STAR && LA137_0<=PLUS)||(LA137_0>=BAND && LA137_0<=BXOR)||(LA137_0>=LT && LA137_0<=GE)||(LA137_0>=MINUS && LA137_0<=MOD)) ) {
                            alt137=1;
                        }


                        switch (alt137) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:466:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3007);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop137;
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
                    // EsperEPL2Ast.g:467:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3026); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3028);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3036);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:467:55: ( valueExpr )*
                    loop138:
                    do {
                        int alt138=2;
                        int LA138_0 = input.LA(1);

                        if ( ((LA138_0>=IN_SET && LA138_0<=REGEXP)||LA138_0==NOT_EXPR||(LA138_0>=SUM && LA138_0<=AVG)||(LA138_0>=COALESCE && LA138_0<=COUNT)||(LA138_0>=CASE && LA138_0<=CASE2)||(LA138_0>=PREVIOUS && LA138_0<=EXISTS)||(LA138_0>=INSTANCEOF && LA138_0<=CURRENT_TIMESTAMP)||(LA138_0>=EVAL_AND_EXPR && LA138_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA138_0==EVENT_PROP_EXPR||(LA138_0>=CONCAT && LA138_0<=LIB_FUNCTION)||LA138_0==ARRAY_EXPR||(LA138_0>=NOT_IN_SET && LA138_0<=NOT_REGEXP)||(LA138_0>=IN_RANGE && LA138_0<=SUBSELECT_EXPR)||(LA138_0>=EXISTS_SUBSELECT_EXPR && LA138_0<=NOT_IN_SUBSELECT_EXPR)||LA138_0==SUBSTITUTION||(LA138_0>=FIRST_AGGREG && LA138_0<=NULL_TYPE)||(LA138_0>=STAR && LA138_0<=PLUS)||(LA138_0>=BAND && LA138_0<=BXOR)||(LA138_0>=LT && LA138_0<=GE)||(LA138_0>=MINUS && LA138_0<=MOD)) ) {
                            alt138=1;
                        }


                        switch (alt138) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:467:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3039);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop138;
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
                    // EsperEPL2Ast.g:468:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3058); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3060);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3068);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3070);
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
                    // EsperEPL2Ast.g:469:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3087); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3089);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3097);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3099);
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
    // EsperEPL2Ast.g:472:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:473:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==BETWEEN) ) {
                alt141=1;
            }
            else if ( (LA141_0==NOT_BETWEEN) ) {
                alt141=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 141, 0, input);

                throw nvae;
            }
            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:473:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3124); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3126);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3128);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3130);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:474:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3141); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3143);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3145);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:474:40: ( valueExpr )*
                    loop140:
                    do {
                        int alt140=2;
                        int LA140_0 = input.LA(1);

                        if ( ((LA140_0>=IN_SET && LA140_0<=REGEXP)||LA140_0==NOT_EXPR||(LA140_0>=SUM && LA140_0<=AVG)||(LA140_0>=COALESCE && LA140_0<=COUNT)||(LA140_0>=CASE && LA140_0<=CASE2)||(LA140_0>=PREVIOUS && LA140_0<=EXISTS)||(LA140_0>=INSTANCEOF && LA140_0<=CURRENT_TIMESTAMP)||(LA140_0>=EVAL_AND_EXPR && LA140_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA140_0==EVENT_PROP_EXPR||(LA140_0>=CONCAT && LA140_0<=LIB_FUNCTION)||LA140_0==ARRAY_EXPR||(LA140_0>=NOT_IN_SET && LA140_0<=NOT_REGEXP)||(LA140_0>=IN_RANGE && LA140_0<=SUBSELECT_EXPR)||(LA140_0>=EXISTS_SUBSELECT_EXPR && LA140_0<=NOT_IN_SUBSELECT_EXPR)||LA140_0==SUBSTITUTION||(LA140_0>=FIRST_AGGREG && LA140_0<=NULL_TYPE)||(LA140_0>=STAR && LA140_0<=PLUS)||(LA140_0>=BAND && LA140_0<=BXOR)||(LA140_0>=LT && LA140_0<=GE)||(LA140_0>=MINUS && LA140_0<=MOD)) ) {
                            alt140=1;
                        }


                        switch (alt140) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:474:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3148);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop140;
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
    // EsperEPL2Ast.g:477:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:478:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( (LA144_0==LIKE) ) {
                alt144=1;
            }
            else if ( (LA144_0==NOT_LIKE) ) {
                alt144=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }
            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:478:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3168); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3170);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3172);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:478:33: ( valueExpr )?
                    int alt142=2;
                    int LA142_0 = input.LA(1);

                    if ( ((LA142_0>=IN_SET && LA142_0<=REGEXP)||LA142_0==NOT_EXPR||(LA142_0>=SUM && LA142_0<=AVG)||(LA142_0>=COALESCE && LA142_0<=COUNT)||(LA142_0>=CASE && LA142_0<=CASE2)||(LA142_0>=PREVIOUS && LA142_0<=EXISTS)||(LA142_0>=INSTANCEOF && LA142_0<=CURRENT_TIMESTAMP)||(LA142_0>=EVAL_AND_EXPR && LA142_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA142_0==EVENT_PROP_EXPR||(LA142_0>=CONCAT && LA142_0<=LIB_FUNCTION)||LA142_0==ARRAY_EXPR||(LA142_0>=NOT_IN_SET && LA142_0<=NOT_REGEXP)||(LA142_0>=IN_RANGE && LA142_0<=SUBSELECT_EXPR)||(LA142_0>=EXISTS_SUBSELECT_EXPR && LA142_0<=NOT_IN_SUBSELECT_EXPR)||LA142_0==SUBSTITUTION||(LA142_0>=FIRST_AGGREG && LA142_0<=NULL_TYPE)||(LA142_0>=STAR && LA142_0<=PLUS)||(LA142_0>=BAND && LA142_0<=BXOR)||(LA142_0>=LT && LA142_0<=GE)||(LA142_0>=MINUS && LA142_0<=MOD)) ) {
                        alt142=1;
                    }
                    switch (alt142) {
                        case 1 :
                            // EsperEPL2Ast.g:478:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3175);
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
                    // EsperEPL2Ast.g:479:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3188); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3190);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3192);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:479:37: ( valueExpr )?
                    int alt143=2;
                    int LA143_0 = input.LA(1);

                    if ( ((LA143_0>=IN_SET && LA143_0<=REGEXP)||LA143_0==NOT_EXPR||(LA143_0>=SUM && LA143_0<=AVG)||(LA143_0>=COALESCE && LA143_0<=COUNT)||(LA143_0>=CASE && LA143_0<=CASE2)||(LA143_0>=PREVIOUS && LA143_0<=EXISTS)||(LA143_0>=INSTANCEOF && LA143_0<=CURRENT_TIMESTAMP)||(LA143_0>=EVAL_AND_EXPR && LA143_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA143_0==EVENT_PROP_EXPR||(LA143_0>=CONCAT && LA143_0<=LIB_FUNCTION)||LA143_0==ARRAY_EXPR||(LA143_0>=NOT_IN_SET && LA143_0<=NOT_REGEXP)||(LA143_0>=IN_RANGE && LA143_0<=SUBSELECT_EXPR)||(LA143_0>=EXISTS_SUBSELECT_EXPR && LA143_0<=NOT_IN_SUBSELECT_EXPR)||LA143_0==SUBSTITUTION||(LA143_0>=FIRST_AGGREG && LA143_0<=NULL_TYPE)||(LA143_0>=STAR && LA143_0<=PLUS)||(LA143_0>=BAND && LA143_0<=BXOR)||(LA143_0>=LT && LA143_0<=GE)||(LA143_0>=MINUS && LA143_0<=MOD)) ) {
                        alt143=1;
                    }
                    switch (alt143) {
                        case 1 :
                            // EsperEPL2Ast.g:479:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3195);
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
    // EsperEPL2Ast.g:482:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:483:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt145=2;
            int LA145_0 = input.LA(1);

            if ( (LA145_0==REGEXP) ) {
                alt145=1;
            }
            else if ( (LA145_0==NOT_REGEXP) ) {
                alt145=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }
            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:483:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3214); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3216);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3218);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:484:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3229); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3231);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3233);
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
    // EsperEPL2Ast.g:487:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:488:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt158=15;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt158=1;
                }
                break;
            case AVG:
                {
                alt158=2;
                }
                break;
            case COUNT:
                {
                alt158=3;
                }
                break;
            case MEDIAN:
                {
                alt158=4;
                }
                break;
            case STDDEV:
                {
                alt158=5;
                }
                break;
            case AVEDEV:
                {
                alt158=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt158=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt158=8;
                }
                break;
            case COALESCE:
                {
                alt158=9;
                }
                break;
            case PREVIOUS:
                {
                alt158=10;
                }
                break;
            case PRIOR:
                {
                alt158=11;
                }
                break;
            case INSTANCEOF:
                {
                alt158=12;
                }
                break;
            case CAST:
                {
                alt158=13;
                }
                break;
            case EXISTS:
                {
                alt158=14;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt158=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }

            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:488:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3252); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:488:13: ( DISTINCT )?
                    int alt146=2;
                    int LA146_0 = input.LA(1);

                    if ( (LA146_0==DISTINCT) ) {
                        alt146=1;
                    }
                    switch (alt146) {
                        case 1 :
                            // EsperEPL2Ast.g:488:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3255); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3259);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:489:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3270); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:489:12: ( DISTINCT )?
                    int alt147=2;
                    int LA147_0 = input.LA(1);

                    if ( (LA147_0==DISTINCT) ) {
                        alt147=1;
                    }
                    switch (alt147) {
                        case 1 :
                            // EsperEPL2Ast.g:489:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3273); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3277);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:490:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3288); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:490:14: ( ( DISTINCT )? valueExpr )?
                        int alt149=2;
                        int LA149_0 = input.LA(1);

                        if ( ((LA149_0>=IN_SET && LA149_0<=REGEXP)||LA149_0==NOT_EXPR||(LA149_0>=SUM && LA149_0<=AVG)||(LA149_0>=COALESCE && LA149_0<=COUNT)||(LA149_0>=CASE && LA149_0<=CASE2)||LA149_0==DISTINCT||(LA149_0>=PREVIOUS && LA149_0<=EXISTS)||(LA149_0>=INSTANCEOF && LA149_0<=CURRENT_TIMESTAMP)||(LA149_0>=EVAL_AND_EXPR && LA149_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA149_0==EVENT_PROP_EXPR||(LA149_0>=CONCAT && LA149_0<=LIB_FUNCTION)||LA149_0==ARRAY_EXPR||(LA149_0>=NOT_IN_SET && LA149_0<=NOT_REGEXP)||(LA149_0>=IN_RANGE && LA149_0<=SUBSELECT_EXPR)||(LA149_0>=EXISTS_SUBSELECT_EXPR && LA149_0<=NOT_IN_SUBSELECT_EXPR)||LA149_0==SUBSTITUTION||(LA149_0>=FIRST_AGGREG && LA149_0<=NULL_TYPE)||(LA149_0>=STAR && LA149_0<=PLUS)||(LA149_0>=BAND && LA149_0<=BXOR)||(LA149_0>=LT && LA149_0<=GE)||(LA149_0>=MINUS && LA149_0<=MOD)) ) {
                            alt149=1;
                        }
                        switch (alt149) {
                            case 1 :
                                // EsperEPL2Ast.g:490:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:490:15: ( DISTINCT )?
                                int alt148=2;
                                int LA148_0 = input.LA(1);

                                if ( (LA148_0==DISTINCT) ) {
                                    alt148=1;
                                }
                                switch (alt148) {
                                    case 1 :
                                        // EsperEPL2Ast.g:490:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3292); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3296);
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
                    // EsperEPL2Ast.g:491:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3310); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:491:15: ( DISTINCT )?
                    int alt150=2;
                    int LA150_0 = input.LA(1);

                    if ( (LA150_0==DISTINCT) ) {
                        alt150=1;
                    }
                    switch (alt150) {
                        case 1 :
                            // EsperEPL2Ast.g:491:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3313); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3317);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:492:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3328); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:492:15: ( DISTINCT )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==DISTINCT) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:492:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3331); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3335);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:493:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3346); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:493:15: ( DISTINCT )?
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( (LA152_0==DISTINCT) ) {
                        alt152=1;
                    }
                    switch (alt152) {
                        case 1 :
                            // EsperEPL2Ast.g:493:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3349); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3353);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:494:4: ^(f= LAST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3364); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:494:20: ( DISTINCT )?
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==DISTINCT) ) {
                        alt153=1;
                    }
                    switch (alt153) {
                        case 1 :
                            // EsperEPL2Ast.g:494:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3367); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3371);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:495:4: ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3382); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:495:21: ( DISTINCT )?
                    int alt154=2;
                    int LA154_0 = input.LA(1);

                    if ( (LA154_0==DISTINCT) ) {
                        alt154=1;
                    }
                    switch (alt154) {
                        case 1 :
                            // EsperEPL2Ast.g:495:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3385); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3389);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:496:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3401); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3403);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3405);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:496:38: ( valueExpr )*
                    loop155:
                    do {
                        int alt155=2;
                        int LA155_0 = input.LA(1);

                        if ( ((LA155_0>=IN_SET && LA155_0<=REGEXP)||LA155_0==NOT_EXPR||(LA155_0>=SUM && LA155_0<=AVG)||(LA155_0>=COALESCE && LA155_0<=COUNT)||(LA155_0>=CASE && LA155_0<=CASE2)||(LA155_0>=PREVIOUS && LA155_0<=EXISTS)||(LA155_0>=INSTANCEOF && LA155_0<=CURRENT_TIMESTAMP)||(LA155_0>=EVAL_AND_EXPR && LA155_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA155_0==EVENT_PROP_EXPR||(LA155_0>=CONCAT && LA155_0<=LIB_FUNCTION)||LA155_0==ARRAY_EXPR||(LA155_0>=NOT_IN_SET && LA155_0<=NOT_REGEXP)||(LA155_0>=IN_RANGE && LA155_0<=SUBSELECT_EXPR)||(LA155_0>=EXISTS_SUBSELECT_EXPR && LA155_0<=NOT_IN_SUBSELECT_EXPR)||LA155_0==SUBSTITUTION||(LA155_0>=FIRST_AGGREG && LA155_0<=NULL_TYPE)||(LA155_0>=STAR && LA155_0<=PLUS)||(LA155_0>=BAND && LA155_0<=BXOR)||(LA155_0>=LT && LA155_0<=GE)||(LA155_0>=MINUS && LA155_0<=MOD)) ) {
                            alt155=1;
                        }


                        switch (alt155) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:496:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3408);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop155;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:497:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3423); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3425);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:497:28: ( valueExpr )?
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( ((LA156_0>=IN_SET && LA156_0<=REGEXP)||LA156_0==NOT_EXPR||(LA156_0>=SUM && LA156_0<=AVG)||(LA156_0>=COALESCE && LA156_0<=COUNT)||(LA156_0>=CASE && LA156_0<=CASE2)||(LA156_0>=PREVIOUS && LA156_0<=EXISTS)||(LA156_0>=INSTANCEOF && LA156_0<=CURRENT_TIMESTAMP)||(LA156_0>=EVAL_AND_EXPR && LA156_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA156_0==EVENT_PROP_EXPR||(LA156_0>=CONCAT && LA156_0<=LIB_FUNCTION)||LA156_0==ARRAY_EXPR||(LA156_0>=NOT_IN_SET && LA156_0<=NOT_REGEXP)||(LA156_0>=IN_RANGE && LA156_0<=SUBSELECT_EXPR)||(LA156_0>=EXISTS_SUBSELECT_EXPR && LA156_0<=NOT_IN_SUBSELECT_EXPR)||LA156_0==SUBSTITUTION||(LA156_0>=FIRST_AGGREG && LA156_0<=NULL_TYPE)||(LA156_0>=STAR && LA156_0<=PLUS)||(LA156_0>=BAND && LA156_0<=BXOR)||(LA156_0>=LT && LA156_0<=GE)||(LA156_0>=MINUS && LA156_0<=MOD)) ) {
                        alt156=1;
                    }
                    switch (alt156) {
                        case 1 :
                            // EsperEPL2Ast.g:497:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3427);
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
                    // EsperEPL2Ast.g:498:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3440); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3444); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3446);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:499:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3459); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3461);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3463); 
                    // EsperEPL2Ast.g:499:42: ( CLASS_IDENT )*
                    loop157:
                    do {
                        int alt157=2;
                        int LA157_0 = input.LA(1);

                        if ( (LA157_0==CLASS_IDENT) ) {
                            alt157=1;
                        }


                        switch (alt157) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:499:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3466); 

                    	    }
                    	    break;

                    	default :
                    	    break loop157;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:500:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3480); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3482);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3484); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:501:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3496); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3498);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:502:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3510); 



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
    // EsperEPL2Ast.g:505:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:506:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:506:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3530); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:506:19: ( valueExpr )*
                loop159:
                do {
                    int alt159=2;
                    int LA159_0 = input.LA(1);

                    if ( ((LA159_0>=IN_SET && LA159_0<=REGEXP)||LA159_0==NOT_EXPR||(LA159_0>=SUM && LA159_0<=AVG)||(LA159_0>=COALESCE && LA159_0<=COUNT)||(LA159_0>=CASE && LA159_0<=CASE2)||(LA159_0>=PREVIOUS && LA159_0<=EXISTS)||(LA159_0>=INSTANCEOF && LA159_0<=CURRENT_TIMESTAMP)||(LA159_0>=EVAL_AND_EXPR && LA159_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA159_0==EVENT_PROP_EXPR||(LA159_0>=CONCAT && LA159_0<=LIB_FUNCTION)||LA159_0==ARRAY_EXPR||(LA159_0>=NOT_IN_SET && LA159_0<=NOT_REGEXP)||(LA159_0>=IN_RANGE && LA159_0<=SUBSELECT_EXPR)||(LA159_0>=EXISTS_SUBSELECT_EXPR && LA159_0<=NOT_IN_SUBSELECT_EXPR)||LA159_0==SUBSTITUTION||(LA159_0>=FIRST_AGGREG && LA159_0<=NULL_TYPE)||(LA159_0>=STAR && LA159_0<=PLUS)||(LA159_0>=BAND && LA159_0<=BXOR)||(LA159_0>=LT && LA159_0<=GE)||(LA159_0>=MINUS && LA159_0<=MOD)) ) {
                        alt159=1;
                    }


                    switch (alt159) {
                	case 1 :
                	    // EsperEPL2Ast.g:506:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3533);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop159;
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
    // EsperEPL2Ast.g:509:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:510:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt161=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt161=1;
                }
                break;
            case MINUS:
                {
                alt161=2;
                }
                break;
            case DIV:
                {
                alt161=3;
                }
                break;
            case STAR:
                {
                alt161=4;
                }
                break;
            case MOD:
                {
                alt161=5;
                }
                break;
            case BAND:
                {
                alt161=6;
                }
                break;
            case BOR:
                {
                alt161=7;
                }
                break;
            case BXOR:
                {
                alt161=8;
                }
                break;
            case CONCAT:
                {
                alt161=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }

            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:510:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3554); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3556);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3558);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:511:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3570); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3572);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3574);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:512:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3586); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3588);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3590);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:513:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3601); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3603);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3605);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:514:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3617); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3619);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3621);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:515:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3632); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3634);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3636);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:516:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3647); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3649);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3651);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:517:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3662); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3664);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3666);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:518:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3678); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3680);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3682);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:518:36: ( valueExpr )*
                    loop160:
                    do {
                        int alt160=2;
                        int LA160_0 = input.LA(1);

                        if ( ((LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||(LA160_0>=CONCAT && LA160_0<=LIB_FUNCTION)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=FIRST_AGGREG && LA160_0<=NULL_TYPE)||(LA160_0>=STAR && LA160_0<=PLUS)||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=MINUS && LA160_0<=MOD)) ) {
                            alt160=1;
                        }


                        switch (alt160) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:518:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3685);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop160;
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
    // EsperEPL2Ast.g:521:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:522:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:522:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3706); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:522:22: ( CLASS_IDENT )?
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==CLASS_IDENT) ) {
                alt162=1;
            }
            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:522:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3709); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3713); 
            // EsperEPL2Ast.g:522:43: ( DISTINCT )?
            int alt163=2;
            int LA163_0 = input.LA(1);

            if ( (LA163_0==DISTINCT) ) {
                alt163=1;
            }
            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:522:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3716); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:522:55: ( valueExpr )*
            loop164:
            do {
                int alt164=2;
                int LA164_0 = input.LA(1);

                if ( ((LA164_0>=IN_SET && LA164_0<=REGEXP)||LA164_0==NOT_EXPR||(LA164_0>=SUM && LA164_0<=AVG)||(LA164_0>=COALESCE && LA164_0<=COUNT)||(LA164_0>=CASE && LA164_0<=CASE2)||(LA164_0>=PREVIOUS && LA164_0<=EXISTS)||(LA164_0>=INSTANCEOF && LA164_0<=CURRENT_TIMESTAMP)||(LA164_0>=EVAL_AND_EXPR && LA164_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA164_0==EVENT_PROP_EXPR||(LA164_0>=CONCAT && LA164_0<=LIB_FUNCTION)||LA164_0==ARRAY_EXPR||(LA164_0>=NOT_IN_SET && LA164_0<=NOT_REGEXP)||(LA164_0>=IN_RANGE && LA164_0<=SUBSELECT_EXPR)||(LA164_0>=EXISTS_SUBSELECT_EXPR && LA164_0<=NOT_IN_SUBSELECT_EXPR)||LA164_0==SUBSTITUTION||(LA164_0>=FIRST_AGGREG && LA164_0<=NULL_TYPE)||(LA164_0>=STAR && LA164_0<=PLUS)||(LA164_0>=BAND && LA164_0<=BXOR)||(LA164_0>=LT && LA164_0<=GE)||(LA164_0>=MINUS && LA164_0<=MOD)) ) {
                    alt164=1;
                }


                switch (alt164) {
            	case 1 :
            	    // EsperEPL2Ast.g:522:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3721);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop164;
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
    // EsperEPL2Ast.g:528:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:529:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:529:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:529:4: ( annotation[true] )*
            loop165:
            do {
                int alt165=2;
                int LA165_0 = input.LA(1);

                if ( (LA165_0==ANNOTATION) ) {
                    alt165=1;
                }


                switch (alt165) {
            	case 1 :
            	    // EsperEPL2Ast.g:529:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule3741);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop165;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3745);
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
    // EsperEPL2Ast.g:532:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:533:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt169=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt169=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt169=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt169=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt169=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt169=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt169=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt169=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 169, 0, input);

                throw nvae;
            }

            switch (alt169) {
                case 1 :
                    // EsperEPL2Ast.g:533:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3759);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:534:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3764);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:535:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3774); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3776);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:536:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3790); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice3792);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3794);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:537:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3808); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3810);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:538:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3824); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3826);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3828); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3830); 
                    // EsperEPL2Ast.g:538:44: ( valueExprWithTime )*
                    loop166:
                    do {
                        int alt166=2;
                        int LA166_0 = input.LA(1);

                        if ( ((LA166_0>=IN_SET && LA166_0<=REGEXP)||LA166_0==NOT_EXPR||(LA166_0>=SUM && LA166_0<=AVG)||(LA166_0>=COALESCE && LA166_0<=COUNT)||(LA166_0>=CASE && LA166_0<=CASE2)||LA166_0==LAST||(LA166_0>=PREVIOUS && LA166_0<=EXISTS)||(LA166_0>=LW && LA166_0<=CURRENT_TIMESTAMP)||(LA166_0>=NUMERIC_PARAM_RANGE && LA166_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA166_0>=EVAL_AND_EXPR && LA166_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA166_0==EVENT_PROP_EXPR||(LA166_0>=CONCAT && LA166_0<=LIB_FUNCTION)||(LA166_0>=TIME_PERIOD && LA166_0<=ARRAY_EXPR)||(LA166_0>=NOT_IN_SET && LA166_0<=NOT_REGEXP)||(LA166_0>=IN_RANGE && LA166_0<=SUBSELECT_EXPR)||(LA166_0>=EXISTS_SUBSELECT_EXPR && LA166_0<=NOT_IN_SUBSELECT_EXPR)||(LA166_0>=LAST_OPERATOR && LA166_0<=SUBSTITUTION)||LA166_0==NUMBERSETSTAR||(LA166_0>=FIRST_AGGREG && LA166_0<=NULL_TYPE)||(LA166_0>=STAR && LA166_0<=PLUS)||(LA166_0>=BAND && LA166_0<=BXOR)||(LA166_0>=LT && LA166_0<=GE)||(LA166_0>=MINUS && LA166_0<=MOD)) ) {
                            alt166=1;
                        }


                        switch (alt166) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:538:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3832);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop166;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:539:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3846); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:539:26: ( matchUntilRange )?
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( ((LA167_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA167_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt167=1;
                    }
                    switch (alt167) {
                        case 1 :
                            // EsperEPL2Ast.g:539:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice3848);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3851);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:539:54: ( exprChoice )?
                    int alt168=2;
                    int LA168_0 = input.LA(1);

                    if ( ((LA168_0>=OR_EXPR && LA168_0<=AND_EXPR)||(LA168_0>=EVERY_EXPR && LA168_0<=EVERY_DISTINCT_EXPR)||LA168_0==FOLLOWED_BY_EXPR||(LA168_0>=PATTERN_FILTER_EXPR && LA168_0<=PATTERN_NOT_EXPR)||(LA168_0>=GUARD_EXPR && LA168_0<=OBSERVER_EXPR)||LA168_0==MATCH_UNTIL_EXPR) ) {
                        alt168=1;
                    }
                    switch (alt168) {
                        case 1 :
                            // EsperEPL2Ast.g:539:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice3853);
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
    // EsperEPL2Ast.g:543:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:544:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:544:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3874); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:544:35: ( valueExpr )+
            int cnt170=0;
            loop170:
            do {
                int alt170=2;
                int LA170_0 = input.LA(1);

                if ( ((LA170_0>=IN_SET && LA170_0<=REGEXP)||LA170_0==NOT_EXPR||(LA170_0>=SUM && LA170_0<=AVG)||(LA170_0>=COALESCE && LA170_0<=COUNT)||(LA170_0>=CASE && LA170_0<=CASE2)||(LA170_0>=PREVIOUS && LA170_0<=EXISTS)||(LA170_0>=INSTANCEOF && LA170_0<=CURRENT_TIMESTAMP)||(LA170_0>=EVAL_AND_EXPR && LA170_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA170_0==EVENT_PROP_EXPR||(LA170_0>=CONCAT && LA170_0<=LIB_FUNCTION)||LA170_0==ARRAY_EXPR||(LA170_0>=NOT_IN_SET && LA170_0<=NOT_REGEXP)||(LA170_0>=IN_RANGE && LA170_0<=SUBSELECT_EXPR)||(LA170_0>=EXISTS_SUBSELECT_EXPR && LA170_0<=NOT_IN_SUBSELECT_EXPR)||LA170_0==SUBSTITUTION||(LA170_0>=FIRST_AGGREG && LA170_0<=NULL_TYPE)||(LA170_0>=STAR && LA170_0<=PLUS)||(LA170_0>=BAND && LA170_0<=BXOR)||(LA170_0>=LT && LA170_0<=GE)||(LA170_0>=MINUS && LA170_0<=MOD)) ) {
                    alt170=1;
                }


                switch (alt170) {
            	case 1 :
            	    // EsperEPL2Ast.g:544:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions3876);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt170 >= 1 ) break loop170;
                        EarlyExitException eee =
                            new EarlyExitException(170, input);
                        throw eee;
                }
                cnt170++;
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
    // EsperEPL2Ast.g:547:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:548:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt174=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt174=1;
                }
                break;
            case OR_EXPR:
                {
                alt174=2;
                }
                break;
            case AND_EXPR:
                {
                alt174=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }

            switch (alt174) {
                case 1 :
                    // EsperEPL2Ast.g:548:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3895); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3897);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3899);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:548:48: ( exprChoice )*
                    loop171:
                    do {
                        int alt171=2;
                        int LA171_0 = input.LA(1);

                        if ( ((LA171_0>=OR_EXPR && LA171_0<=AND_EXPR)||(LA171_0>=EVERY_EXPR && LA171_0<=EVERY_DISTINCT_EXPR)||LA171_0==FOLLOWED_BY_EXPR||(LA171_0>=PATTERN_FILTER_EXPR && LA171_0<=PATTERN_NOT_EXPR)||(LA171_0>=GUARD_EXPR && LA171_0<=OBSERVER_EXPR)||LA171_0==MATCH_UNTIL_EXPR) ) {
                            alt171=1;
                        }


                        switch (alt171) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:548:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3902);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop171;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:549:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp3918); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3920);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3922);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:549:40: ( exprChoice )*
                    loop172:
                    do {
                        int alt172=2;
                        int LA172_0 = input.LA(1);

                        if ( ((LA172_0>=OR_EXPR && LA172_0<=AND_EXPR)||(LA172_0>=EVERY_EXPR && LA172_0<=EVERY_DISTINCT_EXPR)||LA172_0==FOLLOWED_BY_EXPR||(LA172_0>=PATTERN_FILTER_EXPR && LA172_0<=PATTERN_NOT_EXPR)||(LA172_0>=GUARD_EXPR && LA172_0<=OBSERVER_EXPR)||LA172_0==MATCH_UNTIL_EXPR) ) {
                            alt172=1;
                        }


                        switch (alt172) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:549:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3925);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop172;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:550:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3941); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3943);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3945);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:550:41: ( exprChoice )*
                    loop173:
                    do {
                        int alt173=2;
                        int LA173_0 = input.LA(1);

                        if ( ((LA173_0>=OR_EXPR && LA173_0<=AND_EXPR)||(LA173_0>=EVERY_EXPR && LA173_0<=EVERY_DISTINCT_EXPR)||LA173_0==FOLLOWED_BY_EXPR||(LA173_0>=PATTERN_FILTER_EXPR && LA173_0<=PATTERN_NOT_EXPR)||(LA173_0>=GUARD_EXPR && LA173_0<=OBSERVER_EXPR)||LA173_0==MATCH_UNTIL_EXPR) ) {
                            alt173=1;
                        }


                        switch (alt173) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:550:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3948);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop173;
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
    // EsperEPL2Ast.g:553:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:554:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( (LA176_0==PATTERN_FILTER_EXPR) ) {
                alt176=1;
            }
            else if ( (LA176_0==OBSERVER_EXPR) ) {
                alt176=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 176, 0, input);

                throw nvae;
            }
            switch (alt176) {
                case 1 :
                    // EsperEPL2Ast.g:554:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr3967);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:555:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3979); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3981); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3983); 
                    // EsperEPL2Ast.g:555:39: ( valueExprWithTime )*
                    loop175:
                    do {
                        int alt175=2;
                        int LA175_0 = input.LA(1);

                        if ( ((LA175_0>=IN_SET && LA175_0<=REGEXP)||LA175_0==NOT_EXPR||(LA175_0>=SUM && LA175_0<=AVG)||(LA175_0>=COALESCE && LA175_0<=COUNT)||(LA175_0>=CASE && LA175_0<=CASE2)||LA175_0==LAST||(LA175_0>=PREVIOUS && LA175_0<=EXISTS)||(LA175_0>=LW && LA175_0<=CURRENT_TIMESTAMP)||(LA175_0>=NUMERIC_PARAM_RANGE && LA175_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA175_0>=EVAL_AND_EXPR && LA175_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA175_0==EVENT_PROP_EXPR||(LA175_0>=CONCAT && LA175_0<=LIB_FUNCTION)||(LA175_0>=TIME_PERIOD && LA175_0<=ARRAY_EXPR)||(LA175_0>=NOT_IN_SET && LA175_0<=NOT_REGEXP)||(LA175_0>=IN_RANGE && LA175_0<=SUBSELECT_EXPR)||(LA175_0>=EXISTS_SUBSELECT_EXPR && LA175_0<=NOT_IN_SUBSELECT_EXPR)||(LA175_0>=LAST_OPERATOR && LA175_0<=SUBSTITUTION)||LA175_0==NUMBERSETSTAR||(LA175_0>=FIRST_AGGREG && LA175_0<=NULL_TYPE)||(LA175_0>=STAR && LA175_0<=PLUS)||(LA175_0>=BAND && LA175_0<=BXOR)||(LA175_0>=LT && LA175_0<=GE)||(LA175_0>=MINUS && LA175_0<=MOD)) ) {
                            alt175=1;
                        }


                        switch (alt175) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:555:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3985);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop175;
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
    // EsperEPL2Ast.g:558:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:559:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:559:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4005); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:559:29: ( IDENT )?
            int alt177=2;
            int LA177_0 = input.LA(1);

            if ( (LA177_0==IDENT) ) {
                alt177=1;
            }
            switch (alt177) {
                case 1 :
                    // EsperEPL2Ast.g:559:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4007); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4010); 
            // EsperEPL2Ast.g:559:48: ( propertyExpression )?
            int alt178=2;
            int LA178_0 = input.LA(1);

            if ( (LA178_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt178=1;
            }
            switch (alt178) {
                case 1 :
                    // EsperEPL2Ast.g:559:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4012);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:559:68: ( valueExpr )*
            loop179:
            do {
                int alt179=2;
                int LA179_0 = input.LA(1);

                if ( ((LA179_0>=IN_SET && LA179_0<=REGEXP)||LA179_0==NOT_EXPR||(LA179_0>=SUM && LA179_0<=AVG)||(LA179_0>=COALESCE && LA179_0<=COUNT)||(LA179_0>=CASE && LA179_0<=CASE2)||(LA179_0>=PREVIOUS && LA179_0<=EXISTS)||(LA179_0>=INSTANCEOF && LA179_0<=CURRENT_TIMESTAMP)||(LA179_0>=EVAL_AND_EXPR && LA179_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA179_0==EVENT_PROP_EXPR||(LA179_0>=CONCAT && LA179_0<=LIB_FUNCTION)||LA179_0==ARRAY_EXPR||(LA179_0>=NOT_IN_SET && LA179_0<=NOT_REGEXP)||(LA179_0>=IN_RANGE && LA179_0<=SUBSELECT_EXPR)||(LA179_0>=EXISTS_SUBSELECT_EXPR && LA179_0<=NOT_IN_SUBSELECT_EXPR)||LA179_0==SUBSTITUTION||(LA179_0>=FIRST_AGGREG && LA179_0<=NULL_TYPE)||(LA179_0>=STAR && LA179_0<=PLUS)||(LA179_0>=BAND && LA179_0<=BXOR)||(LA179_0>=LT && LA179_0<=GE)||(LA179_0>=MINUS && LA179_0<=MOD)) ) {
                    alt179=1;
                }


                switch (alt179) {
            	case 1 :
            	    // EsperEPL2Ast.g:559:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4016);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop179;
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
    // EsperEPL2Ast.g:562:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:563:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt180=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt180=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt180=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt180=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt180=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 180, 0, input);

                throw nvae;
            }

            switch (alt180) {
                case 1 :
                    // EsperEPL2Ast.g:563:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4034); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4036);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4038);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:564:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4046); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4048);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:565:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4056); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4058);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:566:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4065); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4067);
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
    // EsperEPL2Ast.g:569:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:570:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:574:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:575:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:575:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4096); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4098);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:575:35: ( valueExpr )*
            loop181:
            do {
                int alt181=2;
                int LA181_0 = input.LA(1);

                if ( ((LA181_0>=IN_SET && LA181_0<=REGEXP)||LA181_0==NOT_EXPR||(LA181_0>=SUM && LA181_0<=AVG)||(LA181_0>=COALESCE && LA181_0<=COUNT)||(LA181_0>=CASE && LA181_0<=CASE2)||(LA181_0>=PREVIOUS && LA181_0<=EXISTS)||(LA181_0>=INSTANCEOF && LA181_0<=CURRENT_TIMESTAMP)||(LA181_0>=EVAL_AND_EXPR && LA181_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA181_0==EVENT_PROP_EXPR||(LA181_0>=CONCAT && LA181_0<=LIB_FUNCTION)||LA181_0==ARRAY_EXPR||(LA181_0>=NOT_IN_SET && LA181_0<=NOT_REGEXP)||(LA181_0>=IN_RANGE && LA181_0<=SUBSELECT_EXPR)||(LA181_0>=EXISTS_SUBSELECT_EXPR && LA181_0<=NOT_IN_SUBSELECT_EXPR)||LA181_0==SUBSTITUTION||(LA181_0>=FIRST_AGGREG && LA181_0<=NULL_TYPE)||(LA181_0>=STAR && LA181_0<=PLUS)||(LA181_0>=BAND && LA181_0<=BXOR)||(LA181_0>=LT && LA181_0<=GE)||(LA181_0>=MINUS && LA181_0<=MOD)) ) {
                    alt181=1;
                }


                switch (alt181) {
            	case 1 :
            	    // EsperEPL2Ast.g:575:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4101);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop181;
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
    // EsperEPL2Ast.g:578:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:579:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt194=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt194=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt194=2;
                }
                break;
            case LT:
                {
                alt194=3;
                }
                break;
            case LE:
                {
                alt194=4;
                }
                break;
            case GT:
                {
                alt194=5;
                }
                break;
            case GE:
                {
                alt194=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt194=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt194=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt194=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt194=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt194=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt194=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 194, 0, input);

                throw nvae;
            }

            switch (alt194) {
                case 1 :
                    // EsperEPL2Ast.g:579:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4117); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4119);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:580:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4126); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4128);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:581:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4135); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4137);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:582:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4144); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4146);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:583:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4153); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4155);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:584:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4162); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4164);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:585:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4171); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:585:41: ( constant[false] | filterIdentifier )
                    int alt182=2;
                    int LA182_0 = input.LA(1);

                    if ( ((LA182_0>=INT_TYPE && LA182_0<=NULL_TYPE)) ) {
                        alt182=1;
                    }
                    else if ( (LA182_0==EVENT_FILTER_IDENT) ) {
                        alt182=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 182, 0, input);

                        throw nvae;
                    }
                    switch (alt182) {
                        case 1 :
                            // EsperEPL2Ast.g:585:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4180);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:585:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4183);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:585:76: ( constant[false] | filterIdentifier )
                    int alt183=2;
                    int LA183_0 = input.LA(1);

                    if ( ((LA183_0>=INT_TYPE && LA183_0<=NULL_TYPE)) ) {
                        alt183=1;
                    }
                    else if ( (LA183_0==EVENT_FILTER_IDENT) ) {
                        alt183=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 183, 0, input);

                        throw nvae;
                    }
                    switch (alt183) {
                        case 1 :
                            // EsperEPL2Ast.g:585:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4187);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:585:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4190);
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
                    // EsperEPL2Ast.g:586:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4204); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:586:45: ( constant[false] | filterIdentifier )
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( ((LA184_0>=INT_TYPE && LA184_0<=NULL_TYPE)) ) {
                        alt184=1;
                    }
                    else if ( (LA184_0==EVENT_FILTER_IDENT) ) {
                        alt184=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 184, 0, input);

                        throw nvae;
                    }
                    switch (alt184) {
                        case 1 :
                            // EsperEPL2Ast.g:586:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4213);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:586:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4216);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:586:80: ( constant[false] | filterIdentifier )
                    int alt185=2;
                    int LA185_0 = input.LA(1);

                    if ( ((LA185_0>=INT_TYPE && LA185_0<=NULL_TYPE)) ) {
                        alt185=1;
                    }
                    else if ( (LA185_0==EVENT_FILTER_IDENT) ) {
                        alt185=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 185, 0, input);

                        throw nvae;
                    }
                    switch (alt185) {
                        case 1 :
                            // EsperEPL2Ast.g:586:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4220);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:586:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4223);
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
                    // EsperEPL2Ast.g:587:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4237); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:587:38: ( constant[false] | filterIdentifier )
                    int alt186=2;
                    int LA186_0 = input.LA(1);

                    if ( ((LA186_0>=INT_TYPE && LA186_0<=NULL_TYPE)) ) {
                        alt186=1;
                    }
                    else if ( (LA186_0==EVENT_FILTER_IDENT) ) {
                        alt186=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 186, 0, input);

                        throw nvae;
                    }
                    switch (alt186) {
                        case 1 :
                            // EsperEPL2Ast.g:587:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4246);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:587:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4249);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:587:73: ( constant[false] | filterIdentifier )*
                    loop187:
                    do {
                        int alt187=3;
                        int LA187_0 = input.LA(1);

                        if ( ((LA187_0>=INT_TYPE && LA187_0<=NULL_TYPE)) ) {
                            alt187=1;
                        }
                        else if ( (LA187_0==EVENT_FILTER_IDENT) ) {
                            alt187=2;
                        }


                        switch (alt187) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:587:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4253);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:587:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4256);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop187;
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
                    // EsperEPL2Ast.g:588:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4271); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:588:42: ( constant[false] | filterIdentifier )
                    int alt188=2;
                    int LA188_0 = input.LA(1);

                    if ( ((LA188_0>=INT_TYPE && LA188_0<=NULL_TYPE)) ) {
                        alt188=1;
                    }
                    else if ( (LA188_0==EVENT_FILTER_IDENT) ) {
                        alt188=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 188, 0, input);

                        throw nvae;
                    }
                    switch (alt188) {
                        case 1 :
                            // EsperEPL2Ast.g:588:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4280);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:588:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4283);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:588:77: ( constant[false] | filterIdentifier )*
                    loop189:
                    do {
                        int alt189=3;
                        int LA189_0 = input.LA(1);

                        if ( ((LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)) ) {
                            alt189=1;
                        }
                        else if ( (LA189_0==EVENT_FILTER_IDENT) ) {
                            alt189=2;
                        }


                        switch (alt189) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:588:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4287);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:588:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4290);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop189;
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
                    // EsperEPL2Ast.g:589:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4305); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:589:27: ( constant[false] | filterIdentifier )
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( ((LA190_0>=INT_TYPE && LA190_0<=NULL_TYPE)) ) {
                        alt190=1;
                    }
                    else if ( (LA190_0==EVENT_FILTER_IDENT) ) {
                        alt190=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 190, 0, input);

                        throw nvae;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:589:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4308);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:589:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4311);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:589:62: ( constant[false] | filterIdentifier )
                    int alt191=2;
                    int LA191_0 = input.LA(1);

                    if ( ((LA191_0>=INT_TYPE && LA191_0<=NULL_TYPE)) ) {
                        alt191=1;
                    }
                    else if ( (LA191_0==EVENT_FILTER_IDENT) ) {
                        alt191=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 191, 0, input);

                        throw nvae;
                    }
                    switch (alt191) {
                        case 1 :
                            // EsperEPL2Ast.g:589:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4315);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:589:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4318);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:590:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4326); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:590:31: ( constant[false] | filterIdentifier )
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( ((LA192_0>=INT_TYPE && LA192_0<=NULL_TYPE)) ) {
                        alt192=1;
                    }
                    else if ( (LA192_0==EVENT_FILTER_IDENT) ) {
                        alt192=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 192, 0, input);

                        throw nvae;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:590:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4329);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:590:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4332);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:590:66: ( constant[false] | filterIdentifier )
                    int alt193=2;
                    int LA193_0 = input.LA(1);

                    if ( ((LA193_0>=INT_TYPE && LA193_0<=NULL_TYPE)) ) {
                        alt193=1;
                    }
                    else if ( (LA193_0==EVENT_FILTER_IDENT) ) {
                        alt193=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 193, 0, input);

                        throw nvae;
                    }
                    switch (alt193) {
                        case 1 :
                            // EsperEPL2Ast.g:590:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4336);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:590:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4339);
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
    // EsperEPL2Ast.g:593:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:594:2: ( constant[false] | filterIdentifier )
            int alt195=2;
            int LA195_0 = input.LA(1);

            if ( ((LA195_0>=INT_TYPE && LA195_0<=NULL_TYPE)) ) {
                alt195=1;
            }
            else if ( (LA195_0==EVENT_FILTER_IDENT) ) {
                alt195=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 195, 0, input);

                throw nvae;
            }
            switch (alt195) {
                case 1 :
                    // EsperEPL2Ast.g:594:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4353);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:595:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4359);
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
    // EsperEPL2Ast.g:597:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:598:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:598:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4370); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4372); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4374);
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
    // EsperEPL2Ast.g:601:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:602:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:602:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4393); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4395);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:602:44: ( eventPropertyAtomic )*
            loop196:
            do {
                int alt196=2;
                int LA196_0 = input.LA(1);

                if ( ((LA196_0>=EVENT_PROP_SIMPLE && LA196_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt196=1;
                }


                switch (alt196) {
            	case 1 :
            	    // EsperEPL2Ast.g:602:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4398);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop196;
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
    // EsperEPL2Ast.g:605:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:606:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt197=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt197=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt197=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt197=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt197=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt197=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt197=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 197, 0, input);

                throw nvae;
            }

            switch (alt197) {
                case 1 :
                    // EsperEPL2Ast.g:606:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4417); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4419); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:607:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4426); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4428); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4430); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:608:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4437); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4439); 
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
                    // EsperEPL2Ast.g:609:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4454); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4456); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:610:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4463); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4465); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4467); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:611:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4474); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4476); 
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
    // EsperEPL2Ast.g:614:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:615:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:615:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4503); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4505);
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
    // EsperEPL2Ast.g:618:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:619:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt208=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt208=1;
                }
                break;
            case HOUR_PART:
                {
                alt208=2;
                }
                break;
            case MINUTE_PART:
                {
                alt208=3;
                }
                break;
            case SECOND_PART:
                {
                alt208=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt208=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 208, 0, input);

                throw nvae;
            }

            switch (alt208) {
                case 1 :
                    // EsperEPL2Ast.g:619:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4521);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:619:13: ( hourPart )?
                    int alt198=2;
                    int LA198_0 = input.LA(1);

                    if ( (LA198_0==HOUR_PART) ) {
                        alt198=1;
                    }
                    switch (alt198) {
                        case 1 :
                            // EsperEPL2Ast.g:619:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4524);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:619:25: ( minutePart )?
                    int alt199=2;
                    int LA199_0 = input.LA(1);

                    if ( (LA199_0==MINUTE_PART) ) {
                        alt199=1;
                    }
                    switch (alt199) {
                        case 1 :
                            // EsperEPL2Ast.g:619:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4529);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:619:39: ( secondPart )?
                    int alt200=2;
                    int LA200_0 = input.LA(1);

                    if ( (LA200_0==SECOND_PART) ) {
                        alt200=1;
                    }
                    switch (alt200) {
                        case 1 :
                            // EsperEPL2Ast.g:619:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4534);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:619:53: ( millisecondPart )?
                    int alt201=2;
                    int LA201_0 = input.LA(1);

                    if ( (LA201_0==MILLISECOND_PART) ) {
                        alt201=1;
                    }
                    switch (alt201) {
                        case 1 :
                            // EsperEPL2Ast.g:619:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4539);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:620:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4546);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:620:13: ( minutePart )?
                    int alt202=2;
                    int LA202_0 = input.LA(1);

                    if ( (LA202_0==MINUTE_PART) ) {
                        alt202=1;
                    }
                    switch (alt202) {
                        case 1 :
                            // EsperEPL2Ast.g:620:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4549);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:620:27: ( secondPart )?
                    int alt203=2;
                    int LA203_0 = input.LA(1);

                    if ( (LA203_0==SECOND_PART) ) {
                        alt203=1;
                    }
                    switch (alt203) {
                        case 1 :
                            // EsperEPL2Ast.g:620:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4554);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:620:41: ( millisecondPart )?
                    int alt204=2;
                    int LA204_0 = input.LA(1);

                    if ( (LA204_0==MILLISECOND_PART) ) {
                        alt204=1;
                    }
                    switch (alt204) {
                        case 1 :
                            // EsperEPL2Ast.g:620:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4559);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:621:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4566);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:621:15: ( secondPart )?
                    int alt205=2;
                    int LA205_0 = input.LA(1);

                    if ( (LA205_0==SECOND_PART) ) {
                        alt205=1;
                    }
                    switch (alt205) {
                        case 1 :
                            // EsperEPL2Ast.g:621:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4569);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:621:29: ( millisecondPart )?
                    int alt206=2;
                    int LA206_0 = input.LA(1);

                    if ( (LA206_0==MILLISECOND_PART) ) {
                        alt206=1;
                    }
                    switch (alt206) {
                        case 1 :
                            // EsperEPL2Ast.g:621:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4574);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:622:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4581);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:622:15: ( millisecondPart )?
                    int alt207=2;
                    int LA207_0 = input.LA(1);

                    if ( (LA207_0==MILLISECOND_PART) ) {
                        alt207=1;
                    }
                    switch (alt207) {
                        case 1 :
                            // EsperEPL2Ast.g:622:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4584);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:623:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4591);
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
    // EsperEPL2Ast.g:626:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:627:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:627:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4605); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4607);
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
    // EsperEPL2Ast.g:630:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:631:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:631:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4622); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4624);
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
    // EsperEPL2Ast.g:634:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:635:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:635:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4639); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4641);
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
    // EsperEPL2Ast.g:638:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:639:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:639:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4656); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4658);
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
    // EsperEPL2Ast.g:642:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:643:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:643:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4673); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4675);
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
    // EsperEPL2Ast.g:646:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:647:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:647:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4690); 
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
    // EsperEPL2Ast.g:650:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:651:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt209=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt209=1;
                }
                break;
            case LONG_TYPE:
                {
                alt209=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt209=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt209=4;
                }
                break;
            case STRING_TYPE:
                {
                alt209=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt209=6;
                }
                break;
            case NULL_TYPE:
                {
                alt209=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 209, 0, input);

                throw nvae;
            }

            switch (alt209) {
                case 1 :
                    // EsperEPL2Ast.g:651:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4706); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:652:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4715); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:653:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4724); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:654:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4733); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:655:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4749); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:656:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4765); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:657:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4778); 
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
    // EsperEPL2Ast.g:660:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:661:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L,0x000000FE70000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L,0x000000FE70000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L,0x000000FE30000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L,0x000000FE30000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000010000L,0x0000000010040A00L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr259 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000023000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000023000L});
    public static final BitSet FOLLOW_IDENT_in_onExpr266 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000023000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr273 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr277 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr280 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x000000000000C000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr283 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr290 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr312 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr315 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr332 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr334 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000060000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr337 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00000000060000C0L,0x0000000000010000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr339 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00000000060000C0L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr342 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000006000080L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr345 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000080L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr348 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr370 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000060000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr372 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr374 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput390 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput392 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr410 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr412 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr415 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment430 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom446 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom449 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr467 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr469 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000009L,0x0000000002000400L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr472 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000009L,0x0000000002000400L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr476 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000009L,0x0000000002000400L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr479 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000009L,0x0000000002000400L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr493 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr496 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr525 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr536 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert554 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList573 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList575 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0800000000020000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList578 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0800000000020000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList597 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList599 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList602 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement621 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement645 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement665 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement694 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr730 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr732 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr734 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr737 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr755 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000010000010000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr761 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr766 = new BitSet(new long[]{0x0000000000000002L,0x0000000A00000000L,0x000000B8060000C0L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr771 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000000B8060000C0L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr778 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000000B806000080L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr785 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000000B804000080L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr792 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000000B804000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr799 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr806 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr830 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr832 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr841 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr844 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol863 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol865 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol868 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause886 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause888 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000060000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause901 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause915 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause918 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000001E80000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause921 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000001E80000L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause941 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause950 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000840000000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause956 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000840000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause962 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0000840000000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause968 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0003000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause974 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0003000000000000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy998 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1000 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1017 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1019 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1021 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1023 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1025 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1027 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1042 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1044 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1046 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1062 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1064 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1081 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1083 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1085 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1106 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1121 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1129 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1131 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1133 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1151 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1153 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000480000000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1173 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1188 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1190 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0xD000000000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1192 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1223 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0xD000000000000000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1227 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1239 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1261 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1263 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0004000000000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1280 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1282 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1284 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1301 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0800000000060000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1304 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0800000000060000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1320 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1332 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1335 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1349 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1351 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1354 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1373 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1387 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1389 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1392 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1396 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1399 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1414 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1416 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1419 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1423 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1426 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1443 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1446 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1450 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1453 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1470 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1473 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1477 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1480 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1501 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1504 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1508 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1512 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1516 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1520 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1525 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1530 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_streamExpression1534 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1560 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1563 = new BitSet(new long[]{0x0000000037CC23C8L,0x0008000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1565 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1569 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1589 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1591 = new BitSet(new long[]{0x0000000000000008L,0x0010000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1610 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1612 = new BitSet(new long[]{0x0000000000000000L,0x00E0000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1615 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1622 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1624 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1644 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1656 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1659 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1673 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1675 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1678 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1699 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1701 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1718 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1720 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1722 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1730 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1751 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1753 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1755 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1758 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1772 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1775 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1792 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1794 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1796 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1799 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1820 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1822 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1840 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1842 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1845 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1863 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1865 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1868 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1888 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1890 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1892 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1915 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1917 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1935 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1937 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080001E00000000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1949 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1951 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1968 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1970 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1981 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1996 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1998 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2009 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2024 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2026 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2037 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000023000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2039 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2058 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2061 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x00C0001E00000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2063 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x00C0001E00000000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2067 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2069 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2073 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2076 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2094 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2096 = new BitSet(new long[]{0x0020000037CC23C0L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2098 = new BitSet(new long[]{0x0020000037CC23C0L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2100 = new BitSet(new long[]{0x0020000037CC23C0L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2102 = new BitSet(new long[]{0x0020000037CC23C0L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2104 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2106 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2123 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2138 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2140 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2153 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2155 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2167 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2169 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2191 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2216 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008FL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2225 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2230 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2258 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2260 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2263 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2277 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2279 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2281 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2284 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2298 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2300 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2302 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2314 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2316 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2318 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2332 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2334 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008FL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2343 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2348 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2361 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2363 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2365 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008FL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2374 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2379 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2392 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2394 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2405 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2418 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2430 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2446 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2451 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2459 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2485 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2508 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2521 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2530 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2545 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2547 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2549 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2562 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2578 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2588 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2590 = new BitSet(new long[]{0x0000000000000008L,0x00000A0000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2601 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2650 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2653 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L,0x000000FE00000080L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2656 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L,0x000000FE00000080L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2659 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L,0x000000FE00000080L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2663 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2666 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2693 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2696 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2718 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2721 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2727 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2746 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2749 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2752 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2755 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2776 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr2778 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2797 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2818 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2820 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2839 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2841 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2843 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2855 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2859 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2878 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2880 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2896 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2898 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2918 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2920 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2923 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2928 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr2932 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2935 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2955 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2958 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2971 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2974 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2994 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2996 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_inExpr2998 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3004 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x780000FF8000008EL,0x000000000001DE62L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3007 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x780000FF8000008EL,0x000000000001DE62L});
    public static final BitSet FOLLOW_set_in_inExpr3011 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3026 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3028 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_inExpr3030 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3036 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x780000FF8000008EL,0x000000000001DE62L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3039 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x780000FF8000008EL,0x000000000001DE62L});
    public static final BitSet FOLLOW_set_in_inExpr3043 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3058 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3060 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_inExpr3062 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3068 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_inExpr3072 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3087 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3089 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_inExpr3091 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3097 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3099 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_inExpr3101 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3126 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3128 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3130 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3141 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3143 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3145 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3148 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3168 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3170 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3172 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3175 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3188 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3190 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3192 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3195 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3214 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3216 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3218 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3229 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3231 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3252 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3255 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3259 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3270 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3273 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3288 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3292 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3296 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3313 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3317 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3328 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3331 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3335 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3346 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3349 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3353 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3364 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3367 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3371 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3382 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3385 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3389 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3401 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3403 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3405 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3408 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3423 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3425 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3427 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3440 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3444 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3459 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3461 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3463 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3466 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3480 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3482 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3484 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3496 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3498 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3510 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3530 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3533 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3554 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3556 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3558 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3570 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3572 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3574 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3586 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3588 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3590 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3601 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3603 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3605 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3619 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3621 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3632 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3634 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3636 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3647 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3649 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3651 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3662 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3664 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3666 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3678 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3680 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3682 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3685 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3706 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3709 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3713 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3716 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3721 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule3741 = new BitSet(new long[]{0x000000000000D800L,0x0001A00000000000L,0x0000000000000006L,0x0000000010100000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3759 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3764 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3774 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3776 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3790 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice3792 = new BitSet(new long[]{0x000000000000D800L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3794 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3808 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3810 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3824 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3826 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3828 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3830 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3832 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3846 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice3848 = new BitSet(new long[]{0x000000000000D800L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3851 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3853 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3874 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions3876 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3895 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3897 = new BitSet(new long[]{0x000000000000D800L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3899 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3902 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp3918 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3920 = new BitSet(new long[]{0x000000000000D800L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3922 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3925 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3941 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3943 = new BitSet(new long[]{0x000000000000D800L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3945 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3948 = new BitSet(new long[]{0x000000000000D808L,0x0001A00000000000L,0x0000000000000006L,0x0000000000100000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr3967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3979 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3981 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3983 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3985 = new BitSet(new long[]{0x0020000037CC23C8L,0x00001E0000000F70L,0xE0F06C0010007E00L,0x700000FF880000EEL,0x000000000001DE60L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4005 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4007 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4010 = new BitSet(new long[]{0x0000000037CC23C8L,0x0008000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4012 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4016 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4034 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4036 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000010000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4038 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4046 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4048 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4056 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4058 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4065 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4067 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4096 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4098 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4101 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xE0F04C0010007E00L,0x700000FF8000008EL,0x000000000001DE60L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4119 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4126 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4128 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4137 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4144 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4146 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4153 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4155 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4162 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4173 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4180 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4183 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4187 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4190 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4193 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4204 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4206 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4213 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4216 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4220 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4223 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4226 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4239 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4246 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4249 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4253 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4256 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4260 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4271 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4273 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4280 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4283 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4287 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4290 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x080000FE00000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4305 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4308 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4311 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4315 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4318 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4326 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4329 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4332 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L,0x0000000000000000L,0x000000FE00000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4336 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4339 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4370 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4372 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4374 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4393 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4395 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00000007E0000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4398 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x00000007E0000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4417 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4419 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4426 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4428 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4430 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4439 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4454 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4456 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4463 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4465 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4467 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4474 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4476 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4478 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4503 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4505 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4521 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000F000000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4524 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000E000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4529 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000C000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4534 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4546 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000E000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4549 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000C000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4554 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4559 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4566 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000C000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4569 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4574 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4581 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4591 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4605 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4607 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4622 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4624 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4639 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4641 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4658 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4673 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4675 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4706 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4715 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4724 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4733 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4749 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4765 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}