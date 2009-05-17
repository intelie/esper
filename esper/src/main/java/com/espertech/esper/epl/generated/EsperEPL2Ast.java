// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-05-12 16:08:33

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BOR", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "PLUS", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "QUESTION", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=159;
    public static final int FLOAT_SUFFIX=291;
    public static final int STAR=232;
    public static final int NUMERIC_PARAM_LIST=100;
    public static final int ISTREAM=60;
    public static final int MOD=251;
    public static final int OUTERJOIN_EXPR=142;
    public static final int BSR=273;
    public static final int LIB_FUNCTION=165;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=93;
    public static final int FULL_OUTERJOIN_EXPR=146;
    public static final int RPAREN=231;
    public static final int LNOT=262;
    public static final int INC=266;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=236;
    public static final int BSR_ASSIGN=274;
    public static final int CAST_EXPR=194;
    public static final int STREAM_EXPR=141;
    public static final int TIMEPERIOD_SECONDS=90;
    public static final int NOT_EQUAL=242;
    public static final int METADATASQL=67;
    public static final int EVENT_FILTER_PROPERTY_EXPR=109;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=103;
    public static final int FOLLOWED_BY=255;
    public static final int HOUR_PART=170;
    public static final int RBRACK=234;
    public static final int MATCH_UNTIL_RANGE_CLOSED=209;
    public static final int GE=246;
    public static final int METHOD_JOIN_EXPR=205;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=108;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=107;
    public static final int MINUS_ASSIGN=267;
    public static final int ELSE=30;
    public static final int EVENT_FILTER_NOT_IN=119;
    public static final int NUM_DOUBLE=224;
    public static final int INSERTINTO_STREAM_NAME=182;
    public static final int UNARY_MINUS=166;
    public static final int TIMEPERIOD_MILLISEC=91;
    public static final int LCURLY=252;
    public static final int RETAINUNION=63;
    public static final int DBWHERE_CLAUSE=180;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=117;
    public static final int GROUP=44;
    public static final int EMAILAT=282;
    public static final int WS=283;
    public static final int SUBSELECT_GROUP_EXPR=186;
    public static final int ON_SELECT_INSERT_EXPR=200;
    public static final int ESCAPECHAR=257;
    public static final int SL_COMMENT=284;
    public static final int NULL_TYPE=223;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=207;
    public static final int GT=244;
    public static final int BNOT=263;
    public static final int WHERE_EXPR=128;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=143;
    public static final int LAND=280;
    public static final int NOT_REGEXP=177;
    public static final int MATCH_UNTIL_EXPR=206;
    public static final int EVENT_PROP_EXPR=150;
    public static final int LBRACK=233;
    public static final int VIEW_EXPR=125;
    public static final int ANNOTATION=214;
    public static final int LONG_TYPE=218;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=110;
    public static final int TIMEPERIOD_SEC=88;
    public static final int ON_SELECT_EXPR=199;
    public static final int TICKED_STRING_LITERAL=258;
    public static final int MINUTE_PART=171;
    public static final int PATTERN_NOT_EXPR=106;
    public static final int SUM=18;
    public static final int SQL_NE=241;
    public static final int HexDigit=289;
    public static final int LPAREN=230;
    public static final int AT=81;
    public static final int IN_SUBSELECT_EXPR=188;
    public static final int AS=17;
    public static final int BOOLEAN_TRUE=94;
    public static final int OR_EXPR=11;
    public static final int THEN=32;
    public static final int NOT_IN_RANGE=184;
    public static final int OFFSET=98;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=68;
    public static final int SECOND_PART=172;
    public static final int IDENT=227;
    public static final int DATABASE_JOIN_EXPR=127;
    public static final int PLUS=248;
    public static final int BXOR=240;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=82;
    public static final int EXISTS=70;
    public static final int EVENT_PROP_INDEXED=153;
    public static final int TIMEPERIOD_MILLISECOND=92;
    public static final int EVAL_NOTEQUALS_EXPR=134;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=208;
    public static final int CREATE_VARIABLE_EXPR=204;
    public static final int CREATE_WINDOW_COL_TYPE=212;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=104;
    public static final int RIGHT_OUTERJOIN_EXPR=145;
    public static final int NUMBERSETSTAR=213;
    public static final int LAST_OPERATOR=191;
    public static final int PATTERN_FILTER_EXPR=105;
    public static final int EVAL_AND_EXPR=131;
    public static final int LEFT_OUTERJOIN_EXPR=144;
    public static final int EPL_EXPR=225;
    public static final int GROUP_BY_EXPR=147;
    public static final int SET=78;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=73;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=151;
    public static final int MINUS=249;
    public static final int SEMI=281;
    public static final int STAR_ASSIGN=269;
    public static final int COLON=235;
    public static final int EVAL_EQUALS_GROUP_EXPR=135;
    public static final int BAND_ASSIGN=279;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=160;
    public static final int VALUE_NULL=96;
    public static final int NOT_IN_SET=174;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=154;
    public static final int SL=275;
    public static final int NOT_IN_SUBSELECT_EXPR=189;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=123;
    public static final int SR=271;
    public static final int RCURLY=253;
    public static final int PLUS_ASSIGN=265;
    public static final int DAY_PART=169;
    public static final int EXISTS_SUBSELECT_EXPR=187;
    public static final int EVENT_FILTER_IN=118;
    public static final int DIV=250;
    public static final int OBJECT_PARAM_ORDERED_EXPR=102;
    public static final int OctalEscape=288;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=173;
    public static final int PRIOR=69;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=97;
    public static final int SELECTION_EXPR=138;
    public static final int LOR=247;
    public static final int CAST=74;
    public static final int LW=72;
    public static final int WILDCARD_SELECT=181;
    public static final int EXPONENT=290;
    public static final int LT=243;
    public static final int PATTERN_INCL_EXPR=126;
    public static final int ORDER_BY_EXPR=148;
    public static final int BOOL_TYPE=222;
    public static final int MOD_ASSIGN=270;
    public static final int ANNOTATION_ARRAY=215;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=190;
    public static final int EQUALS=228;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=64;
    public static final int DIV_ASSIGN=264;
    public static final int SL_ASSIGN=276;
    public static final int PATTERN=65;
    public static final int SQL=66;
    public static final int WEEKDAY=71;
    public static final int FULL=40;
    public static final int INSERT=54;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=168;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=95;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=136;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=120;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=89;
    public static final int FLOAT_TYPE=219;
    public static final int SUBSELECT_EXPR=185;
    public static final int ANNOTATION_VALUE=216;
    public static final int CONCAT=164;
    public static final int NUMERIC_PARAM_RANGE=99;
    public static final int CLASS_IDENT=122;
    public static final int ON_EXPR=197;
    public static final int CREATE_WINDOW_EXPR=195;
    public static final int PROPERTY_SELECTION_STREAM=112;
    public static final int ON_DELETE_EXPR=198;
    public static final int ON=41;
    public static final int NUM_LONG=259;
    public static final int TIME_PERIOD=167;
    public static final int DOUBLE_TYPE=220;
    public static final int DELETE=76;
    public static final int INT_TYPE=217;
    public static final int EVAL_BITWISE_EXPR=130;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=149;
    public static final int TIMEPERIOD_HOURS=85;
    public static final int VARIABLE=79;
    public static final int SUBSTITUTION=193;
    public static final int UNTIL=80;
    public static final int STRING_TYPE=221;
    public static final int ON_SET_EXPR=203;
    public static final int NUM_INT=254;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=202;
    public static final int NUM_FLOAT=260;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=111;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=286;
    public static final int WEEKDAY_OPERATOR=192;
    public static final int WHERE=16;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=211;
    public static final int DEC=268;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=101;
    public static final int BXOR_ASSIGN=277;
    public static final int ORDER=56;
    public static final int SNAPSHOT=77;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=156;
    public static final int EVENT_FILTER_PARAM=115;
    public static final int IRSTREAM=61;
    public static final int MAX=20;
    public static final int TIMEPERIOD_DAYS=83;
    public static final int EVENT_FILTER_RANGE=116;
    public static final int ML_COMMENT=285;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=155;
    public static final int BOR_ASSIGN=278;
    public static final int COMMA=226;
    public static final int WHEN_LIMIT_EXPR=161;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=158;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=84;
    public static final int BOR=239;
    public static final int EQUAL=261;
    public static final int EVENT_FILTER_NOT_BETWEEN=121;
    public static final int IN_RANGE=183;
    public static final int DOT=229;
    public static final int CURRENT_TIMESTAMP=75;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=113;
    public static final int INSERTINTO_EXPR=162;
    public static final int HAVING_EXPR=129;
    public static final int UNIDIRECTIONAL=62;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=210;
    public static final int EVAL_EQUALS_EXPR=133;
    public static final int TIMEPERIOD_MINUTES=87;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=176;
    public static final int EVENT_LIMIT_EXPR=157;
    public static final int NOT_BETWEEN=175;
    public static final int TIMEPERIOD_MINUTE=86;
    public static final int EVAL_OR_EXPR=132;
    public static final int ON_SELECT_INSERT_OUTPUT=201;
    public static final int BAND=238;
    public static final int QUOTED_STRING_LITERAL=237;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=256;
    public static final int OBSERVER_EXPR=124;
    public static final int EVENT_FILTER_IDENT=114;
    public static final int EVENT_PROP_MAPPED=152;
    public static final int UnicodeEscape=287;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=178;
    public static final int SELECTION_ELEMENT_EXPR=139;
    public static final int CREATE_WINDOW_SELECT_EXPR=196;
    public static final int INSERTINTO_EXPRCOL=163;
    public static final int WINDOW=5;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=140;
    public static final int SR_ASSIGN=272;
    public static final int DBFROM_CLAUSE=179;
    public static final int LE=245;
    public static final int EVAL_IDENT=137;

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

                if ( ((LA29_0>=IN_SET && LA29_0<=REGEXP)||LA29_0==NOT_EXPR||(LA29_0>=SUM && LA29_0<=AVG)||(LA29_0>=COALESCE && LA29_0<=COUNT)||(LA29_0>=CASE && LA29_0<=CASE2)||(LA29_0>=PREVIOUS && LA29_0<=EXISTS)||(LA29_0>=INSTANCEOF && LA29_0<=CURRENT_TIMESTAMP)||(LA29_0>=EVAL_AND_EXPR && LA29_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA29_0==EVENT_PROP_EXPR||(LA29_0>=CONCAT && LA29_0<=LIB_FUNCTION)||LA29_0==ARRAY_EXPR||(LA29_0>=NOT_IN_SET && LA29_0<=NOT_REGEXP)||(LA29_0>=IN_RANGE && LA29_0<=SUBSELECT_EXPR)||(LA29_0>=EXISTS_SUBSELECT_EXPR && LA29_0<=NOT_IN_SUBSELECT_EXPR)||LA29_0==SUBSTITUTION||(LA29_0>=INT_TYPE && LA29_0<=NULL_TYPE)||LA29_0==STAR||(LA29_0>=BAND && LA29_0<=BXOR)||(LA29_0>=LT && LA29_0<=GE)||(LA29_0>=PLUS && LA29_0<=MOD)) ) {
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

            if ( ((LA35_0>=IN_SET && LA35_0<=REGEXP)||LA35_0==NOT_EXPR||(LA35_0>=SUM && LA35_0<=AVG)||(LA35_0>=COALESCE && LA35_0<=COUNT)||(LA35_0>=CASE && LA35_0<=CASE2)||(LA35_0>=PREVIOUS && LA35_0<=EXISTS)||(LA35_0>=INSTANCEOF && LA35_0<=CURRENT_TIMESTAMP)||(LA35_0>=EVAL_AND_EXPR && LA35_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA35_0==EVENT_PROP_EXPR||(LA35_0>=CONCAT && LA35_0<=LIB_FUNCTION)||LA35_0==ARRAY_EXPR||(LA35_0>=NOT_IN_SET && LA35_0<=NOT_REGEXP)||(LA35_0>=IN_RANGE && LA35_0<=SUBSELECT_EXPR)||(LA35_0>=EXISTS_SUBSELECT_EXPR && LA35_0<=NOT_IN_SUBSELECT_EXPR)||LA35_0==SUBSTITUTION||(LA35_0>=INT_TYPE && LA35_0<=NULL_TYPE)||LA35_0==STAR||(LA35_0>=BAND && LA35_0<=BXOR)||(LA35_0>=LT && LA35_0<=GE)||(LA35_0>=PLUS && LA35_0<=MOD)) ) {
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
    // EsperEPL2Ast.g:156:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:157:2: ( ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:157:4: ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
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

            // EsperEPL2Ast.g:160:3: ( whereClause )?
            int alt37=2;
            int LA37_0 = input.LA(1);

            if ( (LA37_0==WHERE_EXPR) ) {
                alt37=1;
            }
            switch (alt37) {
                case 1 :
                    // EsperEPL2Ast.g:160:4: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr771);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:161:3: ( groupByClause )?
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( (LA38_0==GROUP_BY_EXPR) ) {
                alt38=1;
            }
            switch (alt38) {
                case 1 :
                    // EsperEPL2Ast.g:161:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr778);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:162:3: ( havingClause )?
            int alt39=2;
            int LA39_0 = input.LA(1);

            if ( (LA39_0==HAVING_EXPR) ) {
                alt39=1;
            }
            switch (alt39) {
                case 1 :
                    // EsperEPL2Ast.g:162:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr785);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:163:3: ( outputLimitExpr )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=EVENT_LIMIT_EXPR && LA40_0<=CRONTAB_LIMIT_EXPR)||LA40_0==WHEN_LIMIT_EXPR) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:163:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr792);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:164:3: ( orderByClause )?
            int alt41=2;
            int LA41_0 = input.LA(1);

            if ( (LA41_0==ORDER_BY_EXPR) ) {
                alt41=1;
            }
            switch (alt41) {
                case 1 :
                    // EsperEPL2Ast.g:164:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr799);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:165:3: ( rowLimitClause )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==ROW_LIMIT_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:165:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr806);
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
    // EsperEPL2Ast.g:168:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:169:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:169:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr823); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:169:24: ( ISTREAM | RSTREAM )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( ((LA43_0>=RSTREAM && LA43_0<=ISTREAM)) ) {
                alt43=1;
            }
            switch (alt43) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr834); 
            // EsperEPL2Ast.g:169:51: ( insertIntoExprCol )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==INSERTINTO_EXPRCOL) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:169:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr837);
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
    // EsperEPL2Ast.g:172:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:173:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:173:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol856); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol858); 
            // EsperEPL2Ast.g:173:31: ( IDENT )*
            loop45:
            do {
                int alt45=2;
                int LA45_0 = input.LA(1);

                if ( (LA45_0==IDENT) ) {
                    alt45=1;
                }


                switch (alt45) {
            	case 1 :
            	    // EsperEPL2Ast.g:173:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol861); 

            	    }
            	    break;

            	default :
            	    break loop45;
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
    // EsperEPL2Ast.g:176:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:177:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) )
            // EsperEPL2Ast.g:177:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause879); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:177:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( ((LA46_0>=RSTREAM && LA46_0<=IRSTREAM)) ) {
                alt46=1;
            }
            switch (alt46) {
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

            pushFollow(FOLLOW_selectionList_in_selectClause894);
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
    // EsperEPL2Ast.g:180:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:181:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:181:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause908);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:181:21: ( streamExpression ( outerJoin )* )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( (LA48_0==STREAM_EXPR) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // EsperEPL2Ast.g:181:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause911);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:181:39: ( outerJoin )*
            	    loop47:
            	    do {
            	        int alt47=2;
            	        int LA47_0 = input.LA(1);

            	        if ( ((LA47_0>=INNERJOIN_EXPR && LA47_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt47=1;
            	        }


            	        switch (alt47) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:181:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause914);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop47;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop48;
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


    // $ANTLR start "selectionList"
    // EsperEPL2Ast.g:184:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:185:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:185:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList931);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:185:25: ( selectionListElement )*
            loop49:
            do {
                int alt49=2;
                int LA49_0 = input.LA(1);

                if ( ((LA49_0>=SELECTION_ELEMENT_EXPR && LA49_0<=SELECTION_STREAM)||LA49_0==WILDCARD_SELECT) ) {
                    alt49=1;
                }


                switch (alt49) {
            	case 1 :
            	    // EsperEPL2Ast.g:185:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList934);
            	    selectionListElement();

            	    state._fsp--;


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
    // $ANTLR end "selectionList"


    // $ANTLR start "selectionListElement"
    // EsperEPL2Ast.g:188:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:189:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt52=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt52=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt52=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt52=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 52, 0, input);

                throw nvae;
            }

            switch (alt52) {
                case 1 :
                    // EsperEPL2Ast.g:189:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement950); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:190:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement960); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement962);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:190:41: ( IDENT )?
                    int alt50=2;
                    int LA50_0 = input.LA(1);

                    if ( (LA50_0==IDENT) ) {
                        alt50=1;
                    }
                    switch (alt50) {
                        case 1 :
                            // EsperEPL2Ast.g:190:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement965); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:191:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement979); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement981); 
                    // EsperEPL2Ast.g:191:31: ( IDENT )?
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==IDENT) ) {
                        alt51=1;
                    }
                    switch (alt51) {
                        case 1 :
                            // EsperEPL2Ast.g:191:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement984); 

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
    // EsperEPL2Ast.g:194:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:195:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:195:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1003);
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
    // EsperEPL2Ast.g:198:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:199:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt57=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt57=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt57=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt57=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt57=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }

            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:199:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1017); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1019);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1022);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:199:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop53:
                    do {
                        int alt53=2;
                        int LA53_0 = input.LA(1);

                        if ( (LA53_0==EVENT_PROP_EXPR) ) {
                            alt53=1;
                        }


                        switch (alt53) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:199:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1026);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1029);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop53;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:200:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1044); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1046);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1049);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:200:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop54:
                    do {
                        int alt54=2;
                        int LA54_0 = input.LA(1);

                        if ( (LA54_0==EVENT_PROP_EXPR) ) {
                            alt54=1;
                        }


                        switch (alt54) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:200:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1053);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1056);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop54;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:201:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1071); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1073);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1076);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:201:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop55:
                    do {
                        int alt55=2;
                        int LA55_0 = input.LA(1);

                        if ( (LA55_0==EVENT_PROP_EXPR) ) {
                            alt55=1;
                        }


                        switch (alt55) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:201:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1080);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1083);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop55;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:202:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1098); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1100);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1103);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:202:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop56:
                    do {
                        int alt56=2;
                        int LA56_0 = input.LA(1);

                        if ( (LA56_0==EVENT_PROP_EXPR) ) {
                            alt56=1;
                        }


                        switch (alt56) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:202:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1107);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1110);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop56;
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
    // EsperEPL2Ast.g:205:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:206:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:206:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1131); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:206:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt58=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt58=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt58=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt58=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt58=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 58, 0, input);

                throw nvae;
            }

            switch (alt58) {
                case 1 :
                    // EsperEPL2Ast.g:206:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1134);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:206:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1138);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:206:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1142);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:206:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1146);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:206:115: ( viewListExpr )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==VIEW_EXPR) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EsperEPL2Ast.g:206:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1150);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:206:131: ( IDENT )?
            int alt60=2;
            int LA60_0 = input.LA(1);

            if ( (LA60_0==IDENT) ) {
                alt60=1;
            }
            switch (alt60) {
                case 1 :
                    // EsperEPL2Ast.g:206:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1155); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:206:140: ( UNIDIRECTIONAL )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==UNIDIRECTIONAL) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // EsperEPL2Ast.g:206:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1160); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:206:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( ((LA62_0>=RETAINUNION && LA62_0<=RETAININTERSECTION)) ) {
                alt62=1;
            }
            switch (alt62) {
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
    // EsperEPL2Ast.g:209:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:210:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:210:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1188); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:210:27: ( IDENT )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==IDENT) ) {
                alt63=1;
            }
            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:210:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1190); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1193); 
            // EsperEPL2Ast.g:210:46: ( propertyExpression )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:210:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1195);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:210:66: ( valueExpr )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( ((LA65_0>=IN_SET && LA65_0<=REGEXP)||LA65_0==NOT_EXPR||(LA65_0>=SUM && LA65_0<=AVG)||(LA65_0>=COALESCE && LA65_0<=COUNT)||(LA65_0>=CASE && LA65_0<=CASE2)||(LA65_0>=PREVIOUS && LA65_0<=EXISTS)||(LA65_0>=INSTANCEOF && LA65_0<=CURRENT_TIMESTAMP)||(LA65_0>=EVAL_AND_EXPR && LA65_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA65_0==EVENT_PROP_EXPR||(LA65_0>=CONCAT && LA65_0<=LIB_FUNCTION)||LA65_0==ARRAY_EXPR||(LA65_0>=NOT_IN_SET && LA65_0<=NOT_REGEXP)||(LA65_0>=IN_RANGE && LA65_0<=SUBSELECT_EXPR)||(LA65_0>=EXISTS_SUBSELECT_EXPR && LA65_0<=NOT_IN_SUBSELECT_EXPR)||LA65_0==SUBSTITUTION||(LA65_0>=INT_TYPE && LA65_0<=NULL_TYPE)||LA65_0==STAR||(LA65_0>=BAND && LA65_0<=BXOR)||(LA65_0>=LT && LA65_0<=GE)||(LA65_0>=PLUS && LA65_0<=MOD)) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // EsperEPL2Ast.g:210:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1199);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop65;
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
    // EsperEPL2Ast.g:213:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:214:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:214:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1219); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:214:34: ( propertyExpressionAtom )*
                loop66:
                do {
                    int alt66=2;
                    int LA66_0 = input.LA(1);

                    if ( (LA66_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt66=1;
                    }


                    switch (alt66) {
                	case 1 :
                	    // EsperEPL2Ast.g:214:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1221);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop66;
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
    // EsperEPL2Ast.g:217:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:218:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:218:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1240); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:218:41: ( propertySelectionListElement )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( ((LA67_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA67_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // EsperEPL2Ast.g:218:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1242);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop67;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1245);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:218:96: ( IDENT )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==IDENT) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:218:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1248); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1252); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:218:116: ( valueExpr )?
                int alt69=2;
                int LA69_0 = input.LA(1);

                if ( ((LA69_0>=IN_SET && LA69_0<=REGEXP)||LA69_0==NOT_EXPR||(LA69_0>=SUM && LA69_0<=AVG)||(LA69_0>=COALESCE && LA69_0<=COUNT)||(LA69_0>=CASE && LA69_0<=CASE2)||(LA69_0>=PREVIOUS && LA69_0<=EXISTS)||(LA69_0>=INSTANCEOF && LA69_0<=CURRENT_TIMESTAMP)||(LA69_0>=EVAL_AND_EXPR && LA69_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA69_0==EVENT_PROP_EXPR||(LA69_0>=CONCAT && LA69_0<=LIB_FUNCTION)||LA69_0==ARRAY_EXPR||(LA69_0>=NOT_IN_SET && LA69_0<=NOT_REGEXP)||(LA69_0>=IN_RANGE && LA69_0<=SUBSELECT_EXPR)||(LA69_0>=EXISTS_SUBSELECT_EXPR && LA69_0<=NOT_IN_SUBSELECT_EXPR)||LA69_0==SUBSTITUTION||(LA69_0>=INT_TYPE && LA69_0<=NULL_TYPE)||LA69_0==STAR||(LA69_0>=BAND && LA69_0<=BXOR)||(LA69_0>=LT && LA69_0<=GE)||(LA69_0>=PLUS && LA69_0<=MOD)) ) {
                    alt69=1;
                }
                switch (alt69) {
                    case 1 :
                        // EsperEPL2Ast.g:218:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1254);
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
    // EsperEPL2Ast.g:221:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:222:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt72=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt72=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt72=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt72=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 72, 0, input);

                throw nvae;
            }

            switch (alt72) {
                case 1 :
                    // EsperEPL2Ast.g:222:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1274); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:223:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1284); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1286);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:223:50: ( IDENT )?
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==IDENT) ) {
                        alt70=1;
                    }
                    switch (alt70) {
                        case 1 :
                            // EsperEPL2Ast.g:223:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1289); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:224:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1303); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1305); 
                    // EsperEPL2Ast.g:224:40: ( IDENT )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==IDENT) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // EsperEPL2Ast.g:224:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1308); 

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
    // EsperEPL2Ast.g:227:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:228:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:228:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1329); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1331);
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
    // EsperEPL2Ast.g:231:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:232:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:232:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1348); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1350); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:232:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( ((LA73_0>=STRING_LITERAL && LA73_0<=QUOTED_STRING_LITERAL)) ) {
                alt73=1;
            }
            switch (alt73) {
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
    // EsperEPL2Ast.g:235:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:236:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:236:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1381); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1383); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1385); 
            // EsperEPL2Ast.g:236:41: ( valueExpr )*
            loop74:
            do {
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( ((LA74_0>=IN_SET && LA74_0<=REGEXP)||LA74_0==NOT_EXPR||(LA74_0>=SUM && LA74_0<=AVG)||(LA74_0>=COALESCE && LA74_0<=COUNT)||(LA74_0>=CASE && LA74_0<=CASE2)||(LA74_0>=PREVIOUS && LA74_0<=EXISTS)||(LA74_0>=INSTANCEOF && LA74_0<=CURRENT_TIMESTAMP)||(LA74_0>=EVAL_AND_EXPR && LA74_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA74_0==EVENT_PROP_EXPR||(LA74_0>=CONCAT && LA74_0<=LIB_FUNCTION)||LA74_0==ARRAY_EXPR||(LA74_0>=NOT_IN_SET && LA74_0<=NOT_REGEXP)||(LA74_0>=IN_RANGE && LA74_0<=SUBSELECT_EXPR)||(LA74_0>=EXISTS_SUBSELECT_EXPR && LA74_0<=NOT_IN_SUBSELECT_EXPR)||LA74_0==SUBSTITUTION||(LA74_0>=INT_TYPE && LA74_0<=NULL_TYPE)||LA74_0==STAR||(LA74_0>=BAND && LA74_0<=BXOR)||(LA74_0>=LT && LA74_0<=GE)||(LA74_0>=PLUS && LA74_0<=MOD)) ) {
                    alt74=1;
                }


                switch (alt74) {
            	case 1 :
            	    // EsperEPL2Ast.g:236:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1388);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop74;
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
    // EsperEPL2Ast.g:239:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:240:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:240:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1402);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:240:13: ( viewExpr )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==VIEW_EXPR) ) {
                    alt75=1;
                }


                switch (alt75) {
            	case 1 :
            	    // EsperEPL2Ast.g:240:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1405);
            	    viewExpr();

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
    // $ANTLR end "viewListExpr"


    // $ANTLR start "viewExpr"
    // EsperEPL2Ast.g:243:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:244:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:244:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1422); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1424); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1426); 
            // EsperEPL2Ast.g:244:30: ( valueExprWithTime )*
            loop76:
            do {
                int alt76=2;
                int LA76_0 = input.LA(1);

                if ( ((LA76_0>=IN_SET && LA76_0<=REGEXP)||LA76_0==NOT_EXPR||(LA76_0>=SUM && LA76_0<=AVG)||(LA76_0>=COALESCE && LA76_0<=COUNT)||(LA76_0>=CASE && LA76_0<=CASE2)||LA76_0==LAST||(LA76_0>=PREVIOUS && LA76_0<=EXISTS)||(LA76_0>=LW && LA76_0<=CURRENT_TIMESTAMP)||(LA76_0>=NUMERIC_PARAM_RANGE && LA76_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA76_0>=EVAL_AND_EXPR && LA76_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA76_0==EVENT_PROP_EXPR||(LA76_0>=CONCAT && LA76_0<=LIB_FUNCTION)||(LA76_0>=TIME_PERIOD && LA76_0<=ARRAY_EXPR)||(LA76_0>=NOT_IN_SET && LA76_0<=NOT_REGEXP)||(LA76_0>=IN_RANGE && LA76_0<=SUBSELECT_EXPR)||(LA76_0>=EXISTS_SUBSELECT_EXPR && LA76_0<=NOT_IN_SUBSELECT_EXPR)||(LA76_0>=LAST_OPERATOR && LA76_0<=SUBSTITUTION)||LA76_0==NUMBERSETSTAR||(LA76_0>=INT_TYPE && LA76_0<=NULL_TYPE)||LA76_0==STAR||(LA76_0>=BAND && LA76_0<=BXOR)||(LA76_0>=LT && LA76_0<=GE)||(LA76_0>=PLUS && LA76_0<=MOD)) ) {
                    alt76=1;
                }


                switch (alt76) {
            	case 1 :
            	    // EsperEPL2Ast.g:244:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1429);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop76;
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
    // EsperEPL2Ast.g:247:1: whereClause : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:248:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:248:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1450); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1452);
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
    // EsperEPL2Ast.g:251:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:252:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:252:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1470); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1472);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:252:32: ( valueExpr )*
            loop77:
            do {
                int alt77=2;
                int LA77_0 = input.LA(1);

                if ( ((LA77_0>=IN_SET && LA77_0<=REGEXP)||LA77_0==NOT_EXPR||(LA77_0>=SUM && LA77_0<=AVG)||(LA77_0>=COALESCE && LA77_0<=COUNT)||(LA77_0>=CASE && LA77_0<=CASE2)||(LA77_0>=PREVIOUS && LA77_0<=EXISTS)||(LA77_0>=INSTANCEOF && LA77_0<=CURRENT_TIMESTAMP)||(LA77_0>=EVAL_AND_EXPR && LA77_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA77_0==EVENT_PROP_EXPR||(LA77_0>=CONCAT && LA77_0<=LIB_FUNCTION)||LA77_0==ARRAY_EXPR||(LA77_0>=NOT_IN_SET && LA77_0<=NOT_REGEXP)||(LA77_0>=IN_RANGE && LA77_0<=SUBSELECT_EXPR)||(LA77_0>=EXISTS_SUBSELECT_EXPR && LA77_0<=NOT_IN_SUBSELECT_EXPR)||LA77_0==SUBSTITUTION||(LA77_0>=INT_TYPE && LA77_0<=NULL_TYPE)||LA77_0==STAR||(LA77_0>=BAND && LA77_0<=BXOR)||(LA77_0>=LT && LA77_0<=GE)||(LA77_0>=PLUS && LA77_0<=MOD)) ) {
                    alt77=1;
                }


                switch (alt77) {
            	case 1 :
            	    // EsperEPL2Ast.g:252:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1475);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop77;
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
    // EsperEPL2Ast.g:255:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:256:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:256:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1493); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1495);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:256:35: ( orderByElement )*
            loop78:
            do {
                int alt78=2;
                int LA78_0 = input.LA(1);

                if ( (LA78_0==ORDER_ELEMENT_EXPR) ) {
                    alt78=1;
                }


                switch (alt78) {
            	case 1 :
            	    // EsperEPL2Ast.g:256:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1498);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop78;
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
    // EsperEPL2Ast.g:259:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:260:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:260:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1518); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1520);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:260:38: ( ASC | DESC )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( ((LA79_0>=ASC && LA79_0<=DESC)) ) {
                alt79=1;
            }
            switch (alt79) {
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
    // EsperEPL2Ast.g:263:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:264:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:264:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1545); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1547);
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
    // EsperEPL2Ast.g:267:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:268:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt86=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt86=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt86=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt86=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt86=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 86, 0, input);

                throw nvae;
            }

            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:268:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1565); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:268:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt80=2;
                    int LA80_0 = input.LA(1);

                    if ( (LA80_0==ALL||(LA80_0>=FIRST && LA80_0<=LAST)||LA80_0==SNAPSHOT) ) {
                        alt80=1;
                    }
                    switch (alt80) {
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

                    // EsperEPL2Ast.g:268:52: ( number | IDENT )
                    int alt81=2;
                    int LA81_0 = input.LA(1);

                    if ( ((LA81_0>=INT_TYPE && LA81_0<=DOUBLE_TYPE)) ) {
                        alt81=1;
                    }
                    else if ( (LA81_0==IDENT) ) {
                        alt81=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 81, 0, input);

                        throw nvae;
                    }
                    switch (alt81) {
                        case 1 :
                            // EsperEPL2Ast.g:268:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1579);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:268:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1581); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:269:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1598); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:269:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt82=2;
                    int LA82_0 = input.LA(1);

                    if ( (LA82_0==ALL||(LA82_0>=FIRST && LA82_0<=LAST)||LA82_0==SNAPSHOT) ) {
                        alt82=1;
                    }
                    switch (alt82) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1611);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:270:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1626); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:270:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt83=2;
                    int LA83_0 = input.LA(1);

                    if ( (LA83_0==ALL||(LA83_0>=FIRST && LA83_0<=LAST)||LA83_0==SNAPSHOT) ) {
                        alt83=1;
                    }
                    switch (alt83) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1639);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:271:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1654); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:271:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt84=2;
                    int LA84_0 = input.LA(1);

                    if ( (LA84_0==ALL||(LA84_0>=FIRST && LA84_0<=LAST)||LA84_0==SNAPSHOT) ) {
                        alt84=1;
                    }
                    switch (alt84) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr1667);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:271:67: ( onSetExpr )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==ON_SET_EXPR) ) {
                        alt85=1;
                    }
                    switch (alt85) {
                        case 1 :
                            // EsperEPL2Ast.g:271:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr1669);
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
    // EsperEPL2Ast.g:274:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:275:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:275:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1688); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:275:23: ( number | IDENT )
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( ((LA87_0>=INT_TYPE && LA87_0<=DOUBLE_TYPE)) ) {
                alt87=1;
            }
            else if ( (LA87_0==IDENT) ) {
                alt87=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 87, 0, input);

                throw nvae;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:275:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1691);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:275:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1693); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:275:38: ( number | IDENT )?
            int alt88=3;
            int LA88_0 = input.LA(1);

            if ( ((LA88_0>=INT_TYPE && LA88_0<=DOUBLE_TYPE)) ) {
                alt88=1;
            }
            else if ( (LA88_0==IDENT) ) {
                alt88=2;
            }
            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:275:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1697);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:275:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1699); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:275:54: ( COMMA )?
            int alt89=2;
            int LA89_0 = input.LA(1);

            if ( (LA89_0==COMMA) ) {
                alt89=1;
            }
            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:275:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause1703); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:275:61: ( OFFSET )?
            int alt90=2;
            int LA90_0 = input.LA(1);

            if ( (LA90_0==OFFSET) ) {
                alt90=1;
            }
            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:275:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause1706); 

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
    // EsperEPL2Ast.g:278:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:279:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:279:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1724); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1726);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1728);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1730);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1732);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1734);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:279:121: ( valueExprWithTime )?
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( ((LA91_0>=IN_SET && LA91_0<=REGEXP)||LA91_0==NOT_EXPR||(LA91_0>=SUM && LA91_0<=AVG)||(LA91_0>=COALESCE && LA91_0<=COUNT)||(LA91_0>=CASE && LA91_0<=CASE2)||LA91_0==LAST||(LA91_0>=PREVIOUS && LA91_0<=EXISTS)||(LA91_0>=LW && LA91_0<=CURRENT_TIMESTAMP)||(LA91_0>=NUMERIC_PARAM_RANGE && LA91_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA91_0>=EVAL_AND_EXPR && LA91_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA91_0==EVENT_PROP_EXPR||(LA91_0>=CONCAT && LA91_0<=LIB_FUNCTION)||(LA91_0>=TIME_PERIOD && LA91_0<=ARRAY_EXPR)||(LA91_0>=NOT_IN_SET && LA91_0<=NOT_REGEXP)||(LA91_0>=IN_RANGE && LA91_0<=SUBSELECT_EXPR)||(LA91_0>=EXISTS_SUBSELECT_EXPR && LA91_0<=NOT_IN_SUBSELECT_EXPR)||(LA91_0>=LAST_OPERATOR && LA91_0<=SUBSTITUTION)||LA91_0==NUMBERSETSTAR||(LA91_0>=INT_TYPE && LA91_0<=NULL_TYPE)||LA91_0==STAR||(LA91_0>=BAND && LA91_0<=BXOR)||(LA91_0>=LT && LA91_0<=GE)||(LA91_0>=PLUS && LA91_0<=MOD)) ) {
                alt91=1;
            }
            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:279:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1736);
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
    // EsperEPL2Ast.g:282:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:283:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt92=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt92=1;
                }
                break;
            case GT:
                {
                alt92=2;
                }
                break;
            case LE:
                {
                alt92=3;
                }
                break;
            case GE:
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
                    // EsperEPL2Ast.g:283:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr1753); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1755);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:284:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr1768); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1770);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:285:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr1783); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1785);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:286:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr1797); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1799);
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
    // EsperEPL2Ast.g:289:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:290:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:290:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:290:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:291:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue1821);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:292:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( ((LA95_0>=IN_SET && LA95_0<=REGEXP)||LA95_0==NOT_EXPR||(LA95_0>=SUM && LA95_0<=AVG)||(LA95_0>=COALESCE && LA95_0<=COUNT)||(LA95_0>=CASE && LA95_0<=CASE2)||(LA95_0>=PREVIOUS && LA95_0<=EXISTS)||(LA95_0>=INSTANCEOF && LA95_0<=CURRENT_TIMESTAMP)||(LA95_0>=EVAL_AND_EXPR && LA95_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA95_0==EVENT_PROP_EXPR||(LA95_0>=CONCAT && LA95_0<=LIB_FUNCTION)||LA95_0==ARRAY_EXPR||(LA95_0>=NOT_IN_SET && LA95_0<=NOT_REGEXP)||(LA95_0>=IN_RANGE && LA95_0<=SUBSELECT_EXPR)||(LA95_0>=EXISTS_SUBSELECT_EXPR && LA95_0<=NOT_IN_SUBSELECT_EXPR)||LA95_0==SUBSTITUTION||(LA95_0>=INT_TYPE && LA95_0<=NULL_TYPE)||LA95_0==STAR||(LA95_0>=BAND && LA95_0<=BXOR)||(LA95_0>=LT && LA95_0<=GE)||(LA95_0>=PLUS && LA95_0<=MOD)) ) {
                alt95=1;
            }
            else if ( ((LA95_0>=ALL && LA95_0<=SOME)) ) {
                alt95=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 95, 0, input);

                throw nvae;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:292:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1831);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:294:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:294:21: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt94=2;
                    int LA94_0 = input.LA(1);

                    if ( (LA94_0==UP||(LA94_0>=IN_SET && LA94_0<=REGEXP)||LA94_0==NOT_EXPR||(LA94_0>=SUM && LA94_0<=AVG)||(LA94_0>=COALESCE && LA94_0<=COUNT)||(LA94_0>=CASE && LA94_0<=CASE2)||(LA94_0>=PREVIOUS && LA94_0<=EXISTS)||(LA94_0>=INSTANCEOF && LA94_0<=CURRENT_TIMESTAMP)||(LA94_0>=EVAL_AND_EXPR && LA94_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA94_0==EVENT_PROP_EXPR||(LA94_0>=CONCAT && LA94_0<=LIB_FUNCTION)||LA94_0==ARRAY_EXPR||(LA94_0>=NOT_IN_SET && LA94_0<=NOT_REGEXP)||(LA94_0>=IN_RANGE && LA94_0<=SUBSELECT_EXPR)||(LA94_0>=EXISTS_SUBSELECT_EXPR && LA94_0<=NOT_IN_SUBSELECT_EXPR)||LA94_0==SUBSTITUTION||(LA94_0>=INT_TYPE && LA94_0<=NULL_TYPE)||LA94_0==STAR||(LA94_0>=BAND && LA94_0<=BXOR)||(LA94_0>=LT && LA94_0<=GE)||(LA94_0>=PLUS && LA94_0<=MOD)) ) {
                        alt94=1;
                    }
                    else if ( (LA94_0==SUBSELECT_GROUP_EXPR) ) {
                        alt94=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 94, 0, input);

                        throw nvae;
                    }
                    switch (alt94) {
                        case 1 :
                            // EsperEPL2Ast.g:294:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:294:22: ( valueExpr )*
                            loop93:
                            do {
                                int alt93=2;
                                int LA93_0 = input.LA(1);

                                if ( ((LA93_0>=IN_SET && LA93_0<=REGEXP)||LA93_0==NOT_EXPR||(LA93_0>=SUM && LA93_0<=AVG)||(LA93_0>=COALESCE && LA93_0<=COUNT)||(LA93_0>=CASE && LA93_0<=CASE2)||(LA93_0>=PREVIOUS && LA93_0<=EXISTS)||(LA93_0>=INSTANCEOF && LA93_0<=CURRENT_TIMESTAMP)||(LA93_0>=EVAL_AND_EXPR && LA93_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA93_0==EVENT_PROP_EXPR||(LA93_0>=CONCAT && LA93_0<=LIB_FUNCTION)||LA93_0==ARRAY_EXPR||(LA93_0>=NOT_IN_SET && LA93_0<=NOT_REGEXP)||(LA93_0>=IN_RANGE && LA93_0<=SUBSELECT_EXPR)||(LA93_0>=EXISTS_SUBSELECT_EXPR && LA93_0<=NOT_IN_SUBSELECT_EXPR)||LA93_0==SUBSTITUTION||(LA93_0>=INT_TYPE && LA93_0<=NULL_TYPE)||LA93_0==STAR||(LA93_0>=BAND && LA93_0<=BXOR)||(LA93_0>=LT && LA93_0<=GE)||(LA93_0>=PLUS && LA93_0<=MOD)) ) {
                                    alt93=1;
                                }


                                switch (alt93) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:294:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1855);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop93;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:294:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue1860);
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
    // EsperEPL2Ast.g:299:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:300:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt102=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt102=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt102=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt102=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt102=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt102=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt102=6;
                }
                break;
            case NOT_EXPR:
                {
                alt102=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt102=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 102, 0, input);

                throw nvae;
            }

            switch (alt102) {
                case 1 :
                    // EsperEPL2Ast.g:300:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1886); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1888);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1890);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:300:42: ( valueExpr )*
                    loop96:
                    do {
                        int alt96=2;
                        int LA96_0 = input.LA(1);

                        if ( ((LA96_0>=IN_SET && LA96_0<=REGEXP)||LA96_0==NOT_EXPR||(LA96_0>=SUM && LA96_0<=AVG)||(LA96_0>=COALESCE && LA96_0<=COUNT)||(LA96_0>=CASE && LA96_0<=CASE2)||(LA96_0>=PREVIOUS && LA96_0<=EXISTS)||(LA96_0>=INSTANCEOF && LA96_0<=CURRENT_TIMESTAMP)||(LA96_0>=EVAL_AND_EXPR && LA96_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA96_0==EVENT_PROP_EXPR||(LA96_0>=CONCAT && LA96_0<=LIB_FUNCTION)||LA96_0==ARRAY_EXPR||(LA96_0>=NOT_IN_SET && LA96_0<=NOT_REGEXP)||(LA96_0>=IN_RANGE && LA96_0<=SUBSELECT_EXPR)||(LA96_0>=EXISTS_SUBSELECT_EXPR && LA96_0<=NOT_IN_SUBSELECT_EXPR)||LA96_0==SUBSTITUTION||(LA96_0>=INT_TYPE && LA96_0<=NULL_TYPE)||LA96_0==STAR||(LA96_0>=BAND && LA96_0<=BXOR)||(LA96_0>=LT && LA96_0<=GE)||(LA96_0>=PLUS && LA96_0<=MOD)) ) {
                            alt96=1;
                        }


                        switch (alt96) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:300:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1893);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop96;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:301:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1907); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1909);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1911);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:301:43: ( valueExpr )*
                    loop97:
                    do {
                        int alt97=2;
                        int LA97_0 = input.LA(1);

                        if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=INSTANCEOF && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||LA97_0==ARRAY_EXPR||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=SUBSELECT_EXPR)||(LA97_0>=EXISTS_SUBSELECT_EXPR && LA97_0<=NOT_IN_SUBSELECT_EXPR)||LA97_0==SUBSTITUTION||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||LA97_0==STAR||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=PLUS && LA97_0<=MOD)) ) {
                            alt97=1;
                        }


                        switch (alt97) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:301:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1914);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop97;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:302:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1928); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1930);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1932);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:303:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1944); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1946);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1948);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:304:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice1960); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1962);
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

                    // EsperEPL2Ast.g:304:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt99=2;
                    int LA99_0 = input.LA(1);

                    if ( (LA99_0==UP||(LA99_0>=IN_SET && LA99_0<=REGEXP)||LA99_0==NOT_EXPR||(LA99_0>=SUM && LA99_0<=AVG)||(LA99_0>=COALESCE && LA99_0<=COUNT)||(LA99_0>=CASE && LA99_0<=CASE2)||(LA99_0>=PREVIOUS && LA99_0<=EXISTS)||(LA99_0>=INSTANCEOF && LA99_0<=CURRENT_TIMESTAMP)||(LA99_0>=EVAL_AND_EXPR && LA99_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA99_0==EVENT_PROP_EXPR||(LA99_0>=CONCAT && LA99_0<=LIB_FUNCTION)||LA99_0==ARRAY_EXPR||(LA99_0>=NOT_IN_SET && LA99_0<=NOT_REGEXP)||(LA99_0>=IN_RANGE && LA99_0<=SUBSELECT_EXPR)||(LA99_0>=EXISTS_SUBSELECT_EXPR && LA99_0<=NOT_IN_SUBSELECT_EXPR)||LA99_0==SUBSTITUTION||(LA99_0>=INT_TYPE && LA99_0<=NULL_TYPE)||LA99_0==STAR||(LA99_0>=BAND && LA99_0<=BXOR)||(LA99_0>=LT && LA99_0<=GE)||(LA99_0>=PLUS && LA99_0<=MOD)) ) {
                        alt99=1;
                    }
                    else if ( (LA99_0==SUBSELECT_GROUP_EXPR) ) {
                        alt99=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 99, 0, input);

                        throw nvae;
                    }
                    switch (alt99) {
                        case 1 :
                            // EsperEPL2Ast.g:304:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:304:59: ( valueExpr )*
                            loop98:
                            do {
                                int alt98=2;
                                int LA98_0 = input.LA(1);

                                if ( ((LA98_0>=IN_SET && LA98_0<=REGEXP)||LA98_0==NOT_EXPR||(LA98_0>=SUM && LA98_0<=AVG)||(LA98_0>=COALESCE && LA98_0<=COUNT)||(LA98_0>=CASE && LA98_0<=CASE2)||(LA98_0>=PREVIOUS && LA98_0<=EXISTS)||(LA98_0>=INSTANCEOF && LA98_0<=CURRENT_TIMESTAMP)||(LA98_0>=EVAL_AND_EXPR && LA98_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA98_0==EVENT_PROP_EXPR||(LA98_0>=CONCAT && LA98_0<=LIB_FUNCTION)||LA98_0==ARRAY_EXPR||(LA98_0>=NOT_IN_SET && LA98_0<=NOT_REGEXP)||(LA98_0>=IN_RANGE && LA98_0<=SUBSELECT_EXPR)||(LA98_0>=EXISTS_SUBSELECT_EXPR && LA98_0<=NOT_IN_SUBSELECT_EXPR)||LA98_0==SUBSTITUTION||(LA98_0>=INT_TYPE && LA98_0<=NULL_TYPE)||LA98_0==STAR||(LA98_0>=BAND && LA98_0<=BXOR)||(LA98_0>=LT && LA98_0<=GE)||(LA98_0>=PLUS && LA98_0<=MOD)) ) {
                                    alt98=1;
                                }


                                switch (alt98) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:304:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1973);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop98;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:304:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice1978);
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
                    // EsperEPL2Ast.g:305:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice1991); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1993);
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

                    // EsperEPL2Ast.g:305:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==UP||(LA101_0>=IN_SET && LA101_0<=REGEXP)||LA101_0==NOT_EXPR||(LA101_0>=SUM && LA101_0<=AVG)||(LA101_0>=COALESCE && LA101_0<=COUNT)||(LA101_0>=CASE && LA101_0<=CASE2)||(LA101_0>=PREVIOUS && LA101_0<=EXISTS)||(LA101_0>=INSTANCEOF && LA101_0<=CURRENT_TIMESTAMP)||(LA101_0>=EVAL_AND_EXPR && LA101_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA101_0==EVENT_PROP_EXPR||(LA101_0>=CONCAT && LA101_0<=LIB_FUNCTION)||LA101_0==ARRAY_EXPR||(LA101_0>=NOT_IN_SET && LA101_0<=NOT_REGEXP)||(LA101_0>=IN_RANGE && LA101_0<=SUBSELECT_EXPR)||(LA101_0>=EXISTS_SUBSELECT_EXPR && LA101_0<=NOT_IN_SUBSELECT_EXPR)||LA101_0==SUBSTITUTION||(LA101_0>=INT_TYPE && LA101_0<=NULL_TYPE)||LA101_0==STAR||(LA101_0>=BAND && LA101_0<=BXOR)||(LA101_0>=LT && LA101_0<=GE)||(LA101_0>=PLUS && LA101_0<=MOD)) ) {
                        alt101=1;
                    }
                    else if ( (LA101_0==SUBSELECT_GROUP_EXPR) ) {
                        alt101=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 101, 0, input);

                        throw nvae;
                    }
                    switch (alt101) {
                        case 1 :
                            // EsperEPL2Ast.g:305:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:305:63: ( valueExpr )*
                            loop100:
                            do {
                                int alt100=2;
                                int LA100_0 = input.LA(1);

                                if ( ((LA100_0>=IN_SET && LA100_0<=REGEXP)||LA100_0==NOT_EXPR||(LA100_0>=SUM && LA100_0<=AVG)||(LA100_0>=COALESCE && LA100_0<=COUNT)||(LA100_0>=CASE && LA100_0<=CASE2)||(LA100_0>=PREVIOUS && LA100_0<=EXISTS)||(LA100_0>=INSTANCEOF && LA100_0<=CURRENT_TIMESTAMP)||(LA100_0>=EVAL_AND_EXPR && LA100_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA100_0==EVENT_PROP_EXPR||(LA100_0>=CONCAT && LA100_0<=LIB_FUNCTION)||LA100_0==ARRAY_EXPR||(LA100_0>=NOT_IN_SET && LA100_0<=NOT_REGEXP)||(LA100_0>=IN_RANGE && LA100_0<=SUBSELECT_EXPR)||(LA100_0>=EXISTS_SUBSELECT_EXPR && LA100_0<=NOT_IN_SUBSELECT_EXPR)||LA100_0==SUBSTITUTION||(LA100_0>=INT_TYPE && LA100_0<=NULL_TYPE)||LA100_0==STAR||(LA100_0>=BAND && LA100_0<=BXOR)||(LA100_0>=LT && LA100_0<=GE)||(LA100_0>=PLUS && LA100_0<=MOD)) ) {
                                    alt100=1;
                                }


                                switch (alt100) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:305:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2004);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop100;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:305:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2009);
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
                    // EsperEPL2Ast.g:306:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2022); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2024);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:307:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2035);
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
    // EsperEPL2Ast.g:310:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:311:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt103=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt103=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt103=2;
                }
                break;
            case CONCAT:
            case STAR:
            case BAND:
            case BOR:
            case BXOR:
            case PLUS:
            case MINUS:
            case DIV:
            case MOD:
                {
                alt103=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt103=4;
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
                alt103=5;
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
                {
                alt103=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt103=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt103=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt103=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt103=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt103=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt103=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt103=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt103=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt103=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt103=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 103, 0, input);

                throw nvae;
            }

            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:311:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2048);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:312:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2054);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:313:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2060);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:314:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2067);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:315:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2076);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:316:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2081);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:317:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2089);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:318:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2094);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:319:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2099);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:320:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2105);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:321:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2110);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:322:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2115);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:323:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2120);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:324:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2125);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:325:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2131);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:326:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2138);
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
    // EsperEPL2Ast.g:329:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:330:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt105=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt105=1;
                }
                break;
            case LW:
                {
                alt105=2;
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
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
            case STAR:
            case BAND:
            case BOR:
            case BXOR:
            case LT:
            case GT:
            case LE:
            case GE:
            case PLUS:
            case MINUS:
            case DIV:
            case MOD:
                {
                alt105=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt105=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt105=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt105=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt105=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt105=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt105=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt105=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt105=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 105, 0, input);

                throw nvae;
            }

            switch (alt105) {
                case 1 :
                    // EsperEPL2Ast.g:330:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2151); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:331:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2160); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:332:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2167);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:333:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2175); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2177);
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
                    // EsperEPL2Ast.g:334:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2192);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:335:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2198);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:336:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2203);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:337:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2208);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:338:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2218); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:338:29: ( numericParameterList )+
                    int cnt104=0;
                    loop104:
                    do {
                        int alt104=2;
                        int LA104_0 = input.LA(1);

                        if ( (LA104_0==NUMERIC_PARAM_RANGE||LA104_0==NUMERIC_PARAM_FREQUENCY||(LA104_0>=INT_TYPE && LA104_0<=NULL_TYPE)) ) {
                            alt104=1;
                        }


                        switch (alt104) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:338:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2220);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt104 >= 1 ) break loop104;
                                EarlyExitException eee =
                                    new EarlyExitException(104, input);
                                throw eee;
                        }
                        cnt104++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:339:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2231); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:340:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2238);
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
    // EsperEPL2Ast.g:343:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:344:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt106=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt106=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt106=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:344:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2251);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:345:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2258);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:346:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2264);
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
    // EsperEPL2Ast.g:349:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:350:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:350:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2280); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:350:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt107=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt107=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt107=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt107=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }

            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:350:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2283);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:350:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2286);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:350:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2289);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:350:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt108=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt108=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt108=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt108=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }

            switch (alt108) {
                case 1 :
                    // EsperEPL2Ast.g:350:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2293);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:350:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2296);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:350:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2299);
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
    // EsperEPL2Ast.g:353:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:354:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:354:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2320); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:354:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt109=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt109=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt109=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt109=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 109, 0, input);

                throw nvae;
            }

            switch (alt109) {
                case 1 :
                    // EsperEPL2Ast.g:354:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2323);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:354:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2326);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:354:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2329);
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
    // EsperEPL2Ast.g:357:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:358:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:358:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2348); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:358:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt110=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt110=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt110=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt110=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }

            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:358:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2351);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:358:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2354);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:358:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2357);
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
    // EsperEPL2Ast.g:361:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:362:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:362:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2376); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:362:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt111=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt111=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt111=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt111=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 111, 0, input);

                throw nvae;
            }

            switch (alt111) {
                case 1 :
                    // EsperEPL2Ast.g:362:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2379);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:362:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2382);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:362:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2385);
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
    // EsperEPL2Ast.g:365:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:366:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:366:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2406); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr2408);
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
    // EsperEPL2Ast.g:369:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:370:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:370:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2427); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2429);
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
    // EsperEPL2Ast.g:373:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:374:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:374:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2448); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2450);
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
    // EsperEPL2Ast.g:377:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:378:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==IN_SUBSELECT_EXPR) ) {
                alt112=1;
            }
            else if ( (LA112_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt112=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }
            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:378:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2469); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2471);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2473);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:379:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2485); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2487);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2489);
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
    // EsperEPL2Ast.g:382:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:383:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:383:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2508); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2510);
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
    // EsperEPL2Ast.g:386:1: subQueryExpr : selectionListElement subSelectFilterExpr ( whereClause )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:387:2: ( selectionListElement subSelectFilterExpr ( whereClause )? )
            // EsperEPL2Ast.g:387:4: selectionListElement subSelectFilterExpr ( whereClause )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2526);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2528);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:387:45: ( whereClause )?
            int alt113=2;
            int LA113_0 = input.LA(1);

            if ( (LA113_0==WHERE_EXPR) ) {
                alt113=1;
            }
            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:387:46: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2531);
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
    // EsperEPL2Ast.g:390:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:391:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:391:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2548); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2550);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:391:36: ( viewListExpr )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==VIEW_EXPR) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:391:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2553);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:391:52: ( IDENT )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==IDENT) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // EsperEPL2Ast.g:391:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2558); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:391:61: ( RETAINUNION )?
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==RETAINUNION) ) {
                alt116=1;
            }
            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:391:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr2562); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:391:74: ( RETAININTERSECTION )?
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==RETAININTERSECTION) ) {
                alt117=1;
            }
            switch (alt117) {
                case 1 :
                    // EsperEPL2Ast.g:391:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2565); 

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
    // EsperEPL2Ast.g:394:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:395:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==CASE) ) {
                alt120=1;
            }
            else if ( (LA120_0==CASE2) ) {
                alt120=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }
            switch (alt120) {
                case 1 :
                    // EsperEPL2Ast.g:395:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2585); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:395:13: ( valueExpr )*
                        loop118:
                        do {
                            int alt118=2;
                            int LA118_0 = input.LA(1);

                            if ( ((LA118_0>=IN_SET && LA118_0<=REGEXP)||LA118_0==NOT_EXPR||(LA118_0>=SUM && LA118_0<=AVG)||(LA118_0>=COALESCE && LA118_0<=COUNT)||(LA118_0>=CASE && LA118_0<=CASE2)||(LA118_0>=PREVIOUS && LA118_0<=EXISTS)||(LA118_0>=INSTANCEOF && LA118_0<=CURRENT_TIMESTAMP)||(LA118_0>=EVAL_AND_EXPR && LA118_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA118_0==EVENT_PROP_EXPR||(LA118_0>=CONCAT && LA118_0<=LIB_FUNCTION)||LA118_0==ARRAY_EXPR||(LA118_0>=NOT_IN_SET && LA118_0<=NOT_REGEXP)||(LA118_0>=IN_RANGE && LA118_0<=SUBSELECT_EXPR)||(LA118_0>=EXISTS_SUBSELECT_EXPR && LA118_0<=NOT_IN_SUBSELECT_EXPR)||LA118_0==SUBSTITUTION||(LA118_0>=INT_TYPE && LA118_0<=NULL_TYPE)||LA118_0==STAR||(LA118_0>=BAND && LA118_0<=BXOR)||(LA118_0>=LT && LA118_0<=GE)||(LA118_0>=PLUS && LA118_0<=MOD)) ) {
                                alt118=1;
                            }


                            switch (alt118) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:395:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2588);
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
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:396:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2601); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:396:14: ( valueExpr )*
                        loop119:
                        do {
                            int alt119=2;
                            int LA119_0 = input.LA(1);

                            if ( ((LA119_0>=IN_SET && LA119_0<=REGEXP)||LA119_0==NOT_EXPR||(LA119_0>=SUM && LA119_0<=AVG)||(LA119_0>=COALESCE && LA119_0<=COUNT)||(LA119_0>=CASE && LA119_0<=CASE2)||(LA119_0>=PREVIOUS && LA119_0<=EXISTS)||(LA119_0>=INSTANCEOF && LA119_0<=CURRENT_TIMESTAMP)||(LA119_0>=EVAL_AND_EXPR && LA119_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA119_0==EVENT_PROP_EXPR||(LA119_0>=CONCAT && LA119_0<=LIB_FUNCTION)||LA119_0==ARRAY_EXPR||(LA119_0>=NOT_IN_SET && LA119_0<=NOT_REGEXP)||(LA119_0>=IN_RANGE && LA119_0<=SUBSELECT_EXPR)||(LA119_0>=EXISTS_SUBSELECT_EXPR && LA119_0<=NOT_IN_SUBSELECT_EXPR)||LA119_0==SUBSTITUTION||(LA119_0>=INT_TYPE && LA119_0<=NULL_TYPE)||LA119_0==STAR||(LA119_0>=BAND && LA119_0<=BXOR)||(LA119_0>=LT && LA119_0<=GE)||(LA119_0>=PLUS && LA119_0<=MOD)) ) {
                                alt119=1;
                            }


                            switch (alt119) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:396:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2604);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop119;
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
    // EsperEPL2Ast.g:399:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:400:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt123=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt123=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt123=2;
                }
                break;
            case IN_RANGE:
                {
                alt123=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt123=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 123, 0, input);

                throw nvae;
            }

            switch (alt123) {
                case 1 :
                    // EsperEPL2Ast.g:400:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2624); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2626);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2634);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:400:51: ( valueExpr )*
                    loop121:
                    do {
                        int alt121=2;
                        int LA121_0 = input.LA(1);

                        if ( ((LA121_0>=IN_SET && LA121_0<=REGEXP)||LA121_0==NOT_EXPR||(LA121_0>=SUM && LA121_0<=AVG)||(LA121_0>=COALESCE && LA121_0<=COUNT)||(LA121_0>=CASE && LA121_0<=CASE2)||(LA121_0>=PREVIOUS && LA121_0<=EXISTS)||(LA121_0>=INSTANCEOF && LA121_0<=CURRENT_TIMESTAMP)||(LA121_0>=EVAL_AND_EXPR && LA121_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA121_0==EVENT_PROP_EXPR||(LA121_0>=CONCAT && LA121_0<=LIB_FUNCTION)||LA121_0==ARRAY_EXPR||(LA121_0>=NOT_IN_SET && LA121_0<=NOT_REGEXP)||(LA121_0>=IN_RANGE && LA121_0<=SUBSELECT_EXPR)||(LA121_0>=EXISTS_SUBSELECT_EXPR && LA121_0<=NOT_IN_SUBSELECT_EXPR)||LA121_0==SUBSTITUTION||(LA121_0>=INT_TYPE && LA121_0<=NULL_TYPE)||LA121_0==STAR||(LA121_0>=BAND && LA121_0<=BXOR)||(LA121_0>=LT && LA121_0<=GE)||(LA121_0>=PLUS && LA121_0<=MOD)) ) {
                            alt121=1;
                        }


                        switch (alt121) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:400:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2637);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop121;
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
                    // EsperEPL2Ast.g:401:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr2656); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2658);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2666);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:401:55: ( valueExpr )*
                    loop122:
                    do {
                        int alt122=2;
                        int LA122_0 = input.LA(1);

                        if ( ((LA122_0>=IN_SET && LA122_0<=REGEXP)||LA122_0==NOT_EXPR||(LA122_0>=SUM && LA122_0<=AVG)||(LA122_0>=COALESCE && LA122_0<=COUNT)||(LA122_0>=CASE && LA122_0<=CASE2)||(LA122_0>=PREVIOUS && LA122_0<=EXISTS)||(LA122_0>=INSTANCEOF && LA122_0<=CURRENT_TIMESTAMP)||(LA122_0>=EVAL_AND_EXPR && LA122_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA122_0==EVENT_PROP_EXPR||(LA122_0>=CONCAT && LA122_0<=LIB_FUNCTION)||LA122_0==ARRAY_EXPR||(LA122_0>=NOT_IN_SET && LA122_0<=NOT_REGEXP)||(LA122_0>=IN_RANGE && LA122_0<=SUBSELECT_EXPR)||(LA122_0>=EXISTS_SUBSELECT_EXPR && LA122_0<=NOT_IN_SUBSELECT_EXPR)||LA122_0==SUBSTITUTION||(LA122_0>=INT_TYPE && LA122_0<=NULL_TYPE)||LA122_0==STAR||(LA122_0>=BAND && LA122_0<=BXOR)||(LA122_0>=LT && LA122_0<=GE)||(LA122_0>=PLUS && LA122_0<=MOD)) ) {
                            alt122=1;
                        }


                        switch (alt122) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:401:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2669);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop122;
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
                    // EsperEPL2Ast.g:402:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr2688); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2690);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2698);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2700);
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
                    // EsperEPL2Ast.g:403:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr2717); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2719);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2727);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2729);
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
    // EsperEPL2Ast.g:406:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:407:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==BETWEEN) ) {
                alt125=1;
            }
            else if ( (LA125_0==NOT_BETWEEN) ) {
                alt125=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 125, 0, input);

                throw nvae;
            }
            switch (alt125) {
                case 1 :
                    // EsperEPL2Ast.g:407:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr2754); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2756);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2758);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2760);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:408:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr2771); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2773);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2775);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:408:40: ( valueExpr )*
                    loop124:
                    do {
                        int alt124=2;
                        int LA124_0 = input.LA(1);

                        if ( ((LA124_0>=IN_SET && LA124_0<=REGEXP)||LA124_0==NOT_EXPR||(LA124_0>=SUM && LA124_0<=AVG)||(LA124_0>=COALESCE && LA124_0<=COUNT)||(LA124_0>=CASE && LA124_0<=CASE2)||(LA124_0>=PREVIOUS && LA124_0<=EXISTS)||(LA124_0>=INSTANCEOF && LA124_0<=CURRENT_TIMESTAMP)||(LA124_0>=EVAL_AND_EXPR && LA124_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA124_0==EVENT_PROP_EXPR||(LA124_0>=CONCAT && LA124_0<=LIB_FUNCTION)||LA124_0==ARRAY_EXPR||(LA124_0>=NOT_IN_SET && LA124_0<=NOT_REGEXP)||(LA124_0>=IN_RANGE && LA124_0<=SUBSELECT_EXPR)||(LA124_0>=EXISTS_SUBSELECT_EXPR && LA124_0<=NOT_IN_SUBSELECT_EXPR)||LA124_0==SUBSTITUTION||(LA124_0>=INT_TYPE && LA124_0<=NULL_TYPE)||LA124_0==STAR||(LA124_0>=BAND && LA124_0<=BXOR)||(LA124_0>=LT && LA124_0<=GE)||(LA124_0>=PLUS && LA124_0<=MOD)) ) {
                            alt124=1;
                        }


                        switch (alt124) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:408:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr2778);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop124;
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
    // EsperEPL2Ast.g:411:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:412:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt128=2;
            int LA128_0 = input.LA(1);

            if ( (LA128_0==LIKE) ) {
                alt128=1;
            }
            else if ( (LA128_0==NOT_LIKE) ) {
                alt128=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 128, 0, input);

                throw nvae;
            }
            switch (alt128) {
                case 1 :
                    // EsperEPL2Ast.g:412:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr2798); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2800);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2802);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:412:33: ( valueExpr )?
                    int alt126=2;
                    int LA126_0 = input.LA(1);

                    if ( ((LA126_0>=IN_SET && LA126_0<=REGEXP)||LA126_0==NOT_EXPR||(LA126_0>=SUM && LA126_0<=AVG)||(LA126_0>=COALESCE && LA126_0<=COUNT)||(LA126_0>=CASE && LA126_0<=CASE2)||(LA126_0>=PREVIOUS && LA126_0<=EXISTS)||(LA126_0>=INSTANCEOF && LA126_0<=CURRENT_TIMESTAMP)||(LA126_0>=EVAL_AND_EXPR && LA126_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA126_0==EVENT_PROP_EXPR||(LA126_0>=CONCAT && LA126_0<=LIB_FUNCTION)||LA126_0==ARRAY_EXPR||(LA126_0>=NOT_IN_SET && LA126_0<=NOT_REGEXP)||(LA126_0>=IN_RANGE && LA126_0<=SUBSELECT_EXPR)||(LA126_0>=EXISTS_SUBSELECT_EXPR && LA126_0<=NOT_IN_SUBSELECT_EXPR)||LA126_0==SUBSTITUTION||(LA126_0>=INT_TYPE && LA126_0<=NULL_TYPE)||LA126_0==STAR||(LA126_0>=BAND && LA126_0<=BXOR)||(LA126_0>=LT && LA126_0<=GE)||(LA126_0>=PLUS && LA126_0<=MOD)) ) {
                        alt126=1;
                    }
                    switch (alt126) {
                        case 1 :
                            // EsperEPL2Ast.g:412:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2805);
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
                    // EsperEPL2Ast.g:413:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr2818); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2820);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2822);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:413:37: ( valueExpr )?
                    int alt127=2;
                    int LA127_0 = input.LA(1);

                    if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA127_0==EVENT_PROP_EXPR||(LA127_0>=CONCAT && LA127_0<=LIB_FUNCTION)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=SUBSELECT_EXPR)||(LA127_0>=EXISTS_SUBSELECT_EXPR && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||LA127_0==STAR||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=PLUS && LA127_0<=MOD)) ) {
                        alt127=1;
                    }
                    switch (alt127) {
                        case 1 :
                            // EsperEPL2Ast.g:413:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2825);
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
    // EsperEPL2Ast.g:416:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:417:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt129=2;
            int LA129_0 = input.LA(1);

            if ( (LA129_0==REGEXP) ) {
                alt129=1;
            }
            else if ( (LA129_0==NOT_REGEXP) ) {
                alt129=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 129, 0, input);

                throw nvae;
            }
            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:417:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr2844); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2846);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2848);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:418:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr2859); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2861);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2863);
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
    // EsperEPL2Ast.g:421:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr[true] ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:422:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr[true] ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt139=13;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt139=1;
                }
                break;
            case AVG:
                {
                alt139=2;
                }
                break;
            case COUNT:
                {
                alt139=3;
                }
                break;
            case MEDIAN:
                {
                alt139=4;
                }
                break;
            case STDDEV:
                {
                alt139=5;
                }
                break;
            case AVEDEV:
                {
                alt139=6;
                }
                break;
            case COALESCE:
                {
                alt139=7;
                }
                break;
            case PREVIOUS:
                {
                alt139=8;
                }
                break;
            case PRIOR:
                {
                alt139=9;
                }
                break;
            case INSTANCEOF:
                {
                alt139=10;
                }
                break;
            case CAST:
                {
                alt139=11;
                }
                break;
            case EXISTS:
                {
                alt139=12;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt139=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 139, 0, input);

                throw nvae;
            }

            switch (alt139) {
                case 1 :
                    // EsperEPL2Ast.g:422:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc2882); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:422:13: ( DISTINCT )?
                    int alt130=2;
                    int LA130_0 = input.LA(1);

                    if ( (LA130_0==DISTINCT) ) {
                        alt130=1;
                    }
                    switch (alt130) {
                        case 1 :
                            // EsperEPL2Ast.g:422:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2885); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2889);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:423:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc2900); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:423:12: ( DISTINCT )?
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( (LA131_0==DISTINCT) ) {
                        alt131=1;
                    }
                    switch (alt131) {
                        case 1 :
                            // EsperEPL2Ast.g:423:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2903); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2907);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:424:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc2918); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:424:14: ( ( DISTINCT )? valueExpr )?
                        int alt133=2;
                        int LA133_0 = input.LA(1);

                        if ( ((LA133_0>=IN_SET && LA133_0<=REGEXP)||LA133_0==NOT_EXPR||(LA133_0>=SUM && LA133_0<=AVG)||(LA133_0>=COALESCE && LA133_0<=COUNT)||(LA133_0>=CASE && LA133_0<=CASE2)||LA133_0==DISTINCT||(LA133_0>=PREVIOUS && LA133_0<=EXISTS)||(LA133_0>=INSTANCEOF && LA133_0<=CURRENT_TIMESTAMP)||(LA133_0>=EVAL_AND_EXPR && LA133_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA133_0==EVENT_PROP_EXPR||(LA133_0>=CONCAT && LA133_0<=LIB_FUNCTION)||LA133_0==ARRAY_EXPR||(LA133_0>=NOT_IN_SET && LA133_0<=NOT_REGEXP)||(LA133_0>=IN_RANGE && LA133_0<=SUBSELECT_EXPR)||(LA133_0>=EXISTS_SUBSELECT_EXPR && LA133_0<=NOT_IN_SUBSELECT_EXPR)||LA133_0==SUBSTITUTION||(LA133_0>=INT_TYPE && LA133_0<=NULL_TYPE)||LA133_0==STAR||(LA133_0>=BAND && LA133_0<=BXOR)||(LA133_0>=LT && LA133_0<=GE)||(LA133_0>=PLUS && LA133_0<=MOD)) ) {
                            alt133=1;
                        }
                        switch (alt133) {
                            case 1 :
                                // EsperEPL2Ast.g:424:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:424:15: ( DISTINCT )?
                                int alt132=2;
                                int LA132_0 = input.LA(1);

                                if ( (LA132_0==DISTINCT) ) {
                                    alt132=1;
                                }
                                switch (alt132) {
                                    case 1 :
                                        // EsperEPL2Ast.g:424:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2922); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc2926);
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
                    // EsperEPL2Ast.g:425:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc2940); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:425:15: ( DISTINCT )?
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( (LA134_0==DISTINCT) ) {
                        alt134=1;
                    }
                    switch (alt134) {
                        case 1 :
                            // EsperEPL2Ast.g:425:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2943); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2947);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:426:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc2958); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:426:15: ( DISTINCT )?
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( (LA135_0==DISTINCT) ) {
                        alt135=1;
                    }
                    switch (alt135) {
                        case 1 :
                            // EsperEPL2Ast.g:426:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2961); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2965);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:427:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc2976); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:427:15: ( DISTINCT )?
                    int alt136=2;
                    int LA136_0 = input.LA(1);

                    if ( (LA136_0==DISTINCT) ) {
                        alt136=1;
                    }
                    switch (alt136) {
                        case 1 :
                            // EsperEPL2Ast.g:427:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2979); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2983);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:428:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc2995); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2997);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2999);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:428:38: ( valueExpr )*
                    loop137:
                    do {
                        int alt137=2;
                        int LA137_0 = input.LA(1);

                        if ( ((LA137_0>=IN_SET && LA137_0<=REGEXP)||LA137_0==NOT_EXPR||(LA137_0>=SUM && LA137_0<=AVG)||(LA137_0>=COALESCE && LA137_0<=COUNT)||(LA137_0>=CASE && LA137_0<=CASE2)||(LA137_0>=PREVIOUS && LA137_0<=EXISTS)||(LA137_0>=INSTANCEOF && LA137_0<=CURRENT_TIMESTAMP)||(LA137_0>=EVAL_AND_EXPR && LA137_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA137_0==EVENT_PROP_EXPR||(LA137_0>=CONCAT && LA137_0<=LIB_FUNCTION)||LA137_0==ARRAY_EXPR||(LA137_0>=NOT_IN_SET && LA137_0<=NOT_REGEXP)||(LA137_0>=IN_RANGE && LA137_0<=SUBSELECT_EXPR)||(LA137_0>=EXISTS_SUBSELECT_EXPR && LA137_0<=NOT_IN_SUBSELECT_EXPR)||LA137_0==SUBSTITUTION||(LA137_0>=INT_TYPE && LA137_0<=NULL_TYPE)||LA137_0==STAR||(LA137_0>=BAND && LA137_0<=BXOR)||(LA137_0>=LT && LA137_0<=GE)||(LA137_0>=PLUS && LA137_0<=MOD)) ) {
                            alt137=1;
                        }


                        switch (alt137) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:428:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3002);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop137;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:429:5: ^(f= PREVIOUS valueExpr eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3017); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3019);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3021);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:430:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3034); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3038); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3040);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:431:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3053); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3055);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3057); 
                    // EsperEPL2Ast.g:431:42: ( CLASS_IDENT )*
                    loop138:
                    do {
                        int alt138=2;
                        int LA138_0 = input.LA(1);

                        if ( (LA138_0==CLASS_IDENT) ) {
                            alt138=1;
                        }


                        switch (alt138) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:431:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3060); 

                    	    }
                    	    break;

                    	default :
                    	    break loop138;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:432:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3074); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3076);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3078); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:433:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3090); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3092);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:434:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3104); 



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
    // EsperEPL2Ast.g:437:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:438:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:438:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3124); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:438:19: ( valueExpr )*
                loop140:
                do {
                    int alt140=2;
                    int LA140_0 = input.LA(1);

                    if ( ((LA140_0>=IN_SET && LA140_0<=REGEXP)||LA140_0==NOT_EXPR||(LA140_0>=SUM && LA140_0<=AVG)||(LA140_0>=COALESCE && LA140_0<=COUNT)||(LA140_0>=CASE && LA140_0<=CASE2)||(LA140_0>=PREVIOUS && LA140_0<=EXISTS)||(LA140_0>=INSTANCEOF && LA140_0<=CURRENT_TIMESTAMP)||(LA140_0>=EVAL_AND_EXPR && LA140_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA140_0==EVENT_PROP_EXPR||(LA140_0>=CONCAT && LA140_0<=LIB_FUNCTION)||LA140_0==ARRAY_EXPR||(LA140_0>=NOT_IN_SET && LA140_0<=NOT_REGEXP)||(LA140_0>=IN_RANGE && LA140_0<=SUBSELECT_EXPR)||(LA140_0>=EXISTS_SUBSELECT_EXPR && LA140_0<=NOT_IN_SUBSELECT_EXPR)||LA140_0==SUBSTITUTION||(LA140_0>=INT_TYPE && LA140_0<=NULL_TYPE)||LA140_0==STAR||(LA140_0>=BAND && LA140_0<=BXOR)||(LA140_0>=LT && LA140_0<=GE)||(LA140_0>=PLUS && LA140_0<=MOD)) ) {
                        alt140=1;
                    }


                    switch (alt140) {
                	case 1 :
                	    // EsperEPL2Ast.g:438:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3127);
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
    // EsperEPL2Ast.g:441:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:442:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt142=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt142=1;
                }
                break;
            case MINUS:
                {
                alt142=2;
                }
                break;
            case DIV:
                {
                alt142=3;
                }
                break;
            case STAR:
                {
                alt142=4;
                }
                break;
            case MOD:
                {
                alt142=5;
                }
                break;
            case BAND:
                {
                alt142=6;
                }
                break;
            case BOR:
                {
                alt142=7;
                }
                break;
            case BXOR:
                {
                alt142=8;
                }
                break;
            case CONCAT:
                {
                alt142=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 142, 0, input);

                throw nvae;
            }

            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:442:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3148); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3150);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3152);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:443:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3164); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3166);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3168);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:444:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3180); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3182);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3184);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:445:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3195); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3197);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3199);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:446:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3211); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3213);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3215);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:447:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3226); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3228);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3230);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:448:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3241); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3243);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3245);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:449:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3256); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3258);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3260);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:450:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3272); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3274);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3276);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:450:36: ( valueExpr )*
                    loop141:
                    do {
                        int alt141=2;
                        int LA141_0 = input.LA(1);

                        if ( ((LA141_0>=IN_SET && LA141_0<=REGEXP)||LA141_0==NOT_EXPR||(LA141_0>=SUM && LA141_0<=AVG)||(LA141_0>=COALESCE && LA141_0<=COUNT)||(LA141_0>=CASE && LA141_0<=CASE2)||(LA141_0>=PREVIOUS && LA141_0<=EXISTS)||(LA141_0>=INSTANCEOF && LA141_0<=CURRENT_TIMESTAMP)||(LA141_0>=EVAL_AND_EXPR && LA141_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA141_0==EVENT_PROP_EXPR||(LA141_0>=CONCAT && LA141_0<=LIB_FUNCTION)||LA141_0==ARRAY_EXPR||(LA141_0>=NOT_IN_SET && LA141_0<=NOT_REGEXP)||(LA141_0>=IN_RANGE && LA141_0<=SUBSELECT_EXPR)||(LA141_0>=EXISTS_SUBSELECT_EXPR && LA141_0<=NOT_IN_SUBSELECT_EXPR)||LA141_0==SUBSTITUTION||(LA141_0>=INT_TYPE && LA141_0<=NULL_TYPE)||LA141_0==STAR||(LA141_0>=BAND && LA141_0<=BXOR)||(LA141_0>=LT && LA141_0<=GE)||(LA141_0>=PLUS && LA141_0<=MOD)) ) {
                            alt141=1;
                        }


                        switch (alt141) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:450:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3279);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop141;
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
    // EsperEPL2Ast.g:453:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:454:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:454:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3300); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:454:22: ( CLASS_IDENT )?
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( (LA143_0==CLASS_IDENT) ) {
                alt143=1;
            }
            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:454:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3303); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3307); 
            // EsperEPL2Ast.g:454:43: ( DISTINCT )?
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( (LA144_0==DISTINCT) ) {
                alt144=1;
            }
            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:454:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3310); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:454:55: ( valueExpr )*
            loop145:
            do {
                int alt145=2;
                int LA145_0 = input.LA(1);

                if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||(LA145_0>=CONCAT && LA145_0<=LIB_FUNCTION)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=SUBSELECT_EXPR)||(LA145_0>=EXISTS_SUBSELECT_EXPR && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||LA145_0==STAR||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=PLUS && LA145_0<=MOD)) ) {
                    alt145=1;
                }


                switch (alt145) {
            	case 1 :
            	    // EsperEPL2Ast.g:454:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3315);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop145;
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
    // EsperEPL2Ast.g:460:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:461:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:461:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:461:4: ( annotation[true] )*
            loop146:
            do {
                int alt146=2;
                int LA146_0 = input.LA(1);

                if ( (LA146_0==ANNOTATION) ) {
                    alt146=1;
                }


                switch (alt146) {
            	case 1 :
            	    // EsperEPL2Ast.g:461:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule3335);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop146;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3339);
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
    // EsperEPL2Ast.g:464:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:465:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt150=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt150=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt150=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt150=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt150=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt150=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt150=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt150=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 150, 0, input);

                throw nvae;
            }

            switch (alt150) {
                case 1 :
                    // EsperEPL2Ast.g:465:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3353);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:466:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3358);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:467:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3368); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3370);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:468:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3384); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice3386);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3388);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:469:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3402); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3404);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:470:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3418); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3420);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3422); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3424); 
                    // EsperEPL2Ast.g:470:44: ( valueExprWithTime )*
                    loop147:
                    do {
                        int alt147=2;
                        int LA147_0 = input.LA(1);

                        if ( ((LA147_0>=IN_SET && LA147_0<=REGEXP)||LA147_0==NOT_EXPR||(LA147_0>=SUM && LA147_0<=AVG)||(LA147_0>=COALESCE && LA147_0<=COUNT)||(LA147_0>=CASE && LA147_0<=CASE2)||LA147_0==LAST||(LA147_0>=PREVIOUS && LA147_0<=EXISTS)||(LA147_0>=LW && LA147_0<=CURRENT_TIMESTAMP)||(LA147_0>=NUMERIC_PARAM_RANGE && LA147_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA147_0>=EVAL_AND_EXPR && LA147_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA147_0==EVENT_PROP_EXPR||(LA147_0>=CONCAT && LA147_0<=LIB_FUNCTION)||(LA147_0>=TIME_PERIOD && LA147_0<=ARRAY_EXPR)||(LA147_0>=NOT_IN_SET && LA147_0<=NOT_REGEXP)||(LA147_0>=IN_RANGE && LA147_0<=SUBSELECT_EXPR)||(LA147_0>=EXISTS_SUBSELECT_EXPR && LA147_0<=NOT_IN_SUBSELECT_EXPR)||(LA147_0>=LAST_OPERATOR && LA147_0<=SUBSTITUTION)||LA147_0==NUMBERSETSTAR||(LA147_0>=INT_TYPE && LA147_0<=NULL_TYPE)||LA147_0==STAR||(LA147_0>=BAND && LA147_0<=BXOR)||(LA147_0>=LT && LA147_0<=GE)||(LA147_0>=PLUS && LA147_0<=MOD)) ) {
                            alt147=1;
                        }


                        switch (alt147) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:470:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3426);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop147;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:471:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3440); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:471:26: ( matchUntilRange )?
                    int alt148=2;
                    int LA148_0 = input.LA(1);

                    if ( ((LA148_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA148_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt148=1;
                    }
                    switch (alt148) {
                        case 1 :
                            // EsperEPL2Ast.g:471:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice3442);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3445);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:471:54: ( exprChoice )?
                    int alt149=2;
                    int LA149_0 = input.LA(1);

                    if ( ((LA149_0>=OR_EXPR && LA149_0<=AND_EXPR)||(LA149_0>=EVERY_EXPR && LA149_0<=EVERY_DISTINCT_EXPR)||LA149_0==FOLLOWED_BY_EXPR||(LA149_0>=PATTERN_FILTER_EXPR && LA149_0<=PATTERN_NOT_EXPR)||(LA149_0>=GUARD_EXPR && LA149_0<=OBSERVER_EXPR)||LA149_0==MATCH_UNTIL_EXPR) ) {
                        alt149=1;
                    }
                    switch (alt149) {
                        case 1 :
                            // EsperEPL2Ast.g:471:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice3447);
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
    // EsperEPL2Ast.g:475:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:476:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:476:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3468); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:476:35: ( valueExpr )+
            int cnt151=0;
            loop151:
            do {
                int alt151=2;
                int LA151_0 = input.LA(1);

                if ( ((LA151_0>=IN_SET && LA151_0<=REGEXP)||LA151_0==NOT_EXPR||(LA151_0>=SUM && LA151_0<=AVG)||(LA151_0>=COALESCE && LA151_0<=COUNT)||(LA151_0>=CASE && LA151_0<=CASE2)||(LA151_0>=PREVIOUS && LA151_0<=EXISTS)||(LA151_0>=INSTANCEOF && LA151_0<=CURRENT_TIMESTAMP)||(LA151_0>=EVAL_AND_EXPR && LA151_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA151_0==EVENT_PROP_EXPR||(LA151_0>=CONCAT && LA151_0<=LIB_FUNCTION)||LA151_0==ARRAY_EXPR||(LA151_0>=NOT_IN_SET && LA151_0<=NOT_REGEXP)||(LA151_0>=IN_RANGE && LA151_0<=SUBSELECT_EXPR)||(LA151_0>=EXISTS_SUBSELECT_EXPR && LA151_0<=NOT_IN_SUBSELECT_EXPR)||LA151_0==SUBSTITUTION||(LA151_0>=INT_TYPE && LA151_0<=NULL_TYPE)||LA151_0==STAR||(LA151_0>=BAND && LA151_0<=BXOR)||(LA151_0>=LT && LA151_0<=GE)||(LA151_0>=PLUS && LA151_0<=MOD)) ) {
                    alt151=1;
                }


                switch (alt151) {
            	case 1 :
            	    // EsperEPL2Ast.g:476:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions3470);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    if ( cnt151 >= 1 ) break loop151;
                        EarlyExitException eee =
                            new EarlyExitException(151, input);
                        throw eee;
                }
                cnt151++;
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
    // EsperEPL2Ast.g:479:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:480:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt155=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt155=1;
                }
                break;
            case OR_EXPR:
                {
                alt155=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:480:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3489); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3491);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3493);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:480:48: ( exprChoice )*
                    loop152:
                    do {
                        int alt152=2;
                        int LA152_0 = input.LA(1);

                        if ( ((LA152_0>=OR_EXPR && LA152_0<=AND_EXPR)||(LA152_0>=EVERY_EXPR && LA152_0<=EVERY_DISTINCT_EXPR)||LA152_0==FOLLOWED_BY_EXPR||(LA152_0>=PATTERN_FILTER_EXPR && LA152_0<=PATTERN_NOT_EXPR)||(LA152_0>=GUARD_EXPR && LA152_0<=OBSERVER_EXPR)||LA152_0==MATCH_UNTIL_EXPR) ) {
                            alt152=1;
                        }


                        switch (alt152) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:480:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3496);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop152;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:481:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp3512); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3514);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3516);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:481:40: ( exprChoice )*
                    loop153:
                    do {
                        int alt153=2;
                        int LA153_0 = input.LA(1);

                        if ( ((LA153_0>=OR_EXPR && LA153_0<=AND_EXPR)||(LA153_0>=EVERY_EXPR && LA153_0<=EVERY_DISTINCT_EXPR)||LA153_0==FOLLOWED_BY_EXPR||(LA153_0>=PATTERN_FILTER_EXPR && LA153_0<=PATTERN_NOT_EXPR)||(LA153_0>=GUARD_EXPR && LA153_0<=OBSERVER_EXPR)||LA153_0==MATCH_UNTIL_EXPR) ) {
                            alt153=1;
                        }


                        switch (alt153) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:481:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3519);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop153;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:482:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3535); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3537);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3539);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:482:41: ( exprChoice )*
                    loop154:
                    do {
                        int alt154=2;
                        int LA154_0 = input.LA(1);

                        if ( ((LA154_0>=OR_EXPR && LA154_0<=AND_EXPR)||(LA154_0>=EVERY_EXPR && LA154_0<=EVERY_DISTINCT_EXPR)||LA154_0==FOLLOWED_BY_EXPR||(LA154_0>=PATTERN_FILTER_EXPR && LA154_0<=PATTERN_NOT_EXPR)||(LA154_0>=GUARD_EXPR && LA154_0<=OBSERVER_EXPR)||LA154_0==MATCH_UNTIL_EXPR) ) {
                            alt154=1;
                        }


                        switch (alt154) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:482:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3542);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop154;
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
    // EsperEPL2Ast.g:485:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:486:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt157=2;
            int LA157_0 = input.LA(1);

            if ( (LA157_0==PATTERN_FILTER_EXPR) ) {
                alt157=1;
            }
            else if ( (LA157_0==OBSERVER_EXPR) ) {
                alt157=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 157, 0, input);

                throw nvae;
            }
            switch (alt157) {
                case 1 :
                    // EsperEPL2Ast.g:486:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr3561);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:487:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3573); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3575); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3577); 
                    // EsperEPL2Ast.g:487:39: ( valueExprWithTime )*
                    loop156:
                    do {
                        int alt156=2;
                        int LA156_0 = input.LA(1);

                        if ( ((LA156_0>=IN_SET && LA156_0<=REGEXP)||LA156_0==NOT_EXPR||(LA156_0>=SUM && LA156_0<=AVG)||(LA156_0>=COALESCE && LA156_0<=COUNT)||(LA156_0>=CASE && LA156_0<=CASE2)||LA156_0==LAST||(LA156_0>=PREVIOUS && LA156_0<=EXISTS)||(LA156_0>=LW && LA156_0<=CURRENT_TIMESTAMP)||(LA156_0>=NUMERIC_PARAM_RANGE && LA156_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA156_0>=EVAL_AND_EXPR && LA156_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA156_0==EVENT_PROP_EXPR||(LA156_0>=CONCAT && LA156_0<=LIB_FUNCTION)||(LA156_0>=TIME_PERIOD && LA156_0<=ARRAY_EXPR)||(LA156_0>=NOT_IN_SET && LA156_0<=NOT_REGEXP)||(LA156_0>=IN_RANGE && LA156_0<=SUBSELECT_EXPR)||(LA156_0>=EXISTS_SUBSELECT_EXPR && LA156_0<=NOT_IN_SUBSELECT_EXPR)||(LA156_0>=LAST_OPERATOR && LA156_0<=SUBSTITUTION)||LA156_0==NUMBERSETSTAR||(LA156_0>=INT_TYPE && LA156_0<=NULL_TYPE)||LA156_0==STAR||(LA156_0>=BAND && LA156_0<=BXOR)||(LA156_0>=LT && LA156_0<=GE)||(LA156_0>=PLUS && LA156_0<=MOD)) ) {
                            alt156=1;
                        }


                        switch (alt156) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:487:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3579);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop156;
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
    // EsperEPL2Ast.g:490:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:491:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:491:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3599); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:491:29: ( IDENT )?
            int alt158=2;
            int LA158_0 = input.LA(1);

            if ( (LA158_0==IDENT) ) {
                alt158=1;
            }
            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:491:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr3601); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr3604); 
            // EsperEPL2Ast.g:491:48: ( propertyExpression )?
            int alt159=2;
            int LA159_0 = input.LA(1);

            if ( (LA159_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt159=1;
            }
            switch (alt159) {
                case 1 :
                    // EsperEPL2Ast.g:491:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr3606);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:491:68: ( valueExpr )*
            loop160:
            do {
                int alt160=2;
                int LA160_0 = input.LA(1);

                if ( ((LA160_0>=IN_SET && LA160_0<=REGEXP)||LA160_0==NOT_EXPR||(LA160_0>=SUM && LA160_0<=AVG)||(LA160_0>=COALESCE && LA160_0<=COUNT)||(LA160_0>=CASE && LA160_0<=CASE2)||(LA160_0>=PREVIOUS && LA160_0<=EXISTS)||(LA160_0>=INSTANCEOF && LA160_0<=CURRENT_TIMESTAMP)||(LA160_0>=EVAL_AND_EXPR && LA160_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA160_0==EVENT_PROP_EXPR||(LA160_0>=CONCAT && LA160_0<=LIB_FUNCTION)||LA160_0==ARRAY_EXPR||(LA160_0>=NOT_IN_SET && LA160_0<=NOT_REGEXP)||(LA160_0>=IN_RANGE && LA160_0<=SUBSELECT_EXPR)||(LA160_0>=EXISTS_SUBSELECT_EXPR && LA160_0<=NOT_IN_SUBSELECT_EXPR)||LA160_0==SUBSTITUTION||(LA160_0>=INT_TYPE && LA160_0<=NULL_TYPE)||LA160_0==STAR||(LA160_0>=BAND && LA160_0<=BXOR)||(LA160_0>=LT && LA160_0<=GE)||(LA160_0>=PLUS && LA160_0<=MOD)) ) {
                    alt160=1;
                }


                switch (alt160) {
            	case 1 :
            	    // EsperEPL2Ast.g:491:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr3610);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop160;
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
    // EsperEPL2Ast.g:494:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:495:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt161=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt161=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt161=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt161=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt161=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }

            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:495:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3628); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3630);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3632);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:496:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3640); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3642);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:497:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3650); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3652);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:498:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3659); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3661);
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
    // EsperEPL2Ast.g:501:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:502:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:506:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:507:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:507:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam3690); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam3692);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:507:35: ( valueExpr )*
            loop162:
            do {
                int alt162=2;
                int LA162_0 = input.LA(1);

                if ( ((LA162_0>=IN_SET && LA162_0<=REGEXP)||LA162_0==NOT_EXPR||(LA162_0>=SUM && LA162_0<=AVG)||(LA162_0>=COALESCE && LA162_0<=COUNT)||(LA162_0>=CASE && LA162_0<=CASE2)||(LA162_0>=PREVIOUS && LA162_0<=EXISTS)||(LA162_0>=INSTANCEOF && LA162_0<=CURRENT_TIMESTAMP)||(LA162_0>=EVAL_AND_EXPR && LA162_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA162_0==EVENT_PROP_EXPR||(LA162_0>=CONCAT && LA162_0<=LIB_FUNCTION)||LA162_0==ARRAY_EXPR||(LA162_0>=NOT_IN_SET && LA162_0<=NOT_REGEXP)||(LA162_0>=IN_RANGE && LA162_0<=SUBSELECT_EXPR)||(LA162_0>=EXISTS_SUBSELECT_EXPR && LA162_0<=NOT_IN_SUBSELECT_EXPR)||LA162_0==SUBSTITUTION||(LA162_0>=INT_TYPE && LA162_0<=NULL_TYPE)||LA162_0==STAR||(LA162_0>=BAND && LA162_0<=BXOR)||(LA162_0>=LT && LA162_0<=GE)||(LA162_0>=PLUS && LA162_0<=MOD)) ) {
                    alt162=1;
                }


                switch (alt162) {
            	case 1 :
            	    // EsperEPL2Ast.g:507:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam3695);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop162;
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
    // EsperEPL2Ast.g:510:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:511:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt175=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt175=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt175=2;
                }
                break;
            case LT:
                {
                alt175=3;
                }
                break;
            case LE:
                {
                alt175=4;
                }
                break;
            case GT:
                {
                alt175=5;
                }
                break;
            case GE:
                {
                alt175=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt175=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt175=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt175=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt175=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt175=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt175=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                throw nvae;
            }

            switch (alt175) {
                case 1 :
                    // EsperEPL2Ast.g:511:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator3711); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3713);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:512:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator3720); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3722);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:513:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator3729); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3731);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:514:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator3738); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3740);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:515:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator3747); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3749);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:516:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator3756); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3758);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:517:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3765); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:517:41: ( constant[false] | filterIdentifier )
                    int alt163=2;
                    int LA163_0 = input.LA(1);

                    if ( ((LA163_0>=INT_TYPE && LA163_0<=NULL_TYPE)) ) {
                        alt163=1;
                    }
                    else if ( (LA163_0==EVENT_FILTER_IDENT) ) {
                        alt163=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 163, 0, input);

                        throw nvae;
                    }
                    switch (alt163) {
                        case 1 :
                            // EsperEPL2Ast.g:517:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3774);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:517:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3777);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:517:76: ( constant[false] | filterIdentifier )
                    int alt164=2;
                    int LA164_0 = input.LA(1);

                    if ( ((LA164_0>=INT_TYPE && LA164_0<=NULL_TYPE)) ) {
                        alt164=1;
                    }
                    else if ( (LA164_0==EVENT_FILTER_IDENT) ) {
                        alt164=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 164, 0, input);

                        throw nvae;
                    }
                    switch (alt164) {
                        case 1 :
                            // EsperEPL2Ast.g:517:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3781);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:517:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3784);
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
                    // EsperEPL2Ast.g:518:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3798); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:518:45: ( constant[false] | filterIdentifier )
                    int alt165=2;
                    int LA165_0 = input.LA(1);

                    if ( ((LA165_0>=INT_TYPE && LA165_0<=NULL_TYPE)) ) {
                        alt165=1;
                    }
                    else if ( (LA165_0==EVENT_FILTER_IDENT) ) {
                        alt165=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 165, 0, input);

                        throw nvae;
                    }
                    switch (alt165) {
                        case 1 :
                            // EsperEPL2Ast.g:518:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3807);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:518:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3810);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:518:80: ( constant[false] | filterIdentifier )
                    int alt166=2;
                    int LA166_0 = input.LA(1);

                    if ( ((LA166_0>=INT_TYPE && LA166_0<=NULL_TYPE)) ) {
                        alt166=1;
                    }
                    else if ( (LA166_0==EVENT_FILTER_IDENT) ) {
                        alt166=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 166, 0, input);

                        throw nvae;
                    }
                    switch (alt166) {
                        case 1 :
                            // EsperEPL2Ast.g:518:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3814);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:518:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3817);
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
                    // EsperEPL2Ast.g:519:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3831); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:519:38: ( constant[false] | filterIdentifier )
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( ((LA167_0>=INT_TYPE && LA167_0<=NULL_TYPE)) ) {
                        alt167=1;
                    }
                    else if ( (LA167_0==EVENT_FILTER_IDENT) ) {
                        alt167=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 167, 0, input);

                        throw nvae;
                    }
                    switch (alt167) {
                        case 1 :
                            // EsperEPL2Ast.g:519:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3840);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:519:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3843);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:519:73: ( constant[false] | filterIdentifier )*
                    loop168:
                    do {
                        int alt168=3;
                        int LA168_0 = input.LA(1);

                        if ( ((LA168_0>=INT_TYPE && LA168_0<=NULL_TYPE)) ) {
                            alt168=1;
                        }
                        else if ( (LA168_0==EVENT_FILTER_IDENT) ) {
                            alt168=2;
                        }


                        switch (alt168) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:519:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3847);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:519:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3850);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop168;
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
                    // EsperEPL2Ast.g:520:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3865); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:520:42: ( constant[false] | filterIdentifier )
                    int alt169=2;
                    int LA169_0 = input.LA(1);

                    if ( ((LA169_0>=INT_TYPE && LA169_0<=NULL_TYPE)) ) {
                        alt169=1;
                    }
                    else if ( (LA169_0==EVENT_FILTER_IDENT) ) {
                        alt169=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 169, 0, input);

                        throw nvae;
                    }
                    switch (alt169) {
                        case 1 :
                            // EsperEPL2Ast.g:520:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3874);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:520:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3877);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:520:77: ( constant[false] | filterIdentifier )*
                    loop170:
                    do {
                        int alt170=3;
                        int LA170_0 = input.LA(1);

                        if ( ((LA170_0>=INT_TYPE && LA170_0<=NULL_TYPE)) ) {
                            alt170=1;
                        }
                        else if ( (LA170_0==EVENT_FILTER_IDENT) ) {
                            alt170=2;
                        }


                        switch (alt170) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:520:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3881);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:520:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3884);
                    	    filterIdentifier();

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

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:521:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3899); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:521:27: ( constant[false] | filterIdentifier )
                    int alt171=2;
                    int LA171_0 = input.LA(1);

                    if ( ((LA171_0>=INT_TYPE && LA171_0<=NULL_TYPE)) ) {
                        alt171=1;
                    }
                    else if ( (LA171_0==EVENT_FILTER_IDENT) ) {
                        alt171=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 171, 0, input);

                        throw nvae;
                    }
                    switch (alt171) {
                        case 1 :
                            // EsperEPL2Ast.g:521:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3902);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:521:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3905);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:521:62: ( constant[false] | filterIdentifier )
                    int alt172=2;
                    int LA172_0 = input.LA(1);

                    if ( ((LA172_0>=INT_TYPE && LA172_0<=NULL_TYPE)) ) {
                        alt172=1;
                    }
                    else if ( (LA172_0==EVENT_FILTER_IDENT) ) {
                        alt172=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 172, 0, input);

                        throw nvae;
                    }
                    switch (alt172) {
                        case 1 :
                            // EsperEPL2Ast.g:521:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3909);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:521:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3912);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:522:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3920); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:522:31: ( constant[false] | filterIdentifier )
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( ((LA173_0>=INT_TYPE && LA173_0<=NULL_TYPE)) ) {
                        alt173=1;
                    }
                    else if ( (LA173_0==EVENT_FILTER_IDENT) ) {
                        alt173=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 173, 0, input);

                        throw nvae;
                    }
                    switch (alt173) {
                        case 1 :
                            // EsperEPL2Ast.g:522:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3923);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:522:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3926);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:522:66: ( constant[false] | filterIdentifier )
                    int alt174=2;
                    int LA174_0 = input.LA(1);

                    if ( ((LA174_0>=INT_TYPE && LA174_0<=NULL_TYPE)) ) {
                        alt174=1;
                    }
                    else if ( (LA174_0==EVENT_FILTER_IDENT) ) {
                        alt174=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 174, 0, input);

                        throw nvae;
                    }
                    switch (alt174) {
                        case 1 :
                            // EsperEPL2Ast.g:522:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3930);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:522:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3933);
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
    // EsperEPL2Ast.g:525:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:526:2: ( constant[false] | filterIdentifier )
            int alt176=2;
            int LA176_0 = input.LA(1);

            if ( ((LA176_0>=INT_TYPE && LA176_0<=NULL_TYPE)) ) {
                alt176=1;
            }
            else if ( (LA176_0==EVENT_FILTER_IDENT) ) {
                alt176=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 176, 0, input);

                throw nvae;
            }
            switch (alt176) {
                case 1 :
                    // EsperEPL2Ast.g:526:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom3947);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:527:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom3953);
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
    // EsperEPL2Ast.g:529:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:530:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:530:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3964); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier3966); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier3968);
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
    // EsperEPL2Ast.g:533:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:534:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:534:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3987); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3989);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:534:44: ( eventPropertyAtomic )*
            loop177:
            do {
                int alt177=2;
                int LA177_0 = input.LA(1);

                if ( ((LA177_0>=EVENT_PROP_SIMPLE && LA177_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt177=1;
                }


                switch (alt177) {
            	case 1 :
            	    // EsperEPL2Ast.g:534:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3992);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop177;
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
    // EsperEPL2Ast.g:537:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:538:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt178=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt178=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt178=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt178=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt178=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt178=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt178=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 178, 0, input);

                throw nvae;
            }

            switch (alt178) {
                case 1 :
                    // EsperEPL2Ast.g:538:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4011); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4013); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:539:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4020); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4022); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4024); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:540:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4031); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4033); 
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
                    // EsperEPL2Ast.g:541:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4048); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4050); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:542:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4057); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4059); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4061); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:543:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4068); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4070); 
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
    // EsperEPL2Ast.g:546:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:547:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:547:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4097); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4099);
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
    // EsperEPL2Ast.g:550:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:551:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt189=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt189=1;
                }
                break;
            case HOUR_PART:
                {
                alt189=2;
                }
                break;
            case MINUTE_PART:
                {
                alt189=3;
                }
                break;
            case SECOND_PART:
                {
                alt189=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt189=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 189, 0, input);

                throw nvae;
            }

            switch (alt189) {
                case 1 :
                    // EsperEPL2Ast.g:551:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4115);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:551:13: ( hourPart )?
                    int alt179=2;
                    int LA179_0 = input.LA(1);

                    if ( (LA179_0==HOUR_PART) ) {
                        alt179=1;
                    }
                    switch (alt179) {
                        case 1 :
                            // EsperEPL2Ast.g:551:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4118);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:551:25: ( minutePart )?
                    int alt180=2;
                    int LA180_0 = input.LA(1);

                    if ( (LA180_0==MINUTE_PART) ) {
                        alt180=1;
                    }
                    switch (alt180) {
                        case 1 :
                            // EsperEPL2Ast.g:551:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4123);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:551:39: ( secondPart )?
                    int alt181=2;
                    int LA181_0 = input.LA(1);

                    if ( (LA181_0==SECOND_PART) ) {
                        alt181=1;
                    }
                    switch (alt181) {
                        case 1 :
                            // EsperEPL2Ast.g:551:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4128);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:551:53: ( millisecondPart )?
                    int alt182=2;
                    int LA182_0 = input.LA(1);

                    if ( (LA182_0==MILLISECOND_PART) ) {
                        alt182=1;
                    }
                    switch (alt182) {
                        case 1 :
                            // EsperEPL2Ast.g:551:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4133);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:552:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4140);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:552:13: ( minutePart )?
                    int alt183=2;
                    int LA183_0 = input.LA(1);

                    if ( (LA183_0==MINUTE_PART) ) {
                        alt183=1;
                    }
                    switch (alt183) {
                        case 1 :
                            // EsperEPL2Ast.g:552:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4143);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:552:27: ( secondPart )?
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( (LA184_0==SECOND_PART) ) {
                        alt184=1;
                    }
                    switch (alt184) {
                        case 1 :
                            // EsperEPL2Ast.g:552:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4148);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:552:41: ( millisecondPart )?
                    int alt185=2;
                    int LA185_0 = input.LA(1);

                    if ( (LA185_0==MILLISECOND_PART) ) {
                        alt185=1;
                    }
                    switch (alt185) {
                        case 1 :
                            // EsperEPL2Ast.g:552:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4153);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:553:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4160);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:553:15: ( secondPart )?
                    int alt186=2;
                    int LA186_0 = input.LA(1);

                    if ( (LA186_0==SECOND_PART) ) {
                        alt186=1;
                    }
                    switch (alt186) {
                        case 1 :
                            // EsperEPL2Ast.g:553:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4163);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:553:29: ( millisecondPart )?
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( (LA187_0==MILLISECOND_PART) ) {
                        alt187=1;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:553:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4168);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:554:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4175);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:554:15: ( millisecondPart )?
                    int alt188=2;
                    int LA188_0 = input.LA(1);

                    if ( (LA188_0==MILLISECOND_PART) ) {
                        alt188=1;
                    }
                    switch (alt188) {
                        case 1 :
                            // EsperEPL2Ast.g:554:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4178);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:555:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4185);
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
    // EsperEPL2Ast.g:558:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:559:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:559:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4199); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4201);
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
    // EsperEPL2Ast.g:562:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:563:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:563:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4216); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4218);
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
    // EsperEPL2Ast.g:566:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:567:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:567:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4233); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4235);
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
    // EsperEPL2Ast.g:570:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:571:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:571:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4250); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4252);
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
    // EsperEPL2Ast.g:574:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:575:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:575:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4267); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4269);
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
    // EsperEPL2Ast.g:578:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:579:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:579:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4284); 
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
    // EsperEPL2Ast.g:582:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:583:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt190=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt190=1;
                }
                break;
            case LONG_TYPE:
                {
                alt190=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt190=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt190=4;
                }
                break;
            case STRING_TYPE:
                {
                alt190=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt190=6;
                }
                break;
            case NULL_TYPE:
                {
                alt190=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 190, 0, input);

                throw nvae;
            }

            switch (alt190) {
                case 1 :
                    // EsperEPL2Ast.g:583:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4300); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:584:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4309); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:585:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4318); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:586:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4327); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:587:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4343); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:588:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4359); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:589:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4372); 
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
    // EsperEPL2Ast.g:592:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:593:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0400000000000000L,0x0000000000000000L,0x00000000FFC00000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0400000000000000L,0x0000000000000000L,0x00000000FFC00000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L,0x0000000000000000L,0x00000000FEC00000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0400000000000000L,0x0000000000000000L,0x00000000FEC00000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000400L,0x0000000000401028L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr259 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000008000008C0L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000008000008C0L});
    public static final BitSet FOLLOW_IDENT_in_onExpr266 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000008000008C0L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr273 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr277 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr280 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000300L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr283 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr290 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr312 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr315 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr332 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr334 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000001800L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr337 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000180003L,0x0000000000000400L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr339 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000180003L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr342 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000180002L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr345 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100002L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr348 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr370 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000001800L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr372 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr374 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput390 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput392 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr410 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr412 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr415 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment430 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment432 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom444 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom446 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom449 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr467 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr469 = new BitSet(new long[]{0x8000000000000000L,0x2400000000000001L,0x0000000000000000L,0x0000000000080010L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr472 = new BitSet(new long[]{0x8000000000000000L,0x2400000000000001L,0x0000000000000000L,0x0000000000080010L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr476 = new BitSet(new long[]{0x8000000000000000L,0x2400000000000001L,0x0000000000000000L,0x0000000000080010L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr479 = new BitSet(new long[]{0x8000000000000000L,0x2400000000000001L,0x0000000000000000L,0x0000000000080010L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr493 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr496 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr525 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr536 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert554 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert556 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList573 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList575 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0020000000000800L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList578 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0020000000000800L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList597 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList599 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList602 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement617 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement619 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement621 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement645 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement665 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement691 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement694 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr730 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr732 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr734 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr737 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr755 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000400000400L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr761 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr766 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000002E0180003L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr771 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000002E0180002L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr778 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000002E0100002L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr785 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000002E0100000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr792 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr799 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr823 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr825 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr834 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr837 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol856 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol858 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol861 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause879 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause881 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0020000000001800L});
    public static final BitSet FOLLOW_selectionList_in_selectClause894 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause908 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause911 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000007A000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause914 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000007A000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList931 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000001800L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList934 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0020000000001800L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement950 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement960 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement962 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement965 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement979 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement981 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement984 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1003 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1017 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1019 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1022 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1026 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1029 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1044 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1046 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1049 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1053 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1056 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1071 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1073 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1076 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1080 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1083 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1098 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1100 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1103 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1107 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1110 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1131 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1134 = new BitSet(new long[]{0xC000000000000008L,0x2000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1138 = new BitSet(new long[]{0xC000000000000008L,0x2000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1142 = new BitSet(new long[]{0xC000000000000008L,0x2000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1146 = new BitSet(new long[]{0xC000000000000008L,0x2000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1150 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1155 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1160 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_streamExpression1164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1188 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1190 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1193 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000200000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1195 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1199 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1221 = new BitSet(new long[]{0x0000000000000008L,0x0000400000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1240 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1242 = new BitSet(new long[]{0x0000000000000000L,0x0003800000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1245 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1248 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1252 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1254 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1274 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1303 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1305 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1308 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1331 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1348 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1350 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1352 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1360 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1381 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1383 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1385 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1388 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1402 = new BitSet(new long[]{0x0000000000000002L,0x2000000000000000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1405 = new BitSet(new long[]{0x0000000000000002L,0x2000000000000000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1422 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1424 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1426 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1429 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1450 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1452 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1470 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1472 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1475 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1493 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1495 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1498 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1518 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1520 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1522 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1545 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1547 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1565 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1567 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x000000081E000000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1579 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1581 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1598 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1600 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1611 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1626 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1628 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1639 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1654 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1656 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr1667 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x00000008000008C0L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr1669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1688 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1691 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0000000C1E000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1693 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0000000C1E000000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1697 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1699 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause1703 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause1706 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1724 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1726 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1728 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1730 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1732 = new BitSet(new long[]{0x0020000037CC23C0L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1734 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1736 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr1753 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1755 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr1768 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1770 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr1783 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1785 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr1797 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1821 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1831 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue1846 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x3F83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1855 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue1860 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1886 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1888 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1890 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1893 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1907 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1909 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1911 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1914 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1928 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1930 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1932 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1944 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1946 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1948 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice1960 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1962 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice1964 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3F83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1973 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice1978 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice1991 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1993 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice1995 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3F83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2004 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2009 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2022 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2024 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2035 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2048 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2054 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2067 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2076 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2089 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2094 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2099 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2105 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2125 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2138 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2175 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2177 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2179 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2192 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2198 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2203 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2208 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2218 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2220 = new BitSet(new long[]{0x0000000000000008L,0x0000002800000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2231 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2238 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2251 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2280 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2283 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L,0x00000000FE000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2286 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L,0x00000000FE000002L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2289 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L,0x00000000FE000002L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2293 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2296 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2299 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2320 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2323 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2326 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2329 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2348 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2351 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2354 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2357 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2376 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2379 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2382 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2385 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2406 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr2408 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2427 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2429 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2448 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2450 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2469 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2473 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2487 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2489 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2508 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2510 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2526 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000002000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2528 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2531 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2548 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2550 = new BitSet(new long[]{0x8000000000000008L,0x2000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2553 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2558 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr2562 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2565 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2585 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2588 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2601 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2604 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2624 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000024000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2628 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2634 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C580FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2637 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C580FE000002L});
    public static final BitSet FOLLOW_set_in_inExpr2641 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr2656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000024000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2660 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2666 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C580FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2669 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C580FE000002L});
    public static final BitSet FOLLOW_set_in_inExpr2673 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr2688 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2690 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000024000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2692 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2698 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2700 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000048000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2702 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr2717 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2719 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000024000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2721 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2727 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2729 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000048000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2731 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr2754 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2756 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2758 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2760 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr2771 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2773 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2775 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2778 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr2798 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2800 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2802 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2805 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr2818 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2820 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2822 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2825 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr2844 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2846 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2848 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr2859 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2861 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2863 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc2882 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2885 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2889 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc2900 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2903 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2907 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc2918 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2922 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2926 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc2940 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2943 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2947 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc2958 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2961 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2965 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc2976 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2979 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2983 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc2995 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2997 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2999 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3002 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3017 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3019 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3021 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3034 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3038 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3040 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3053 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3055 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3057 = new BitSet(new long[]{0x0000000000000008L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3060 = new BitSet(new long[]{0x0000000000000008L,0x0400000000000000L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3074 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3076 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3078 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3090 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3092 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3127 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3148 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3150 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3152 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3164 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3166 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3168 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3180 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3182 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3184 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3195 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3197 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3199 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3211 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3213 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3215 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3226 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3228 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3241 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3243 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3245 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3256 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3258 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3260 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3272 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3274 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3276 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3279 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3300 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3303 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3307 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3310 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3315 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule3335 = new BitSet(new long[]{0x000000000000D800L,0x1800068000000000L,0x0000000000000000L,0x0000000000404000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3339 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3358 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3368 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3384 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice3386 = new BitSet(new long[]{0x000000000000D800L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3388 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3402 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3404 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3418 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3420 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3422 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3424 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3426 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3440 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice3442 = new BitSet(new long[]{0x000000000000D800L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3445 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3447 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3468 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions3470 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3489 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3491 = new BitSet(new long[]{0x000000000000D800L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3493 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3496 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp3512 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3514 = new BitSet(new long[]{0x000000000000D800L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3516 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3519 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3535 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3537 = new BitSet(new long[]{0x000000000000D800L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3539 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3542 = new BitSet(new long[]{0x000000000000D808L,0x1800068000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr3561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3573 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3575 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3577 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3579 = new BitSet(new long[]{0x0020000037CC23C8L,0x0000007800000F70L,0xBB83C1B0004001F8L,0x0F79C100FE200003L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr3601 = new BitSet(new long[]{0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr3604 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000200000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr3606 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr3610 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3628 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3630 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000100000000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3632 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3640 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3642 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3650 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3652 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3659 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3661 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam3690 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3692 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3695 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x3B83C130004001F8L,0x0F79C100FE000002L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator3711 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3713 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator3720 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3722 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator3729 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3731 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator3738 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3740 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator3747 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3749 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator3756 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3758 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3765 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3767 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3774 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3777 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3781 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000048000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3784 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000048000000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3787 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3798 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3800 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3807 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3810 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3814 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000048000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3817 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000048000000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3820 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3831 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3833 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3840 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3843 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3847 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3850 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3854 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3865 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3867 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3874 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3877 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3881 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3884 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000480FE000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3888 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3899 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3902 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3905 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3909 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3912 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3920 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3923 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3926 = new BitSet(new long[]{0x0000000000000000L,0x0004000000000000L,0x0000000000000000L,0x00000000FE000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3930 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3933 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom3947 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom3953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3964 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier3966 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier3968 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3987 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3989 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000001F800000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3992 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000001F800000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4011 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4013 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4020 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4022 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4024 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4031 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4033 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4035 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4048 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4050 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4057 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4059 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4061 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4068 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4072 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4097 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4099 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4115 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00003C0000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4118 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000380000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4123 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4128 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4133 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4140 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000380000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4143 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4148 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4153 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4160 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000300000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4163 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4168 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4175 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000200000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4185 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4199 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4201 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4216 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4218 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4233 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4235 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4252 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4267 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4269 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4318 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4327 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4359 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4372 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}
