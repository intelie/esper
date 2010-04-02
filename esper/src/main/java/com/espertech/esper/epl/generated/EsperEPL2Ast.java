// $ANTLR 3.1.1 EsperEPL2Ast.g 2010-04-01 18:20:56

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "SCHEMA", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "INDEX", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_INDEX_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_STREAM", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_UPDATE_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_COL_TYPE_LIST", "CREATE_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "CREATE_SCHEMA_EXPR", "CREATE_SCHEMA_EXPR_QUAL", "CREATE_SCHEMA_EXPR_INH", "VARIANT_LIST", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "STAR", "BOR", "PLUS", "QUESTION", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=169;
    public static final int FLOAT_SUFFIX=324;
    public static final int STAR=267;
    public static final int NUMERIC_PARAM_LIST=109;
    public static final int ISTREAM=60;
    public static final int MOD=285;
    public static final int OUTERJOIN_EXPR=151;
    public static final int CREATE_COL_TYPE_LIST=224;
    public static final int BSR=306;
    public static final int LIB_FUNCTION=175;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=95;
    public static final int FULL_OUTERJOIN_EXPR=155;
    public static final int MATCHREC_PATTERN_CONCAT=249;
    public static final int RPAREN=264;
    public static final int LNOT=295;
    public static final int INC=299;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=272;
    public static final int BSR_ASSIGN=307;
    public static final int CAST_EXPR=204;
    public static final int MATCHES=106;
    public static final int STREAM_EXPR=150;
    public static final int TIMEPERIOD_SECONDS=92;
    public static final int NOT_EQUAL=277;
    public static final int METADATASQL=68;
    public static final int EVENT_FILTER_PROPERTY_EXPR=118;
    public static final int LAST_AGGREG=231;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=112;
    public static final int FOLLOWED_BY=289;
    public static final int HOUR_PART=180;
    public static final int RBRACK=266;
    public static final int MATCHREC_PATTERN_NESTED=251;
    public static final int MATCH_UNTIL_RANGE_CLOSED=222;
    public static final int GE=281;
    public static final int METHOD_JOIN_EXPR=218;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=117;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=116;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=300;
    public static final int EVENT_FILTER_NOT_IN=128;
    public static final int INSERTINTO_STREAM_NAME=192;
    public static final int NUM_DOUBLE=245;
    public static final int UNARY_MINUS=176;
    public static final int TIMEPERIOD_MILLISEC=93;
    public static final int LCURLY=286;
    public static final int RETAINUNION=64;
    public static final int DBWHERE_CLAUSE=190;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=126;
    public static final int GROUP=44;
    public static final int EMAILAT=315;
    public static final int WS=316;
    public static final int SUBSELECT_GROUP_EXPR=196;
    public static final int ON_SELECT_INSERT_EXPR=213;
    public static final int ESCAPECHAR=290;
    public static final int EXPRCOL=173;
    public static final int SL_COMMENT=317;
    public static final int NULL_TYPE=244;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=220;
    public static final int GT=279;
    public static final int BNOT=296;
    public static final int WHERE_EXPR=137;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=152;
    public static final int LAND=313;
    public static final int NOT_REGEXP=187;
    public static final int MATCH_UNTIL_EXPR=219;
    public static final int EVENT_PROP_EXPR=159;
    public static final int LBRACK=265;
    public static final int VIEW_EXPR=134;
    public static final int ANNOTATION=227;
    public static final int LONG_TYPE=239;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=119;
    public static final int MATCHREC_PATTERN=247;
    public static final int TIMEPERIOD_SEC=90;
    public static final int ON_SELECT_EXPR=211;
    public static final int TICKED_STRING_LITERAL=291;
    public static final int MINUTE_PART=181;
    public static final int PATTERN_NOT_EXPR=115;
    public static final int SUM=18;
    public static final int SQL_NE=276;
    public static final int HexDigit=322;
    public static final int UPDATE_EXPR=232;
    public static final int LPAREN=263;
    public static final int IN_SUBSELECT_EXPR=198;
    public static final int AT=82;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=96;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=253;
    public static final int NOT_IN_RANGE=194;
    public static final int OFFSET=100;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=69;
    public static final int SECOND_PART=182;
    public static final int MATCH_RECOGNIZE=102;
    public static final int IDENT=260;
    public static final int DATABASE_JOIN_EXPR=136;
    public static final int BXOR=275;
    public static final int PLUS=269;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=84;
    public static final int CREATE_SCHEMA_EXPR=234;
    public static final int EXISTS=71;
    public static final int EVENT_PROP_INDEXED=162;
    public static final int CREATE_INDEX_EXPR=205;
    public static final int TIMEPERIOD_MILLISECOND=94;
    public static final int EVAL_NOTEQUALS_EXPR=143;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=221;
    public static final int CREATE_VARIABLE_EXPR=217;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=254;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=113;
    public static final int RIGHT_OUTERJOIN_EXPR=154;
    public static final int NUMBERSETSTAR=226;
    public static final int LAST_OPERATOR=201;
    public static final int PATTERN_FILTER_EXPR=114;
    public static final int EVAL_AND_EXPR=140;
    public static final int LEFT_OUTERJOIN_EXPR=153;
    public static final int EPL_EXPR=246;
    public static final int GROUP_BY_EXPR=156;
    public static final int SET=79;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=74;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=160;
    public static final int MINUS=283;
    public static final int SEMI=314;
    public static final int STAR_ASSIGN=302;
    public static final int VARIANT_LIST=237;
    public static final int FIRST_AGGREG=230;
    public static final int COLON=271;
    public static final int EVAL_EQUALS_GROUP_EXPR=144;
    public static final int BAND_ASSIGN=312;
    public static final int SCHEMA=62;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=170;
    public static final int VALUE_NULL=98;
    public static final int NOT_IN_SET=184;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=163;
    public static final int SL=308;
    public static final int NOT_IN_SUBSELECT_EXPR=199;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=132;
    public static final int SR=304;
    public static final int RCURLY=287;
    public static final int PLUS_ASSIGN=298;
    public static final int EXISTS_SUBSELECT_EXPR=197;
    public static final int DAY_PART=179;
    public static final int EVENT_FILTER_IN=127;
    public static final int DIV=284;
    public static final int OBJECT_PARAM_ORDERED_EXPR=111;
    public static final int OctalEscape=321;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=183;
    public static final int PRIOR=70;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=99;
    public static final int SELECTION_EXPR=147;
    public static final int LOR=282;
    public static final int CAST=75;
    public static final int LW=73;
    public static final int WILDCARD_SELECT=191;
    public static final int EXPONENT=323;
    public static final int LT=278;
    public static final int PATTERN_INCL_EXPR=135;
    public static final int ORDER_BY_EXPR=157;
    public static final int BOOL_TYPE=243;
    public static final int MOD_ASSIGN=303;
    public static final int ANNOTATION_ARRAY=228;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=200;
    public static final int EQUALS=261;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=65;
    public static final int DIV_ASSIGN=297;
    public static final int SL_ASSIGN=309;
    public static final int PATTERN=66;
    public static final int SQL=67;
    public static final int FULL=40;
    public static final int WEEKDAY=72;
    public static final int MATCHREC_AFTER_SKIP=252;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=212;
    public static final int ARRAY_EXPR=178;
    public static final int CREATE_COL_TYPE=225;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=97;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=145;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=129;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=91;
    public static final int FLOAT_TYPE=240;
    public static final int SUBSELECT_EXPR=195;
    public static final int ANNOTATION_VALUE=229;
    public static final int CONCAT=174;
    public static final int NUMERIC_PARAM_RANGE=108;
    public static final int CLASS_IDENT=131;
    public static final int MATCHREC_PATTERN_ALTER=250;
    public static final int ON_EXPR=208;
    public static final int CREATE_WINDOW_EXPR=206;
    public static final int PROPERTY_SELECTION_STREAM=121;
    public static final int ON_DELETE_EXPR=210;
    public static final int ON=41;
    public static final int NUM_LONG=292;
    public static final int TIME_PERIOD=177;
    public static final int DOUBLE_TYPE=241;
    public static final int DELETE=77;
    public static final int INT_TYPE=238;
    public static final int MATCHREC_PARTITION=258;
    public static final int EVAL_BITWISE_EXPR=139;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=158;
    public static final int TIMEPERIOD_HOURS=87;
    public static final int VARIABLE=80;
    public static final int SUBSTITUTION=203;
    public static final int UNTIL=81;
    public static final int STRING_TYPE=242;
    public static final int ON_SET_EXPR=216;
    public static final int MATCHREC_DEFINE_ITEM=255;
    public static final int NUM_INT=288;
    public static final int STDDEV=24;
    public static final int CREATE_SCHEMA_EXPR_INH=236;
    public static final int ON_EXPR_FROM=215;
    public static final int NUM_FLOAT=293;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=120;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=319;
    public static final int WEEKDAY_OPERATOR=202;
    public static final int WHERE=16;
    public static final int DEC=301;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=110;
    public static final int BXOR_ASSIGN=310;
    public static final int AFTER_LIMIT_EXPR=168;
    public static final int ORDER=56;
    public static final int SNAPSHOT=78;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=165;
    public static final int EVENT_FILTER_PARAM=124;
    public static final int IRSTREAM=61;
    public static final int UPDATE=101;
    public static final int MAX=20;
    public static final int ON_STREAM=209;
    public static final int DEFINE=104;
    public static final int TIMEPERIOD_DAYS=85;
    public static final int EVENT_FILTER_RANGE=125;
    public static final int INDEX=83;
    public static final int ML_COMMENT=318;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=164;
    public static final int BOR_ASSIGN=311;
    public static final int COMMA=259;
    public static final int WHEN_LIMIT_EXPR=171;
    public static final int PARTITION=105;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=167;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=86;
    public static final int MATCHREC_MEASURE_ITEM=257;
    public static final int BOR=268;
    public static final int EQUAL=294;
    public static final int EVENT_FILTER_NOT_BETWEEN=130;
    public static final int IN_RANGE=193;
    public static final int DOT=262;
    public static final int CURRENT_TIMESTAMP=76;
    public static final int MATCHREC_MEASURES=256;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=122;
    public static final int INSERTINTO_EXPR=172;
    public static final int HAVING_EXPR=138;
    public static final int UNIDIRECTIONAL=63;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=223;
    public static final int EVAL_EQUALS_EXPR=142;
    public static final int TIMEPERIOD_MINUTES=89;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=186;
    public static final int EVENT_LIMIT_EXPR=166;
    public static final int NOT_BETWEEN=185;
    public static final int TIMEPERIOD_MINUTE=88;
    public static final int EVAL_OR_EXPR=141;
    public static final int ON_SELECT_INSERT_OUTPUT=214;
    public static final int AFTER=107;
    public static final int MEASURES=103;
    public static final int MATCHREC_PATTERN_ATOM=248;
    public static final int BAND=274;
    public static final int QUOTED_STRING_LITERAL=273;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=270;
    public static final int OBSERVER_EXPR=133;
    public static final int EVENT_FILTER_IDENT=123;
    public static final int CREATE_SCHEMA_EXPR_QUAL=235;
    public static final int EVENT_PROP_MAPPED=161;
    public static final int UnicodeEscape=320;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=188;
    public static final int SELECTION_ELEMENT_EXPR=148;
    public static final int CREATE_WINDOW_SELECT_EXPR=207;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=233;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=149;
    public static final int SR_ASSIGN=305;
    public static final int DBFROM_CLAUSE=189;
    public static final int LE=280;
    public static final int EVAL_IDENT=146;

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
    // EsperEPL2Ast.g:79:1: eplExpressionRule : ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:80:2: ( ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr ) )
            // EsperEPL2Ast.g:80:4: ( selectExpr | createWindowExpr | createIndexExpr | createVariableExpr | createSchemaExpr | onExpr | updateExpr )
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
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr268); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onStreamExpr_in_onExpr270);
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
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr275);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:85:19: onUpdateExpr
                    {
                    pushFollow(FOLLOW_onUpdateExpr_in_onExpr279);
                    onUpdateExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:85:34: onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )?
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr283);
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
                            	    pushFollow(FOLLOW_onSelectInsertExpr_in_onExpr286);
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
                                    pushFollow(FOLLOW_onSelectInsertOutput_in_onExpr289);
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
                    pushFollow(FOLLOW_onSetExpr_in_onExpr296);
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
            s=(CommonTree)match(input,ON_STREAM,FOLLOW_ON_STREAM_in_onStreamExpr318); 

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
                    pushFollow(FOLLOW_eventFilterExpr_in_onStreamExpr321);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:90:37: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onStreamExpr325);
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
                    match(input,IDENT,FOLLOW_IDENT_in_onStreamExpr328); 

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
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr347); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr349); 
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
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr351); 

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
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr354);
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
                    pushFollow(FOLLOW_whereClause_in_updateExpr357);
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
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr374); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr376);
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
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr379);
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
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr399); 

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
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr401);
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
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr404); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr407);
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
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr409);
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
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr412);
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
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr416);
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
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr419);
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
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr422);
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
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr425);
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
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr445); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr447);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr449);
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
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr451);
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
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput468); 

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
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr488); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr490);
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
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr493);
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
                    pushFollow(FOLLOW_whereClause_in_onSetExpr497);
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
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr512); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr514);
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
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr516);
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
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr519);
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
    // EsperEPL2Ast.g:121:1: onSetAssignment : ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:122:2: ( ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr ) )
            // EsperEPL2Ast.g:122:4: ^( ON_SET_EXPR_ITEM eventPropertyExpr[false] valueExpr )
            {
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment534); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_onSetAssignment536);
            eventPropertyExpr(false);

            state._fsp--;

            pushFollow(FOLLOW_valueExpr_in_onSetAssignment539);
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
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom553); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom555); 
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
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom558); 

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
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr576); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr578); 
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
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr581);
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
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr585); 

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
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr588); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:131:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt35=2;
            int LA35_0 = input.LA(1);

            if ( (LA35_0==CLASS_IDENT||LA35_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt35=1;
            }
            else if ( (LA35_0==CREATE_COL_TYPE_LIST) ) {
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
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr602);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr605); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:134:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:134:12: ( createColTypeList )
                    // EsperEPL2Ast.g:134:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr634);
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
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr645);
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
    // EsperEPL2Ast.g:140:1: createIndexExpr : ^(i= CREATE_INDEX_EXPR IDENT IDENT exprCol ) ;
    public final void createIndexExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:141:2: ( ^(i= CREATE_INDEX_EXPR IDENT IDENT exprCol ) )
            // EsperEPL2Ast.g:141:4: ^(i= CREATE_INDEX_EXPR IDENT IDENT exprCol )
            {
            i=(CommonTree)match(input,CREATE_INDEX_EXPR,FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr665); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr667); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr669); 
            pushFollow(FOLLOW_exprCol_in_createIndexExpr671);
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
    // EsperEPL2Ast.g:144:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:145:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:145:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert686); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:145:13: ( valueExpr )?
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( ((LA37_0>=IN_SET && LA37_0<=REGEXP)||LA37_0==NOT_EXPR||(LA37_0>=SUM && LA37_0<=AVG)||(LA37_0>=COALESCE && LA37_0<=COUNT)||(LA37_0>=CASE && LA37_0<=CASE2)||(LA37_0>=PREVIOUS && LA37_0<=EXISTS)||(LA37_0>=INSTANCEOF && LA37_0<=CURRENT_TIMESTAMP)||(LA37_0>=EVAL_AND_EXPR && LA37_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA37_0==EVENT_PROP_EXPR||(LA37_0>=CONCAT && LA37_0<=LIB_FUNCTION)||LA37_0==ARRAY_EXPR||(LA37_0>=NOT_IN_SET && LA37_0<=NOT_REGEXP)||(LA37_0>=IN_RANGE && LA37_0<=SUBSELECT_EXPR)||(LA37_0>=EXISTS_SUBSELECT_EXPR && LA37_0<=NOT_IN_SUBSELECT_EXPR)||LA37_0==SUBSTITUTION||(LA37_0>=FIRST_AGGREG && LA37_0<=LAST_AGGREG)||(LA37_0>=INT_TYPE && LA37_0<=NULL_TYPE)||(LA37_0>=STAR && LA37_0<=PLUS)||(LA37_0>=BAND && LA37_0<=BXOR)||(LA37_0>=LT && LA37_0<=GE)||(LA37_0>=MINUS && LA37_0<=MOD)) ) {
                    alt37=1;
                }
                switch (alt37) {
                    case 1 :
                        // EsperEPL2Ast.g:145:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert688);
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
    // EsperEPL2Ast.g:148:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:149:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:149:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList705); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList707);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:149:61: ( createSelectionListElement )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==SELECTION_ELEMENT_EXPR||LA38_0==WILDCARD_SELECT) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // EsperEPL2Ast.g:149:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList710);
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
    // EsperEPL2Ast.g:152:1: createColTypeList : ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:153:2: ( ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:153:4: ^( CREATE_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_COL_TYPE_LIST,FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList729); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList731);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:153:52: ( createColTypeListElement )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==CREATE_COL_TYPE) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // EsperEPL2Ast.g:153:53: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList734);
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
    // EsperEPL2Ast.g:156:1: createColTypeListElement : ^( CREATE_COL_TYPE IDENT CLASS_IDENT ( LBRACK )? ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:157:2: ( ^( CREATE_COL_TYPE IDENT CLASS_IDENT ( LBRACK )? ) )
            // EsperEPL2Ast.g:157:4: ^( CREATE_COL_TYPE IDENT CLASS_IDENT ( LBRACK )? )
            {
            match(input,CREATE_COL_TYPE,FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement749); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement751); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement753); 
            // EsperEPL2Ast.g:157:40: ( LBRACK )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( (LA40_0==LBRACK) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:157:40: LBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_createColTypeListElement755); 

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
    // EsperEPL2Ast.g:160:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:161:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==WILDCARD_SELECT) ) {
                alt43=1;
            }
            else if ( (LA43_0==SELECTION_ELEMENT_EXPR) ) {
                alt43=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 43, 0, input);

                throw nvae;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:161:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement770); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:162:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement780); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:162:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt42=2;
                    int LA42_0 = input.LA(1);

                    if ( (LA42_0==EVENT_PROP_EXPR) ) {
                        alt42=1;
                    }
                    else if ( ((LA42_0>=INT_TYPE && LA42_0<=NULL_TYPE)) ) {
                        alt42=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 42, 0, input);

                        throw nvae;
                    }
                    switch (alt42) {
                        case 1 :
                            // EsperEPL2Ast.g:163:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:163:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:163:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement800);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:163:41: ( IDENT )?
                            int alt41=2;
                            int LA41_0 = input.LA(1);

                            if ( (LA41_0==IDENT) ) {
                                alt41=1;
                            }
                            switch (alt41) {
                                case 1 :
                                    // EsperEPL2Ast.g:163:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement804); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:164:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:164:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:164:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement826);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement829); 

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
    // EsperEPL2Ast.g:168:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:169:2: ( ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:169:4: ^(i= CREATE_VARIABLE_EXPR CLASS_IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr865); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createVariableExpr867); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr869); 
            // EsperEPL2Ast.g:169:47: ( valueExpr )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( ((LA44_0>=IN_SET && LA44_0<=REGEXP)||LA44_0==NOT_EXPR||(LA44_0>=SUM && LA44_0<=AVG)||(LA44_0>=COALESCE && LA44_0<=COUNT)||(LA44_0>=CASE && LA44_0<=CASE2)||(LA44_0>=PREVIOUS && LA44_0<=EXISTS)||(LA44_0>=INSTANCEOF && LA44_0<=CURRENT_TIMESTAMP)||(LA44_0>=EVAL_AND_EXPR && LA44_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA44_0==EVENT_PROP_EXPR||(LA44_0>=CONCAT && LA44_0<=LIB_FUNCTION)||LA44_0==ARRAY_EXPR||(LA44_0>=NOT_IN_SET && LA44_0<=NOT_REGEXP)||(LA44_0>=IN_RANGE && LA44_0<=SUBSELECT_EXPR)||(LA44_0>=EXISTS_SUBSELECT_EXPR && LA44_0<=NOT_IN_SUBSELECT_EXPR)||LA44_0==SUBSTITUTION||(LA44_0>=FIRST_AGGREG && LA44_0<=LAST_AGGREG)||(LA44_0>=INT_TYPE && LA44_0<=NULL_TYPE)||(LA44_0>=STAR && LA44_0<=PLUS)||(LA44_0>=BAND && LA44_0<=BXOR)||(LA44_0>=LT && LA44_0<=GE)||(LA44_0>=MINUS && LA44_0<=MOD)) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:169:48: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr872);
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
    // EsperEPL2Ast.g:172:1: createSchemaExpr : ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? ) ;
    public final void createSchemaExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:173:2: ( ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? ) )
            // EsperEPL2Ast.g:173:4: ^(s= CREATE_SCHEMA_EXPR IDENT ( variantList | ( createColTypeList )? ) ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )? ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )? )
            {
            s=(CommonTree)match(input,CREATE_SCHEMA_EXPR,FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr892); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr894); 
            // EsperEPL2Ast.g:173:33: ( variantList | ( createColTypeList )? )
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==VARIANT_LIST) ) {
                alt46=1;
            }
            else if ( (LA46_0==UP||LA46_0==CREATE_COL_TYPE_LIST||(LA46_0>=CREATE_SCHEMA_EXPR_QUAL && LA46_0<=CREATE_SCHEMA_EXPR_INH)) ) {
                alt46=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }
            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:173:34: variantList
                    {
                    pushFollow(FOLLOW_variantList_in_createSchemaExpr897);
                    variantList();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:173:46: ( createColTypeList )?
                    {
                    // EsperEPL2Ast.g:173:46: ( createColTypeList )?
                    int alt45=2;
                    int LA45_0 = input.LA(1);

                    if ( (LA45_0==CREATE_COL_TYPE_LIST) ) {
                        alt45=1;
                    }
                    switch (alt45) {
                        case 1 :
                            // EsperEPL2Ast.g:173:46: createColTypeList
                            {
                            pushFollow(FOLLOW_createColTypeList_in_createSchemaExpr899);
                            createColTypeList();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:174:5: ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==CREATE_SCHEMA_EXPR_QUAL) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:174:6: ^( CREATE_SCHEMA_EXPR_QUAL IDENT )
                    {
                    match(input,CREATE_SCHEMA_EXPR_QUAL,FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr910); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr912); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:175:5: ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==CREATE_SCHEMA_EXPR_INH) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:175:6: ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol )
                    {
                    match(input,CREATE_SCHEMA_EXPR_INH,FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr923); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr925); 
                    pushFollow(FOLLOW_exprCol_in_createSchemaExpr927);
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
    // EsperEPL2Ast.g:178:1: variantList : ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) ;
    public final void variantList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:179:2: ( ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ ) )
            // EsperEPL2Ast.g:179:4: ^( VARIANT_LIST ( STAR | CLASS_IDENT )+ )
            {
            match(input,VARIANT_LIST,FOLLOW_VARIANT_LIST_in_variantList948); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:179:19: ( STAR | CLASS_IDENT )+
            int cnt49=0;
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( (LA49_0==CLASS_IDENT||LA49_0==STAR) ) {
                    alt49=1;
                }


                switch (alt49) {
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
            	    if ( cnt49 >= 1 ) break loop49;
                        EarlyExitException eee =
                            new EarlyExitException(49, input);
                        throw eee;
                }
                cnt49++;
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
    // EsperEPL2Ast.g:182:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:183:2: ( ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:183:4: ( insertIntoExpr )? selectClause fromClause ( matchRecogClause )? ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:183:4: ( insertIntoExpr )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==INSERTINTO_EXPR) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // EsperEPL2Ast.g:183:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr968);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr974);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr979);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:186:3: ( matchRecogClause )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==MATCH_RECOGNIZE) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:186:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr984);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:187:3: ( whereClause[true] )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==WHERE_EXPR) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // EsperEPL2Ast.g:187:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr991);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:188:3: ( groupByClause )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==GROUP_BY_EXPR) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:188:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr999);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:189:3: ( havingClause )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==HAVING_EXPR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // EsperEPL2Ast.g:189:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr1006);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:190:3: ( outputLimitExpr )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( ((LA55_0>=EVENT_LIMIT_EXPR && LA55_0<=CRONTAB_LIMIT_EXPR)||LA55_0==WHEN_LIMIT_EXPR) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // EsperEPL2Ast.g:190:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr1013);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:191:3: ( orderByClause )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( (LA56_0==ORDER_BY_EXPR) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:191:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr1020);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:192:3: ( rowLimitClause )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ROW_LIMIT_EXPR) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:192:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr1027);
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
    // EsperEPL2Ast.g:195:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( exprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:196:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( exprCol )? ) )
            // EsperEPL2Ast.g:196:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( exprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1044); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:196:24: ( ISTREAM | RSTREAM )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( ((LA58_0>=RSTREAM && LA58_0<=ISTREAM)) ) {
                alt58=1;
            }
            switch (alt58) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr1055); 
            // EsperEPL2Ast.g:196:51: ( exprCol )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==EXPRCOL) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:196:52: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_insertIntoExpr1058);
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
    // EsperEPL2Ast.g:199:1: exprCol : ^( EXPRCOL IDENT ( IDENT )* ) ;
    public final void exprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:200:2: ( ^( EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:200:4: ^( EXPRCOL IDENT ( IDENT )* )
            {
            match(input,EXPRCOL,FOLLOW_EXPRCOL_in_exprCol1077); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_exprCol1079); 
            // EsperEPL2Ast.g:200:20: ( IDENT )*
            loop60:
            do {
                int alt60=2;
                int LA60_0 = input.LA(1);

                if ( (LA60_0==IDENT) ) {
                    alt60=1;
                }


                switch (alt60) {
            	case 1 :
            	    // EsperEPL2Ast.g:200:21: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_exprCol1082); 

            	    }
            	    break;

            	default :
            	    break loop60;
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
    // EsperEPL2Ast.g:203:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:204:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList ) )
            // EsperEPL2Ast.g:204:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? ( DISTINCT )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause1100); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:204:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( ((LA61_0>=RSTREAM && LA61_0<=IRSTREAM)) ) {
                alt61=1;
            }
            switch (alt61) {
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

            // EsperEPL2Ast.g:204:55: ( DISTINCT )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==DISTINCT) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:204:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1115); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1118);
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
    // EsperEPL2Ast.g:207:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:208:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:208:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause1132);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:208:21: ( streamExpression ( outerJoin )* )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( (LA64_0==STREAM_EXPR) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // EsperEPL2Ast.g:208:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1135);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:208:39: ( outerJoin )*
            	    loop63:
            	    do {
            	        int alt63=2;
            	        int LA63_0 = input.LA(1);

            	        if ( ((LA63_0>=INNERJOIN_EXPR && LA63_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt63=1;
            	        }


            	        switch (alt63) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:208:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1138);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop63;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop64;
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
    // EsperEPL2Ast.g:211:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:212:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:212:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1158); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:212:24: ( matchRecogPartitionBy )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==MATCHREC_PARTITION) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:212:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1160);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1167);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:214:4: ( ALL )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==ALL) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:214:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1173); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:215:4: ( matchRecogMatchesAfterSkip )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==MATCHREC_AFTER_SKIP) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:215:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1179);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1185);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:217:4: ( matchRecogMatchesInterval )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==MATCHREC_INTERVAL) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:217:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1191);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1197);
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
    // EsperEPL2Ast.g:221:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:222:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:222:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1215); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:222:27: ( valueExpr )+
            int cnt69=0;
            loop69:
            do {
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( ((LA69_0>=IN_SET && LA69_0<=REGEXP)||LA69_0==NOT_EXPR||(LA69_0>=SUM && LA69_0<=AVG)||(LA69_0>=COALESCE && LA69_0<=COUNT)||(LA69_0>=CASE && LA69_0<=CASE2)||(LA69_0>=PREVIOUS && LA69_0<=EXISTS)||(LA69_0>=INSTANCEOF && LA69_0<=CURRENT_TIMESTAMP)||(LA69_0>=EVAL_AND_EXPR && LA69_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA69_0==EVENT_PROP_EXPR||(LA69_0>=CONCAT && LA69_0<=LIB_FUNCTION)||LA69_0==ARRAY_EXPR||(LA69_0>=NOT_IN_SET && LA69_0<=NOT_REGEXP)||(LA69_0>=IN_RANGE && LA69_0<=SUBSELECT_EXPR)||(LA69_0>=EXISTS_SUBSELECT_EXPR && LA69_0<=NOT_IN_SUBSELECT_EXPR)||LA69_0==SUBSTITUTION||(LA69_0>=FIRST_AGGREG && LA69_0<=LAST_AGGREG)||(LA69_0>=INT_TYPE && LA69_0<=NULL_TYPE)||(LA69_0>=STAR && LA69_0<=PLUS)||(LA69_0>=BAND && LA69_0<=BXOR)||(LA69_0>=LT && LA69_0<=GE)||(LA69_0>=MINUS && LA69_0<=MOD)) ) {
                    alt69=1;
                }


                switch (alt69) {
            	case 1 :
            	    // EsperEPL2Ast.g:222:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1217);
            	    valueExpr();

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
    // $ANTLR end "matchRecogPartitionBy"


    // $ANTLR start "matchRecogMatchesAfterSkip"
    // EsperEPL2Ast.g:225:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:226:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) )
            // EsperEPL2Ast.g:226:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1234); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1236); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1238); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1240); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1248); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:229:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:230:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:230:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1263); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1265); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1267);
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
    // EsperEPL2Ast.g:233:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:234:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:234:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1283); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:234:26: ( matchRecogMeasureListElement )*
                loop70:
                do {
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==MATCHREC_MEASURE_ITEM) ) {
                        alt70=1;
                    }


                    switch (alt70) {
                	case 1 :
                	    // EsperEPL2Ast.g:234:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1285);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop70;
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
    // EsperEPL2Ast.g:237:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:238:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:238:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1302); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1304);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:238:40: ( IDENT )?
            int alt71=2;
            int LA71_0 = input.LA(1);

            if ( (LA71_0==IDENT) ) {
                alt71=1;
            }
            switch (alt71) {
                case 1 :
                    // EsperEPL2Ast.g:238:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1306); 

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
    // EsperEPL2Ast.g:241:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:242:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:242:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1326); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:242:25: ( matchRecogPatternAlteration )+
            int cnt72=0;
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=MATCHREC_PATTERN_CONCAT && LA72_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // EsperEPL2Ast.g:242:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1328);
            	    matchRecogPatternAlteration();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt72 >= 1 ) break loop72;
                        EarlyExitException eee =
                            new EarlyExitException(72, input);
                        throw eee;
                }
                cnt72++;
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
    // EsperEPL2Ast.g:245:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:246:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==MATCHREC_PATTERN_CONCAT) ) {
                alt74=1;
            }
            else if ( (LA74_0==MATCHREC_PATTERN_ALTER) ) {
                alt74=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }
            switch (alt74) {
                case 1 :
                    // EsperEPL2Ast.g:246:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1343);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:247:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1351); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1353);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:247:55: ( matchRecogPatternConcat )+
                    int cnt73=0;
                    loop73:
                    do {
                        int alt73=2;
                        int LA73_0 = input.LA(1);

                        if ( (LA73_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt73=1;
                        }


                        switch (alt73) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:247:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1355);
                    	    matchRecogPatternConcat();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt73 >= 1 ) break loop73;
                                EarlyExitException eee =
                                    new EarlyExitException(73, input);
                                throw eee;
                        }
                        cnt73++;
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
    // EsperEPL2Ast.g:250:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:251:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:251:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1373); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:251:32: ( matchRecogPatternUnary )+
            int cnt75=0;
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==MATCHREC_PATTERN_ATOM||LA75_0==MATCHREC_PATTERN_NESTED) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // EsperEPL2Ast.g:251:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1375);
            	    matchRecogPatternUnary();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt75 >= 1 ) break loop75;
                        EarlyExitException eee =
                            new EarlyExitException(75, input);
                        throw eee;
                }
                cnt75++;
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
    // EsperEPL2Ast.g:254:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:255:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==MATCHREC_PATTERN_NESTED) ) {
                alt76=1;
            }
            else if ( (LA76_0==MATCHREC_PATTERN_ATOM) ) {
                alt76=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }
            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:255:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1390);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:256:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1395);
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
    // EsperEPL2Ast.g:259:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:260:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:260:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1410); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1412);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:260:60: ( PLUS | STAR | QUESTION )?
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( (LA77_0==STAR||(LA77_0>=PLUS && LA77_0<=QUESTION)) ) {
                alt77=1;
            }
            switch (alt77) {
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
    // EsperEPL2Ast.g:263:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:264:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:264:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1443); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1445); 
            // EsperEPL2Ast.g:264:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==STAR||(LA79_0>=PLUS && LA79_0<=QUESTION)) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:264:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:264:63: ( QUESTION )?
                    int alt78=2;
                    int LA78_0 = input.LA(1);

                    if ( (LA78_0==QUESTION) ) {
                        alt78=1;
                    }
                    switch (alt78) {
                        case 1 :
                            // EsperEPL2Ast.g:264:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1461); 

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
    // EsperEPL2Ast.g:267:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:268:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:268:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1483); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:268:24: ( matchRecogDefineItem )+
            int cnt80=0;
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==MATCHREC_DEFINE_ITEM) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // EsperEPL2Ast.g:268:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1485);
            	    matchRecogDefineItem();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt80 >= 1 ) break loop80;
                        EarlyExitException eee =
                            new EarlyExitException(80, input);
                        throw eee;
                }
                cnt80++;
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
    // EsperEPL2Ast.g:271:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:272:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:272:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1502); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1504); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1506);
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
    // EsperEPL2Ast.g:276:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:277:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:277:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1523);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:277:25: ( selectionListElement )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( ((LA81_0>=SELECTION_ELEMENT_EXPR && LA81_0<=SELECTION_STREAM)||LA81_0==WILDCARD_SELECT) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // EsperEPL2Ast.g:277:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1526);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop81;
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
    // EsperEPL2Ast.g:280:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:281:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt84=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt84=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt84=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt84=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }

            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:281:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1542); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:282:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1552); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1554);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:282:41: ( IDENT )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==IDENT) ) {
                        alt82=1;
                    }
                    switch (alt82) {
                        case 1 :
                            // EsperEPL2Ast.g:282:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1557); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:283:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1571); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1573); 
                    // EsperEPL2Ast.g:283:31: ( IDENT )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==IDENT) ) {
                        alt83=1;
                    }
                    switch (alt83) {
                        case 1 :
                            // EsperEPL2Ast.g:283:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1576); 

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
    // EsperEPL2Ast.g:286:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:287:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:287:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1595);
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
    // EsperEPL2Ast.g:290:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:291:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt89=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt89=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt89=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt89=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt89=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:291:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1609); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1611);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1614);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:291:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop85:
                    do {
                        int alt85=2;
                        int LA85_0 = input.LA(1);

                        if ( (LA85_0==EVENT_PROP_EXPR) ) {
                            alt85=1;
                        }


                        switch (alt85) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:291:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1618);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1621);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop85;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:292:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1636); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1638);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1641);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:292:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop86:
                    do {
                        int alt86=2;
                        int LA86_0 = input.LA(1);

                        if ( (LA86_0==EVENT_PROP_EXPR) ) {
                            alt86=1;
                        }


                        switch (alt86) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:292:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1645);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1648);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop86;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:293:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1663); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1665);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1668);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:293:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( (LA87_0==EVENT_PROP_EXPR) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:293:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1672);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1675);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop87;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:294:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1690); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1692);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1695);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:294:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop88:
                    do {
                        int alt88=2;
                        int LA88_0 = input.LA(1);

                        if ( (LA88_0==EVENT_PROP_EXPR) ) {
                            alt88=1;
                        }


                        switch (alt88) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:294:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1699);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1702);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop88;
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
    // EsperEPL2Ast.g:297:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:298:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:298:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1723); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:298:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt90=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt90=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt90=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt90=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt90=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }

            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:298:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1726);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1730);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:298:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1734);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:298:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1738);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:298:115: ( viewListExpr )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==VIEW_EXPR) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:298:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1742);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:298:131: ( IDENT )?
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( (LA92_0==IDENT) ) {
                alt92=1;
            }
            switch (alt92) {
                case 1 :
                    // EsperEPL2Ast.g:298:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1747); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:298:140: ( UNIDIRECTIONAL )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==UNIDIRECTIONAL) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // EsperEPL2Ast.g:298:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1752); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:298:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( ((LA94_0>=RETAINUNION && LA94_0<=RETAININTERSECTION)) ) {
                alt94=1;
            }
            switch (alt94) {
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
    // EsperEPL2Ast.g:301:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:302:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:302:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1780); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:302:27: ( IDENT )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==IDENT) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:302:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1782); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1785); 
            // EsperEPL2Ast.g:302:46: ( propertyExpression )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // EsperEPL2Ast.g:302:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1787);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:302:66: ( valueExpr )*
            loop97:
            do {
                int alt97=2;
                int LA97_0 = input.LA(1);

                if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=INSTANCEOF && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||LA97_0==ARRAY_EXPR||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=SUBSELECT_EXPR)||(LA97_0>=EXISTS_SUBSELECT_EXPR && LA97_0<=NOT_IN_SUBSELECT_EXPR)||LA97_0==SUBSTITUTION||(LA97_0>=FIRST_AGGREG && LA97_0<=LAST_AGGREG)||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||(LA97_0>=STAR && LA97_0<=PLUS)||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=MINUS && LA97_0<=MOD)) ) {
                    alt97=1;
                }


                switch (alt97) {
            	case 1 :
            	    // EsperEPL2Ast.g:302:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1791);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop97;
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
    // EsperEPL2Ast.g:305:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:306:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:306:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1811); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:306:34: ( propertyExpressionAtom )*
                loop98:
                do {
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( (LA98_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt98=1;
                    }


                    switch (alt98) {
                	case 1 :
                	    // EsperEPL2Ast.g:306:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1813);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop98;
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
    // EsperEPL2Ast.g:309:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:310:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:310:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1832); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:310:41: ( propertySelectionListElement )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( ((LA99_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA99_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // EsperEPL2Ast.g:310:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1834);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop99;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1837);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:310:96: ( IDENT )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==IDENT) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:310:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1840); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1844); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:310:116: ( valueExpr )?
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( ((LA101_0>=IN_SET && LA101_0<=REGEXP)||LA101_0==NOT_EXPR||(LA101_0>=SUM && LA101_0<=AVG)||(LA101_0>=COALESCE && LA101_0<=COUNT)||(LA101_0>=CASE && LA101_0<=CASE2)||(LA101_0>=PREVIOUS && LA101_0<=EXISTS)||(LA101_0>=INSTANCEOF && LA101_0<=CURRENT_TIMESTAMP)||(LA101_0>=EVAL_AND_EXPR && LA101_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA101_0==EVENT_PROP_EXPR||(LA101_0>=CONCAT && LA101_0<=LIB_FUNCTION)||LA101_0==ARRAY_EXPR||(LA101_0>=NOT_IN_SET && LA101_0<=NOT_REGEXP)||(LA101_0>=IN_RANGE && LA101_0<=SUBSELECT_EXPR)||(LA101_0>=EXISTS_SUBSELECT_EXPR && LA101_0<=NOT_IN_SUBSELECT_EXPR)||LA101_0==SUBSTITUTION||(LA101_0>=FIRST_AGGREG && LA101_0<=LAST_AGGREG)||(LA101_0>=INT_TYPE && LA101_0<=NULL_TYPE)||(LA101_0>=STAR && LA101_0<=PLUS)||(LA101_0>=BAND && LA101_0<=BXOR)||(LA101_0>=LT && LA101_0<=GE)||(LA101_0>=MINUS && LA101_0<=MOD)) ) {
                    alt101=1;
                }
                switch (alt101) {
                    case 1 :
                        // EsperEPL2Ast.g:310:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1846);
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
    // EsperEPL2Ast.g:313:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:314:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt104=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt104=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt104=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt104=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 104, 0, input);

                throw nvae;
            }

            switch (alt104) {
                case 1 :
                    // EsperEPL2Ast.g:314:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1866); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:315:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1876); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1878);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:315:50: ( IDENT )?
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( (LA102_0==IDENT) ) {
                        alt102=1;
                    }
                    switch (alt102) {
                        case 1 :
                            // EsperEPL2Ast.g:315:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1881); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:316:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1895); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1897); 
                    // EsperEPL2Ast.g:316:40: ( IDENT )?
                    int alt103=2;
                    int LA103_0 = input.LA(1);

                    if ( (LA103_0==IDENT) ) {
                        alt103=1;
                    }
                    switch (alt103) {
                        case 1 :
                            // EsperEPL2Ast.g:316:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1900); 

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
    // EsperEPL2Ast.g:319:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:320:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:320:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1921); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1923);
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
    // EsperEPL2Ast.g:323:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:324:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:324:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1940); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1942); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:324:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt105=2;
            int LA105_0 = input.LA(1);

            if ( ((LA105_0>=STRING_LITERAL && LA105_0<=QUOTED_STRING_LITERAL)) ) {
                alt105=1;
            }
            switch (alt105) {
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
    // EsperEPL2Ast.g:327:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:328:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:328:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1973); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1975); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1977); 
            // EsperEPL2Ast.g:328:41: ( valueExpr )*
            loop106:
            do {
                int alt106=2;
                int LA106_0 = input.LA(1);

                if ( ((LA106_0>=IN_SET && LA106_0<=REGEXP)||LA106_0==NOT_EXPR||(LA106_0>=SUM && LA106_0<=AVG)||(LA106_0>=COALESCE && LA106_0<=COUNT)||(LA106_0>=CASE && LA106_0<=CASE2)||(LA106_0>=PREVIOUS && LA106_0<=EXISTS)||(LA106_0>=INSTANCEOF && LA106_0<=CURRENT_TIMESTAMP)||(LA106_0>=EVAL_AND_EXPR && LA106_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA106_0==EVENT_PROP_EXPR||(LA106_0>=CONCAT && LA106_0<=LIB_FUNCTION)||LA106_0==ARRAY_EXPR||(LA106_0>=NOT_IN_SET && LA106_0<=NOT_REGEXP)||(LA106_0>=IN_RANGE && LA106_0<=SUBSELECT_EXPR)||(LA106_0>=EXISTS_SUBSELECT_EXPR && LA106_0<=NOT_IN_SUBSELECT_EXPR)||LA106_0==SUBSTITUTION||(LA106_0>=FIRST_AGGREG && LA106_0<=LAST_AGGREG)||(LA106_0>=INT_TYPE && LA106_0<=NULL_TYPE)||(LA106_0>=STAR && LA106_0<=PLUS)||(LA106_0>=BAND && LA106_0<=BXOR)||(LA106_0>=LT && LA106_0<=GE)||(LA106_0>=MINUS && LA106_0<=MOD)) ) {
                    alt106=1;
                }


                switch (alt106) {
            	case 1 :
            	    // EsperEPL2Ast.g:328:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1980);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop106;
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
    // EsperEPL2Ast.g:331:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:332:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:332:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1994);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:332:13: ( viewExpr )*
            loop107:
            do {
                int alt107=2;
                int LA107_0 = input.LA(1);

                if ( (LA107_0==VIEW_EXPR) ) {
                    alt107=1;
                }


                switch (alt107) {
            	case 1 :
            	    // EsperEPL2Ast.g:332:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1997);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop107;
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
    // EsperEPL2Ast.g:335:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:336:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:336:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr2014); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2016); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2018); 
            // EsperEPL2Ast.g:336:30: ( valueExprWithTime )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( ((LA108_0>=IN_SET && LA108_0<=REGEXP)||LA108_0==NOT_EXPR||(LA108_0>=SUM && LA108_0<=AVG)||(LA108_0>=COALESCE && LA108_0<=COUNT)||(LA108_0>=CASE && LA108_0<=CASE2)||LA108_0==LAST||(LA108_0>=PREVIOUS && LA108_0<=EXISTS)||(LA108_0>=LW && LA108_0<=CURRENT_TIMESTAMP)||(LA108_0>=NUMERIC_PARAM_RANGE && LA108_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA108_0>=EVAL_AND_EXPR && LA108_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA108_0==EVENT_PROP_EXPR||(LA108_0>=CONCAT && LA108_0<=LIB_FUNCTION)||(LA108_0>=TIME_PERIOD && LA108_0<=ARRAY_EXPR)||(LA108_0>=NOT_IN_SET && LA108_0<=NOT_REGEXP)||(LA108_0>=IN_RANGE && LA108_0<=SUBSELECT_EXPR)||(LA108_0>=EXISTS_SUBSELECT_EXPR && LA108_0<=NOT_IN_SUBSELECT_EXPR)||(LA108_0>=LAST_OPERATOR && LA108_0<=SUBSTITUTION)||LA108_0==NUMBERSETSTAR||(LA108_0>=FIRST_AGGREG && LA108_0<=LAST_AGGREG)||(LA108_0>=INT_TYPE && LA108_0<=NULL_TYPE)||(LA108_0>=STAR && LA108_0<=PLUS)||(LA108_0>=BAND && LA108_0<=BXOR)||(LA108_0>=LT && LA108_0<=GE)||(LA108_0>=MINUS && LA108_0<=MOD)) ) {
                    alt108=1;
                }


                switch (alt108) {
            	case 1 :
            	    // EsperEPL2Ast.g:336:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr2021);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop108;
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
    // EsperEPL2Ast.g:339:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:340:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:340:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause2043); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause2045);
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
    // EsperEPL2Ast.g:343:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:344:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:344:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause2063); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause2065);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:344:32: ( valueExpr )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( ((LA109_0>=IN_SET && LA109_0<=REGEXP)||LA109_0==NOT_EXPR||(LA109_0>=SUM && LA109_0<=AVG)||(LA109_0>=COALESCE && LA109_0<=COUNT)||(LA109_0>=CASE && LA109_0<=CASE2)||(LA109_0>=PREVIOUS && LA109_0<=EXISTS)||(LA109_0>=INSTANCEOF && LA109_0<=CURRENT_TIMESTAMP)||(LA109_0>=EVAL_AND_EXPR && LA109_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA109_0==EVENT_PROP_EXPR||(LA109_0>=CONCAT && LA109_0<=LIB_FUNCTION)||LA109_0==ARRAY_EXPR||(LA109_0>=NOT_IN_SET && LA109_0<=NOT_REGEXP)||(LA109_0>=IN_RANGE && LA109_0<=SUBSELECT_EXPR)||(LA109_0>=EXISTS_SUBSELECT_EXPR && LA109_0<=NOT_IN_SUBSELECT_EXPR)||LA109_0==SUBSTITUTION||(LA109_0>=FIRST_AGGREG && LA109_0<=LAST_AGGREG)||(LA109_0>=INT_TYPE && LA109_0<=NULL_TYPE)||(LA109_0>=STAR && LA109_0<=PLUS)||(LA109_0>=BAND && LA109_0<=BXOR)||(LA109_0>=LT && LA109_0<=GE)||(LA109_0>=MINUS && LA109_0<=MOD)) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // EsperEPL2Ast.g:344:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause2068);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop109;
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
    // EsperEPL2Ast.g:347:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:348:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:348:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause2086); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause2088);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:348:35: ( orderByElement )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( (LA110_0==ORDER_ELEMENT_EXPR) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // EsperEPL2Ast.g:348:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause2091);
            	    orderByElement();

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

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "orderByClause"


    // $ANTLR start "orderByElement"
    // EsperEPL2Ast.g:351:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:352:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:352:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2111); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2113);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:352:38: ( ASC | DESC )?
            int alt111=2;
            int LA111_0 = input.LA(1);

            if ( ((LA111_0>=ASC && LA111_0<=DESC)) ) {
                alt111=1;
            }
            switch (alt111) {
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
    // EsperEPL2Ast.g:355:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:356:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:356:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2138); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2140);
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
    // EsperEPL2Ast.g:359:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:360:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt122=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt122=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt122=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt122=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt122=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt122=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 122, 0, input);

                throw nvae;
            }

            switch (alt122) {
                case 1 :
                    // EsperEPL2Ast.g:360:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2158); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:360:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt112=2;
                    int LA112_0 = input.LA(1);

                    if ( (LA112_0==ALL||(LA112_0>=FIRST && LA112_0<=LAST)||LA112_0==SNAPSHOT) ) {
                        alt112=1;
                    }
                    switch (alt112) {
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

                    // EsperEPL2Ast.g:360:52: ( number | IDENT )
                    int alt113=2;
                    int LA113_0 = input.LA(1);

                    if ( ((LA113_0>=INT_TYPE && LA113_0<=DOUBLE_TYPE)) ) {
                        alt113=1;
                    }
                    else if ( (LA113_0==IDENT) ) {
                        alt113=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 113, 0, input);

                        throw nvae;
                    }
                    switch (alt113) {
                        case 1 :
                            // EsperEPL2Ast.g:360:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2172);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:360:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2174); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:360:67: ( outputLimitAfter )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==AFTER) ) {
                        alt114=1;
                    }
                    switch (alt114) {
                        case 1 :
                            // EsperEPL2Ast.g:360:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2177);
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
                    // EsperEPL2Ast.g:361:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2194); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:361:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( (LA115_0==ALL||(LA115_0>=FIRST && LA115_0<=LAST)||LA115_0==SNAPSHOT) ) {
                        alt115=1;
                    }
                    switch (alt115) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2207);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:361:72: ( outputLimitAfter )?
                    int alt116=2;
                    int LA116_0 = input.LA(1);

                    if ( (LA116_0==AFTER) ) {
                        alt116=1;
                    }
                    switch (alt116) {
                        case 1 :
                            // EsperEPL2Ast.g:361:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2209);
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
                    // EsperEPL2Ast.g:362:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2225); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:362:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt117=2;
                    int LA117_0 = input.LA(1);

                    if ( (LA117_0==ALL||(LA117_0>=FIRST && LA117_0<=LAST)||LA117_0==SNAPSHOT) ) {
                        alt117=1;
                    }
                    switch (alt117) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2238);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:362:85: ( outputLimitAfter )?
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( (LA118_0==AFTER) ) {
                        alt118=1;
                    }
                    switch (alt118) {
                        case 1 :
                            // EsperEPL2Ast.g:362:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2240);
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
                    // EsperEPL2Ast.g:363:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2256); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:363:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt119=2;
                    int LA119_0 = input.LA(1);

                    if ( (LA119_0==ALL||(LA119_0>=FIRST && LA119_0<=LAST)||LA119_0==SNAPSHOT) ) {
                        alt119=1;
                    }
                    switch (alt119) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2269);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:363:67: ( onSetExpr )?
                    int alt120=2;
                    int LA120_0 = input.LA(1);

                    if ( (LA120_0==ON_SET_EXPR) ) {
                        alt120=1;
                    }
                    switch (alt120) {
                        case 1 :
                            // EsperEPL2Ast.g:363:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2271);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:363:78: ( outputLimitAfter )?
                    int alt121=2;
                    int LA121_0 = input.LA(1);

                    if ( (LA121_0==AFTER) ) {
                        alt121=1;
                    }
                    switch (alt121) {
                        case 1 :
                            // EsperEPL2Ast.g:363:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2274);
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
                    // EsperEPL2Ast.g:364:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2287); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2289);
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
    // EsperEPL2Ast.g:367:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:368:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:368:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2304); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:368:12: ( timePeriod )?
                int alt123=2;
                int LA123_0 = input.LA(1);

                if ( (LA123_0==TIME_PERIOD) ) {
                    alt123=1;
                }
                switch (alt123) {
                    case 1 :
                        // EsperEPL2Ast.g:368:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2306);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:368:24: ( number )?
                int alt124=2;
                int LA124_0 = input.LA(1);

                if ( ((LA124_0>=INT_TYPE && LA124_0<=DOUBLE_TYPE)) ) {
                    alt124=1;
                }
                switch (alt124) {
                    case 1 :
                        // EsperEPL2Ast.g:368:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2309);
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
    // EsperEPL2Ast.g:371:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:372:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:372:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2325); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:372:23: ( number | IDENT )
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
                    // EsperEPL2Ast.g:372:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2328);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:372:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2330); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:372:38: ( number | IDENT )?
            int alt126=3;
            int LA126_0 = input.LA(1);

            if ( ((LA126_0>=INT_TYPE && LA126_0<=DOUBLE_TYPE)) ) {
                alt126=1;
            }
            else if ( (LA126_0==IDENT) ) {
                alt126=2;
            }
            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:372:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2334);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:372:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2336); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:372:54: ( COMMA )?
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==COMMA) ) {
                alt127=1;
            }
            switch (alt127) {
                case 1 :
                    // EsperEPL2Ast.g:372:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2340); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:372:61: ( OFFSET )?
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( (LA128_0==OFFSET) ) {
                alt128=1;
            }
            switch (alt128) {
                case 1 :
                    // EsperEPL2Ast.g:372:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2343); 

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
    // EsperEPL2Ast.g:375:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:376:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:376:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2361); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2363);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2365);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2367);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2369);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2371);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:376:121: ( valueExprWithTime )?
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||LA129_0==LAST||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=LW && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=NUMERIC_PARAM_RANGE && LA129_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA129_0==EVENT_PROP_EXPR||(LA129_0>=CONCAT && LA129_0<=LIB_FUNCTION)||(LA129_0>=TIME_PERIOD && LA129_0<=ARRAY_EXPR)||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=SUBSELECT_EXPR)||(LA129_0>=EXISTS_SUBSELECT_EXPR && LA129_0<=NOT_IN_SUBSELECT_EXPR)||(LA129_0>=LAST_OPERATOR && LA129_0<=SUBSTITUTION)||LA129_0==NUMBERSETSTAR||(LA129_0>=FIRST_AGGREG && LA129_0<=LAST_AGGREG)||(LA129_0>=INT_TYPE && LA129_0<=NULL_TYPE)||(LA129_0>=STAR && LA129_0<=PLUS)||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=MINUS && LA129_0<=MOD)) ) {
                alt129=1;
            }
            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:376:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2373);
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
    // EsperEPL2Ast.g:379:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:380:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt130=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt130=1;
                }
                break;
            case GT:
                {
                alt130=2;
                }
                break;
            case LE:
                {
                alt130=3;
                }
                break;
            case GE:
                {
                alt130=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 130, 0, input);

                throw nvae;
            }

            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:380:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2390); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2392);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:381:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2405); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2407);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:382:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2420); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2422);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:383:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2434); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2436);
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
    // EsperEPL2Ast.g:386:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:387:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:387:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:387:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:388:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2458);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:389:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( ((LA133_0>=IN_SET && LA133_0<=REGEXP)||LA133_0==NOT_EXPR||(LA133_0>=SUM && LA133_0<=AVG)||(LA133_0>=COALESCE && LA133_0<=COUNT)||(LA133_0>=CASE && LA133_0<=CASE2)||(LA133_0>=PREVIOUS && LA133_0<=EXISTS)||(LA133_0>=INSTANCEOF && LA133_0<=CURRENT_TIMESTAMP)||(LA133_0>=EVAL_AND_EXPR && LA133_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA133_0==EVENT_PROP_EXPR||(LA133_0>=CONCAT && LA133_0<=LIB_FUNCTION)||LA133_0==ARRAY_EXPR||(LA133_0>=NOT_IN_SET && LA133_0<=NOT_REGEXP)||(LA133_0>=IN_RANGE && LA133_0<=SUBSELECT_EXPR)||(LA133_0>=EXISTS_SUBSELECT_EXPR && LA133_0<=NOT_IN_SUBSELECT_EXPR)||LA133_0==SUBSTITUTION||(LA133_0>=FIRST_AGGREG && LA133_0<=LAST_AGGREG)||(LA133_0>=INT_TYPE && LA133_0<=NULL_TYPE)||(LA133_0>=STAR && LA133_0<=PLUS)||(LA133_0>=BAND && LA133_0<=BXOR)||(LA133_0>=LT && LA133_0<=GE)||(LA133_0>=MINUS && LA133_0<=MOD)) ) {
                alt133=1;
            }
            else if ( ((LA133_0>=ALL && LA133_0<=SOME)) ) {
                alt133=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 133, 0, input);

                throw nvae;
            }
            switch (alt133) {
                case 1 :
                    // EsperEPL2Ast.g:389:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2468);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:391:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:391:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt132=2;
                    int LA132_0 = input.LA(1);

                    if ( (LA132_0==UP||(LA132_0>=IN_SET && LA132_0<=REGEXP)||LA132_0==NOT_EXPR||(LA132_0>=SUM && LA132_0<=AVG)||(LA132_0>=COALESCE && LA132_0<=COUNT)||(LA132_0>=CASE && LA132_0<=CASE2)||(LA132_0>=PREVIOUS && LA132_0<=EXISTS)||(LA132_0>=INSTANCEOF && LA132_0<=CURRENT_TIMESTAMP)||(LA132_0>=EVAL_AND_EXPR && LA132_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA132_0==EVENT_PROP_EXPR||(LA132_0>=CONCAT && LA132_0<=LIB_FUNCTION)||LA132_0==ARRAY_EXPR||(LA132_0>=NOT_IN_SET && LA132_0<=NOT_REGEXP)||(LA132_0>=IN_RANGE && LA132_0<=SUBSELECT_EXPR)||(LA132_0>=EXISTS_SUBSELECT_EXPR && LA132_0<=NOT_IN_SUBSELECT_EXPR)||LA132_0==SUBSTITUTION||(LA132_0>=FIRST_AGGREG && LA132_0<=LAST_AGGREG)||(LA132_0>=INT_TYPE && LA132_0<=NULL_TYPE)||(LA132_0>=STAR && LA132_0<=PLUS)||(LA132_0>=BAND && LA132_0<=BXOR)||(LA132_0>=LT && LA132_0<=GE)||(LA132_0>=MINUS && LA132_0<=MOD)) ) {
                        alt132=1;
                    }
                    else if ( (LA132_0==SUBSELECT_GROUP_EXPR) ) {
                        alt132=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 132, 0, input);

                        throw nvae;
                    }
                    switch (alt132) {
                        case 1 :
                            // EsperEPL2Ast.g:391:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:391:22: ( valueExpr )*
                            loop131:
                            do {
                                int alt131=2;
                                int LA131_0 = input.LA(1);

                                if ( ((LA131_0>=IN_SET && LA131_0<=REGEXP)||LA131_0==NOT_EXPR||(LA131_0>=SUM && LA131_0<=AVG)||(LA131_0>=COALESCE && LA131_0<=COUNT)||(LA131_0>=CASE && LA131_0<=CASE2)||(LA131_0>=PREVIOUS && LA131_0<=EXISTS)||(LA131_0>=INSTANCEOF && LA131_0<=CURRENT_TIMESTAMP)||(LA131_0>=EVAL_AND_EXPR && LA131_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA131_0==EVENT_PROP_EXPR||(LA131_0>=CONCAT && LA131_0<=LIB_FUNCTION)||LA131_0==ARRAY_EXPR||(LA131_0>=NOT_IN_SET && LA131_0<=NOT_REGEXP)||(LA131_0>=IN_RANGE && LA131_0<=SUBSELECT_EXPR)||(LA131_0>=EXISTS_SUBSELECT_EXPR && LA131_0<=NOT_IN_SUBSELECT_EXPR)||LA131_0==SUBSTITUTION||(LA131_0>=FIRST_AGGREG && LA131_0<=LAST_AGGREG)||(LA131_0>=INT_TYPE && LA131_0<=NULL_TYPE)||(LA131_0>=STAR && LA131_0<=PLUS)||(LA131_0>=BAND && LA131_0<=BXOR)||(LA131_0>=LT && LA131_0<=GE)||(LA131_0>=MINUS && LA131_0<=MOD)) ) {
                                    alt131=1;
                                }


                                switch (alt131) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:391:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2492);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop131;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:391:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2497);
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
    // EsperEPL2Ast.g:396:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:397:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt140=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt140=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt140=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt140=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt140=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt140=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt140=6;
                }
                break;
            case NOT_EXPR:
                {
                alt140=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt140=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 140, 0, input);

                throw nvae;
            }

            switch (alt140) {
                case 1 :
                    // EsperEPL2Ast.g:397:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2523); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2525);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2527);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:397:42: ( valueExpr )*
                    loop134:
                    do {
                        int alt134=2;
                        int LA134_0 = input.LA(1);

                        if ( ((LA134_0>=IN_SET && LA134_0<=REGEXP)||LA134_0==NOT_EXPR||(LA134_0>=SUM && LA134_0<=AVG)||(LA134_0>=COALESCE && LA134_0<=COUNT)||(LA134_0>=CASE && LA134_0<=CASE2)||(LA134_0>=PREVIOUS && LA134_0<=EXISTS)||(LA134_0>=INSTANCEOF && LA134_0<=CURRENT_TIMESTAMP)||(LA134_0>=EVAL_AND_EXPR && LA134_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA134_0==EVENT_PROP_EXPR||(LA134_0>=CONCAT && LA134_0<=LIB_FUNCTION)||LA134_0==ARRAY_EXPR||(LA134_0>=NOT_IN_SET && LA134_0<=NOT_REGEXP)||(LA134_0>=IN_RANGE && LA134_0<=SUBSELECT_EXPR)||(LA134_0>=EXISTS_SUBSELECT_EXPR && LA134_0<=NOT_IN_SUBSELECT_EXPR)||LA134_0==SUBSTITUTION||(LA134_0>=FIRST_AGGREG && LA134_0<=LAST_AGGREG)||(LA134_0>=INT_TYPE && LA134_0<=NULL_TYPE)||(LA134_0>=STAR && LA134_0<=PLUS)||(LA134_0>=BAND && LA134_0<=BXOR)||(LA134_0>=LT && LA134_0<=GE)||(LA134_0>=MINUS && LA134_0<=MOD)) ) {
                            alt134=1;
                        }


                        switch (alt134) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:397:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2530);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop134;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:398:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2544); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2546);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2548);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:398:43: ( valueExpr )*
                    loop135:
                    do {
                        int alt135=2;
                        int LA135_0 = input.LA(1);

                        if ( ((LA135_0>=IN_SET && LA135_0<=REGEXP)||LA135_0==NOT_EXPR||(LA135_0>=SUM && LA135_0<=AVG)||(LA135_0>=COALESCE && LA135_0<=COUNT)||(LA135_0>=CASE && LA135_0<=CASE2)||(LA135_0>=PREVIOUS && LA135_0<=EXISTS)||(LA135_0>=INSTANCEOF && LA135_0<=CURRENT_TIMESTAMP)||(LA135_0>=EVAL_AND_EXPR && LA135_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA135_0==EVENT_PROP_EXPR||(LA135_0>=CONCAT && LA135_0<=LIB_FUNCTION)||LA135_0==ARRAY_EXPR||(LA135_0>=NOT_IN_SET && LA135_0<=NOT_REGEXP)||(LA135_0>=IN_RANGE && LA135_0<=SUBSELECT_EXPR)||(LA135_0>=EXISTS_SUBSELECT_EXPR && LA135_0<=NOT_IN_SUBSELECT_EXPR)||LA135_0==SUBSTITUTION||(LA135_0>=FIRST_AGGREG && LA135_0<=LAST_AGGREG)||(LA135_0>=INT_TYPE && LA135_0<=NULL_TYPE)||(LA135_0>=STAR && LA135_0<=PLUS)||(LA135_0>=BAND && LA135_0<=BXOR)||(LA135_0>=LT && LA135_0<=GE)||(LA135_0>=MINUS && LA135_0<=MOD)) ) {
                            alt135=1;
                        }


                        switch (alt135) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:398:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2551);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop135;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:399:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2565); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2567);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2569);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:400:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2581); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2583);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2585);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:401:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2597); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2599);
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

                    // EsperEPL2Ast.g:401:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt137=2;
                    int LA137_0 = input.LA(1);

                    if ( (LA137_0==UP||(LA137_0>=IN_SET && LA137_0<=REGEXP)||LA137_0==NOT_EXPR||(LA137_0>=SUM && LA137_0<=AVG)||(LA137_0>=COALESCE && LA137_0<=COUNT)||(LA137_0>=CASE && LA137_0<=CASE2)||(LA137_0>=PREVIOUS && LA137_0<=EXISTS)||(LA137_0>=INSTANCEOF && LA137_0<=CURRENT_TIMESTAMP)||(LA137_0>=EVAL_AND_EXPR && LA137_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA137_0==EVENT_PROP_EXPR||(LA137_0>=CONCAT && LA137_0<=LIB_FUNCTION)||LA137_0==ARRAY_EXPR||(LA137_0>=NOT_IN_SET && LA137_0<=NOT_REGEXP)||(LA137_0>=IN_RANGE && LA137_0<=SUBSELECT_EXPR)||(LA137_0>=EXISTS_SUBSELECT_EXPR && LA137_0<=NOT_IN_SUBSELECT_EXPR)||LA137_0==SUBSTITUTION||(LA137_0>=FIRST_AGGREG && LA137_0<=LAST_AGGREG)||(LA137_0>=INT_TYPE && LA137_0<=NULL_TYPE)||(LA137_0>=STAR && LA137_0<=PLUS)||(LA137_0>=BAND && LA137_0<=BXOR)||(LA137_0>=LT && LA137_0<=GE)||(LA137_0>=MINUS && LA137_0<=MOD)) ) {
                        alt137=1;
                    }
                    else if ( (LA137_0==SUBSELECT_GROUP_EXPR) ) {
                        alt137=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 137, 0, input);

                        throw nvae;
                    }
                    switch (alt137) {
                        case 1 :
                            // EsperEPL2Ast.g:401:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:401:59: ( valueExpr )*
                            loop136:
                            do {
                                int alt136=2;
                                int LA136_0 = input.LA(1);

                                if ( ((LA136_0>=IN_SET && LA136_0<=REGEXP)||LA136_0==NOT_EXPR||(LA136_0>=SUM && LA136_0<=AVG)||(LA136_0>=COALESCE && LA136_0<=COUNT)||(LA136_0>=CASE && LA136_0<=CASE2)||(LA136_0>=PREVIOUS && LA136_0<=EXISTS)||(LA136_0>=INSTANCEOF && LA136_0<=CURRENT_TIMESTAMP)||(LA136_0>=EVAL_AND_EXPR && LA136_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA136_0==EVENT_PROP_EXPR||(LA136_0>=CONCAT && LA136_0<=LIB_FUNCTION)||LA136_0==ARRAY_EXPR||(LA136_0>=NOT_IN_SET && LA136_0<=NOT_REGEXP)||(LA136_0>=IN_RANGE && LA136_0<=SUBSELECT_EXPR)||(LA136_0>=EXISTS_SUBSELECT_EXPR && LA136_0<=NOT_IN_SUBSELECT_EXPR)||LA136_0==SUBSTITUTION||(LA136_0>=FIRST_AGGREG && LA136_0<=LAST_AGGREG)||(LA136_0>=INT_TYPE && LA136_0<=NULL_TYPE)||(LA136_0>=STAR && LA136_0<=PLUS)||(LA136_0>=BAND && LA136_0<=BXOR)||(LA136_0>=LT && LA136_0<=GE)||(LA136_0>=MINUS && LA136_0<=MOD)) ) {
                                    alt136=1;
                                }


                                switch (alt136) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:401:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2610);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop136;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:401:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2615);
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
                    // EsperEPL2Ast.g:402:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2628); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2630);
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

                    // EsperEPL2Ast.g:402:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt139=2;
                    int LA139_0 = input.LA(1);

                    if ( (LA139_0==UP||(LA139_0>=IN_SET && LA139_0<=REGEXP)||LA139_0==NOT_EXPR||(LA139_0>=SUM && LA139_0<=AVG)||(LA139_0>=COALESCE && LA139_0<=COUNT)||(LA139_0>=CASE && LA139_0<=CASE2)||(LA139_0>=PREVIOUS && LA139_0<=EXISTS)||(LA139_0>=INSTANCEOF && LA139_0<=CURRENT_TIMESTAMP)||(LA139_0>=EVAL_AND_EXPR && LA139_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA139_0==EVENT_PROP_EXPR||(LA139_0>=CONCAT && LA139_0<=LIB_FUNCTION)||LA139_0==ARRAY_EXPR||(LA139_0>=NOT_IN_SET && LA139_0<=NOT_REGEXP)||(LA139_0>=IN_RANGE && LA139_0<=SUBSELECT_EXPR)||(LA139_0>=EXISTS_SUBSELECT_EXPR && LA139_0<=NOT_IN_SUBSELECT_EXPR)||LA139_0==SUBSTITUTION||(LA139_0>=FIRST_AGGREG && LA139_0<=LAST_AGGREG)||(LA139_0>=INT_TYPE && LA139_0<=NULL_TYPE)||(LA139_0>=STAR && LA139_0<=PLUS)||(LA139_0>=BAND && LA139_0<=BXOR)||(LA139_0>=LT && LA139_0<=GE)||(LA139_0>=MINUS && LA139_0<=MOD)) ) {
                        alt139=1;
                    }
                    else if ( (LA139_0==SUBSELECT_GROUP_EXPR) ) {
                        alt139=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 139, 0, input);

                        throw nvae;
                    }
                    switch (alt139) {
                        case 1 :
                            // EsperEPL2Ast.g:402:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:402:63: ( valueExpr )*
                            loop138:
                            do {
                                int alt138=2;
                                int LA138_0 = input.LA(1);

                                if ( ((LA138_0>=IN_SET && LA138_0<=REGEXP)||LA138_0==NOT_EXPR||(LA138_0>=SUM && LA138_0<=AVG)||(LA138_0>=COALESCE && LA138_0<=COUNT)||(LA138_0>=CASE && LA138_0<=CASE2)||(LA138_0>=PREVIOUS && LA138_0<=EXISTS)||(LA138_0>=INSTANCEOF && LA138_0<=CURRENT_TIMESTAMP)||(LA138_0>=EVAL_AND_EXPR && LA138_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA138_0==EVENT_PROP_EXPR||(LA138_0>=CONCAT && LA138_0<=LIB_FUNCTION)||LA138_0==ARRAY_EXPR||(LA138_0>=NOT_IN_SET && LA138_0<=NOT_REGEXP)||(LA138_0>=IN_RANGE && LA138_0<=SUBSELECT_EXPR)||(LA138_0>=EXISTS_SUBSELECT_EXPR && LA138_0<=NOT_IN_SUBSELECT_EXPR)||LA138_0==SUBSTITUTION||(LA138_0>=FIRST_AGGREG && LA138_0<=LAST_AGGREG)||(LA138_0>=INT_TYPE && LA138_0<=NULL_TYPE)||(LA138_0>=STAR && LA138_0<=PLUS)||(LA138_0>=BAND && LA138_0<=BXOR)||(LA138_0>=LT && LA138_0<=GE)||(LA138_0>=MINUS && LA138_0<=MOD)) ) {
                                    alt138=1;
                                }


                                switch (alt138) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:402:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2641);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop138;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:402:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2646);
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
                    // EsperEPL2Ast.g:403:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2659); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2661);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:404:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2672);
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
    // EsperEPL2Ast.g:407:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:408:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt141=16;
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
            case SUBSTITUTION:
                {
                alt141=2;
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
                alt141=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt141=4;
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
                alt141=5;
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
                alt141=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt141=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt141=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt141=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt141=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt141=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt141=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt141=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt141=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt141=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt141=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 141, 0, input);

                throw nvae;
            }

            switch (alt141) {
                case 1 :
                    // EsperEPL2Ast.g:408:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2685);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:409:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2691);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:410:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2697);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:411:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2704);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:412:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2713);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:413:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2718);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:414:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2726);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:415:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2731);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:416:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2736);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:417:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2742);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:418:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2747);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:419:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2752);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:420:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2757);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:421:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2762);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:422:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2768);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:423:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2775);
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
    // EsperEPL2Ast.g:426:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:427:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt143=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt143=1;
                }
                break;
            case LW:
                {
                alt143=2;
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
                alt143=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt143=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt143=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt143=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt143=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt143=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt143=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt143=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt143=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }

            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:427:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2788); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:428:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2797); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:429:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2804);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:430:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2812); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2814);
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
                    // EsperEPL2Ast.g:431:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2829);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:432:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2835);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:433:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2840);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:434:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2845);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:435:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2855); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:435:29: ( numericParameterList )+
                    int cnt142=0;
                    loop142:
                    do {
                        int alt142=2;
                        int LA142_0 = input.LA(1);

                        if ( (LA142_0==NUMERIC_PARAM_RANGE||LA142_0==NUMERIC_PARAM_FREQUENCY||(LA142_0>=INT_TYPE && LA142_0<=NULL_TYPE)) ) {
                            alt142=1;
                        }


                        switch (alt142) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:435:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2857);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt142 >= 1 ) break loop142;
                                EarlyExitException eee =
                                    new EarlyExitException(142, input);
                                throw eee;
                        }
                        cnt142++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:436:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2868); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:437:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2875);
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
    // EsperEPL2Ast.g:440:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:441:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt144=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt144=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt144=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt144=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }

            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:441:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2888);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:442:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2895);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:443:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2901);
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
    // EsperEPL2Ast.g:446:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:447:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:447:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2917); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:447:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt145=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt145=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt145=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt145=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }

            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:447:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2920);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:447:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2923);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:447:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2926);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:447:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt146=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt146=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt146=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt146=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 146, 0, input);

                throw nvae;
            }

            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:447:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2930);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:447:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2933);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:447:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2936);
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
    // EsperEPL2Ast.g:450:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:451:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:451:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2957); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:451:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt147=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt147=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt147=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt147=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 147, 0, input);

                throw nvae;
            }

            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:451:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2960);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:451:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2963);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:451:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2966);
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
    // EsperEPL2Ast.g:454:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:455:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:455:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2985); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:455:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt148=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt148=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt148=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt148=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 148, 0, input);

                throw nvae;
            }

            switch (alt148) {
                case 1 :
                    // EsperEPL2Ast.g:455:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2988);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:455:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2991);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:455:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2994);
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
    // EsperEPL2Ast.g:458:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:459:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:459:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3013); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:459:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt149=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt149=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt149=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt149=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 149, 0, input);

                throw nvae;
            }

            switch (alt149) {
                case 1 :
                    // EsperEPL2Ast.g:459:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator3016);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:459:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator3019);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:459:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator3022);
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
    // EsperEPL2Ast.g:462:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:463:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:463:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3043); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr3045);
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
    // EsperEPL2Ast.g:466:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:467:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:467:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3064); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr3066);
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
    // EsperEPL2Ast.g:470:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:471:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:471:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3085); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr3087);
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
    // EsperEPL2Ast.g:474:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:475:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt150=2;
            int LA150_0 = input.LA(1);

            if ( (LA150_0==IN_SUBSELECT_EXPR) ) {
                alt150=1;
            }
            else if ( (LA150_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt150=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }
            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:475:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3106); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3108);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3110);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:476:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3122); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3124);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3126);
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
    // EsperEPL2Ast.g:479:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:480:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:480:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3145); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3147);
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
    // EsperEPL2Ast.g:483:1: subQueryExpr : ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:484:2: ( ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:484:4: ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:484:4: ( DISTINCT )?
            int alt151=2;
            int LA151_0 = input.LA(1);

            if ( (LA151_0==DISTINCT) ) {
                alt151=1;
            }
            switch (alt151) {
                case 1 :
                    // EsperEPL2Ast.g:484:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3163); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr3166);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3168);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:484:55: ( whereClause[true] )?
            int alt152=2;
            int LA152_0 = input.LA(1);

            if ( (LA152_0==WHERE_EXPR) ) {
                alt152=1;
            }
            switch (alt152) {
                case 1 :
                    // EsperEPL2Ast.g:484:56: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3171);
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
    // EsperEPL2Ast.g:487:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:488:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:488:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3189); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3191);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:488:36: ( viewListExpr )?
            int alt153=2;
            int LA153_0 = input.LA(1);

            if ( (LA153_0==VIEW_EXPR) ) {
                alt153=1;
            }
            switch (alt153) {
                case 1 :
                    // EsperEPL2Ast.g:488:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3194);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:488:52: ( IDENT )?
            int alt154=2;
            int LA154_0 = input.LA(1);

            if ( (LA154_0==IDENT) ) {
                alt154=1;
            }
            switch (alt154) {
                case 1 :
                    // EsperEPL2Ast.g:488:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3199); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:488:61: ( RETAINUNION )?
            int alt155=2;
            int LA155_0 = input.LA(1);

            if ( (LA155_0==RETAINUNION) ) {
                alt155=1;
            }
            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:488:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3203); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:488:74: ( RETAININTERSECTION )?
            int alt156=2;
            int LA156_0 = input.LA(1);

            if ( (LA156_0==RETAININTERSECTION) ) {
                alt156=1;
            }
            switch (alt156) {
                case 1 :
                    // EsperEPL2Ast.g:488:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3206); 

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
    // EsperEPL2Ast.g:491:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:492:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==CASE) ) {
                alt159=1;
            }
            else if ( (LA159_0==CASE2) ) {
                alt159=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 159, 0, input);

                throw nvae;
            }
            switch (alt159) {
                case 1 :
                    // EsperEPL2Ast.g:492:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3226); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:492:13: ( valueExpr )*
                        loop157:
                        do {
                            int alt157=2;
                            int LA157_0 = input.LA(1);

                            if ( ((LA157_0>=IN_SET && LA157_0<=REGEXP)||LA157_0==NOT_EXPR||(LA157_0>=SUM && LA157_0<=AVG)||(LA157_0>=COALESCE && LA157_0<=COUNT)||(LA157_0>=CASE && LA157_0<=CASE2)||(LA157_0>=PREVIOUS && LA157_0<=EXISTS)||(LA157_0>=INSTANCEOF && LA157_0<=CURRENT_TIMESTAMP)||(LA157_0>=EVAL_AND_EXPR && LA157_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA157_0==EVENT_PROP_EXPR||(LA157_0>=CONCAT && LA157_0<=LIB_FUNCTION)||LA157_0==ARRAY_EXPR||(LA157_0>=NOT_IN_SET && LA157_0<=NOT_REGEXP)||(LA157_0>=IN_RANGE && LA157_0<=SUBSELECT_EXPR)||(LA157_0>=EXISTS_SUBSELECT_EXPR && LA157_0<=NOT_IN_SUBSELECT_EXPR)||LA157_0==SUBSTITUTION||(LA157_0>=FIRST_AGGREG && LA157_0<=LAST_AGGREG)||(LA157_0>=INT_TYPE && LA157_0<=NULL_TYPE)||(LA157_0>=STAR && LA157_0<=PLUS)||(LA157_0>=BAND && LA157_0<=BXOR)||(LA157_0>=LT && LA157_0<=GE)||(LA157_0>=MINUS && LA157_0<=MOD)) ) {
                                alt157=1;
                            }


                            switch (alt157) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:492:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3229);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop157;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:493:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3242); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:493:14: ( valueExpr )*
                        loop158:
                        do {
                            int alt158=2;
                            int LA158_0 = input.LA(1);

                            if ( ((LA158_0>=IN_SET && LA158_0<=REGEXP)||LA158_0==NOT_EXPR||(LA158_0>=SUM && LA158_0<=AVG)||(LA158_0>=COALESCE && LA158_0<=COUNT)||(LA158_0>=CASE && LA158_0<=CASE2)||(LA158_0>=PREVIOUS && LA158_0<=EXISTS)||(LA158_0>=INSTANCEOF && LA158_0<=CURRENT_TIMESTAMP)||(LA158_0>=EVAL_AND_EXPR && LA158_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA158_0==EVENT_PROP_EXPR||(LA158_0>=CONCAT && LA158_0<=LIB_FUNCTION)||LA158_0==ARRAY_EXPR||(LA158_0>=NOT_IN_SET && LA158_0<=NOT_REGEXP)||(LA158_0>=IN_RANGE && LA158_0<=SUBSELECT_EXPR)||(LA158_0>=EXISTS_SUBSELECT_EXPR && LA158_0<=NOT_IN_SUBSELECT_EXPR)||LA158_0==SUBSTITUTION||(LA158_0>=FIRST_AGGREG && LA158_0<=LAST_AGGREG)||(LA158_0>=INT_TYPE && LA158_0<=NULL_TYPE)||(LA158_0>=STAR && LA158_0<=PLUS)||(LA158_0>=BAND && LA158_0<=BXOR)||(LA158_0>=LT && LA158_0<=GE)||(LA158_0>=MINUS && LA158_0<=MOD)) ) {
                                alt158=1;
                            }


                            switch (alt158) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:493:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3245);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop158;
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
    // EsperEPL2Ast.g:496:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:497:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt162=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt162=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt162=2;
                }
                break;
            case IN_RANGE:
                {
                alt162=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt162=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }

            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:497:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3265); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3267);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3275);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:497:51: ( valueExpr )*
                    loop160:
                    do {
                        int alt160=2;
                        int LA160_0 = input.LA(1);

                        if ( ((LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||(LA160_0>=CONCAT && LA160_0<=LIB_FUNCTION)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=FIRST_AGGREG && LA160_0<=LAST_AGGREG)||(LA160_0>=INT_TYPE && LA160_0<=NULL_TYPE)||(LA160_0>=STAR && LA160_0<=PLUS)||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=MINUS && LA160_0<=MOD)) ) {
                            alt160=1;
                        }


                        switch (alt160) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:497:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3278);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop160;
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
                    // EsperEPL2Ast.g:498:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3297); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3299);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3307);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:498:55: ( valueExpr )*
                    loop161:
                    do {
                        int alt161=2;
                        int LA161_0 = input.LA(1);

                        if ( ((LA161_0>=IN_SET && LA161_0<=REGEXP)||LA161_0==NOT_EXPR||(LA161_0>=SUM && LA161_0<=AVG)||(LA161_0>=COALESCE && LA161_0<=COUNT)||(LA161_0>=CASE && LA161_0<=CASE2)||(LA161_0>=PREVIOUS && LA161_0<=EXISTS)||(LA161_0>=INSTANCEOF && LA161_0<=CURRENT_TIMESTAMP)||(LA161_0>=EVAL_AND_EXPR && LA161_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA161_0==EVENT_PROP_EXPR||(LA161_0>=CONCAT && LA161_0<=LIB_FUNCTION)||LA161_0==ARRAY_EXPR||(LA161_0>=NOT_IN_SET && LA161_0<=NOT_REGEXP)||(LA161_0>=IN_RANGE && LA161_0<=SUBSELECT_EXPR)||(LA161_0>=EXISTS_SUBSELECT_EXPR && LA161_0<=NOT_IN_SUBSELECT_EXPR)||LA161_0==SUBSTITUTION||(LA161_0>=FIRST_AGGREG && LA161_0<=LAST_AGGREG)||(LA161_0>=INT_TYPE && LA161_0<=NULL_TYPE)||(LA161_0>=STAR && LA161_0<=PLUS)||(LA161_0>=BAND && LA161_0<=BXOR)||(LA161_0>=LT && LA161_0<=GE)||(LA161_0>=MINUS && LA161_0<=MOD)) ) {
                            alt161=1;
                        }


                        switch (alt161) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:498:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3310);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop161;
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
                    // EsperEPL2Ast.g:499:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3329); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3331);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3339);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3341);
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
                    // EsperEPL2Ast.g:500:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3358); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3360);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3368);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3370);
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
    // EsperEPL2Ast.g:503:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:504:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==BETWEEN) ) {
                alt164=1;
            }
            else if ( (LA164_0==NOT_BETWEEN) ) {
                alt164=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 164, 0, input);

                throw nvae;
            }
            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:504:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3395); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3397);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3399);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3401);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:505:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3412); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3414);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3416);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:505:40: ( valueExpr )*
                    loop163:
                    do {
                        int alt163=2;
                        int LA163_0 = input.LA(1);

                        if ( ((LA163_0>=IN_SET && LA163_0<=REGEXP)||LA163_0==NOT_EXPR||(LA163_0>=SUM && LA163_0<=AVG)||(LA163_0>=COALESCE && LA163_0<=COUNT)||(LA163_0>=CASE && LA163_0<=CASE2)||(LA163_0>=PREVIOUS && LA163_0<=EXISTS)||(LA163_0>=INSTANCEOF && LA163_0<=CURRENT_TIMESTAMP)||(LA163_0>=EVAL_AND_EXPR && LA163_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA163_0==EVENT_PROP_EXPR||(LA163_0>=CONCAT && LA163_0<=LIB_FUNCTION)||LA163_0==ARRAY_EXPR||(LA163_0>=NOT_IN_SET && LA163_0<=NOT_REGEXP)||(LA163_0>=IN_RANGE && LA163_0<=SUBSELECT_EXPR)||(LA163_0>=EXISTS_SUBSELECT_EXPR && LA163_0<=NOT_IN_SUBSELECT_EXPR)||LA163_0==SUBSTITUTION||(LA163_0>=FIRST_AGGREG && LA163_0<=LAST_AGGREG)||(LA163_0>=INT_TYPE && LA163_0<=NULL_TYPE)||(LA163_0>=STAR && LA163_0<=PLUS)||(LA163_0>=BAND && LA163_0<=BXOR)||(LA163_0>=LT && LA163_0<=GE)||(LA163_0>=MINUS && LA163_0<=MOD)) ) {
                            alt163=1;
                        }


                        switch (alt163) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:505:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3419);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop163;
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
    // EsperEPL2Ast.g:508:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:509:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt167=2;
            int LA167_0 = input.LA(1);

            if ( (LA167_0==LIKE) ) {
                alt167=1;
            }
            else if ( (LA167_0==NOT_LIKE) ) {
                alt167=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 167, 0, input);

                throw nvae;
            }
            switch (alt167) {
                case 1 :
                    // EsperEPL2Ast.g:509:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3439); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3441);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3443);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:509:33: ( valueExpr )?
                    int alt165=2;
                    int LA165_0 = input.LA(1);

                    if ( ((LA165_0>=IN_SET && LA165_0<=REGEXP)||LA165_0==NOT_EXPR||(LA165_0>=SUM && LA165_0<=AVG)||(LA165_0>=COALESCE && LA165_0<=COUNT)||(LA165_0>=CASE && LA165_0<=CASE2)||(LA165_0>=PREVIOUS && LA165_0<=EXISTS)||(LA165_0>=INSTANCEOF && LA165_0<=CURRENT_TIMESTAMP)||(LA165_0>=EVAL_AND_EXPR && LA165_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA165_0==EVENT_PROP_EXPR||(LA165_0>=CONCAT && LA165_0<=LIB_FUNCTION)||LA165_0==ARRAY_EXPR||(LA165_0>=NOT_IN_SET && LA165_0<=NOT_REGEXP)||(LA165_0>=IN_RANGE && LA165_0<=SUBSELECT_EXPR)||(LA165_0>=EXISTS_SUBSELECT_EXPR && LA165_0<=NOT_IN_SUBSELECT_EXPR)||LA165_0==SUBSTITUTION||(LA165_0>=FIRST_AGGREG && LA165_0<=LAST_AGGREG)||(LA165_0>=INT_TYPE && LA165_0<=NULL_TYPE)||(LA165_0>=STAR && LA165_0<=PLUS)||(LA165_0>=BAND && LA165_0<=BXOR)||(LA165_0>=LT && LA165_0<=GE)||(LA165_0>=MINUS && LA165_0<=MOD)) ) {
                        alt165=1;
                    }
                    switch (alt165) {
                        case 1 :
                            // EsperEPL2Ast.g:509:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3446);
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
                    // EsperEPL2Ast.g:510:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3459); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3461);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3463);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:510:37: ( valueExpr )?
                    int alt166=2;
                    int LA166_0 = input.LA(1);

                    if ( ((LA166_0>=IN_SET && LA166_0<=REGEXP)||LA166_0==NOT_EXPR||(LA166_0>=SUM && LA166_0<=AVG)||(LA166_0>=COALESCE && LA166_0<=COUNT)||(LA166_0>=CASE && LA166_0<=CASE2)||(LA166_0>=PREVIOUS && LA166_0<=EXISTS)||(LA166_0>=INSTANCEOF && LA166_0<=CURRENT_TIMESTAMP)||(LA166_0>=EVAL_AND_EXPR && LA166_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA166_0==EVENT_PROP_EXPR||(LA166_0>=CONCAT && LA166_0<=LIB_FUNCTION)||LA166_0==ARRAY_EXPR||(LA166_0>=NOT_IN_SET && LA166_0<=NOT_REGEXP)||(LA166_0>=IN_RANGE && LA166_0<=SUBSELECT_EXPR)||(LA166_0>=EXISTS_SUBSELECT_EXPR && LA166_0<=NOT_IN_SUBSELECT_EXPR)||LA166_0==SUBSTITUTION||(LA166_0>=FIRST_AGGREG && LA166_0<=LAST_AGGREG)||(LA166_0>=INT_TYPE && LA166_0<=NULL_TYPE)||(LA166_0>=STAR && LA166_0<=PLUS)||(LA166_0>=BAND && LA166_0<=BXOR)||(LA166_0>=LT && LA166_0<=GE)||(LA166_0>=MINUS && LA166_0<=MOD)) ) {
                        alt166=1;
                    }
                    switch (alt166) {
                        case 1 :
                            // EsperEPL2Ast.g:510:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3466);
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
    // EsperEPL2Ast.g:513:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:514:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt168=2;
            int LA168_0 = input.LA(1);

            if ( (LA168_0==REGEXP) ) {
                alt168=1;
            }
            else if ( (LA168_0==NOT_REGEXP) ) {
                alt168=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 168, 0, input);

                throw nvae;
            }
            switch (alt168) {
                case 1 :
                    // EsperEPL2Ast.g:514:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3485); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3487);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3489);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:515:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3500); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3502);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3504);
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
    // EsperEPL2Ast.g:518:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:519:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt181=15;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt181=1;
                }
                break;
            case AVG:
                {
                alt181=2;
                }
                break;
            case COUNT:
                {
                alt181=3;
                }
                break;
            case MEDIAN:
                {
                alt181=4;
                }
                break;
            case STDDEV:
                {
                alt181=5;
                }
                break;
            case AVEDEV:
                {
                alt181=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt181=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt181=8;
                }
                break;
            case COALESCE:
                {
                alt181=9;
                }
                break;
            case PREVIOUS:
                {
                alt181=10;
                }
                break;
            case PRIOR:
                {
                alt181=11;
                }
                break;
            case INSTANCEOF:
                {
                alt181=12;
                }
                break;
            case CAST:
                {
                alt181=13;
                }
                break;
            case EXISTS:
                {
                alt181=14;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt181=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 181, 0, input);

                throw nvae;
            }

            switch (alt181) {
                case 1 :
                    // EsperEPL2Ast.g:519:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3523); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:519:13: ( DISTINCT )?
                    int alt169=2;
                    int LA169_0 = input.LA(1);

                    if ( (LA169_0==DISTINCT) ) {
                        alt169=1;
                    }
                    switch (alt169) {
                        case 1 :
                            // EsperEPL2Ast.g:519:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3526); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3530);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:520:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3541); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:520:12: ( DISTINCT )?
                    int alt170=2;
                    int LA170_0 = input.LA(1);

                    if ( (LA170_0==DISTINCT) ) {
                        alt170=1;
                    }
                    switch (alt170) {
                        case 1 :
                            // EsperEPL2Ast.g:520:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3544); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3548);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:521:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3559); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:521:14: ( ( DISTINCT )? valueExpr )?
                        int alt172=2;
                        int LA172_0 = input.LA(1);

                        if ( ((LA172_0>=IN_SET && LA172_0<=REGEXP)||LA172_0==NOT_EXPR||(LA172_0>=SUM && LA172_0<=AVG)||(LA172_0>=COALESCE && LA172_0<=COUNT)||(LA172_0>=CASE && LA172_0<=CASE2)||LA172_0==DISTINCT||(LA172_0>=PREVIOUS && LA172_0<=EXISTS)||(LA172_0>=INSTANCEOF && LA172_0<=CURRENT_TIMESTAMP)||(LA172_0>=EVAL_AND_EXPR && LA172_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA172_0==EVENT_PROP_EXPR||(LA172_0>=CONCAT && LA172_0<=LIB_FUNCTION)||LA172_0==ARRAY_EXPR||(LA172_0>=NOT_IN_SET && LA172_0<=NOT_REGEXP)||(LA172_0>=IN_RANGE && LA172_0<=SUBSELECT_EXPR)||(LA172_0>=EXISTS_SUBSELECT_EXPR && LA172_0<=NOT_IN_SUBSELECT_EXPR)||LA172_0==SUBSTITUTION||(LA172_0>=FIRST_AGGREG && LA172_0<=LAST_AGGREG)||(LA172_0>=INT_TYPE && LA172_0<=NULL_TYPE)||(LA172_0>=STAR && LA172_0<=PLUS)||(LA172_0>=BAND && LA172_0<=BXOR)||(LA172_0>=LT && LA172_0<=GE)||(LA172_0>=MINUS && LA172_0<=MOD)) ) {
                            alt172=1;
                        }
                        switch (alt172) {
                            case 1 :
                                // EsperEPL2Ast.g:521:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:521:15: ( DISTINCT )?
                                int alt171=2;
                                int LA171_0 = input.LA(1);

                                if ( (LA171_0==DISTINCT) ) {
                                    alt171=1;
                                }
                                switch (alt171) {
                                    case 1 :
                                        // EsperEPL2Ast.g:521:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3563); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3567);
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
                    // EsperEPL2Ast.g:522:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3581); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:522:15: ( DISTINCT )?
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( (LA173_0==DISTINCT) ) {
                        alt173=1;
                    }
                    switch (alt173) {
                        case 1 :
                            // EsperEPL2Ast.g:522:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3584); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3588);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:523:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3599); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:523:15: ( DISTINCT )?
                    int alt174=2;
                    int LA174_0 = input.LA(1);

                    if ( (LA174_0==DISTINCT) ) {
                        alt174=1;
                    }
                    switch (alt174) {
                        case 1 :
                            // EsperEPL2Ast.g:523:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3602); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3606);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:524:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3617); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:524:15: ( DISTINCT )?
                    int alt175=2;
                    int LA175_0 = input.LA(1);

                    if ( (LA175_0==DISTINCT) ) {
                        alt175=1;
                    }
                    switch (alt175) {
                        case 1 :
                            // EsperEPL2Ast.g:524:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3620); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3624);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:525:4: ^(f= LAST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3635); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:525:20: ( DISTINCT )?
                    int alt176=2;
                    int LA176_0 = input.LA(1);

                    if ( (LA176_0==DISTINCT) ) {
                        alt176=1;
                    }
                    switch (alt176) {
                        case 1 :
                            // EsperEPL2Ast.g:525:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3638); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3642);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:526:4: ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3653); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:526:21: ( DISTINCT )?
                    int alt177=2;
                    int LA177_0 = input.LA(1);

                    if ( (LA177_0==DISTINCT) ) {
                        alt177=1;
                    }
                    switch (alt177) {
                        case 1 :
                            // EsperEPL2Ast.g:526:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3656); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3660);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:527:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3672); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3674);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3676);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:527:38: ( valueExpr )*
                    loop178:
                    do {
                        int alt178=2;
                        int LA178_0 = input.LA(1);

                        if ( ((LA178_0>=IN_SET && LA178_0<=REGEXP)||LA178_0==NOT_EXPR||(LA178_0>=SUM && LA178_0<=AVG)||(LA178_0>=COALESCE && LA178_0<=COUNT)||(LA178_0>=CASE && LA178_0<=CASE2)||(LA178_0>=PREVIOUS && LA178_0<=EXISTS)||(LA178_0>=INSTANCEOF && LA178_0<=CURRENT_TIMESTAMP)||(LA178_0>=EVAL_AND_EXPR && LA178_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA178_0==EVENT_PROP_EXPR||(LA178_0>=CONCAT && LA178_0<=LIB_FUNCTION)||LA178_0==ARRAY_EXPR||(LA178_0>=NOT_IN_SET && LA178_0<=NOT_REGEXP)||(LA178_0>=IN_RANGE && LA178_0<=SUBSELECT_EXPR)||(LA178_0>=EXISTS_SUBSELECT_EXPR && LA178_0<=NOT_IN_SUBSELECT_EXPR)||LA178_0==SUBSTITUTION||(LA178_0>=FIRST_AGGREG && LA178_0<=LAST_AGGREG)||(LA178_0>=INT_TYPE && LA178_0<=NULL_TYPE)||(LA178_0>=STAR && LA178_0<=PLUS)||(LA178_0>=BAND && LA178_0<=BXOR)||(LA178_0>=LT && LA178_0<=GE)||(LA178_0>=MINUS && LA178_0<=MOD)) ) {
                            alt178=1;
                        }


                        switch (alt178) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:527:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3679);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop178;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:528:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3694); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3696);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:528:28: ( valueExpr )?
                    int alt179=2;
                    int LA179_0 = input.LA(1);

                    if ( ((LA179_0>=IN_SET && LA179_0<=REGEXP)||LA179_0==NOT_EXPR||(LA179_0>=SUM && LA179_0<=AVG)||(LA179_0>=COALESCE && LA179_0<=COUNT)||(LA179_0>=CASE && LA179_0<=CASE2)||(LA179_0>=PREVIOUS && LA179_0<=EXISTS)||(LA179_0>=INSTANCEOF && LA179_0<=CURRENT_TIMESTAMP)||(LA179_0>=EVAL_AND_EXPR && LA179_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA179_0==EVENT_PROP_EXPR||(LA179_0>=CONCAT && LA179_0<=LIB_FUNCTION)||LA179_0==ARRAY_EXPR||(LA179_0>=NOT_IN_SET && LA179_0<=NOT_REGEXP)||(LA179_0>=IN_RANGE && LA179_0<=SUBSELECT_EXPR)||(LA179_0>=EXISTS_SUBSELECT_EXPR && LA179_0<=NOT_IN_SUBSELECT_EXPR)||LA179_0==SUBSTITUTION||(LA179_0>=FIRST_AGGREG && LA179_0<=LAST_AGGREG)||(LA179_0>=INT_TYPE && LA179_0<=NULL_TYPE)||(LA179_0>=STAR && LA179_0<=PLUS)||(LA179_0>=BAND && LA179_0<=BXOR)||(LA179_0>=LT && LA179_0<=GE)||(LA179_0>=MINUS && LA179_0<=MOD)) ) {
                        alt179=1;
                    }
                    switch (alt179) {
                        case 1 :
                            // EsperEPL2Ast.g:528:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3698);
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
                    // EsperEPL2Ast.g:529:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3711); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3715); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3717);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:530:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3730); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3732);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3734); 
                    // EsperEPL2Ast.g:530:42: ( CLASS_IDENT )*
                    loop180:
                    do {
                        int alt180=2;
                        int LA180_0 = input.LA(1);

                        if ( (LA180_0==CLASS_IDENT) ) {
                            alt180=1;
                        }


                        switch (alt180) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:530:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3737); 

                    	    }
                    	    break;

                    	default :
                    	    break loop180;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:531:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3751); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3753);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3755); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:532:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3767); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3769);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:533:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3781); 



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
    // EsperEPL2Ast.g:536:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:537:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:537:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3801); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:537:19: ( valueExpr )*
                loop182:
                do {
                    int alt182=2;
                    int LA182_0 = input.LA(1);

                    if ( ((LA182_0>=IN_SET && LA182_0<=REGEXP)||LA182_0==NOT_EXPR||(LA182_0>=SUM && LA182_0<=AVG)||(LA182_0>=COALESCE && LA182_0<=COUNT)||(LA182_0>=CASE && LA182_0<=CASE2)||(LA182_0>=PREVIOUS && LA182_0<=EXISTS)||(LA182_0>=INSTANCEOF && LA182_0<=CURRENT_TIMESTAMP)||(LA182_0>=EVAL_AND_EXPR && LA182_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA182_0==EVENT_PROP_EXPR||(LA182_0>=CONCAT && LA182_0<=LIB_FUNCTION)||LA182_0==ARRAY_EXPR||(LA182_0>=NOT_IN_SET && LA182_0<=NOT_REGEXP)||(LA182_0>=IN_RANGE && LA182_0<=SUBSELECT_EXPR)||(LA182_0>=EXISTS_SUBSELECT_EXPR && LA182_0<=NOT_IN_SUBSELECT_EXPR)||LA182_0==SUBSTITUTION||(LA182_0>=FIRST_AGGREG && LA182_0<=LAST_AGGREG)||(LA182_0>=INT_TYPE && LA182_0<=NULL_TYPE)||(LA182_0>=STAR && LA182_0<=PLUS)||(LA182_0>=BAND && LA182_0<=BXOR)||(LA182_0>=LT && LA182_0<=GE)||(LA182_0>=MINUS && LA182_0<=MOD)) ) {
                        alt182=1;
                    }


                    switch (alt182) {
                	case 1 :
                	    // EsperEPL2Ast.g:537:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3804);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop182;
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
    // EsperEPL2Ast.g:540:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:541:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt184=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt184=1;
                }
                break;
            case MINUS:
                {
                alt184=2;
                }
                break;
            case DIV:
                {
                alt184=3;
                }
                break;
            case STAR:
                {
                alt184=4;
                }
                break;
            case MOD:
                {
                alt184=5;
                }
                break;
            case BAND:
                {
                alt184=6;
                }
                break;
            case BOR:
                {
                alt184=7;
                }
                break;
            case BXOR:
                {
                alt184=8;
                }
                break;
            case CONCAT:
                {
                alt184=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 184, 0, input);

                throw nvae;
            }

            switch (alt184) {
                case 1 :
                    // EsperEPL2Ast.g:541:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3825); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3827);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3829);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:542:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3841); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3843);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3845);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:543:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3857); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3859);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3861);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:544:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3872); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3874);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3876);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:545:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3888); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3890);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3892);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:546:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3903); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3905);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3907);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:547:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3918); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3920);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3922);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:548:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3933); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3935);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3937);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:549:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3949); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3951);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3953);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:549:36: ( valueExpr )*
                    loop183:
                    do {
                        int alt183=2;
                        int LA183_0 = input.LA(1);

                        if ( ((LA183_0>=IN_SET && LA183_0<=REGEXP)||LA183_0==NOT_EXPR||(LA183_0>=SUM && LA183_0<=AVG)||(LA183_0>=COALESCE && LA183_0<=COUNT)||(LA183_0>=CASE && LA183_0<=CASE2)||(LA183_0>=PREVIOUS && LA183_0<=EXISTS)||(LA183_0>=INSTANCEOF && LA183_0<=CURRENT_TIMESTAMP)||(LA183_0>=EVAL_AND_EXPR && LA183_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA183_0==EVENT_PROP_EXPR||(LA183_0>=CONCAT && LA183_0<=LIB_FUNCTION)||LA183_0==ARRAY_EXPR||(LA183_0>=NOT_IN_SET && LA183_0<=NOT_REGEXP)||(LA183_0>=IN_RANGE && LA183_0<=SUBSELECT_EXPR)||(LA183_0>=EXISTS_SUBSELECT_EXPR && LA183_0<=NOT_IN_SUBSELECT_EXPR)||LA183_0==SUBSTITUTION||(LA183_0>=FIRST_AGGREG && LA183_0<=LAST_AGGREG)||(LA183_0>=INT_TYPE && LA183_0<=NULL_TYPE)||(LA183_0>=STAR && LA183_0<=PLUS)||(LA183_0>=BAND && LA183_0<=BXOR)||(LA183_0>=LT && LA183_0<=GE)||(LA183_0>=MINUS && LA183_0<=MOD)) ) {
                            alt183=1;
                        }


                        switch (alt183) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:549:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3956);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop183;
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
    // EsperEPL2Ast.g:552:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:553:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:553:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3977); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:553:22: ( CLASS_IDENT )?
            int alt185=2;
            int LA185_0 = input.LA(1);

            if ( (LA185_0==CLASS_IDENT) ) {
                alt185=1;
            }
            switch (alt185) {
                case 1 :
                    // EsperEPL2Ast.g:553:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3980); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3984); 
            // EsperEPL2Ast.g:553:43: ( DISTINCT )?
            int alt186=2;
            int LA186_0 = input.LA(1);

            if ( (LA186_0==DISTINCT) ) {
                alt186=1;
            }
            switch (alt186) {
                case 1 :
                    // EsperEPL2Ast.g:553:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3987); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:553:55: ( valueExpr )*
            loop187:
            do {
                int alt187=2;
                int LA187_0 = input.LA(1);

                if ( ((LA187_0>=IN_SET && LA187_0<=REGEXP)||LA187_0==NOT_EXPR||(LA187_0>=SUM && LA187_0<=AVG)||(LA187_0>=COALESCE && LA187_0<=COUNT)||(LA187_0>=CASE && LA187_0<=CASE2)||(LA187_0>=PREVIOUS && LA187_0<=EXISTS)||(LA187_0>=INSTANCEOF && LA187_0<=CURRENT_TIMESTAMP)||(LA187_0>=EVAL_AND_EXPR && LA187_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA187_0==EVENT_PROP_EXPR||(LA187_0>=CONCAT && LA187_0<=LIB_FUNCTION)||LA187_0==ARRAY_EXPR||(LA187_0>=NOT_IN_SET && LA187_0<=NOT_REGEXP)||(LA187_0>=IN_RANGE && LA187_0<=SUBSELECT_EXPR)||(LA187_0>=EXISTS_SUBSELECT_EXPR && LA187_0<=NOT_IN_SUBSELECT_EXPR)||LA187_0==SUBSTITUTION||(LA187_0>=FIRST_AGGREG && LA187_0<=LAST_AGGREG)||(LA187_0>=INT_TYPE && LA187_0<=NULL_TYPE)||(LA187_0>=STAR && LA187_0<=PLUS)||(LA187_0>=BAND && LA187_0<=BXOR)||(LA187_0>=LT && LA187_0<=GE)||(LA187_0>=MINUS && LA187_0<=MOD)) ) {
                    alt187=1;
                }


                switch (alt187) {
            	case 1 :
            	    // EsperEPL2Ast.g:553:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3992);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop187;
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
    // EsperEPL2Ast.g:559:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:560:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:560:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:560:4: ( annotation[true] )*
            loop188:
            do {
                int alt188=2;
                int LA188_0 = input.LA(1);

                if ( (LA188_0==ANNOTATION) ) {
                    alt188=1;
                }


                switch (alt188) {
            	case 1 :
            	    // EsperEPL2Ast.g:560:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule4012);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop188;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule4016);
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
    // EsperEPL2Ast.g:563:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:564:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt192=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt192=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt192=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt192=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt192=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt192=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt192=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt192=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 192, 0, input);

                throw nvae;
            }

            switch (alt192) {
                case 1 :
                    // EsperEPL2Ast.g:564:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice4030);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:565:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice4035);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:566:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice4045); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4047);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:567:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4061); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice4063);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4065);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:568:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4079); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4081);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:569:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice4095); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4097);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice4099); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice4101); 
                    // EsperEPL2Ast.g:569:44: ( valueExprWithTime )*
                    loop189:
                    do {
                        int alt189=2;
                        int LA189_0 = input.LA(1);

                        if ( ((LA189_0>=IN_SET && LA189_0<=REGEXP)||LA189_0==NOT_EXPR||(LA189_0>=SUM && LA189_0<=AVG)||(LA189_0>=COALESCE && LA189_0<=COUNT)||(LA189_0>=CASE && LA189_0<=CASE2)||LA189_0==LAST||(LA189_0>=PREVIOUS && LA189_0<=EXISTS)||(LA189_0>=LW && LA189_0<=CURRENT_TIMESTAMP)||(LA189_0>=NUMERIC_PARAM_RANGE && LA189_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA189_0>=EVAL_AND_EXPR && LA189_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA189_0==EVENT_PROP_EXPR||(LA189_0>=CONCAT && LA189_0<=LIB_FUNCTION)||(LA189_0>=TIME_PERIOD && LA189_0<=ARRAY_EXPR)||(LA189_0>=NOT_IN_SET && LA189_0<=NOT_REGEXP)||(LA189_0>=IN_RANGE && LA189_0<=SUBSELECT_EXPR)||(LA189_0>=EXISTS_SUBSELECT_EXPR && LA189_0<=NOT_IN_SUBSELECT_EXPR)||(LA189_0>=LAST_OPERATOR && LA189_0<=SUBSTITUTION)||LA189_0==NUMBERSETSTAR||(LA189_0>=FIRST_AGGREG && LA189_0<=LAST_AGGREG)||(LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)||(LA189_0>=STAR && LA189_0<=PLUS)||(LA189_0>=BAND && LA189_0<=BXOR)||(LA189_0>=LT && LA189_0<=GE)||(LA189_0>=MINUS && LA189_0<=MOD)) ) {
                            alt189=1;
                        }


                        switch (alt189) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:569:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice4103);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop189;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:570:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4117); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:570:26: ( matchUntilRange )?
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( ((LA190_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA190_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt190=1;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:570:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4119);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4122);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:570:54: ( exprChoice )?
                    int alt191=2;
                    int LA191_0 = input.LA(1);

                    if ( ((LA191_0>=OR_EXPR && LA191_0<=AND_EXPR)||(LA191_0>=EVERY_EXPR && LA191_0<=EVERY_DISTINCT_EXPR)||LA191_0==FOLLOWED_BY_EXPR||(LA191_0>=PATTERN_FILTER_EXPR && LA191_0<=PATTERN_NOT_EXPR)||(LA191_0>=GUARD_EXPR && LA191_0<=OBSERVER_EXPR)||LA191_0==MATCH_UNTIL_EXPR) ) {
                        alt191=1;
                    }
                    switch (alt191) {
                        case 1 :
                            // EsperEPL2Ast.g:570:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4124);
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
    // EsperEPL2Ast.g:574:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:575:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:575:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4145); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:575:35: ( valueExpr )+
            int cnt193=0;
            loop193:
            do {
                int alt193=2;
                int LA193_0 = input.LA(1);

                if ( ((LA193_0>=IN_SET && LA193_0<=REGEXP)||LA193_0==NOT_EXPR||(LA193_0>=SUM && LA193_0<=AVG)||(LA193_0>=COALESCE && LA193_0<=COUNT)||(LA193_0>=CASE && LA193_0<=CASE2)||(LA193_0>=PREVIOUS && LA193_0<=EXISTS)||(LA193_0>=INSTANCEOF && LA193_0<=CURRENT_TIMESTAMP)||(LA193_0>=EVAL_AND_EXPR && LA193_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA193_0==EVENT_PROP_EXPR||(LA193_0>=CONCAT && LA193_0<=LIB_FUNCTION)||LA193_0==ARRAY_EXPR||(LA193_0>=NOT_IN_SET && LA193_0<=NOT_REGEXP)||(LA193_0>=IN_RANGE && LA193_0<=SUBSELECT_EXPR)||(LA193_0>=EXISTS_SUBSELECT_EXPR && LA193_0<=NOT_IN_SUBSELECT_EXPR)||LA193_0==SUBSTITUTION||(LA193_0>=FIRST_AGGREG && LA193_0<=LAST_AGGREG)||(LA193_0>=INT_TYPE && LA193_0<=NULL_TYPE)||(LA193_0>=STAR && LA193_0<=PLUS)||(LA193_0>=BAND && LA193_0<=BXOR)||(LA193_0>=LT && LA193_0<=GE)||(LA193_0>=MINUS && LA193_0<=MOD)) ) {
                    alt193=1;
                }


                switch (alt193) {
            	case 1 :
            	    // EsperEPL2Ast.g:575:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions4147);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt193 >= 1 ) break loop193;
                        EarlyExitException eee =
                            new EarlyExitException(193, input);
                        throw eee;
                }
                cnt193++;
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
    // EsperEPL2Ast.g:578:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:579:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt197=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt197=1;
                }
                break;
            case OR_EXPR:
                {
                alt197=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:579:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4166); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4168);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4170);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:579:48: ( exprChoice )*
                    loop194:
                    do {
                        int alt194=2;
                        int LA194_0 = input.LA(1);

                        if ( ((LA194_0>=OR_EXPR && LA194_0<=AND_EXPR)||(LA194_0>=EVERY_EXPR && LA194_0<=EVERY_DISTINCT_EXPR)||LA194_0==FOLLOWED_BY_EXPR||(LA194_0>=PATTERN_FILTER_EXPR && LA194_0<=PATTERN_NOT_EXPR)||(LA194_0>=GUARD_EXPR && LA194_0<=OBSERVER_EXPR)||LA194_0==MATCH_UNTIL_EXPR) ) {
                            alt194=1;
                        }


                        switch (alt194) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:579:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4173);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop194;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:580:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4189); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4191);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4193);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:580:40: ( exprChoice )*
                    loop195:
                    do {
                        int alt195=2;
                        int LA195_0 = input.LA(1);

                        if ( ((LA195_0>=OR_EXPR && LA195_0<=AND_EXPR)||(LA195_0>=EVERY_EXPR && LA195_0<=EVERY_DISTINCT_EXPR)||LA195_0==FOLLOWED_BY_EXPR||(LA195_0>=PATTERN_FILTER_EXPR && LA195_0<=PATTERN_NOT_EXPR)||(LA195_0>=GUARD_EXPR && LA195_0<=OBSERVER_EXPR)||LA195_0==MATCH_UNTIL_EXPR) ) {
                            alt195=1;
                        }


                        switch (alt195) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:580:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4196);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop195;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:581:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4212); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4214);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4216);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:581:41: ( exprChoice )*
                    loop196:
                    do {
                        int alt196=2;
                        int LA196_0 = input.LA(1);

                        if ( ((LA196_0>=OR_EXPR && LA196_0<=AND_EXPR)||(LA196_0>=EVERY_EXPR && LA196_0<=EVERY_DISTINCT_EXPR)||LA196_0==FOLLOWED_BY_EXPR||(LA196_0>=PATTERN_FILTER_EXPR && LA196_0<=PATTERN_NOT_EXPR)||(LA196_0>=GUARD_EXPR && LA196_0<=OBSERVER_EXPR)||LA196_0==MATCH_UNTIL_EXPR) ) {
                            alt196=1;
                        }


                        switch (alt196) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:581:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4219);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop196;
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
    // EsperEPL2Ast.g:584:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:585:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt199=2;
            int LA199_0 = input.LA(1);

            if ( (LA199_0==PATTERN_FILTER_EXPR) ) {
                alt199=1;
            }
            else if ( (LA199_0==OBSERVER_EXPR) ) {
                alt199=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 199, 0, input);

                throw nvae;
            }
            switch (alt199) {
                case 1 :
                    // EsperEPL2Ast.g:585:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4238);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:586:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4250); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4252); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4254); 
                    // EsperEPL2Ast.g:586:39: ( valueExprWithTime )*
                    loop198:
                    do {
                        int alt198=2;
                        int LA198_0 = input.LA(1);

                        if ( ((LA198_0>=IN_SET && LA198_0<=REGEXP)||LA198_0==NOT_EXPR||(LA198_0>=SUM && LA198_0<=AVG)||(LA198_0>=COALESCE && LA198_0<=COUNT)||(LA198_0>=CASE && LA198_0<=CASE2)||LA198_0==LAST||(LA198_0>=PREVIOUS && LA198_0<=EXISTS)||(LA198_0>=LW && LA198_0<=CURRENT_TIMESTAMP)||(LA198_0>=NUMERIC_PARAM_RANGE && LA198_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA198_0>=EVAL_AND_EXPR && LA198_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA198_0==EVENT_PROP_EXPR||(LA198_0>=CONCAT && LA198_0<=LIB_FUNCTION)||(LA198_0>=TIME_PERIOD && LA198_0<=ARRAY_EXPR)||(LA198_0>=NOT_IN_SET && LA198_0<=NOT_REGEXP)||(LA198_0>=IN_RANGE && LA198_0<=SUBSELECT_EXPR)||(LA198_0>=EXISTS_SUBSELECT_EXPR && LA198_0<=NOT_IN_SUBSELECT_EXPR)||(LA198_0>=LAST_OPERATOR && LA198_0<=SUBSTITUTION)||LA198_0==NUMBERSETSTAR||(LA198_0>=FIRST_AGGREG && LA198_0<=LAST_AGGREG)||(LA198_0>=INT_TYPE && LA198_0<=NULL_TYPE)||(LA198_0>=STAR && LA198_0<=PLUS)||(LA198_0>=BAND && LA198_0<=BXOR)||(LA198_0>=LT && LA198_0<=GE)||(LA198_0>=MINUS && LA198_0<=MOD)) ) {
                            alt198=1;
                        }


                        switch (alt198) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:586:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4256);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop198;
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
    // EsperEPL2Ast.g:589:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:590:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:590:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4276); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:590:29: ( IDENT )?
            int alt200=2;
            int LA200_0 = input.LA(1);

            if ( (LA200_0==IDENT) ) {
                alt200=1;
            }
            switch (alt200) {
                case 1 :
                    // EsperEPL2Ast.g:590:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4278); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4281); 
            // EsperEPL2Ast.g:590:48: ( propertyExpression )?
            int alt201=2;
            int LA201_0 = input.LA(1);

            if ( (LA201_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt201=1;
            }
            switch (alt201) {
                case 1 :
                    // EsperEPL2Ast.g:590:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4283);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:590:68: ( valueExpr )*
            loop202:
            do {
                int alt202=2;
                int LA202_0 = input.LA(1);

                if ( ((LA202_0>=IN_SET && LA202_0<=REGEXP)||LA202_0==NOT_EXPR||(LA202_0>=SUM && LA202_0<=AVG)||(LA202_0>=COALESCE && LA202_0<=COUNT)||(LA202_0>=CASE && LA202_0<=CASE2)||(LA202_0>=PREVIOUS && LA202_0<=EXISTS)||(LA202_0>=INSTANCEOF && LA202_0<=CURRENT_TIMESTAMP)||(LA202_0>=EVAL_AND_EXPR && LA202_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA202_0==EVENT_PROP_EXPR||(LA202_0>=CONCAT && LA202_0<=LIB_FUNCTION)||LA202_0==ARRAY_EXPR||(LA202_0>=NOT_IN_SET && LA202_0<=NOT_REGEXP)||(LA202_0>=IN_RANGE && LA202_0<=SUBSELECT_EXPR)||(LA202_0>=EXISTS_SUBSELECT_EXPR && LA202_0<=NOT_IN_SUBSELECT_EXPR)||LA202_0==SUBSTITUTION||(LA202_0>=FIRST_AGGREG && LA202_0<=LAST_AGGREG)||(LA202_0>=INT_TYPE && LA202_0<=NULL_TYPE)||(LA202_0>=STAR && LA202_0<=PLUS)||(LA202_0>=BAND && LA202_0<=BXOR)||(LA202_0>=LT && LA202_0<=GE)||(LA202_0>=MINUS && LA202_0<=MOD)) ) {
                    alt202=1;
                }


                switch (alt202) {
            	case 1 :
            	    // EsperEPL2Ast.g:590:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4287);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop202;
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
    // EsperEPL2Ast.g:593:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:594:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt203=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt203=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt203=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt203=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt203=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 203, 0, input);

                throw nvae;
            }

            switch (alt203) {
                case 1 :
                    // EsperEPL2Ast.g:594:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4305); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4307);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4309);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:595:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4317); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4319);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:596:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4327); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4329);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:597:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4336); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4338);
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
    // EsperEPL2Ast.g:600:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:601:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:605:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:606:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:606:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4367); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4369);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:606:35: ( valueExpr )*
            loop204:
            do {
                int alt204=2;
                int LA204_0 = input.LA(1);

                if ( ((LA204_0>=IN_SET && LA204_0<=REGEXP)||LA204_0==NOT_EXPR||(LA204_0>=SUM && LA204_0<=AVG)||(LA204_0>=COALESCE && LA204_0<=COUNT)||(LA204_0>=CASE && LA204_0<=CASE2)||(LA204_0>=PREVIOUS && LA204_0<=EXISTS)||(LA204_0>=INSTANCEOF && LA204_0<=CURRENT_TIMESTAMP)||(LA204_0>=EVAL_AND_EXPR && LA204_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA204_0==EVENT_PROP_EXPR||(LA204_0>=CONCAT && LA204_0<=LIB_FUNCTION)||LA204_0==ARRAY_EXPR||(LA204_0>=NOT_IN_SET && LA204_0<=NOT_REGEXP)||(LA204_0>=IN_RANGE && LA204_0<=SUBSELECT_EXPR)||(LA204_0>=EXISTS_SUBSELECT_EXPR && LA204_0<=NOT_IN_SUBSELECT_EXPR)||LA204_0==SUBSTITUTION||(LA204_0>=FIRST_AGGREG && LA204_0<=LAST_AGGREG)||(LA204_0>=INT_TYPE && LA204_0<=NULL_TYPE)||(LA204_0>=STAR && LA204_0<=PLUS)||(LA204_0>=BAND && LA204_0<=BXOR)||(LA204_0>=LT && LA204_0<=GE)||(LA204_0>=MINUS && LA204_0<=MOD)) ) {
                    alt204=1;
                }


                switch (alt204) {
            	case 1 :
            	    // EsperEPL2Ast.g:606:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4372);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop204;
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
    // EsperEPL2Ast.g:609:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:610:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt217=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt217=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt217=2;
                }
                break;
            case LT:
                {
                alt217=3;
                }
                break;
            case LE:
                {
                alt217=4;
                }
                break;
            case GT:
                {
                alt217=5;
                }
                break;
            case GE:
                {
                alt217=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt217=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt217=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt217=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt217=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt217=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt217=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 217, 0, input);

                throw nvae;
            }

            switch (alt217) {
                case 1 :
                    // EsperEPL2Ast.g:610:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4388); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4390);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:611:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4397); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4399);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:612:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4406); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4408);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:613:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4415); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4417);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:614:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4424); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4426);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:615:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4433); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4435);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:616:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4442); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:616:41: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:616:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4451);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:616:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4454);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:616:76: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:616:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4458);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:616:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4461);
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
                    // EsperEPL2Ast.g:617:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4475); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:617:45: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:617:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4484);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:617:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4487);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:617:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:617:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4491);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:617:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4494);
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
                    // EsperEPL2Ast.g:618:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4508); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:618:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:618:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4517);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:618:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4520);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:618:73: ( constant[false] | filterIdentifier )*
                    loop210:
                    do {
                        int alt210=3;
                        int LA210_0 = input.LA(1);

                        if ( ((LA210_0>=INT_TYPE && LA210_0<=NULL_TYPE)) ) {
                            alt210=1;
                        }
                        else if ( (LA210_0==EVENT_FILTER_IDENT) ) {
                            alt210=2;
                        }


                        switch (alt210) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:618:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4524);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:618:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4527);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop210;
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
                    // EsperEPL2Ast.g:619:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4542); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:619:42: ( constant[false] | filterIdentifier )
                    int alt211=2;
                    int LA211_0 = input.LA(1);

                    if ( ((LA211_0>=INT_TYPE && LA211_0<=NULL_TYPE)) ) {
                        alt211=1;
                    }
                    else if ( (LA211_0==EVENT_FILTER_IDENT) ) {
                        alt211=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 211, 0, input);

                        throw nvae;
                    }
                    switch (alt211) {
                        case 1 :
                            // EsperEPL2Ast.g:619:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4551);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:619:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4554);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:619:77: ( constant[false] | filterIdentifier )*
                    loop212:
                    do {
                        int alt212=3;
                        int LA212_0 = input.LA(1);

                        if ( ((LA212_0>=INT_TYPE && LA212_0<=NULL_TYPE)) ) {
                            alt212=1;
                        }
                        else if ( (LA212_0==EVENT_FILTER_IDENT) ) {
                            alt212=2;
                        }


                        switch (alt212) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:619:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4558);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:619:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4561);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop212;
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
                    // EsperEPL2Ast.g:620:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4576); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:620:27: ( constant[false] | filterIdentifier )
                    int alt213=2;
                    int LA213_0 = input.LA(1);

                    if ( ((LA213_0>=INT_TYPE && LA213_0<=NULL_TYPE)) ) {
                        alt213=1;
                    }
                    else if ( (LA213_0==EVENT_FILTER_IDENT) ) {
                        alt213=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 213, 0, input);

                        throw nvae;
                    }
                    switch (alt213) {
                        case 1 :
                            // EsperEPL2Ast.g:620:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4579);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:620:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4582);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:620:62: ( constant[false] | filterIdentifier )
                    int alt214=2;
                    int LA214_0 = input.LA(1);

                    if ( ((LA214_0>=INT_TYPE && LA214_0<=NULL_TYPE)) ) {
                        alt214=1;
                    }
                    else if ( (LA214_0==EVENT_FILTER_IDENT) ) {
                        alt214=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 214, 0, input);

                        throw nvae;
                    }
                    switch (alt214) {
                        case 1 :
                            // EsperEPL2Ast.g:620:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4586);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:620:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4589);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:621:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4597); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:621:31: ( constant[false] | filterIdentifier )
                    int alt215=2;
                    int LA215_0 = input.LA(1);

                    if ( ((LA215_0>=INT_TYPE && LA215_0<=NULL_TYPE)) ) {
                        alt215=1;
                    }
                    else if ( (LA215_0==EVENT_FILTER_IDENT) ) {
                        alt215=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 215, 0, input);

                        throw nvae;
                    }
                    switch (alt215) {
                        case 1 :
                            // EsperEPL2Ast.g:621:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4600);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:621:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4603);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:621:66: ( constant[false] | filterIdentifier )
                    int alt216=2;
                    int LA216_0 = input.LA(1);

                    if ( ((LA216_0>=INT_TYPE && LA216_0<=NULL_TYPE)) ) {
                        alt216=1;
                    }
                    else if ( (LA216_0==EVENT_FILTER_IDENT) ) {
                        alt216=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 216, 0, input);

                        throw nvae;
                    }
                    switch (alt216) {
                        case 1 :
                            // EsperEPL2Ast.g:621:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4607);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:621:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4610);
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
    // EsperEPL2Ast.g:624:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:625:2: ( constant[false] | filterIdentifier )
            int alt218=2;
            int LA218_0 = input.LA(1);

            if ( ((LA218_0>=INT_TYPE && LA218_0<=NULL_TYPE)) ) {
                alt218=1;
            }
            else if ( (LA218_0==EVENT_FILTER_IDENT) ) {
                alt218=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 218, 0, input);

                throw nvae;
            }
            switch (alt218) {
                case 1 :
                    // EsperEPL2Ast.g:625:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4624);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:626:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4630);
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
    // EsperEPL2Ast.g:628:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:629:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:629:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4641); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4643); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4645);
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
    // EsperEPL2Ast.g:632:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:633:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:633:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4664); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4666);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:633:44: ( eventPropertyAtomic )*
            loop219:
            do {
                int alt219=2;
                int LA219_0 = input.LA(1);

                if ( ((LA219_0>=EVENT_PROP_SIMPLE && LA219_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt219=1;
                }


                switch (alt219) {
            	case 1 :
            	    // EsperEPL2Ast.g:633:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4669);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop219;
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
    // EsperEPL2Ast.g:636:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:637:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt220=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt220=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt220=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt220=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt220=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt220=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt220=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 220, 0, input);

                throw nvae;
            }

            switch (alt220) {
                case 1 :
                    // EsperEPL2Ast.g:637:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4688); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4690); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:638:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4697); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4699); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4701); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:639:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4708); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4710); 
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
                    // EsperEPL2Ast.g:640:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4725); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4727); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:641:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4734); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4736); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4738); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:642:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4745); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4747); 
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
    // EsperEPL2Ast.g:645:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:646:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:646:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4774); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4776);
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
    // EsperEPL2Ast.g:649:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:650:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt231=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt231=1;
                }
                break;
            case HOUR_PART:
                {
                alt231=2;
                }
                break;
            case MINUTE_PART:
                {
                alt231=3;
                }
                break;
            case SECOND_PART:
                {
                alt231=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt231=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 231, 0, input);

                throw nvae;
            }

            switch (alt231) {
                case 1 :
                    // EsperEPL2Ast.g:650:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4792);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:650:13: ( hourPart )?
                    int alt221=2;
                    int LA221_0 = input.LA(1);

                    if ( (LA221_0==HOUR_PART) ) {
                        alt221=1;
                    }
                    switch (alt221) {
                        case 1 :
                            // EsperEPL2Ast.g:650:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4795);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:650:25: ( minutePart )?
                    int alt222=2;
                    int LA222_0 = input.LA(1);

                    if ( (LA222_0==MINUTE_PART) ) {
                        alt222=1;
                    }
                    switch (alt222) {
                        case 1 :
                            // EsperEPL2Ast.g:650:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4800);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:650:39: ( secondPart )?
                    int alt223=2;
                    int LA223_0 = input.LA(1);

                    if ( (LA223_0==SECOND_PART) ) {
                        alt223=1;
                    }
                    switch (alt223) {
                        case 1 :
                            // EsperEPL2Ast.g:650:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4805);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:650:53: ( millisecondPart )?
                    int alt224=2;
                    int LA224_0 = input.LA(1);

                    if ( (LA224_0==MILLISECOND_PART) ) {
                        alt224=1;
                    }
                    switch (alt224) {
                        case 1 :
                            // EsperEPL2Ast.g:650:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4810);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:651:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4817);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:651:13: ( minutePart )?
                    int alt225=2;
                    int LA225_0 = input.LA(1);

                    if ( (LA225_0==MINUTE_PART) ) {
                        alt225=1;
                    }
                    switch (alt225) {
                        case 1 :
                            // EsperEPL2Ast.g:651:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4820);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:651:27: ( secondPart )?
                    int alt226=2;
                    int LA226_0 = input.LA(1);

                    if ( (LA226_0==SECOND_PART) ) {
                        alt226=1;
                    }
                    switch (alt226) {
                        case 1 :
                            // EsperEPL2Ast.g:651:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4825);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:651:41: ( millisecondPart )?
                    int alt227=2;
                    int LA227_0 = input.LA(1);

                    if ( (LA227_0==MILLISECOND_PART) ) {
                        alt227=1;
                    }
                    switch (alt227) {
                        case 1 :
                            // EsperEPL2Ast.g:651:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4830);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:652:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4837);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:652:15: ( secondPart )?
                    int alt228=2;
                    int LA228_0 = input.LA(1);

                    if ( (LA228_0==SECOND_PART) ) {
                        alt228=1;
                    }
                    switch (alt228) {
                        case 1 :
                            // EsperEPL2Ast.g:652:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4840);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:652:29: ( millisecondPart )?
                    int alt229=2;
                    int LA229_0 = input.LA(1);

                    if ( (LA229_0==MILLISECOND_PART) ) {
                        alt229=1;
                    }
                    switch (alt229) {
                        case 1 :
                            // EsperEPL2Ast.g:652:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4845);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:653:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4852);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:653:15: ( millisecondPart )?
                    int alt230=2;
                    int LA230_0 = input.LA(1);

                    if ( (LA230_0==MILLISECOND_PART) ) {
                        alt230=1;
                    }
                    switch (alt230) {
                        case 1 :
                            // EsperEPL2Ast.g:653:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4855);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:654:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4862);
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
    // EsperEPL2Ast.g:657:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:658:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:658:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4876); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4878);
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
    // EsperEPL2Ast.g:661:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:662:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:662:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4893); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4895);
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
    // EsperEPL2Ast.g:665:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:666:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:666:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4910); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4912);
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
    // EsperEPL2Ast.g:669:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:670:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:670:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4927); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4929);
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
    // EsperEPL2Ast.g:673:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:674:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:674:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4944); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4946);
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
    // EsperEPL2Ast.g:677:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:678:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:678:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4961); 
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
    // EsperEPL2Ast.g:681:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:682:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt232=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt232=1;
                }
                break;
            case LONG_TYPE:
                {
                alt232=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt232=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt232=4;
                }
                break;
            case STRING_TYPE:
                {
                alt232=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt232=6;
                }
                break;
            case NULL_TYPE:
                {
                alt232=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 232, 0, input);

                throw nvae;
            }

            switch (alt232) {
                case 1 :
                    // EsperEPL2Ast.g:682:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4977); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:683:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4986); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:684:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4995); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:685:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant5004); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:686:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant5020); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:687:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant5036); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:688:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant5049); 
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
    // EsperEPL2Ast.g:691:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:692:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000008L,0x001FC03800000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000008L,0x001FC03800000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L,0x001FC01800000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000008L,0x001FC01800000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000100000080000L,0x0000050802016000L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createIndexExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createSchemaExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule245 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onStreamExpr_in_onExpr270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000011C0000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr275 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onUpdateExpr_in_onExpr279 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr283 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000600000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr296 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_STREAM_in_onStreamExpr318 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onStreamExpr321 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onStreamExpr325 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_onStreamExpr328 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr347 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr349 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr351 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr354 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr357 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr374 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr376 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr379 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr401 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr404 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr407 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000030000600L,0x0000000000800000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr409 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000030000600L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr412 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000030000400L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr416 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000020000400L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr419 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr422 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr425 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr445 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr447 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr449 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr451 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput470 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr488 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr490 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr493 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr497 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr512 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr514 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr516 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L,0x0000020000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr519 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment534 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_onSetAssignment536 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment539 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom553 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom555 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom558 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr576 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr578 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000048L,0x0000000100008000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr581 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000048L,0x0000000100008000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr585 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000048L,0x0000000100008000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr588 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000048L,0x0000000100008000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr602 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr605 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr634 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr645 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr665 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr667 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr669 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_exprCol_in_createIndexExpr671 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert686 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert688 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList705 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList707 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x8000000000100000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList710 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x8000000000100000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList729 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList731 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList734 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement749 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement751 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement753 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_LBRACK_in_createColTypeListElement755 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement770 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement780 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement800 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement804 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement826 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement829 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr865 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createVariableExpr867 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr869 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr872 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr892 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr894 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000048L,0x0000380100008000L});
    public static final BitSet FOLLOW_variantList_in_createSchemaExpr897 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_createColTypeList_in_createSchemaExpr899 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000180000000000L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr910 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr912 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr923 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr925 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_exprCol_in_createSchemaExpr927 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIANT_LIST_in_variantList948 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_variantList950 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr968 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000100000080000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr974 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr979 = new BitSet(new long[]{0x0000000000000002L,0x0000004800000000L,0x00000BC030000600L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr984 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00000BC030000600L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr991 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00000BC030000400L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr999 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00000BC020000400L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr1006 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00000BC020000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr1013 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x0000000020000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr1020 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr1027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1044 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr1046 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr1055 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_exprCol_in_insertIntoExpr1058 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRCOL_in_exprCol1077 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1079 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1082 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause1100 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause1102 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1115 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1118 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1132 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1135 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000F400000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1138 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000F400000L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1160 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1167 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x1080000000000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1173 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x1080000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1179 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x1080000000000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1185 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x6000000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1191 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x6000000000000000L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1197 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1217 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1234 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1236 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1238 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1240 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1242 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1248 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1263 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1265 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1267 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1283 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1285 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1302 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1304 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1306 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1326 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1328 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0600000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1351 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1353 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1355 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1373 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1375 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0900000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1410 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1412 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000006800L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1414 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1443 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1445 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000006800L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1449 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1461 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1483 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1485 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1502 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1504 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1506 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1523 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1526 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1542 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1552 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1554 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1557 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1571 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1573 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1595 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1609 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1611 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1614 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1618 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1621 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1636 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1638 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1641 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1645 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1648 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1663 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1665 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1668 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1675 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1692 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1695 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1699 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1702 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1723 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1726 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000040L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1730 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000040L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1734 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000040L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1738 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000040L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1742 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1747 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1752 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_streamExpression1756 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1780 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1782 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1785 = new BitSet(new long[]{0x0000000037CC23C8L,0x0040000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1787 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1791 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1811 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1813 = new BitSet(new long[]{0x0000000000000008L,0x0080000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1832 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1834 = new BitSet(new long[]{0x0000000000000000L,0x0700000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1837 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1840 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1844 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1846 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1876 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1878 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1881 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1895 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1897 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1900 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1923 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1940 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1942 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1944 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1952 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1973 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1975 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1977 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1980 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1994 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1997 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr2014 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2016 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2018 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr2021 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause2043 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause2045 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause2063 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2065 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2068 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause2086 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2088 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2091 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2111 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2113 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2115 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2138 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2140 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2158 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2160 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0003C00000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2172 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2174 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2177 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2194 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2196 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0002000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2207 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2209 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2225 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2238 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2240 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2258 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2269 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L,0x0000000000000000L,0x00000000011C0000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2271 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2274 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2287 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2304 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2306 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0003C00000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2309 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2325 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2328 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x0003C00000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2330 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x0003C00000000000L,0x0000000000000018L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2334 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2336 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2340 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2343 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2361 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2363 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2365 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2367 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2369 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2371 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2373 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2390 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2392 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2405 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2407 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2420 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2422 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2434 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2436 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2458 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2483 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008FEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2492 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2523 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2525 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2527 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2530 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2544 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2546 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2548 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2551 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2565 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2567 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2569 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2581 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2583 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2585 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2597 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2599 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2601 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008FEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2610 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2615 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2628 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2630 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2632 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008FEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2641 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2646 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2659 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2661 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2672 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2704 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2713 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2718 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2726 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2731 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2736 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2742 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2775 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2804 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2812 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2814 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2816 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2829 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2835 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2840 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2855 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2857 = new BitSet(new long[]{0x0000000000000008L,0x0000500000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2868 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2888 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2917 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2920 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L,0x001FC00000000800L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2923 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L,0x001FC00000000800L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L,0x001FC00000000800L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2930 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2933 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2936 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2957 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2960 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2963 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2966 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2985 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2988 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2991 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2994 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3013 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator3016 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator3019 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator3022 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3043 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr3045 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3064 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr3066 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3085 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr3087 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3106 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3108 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3110 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3122 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3124 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3126 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3145 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3147 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3163 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x8000000000300000L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr3166 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3168 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3171 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3191 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000040L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3194 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3199 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3203 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3206 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3226 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3229 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3242 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3245 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3265 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000280L});
    public static final BitSet FOLLOW_set_in_inExpr3269 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3275 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3D00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3278 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3D00L});
    public static final BitSet FOLLOW_set_in_inExpr3282 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3297 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3299 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000280L});
    public static final BitSet FOLLOW_set_in_inExpr3301 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3307 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3D00L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3310 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3D00L});
    public static final BitSet FOLLOW_set_in_inExpr3314 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3331 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000280L});
    public static final BitSet FOLLOW_set_in_inExpr3333 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3339 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3341 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_set_in_inExpr3343 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3360 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000280L});
    public static final BitSet FOLLOW_set_in_inExpr3362 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3368 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3370 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_set_in_inExpr3372 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3395 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3397 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3399 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3401 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3412 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3414 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3416 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3419 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3439 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3441 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3443 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3446 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3459 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3461 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3463 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3466 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3487 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3489 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3500 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3502 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3504 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3523 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3526 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3530 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3541 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3544 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3548 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3559 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3563 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3567 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3581 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3584 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3588 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3602 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3606 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3620 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3624 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3635 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3638 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3642 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3653 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3656 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3660 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3672 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3674 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3676 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3679 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3694 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3696 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3698 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3711 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3715 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3717 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3730 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3732 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3734 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3737 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3751 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3753 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3755 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3767 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3769 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3781 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3801 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3804 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3825 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3827 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3829 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3841 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3843 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3845 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3857 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3859 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3861 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3874 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3876 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3888 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3890 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3892 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3903 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3905 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3907 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3918 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3920 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3922 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3933 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3935 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3937 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3949 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3951 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3953 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3956 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3977 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3980 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3984 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3987 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3992 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule4012 = new BitSet(new long[]{0x000000000000D800L,0x000D000000000000L,0x0000000000000030L,0x0000000808000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule4016 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice4030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice4035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice4045 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4047 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4061 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice4063 = new BitSet(new long[]{0x000000000000D800L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4065 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4079 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4081 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice4095 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4097 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4099 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4101 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice4103 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4119 = new BitSet(new long[]{0x000000000000D800L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4122 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4124 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4145 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions4147 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4166 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4168 = new BitSet(new long[]{0x000000000000D800L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4170 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4173 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4189 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4191 = new BitSet(new long[]{0x000000000000D800L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4193 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4196 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4212 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4214 = new BitSet(new long[]{0x000000000000D800L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4216 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4219 = new BitSet(new long[]{0x000000000000D808L,0x000D000000000000L,0x0000000000000030L,0x0000000008000000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4254 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4256 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000F00000001EE0L,0x0F06C0008003F000L,0x001FC0C400000EEEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4276 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4278 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4281 = new BitSet(new long[]{0x0000000037CC23C8L,0x0040000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4283 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4287 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4305 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4307 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0020000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4309 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4317 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4319 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4327 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4329 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4336 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4338 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4367 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4369 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4372 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x0F04C0008003F000L,0x001FC0C0000008EEL,0x000000003BCC3800L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4388 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4397 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4399 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4406 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4408 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4415 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4417 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4424 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4426 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4433 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4435 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4442 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4444 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4451 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4454 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4458 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4461 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4464 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4477 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4484 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4487 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4491 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4494 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4497 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4508 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4510 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4517 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4520 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4524 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4527 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4531 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4542 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4544 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4551 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4554 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4558 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4561 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L,0x0000000000000500L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4565 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4576 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4579 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4582 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4586 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4589 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4597 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4600 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4603 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x001FC00000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4607 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4641 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4643 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4645 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4664 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4666 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000003F00000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4669 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000003F00000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4688 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4690 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4697 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4699 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4701 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4708 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4710 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4712 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4725 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4727 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4734 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4736 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4738 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4745 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4747 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4749 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4774 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4776 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4792 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00F0000000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4795 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00E0000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4800 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4805 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4810 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4817 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00E0000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4820 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4825 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4837 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00C0000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4840 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4845 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4852 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0080000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4876 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4878 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4893 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4895 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4910 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4912 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4927 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4929 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4944 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4946 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4961 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4977 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant5004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant5020 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant5036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant5049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}