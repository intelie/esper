// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-08-03 16:15:24

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "BOR", "PLUS", "QUESTION", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=166;
    public static final int FLOAT_SUFFIX=314;
    public static final int STAR=255;
    public static final int NUMERIC_PARAM_LIST=107;
    public static final int ISTREAM=60;
    public static final int MOD=275;
    public static final int OUTERJOIN_EXPR=149;
    public static final int BSR=296;
    public static final int LIB_FUNCTION=172;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=93;
    public static final int FULL_OUTERJOIN_EXPR=153;
    public static final int MATCHREC_PATTERN_CONCAT=239;
    public static final int RPAREN=254;
    public static final int LNOT=285;
    public static final int INC=289;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=262;
    public static final int BSR_ASSIGN=297;
    public static final int CAST_EXPR=201;
    public static final int MATCHES=104;
    public static final int STREAM_EXPR=148;
    public static final int TIMEPERIOD_SECONDS=90;
    public static final int NOT_EQUAL=267;
    public static final int METADATASQL=67;
    public static final int EVENT_FILTER_PROPERTY_EXPR=116;
    public static final int LAST_AGGREG=225;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=110;
    public static final int FOLLOWED_BY=279;
    public static final int HOUR_PART=177;
    public static final int RBRACK=260;
    public static final int MATCHREC_PATTERN_NESTED=241;
    public static final int MATCH_UNTIL_RANGE_CLOSED=216;
    public static final int GE=271;
    public static final int METHOD_JOIN_EXPR=212;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=115;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=114;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=290;
    public static final int EVENT_FILTER_NOT_IN=126;
    public static final int INSERTINTO_STREAM_NAME=189;
    public static final int NUM_DOUBLE=235;
    public static final int UNARY_MINUS=173;
    public static final int TIMEPERIOD_MILLISEC=91;
    public static final int LCURLY=276;
    public static final int RETAINUNION=63;
    public static final int DBWHERE_CLAUSE=187;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=124;
    public static final int GROUP=44;
    public static final int EMAILAT=305;
    public static final int WS=306;
    public static final int SUBSELECT_GROUP_EXPR=193;
    public static final int ON_SELECT_INSERT_EXPR=207;
    public static final int ESCAPECHAR=280;
    public static final int SL_COMMENT=307;
    public static final int NULL_TYPE=234;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=214;
    public static final int GT=269;
    public static final int BNOT=286;
    public static final int WHERE_EXPR=135;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=150;
    public static final int LAND=303;
    public static final int NOT_REGEXP=184;
    public static final int MATCH_UNTIL_EXPR=213;
    public static final int EVENT_PROP_EXPR=157;
    public static final int LBRACK=259;
    public static final int VIEW_EXPR=132;
    public static final int ANNOTATION=221;
    public static final int LONG_TYPE=229;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=117;
    public static final int MATCHREC_PATTERN=237;
    public static final int TIMEPERIOD_SEC=88;
    public static final int ON_SELECT_EXPR=206;
    public static final int TICKED_STRING_LITERAL=281;
    public static final int MINUTE_PART=178;
    public static final int PATTERN_NOT_EXPR=113;
    public static final int SUM=18;
    public static final int SQL_NE=266;
    public static final int HexDigit=312;
    public static final int UPDATE_EXPR=226;
    public static final int LPAREN=253;
    public static final int IN_SUBSELECT_EXPR=195;
    public static final int AT=81;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=94;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=243;
    public static final int NOT_IN_RANGE=191;
    public static final int OFFSET=98;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=68;
    public static final int SECOND_PART=179;
    public static final int MATCH_RECOGNIZE=100;
    public static final int IDENT=250;
    public static final int DATABASE_JOIN_EXPR=134;
    public static final int BXOR=265;
    public static final int PLUS=257;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=82;
    public static final int EXISTS=70;
    public static final int EVENT_PROP_INDEXED=160;
    public static final int TIMEPERIOD_MILLISECOND=92;
    public static final int EVAL_NOTEQUALS_EXPR=141;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=215;
    public static final int CREATE_VARIABLE_EXPR=211;
    public static final int CREATE_WINDOW_COL_TYPE=219;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=244;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=111;
    public static final int RIGHT_OUTERJOIN_EXPR=152;
    public static final int NUMBERSETSTAR=220;
    public static final int LAST_OPERATOR=198;
    public static final int PATTERN_FILTER_EXPR=112;
    public static final int EVAL_AND_EXPR=138;
    public static final int LEFT_OUTERJOIN_EXPR=151;
    public static final int EPL_EXPR=236;
    public static final int GROUP_BY_EXPR=154;
    public static final int SET=78;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=73;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=158;
    public static final int MINUS=273;
    public static final int SEMI=304;
    public static final int STAR_ASSIGN=292;
    public static final int FIRST_AGGREG=224;
    public static final int COLON=261;
    public static final int EVAL_EQUALS_GROUP_EXPR=142;
    public static final int BAND_ASSIGN=302;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=167;
    public static final int VALUE_NULL=96;
    public static final int NOT_IN_SET=181;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=161;
    public static final int SL=298;
    public static final int NOT_IN_SUBSELECT_EXPR=196;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=130;
    public static final int SR=294;
    public static final int RCURLY=277;
    public static final int PLUS_ASSIGN=288;
    public static final int EXISTS_SUBSELECT_EXPR=194;
    public static final int DAY_PART=176;
    public static final int EVENT_FILTER_IN=125;
    public static final int DIV=274;
    public static final int OBJECT_PARAM_ORDERED_EXPR=109;
    public static final int OctalEscape=311;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=180;
    public static final int PRIOR=69;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=97;
    public static final int SELECTION_EXPR=145;
    public static final int LOR=272;
    public static final int CAST=74;
    public static final int LW=72;
    public static final int WILDCARD_SELECT=188;
    public static final int EXPONENT=313;
    public static final int LT=268;
    public static final int PATTERN_INCL_EXPR=133;
    public static final int ORDER_BY_EXPR=155;
    public static final int BOOL_TYPE=233;
    public static final int MOD_ASSIGN=293;
    public static final int ANNOTATION_ARRAY=222;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=197;
    public static final int EQUALS=251;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=64;
    public static final int DIV_ASSIGN=287;
    public static final int SL_ASSIGN=299;
    public static final int PATTERN=65;
    public static final int SQL=66;
    public static final int FULL=40;
    public static final int WEEKDAY=71;
    public static final int MATCHREC_AFTER_SKIP=242;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ARRAY_EXPR=175;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=95;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=143;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=127;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=89;
    public static final int FLOAT_TYPE=230;
    public static final int SUBSELECT_EXPR=192;
    public static final int ANNOTATION_VALUE=223;
    public static final int CONCAT=171;
    public static final int NUMERIC_PARAM_RANGE=106;
    public static final int CLASS_IDENT=129;
    public static final int MATCHREC_PATTERN_ALTER=240;
    public static final int ON_EXPR=204;
    public static final int CREATE_WINDOW_EXPR=202;
    public static final int PROPERTY_SELECTION_STREAM=119;
    public static final int ON_DELETE_EXPR=205;
    public static final int ON=41;
    public static final int NUM_LONG=282;
    public static final int TIME_PERIOD=174;
    public static final int DOUBLE_TYPE=231;
    public static final int DELETE=76;
    public static final int INT_TYPE=228;
    public static final int MATCHREC_PARTITION=248;
    public static final int EVAL_BITWISE_EXPR=137;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=156;
    public static final int TIMEPERIOD_HOURS=85;
    public static final int VARIABLE=79;
    public static final int SUBSTITUTION=200;
    public static final int UNTIL=80;
    public static final int STRING_TYPE=232;
    public static final int ON_SET_EXPR=210;
    public static final int MATCHREC_DEFINE_ITEM=245;
    public static final int NUM_INT=278;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=209;
    public static final int NUM_FLOAT=283;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=118;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=309;
    public static final int WEEKDAY_OPERATOR=199;
    public static final int WHERE=16;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=218;
    public static final int DEC=291;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=108;
    public static final int BXOR_ASSIGN=300;
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
    public static final int ML_COMMENT=308;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=162;
    public static final int BOR_ASSIGN=301;
    public static final int COMMA=249;
    public static final int WHEN_LIMIT_EXPR=168;
    public static final int PARTITION=103;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=165;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=84;
    public static final int MATCHREC_MEASURE_ITEM=247;
    public static final int BOR=256;
    public static final int EQUAL=284;
    public static final int EVENT_FILTER_NOT_BETWEEN=128;
    public static final int IN_RANGE=190;
    public static final int DOT=252;
    public static final int CURRENT_TIMESTAMP=75;
    public static final int MATCHREC_MEASURES=246;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=120;
    public static final int INSERTINTO_EXPR=169;
    public static final int HAVING_EXPR=136;
    public static final int UNIDIRECTIONAL=62;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=217;
    public static final int EVAL_EQUALS_EXPR=140;
    public static final int TIMEPERIOD_MINUTES=87;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=183;
    public static final int EVENT_LIMIT_EXPR=164;
    public static final int NOT_BETWEEN=182;
    public static final int TIMEPERIOD_MINUTE=86;
    public static final int EVAL_OR_EXPR=139;
    public static final int ON_SELECT_INSERT_OUTPUT=208;
    public static final int AFTER=105;
    public static final int MEASURES=101;
    public static final int MATCHREC_PATTERN_ATOM=238;
    public static final int BAND=264;
    public static final int QUOTED_STRING_LITERAL=263;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=258;
    public static final int OBSERVER_EXPR=131;
    public static final int EVENT_FILTER_IDENT=121;
    public static final int EVENT_PROP_MAPPED=159;
    public static final int UnicodeEscape=310;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=185;
    public static final int SELECTION_ELEMENT_EXPR=146;
    public static final int CREATE_WINDOW_SELECT_EXPR=203;
    public static final int INSERTINTO_EXPRCOL=170;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=227;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=147;
    public static final int SR_ASSIGN=295;
    public static final int DBFROM_CLAUSE=186;
    public static final int LE=270;
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
    // EsperEPL2Ast.g:97:1: onSelectExpr : ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:98:2: ( ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) )
            // EsperEPL2Ast.g:98:4: ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList ( onExprFrom )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( orderByClause )? )
            {
            match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr366); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:98:21: ( insertIntoExpr )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==INSERTINTO_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:98:21: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr368);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr371);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:98:51: ( onExprFrom )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==ON_EXPR_FROM) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:98:51: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr373);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:63: ( whereClause[true] )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==WHERE_EXPR) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:98:63: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr376);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:82: ( groupByClause )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==GROUP_BY_EXPR) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // EsperEPL2Ast.g:98:82: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr380);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:97: ( havingClause )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==HAVING_EXPR) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:98:97: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr383);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:111: ( orderByClause )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==ORDER_BY_EXPR) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:98:111: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr386);
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
    // EsperEPL2Ast.g:101:1: onSelectInsertExpr : ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) ;
    public final void onSelectInsertExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:102:2: ( ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? ) )
            // EsperEPL2Ast.g:102:4: ^( ON_SELECT_INSERT_EXPR insertIntoExpr selectionList ( whereClause[true] )? )
            {
            pushStmtContext();
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr403); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr405);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr407);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:102:78: ( whereClause[true] )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==WHERE_EXPR) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:102:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr409);
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
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput426); 

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
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr446); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr448);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:110:34: ( onSetAssignment )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==ON_SET_EXPR_ITEM) ) {
                    alt24=1;
                }


                switch (alt24) {
            	case 1 :
            	    // EsperEPL2Ast.g:110:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr451);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);

            // EsperEPL2Ast.g:110:53: ( whereClause[false] )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==WHERE_EXPR) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:110:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr455);
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
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment470); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onSetAssignment472); 
            pushFollow(FOLLOW_valueExpr_in_onSetAssignment474);
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
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom488); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom490); 
            // EsperEPL2Ast.g:118:25: ( IDENT )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==IDENT) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:118:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom493); 

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
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr511); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr513); 
            // EsperEPL2Ast.g:122:33: ( viewListExpr )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==VIEW_EXPR) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:122:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr516);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:122:49: ( RETAINUNION )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==RETAINUNION) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:122:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr520); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:122:62: ( RETAININTERSECTION )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==RETAININTERSECTION) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:122:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr523); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:123:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==CLASS_IDENT||LA31_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt31=1;
            }
            else if ( (LA31_0==CREATE_WINDOW_COL_TYPE_LIST) ) {
                alt31=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:124:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:124:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:124:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:124:6: ( createSelectionList )?
                    int alt30=2;
                    int LA30_0 = input.LA(1);

                    if ( (LA30_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt30=1;
                    }
                    switch (alt30) {
                        case 1 :
                            // EsperEPL2Ast.g:124:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr537);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr540); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:126:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:126:12: ( createColTypeList )
                    // EsperEPL2Ast.g:126:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr569);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:128:4: ( createWindowExprInsert )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==INSERT) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:128:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr580);
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
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert598); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:133:13: ( valueExpr )?
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( ((LA33_0>=IN_SET && LA33_0<=REGEXP)||LA33_0==NOT_EXPR||(LA33_0>=SUM && LA33_0<=AVG)||(LA33_0>=COALESCE && LA33_0<=COUNT)||(LA33_0>=CASE && LA33_0<=CASE2)||(LA33_0>=PREVIOUS && LA33_0<=EXISTS)||(LA33_0>=INSTANCEOF && LA33_0<=CURRENT_TIMESTAMP)||(LA33_0>=EVAL_AND_EXPR && LA33_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA33_0==EVENT_PROP_EXPR||(LA33_0>=CONCAT && LA33_0<=LIB_FUNCTION)||LA33_0==ARRAY_EXPR||(LA33_0>=NOT_IN_SET && LA33_0<=NOT_REGEXP)||(LA33_0>=IN_RANGE && LA33_0<=SUBSELECT_EXPR)||(LA33_0>=EXISTS_SUBSELECT_EXPR && LA33_0<=NOT_IN_SUBSELECT_EXPR)||LA33_0==SUBSTITUTION||(LA33_0>=FIRST_AGGREG && LA33_0<=LAST_AGGREG)||(LA33_0>=INT_TYPE && LA33_0<=NULL_TYPE)||(LA33_0>=STAR && LA33_0<=PLUS)||(LA33_0>=BAND && LA33_0<=BXOR)||(LA33_0>=LT && LA33_0<=GE)||(LA33_0>=MINUS && LA33_0<=MOD)) ) {
                    alt33=1;
                }
                switch (alt33) {
                    case 1 :
                        // EsperEPL2Ast.g:133:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert600);
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
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList617); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList619);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:137:61: ( createSelectionListElement )*
            loop34:
            do {
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( (LA34_0==SELECTION_ELEMENT_EXPR||LA34_0==WILDCARD_SELECT) ) {
                    alt34=1;
                }


                switch (alt34) {
            	case 1 :
            	    // EsperEPL2Ast.g:137:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList622);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop34;
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
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList641); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList643);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:141:59: ( createColTypeListElement )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==CREATE_WINDOW_COL_TYPE) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // EsperEPL2Ast.g:141:60: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList646);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop35;
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
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement661); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement663); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement665); 

            match(input, Token.UP, null); 

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
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==WILDCARD_SELECT) ) {
                alt38=1;
            }
            else if ( (LA38_0==SELECTION_ELEMENT_EXPR) ) {
                alt38=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:149:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement679); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:150:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement689); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:150:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt37=2;
                    int LA37_0 = input.LA(1);

                    if ( (LA37_0==EVENT_PROP_EXPR) ) {
                        alt37=1;
                    }
                    else if ( ((LA37_0>=INT_TYPE && LA37_0<=NULL_TYPE)) ) {
                        alt37=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 0, input);

                        throw nvae;
                    }
                    switch (alt37) {
                        case 1 :
                            // EsperEPL2Ast.g:151:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:151:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:151:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement709);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:151:41: ( IDENT )?
                            int alt36=2;
                            int LA36_0 = input.LA(1);

                            if ( (LA36_0==IDENT) ) {
                                alt36=1;
                            }
                            switch (alt36) {
                                case 1 :
                                    // EsperEPL2Ast.g:151:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement713); 

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
                            pushFollow(FOLLOW_constant_in_createSelectionListElement735);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement738); 

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
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr774); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr776); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr778); 
            // EsperEPL2Ast.g:157:41: ( valueExpr )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( ((LA39_0>=IN_SET && LA39_0<=REGEXP)||LA39_0==NOT_EXPR||(LA39_0>=SUM && LA39_0<=AVG)||(LA39_0>=COALESCE && LA39_0<=COUNT)||(LA39_0>=CASE && LA39_0<=CASE2)||(LA39_0>=PREVIOUS && LA39_0<=EXISTS)||(LA39_0>=INSTANCEOF && LA39_0<=CURRENT_TIMESTAMP)||(LA39_0>=EVAL_AND_EXPR && LA39_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA39_0==EVENT_PROP_EXPR||(LA39_0>=CONCAT && LA39_0<=LIB_FUNCTION)||LA39_0==ARRAY_EXPR||(LA39_0>=NOT_IN_SET && LA39_0<=NOT_REGEXP)||(LA39_0>=IN_RANGE && LA39_0<=SUBSELECT_EXPR)||(LA39_0>=EXISTS_SUBSELECT_EXPR && LA39_0<=NOT_IN_SUBSELECT_EXPR)||LA39_0==SUBSTITUTION||(LA39_0>=FIRST_AGGREG && LA39_0<=LAST_AGGREG)||(LA39_0>=INT_TYPE && LA39_0<=NULL_TYPE)||(LA39_0>=STAR && LA39_0<=PLUS)||(LA39_0>=BAND && LA39_0<=BXOR)||(LA39_0>=LT && LA39_0<=GE)||(LA39_0>=MINUS && LA39_0<=MOD)) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // EsperEPL2Ast.g:157:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr781);
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
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==INSERTINTO_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:161:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr799);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr805);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr810);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:164:3: ( matchRecogClause )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==MATCH_RECOGNIZE) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:164:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr815);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:165:3: ( whereClause[true] )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==WHERE_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:165:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr822);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:166:3: ( groupByClause )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==GROUP_BY_EXPR) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:166:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr830);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:167:3: ( havingClause )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==HAVING_EXPR) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:167:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr837);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:168:3: ( outputLimitExpr )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( ((LA45_0>=EVENT_LIMIT_EXPR && LA45_0<=CRONTAB_LIMIT_EXPR)||LA45_0==WHEN_LIMIT_EXPR) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:168:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr844);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:169:3: ( orderByClause )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==ORDER_BY_EXPR) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:169:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr851);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:170:3: ( rowLimitClause )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==ROW_LIMIT_EXPR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:170:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr858);
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
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr875); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:174:24: ( ISTREAM | RSTREAM )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( ((LA48_0>=RSTREAM && LA48_0<=ISTREAM)) ) {
                alt48=1;
            }
            switch (alt48) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr886); 
            // EsperEPL2Ast.g:174:51: ( insertIntoExprCol )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==INSERTINTO_EXPRCOL) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:174:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr889);
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
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol908); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol910); 
            // EsperEPL2Ast.g:178:31: ( IDENT )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==IDENT) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // EsperEPL2Ast.g:178:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol913); 

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
    // $ANTLR end "insertIntoExprCol"


    // $ANTLR start "selectClause"
    // EsperEPL2Ast.g:181:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:182:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) )
            // EsperEPL2Ast.g:182:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause931); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:182:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( ((LA51_0>=RSTREAM && LA51_0<=IRSTREAM)) ) {
                alt51=1;
            }
            switch (alt51) {
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

            pushFollow(FOLLOW_selectionList_in_selectClause946);
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
            pushFollow(FOLLOW_streamExpression_in_fromClause960);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:186:21: ( streamExpression ( outerJoin )* )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==STREAM_EXPR) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // EsperEPL2Ast.g:186:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause963);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:186:39: ( outerJoin )*
            	    loop52:
            	    do {
            	        int alt52=2;
            	        int LA52_0 = input.LA(1);

            	        if ( ((LA52_0>=INNERJOIN_EXPR && LA52_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt52=1;
            	        }


            	        switch (alt52) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:186:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause966);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop52;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop53;
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
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause986); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:190:24: ( matchRecogPartitionBy )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==MATCHREC_PARTITION) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // EsperEPL2Ast.g:190:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause988);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause995);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:192:4: ( ALL )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==ALL) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // EsperEPL2Ast.g:192:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1001); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:193:4: ( matchRecogMatchesAfterSkip )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==MATCHREC_AFTER_SKIP) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:193:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1007);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1013);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:195:4: ( matchRecogMatchesInterval )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==MATCHREC_INTERVAL) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:195:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1019);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1025);
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
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1043); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:200:27: ( valueExpr )+
            int cnt58=0;
            loop58:
            do {
                int alt58=2;
                int LA58_0 = input.LA(1);

                if ( ((LA58_0>=IN_SET && LA58_0<=REGEXP)||LA58_0==NOT_EXPR||(LA58_0>=SUM && LA58_0<=AVG)||(LA58_0>=COALESCE && LA58_0<=COUNT)||(LA58_0>=CASE && LA58_0<=CASE2)||(LA58_0>=PREVIOUS && LA58_0<=EXISTS)||(LA58_0>=INSTANCEOF && LA58_0<=CURRENT_TIMESTAMP)||(LA58_0>=EVAL_AND_EXPR && LA58_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA58_0==EVENT_PROP_EXPR||(LA58_0>=CONCAT && LA58_0<=LIB_FUNCTION)||LA58_0==ARRAY_EXPR||(LA58_0>=NOT_IN_SET && LA58_0<=NOT_REGEXP)||(LA58_0>=IN_RANGE && LA58_0<=SUBSELECT_EXPR)||(LA58_0>=EXISTS_SUBSELECT_EXPR && LA58_0<=NOT_IN_SUBSELECT_EXPR)||LA58_0==SUBSTITUTION||(LA58_0>=FIRST_AGGREG && LA58_0<=LAST_AGGREG)||(LA58_0>=INT_TYPE && LA58_0<=NULL_TYPE)||(LA58_0>=STAR && LA58_0<=PLUS)||(LA58_0>=BAND && LA58_0<=BXOR)||(LA58_0>=LT && LA58_0<=GE)||(LA58_0>=MINUS && LA58_0<=MOD)) ) {
                    alt58=1;
                }


                switch (alt58) {
            	case 1 :
            	    // EsperEPL2Ast.g:200:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1045);
            	    valueExpr();

            	    state._fsp--;


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
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1062); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1064); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1066); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1068); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1070); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1072); 

            match(input, Token.UP, null); 

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
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1087); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1089); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1091);
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
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1107); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:212:26: ( matchRecogMeasureListElement )*
                loop59:
                do {
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( (LA59_0==MATCHREC_MEASURE_ITEM) ) {
                        alt59=1;
                    }


                    switch (alt59) {
                	case 1 :
                	    // EsperEPL2Ast.g:212:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1109);
                	    matchRecogMeasureListElement();

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
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1126); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1128);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:216:40: ( IDENT )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==IDENT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:216:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1130); 

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
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1150); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:220:25: ( matchRecogPatternAlteration )+
            int cnt61=0;
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( ((LA61_0>=MATCHREC_PATTERN_CONCAT && LA61_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // EsperEPL2Ast.g:220:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1152);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt61 >= 1 ) break loop61;
                        EarlyExitException eee =
                            new EarlyExitException(61, input);
                        throw eee;
                }
                cnt61++;
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
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==MATCHREC_PATTERN_CONCAT) ) {
                alt63=1;
            }
            else if ( (LA63_0==MATCHREC_PATTERN_ALTER) ) {
                alt63=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:224:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1167);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:225:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1175); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1177);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:225:55: ( matchRecogPatternConcat )+
                    int cnt62=0;
                    loop62:
                    do {
                        int alt62=2;
                        int LA62_0 = input.LA(1);

                        if ( (LA62_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt62=1;
                        }


                        switch (alt62) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:225:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1179);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


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
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1197); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:229:32: ( matchRecogPatternUnary )+
            int cnt64=0;
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==MATCHREC_PATTERN_ATOM||LA64_0==MATCHREC_PATTERN_NESTED) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // EsperEPL2Ast.g:229:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1199);
            	    matchRecogPatternUnary();

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
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==MATCHREC_PATTERN_NESTED) ) {
                alt65=1;
            }
            else if ( (LA65_0==MATCHREC_PATTERN_ATOM) ) {
                alt65=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:233:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1214);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:234:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1219);
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
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1234); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1236);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:238:60: ( PLUS | STAR | QUESTION )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==STAR||(LA66_0>=PLUS && LA66_0<=QUESTION)) ) {
                alt66=1;
            }
            switch (alt66) {
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
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1267); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1269); 
            // EsperEPL2Ast.g:242:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==STAR||(LA68_0>=PLUS && LA68_0<=QUESTION)) ) {
                alt68=1;
            }
            switch (alt68) {
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
                    int alt67=2;
                    int LA67_0 = input.LA(1);

                    if ( (LA67_0==QUESTION) ) {
                        alt67=1;
                    }
                    switch (alt67) {
                        case 1 :
                            // EsperEPL2Ast.g:242:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1285); 

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
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1307); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:246:24: ( matchRecogDefineItem )+
            int cnt69=0;
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( (LA69_0==MATCHREC_DEFINE_ITEM) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // EsperEPL2Ast.g:246:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1309);
            	    matchRecogDefineItem();

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


            match(input, Token.UP, null); 

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
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1326); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1328); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1330);
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
            pushFollow(FOLLOW_selectionListElement_in_selectionList1347);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:255:25: ( selectionListElement )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( ((LA70_0>=SELECTION_ELEMENT_EXPR && LA70_0<=SELECTION_STREAM)||LA70_0==WILDCARD_SELECT) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // EsperEPL2Ast.g:255:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1350);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop70;
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
            int alt73=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt73=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt73=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt73=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }

            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:259:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1366); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:260:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1376); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1378);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:260:41: ( IDENT )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==IDENT) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // EsperEPL2Ast.g:260:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1381); 

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
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1395); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1397); 
                    // EsperEPL2Ast.g:261:31: ( IDENT )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==IDENT) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // EsperEPL2Ast.g:261:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1400); 

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
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1419);
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
            int alt78=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt78=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt78=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt78=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt78=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }

            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:269:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1433); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1435);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1438);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:269:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop74:
                    do {
                        int alt74=2;
                        int LA74_0 = input.LA(1);

                        if ( (LA74_0==EVENT_PROP_EXPR) ) {
                            alt74=1;
                        }


                        switch (alt74) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:269:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1442);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1445);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop74;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:270:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1460); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1462);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1465);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:270:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==EVENT_PROP_EXPR) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:270:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1469);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1472);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop75;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:271:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1487); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1489);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1492);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:271:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop76:
                    do {
                        int alt76=2;
                        int LA76_0 = input.LA(1);

                        if ( (LA76_0==EVENT_PROP_EXPR) ) {
                            alt76=1;
                        }


                        switch (alt76) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:271:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1496);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1499);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop76;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:272:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1514); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1516);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1519);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:272:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop77:
                    do {
                        int alt77=2;
                        int LA77_0 = input.LA(1);

                        if ( (LA77_0==EVENT_PROP_EXPR) ) {
                            alt77=1;
                        }


                        switch (alt77) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:272:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1523);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1526);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop77;
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
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1547); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:276:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt79=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt79=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt79=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt79=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt79=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:276:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1550);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:276:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1554);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:276:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1558);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:276:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1562);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:276:115: ( viewListExpr )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==VIEW_EXPR) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:276:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1566);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:276:131: ( IDENT )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==IDENT) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:276:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1571); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:276:140: ( UNIDIRECTIONAL )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==UNIDIRECTIONAL) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:276:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1576); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:276:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( ((LA83_0>=RETAINUNION && LA83_0<=RETAININTERSECTION)) ) {
                alt83=1;
            }
            switch (alt83) {
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
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1604); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:280:27: ( IDENT )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==IDENT) ) {
                alt84=1;
            }
            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:280:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1606); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1609); 
            // EsperEPL2Ast.g:280:46: ( propertyExpression )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // EsperEPL2Ast.g:280:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1611);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:280:66: ( valueExpr )*
            loop86:
            do {
                int alt86=2;
                int LA86_0 = input.LA(1);

                if ( ((LA86_0>=IN_SET && LA86_0<=REGEXP)||LA86_0==NOT_EXPR||(LA86_0>=SUM && LA86_0<=AVG)||(LA86_0>=COALESCE && LA86_0<=COUNT)||(LA86_0>=CASE && LA86_0<=CASE2)||(LA86_0>=PREVIOUS && LA86_0<=EXISTS)||(LA86_0>=INSTANCEOF && LA86_0<=CURRENT_TIMESTAMP)||(LA86_0>=EVAL_AND_EXPR && LA86_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA86_0==EVENT_PROP_EXPR||(LA86_0>=CONCAT && LA86_0<=LIB_FUNCTION)||LA86_0==ARRAY_EXPR||(LA86_0>=NOT_IN_SET && LA86_0<=NOT_REGEXP)||(LA86_0>=IN_RANGE && LA86_0<=SUBSELECT_EXPR)||(LA86_0>=EXISTS_SUBSELECT_EXPR && LA86_0<=NOT_IN_SUBSELECT_EXPR)||LA86_0==SUBSTITUTION||(LA86_0>=FIRST_AGGREG && LA86_0<=LAST_AGGREG)||(LA86_0>=INT_TYPE && LA86_0<=NULL_TYPE)||(LA86_0>=STAR && LA86_0<=PLUS)||(LA86_0>=BAND && LA86_0<=BXOR)||(LA86_0>=LT && LA86_0<=GE)||(LA86_0>=MINUS && LA86_0<=MOD)) ) {
                    alt86=1;
                }


                switch (alt86) {
            	case 1 :
            	    // EsperEPL2Ast.g:280:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1615);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop86;
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
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1635); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:284:34: ( propertyExpressionAtom )*
                loop87:
                do {
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt87=1;
                    }


                    switch (alt87) {
                	case 1 :
                	    // EsperEPL2Ast.g:284:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1637);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop87;
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
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1656); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:288:41: ( propertySelectionListElement )*
            loop88:
            do {
                int alt88=2;
                int LA88_0 = input.LA(1);

                if ( ((LA88_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA88_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt88=1;
                }


                switch (alt88) {
            	case 1 :
            	    // EsperEPL2Ast.g:288:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1658);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop88;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1661);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:288:96: ( IDENT )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==IDENT) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:288:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1664); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1668); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:288:116: ( valueExpr )?
                int alt90=2;
                int LA90_0 = input.LA(1);

                if ( ((LA90_0>=IN_SET && LA90_0<=REGEXP)||LA90_0==NOT_EXPR||(LA90_0>=SUM && LA90_0<=AVG)||(LA90_0>=COALESCE && LA90_0<=COUNT)||(LA90_0>=CASE && LA90_0<=CASE2)||(LA90_0>=PREVIOUS && LA90_0<=EXISTS)||(LA90_0>=INSTANCEOF && LA90_0<=CURRENT_TIMESTAMP)||(LA90_0>=EVAL_AND_EXPR && LA90_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA90_0==EVENT_PROP_EXPR||(LA90_0>=CONCAT && LA90_0<=LIB_FUNCTION)||LA90_0==ARRAY_EXPR||(LA90_0>=NOT_IN_SET && LA90_0<=NOT_REGEXP)||(LA90_0>=IN_RANGE && LA90_0<=SUBSELECT_EXPR)||(LA90_0>=EXISTS_SUBSELECT_EXPR && LA90_0<=NOT_IN_SUBSELECT_EXPR)||LA90_0==SUBSTITUTION||(LA90_0>=FIRST_AGGREG && LA90_0<=LAST_AGGREG)||(LA90_0>=INT_TYPE && LA90_0<=NULL_TYPE)||(LA90_0>=STAR && LA90_0<=PLUS)||(LA90_0>=BAND && LA90_0<=BXOR)||(LA90_0>=LT && LA90_0<=GE)||(LA90_0>=MINUS && LA90_0<=MOD)) ) {
                    alt90=1;
                }
                switch (alt90) {
                    case 1 :
                        // EsperEPL2Ast.g:288:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1670);
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
            int alt93=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt93=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt93=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt93=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 93, 0, input);

                throw nvae;
            }

            switch (alt93) {
                case 1 :
                    // EsperEPL2Ast.g:292:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1690); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:293:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1700); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1702);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:293:50: ( IDENT )?
                    int alt91=2;
                    int LA91_0 = input.LA(1);

                    if ( (LA91_0==IDENT) ) {
                        alt91=1;
                    }
                    switch (alt91) {
                        case 1 :
                            // EsperEPL2Ast.g:293:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1705); 

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
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1719); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1721); 
                    // EsperEPL2Ast.g:294:40: ( IDENT )?
                    int alt92=2;
                    int LA92_0 = input.LA(1);

                    if ( (LA92_0==IDENT) ) {
                        alt92=1;
                    }
                    switch (alt92) {
                        case 1 :
                            // EsperEPL2Ast.g:294:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1724); 

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
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1745); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1747);
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
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1764); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1766); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:302:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( ((LA94_0>=STRING_LITERAL && LA94_0<=QUOTED_STRING_LITERAL)) ) {
                alt94=1;
            }
            switch (alt94) {
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
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1797); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1799); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1801); 
            // EsperEPL2Ast.g:306:41: ( valueExpr )*
            loop95:
            do {
                int alt95=2;
                int LA95_0 = input.LA(1);

                if ( ((LA95_0>=IN_SET && LA95_0<=REGEXP)||LA95_0==NOT_EXPR||(LA95_0>=SUM && LA95_0<=AVG)||(LA95_0>=COALESCE && LA95_0<=COUNT)||(LA95_0>=CASE && LA95_0<=CASE2)||(LA95_0>=PREVIOUS && LA95_0<=EXISTS)||(LA95_0>=INSTANCEOF && LA95_0<=CURRENT_TIMESTAMP)||(LA95_0>=EVAL_AND_EXPR && LA95_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA95_0==EVENT_PROP_EXPR||(LA95_0>=CONCAT && LA95_0<=LIB_FUNCTION)||LA95_0==ARRAY_EXPR||(LA95_0>=NOT_IN_SET && LA95_0<=NOT_REGEXP)||(LA95_0>=IN_RANGE && LA95_0<=SUBSELECT_EXPR)||(LA95_0>=EXISTS_SUBSELECT_EXPR && LA95_0<=NOT_IN_SUBSELECT_EXPR)||LA95_0==SUBSTITUTION||(LA95_0>=FIRST_AGGREG && LA95_0<=LAST_AGGREG)||(LA95_0>=INT_TYPE && LA95_0<=NULL_TYPE)||(LA95_0>=STAR && LA95_0<=PLUS)||(LA95_0>=BAND && LA95_0<=BXOR)||(LA95_0>=LT && LA95_0<=GE)||(LA95_0>=MINUS && LA95_0<=MOD)) ) {
                    alt95=1;
                }


                switch (alt95) {
            	case 1 :
            	    // EsperEPL2Ast.g:306:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1804);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop95;
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
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1818);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:310:13: ( viewExpr )*
            loop96:
            do {
                int alt96=2;
                int LA96_0 = input.LA(1);

                if ( (LA96_0==VIEW_EXPR) ) {
                    alt96=1;
                }


                switch (alt96) {
            	case 1 :
            	    // EsperEPL2Ast.g:310:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1821);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop96;
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
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1838); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1840); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1842); 
            // EsperEPL2Ast.g:314:30: ( valueExprWithTime )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||LA97_0==LAST||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=LW && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=NUMERIC_PARAM_RANGE && LA97_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||(LA97_0>=TIME_PERIOD && LA97_0<=ARRAY_EXPR)||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=SUBSELECT_EXPR)||(LA97_0>=EXISTS_SUBSELECT_EXPR && LA97_0<=NOT_IN_SUBSELECT_EXPR)||(LA97_0>=LAST_OPERATOR && LA97_0<=SUBSTITUTION)||LA97_0==NUMBERSETSTAR||(LA97_0>=FIRST_AGGREG && LA97_0<=LAST_AGGREG)||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||(LA97_0>=STAR && LA97_0<=PLUS)||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=MINUS && LA97_0<=MOD)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // EsperEPL2Ast.g:314:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1845);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop97;
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
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1867); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1869);
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
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1887); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1889);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:322:32: ( valueExpr )*
            loop98:
            do {
                int alt98=2;
                int LA98_0 = input.LA(1);

                if ( ((LA98_0>=IN_SET && LA98_0<=REGEXP)||LA98_0==NOT_EXPR||(LA98_0>=SUM && LA98_0<=AVG)||(LA98_0>=COALESCE && LA98_0<=COUNT)||(LA98_0>=CASE && LA98_0<=CASE2)||(LA98_0>=PREVIOUS && LA98_0<=EXISTS)||(LA98_0>=INSTANCEOF && LA98_0<=CURRENT_TIMESTAMP)||(LA98_0>=EVAL_AND_EXPR && LA98_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA98_0==EVENT_PROP_EXPR||(LA98_0>=CONCAT && LA98_0<=LIB_FUNCTION)||LA98_0==ARRAY_EXPR||(LA98_0>=NOT_IN_SET && LA98_0<=NOT_REGEXP)||(LA98_0>=IN_RANGE && LA98_0<=SUBSELECT_EXPR)||(LA98_0>=EXISTS_SUBSELECT_EXPR && LA98_0<=NOT_IN_SUBSELECT_EXPR)||LA98_0==SUBSTITUTION||(LA98_0>=FIRST_AGGREG && LA98_0<=LAST_AGGREG)||(LA98_0>=INT_TYPE && LA98_0<=NULL_TYPE)||(LA98_0>=STAR && LA98_0<=PLUS)||(LA98_0>=BAND && LA98_0<=BXOR)||(LA98_0>=LT && LA98_0<=GE)||(LA98_0>=MINUS && LA98_0<=MOD)) ) {
                    alt98=1;
                }


                switch (alt98) {
            	case 1 :
            	    // EsperEPL2Ast.g:322:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1892);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop98;
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
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1910); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1912);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:326:35: ( orderByElement )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( (LA99_0==ORDER_ELEMENT_EXPR) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // EsperEPL2Ast.g:326:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1915);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop99;
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
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1935); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1937);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:330:38: ( ASC | DESC )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( ((LA100_0>=ASC && LA100_0<=DESC)) ) {
                alt100=1;
            }
            switch (alt100) {
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
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1962); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1964);
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
    // EsperEPL2Ast.g:337:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:338:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt107=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt107=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt107=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt107=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt107=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }

            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:338:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1982); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:338:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==ALL||(LA101_0>=FIRST && LA101_0<=LAST)||LA101_0==SNAPSHOT) ) {
                        alt101=1;
                    }
                    switch (alt101) {
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
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( ((LA102_0>=INT_TYPE && LA102_0<=DOUBLE_TYPE)) ) {
                        alt102=1;
                    }
                    else if ( (LA102_0==IDENT) ) {
                        alt102=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 102, 0, input);

                        throw nvae;
                    }
                    switch (alt102) {
                        case 1 :
                            // EsperEPL2Ast.g:338:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1996);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:338:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1998); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:339:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2015); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:339:34: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2028);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:340:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2043); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:340:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==ALL||(LA104_0>=FIRST && LA104_0<=LAST)||LA104_0==SNAPSHOT) ) {
                        alt104=1;
                    }
                    switch (alt104) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2056);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:341:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2071); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:341:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==ALL||(LA105_0>=FIRST && LA105_0<=LAST)||LA105_0==SNAPSHOT) ) {
                        alt105=1;
                    }
                    switch (alt105) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2084);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:341:67: ( onSetExpr )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==ON_SET_EXPR) ) {
                        alt106=1;
                    }
                    switch (alt106) {
                        case 1 :
                            // EsperEPL2Ast.g:341:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2086);
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
    // EsperEPL2Ast.g:344:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:345:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:345:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2105); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:345:23: ( number | IDENT )
            int alt108=2;
            int LA108_0 = input.LA(1);

            if ( ((LA108_0>=INT_TYPE && LA108_0<=DOUBLE_TYPE)) ) {
                alt108=1;
            }
            else if ( (LA108_0==IDENT) ) {
                alt108=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }
            switch (alt108) {
                case 1 :
                    // EsperEPL2Ast.g:345:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2108);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:345:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2110); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:345:38: ( number | IDENT )?
            int alt109=3;
            int LA109_0 = input.LA(1);

            if ( ((LA109_0>=INT_TYPE && LA109_0<=DOUBLE_TYPE)) ) {
                alt109=1;
            }
            else if ( (LA109_0==IDENT) ) {
                alt109=2;
            }
            switch (alt109) {
                case 1 :
                    // EsperEPL2Ast.g:345:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2114);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:345:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2116); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:345:54: ( COMMA )?
            int alt110=2;
            int LA110_0 = input.LA(1);

            if ( (LA110_0==COMMA) ) {
                alt110=1;
            }
            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:345:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2120); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:345:61: ( OFFSET )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( (LA111_0==OFFSET) ) {
                alt111=1;
            }
            switch (alt111) {
                case 1 :
                    // EsperEPL2Ast.g:345:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2123); 

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
    // EsperEPL2Ast.g:348:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:349:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:349:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2141); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2143);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2145);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2147);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2149);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2151);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:349:121: ( valueExprWithTime )?
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( ((LA112_0>=IN_SET && LA112_0<=REGEXP)||LA112_0==NOT_EXPR||(LA112_0>=SUM && LA112_0<=AVG)||(LA112_0>=COALESCE && LA112_0<=COUNT)||(LA112_0>=CASE && LA112_0<=CASE2)||LA112_0==LAST||(LA112_0>=PREVIOUS && LA112_0<=EXISTS)||(LA112_0>=LW && LA112_0<=CURRENT_TIMESTAMP)||(LA112_0>=NUMERIC_PARAM_RANGE && LA112_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA112_0>=EVAL_AND_EXPR && LA112_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA112_0==EVENT_PROP_EXPR||(LA112_0>=CONCAT && LA112_0<=LIB_FUNCTION)||(LA112_0>=TIME_PERIOD && LA112_0<=ARRAY_EXPR)||(LA112_0>=NOT_IN_SET && LA112_0<=NOT_REGEXP)||(LA112_0>=IN_RANGE && LA112_0<=SUBSELECT_EXPR)||(LA112_0>=EXISTS_SUBSELECT_EXPR && LA112_0<=NOT_IN_SUBSELECT_EXPR)||(LA112_0>=LAST_OPERATOR && LA112_0<=SUBSTITUTION)||LA112_0==NUMBERSETSTAR||(LA112_0>=FIRST_AGGREG && LA112_0<=LAST_AGGREG)||(LA112_0>=INT_TYPE && LA112_0<=NULL_TYPE)||(LA112_0>=STAR && LA112_0<=PLUS)||(LA112_0>=BAND && LA112_0<=BXOR)||(LA112_0>=LT && LA112_0<=GE)||(LA112_0>=MINUS && LA112_0<=MOD)) ) {
                alt112=1;
            }
            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:349:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2153);
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
    // EsperEPL2Ast.g:352:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:353:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt113=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt113=1;
                }
                break;
            case GT:
                {
                alt113=2;
                }
                break;
            case LE:
                {
                alt113=3;
                }
                break;
            case GE:
                {
                alt113=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:353:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2170); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2172);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:354:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2185); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2187);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:355:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2200); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2202);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:356:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2214); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2216);
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
    // EsperEPL2Ast.g:359:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:360:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:360:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:360:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:361:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2238);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:362:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( ((LA116_0>=IN_SET && LA116_0<=REGEXP)||LA116_0==NOT_EXPR||(LA116_0>=SUM && LA116_0<=AVG)||(LA116_0>=COALESCE && LA116_0<=COUNT)||(LA116_0>=CASE && LA116_0<=CASE2)||(LA116_0>=PREVIOUS && LA116_0<=EXISTS)||(LA116_0>=INSTANCEOF && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=EVAL_AND_EXPR && LA116_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA116_0==EVENT_PROP_EXPR||(LA116_0>=CONCAT && LA116_0<=LIB_FUNCTION)||LA116_0==ARRAY_EXPR||(LA116_0>=NOT_IN_SET && LA116_0<=NOT_REGEXP)||(LA116_0>=IN_RANGE && LA116_0<=SUBSELECT_EXPR)||(LA116_0>=EXISTS_SUBSELECT_EXPR && LA116_0<=NOT_IN_SUBSELECT_EXPR)||LA116_0==SUBSTITUTION||(LA116_0>=FIRST_AGGREG && LA116_0<=LAST_AGGREG)||(LA116_0>=INT_TYPE && LA116_0<=NULL_TYPE)||(LA116_0>=STAR && LA116_0<=PLUS)||(LA116_0>=BAND && LA116_0<=BXOR)||(LA116_0>=LT && LA116_0<=GE)||(LA116_0>=MINUS && LA116_0<=MOD)) ) {
                alt116=1;
            }
            else if ( ((LA116_0>=ALL && LA116_0<=SOME)) ) {
                alt116=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }
            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:362:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2248);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:364:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:364:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==UP||(LA115_0>=IN_SET && LA115_0<=REGEXP)||LA115_0==NOT_EXPR||(LA115_0>=SUM && LA115_0<=AVG)||(LA115_0>=COALESCE && LA115_0<=COUNT)||(LA115_0>=CASE && LA115_0<=CASE2)||(LA115_0>=PREVIOUS && LA115_0<=EXISTS)||(LA115_0>=INSTANCEOF && LA115_0<=CURRENT_TIMESTAMP)||(LA115_0>=EVAL_AND_EXPR && LA115_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA115_0==EVENT_PROP_EXPR||(LA115_0>=CONCAT && LA115_0<=LIB_FUNCTION)||LA115_0==ARRAY_EXPR||(LA115_0>=NOT_IN_SET && LA115_0<=NOT_REGEXP)||(LA115_0>=IN_RANGE && LA115_0<=SUBSELECT_EXPR)||(LA115_0>=EXISTS_SUBSELECT_EXPR && LA115_0<=NOT_IN_SUBSELECT_EXPR)||LA115_0==SUBSTITUTION||(LA115_0>=FIRST_AGGREG && LA115_0<=LAST_AGGREG)||(LA115_0>=INT_TYPE && LA115_0<=NULL_TYPE)||(LA115_0>=STAR && LA115_0<=PLUS)||(LA115_0>=BAND && LA115_0<=BXOR)||(LA115_0>=LT && LA115_0<=GE)||(LA115_0>=MINUS && LA115_0<=MOD)) ) {
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
                            // EsperEPL2Ast.g:364:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:364:22: ( valueExpr )*
                            loop114:
                            do {
                                int alt114=2;
                                int LA114_0 = input.LA(1);

                                if ( ((LA114_0>=IN_SET && LA114_0<=REGEXP)||LA114_0==NOT_EXPR||(LA114_0>=SUM && LA114_0<=AVG)||(LA114_0>=COALESCE && LA114_0<=COUNT)||(LA114_0>=CASE && LA114_0<=CASE2)||(LA114_0>=PREVIOUS && LA114_0<=EXISTS)||(LA114_0>=INSTANCEOF && LA114_0<=CURRENT_TIMESTAMP)||(LA114_0>=EVAL_AND_EXPR && LA114_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA114_0==EVENT_PROP_EXPR||(LA114_0>=CONCAT && LA114_0<=LIB_FUNCTION)||LA114_0==ARRAY_EXPR||(LA114_0>=NOT_IN_SET && LA114_0<=NOT_REGEXP)||(LA114_0>=IN_RANGE && LA114_0<=SUBSELECT_EXPR)||(LA114_0>=EXISTS_SUBSELECT_EXPR && LA114_0<=NOT_IN_SUBSELECT_EXPR)||LA114_0==SUBSTITUTION||(LA114_0>=FIRST_AGGREG && LA114_0<=LAST_AGGREG)||(LA114_0>=INT_TYPE && LA114_0<=NULL_TYPE)||(LA114_0>=STAR && LA114_0<=PLUS)||(LA114_0>=BAND && LA114_0<=BXOR)||(LA114_0>=LT && LA114_0<=GE)||(LA114_0>=MINUS && LA114_0<=MOD)) ) {
                                    alt114=1;
                                }


                                switch (alt114) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:364:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2272);
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
                            // EsperEPL2Ast.g:364:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2277);
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
    // EsperEPL2Ast.g:369:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:370:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt123=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt123=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt123=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt123=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt123=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt123=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt123=6;
                }
                break;
            case NOT_EXPR:
                {
                alt123=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt123=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 123, 0, input);

                throw nvae;
            }

            switch (alt123) {
                case 1 :
                    // EsperEPL2Ast.g:370:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2303); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2305);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2307);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:370:42: ( valueExpr )*
                    loop117:
                    do {
                        int alt117=2;
                        int LA117_0 = input.LA(1);

                        if ( ((LA117_0>=IN_SET && LA117_0<=REGEXP)||LA117_0==NOT_EXPR||(LA117_0>=SUM && LA117_0<=AVG)||(LA117_0>=COALESCE && LA117_0<=COUNT)||(LA117_0>=CASE && LA117_0<=CASE2)||(LA117_0>=PREVIOUS && LA117_0<=EXISTS)||(LA117_0>=INSTANCEOF && LA117_0<=CURRENT_TIMESTAMP)||(LA117_0>=EVAL_AND_EXPR && LA117_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA117_0==EVENT_PROP_EXPR||(LA117_0>=CONCAT && LA117_0<=LIB_FUNCTION)||LA117_0==ARRAY_EXPR||(LA117_0>=NOT_IN_SET && LA117_0<=NOT_REGEXP)||(LA117_0>=IN_RANGE && LA117_0<=SUBSELECT_EXPR)||(LA117_0>=EXISTS_SUBSELECT_EXPR && LA117_0<=NOT_IN_SUBSELECT_EXPR)||LA117_0==SUBSTITUTION||(LA117_0>=FIRST_AGGREG && LA117_0<=LAST_AGGREG)||(LA117_0>=INT_TYPE && LA117_0<=NULL_TYPE)||(LA117_0>=STAR && LA117_0<=PLUS)||(LA117_0>=BAND && LA117_0<=BXOR)||(LA117_0>=LT && LA117_0<=GE)||(LA117_0>=MINUS && LA117_0<=MOD)) ) {
                            alt117=1;
                        }


                        switch (alt117) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:370:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2310);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop117;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:371:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2324); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2326);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2328);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:371:43: ( valueExpr )*
                    loop118:
                    do {
                        int alt118=2;
                        int LA118_0 = input.LA(1);

                        if ( ((LA118_0>=IN_SET && LA118_0<=REGEXP)||LA118_0==NOT_EXPR||(LA118_0>=SUM && LA118_0<=AVG)||(LA118_0>=COALESCE && LA118_0<=COUNT)||(LA118_0>=CASE && LA118_0<=CASE2)||(LA118_0>=PREVIOUS && LA118_0<=EXISTS)||(LA118_0>=INSTANCEOF && LA118_0<=CURRENT_TIMESTAMP)||(LA118_0>=EVAL_AND_EXPR && LA118_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA118_0==EVENT_PROP_EXPR||(LA118_0>=CONCAT && LA118_0<=LIB_FUNCTION)||LA118_0==ARRAY_EXPR||(LA118_0>=NOT_IN_SET && LA118_0<=NOT_REGEXP)||(LA118_0>=IN_RANGE && LA118_0<=SUBSELECT_EXPR)||(LA118_0>=EXISTS_SUBSELECT_EXPR && LA118_0<=NOT_IN_SUBSELECT_EXPR)||LA118_0==SUBSTITUTION||(LA118_0>=FIRST_AGGREG && LA118_0<=LAST_AGGREG)||(LA118_0>=INT_TYPE && LA118_0<=NULL_TYPE)||(LA118_0>=STAR && LA118_0<=PLUS)||(LA118_0>=BAND && LA118_0<=BXOR)||(LA118_0>=LT && LA118_0<=GE)||(LA118_0>=MINUS && LA118_0<=MOD)) ) {
                            alt118=1;
                        }


                        switch (alt118) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:371:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2331);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop118;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:372:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2345); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2347);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2349);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:373:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2361); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2363);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2365);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:374:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2377); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2379);
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

                    // EsperEPL2Ast.g:374:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt120=2;
                    int LA120_0 = input.LA(1);

                    if ( (LA120_0==UP||(LA120_0>=IN_SET && LA120_0<=REGEXP)||LA120_0==NOT_EXPR||(LA120_0>=SUM && LA120_0<=AVG)||(LA120_0>=COALESCE && LA120_0<=COUNT)||(LA120_0>=CASE && LA120_0<=CASE2)||(LA120_0>=PREVIOUS && LA120_0<=EXISTS)||(LA120_0>=INSTANCEOF && LA120_0<=CURRENT_TIMESTAMP)||(LA120_0>=EVAL_AND_EXPR && LA120_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA120_0==EVENT_PROP_EXPR||(LA120_0>=CONCAT && LA120_0<=LIB_FUNCTION)||LA120_0==ARRAY_EXPR||(LA120_0>=NOT_IN_SET && LA120_0<=NOT_REGEXP)||(LA120_0>=IN_RANGE && LA120_0<=SUBSELECT_EXPR)||(LA120_0>=EXISTS_SUBSELECT_EXPR && LA120_0<=NOT_IN_SUBSELECT_EXPR)||LA120_0==SUBSTITUTION||(LA120_0>=FIRST_AGGREG && LA120_0<=LAST_AGGREG)||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)||(LA120_0>=STAR && LA120_0<=PLUS)||(LA120_0>=BAND && LA120_0<=BXOR)||(LA120_0>=LT && LA120_0<=GE)||(LA120_0>=MINUS && LA120_0<=MOD)) ) {
                        alt120=1;
                    }
                    else if ( (LA120_0==SUBSELECT_GROUP_EXPR) ) {
                        alt120=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 120, 0, input);

                        throw nvae;
                    }
                    switch (alt120) {
                        case 1 :
                            // EsperEPL2Ast.g:374:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:374:59: ( valueExpr )*
                            loop119:
                            do {
                                int alt119=2;
                                int LA119_0 = input.LA(1);

                                if ( ((LA119_0>=IN_SET && LA119_0<=REGEXP)||LA119_0==NOT_EXPR||(LA119_0>=SUM && LA119_0<=AVG)||(LA119_0>=COALESCE && LA119_0<=COUNT)||(LA119_0>=CASE && LA119_0<=CASE2)||(LA119_0>=PREVIOUS && LA119_0<=EXISTS)||(LA119_0>=INSTANCEOF && LA119_0<=CURRENT_TIMESTAMP)||(LA119_0>=EVAL_AND_EXPR && LA119_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA119_0==EVENT_PROP_EXPR||(LA119_0>=CONCAT && LA119_0<=LIB_FUNCTION)||LA119_0==ARRAY_EXPR||(LA119_0>=NOT_IN_SET && LA119_0<=NOT_REGEXP)||(LA119_0>=IN_RANGE && LA119_0<=SUBSELECT_EXPR)||(LA119_0>=EXISTS_SUBSELECT_EXPR && LA119_0<=NOT_IN_SUBSELECT_EXPR)||LA119_0==SUBSTITUTION||(LA119_0>=FIRST_AGGREG && LA119_0<=LAST_AGGREG)||(LA119_0>=INT_TYPE && LA119_0<=NULL_TYPE)||(LA119_0>=STAR && LA119_0<=PLUS)||(LA119_0>=BAND && LA119_0<=BXOR)||(LA119_0>=LT && LA119_0<=GE)||(LA119_0>=MINUS && LA119_0<=MOD)) ) {
                                    alt119=1;
                                }


                                switch (alt119) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:374:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2390);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop119;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:374:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2395);
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
                    // EsperEPL2Ast.g:375:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2408); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2410);
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

                    // EsperEPL2Ast.g:375:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==UP||(LA122_0>=IN_SET && LA122_0<=REGEXP)||LA122_0==NOT_EXPR||(LA122_0>=SUM && LA122_0<=AVG)||(LA122_0>=COALESCE && LA122_0<=COUNT)||(LA122_0>=CASE && LA122_0<=CASE2)||(LA122_0>=PREVIOUS && LA122_0<=EXISTS)||(LA122_0>=INSTANCEOF && LA122_0<=CURRENT_TIMESTAMP)||(LA122_0>=EVAL_AND_EXPR && LA122_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA122_0==EVENT_PROP_EXPR||(LA122_0>=CONCAT && LA122_0<=LIB_FUNCTION)||LA122_0==ARRAY_EXPR||(LA122_0>=NOT_IN_SET && LA122_0<=NOT_REGEXP)||(LA122_0>=IN_RANGE && LA122_0<=SUBSELECT_EXPR)||(LA122_0>=EXISTS_SUBSELECT_EXPR && LA122_0<=NOT_IN_SUBSELECT_EXPR)||LA122_0==SUBSTITUTION||(LA122_0>=FIRST_AGGREG && LA122_0<=LAST_AGGREG)||(LA122_0>=INT_TYPE && LA122_0<=NULL_TYPE)||(LA122_0>=STAR && LA122_0<=PLUS)||(LA122_0>=BAND && LA122_0<=BXOR)||(LA122_0>=LT && LA122_0<=GE)||(LA122_0>=MINUS && LA122_0<=MOD)) ) {
                        alt122=1;
                    }
                    else if ( (LA122_0==SUBSELECT_GROUP_EXPR) ) {
                        alt122=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 122, 0, input);

                        throw nvae;
                    }
                    switch (alt122) {
                        case 1 :
                            // EsperEPL2Ast.g:375:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:375:63: ( valueExpr )*
                            loop121:
                            do {
                                int alt121=2;
                                int LA121_0 = input.LA(1);

                                if ( ((LA121_0>=IN_SET && LA121_0<=REGEXP)||LA121_0==NOT_EXPR||(LA121_0>=SUM && LA121_0<=AVG)||(LA121_0>=COALESCE && LA121_0<=COUNT)||(LA121_0>=CASE && LA121_0<=CASE2)||(LA121_0>=PREVIOUS && LA121_0<=EXISTS)||(LA121_0>=INSTANCEOF && LA121_0<=CURRENT_TIMESTAMP)||(LA121_0>=EVAL_AND_EXPR && LA121_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA121_0==EVENT_PROP_EXPR||(LA121_0>=CONCAT && LA121_0<=LIB_FUNCTION)||LA121_0==ARRAY_EXPR||(LA121_0>=NOT_IN_SET && LA121_0<=NOT_REGEXP)||(LA121_0>=IN_RANGE && LA121_0<=SUBSELECT_EXPR)||(LA121_0>=EXISTS_SUBSELECT_EXPR && LA121_0<=NOT_IN_SUBSELECT_EXPR)||LA121_0==SUBSTITUTION||(LA121_0>=FIRST_AGGREG && LA121_0<=LAST_AGGREG)||(LA121_0>=INT_TYPE && LA121_0<=NULL_TYPE)||(LA121_0>=STAR && LA121_0<=PLUS)||(LA121_0>=BAND && LA121_0<=BXOR)||(LA121_0>=LT && LA121_0<=GE)||(LA121_0>=MINUS && LA121_0<=MOD)) ) {
                                    alt121=1;
                                }


                                switch (alt121) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:375:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2421);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop121;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:375:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2426);
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
                    // EsperEPL2Ast.g:376:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2439); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2441);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:377:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2452);
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
    // EsperEPL2Ast.g:380:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:381:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt124=16;
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
            case SUBSTITUTION:
                {
                alt124=2;
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
                alt124=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt124=4;
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
                alt124=5;
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
                alt124=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt124=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt124=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt124=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt124=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt124=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt124=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt124=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt124=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt124=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt124=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }

            switch (alt124) {
                case 1 :
                    // EsperEPL2Ast.g:381:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2465);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:382:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2471);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:383:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2477);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:384:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2484);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:385:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2493);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:386:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2498);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:387:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2506);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:388:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2511);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:389:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2516);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:390:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2522);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:391:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2527);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:392:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2532);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:393:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2537);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:394:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2542);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:395:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2548);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:396:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2555);
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
    // EsperEPL2Ast.g:399:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:400:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt126=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt126=1;
                }
                break;
            case LW:
                {
                alt126=2;
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
                alt126=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt126=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt126=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt126=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt126=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt126=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt126=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt126=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt126=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                throw nvae;
            }

            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:400:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2568); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:401:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2577); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:402:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2584);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:403:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2592); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2594);
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
                    // EsperEPL2Ast.g:404:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2609);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:405:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2615);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:406:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2620);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:407:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2625);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:408:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2635); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:408:29: ( numericParameterList )+
                    int cnt125=0;
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( (LA125_0==NUMERIC_PARAM_RANGE||LA125_0==NUMERIC_PARAM_FREQUENCY||(LA125_0>=INT_TYPE && LA125_0<=NULL_TYPE)) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:408:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2637);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt125 >= 1 ) break loop125;
                                EarlyExitException eee =
                                    new EarlyExitException(125, input);
                                throw eee;
                        }
                        cnt125++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:409:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2648); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:410:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2655);
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
    // EsperEPL2Ast.g:413:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:414:2: ( constant[true] | rangeOperator | frequencyOperator )
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
            case NUMERIC_PARAM_RANGE:
                {
                alt127=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:414:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2668);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:415:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2675);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:416:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2681);
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
    // EsperEPL2Ast.g:419:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:420:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:420:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2697); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:420:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt128=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt128=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt128=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt128=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 128, 0, input);

                throw nvae;
            }

            switch (alt128) {
                case 1 :
                    // EsperEPL2Ast.g:420:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2700);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:420:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2703);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:420:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2706);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:420:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt129=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt129=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt129=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt129=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 129, 0, input);

                throw nvae;
            }

            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:420:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2710);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:420:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2713);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:420:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2716);
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
    // EsperEPL2Ast.g:423:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:424:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:424:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2737); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:424:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt130=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt130=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt130=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt130=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 130, 0, input);

                throw nvae;
            }

            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:424:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2740);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:424:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2743);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:424:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2746);
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
    // EsperEPL2Ast.g:427:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:428:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:428:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2765); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:428:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt131=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt131=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt131=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt131=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 131, 0, input);

                throw nvae;
            }

            switch (alt131) {
                case 1 :
                    // EsperEPL2Ast.g:428:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2768);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:428:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2771);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:428:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2774);
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
    // EsperEPL2Ast.g:431:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:432:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:432:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2793); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:432:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt132=3;
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
            case EVENT_PROP_EXPR:
                {
                alt132=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt132=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }

            switch (alt132) {
                case 1 :
                    // EsperEPL2Ast.g:432:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2796);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:432:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2799);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:432:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2802);
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
    // EsperEPL2Ast.g:435:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:436:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:436:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2823); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr2825);
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
    // EsperEPL2Ast.g:439:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:440:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:440:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2844); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2846);
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
    // EsperEPL2Ast.g:443:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:444:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:444:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2865); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2867);
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
    // EsperEPL2Ast.g:447:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:448:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==IN_SUBSELECT_EXPR) ) {
                alt133=1;
            }
            else if ( (LA133_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt133=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 133, 0, input);

                throw nvae;
            }
            switch (alt133) {
                case 1 :
                    // EsperEPL2Ast.g:448:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2886); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2888);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2890);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:449:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2902); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2904);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2906);
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
    // EsperEPL2Ast.g:452:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:453:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:453:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2925); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2927);
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
    // EsperEPL2Ast.g:456:1: subQueryExpr : selectionListElement subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:457:2: ( selectionListElement subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:457:4: selectionListElement subSelectFilterExpr ( whereClause[true] )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2943);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2945);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:457:45: ( whereClause[true] )?
            int alt134=2;
            int LA134_0 = input.LA(1);

            if ( (LA134_0==WHERE_EXPR) ) {
                alt134=1;
            }
            switch (alt134) {
                case 1 :
                    // EsperEPL2Ast.g:457:46: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2948);
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
    // EsperEPL2Ast.g:460:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:461:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:461:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2966); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2968);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:461:36: ( viewListExpr )?
            int alt135=2;
            int LA135_0 = input.LA(1);

            if ( (LA135_0==VIEW_EXPR) ) {
                alt135=1;
            }
            switch (alt135) {
                case 1 :
                    // EsperEPL2Ast.g:461:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2971);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:461:52: ( IDENT )?
            int alt136=2;
            int LA136_0 = input.LA(1);

            if ( (LA136_0==IDENT) ) {
                alt136=1;
            }
            switch (alt136) {
                case 1 :
                    // EsperEPL2Ast.g:461:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2976); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:461:61: ( RETAINUNION )?
            int alt137=2;
            int LA137_0 = input.LA(1);

            if ( (LA137_0==RETAINUNION) ) {
                alt137=1;
            }
            switch (alt137) {
                case 1 :
                    // EsperEPL2Ast.g:461:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr2980); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:461:74: ( RETAININTERSECTION )?
            int alt138=2;
            int LA138_0 = input.LA(1);

            if ( (LA138_0==RETAININTERSECTION) ) {
                alt138=1;
            }
            switch (alt138) {
                case 1 :
                    // EsperEPL2Ast.g:461:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2983); 

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
    // EsperEPL2Ast.g:464:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:465:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt141=2;
            int LA141_0 = input.LA(1);

            if ( (LA141_0==CASE) ) {
                alt141=1;
            }
            else if ( (LA141_0==CASE2) ) {
                alt141=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 141, 0, input);

                throw nvae;
            }
            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:465:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3003); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:465:13: ( valueExpr )*
                        loop139:
                        do {
                            int alt139=2;
                            int LA139_0 = input.LA(1);

                            if ( ((LA139_0>=IN_SET && LA139_0<=REGEXP)||LA139_0==NOT_EXPR||(LA139_0>=SUM && LA139_0<=AVG)||(LA139_0>=COALESCE && LA139_0<=COUNT)||(LA139_0>=CASE && LA139_0<=CASE2)||(LA139_0>=PREVIOUS && LA139_0<=EXISTS)||(LA139_0>=INSTANCEOF && LA139_0<=CURRENT_TIMESTAMP)||(LA139_0>=EVAL_AND_EXPR && LA139_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA139_0==EVENT_PROP_EXPR||(LA139_0>=CONCAT && LA139_0<=LIB_FUNCTION)||LA139_0==ARRAY_EXPR||(LA139_0>=NOT_IN_SET && LA139_0<=NOT_REGEXP)||(LA139_0>=IN_RANGE && LA139_0<=SUBSELECT_EXPR)||(LA139_0>=EXISTS_SUBSELECT_EXPR && LA139_0<=NOT_IN_SUBSELECT_EXPR)||LA139_0==SUBSTITUTION||(LA139_0>=FIRST_AGGREG && LA139_0<=LAST_AGGREG)||(LA139_0>=INT_TYPE && LA139_0<=NULL_TYPE)||(LA139_0>=STAR && LA139_0<=PLUS)||(LA139_0>=BAND && LA139_0<=BXOR)||(LA139_0>=LT && LA139_0<=GE)||(LA139_0>=MINUS && LA139_0<=MOD)) ) {
                                alt139=1;
                            }


                            switch (alt139) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:465:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3006);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop139;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:466:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3019); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:466:14: ( valueExpr )*
                        loop140:
                        do {
                            int alt140=2;
                            int LA140_0 = input.LA(1);

                            if ( ((LA140_0>=IN_SET && LA140_0<=REGEXP)||LA140_0==NOT_EXPR||(LA140_0>=SUM && LA140_0<=AVG)||(LA140_0>=COALESCE && LA140_0<=COUNT)||(LA140_0>=CASE && LA140_0<=CASE2)||(LA140_0>=PREVIOUS && LA140_0<=EXISTS)||(LA140_0>=INSTANCEOF && LA140_0<=CURRENT_TIMESTAMP)||(LA140_0>=EVAL_AND_EXPR && LA140_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA140_0==EVENT_PROP_EXPR||(LA140_0>=CONCAT && LA140_0<=LIB_FUNCTION)||LA140_0==ARRAY_EXPR||(LA140_0>=NOT_IN_SET && LA140_0<=NOT_REGEXP)||(LA140_0>=IN_RANGE && LA140_0<=SUBSELECT_EXPR)||(LA140_0>=EXISTS_SUBSELECT_EXPR && LA140_0<=NOT_IN_SUBSELECT_EXPR)||LA140_0==SUBSTITUTION||(LA140_0>=FIRST_AGGREG && LA140_0<=LAST_AGGREG)||(LA140_0>=INT_TYPE && LA140_0<=NULL_TYPE)||(LA140_0>=STAR && LA140_0<=PLUS)||(LA140_0>=BAND && LA140_0<=BXOR)||(LA140_0>=LT && LA140_0<=GE)||(LA140_0>=MINUS && LA140_0<=MOD)) ) {
                                alt140=1;
                            }


                            switch (alt140) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:466:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3022);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop140;
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
    // EsperEPL2Ast.g:469:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:470:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt144=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt144=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt144=2;
                }
                break;
            case IN_RANGE:
                {
                alt144=3;
                }
                break;
            case NOT_IN_RANGE:
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
                    // EsperEPL2Ast.g:470:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3042); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3044);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3052);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:470:51: ( valueExpr )*
                    loop142:
                    do {
                        int alt142=2;
                        int LA142_0 = input.LA(1);

                        if ( ((LA142_0>=IN_SET && LA142_0<=REGEXP)||LA142_0==NOT_EXPR||(LA142_0>=SUM && LA142_0<=AVG)||(LA142_0>=COALESCE && LA142_0<=COUNT)||(LA142_0>=CASE && LA142_0<=CASE2)||(LA142_0>=PREVIOUS && LA142_0<=EXISTS)||(LA142_0>=INSTANCEOF && LA142_0<=CURRENT_TIMESTAMP)||(LA142_0>=EVAL_AND_EXPR && LA142_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA142_0==EVENT_PROP_EXPR||(LA142_0>=CONCAT && LA142_0<=LIB_FUNCTION)||LA142_0==ARRAY_EXPR||(LA142_0>=NOT_IN_SET && LA142_0<=NOT_REGEXP)||(LA142_0>=IN_RANGE && LA142_0<=SUBSELECT_EXPR)||(LA142_0>=EXISTS_SUBSELECT_EXPR && LA142_0<=NOT_IN_SUBSELECT_EXPR)||LA142_0==SUBSTITUTION||(LA142_0>=FIRST_AGGREG && LA142_0<=LAST_AGGREG)||(LA142_0>=INT_TYPE && LA142_0<=NULL_TYPE)||(LA142_0>=STAR && LA142_0<=PLUS)||(LA142_0>=BAND && LA142_0<=BXOR)||(LA142_0>=LT && LA142_0<=GE)||(LA142_0>=MINUS && LA142_0<=MOD)) ) {
                            alt142=1;
                        }


                        switch (alt142) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:470:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3055);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop142;
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
                    // EsperEPL2Ast.g:471:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3074); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3076);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3084);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:471:55: ( valueExpr )*
                    loop143:
                    do {
                        int alt143=2;
                        int LA143_0 = input.LA(1);

                        if ( ((LA143_0>=IN_SET && LA143_0<=REGEXP)||LA143_0==NOT_EXPR||(LA143_0>=SUM && LA143_0<=AVG)||(LA143_0>=COALESCE && LA143_0<=COUNT)||(LA143_0>=CASE && LA143_0<=CASE2)||(LA143_0>=PREVIOUS && LA143_0<=EXISTS)||(LA143_0>=INSTANCEOF && LA143_0<=CURRENT_TIMESTAMP)||(LA143_0>=EVAL_AND_EXPR && LA143_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA143_0==EVENT_PROP_EXPR||(LA143_0>=CONCAT && LA143_0<=LIB_FUNCTION)||LA143_0==ARRAY_EXPR||(LA143_0>=NOT_IN_SET && LA143_0<=NOT_REGEXP)||(LA143_0>=IN_RANGE && LA143_0<=SUBSELECT_EXPR)||(LA143_0>=EXISTS_SUBSELECT_EXPR && LA143_0<=NOT_IN_SUBSELECT_EXPR)||LA143_0==SUBSTITUTION||(LA143_0>=FIRST_AGGREG && LA143_0<=LAST_AGGREG)||(LA143_0>=INT_TYPE && LA143_0<=NULL_TYPE)||(LA143_0>=STAR && LA143_0<=PLUS)||(LA143_0>=BAND && LA143_0<=BXOR)||(LA143_0>=LT && LA143_0<=GE)||(LA143_0>=MINUS && LA143_0<=MOD)) ) {
                            alt143=1;
                        }


                        switch (alt143) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:471:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3087);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop143;
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
                    // EsperEPL2Ast.g:472:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3106); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3108);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3116);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3118);
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
                    // EsperEPL2Ast.g:473:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3135); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3137);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3145);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3147);
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
    // EsperEPL2Ast.g:476:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:477:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt146=2;
            int LA146_0 = input.LA(1);

            if ( (LA146_0==BETWEEN) ) {
                alt146=1;
            }
            else if ( (LA146_0==NOT_BETWEEN) ) {
                alt146=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 146, 0, input);

                throw nvae;
            }
            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:477:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3172); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3174);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3176);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3178);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:478:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3189); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3191);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3193);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:478:40: ( valueExpr )*
                    loop145:
                    do {
                        int alt145=2;
                        int LA145_0 = input.LA(1);

                        if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||(LA145_0>=CONCAT && LA145_0<=LIB_FUNCTION)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=SUBSELECT_EXPR)||(LA145_0>=EXISTS_SUBSELECT_EXPR && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=FIRST_AGGREG && LA145_0<=LAST_AGGREG)||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||(LA145_0>=STAR && LA145_0<=PLUS)||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=MINUS && LA145_0<=MOD)) ) {
                            alt145=1;
                        }


                        switch (alt145) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:478:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3196);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop145;
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
    // EsperEPL2Ast.g:481:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:482:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==LIKE) ) {
                alt149=1;
            }
            else if ( (LA149_0==NOT_LIKE) ) {
                alt149=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 149, 0, input);

                throw nvae;
            }
            switch (alt149) {
                case 1 :
                    // EsperEPL2Ast.g:482:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3216); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3218);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3220);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:482:33: ( valueExpr )?
                    int alt147=2;
                    int LA147_0 = input.LA(1);

                    if ( ((LA147_0>=IN_SET && LA147_0<=REGEXP)||LA147_0==NOT_EXPR||(LA147_0>=SUM && LA147_0<=AVG)||(LA147_0>=COALESCE && LA147_0<=COUNT)||(LA147_0>=CASE && LA147_0<=CASE2)||(LA147_0>=PREVIOUS && LA147_0<=EXISTS)||(LA147_0>=INSTANCEOF && LA147_0<=CURRENT_TIMESTAMP)||(LA147_0>=EVAL_AND_EXPR && LA147_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA147_0==EVENT_PROP_EXPR||(LA147_0>=CONCAT && LA147_0<=LIB_FUNCTION)||LA147_0==ARRAY_EXPR||(LA147_0>=NOT_IN_SET && LA147_0<=NOT_REGEXP)||(LA147_0>=IN_RANGE && LA147_0<=SUBSELECT_EXPR)||(LA147_0>=EXISTS_SUBSELECT_EXPR && LA147_0<=NOT_IN_SUBSELECT_EXPR)||LA147_0==SUBSTITUTION||(LA147_0>=FIRST_AGGREG && LA147_0<=LAST_AGGREG)||(LA147_0>=INT_TYPE && LA147_0<=NULL_TYPE)||(LA147_0>=STAR && LA147_0<=PLUS)||(LA147_0>=BAND && LA147_0<=BXOR)||(LA147_0>=LT && LA147_0<=GE)||(LA147_0>=MINUS && LA147_0<=MOD)) ) {
                        alt147=1;
                    }
                    switch (alt147) {
                        case 1 :
                            // EsperEPL2Ast.g:482:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3223);
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
                    // EsperEPL2Ast.g:483:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3236); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3238);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3240);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:483:37: ( valueExpr )?
                    int alt148=2;
                    int LA148_0 = input.LA(1);

                    if ( ((LA148_0>=IN_SET && LA148_0<=REGEXP)||LA148_0==NOT_EXPR||(LA148_0>=SUM && LA148_0<=AVG)||(LA148_0>=COALESCE && LA148_0<=COUNT)||(LA148_0>=CASE && LA148_0<=CASE2)||(LA148_0>=PREVIOUS && LA148_0<=EXISTS)||(LA148_0>=INSTANCEOF && LA148_0<=CURRENT_TIMESTAMP)||(LA148_0>=EVAL_AND_EXPR && LA148_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA148_0==EVENT_PROP_EXPR||(LA148_0>=CONCAT && LA148_0<=LIB_FUNCTION)||LA148_0==ARRAY_EXPR||(LA148_0>=NOT_IN_SET && LA148_0<=NOT_REGEXP)||(LA148_0>=IN_RANGE && LA148_0<=SUBSELECT_EXPR)||(LA148_0>=EXISTS_SUBSELECT_EXPR && LA148_0<=NOT_IN_SUBSELECT_EXPR)||LA148_0==SUBSTITUTION||(LA148_0>=FIRST_AGGREG && LA148_0<=LAST_AGGREG)||(LA148_0>=INT_TYPE && LA148_0<=NULL_TYPE)||(LA148_0>=STAR && LA148_0<=PLUS)||(LA148_0>=BAND && LA148_0<=BXOR)||(LA148_0>=LT && LA148_0<=GE)||(LA148_0>=MINUS && LA148_0<=MOD)) ) {
                        alt148=1;
                    }
                    switch (alt148) {
                        case 1 :
                            // EsperEPL2Ast.g:483:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3243);
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
    // EsperEPL2Ast.g:486:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:487:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( (LA150_0==REGEXP) ) {
                alt150=1;
            }
            else if ( (LA150_0==NOT_REGEXP) ) {
                alt150=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }
            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:487:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3262); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3264);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3266);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:488:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3277); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3279);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3281);
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
    // EsperEPL2Ast.g:491:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:492:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt163=15;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt163=1;
                }
                break;
            case AVG:
                {
                alt163=2;
                }
                break;
            case COUNT:
                {
                alt163=3;
                }
                break;
            case MEDIAN:
                {
                alt163=4;
                }
                break;
            case STDDEV:
                {
                alt163=5;
                }
                break;
            case AVEDEV:
                {
                alt163=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt163=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt163=8;
                }
                break;
            case COALESCE:
                {
                alt163=9;
                }
                break;
            case PREVIOUS:
                {
                alt163=10;
                }
                break;
            case PRIOR:
                {
                alt163=11;
                }
                break;
            case INSTANCEOF:
                {
                alt163=12;
                }
                break;
            case CAST:
                {
                alt163=13;
                }
                break;
            case EXISTS:
                {
                alt163=14;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt163=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 163, 0, input);

                throw nvae;
            }

            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:492:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3300); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:492:13: ( DISTINCT )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==DISTINCT) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:492:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3303); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3307);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:493:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3318); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:493:12: ( DISTINCT )?
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( (LA152_0==DISTINCT) ) {
                        alt152=1;
                    }
                    switch (alt152) {
                        case 1 :
                            // EsperEPL2Ast.g:493:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3321); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3325);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:494:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3336); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:494:14: ( ( DISTINCT )? valueExpr )?
                        int alt154=2;
                        int LA154_0 = input.LA(1);

                        if ( ((LA154_0>=IN_SET && LA154_0<=REGEXP)||LA154_0==NOT_EXPR||(LA154_0>=SUM && LA154_0<=AVG)||(LA154_0>=COALESCE && LA154_0<=COUNT)||(LA154_0>=CASE && LA154_0<=CASE2)||LA154_0==DISTINCT||(LA154_0>=PREVIOUS && LA154_0<=EXISTS)||(LA154_0>=INSTANCEOF && LA154_0<=CURRENT_TIMESTAMP)||(LA154_0>=EVAL_AND_EXPR && LA154_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA154_0==EVENT_PROP_EXPR||(LA154_0>=CONCAT && LA154_0<=LIB_FUNCTION)||LA154_0==ARRAY_EXPR||(LA154_0>=NOT_IN_SET && LA154_0<=NOT_REGEXP)||(LA154_0>=IN_RANGE && LA154_0<=SUBSELECT_EXPR)||(LA154_0>=EXISTS_SUBSELECT_EXPR && LA154_0<=NOT_IN_SUBSELECT_EXPR)||LA154_0==SUBSTITUTION||(LA154_0>=FIRST_AGGREG && LA154_0<=LAST_AGGREG)||(LA154_0>=INT_TYPE && LA154_0<=NULL_TYPE)||(LA154_0>=STAR && LA154_0<=PLUS)||(LA154_0>=BAND && LA154_0<=BXOR)||(LA154_0>=LT && LA154_0<=GE)||(LA154_0>=MINUS && LA154_0<=MOD)) ) {
                            alt154=1;
                        }
                        switch (alt154) {
                            case 1 :
                                // EsperEPL2Ast.g:494:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:494:15: ( DISTINCT )?
                                int alt153=2;
                                int LA153_0 = input.LA(1);

                                if ( (LA153_0==DISTINCT) ) {
                                    alt153=1;
                                }
                                switch (alt153) {
                                    case 1 :
                                        // EsperEPL2Ast.g:494:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3340); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3344);
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
                    // EsperEPL2Ast.g:495:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3358); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:495:15: ( DISTINCT )?
                    int alt155=2;
                    int LA155_0 = input.LA(1);

                    if ( (LA155_0==DISTINCT) ) {
                        alt155=1;
                    }
                    switch (alt155) {
                        case 1 :
                            // EsperEPL2Ast.g:495:16: DISTINCT
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
                case 5 :
                    // EsperEPL2Ast.g:496:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3376); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:496:15: ( DISTINCT )?
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( (LA156_0==DISTINCT) ) {
                        alt156=1;
                    }
                    switch (alt156) {
                        case 1 :
                            // EsperEPL2Ast.g:496:16: DISTINCT
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
                case 6 :
                    // EsperEPL2Ast.g:497:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3394); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:497:15: ( DISTINCT )?
                    int alt157=2;
                    int LA157_0 = input.LA(1);

                    if ( (LA157_0==DISTINCT) ) {
                        alt157=1;
                    }
                    switch (alt157) {
                        case 1 :
                            // EsperEPL2Ast.g:497:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3397); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3401);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:498:4: ^(f= LAST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3412); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:498:20: ( DISTINCT )?
                    int alt158=2;
                    int LA158_0 = input.LA(1);

                    if ( (LA158_0==DISTINCT) ) {
                        alt158=1;
                    }
                    switch (alt158) {
                        case 1 :
                            // EsperEPL2Ast.g:498:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3415); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3419);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:499:4: ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3430); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:499:21: ( DISTINCT )?
                    int alt159=2;
                    int LA159_0 = input.LA(1);

                    if ( (LA159_0==DISTINCT) ) {
                        alt159=1;
                    }
                    switch (alt159) {
                        case 1 :
                            // EsperEPL2Ast.g:499:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3433); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3437);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:500:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3449); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3451);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3453);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:500:38: ( valueExpr )*
                    loop160:
                    do {
                        int alt160=2;
                        int LA160_0 = input.LA(1);

                        if ( ((LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||(LA160_0>=CONCAT && LA160_0<=LIB_FUNCTION)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=FIRST_AGGREG && LA160_0<=LAST_AGGREG)||(LA160_0>=INT_TYPE && LA160_0<=NULL_TYPE)||(LA160_0>=STAR && LA160_0<=PLUS)||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=MINUS && LA160_0<=MOD)) ) {
                            alt160=1;
                        }


                        switch (alt160) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:500:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3456);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop160;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:501:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3471); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3473);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:501:28: ( valueExpr )?
                    int alt161=2;
                    int LA161_0 = input.LA(1);

                    if ( ((LA161_0>=IN_SET && LA161_0<=REGEXP)||LA161_0==NOT_EXPR||(LA161_0>=SUM && LA161_0<=AVG)||(LA161_0>=COALESCE && LA161_0<=COUNT)||(LA161_0>=CASE && LA161_0<=CASE2)||(LA161_0>=PREVIOUS && LA161_0<=EXISTS)||(LA161_0>=INSTANCEOF && LA161_0<=CURRENT_TIMESTAMP)||(LA161_0>=EVAL_AND_EXPR && LA161_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA161_0==EVENT_PROP_EXPR||(LA161_0>=CONCAT && LA161_0<=LIB_FUNCTION)||LA161_0==ARRAY_EXPR||(LA161_0>=NOT_IN_SET && LA161_0<=NOT_REGEXP)||(LA161_0>=IN_RANGE && LA161_0<=SUBSELECT_EXPR)||(LA161_0>=EXISTS_SUBSELECT_EXPR && LA161_0<=NOT_IN_SUBSELECT_EXPR)||LA161_0==SUBSTITUTION||(LA161_0>=FIRST_AGGREG && LA161_0<=LAST_AGGREG)||(LA161_0>=INT_TYPE && LA161_0<=NULL_TYPE)||(LA161_0>=STAR && LA161_0<=PLUS)||(LA161_0>=BAND && LA161_0<=BXOR)||(LA161_0>=LT && LA161_0<=GE)||(LA161_0>=MINUS && LA161_0<=MOD)) ) {
                        alt161=1;
                    }
                    switch (alt161) {
                        case 1 :
                            // EsperEPL2Ast.g:501:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3475);
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
                    // EsperEPL2Ast.g:502:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3488); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3492); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3494);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:503:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3507); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3509);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3511); 
                    // EsperEPL2Ast.g:503:42: ( CLASS_IDENT )*
                    loop162:
                    do {
                        int alt162=2;
                        int LA162_0 = input.LA(1);

                        if ( (LA162_0==CLASS_IDENT) ) {
                            alt162=1;
                        }


                        switch (alt162) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:503:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3514); 

                    	    }
                    	    break;

                    	default :
                    	    break loop162;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:504:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3528); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3530);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3532); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:505:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3544); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3546);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:506:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3558); 



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
    // EsperEPL2Ast.g:509:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:510:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:510:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3578); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:510:19: ( valueExpr )*
                loop164:
                do {
                    int alt164=2;
                    int LA164_0 = input.LA(1);

                    if ( ((LA164_0>=IN_SET && LA164_0<=REGEXP)||LA164_0==NOT_EXPR||(LA164_0>=SUM && LA164_0<=AVG)||(LA164_0>=COALESCE && LA164_0<=COUNT)||(LA164_0>=CASE && LA164_0<=CASE2)||(LA164_0>=PREVIOUS && LA164_0<=EXISTS)||(LA164_0>=INSTANCEOF && LA164_0<=CURRENT_TIMESTAMP)||(LA164_0>=EVAL_AND_EXPR && LA164_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA164_0==EVENT_PROP_EXPR||(LA164_0>=CONCAT && LA164_0<=LIB_FUNCTION)||LA164_0==ARRAY_EXPR||(LA164_0>=NOT_IN_SET && LA164_0<=NOT_REGEXP)||(LA164_0>=IN_RANGE && LA164_0<=SUBSELECT_EXPR)||(LA164_0>=EXISTS_SUBSELECT_EXPR && LA164_0<=NOT_IN_SUBSELECT_EXPR)||LA164_0==SUBSTITUTION||(LA164_0>=FIRST_AGGREG && LA164_0<=LAST_AGGREG)||(LA164_0>=INT_TYPE && LA164_0<=NULL_TYPE)||(LA164_0>=STAR && LA164_0<=PLUS)||(LA164_0>=BAND && LA164_0<=BXOR)||(LA164_0>=LT && LA164_0<=GE)||(LA164_0>=MINUS && LA164_0<=MOD)) ) {
                        alt164=1;
                    }


                    switch (alt164) {
                	case 1 :
                	    // EsperEPL2Ast.g:510:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3581);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop164;
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
    // EsperEPL2Ast.g:513:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:514:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt166=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt166=1;
                }
                break;
            case MINUS:
                {
                alt166=2;
                }
                break;
            case DIV:
                {
                alt166=3;
                }
                break;
            case STAR:
                {
                alt166=4;
                }
                break;
            case MOD:
                {
                alt166=5;
                }
                break;
            case BAND:
                {
                alt166=6;
                }
                break;
            case BOR:
                {
                alt166=7;
                }
                break;
            case BXOR:
                {
                alt166=8;
                }
                break;
            case CONCAT:
                {
                alt166=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 166, 0, input);

                throw nvae;
            }

            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:514:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3602); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3604);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3606);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:515:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3618); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3620);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3622);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:516:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3634); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3636);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3638);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:517:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3649); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3651);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3653);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:518:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3665); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3667);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3669);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:519:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3680); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3682);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3684);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:520:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3695); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3697);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3699);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:521:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3710); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3712);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3714);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:522:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3726); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3728);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3730);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:522:36: ( valueExpr )*
                    loop165:
                    do {
                        int alt165=2;
                        int LA165_0 = input.LA(1);

                        if ( ((LA165_0>=IN_SET && LA165_0<=REGEXP)||LA165_0==NOT_EXPR||(LA165_0>=SUM && LA165_0<=AVG)||(LA165_0>=COALESCE && LA165_0<=COUNT)||(LA165_0>=CASE && LA165_0<=CASE2)||(LA165_0>=PREVIOUS && LA165_0<=EXISTS)||(LA165_0>=INSTANCEOF && LA165_0<=CURRENT_TIMESTAMP)||(LA165_0>=EVAL_AND_EXPR && LA165_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA165_0==EVENT_PROP_EXPR||(LA165_0>=CONCAT && LA165_0<=LIB_FUNCTION)||LA165_0==ARRAY_EXPR||(LA165_0>=NOT_IN_SET && LA165_0<=NOT_REGEXP)||(LA165_0>=IN_RANGE && LA165_0<=SUBSELECT_EXPR)||(LA165_0>=EXISTS_SUBSELECT_EXPR && LA165_0<=NOT_IN_SUBSELECT_EXPR)||LA165_0==SUBSTITUTION||(LA165_0>=FIRST_AGGREG && LA165_0<=LAST_AGGREG)||(LA165_0>=INT_TYPE && LA165_0<=NULL_TYPE)||(LA165_0>=STAR && LA165_0<=PLUS)||(LA165_0>=BAND && LA165_0<=BXOR)||(LA165_0>=LT && LA165_0<=GE)||(LA165_0>=MINUS && LA165_0<=MOD)) ) {
                            alt165=1;
                        }


                        switch (alt165) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:522:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3733);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop165;
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
    // EsperEPL2Ast.g:525:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:526:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:526:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3754); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:526:22: ( CLASS_IDENT )?
            int alt167=2;
            int LA167_0 = input.LA(1);

            if ( (LA167_0==CLASS_IDENT) ) {
                alt167=1;
            }
            switch (alt167) {
                case 1 :
                    // EsperEPL2Ast.g:526:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3757); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3761); 
            // EsperEPL2Ast.g:526:43: ( DISTINCT )?
            int alt168=2;
            int LA168_0 = input.LA(1);

            if ( (LA168_0==DISTINCT) ) {
                alt168=1;
            }
            switch (alt168) {
                case 1 :
                    // EsperEPL2Ast.g:526:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3764); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:526:55: ( valueExpr )*
            loop169:
            do {
                int alt169=2;
                int LA169_0 = input.LA(1);

                if ( ((LA169_0>=IN_SET && LA169_0<=REGEXP)||LA169_0==NOT_EXPR||(LA169_0>=SUM && LA169_0<=AVG)||(LA169_0>=COALESCE && LA169_0<=COUNT)||(LA169_0>=CASE && LA169_0<=CASE2)||(LA169_0>=PREVIOUS && LA169_0<=EXISTS)||(LA169_0>=INSTANCEOF && LA169_0<=CURRENT_TIMESTAMP)||(LA169_0>=EVAL_AND_EXPR && LA169_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA169_0==EVENT_PROP_EXPR||(LA169_0>=CONCAT && LA169_0<=LIB_FUNCTION)||LA169_0==ARRAY_EXPR||(LA169_0>=NOT_IN_SET && LA169_0<=NOT_REGEXP)||(LA169_0>=IN_RANGE && LA169_0<=SUBSELECT_EXPR)||(LA169_0>=EXISTS_SUBSELECT_EXPR && LA169_0<=NOT_IN_SUBSELECT_EXPR)||LA169_0==SUBSTITUTION||(LA169_0>=FIRST_AGGREG && LA169_0<=LAST_AGGREG)||(LA169_0>=INT_TYPE && LA169_0<=NULL_TYPE)||(LA169_0>=STAR && LA169_0<=PLUS)||(LA169_0>=BAND && LA169_0<=BXOR)||(LA169_0>=LT && LA169_0<=GE)||(LA169_0>=MINUS && LA169_0<=MOD)) ) {
                    alt169=1;
                }


                switch (alt169) {
            	case 1 :
            	    // EsperEPL2Ast.g:526:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3769);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop169;
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
    // EsperEPL2Ast.g:532:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:533:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:533:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:533:4: ( annotation[true] )*
            loop170:
            do {
                int alt170=2;
                int LA170_0 = input.LA(1);

                if ( (LA170_0==ANNOTATION) ) {
                    alt170=1;
                }


                switch (alt170) {
            	case 1 :
            	    // EsperEPL2Ast.g:533:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule3789);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop170;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3793);
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
    // EsperEPL2Ast.g:536:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:537:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt174=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt174=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt174=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt174=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt174=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt174=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt174=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt174=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }

            switch (alt174) {
                case 1 :
                    // EsperEPL2Ast.g:537:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3807);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:538:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3812);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:539:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3822); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3824);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:540:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3838); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice3840);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3842);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:541:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3856); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3858);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:542:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3872); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3874);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3876); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3878); 
                    // EsperEPL2Ast.g:542:44: ( valueExprWithTime )*
                    loop171:
                    do {
                        int alt171=2;
                        int LA171_0 = input.LA(1);

                        if ( ((LA171_0>=IN_SET && LA171_0<=REGEXP)||LA171_0==NOT_EXPR||(LA171_0>=SUM && LA171_0<=AVG)||(LA171_0>=COALESCE && LA171_0<=COUNT)||(LA171_0>=CASE && LA171_0<=CASE2)||LA171_0==LAST||(LA171_0>=PREVIOUS && LA171_0<=EXISTS)||(LA171_0>=LW && LA171_0<=CURRENT_TIMESTAMP)||(LA171_0>=NUMERIC_PARAM_RANGE && LA171_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA171_0>=EVAL_AND_EXPR && LA171_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA171_0==EVENT_PROP_EXPR||(LA171_0>=CONCAT && LA171_0<=LIB_FUNCTION)||(LA171_0>=TIME_PERIOD && LA171_0<=ARRAY_EXPR)||(LA171_0>=NOT_IN_SET && LA171_0<=NOT_REGEXP)||(LA171_0>=IN_RANGE && LA171_0<=SUBSELECT_EXPR)||(LA171_0>=EXISTS_SUBSELECT_EXPR && LA171_0<=NOT_IN_SUBSELECT_EXPR)||(LA171_0>=LAST_OPERATOR && LA171_0<=SUBSTITUTION)||LA171_0==NUMBERSETSTAR||(LA171_0>=FIRST_AGGREG && LA171_0<=LAST_AGGREG)||(LA171_0>=INT_TYPE && LA171_0<=NULL_TYPE)||(LA171_0>=STAR && LA171_0<=PLUS)||(LA171_0>=BAND && LA171_0<=BXOR)||(LA171_0>=LT && LA171_0<=GE)||(LA171_0>=MINUS && LA171_0<=MOD)) ) {
                            alt171=1;
                        }


                        switch (alt171) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:542:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3880);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop171;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:543:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3894); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:543:26: ( matchUntilRange )?
                    int alt172=2;
                    int LA172_0 = input.LA(1);

                    if ( ((LA172_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA172_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt172=1;
                    }
                    switch (alt172) {
                        case 1 :
                            // EsperEPL2Ast.g:543:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice3896);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3899);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:543:54: ( exprChoice )?
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( ((LA173_0>=OR_EXPR && LA173_0<=AND_EXPR)||(LA173_0>=EVERY_EXPR && LA173_0<=EVERY_DISTINCT_EXPR)||LA173_0==FOLLOWED_BY_EXPR||(LA173_0>=PATTERN_FILTER_EXPR && LA173_0<=PATTERN_NOT_EXPR)||(LA173_0>=GUARD_EXPR && LA173_0<=OBSERVER_EXPR)||LA173_0==MATCH_UNTIL_EXPR) ) {
                        alt173=1;
                    }
                    switch (alt173) {
                        case 1 :
                            // EsperEPL2Ast.g:543:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice3901);
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
    // EsperEPL2Ast.g:547:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:548:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:548:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3922); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:548:35: ( valueExpr )+
            int cnt175=0;
            loop175:
            do {
                int alt175=2;
                int LA175_0 = input.LA(1);

                if ( ((LA175_0>=IN_SET && LA175_0<=REGEXP)||LA175_0==NOT_EXPR||(LA175_0>=SUM && LA175_0<=AVG)||(LA175_0>=COALESCE && LA175_0<=COUNT)||(LA175_0>=CASE && LA175_0<=CASE2)||(LA175_0>=PREVIOUS && LA175_0<=EXISTS)||(LA175_0>=INSTANCEOF && LA175_0<=CURRENT_TIMESTAMP)||(LA175_0>=EVAL_AND_EXPR && LA175_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA175_0==EVENT_PROP_EXPR||(LA175_0>=CONCAT && LA175_0<=LIB_FUNCTION)||LA175_0==ARRAY_EXPR||(LA175_0>=NOT_IN_SET && LA175_0<=NOT_REGEXP)||(LA175_0>=IN_RANGE && LA175_0<=SUBSELECT_EXPR)||(LA175_0>=EXISTS_SUBSELECT_EXPR && LA175_0<=NOT_IN_SUBSELECT_EXPR)||LA175_0==SUBSTITUTION||(LA175_0>=FIRST_AGGREG && LA175_0<=LAST_AGGREG)||(LA175_0>=INT_TYPE && LA175_0<=NULL_TYPE)||(LA175_0>=STAR && LA175_0<=PLUS)||(LA175_0>=BAND && LA175_0<=BXOR)||(LA175_0>=LT && LA175_0<=GE)||(LA175_0>=MINUS && LA175_0<=MOD)) ) {
                    alt175=1;
                }


                switch (alt175) {
            	case 1 :
            	    // EsperEPL2Ast.g:548:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions3924);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt175 >= 1 ) break loop175;
                        EarlyExitException eee =
                            new EarlyExitException(175, input);
                        throw eee;
                }
                cnt175++;
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
    // EsperEPL2Ast.g:551:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:552:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt179=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt179=1;
                }
                break;
            case OR_EXPR:
                {
                alt179=2;
                }
                break;
            case AND_EXPR:
                {
                alt179=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 179, 0, input);

                throw nvae;
            }

            switch (alt179) {
                case 1 :
                    // EsperEPL2Ast.g:552:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3943); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3945);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3947);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:552:48: ( exprChoice )*
                    loop176:
                    do {
                        int alt176=2;
                        int LA176_0 = input.LA(1);

                        if ( ((LA176_0>=OR_EXPR && LA176_0<=AND_EXPR)||(LA176_0>=EVERY_EXPR && LA176_0<=EVERY_DISTINCT_EXPR)||LA176_0==FOLLOWED_BY_EXPR||(LA176_0>=PATTERN_FILTER_EXPR && LA176_0<=PATTERN_NOT_EXPR)||(LA176_0>=GUARD_EXPR && LA176_0<=OBSERVER_EXPR)||LA176_0==MATCH_UNTIL_EXPR) ) {
                            alt176=1;
                        }


                        switch (alt176) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:552:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3950);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop176;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:553:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp3966); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3968);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3970);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:553:40: ( exprChoice )*
                    loop177:
                    do {
                        int alt177=2;
                        int LA177_0 = input.LA(1);

                        if ( ((LA177_0>=OR_EXPR && LA177_0<=AND_EXPR)||(LA177_0>=EVERY_EXPR && LA177_0<=EVERY_DISTINCT_EXPR)||LA177_0==FOLLOWED_BY_EXPR||(LA177_0>=PATTERN_FILTER_EXPR && LA177_0<=PATTERN_NOT_EXPR)||(LA177_0>=GUARD_EXPR && LA177_0<=OBSERVER_EXPR)||LA177_0==MATCH_UNTIL_EXPR) ) {
                            alt177=1;
                        }


                        switch (alt177) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:553:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3973);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop177;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:554:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3989); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3991);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3993);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:554:41: ( exprChoice )*
                    loop178:
                    do {
                        int alt178=2;
                        int LA178_0 = input.LA(1);

                        if ( ((LA178_0>=OR_EXPR && LA178_0<=AND_EXPR)||(LA178_0>=EVERY_EXPR && LA178_0<=EVERY_DISTINCT_EXPR)||LA178_0==FOLLOWED_BY_EXPR||(LA178_0>=PATTERN_FILTER_EXPR && LA178_0<=PATTERN_NOT_EXPR)||(LA178_0>=GUARD_EXPR && LA178_0<=OBSERVER_EXPR)||LA178_0==MATCH_UNTIL_EXPR) ) {
                            alt178=1;
                        }


                        switch (alt178) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:554:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3996);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop178;
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
    // EsperEPL2Ast.g:557:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:558:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt181=2;
            int LA181_0 = input.LA(1);

            if ( (LA181_0==PATTERN_FILTER_EXPR) ) {
                alt181=1;
            }
            else if ( (LA181_0==OBSERVER_EXPR) ) {
                alt181=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 181, 0, input);

                throw nvae;
            }
            switch (alt181) {
                case 1 :
                    // EsperEPL2Ast.g:558:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4015);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:559:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4027); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4029); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4031); 
                    // EsperEPL2Ast.g:559:39: ( valueExprWithTime )*
                    loop180:
                    do {
                        int alt180=2;
                        int LA180_0 = input.LA(1);

                        if ( ((LA180_0>=IN_SET && LA180_0<=REGEXP)||LA180_0==NOT_EXPR||(LA180_0>=SUM && LA180_0<=AVG)||(LA180_0>=COALESCE && LA180_0<=COUNT)||(LA180_0>=CASE && LA180_0<=CASE2)||LA180_0==LAST||(LA180_0>=PREVIOUS && LA180_0<=EXISTS)||(LA180_0>=LW && LA180_0<=CURRENT_TIMESTAMP)||(LA180_0>=NUMERIC_PARAM_RANGE && LA180_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA180_0>=EVAL_AND_EXPR && LA180_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA180_0==EVENT_PROP_EXPR||(LA180_0>=CONCAT && LA180_0<=LIB_FUNCTION)||(LA180_0>=TIME_PERIOD && LA180_0<=ARRAY_EXPR)||(LA180_0>=NOT_IN_SET && LA180_0<=NOT_REGEXP)||(LA180_0>=IN_RANGE && LA180_0<=SUBSELECT_EXPR)||(LA180_0>=EXISTS_SUBSELECT_EXPR && LA180_0<=NOT_IN_SUBSELECT_EXPR)||(LA180_0>=LAST_OPERATOR && LA180_0<=SUBSTITUTION)||LA180_0==NUMBERSETSTAR||(LA180_0>=FIRST_AGGREG && LA180_0<=LAST_AGGREG)||(LA180_0>=INT_TYPE && LA180_0<=NULL_TYPE)||(LA180_0>=STAR && LA180_0<=PLUS)||(LA180_0>=BAND && LA180_0<=BXOR)||(LA180_0>=LT && LA180_0<=GE)||(LA180_0>=MINUS && LA180_0<=MOD)) ) {
                            alt180=1;
                        }


                        switch (alt180) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:559:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4033);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop180;
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
    // EsperEPL2Ast.g:562:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:563:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:563:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4053); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:563:29: ( IDENT )?
            int alt182=2;
            int LA182_0 = input.LA(1);

            if ( (LA182_0==IDENT) ) {
                alt182=1;
            }
            switch (alt182) {
                case 1 :
                    // EsperEPL2Ast.g:563:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4055); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4058); 
            // EsperEPL2Ast.g:563:48: ( propertyExpression )?
            int alt183=2;
            int LA183_0 = input.LA(1);

            if ( (LA183_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt183=1;
            }
            switch (alt183) {
                case 1 :
                    // EsperEPL2Ast.g:563:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4060);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:563:68: ( valueExpr )*
            loop184:
            do {
                int alt184=2;
                int LA184_0 = input.LA(1);

                if ( ((LA184_0>=IN_SET && LA184_0<=REGEXP)||LA184_0==NOT_EXPR||(LA184_0>=SUM && LA184_0<=AVG)||(LA184_0>=COALESCE && LA184_0<=COUNT)||(LA184_0>=CASE && LA184_0<=CASE2)||(LA184_0>=PREVIOUS && LA184_0<=EXISTS)||(LA184_0>=INSTANCEOF && LA184_0<=CURRENT_TIMESTAMP)||(LA184_0>=EVAL_AND_EXPR && LA184_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA184_0==EVENT_PROP_EXPR||(LA184_0>=CONCAT && LA184_0<=LIB_FUNCTION)||LA184_0==ARRAY_EXPR||(LA184_0>=NOT_IN_SET && LA184_0<=NOT_REGEXP)||(LA184_0>=IN_RANGE && LA184_0<=SUBSELECT_EXPR)||(LA184_0>=EXISTS_SUBSELECT_EXPR && LA184_0<=NOT_IN_SUBSELECT_EXPR)||LA184_0==SUBSTITUTION||(LA184_0>=FIRST_AGGREG && LA184_0<=LAST_AGGREG)||(LA184_0>=INT_TYPE && LA184_0<=NULL_TYPE)||(LA184_0>=STAR && LA184_0<=PLUS)||(LA184_0>=BAND && LA184_0<=BXOR)||(LA184_0>=LT && LA184_0<=GE)||(LA184_0>=MINUS && LA184_0<=MOD)) ) {
                    alt184=1;
                }


                switch (alt184) {
            	case 1 :
            	    // EsperEPL2Ast.g:563:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4064);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop184;
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
    // EsperEPL2Ast.g:566:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:567:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt185=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt185=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt185=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt185=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt185=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 185, 0, input);

                throw nvae;
            }

            switch (alt185) {
                case 1 :
                    // EsperEPL2Ast.g:567:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4082); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4084);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4086);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:568:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4094); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4096);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:569:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4104); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4106);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:570:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4113); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4115);
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
    // EsperEPL2Ast.g:573:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:574:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:578:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:579:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:579:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4144); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4146);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:579:35: ( valueExpr )*
            loop186:
            do {
                int alt186=2;
                int LA186_0 = input.LA(1);

                if ( ((LA186_0>=IN_SET && LA186_0<=REGEXP)||LA186_0==NOT_EXPR||(LA186_0>=SUM && LA186_0<=AVG)||(LA186_0>=COALESCE && LA186_0<=COUNT)||(LA186_0>=CASE && LA186_0<=CASE2)||(LA186_0>=PREVIOUS && LA186_0<=EXISTS)||(LA186_0>=INSTANCEOF && LA186_0<=CURRENT_TIMESTAMP)||(LA186_0>=EVAL_AND_EXPR && LA186_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA186_0==EVENT_PROP_EXPR||(LA186_0>=CONCAT && LA186_0<=LIB_FUNCTION)||LA186_0==ARRAY_EXPR||(LA186_0>=NOT_IN_SET && LA186_0<=NOT_REGEXP)||(LA186_0>=IN_RANGE && LA186_0<=SUBSELECT_EXPR)||(LA186_0>=EXISTS_SUBSELECT_EXPR && LA186_0<=NOT_IN_SUBSELECT_EXPR)||LA186_0==SUBSTITUTION||(LA186_0>=FIRST_AGGREG && LA186_0<=LAST_AGGREG)||(LA186_0>=INT_TYPE && LA186_0<=NULL_TYPE)||(LA186_0>=STAR && LA186_0<=PLUS)||(LA186_0>=BAND && LA186_0<=BXOR)||(LA186_0>=LT && LA186_0<=GE)||(LA186_0>=MINUS && LA186_0<=MOD)) ) {
                    alt186=1;
                }


                switch (alt186) {
            	case 1 :
            	    // EsperEPL2Ast.g:579:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4149);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop186;
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
    // EsperEPL2Ast.g:582:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:583:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt199=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt199=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt199=2;
                }
                break;
            case LT:
                {
                alt199=3;
                }
                break;
            case LE:
                {
                alt199=4;
                }
                break;
            case GT:
                {
                alt199=5;
                }
                break;
            case GE:
                {
                alt199=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt199=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt199=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt199=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt199=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt199=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt199=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 199, 0, input);

                throw nvae;
            }

            switch (alt199) {
                case 1 :
                    // EsperEPL2Ast.g:583:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4165); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4167);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:584:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4174); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4176);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:585:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4183); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4185);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:586:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4192); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4194);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:587:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4201); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4203);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:588:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4210); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4212);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:589:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4219); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:589:41: ( constant[false] | filterIdentifier )
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( ((LA187_0>=INT_TYPE && LA187_0<=NULL_TYPE)) ) {
                        alt187=1;
                    }
                    else if ( (LA187_0==EVENT_FILTER_IDENT) ) {
                        alt187=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 187, 0, input);

                        throw nvae;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:589:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4228);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:589:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4231);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:589:76: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:589:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4235);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:589:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4238);
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
                    // EsperEPL2Ast.g:590:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4252); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:590:45: ( constant[false] | filterIdentifier )
                    int alt189=2;
                    int LA189_0 = input.LA(1);

                    if ( ((LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)) ) {
                        alt189=1;
                    }
                    else if ( (LA189_0==EVENT_FILTER_IDENT) ) {
                        alt189=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 189, 0, input);

                        throw nvae;
                    }
                    switch (alt189) {
                        case 1 :
                            // EsperEPL2Ast.g:590:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4261);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:590:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4264);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:590:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:590:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4268);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:590:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4271);
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
                    // EsperEPL2Ast.g:591:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4285); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:591:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:591:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4294);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:591:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4297);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:591:73: ( constant[false] | filterIdentifier )*
                    loop192:
                    do {
                        int alt192=3;
                        int LA192_0 = input.LA(1);

                        if ( ((LA192_0>=INT_TYPE && LA192_0<=NULL_TYPE)) ) {
                            alt192=1;
                        }
                        else if ( (LA192_0==EVENT_FILTER_IDENT) ) {
                            alt192=2;
                        }


                        switch (alt192) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:591:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4301);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:591:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4304);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop192;
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
                    // EsperEPL2Ast.g:592:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4319); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:592:42: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:592:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4328);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:592:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4331);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:592:77: ( constant[false] | filterIdentifier )*
                    loop194:
                    do {
                        int alt194=3;
                        int LA194_0 = input.LA(1);

                        if ( ((LA194_0>=INT_TYPE && LA194_0<=NULL_TYPE)) ) {
                            alt194=1;
                        }
                        else if ( (LA194_0==EVENT_FILTER_IDENT) ) {
                            alt194=2;
                        }


                        switch (alt194) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:592:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4335);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:592:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4338);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop194;
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
                    // EsperEPL2Ast.g:593:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4353); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:593:27: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:593:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4356);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:593:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4359);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:593:62: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:593:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4363);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:593:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4366);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:594:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4374); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:594:31: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:594:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4377);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:594:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4380);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:594:66: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:594:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4384);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:594:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4387);
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
    // EsperEPL2Ast.g:597:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:598:2: ( constant[false] | filterIdentifier )
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
                    // EsperEPL2Ast.g:598:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4401);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:599:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4407);
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
    // EsperEPL2Ast.g:601:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:602:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:602:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4418); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4420); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4422);
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
    // EsperEPL2Ast.g:605:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:606:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:606:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4441); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4443);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:606:44: ( eventPropertyAtomic )*
            loop201:
            do {
                int alt201=2;
                int LA201_0 = input.LA(1);

                if ( ((LA201_0>=EVENT_PROP_SIMPLE && LA201_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt201=1;
                }


                switch (alt201) {
            	case 1 :
            	    // EsperEPL2Ast.g:606:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4446);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop201;
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
    // EsperEPL2Ast.g:609:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:610:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt202=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt202=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt202=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt202=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt202=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt202=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt202=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 202, 0, input);

                throw nvae;
            }

            switch (alt202) {
                case 1 :
                    // EsperEPL2Ast.g:610:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4465); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4467); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:611:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4474); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4476); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4478); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:612:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4485); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4487); 
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
                    // EsperEPL2Ast.g:613:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4502); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4504); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:614:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4511); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4513); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4515); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:615:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4522); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4524); 
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
    // EsperEPL2Ast.g:618:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:619:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:619:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4551); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4553);
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
    // EsperEPL2Ast.g:622:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:623:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt213=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt213=1;
                }
                break;
            case HOUR_PART:
                {
                alt213=2;
                }
                break;
            case MINUTE_PART:
                {
                alt213=3;
                }
                break;
            case SECOND_PART:
                {
                alt213=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt213=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 213, 0, input);

                throw nvae;
            }

            switch (alt213) {
                case 1 :
                    // EsperEPL2Ast.g:623:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4569);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:623:13: ( hourPart )?
                    int alt203=2;
                    int LA203_0 = input.LA(1);

                    if ( (LA203_0==HOUR_PART) ) {
                        alt203=1;
                    }
                    switch (alt203) {
                        case 1 :
                            // EsperEPL2Ast.g:623:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4572);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:623:25: ( minutePart )?
                    int alt204=2;
                    int LA204_0 = input.LA(1);

                    if ( (LA204_0==MINUTE_PART) ) {
                        alt204=1;
                    }
                    switch (alt204) {
                        case 1 :
                            // EsperEPL2Ast.g:623:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4577);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:623:39: ( secondPart )?
                    int alt205=2;
                    int LA205_0 = input.LA(1);

                    if ( (LA205_0==SECOND_PART) ) {
                        alt205=1;
                    }
                    switch (alt205) {
                        case 1 :
                            // EsperEPL2Ast.g:623:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4582);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:623:53: ( millisecondPart )?
                    int alt206=2;
                    int LA206_0 = input.LA(1);

                    if ( (LA206_0==MILLISECOND_PART) ) {
                        alt206=1;
                    }
                    switch (alt206) {
                        case 1 :
                            // EsperEPL2Ast.g:623:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4587);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:624:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4594);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:624:13: ( minutePart )?
                    int alt207=2;
                    int LA207_0 = input.LA(1);

                    if ( (LA207_0==MINUTE_PART) ) {
                        alt207=1;
                    }
                    switch (alt207) {
                        case 1 :
                            // EsperEPL2Ast.g:624:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4597);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:624:27: ( secondPart )?
                    int alt208=2;
                    int LA208_0 = input.LA(1);

                    if ( (LA208_0==SECOND_PART) ) {
                        alt208=1;
                    }
                    switch (alt208) {
                        case 1 :
                            // EsperEPL2Ast.g:624:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4602);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:624:41: ( millisecondPart )?
                    int alt209=2;
                    int LA209_0 = input.LA(1);

                    if ( (LA209_0==MILLISECOND_PART) ) {
                        alt209=1;
                    }
                    switch (alt209) {
                        case 1 :
                            // EsperEPL2Ast.g:624:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4607);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:625:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4614);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:625:15: ( secondPart )?
                    int alt210=2;
                    int LA210_0 = input.LA(1);

                    if ( (LA210_0==SECOND_PART) ) {
                        alt210=1;
                    }
                    switch (alt210) {
                        case 1 :
                            // EsperEPL2Ast.g:625:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4617);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:625:29: ( millisecondPart )?
                    int alt211=2;
                    int LA211_0 = input.LA(1);

                    if ( (LA211_0==MILLISECOND_PART) ) {
                        alt211=1;
                    }
                    switch (alt211) {
                        case 1 :
                            // EsperEPL2Ast.g:625:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4622);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:626:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4629);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:626:15: ( millisecondPart )?
                    int alt212=2;
                    int LA212_0 = input.LA(1);

                    if ( (LA212_0==MILLISECOND_PART) ) {
                        alt212=1;
                    }
                    switch (alt212) {
                        case 1 :
                            // EsperEPL2Ast.g:626:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4632);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:627:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4639);
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
    // EsperEPL2Ast.g:630:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:631:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:631:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4653); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4655);
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
    // EsperEPL2Ast.g:634:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:635:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:635:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4670); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4672);
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
    // EsperEPL2Ast.g:638:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:639:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:639:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4687); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4689);
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
    // EsperEPL2Ast.g:642:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:643:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:643:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4704); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4706);
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
    // EsperEPL2Ast.g:646:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:647:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:647:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4721); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4723);
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
    // EsperEPL2Ast.g:650:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:651:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:651:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4738); 
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
    // EsperEPL2Ast.g:654:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:655:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt214=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt214=1;
                }
                break;
            case LONG_TYPE:
                {
                alt214=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt214=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt214=4;
                }
                break;
            case STRING_TYPE:
                {
                alt214=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt214=6;
                }
                break;
            case NULL_TYPE:
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
                    // EsperEPL2Ast.g:655:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4754); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:656:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4763); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:657:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4772); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:658:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4781); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:659:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4797); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:660:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4813); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:661:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4826); 
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
    // EsperEPL2Ast.g:664:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:665:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x000007F0E0000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x000007F0E0000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L,0x000007F060000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x000007F060000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020000020000L,0x0000000420081400L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000046000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000046000L});
    public static final BitSet FOLLOW_IDENT_in_onExpr270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000046000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr277 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr281 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr284 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000018000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr287 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr294 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr316 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr318 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000800000000L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr320 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000800000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr323 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x0400000800000000L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr326 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr343 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr345 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr348 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr368 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x10000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr371 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000C000180L,0x0000000000020000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr373 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000C000180L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr376 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000C000100L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr380 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000008000100L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr383 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr386 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr403 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr405 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x10000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr407 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr409 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput426 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput428 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr446 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr448 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x0400000800000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr451 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L,0x0400000800000000L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment470 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment472 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment474 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom488 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom490 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom493 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr511 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr513 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000004000800L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr516 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000004000800L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr520 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000004000800L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr523 = new BitSet(new long[]{0x8000000000000000L,0x0000000000000001L,0x0000000000000012L,0x0000000004000800L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr537 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr540 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr569 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr580 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert598 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert600 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList619 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x1000000000040000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList622 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x1000000000040000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList641 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList643 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList646 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement661 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement663 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement665 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement679 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement689 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement709 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement713 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement735 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement738 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr774 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr776 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr778 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr781 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr799 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020000020000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr805 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr810 = new BitSet(new long[]{0x0000000000000002L,0x0000001200000000L,0x000001700C000180L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr815 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000001700C000180L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr822 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x000001700C000100L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr830 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000017008000100L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr837 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000017008000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr844 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr851 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr875 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr877 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr886 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr889 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol908 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol910 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol913 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause931 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause933 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x10000000000C0000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause946 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause960 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause963 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000003D00000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause966 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000003D00000L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause986 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause988 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause995 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0004200000000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1001 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0004200000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1007 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x0004200000000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1013 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1019 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1025 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1043 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1045 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1062 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1064 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1066 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1068 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1072 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1087 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1089 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1107 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1109 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1126 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1128 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1130 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1150 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1152 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1175 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1177 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1179 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1197 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1199 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0002400000000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1214 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1234 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1236 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1238 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1267 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1269 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000006L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1273 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1285 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1309 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1326 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1328 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1330 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1347 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x10000000000C0000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1350 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x10000000000C0000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1366 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1376 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1378 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1381 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1395 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1397 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1400 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1419 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1433 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1435 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1438 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1442 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1445 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1460 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1462 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1465 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1469 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1472 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1487 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1489 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1492 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1496 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1499 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1514 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1516 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1519 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1523 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1526 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1547 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1550 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0400000000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1554 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0400000000000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1558 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0400000000000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1562 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0400000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1566 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1571 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1576 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_streamExpression1580 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1604 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1606 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1609 = new BitSet(new long[]{0x0000000037CC23C8L,0x0010000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1611 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1615 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1635 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1637 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1658 = new BitSet(new long[]{0x0000000000000000L,0x01C0000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1661 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1664 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1668 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1700 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1702 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1705 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1719 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1721 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1745 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1747 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1764 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1766 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1768 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1776 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1797 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1799 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1801 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1804 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1818 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1821 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1838 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1840 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1842 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1845 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1867 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1869 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1887 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1889 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1892 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1910 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1912 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1915 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1935 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1937 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1939 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1962 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1964 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1982 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1984 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x040000F000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1996 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1998 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2015 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2017 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2028 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2043 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2045 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2056 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2071 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2073 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2084 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0400000000046000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2086 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2105 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2108 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x060000F000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2110 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x060000F000000000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2114 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2116 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2120 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2123 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2141 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2143 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2145 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2147 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2149 = new BitSet(new long[]{0x0020000037CC23C0L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2151 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2153 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2170 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2172 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2185 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2187 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2200 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2202 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2214 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2216 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2238 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2263 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011FL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2272 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2277 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2303 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2305 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2307 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2310 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2324 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2326 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2328 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2331 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2345 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2347 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2349 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2361 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2363 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2365 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2377 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2379 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2381 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011FL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2390 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2395 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2408 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2410 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2412 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011FL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2421 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2426 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2439 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2441 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2465 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2477 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2484 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2527 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2537 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2555 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2577 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2584 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2592 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2594 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2596 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2609 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2615 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2635 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2637 = new BitSet(new long[]{0x0000000000000008L,0x0000140000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2655 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2668 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2681 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2700 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x000007F000000100L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2703 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x000007F000000100L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2706 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L,0x000007F000000100L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2710 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2713 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2716 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2737 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2740 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2743 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2746 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2765 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2768 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2771 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2774 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2793 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2796 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2802 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2823 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr2825 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2844 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2846 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2865 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2867 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2886 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2888 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2890 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2902 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2904 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2906 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2925 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2927 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2943 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2945 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2948 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2966 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2968 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000010L,0x0400000000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2971 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2976 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr2980 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2983 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3003 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3006 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3019 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3022 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3042 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3044 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_inExpr3046 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3052 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0xC00007F30000011DL,0x00000000000EF313L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3055 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0xC00007F30000011DL,0x00000000000EF313L});
    public static final BitSet FOLLOW_set_in_inExpr3059 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3074 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3076 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_inExpr3078 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3084 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0xC00007F30000011DL,0x00000000000EF313L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3087 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0xC00007F30000011DL,0x00000000000EF313L});
    public static final BitSet FOLLOW_set_in_inExpr3091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3106 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3108 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_inExpr3110 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3116 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3118 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_inExpr3120 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3135 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3137 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x2000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_inExpr3139 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3145 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3147 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_inExpr3149 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3172 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3174 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3176 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3178 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3191 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3193 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3196 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3218 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3220 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3223 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3236 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3238 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3240 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3243 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3262 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3264 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3266 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3277 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3279 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3281 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3300 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3303 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3307 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3318 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3321 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3325 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3336 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3340 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3344 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3361 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3365 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3376 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3379 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3383 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3394 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3397 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3401 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3415 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3419 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3430 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3433 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3437 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3449 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3451 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3453 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3456 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3473 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3475 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3488 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3492 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3494 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3507 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3509 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3511 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3514 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3528 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3530 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3532 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3544 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3546 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3578 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3581 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3602 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3604 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3606 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3618 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3620 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3622 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3634 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3636 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3638 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3649 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3651 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3653 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3665 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3667 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3680 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3682 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3684 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3695 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3697 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3699 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3710 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3712 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3714 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3726 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3728 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3730 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3733 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3754 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3757 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3761 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3764 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3769 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule3789 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000020200000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3793 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3812 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3822 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3824 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3838 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice3840 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3842 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3856 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3858 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3876 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3878 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3880 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3894 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice3896 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3899 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3901 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3922 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions3924 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3943 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3945 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3947 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3950 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp3966 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3968 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3970 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3973 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3989 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3991 = new BitSet(new long[]{0x000000000000D800L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3993 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3996 = new BitSet(new long[]{0x000000000000D808L,0x0003400000000000L,0x000000000000000CL,0x0000000000200000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4027 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4029 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4031 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4033 = new BitSet(new long[]{0x0020000037CC23C8L,0x00003C0000000F70L,0xC1E0D8002000FC00L,0x800007F3100001DDL,0x00000000000EF303L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4053 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4055 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4058 = new BitSet(new long[]{0x0000000037CC23C8L,0x0010000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4060 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4064 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4082 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4086 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4094 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4096 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4106 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4113 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4115 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4144 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4146 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4149 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0xC1E098002000FC00L,0x800007F30000011DL,0x00000000000EF303L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4165 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4167 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4174 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4176 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4183 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4185 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4192 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4194 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4201 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4203 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4210 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4212 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4221 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4228 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4231 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4235 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4241 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4252 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4254 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4261 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4264 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4268 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4271 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4274 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4285 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4287 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4294 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4297 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4301 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4304 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4308 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4319 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4321 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4328 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4331 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4335 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4338 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x400007F000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4342 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4353 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4356 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4359 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4363 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4366 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4374 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4377 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4380 = new BitSet(new long[]{0x0000000000000000L,0x0200000000000000L,0x0000000000000000L,0x000007F000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4384 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4387 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4401 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4407 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4418 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4422 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4441 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4443 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000FC0000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4446 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000FC0000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4465 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4467 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4474 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4476 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4478 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4487 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4489 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4502 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4504 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4511 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4513 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4515 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4522 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4524 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4526 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4551 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4553 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4569 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x001E000000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4572 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x001C000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4577 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4582 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4587 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4594 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x001C000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4597 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4602 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4607 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4614 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0018000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4617 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4622 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4629 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4653 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4655 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4670 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4672 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4687 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4689 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4704 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4706 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4721 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4723 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4738 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4754 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4772 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4826 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}