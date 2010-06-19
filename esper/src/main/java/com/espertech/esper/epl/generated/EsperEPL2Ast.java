// $ANTLR 3.1.1 EsperEPL2Ast.g 2010-06-10 08:11:54

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "SCHEMA", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "INDEX", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "MATCH_RECOGNIZE", "MEASURES", "DEFINE", "PARTITION", "MATCHES", "AFTER", "FOR", "WHILE", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "AFTER_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_INDEX_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_STREAM", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_UPDATE_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_COL_TYPE_LIST", "CREATE_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "FIRST_AGGREG", "LAST_AGGREG", "UPDATE_EXPR", "ON_SET_EXPR_ITEM", "CREATE_SCHEMA_EXPR", "CREATE_SCHEMA_EXPR_QUAL", "CREATE_SCHEMA_EXPR_INH", "VARIANT_LIST", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "MATCHREC_PATTERN", "MATCHREC_PATTERN_ATOM", "MATCHREC_PATTERN_CONCAT", "MATCHREC_PATTERN_ALTER", "MATCHREC_PATTERN_NESTED", "MATCHREC_AFTER_SKIP", "MATCHREC_INTERVAL", "MATCHREC_DEFINE", "MATCHREC_DEFINE_ITEM", "MATCHREC_MEASURES", "MATCHREC_MEASURE_ITEM", "MATCHREC_PARTITION", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "STAR", "BOR", "PLUS", "QUESTION", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=171;
    public static final int FLOAT_SUFFIX=326;
    public static final int STAR=269;
    public static final int NUMERIC_PARAM_LIST=111;
    public static final int ISTREAM=60;
    public static final int MOD=287;
    public static final int OUTERJOIN_EXPR=153;
    public static final int CREATE_COL_TYPE_LIST=226;
    public static final int BSR=308;
    public static final int LIB_FUNCTION=177;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=95;
    public static final int FULL_OUTERJOIN_EXPR=157;
    public static final int MATCHREC_PATTERN_CONCAT=251;
    public static final int RPAREN=266;
    public static final int LNOT=297;
    public static final int INC=301;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=274;
    public static final int BSR_ASSIGN=309;
    public static final int CAST_EXPR=206;
    public static final int MATCHES=106;
    public static final int STREAM_EXPR=152;
    public static final int TIMEPERIOD_SECONDS=92;
    public static final int NOT_EQUAL=279;
    public static final int METADATASQL=68;
    public static final int EVENT_FILTER_PROPERTY_EXPR=120;
    public static final int LAST_AGGREG=233;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=114;
    public static final int FOLLOWED_BY=291;
    public static final int HOUR_PART=182;
    public static final int RBRACK=268;
    public static final int MATCHREC_PATTERN_NESTED=253;
    public static final int MATCH_UNTIL_RANGE_CLOSED=224;
    public static final int GE=283;
    public static final int METHOD_JOIN_EXPR=220;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=119;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=118;
    public static final int ELSE=30;
    public static final int MINUS_ASSIGN=302;
    public static final int EVENT_FILTER_NOT_IN=130;
    public static final int INSERTINTO_STREAM_NAME=194;
    public static final int NUM_DOUBLE=247;
    public static final int UNARY_MINUS=178;
    public static final int TIMEPERIOD_MILLISEC=93;
    public static final int LCURLY=288;
    public static final int RETAINUNION=64;
    public static final int DBWHERE_CLAUSE=192;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=128;
    public static final int GROUP=44;
    public static final int EMAILAT=317;
    public static final int WS=318;
    public static final int SUBSELECT_GROUP_EXPR=198;
    public static final int ON_SELECT_INSERT_EXPR=215;
    public static final int ESCAPECHAR=292;
    public static final int EXPRCOL=175;
    public static final int SL_COMMENT=319;
    public static final int NULL_TYPE=246;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=222;
    public static final int GT=281;
    public static final int BNOT=298;
    public static final int WHERE_EXPR=139;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=154;
    public static final int LAND=315;
    public static final int NOT_REGEXP=189;
    public static final int MATCH_UNTIL_EXPR=221;
    public static final int EVENT_PROP_EXPR=161;
    public static final int LBRACK=267;
    public static final int VIEW_EXPR=136;
    public static final int ANNOTATION=229;
    public static final int LONG_TYPE=241;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=121;
    public static final int MATCHREC_PATTERN=249;
    public static final int TIMEPERIOD_SEC=90;
    public static final int ON_SELECT_EXPR=213;
    public static final int TICKED_STRING_LITERAL=293;
    public static final int MINUTE_PART=183;
    public static final int PATTERN_NOT_EXPR=117;
    public static final int SUM=18;
    public static final int SQL_NE=278;
    public static final int HexDigit=324;
    public static final int UPDATE_EXPR=234;
    public static final int LPAREN=265;
    public static final int IN_SUBSELECT_EXPR=200;
    public static final int AT=82;
    public static final int AS=17;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=96;
    public static final int THEN=32;
    public static final int MATCHREC_INTERVAL=255;
    public static final int NOT_IN_RANGE=196;
    public static final int OFFSET=100;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=69;
    public static final int SECOND_PART=184;
    public static final int MATCH_RECOGNIZE=102;
    public static final int IDENT=262;
    public static final int DATABASE_JOIN_EXPR=138;
    public static final int BXOR=277;
    public static final int PLUS=271;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=84;
    public static final int CREATE_SCHEMA_EXPR=236;
    public static final int EXISTS=71;
    public static final int EVENT_PROP_INDEXED=164;
    public static final int CREATE_INDEX_EXPR=207;
    public static final int TIMEPERIOD_MILLISECOND=94;
    public static final int EVAL_NOTEQUALS_EXPR=145;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=223;
    public static final int CREATE_VARIABLE_EXPR=219;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int MATCHREC_DEFINE=256;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=115;
    public static final int RIGHT_OUTERJOIN_EXPR=156;
    public static final int NUMBERSETSTAR=228;
    public static final int LAST_OPERATOR=203;
    public static final int PATTERN_FILTER_EXPR=116;
    public static final int EVAL_AND_EXPR=142;
    public static final int LEFT_OUTERJOIN_EXPR=155;
    public static final int EPL_EXPR=248;
    public static final int GROUP_BY_EXPR=158;
    public static final int SET=79;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=74;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=162;
    public static final int MINUS=285;
    public static final int SEMI=316;
    public static final int STAR_ASSIGN=304;
    public static final int VARIANT_LIST=239;
    public static final int FIRST_AGGREG=232;
    public static final int COLON=273;
    public static final int EVAL_EQUALS_GROUP_EXPR=146;
    public static final int BAND_ASSIGN=314;
    public static final int SCHEMA=62;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=172;
    public static final int VALUE_NULL=98;
    public static final int NOT_IN_SET=186;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=165;
    public static final int SL=310;
    public static final int NOT_IN_SUBSELECT_EXPR=201;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=134;
    public static final int SR=306;
    public static final int RCURLY=289;
    public static final int PLUS_ASSIGN=300;
    public static final int EXISTS_SUBSELECT_EXPR=199;
    public static final int DAY_PART=181;
    public static final int EVENT_FILTER_IN=129;
    public static final int DIV=286;
    public static final int OBJECT_PARAM_ORDERED_EXPR=113;
    public static final int OctalEscape=323;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=185;
    public static final int PRIOR=70;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=99;
    public static final int SELECTION_EXPR=149;
    public static final int LOR=284;
    public static final int CAST=75;
    public static final int LW=73;
    public static final int WILDCARD_SELECT=193;
    public static final int EXPONENT=325;
    public static final int LT=280;
    public static final int PATTERN_INCL_EXPR=137;
    public static final int WHILE=109;
    public static final int ORDER_BY_EXPR=159;
    public static final int BOOL_TYPE=245;
    public static final int MOD_ASSIGN=305;
    public static final int ANNOTATION_ARRAY=230;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=202;
    public static final int EQUALS=263;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=65;
    public static final int DIV_ASSIGN=299;
    public static final int SL_ASSIGN=311;
    public static final int PATTERN=66;
    public static final int SQL=67;
    public static final int FULL=40;
    public static final int WEEKDAY=72;
    public static final int MATCHREC_AFTER_SKIP=254;
    public static final int ESCAPE=10;
    public static final int INSERT=54;
    public static final int ON_UPDATE_EXPR=214;
    public static final int ARRAY_EXPR=180;
    public static final int CREATE_COL_TYPE=227;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=97;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=147;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=131;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=91;
    public static final int FLOAT_TYPE=242;
    public static final int SUBSELECT_EXPR=197;
    public static final int ANNOTATION_VALUE=231;
    public static final int CONCAT=176;
    public static final int NUMERIC_PARAM_RANGE=110;
    public static final int CLASS_IDENT=133;
    public static final int MATCHREC_PATTERN_ALTER=252;
    public static final int ON_EXPR=210;
    public static final int CREATE_WINDOW_EXPR=208;
    public static final int PROPERTY_SELECTION_STREAM=123;
    public static final int ON_DELETE_EXPR=212;
    public static final int ON=41;
    public static final int NUM_LONG=294;
    public static final int TIME_PERIOD=179;
    public static final int DOUBLE_TYPE=243;
    public static final int DELETE=77;
    public static final int INT_TYPE=240;
    public static final int MATCHREC_PARTITION=260;
    public static final int EVAL_BITWISE_EXPR=141;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=160;
    public static final int TIMEPERIOD_HOURS=87;
    public static final int VARIABLE=80;
    public static final int SUBSTITUTION=205;
    public static final int UNTIL=81;
    public static final int STRING_TYPE=244;
    public static final int ON_SET_EXPR=218;
    public static final int MATCHREC_DEFINE_ITEM=257;
    public static final int NUM_INT=290;
    public static final int STDDEV=24;
    public static final int CREATE_SCHEMA_EXPR_INH=238;
    public static final int ON_EXPR_FROM=217;
    public static final int NUM_FLOAT=295;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=122;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=321;
    public static final int WEEKDAY_OPERATOR=204;
    public static final int WHERE=16;
    public static final int DEC=303;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=112;
    public static final int BXOR_ASSIGN=312;
    public static final int AFTER_LIMIT_EXPR=170;
    public static final int ORDER=56;
    public static final int SNAPSHOT=78;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=167;
    public static final int EVENT_FILTER_PARAM=126;
    public static final int IRSTREAM=61;
    public static final int UPDATE=101;
    public static final int MAX=20;
    public static final int FOR=108;
    public static final int ON_STREAM=211;
    public static final int DEFINE=104;
    public static final int TIMEPERIOD_DAYS=85;
    public static final int EVENT_FILTER_RANGE=127;
    public static final int INDEX=83;
    public static final int ML_COMMENT=320;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=166;
    public static final int BOR_ASSIGN=313;
    public static final int COMMA=261;
    public static final int WHEN_LIMIT_EXPR=173;
    public static final int PARTITION=105;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=169;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=86;
    public static final int MATCHREC_MEASURE_ITEM=259;
    public static final int BOR=270;
    public static final int EQUAL=296;
    public static final int EVENT_FILTER_NOT_BETWEEN=132;
    public static final int IN_RANGE=195;
    public static final int DOT=264;
    public static final int CURRENT_TIMESTAMP=76;
    public static final int MATCHREC_MEASURES=258;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=124;
    public static final int INSERTINTO_EXPR=174;
    public static final int HAVING_EXPR=140;
    public static final int UNIDIRECTIONAL=63;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=225;
    public static final int EVAL_EQUALS_EXPR=144;
    public static final int TIMEPERIOD_MINUTES=89;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=188;
    public static final int EVENT_LIMIT_EXPR=168;
    public static final int NOT_BETWEEN=187;
    public static final int TIMEPERIOD_MINUTE=88;
    public static final int EVAL_OR_EXPR=143;
    public static final int ON_SELECT_INSERT_OUTPUT=216;
    public static final int AFTER=107;
    public static final int MEASURES=103;
    public static final int MATCHREC_PATTERN_ATOM=250;
    public static final int BAND=276;
    public static final int QUOTED_STRING_LITERAL=275;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=272;
    public static final int OBSERVER_EXPR=135;
    public static final int EVENT_FILTER_IDENT=125;
    public static final int CREATE_SCHEMA_EXPR_QUAL=237;
    public static final int EVENT_PROP_MAPPED=163;
    public static final int UnicodeEscape=322;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=190;
    public static final int SELECTION_ELEMENT_EXPR=150;
    public static final int CREATE_WINDOW_SELECT_EXPR=209;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=235;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=151;
    public static final int SR_ASSIGN=307;
    public static final int DBFROM_CLAUSE=191;
    public static final int LE=282;
    public static final int EVAL_IDENT=148;

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
    // EsperEPL2Ast.g:83:1: onExpr : ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:84:2: ( ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ) )
            // EsperEPL2Ast.g:84:4: ^(i= ON_EXPR onStreamExpr ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr271); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onStreamExpr_in_onExpr273);
            onStreamExpr();

            state._fsp--;

            // EsperEPL2Ast.g:85:3: ( onDeleteExpr | onUpdateExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr )
            int alt11=4;
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
            s=(CommonTree)match(input,ON_STREAM,FOLLOW_ON_STREAM_in_onStreamExpr321); 

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
                    pushFollow(FOLLOW_eventFilterExpr_in_onStreamExpr324);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:90:37: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onStreamExpr328);
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
                    match(input,IDENT,FOLLOW_IDENT_in_onStreamExpr331); 

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
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr350); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr352); 
            // EsperEPL2Ast.g:94:32: ( IDENT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==IDENT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // EsperEPL2Ast.g:94:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr354); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:94:39: ( onSetAssignment )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==ON_SET_EXPR_ITEM) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // EsperEPL2Ast.g:94:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr357);
            	    onSetAssignment();

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

            // EsperEPL2Ast.g:94:56: ( whereClause[false] )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE_EXPR) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:94:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr360);
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
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr377); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr379);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:98:32: ( whereClause[true] )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==WHERE_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:98:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr382);
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
            s=(CommonTree)match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr402); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:102:23: ( insertIntoExpr )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==INSERTINTO_EXPR) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:102:23: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr404);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:39: ( DISTINCT )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==DISTINCT) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:102:39: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_onSelectExpr407); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr410);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:102:63: ( onExprFrom )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==ON_EXPR_FROM) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // EsperEPL2Ast.g:102:63: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr412);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:75: ( whereClause[true] )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==WHERE_EXPR) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:102:75: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr415);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:94: ( groupByClause )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==GROUP_BY_EXPR) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:102:94: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr419);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:109: ( havingClause )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==HAVING_EXPR) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:102:109: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr422);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:123: ( orderByClause )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==ORDER_BY_EXPR) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:102:123: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr425);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:102:138: ( rowLimitClause )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==ROW_LIMIT_EXPR) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:102:138: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_onSelectExpr428);
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
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr448); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr450);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr452);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:106:78: ( whereClause[true] )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==WHERE_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:106:78: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr454);
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
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput471); 

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
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr491); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr493);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:114:34: ( onSetAssignment )*
            loop27:
            do {
                int alt27=2;
                int LA27_0 = input.LA(1);

                if ( (LA27_0==ON_SET_EXPR_ITEM) ) {
                    alt27=1;
                }


                switch (alt27) {
            	case 1 :
            	    // EsperEPL2Ast.g:114:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr496);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop27;
                }
            } while (true);

            // EsperEPL2Ast.g:114:53: ( whereClause[false] )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==WHERE_EXPR) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:114:53: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSetExpr500);
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
            match(input,ON_UPDATE_EXPR,FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr515); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onUpdateExpr517);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:118:32: ( onSetAssignment )+
            int cnt29=0;
            loop29:
            do {
                int alt29=2;
                int LA29_0 = input.LA(1);

                if ( (LA29_0==ON_SET_EXPR_ITEM) ) {
                    alt29=1;
                }


                switch (alt29) {
            	case 1 :
            	    // EsperEPL2Ast.g:118:32: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onUpdateExpr519);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt29 >= 1 ) break loop29;
                        EarlyExitException eee =
                            new EarlyExitException(29, input);
                        throw eee;
                }
                cnt29++;
            } while (true);

            // EsperEPL2Ast.g:118:49: ( whereClause[false] )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==WHERE_EXPR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:118:49: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_onUpdateExpr522);
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
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment537); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyExpr_in_onSetAssignment539);
            eventPropertyExpr(false);

            state._fsp--;

            pushFollow(FOLLOW_valueExpr_in_onSetAssignment542);
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
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom556); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom558); 
            // EsperEPL2Ast.g:126:25: ( IDENT )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==IDENT) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:126:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom561); 

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
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr579); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr581); 
            // EsperEPL2Ast.g:130:33: ( viewListExpr )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==VIEW_EXPR) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:130:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr584);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:130:49: ( RETAINUNION )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==RETAINUNION) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EsperEPL2Ast.g:130:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr588); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:130:62: ( RETAININTERSECTION )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==RETAININTERSECTION) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:130:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr591); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:131:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==CLASS_IDENT||LA36_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt36=1;
            }
            else if ( (LA36_0==CREATE_COL_TYPE_LIST) ) {
                alt36=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 36, 0, input);

                throw nvae;
            }
            switch (alt36) {
                case 1 :
                    // EsperEPL2Ast.g:132:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:132:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:132:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:132:6: ( createSelectionList )?
                    int alt35=2;
                    int LA35_0 = input.LA(1);

                    if ( (LA35_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt35=1;
                    }
                    switch (alt35) {
                        case 1 :
                            // EsperEPL2Ast.g:132:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr605);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr608); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:134:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:134:12: ( createColTypeList )
                    // EsperEPL2Ast.g:134:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr637);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:136:4: ( createWindowExprInsert )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==INSERT) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // EsperEPL2Ast.g:136:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr648);
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
            i=(CommonTree)match(input,CREATE_INDEX_EXPR,FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr668); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr670); 
            match(input,IDENT,FOLLOW_IDENT_in_createIndexExpr672); 
            pushFollow(FOLLOW_exprCol_in_createIndexExpr674);
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
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert689); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:145:13: ( valueExpr )?
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( ((LA38_0>=IN_SET && LA38_0<=REGEXP)||LA38_0==NOT_EXPR||(LA38_0>=SUM && LA38_0<=AVG)||(LA38_0>=COALESCE && LA38_0<=COUNT)||(LA38_0>=CASE && LA38_0<=CASE2)||(LA38_0>=PREVIOUS && LA38_0<=EXISTS)||(LA38_0>=INSTANCEOF && LA38_0<=CURRENT_TIMESTAMP)||(LA38_0>=EVAL_AND_EXPR && LA38_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA38_0==EVENT_PROP_EXPR||(LA38_0>=CONCAT && LA38_0<=LIB_FUNCTION)||LA38_0==ARRAY_EXPR||(LA38_0>=NOT_IN_SET && LA38_0<=NOT_REGEXP)||(LA38_0>=IN_RANGE && LA38_0<=SUBSELECT_EXPR)||(LA38_0>=EXISTS_SUBSELECT_EXPR && LA38_0<=NOT_IN_SUBSELECT_EXPR)||LA38_0==SUBSTITUTION||(LA38_0>=FIRST_AGGREG && LA38_0<=LAST_AGGREG)||(LA38_0>=INT_TYPE && LA38_0<=NULL_TYPE)||(LA38_0>=STAR && LA38_0<=PLUS)||(LA38_0>=BAND && LA38_0<=BXOR)||(LA38_0>=LT && LA38_0<=GE)||(LA38_0>=MINUS && LA38_0<=MOD)) ) {
                    alt38=1;
                }
                switch (alt38) {
                    case 1 :
                        // EsperEPL2Ast.g:145:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert691);
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
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList708); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList710);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:149:61: ( createSelectionListElement )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( (LA39_0==SELECTION_ELEMENT_EXPR||LA39_0==WILDCARD_SELECT) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // EsperEPL2Ast.g:149:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList713);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop39;
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
            match(input,CREATE_COL_TYPE_LIST,FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList732); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList734);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:153:52: ( createColTypeListElement )*
            loop40:
            do {
                int alt40=2;
                int LA40_0 = input.LA(1);

                if ( (LA40_0==CREATE_COL_TYPE) ) {
                    alt40=1;
                }


                switch (alt40) {
            	case 1 :
            	    // EsperEPL2Ast.g:153:53: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList737);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop40;
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
            match(input,CREATE_COL_TYPE,FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement752); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement754); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createColTypeListElement756); 
            // EsperEPL2Ast.g:157:40: ( LBRACK )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==LBRACK) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:157:40: LBRACK
                    {
                    match(input,LBRACK,FOLLOW_LBRACK_in_createColTypeListElement758); 

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
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==WILDCARD_SELECT) ) {
                alt44=1;
            }
            else if ( (LA44_0==SELECTION_ELEMENT_EXPR) ) {
                alt44=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 44, 0, input);

                throw nvae;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:161:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement773); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:162:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement783); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:162:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt43=2;
                    int LA43_0 = input.LA(1);

                    if ( (LA43_0==EVENT_PROP_EXPR) ) {
                        alt43=1;
                    }
                    else if ( ((LA43_0>=INT_TYPE && LA43_0<=NULL_TYPE)) ) {
                        alt43=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 43, 0, input);

                        throw nvae;
                    }
                    switch (alt43) {
                        case 1 :
                            // EsperEPL2Ast.g:163:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:163:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:163:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement803);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:163:41: ( IDENT )?
                            int alt42=2;
                            int LA42_0 = input.LA(1);

                            if ( (LA42_0==IDENT) ) {
                                alt42=1;
                            }
                            switch (alt42) {
                                case 1 :
                                    // EsperEPL2Ast.g:163:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement807); 

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
                            pushFollow(FOLLOW_constant_in_createSelectionListElement829);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement832); 

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
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr868); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createVariableExpr870); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr872); 
            // EsperEPL2Ast.g:169:47: ( valueExpr )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( ((LA45_0>=IN_SET && LA45_0<=REGEXP)||LA45_0==NOT_EXPR||(LA45_0>=SUM && LA45_0<=AVG)||(LA45_0>=COALESCE && LA45_0<=COUNT)||(LA45_0>=CASE && LA45_0<=CASE2)||(LA45_0>=PREVIOUS && LA45_0<=EXISTS)||(LA45_0>=INSTANCEOF && LA45_0<=CURRENT_TIMESTAMP)||(LA45_0>=EVAL_AND_EXPR && LA45_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA45_0==EVENT_PROP_EXPR||(LA45_0>=CONCAT && LA45_0<=LIB_FUNCTION)||LA45_0==ARRAY_EXPR||(LA45_0>=NOT_IN_SET && LA45_0<=NOT_REGEXP)||(LA45_0>=IN_RANGE && LA45_0<=SUBSELECT_EXPR)||(LA45_0>=EXISTS_SUBSELECT_EXPR && LA45_0<=NOT_IN_SUBSELECT_EXPR)||LA45_0==SUBSTITUTION||(LA45_0>=FIRST_AGGREG && LA45_0<=LAST_AGGREG)||(LA45_0>=INT_TYPE && LA45_0<=NULL_TYPE)||(LA45_0>=STAR && LA45_0<=PLUS)||(LA45_0>=BAND && LA45_0<=BXOR)||(LA45_0>=LT && LA45_0<=GE)||(LA45_0>=MINUS && LA45_0<=MOD)) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:169:48: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr875);
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
            s=(CommonTree)match(input,CREATE_SCHEMA_EXPR,FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr895); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr897); 
            // EsperEPL2Ast.g:173:33: ( variantList | ( createColTypeList )? )
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==VARIANT_LIST) ) {
                alt47=1;
            }
            else if ( (LA47_0==UP||LA47_0==CREATE_COL_TYPE_LIST||(LA47_0>=CREATE_SCHEMA_EXPR_QUAL && LA47_0<=CREATE_SCHEMA_EXPR_INH)) ) {
                alt47=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:173:34: variantList
                    {
                    pushFollow(FOLLOW_variantList_in_createSchemaExpr900);
                    variantList();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:173:46: ( createColTypeList )?
                    {
                    // EsperEPL2Ast.g:173:46: ( createColTypeList )?
                    int alt46=2;
                    int LA46_0 = input.LA(1);

                    if ( (LA46_0==CREATE_COL_TYPE_LIST) ) {
                        alt46=1;
                    }
                    switch (alt46) {
                        case 1 :
                            // EsperEPL2Ast.g:173:46: createColTypeList
                            {
                            pushFollow(FOLLOW_createColTypeList_in_createSchemaExpr902);
                            createColTypeList();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:174:5: ( ^( CREATE_SCHEMA_EXPR_QUAL IDENT ) )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==CREATE_SCHEMA_EXPR_QUAL) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:174:6: ^( CREATE_SCHEMA_EXPR_QUAL IDENT )
                    {
                    match(input,CREATE_SCHEMA_EXPR_QUAL,FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr913); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr915); 

                    match(input, Token.UP, null); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:175:5: ( ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol ) )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==CREATE_SCHEMA_EXPR_INH) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:175:6: ^( CREATE_SCHEMA_EXPR_INH IDENT exprCol )
                    {
                    match(input,CREATE_SCHEMA_EXPR_INH,FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr926); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_createSchemaExpr928); 
                    pushFollow(FOLLOW_exprCol_in_createSchemaExpr930);
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
            match(input,VARIANT_LIST,FOLLOW_VARIANT_LIST_in_variantList951); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:179:19: ( STAR | CLASS_IDENT )+
            int cnt50=0;
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==CLASS_IDENT||LA50_0==STAR) ) {
                    alt50=1;
                }


                switch (alt50) {
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
            	    if ( cnt50 >= 1 ) break loop50;
                        EarlyExitException eee =
                            new EarlyExitException(50, input);
                        throw eee;
                }
                cnt50++;
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
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==INSERTINTO_EXPR) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:183:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr971);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr977);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr982);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:186:3: ( matchRecogClause )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==MATCH_RECOGNIZE) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // EsperEPL2Ast.g:186:4: matchRecogClause
                    {
                    pushFollow(FOLLOW_matchRecogClause_in_selectExpr987);
                    matchRecogClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:187:3: ( whereClause[true] )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==WHERE_EXPR) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:187:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr994);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:188:3: ( groupByClause )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==GROUP_BY_EXPR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // EsperEPL2Ast.g:188:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr1002);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:189:3: ( havingClause )?
            int alt55=2;
            int LA55_0 = input.LA(1);

            if ( (LA55_0==HAVING_EXPR) ) {
                alt55=1;
            }
            switch (alt55) {
                case 1 :
                    // EsperEPL2Ast.g:189:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr1009);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:190:3: ( outputLimitExpr )?
            int alt56=2;
            int LA56_0 = input.LA(1);

            if ( ((LA56_0>=EVENT_LIMIT_EXPR && LA56_0<=CRONTAB_LIMIT_EXPR)||LA56_0==WHEN_LIMIT_EXPR) ) {
                alt56=1;
            }
            switch (alt56) {
                case 1 :
                    // EsperEPL2Ast.g:190:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr1016);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:191:3: ( orderByClause )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( (LA57_0==ORDER_BY_EXPR) ) {
                alt57=1;
            }
            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:191:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr1023);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:192:3: ( rowLimitClause )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==ROW_LIMIT_EXPR) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // EsperEPL2Ast.g:192:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr1030);
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
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1047); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:196:24: ( ISTREAM | RSTREAM )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( ((LA59_0>=RSTREAM && LA59_0<=ISTREAM)) ) {
                alt59=1;
            }
            switch (alt59) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr1058); 
            // EsperEPL2Ast.g:196:51: ( exprCol )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==EXPRCOL) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:196:52: exprCol
                    {
                    pushFollow(FOLLOW_exprCol_in_insertIntoExpr1061);
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
            match(input,EXPRCOL,FOLLOW_EXPRCOL_in_exprCol1080); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_exprCol1082); 
            // EsperEPL2Ast.g:200:20: ( IDENT )*
            loop61:
            do {
                int alt61=2;
                int LA61_0 = input.LA(1);

                if ( (LA61_0==IDENT) ) {
                    alt61=1;
                }


                switch (alt61) {
            	case 1 :
            	    // EsperEPL2Ast.g:200:21: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_exprCol1085); 

            	    }
            	    break;

            	default :
            	    break loop61;
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
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause1103); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:204:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( ((LA62_0>=RSTREAM && LA62_0<=IRSTREAM)) ) {
                alt62=1;
            }
            switch (alt62) {
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
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==DISTINCT) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:204:55: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_selectClause1118); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_selectClause1121);
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
            pushFollow(FOLLOW_streamExpression_in_fromClause1135);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:208:21: ( streamExpression ( outerJoin )* )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==STREAM_EXPR) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // EsperEPL2Ast.g:208:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause1138);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:208:39: ( outerJoin )*
            	    loop64:
            	    do {
            	        int alt64=2;
            	        int LA64_0 = input.LA(1);

            	        if ( ((LA64_0>=INNERJOIN_EXPR && LA64_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt64=1;
            	        }


            	        switch (alt64) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:208:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause1141);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop64;
            	        }
            	    } while (true);


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
    // $ANTLR end "fromClause"


    // $ANTLR start "forExpr"
    // EsperEPL2Ast.g:211:1: forExpr : ^(f= FOR IDENT ( valueExpr )* ) ;
    public final void forExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:212:2: ( ^(f= FOR IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:212:4: ^(f= FOR IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,FOR,FOLLOW_FOR_in_forExpr1161); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_forExpr1163); 
            // EsperEPL2Ast.g:212:18: ( valueExpr )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=IN_SET && LA66_0<=REGEXP)||LA66_0==NOT_EXPR||(LA66_0>=SUM && LA66_0<=AVG)||(LA66_0>=COALESCE && LA66_0<=COUNT)||(LA66_0>=CASE && LA66_0<=CASE2)||(LA66_0>=PREVIOUS && LA66_0<=EXISTS)||(LA66_0>=INSTANCEOF && LA66_0<=CURRENT_TIMESTAMP)||(LA66_0>=EVAL_AND_EXPR && LA66_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA66_0==EVENT_PROP_EXPR||(LA66_0>=CONCAT && LA66_0<=LIB_FUNCTION)||LA66_0==ARRAY_EXPR||(LA66_0>=NOT_IN_SET && LA66_0<=NOT_REGEXP)||(LA66_0>=IN_RANGE && LA66_0<=SUBSELECT_EXPR)||(LA66_0>=EXISTS_SUBSELECT_EXPR && LA66_0<=NOT_IN_SUBSELECT_EXPR)||LA66_0==SUBSTITUTION||(LA66_0>=FIRST_AGGREG && LA66_0<=LAST_AGGREG)||(LA66_0>=INT_TYPE && LA66_0<=NULL_TYPE)||(LA66_0>=STAR && LA66_0<=PLUS)||(LA66_0>=BAND && LA66_0<=BXOR)||(LA66_0>=LT && LA66_0<=GE)||(LA66_0>=MINUS && LA66_0<=MOD)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // EsperEPL2Ast.g:212:18: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_forExpr1165);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop66;
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
    // EsperEPL2Ast.g:215:1: matchRecogClause : ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) ;
    public final void matchRecogClause() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:216:2: ( ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine ) )
            // EsperEPL2Ast.g:216:4: ^(m= MATCH_RECOGNIZE ( matchRecogPartitionBy )? matchRecogMeasures ( ALL )? ( matchRecogMatchesAfterSkip )? matchRecogPattern ( matchRecogMatchesInterval )? matchRecogDefine )
            {
            m=(CommonTree)match(input,MATCH_RECOGNIZE,FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1184); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:216:24: ( matchRecogPartitionBy )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==MATCHREC_PARTITION) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:216:24: matchRecogPartitionBy
                    {
                    pushFollow(FOLLOW_matchRecogPartitionBy_in_matchRecogClause1186);
                    matchRecogPartitionBy();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogMeasures_in_matchRecogClause1193);
            matchRecogMeasures();

            state._fsp--;

            // EsperEPL2Ast.g:218:4: ( ALL )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==ALL) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:218:4: ALL
                    {
                    match(input,ALL,FOLLOW_ALL_in_matchRecogClause1199); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:219:4: ( matchRecogMatchesAfterSkip )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==MATCHREC_AFTER_SKIP) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // EsperEPL2Ast.g:219:4: matchRecogMatchesAfterSkip
                    {
                    pushFollow(FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1205);
                    matchRecogMatchesAfterSkip();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogPattern_in_matchRecogClause1211);
            matchRecogPattern();

            state._fsp--;

            // EsperEPL2Ast.g:221:4: ( matchRecogMatchesInterval )?
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==MATCHREC_INTERVAL) ) {
                alt70=1;
            }
            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:221:4: matchRecogMatchesInterval
                    {
                    pushFollow(FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1217);
                    matchRecogMatchesInterval();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_matchRecogDefine_in_matchRecogClause1223);
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
    // EsperEPL2Ast.g:225:1: matchRecogPartitionBy : ^(p= MATCHREC_PARTITION ( valueExpr )+ ) ;
    public final void matchRecogPartitionBy() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:226:2: ( ^(p= MATCHREC_PARTITION ( valueExpr )+ ) )
            // EsperEPL2Ast.g:226:4: ^(p= MATCHREC_PARTITION ( valueExpr )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PARTITION,FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1241); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:226:27: ( valueExpr )+
            int cnt71=0;
            loop71:
            do {
                int alt71=2;
                int LA71_0 = input.LA(1);

                if ( ((LA71_0>=IN_SET && LA71_0<=REGEXP)||LA71_0==NOT_EXPR||(LA71_0>=SUM && LA71_0<=AVG)||(LA71_0>=COALESCE && LA71_0<=COUNT)||(LA71_0>=CASE && LA71_0<=CASE2)||(LA71_0>=PREVIOUS && LA71_0<=EXISTS)||(LA71_0>=INSTANCEOF && LA71_0<=CURRENT_TIMESTAMP)||(LA71_0>=EVAL_AND_EXPR && LA71_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA71_0==EVENT_PROP_EXPR||(LA71_0>=CONCAT && LA71_0<=LIB_FUNCTION)||LA71_0==ARRAY_EXPR||(LA71_0>=NOT_IN_SET && LA71_0<=NOT_REGEXP)||(LA71_0>=IN_RANGE && LA71_0<=SUBSELECT_EXPR)||(LA71_0>=EXISTS_SUBSELECT_EXPR && LA71_0<=NOT_IN_SUBSELECT_EXPR)||LA71_0==SUBSTITUTION||(LA71_0>=FIRST_AGGREG && LA71_0<=LAST_AGGREG)||(LA71_0>=INT_TYPE && LA71_0<=NULL_TYPE)||(LA71_0>=STAR && LA71_0<=PLUS)||(LA71_0>=BAND && LA71_0<=BXOR)||(LA71_0>=LT && LA71_0<=GE)||(LA71_0>=MINUS && LA71_0<=MOD)) ) {
                    alt71=1;
                }


                switch (alt71) {
            	case 1 :
            	    // EsperEPL2Ast.g:226:27: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_matchRecogPartitionBy1243);
            	    valueExpr();

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
    // EsperEPL2Ast.g:229:1: matchRecogMatchesAfterSkip : ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) ;
    public final void matchRecogMatchesAfterSkip() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:230:2: ( ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT ) )
            // EsperEPL2Ast.g:230:4: ^( MATCHREC_AFTER_SKIP IDENT IDENT IDENT ( IDENT | LAST ) IDENT )
            {
            match(input,MATCHREC_AFTER_SKIP,FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1260); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1262); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1264); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1266); 
            if ( input.LA(1)==LAST||input.LA(1)==IDENT ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1274); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:233:1: matchRecogMatchesInterval : ^( MATCHREC_INTERVAL IDENT timePeriod ) ;
    public final void matchRecogMatchesInterval() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:234:2: ( ^( MATCHREC_INTERVAL IDENT timePeriod ) )
            // EsperEPL2Ast.g:234:4: ^( MATCHREC_INTERVAL IDENT timePeriod )
            {
            match(input,MATCHREC_INTERVAL,FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1289); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogMatchesInterval1291); 
            pushFollow(FOLLOW_timePeriod_in_matchRecogMatchesInterval1293);
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
    // EsperEPL2Ast.g:237:1: matchRecogMeasures : ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) ;
    public final void matchRecogMeasures() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:238:2: ( ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* ) )
            // EsperEPL2Ast.g:238:4: ^(m= MATCHREC_MEASURES ( matchRecogMeasureListElement )* )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURES,FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1309); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:238:26: ( matchRecogMeasureListElement )*
                loop72:
                do {
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==MATCHREC_MEASURE_ITEM) ) {
                        alt72=1;
                    }


                    switch (alt72) {
                	case 1 :
                	    // EsperEPL2Ast.g:238:26: matchRecogMeasureListElement
                	    {
                	    pushFollow(FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1311);
                	    matchRecogMeasureListElement();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop72;
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
    // EsperEPL2Ast.g:241:1: matchRecogMeasureListElement : ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) ;
    public final void matchRecogMeasureListElement() throws RecognitionException {
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:242:2: ( ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? ) )
            // EsperEPL2Ast.g:242:4: ^(m= MATCHREC_MEASURE_ITEM valueExpr ( IDENT )? )
            {
            m=(CommonTree)match(input,MATCHREC_MEASURE_ITEM,FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1328); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogMeasureListElement1330);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:242:40: ( IDENT )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==IDENT) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:242:40: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_matchRecogMeasureListElement1332); 

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
    // EsperEPL2Ast.g:245:1: matchRecogPattern : ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) ;
    public final void matchRecogPattern() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:246:2: ( ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ ) )
            // EsperEPL2Ast.g:246:4: ^(p= MATCHREC_PATTERN ( matchRecogPatternAlteration )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN,FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1352); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:246:25: ( matchRecogPatternAlteration )+
            int cnt74=0;
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( ((LA74_0>=MATCHREC_PATTERN_CONCAT && LA74_0<=MATCHREC_PATTERN_ALTER)) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // EsperEPL2Ast.g:246:25: matchRecogPatternAlteration
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1354);
            	    matchRecogPatternAlteration();

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
    // EsperEPL2Ast.g:249:1: matchRecogPatternAlteration : ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) );
    public final void matchRecogPatternAlteration() throws RecognitionException {
        CommonTree o=null;

        try {
            // EsperEPL2Ast.g:250:2: ( matchRecogPatternConcat | ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ ) )
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==MATCHREC_PATTERN_CONCAT) ) {
                alt76=1;
            }
            else if ( (LA76_0==MATCHREC_PATTERN_ALTER) ) {
                alt76=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }
            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:250:4: matchRecogPatternConcat
                    {
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1369);
                    matchRecogPatternConcat();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:251:4: ^(o= MATCHREC_PATTERN_ALTER matchRecogPatternConcat ( matchRecogPatternConcat )+ )
                    {
                    o=(CommonTree)match(input,MATCHREC_PATTERN_ALTER,FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1377); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1379);
                    matchRecogPatternConcat();

                    state._fsp--;

                    // EsperEPL2Ast.g:251:55: ( matchRecogPatternConcat )+
                    int cnt75=0;
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==MATCHREC_PATTERN_CONCAT) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:251:55: matchRecogPatternConcat
                    	    {
                    	    pushFollow(FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1381);
                    	    matchRecogPatternConcat();

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
    // EsperEPL2Ast.g:254:1: matchRecogPatternConcat : ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) ;
    public final void matchRecogPatternConcat() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:255:2: ( ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ ) )
            // EsperEPL2Ast.g:255:4: ^(p= MATCHREC_PATTERN_CONCAT ( matchRecogPatternUnary )+ )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_CONCAT,FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1399); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:255:32: ( matchRecogPatternUnary )+
            int cnt77=0;
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( (LA77_0==MATCHREC_PATTERN_ATOM||LA77_0==MATCHREC_PATTERN_NESTED) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // EsperEPL2Ast.g:255:32: matchRecogPatternUnary
            	    {
            	    pushFollow(FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1401);
            	    matchRecogPatternUnary();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt77 >= 1 ) break loop77;
                        EarlyExitException eee =
                            new EarlyExitException(77, input);
                        throw eee;
                }
                cnt77++;
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
    // EsperEPL2Ast.g:258:1: matchRecogPatternUnary : ( matchRecogPatternNested | matchRecogPatternAtom );
    public final void matchRecogPatternUnary() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:259:2: ( matchRecogPatternNested | matchRecogPatternAtom )
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==MATCHREC_PATTERN_NESTED) ) {
                alt78=1;
            }
            else if ( (LA78_0==MATCHREC_PATTERN_ATOM) ) {
                alt78=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 78, 0, input);

                throw nvae;
            }
            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:259:4: matchRecogPatternNested
                    {
                    pushFollow(FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1416);
                    matchRecogPatternNested();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:260:4: matchRecogPatternAtom
                    {
                    pushFollow(FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1421);
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
    // EsperEPL2Ast.g:263:1: matchRecogPatternNested : ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) ;
    public final void matchRecogPatternNested() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:264:2: ( ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? ) )
            // EsperEPL2Ast.g:264:4: ^(p= MATCHREC_PATTERN_NESTED matchRecogPatternAlteration ( PLUS | STAR | QUESTION )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_NESTED,FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1436); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1438);
            matchRecogPatternAlteration();

            state._fsp--;

            // EsperEPL2Ast.g:264:60: ( PLUS | STAR | QUESTION )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==STAR||(LA79_0>=PLUS && LA79_0<=QUESTION)) ) {
                alt79=1;
            }
            switch (alt79) {
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
    // EsperEPL2Ast.g:267:1: matchRecogPatternAtom : ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) ;
    public final void matchRecogPatternAtom() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:268:2: ( ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? ) )
            // EsperEPL2Ast.g:268:4: ^(p= MATCHREC_PATTERN_ATOM IDENT ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )? )
            {
            p=(CommonTree)match(input,MATCHREC_PATTERN_ATOM,FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1469); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogPatternAtom1471); 
            // EsperEPL2Ast.g:268:36: ( ( PLUS | STAR | QUESTION ) ( QUESTION )? )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==STAR||(LA81_0>=PLUS && LA81_0<=QUESTION)) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:268:38: ( PLUS | STAR | QUESTION ) ( QUESTION )?
                    {
                    if ( input.LA(1)==STAR||(input.LA(1)>=PLUS && input.LA(1)<=QUESTION) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:268:63: ( QUESTION )?
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==QUESTION) ) {
                        alt80=1;
                    }
                    switch (alt80) {
                        case 1 :
                            // EsperEPL2Ast.g:268:63: QUESTION
                            {
                            match(input,QUESTION,FOLLOW_QUESTION_in_matchRecogPatternAtom1487); 

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
    // EsperEPL2Ast.g:271:1: matchRecogDefine : ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) ;
    public final void matchRecogDefine() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:272:2: ( ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ ) )
            // EsperEPL2Ast.g:272:4: ^(p= MATCHREC_DEFINE ( matchRecogDefineItem )+ )
            {
            p=(CommonTree)match(input,MATCHREC_DEFINE,FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1509); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:272:24: ( matchRecogDefineItem )+
            int cnt82=0;
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( (LA82_0==MATCHREC_DEFINE_ITEM) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // EsperEPL2Ast.g:272:24: matchRecogDefineItem
            	    {
            	    pushFollow(FOLLOW_matchRecogDefineItem_in_matchRecogDefine1511);
            	    matchRecogDefineItem();

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


            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:275:1: matchRecogDefineItem : ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) ;
    public final void matchRecogDefineItem() throws RecognitionException {
        CommonTree d=null;

        try {
            // EsperEPL2Ast.g:276:2: ( ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr ) )
            // EsperEPL2Ast.g:276:4: ^(d= MATCHREC_DEFINE_ITEM IDENT valueExpr )
            {
            d=(CommonTree)match(input,MATCHREC_DEFINE_ITEM,FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1528); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_matchRecogDefineItem1530); 
            pushFollow(FOLLOW_valueExpr_in_matchRecogDefineItem1532);
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
    // EsperEPL2Ast.g:280:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:281:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:281:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList1549);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:281:25: ( selectionListElement )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( ((LA83_0>=SELECTION_ELEMENT_EXPR && LA83_0<=SELECTION_STREAM)||LA83_0==WILDCARD_SELECT) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // EsperEPL2Ast.g:281:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList1552);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop83;
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
    // EsperEPL2Ast.g:284:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:285:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt86=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt86=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt86=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt86=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }

            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:285:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement1568); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:286:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1578); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1580);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:286:41: ( IDENT )?
                    int alt84=2;
                    int LA84_0 = input.LA(1);

                    if ( (LA84_0==IDENT) ) {
                        alt84=1;
                    }
                    switch (alt84) {
                        case 1 :
                            // EsperEPL2Ast.g:286:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1583); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:287:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1597); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1599); 
                    // EsperEPL2Ast.g:287:31: ( IDENT )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==IDENT) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // EsperEPL2Ast.g:287:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1602); 

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
    // EsperEPL2Ast.g:290:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:291:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:291:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1621);
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
    // EsperEPL2Ast.g:294:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:295:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt91=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt91=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt91=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt91=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt91=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }

            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:295:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1635); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1637);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1640);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:295:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( (LA87_0==EVENT_PROP_EXPR) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:295:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1644);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1647);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop87;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:296:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1662); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1664);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1667);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:296:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop88:
                    do {
                        int alt88=2;
                        int LA88_0 = input.LA(1);

                        if ( (LA88_0==EVENT_PROP_EXPR) ) {
                            alt88=1;
                        }


                        switch (alt88) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:296:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1671);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1674);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop88;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:297:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1689); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1691);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1694);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:297:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop89:
                    do {
                        int alt89=2;
                        int LA89_0 = input.LA(1);

                        if ( (LA89_0==EVENT_PROP_EXPR) ) {
                            alt89=1;
                        }


                        switch (alt89) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:297:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1698);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1701);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop89;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:298:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1716); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1718);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1721);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:298:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop90:
                    do {
                        int alt90=2;
                        int LA90_0 = input.LA(1);

                        if ( (LA90_0==EVENT_PROP_EXPR) ) {
                            alt90=1;
                        }


                        switch (alt90) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:298:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1725);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1728);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop90;
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
    // EsperEPL2Ast.g:301:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:302:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:302:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1749); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:302:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt92=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt92=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt92=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt92=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt92=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // EsperEPL2Ast.g:302:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1752);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:302:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1756);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:302:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1760);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:302:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1764);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:302:115: ( viewListExpr )?
            int alt93=2;
            int LA93_0 = input.LA(1);

            if ( (LA93_0==VIEW_EXPR) ) {
                alt93=1;
            }
            switch (alt93) {
                case 1 :
                    // EsperEPL2Ast.g:302:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1768);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:302:131: ( IDENT )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==IDENT) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // EsperEPL2Ast.g:302:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1773); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:302:140: ( UNIDIRECTIONAL )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==UNIDIRECTIONAL) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:302:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1778); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:302:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( ((LA96_0>=RETAINUNION && LA96_0<=RETAININTERSECTION)) ) {
                alt96=1;
            }
            switch (alt96) {
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
    // EsperEPL2Ast.g:305:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:306:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:306:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1806); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:306:27: ( IDENT )?
            int alt97=2;
            int LA97_0 = input.LA(1);

            if ( (LA97_0==IDENT) ) {
                alt97=1;
            }
            switch (alt97) {
                case 1 :
                    // EsperEPL2Ast.g:306:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1808); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1811); 
            // EsperEPL2Ast.g:306:46: ( propertyExpression )?
            int alt98=2;
            int LA98_0 = input.LA(1);

            if ( (LA98_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt98=1;
            }
            switch (alt98) {
                case 1 :
                    // EsperEPL2Ast.g:306:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1813);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:306:66: ( valueExpr )*
            loop99:
            do {
                int alt99=2;
                int LA99_0 = input.LA(1);

                if ( ((LA99_0>=IN_SET && LA99_0<=REGEXP)||LA99_0==NOT_EXPR||(LA99_0>=SUM && LA99_0<=AVG)||(LA99_0>=COALESCE && LA99_0<=COUNT)||(LA99_0>=CASE && LA99_0<=CASE2)||(LA99_0>=PREVIOUS && LA99_0<=EXISTS)||(LA99_0>=INSTANCEOF && LA99_0<=CURRENT_TIMESTAMP)||(LA99_0>=EVAL_AND_EXPR && LA99_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA99_0==EVENT_PROP_EXPR||(LA99_0>=CONCAT && LA99_0<=LIB_FUNCTION)||LA99_0==ARRAY_EXPR||(LA99_0>=NOT_IN_SET && LA99_0<=NOT_REGEXP)||(LA99_0>=IN_RANGE && LA99_0<=SUBSELECT_EXPR)||(LA99_0>=EXISTS_SUBSELECT_EXPR && LA99_0<=NOT_IN_SUBSELECT_EXPR)||LA99_0==SUBSTITUTION||(LA99_0>=FIRST_AGGREG && LA99_0<=LAST_AGGREG)||(LA99_0>=INT_TYPE && LA99_0<=NULL_TYPE)||(LA99_0>=STAR && LA99_0<=PLUS)||(LA99_0>=BAND && LA99_0<=BXOR)||(LA99_0>=LT && LA99_0<=GE)||(LA99_0>=MINUS && LA99_0<=MOD)) ) {
                    alt99=1;
                }


                switch (alt99) {
            	case 1 :
            	    // EsperEPL2Ast.g:306:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1817);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop99;
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
    // EsperEPL2Ast.g:309:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:310:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:310:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1837); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:310:34: ( propertyExpressionAtom )*
                loop100:
                do {
                    int alt100=2;
                    int LA100_0 = input.LA(1);

                    if ( (LA100_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt100=1;
                    }


                    switch (alt100) {
                	case 1 :
                	    // EsperEPL2Ast.g:310:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1839);
                	    propertyExpressionAtom();

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
    // EsperEPL2Ast.g:313:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:314:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:314:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1858); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:314:41: ( propertySelectionListElement )*
            loop101:
            do {
                int alt101=2;
                int LA101_0 = input.LA(1);

                if ( ((LA101_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA101_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt101=1;
                }


                switch (alt101) {
            	case 1 :
            	    // EsperEPL2Ast.g:314:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1860);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop101;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1863);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:314:96: ( IDENT )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==IDENT) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // EsperEPL2Ast.g:314:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1866); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1870); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:314:116: ( valueExpr )?
                int alt103=2;
                int LA103_0 = input.LA(1);

                if ( ((LA103_0>=IN_SET && LA103_0<=REGEXP)||LA103_0==NOT_EXPR||(LA103_0>=SUM && LA103_0<=AVG)||(LA103_0>=COALESCE && LA103_0<=COUNT)||(LA103_0>=CASE && LA103_0<=CASE2)||(LA103_0>=PREVIOUS && LA103_0<=EXISTS)||(LA103_0>=INSTANCEOF && LA103_0<=CURRENT_TIMESTAMP)||(LA103_0>=EVAL_AND_EXPR && LA103_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA103_0==EVENT_PROP_EXPR||(LA103_0>=CONCAT && LA103_0<=LIB_FUNCTION)||LA103_0==ARRAY_EXPR||(LA103_0>=NOT_IN_SET && LA103_0<=NOT_REGEXP)||(LA103_0>=IN_RANGE && LA103_0<=SUBSELECT_EXPR)||(LA103_0>=EXISTS_SUBSELECT_EXPR && LA103_0<=NOT_IN_SUBSELECT_EXPR)||LA103_0==SUBSTITUTION||(LA103_0>=FIRST_AGGREG && LA103_0<=LAST_AGGREG)||(LA103_0>=INT_TYPE && LA103_0<=NULL_TYPE)||(LA103_0>=STAR && LA103_0<=PLUS)||(LA103_0>=BAND && LA103_0<=BXOR)||(LA103_0>=LT && LA103_0<=GE)||(LA103_0>=MINUS && LA103_0<=MOD)) ) {
                    alt103=1;
                }
                switch (alt103) {
                    case 1 :
                        // EsperEPL2Ast.g:314:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1872);
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
    // EsperEPL2Ast.g:317:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:318:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt106=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt106=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt106=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt106=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 106, 0, input);

                throw nvae;
            }

            switch (alt106) {
                case 1 :
                    // EsperEPL2Ast.g:318:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1892); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:319:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1902); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1904);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:319:50: ( IDENT )?
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==IDENT) ) {
                        alt104=1;
                    }
                    switch (alt104) {
                        case 1 :
                            // EsperEPL2Ast.g:319:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1907); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:320:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1921); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1923); 
                    // EsperEPL2Ast.g:320:40: ( IDENT )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==IDENT) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // EsperEPL2Ast.g:320:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1926); 

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
    // EsperEPL2Ast.g:323:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:324:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:324:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1947); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1949);
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
    // EsperEPL2Ast.g:327:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:328:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:328:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1966); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1968); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:328:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( ((LA107_0>=STRING_LITERAL && LA107_0<=QUOTED_STRING_LITERAL)) ) {
                alt107=1;
            }
            switch (alt107) {
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
    // EsperEPL2Ast.g:331:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:332:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:332:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1999); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression2001); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression2003); 
            // EsperEPL2Ast.g:332:41: ( valueExpr )*
            loop108:
            do {
                int alt108=2;
                int LA108_0 = input.LA(1);

                if ( ((LA108_0>=IN_SET && LA108_0<=REGEXP)||LA108_0==NOT_EXPR||(LA108_0>=SUM && LA108_0<=AVG)||(LA108_0>=COALESCE && LA108_0<=COUNT)||(LA108_0>=CASE && LA108_0<=CASE2)||(LA108_0>=PREVIOUS && LA108_0<=EXISTS)||(LA108_0>=INSTANCEOF && LA108_0<=CURRENT_TIMESTAMP)||(LA108_0>=EVAL_AND_EXPR && LA108_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA108_0==EVENT_PROP_EXPR||(LA108_0>=CONCAT && LA108_0<=LIB_FUNCTION)||LA108_0==ARRAY_EXPR||(LA108_0>=NOT_IN_SET && LA108_0<=NOT_REGEXP)||(LA108_0>=IN_RANGE && LA108_0<=SUBSELECT_EXPR)||(LA108_0>=EXISTS_SUBSELECT_EXPR && LA108_0<=NOT_IN_SUBSELECT_EXPR)||LA108_0==SUBSTITUTION||(LA108_0>=FIRST_AGGREG && LA108_0<=LAST_AGGREG)||(LA108_0>=INT_TYPE && LA108_0<=NULL_TYPE)||(LA108_0>=STAR && LA108_0<=PLUS)||(LA108_0>=BAND && LA108_0<=BXOR)||(LA108_0>=LT && LA108_0<=GE)||(LA108_0>=MINUS && LA108_0<=MOD)) ) {
                    alt108=1;
                }


                switch (alt108) {
            	case 1 :
            	    // EsperEPL2Ast.g:332:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression2006);
            	    valueExpr();

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

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "methodJoinExpression"


    // $ANTLR start "viewListExpr"
    // EsperEPL2Ast.g:335:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:336:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:336:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr2020);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:336:13: ( viewExpr )*
            loop109:
            do {
                int alt109=2;
                int LA109_0 = input.LA(1);

                if ( (LA109_0==VIEW_EXPR) ) {
                    alt109=1;
                }


                switch (alt109) {
            	case 1 :
            	    // EsperEPL2Ast.g:336:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr2023);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop109;
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
    // EsperEPL2Ast.g:339:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:340:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:340:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr2040); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2042); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr2044); 
            // EsperEPL2Ast.g:340:30: ( valueExprWithTime )*
            loop110:
            do {
                int alt110=2;
                int LA110_0 = input.LA(1);

                if ( ((LA110_0>=IN_SET && LA110_0<=REGEXP)||LA110_0==NOT_EXPR||(LA110_0>=SUM && LA110_0<=AVG)||(LA110_0>=COALESCE && LA110_0<=COUNT)||(LA110_0>=CASE && LA110_0<=CASE2)||LA110_0==LAST||(LA110_0>=PREVIOUS && LA110_0<=EXISTS)||(LA110_0>=LW && LA110_0<=CURRENT_TIMESTAMP)||(LA110_0>=NUMERIC_PARAM_RANGE && LA110_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA110_0>=EVAL_AND_EXPR && LA110_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA110_0==EVENT_PROP_EXPR||(LA110_0>=CONCAT && LA110_0<=LIB_FUNCTION)||(LA110_0>=TIME_PERIOD && LA110_0<=ARRAY_EXPR)||(LA110_0>=NOT_IN_SET && LA110_0<=NOT_REGEXP)||(LA110_0>=IN_RANGE && LA110_0<=SUBSELECT_EXPR)||(LA110_0>=EXISTS_SUBSELECT_EXPR && LA110_0<=NOT_IN_SUBSELECT_EXPR)||(LA110_0>=LAST_OPERATOR && LA110_0<=SUBSTITUTION)||LA110_0==NUMBERSETSTAR||(LA110_0>=FIRST_AGGREG && LA110_0<=LAST_AGGREG)||(LA110_0>=INT_TYPE && LA110_0<=NULL_TYPE)||(LA110_0>=STAR && LA110_0<=PLUS)||(LA110_0>=BAND && LA110_0<=BXOR)||(LA110_0>=LT && LA110_0<=GE)||(LA110_0>=MINUS && LA110_0<=MOD)) ) {
                    alt110=1;
                }


                switch (alt110) {
            	case 1 :
            	    // EsperEPL2Ast.g:340:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr2047);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop110;
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
    // EsperEPL2Ast.g:343:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:344:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:344:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause2069); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause2071);
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
    // EsperEPL2Ast.g:347:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:348:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:348:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause2089); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause2091);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:348:32: ( valueExpr )*
            loop111:
            do {
                int alt111=2;
                int LA111_0 = input.LA(1);

                if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA111_0==EVENT_PROP_EXPR||(LA111_0>=CONCAT && LA111_0<=LIB_FUNCTION)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=SUBSELECT_EXPR)||(LA111_0>=EXISTS_SUBSELECT_EXPR && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=FIRST_AGGREG && LA111_0<=LAST_AGGREG)||(LA111_0>=INT_TYPE && LA111_0<=NULL_TYPE)||(LA111_0>=STAR && LA111_0<=PLUS)||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=MINUS && LA111_0<=MOD)) ) {
                    alt111=1;
                }


                switch (alt111) {
            	case 1 :
            	    // EsperEPL2Ast.g:348:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause2094);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop111;
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
    // EsperEPL2Ast.g:351:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:352:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:352:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause2112); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause2114);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:352:35: ( orderByElement )*
            loop112:
            do {
                int alt112=2;
                int LA112_0 = input.LA(1);

                if ( (LA112_0==ORDER_ELEMENT_EXPR) ) {
                    alt112=1;
                }


                switch (alt112) {
            	case 1 :
            	    // EsperEPL2Ast.g:352:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause2117);
            	    orderByElement();

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

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "orderByClause"


    // $ANTLR start "orderByElement"
    // EsperEPL2Ast.g:355:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:356:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:356:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2137); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement2139);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:356:38: ( ASC | DESC )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( ((LA113_0>=ASC && LA113_0<=DESC)) ) {
                alt113=1;
            }
            switch (alt113) {
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
    // EsperEPL2Ast.g:359:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:360:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:360:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause2164); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause2166);
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
    // EsperEPL2Ast.g:363:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;
        CommonTree after=null;

        try {
            // EsperEPL2Ast.g:364:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? ) | ^(after= AFTER_LIMIT_EXPR outputLimitAfter ) )
            int alt124=5;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt124=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt124=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt124=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt124=4;
                }
                break;
            case AFTER_LIMIT_EXPR:
                {
                alt124=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 124, 0, input);

                throw nvae;
            }

            switch (alt124) {
                case 1 :
                    // EsperEPL2Ast.g:364:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ( outputLimitAfter )? )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2184); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:364:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( (LA114_0==ALL||(LA114_0>=FIRST && LA114_0<=LAST)||LA114_0==SNAPSHOT) ) {
                        alt114=1;
                    }
                    switch (alt114) {
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

                    // EsperEPL2Ast.g:364:52: ( number | IDENT )
                    int alt115=2;
                    int LA115_0 = input.LA(1);

                    if ( ((LA115_0>=INT_TYPE && LA115_0<=DOUBLE_TYPE)) ) {
                        alt115=1;
                    }
                    else if ( (LA115_0==IDENT) ) {
                        alt115=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 115, 0, input);

                        throw nvae;
                    }
                    switch (alt115) {
                        case 1 :
                            // EsperEPL2Ast.g:364:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr2198);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:364:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr2200); 

                            }
                            break;

                    }

                    // EsperEPL2Ast.g:364:67: ( outputLimitAfter )?
                    int alt116=2;
                    int LA116_0 = input.LA(1);

                    if ( (LA116_0==AFTER) ) {
                        alt116=1;
                    }
                    switch (alt116) {
                        case 1 :
                            // EsperEPL2Ast.g:364:67: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2203);
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
                    // EsperEPL2Ast.g:365:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ( outputLimitAfter )? )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2220); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:365:34: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr2233);
                    timePeriod();

                    state._fsp--;

                    // EsperEPL2Ast.g:365:72: ( outputLimitAfter )?
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( (LA118_0==AFTER) ) {
                        alt118=1;
                    }
                    switch (alt118) {
                        case 1 :
                            // EsperEPL2Ast.g:365:72: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2235);
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
                    // EsperEPL2Ast.g:366:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ( outputLimitAfter )? )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2251); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:366:33: ( ALL | FIRST | LAST | SNAPSHOT )?
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2264);
                    crontabLimitParameterSet();

                    state._fsp--;

                    // EsperEPL2Ast.g:366:85: ( outputLimitAfter )?
                    int alt120=2;
                    int LA120_0 = input.LA(1);

                    if ( (LA120_0==AFTER) ) {
                        alt120=1;
                    }
                    switch (alt120) {
                        case 1 :
                            // EsperEPL2Ast.g:366:85: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2266);
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
                    // EsperEPL2Ast.g:367:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ( outputLimitAfter )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2282); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:367:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt121=2;
                    int LA121_0 = input.LA(1);

                    if ( (LA121_0==ALL||(LA121_0>=FIRST && LA121_0<=LAST)||LA121_0==SNAPSHOT) ) {
                        alt121=1;
                    }
                    switch (alt121) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr2295);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:367:67: ( onSetExpr )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==ON_SET_EXPR) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // EsperEPL2Ast.g:367:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr2297);
                            onSetExpr();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:367:78: ( outputLimitAfter )?
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( (LA123_0==AFTER) ) {
                        alt123=1;
                    }
                    switch (alt123) {
                        case 1 :
                            // EsperEPL2Ast.g:367:78: outputLimitAfter
                            {
                            pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2300);
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
                    // EsperEPL2Ast.g:368:4: ^(after= AFTER_LIMIT_EXPR outputLimitAfter )
                    {
                    after=(CommonTree)match(input,AFTER_LIMIT_EXPR,FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2313); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_outputLimitAfter_in_outputLimitExpr2315);
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
    // EsperEPL2Ast.g:371:1: outputLimitAfter : ^( AFTER ( timePeriod )? ( number )? ) ;
    public final void outputLimitAfter() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:372:2: ( ^( AFTER ( timePeriod )? ( number )? ) )
            // EsperEPL2Ast.g:372:4: ^( AFTER ( timePeriod )? ( number )? )
            {
            match(input,AFTER,FOLLOW_AFTER_in_outputLimitAfter2330); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:372:12: ( timePeriod )?
                int alt125=2;
                int LA125_0 = input.LA(1);

                if ( (LA125_0==TIME_PERIOD) ) {
                    alt125=1;
                }
                switch (alt125) {
                    case 1 :
                        // EsperEPL2Ast.g:372:12: timePeriod
                        {
                        pushFollow(FOLLOW_timePeriod_in_outputLimitAfter2332);
                        timePeriod();

                        state._fsp--;


                        }
                        break;

                }

                // EsperEPL2Ast.g:372:24: ( number )?
                int alt126=2;
                int LA126_0 = input.LA(1);

                if ( ((LA126_0>=INT_TYPE && LA126_0<=DOUBLE_TYPE)) ) {
                    alt126=1;
                }
                switch (alt126) {
                    case 1 :
                        // EsperEPL2Ast.g:372:24: number
                        {
                        pushFollow(FOLLOW_number_in_outputLimitAfter2335);
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
    // EsperEPL2Ast.g:375:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:376:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:376:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2351); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:376:23: ( number | IDENT )
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
                    // EsperEPL2Ast.g:376:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2354);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:376:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2356); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:376:38: ( number | IDENT )?
            int alt128=3;
            int LA128_0 = input.LA(1);

            if ( ((LA128_0>=INT_TYPE && LA128_0<=DOUBLE_TYPE)) ) {
                alt128=1;
            }
            else if ( (LA128_0==IDENT) ) {
                alt128=2;
            }
            switch (alt128) {
                case 1 :
                    // EsperEPL2Ast.g:376:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause2360);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:376:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause2362); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:376:54: ( COMMA )?
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( (LA129_0==COMMA) ) {
                alt129=1;
            }
            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:376:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause2366); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:376:61: ( OFFSET )?
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==OFFSET) ) {
                alt130=1;
            }
            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:376:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause2369); 

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
    // EsperEPL2Ast.g:379:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:380:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:380:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2387); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2389);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2391);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2393);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2395);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2397);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:380:121: ( valueExprWithTime )?
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( ((LA131_0>=IN_SET && LA131_0<=REGEXP)||LA131_0==NOT_EXPR||(LA131_0>=SUM && LA131_0<=AVG)||(LA131_0>=COALESCE && LA131_0<=COUNT)||(LA131_0>=CASE && LA131_0<=CASE2)||LA131_0==LAST||(LA131_0>=PREVIOUS && LA131_0<=EXISTS)||(LA131_0>=LW && LA131_0<=CURRENT_TIMESTAMP)||(LA131_0>=NUMERIC_PARAM_RANGE && LA131_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA131_0>=EVAL_AND_EXPR && LA131_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA131_0==EVENT_PROP_EXPR||(LA131_0>=CONCAT && LA131_0<=LIB_FUNCTION)||(LA131_0>=TIME_PERIOD && LA131_0<=ARRAY_EXPR)||(LA131_0>=NOT_IN_SET && LA131_0<=NOT_REGEXP)||(LA131_0>=IN_RANGE && LA131_0<=SUBSELECT_EXPR)||(LA131_0>=EXISTS_SUBSELECT_EXPR && LA131_0<=NOT_IN_SUBSELECT_EXPR)||(LA131_0>=LAST_OPERATOR && LA131_0<=SUBSTITUTION)||LA131_0==NUMBERSETSTAR||(LA131_0>=FIRST_AGGREG && LA131_0<=LAST_AGGREG)||(LA131_0>=INT_TYPE && LA131_0<=NULL_TYPE)||(LA131_0>=STAR && LA131_0<=PLUS)||(LA131_0>=BAND && LA131_0<=BXOR)||(LA131_0>=LT && LA131_0<=GE)||(LA131_0>=MINUS && LA131_0<=MOD)) ) {
                alt131=1;
            }
            switch (alt131) {
                case 1 :
                    // EsperEPL2Ast.g:380:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2399);
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
    // EsperEPL2Ast.g:383:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:384:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt132=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt132=1;
                }
                break;
            case GT:
                {
                alt132=2;
                }
                break;
            case LE:
                {
                alt132=3;
                }
                break;
            case GE:
                {
                alt132=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 132, 0, input);

                throw nvae;
            }

            switch (alt132) {
                case 1 :
                    // EsperEPL2Ast.g:384:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr2416); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2418);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:385:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr2431); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2433);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:386:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr2446); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2448);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:387:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr2460); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr2462);
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
    // EsperEPL2Ast.g:390:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:391:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:391:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:391:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:392:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue2484);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:393:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt135=2;
            int LA135_0 = input.LA(1);

            if ( ((LA135_0>=IN_SET && LA135_0<=REGEXP)||LA135_0==NOT_EXPR||(LA135_0>=SUM && LA135_0<=AVG)||(LA135_0>=COALESCE && LA135_0<=COUNT)||(LA135_0>=CASE && LA135_0<=CASE2)||(LA135_0>=PREVIOUS && LA135_0<=EXISTS)||(LA135_0>=INSTANCEOF && LA135_0<=CURRENT_TIMESTAMP)||(LA135_0>=EVAL_AND_EXPR && LA135_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA135_0==EVENT_PROP_EXPR||(LA135_0>=CONCAT && LA135_0<=LIB_FUNCTION)||LA135_0==ARRAY_EXPR||(LA135_0>=NOT_IN_SET && LA135_0<=NOT_REGEXP)||(LA135_0>=IN_RANGE && LA135_0<=SUBSELECT_EXPR)||(LA135_0>=EXISTS_SUBSELECT_EXPR && LA135_0<=NOT_IN_SUBSELECT_EXPR)||LA135_0==SUBSTITUTION||(LA135_0>=FIRST_AGGREG && LA135_0<=LAST_AGGREG)||(LA135_0>=INT_TYPE && LA135_0<=NULL_TYPE)||(LA135_0>=STAR && LA135_0<=PLUS)||(LA135_0>=BAND && LA135_0<=BXOR)||(LA135_0>=LT && LA135_0<=GE)||(LA135_0>=MINUS && LA135_0<=MOD)) ) {
                alt135=1;
            }
            else if ( ((LA135_0>=ALL && LA135_0<=SOME)) ) {
                alt135=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 135, 0, input);

                throw nvae;
            }
            switch (alt135) {
                case 1 :
                    // EsperEPL2Ast.g:393:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2494);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:395:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:395:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( (LA134_0==UP||(LA134_0>=IN_SET && LA134_0<=REGEXP)||LA134_0==NOT_EXPR||(LA134_0>=SUM && LA134_0<=AVG)||(LA134_0>=COALESCE && LA134_0<=COUNT)||(LA134_0>=CASE && LA134_0<=CASE2)||(LA134_0>=PREVIOUS && LA134_0<=EXISTS)||(LA134_0>=INSTANCEOF && LA134_0<=CURRENT_TIMESTAMP)||(LA134_0>=EVAL_AND_EXPR && LA134_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA134_0==EVENT_PROP_EXPR||(LA134_0>=CONCAT && LA134_0<=LIB_FUNCTION)||LA134_0==ARRAY_EXPR||(LA134_0>=NOT_IN_SET && LA134_0<=NOT_REGEXP)||(LA134_0>=IN_RANGE && LA134_0<=SUBSELECT_EXPR)||(LA134_0>=EXISTS_SUBSELECT_EXPR && LA134_0<=NOT_IN_SUBSELECT_EXPR)||LA134_0==SUBSTITUTION||(LA134_0>=FIRST_AGGREG && LA134_0<=LAST_AGGREG)||(LA134_0>=INT_TYPE && LA134_0<=NULL_TYPE)||(LA134_0>=STAR && LA134_0<=PLUS)||(LA134_0>=BAND && LA134_0<=BXOR)||(LA134_0>=LT && LA134_0<=GE)||(LA134_0>=MINUS && LA134_0<=MOD)) ) {
                        alt134=1;
                    }
                    else if ( (LA134_0==SUBSELECT_GROUP_EXPR) ) {
                        alt134=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 134, 0, input);

                        throw nvae;
                    }
                    switch (alt134) {
                        case 1 :
                            // EsperEPL2Ast.g:395:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:395:22: ( valueExpr )*
                            loop133:
                            do {
                                int alt133=2;
                                int LA133_0 = input.LA(1);

                                if ( ((LA133_0>=IN_SET && LA133_0<=REGEXP)||LA133_0==NOT_EXPR||(LA133_0>=SUM && LA133_0<=AVG)||(LA133_0>=COALESCE && LA133_0<=COUNT)||(LA133_0>=CASE && LA133_0<=CASE2)||(LA133_0>=PREVIOUS && LA133_0<=EXISTS)||(LA133_0>=INSTANCEOF && LA133_0<=CURRENT_TIMESTAMP)||(LA133_0>=EVAL_AND_EXPR && LA133_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA133_0==EVENT_PROP_EXPR||(LA133_0>=CONCAT && LA133_0<=LIB_FUNCTION)||LA133_0==ARRAY_EXPR||(LA133_0>=NOT_IN_SET && LA133_0<=NOT_REGEXP)||(LA133_0>=IN_RANGE && LA133_0<=SUBSELECT_EXPR)||(LA133_0>=EXISTS_SUBSELECT_EXPR && LA133_0<=NOT_IN_SUBSELECT_EXPR)||LA133_0==SUBSTITUTION||(LA133_0>=FIRST_AGGREG && LA133_0<=LAST_AGGREG)||(LA133_0>=INT_TYPE && LA133_0<=NULL_TYPE)||(LA133_0>=STAR && LA133_0<=PLUS)||(LA133_0>=BAND && LA133_0<=BXOR)||(LA133_0>=LT && LA133_0<=GE)||(LA133_0>=MINUS && LA133_0<=MOD)) ) {
                                    alt133=1;
                                }


                                switch (alt133) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:395:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue2518);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop133;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:395:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue2523);
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
    // EsperEPL2Ast.g:400:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:401:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt142=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt142=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt142=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt142=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt142=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt142=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt142=6;
                }
                break;
            case NOT_EXPR:
                {
                alt142=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt142=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 142, 0, input);

                throw nvae;
            }

            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:401:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2549); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2551);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2553);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:401:42: ( valueExpr )*
                    loop136:
                    do {
                        int alt136=2;
                        int LA136_0 = input.LA(1);

                        if ( ((LA136_0>=IN_SET && LA136_0<=REGEXP)||LA136_0==NOT_EXPR||(LA136_0>=SUM && LA136_0<=AVG)||(LA136_0>=COALESCE && LA136_0<=COUNT)||(LA136_0>=CASE && LA136_0<=CASE2)||(LA136_0>=PREVIOUS && LA136_0<=EXISTS)||(LA136_0>=INSTANCEOF && LA136_0<=CURRENT_TIMESTAMP)||(LA136_0>=EVAL_AND_EXPR && LA136_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA136_0==EVENT_PROP_EXPR||(LA136_0>=CONCAT && LA136_0<=LIB_FUNCTION)||LA136_0==ARRAY_EXPR||(LA136_0>=NOT_IN_SET && LA136_0<=NOT_REGEXP)||(LA136_0>=IN_RANGE && LA136_0<=SUBSELECT_EXPR)||(LA136_0>=EXISTS_SUBSELECT_EXPR && LA136_0<=NOT_IN_SUBSELECT_EXPR)||LA136_0==SUBSTITUTION||(LA136_0>=FIRST_AGGREG && LA136_0<=LAST_AGGREG)||(LA136_0>=INT_TYPE && LA136_0<=NULL_TYPE)||(LA136_0>=STAR && LA136_0<=PLUS)||(LA136_0>=BAND && LA136_0<=BXOR)||(LA136_0>=LT && LA136_0<=GE)||(LA136_0>=MINUS && LA136_0<=MOD)) ) {
                            alt136=1;
                        }


                        switch (alt136) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:401:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2556);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop136;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:402:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2570); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2572);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2574);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:402:43: ( valueExpr )*
                    loop137:
                    do {
                        int alt137=2;
                        int LA137_0 = input.LA(1);

                        if ( ((LA137_0>=IN_SET && LA137_0<=REGEXP)||LA137_0==NOT_EXPR||(LA137_0>=SUM && LA137_0<=AVG)||(LA137_0>=COALESCE && LA137_0<=COUNT)||(LA137_0>=CASE && LA137_0<=CASE2)||(LA137_0>=PREVIOUS && LA137_0<=EXISTS)||(LA137_0>=INSTANCEOF && LA137_0<=CURRENT_TIMESTAMP)||(LA137_0>=EVAL_AND_EXPR && LA137_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA137_0==EVENT_PROP_EXPR||(LA137_0>=CONCAT && LA137_0<=LIB_FUNCTION)||LA137_0==ARRAY_EXPR||(LA137_0>=NOT_IN_SET && LA137_0<=NOT_REGEXP)||(LA137_0>=IN_RANGE && LA137_0<=SUBSELECT_EXPR)||(LA137_0>=EXISTS_SUBSELECT_EXPR && LA137_0<=NOT_IN_SUBSELECT_EXPR)||LA137_0==SUBSTITUTION||(LA137_0>=FIRST_AGGREG && LA137_0<=LAST_AGGREG)||(LA137_0>=INT_TYPE && LA137_0<=NULL_TYPE)||(LA137_0>=STAR && LA137_0<=PLUS)||(LA137_0>=BAND && LA137_0<=BXOR)||(LA137_0>=LT && LA137_0<=GE)||(LA137_0>=MINUS && LA137_0<=MOD)) ) {
                            alt137=1;
                        }


                        switch (alt137) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:402:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2577);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop137;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:403:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2591); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2593);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2595);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:404:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2607); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2609);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2611);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:405:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2623); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2625);
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

                    // EsperEPL2Ast.g:405:58: ( ( valueExpr )* | subSelectGroupExpr )
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
                            // EsperEPL2Ast.g:405:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:405:59: ( valueExpr )*
                            loop138:
                            do {
                                int alt138=2;
                                int LA138_0 = input.LA(1);

                                if ( ((LA138_0>=IN_SET && LA138_0<=REGEXP)||LA138_0==NOT_EXPR||(LA138_0>=SUM && LA138_0<=AVG)||(LA138_0>=COALESCE && LA138_0<=COUNT)||(LA138_0>=CASE && LA138_0<=CASE2)||(LA138_0>=PREVIOUS && LA138_0<=EXISTS)||(LA138_0>=INSTANCEOF && LA138_0<=CURRENT_TIMESTAMP)||(LA138_0>=EVAL_AND_EXPR && LA138_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA138_0==EVENT_PROP_EXPR||(LA138_0>=CONCAT && LA138_0<=LIB_FUNCTION)||LA138_0==ARRAY_EXPR||(LA138_0>=NOT_IN_SET && LA138_0<=NOT_REGEXP)||(LA138_0>=IN_RANGE && LA138_0<=SUBSELECT_EXPR)||(LA138_0>=EXISTS_SUBSELECT_EXPR && LA138_0<=NOT_IN_SUBSELECT_EXPR)||LA138_0==SUBSTITUTION||(LA138_0>=FIRST_AGGREG && LA138_0<=LAST_AGGREG)||(LA138_0>=INT_TYPE && LA138_0<=NULL_TYPE)||(LA138_0>=STAR && LA138_0<=PLUS)||(LA138_0>=BAND && LA138_0<=BXOR)||(LA138_0>=LT && LA138_0<=GE)||(LA138_0>=MINUS && LA138_0<=MOD)) ) {
                                    alt138=1;
                                }


                                switch (alt138) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:405:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2636);
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
                            // EsperEPL2Ast.g:405:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2641);
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
                    // EsperEPL2Ast.g:406:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2654); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2656);
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

                    // EsperEPL2Ast.g:406:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt141=2;
                    int LA141_0 = input.LA(1);

                    if ( (LA141_0==UP||(LA141_0>=IN_SET && LA141_0<=REGEXP)||LA141_0==NOT_EXPR||(LA141_0>=SUM && LA141_0<=AVG)||(LA141_0>=COALESCE && LA141_0<=COUNT)||(LA141_0>=CASE && LA141_0<=CASE2)||(LA141_0>=PREVIOUS && LA141_0<=EXISTS)||(LA141_0>=INSTANCEOF && LA141_0<=CURRENT_TIMESTAMP)||(LA141_0>=EVAL_AND_EXPR && LA141_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA141_0==EVENT_PROP_EXPR||(LA141_0>=CONCAT && LA141_0<=LIB_FUNCTION)||LA141_0==ARRAY_EXPR||(LA141_0>=NOT_IN_SET && LA141_0<=NOT_REGEXP)||(LA141_0>=IN_RANGE && LA141_0<=SUBSELECT_EXPR)||(LA141_0>=EXISTS_SUBSELECT_EXPR && LA141_0<=NOT_IN_SUBSELECT_EXPR)||LA141_0==SUBSTITUTION||(LA141_0>=FIRST_AGGREG && LA141_0<=LAST_AGGREG)||(LA141_0>=INT_TYPE && LA141_0<=NULL_TYPE)||(LA141_0>=STAR && LA141_0<=PLUS)||(LA141_0>=BAND && LA141_0<=BXOR)||(LA141_0>=LT && LA141_0<=GE)||(LA141_0>=MINUS && LA141_0<=MOD)) ) {
                        alt141=1;
                    }
                    else if ( (LA141_0==SUBSELECT_GROUP_EXPR) ) {
                        alt141=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 141, 0, input);

                        throw nvae;
                    }
                    switch (alt141) {
                        case 1 :
                            // EsperEPL2Ast.g:406:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:406:63: ( valueExpr )*
                            loop140:
                            do {
                                int alt140=2;
                                int LA140_0 = input.LA(1);

                                if ( ((LA140_0>=IN_SET && LA140_0<=REGEXP)||LA140_0==NOT_EXPR||(LA140_0>=SUM && LA140_0<=AVG)||(LA140_0>=COALESCE && LA140_0<=COUNT)||(LA140_0>=CASE && LA140_0<=CASE2)||(LA140_0>=PREVIOUS && LA140_0<=EXISTS)||(LA140_0>=INSTANCEOF && LA140_0<=CURRENT_TIMESTAMP)||(LA140_0>=EVAL_AND_EXPR && LA140_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA140_0==EVENT_PROP_EXPR||(LA140_0>=CONCAT && LA140_0<=LIB_FUNCTION)||LA140_0==ARRAY_EXPR||(LA140_0>=NOT_IN_SET && LA140_0<=NOT_REGEXP)||(LA140_0>=IN_RANGE && LA140_0<=SUBSELECT_EXPR)||(LA140_0>=EXISTS_SUBSELECT_EXPR && LA140_0<=NOT_IN_SUBSELECT_EXPR)||LA140_0==SUBSTITUTION||(LA140_0>=FIRST_AGGREG && LA140_0<=LAST_AGGREG)||(LA140_0>=INT_TYPE && LA140_0<=NULL_TYPE)||(LA140_0>=STAR && LA140_0<=PLUS)||(LA140_0>=BAND && LA140_0<=BXOR)||(LA140_0>=LT && LA140_0<=GE)||(LA140_0>=MINUS && LA140_0<=MOD)) ) {
                                    alt140=1;
                                }


                                switch (alt140) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:406:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2667);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop140;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:406:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2672);
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
                    // EsperEPL2Ast.g:407:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2685); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2687);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:408:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2698);
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
    // EsperEPL2Ast.g:411:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:412:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt143=16;
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
            case SUBSTITUTION:
                {
                alt143=2;
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
                alt143=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt143=4;
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
                alt143=5;
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
                alt143=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt143=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt143=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt143=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt143=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt143=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt143=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt143=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt143=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt143=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt143=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }

            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:412:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2711);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:413:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2717);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:414:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2723);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:415:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2730);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:416:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2739);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:417:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2744);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:418:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2752);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:419:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2757);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:420:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2762);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:421:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2768);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:422:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2773);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:423:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2778);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:424:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2783);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:425:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2788);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:426:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2794);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:427:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2801);
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
    // EsperEPL2Ast.g:430:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:431:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt145=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt145=1;
                }
                break;
            case LW:
                {
                alt145=2;
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
                alt145=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt145=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt145=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt145=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt145=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt145=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt145=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt145=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt145=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 145, 0, input);

                throw nvae;
            }

            switch (alt145) {
                case 1 :
                    // EsperEPL2Ast.g:431:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2814); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:432:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2823); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:433:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2830);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:434:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2838); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2840);
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
                    // EsperEPL2Ast.g:435:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2855);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:436:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2861);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:437:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2866);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:438:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2871);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:439:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2881); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:439:29: ( numericParameterList )+
                    int cnt144=0;
                    loop144:
                    do {
                        int alt144=2;
                        int LA144_0 = input.LA(1);

                        if ( (LA144_0==NUMERIC_PARAM_RANGE||LA144_0==NUMERIC_PARAM_FREQUENCY||(LA144_0>=INT_TYPE && LA144_0<=NULL_TYPE)) ) {
                            alt144=1;
                        }


                        switch (alt144) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:439:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2883);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt144 >= 1 ) break loop144;
                                EarlyExitException eee =
                                    new EarlyExitException(144, input);
                                throw eee;
                        }
                        cnt144++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:440:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2894); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:441:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2901);
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
    // EsperEPL2Ast.g:444:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:445:2: ( constant[true] | rangeOperator | frequencyOperator )
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
            case NUMERIC_PARAM_RANGE:
                {
                alt146=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:445:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2914);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:446:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2921);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:447:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2927);
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
    // EsperEPL2Ast.g:450:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:451:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:451:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2943); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:451:29: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:451:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2946);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:451:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2949);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:451:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2952);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:451:83: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:451:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2956);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:451:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2959);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:451:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2962);
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
    // EsperEPL2Ast.g:454:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:455:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:455:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2983); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:455:33: ( constant[true] | eventPropertyExpr[true] | substitution )
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
                    // EsperEPL2Ast.g:455:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2986);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:455:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2989);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:455:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2992);
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
    // EsperEPL2Ast.g:458:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:459:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:459:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator3011); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:459:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt150=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt150=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt150=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt150=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }

            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:459:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator3014);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:459:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator3017);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:459:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator3020);
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
    // EsperEPL2Ast.g:462:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:463:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:463:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3039); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:463:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt151=3;
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
            case EVENT_PROP_EXPR:
                {
                alt151=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt151=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 151, 0, input);

                throw nvae;
            }

            switch (alt151) {
                case 1 :
                    // EsperEPL2Ast.g:463:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator3042);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:463:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator3045);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:463:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator3048);
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
    // EsperEPL2Ast.g:466:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:467:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:467:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3069); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr3071);
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
    // EsperEPL2Ast.g:470:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:471:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:471:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3090); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr3092);
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
    // EsperEPL2Ast.g:474:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:475:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:475:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3111); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr3113);
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
    // EsperEPL2Ast.g:478:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:479:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt152=2;
            int LA152_0 = input.LA(1);

            if ( (LA152_0==IN_SUBSELECT_EXPR) ) {
                alt152=1;
            }
            else if ( (LA152_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt152=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 152, 0, input);

                throw nvae;
            }
            switch (alt152) {
                case 1 :
                    // EsperEPL2Ast.g:479:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3132); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3134);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3136);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:480:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3148); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr3150);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3152);
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
    // EsperEPL2Ast.g:483:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:484:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:484:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3171); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr3173);
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
    // EsperEPL2Ast.g:487:1: subQueryExpr : ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:488:2: ( ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:488:4: ( DISTINCT )? selectionListElement subSelectFilterExpr ( whereClause[true] )?
            {
            // EsperEPL2Ast.g:488:4: ( DISTINCT )?
            int alt153=2;
            int LA153_0 = input.LA(1);

            if ( (LA153_0==DISTINCT) ) {
                alt153=1;
            }
            switch (alt153) {
                case 1 :
                    // EsperEPL2Ast.g:488:4: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_subQueryExpr3189); 

                    }
                    break;

            }

            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr3192);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr3194);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:488:55: ( whereClause[true] )?
            int alt154=2;
            int LA154_0 = input.LA(1);

            if ( (LA154_0==WHERE_EXPR) ) {
                alt154=1;
            }
            switch (alt154) {
                case 1 :
                    // EsperEPL2Ast.g:488:56: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr3197);
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
    // EsperEPL2Ast.g:491:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:492:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:492:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3215); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr3217);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:492:36: ( viewListExpr )?
            int alt155=2;
            int LA155_0 = input.LA(1);

            if ( (LA155_0==VIEW_EXPR) ) {
                alt155=1;
            }
            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:492:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr3220);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:492:52: ( IDENT )?
            int alt156=2;
            int LA156_0 = input.LA(1);

            if ( (LA156_0==IDENT) ) {
                alt156=1;
            }
            switch (alt156) {
                case 1 :
                    // EsperEPL2Ast.g:492:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr3225); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:492:61: ( RETAINUNION )?
            int alt157=2;
            int LA157_0 = input.LA(1);

            if ( (LA157_0==RETAINUNION) ) {
                alt157=1;
            }
            switch (alt157) {
                case 1 :
                    // EsperEPL2Ast.g:492:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr3229); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:492:74: ( RETAININTERSECTION )?
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==RETAININTERSECTION) ) {
                alt158=1;
            }
            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:492:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3232); 

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
    // EsperEPL2Ast.g:495:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:496:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt161=2;
            int LA161_0 = input.LA(1);

            if ( (LA161_0==CASE) ) {
                alt161=1;
            }
            else if ( (LA161_0==CASE2) ) {
                alt161=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }
            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:496:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr3252); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:496:13: ( valueExpr )*
                        loop159:
                        do {
                            int alt159=2;
                            int LA159_0 = input.LA(1);

                            if ( ((LA159_0>=IN_SET && LA159_0<=REGEXP)||LA159_0==NOT_EXPR||(LA159_0>=SUM && LA159_0<=AVG)||(LA159_0>=COALESCE && LA159_0<=COUNT)||(LA159_0>=CASE && LA159_0<=CASE2)||(LA159_0>=PREVIOUS && LA159_0<=EXISTS)||(LA159_0>=INSTANCEOF && LA159_0<=CURRENT_TIMESTAMP)||(LA159_0>=EVAL_AND_EXPR && LA159_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA159_0==EVENT_PROP_EXPR||(LA159_0>=CONCAT && LA159_0<=LIB_FUNCTION)||LA159_0==ARRAY_EXPR||(LA159_0>=NOT_IN_SET && LA159_0<=NOT_REGEXP)||(LA159_0>=IN_RANGE && LA159_0<=SUBSELECT_EXPR)||(LA159_0>=EXISTS_SUBSELECT_EXPR && LA159_0<=NOT_IN_SUBSELECT_EXPR)||LA159_0==SUBSTITUTION||(LA159_0>=FIRST_AGGREG && LA159_0<=LAST_AGGREG)||(LA159_0>=INT_TYPE && LA159_0<=NULL_TYPE)||(LA159_0>=STAR && LA159_0<=PLUS)||(LA159_0>=BAND && LA159_0<=BXOR)||(LA159_0>=LT && LA159_0<=GE)||(LA159_0>=MINUS && LA159_0<=MOD)) ) {
                                alt159=1;
                            }


                            switch (alt159) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:496:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3255);
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
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:497:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr3268); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:497:14: ( valueExpr )*
                        loop160:
                        do {
                            int alt160=2;
                            int LA160_0 = input.LA(1);

                            if ( ((LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||(LA160_0>=CONCAT && LA160_0<=LIB_FUNCTION)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=FIRST_AGGREG && LA160_0<=LAST_AGGREG)||(LA160_0>=INT_TYPE && LA160_0<=NULL_TYPE)||(LA160_0>=STAR && LA160_0<=PLUS)||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=MINUS && LA160_0<=MOD)) ) {
                                alt160=1;
                            }


                            switch (alt160) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:497:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr3271);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop160;
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
    // EsperEPL2Ast.g:500:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:501:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt164=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt164=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt164=2;
                }
                break;
            case IN_RANGE:
                {
                alt164=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt164=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 164, 0, input);

                throw nvae;
            }

            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:501:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr3291); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3293);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3301);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:501:51: ( valueExpr )*
                    loop162:
                    do {
                        int alt162=2;
                        int LA162_0 = input.LA(1);

                        if ( ((LA162_0>=IN_SET && LA162_0<=REGEXP)||LA162_0==NOT_EXPR||(LA162_0>=SUM && LA162_0<=AVG)||(LA162_0>=COALESCE && LA162_0<=COUNT)||(LA162_0>=CASE && LA162_0<=CASE2)||(LA162_0>=PREVIOUS && LA162_0<=EXISTS)||(LA162_0>=INSTANCEOF && LA162_0<=CURRENT_TIMESTAMP)||(LA162_0>=EVAL_AND_EXPR && LA162_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA162_0==EVENT_PROP_EXPR||(LA162_0>=CONCAT && LA162_0<=LIB_FUNCTION)||LA162_0==ARRAY_EXPR||(LA162_0>=NOT_IN_SET && LA162_0<=NOT_REGEXP)||(LA162_0>=IN_RANGE && LA162_0<=SUBSELECT_EXPR)||(LA162_0>=EXISTS_SUBSELECT_EXPR && LA162_0<=NOT_IN_SUBSELECT_EXPR)||LA162_0==SUBSTITUTION||(LA162_0>=FIRST_AGGREG && LA162_0<=LAST_AGGREG)||(LA162_0>=INT_TYPE && LA162_0<=NULL_TYPE)||(LA162_0>=STAR && LA162_0<=PLUS)||(LA162_0>=BAND && LA162_0<=BXOR)||(LA162_0>=LT && LA162_0<=GE)||(LA162_0>=MINUS && LA162_0<=MOD)) ) {
                            alt162=1;
                        }


                        switch (alt162) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:501:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3304);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop162;
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
                    // EsperEPL2Ast.g:502:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr3323); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3325);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3333);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:502:55: ( valueExpr )*
                    loop163:
                    do {
                        int alt163=2;
                        int LA163_0 = input.LA(1);

                        if ( ((LA163_0>=IN_SET && LA163_0<=REGEXP)||LA163_0==NOT_EXPR||(LA163_0>=SUM && LA163_0<=AVG)||(LA163_0>=COALESCE && LA163_0<=COUNT)||(LA163_0>=CASE && LA163_0<=CASE2)||(LA163_0>=PREVIOUS && LA163_0<=EXISTS)||(LA163_0>=INSTANCEOF && LA163_0<=CURRENT_TIMESTAMP)||(LA163_0>=EVAL_AND_EXPR && LA163_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA163_0==EVENT_PROP_EXPR||(LA163_0>=CONCAT && LA163_0<=LIB_FUNCTION)||LA163_0==ARRAY_EXPR||(LA163_0>=NOT_IN_SET && LA163_0<=NOT_REGEXP)||(LA163_0>=IN_RANGE && LA163_0<=SUBSELECT_EXPR)||(LA163_0>=EXISTS_SUBSELECT_EXPR && LA163_0<=NOT_IN_SUBSELECT_EXPR)||LA163_0==SUBSTITUTION||(LA163_0>=FIRST_AGGREG && LA163_0<=LAST_AGGREG)||(LA163_0>=INT_TYPE && LA163_0<=NULL_TYPE)||(LA163_0>=STAR && LA163_0<=PLUS)||(LA163_0>=BAND && LA163_0<=BXOR)||(LA163_0>=LT && LA163_0<=GE)||(LA163_0>=MINUS && LA163_0<=MOD)) ) {
                            alt163=1;
                        }


                        switch (alt163) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:502:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr3336);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop163;
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
                    // EsperEPL2Ast.g:503:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr3355); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr3357);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3365);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr3367);
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
                    // EsperEPL2Ast.g:504:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr3384); 

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

                    pushFollow(FOLLOW_valueExpr_in_inExpr3396);
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
    // EsperEPL2Ast.g:507:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:508:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt166=2;
            int LA166_0 = input.LA(1);

            if ( (LA166_0==BETWEEN) ) {
                alt166=1;
            }
            else if ( (LA166_0==NOT_BETWEEN) ) {
                alt166=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 166, 0, input);

                throw nvae;
            }
            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:508:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr3421); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3423);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3425);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3427);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:509:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr3438); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3440);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr3442);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:509:40: ( valueExpr )*
                    loop165:
                    do {
                        int alt165=2;
                        int LA165_0 = input.LA(1);

                        if ( ((LA165_0>=IN_SET && LA165_0<=REGEXP)||LA165_0==NOT_EXPR||(LA165_0>=SUM && LA165_0<=AVG)||(LA165_0>=COALESCE && LA165_0<=COUNT)||(LA165_0>=CASE && LA165_0<=CASE2)||(LA165_0>=PREVIOUS && LA165_0<=EXISTS)||(LA165_0>=INSTANCEOF && LA165_0<=CURRENT_TIMESTAMP)||(LA165_0>=EVAL_AND_EXPR && LA165_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA165_0==EVENT_PROP_EXPR||(LA165_0>=CONCAT && LA165_0<=LIB_FUNCTION)||LA165_0==ARRAY_EXPR||(LA165_0>=NOT_IN_SET && LA165_0<=NOT_REGEXP)||(LA165_0>=IN_RANGE && LA165_0<=SUBSELECT_EXPR)||(LA165_0>=EXISTS_SUBSELECT_EXPR && LA165_0<=NOT_IN_SUBSELECT_EXPR)||LA165_0==SUBSTITUTION||(LA165_0>=FIRST_AGGREG && LA165_0<=LAST_AGGREG)||(LA165_0>=INT_TYPE && LA165_0<=NULL_TYPE)||(LA165_0>=STAR && LA165_0<=PLUS)||(LA165_0>=BAND && LA165_0<=BXOR)||(LA165_0>=LT && LA165_0<=GE)||(LA165_0>=MINUS && LA165_0<=MOD)) ) {
                            alt165=1;
                        }


                        switch (alt165) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:509:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr3445);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop165;
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
    // EsperEPL2Ast.g:512:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:513:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt169=2;
            int LA169_0 = input.LA(1);

            if ( (LA169_0==LIKE) ) {
                alt169=1;
            }
            else if ( (LA169_0==NOT_LIKE) ) {
                alt169=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 169, 0, input);

                throw nvae;
            }
            switch (alt169) {
                case 1 :
                    // EsperEPL2Ast.g:513:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr3465); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3467);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3469);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:513:33: ( valueExpr )?
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( ((LA167_0>=IN_SET && LA167_0<=REGEXP)||LA167_0==NOT_EXPR||(LA167_0>=SUM && LA167_0<=AVG)||(LA167_0>=COALESCE && LA167_0<=COUNT)||(LA167_0>=CASE && LA167_0<=CASE2)||(LA167_0>=PREVIOUS && LA167_0<=EXISTS)||(LA167_0>=INSTANCEOF && LA167_0<=CURRENT_TIMESTAMP)||(LA167_0>=EVAL_AND_EXPR && LA167_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA167_0==EVENT_PROP_EXPR||(LA167_0>=CONCAT && LA167_0<=LIB_FUNCTION)||LA167_0==ARRAY_EXPR||(LA167_0>=NOT_IN_SET && LA167_0<=NOT_REGEXP)||(LA167_0>=IN_RANGE && LA167_0<=SUBSELECT_EXPR)||(LA167_0>=EXISTS_SUBSELECT_EXPR && LA167_0<=NOT_IN_SUBSELECT_EXPR)||LA167_0==SUBSTITUTION||(LA167_0>=FIRST_AGGREG && LA167_0<=LAST_AGGREG)||(LA167_0>=INT_TYPE && LA167_0<=NULL_TYPE)||(LA167_0>=STAR && LA167_0<=PLUS)||(LA167_0>=BAND && LA167_0<=BXOR)||(LA167_0>=LT && LA167_0<=GE)||(LA167_0>=MINUS && LA167_0<=MOD)) ) {
                        alt167=1;
                    }
                    switch (alt167) {
                        case 1 :
                            // EsperEPL2Ast.g:513:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3472);
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
                    // EsperEPL2Ast.g:514:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr3485); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr3487);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr3489);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:514:37: ( valueExpr )?
                    int alt168=2;
                    int LA168_0 = input.LA(1);

                    if ( ((LA168_0>=IN_SET && LA168_0<=REGEXP)||LA168_0==NOT_EXPR||(LA168_0>=SUM && LA168_0<=AVG)||(LA168_0>=COALESCE && LA168_0<=COUNT)||(LA168_0>=CASE && LA168_0<=CASE2)||(LA168_0>=PREVIOUS && LA168_0<=EXISTS)||(LA168_0>=INSTANCEOF && LA168_0<=CURRENT_TIMESTAMP)||(LA168_0>=EVAL_AND_EXPR && LA168_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA168_0==EVENT_PROP_EXPR||(LA168_0>=CONCAT && LA168_0<=LIB_FUNCTION)||LA168_0==ARRAY_EXPR||(LA168_0>=NOT_IN_SET && LA168_0<=NOT_REGEXP)||(LA168_0>=IN_RANGE && LA168_0<=SUBSELECT_EXPR)||(LA168_0>=EXISTS_SUBSELECT_EXPR && LA168_0<=NOT_IN_SUBSELECT_EXPR)||LA168_0==SUBSTITUTION||(LA168_0>=FIRST_AGGREG && LA168_0<=LAST_AGGREG)||(LA168_0>=INT_TYPE && LA168_0<=NULL_TYPE)||(LA168_0>=STAR && LA168_0<=PLUS)||(LA168_0>=BAND && LA168_0<=BXOR)||(LA168_0>=LT && LA168_0<=GE)||(LA168_0>=MINUS && LA168_0<=MOD)) ) {
                        alt168=1;
                    }
                    switch (alt168) {
                        case 1 :
                            // EsperEPL2Ast.g:514:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr3492);
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
    // EsperEPL2Ast.g:517:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:518:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt170=2;
            int LA170_0 = input.LA(1);

            if ( (LA170_0==REGEXP) ) {
                alt170=1;
            }
            else if ( (LA170_0==NOT_REGEXP) ) {
                alt170=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 170, 0, input);

                throw nvae;
            }
            switch (alt170) {
                case 1 :
                    // EsperEPL2Ast.g:518:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr3511); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3513);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3515);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:519:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr3526); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3528);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr3530);
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
    // EsperEPL2Ast.g:522:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:523:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= LAST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr ( valueExpr )? ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt183=15;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt183=1;
                }
                break;
            case AVG:
                {
                alt183=2;
                }
                break;
            case COUNT:
                {
                alt183=3;
                }
                break;
            case MEDIAN:
                {
                alt183=4;
                }
                break;
            case STDDEV:
                {
                alt183=5;
                }
                break;
            case AVEDEV:
                {
                alt183=6;
                }
                break;
            case LAST_AGGREG:
                {
                alt183=7;
                }
                break;
            case FIRST_AGGREG:
                {
                alt183=8;
                }
                break;
            case COALESCE:
                {
                alt183=9;
                }
                break;
            case PREVIOUS:
                {
                alt183=10;
                }
                break;
            case PRIOR:
                {
                alt183=11;
                }
                break;
            case INSTANCEOF:
                {
                alt183=12;
                }
                break;
            case CAST:
                {
                alt183=13;
                }
                break;
            case EXISTS:
                {
                alt183=14;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt183=15;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 183, 0, input);

                throw nvae;
            }

            switch (alt183) {
                case 1 :
                    // EsperEPL2Ast.g:523:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc3549); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:523:13: ( DISTINCT )?
                    int alt171=2;
                    int LA171_0 = input.LA(1);

                    if ( (LA171_0==DISTINCT) ) {
                        alt171=1;
                    }
                    switch (alt171) {
                        case 1 :
                            // EsperEPL2Ast.g:523:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3552); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3556);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:524:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc3567); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:524:12: ( DISTINCT )?
                    int alt172=2;
                    int LA172_0 = input.LA(1);

                    if ( (LA172_0==DISTINCT) ) {
                        alt172=1;
                    }
                    switch (alt172) {
                        case 1 :
                            // EsperEPL2Ast.g:524:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3570); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3574);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:525:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc3585); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:525:14: ( ( DISTINCT )? valueExpr )?
                        int alt174=2;
                        int LA174_0 = input.LA(1);

                        if ( ((LA174_0>=IN_SET && LA174_0<=REGEXP)||LA174_0==NOT_EXPR||(LA174_0>=SUM && LA174_0<=AVG)||(LA174_0>=COALESCE && LA174_0<=COUNT)||(LA174_0>=CASE && LA174_0<=CASE2)||LA174_0==DISTINCT||(LA174_0>=PREVIOUS && LA174_0<=EXISTS)||(LA174_0>=INSTANCEOF && LA174_0<=CURRENT_TIMESTAMP)||(LA174_0>=EVAL_AND_EXPR && LA174_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA174_0==EVENT_PROP_EXPR||(LA174_0>=CONCAT && LA174_0<=LIB_FUNCTION)||LA174_0==ARRAY_EXPR||(LA174_0>=NOT_IN_SET && LA174_0<=NOT_REGEXP)||(LA174_0>=IN_RANGE && LA174_0<=SUBSELECT_EXPR)||(LA174_0>=EXISTS_SUBSELECT_EXPR && LA174_0<=NOT_IN_SUBSELECT_EXPR)||LA174_0==SUBSTITUTION||(LA174_0>=FIRST_AGGREG && LA174_0<=LAST_AGGREG)||(LA174_0>=INT_TYPE && LA174_0<=NULL_TYPE)||(LA174_0>=STAR && LA174_0<=PLUS)||(LA174_0>=BAND && LA174_0<=BXOR)||(LA174_0>=LT && LA174_0<=GE)||(LA174_0>=MINUS && LA174_0<=MOD)) ) {
                            alt174=1;
                        }
                        switch (alt174) {
                            case 1 :
                                // EsperEPL2Ast.g:525:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:525:15: ( DISTINCT )?
                                int alt173=2;
                                int LA173_0 = input.LA(1);

                                if ( (LA173_0==DISTINCT) ) {
                                    alt173=1;
                                }
                                switch (alt173) {
                                    case 1 :
                                        // EsperEPL2Ast.g:525:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3589); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc3593);
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
                    // EsperEPL2Ast.g:526:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc3607); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:526:15: ( DISTINCT )?
                    int alt175=2;
                    int LA175_0 = input.LA(1);

                    if ( (LA175_0==DISTINCT) ) {
                        alt175=1;
                    }
                    switch (alt175) {
                        case 1 :
                            // EsperEPL2Ast.g:526:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3610); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3614);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:527:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3625); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:527:15: ( DISTINCT )?
                    int alt176=2;
                    int LA176_0 = input.LA(1);

                    if ( (LA176_0==DISTINCT) ) {
                        alt176=1;
                    }
                    switch (alt176) {
                        case 1 :
                            // EsperEPL2Ast.g:527:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3628); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3632);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:528:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3643); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:528:15: ( DISTINCT )?
                    int alt177=2;
                    int LA177_0 = input.LA(1);

                    if ( (LA177_0==DISTINCT) ) {
                        alt177=1;
                    }
                    switch (alt177) {
                        case 1 :
                            // EsperEPL2Ast.g:528:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3646); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3650);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:529:4: ^(f= LAST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,LAST_AGGREG,FOLLOW_LAST_AGGREG_in_builtinFunc3661); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:529:20: ( DISTINCT )?
                    int alt178=2;
                    int LA178_0 = input.LA(1);

                    if ( (LA178_0==DISTINCT) ) {
                        alt178=1;
                    }
                    switch (alt178) {
                        case 1 :
                            // EsperEPL2Ast.g:529:21: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3664); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3668);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:530:4: ^(f= FIRST_AGGREG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,FIRST_AGGREG,FOLLOW_FIRST_AGGREG_in_builtinFunc3679); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:530:21: ( DISTINCT )?
                    int alt179=2;
                    int LA179_0 = input.LA(1);

                    if ( (LA179_0==DISTINCT) ) {
                        alt179=1;
                    }
                    switch (alt179) {
                        case 1 :
                            // EsperEPL2Ast.g:530:22: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3682); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3686);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:531:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3698); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3700);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3702);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:531:38: ( valueExpr )*
                    loop180:
                    do {
                        int alt180=2;
                        int LA180_0 = input.LA(1);

                        if ( ((LA180_0>=IN_SET && LA180_0<=REGEXP)||LA180_0==NOT_EXPR||(LA180_0>=SUM && LA180_0<=AVG)||(LA180_0>=COALESCE && LA180_0<=COUNT)||(LA180_0>=CASE && LA180_0<=CASE2)||(LA180_0>=PREVIOUS && LA180_0<=EXISTS)||(LA180_0>=INSTANCEOF && LA180_0<=CURRENT_TIMESTAMP)||(LA180_0>=EVAL_AND_EXPR && LA180_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA180_0==EVENT_PROP_EXPR||(LA180_0>=CONCAT && LA180_0<=LIB_FUNCTION)||LA180_0==ARRAY_EXPR||(LA180_0>=NOT_IN_SET && LA180_0<=NOT_REGEXP)||(LA180_0>=IN_RANGE && LA180_0<=SUBSELECT_EXPR)||(LA180_0>=EXISTS_SUBSELECT_EXPR && LA180_0<=NOT_IN_SUBSELECT_EXPR)||LA180_0==SUBSTITUTION||(LA180_0>=FIRST_AGGREG && LA180_0<=LAST_AGGREG)||(LA180_0>=INT_TYPE && LA180_0<=NULL_TYPE)||(LA180_0>=STAR && LA180_0<=PLUS)||(LA180_0>=BAND && LA180_0<=BXOR)||(LA180_0>=LT && LA180_0<=GE)||(LA180_0>=MINUS && LA180_0<=MOD)) ) {
                            alt180=1;
                        }


                        switch (alt180) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:531:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3705);
                    	    valueExpr();

                    	    state._fsp--;


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
                case 10 :
                    // EsperEPL2Ast.g:532:5: ^(f= PREVIOUS valueExpr ( valueExpr )? )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3720); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3722);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:532:28: ( valueExpr )?
                    int alt181=2;
                    int LA181_0 = input.LA(1);

                    if ( ((LA181_0>=IN_SET && LA181_0<=REGEXP)||LA181_0==NOT_EXPR||(LA181_0>=SUM && LA181_0<=AVG)||(LA181_0>=COALESCE && LA181_0<=COUNT)||(LA181_0>=CASE && LA181_0<=CASE2)||(LA181_0>=PREVIOUS && LA181_0<=EXISTS)||(LA181_0>=INSTANCEOF && LA181_0<=CURRENT_TIMESTAMP)||(LA181_0>=EVAL_AND_EXPR && LA181_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA181_0==EVENT_PROP_EXPR||(LA181_0>=CONCAT && LA181_0<=LIB_FUNCTION)||LA181_0==ARRAY_EXPR||(LA181_0>=NOT_IN_SET && LA181_0<=NOT_REGEXP)||(LA181_0>=IN_RANGE && LA181_0<=SUBSELECT_EXPR)||(LA181_0>=EXISTS_SUBSELECT_EXPR && LA181_0<=NOT_IN_SUBSELECT_EXPR)||LA181_0==SUBSTITUTION||(LA181_0>=FIRST_AGGREG && LA181_0<=LAST_AGGREG)||(LA181_0>=INT_TYPE && LA181_0<=NULL_TYPE)||(LA181_0>=STAR && LA181_0<=PLUS)||(LA181_0>=BAND && LA181_0<=BXOR)||(LA181_0>=LT && LA181_0<=GE)||(LA181_0>=MINUS && LA181_0<=MOD)) ) {
                        alt181=1;
                    }
                    switch (alt181) {
                        case 1 :
                            // EsperEPL2Ast.g:532:28: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_builtinFunc3724);
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
                    // EsperEPL2Ast.g:533:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3737); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3741); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3743);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:534:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3756); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3758);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3760); 
                    // EsperEPL2Ast.g:534:42: ( CLASS_IDENT )*
                    loop182:
                    do {
                        int alt182=2;
                        int LA182_0 = input.LA(1);

                        if ( (LA182_0==CLASS_IDENT) ) {
                            alt182=1;
                        }


                        switch (alt182) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:534:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3763); 

                    	    }
                    	    break;

                    	default :
                    	    break loop182;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:535:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3777); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3779);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3781); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:536:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3793); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3795);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:537:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3807); 



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
    // EsperEPL2Ast.g:540:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:541:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:541:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3827); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:541:19: ( valueExpr )*
                loop184:
                do {
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( ((LA184_0>=IN_SET && LA184_0<=REGEXP)||LA184_0==NOT_EXPR||(LA184_0>=SUM && LA184_0<=AVG)||(LA184_0>=COALESCE && LA184_0<=COUNT)||(LA184_0>=CASE && LA184_0<=CASE2)||(LA184_0>=PREVIOUS && LA184_0<=EXISTS)||(LA184_0>=INSTANCEOF && LA184_0<=CURRENT_TIMESTAMP)||(LA184_0>=EVAL_AND_EXPR && LA184_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA184_0==EVENT_PROP_EXPR||(LA184_0>=CONCAT && LA184_0<=LIB_FUNCTION)||LA184_0==ARRAY_EXPR||(LA184_0>=NOT_IN_SET && LA184_0<=NOT_REGEXP)||(LA184_0>=IN_RANGE && LA184_0<=SUBSELECT_EXPR)||(LA184_0>=EXISTS_SUBSELECT_EXPR && LA184_0<=NOT_IN_SUBSELECT_EXPR)||LA184_0==SUBSTITUTION||(LA184_0>=FIRST_AGGREG && LA184_0<=LAST_AGGREG)||(LA184_0>=INT_TYPE && LA184_0<=NULL_TYPE)||(LA184_0>=STAR && LA184_0<=PLUS)||(LA184_0>=BAND && LA184_0<=BXOR)||(LA184_0>=LT && LA184_0<=GE)||(LA184_0>=MINUS && LA184_0<=MOD)) ) {
                        alt184=1;
                    }


                    switch (alt184) {
                	case 1 :
                	    // EsperEPL2Ast.g:541:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3830);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop184;
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
    // EsperEPL2Ast.g:544:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:545:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt186=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt186=1;
                }
                break;
            case MINUS:
                {
                alt186=2;
                }
                break;
            case DIV:
                {
                alt186=3;
                }
                break;
            case STAR:
                {
                alt186=4;
                }
                break;
            case MOD:
                {
                alt186=5;
                }
                break;
            case BAND:
                {
                alt186=6;
                }
                break;
            case BOR:
                {
                alt186=7;
                }
                break;
            case BXOR:
                {
                alt186=8;
                }
                break;
            case CONCAT:
                {
                alt186=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 186, 0, input);

                throw nvae;
            }

            switch (alt186) {
                case 1 :
                    // EsperEPL2Ast.g:545:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3851); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3853);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3855);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:546:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3867); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3869);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3871);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:547:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3883); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3885);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3887);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:548:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3898); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3900);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3902);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:549:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3914); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3916);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3918);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:550:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3929); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3931);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3933);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:551:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3944); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3946);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3948);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:552:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3959); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3961);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3963);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:553:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3975); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3977);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3979);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:553:36: ( valueExpr )*
                    loop185:
                    do {
                        int alt185=2;
                        int LA185_0 = input.LA(1);

                        if ( ((LA185_0>=IN_SET && LA185_0<=REGEXP)||LA185_0==NOT_EXPR||(LA185_0>=SUM && LA185_0<=AVG)||(LA185_0>=COALESCE && LA185_0<=COUNT)||(LA185_0>=CASE && LA185_0<=CASE2)||(LA185_0>=PREVIOUS && LA185_0<=EXISTS)||(LA185_0>=INSTANCEOF && LA185_0<=CURRENT_TIMESTAMP)||(LA185_0>=EVAL_AND_EXPR && LA185_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA185_0==EVENT_PROP_EXPR||(LA185_0>=CONCAT && LA185_0<=LIB_FUNCTION)||LA185_0==ARRAY_EXPR||(LA185_0>=NOT_IN_SET && LA185_0<=NOT_REGEXP)||(LA185_0>=IN_RANGE && LA185_0<=SUBSELECT_EXPR)||(LA185_0>=EXISTS_SUBSELECT_EXPR && LA185_0<=NOT_IN_SUBSELECT_EXPR)||LA185_0==SUBSTITUTION||(LA185_0>=FIRST_AGGREG && LA185_0<=LAST_AGGREG)||(LA185_0>=INT_TYPE && LA185_0<=NULL_TYPE)||(LA185_0>=STAR && LA185_0<=PLUS)||(LA185_0>=BAND && LA185_0<=BXOR)||(LA185_0>=LT && LA185_0<=GE)||(LA185_0>=MINUS && LA185_0<=MOD)) ) {
                            alt185=1;
                        }


                        switch (alt185) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:553:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3982);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop185;
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
    // EsperEPL2Ast.g:556:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:557:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:557:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc4003); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:557:22: ( CLASS_IDENT )?
            int alt187=2;
            int LA187_0 = input.LA(1);

            if ( (LA187_0==CLASS_IDENT) ) {
                alt187=1;
            }
            switch (alt187) {
                case 1 :
                    // EsperEPL2Ast.g:557:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc4006); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc4010); 
            // EsperEPL2Ast.g:557:43: ( DISTINCT )?
            int alt188=2;
            int LA188_0 = input.LA(1);

            if ( (LA188_0==DISTINCT) ) {
                alt188=1;
            }
            switch (alt188) {
                case 1 :
                    // EsperEPL2Ast.g:557:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc4013); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:557:55: ( valueExpr )*
            loop189:
            do {
                int alt189=2;
                int LA189_0 = input.LA(1);

                if ( ((LA189_0>=IN_SET && LA189_0<=REGEXP)||LA189_0==NOT_EXPR||(LA189_0>=SUM && LA189_0<=AVG)||(LA189_0>=COALESCE && LA189_0<=COUNT)||(LA189_0>=CASE && LA189_0<=CASE2)||(LA189_0>=PREVIOUS && LA189_0<=EXISTS)||(LA189_0>=INSTANCEOF && LA189_0<=CURRENT_TIMESTAMP)||(LA189_0>=EVAL_AND_EXPR && LA189_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA189_0==EVENT_PROP_EXPR||(LA189_0>=CONCAT && LA189_0<=LIB_FUNCTION)||LA189_0==ARRAY_EXPR||(LA189_0>=NOT_IN_SET && LA189_0<=NOT_REGEXP)||(LA189_0>=IN_RANGE && LA189_0<=SUBSELECT_EXPR)||(LA189_0>=EXISTS_SUBSELECT_EXPR && LA189_0<=NOT_IN_SUBSELECT_EXPR)||LA189_0==SUBSTITUTION||(LA189_0>=FIRST_AGGREG && LA189_0<=LAST_AGGREG)||(LA189_0>=INT_TYPE && LA189_0<=NULL_TYPE)||(LA189_0>=STAR && LA189_0<=PLUS)||(LA189_0>=BAND && LA189_0<=BXOR)||(LA189_0>=LT && LA189_0<=GE)||(LA189_0>=MINUS && LA189_0<=MOD)) ) {
                    alt189=1;
                }


                switch (alt189) {
            	case 1 :
            	    // EsperEPL2Ast.g:557:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc4018);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop189;
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
    // EsperEPL2Ast.g:563:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:564:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:564:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:564:4: ( annotation[true] )*
            loop190:
            do {
                int alt190=2;
                int LA190_0 = input.LA(1);

                if ( (LA190_0==ANNOTATION) ) {
                    alt190=1;
                }


                switch (alt190) {
            	case 1 :
            	    // EsperEPL2Ast.g:564:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule4038);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop190;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule4042);
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
    // EsperEPL2Ast.g:567:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:568:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt195=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt195=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt195=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt195=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt195=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt195=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt195=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt195=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 195, 0, input);

                throw nvae;
            }

            switch (alt195) {
                case 1 :
                    // EsperEPL2Ast.g:568:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice4056);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:569:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice4061);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:570:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice4071); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4073);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:571:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4087); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice4089);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4091);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:572:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4105); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4107);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:573:5: ^(g= GUARD_EXPR exprChoice ( IDENT IDENT ( valueExprWithTime )* | valueExpr ) )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice4121); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice4123);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:573:32: ( IDENT IDENT ( valueExprWithTime )* | valueExpr )
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( (LA192_0==IDENT) ) {
                        alt192=1;
                    }
                    else if ( ((LA192_0>=IN_SET && LA192_0<=REGEXP)||LA192_0==NOT_EXPR||(LA192_0>=SUM && LA192_0<=AVG)||(LA192_0>=COALESCE && LA192_0<=COUNT)||(LA192_0>=CASE && LA192_0<=CASE2)||(LA192_0>=PREVIOUS && LA192_0<=EXISTS)||(LA192_0>=INSTANCEOF && LA192_0<=CURRENT_TIMESTAMP)||(LA192_0>=EVAL_AND_EXPR && LA192_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA192_0==EVENT_PROP_EXPR||(LA192_0>=CONCAT && LA192_0<=LIB_FUNCTION)||LA192_0==ARRAY_EXPR||(LA192_0>=NOT_IN_SET && LA192_0<=NOT_REGEXP)||(LA192_0>=IN_RANGE && LA192_0<=SUBSELECT_EXPR)||(LA192_0>=EXISTS_SUBSELECT_EXPR && LA192_0<=NOT_IN_SUBSELECT_EXPR)||LA192_0==SUBSTITUTION||(LA192_0>=FIRST_AGGREG && LA192_0<=LAST_AGGREG)||(LA192_0>=INT_TYPE && LA192_0<=NULL_TYPE)||(LA192_0>=STAR && LA192_0<=PLUS)||(LA192_0>=BAND && LA192_0<=BXOR)||(LA192_0>=LT && LA192_0<=GE)||(LA192_0>=MINUS && LA192_0<=MOD)) ) {
                        alt192=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 192, 0, input);

                        throw nvae;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:573:33: IDENT IDENT ( valueExprWithTime )*
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4126); 
                            match(input,IDENT,FOLLOW_IDENT_in_exprChoice4128); 
                            // EsperEPL2Ast.g:573:45: ( valueExprWithTime )*
                            loop191:
                            do {
                                int alt191=2;
                                int LA191_0 = input.LA(1);

                                if ( ((LA191_0>=IN_SET && LA191_0<=REGEXP)||LA191_0==NOT_EXPR||(LA191_0>=SUM && LA191_0<=AVG)||(LA191_0>=COALESCE && LA191_0<=COUNT)||(LA191_0>=CASE && LA191_0<=CASE2)||LA191_0==LAST||(LA191_0>=PREVIOUS && LA191_0<=EXISTS)||(LA191_0>=LW && LA191_0<=CURRENT_TIMESTAMP)||(LA191_0>=NUMERIC_PARAM_RANGE && LA191_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA191_0>=EVAL_AND_EXPR && LA191_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA191_0==EVENT_PROP_EXPR||(LA191_0>=CONCAT && LA191_0<=LIB_FUNCTION)||(LA191_0>=TIME_PERIOD && LA191_0<=ARRAY_EXPR)||(LA191_0>=NOT_IN_SET && LA191_0<=NOT_REGEXP)||(LA191_0>=IN_RANGE && LA191_0<=SUBSELECT_EXPR)||(LA191_0>=EXISTS_SUBSELECT_EXPR && LA191_0<=NOT_IN_SUBSELECT_EXPR)||(LA191_0>=LAST_OPERATOR && LA191_0<=SUBSTITUTION)||LA191_0==NUMBERSETSTAR||(LA191_0>=FIRST_AGGREG && LA191_0<=LAST_AGGREG)||(LA191_0>=INT_TYPE && LA191_0<=NULL_TYPE)||(LA191_0>=STAR && LA191_0<=PLUS)||(LA191_0>=BAND && LA191_0<=BXOR)||(LA191_0>=LT && LA191_0<=GE)||(LA191_0>=MINUS && LA191_0<=MOD)) ) {
                                    alt191=1;
                                }


                                switch (alt191) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:573:45: valueExprWithTime
                            	    {
                            	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice4130);
                            	    valueExprWithTime();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop191;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:573:66: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_exprChoice4135);
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
                    // EsperEPL2Ast.g:574:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4149); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:574:26: ( matchUntilRange )?
                    int alt193=2;
                    int LA193_0 = input.LA(1);

                    if ( ((LA193_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA193_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt193=1;
                    }
                    switch (alt193) {
                        case 1 :
                            // EsperEPL2Ast.g:574:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice4151);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice4154);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:574:54: ( exprChoice )?
                    int alt194=2;
                    int LA194_0 = input.LA(1);

                    if ( ((LA194_0>=OR_EXPR && LA194_0<=AND_EXPR)||(LA194_0>=EVERY_EXPR && LA194_0<=EVERY_DISTINCT_EXPR)||LA194_0==FOLLOWED_BY_EXPR||(LA194_0>=PATTERN_FILTER_EXPR && LA194_0<=PATTERN_NOT_EXPR)||(LA194_0>=GUARD_EXPR && LA194_0<=OBSERVER_EXPR)||LA194_0==MATCH_UNTIL_EXPR) ) {
                        alt194=1;
                    }
                    switch (alt194) {
                        case 1 :
                            // EsperEPL2Ast.g:574:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice4156);
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
    // EsperEPL2Ast.g:578:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:579:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:579:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4177); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:579:35: ( valueExpr )+
            int cnt196=0;
            loop196:
            do {
                int alt196=2;
                int LA196_0 = input.LA(1);

                if ( ((LA196_0>=IN_SET && LA196_0<=REGEXP)||LA196_0==NOT_EXPR||(LA196_0>=SUM && LA196_0<=AVG)||(LA196_0>=COALESCE && LA196_0<=COUNT)||(LA196_0>=CASE && LA196_0<=CASE2)||(LA196_0>=PREVIOUS && LA196_0<=EXISTS)||(LA196_0>=INSTANCEOF && LA196_0<=CURRENT_TIMESTAMP)||(LA196_0>=EVAL_AND_EXPR && LA196_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA196_0==EVENT_PROP_EXPR||(LA196_0>=CONCAT && LA196_0<=LIB_FUNCTION)||LA196_0==ARRAY_EXPR||(LA196_0>=NOT_IN_SET && LA196_0<=NOT_REGEXP)||(LA196_0>=IN_RANGE && LA196_0<=SUBSELECT_EXPR)||(LA196_0>=EXISTS_SUBSELECT_EXPR && LA196_0<=NOT_IN_SUBSELECT_EXPR)||LA196_0==SUBSTITUTION||(LA196_0>=FIRST_AGGREG && LA196_0<=LAST_AGGREG)||(LA196_0>=INT_TYPE && LA196_0<=NULL_TYPE)||(LA196_0>=STAR && LA196_0<=PLUS)||(LA196_0>=BAND && LA196_0<=BXOR)||(LA196_0>=LT && LA196_0<=GE)||(LA196_0>=MINUS && LA196_0<=MOD)) ) {
                    alt196=1;
                }


                switch (alt196) {
            	case 1 :
            	    // EsperEPL2Ast.g:579:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions4179);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt196 >= 1 ) break loop196;
                        EarlyExitException eee =
                            new EarlyExitException(196, input);
                        throw eee;
                }
                cnt196++;
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
    // EsperEPL2Ast.g:582:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:583:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt200=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt200=1;
                }
                break;
            case OR_EXPR:
                {
                alt200=2;
                }
                break;
            case AND_EXPR:
                {
                alt200=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 200, 0, input);

                throw nvae;
            }

            switch (alt200) {
                case 1 :
                    // EsperEPL2Ast.g:583:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4198); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4200);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4202);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:583:48: ( exprChoice )*
                    loop197:
                    do {
                        int alt197=2;
                        int LA197_0 = input.LA(1);

                        if ( ((LA197_0>=OR_EXPR && LA197_0<=AND_EXPR)||(LA197_0>=EVERY_EXPR && LA197_0<=EVERY_DISTINCT_EXPR)||LA197_0==FOLLOWED_BY_EXPR||(LA197_0>=PATTERN_FILTER_EXPR && LA197_0<=PATTERN_NOT_EXPR)||(LA197_0>=GUARD_EXPR && LA197_0<=OBSERVER_EXPR)||LA197_0==MATCH_UNTIL_EXPR) ) {
                            alt197=1;
                        }


                        switch (alt197) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:583:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4205);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop197;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:584:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp4221); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4223);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4225);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:584:40: ( exprChoice )*
                    loop198:
                    do {
                        int alt198=2;
                        int LA198_0 = input.LA(1);

                        if ( ((LA198_0>=OR_EXPR && LA198_0<=AND_EXPR)||(LA198_0>=EVERY_EXPR && LA198_0<=EVERY_DISTINCT_EXPR)||LA198_0==FOLLOWED_BY_EXPR||(LA198_0>=PATTERN_FILTER_EXPR && LA198_0<=PATTERN_NOT_EXPR)||(LA198_0>=GUARD_EXPR && LA198_0<=OBSERVER_EXPR)||LA198_0==MATCH_UNTIL_EXPR) ) {
                            alt198=1;
                        }


                        switch (alt198) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:584:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4228);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop198;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:585:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp4244); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp4246);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp4248);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:585:41: ( exprChoice )*
                    loop199:
                    do {
                        int alt199=2;
                        int LA199_0 = input.LA(1);

                        if ( ((LA199_0>=OR_EXPR && LA199_0<=AND_EXPR)||(LA199_0>=EVERY_EXPR && LA199_0<=EVERY_DISTINCT_EXPR)||LA199_0==FOLLOWED_BY_EXPR||(LA199_0>=PATTERN_FILTER_EXPR && LA199_0<=PATTERN_NOT_EXPR)||(LA199_0>=GUARD_EXPR && LA199_0<=OBSERVER_EXPR)||LA199_0==MATCH_UNTIL_EXPR) ) {
                            alt199=1;
                        }


                        switch (alt199) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:585:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp4251);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop199;
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
    // EsperEPL2Ast.g:588:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:589:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt202=2;
            int LA202_0 = input.LA(1);

            if ( (LA202_0==PATTERN_FILTER_EXPR) ) {
                alt202=1;
            }
            else if ( (LA202_0==OBSERVER_EXPR) ) {
                alt202=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 202, 0, input);

                throw nvae;
            }
            switch (alt202) {
                case 1 :
                    // EsperEPL2Ast.g:589:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr4270);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:590:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr4282); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4284); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr4286); 
                    // EsperEPL2Ast.g:590:39: ( valueExprWithTime )*
                    loop201:
                    do {
                        int alt201=2;
                        int LA201_0 = input.LA(1);

                        if ( ((LA201_0>=IN_SET && LA201_0<=REGEXP)||LA201_0==NOT_EXPR||(LA201_0>=SUM && LA201_0<=AVG)||(LA201_0>=COALESCE && LA201_0<=COUNT)||(LA201_0>=CASE && LA201_0<=CASE2)||LA201_0==LAST||(LA201_0>=PREVIOUS && LA201_0<=EXISTS)||(LA201_0>=LW && LA201_0<=CURRENT_TIMESTAMP)||(LA201_0>=NUMERIC_PARAM_RANGE && LA201_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA201_0>=EVAL_AND_EXPR && LA201_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA201_0==EVENT_PROP_EXPR||(LA201_0>=CONCAT && LA201_0<=LIB_FUNCTION)||(LA201_0>=TIME_PERIOD && LA201_0<=ARRAY_EXPR)||(LA201_0>=NOT_IN_SET && LA201_0<=NOT_REGEXP)||(LA201_0>=IN_RANGE && LA201_0<=SUBSELECT_EXPR)||(LA201_0>=EXISTS_SUBSELECT_EXPR && LA201_0<=NOT_IN_SUBSELECT_EXPR)||(LA201_0>=LAST_OPERATOR && LA201_0<=SUBSTITUTION)||LA201_0==NUMBERSETSTAR||(LA201_0>=FIRST_AGGREG && LA201_0<=LAST_AGGREG)||(LA201_0>=INT_TYPE && LA201_0<=NULL_TYPE)||(LA201_0>=STAR && LA201_0<=PLUS)||(LA201_0>=BAND && LA201_0<=BXOR)||(LA201_0>=LT && LA201_0<=GE)||(LA201_0>=MINUS && LA201_0<=MOD)) ) {
                            alt201=1;
                        }


                        switch (alt201) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:590:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr4288);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop201;
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
    // EsperEPL2Ast.g:593:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:594:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:594:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4308); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:594:29: ( IDENT )?
            int alt203=2;
            int LA203_0 = input.LA(1);

            if ( (LA203_0==IDENT) ) {
                alt203=1;
            }
            switch (alt203) {
                case 1 :
                    // EsperEPL2Ast.g:594:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr4310); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr4313); 
            // EsperEPL2Ast.g:594:48: ( propertyExpression )?
            int alt204=2;
            int LA204_0 = input.LA(1);

            if ( (LA204_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt204=1;
            }
            switch (alt204) {
                case 1 :
                    // EsperEPL2Ast.g:594:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr4315);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:594:68: ( valueExpr )*
            loop205:
            do {
                int alt205=2;
                int LA205_0 = input.LA(1);

                if ( ((LA205_0>=IN_SET && LA205_0<=REGEXP)||LA205_0==NOT_EXPR||(LA205_0>=SUM && LA205_0<=AVG)||(LA205_0>=COALESCE && LA205_0<=COUNT)||(LA205_0>=CASE && LA205_0<=CASE2)||(LA205_0>=PREVIOUS && LA205_0<=EXISTS)||(LA205_0>=INSTANCEOF && LA205_0<=CURRENT_TIMESTAMP)||(LA205_0>=EVAL_AND_EXPR && LA205_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA205_0==EVENT_PROP_EXPR||(LA205_0>=CONCAT && LA205_0<=LIB_FUNCTION)||LA205_0==ARRAY_EXPR||(LA205_0>=NOT_IN_SET && LA205_0<=NOT_REGEXP)||(LA205_0>=IN_RANGE && LA205_0<=SUBSELECT_EXPR)||(LA205_0>=EXISTS_SUBSELECT_EXPR && LA205_0<=NOT_IN_SUBSELECT_EXPR)||LA205_0==SUBSTITUTION||(LA205_0>=FIRST_AGGREG && LA205_0<=LAST_AGGREG)||(LA205_0>=INT_TYPE && LA205_0<=NULL_TYPE)||(LA205_0>=STAR && LA205_0<=PLUS)||(LA205_0>=BAND && LA205_0<=BXOR)||(LA205_0>=LT && LA205_0<=GE)||(LA205_0>=MINUS && LA205_0<=MOD)) ) {
                    alt205=1;
                }


                switch (alt205) {
            	case 1 :
            	    // EsperEPL2Ast.g:594:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr4319);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop205;
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
    // EsperEPL2Ast.g:597:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:598:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt206=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt206=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt206=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt206=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt206=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 206, 0, input);

                throw nvae;
            }

            switch (alt206) {
                case 1 :
                    // EsperEPL2Ast.g:598:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4337); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4339);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4341);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:599:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4349); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4351);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:600:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4359); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4361);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:601:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4368); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange4370);
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
    // EsperEPL2Ast.g:604:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:605:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:609:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:610:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:610:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam4399); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam4401);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:610:35: ( valueExpr )*
            loop207:
            do {
                int alt207=2;
                int LA207_0 = input.LA(1);

                if ( ((LA207_0>=IN_SET && LA207_0<=REGEXP)||LA207_0==NOT_EXPR||(LA207_0>=SUM && LA207_0<=AVG)||(LA207_0>=COALESCE && LA207_0<=COUNT)||(LA207_0>=CASE && LA207_0<=CASE2)||(LA207_0>=PREVIOUS && LA207_0<=EXISTS)||(LA207_0>=INSTANCEOF && LA207_0<=CURRENT_TIMESTAMP)||(LA207_0>=EVAL_AND_EXPR && LA207_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA207_0==EVENT_PROP_EXPR||(LA207_0>=CONCAT && LA207_0<=LIB_FUNCTION)||LA207_0==ARRAY_EXPR||(LA207_0>=NOT_IN_SET && LA207_0<=NOT_REGEXP)||(LA207_0>=IN_RANGE && LA207_0<=SUBSELECT_EXPR)||(LA207_0>=EXISTS_SUBSELECT_EXPR && LA207_0<=NOT_IN_SUBSELECT_EXPR)||LA207_0==SUBSTITUTION||(LA207_0>=FIRST_AGGREG && LA207_0<=LAST_AGGREG)||(LA207_0>=INT_TYPE && LA207_0<=NULL_TYPE)||(LA207_0>=STAR && LA207_0<=PLUS)||(LA207_0>=BAND && LA207_0<=BXOR)||(LA207_0>=LT && LA207_0<=GE)||(LA207_0>=MINUS && LA207_0<=MOD)) ) {
                    alt207=1;
                }


                switch (alt207) {
            	case 1 :
            	    // EsperEPL2Ast.g:610:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam4404);
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
    // $ANTLR end "filterParam"


    // $ANTLR start "filterParamComparator"
    // EsperEPL2Ast.g:613:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:614:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt220=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt220=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt220=2;
                }
                break;
            case LT:
                {
                alt220=3;
                }
                break;
            case LE:
                {
                alt220=4;
                }
                break;
            case GT:
                {
                alt220=5;
                }
                break;
            case GE:
                {
                alt220=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt220=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt220=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt220=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt220=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt220=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt220=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 220, 0, input);

                throw nvae;
            }

            switch (alt220) {
                case 1 :
                    // EsperEPL2Ast.g:614:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator4420); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4422);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:615:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator4429); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4431);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:616:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator4438); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4440);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:617:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator4447); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4449);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:618:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator4456); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4458);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:619:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator4465); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator4467);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:620:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4474); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:620:41: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:620:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4483);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:620:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4486);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:620:76: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:620:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4490);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:620:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4493);
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
                    // EsperEPL2Ast.g:621:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4507); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:621:45: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:621:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4516);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:621:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4519);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:621:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:621:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4523);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:621:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4526);
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
                    // EsperEPL2Ast.g:622:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4540); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:622:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:622:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4549);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:622:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4552);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:622:73: ( constant[false] | filterIdentifier )*
                    loop213:
                    do {
                        int alt213=3;
                        int LA213_0 = input.LA(1);

                        if ( ((LA213_0>=INT_TYPE && LA213_0<=NULL_TYPE)) ) {
                            alt213=1;
                        }
                        else if ( (LA213_0==EVENT_FILTER_IDENT) ) {
                            alt213=2;
                        }


                        switch (alt213) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:622:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4556);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:622:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4559);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop213;
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
                    // EsperEPL2Ast.g:623:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4574); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:623:42: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:623:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4583);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:623:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4586);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:623:77: ( constant[false] | filterIdentifier )*
                    loop215:
                    do {
                        int alt215=3;
                        int LA215_0 = input.LA(1);

                        if ( ((LA215_0>=INT_TYPE && LA215_0<=NULL_TYPE)) ) {
                            alt215=1;
                        }
                        else if ( (LA215_0==EVENT_FILTER_IDENT) ) {
                            alt215=2;
                        }


                        switch (alt215) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:623:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator4590);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:623:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4593);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop215;
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
                    // EsperEPL2Ast.g:624:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4608); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:624:27: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:624:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4611);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:624:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4614);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:624:62: ( constant[false] | filterIdentifier )
                    int alt217=2;
                    int LA217_0 = input.LA(1);

                    if ( ((LA217_0>=INT_TYPE && LA217_0<=NULL_TYPE)) ) {
                        alt217=1;
                    }
                    else if ( (LA217_0==EVENT_FILTER_IDENT) ) {
                        alt217=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 217, 0, input);

                        throw nvae;
                    }
                    switch (alt217) {
                        case 1 :
                            // EsperEPL2Ast.g:624:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4618);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:624:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4621);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:625:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4629); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:625:31: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:625:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4632);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:625:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4635);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:625:66: ( constant[false] | filterIdentifier )
                    int alt219=2;
                    int LA219_0 = input.LA(1);

                    if ( ((LA219_0>=INT_TYPE && LA219_0<=NULL_TYPE)) ) {
                        alt219=1;
                    }
                    else if ( (LA219_0==EVENT_FILTER_IDENT) ) {
                        alt219=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 219, 0, input);

                        throw nvae;
                    }
                    switch (alt219) {
                        case 1 :
                            // EsperEPL2Ast.g:625:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator4639);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:625:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator4642);
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
    // EsperEPL2Ast.g:628:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:629:2: ( constant[false] | filterIdentifier )
            int alt221=2;
            int LA221_0 = input.LA(1);

            if ( ((LA221_0>=INT_TYPE && LA221_0<=NULL_TYPE)) ) {
                alt221=1;
            }
            else if ( (LA221_0==EVENT_FILTER_IDENT) ) {
                alt221=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 221, 0, input);

                throw nvae;
            }
            switch (alt221) {
                case 1 :
                    // EsperEPL2Ast.g:629:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom4656);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:630:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4662);
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
    // EsperEPL2Ast.g:632:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:633:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:633:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4673); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4675); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4677);
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
    // EsperEPL2Ast.g:636:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:637:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:637:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4696); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4698);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:637:44: ( eventPropertyAtomic )*
            loop222:
            do {
                int alt222=2;
                int LA222_0 = input.LA(1);

                if ( ((LA222_0>=EVENT_PROP_SIMPLE && LA222_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt222=1;
                }


                switch (alt222) {
            	case 1 :
            	    // EsperEPL2Ast.g:637:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4701);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop222;
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
    // EsperEPL2Ast.g:640:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:641:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt223=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt223=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt223=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt223=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt223=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt223=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt223=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 223, 0, input);

                throw nvae;
            }

            switch (alt223) {
                case 1 :
                    // EsperEPL2Ast.g:641:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4720); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4722); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:642:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4729); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4731); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4733); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:643:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4740); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4742); 
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
                    // EsperEPL2Ast.g:644:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4757); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4759); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:645:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4766); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4768); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4770); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:646:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4777); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4779); 
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
    // EsperEPL2Ast.g:649:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:650:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:650:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4806); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4808);
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
    // EsperEPL2Ast.g:653:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:654:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt234=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt234=1;
                }
                break;
            case HOUR_PART:
                {
                alt234=2;
                }
                break;
            case MINUTE_PART:
                {
                alt234=3;
                }
                break;
            case SECOND_PART:
                {
                alt234=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt234=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 234, 0, input);

                throw nvae;
            }

            switch (alt234) {
                case 1 :
                    // EsperEPL2Ast.g:654:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4824);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:654:13: ( hourPart )?
                    int alt224=2;
                    int LA224_0 = input.LA(1);

                    if ( (LA224_0==HOUR_PART) ) {
                        alt224=1;
                    }
                    switch (alt224) {
                        case 1 :
                            // EsperEPL2Ast.g:654:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4827);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:654:25: ( minutePart )?
                    int alt225=2;
                    int LA225_0 = input.LA(1);

                    if ( (LA225_0==MINUTE_PART) ) {
                        alt225=1;
                    }
                    switch (alt225) {
                        case 1 :
                            // EsperEPL2Ast.g:654:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4832);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:654:39: ( secondPart )?
                    int alt226=2;
                    int LA226_0 = input.LA(1);

                    if ( (LA226_0==SECOND_PART) ) {
                        alt226=1;
                    }
                    switch (alt226) {
                        case 1 :
                            // EsperEPL2Ast.g:654:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4837);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:654:53: ( millisecondPart )?
                    int alt227=2;
                    int LA227_0 = input.LA(1);

                    if ( (LA227_0==MILLISECOND_PART) ) {
                        alt227=1;
                    }
                    switch (alt227) {
                        case 1 :
                            // EsperEPL2Ast.g:654:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4842);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:655:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4849);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:655:13: ( minutePart )?
                    int alt228=2;
                    int LA228_0 = input.LA(1);

                    if ( (LA228_0==MINUTE_PART) ) {
                        alt228=1;
                    }
                    switch (alt228) {
                        case 1 :
                            // EsperEPL2Ast.g:655:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4852);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:655:27: ( secondPart )?
                    int alt229=2;
                    int LA229_0 = input.LA(1);

                    if ( (LA229_0==SECOND_PART) ) {
                        alt229=1;
                    }
                    switch (alt229) {
                        case 1 :
                            // EsperEPL2Ast.g:655:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4857);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:655:41: ( millisecondPart )?
                    int alt230=2;
                    int LA230_0 = input.LA(1);

                    if ( (LA230_0==MILLISECOND_PART) ) {
                        alt230=1;
                    }
                    switch (alt230) {
                        case 1 :
                            // EsperEPL2Ast.g:655:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4862);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:656:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4869);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:656:15: ( secondPart )?
                    int alt231=2;
                    int LA231_0 = input.LA(1);

                    if ( (LA231_0==SECOND_PART) ) {
                        alt231=1;
                    }
                    switch (alt231) {
                        case 1 :
                            // EsperEPL2Ast.g:656:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4872);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:656:29: ( millisecondPart )?
                    int alt232=2;
                    int LA232_0 = input.LA(1);

                    if ( (LA232_0==MILLISECOND_PART) ) {
                        alt232=1;
                    }
                    switch (alt232) {
                        case 1 :
                            // EsperEPL2Ast.g:656:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4877);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:657:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4884);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:657:15: ( millisecondPart )?
                    int alt233=2;
                    int LA233_0 = input.LA(1);

                    if ( (LA233_0==MILLISECOND_PART) ) {
                        alt233=1;
                    }
                    switch (alt233) {
                        case 1 :
                            // EsperEPL2Ast.g:657:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4887);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:658:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4894);
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
    // EsperEPL2Ast.g:661:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:662:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:662:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4908); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4910);
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
    // EsperEPL2Ast.g:665:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:666:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:666:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4925); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4927);
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
    // EsperEPL2Ast.g:669:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:670:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:670:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4942); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4944);
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
    // EsperEPL2Ast.g:673:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:674:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:674:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4959); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4961);
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
    // EsperEPL2Ast.g:677:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:678:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:678:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4976); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4978);
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
    // EsperEPL2Ast.g:681:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:682:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:682:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4993); 
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
    // EsperEPL2Ast.g:685:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:686:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt235=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt235=1;
                }
                break;
            case LONG_TYPE:
                {
                alt235=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt235=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt235=4;
                }
                break;
            case STRING_TYPE:
                {
                alt235=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt235=6;
                }
                break;
            case NULL_TYPE:
                {
                alt235=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 235, 0, input);

                throw nvae;
            }

            switch (alt235) {
                case 1 :
                    // EsperEPL2Ast.g:686:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant5009); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:687:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant5018); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:688:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant5027); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:689:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant5036); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:690:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant5052); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:691:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant5068); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:692:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant5081); 
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
    // EsperEPL2Ast.g:695:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:696:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L,0x007F00E000000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L,0x007F00E000000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L,0x007F006000000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L,0x007F006000000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000200000L,0x0000142008058000L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_createIndexExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_createSchemaExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule245 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule249 = new BitSet(new long[]{0x0000000000000002L,0x0000100000000000L});
    public static final BitSet FOLLOW_forExpr_in_eplExpressionRule252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr271 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onStreamExpr_in_onExpr273 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004700000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr278 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onUpdateExpr_in_onExpr282 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr289 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000001800000L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr299 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_STREAM_in_onStreamExpr321 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onStreamExpr324 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onStreamExpr328 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_onStreamExpr331 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr350 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr352 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr354 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr357 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr360 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr377 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr379 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr382 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr402 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr404 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_DISTINCT_in_onSelectExpr407 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr410 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x00000000C0001800L,0x0000000002000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr412 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x00000000C0001800L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr415 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x00000000C0001000L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr419 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000080001000L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr422 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr425 = new BitSet(new long[]{0x0000000000000008L,0x0000000800000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_onSelectExpr428 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr448 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr450 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr452 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr454 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput473 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr491 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr493 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr496 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr500 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_UPDATE_EXPR_in_onUpdateExpr515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onUpdateExpr517 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_onSetAssignment_in_onUpdateExpr519 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000800L,0x0000080000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_whereClause_in_onUpdateExpr522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment537 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_onSetAssignment539 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment542 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom556 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom558 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom561 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr579 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr581 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000120L,0x0000000400020000L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr584 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000120L,0x0000000400020000L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr588 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000120L,0x0000000400020000L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr591 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000003L,0x0000000000000120L,0x0000000400020000L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr605 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr608 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr637 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr648 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_INDEX_EXPR_in_createIndexExpr668 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr670 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_createIndexExpr672 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_exprCol_in_createIndexExpr674 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert689 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert691 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList708 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList710 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L,0x0000000000000002L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList713 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L,0x0000000000000002L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_LIST_in_createColTypeList732 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList734 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList737 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_CREATE_COL_TYPE_in_createColTypeListElement752 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement754 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createColTypeListElement756 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_LBRACK_in_createColTypeListElement758 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement783 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement803 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement807 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement829 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement832 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr868 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createVariableExpr870 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr872 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr875 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_in_createSchemaExpr895 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr897 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000120L,0x0000E00400020000L});
    public static final BitSet FOLLOW_variantList_in_createSchemaExpr900 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_createColTypeList_in_createSchemaExpr902 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_QUAL_in_createSchemaExpr913 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr915 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_SCHEMA_EXPR_INH_in_createSchemaExpr926 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createSchemaExpr928 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_exprCol_in_createSchemaExpr930 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_VARIANT_LIST_in_variantList951 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_variantList953 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr971 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000400000200000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr977 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr982 = new BitSet(new long[]{0x0000000000000002L,0x0000004800000000L,0x00002F00C0001800L});
    public static final BitSet FOLLOW_matchRecogClause_in_selectExpr987 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00002F00C0001800L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr994 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00002F00C0001000L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr1002 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00002F0080001000L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr1009 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x00002F0080000000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr1016 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L,0x0000000080000000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr1023 = new BitSet(new long[]{0x0000000000000002L,0x0000000800000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr1047 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr1049 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr1058 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_exprCol_in_insertIntoExpr1061 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXPRCOL_in_exprCol1080 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1082 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_exprCol1085 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause1103 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause1105 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_DISTINCT_in_selectClause1118 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_selectionList_in_selectClause1121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1135 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause1138 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000003D000000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause1141 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000003D000000L});
    public static final BitSet FOLLOW_FOR_in_forExpr1161 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_forExpr1163 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_forExpr1165 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_MATCH_RECOGNIZE_in_matchRecogClause1184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPartitionBy_in_matchRecogClause1186 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasures_in_matchRecogClause1193 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x4200000000000000L});
    public static final BitSet FOLLOW_ALL_in_matchRecogClause1199 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x4200000000000000L});
    public static final BitSet FOLLOW_matchRecogMatchesAfterSkip_in_matchRecogClause1205 = new BitSet(new long[]{0x0000800000000000L,0x0000000000000000L,0x0000000000000000L,0x4200000000000000L});
    public static final BitSet FOLLOW_matchRecogPattern_in_matchRecogClause1211 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_matchRecogMatchesInterval_in_matchRecogClause1217 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x8000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_matchRecogDefine_in_matchRecogClause1223 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PARTITION_in_matchRecogPartitionBy1241 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogPartitionBy1243 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_MATCHREC_AFTER_SKIP_in_matchRecogMatchesAfterSkip1260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1262 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1266 = new BitSet(new long[]{0x0020000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_set_in_matchRecogMatchesAfterSkip1268 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesAfterSkip1274 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_INTERVAL_in_matchRecogMatchesInterval1289 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMatchesInterval1291 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_matchRecogMatchesInterval1293 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURES_in_matchRecogMeasures1309 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogMeasureListElement_in_matchRecogMeasures1311 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_MEASURE_ITEM_in_matchRecogMeasureListElement1328 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogMeasureListElement1330 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogMeasureListElement1332 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_in_matchRecogPattern1352 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPattern1354 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x1800000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ALTER_in_matchRecogPatternAlteration1377 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1379 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternConcat_in_matchRecogPatternAlteration1381 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_CONCAT_in_matchRecogPatternConcat1399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternUnary_in_matchRecogPatternConcat1401 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x2400000000000000L});
    public static final BitSet FOLLOW_matchRecogPatternNested_in_matchRecogPatternUnary1416 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_matchRecogPatternAtom_in_matchRecogPatternUnary1421 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_NESTED_in_matchRecogPatternNested1436 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogPatternAlteration_in_matchRecogPatternNested1438 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000000001A000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternNested1440 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_PATTERN_ATOM_in_matchRecogPatternAtom1469 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogPatternAtom1471 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000000001A000L});
    public static final BitSet FOLLOW_set_in_matchRecogPatternAtom1475 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_QUESTION_in_matchRecogPatternAtom1487 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_in_matchRecogDefine1509 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchRecogDefineItem_in_matchRecogDefine1511 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_MATCHREC_DEFINE_ITEM_in_matchRecogDefineItem1528 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_matchRecogDefineItem1530 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_matchRecogDefineItem1532 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1549 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList1552 = new BitSet(new long[]{0x0000400000000002L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement1568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1578 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1580 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1583 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1597 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1599 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1602 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1635 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1637 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1640 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1644 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1647 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1662 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1664 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1667 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1671 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1674 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1689 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1694 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1698 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1701 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1716 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1718 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1721 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1725 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1728 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1749 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1752 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000100L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1756 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000100L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1760 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000100L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1764 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000100L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1768 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1773 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1778 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_set_in_streamExpression1782 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1806 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1808 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1811 = new BitSet(new long[]{0x0000000037CC23C8L,0x0100000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1813 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1817 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1837 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1839 = new BitSet(new long[]{0x0000000000000008L,0x0200000000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1858 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1860 = new BitSet(new long[]{0x0000000000000000L,0x1C00000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1863 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1866 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1870 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1872 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1892 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1902 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1904 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1907 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1923 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1926 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1947 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1949 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1966 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1968 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1970 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1978 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1999 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression2001 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression2003 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression2006 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2020 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr2023 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr2040 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2042 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr2044 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr2047 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause2069 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause2071 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause2089 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2091 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause2094 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause2112 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2114 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause2117 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement2137 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement2139 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement2141 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause2164 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause2166 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr2184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2186 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000F000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr2198 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr2200 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2203 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr2220 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2222 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr2233 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2235 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr2251 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2253 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr2264 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2266 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr2282 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr2284 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr2295 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L,0x0000000000000000L,0x0000000004700000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr2297 = new BitSet(new long[]{0x0000000000000008L,0x0000080000000000L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2300 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_LIMIT_EXPR_in_outputLimitExpr2313 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_outputLimitAfter_in_outputLimitExpr2315 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AFTER_in_outputLimitAfter2330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitAfter2332 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x000F000000000000L});
    public static final BitSet FOLLOW_number_in_outputLimitAfter2335 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause2351 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2354 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x000F000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2356 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x000F000000000000L,0x0000000000000060L});
    public static final BitSet FOLLOW_number_in_rowLimitClause2360 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause2362 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause2366 = new BitSet(new long[]{0x0000000000000008L,0x0000001000000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause2369 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet2387 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2389 = new BitSet(new long[]{0x0020000037CC23C0L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2391 = new BitSet(new long[]{0x0020000037CC23C0L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2393 = new BitSet(new long[]{0x0020000037CC23C0L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2395 = new BitSet(new long[]{0x0020000037CC23C0L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2397 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet2399 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr2416 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2418 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr2431 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2433 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr2446 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2448 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr2460 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr2462 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2484 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue2509 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023F8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue2518 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue2523 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice2549 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2551 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2553 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2556 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice2570 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2572 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2574 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2577 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice2591 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2593 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2595 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice2607 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2609 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2611 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2623 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2625 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2627 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023F8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2636 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2641 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2656 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2658 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023F8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2667 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2672 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2685 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2687 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2698 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2730 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2739 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2744 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2762 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2773 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2778 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2794 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2801 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2814 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2823 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2830 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2838 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2840 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2842 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2866 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2881 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2883 = new BitSet(new long[]{0x0000000000000008L,0x0001400000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2914 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2943 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2946 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L,0x007F000000002000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2949 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L,0x007F000000002000L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2952 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L,0x007F000000002000L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2956 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2959 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2962 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2983 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2986 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2989 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2992 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator3011 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator3014 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator3017 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator3020 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator3039 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator3042 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator3045 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator3048 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr3069 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr3071 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr3090 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr3092 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr3111 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr3113 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr3132 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3134 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3136 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr3148 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr3150 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000400L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr3152 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr3171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr3173 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DISTINCT_in_subQueryExpr3189 = new BitSet(new long[]{0x0000400000000000L,0x0000000000000000L,0x0000000000C00000L,0x0000000000000002L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr3192 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr3194 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr3197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr3215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr3217 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000100L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr3220 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr3225 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000003L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr3229 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr3232 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr3252 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3255 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr3268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr3271 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr3291 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3293 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000A00L});
    public static final BitSet FOLLOW_set_in_inExpr3295 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3301 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30F400L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3304 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30F400L});
    public static final BitSet FOLLOW_set_in_inExpr3308 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr3323 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3325 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000A00L});
    public static final BitSet FOLLOW_set_in_inExpr3327 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3333 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30F400L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3336 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30F400L});
    public static final BitSet FOLLOW_set_in_inExpr3340 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr3355 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3357 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000A00L});
    public static final BitSet FOLLOW_set_in_inExpr3359 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3365 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3367 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_set_in_inExpr3369 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr3384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3386 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000A00L});
    public static final BitSet FOLLOW_set_in_inExpr3388 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3394 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr3396 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_set_in_inExpr3398 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr3421 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3423 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3425 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3427 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr3438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3440 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3442 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr3445 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr3465 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3467 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3469 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3472 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr3485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3487 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3489 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr3492 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr3511 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3513 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3515 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr3526 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3528 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr3530 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc3549 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3552 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc3567 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3570 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3574 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc3585 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3589 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3593 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc3607 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3610 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3614 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3628 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3632 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3643 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3646 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3650 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_AGGREG_in_builtinFunc3661 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3664 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3668 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FIRST_AGGREG_in_builtinFunc3679 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3682 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3686 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3698 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3700 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3702 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3705 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3720 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3722 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3737 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3741 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3743 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3756 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3758 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3760 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3763 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3777 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3779 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3781 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3793 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3795 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3807 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3827 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3830 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3851 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3853 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3855 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3867 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3869 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3871 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3883 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3885 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3887 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3898 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3900 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3902 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3914 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3916 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3918 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3929 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3931 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3933 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3944 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3946 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3948 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3959 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3961 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3963 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3975 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3977 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3979 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3982 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc4003 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc4006 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_libFunc4010 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc4013 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc4018 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule4038 = new BitSet(new long[]{0x000000000000D800L,0x0034000000000000L,0x00000000000000C0L,0x0000002020000000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule4042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice4056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice4061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice4071 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4073 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice4087 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice4089 = new BitSet(new long[]{0x000000000000D800L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice4105 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4107 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice4121 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4123 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E040L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4126 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice4128 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice4130 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_exprChoice4135 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice4149 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice4151 = new BitSet(new long[]{0x000000000000D800L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4154 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice4156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions4177 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions4179 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp4198 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4200 = new BitSet(new long[]{0x000000000000D800L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4202 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4205 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp4221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4223 = new BitSet(new long[]{0x000000000000D800L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4225 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4228 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp4244 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4246 = new BitSet(new long[]{0x000000000000D800L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4248 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp4251 = new BitSet(new long[]{0x000000000000D808L,0x0034000000000000L,0x00000000000000C0L,0x0000000020000000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr4270 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr4282 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4284 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000040L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr4286 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr4288 = new BitSet(new long[]{0x0020000037CC23C8L,0x0003C00000001EE0L,0x3C1B0002000FC000L,0x007F031000003BB8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr4308 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr4310 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr4313 = new BitSet(new long[]{0x0000000037CC23C8L,0x0100000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr4315 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr4319 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange4337 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0080000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4341 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange4349 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange4359 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4361 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange4368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange4370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam4399 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4401 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam4404 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000001CE0L,0x3C130002000FC000L,0x007F0300000023B8L,0x00000000EF30E000L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator4420 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4422 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator4429 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4431 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator4438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4440 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator4447 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4449 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator4456 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator4465 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator4467 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator4474 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4476 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4483 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4486 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4490 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4493 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4496 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator4507 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4509 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4516 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4519 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4523 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4529 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator4540 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4542 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4549 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4552 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4556 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4559 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4563 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator4574 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4576 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4583 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4586 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4590 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4593 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L,0x0000000000001400L});
    public static final BitSet FOLLOW_set_in_filterParamComparator4597 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator4608 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4611 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4614 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4618 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4621 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator4629 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4632 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4635 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L,0x0000000000000000L,0x007F000000000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator4639 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator4642 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom4656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4673 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4675 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4677 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4696 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4698 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000FC00000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4701 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000FC00000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4720 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4722 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4729 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4731 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4733 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4740 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4742 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4744 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4757 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4759 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4766 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4768 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4770 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4777 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4779 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4781 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4806 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4808 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4824 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x03C0000000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4827 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0380000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4832 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0300000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4837 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4842 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4849 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0380000000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4852 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0300000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4857 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4869 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0300000000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4872 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4884 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0200000000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4887 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4894 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4908 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4910 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4925 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4927 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4942 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4944 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4959 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4961 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4976 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4978 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4993 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant5009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant5018 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant5027 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant5036 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant5052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant5068 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant5081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}