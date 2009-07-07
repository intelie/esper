// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-07-06 18:46:45

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "EVERY_DISTINCT_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "UPDATE", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "PATTERN_EVERY_DISTINCT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "SUBSELECT_GROUP_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_SELECT_INSERT_EXPR", "ON_SELECT_INSERT_OUTPUT", "ON_EXPR_FROM", "ON_SET_EXPR", "ON_SET_EXPR_ITEM", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "ANNOTATION", "ANNOTATION_ARRAY", "ANNOTATION_VALUE", "UPDATE_EXPR", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "COMMA", "IDENT", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BOR", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "PLUS", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "QUESTION", "ESCAPECHAR", "TICKED_STRING_LITERAL", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=160;
    public static final int FLOAT_SUFFIX=294;
    public static final int STAR=235;
    public static final int NUMERIC_PARAM_LIST=101;
    public static final int ISTREAM=60;
    public static final int MOD=254;
    public static final int OUTERJOIN_EXPR=143;
    public static final int BSR=276;
    public static final int LIB_FUNCTION=166;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=93;
    public static final int FULL_OUTERJOIN_EXPR=147;
    public static final int RPAREN=234;
    public static final int LNOT=265;
    public static final int INC=269;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=239;
    public static final int BSR_ASSIGN=277;
    public static final int CAST_EXPR=195;
    public static final int STREAM_EXPR=142;
    public static final int TIMEPERIOD_SECONDS=90;
    public static final int NOT_EQUAL=245;
    public static final int METADATASQL=67;
    public static final int EVENT_FILTER_PROPERTY_EXPR=110;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=104;
    public static final int FOLLOWED_BY=258;
    public static final int HOUR_PART=171;
    public static final int RBRACK=237;
    public static final int MATCH_UNTIL_RANGE_CLOSED=211;
    public static final int GE=249;
    public static final int METHOD_JOIN_EXPR=207;
    public static final int ASC=57;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=109;
    public static final int PATTERN_EVERY_DISTINCT_EXPR=108;
    public static final int MINUS_ASSIGN=270;
    public static final int ELSE=30;
    public static final int EVENT_FILTER_NOT_IN=120;
    public static final int NUM_DOUBLE=227;
    public static final int INSERTINTO_STREAM_NAME=183;
    public static final int UNARY_MINUS=167;
    public static final int TIMEPERIOD_MILLISEC=91;
    public static final int LCURLY=255;
    public static final int RETAINUNION=63;
    public static final int DBWHERE_CLAUSE=181;
    public static final int MEDIAN=23;
    public static final int EVENTS=51;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=118;
    public static final int GROUP=44;
    public static final int EMAILAT=285;
    public static final int WS=286;
    public static final int SUBSELECT_GROUP_EXPR=187;
    public static final int ON_SELECT_INSERT_EXPR=201;
    public static final int ESCAPECHAR=260;
    public static final int SL_COMMENT=287;
    public static final int NULL_TYPE=226;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=209;
    public static final int GT=247;
    public static final int BNOT=266;
    public static final int WHERE_EXPR=129;
    public static final int END=33;
    public static final int INNERJOIN_EXPR=144;
    public static final int LAND=283;
    public static final int NOT_REGEXP=178;
    public static final int MATCH_UNTIL_EXPR=208;
    public static final int EVENT_PROP_EXPR=151;
    public static final int LBRACK=236;
    public static final int VIEW_EXPR=126;
    public static final int ANNOTATION=216;
    public static final int LONG_TYPE=221;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=111;
    public static final int TIMEPERIOD_SEC=88;
    public static final int ON_SELECT_EXPR=200;
    public static final int TICKED_STRING_LITERAL=261;
    public static final int MINUTE_PART=172;
    public static final int PATTERN_NOT_EXPR=107;
    public static final int SUM=18;
    public static final int SQL_NE=244;
    public static final int HexDigit=292;
    public static final int UPDATE_EXPR=219;
    public static final int LPAREN=233;
    public static final int AT=81;
    public static final int IN_SUBSELECT_EXPR=189;
    public static final int AS=17;
    public static final int BOOLEAN_TRUE=94;
    public static final int OR_EXPR=11;
    public static final int THEN=32;
    public static final int NOT_IN_RANGE=185;
    public static final int OFFSET=98;
    public static final int AVG=19;
    public static final int LEFT=38;
    public static final int PREVIOUS=68;
    public static final int SECOND_PART=173;
    public static final int IDENT=230;
    public static final int DATABASE_JOIN_EXPR=128;
    public static final int PLUS=251;
    public static final int BXOR=243;
    public static final int CASE2=29;
    public static final int TIMEPERIOD_DAY=82;
    public static final int EXISTS=70;
    public static final int EVENT_PROP_INDEXED=154;
    public static final int TIMEPERIOD_MILLISECOND=92;
    public static final int EVAL_NOTEQUALS_EXPR=135;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=210;
    public static final int CREATE_VARIABLE_EXPR=206;
    public static final int CREATE_WINDOW_COL_TYPE=214;
    public static final int LIKE=8;
    public static final int OUTER=35;
    public static final int BY=43;
    public static final int ARRAY_PARAM_LIST=105;
    public static final int RIGHT_OUTERJOIN_EXPR=146;
    public static final int NUMBERSETSTAR=215;
    public static final int LAST_OPERATOR=192;
    public static final int PATTERN_FILTER_EXPR=106;
    public static final int EVAL_AND_EXPR=132;
    public static final int LEFT_OUTERJOIN_EXPR=145;
    public static final int EPL_EXPR=228;
    public static final int GROUP_BY_EXPR=148;
    public static final int SET=78;
    public static final int RIGHT=39;
    public static final int HAVING=45;
    public static final int INSTANCEOF=73;
    public static final int MIN=21;
    public static final int EVENT_PROP_SIMPLE=152;
    public static final int MINUS=252;
    public static final int SEMI=284;
    public static final int STAR_ASSIGN=272;
    public static final int COLON=238;
    public static final int EVAL_EQUALS_GROUP_EXPR=136;
    public static final int BAND_ASSIGN=282;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=161;
    public static final int VALUE_NULL=96;
    public static final int NOT_IN_SET=175;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=155;
    public static final int SL=278;
    public static final int NOT_IN_SUBSELECT_EXPR=190;
    public static final int WHEN=31;
    public static final int GUARD_EXPR=124;
    public static final int SR=274;
    public static final int RCURLY=256;
    public static final int PLUS_ASSIGN=268;
    public static final int EXISTS_SUBSELECT_EXPR=188;
    public static final int DAY_PART=170;
    public static final int EVENT_FILTER_IN=119;
    public static final int DIV=253;
    public static final int OBJECT_PARAM_ORDERED_EXPR=103;
    public static final int OctalEscape=291;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=174;
    public static final int PRIOR=69;
    public static final int FIRST=52;
    public static final int ROW_LIMIT_EXPR=97;
    public static final int SELECTION_EXPR=139;
    public static final int LOR=250;
    public static final int CAST=74;
    public static final int LW=72;
    public static final int WILDCARD_SELECT=182;
    public static final int EXPONENT=293;
    public static final int LT=246;
    public static final int PATTERN_INCL_EXPR=127;
    public static final int ORDER_BY_EXPR=149;
    public static final int BOOL_TYPE=225;
    public static final int MOD_ASSIGN=273;
    public static final int ANNOTATION_ARRAY=217;
    public static final int CASE=28;
    public static final int IN_SUBSELECT_QUERY_EXPR=191;
    public static final int EQUALS=231;
    public static final int COUNT=26;
    public static final int RETAININTERSECTION=64;
    public static final int DIV_ASSIGN=267;
    public static final int SL_ASSIGN=279;
    public static final int PATTERN=65;
    public static final int SQL=66;
    public static final int WEEKDAY=71;
    public static final int FULL=40;
    public static final int INSERT=54;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=169;
    public static final int LAST=53;
    public static final int BOOLEAN_FALSE=95;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=137;
    public static final int SELECT=27;
    public static final int INTO=55;
    public static final int EVENT_FILTER_BETWEEN=121;
    public static final int COALESCE=22;
    public static final int TIMEPERIOD_SECOND=89;
    public static final int FLOAT_TYPE=222;
    public static final int SUBSELECT_EXPR=186;
    public static final int ANNOTATION_VALUE=218;
    public static final int CONCAT=165;
    public static final int NUMERIC_PARAM_RANGE=100;
    public static final int CLASS_IDENT=123;
    public static final int ON_EXPR=198;
    public static final int CREATE_WINDOW_EXPR=196;
    public static final int PROPERTY_SELECTION_STREAM=113;
    public static final int ON_DELETE_EXPR=199;
    public static final int ON=41;
    public static final int NUM_LONG=262;
    public static final int TIME_PERIOD=168;
    public static final int DOUBLE_TYPE=223;
    public static final int DELETE=76;
    public static final int INT_TYPE=220;
    public static final int EVAL_BITWISE_EXPR=131;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=150;
    public static final int TIMEPERIOD_HOURS=85;
    public static final int VARIABLE=79;
    public static final int SUBSTITUTION=194;
    public static final int UNTIL=80;
    public static final int STRING_TYPE=224;
    public static final int ON_SET_EXPR=204;
    public static final int NUM_INT=257;
    public static final int STDDEV=24;
    public static final int ON_EXPR_FROM=203;
    public static final int NUM_FLOAT=263;
    public static final int FROM=34;
    public static final int DISTINCT=46;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=112;
    public static final int OUTPUT=50;
    public static final int EscapeSequence=289;
    public static final int WEEKDAY_OPERATOR=193;
    public static final int WHERE=16;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=213;
    public static final int DEC=271;
    public static final int INNER=36;
    public static final int NUMERIC_PARAM_FREQUENCY=102;
    public static final int BXOR_ASSIGN=280;
    public static final int ORDER=56;
    public static final int SNAPSHOT=77;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=157;
    public static final int EVENT_FILTER_PARAM=116;
    public static final int IRSTREAM=61;
    public static final int UPDATE=99;
    public static final int MAX=20;
    public static final int TIMEPERIOD_DAYS=83;
    public static final int EVENT_FILTER_RANGE=117;
    public static final int ML_COMMENT=288;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=156;
    public static final int BOR_ASSIGN=281;
    public static final int COMMA=229;
    public static final int WHEN_LIMIT_EXPR=162;
    public static final int IS=42;
    public static final int TIMEPERIOD_LIMIT_EXPR=159;
    public static final int SOME=49;
    public static final int ALL=47;
    public static final int TIMEPERIOD_HOUR=84;
    public static final int BOR=242;
    public static final int EQUAL=264;
    public static final int EVENT_FILTER_NOT_BETWEEN=122;
    public static final int IN_RANGE=184;
    public static final int DOT=232;
    public static final int CURRENT_TIMESTAMP=75;
    public static final int EVERY_DISTINCT_EXPR=15;
    public static final int PROPERTY_WILDCARD_SELECT=114;
    public static final int INSERTINTO_EXPR=163;
    public static final int HAVING_EXPR=130;
    public static final int UNIDIRECTIONAL=62;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=212;
    public static final int EVAL_EQUALS_EXPR=134;
    public static final int TIMEPERIOD_MINUTES=87;
    public static final int RSTREAM=59;
    public static final int NOT_LIKE=177;
    public static final int EVENT_LIMIT_EXPR=158;
    public static final int NOT_BETWEEN=176;
    public static final int TIMEPERIOD_MINUTE=86;
    public static final int EVAL_OR_EXPR=133;
    public static final int ON_SELECT_INSERT_OUTPUT=202;
    public static final int BAND=241;
    public static final int QUOTED_STRING_LITERAL=240;
    public static final int JOIN=37;
    public static final int ANY=48;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=259;
    public static final int OBSERVER_EXPR=125;
    public static final int EVENT_FILTER_IDENT=115;
    public static final int EVENT_PROP_MAPPED=153;
    public static final int UnicodeEscape=290;
    public static final int AVEDEV=25;
    public static final int DBSELECT_EXPR=179;
    public static final int SELECTION_ELEMENT_EXPR=140;
    public static final int CREATE_WINDOW_SELECT_EXPR=197;
    public static final int INSERTINTO_EXPRCOL=164;
    public static final int WINDOW=5;
    public static final int ON_SET_EXPR_ITEM=205;
    public static final int DESC=58;
    public static final int SELECTION_STREAM=141;
    public static final int SR_ASSIGN=275;
    public static final int DBFROM_CLAUSE=180;
    public static final int LE=248;
    public static final int EVAL_IDENT=138;

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
    // EsperEPL2Ast.g:83:1: onExpr : ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ( INSERT )? ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:84:2: ( ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ( INSERT )? ) )
            // EsperEPL2Ast.g:84:4: ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr ( ( onSelectInsertExpr )+ ( onSelectInsertOutput )? )? | onSetExpr ) ( INSERT )? )
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

            // EsperEPL2Ast.g:85:90: ( INSERT )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==INSERT) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:85:90: INSERT
                    {
                    match(input,INSERT,FOLLOW_INSERT_in_onExpr297); 

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
            u=(CommonTree)match(input,UPDATE_EXPR,FOLLOW_UPDATE_EXPR_in_updateExpr319); 

            match(input, Token.DOWN, null); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_updateExpr321); 
            // EsperEPL2Ast.g:90:32: ( IDENT )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==IDENT) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // EsperEPL2Ast.g:90:32: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_updateExpr323); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:90:39: ( onSetAssignment )+
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
            	    // EsperEPL2Ast.g:90:39: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_updateExpr326);
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

            // EsperEPL2Ast.g:90:56: ( whereClause[false] )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==WHERE_EXPR) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:90:56: whereClause[false]
                    {
                    pushFollow(FOLLOW_whereClause_in_updateExpr329);
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
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr347); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr349);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:94:32: ( whereClause[true] )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==WHERE_EXPR) ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:94:33: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr352);
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
            match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr370); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:98:21: ( insertIntoExpr )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==INSERTINTO_EXPR) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:98:21: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr372);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr375);
            selectionList();

            state._fsp--;

            // EsperEPL2Ast.g:98:51: ( onExprFrom )?
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( (LA19_0==ON_EXPR_FROM) ) {
                alt19=1;
            }
            switch (alt19) {
                case 1 :
                    // EsperEPL2Ast.g:98:51: onExprFrom
                    {
                    pushFollow(FOLLOW_onExprFrom_in_onSelectExpr377);
                    onExprFrom();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:63: ( whereClause[true] )?
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( (LA20_0==WHERE_EXPR) ) {
                alt20=1;
            }
            switch (alt20) {
                case 1 :
                    // EsperEPL2Ast.g:98:63: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr380);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:82: ( groupByClause )?
            int alt21=2;
            int LA21_0 = input.LA(1);

            if ( (LA21_0==GROUP_BY_EXPR) ) {
                alt21=1;
            }
            switch (alt21) {
                case 1 :
                    // EsperEPL2Ast.g:98:82: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr384);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:97: ( havingClause )?
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==HAVING_EXPR) ) {
                alt22=1;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:98:97: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr387);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:111: ( orderByClause )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==ORDER_BY_EXPR) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:98:111: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr390);
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
            match(input,ON_SELECT_INSERT_EXPR,FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr407); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_insertIntoExpr_in_onSelectInsertExpr409);
            insertIntoExpr();

            state._fsp--;

            pushFollow(FOLLOW_selectionList_in_onSelectInsertExpr411);
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
                    pushFollow(FOLLOW_whereClause_in_onSelectInsertExpr413);
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
            match(input,ON_SELECT_INSERT_OUTPUT,FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput430); 

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
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr450); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr452);
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
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr455);
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
                    pushFollow(FOLLOW_whereClause_in_onSetExpr459);
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
            match(input,ON_SET_EXPR_ITEM,FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment475); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onSetAssignment477); 
            pushFollow(FOLLOW_valueExpr_in_onSetAssignment479);
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
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom492); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom494); 
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
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom497); 

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
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr515); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr517); 
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
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr520);
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
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr524); 

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
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr527); 

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
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr541);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr544); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:126:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:126:12: ( createColTypeList )
                    // EsperEPL2Ast.g:126:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr573);
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
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr584);
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
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert602); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:133:13: ( valueExpr )?
                int alt34=2;
                int LA34_0 = input.LA(1);

                if ( ((LA34_0>=IN_SET && LA34_0<=REGEXP)||LA34_0==NOT_EXPR||(LA34_0>=SUM && LA34_0<=AVG)||(LA34_0>=COALESCE && LA34_0<=COUNT)||(LA34_0>=CASE && LA34_0<=CASE2)||(LA34_0>=PREVIOUS && LA34_0<=EXISTS)||(LA34_0>=INSTANCEOF && LA34_0<=CURRENT_TIMESTAMP)||(LA34_0>=EVAL_AND_EXPR && LA34_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA34_0==EVENT_PROP_EXPR||(LA34_0>=CONCAT && LA34_0<=LIB_FUNCTION)||LA34_0==ARRAY_EXPR||(LA34_0>=NOT_IN_SET && LA34_0<=NOT_REGEXP)||(LA34_0>=IN_RANGE && LA34_0<=SUBSELECT_EXPR)||(LA34_0>=EXISTS_SUBSELECT_EXPR && LA34_0<=NOT_IN_SUBSELECT_EXPR)||LA34_0==SUBSTITUTION||(LA34_0>=INT_TYPE && LA34_0<=NULL_TYPE)||LA34_0==STAR||(LA34_0>=BAND && LA34_0<=BXOR)||(LA34_0>=LT && LA34_0<=GE)||(LA34_0>=PLUS && LA34_0<=MOD)) ) {
                    alt34=1;
                }
                switch (alt34) {
                    case 1 :
                        // EsperEPL2Ast.g:133:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert604);
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
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList621); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList623);
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
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList626);
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
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList645); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList647);
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
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList650);
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
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement665); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement667); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement669); 

            match(input, Token.UP, null); 

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
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement683); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:150:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement693); 

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
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement713);
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
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement717); 

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
                            pushFollow(FOLLOW_constant_in_createSelectionListElement739);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement742); 

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
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr778); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr780); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr782); 
            // EsperEPL2Ast.g:157:41: ( valueExpr )?
            int alt40=2;
            int LA40_0 = input.LA(1);

            if ( ((LA40_0>=IN_SET && LA40_0<=REGEXP)||LA40_0==NOT_EXPR||(LA40_0>=SUM && LA40_0<=AVG)||(LA40_0>=COALESCE && LA40_0<=COUNT)||(LA40_0>=CASE && LA40_0<=CASE2)||(LA40_0>=PREVIOUS && LA40_0<=EXISTS)||(LA40_0>=INSTANCEOF && LA40_0<=CURRENT_TIMESTAMP)||(LA40_0>=EVAL_AND_EXPR && LA40_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA40_0==EVENT_PROP_EXPR||(LA40_0>=CONCAT && LA40_0<=LIB_FUNCTION)||LA40_0==ARRAY_EXPR||(LA40_0>=NOT_IN_SET && LA40_0<=NOT_REGEXP)||(LA40_0>=IN_RANGE && LA40_0<=SUBSELECT_EXPR)||(LA40_0>=EXISTS_SUBSELECT_EXPR && LA40_0<=NOT_IN_SUBSELECT_EXPR)||LA40_0==SUBSTITUTION||(LA40_0>=INT_TYPE && LA40_0<=NULL_TYPE)||LA40_0==STAR||(LA40_0>=BAND && LA40_0<=BXOR)||(LA40_0>=LT && LA40_0<=GE)||(LA40_0>=PLUS && LA40_0<=MOD)) ) {
                alt40=1;
            }
            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:157:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr785);
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
    // EsperEPL2Ast.g:160:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:161:2: ( ( insertIntoExpr )? selectClause fromClause ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:161:4: ( insertIntoExpr )? selectClause fromClause ( whereClause[true] )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
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
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr803);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr809);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr814);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:164:3: ( whereClause[true] )?
            int alt42=2;
            int LA42_0 = input.LA(1);

            if ( (LA42_0==WHERE_EXPR) ) {
                alt42=1;
            }
            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:164:4: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr819);
                    whereClause(true);

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:165:3: ( groupByClause )?
            int alt43=2;
            int LA43_0 = input.LA(1);

            if ( (LA43_0==GROUP_BY_EXPR) ) {
                alt43=1;
            }
            switch (alt43) {
                case 1 :
                    // EsperEPL2Ast.g:165:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr827);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:166:3: ( havingClause )?
            int alt44=2;
            int LA44_0 = input.LA(1);

            if ( (LA44_0==HAVING_EXPR) ) {
                alt44=1;
            }
            switch (alt44) {
                case 1 :
                    // EsperEPL2Ast.g:166:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr834);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:167:3: ( outputLimitExpr )?
            int alt45=2;
            int LA45_0 = input.LA(1);

            if ( ((LA45_0>=EVENT_LIMIT_EXPR && LA45_0<=CRONTAB_LIMIT_EXPR)||LA45_0==WHEN_LIMIT_EXPR) ) {
                alt45=1;
            }
            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:167:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr841);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:168:3: ( orderByClause )?
            int alt46=2;
            int LA46_0 = input.LA(1);

            if ( (LA46_0==ORDER_BY_EXPR) ) {
                alt46=1;
            }
            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:168:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr848);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:169:3: ( rowLimitClause )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==ROW_LIMIT_EXPR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:169:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr855);
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
    // EsperEPL2Ast.g:172:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:173:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:173:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr872); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:173:24: ( ISTREAM | RSTREAM )?
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr883); 
            // EsperEPL2Ast.g:173:51: ( insertIntoExprCol )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==INSERTINTO_EXPRCOL) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:173:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr886);
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
    // EsperEPL2Ast.g:176:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:177:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:177:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol905); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol907); 
            // EsperEPL2Ast.g:177:31: ( IDENT )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( (LA50_0==IDENT) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // EsperEPL2Ast.g:177:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol910); 

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
    // EsperEPL2Ast.g:180:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:181:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) )
            // EsperEPL2Ast.g:181:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause928); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:181:23: ( RSTREAM | ISTREAM | IRSTREAM )?
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

            pushFollow(FOLLOW_selectionList_in_selectClause943);
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
    // EsperEPL2Ast.g:184:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:185:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:185:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause957);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:185:21: ( streamExpression ( outerJoin )* )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==STREAM_EXPR) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // EsperEPL2Ast.g:185:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause960);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:185:39: ( outerJoin )*
            	    loop52:
            	    do {
            	        int alt52=2;
            	        int LA52_0 = input.LA(1);

            	        if ( ((LA52_0>=INNERJOIN_EXPR && LA52_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt52=1;
            	        }


            	        switch (alt52) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:185:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause963);
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


    // $ANTLR start "selectionList"
    // EsperEPL2Ast.g:188:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:189:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:189:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList980);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:189:25: ( selectionListElement )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=SELECTION_ELEMENT_EXPR && LA54_0<=SELECTION_STREAM)||LA54_0==WILDCARD_SELECT) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // EsperEPL2Ast.g:189:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList983);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop54;
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
    // EsperEPL2Ast.g:192:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:193:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt57=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt57=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt57=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt57=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 57, 0, input);

                throw nvae;
            }

            switch (alt57) {
                case 1 :
                    // EsperEPL2Ast.g:193:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement999); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:194:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1009); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement1011);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:194:41: ( IDENT )?
                    int alt55=2;
                    int LA55_0 = input.LA(1);

                    if ( (LA55_0==IDENT) ) {
                        alt55=1;
                    }
                    switch (alt55) {
                        case 1 :
                            // EsperEPL2Ast.g:194:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1014); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:195:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement1028); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1030); 
                    // EsperEPL2Ast.g:195:31: ( IDENT )?
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==IDENT) ) {
                        alt56=1;
                    }
                    switch (alt56) {
                        case 1 :
                            // EsperEPL2Ast.g:195:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement1033); 

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
    // EsperEPL2Ast.g:198:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:199:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:199:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin1052);
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
    // EsperEPL2Ast.g:202:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:203:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt62=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt62=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt62=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt62=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt62=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:203:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1066); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1068);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1071);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:203:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop58:
                    do {
                        int alt58=2;
                        int LA58_0 = input.LA(1);

                        if ( (LA58_0==EVENT_PROP_EXPR) ) {
                            alt58=1;
                        }


                        switch (alt58) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:203:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1075);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1078);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop58;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:204:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1093); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1095);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1098);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:204:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop59:
                    do {
                        int alt59=2;
                        int LA59_0 = input.LA(1);

                        if ( (LA59_0==EVENT_PROP_EXPR) ) {
                            alt59=1;
                        }


                        switch (alt59) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:204:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1102);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1105);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop59;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:205:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1120); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1122);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1125);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:205:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==EVENT_PROP_EXPR) ) {
                            alt60=1;
                        }


                        switch (alt60) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:205:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1129);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1132);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:206:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1147); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1149);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1152);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:206:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop61:
                    do {
                        int alt61=2;
                        int LA61_0 = input.LA(1);

                        if ( (LA61_0==EVENT_PROP_EXPR) ) {
                            alt61=1;
                        }


                        switch (alt61) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:206:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1156);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent1159);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop61;
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
    // EsperEPL2Ast.g:209:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:210:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:210:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression1180); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:210:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt63=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt63=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt63=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt63=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt63=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }

            switch (alt63) {
                case 1 :
                    // EsperEPL2Ast.g:210:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression1183);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:210:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression1187);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:210:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression1191);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:210:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression1195);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:210:115: ( viewListExpr )?
            int alt64=2;
            int LA64_0 = input.LA(1);

            if ( (LA64_0==VIEW_EXPR) ) {
                alt64=1;
            }
            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:210:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression1199);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:210:131: ( IDENT )?
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( (LA65_0==IDENT) ) {
                alt65=1;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:210:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression1204); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:210:140: ( UNIDIRECTIONAL )?
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==UNIDIRECTIONAL) ) {
                alt66=1;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:210:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1209); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:210:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( ((LA67_0>=RETAINUNION && LA67_0<=RETAININTERSECTION)) ) {
                alt67=1;
            }
            switch (alt67) {
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
    // EsperEPL2Ast.g:213:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:214:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:214:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1237); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:214:27: ( IDENT )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==IDENT) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:214:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1239); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1242); 
            // EsperEPL2Ast.g:214:46: ( propertyExpression )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // EsperEPL2Ast.g:214:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1244);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:214:66: ( valueExpr )*
            loop70:
            do {
                int alt70=2;
                int LA70_0 = input.LA(1);

                if ( ((LA70_0>=IN_SET && LA70_0<=REGEXP)||LA70_0==NOT_EXPR||(LA70_0>=SUM && LA70_0<=AVG)||(LA70_0>=COALESCE && LA70_0<=COUNT)||(LA70_0>=CASE && LA70_0<=CASE2)||(LA70_0>=PREVIOUS && LA70_0<=EXISTS)||(LA70_0>=INSTANCEOF && LA70_0<=CURRENT_TIMESTAMP)||(LA70_0>=EVAL_AND_EXPR && LA70_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA70_0==EVENT_PROP_EXPR||(LA70_0>=CONCAT && LA70_0<=LIB_FUNCTION)||LA70_0==ARRAY_EXPR||(LA70_0>=NOT_IN_SET && LA70_0<=NOT_REGEXP)||(LA70_0>=IN_RANGE && LA70_0<=SUBSELECT_EXPR)||(LA70_0>=EXISTS_SUBSELECT_EXPR && LA70_0<=NOT_IN_SUBSELECT_EXPR)||LA70_0==SUBSTITUTION||(LA70_0>=INT_TYPE && LA70_0<=NULL_TYPE)||LA70_0==STAR||(LA70_0>=BAND && LA70_0<=BXOR)||(LA70_0>=LT && LA70_0<=GE)||(LA70_0>=PLUS && LA70_0<=MOD)) ) {
                    alt70=1;
                }


                switch (alt70) {
            	case 1 :
            	    // EsperEPL2Ast.g:214:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1248);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop70;
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
    // EsperEPL2Ast.g:217:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:218:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:218:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1268); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:218:34: ( propertyExpressionAtom )*
                loop71:
                do {
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt71=1;
                    }


                    switch (alt71) {
                	case 1 :
                	    // EsperEPL2Ast.g:218:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1270);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop71;
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
    // EsperEPL2Ast.g:221:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:222:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:222:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1289); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:222:41: ( propertySelectionListElement )*
            loop72:
            do {
                int alt72=2;
                int LA72_0 = input.LA(1);

                if ( ((LA72_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA72_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt72=1;
                }


                switch (alt72) {
            	case 1 :
            	    // EsperEPL2Ast.g:222:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1291);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop72;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1294);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:222:96: ( IDENT )?
            int alt73=2;
            int LA73_0 = input.LA(1);

            if ( (LA73_0==IDENT) ) {
                alt73=1;
            }
            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:222:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1297); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1301); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:222:116: ( valueExpr )?
                int alt74=2;
                int LA74_0 = input.LA(1);

                if ( ((LA74_0>=IN_SET && LA74_0<=REGEXP)||LA74_0==NOT_EXPR||(LA74_0>=SUM && LA74_0<=AVG)||(LA74_0>=COALESCE && LA74_0<=COUNT)||(LA74_0>=CASE && LA74_0<=CASE2)||(LA74_0>=PREVIOUS && LA74_0<=EXISTS)||(LA74_0>=INSTANCEOF && LA74_0<=CURRENT_TIMESTAMP)||(LA74_0>=EVAL_AND_EXPR && LA74_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA74_0==EVENT_PROP_EXPR||(LA74_0>=CONCAT && LA74_0<=LIB_FUNCTION)||LA74_0==ARRAY_EXPR||(LA74_0>=NOT_IN_SET && LA74_0<=NOT_REGEXP)||(LA74_0>=IN_RANGE && LA74_0<=SUBSELECT_EXPR)||(LA74_0>=EXISTS_SUBSELECT_EXPR && LA74_0<=NOT_IN_SUBSELECT_EXPR)||LA74_0==SUBSTITUTION||(LA74_0>=INT_TYPE && LA74_0<=NULL_TYPE)||LA74_0==STAR||(LA74_0>=BAND && LA74_0<=BXOR)||(LA74_0>=LT && LA74_0<=GE)||(LA74_0>=PLUS && LA74_0<=MOD)) ) {
                    alt74=1;
                }
                switch (alt74) {
                    case 1 :
                        // EsperEPL2Ast.g:222:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1303);
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
    // EsperEPL2Ast.g:225:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:226:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt77=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt77=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt77=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt77=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }

            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:226:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1323); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:227:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1333); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1335);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:227:50: ( IDENT )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==IDENT) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // EsperEPL2Ast.g:227:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1338); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:228:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1352); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1354); 
                    // EsperEPL2Ast.g:228:40: ( IDENT )?
                    int alt76=2;
                    int LA76_0 = input.LA(1);

                    if ( (LA76_0==IDENT) ) {
                        alt76=1;
                    }
                    switch (alt76) {
                        case 1 :
                            // EsperEPL2Ast.g:228:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1357); 

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
    // EsperEPL2Ast.g:231:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:232:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:232:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1378); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1380);
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
    // EsperEPL2Ast.g:235:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:236:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:236:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1397); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1399); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:236:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( ((LA78_0>=STRING_LITERAL && LA78_0<=QUOTED_STRING_LITERAL)) ) {
                alt78=1;
            }
            switch (alt78) {
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
    // EsperEPL2Ast.g:239:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:240:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:240:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1430); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1432); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1434); 
            // EsperEPL2Ast.g:240:41: ( valueExpr )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( ((LA79_0>=IN_SET && LA79_0<=REGEXP)||LA79_0==NOT_EXPR||(LA79_0>=SUM && LA79_0<=AVG)||(LA79_0>=COALESCE && LA79_0<=COUNT)||(LA79_0>=CASE && LA79_0<=CASE2)||(LA79_0>=PREVIOUS && LA79_0<=EXISTS)||(LA79_0>=INSTANCEOF && LA79_0<=CURRENT_TIMESTAMP)||(LA79_0>=EVAL_AND_EXPR && LA79_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA79_0==EVENT_PROP_EXPR||(LA79_0>=CONCAT && LA79_0<=LIB_FUNCTION)||LA79_0==ARRAY_EXPR||(LA79_0>=NOT_IN_SET && LA79_0<=NOT_REGEXP)||(LA79_0>=IN_RANGE && LA79_0<=SUBSELECT_EXPR)||(LA79_0>=EXISTS_SUBSELECT_EXPR && LA79_0<=NOT_IN_SUBSELECT_EXPR)||LA79_0==SUBSTITUTION||(LA79_0>=INT_TYPE && LA79_0<=NULL_TYPE)||LA79_0==STAR||(LA79_0>=BAND && LA79_0<=BXOR)||(LA79_0>=LT && LA79_0<=GE)||(LA79_0>=PLUS && LA79_0<=MOD)) ) {
                    alt79=1;
                }


                switch (alt79) {
            	case 1 :
            	    // EsperEPL2Ast.g:240:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1437);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop79;
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
    // EsperEPL2Ast.g:243:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:244:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:244:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1451);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:244:13: ( viewExpr )*
            loop80:
            do {
                int alt80=2;
                int LA80_0 = input.LA(1);

                if ( (LA80_0==VIEW_EXPR) ) {
                    alt80=1;
                }


                switch (alt80) {
            	case 1 :
            	    // EsperEPL2Ast.g:244:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1454);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop80;
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
    // EsperEPL2Ast.g:247:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:248:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:248:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1471); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1473); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1475); 
            // EsperEPL2Ast.g:248:30: ( valueExprWithTime )*
            loop81:
            do {
                int alt81=2;
                int LA81_0 = input.LA(1);

                if ( ((LA81_0>=IN_SET && LA81_0<=REGEXP)||LA81_0==NOT_EXPR||(LA81_0>=SUM && LA81_0<=AVG)||(LA81_0>=COALESCE && LA81_0<=COUNT)||(LA81_0>=CASE && LA81_0<=CASE2)||LA81_0==LAST||(LA81_0>=PREVIOUS && LA81_0<=EXISTS)||(LA81_0>=LW && LA81_0<=CURRENT_TIMESTAMP)||(LA81_0>=NUMERIC_PARAM_RANGE && LA81_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA81_0>=EVAL_AND_EXPR && LA81_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA81_0==EVENT_PROP_EXPR||(LA81_0>=CONCAT && LA81_0<=LIB_FUNCTION)||(LA81_0>=TIME_PERIOD && LA81_0<=ARRAY_EXPR)||(LA81_0>=NOT_IN_SET && LA81_0<=NOT_REGEXP)||(LA81_0>=IN_RANGE && LA81_0<=SUBSELECT_EXPR)||(LA81_0>=EXISTS_SUBSELECT_EXPR && LA81_0<=NOT_IN_SUBSELECT_EXPR)||(LA81_0>=LAST_OPERATOR && LA81_0<=SUBSTITUTION)||LA81_0==NUMBERSETSTAR||(LA81_0>=INT_TYPE && LA81_0<=NULL_TYPE)||LA81_0==STAR||(LA81_0>=BAND && LA81_0<=BXOR)||(LA81_0>=LT && LA81_0<=GE)||(LA81_0>=PLUS && LA81_0<=MOD)) ) {
                    alt81=1;
                }


                switch (alt81) {
            	case 1 :
            	    // EsperEPL2Ast.g:248:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1478);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop81;
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
    // EsperEPL2Ast.g:251:1: whereClause[boolean isLeaveNode] : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause(boolean isLeaveNode) throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:252:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:252:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1500); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1502);
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
    // EsperEPL2Ast.g:255:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:256:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:256:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1520); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1522);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:256:32: ( valueExpr )*
            loop82:
            do {
                int alt82=2;
                int LA82_0 = input.LA(1);

                if ( ((LA82_0>=IN_SET && LA82_0<=REGEXP)||LA82_0==NOT_EXPR||(LA82_0>=SUM && LA82_0<=AVG)||(LA82_0>=COALESCE && LA82_0<=COUNT)||(LA82_0>=CASE && LA82_0<=CASE2)||(LA82_0>=PREVIOUS && LA82_0<=EXISTS)||(LA82_0>=INSTANCEOF && LA82_0<=CURRENT_TIMESTAMP)||(LA82_0>=EVAL_AND_EXPR && LA82_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA82_0==EVENT_PROP_EXPR||(LA82_0>=CONCAT && LA82_0<=LIB_FUNCTION)||LA82_0==ARRAY_EXPR||(LA82_0>=NOT_IN_SET && LA82_0<=NOT_REGEXP)||(LA82_0>=IN_RANGE && LA82_0<=SUBSELECT_EXPR)||(LA82_0>=EXISTS_SUBSELECT_EXPR && LA82_0<=NOT_IN_SUBSELECT_EXPR)||LA82_0==SUBSTITUTION||(LA82_0>=INT_TYPE && LA82_0<=NULL_TYPE)||LA82_0==STAR||(LA82_0>=BAND && LA82_0<=BXOR)||(LA82_0>=LT && LA82_0<=GE)||(LA82_0>=PLUS && LA82_0<=MOD)) ) {
                    alt82=1;
                }


                switch (alt82) {
            	case 1 :
            	    // EsperEPL2Ast.g:256:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1525);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop82;
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
    // EsperEPL2Ast.g:259:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:260:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:260:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1543); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1545);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:260:35: ( orderByElement )*
            loop83:
            do {
                int alt83=2;
                int LA83_0 = input.LA(1);

                if ( (LA83_0==ORDER_ELEMENT_EXPR) ) {
                    alt83=1;
                }


                switch (alt83) {
            	case 1 :
            	    // EsperEPL2Ast.g:260:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1548);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop83;
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
    // EsperEPL2Ast.g:263:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:264:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:264:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1568); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1570);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:264:38: ( ASC | DESC )?
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( ((LA84_0>=ASC && LA84_0<=DESC)) ) {
                alt84=1;
            }
            switch (alt84) {
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
    // EsperEPL2Ast.g:267:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:268:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:268:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1595); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1597);
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
    // EsperEPL2Ast.g:271:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:272:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt91=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt91=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt91=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt91=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
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
                    // EsperEPL2Ast.g:272:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1615); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:272:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt85=2;
                    int LA85_0 = input.LA(1);

                    if ( (LA85_0==ALL||(LA85_0>=FIRST && LA85_0<=LAST)||LA85_0==SNAPSHOT) ) {
                        alt85=1;
                    }
                    switch (alt85) {
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

                    // EsperEPL2Ast.g:272:52: ( number | IDENT )
                    int alt86=2;
                    int LA86_0 = input.LA(1);

                    if ( ((LA86_0>=INT_TYPE && LA86_0<=DOUBLE_TYPE)) ) {
                        alt86=1;
                    }
                    else if ( (LA86_0==IDENT) ) {
                        alt86=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 86, 0, input);

                        throw nvae;
                    }
                    switch (alt86) {
                        case 1 :
                            // EsperEPL2Ast.g:272:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1629);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:272:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1631); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:273:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1648); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:273:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt87=2;
                    int LA87_0 = input.LA(1);

                    if ( (LA87_0==ALL||(LA87_0>=FIRST && LA87_0<=LAST)||LA87_0==SNAPSHOT) ) {
                        alt87=1;
                    }
                    switch (alt87) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1661);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:274:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1676); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:274:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt88=2;
                    int LA88_0 = input.LA(1);

                    if ( (LA88_0==ALL||(LA88_0>=FIRST && LA88_0<=LAST)||LA88_0==SNAPSHOT) ) {
                        alt88=1;
                    }
                    switch (alt88) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1689);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:275:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1704); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:275:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt89=2;
                    int LA89_0 = input.LA(1);

                    if ( (LA89_0==ALL||(LA89_0>=FIRST && LA89_0<=LAST)||LA89_0==SNAPSHOT) ) {
                        alt89=1;
                    }
                    switch (alt89) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr1717);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:275:67: ( onSetExpr )?
                    int alt90=2;
                    int LA90_0 = input.LA(1);

                    if ( (LA90_0==ON_SET_EXPR) ) {
                        alt90=1;
                    }
                    switch (alt90) {
                        case 1 :
                            // EsperEPL2Ast.g:275:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr1719);
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
    // EsperEPL2Ast.g:278:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:279:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:279:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1738); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:279:23: ( number | IDENT )
            int alt92=2;
            int LA92_0 = input.LA(1);

            if ( ((LA92_0>=INT_TYPE && LA92_0<=DOUBLE_TYPE)) ) {
                alt92=1;
            }
            else if ( (LA92_0==IDENT) ) {
                alt92=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }
            switch (alt92) {
                case 1 :
                    // EsperEPL2Ast.g:279:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1741);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:279:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1743); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:279:38: ( number | IDENT )?
            int alt93=3;
            int LA93_0 = input.LA(1);

            if ( ((LA93_0>=INT_TYPE && LA93_0<=DOUBLE_TYPE)) ) {
                alt93=1;
            }
            else if ( (LA93_0==IDENT) ) {
                alt93=2;
            }
            switch (alt93) {
                case 1 :
                    // EsperEPL2Ast.g:279:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1747);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:279:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1749); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:279:54: ( COMMA )?
            int alt94=2;
            int LA94_0 = input.LA(1);

            if ( (LA94_0==COMMA) ) {
                alt94=1;
            }
            switch (alt94) {
                case 1 :
                    // EsperEPL2Ast.g:279:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause1753); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:279:61: ( OFFSET )?
            int alt95=2;
            int LA95_0 = input.LA(1);

            if ( (LA95_0==OFFSET) ) {
                alt95=1;
            }
            switch (alt95) {
                case 1 :
                    // EsperEPL2Ast.g:279:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause1756); 

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
    // EsperEPL2Ast.g:282:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:283:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:283:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1774); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1776);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1778);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1780);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1782);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1784);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:283:121: ( valueExprWithTime )?
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( ((LA96_0>=IN_SET && LA96_0<=REGEXP)||LA96_0==NOT_EXPR||(LA96_0>=SUM && LA96_0<=AVG)||(LA96_0>=COALESCE && LA96_0<=COUNT)||(LA96_0>=CASE && LA96_0<=CASE2)||LA96_0==LAST||(LA96_0>=PREVIOUS && LA96_0<=EXISTS)||(LA96_0>=LW && LA96_0<=CURRENT_TIMESTAMP)||(LA96_0>=NUMERIC_PARAM_RANGE && LA96_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA96_0>=EVAL_AND_EXPR && LA96_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA96_0==EVENT_PROP_EXPR||(LA96_0>=CONCAT && LA96_0<=LIB_FUNCTION)||(LA96_0>=TIME_PERIOD && LA96_0<=ARRAY_EXPR)||(LA96_0>=NOT_IN_SET && LA96_0<=NOT_REGEXP)||(LA96_0>=IN_RANGE && LA96_0<=SUBSELECT_EXPR)||(LA96_0>=EXISTS_SUBSELECT_EXPR && LA96_0<=NOT_IN_SUBSELECT_EXPR)||(LA96_0>=LAST_OPERATOR && LA96_0<=SUBSTITUTION)||LA96_0==NUMBERSETSTAR||(LA96_0>=INT_TYPE && LA96_0<=NULL_TYPE)||LA96_0==STAR||(LA96_0>=BAND && LA96_0<=BXOR)||(LA96_0>=LT && LA96_0<=GE)||(LA96_0>=PLUS && LA96_0<=MOD)) ) {
                alt96=1;
            }
            switch (alt96) {
                case 1 :
                    // EsperEPL2Ast.g:283:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1786);
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
    // EsperEPL2Ast.g:286:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:287:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt97=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt97=1;
                }
                break;
            case GT:
                {
                alt97=2;
                }
                break;
            case LE:
                {
                alt97=3;
                }
                break;
            case GE:
                {
                alt97=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // EsperEPL2Ast.g:287:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr1803); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1805);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:288:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr1818); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1820);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:289:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr1833); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1835);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:290:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr1847); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1849);
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
    // EsperEPL2Ast.g:293:1: relationalExprValue : ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:294:2: ( ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) ) )
            // EsperEPL2Ast.g:294:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            {
            // EsperEPL2Ast.g:294:4: ( valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) )
            // EsperEPL2Ast.g:295:5: valueExpr ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            {
            pushFollow(FOLLOW_valueExpr_in_relationalExprValue1871);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:296:6: ( valueExpr | ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( ((LA100_0>=IN_SET && LA100_0<=REGEXP)||LA100_0==NOT_EXPR||(LA100_0>=SUM && LA100_0<=AVG)||(LA100_0>=COALESCE && LA100_0<=COUNT)||(LA100_0>=CASE && LA100_0<=CASE2)||(LA100_0>=PREVIOUS && LA100_0<=EXISTS)||(LA100_0>=INSTANCEOF && LA100_0<=CURRENT_TIMESTAMP)||(LA100_0>=EVAL_AND_EXPR && LA100_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA100_0==EVENT_PROP_EXPR||(LA100_0>=CONCAT && LA100_0<=LIB_FUNCTION)||LA100_0==ARRAY_EXPR||(LA100_0>=NOT_IN_SET && LA100_0<=NOT_REGEXP)||(LA100_0>=IN_RANGE && LA100_0<=SUBSELECT_EXPR)||(LA100_0>=EXISTS_SUBSELECT_EXPR && LA100_0<=NOT_IN_SUBSELECT_EXPR)||LA100_0==SUBSTITUTION||(LA100_0>=INT_TYPE && LA100_0<=NULL_TYPE)||LA100_0==STAR||(LA100_0>=BAND && LA100_0<=BXOR)||(LA100_0>=LT && LA100_0<=GE)||(LA100_0>=PLUS && LA100_0<=MOD)) ) {
                alt100=1;
            }
            else if ( ((LA100_0>=ALL && LA100_0<=SOME)) ) {
                alt100=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:296:8: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1881);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:298:6: ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr )
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:298:21: ( ( valueExpr )* | subSelectGroupExpr )
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
                            // EsperEPL2Ast.g:298:22: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:298:22: ( valueExpr )*
                            loop98:
                            do {
                                int alt98=2;
                                int LA98_0 = input.LA(1);

                                if ( ((LA98_0>=IN_SET && LA98_0<=REGEXP)||LA98_0==NOT_EXPR||(LA98_0>=SUM && LA98_0<=AVG)||(LA98_0>=COALESCE && LA98_0<=COUNT)||(LA98_0>=CASE && LA98_0<=CASE2)||(LA98_0>=PREVIOUS && LA98_0<=EXISTS)||(LA98_0>=INSTANCEOF && LA98_0<=CURRENT_TIMESTAMP)||(LA98_0>=EVAL_AND_EXPR && LA98_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA98_0==EVENT_PROP_EXPR||(LA98_0>=CONCAT && LA98_0<=LIB_FUNCTION)||LA98_0==ARRAY_EXPR||(LA98_0>=NOT_IN_SET && LA98_0<=NOT_REGEXP)||(LA98_0>=IN_RANGE && LA98_0<=SUBSELECT_EXPR)||(LA98_0>=EXISTS_SUBSELECT_EXPR && LA98_0<=NOT_IN_SUBSELECT_EXPR)||LA98_0==SUBSTITUTION||(LA98_0>=INT_TYPE && LA98_0<=NULL_TYPE)||LA98_0==STAR||(LA98_0>=BAND && LA98_0<=BXOR)||(LA98_0>=LT && LA98_0<=GE)||(LA98_0>=PLUS && LA98_0<=MOD)) ) {
                                    alt98=1;
                                }


                                switch (alt98) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:298:22: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1905);
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
                            // EsperEPL2Ast.g:298:35: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_relationalExprValue1910);
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
    // EsperEPL2Ast.g:303:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:304:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt107=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt107=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt107=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt107=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt107=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt107=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt107=6;
                }
                break;
            case NOT_EXPR:
                {
                alt107=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt107=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }

            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:304:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1936); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1938);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1940);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:304:42: ( valueExpr )*
                    loop101:
                    do {
                        int alt101=2;
                        int LA101_0 = input.LA(1);

                        if ( ((LA101_0>=IN_SET && LA101_0<=REGEXP)||LA101_0==NOT_EXPR||(LA101_0>=SUM && LA101_0<=AVG)||(LA101_0>=COALESCE && LA101_0<=COUNT)||(LA101_0>=CASE && LA101_0<=CASE2)||(LA101_0>=PREVIOUS && LA101_0<=EXISTS)||(LA101_0>=INSTANCEOF && LA101_0<=CURRENT_TIMESTAMP)||(LA101_0>=EVAL_AND_EXPR && LA101_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA101_0==EVENT_PROP_EXPR||(LA101_0>=CONCAT && LA101_0<=LIB_FUNCTION)||LA101_0==ARRAY_EXPR||(LA101_0>=NOT_IN_SET && LA101_0<=NOT_REGEXP)||(LA101_0>=IN_RANGE && LA101_0<=SUBSELECT_EXPR)||(LA101_0>=EXISTS_SUBSELECT_EXPR && LA101_0<=NOT_IN_SUBSELECT_EXPR)||LA101_0==SUBSTITUTION||(LA101_0>=INT_TYPE && LA101_0<=NULL_TYPE)||LA101_0==STAR||(LA101_0>=BAND && LA101_0<=BXOR)||(LA101_0>=LT && LA101_0<=GE)||(LA101_0>=PLUS && LA101_0<=MOD)) ) {
                            alt101=1;
                        }


                        switch (alt101) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:304:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1943);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop101;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:305:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1957); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1959);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1961);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:305:43: ( valueExpr )*
                    loop102:
                    do {
                        int alt102=2;
                        int LA102_0 = input.LA(1);

                        if ( ((LA102_0>=IN_SET && LA102_0<=REGEXP)||LA102_0==NOT_EXPR||(LA102_0>=SUM && LA102_0<=AVG)||(LA102_0>=COALESCE && LA102_0<=COUNT)||(LA102_0>=CASE && LA102_0<=CASE2)||(LA102_0>=PREVIOUS && LA102_0<=EXISTS)||(LA102_0>=INSTANCEOF && LA102_0<=CURRENT_TIMESTAMP)||(LA102_0>=EVAL_AND_EXPR && LA102_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA102_0==EVENT_PROP_EXPR||(LA102_0>=CONCAT && LA102_0<=LIB_FUNCTION)||LA102_0==ARRAY_EXPR||(LA102_0>=NOT_IN_SET && LA102_0<=NOT_REGEXP)||(LA102_0>=IN_RANGE && LA102_0<=SUBSELECT_EXPR)||(LA102_0>=EXISTS_SUBSELECT_EXPR && LA102_0<=NOT_IN_SUBSELECT_EXPR)||LA102_0==SUBSTITUTION||(LA102_0>=INT_TYPE && LA102_0<=NULL_TYPE)||LA102_0==STAR||(LA102_0>=BAND && LA102_0<=BXOR)||(LA102_0>=LT && LA102_0<=GE)||(LA102_0>=PLUS && LA102_0<=MOD)) ) {
                            alt102=1;
                        }


                        switch (alt102) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:305:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1964);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop102;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:306:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1978); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1980);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1982);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:307:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1994); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1996);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1998);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:308:4: ^(jge= EVAL_EQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2010); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2012);
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

                    // EsperEPL2Ast.g:308:58: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt104=2;
                    int LA104_0 = input.LA(1);

                    if ( (LA104_0==UP||(LA104_0>=IN_SET && LA104_0<=REGEXP)||LA104_0==NOT_EXPR||(LA104_0>=SUM && LA104_0<=AVG)||(LA104_0>=COALESCE && LA104_0<=COUNT)||(LA104_0>=CASE && LA104_0<=CASE2)||(LA104_0>=PREVIOUS && LA104_0<=EXISTS)||(LA104_0>=INSTANCEOF && LA104_0<=CURRENT_TIMESTAMP)||(LA104_0>=EVAL_AND_EXPR && LA104_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA104_0==EVENT_PROP_EXPR||(LA104_0>=CONCAT && LA104_0<=LIB_FUNCTION)||LA104_0==ARRAY_EXPR||(LA104_0>=NOT_IN_SET && LA104_0<=NOT_REGEXP)||(LA104_0>=IN_RANGE && LA104_0<=SUBSELECT_EXPR)||(LA104_0>=EXISTS_SUBSELECT_EXPR && LA104_0<=NOT_IN_SUBSELECT_EXPR)||LA104_0==SUBSTITUTION||(LA104_0>=INT_TYPE && LA104_0<=NULL_TYPE)||LA104_0==STAR||(LA104_0>=BAND && LA104_0<=BXOR)||(LA104_0>=LT && LA104_0<=GE)||(LA104_0>=PLUS && LA104_0<=MOD)) ) {
                        alt104=1;
                    }
                    else if ( (LA104_0==SUBSELECT_GROUP_EXPR) ) {
                        alt104=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 104, 0, input);

                        throw nvae;
                    }
                    switch (alt104) {
                        case 1 :
                            // EsperEPL2Ast.g:308:59: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:308:59: ( valueExpr )*
                            loop103:
                            do {
                                int alt103=2;
                                int LA103_0 = input.LA(1);

                                if ( ((LA103_0>=IN_SET && LA103_0<=REGEXP)||LA103_0==NOT_EXPR||(LA103_0>=SUM && LA103_0<=AVG)||(LA103_0>=COALESCE && LA103_0<=COUNT)||(LA103_0>=CASE && LA103_0<=CASE2)||(LA103_0>=PREVIOUS && LA103_0<=EXISTS)||(LA103_0>=INSTANCEOF && LA103_0<=CURRENT_TIMESTAMP)||(LA103_0>=EVAL_AND_EXPR && LA103_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA103_0==EVENT_PROP_EXPR||(LA103_0>=CONCAT && LA103_0<=LIB_FUNCTION)||LA103_0==ARRAY_EXPR||(LA103_0>=NOT_IN_SET && LA103_0<=NOT_REGEXP)||(LA103_0>=IN_RANGE && LA103_0<=SUBSELECT_EXPR)||(LA103_0>=EXISTS_SUBSELECT_EXPR && LA103_0<=NOT_IN_SUBSELECT_EXPR)||LA103_0==SUBSTITUTION||(LA103_0>=INT_TYPE && LA103_0<=NULL_TYPE)||LA103_0==STAR||(LA103_0>=BAND && LA103_0<=BXOR)||(LA103_0>=LT && LA103_0<=GE)||(LA103_0>=PLUS && LA103_0<=MOD)) ) {
                                    alt103=1;
                                }


                                switch (alt103) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:308:59: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2023);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop103;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:308:72: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2028);
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
                    // EsperEPL2Ast.g:309:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR valueExpr ( ANY | SOME | ALL ) ( ( valueExpr )* | subSelectGroupExpr ) )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2041); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2043);
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

                    // EsperEPL2Ast.g:309:62: ( ( valueExpr )* | subSelectGroupExpr )
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==UP||(LA106_0>=IN_SET && LA106_0<=REGEXP)||LA106_0==NOT_EXPR||(LA106_0>=SUM && LA106_0<=AVG)||(LA106_0>=COALESCE && LA106_0<=COUNT)||(LA106_0>=CASE && LA106_0<=CASE2)||(LA106_0>=PREVIOUS && LA106_0<=EXISTS)||(LA106_0>=INSTANCEOF && LA106_0<=CURRENT_TIMESTAMP)||(LA106_0>=EVAL_AND_EXPR && LA106_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA106_0==EVENT_PROP_EXPR||(LA106_0>=CONCAT && LA106_0<=LIB_FUNCTION)||LA106_0==ARRAY_EXPR||(LA106_0>=NOT_IN_SET && LA106_0<=NOT_REGEXP)||(LA106_0>=IN_RANGE && LA106_0<=SUBSELECT_EXPR)||(LA106_0>=EXISTS_SUBSELECT_EXPR && LA106_0<=NOT_IN_SUBSELECT_EXPR)||LA106_0==SUBSTITUTION||(LA106_0>=INT_TYPE && LA106_0<=NULL_TYPE)||LA106_0==STAR||(LA106_0>=BAND && LA106_0<=BXOR)||(LA106_0>=LT && LA106_0<=GE)||(LA106_0>=PLUS && LA106_0<=MOD)) ) {
                        alt106=1;
                    }
                    else if ( (LA106_0==SUBSELECT_GROUP_EXPR) ) {
                        alt106=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 106, 0, input);

                        throw nvae;
                    }
                    switch (alt106) {
                        case 1 :
                            // EsperEPL2Ast.g:309:63: ( valueExpr )*
                            {
                            // EsperEPL2Ast.g:309:63: ( valueExpr )*
                            loop105:
                            do {
                                int alt105=2;
                                int LA105_0 = input.LA(1);

                                if ( ((LA105_0>=IN_SET && LA105_0<=REGEXP)||LA105_0==NOT_EXPR||(LA105_0>=SUM && LA105_0<=AVG)||(LA105_0>=COALESCE && LA105_0<=COUNT)||(LA105_0>=CASE && LA105_0<=CASE2)||(LA105_0>=PREVIOUS && LA105_0<=EXISTS)||(LA105_0>=INSTANCEOF && LA105_0<=CURRENT_TIMESTAMP)||(LA105_0>=EVAL_AND_EXPR && LA105_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA105_0==EVENT_PROP_EXPR||(LA105_0>=CONCAT && LA105_0<=LIB_FUNCTION)||LA105_0==ARRAY_EXPR||(LA105_0>=NOT_IN_SET && LA105_0<=NOT_REGEXP)||(LA105_0>=IN_RANGE && LA105_0<=SUBSELECT_EXPR)||(LA105_0>=EXISTS_SUBSELECT_EXPR && LA105_0<=NOT_IN_SUBSELECT_EXPR)||LA105_0==SUBSTITUTION||(LA105_0>=INT_TYPE && LA105_0<=NULL_TYPE)||LA105_0==STAR||(LA105_0>=BAND && LA105_0<=BXOR)||(LA105_0>=LT && LA105_0<=GE)||(LA105_0>=PLUS && LA105_0<=MOD)) ) {
                                    alt105=1;
                                }


                                switch (alt105) {
                            	case 1 :
                            	    // EsperEPL2Ast.g:309:63: valueExpr
                            	    {
                            	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2054);
                            	    valueExpr();

                            	    state._fsp--;


                            	    }
                            	    break;

                            	default :
                            	    break loop105;
                                }
                            } while (true);


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:309:76: subSelectGroupExpr
                            {
                            pushFollow(FOLLOW_subSelectGroupExpr_in_evalExprChoice2059);
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
                    // EsperEPL2Ast.g:310:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice2072); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice2074);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:311:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice2085);
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
    // EsperEPL2Ast.g:314:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:315:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt108=16;
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
            case SUBSTITUTION:
                {
                alt108=2;
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
                alt108=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt108=4;
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
                alt108=5;
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
                alt108=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt108=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt108=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt108=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt108=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt108=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt108=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt108=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt108=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt108=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt108=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 108, 0, input);

                throw nvae;
            }

            switch (alt108) {
                case 1 :
                    // EsperEPL2Ast.g:315:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr2098);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:316:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr2104);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:317:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr2110);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:318:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr2117);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:319:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr2126);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:320:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr2131);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:321:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr2139);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:322:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr2144);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:323:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr2149);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:324:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr2155);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:325:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr2160);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:326:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr2165);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:327:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr2170);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:328:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr2175);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:329:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr2181);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:330:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr2188);
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
    // EsperEPL2Ast.g:333:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:334:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt110=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt110=1;
                }
                break;
            case LW:
                {
                alt110=2;
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
                alt110=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt110=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt110=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt110=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt110=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt110=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt110=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt110=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt110=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }

            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:334:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime2201); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:335:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime2210); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:336:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2217);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:337:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2225); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime2227);
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
                    // EsperEPL2Ast.g:338:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime2242);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:339:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2248);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:340:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2253);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:341:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2258);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:342:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2268); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:342:29: ( numericParameterList )+
                    int cnt109=0;
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==NUMERIC_PARAM_RANGE||LA109_0==NUMERIC_PARAM_FREQUENCY||(LA109_0>=INT_TYPE && LA109_0<=NULL_TYPE)) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:342:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2270);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt109 >= 1 ) break loop109;
                                EarlyExitException eee =
                                    new EarlyExitException(109, input);
                                throw eee;
                        }
                        cnt109++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:343:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2281); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:344:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2288);
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
    // EsperEPL2Ast.g:347:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:348:2: ( constant[true] | rangeOperator | frequencyOperator )
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
            case NUMERIC_PARAM_RANGE:
                {
                alt111=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:348:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2301);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:349:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2308);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:350:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2314);
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
    // EsperEPL2Ast.g:353:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:354:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:354:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2330); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:354:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt112=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt112=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt112=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt112=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }

            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:354:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2333);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:354:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2336);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:354:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2339);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:354:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt113=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt113=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt113=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt113=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:354:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2343);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:354:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2346);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:354:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2349);
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
    // EsperEPL2Ast.g:357:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:358:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:358:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2370); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:358:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt114=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt114=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt114=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:358:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2373);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:358:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2376);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:358:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2379);
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
    // EsperEPL2Ast.g:361:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:362:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:362:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2398); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:362:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt115=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt115=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt115=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt115=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }

            switch (alt115) {
                case 1 :
                    // EsperEPL2Ast.g:362:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2401);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:362:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2404);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:362:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2407);
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
    // EsperEPL2Ast.g:365:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:366:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:366:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2426); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:366:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt116=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt116=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt116=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:366:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2429);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:366:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2432);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:366:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2435);
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
    // EsperEPL2Ast.g:369:1: subSelectGroupExpr : ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) ;
    public final void subSelectGroupExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:370:2: ( ^(s= SUBSELECT_GROUP_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:370:4: ^(s= SUBSELECT_GROUP_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_GROUP_EXPR,FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2456); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectGroupExpr2458);
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
    // EsperEPL2Ast.g:373:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:374:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:374:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2477); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2479);
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
    // EsperEPL2Ast.g:377:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:378:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:378:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2498); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2500);
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
    // EsperEPL2Ast.g:381:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:382:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt117=2;
            int LA117_0 = input.LA(1);

            if ( (LA117_0==IN_SUBSELECT_EXPR) ) {
                alt117=1;
            }
            else if ( (LA117_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt117=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 117, 0, input);

                throw nvae;
            }
            switch (alt117) {
                case 1 :
                    // EsperEPL2Ast.g:382:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2519); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2521);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2523);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:383:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2535); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2537);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2539);
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
    // EsperEPL2Ast.g:386:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:387:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:387:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2558); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2560);
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
    // EsperEPL2Ast.g:390:1: subQueryExpr : selectionListElement subSelectFilterExpr ( whereClause[true] )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:391:2: ( selectionListElement subSelectFilterExpr ( whereClause[true] )? )
            // EsperEPL2Ast.g:391:4: selectionListElement subSelectFilterExpr ( whereClause[true] )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2576);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2578);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:391:45: ( whereClause[true] )?
            int alt118=2;
            int LA118_0 = input.LA(1);

            if ( (LA118_0==WHERE_EXPR) ) {
                alt118=1;
            }
            switch (alt118) {
                case 1 :
                    // EsperEPL2Ast.g:391:46: whereClause[true]
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2581);
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
    // EsperEPL2Ast.g:394:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:395:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:395:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2599); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2601);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:395:36: ( viewListExpr )?
            int alt119=2;
            int LA119_0 = input.LA(1);

            if ( (LA119_0==VIEW_EXPR) ) {
                alt119=1;
            }
            switch (alt119) {
                case 1 :
                    // EsperEPL2Ast.g:395:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2604);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:395:52: ( IDENT )?
            int alt120=2;
            int LA120_0 = input.LA(1);

            if ( (LA120_0==IDENT) ) {
                alt120=1;
            }
            switch (alt120) {
                case 1 :
                    // EsperEPL2Ast.g:395:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2609); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:395:61: ( RETAINUNION )?
            int alt121=2;
            int LA121_0 = input.LA(1);

            if ( (LA121_0==RETAINUNION) ) {
                alt121=1;
            }
            switch (alt121) {
                case 1 :
                    // EsperEPL2Ast.g:395:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr2613); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:395:74: ( RETAININTERSECTION )?
            int alt122=2;
            int LA122_0 = input.LA(1);

            if ( (LA122_0==RETAININTERSECTION) ) {
                alt122=1;
            }
            switch (alt122) {
                case 1 :
                    // EsperEPL2Ast.g:395:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2616); 

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
    // EsperEPL2Ast.g:398:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:399:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt125=2;
            int LA125_0 = input.LA(1);

            if ( (LA125_0==CASE) ) {
                alt125=1;
            }
            else if ( (LA125_0==CASE2) ) {
                alt125=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 125, 0, input);

                throw nvae;
            }
            switch (alt125) {
                case 1 :
                    // EsperEPL2Ast.g:399:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2636); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:399:13: ( valueExpr )*
                        loop123:
                        do {
                            int alt123=2;
                            int LA123_0 = input.LA(1);

                            if ( ((LA123_0>=IN_SET && LA123_0<=REGEXP)||LA123_0==NOT_EXPR||(LA123_0>=SUM && LA123_0<=AVG)||(LA123_0>=COALESCE && LA123_0<=COUNT)||(LA123_0>=CASE && LA123_0<=CASE2)||(LA123_0>=PREVIOUS && LA123_0<=EXISTS)||(LA123_0>=INSTANCEOF && LA123_0<=CURRENT_TIMESTAMP)||(LA123_0>=EVAL_AND_EXPR && LA123_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA123_0==EVENT_PROP_EXPR||(LA123_0>=CONCAT && LA123_0<=LIB_FUNCTION)||LA123_0==ARRAY_EXPR||(LA123_0>=NOT_IN_SET && LA123_0<=NOT_REGEXP)||(LA123_0>=IN_RANGE && LA123_0<=SUBSELECT_EXPR)||(LA123_0>=EXISTS_SUBSELECT_EXPR && LA123_0<=NOT_IN_SUBSELECT_EXPR)||LA123_0==SUBSTITUTION||(LA123_0>=INT_TYPE && LA123_0<=NULL_TYPE)||LA123_0==STAR||(LA123_0>=BAND && LA123_0<=BXOR)||(LA123_0>=LT && LA123_0<=GE)||(LA123_0>=PLUS && LA123_0<=MOD)) ) {
                                alt123=1;
                            }


                            switch (alt123) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:399:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2639);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop123;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:400:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2652); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:400:14: ( valueExpr )*
                        loop124:
                        do {
                            int alt124=2;
                            int LA124_0 = input.LA(1);

                            if ( ((LA124_0>=IN_SET && LA124_0<=REGEXP)||LA124_0==NOT_EXPR||(LA124_0>=SUM && LA124_0<=AVG)||(LA124_0>=COALESCE && LA124_0<=COUNT)||(LA124_0>=CASE && LA124_0<=CASE2)||(LA124_0>=PREVIOUS && LA124_0<=EXISTS)||(LA124_0>=INSTANCEOF && LA124_0<=CURRENT_TIMESTAMP)||(LA124_0>=EVAL_AND_EXPR && LA124_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA124_0==EVENT_PROP_EXPR||(LA124_0>=CONCAT && LA124_0<=LIB_FUNCTION)||LA124_0==ARRAY_EXPR||(LA124_0>=NOT_IN_SET && LA124_0<=NOT_REGEXP)||(LA124_0>=IN_RANGE && LA124_0<=SUBSELECT_EXPR)||(LA124_0>=EXISTS_SUBSELECT_EXPR && LA124_0<=NOT_IN_SUBSELECT_EXPR)||LA124_0==SUBSTITUTION||(LA124_0>=INT_TYPE && LA124_0<=NULL_TYPE)||LA124_0==STAR||(LA124_0>=BAND && LA124_0<=BXOR)||(LA124_0>=LT && LA124_0<=GE)||(LA124_0>=PLUS && LA124_0<=MOD)) ) {
                                alt124=1;
                            }


                            switch (alt124) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:400:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2655);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop124;
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
    // EsperEPL2Ast.g:403:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:404:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt128=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt128=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt128=2;
                }
                break;
            case IN_RANGE:
                {
                alt128=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt128=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 128, 0, input);

                throw nvae;
            }

            switch (alt128) {
                case 1 :
                    // EsperEPL2Ast.g:404:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2675); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2677);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2685);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:404:51: ( valueExpr )*
                    loop126:
                    do {
                        int alt126=2;
                        int LA126_0 = input.LA(1);

                        if ( ((LA126_0>=IN_SET && LA126_0<=REGEXP)||LA126_0==NOT_EXPR||(LA126_0>=SUM && LA126_0<=AVG)||(LA126_0>=COALESCE && LA126_0<=COUNT)||(LA126_0>=CASE && LA126_0<=CASE2)||(LA126_0>=PREVIOUS && LA126_0<=EXISTS)||(LA126_0>=INSTANCEOF && LA126_0<=CURRENT_TIMESTAMP)||(LA126_0>=EVAL_AND_EXPR && LA126_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA126_0==EVENT_PROP_EXPR||(LA126_0>=CONCAT && LA126_0<=LIB_FUNCTION)||LA126_0==ARRAY_EXPR||(LA126_0>=NOT_IN_SET && LA126_0<=NOT_REGEXP)||(LA126_0>=IN_RANGE && LA126_0<=SUBSELECT_EXPR)||(LA126_0>=EXISTS_SUBSELECT_EXPR && LA126_0<=NOT_IN_SUBSELECT_EXPR)||LA126_0==SUBSTITUTION||(LA126_0>=INT_TYPE && LA126_0<=NULL_TYPE)||LA126_0==STAR||(LA126_0>=BAND && LA126_0<=BXOR)||(LA126_0>=LT && LA126_0<=GE)||(LA126_0>=PLUS && LA126_0<=MOD)) ) {
                            alt126=1;
                        }


                        switch (alt126) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:404:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2688);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop126;
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
                    // EsperEPL2Ast.g:405:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr2707); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2709);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2717);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:405:55: ( valueExpr )*
                    loop127:
                    do {
                        int alt127=2;
                        int LA127_0 = input.LA(1);

                        if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA127_0==EVENT_PROP_EXPR||(LA127_0>=CONCAT && LA127_0<=LIB_FUNCTION)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=SUBSELECT_EXPR)||(LA127_0>=EXISTS_SUBSELECT_EXPR && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||LA127_0==STAR||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=PLUS && LA127_0<=MOD)) ) {
                            alt127=1;
                        }


                        switch (alt127) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:405:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2720);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop127;
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
                    // EsperEPL2Ast.g:406:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr2739); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2741);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2749);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2751);
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
                    // EsperEPL2Ast.g:407:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr2768); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2770);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2778);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2780);
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
    // EsperEPL2Ast.g:410:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:411:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==BETWEEN) ) {
                alt130=1;
            }
            else if ( (LA130_0==NOT_BETWEEN) ) {
                alt130=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 130, 0, input);

                throw nvae;
            }
            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:411:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr2805); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2807);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2809);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2811);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:412:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr2822); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2824);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2826);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:412:40: ( valueExpr )*
                    loop129:
                    do {
                        int alt129=2;
                        int LA129_0 = input.LA(1);

                        if ( ((LA129_0>=IN_SET && LA129_0<=REGEXP)||LA129_0==NOT_EXPR||(LA129_0>=SUM && LA129_0<=AVG)||(LA129_0>=COALESCE && LA129_0<=COUNT)||(LA129_0>=CASE && LA129_0<=CASE2)||(LA129_0>=PREVIOUS && LA129_0<=EXISTS)||(LA129_0>=INSTANCEOF && LA129_0<=CURRENT_TIMESTAMP)||(LA129_0>=EVAL_AND_EXPR && LA129_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA129_0==EVENT_PROP_EXPR||(LA129_0>=CONCAT && LA129_0<=LIB_FUNCTION)||LA129_0==ARRAY_EXPR||(LA129_0>=NOT_IN_SET && LA129_0<=NOT_REGEXP)||(LA129_0>=IN_RANGE && LA129_0<=SUBSELECT_EXPR)||(LA129_0>=EXISTS_SUBSELECT_EXPR && LA129_0<=NOT_IN_SUBSELECT_EXPR)||LA129_0==SUBSTITUTION||(LA129_0>=INT_TYPE && LA129_0<=NULL_TYPE)||LA129_0==STAR||(LA129_0>=BAND && LA129_0<=BXOR)||(LA129_0>=LT && LA129_0<=GE)||(LA129_0>=PLUS && LA129_0<=MOD)) ) {
                            alt129=1;
                        }


                        switch (alt129) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:412:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr2829);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop129;
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
    // EsperEPL2Ast.g:415:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:416:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt133=2;
            int LA133_0 = input.LA(1);

            if ( (LA133_0==LIKE) ) {
                alt133=1;
            }
            else if ( (LA133_0==NOT_LIKE) ) {
                alt133=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 133, 0, input);

                throw nvae;
            }
            switch (alt133) {
                case 1 :
                    // EsperEPL2Ast.g:416:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr2849); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2851);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2853);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:416:33: ( valueExpr )?
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( ((LA131_0>=IN_SET && LA131_0<=REGEXP)||LA131_0==NOT_EXPR||(LA131_0>=SUM && LA131_0<=AVG)||(LA131_0>=COALESCE && LA131_0<=COUNT)||(LA131_0>=CASE && LA131_0<=CASE2)||(LA131_0>=PREVIOUS && LA131_0<=EXISTS)||(LA131_0>=INSTANCEOF && LA131_0<=CURRENT_TIMESTAMP)||(LA131_0>=EVAL_AND_EXPR && LA131_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA131_0==EVENT_PROP_EXPR||(LA131_0>=CONCAT && LA131_0<=LIB_FUNCTION)||LA131_0==ARRAY_EXPR||(LA131_0>=NOT_IN_SET && LA131_0<=NOT_REGEXP)||(LA131_0>=IN_RANGE && LA131_0<=SUBSELECT_EXPR)||(LA131_0>=EXISTS_SUBSELECT_EXPR && LA131_0<=NOT_IN_SUBSELECT_EXPR)||LA131_0==SUBSTITUTION||(LA131_0>=INT_TYPE && LA131_0<=NULL_TYPE)||LA131_0==STAR||(LA131_0>=BAND && LA131_0<=BXOR)||(LA131_0>=LT && LA131_0<=GE)||(LA131_0>=PLUS && LA131_0<=MOD)) ) {
                        alt131=1;
                    }
                    switch (alt131) {
                        case 1 :
                            // EsperEPL2Ast.g:416:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2856);
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
                    // EsperEPL2Ast.g:417:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr2869); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2871);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2873);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:417:37: ( valueExpr )?
                    int alt132=2;
                    int LA132_0 = input.LA(1);

                    if ( ((LA132_0>=IN_SET && LA132_0<=REGEXP)||LA132_0==NOT_EXPR||(LA132_0>=SUM && LA132_0<=AVG)||(LA132_0>=COALESCE && LA132_0<=COUNT)||(LA132_0>=CASE && LA132_0<=CASE2)||(LA132_0>=PREVIOUS && LA132_0<=EXISTS)||(LA132_0>=INSTANCEOF && LA132_0<=CURRENT_TIMESTAMP)||(LA132_0>=EVAL_AND_EXPR && LA132_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA132_0==EVENT_PROP_EXPR||(LA132_0>=CONCAT && LA132_0<=LIB_FUNCTION)||LA132_0==ARRAY_EXPR||(LA132_0>=NOT_IN_SET && LA132_0<=NOT_REGEXP)||(LA132_0>=IN_RANGE && LA132_0<=SUBSELECT_EXPR)||(LA132_0>=EXISTS_SUBSELECT_EXPR && LA132_0<=NOT_IN_SUBSELECT_EXPR)||LA132_0==SUBSTITUTION||(LA132_0>=INT_TYPE && LA132_0<=NULL_TYPE)||LA132_0==STAR||(LA132_0>=BAND && LA132_0<=BXOR)||(LA132_0>=LT && LA132_0<=GE)||(LA132_0>=PLUS && LA132_0<=MOD)) ) {
                        alt132=1;
                    }
                    switch (alt132) {
                        case 1 :
                            // EsperEPL2Ast.g:417:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2876);
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
    // EsperEPL2Ast.g:420:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:421:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt134=2;
            int LA134_0 = input.LA(1);

            if ( (LA134_0==REGEXP) ) {
                alt134=1;
            }
            else if ( (LA134_0==NOT_REGEXP) ) {
                alt134=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 134, 0, input);

                throw nvae;
            }
            switch (alt134) {
                case 1 :
                    // EsperEPL2Ast.g:421:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr2895); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2897);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2899);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:422:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr2910); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2912);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2914);
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
    // EsperEPL2Ast.g:425:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr[true] ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:426:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr[true] ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt144=13;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt144=1;
                }
                break;
            case AVG:
                {
                alt144=2;
                }
                break;
            case COUNT:
                {
                alt144=3;
                }
                break;
            case MEDIAN:
                {
                alt144=4;
                }
                break;
            case STDDEV:
                {
                alt144=5;
                }
                break;
            case AVEDEV:
                {
                alt144=6;
                }
                break;
            case COALESCE:
                {
                alt144=7;
                }
                break;
            case PREVIOUS:
                {
                alt144=8;
                }
                break;
            case PRIOR:
                {
                alt144=9;
                }
                break;
            case INSTANCEOF:
                {
                alt144=10;
                }
                break;
            case CAST:
                {
                alt144=11;
                }
                break;
            case EXISTS:
                {
                alt144=12;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt144=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }

            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:426:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc2933); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:426:13: ( DISTINCT )?
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( (LA135_0==DISTINCT) ) {
                        alt135=1;
                    }
                    switch (alt135) {
                        case 1 :
                            // EsperEPL2Ast.g:426:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2936); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2940);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:427:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc2951); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:427:12: ( DISTINCT )?
                    int alt136=2;
                    int LA136_0 = input.LA(1);

                    if ( (LA136_0==DISTINCT) ) {
                        alt136=1;
                    }
                    switch (alt136) {
                        case 1 :
                            // EsperEPL2Ast.g:427:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2954); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2958);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:428:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc2969); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:428:14: ( ( DISTINCT )? valueExpr )?
                        int alt138=2;
                        int LA138_0 = input.LA(1);

                        if ( ((LA138_0>=IN_SET && LA138_0<=REGEXP)||LA138_0==NOT_EXPR||(LA138_0>=SUM && LA138_0<=AVG)||(LA138_0>=COALESCE && LA138_0<=COUNT)||(LA138_0>=CASE && LA138_0<=CASE2)||LA138_0==DISTINCT||(LA138_0>=PREVIOUS && LA138_0<=EXISTS)||(LA138_0>=INSTANCEOF && LA138_0<=CURRENT_TIMESTAMP)||(LA138_0>=EVAL_AND_EXPR && LA138_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA138_0==EVENT_PROP_EXPR||(LA138_0>=CONCAT && LA138_0<=LIB_FUNCTION)||LA138_0==ARRAY_EXPR||(LA138_0>=NOT_IN_SET && LA138_0<=NOT_REGEXP)||(LA138_0>=IN_RANGE && LA138_0<=SUBSELECT_EXPR)||(LA138_0>=EXISTS_SUBSELECT_EXPR && LA138_0<=NOT_IN_SUBSELECT_EXPR)||LA138_0==SUBSTITUTION||(LA138_0>=INT_TYPE && LA138_0<=NULL_TYPE)||LA138_0==STAR||(LA138_0>=BAND && LA138_0<=BXOR)||(LA138_0>=LT && LA138_0<=GE)||(LA138_0>=PLUS && LA138_0<=MOD)) ) {
                            alt138=1;
                        }
                        switch (alt138) {
                            case 1 :
                                // EsperEPL2Ast.g:428:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:428:15: ( DISTINCT )?
                                int alt137=2;
                                int LA137_0 = input.LA(1);

                                if ( (LA137_0==DISTINCT) ) {
                                    alt137=1;
                                }
                                switch (alt137) {
                                    case 1 :
                                        // EsperEPL2Ast.g:428:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2973); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc2977);
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
                    // EsperEPL2Ast.g:429:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc2991); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:429:15: ( DISTINCT )?
                    int alt139=2;
                    int LA139_0 = input.LA(1);

                    if ( (LA139_0==DISTINCT) ) {
                        alt139=1;
                    }
                    switch (alt139) {
                        case 1 :
                            // EsperEPL2Ast.g:429:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2994); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2998);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:430:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc3009); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:430:15: ( DISTINCT )?
                    int alt140=2;
                    int LA140_0 = input.LA(1);

                    if ( (LA140_0==DISTINCT) ) {
                        alt140=1;
                    }
                    switch (alt140) {
                        case 1 :
                            // EsperEPL2Ast.g:430:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3012); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3016);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:431:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc3027); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:431:15: ( DISTINCT )?
                    int alt141=2;
                    int LA141_0 = input.LA(1);

                    if ( (LA141_0==DISTINCT) ) {
                        alt141=1;
                    }
                    switch (alt141) {
                        case 1 :
                            // EsperEPL2Ast.g:431:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc3030); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3034);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:432:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc3046); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3048);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3050);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:432:38: ( valueExpr )*
                    loop142:
                    do {
                        int alt142=2;
                        int LA142_0 = input.LA(1);

                        if ( ((LA142_0>=IN_SET && LA142_0<=REGEXP)||LA142_0==NOT_EXPR||(LA142_0>=SUM && LA142_0<=AVG)||(LA142_0>=COALESCE && LA142_0<=COUNT)||(LA142_0>=CASE && LA142_0<=CASE2)||(LA142_0>=PREVIOUS && LA142_0<=EXISTS)||(LA142_0>=INSTANCEOF && LA142_0<=CURRENT_TIMESTAMP)||(LA142_0>=EVAL_AND_EXPR && LA142_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA142_0==EVENT_PROP_EXPR||(LA142_0>=CONCAT && LA142_0<=LIB_FUNCTION)||LA142_0==ARRAY_EXPR||(LA142_0>=NOT_IN_SET && LA142_0<=NOT_REGEXP)||(LA142_0>=IN_RANGE && LA142_0<=SUBSELECT_EXPR)||(LA142_0>=EXISTS_SUBSELECT_EXPR && LA142_0<=NOT_IN_SUBSELECT_EXPR)||LA142_0==SUBSTITUTION||(LA142_0>=INT_TYPE && LA142_0<=NULL_TYPE)||LA142_0==STAR||(LA142_0>=BAND && LA142_0<=BXOR)||(LA142_0>=LT && LA142_0<=GE)||(LA142_0>=PLUS && LA142_0<=MOD)) ) {
                            alt142=1;
                        }


                        switch (alt142) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:432:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc3053);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop142;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:433:5: ^(f= PREVIOUS valueExpr eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc3068); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3070);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3072);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:434:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc3085); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc3089); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3091);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:435:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc3104); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3106);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3108); 
                    // EsperEPL2Ast.g:435:42: ( CLASS_IDENT )*
                    loop143:
                    do {
                        int alt143=2;
                        int LA143_0 = input.LA(1);

                        if ( (LA143_0==CLASS_IDENT) ) {
                            alt143=1;
                        }


                        switch (alt143) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:435:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3111); 

                    	    }
                    	    break;

                    	default :
                    	    break loop143;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:436:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc3125); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc3127);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc3129); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:437:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc3141); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc3143);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:438:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3155); 



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
    // EsperEPL2Ast.g:441:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:442:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:442:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr3175); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:442:19: ( valueExpr )*
                loop145:
                do {
                    int alt145=2;
                    int LA145_0 = input.LA(1);

                    if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||(LA145_0>=CONCAT && LA145_0<=LIB_FUNCTION)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=SUBSELECT_EXPR)||(LA145_0>=EXISTS_SUBSELECT_EXPR && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||LA145_0==STAR||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=PLUS && LA145_0<=MOD)) ) {
                        alt145=1;
                    }


                    switch (alt145) {
                	case 1 :
                	    // EsperEPL2Ast.g:442:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr3178);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop145;
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
    // EsperEPL2Ast.g:445:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:446:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt147=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt147=1;
                }
                break;
            case MINUS:
                {
                alt147=2;
                }
                break;
            case DIV:
                {
                alt147=3;
                }
                break;
            case STAR:
                {
                alt147=4;
                }
                break;
            case MOD:
                {
                alt147=5;
                }
                break;
            case BAND:
                {
                alt147=6;
                }
                break;
            case BOR:
                {
                alt147=7;
                }
                break;
            case BXOR:
                {
                alt147=8;
                }
                break;
            case CONCAT:
                {
                alt147=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 147, 0, input);

                throw nvae;
            }

            switch (alt147) {
                case 1 :
                    // EsperEPL2Ast.g:446:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr3199); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3201);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3203);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:447:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr3215); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3217);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3219);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:448:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr3231); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3233);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3235);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:449:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr3246); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3248);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3250);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:450:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr3262); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3264);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3266);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:451:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3277); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3279);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3281);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:452:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3292); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3294);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3296);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:453:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3307); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3309);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3311);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:454:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3323); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3325);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3327);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:454:36: ( valueExpr )*
                    loop146:
                    do {
                        int alt146=2;
                        int LA146_0 = input.LA(1);

                        if ( ((LA146_0>=IN_SET && LA146_0<=REGEXP)||LA146_0==NOT_EXPR||(LA146_0>=SUM && LA146_0<=AVG)||(LA146_0>=COALESCE && LA146_0<=COUNT)||(LA146_0>=CASE && LA146_0<=CASE2)||(LA146_0>=PREVIOUS && LA146_0<=EXISTS)||(LA146_0>=INSTANCEOF && LA146_0<=CURRENT_TIMESTAMP)||(LA146_0>=EVAL_AND_EXPR && LA146_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA146_0==EVENT_PROP_EXPR||(LA146_0>=CONCAT && LA146_0<=LIB_FUNCTION)||LA146_0==ARRAY_EXPR||(LA146_0>=NOT_IN_SET && LA146_0<=NOT_REGEXP)||(LA146_0>=IN_RANGE && LA146_0<=SUBSELECT_EXPR)||(LA146_0>=EXISTS_SUBSELECT_EXPR && LA146_0<=NOT_IN_SUBSELECT_EXPR)||LA146_0==SUBSTITUTION||(LA146_0>=INT_TYPE && LA146_0<=NULL_TYPE)||LA146_0==STAR||(LA146_0>=BAND && LA146_0<=BXOR)||(LA146_0>=LT && LA146_0<=GE)||(LA146_0>=PLUS && LA146_0<=MOD)) ) {
                            alt146=1;
                        }


                        switch (alt146) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:454:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3330);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop146;
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
    // EsperEPL2Ast.g:457:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:458:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:458:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3351); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:458:22: ( CLASS_IDENT )?
            int alt148=2;
            int LA148_0 = input.LA(1);

            if ( (LA148_0==CLASS_IDENT) ) {
                alt148=1;
            }
            switch (alt148) {
                case 1 :
                    // EsperEPL2Ast.g:458:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3354); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3358); 
            // EsperEPL2Ast.g:458:43: ( DISTINCT )?
            int alt149=2;
            int LA149_0 = input.LA(1);

            if ( (LA149_0==DISTINCT) ) {
                alt149=1;
            }
            switch (alt149) {
                case 1 :
                    // EsperEPL2Ast.g:458:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3361); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:458:55: ( valueExpr )*
            loop150:
            do {
                int alt150=2;
                int LA150_0 = input.LA(1);

                if ( ((LA150_0>=IN_SET && LA150_0<=REGEXP)||LA150_0==NOT_EXPR||(LA150_0>=SUM && LA150_0<=AVG)||(LA150_0>=COALESCE && LA150_0<=COUNT)||(LA150_0>=CASE && LA150_0<=CASE2)||(LA150_0>=PREVIOUS && LA150_0<=EXISTS)||(LA150_0>=INSTANCEOF && LA150_0<=CURRENT_TIMESTAMP)||(LA150_0>=EVAL_AND_EXPR && LA150_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA150_0==EVENT_PROP_EXPR||(LA150_0>=CONCAT && LA150_0<=LIB_FUNCTION)||LA150_0==ARRAY_EXPR||(LA150_0>=NOT_IN_SET && LA150_0<=NOT_REGEXP)||(LA150_0>=IN_RANGE && LA150_0<=SUBSELECT_EXPR)||(LA150_0>=EXISTS_SUBSELECT_EXPR && LA150_0<=NOT_IN_SUBSELECT_EXPR)||LA150_0==SUBSTITUTION||(LA150_0>=INT_TYPE && LA150_0<=NULL_TYPE)||LA150_0==STAR||(LA150_0>=BAND && LA150_0<=BXOR)||(LA150_0>=LT && LA150_0<=GE)||(LA150_0>=PLUS && LA150_0<=MOD)) ) {
                    alt150=1;
                }


                switch (alt150) {
            	case 1 :
            	    // EsperEPL2Ast.g:458:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3366);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop150;
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
    // EsperEPL2Ast.g:464:1: startPatternExpressionRule : ( annotation[true] )* exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:465:2: ( ( annotation[true] )* exprChoice )
            // EsperEPL2Ast.g:465:4: ( annotation[true] )* exprChoice
            {
            // EsperEPL2Ast.g:465:4: ( annotation[true] )*
            loop151:
            do {
                int alt151=2;
                int LA151_0 = input.LA(1);

                if ( (LA151_0==ANNOTATION) ) {
                    alt151=1;
                }


                switch (alt151) {
            	case 1 :
            	    // EsperEPL2Ast.g:465:4: annotation[true]
            	    {
            	    pushFollow(FOLLOW_annotation_in_startPatternExpressionRule3386);
            	    annotation(true);

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop151;
                }
            } while (true);

            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3390);
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
    // EsperEPL2Ast.g:468:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:469:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt155=7;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt155=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt155=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt155=3;
                }
                break;
            case EVERY_DISTINCT_EXPR:
                {
                alt155=4;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt155=5;
                }
                break;
            case GUARD_EXPR:
                {
                alt155=6;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt155=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 155, 0, input);

                throw nvae;
            }

            switch (alt155) {
                case 1 :
                    // EsperEPL2Ast.g:469:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3404);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:470:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3409);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:471:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3419); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3421);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:472:5: ^(a= EVERY_DISTINCT_EXPR distinctExpressions exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_DISTINCT_EXPR,FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3435); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_distinctExpressions_in_exprChoice3437);
                    distinctExpressions();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3439);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:473:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3453); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3455);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:474:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3469); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3471);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3473); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3475); 
                    // EsperEPL2Ast.g:474:44: ( valueExprWithTime )*
                    loop152:
                    do {
                        int alt152=2;
                        int LA152_0 = input.LA(1);

                        if ( ((LA152_0>=IN_SET && LA152_0<=REGEXP)||LA152_0==NOT_EXPR||(LA152_0>=SUM && LA152_0<=AVG)||(LA152_0>=COALESCE && LA152_0<=COUNT)||(LA152_0>=CASE && LA152_0<=CASE2)||LA152_0==LAST||(LA152_0>=PREVIOUS && LA152_0<=EXISTS)||(LA152_0>=LW && LA152_0<=CURRENT_TIMESTAMP)||(LA152_0>=NUMERIC_PARAM_RANGE && LA152_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA152_0>=EVAL_AND_EXPR && LA152_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA152_0==EVENT_PROP_EXPR||(LA152_0>=CONCAT && LA152_0<=LIB_FUNCTION)||(LA152_0>=TIME_PERIOD && LA152_0<=ARRAY_EXPR)||(LA152_0>=NOT_IN_SET && LA152_0<=NOT_REGEXP)||(LA152_0>=IN_RANGE && LA152_0<=SUBSELECT_EXPR)||(LA152_0>=EXISTS_SUBSELECT_EXPR && LA152_0<=NOT_IN_SUBSELECT_EXPR)||(LA152_0>=LAST_OPERATOR && LA152_0<=SUBSTITUTION)||LA152_0==NUMBERSETSTAR||(LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)||LA152_0==STAR||(LA152_0>=BAND && LA152_0<=BXOR)||(LA152_0>=LT && LA152_0<=GE)||(LA152_0>=PLUS && LA152_0<=MOD)) ) {
                            alt152=1;
                        }


                        switch (alt152) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:474:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3477);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop152;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:475:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3491); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:475:26: ( matchUntilRange )?
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( ((LA153_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA153_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt153=1;
                    }
                    switch (alt153) {
                        case 1 :
                            // EsperEPL2Ast.g:475:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice3493);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3496);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:475:54: ( exprChoice )?
                    int alt154=2;
                    int LA154_0 = input.LA(1);

                    if ( ((LA154_0>=OR_EXPR && LA154_0<=AND_EXPR)||(LA154_0>=EVERY_EXPR && LA154_0<=EVERY_DISTINCT_EXPR)||LA154_0==FOLLOWED_BY_EXPR||(LA154_0>=PATTERN_FILTER_EXPR && LA154_0<=PATTERN_NOT_EXPR)||(LA154_0>=GUARD_EXPR && LA154_0<=OBSERVER_EXPR)||LA154_0==MATCH_UNTIL_EXPR) ) {
                        alt154=1;
                    }
                    switch (alt154) {
                        case 1 :
                            // EsperEPL2Ast.g:475:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice3498);
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
    // EsperEPL2Ast.g:479:1: distinctExpressions : ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) ;
    public final void distinctExpressions() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:480:2: ( ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ ) )
            // EsperEPL2Ast.g:480:4: ^( PATTERN_EVERY_DISTINCT_EXPR ( valueExpr )+ )
            {
            match(input,PATTERN_EVERY_DISTINCT_EXPR,FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3519); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:480:35: ( valueExpr )+
            int cnt156=0;
            loop156:
            do {
                int alt156=2;
                int LA156_0 = input.LA(1);

                if ( ((LA156_0>=IN_SET && LA156_0<=REGEXP)||LA156_0==NOT_EXPR||(LA156_0>=SUM && LA156_0<=AVG)||(LA156_0>=COALESCE && LA156_0<=COUNT)||(LA156_0>=CASE && LA156_0<=CASE2)||(LA156_0>=PREVIOUS && LA156_0<=EXISTS)||(LA156_0>=INSTANCEOF && LA156_0<=CURRENT_TIMESTAMP)||(LA156_0>=EVAL_AND_EXPR && LA156_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA156_0==EVENT_PROP_EXPR||(LA156_0>=CONCAT && LA156_0<=LIB_FUNCTION)||LA156_0==ARRAY_EXPR||(LA156_0>=NOT_IN_SET && LA156_0<=NOT_REGEXP)||(LA156_0>=IN_RANGE && LA156_0<=SUBSELECT_EXPR)||(LA156_0>=EXISTS_SUBSELECT_EXPR && LA156_0<=NOT_IN_SUBSELECT_EXPR)||LA156_0==SUBSTITUTION||(LA156_0>=INT_TYPE && LA156_0<=NULL_TYPE)||LA156_0==STAR||(LA156_0>=BAND && LA156_0<=BXOR)||(LA156_0>=LT && LA156_0<=GE)||(LA156_0>=PLUS && LA156_0<=MOD)) ) {
                    alt156=1;
                }


                switch (alt156) {
            	case 1 :
            	    // EsperEPL2Ast.g:480:35: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_distinctExpressions3521);
            	    valueExpr();

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


            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:483:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:484:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt160=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt160=1;
                }
                break;
            case OR_EXPR:
                {
                alt160=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:484:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3540); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3542);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3544);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:484:48: ( exprChoice )*
                    loop157:
                    do {
                        int alt157=2;
                        int LA157_0 = input.LA(1);

                        if ( ((LA157_0>=OR_EXPR && LA157_0<=AND_EXPR)||(LA157_0>=EVERY_EXPR && LA157_0<=EVERY_DISTINCT_EXPR)||LA157_0==FOLLOWED_BY_EXPR||(LA157_0>=PATTERN_FILTER_EXPR && LA157_0<=PATTERN_NOT_EXPR)||(LA157_0>=GUARD_EXPR && LA157_0<=OBSERVER_EXPR)||LA157_0==MATCH_UNTIL_EXPR) ) {
                            alt157=1;
                        }


                        switch (alt157) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:484:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3547);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop157;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:485:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp3563); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3565);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3567);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:485:40: ( exprChoice )*
                    loop158:
                    do {
                        int alt158=2;
                        int LA158_0 = input.LA(1);

                        if ( ((LA158_0>=OR_EXPR && LA158_0<=AND_EXPR)||(LA158_0>=EVERY_EXPR && LA158_0<=EVERY_DISTINCT_EXPR)||LA158_0==FOLLOWED_BY_EXPR||(LA158_0>=PATTERN_FILTER_EXPR && LA158_0<=PATTERN_NOT_EXPR)||(LA158_0>=GUARD_EXPR && LA158_0<=OBSERVER_EXPR)||LA158_0==MATCH_UNTIL_EXPR) ) {
                            alt158=1;
                        }


                        switch (alt158) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:485:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3570);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop158;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:486:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3586); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3588);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3590);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:486:41: ( exprChoice )*
                    loop159:
                    do {
                        int alt159=2;
                        int LA159_0 = input.LA(1);

                        if ( ((LA159_0>=OR_EXPR && LA159_0<=AND_EXPR)||(LA159_0>=EVERY_EXPR && LA159_0<=EVERY_DISTINCT_EXPR)||LA159_0==FOLLOWED_BY_EXPR||(LA159_0>=PATTERN_FILTER_EXPR && LA159_0<=PATTERN_NOT_EXPR)||(LA159_0>=GUARD_EXPR && LA159_0<=OBSERVER_EXPR)||LA159_0==MATCH_UNTIL_EXPR) ) {
                            alt159=1;
                        }


                        switch (alt159) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:486:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3593);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop159;
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
    // EsperEPL2Ast.g:489:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:490:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt162=2;
            int LA162_0 = input.LA(1);

            if ( (LA162_0==PATTERN_FILTER_EXPR) ) {
                alt162=1;
            }
            else if ( (LA162_0==OBSERVER_EXPR) ) {
                alt162=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 162, 0, input);

                throw nvae;
            }
            switch (alt162) {
                case 1 :
                    // EsperEPL2Ast.g:490:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr3612);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:491:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3624); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3626); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3628); 
                    // EsperEPL2Ast.g:491:39: ( valueExprWithTime )*
                    loop161:
                    do {
                        int alt161=2;
                        int LA161_0 = input.LA(1);

                        if ( ((LA161_0>=IN_SET && LA161_0<=REGEXP)||LA161_0==NOT_EXPR||(LA161_0>=SUM && LA161_0<=AVG)||(LA161_0>=COALESCE && LA161_0<=COUNT)||(LA161_0>=CASE && LA161_0<=CASE2)||LA161_0==LAST||(LA161_0>=PREVIOUS && LA161_0<=EXISTS)||(LA161_0>=LW && LA161_0<=CURRENT_TIMESTAMP)||(LA161_0>=NUMERIC_PARAM_RANGE && LA161_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA161_0>=EVAL_AND_EXPR && LA161_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA161_0==EVENT_PROP_EXPR||(LA161_0>=CONCAT && LA161_0<=LIB_FUNCTION)||(LA161_0>=TIME_PERIOD && LA161_0<=ARRAY_EXPR)||(LA161_0>=NOT_IN_SET && LA161_0<=NOT_REGEXP)||(LA161_0>=IN_RANGE && LA161_0<=SUBSELECT_EXPR)||(LA161_0>=EXISTS_SUBSELECT_EXPR && LA161_0<=NOT_IN_SUBSELECT_EXPR)||(LA161_0>=LAST_OPERATOR && LA161_0<=SUBSTITUTION)||LA161_0==NUMBERSETSTAR||(LA161_0>=INT_TYPE && LA161_0<=NULL_TYPE)||LA161_0==STAR||(LA161_0>=BAND && LA161_0<=BXOR)||(LA161_0>=LT && LA161_0<=GE)||(LA161_0>=PLUS && LA161_0<=MOD)) ) {
                            alt161=1;
                        }


                        switch (alt161) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:491:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3630);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop161;
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
    // EsperEPL2Ast.g:494:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:495:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:495:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3650); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:495:29: ( IDENT )?
            int alt163=2;
            int LA163_0 = input.LA(1);

            if ( (LA163_0==IDENT) ) {
                alt163=1;
            }
            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:495:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr3652); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr3655); 
            // EsperEPL2Ast.g:495:48: ( propertyExpression )?
            int alt164=2;
            int LA164_0 = input.LA(1);

            if ( (LA164_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt164=1;
            }
            switch (alt164) {
                case 1 :
                    // EsperEPL2Ast.g:495:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr3657);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:495:68: ( valueExpr )*
            loop165:
            do {
                int alt165=2;
                int LA165_0 = input.LA(1);

                if ( ((LA165_0>=IN_SET && LA165_0<=REGEXP)||LA165_0==NOT_EXPR||(LA165_0>=SUM && LA165_0<=AVG)||(LA165_0>=COALESCE && LA165_0<=COUNT)||(LA165_0>=CASE && LA165_0<=CASE2)||(LA165_0>=PREVIOUS && LA165_0<=EXISTS)||(LA165_0>=INSTANCEOF && LA165_0<=CURRENT_TIMESTAMP)||(LA165_0>=EVAL_AND_EXPR && LA165_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA165_0==EVENT_PROP_EXPR||(LA165_0>=CONCAT && LA165_0<=LIB_FUNCTION)||LA165_0==ARRAY_EXPR||(LA165_0>=NOT_IN_SET && LA165_0<=NOT_REGEXP)||(LA165_0>=IN_RANGE && LA165_0<=SUBSELECT_EXPR)||(LA165_0>=EXISTS_SUBSELECT_EXPR && LA165_0<=NOT_IN_SUBSELECT_EXPR)||LA165_0==SUBSTITUTION||(LA165_0>=INT_TYPE && LA165_0<=NULL_TYPE)||LA165_0==STAR||(LA165_0>=BAND && LA165_0<=BXOR)||(LA165_0>=LT && LA165_0<=GE)||(LA165_0>=PLUS && LA165_0<=MOD)) ) {
                    alt165=1;
                }


                switch (alt165) {
            	case 1 :
            	    // EsperEPL2Ast.g:495:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr3661);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop165;
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
    // EsperEPL2Ast.g:498:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:499:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt166=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt166=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt166=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt166=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt166=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 166, 0, input);

                throw nvae;
            }

            switch (alt166) {
                case 1 :
                    // EsperEPL2Ast.g:499:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3679); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3681);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3683);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:500:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3691); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3693);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:501:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3701); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3703);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:502:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3710); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3712);
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
    // EsperEPL2Ast.g:505:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:506:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:510:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:511:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:511:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam3741); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam3743);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:511:35: ( valueExpr )*
            loop167:
            do {
                int alt167=2;
                int LA167_0 = input.LA(1);

                if ( ((LA167_0>=IN_SET && LA167_0<=REGEXP)||LA167_0==NOT_EXPR||(LA167_0>=SUM && LA167_0<=AVG)||(LA167_0>=COALESCE && LA167_0<=COUNT)||(LA167_0>=CASE && LA167_0<=CASE2)||(LA167_0>=PREVIOUS && LA167_0<=EXISTS)||(LA167_0>=INSTANCEOF && LA167_0<=CURRENT_TIMESTAMP)||(LA167_0>=EVAL_AND_EXPR && LA167_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA167_0==EVENT_PROP_EXPR||(LA167_0>=CONCAT && LA167_0<=LIB_FUNCTION)||LA167_0==ARRAY_EXPR||(LA167_0>=NOT_IN_SET && LA167_0<=NOT_REGEXP)||(LA167_0>=IN_RANGE && LA167_0<=SUBSELECT_EXPR)||(LA167_0>=EXISTS_SUBSELECT_EXPR && LA167_0<=NOT_IN_SUBSELECT_EXPR)||LA167_0==SUBSTITUTION||(LA167_0>=INT_TYPE && LA167_0<=NULL_TYPE)||LA167_0==STAR||(LA167_0>=BAND && LA167_0<=BXOR)||(LA167_0>=LT && LA167_0<=GE)||(LA167_0>=PLUS && LA167_0<=MOD)) ) {
                    alt167=1;
                }


                switch (alt167) {
            	case 1 :
            	    // EsperEPL2Ast.g:511:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam3746);
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
    // EsperEPL2Ast.g:514:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:515:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt180=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt180=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt180=2;
                }
                break;
            case LT:
                {
                alt180=3;
                }
                break;
            case LE:
                {
                alt180=4;
                }
                break;
            case GT:
                {
                alt180=5;
                }
                break;
            case GE:
                {
                alt180=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt180=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt180=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt180=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt180=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt180=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt180=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 180, 0, input);

                throw nvae;
            }

            switch (alt180) {
                case 1 :
                    // EsperEPL2Ast.g:515:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator3762); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3764);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:516:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator3771); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3773);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:517:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator3780); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3782);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:518:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator3789); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3791);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:519:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator3798); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3800);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:520:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator3807); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3809);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:521:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3816); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:521:41: ( constant[false] | filterIdentifier )
                    int alt168=2;
                    int LA168_0 = input.LA(1);

                    if ( ((LA168_0>=INT_TYPE && LA168_0<=NULL_TYPE)) ) {
                        alt168=1;
                    }
                    else if ( (LA168_0==EVENT_FILTER_IDENT) ) {
                        alt168=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 168, 0, input);

                        throw nvae;
                    }
                    switch (alt168) {
                        case 1 :
                            // EsperEPL2Ast.g:521:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3825);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:521:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3828);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:521:76: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:521:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3832);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:521:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3835);
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
                    // EsperEPL2Ast.g:522:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3849); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:522:45: ( constant[false] | filterIdentifier )
                    int alt170=2;
                    int LA170_0 = input.LA(1);

                    if ( ((LA170_0>=INT_TYPE && LA170_0<=NULL_TYPE)) ) {
                        alt170=1;
                    }
                    else if ( (LA170_0==EVENT_FILTER_IDENT) ) {
                        alt170=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 170, 0, input);

                        throw nvae;
                    }
                    switch (alt170) {
                        case 1 :
                            // EsperEPL2Ast.g:522:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3858);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:522:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3861);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:522:80: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:522:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3865);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:522:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3868);
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
                    // EsperEPL2Ast.g:523:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3882); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:523:38: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:523:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3891);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:523:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3894);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:523:73: ( constant[false] | filterIdentifier )*
                    loop173:
                    do {
                        int alt173=3;
                        int LA173_0 = input.LA(1);

                        if ( ((LA173_0>=INT_TYPE && LA173_0<=NULL_TYPE)) ) {
                            alt173=1;
                        }
                        else if ( (LA173_0==EVENT_FILTER_IDENT) ) {
                            alt173=2;
                        }


                        switch (alt173) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:523:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3898);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:523:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3901);
                    	    filterIdentifier();

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

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:524:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3916); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:524:42: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:524:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3925);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:524:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3928);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:524:77: ( constant[false] | filterIdentifier )*
                    loop175:
                    do {
                        int alt175=3;
                        int LA175_0 = input.LA(1);

                        if ( ((LA175_0>=INT_TYPE && LA175_0<=NULL_TYPE)) ) {
                            alt175=1;
                        }
                        else if ( (LA175_0==EVENT_FILTER_IDENT) ) {
                            alt175=2;
                        }


                        switch (alt175) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:524:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3932);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:524:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3935);
                    	    filterIdentifier();

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

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:525:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3950); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:525:27: ( constant[false] | filterIdentifier )
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
                            // EsperEPL2Ast.g:525:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3953);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:525:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3956);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:525:62: ( constant[false] | filterIdentifier )
                    int alt177=2;
                    int LA177_0 = input.LA(1);

                    if ( ((LA177_0>=INT_TYPE && LA177_0<=NULL_TYPE)) ) {
                        alt177=1;
                    }
                    else if ( (LA177_0==EVENT_FILTER_IDENT) ) {
                        alt177=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 177, 0, input);

                        throw nvae;
                    }
                    switch (alt177) {
                        case 1 :
                            // EsperEPL2Ast.g:525:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3960);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:525:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3963);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:526:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3971); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:526:31: ( constant[false] | filterIdentifier )
                    int alt178=2;
                    int LA178_0 = input.LA(1);

                    if ( ((LA178_0>=INT_TYPE && LA178_0<=NULL_TYPE)) ) {
                        alt178=1;
                    }
                    else if ( (LA178_0==EVENT_FILTER_IDENT) ) {
                        alt178=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 178, 0, input);

                        throw nvae;
                    }
                    switch (alt178) {
                        case 1 :
                            // EsperEPL2Ast.g:526:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3974);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:526:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3977);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:526:66: ( constant[false] | filterIdentifier )
                    int alt179=2;
                    int LA179_0 = input.LA(1);

                    if ( ((LA179_0>=INT_TYPE && LA179_0<=NULL_TYPE)) ) {
                        alt179=1;
                    }
                    else if ( (LA179_0==EVENT_FILTER_IDENT) ) {
                        alt179=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 179, 0, input);

                        throw nvae;
                    }
                    switch (alt179) {
                        case 1 :
                            // EsperEPL2Ast.g:526:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3981);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:526:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3984);
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
    // EsperEPL2Ast.g:529:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:530:2: ( constant[false] | filterIdentifier )
            int alt181=2;
            int LA181_0 = input.LA(1);

            if ( ((LA181_0>=INT_TYPE && LA181_0<=NULL_TYPE)) ) {
                alt181=1;
            }
            else if ( (LA181_0==EVENT_FILTER_IDENT) ) {
                alt181=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 181, 0, input);

                throw nvae;
            }
            switch (alt181) {
                case 1 :
                    // EsperEPL2Ast.g:530:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom3998);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:531:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom4004);
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
    // EsperEPL2Ast.g:533:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:534:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:534:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4015); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier4017); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier4019);
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
    // EsperEPL2Ast.g:537:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:538:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:538:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4038); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4040);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:538:44: ( eventPropertyAtomic )*
            loop182:
            do {
                int alt182=2;
                int LA182_0 = input.LA(1);

                if ( ((LA182_0>=EVENT_PROP_SIMPLE && LA182_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt182=1;
                }


                switch (alt182) {
            	case 1 :
            	    // EsperEPL2Ast.g:538:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4043);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop182;
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
    // EsperEPL2Ast.g:541:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:542:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt183=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt183=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt183=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt183=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt183=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt183=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt183=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 183, 0, input);

                throw nvae;
            }

            switch (alt183) {
                case 1 :
                    // EsperEPL2Ast.g:542:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4062); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4064); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:543:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4071); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4073); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4075); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:544:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4082); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4084); 
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
                    // EsperEPL2Ast.g:545:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4099); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4101); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:546:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4108); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4110); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic4112); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:547:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4119); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic4121); 
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
    // EsperEPL2Ast.g:550:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:551:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:551:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod4148); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod4150);
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
    // EsperEPL2Ast.g:554:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:555:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt194=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt194=1;
                }
                break;
            case HOUR_PART:
                {
                alt194=2;
                }
                break;
            case MINUTE_PART:
                {
                alt194=3;
                }
                break;
            case SECOND_PART:
                {
                alt194=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt194=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 194, 0, input);

                throw nvae;
            }

            switch (alt194) {
                case 1 :
                    // EsperEPL2Ast.g:555:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef4166);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:555:13: ( hourPart )?
                    int alt184=2;
                    int LA184_0 = input.LA(1);

                    if ( (LA184_0==HOUR_PART) ) {
                        alt184=1;
                    }
                    switch (alt184) {
                        case 1 :
                            // EsperEPL2Ast.g:555:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef4169);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:555:25: ( minutePart )?
                    int alt185=2;
                    int LA185_0 = input.LA(1);

                    if ( (LA185_0==MINUTE_PART) ) {
                        alt185=1;
                    }
                    switch (alt185) {
                        case 1 :
                            // EsperEPL2Ast.g:555:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4174);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:555:39: ( secondPart )?
                    int alt186=2;
                    int LA186_0 = input.LA(1);

                    if ( (LA186_0==SECOND_PART) ) {
                        alt186=1;
                    }
                    switch (alt186) {
                        case 1 :
                            // EsperEPL2Ast.g:555:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4179);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:555:53: ( millisecondPart )?
                    int alt187=2;
                    int LA187_0 = input.LA(1);

                    if ( (LA187_0==MILLISECOND_PART) ) {
                        alt187=1;
                    }
                    switch (alt187) {
                        case 1 :
                            // EsperEPL2Ast.g:555:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4184);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:556:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef4191);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:556:13: ( minutePart )?
                    int alt188=2;
                    int LA188_0 = input.LA(1);

                    if ( (LA188_0==MINUTE_PART) ) {
                        alt188=1;
                    }
                    switch (alt188) {
                        case 1 :
                            // EsperEPL2Ast.g:556:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef4194);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:556:27: ( secondPart )?
                    int alt189=2;
                    int LA189_0 = input.LA(1);

                    if ( (LA189_0==SECOND_PART) ) {
                        alt189=1;
                    }
                    switch (alt189) {
                        case 1 :
                            // EsperEPL2Ast.g:556:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4199);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:556:41: ( millisecondPart )?
                    int alt190=2;
                    int LA190_0 = input.LA(1);

                    if ( (LA190_0==MILLISECOND_PART) ) {
                        alt190=1;
                    }
                    switch (alt190) {
                        case 1 :
                            // EsperEPL2Ast.g:556:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4204);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:557:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef4211);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:557:15: ( secondPart )?
                    int alt191=2;
                    int LA191_0 = input.LA(1);

                    if ( (LA191_0==SECOND_PART) ) {
                        alt191=1;
                    }
                    switch (alt191) {
                        case 1 :
                            // EsperEPL2Ast.g:557:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef4214);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:557:29: ( millisecondPart )?
                    int alt192=2;
                    int LA192_0 = input.LA(1);

                    if ( (LA192_0==MILLISECOND_PART) ) {
                        alt192=1;
                    }
                    switch (alt192) {
                        case 1 :
                            // EsperEPL2Ast.g:557:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4219);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:558:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef4226);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:558:15: ( millisecondPart )?
                    int alt193=2;
                    int LA193_0 = input.LA(1);

                    if ( (LA193_0==MILLISECOND_PART) ) {
                        alt193=1;
                    }
                    switch (alt193) {
                        case 1 :
                            // EsperEPL2Ast.g:558:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4229);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:559:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef4236);
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
    // EsperEPL2Ast.g:562:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:563:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:563:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart4250); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart4252);
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
    // EsperEPL2Ast.g:566:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:567:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:567:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart4267); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart4269);
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
    // EsperEPL2Ast.g:570:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:571:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:571:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart4284); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart4286);
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
    // EsperEPL2Ast.g:574:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:575:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:575:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart4301); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart4303);
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
    // EsperEPL2Ast.g:578:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:579:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:579:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4318); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4320);
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
    // EsperEPL2Ast.g:582:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:583:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:583:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4335); 
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
    // EsperEPL2Ast.g:586:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:587:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt195=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt195=1;
                }
                break;
            case LONG_TYPE:
                {
                alt195=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt195=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt195=4;
                }
                break;
            case STRING_TYPE:
                {
                alt195=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt195=6;
                }
                break;
            case NULL_TYPE:
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
                    // EsperEPL2Ast.g:587:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4351); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:588:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4360); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:589:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4369); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:590:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4378); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:591:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4394); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:592:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4410); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:593:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4423); 
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
    // EsperEPL2Ast.g:596:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:597:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_CLASS_IDENT_in_annotation94 = new BitSet(new long[]{0x0000000000000008L,0x0800000000000000L,0x0000000000000000L,0x00000007F7000000L});
    public static final BitSet FOLLOW_elementValuePair_in_annotation96 = new BitSet(new long[]{0x0000000000000008L,0x0800000000000000L,0x0000000000000000L,0x00000007F7000000L});
    public static final BitSet FOLLOW_elementValue_in_annotation99 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ANNOTATION_VALUE_in_elementValuePair117 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_elementValuePair119 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L,0x0000000000000000L,0x00000007F3000000L});
    public static final BitSet FOLLOW_elementValue_in_elementValuePair121 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_annotation_in_elementValue148 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ANNOTATION_ARRAY_in_elementValue156 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_elementValue_in_elementValue158 = new BitSet(new long[]{0x0000000000000008L,0x0800000000000000L,0x0000000000000000L,0x00000007F3000000L});
    public static final BitSet FOLLOW_constant_in_elementValue169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_elementValue179 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule202 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_annotation_in_startEPLExpressionRule204 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000800L,0x0000000009004050L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule208 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule225 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule233 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_updateExpr_in_eplExpressionRule241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr260 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr263 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000001180L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr267 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000001180L});
    public static final BitSet FOLLOW_IDENT_in_onExpr270 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000001180L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr277 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr281 = new BitSet(new long[]{0x0040000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_onSelectInsertExpr_in_onExpr284 = new BitSet(new long[]{0x0040000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000600L});
    public static final BitSet FOLLOW_onSelectInsertOutput_in_onExpr287 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr294 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_INSERT_in_onExpr297 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_UPDATE_EXPR_in_updateExpr319 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_updateExpr321 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000002000L});
    public static final BitSet FOLLOW_IDENT_in_updateExpr323 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000002000L});
    public static final BitSet FOLLOW_onSetAssignment_in_updateExpr326 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x0000004000002000L});
    public static final BitSet FOLLOW_whereClause_in_updateExpr329 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr347 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr349 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr352 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr370 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr372 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000003000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr375 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000300006L,0x0000000000000800L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr377 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000300006L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr380 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000300004L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr384 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000200004L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr387 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_EXPR_in_onSelectInsertExpr407 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectInsertExpr409 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000003000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectInsertExpr411 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_whereClause_in_onSelectInsertExpr413 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_INSERT_OUTPUT_in_onSelectInsertOutput430 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_onSelectInsertOutput432 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr450 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr452 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x0000004000002000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr455 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000002L,0x0000004000002000L});
    public static final BitSet FOLLOW_whereClause_in_onSetExpr459 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_ITEM_in_onSetAssignment475 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment477 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment479 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom492 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom494 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom497 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr515 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr517 = new BitSet(new long[]{0x8000000000000000L,0x4800000000000001L,0x0000000000000000L,0x0000000000200020L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr520 = new BitSet(new long[]{0x8000000000000000L,0x4800000000000001L,0x0000000000000000L,0x0000000000200020L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr524 = new BitSet(new long[]{0x8000000000000000L,0x4800000000000001L,0x0000000000000000L,0x0000000000200020L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr527 = new BitSet(new long[]{0x8000000000000000L,0x4800000000000001L,0x0000000000000000L,0x0000000000200020L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr541 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr544 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr573 = new BitSet(new long[]{0x0040000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr584 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert602 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert604 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList621 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList623 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0040000000001000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList626 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0040000000001000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList645 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList647 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList650 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement665 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement667 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement669 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement683 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement693 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement713 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement717 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement739 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement742 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr778 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr780 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr782 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr785 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr803 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000800000800L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr809 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr814 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000005C0300006L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr819 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000005C0300004L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr827 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000005C0200004L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr834 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x00000005C0200000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr841 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L,0x0000000000200000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr848 = new BitSet(new long[]{0x0000000000000002L,0x0000000200000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr855 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr872 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr874 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr883 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr886 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol905 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol907 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol910 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause928 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause930 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0040000000003000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause943 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause957 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause960 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000F4000L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause963 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000F4000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList980 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0040000000003000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList983 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0040000000003000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement999 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement1009 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement1011 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1014 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement1028 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1030 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement1033 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin1052 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent1066 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1068 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1071 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1075 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1078 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent1093 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1095 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1098 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1102 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1105 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent1120 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1122 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1125 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1129 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1132 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent1147 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1149 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1152 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1156 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent1159 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression1180 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression1183 = new BitSet(new long[]{0xC000000000000008L,0x4000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression1187 = new BitSet(new long[]{0xC000000000000008L,0x4000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression1191 = new BitSet(new long[]{0xC000000000000008L,0x4000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression1195 = new BitSet(new long[]{0xC000000000000008L,0x4000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression1199 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression1204 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1209 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_set_in_streamExpression1213 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1239 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1242 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000400000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1244 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1248 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1270 = new BitSet(new long[]{0x0000000000000008L,0x0000800000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1289 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1291 = new BitSet(new long[]{0x0000000000000000L,0x0007000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1294 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1297 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1301 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1303 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1323 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1333 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1335 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1338 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1352 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1354 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1357 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1378 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1380 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1397 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1399 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1401 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1409 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1430 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1432 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1434 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1437 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1451 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1454 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1471 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1475 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1478 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1500 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1502 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1520 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1522 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1525 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1543 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1545 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1548 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1568 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1570 = new BitSet(new long[]{0x0600000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1572 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1595 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1597 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1615 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1617 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000040F0000000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1629 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1631 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1648 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1650 = new BitSet(new long[]{0x0020000037CC23C0L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1661 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1676 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1678 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1689 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1704 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1706 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr1717 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000004000001180L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr1719 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1738 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1741 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x00000060F0000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1743 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x00000060F0000000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1747 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1749 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause1753 = new BitSet(new long[]{0x0000000000000008L,0x0000000400000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause1756 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1774 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1776 = new BitSet(new long[]{0x0020000037CC23C0L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1778 = new BitSet(new long[]{0x0020000037CC23C0L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1780 = new BitSet(new long[]{0x0020000037CC23C0L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1782 = new BitSet(new long[]{0x0020000037CC23C0L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1784 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1786 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr1803 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1805 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr1818 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1820 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr1833 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1835 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr1847 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1849 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1871 = new BitSet(new long[]{0x0003800037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1881 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue1896 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x7F078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1905 = new BitSet(new long[]{0x0000000037CC23C2L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_relationalExprValue1910 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1936 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1938 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1940 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1943 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1957 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1959 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1961 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1964 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1978 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1980 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1982 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1994 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1996 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1998 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice2010 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2012 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2014 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x7F078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2023 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2028 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice2041 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2043 = new BitSet(new long[]{0x0003800000000000L});
    public static final BitSet FOLLOW_set_in_evalExprChoice2045 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x7F078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2054 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_subSelectGroupExpr_in_evalExprChoice2059 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice2072 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice2074 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice2085 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr2098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr2104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr2110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr2117 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr2126 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr2131 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr2139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr2144 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr2149 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr2155 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr2160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr2165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr2170 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr2175 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr2181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr2188 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime2201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime2210 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2217 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime2225 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime2227 = new BitSet(new long[]{0x0600000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime2229 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime2242 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2248 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2253 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2268 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2270 = new BitSet(new long[]{0x0000000000000008L,0x0000005000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2281 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2288 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2301 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2308 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2314 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2330 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2333 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L,0x00000007F0000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2336 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L,0x00000007F0000004L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2339 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L,0x00000007F0000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2343 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2346 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2349 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2370 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2373 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2376 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2379 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2398 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2401 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2404 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2407 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2426 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2429 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2432 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2435 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_GROUP_EXPR_in_subSelectGroupExpr2456 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectGroupExpr2458 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2477 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2479 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2498 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2500 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2519 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2521 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2523 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2535 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2537 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x8000000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2539 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2558 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2560 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2576 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000004000L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2578 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2581 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2601 = new BitSet(new long[]{0x8000000000000008L,0x4000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2604 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2609 = new BitSet(new long[]{0x8000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr2613 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000001L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2616 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2636 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2639 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2652 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2655 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2675 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2677 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000120000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2679 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2685 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE2C07F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2688 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE2C07F0000004L});
    public static final BitSet FOLLOW_set_in_inExpr2692 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr2707 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2709 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000120000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2711 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2717 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE2C07F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2720 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE2C07F0000004L});
    public static final BitSet FOLLOW_set_in_inExpr2724 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr2739 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2741 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000120000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2743 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2749 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2751 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000240000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2753 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr2768 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2770 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000120000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2772 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2778 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2780 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000240000000000L});
    public static final BitSet FOLLOW_set_in_inExpr2782 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr2805 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2807 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2809 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2811 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr2822 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2824 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2826 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2829 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr2849 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2851 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2853 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2856 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr2869 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2871 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2873 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2876 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr2895 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2897 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2899 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr2910 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2912 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2914 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc2933 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2936 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2940 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc2951 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2954 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2958 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc2969 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2973 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2977 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc2991 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2994 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2998 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc3009 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3012 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3016 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc3027 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc3030 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3034 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc3046 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3048 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3050 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3053 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc3068 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3070 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3072 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc3085 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc3089 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc3104 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3106 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3108 = new BitSet(new long[]{0x0000000000000008L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3111 = new BitSet(new long[]{0x0000000000000008L,0x0800000000000000L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc3125 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc3127 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc3129 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc3141 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc3143 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc3155 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr3175 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr3178 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr3199 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3201 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3203 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr3215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3217 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3219 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr3231 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3233 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3235 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr3246 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3248 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3250 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr3262 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3264 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3266 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3277 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3279 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3281 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3292 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3294 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3296 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3309 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3311 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3323 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3325 = new BitSet(new long[]{0x0000000037CC23C0L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3327 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3330 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3351 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3354 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3358 = new BitSet(new long[]{0x0000400037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3361 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3366 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_annotation_in_startPatternExpressionRule3386 = new BitSet(new long[]{0x000000000000D800L,0x30000D0000000000L,0x0000000000000000L,0x0000000001010000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3390 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3404 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3409 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3419 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3421 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVERY_DISTINCT_EXPR_in_exprChoice3435 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_distinctExpressions_in_exprChoice3437 = new BitSet(new long[]{0x000000000000D800L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3439 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3453 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3469 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3473 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3475 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3477 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3491 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice3493 = new BitSet(new long[]{0x000000000000D800L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3496 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3498 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_EVERY_DISTINCT_EXPR_in_distinctExpressions3519 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_distinctExpressions3521 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3540 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3542 = new BitSet(new long[]{0x000000000000D800L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3544 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3547 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp3563 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3565 = new BitSet(new long[]{0x000000000000D800L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3567 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3570 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3586 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3588 = new BitSet(new long[]{0x000000000000D800L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3590 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3593 = new BitSet(new long[]{0x000000000000D808L,0x30000D0000000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr3612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3624 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3626 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3628 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3630 = new BitSet(new long[]{0x0020000037CC23C8L,0x000000F000000F70L,0x77078360008003F0L,0x7BCE0807F0800007L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3650 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr3652 = new BitSet(new long[]{0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr3655 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000400000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr3657 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr3661 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3679 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3681 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000800000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3683 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3691 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3693 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3701 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3703 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3710 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3712 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam3741 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3743 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3746 = new BitSet(new long[]{0x0000000037CC23C8L,0x0000000000000E70L,0x77078260008003F0L,0x7BCE0807F0000004L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator3762 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3764 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator3771 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3773 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator3780 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3782 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator3789 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3791 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator3798 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3800 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator3807 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3809 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3816 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3818 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3825 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3828 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3832 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000240000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3835 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000240000000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3838 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3849 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3851 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3858 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3861 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3865 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000240000000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3868 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000240000000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3871 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3882 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3884 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3891 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3894 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3898 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3901 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3905 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3916 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3918 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3925 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3928 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3932 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3935 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00002407F0000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3939 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3953 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3956 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3960 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3963 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3971 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3974 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3977 = new BitSet(new long[]{0x0000000000000000L,0x0008000000000000L,0x0000000000000000L,0x00000007F0000000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3981 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3984 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom3998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom4004 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier4015 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier4017 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier4019 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr4038 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4040 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000003F000000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr4043 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000003F000000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic4062 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4064 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic4071 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4073 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4075 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic4082 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4086 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic4099 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4101 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic4108 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4110 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000002L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic4112 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic4119 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic4121 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0001800000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic4123 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod4148 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod4150 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef4166 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000780000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4169 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000700000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4174 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4179 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4184 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef4191 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000700000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4194 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4199 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef4211 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000600000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4214 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4219 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef4226 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000400000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4229 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef4236 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart4250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart4252 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart4267 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart4269 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart4284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart4286 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart4301 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart4303 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4318 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4320 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4335 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4351 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4360 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4369 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4394 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4410 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4423 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}