// $ANTLR 3.1.1 EsperEPL2Ast.g 2009-01-20 06:48:53

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "ANY", "SOME", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "PATTERN_FILTER_EXPR", "PATTERN_NOT_EXPR", "EVENT_FILTER_EXPR", "EVENT_FILTER_PROPERTY_EXPR", "EVENT_FILTER_PROPERTY_EXPR_ATOM", "PROPERTY_SELECTION_ELEMENT_EXPR", "PROPERTY_SELECTION_STREAM", "PROPERTY_WILDCARD_SELECT", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_EQUALS_GROUP_EXPR", "EVAL_NOTEQUALS_GROUP_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "IDENT", "COMMA", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BOR", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "PLUS", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "QUESTION", "ESCAPECHAR", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=157;
    public static final int FLOAT_SUFFIX=282;
    public static final int STAR=224;
    public static final int NUMERIC_PARAM_LIST=99;
    public static final int ISTREAM=59;
    public static final int MOD=243;
    public static final int OUTERJOIN_EXPR=140;
    public static final int BSR=264;
    public static final int LIB_FUNCTION=163;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=92;
    public static final int FULL_OUTERJOIN_EXPR=144;
    public static final int RPAREN=223;
    public static final int LNOT=253;
    public static final int INC=257;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=228;
    public static final int BSR_ASSIGN=265;
    public static final int CAST_EXPR=191;
    public static final int STREAM_EXPR=139;
    public static final int TIMEPERIOD_SECONDS=89;
    public static final int NOT_EQUAL=234;
    public static final int METADATASQL=66;
    public static final int EVENT_FILTER_PROPERTY_EXPR=107;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=102;
    public static final int FOLLOWED_BY=247;
    public static final int HOUR_PART=168;
    public static final int RBRACK=226;
    public static final int MATCH_UNTIL_RANGE_CLOSED=204;
    public static final int GE=238;
    public static final int METHOD_JOIN_EXPR=200;
    public static final int ASC=56;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=106;
    public static final int MINUS_ASSIGN=258;
    public static final int ELSE=29;
    public static final int EVENT_FILTER_NOT_IN=117;
    public static final int NUM_DOUBLE=216;
    public static final int INSERTINTO_STREAM_NAME=180;
    public static final int UNARY_MINUS=164;
    public static final int TIMEPERIOD_MILLISEC=90;
    public static final int LCURLY=244;
    public static final int RETAINUNION=62;
    public static final int DBWHERE_CLAUSE=178;
    public static final int MEDIAN=22;
    public static final int EVENTS=50;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=115;
    public static final int GROUP=43;
    public static final int EMAILAT=273;
    public static final int WS=274;
    public static final int ESCAPECHAR=249;
    public static final int SL_COMMENT=275;
    public static final int NULL_TYPE=215;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=202;
    public static final int GT=236;
    public static final int BNOT=254;
    public static final int WHERE_EXPR=126;
    public static final int END=32;
    public static final int INNERJOIN_EXPR=141;
    public static final int LAND=271;
    public static final int NOT_REGEXP=175;
    public static final int MATCH_UNTIL_EXPR=201;
    public static final int EVENT_PROP_EXPR=148;
    public static final int LBRACK=225;
    public static final int VIEW_EXPR=123;
    public static final int LONG_TYPE=210;
    public static final int EVENT_FILTER_PROPERTY_EXPR_ATOM=108;
    public static final int TIMEPERIOD_SEC=87;
    public static final int ON_SELECT_EXPR=196;
    public static final int MINUTE_PART=169;
    public static final int PATTERN_NOT_EXPR=105;
    public static final int SUM=17;
    public static final int SQL_NE=233;
    public static final int HexDigit=280;
    public static final int LPAREN=222;
    public static final int IN_SUBSELECT_EXPR=185;
    public static final int AT=80;
    public static final int AS=16;
    public static final int BOOLEAN_TRUE=93;
    public static final int OR_EXPR=11;
    public static final int THEN=31;
    public static final int NOT_IN_RANGE=182;
    public static final int OFFSET=97;
    public static final int LEFT=37;
    public static final int AVG=18;
    public static final int SECOND_PART=170;
    public static final int PREVIOUS=67;
    public static final int IDENT=218;
    public static final int DATABASE_JOIN_EXPR=125;
    public static final int PLUS=240;
    public static final int BXOR=232;
    public static final int CASE2=28;
    public static final int TIMEPERIOD_DAY=81;
    public static final int EXISTS=69;
    public static final int EVENT_PROP_INDEXED=151;
    public static final int TIMEPERIOD_MILLISECOND=91;
    public static final int EVAL_NOTEQUALS_EXPR=132;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=203;
    public static final int CREATE_VARIABLE_EXPR=199;
    public static final int CREATE_WINDOW_COL_TYPE=207;
    public static final int LIKE=8;
    public static final int OUTER=34;
    public static final int BY=42;
    public static final int ARRAY_PARAM_LIST=103;
    public static final int RIGHT_OUTERJOIN_EXPR=143;
    public static final int NUMBERSETSTAR=208;
    public static final int LAST_OPERATOR=188;
    public static final int PATTERN_FILTER_EXPR=104;
    public static final int EVAL_AND_EXPR=129;
    public static final int LEFT_OUTERJOIN_EXPR=142;
    public static final int EPL_EXPR=217;
    public static final int GROUP_BY_EXPR=145;
    public static final int SET=77;
    public static final int RIGHT=38;
    public static final int HAVING=44;
    public static final int INSTANCEOF=72;
    public static final int MIN=20;
    public static final int EVENT_PROP_SIMPLE=149;
    public static final int MINUS=241;
    public static final int SEMI=272;
    public static final int STAR_ASSIGN=260;
    public static final int COLON=227;
    public static final int EVAL_EQUALS_GROUP_EXPR=133;
    public static final int BAND_ASSIGN=270;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=158;
    public static final int VALUE_NULL=95;
    public static final int NOT_IN_SET=172;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=152;
    public static final int SL=266;
    public static final int WHEN=30;
    public static final int NOT_IN_SUBSELECT_EXPR=186;
    public static final int GUARD_EXPR=121;
    public static final int SR=262;
    public static final int RCURLY=245;
    public static final int PLUS_ASSIGN=256;
    public static final int DAY_PART=167;
    public static final int EXISTS_SUBSELECT_EXPR=184;
    public static final int EVENT_FILTER_IN=116;
    public static final int DIV=242;
    public static final int OBJECT_PARAM_ORDERED_EXPR=101;
    public static final int OctalEscape=279;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=171;
    public static final int PRIOR=68;
    public static final int FIRST=51;
    public static final int ROW_LIMIT_EXPR=96;
    public static final int SELECTION_EXPR=136;
    public static final int LOR=239;
    public static final int CAST=73;
    public static final int LW=71;
    public static final int WILDCARD_SELECT=179;
    public static final int EXPONENT=281;
    public static final int LT=235;
    public static final int PATTERN_INCL_EXPR=124;
    public static final int ORDER_BY_EXPR=146;
    public static final int BOOL_TYPE=214;
    public static final int MOD_ASSIGN=261;
    public static final int CASE=27;
    public static final int IN_SUBSELECT_QUERY_EXPR=187;
    public static final int EQUALS=220;
    public static final int COUNT=25;
    public static final int RETAININTERSECTION=63;
    public static final int DIV_ASSIGN=255;
    public static final int SL_ASSIGN=267;
    public static final int PATTERN=64;
    public static final int SQL=65;
    public static final int WEEKDAY=70;
    public static final int FULL=39;
    public static final int INSERT=53;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=166;
    public static final int LAST=52;
    public static final int BOOLEAN_FALSE=94;
    public static final int EVAL_NOTEQUALS_GROUP_EXPR=134;
    public static final int SELECT=26;
    public static final int INTO=54;
    public static final int FLOAT_TYPE=211;
    public static final int TIMEPERIOD_SECOND=88;
    public static final int COALESCE=21;
    public static final int EVENT_FILTER_BETWEEN=118;
    public static final int SUBSELECT_EXPR=183;
    public static final int NUMERIC_PARAM_RANGE=98;
    public static final int CONCAT=162;
    public static final int CLASS_IDENT=120;
    public static final int ON_EXPR=194;
    public static final int CREATE_WINDOW_EXPR=192;
    public static final int PROPERTY_SELECTION_STREAM=110;
    public static final int ON_DELETE_EXPR=195;
    public static final int ON=40;
    public static final int NUM_LONG=250;
    public static final int TIME_PERIOD=165;
    public static final int DOUBLE_TYPE=212;
    public static final int DELETE=75;
    public static final int INT_TYPE=209;
    public static final int EVAL_BITWISE_EXPR=128;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=147;
    public static final int TIMEPERIOD_HOURS=84;
    public static final int VARIABLE=78;
    public static final int SUBSTITUTION=190;
    public static final int UNTIL=79;
    public static final int STRING_TYPE=213;
    public static final int ON_SET_EXPR=198;
    public static final int NUM_INT=246;
    public static final int STDDEV=23;
    public static final int ON_EXPR_FROM=197;
    public static final int NUM_FLOAT=251;
    public static final int FROM=33;
    public static final int DISTINCT=45;
    public static final int PROPERTY_SELECTION_ELEMENT_EXPR=109;
    public static final int OUTPUT=49;
    public static final int EscapeSequence=277;
    public static final int WEEKDAY_OPERATOR=189;
    public static final int WHERE=15;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=206;
    public static final int DEC=259;
    public static final int INNER=35;
    public static final int NUMERIC_PARAM_FREQUENCY=100;
    public static final int BXOR_ASSIGN=268;
    public static final int ORDER=55;
    public static final int SNAPSHOT=76;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=154;
    public static final int EVENT_FILTER_PARAM=113;
    public static final int IRSTREAM=60;
    public static final int MAX=19;
    public static final int TIMEPERIOD_DAYS=82;
    public static final int EVENT_FILTER_RANGE=114;
    public static final int ML_COMMENT=276;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=153;
    public static final int BOR_ASSIGN=269;
    public static final int COMMA=219;
    public static final int WHEN_LIMIT_EXPR=159;
    public static final int IS=41;
    public static final int TIMEPERIOD_LIMIT_EXPR=156;
    public static final int SOME=48;
    public static final int ALL=46;
    public static final int TIMEPERIOD_HOUR=83;
    public static final int BOR=231;
    public static final int EQUAL=252;
    public static final int EVENT_FILTER_NOT_BETWEEN=119;
    public static final int IN_RANGE=181;
    public static final int DOT=221;
    public static final int CURRENT_TIMESTAMP=74;
    public static final int PROPERTY_WILDCARD_SELECT=111;
    public static final int INSERTINTO_EXPR=160;
    public static final int HAVING_EXPR=127;
    public static final int UNIDIRECTIONAL=61;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=205;
    public static final int EVAL_EQUALS_EXPR=131;
    public static final int TIMEPERIOD_MINUTES=86;
    public static final int RSTREAM=58;
    public static final int NOT_LIKE=174;
    public static final int EVENT_LIMIT_EXPR=155;
    public static final int NOT_BETWEEN=173;
    public static final int TIMEPERIOD_MINUTE=85;
    public static final int EVAL_OR_EXPR=130;
    public static final int BAND=230;
    public static final int QUOTED_STRING_LITERAL=229;
    public static final int JOIN=36;
    public static final int ANY=47;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=248;
    public static final int OBSERVER_EXPR=122;
    public static final int EVENT_FILTER_IDENT=112;
    public static final int EVENT_PROP_MAPPED=150;
    public static final int UnicodeEscape=278;
    public static final int AVEDEV=24;
    public static final int DBSELECT_EXPR=176;
    public static final int SELECTION_ELEMENT_EXPR=137;
    public static final int CREATE_WINDOW_SELECT_EXPR=193;
    public static final int INSERTINTO_EXPRCOL=161;
    public static final int WINDOW=5;
    public static final int DESC=57;
    public static final int SELECTION_STREAM=138;
    public static final int SR_ASSIGN=263;
    public static final int DBFROM_CLAUSE=177;
    public static final int LE=237;
    public static final int EVAL_IDENT=135;

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



    // $ANTLR start "startEPLExpressionRule"
    // EsperEPL2Ast.g:57:1: startEPLExpressionRule : ^( EPL_EXPR eplExpressionRule ) ;
    public final void startEPLExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:58:2: ( ^( EPL_EXPR eplExpressionRule ) )
            // EsperEPL2Ast.g:58:4: ^( EPL_EXPR eplExpressionRule )
            {
            match(input,EPL_EXPR,FOLLOW_EPL_EXPR_in_startEPLExpressionRule89); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eplExpressionRule_in_startEPLExpressionRule91);
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
    // EsperEPL2Ast.g:61:1: eplExpressionRule : ( selectExpr | createWindowExpr | createVariableExpr | onExpr ) ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:62:2: ( ( selectExpr | createWindowExpr | createVariableExpr | onExpr ) )
            // EsperEPL2Ast.g:62:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr )
            {
            // EsperEPL2Ast.g:62:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr )
            int alt1=4;
            switch ( input.LA(1) ) {
            case SELECTION_EXPR:
            case INSERTINTO_EXPR:
                {
                alt1=1;
                }
                break;
            case CREATE_WINDOW_EXPR:
                {
                alt1=2;
                }
                break;
            case CREATE_VARIABLE_EXPR:
                {
                alt1=3;
                }
                break;
            case ON_EXPR:
                {
                alt1=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // EsperEPL2Ast.g:62:5: selectExpr
                    {
                    pushFollow(FOLLOW_selectExpr_in_eplExpressionRule108);
                    selectExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:62:18: createWindowExpr
                    {
                    pushFollow(FOLLOW_createWindowExpr_in_eplExpressionRule112);
                    createWindowExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:62:37: createVariableExpr
                    {
                    pushFollow(FOLLOW_createVariableExpr_in_eplExpressionRule116);
                    createVariableExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:62:58: onExpr
                    {
                    pushFollow(FOLLOW_onExpr_in_eplExpressionRule120);
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
    // EsperEPL2Ast.g:65:1: onExpr : ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr | onSetExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:66:2: ( ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr | onSetExpr ) ) )
            // EsperEPL2Ast.g:66:4: ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr | onSetExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr139); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:66:16: ( eventFilterExpr | patternInclusionExpression )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==EVENT_FILTER_EXPR) ) {
                alt2=1;
            }
            else if ( (LA2_0==PATTERN_INCL_EXPR) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // EsperEPL2Ast.g:66:17: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_onExpr142);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:66:35: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onExpr146);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:66:63: ( IDENT )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==IDENT) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // EsperEPL2Ast.g:66:63: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExpr149); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:67:3: ( onDeleteExpr | onSelectExpr | onSetExpr )
            int alt4=3;
            switch ( input.LA(1) ) {
            case ON_DELETE_EXPR:
                {
                alt4=1;
                }
                break;
            case ON_SELECT_EXPR:
                {
                alt4=2;
                }
                break;
            case ON_SET_EXPR:
                {
                alt4=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // EsperEPL2Ast.g:67:4: onDeleteExpr
                    {
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr156);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:67:19: onSelectExpr
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr160);
                    onSelectExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:67:34: onSetExpr
                    {
                    pushFollow(FOLLOW_onSetExpr_in_onExpr164);
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
    // EsperEPL2Ast.g:71:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:72:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause )? ) )
            // EsperEPL2Ast.g:72:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr184); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr186);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:72:32: ( whereClause )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==WHERE_EXPR) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // EsperEPL2Ast.g:72:33: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_onDeleteExpr189);
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
    // EsperEPL2Ast.g:75:1: onSelectExpr : ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList onExprFrom ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:76:2: ( ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList onExprFrom ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) )
            // EsperEPL2Ast.g:76:4: ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList onExprFrom ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? )
            {
            match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr206); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:76:21: ( insertIntoExpr )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INSERTINTO_EXPR) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // EsperEPL2Ast.g:76:22: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_onSelectExpr209);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectionList_in_onSelectExpr213);
            selectionList();

            state._fsp--;

            pushFollow(FOLLOW_onExprFrom_in_onSelectExpr215);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:76:64: ( whereClause )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==WHERE_EXPR) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // EsperEPL2Ast.g:76:65: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr218);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:76:79: ( groupByClause )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==GROUP_BY_EXPR) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // EsperEPL2Ast.g:76:80: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr223);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:76:96: ( havingClause )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==HAVING_EXPR) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // EsperEPL2Ast.g:76:97: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr228);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:76:112: ( orderByClause )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ORDER_BY_EXPR) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // EsperEPL2Ast.g:76:113: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_onSelectExpr233);
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


    // $ANTLR start "onSetExpr"
    // EsperEPL2Ast.g:79:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:80:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ) )
            // EsperEPL2Ast.g:80:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr250); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr252);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:80:34: ( onSetAssignment )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==IDENT) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // EsperEPL2Ast.g:80:35: onSetAssignment
            	    {
            	    pushFollow(FOLLOW_onSetAssignment_in_onSetExpr255);
            	    onSetAssignment();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop11;
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
    // EsperEPL2Ast.g:83:1: onSetAssignment : IDENT valueExpr ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:84:2: ( IDENT valueExpr )
            // EsperEPL2Ast.g:84:4: IDENT valueExpr
            {
            match(input,IDENT,FOLLOW_IDENT_in_onSetAssignment270); 
            pushFollow(FOLLOW_valueExpr_in_onSetAssignment272);
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
    // EsperEPL2Ast.g:87:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:88:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:88:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom284); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom286); 
            // EsperEPL2Ast.g:88:25: ( IDENT )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==IDENT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // EsperEPL2Ast.g:88:26: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExprFrom289); 

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
    // EsperEPL2Ast.g:91:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:92:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:92:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( RETAINUNION )? ( RETAININTERSECTION )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr307); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr309); 
            // EsperEPL2Ast.g:92:33: ( viewListExpr )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==VIEW_EXPR) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:92:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr312);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:92:49: ( RETAINUNION )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==RETAINUNION) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // EsperEPL2Ast.g:92:49: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_createWindowExpr316); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:92:62: ( RETAININTERSECTION )?
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==RETAININTERSECTION) ) {
                alt15=1;
            }
            switch (alt15) {
                case 1 :
                    // EsperEPL2Ast.g:92:62: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_createWindowExpr319); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:93:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==CLASS_IDENT||LA17_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt17=1;
            }
            else if ( (LA17_0==CREATE_WINDOW_COL_TYPE_LIST) ) {
                alt17=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }
            switch (alt17) {
                case 1 :
                    // EsperEPL2Ast.g:94:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:94:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:94:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:94:6: ( createSelectionList )?
                    int alt16=2;
                    int LA16_0 = input.LA(1);

                    if ( (LA16_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt16=1;
                    }
                    switch (alt16) {
                        case 1 :
                            // EsperEPL2Ast.g:94:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr333);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr336); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:96:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:96:12: ( createColTypeList )
                    // EsperEPL2Ast.g:96:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr365);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:98:4: ( createWindowExprInsert )?
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==INSERT) ) {
                alt18=1;
            }
            switch (alt18) {
                case 1 :
                    // EsperEPL2Ast.g:98:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr376);
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
    // EsperEPL2Ast.g:102:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:103:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:103:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert394); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:103:13: ( valueExpr )?
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>=IN_SET && LA19_0<=REGEXP)||LA19_0==NOT_EXPR||(LA19_0>=SUM && LA19_0<=AVG)||(LA19_0>=COALESCE && LA19_0<=COUNT)||(LA19_0>=CASE && LA19_0<=CASE2)||(LA19_0>=PREVIOUS && LA19_0<=EXISTS)||(LA19_0>=INSTANCEOF && LA19_0<=CURRENT_TIMESTAMP)||(LA19_0>=EVAL_AND_EXPR && LA19_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA19_0==EVENT_PROP_EXPR||(LA19_0>=CONCAT && LA19_0<=LIB_FUNCTION)||LA19_0==ARRAY_EXPR||(LA19_0>=NOT_IN_SET && LA19_0<=NOT_REGEXP)||(LA19_0>=IN_RANGE && LA19_0<=NOT_IN_SUBSELECT_EXPR)||LA19_0==SUBSTITUTION||(LA19_0>=INT_TYPE && LA19_0<=NULL_TYPE)||LA19_0==STAR||(LA19_0>=BAND && LA19_0<=BXOR)||(LA19_0>=LT && LA19_0<=GE)||(LA19_0>=PLUS && LA19_0<=MOD)) ) {
                    alt19=1;
                }
                switch (alt19) {
                    case 1 :
                        // EsperEPL2Ast.g:103:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert396);
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
    // EsperEPL2Ast.g:106:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:107:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:107:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList413); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList415);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:107:61: ( createSelectionListElement )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( (LA20_0==SELECTION_ELEMENT_EXPR||LA20_0==WILDCARD_SELECT) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // EsperEPL2Ast.g:107:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList418);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop20;
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
    // EsperEPL2Ast.g:110:1: createColTypeList : ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:111:2: ( ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:111:4: ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList437); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList439);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:111:59: ( createColTypeListElement )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==CREATE_WINDOW_COL_TYPE) ) {
                    alt21=1;
                }


                switch (alt21) {
            	case 1 :
            	    // EsperEPL2Ast.g:111:60: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList442);
            	    createColTypeListElement();

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
    // $ANTLR end "createColTypeList"


    // $ANTLR start "createColTypeListElement"
    // EsperEPL2Ast.g:114:1: createColTypeListElement : ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:115:2: ( ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) )
            // EsperEPL2Ast.g:115:4: ^( CREATE_WINDOW_COL_TYPE IDENT IDENT )
            {
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement457); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement459); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement461); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:118:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:119:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==WILDCARD_SELECT) ) {
                alt24=1;
            }
            else if ( (LA24_0==SELECTION_ELEMENT_EXPR) ) {
                alt24=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:119:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement475); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:120:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement485); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:120:31: ( ( eventPropertyExpr[true] ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt23=2;
                    int LA23_0 = input.LA(1);

                    if ( (LA23_0==EVENT_PROP_EXPR) ) {
                        alt23=1;
                    }
                    else if ( ((LA23_0>=INT_TYPE && LA23_0<=NULL_TYPE)) ) {
                        alt23=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 23, 0, input);

                        throw nvae;
                    }
                    switch (alt23) {
                        case 1 :
                            // EsperEPL2Ast.g:121:16: ( eventPropertyExpr[true] ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:121:16: ( eventPropertyExpr[true] ( IDENT )? )
                            // EsperEPL2Ast.g:121:17: eventPropertyExpr[true] ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement505);
                            eventPropertyExpr(true);

                            state._fsp--;

                            // EsperEPL2Ast.g:121:41: ( IDENT )?
                            int alt22=2;
                            int LA22_0 = input.LA(1);

                            if ( (LA22_0==IDENT) ) {
                                alt22=1;
                            }
                            switch (alt22) {
                                case 1 :
                                    // EsperEPL2Ast.g:121:42: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement509); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:122:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:122:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:122:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement531);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement534); 

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
    // EsperEPL2Ast.g:126:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:127:2: ( ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:127:4: ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr570); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr572); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr574); 
            // EsperEPL2Ast.g:127:41: ( valueExpr )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=IN_SET && LA25_0<=REGEXP)||LA25_0==NOT_EXPR||(LA25_0>=SUM && LA25_0<=AVG)||(LA25_0>=COALESCE && LA25_0<=COUNT)||(LA25_0>=CASE && LA25_0<=CASE2)||(LA25_0>=PREVIOUS && LA25_0<=EXISTS)||(LA25_0>=INSTANCEOF && LA25_0<=CURRENT_TIMESTAMP)||(LA25_0>=EVAL_AND_EXPR && LA25_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA25_0==EVENT_PROP_EXPR||(LA25_0>=CONCAT && LA25_0<=LIB_FUNCTION)||LA25_0==ARRAY_EXPR||(LA25_0>=NOT_IN_SET && LA25_0<=NOT_REGEXP)||(LA25_0>=IN_RANGE && LA25_0<=NOT_IN_SUBSELECT_EXPR)||LA25_0==SUBSTITUTION||(LA25_0>=INT_TYPE && LA25_0<=NULL_TYPE)||LA25_0==STAR||(LA25_0>=BAND && LA25_0<=BXOR)||(LA25_0>=LT && LA25_0<=GE)||(LA25_0>=PLUS && LA25_0<=MOD)) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:127:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr577);
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
    // EsperEPL2Ast.g:130:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:131:2: ( ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:131:4: ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:131:4: ( insertIntoExpr )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==INSERTINTO_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:131:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr595);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr601);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr606);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:134:3: ( whereClause )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==WHERE_EXPR) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:134:4: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr611);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:135:3: ( groupByClause )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( (LA28_0==GROUP_BY_EXPR) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:135:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr618);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:136:3: ( havingClause )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==HAVING_EXPR) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:136:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr625);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:137:3: ( outputLimitExpr )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=EVENT_LIMIT_EXPR && LA30_0<=CRONTAB_LIMIT_EXPR)||LA30_0==WHEN_LIMIT_EXPR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:137:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr632);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:138:3: ( orderByClause )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==ORDER_BY_EXPR) ) {
                alt31=1;
            }
            switch (alt31) {
                case 1 :
                    // EsperEPL2Ast.g:138:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr639);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:3: ( rowLimitClause )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==ROW_LIMIT_EXPR) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:139:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr646);
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
    // EsperEPL2Ast.g:142:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:143:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:143:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr663); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:143:24: ( ISTREAM | RSTREAM )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( ((LA33_0>=RSTREAM && LA33_0<=ISTREAM)) ) {
                alt33=1;
            }
            switch (alt33) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr674); 
            // EsperEPL2Ast.g:143:51: ( insertIntoExprCol )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==INSERTINTO_EXPRCOL) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EsperEPL2Ast.g:143:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr677);
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
    // EsperEPL2Ast.g:146:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:147:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:147:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol696); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol698); 
            // EsperEPL2Ast.g:147:31: ( IDENT )*
            loop35:
            do {
                int alt35=2;
                int LA35_0 = input.LA(1);

                if ( (LA35_0==IDENT) ) {
                    alt35=1;
                }


                switch (alt35) {
            	case 1 :
            	    // EsperEPL2Ast.g:147:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol701); 

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
    // $ANTLR end "insertIntoExprCol"


    // $ANTLR start "selectClause"
    // EsperEPL2Ast.g:150:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:151:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) )
            // EsperEPL2Ast.g:151:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause719); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:151:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( ((LA36_0>=RSTREAM && LA36_0<=IRSTREAM)) ) {
                alt36=1;
            }
            switch (alt36) {
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

            pushFollow(FOLLOW_selectionList_in_selectClause734);
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
    // EsperEPL2Ast.g:154:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:155:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:155:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause748);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:155:21: ( streamExpression ( outerJoin )* )*
            loop38:
            do {
                int alt38=2;
                int LA38_0 = input.LA(1);

                if ( (LA38_0==STREAM_EXPR) ) {
                    alt38=1;
                }


                switch (alt38) {
            	case 1 :
            	    // EsperEPL2Ast.g:155:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause751);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:155:39: ( outerJoin )*
            	    loop37:
            	    do {
            	        int alt37=2;
            	        int LA37_0 = input.LA(1);

            	        if ( ((LA37_0>=INNERJOIN_EXPR && LA37_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt37=1;
            	        }


            	        switch (alt37) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:155:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause754);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop37;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop38;
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
    // EsperEPL2Ast.g:158:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:159:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:159:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList771);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:159:25: ( selectionListElement )*
            loop39:
            do {
                int alt39=2;
                int LA39_0 = input.LA(1);

                if ( ((LA39_0>=SELECTION_ELEMENT_EXPR && LA39_0<=SELECTION_STREAM)||LA39_0==WILDCARD_SELECT) ) {
                    alt39=1;
                }


                switch (alt39) {
            	case 1 :
            	    // EsperEPL2Ast.g:159:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList774);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop39;
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
    // EsperEPL2Ast.g:162:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:163:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt42=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt42=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt42=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt42=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 42, 0, input);

                throw nvae;
            }

            switch (alt42) {
                case 1 :
                    // EsperEPL2Ast.g:163:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement790); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:164:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement800); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement802);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:164:41: ( IDENT )?
                    int alt40=2;
                    int LA40_0 = input.LA(1);

                    if ( (LA40_0==IDENT) ) {
                        alt40=1;
                    }
                    switch (alt40) {
                        case 1 :
                            // EsperEPL2Ast.g:164:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement805); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:165:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement819); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement821); 
                    // EsperEPL2Ast.g:165:31: ( IDENT )?
                    int alt41=2;
                    int LA41_0 = input.LA(1);

                    if ( (LA41_0==IDENT) ) {
                        alt41=1;
                    }
                    switch (alt41) {
                        case 1 :
                            // EsperEPL2Ast.g:165:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement824); 

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
    // EsperEPL2Ast.g:168:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:169:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:169:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin843);
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
    // EsperEPL2Ast.g:172:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:173:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* ) )
            int alt47=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt47=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt47=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt47=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt47=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 47, 0, input);

                throw nvae;
            }

            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:173:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent857); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent859);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent862);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:173:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==EVENT_PROP_EXPR) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:173:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent866);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent869);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:174:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent884); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent886);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent889);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:174:78: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==EVENT_PROP_EXPR) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:174:79: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent893);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent896);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop44;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:175:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent911); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent913);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent916);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:175:77: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop45:
                    do {
                        int alt45=2;
                        int LA45_0 = input.LA(1);

                        if ( (LA45_0==EVENT_PROP_EXPR) ) {
                            alt45=1;
                        }


                        switch (alt45) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:175:78: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent920);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent923);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop45;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:176:4: ^(i= INNERJOIN_EXPR eventPropertyExpr[true] eventPropertyExpr[true] ( eventPropertyExpr[true] eventPropertyExpr[true] )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent938); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent940);
                    eventPropertyExpr(true);

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent943);
                    eventPropertyExpr(true);

                    state._fsp--;

                    // EsperEPL2Ast.g:176:71: ( eventPropertyExpr[true] eventPropertyExpr[true] )*
                    loop46:
                    do {
                        int alt46=2;
                        int LA46_0 = input.LA(1);

                        if ( (LA46_0==EVENT_PROP_EXPR) ) {
                            alt46=1;
                        }


                        switch (alt46) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:176:72: eventPropertyExpr[true] eventPropertyExpr[true]
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent947);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent950);
                    	    eventPropertyExpr(true);

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop46;
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
    // EsperEPL2Ast.g:179:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:180:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:180:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression971); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:180:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt48=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt48=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt48=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt48=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt48=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 48, 0, input);

                throw nvae;
            }

            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:180:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression974);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:180:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression978);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:180:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression982);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:180:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression986);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:180:115: ( viewListExpr )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==VIEW_EXPR) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:180:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression990);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:180:131: ( IDENT )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( (LA50_0==IDENT) ) {
                alt50=1;
            }
            switch (alt50) {
                case 1 :
                    // EsperEPL2Ast.g:180:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression995); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:180:140: ( UNIDIRECTIONAL )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( (LA51_0==UNIDIRECTIONAL) ) {
                alt51=1;
            }
            switch (alt51) {
                case 1 :
                    // EsperEPL2Ast.g:180:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression1000); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:180:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( ((LA52_0>=RETAINUNION && LA52_0<=RETAININTERSECTION)) ) {
                alt52=1;
            }
            switch (alt52) {
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
    // EsperEPL2Ast.g:183:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:184:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:184:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1028); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:184:27: ( IDENT )?
            int alt53=2;
            int LA53_0 = input.LA(1);

            if ( (LA53_0==IDENT) ) {
                alt53=1;
            }
            switch (alt53) {
                case 1 :
                    // EsperEPL2Ast.g:184:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr1030); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr1033); 
            // EsperEPL2Ast.g:184:46: ( propertyExpression )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( (LA54_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // EsperEPL2Ast.g:184:46: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_eventFilterExpr1035);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:184:66: ( valueExpr )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( ((LA55_0>=IN_SET && LA55_0<=REGEXP)||LA55_0==NOT_EXPR||(LA55_0>=SUM && LA55_0<=AVG)||(LA55_0>=COALESCE && LA55_0<=COUNT)||(LA55_0>=CASE && LA55_0<=CASE2)||(LA55_0>=PREVIOUS && LA55_0<=EXISTS)||(LA55_0>=INSTANCEOF && LA55_0<=CURRENT_TIMESTAMP)||(LA55_0>=EVAL_AND_EXPR && LA55_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA55_0==EVENT_PROP_EXPR||(LA55_0>=CONCAT && LA55_0<=LIB_FUNCTION)||LA55_0==ARRAY_EXPR||(LA55_0>=NOT_IN_SET && LA55_0<=NOT_REGEXP)||(LA55_0>=IN_RANGE && LA55_0<=NOT_IN_SUBSELECT_EXPR)||LA55_0==SUBSTITUTION||(LA55_0>=INT_TYPE && LA55_0<=NULL_TYPE)||LA55_0==STAR||(LA55_0>=BAND && LA55_0<=BXOR)||(LA55_0>=LT && LA55_0<=GE)||(LA55_0>=PLUS && LA55_0<=MOD)) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // EsperEPL2Ast.g:184:67: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr1039);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // EsperEPL2Ast.g:187:1: propertyExpression : ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) ;
    public final void propertyExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:188:2: ( ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* ) )
            // EsperEPL2Ast.g:188:4: ^( EVENT_FILTER_PROPERTY_EXPR ( propertyExpressionAtom )* )
            {
            match(input,EVENT_FILTER_PROPERTY_EXPR,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1059); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:188:34: ( propertyExpressionAtom )*
                loop56:
                do {
                    int alt56=2;
                    int LA56_0 = input.LA(1);

                    if ( (LA56_0==EVENT_FILTER_PROPERTY_EXPR_ATOM) ) {
                        alt56=1;
                    }


                    switch (alt56) {
                	case 1 :
                	    // EsperEPL2Ast.g:188:34: propertyExpressionAtom
                	    {
                	    pushFollow(FOLLOW_propertyExpressionAtom_in_propertyExpression1061);
                	    propertyExpressionAtom();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop56;
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
    // EsperEPL2Ast.g:191:1: propertyExpressionAtom : ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) ;
    public final void propertyExpressionAtom() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:192:2: ( ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) ) )
            // EsperEPL2Ast.g:192:4: ^(a= EVENT_FILTER_PROPERTY_EXPR_ATOM ( propertySelectionListElement )* eventPropertyExpr[false] ( IDENT )? ^( WHERE_EXPR ( valueExpr )? ) )
            {
            a=(CommonTree)match(input,EVENT_FILTER_PROPERTY_EXPR_ATOM,FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1080); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:192:41: ( propertySelectionListElement )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( ((LA57_0>=PROPERTY_SELECTION_ELEMENT_EXPR && LA57_0<=PROPERTY_WILDCARD_SELECT)) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // EsperEPL2Ast.g:192:41: propertySelectionListElement
            	    {
            	    pushFollow(FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1082);
            	    propertySelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);

            pushFollow(FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1085);
            eventPropertyExpr(false);

            state._fsp--;

            // EsperEPL2Ast.g:192:96: ( IDENT )?
            int alt58=2;
            int LA58_0 = input.LA(1);

            if ( (LA58_0==IDENT) ) {
                alt58=1;
            }
            switch (alt58) {
                case 1 :
                    // EsperEPL2Ast.g:192:96: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_propertyExpressionAtom1088); 

                    }
                    break;

            }

            match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1092); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:192:116: ( valueExpr )?
                int alt59=2;
                int LA59_0 = input.LA(1);

                if ( ((LA59_0>=IN_SET && LA59_0<=REGEXP)||LA59_0==NOT_EXPR||(LA59_0>=SUM && LA59_0<=AVG)||(LA59_0>=COALESCE && LA59_0<=COUNT)||(LA59_0>=CASE && LA59_0<=CASE2)||(LA59_0>=PREVIOUS && LA59_0<=EXISTS)||(LA59_0>=INSTANCEOF && LA59_0<=CURRENT_TIMESTAMP)||(LA59_0>=EVAL_AND_EXPR && LA59_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA59_0==EVENT_PROP_EXPR||(LA59_0>=CONCAT && LA59_0<=LIB_FUNCTION)||LA59_0==ARRAY_EXPR||(LA59_0>=NOT_IN_SET && LA59_0<=NOT_REGEXP)||(LA59_0>=IN_RANGE && LA59_0<=NOT_IN_SUBSELECT_EXPR)||LA59_0==SUBSTITUTION||(LA59_0>=INT_TYPE && LA59_0<=NULL_TYPE)||LA59_0==STAR||(LA59_0>=BAND && LA59_0<=BXOR)||(LA59_0>=LT && LA59_0<=GE)||(LA59_0>=PLUS && LA59_0<=MOD)) ) {
                    alt59=1;
                }
                switch (alt59) {
                    case 1 :
                        // EsperEPL2Ast.g:192:116: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_propertyExpressionAtom1094);
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
    // EsperEPL2Ast.g:195:1: propertySelectionListElement : (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void propertySelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:196:2: (w= PROPERTY_WILDCARD_SELECT | ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt62=3;
            switch ( input.LA(1) ) {
            case PROPERTY_WILDCARD_SELECT:
                {
                alt62=1;
                }
                break;
            case PROPERTY_SELECTION_ELEMENT_EXPR:
                {
                alt62=2;
                }
                break;
            case PROPERTY_SELECTION_STREAM:
                {
                alt62=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 62, 0, input);

                throw nvae;
            }

            switch (alt62) {
                case 1 :
                    // EsperEPL2Ast.g:196:4: w= PROPERTY_WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,PROPERTY_WILDCARD_SELECT,FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1114); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:197:4: ^(e= PROPERTY_SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,PROPERTY_SELECTION_ELEMENT_EXPR,FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1124); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_propertySelectionListElement1126);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:197:50: ( IDENT )?
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==IDENT) ) {
                        alt60=1;
                    }
                    switch (alt60) {
                        case 1 :
                            // EsperEPL2Ast.g:197:51: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1129); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:198:4: ^(s= PROPERTY_SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,PROPERTY_SELECTION_STREAM,FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1143); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1145); 
                    // EsperEPL2Ast.g:198:40: ( IDENT )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==IDENT) ) {
                        alt61=1;
                    }
                    switch (alt61) {
                        case 1 :
                            // EsperEPL2Ast.g:198:41: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_propertySelectionListElement1148); 

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
    // EsperEPL2Ast.g:201:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:202:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:202:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1169); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1171);
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
    // EsperEPL2Ast.g:205:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:206:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:206:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1188); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1190); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:206:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( ((LA63_0>=STRING_LITERAL && LA63_0<=QUOTED_STRING_LITERAL)) ) {
                alt63=1;
            }
            switch (alt63) {
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
    // EsperEPL2Ast.g:209:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:210:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:210:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1221); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1223); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1225); 
            // EsperEPL2Ast.g:210:41: ( valueExpr )*
            loop64:
            do {
                int alt64=2;
                int LA64_0 = input.LA(1);

                if ( ((LA64_0>=IN_SET && LA64_0<=REGEXP)||LA64_0==NOT_EXPR||(LA64_0>=SUM && LA64_0<=AVG)||(LA64_0>=COALESCE && LA64_0<=COUNT)||(LA64_0>=CASE && LA64_0<=CASE2)||(LA64_0>=PREVIOUS && LA64_0<=EXISTS)||(LA64_0>=INSTANCEOF && LA64_0<=CURRENT_TIMESTAMP)||(LA64_0>=EVAL_AND_EXPR && LA64_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA64_0==EVENT_PROP_EXPR||(LA64_0>=CONCAT && LA64_0<=LIB_FUNCTION)||LA64_0==ARRAY_EXPR||(LA64_0>=NOT_IN_SET && LA64_0<=NOT_REGEXP)||(LA64_0>=IN_RANGE && LA64_0<=NOT_IN_SUBSELECT_EXPR)||LA64_0==SUBSTITUTION||(LA64_0>=INT_TYPE && LA64_0<=NULL_TYPE)||LA64_0==STAR||(LA64_0>=BAND && LA64_0<=BXOR)||(LA64_0>=LT && LA64_0<=GE)||(LA64_0>=PLUS && LA64_0<=MOD)) ) {
                    alt64=1;
                }


                switch (alt64) {
            	case 1 :
            	    // EsperEPL2Ast.g:210:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1228);
            	    valueExpr();

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

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "methodJoinExpression"


    // $ANTLR start "viewListExpr"
    // EsperEPL2Ast.g:213:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:214:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:214:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1242);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:214:13: ( viewExpr )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==VIEW_EXPR) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // EsperEPL2Ast.g:214:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1245);
            	    viewExpr();

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
    // $ANTLR end "viewListExpr"


    // $ANTLR start "viewExpr"
    // EsperEPL2Ast.g:217:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:218:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:218:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1262); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1264); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1266); 
            // EsperEPL2Ast.g:218:30: ( valueExprWithTime )*
            loop66:
            do {
                int alt66=2;
                int LA66_0 = input.LA(1);

                if ( ((LA66_0>=IN_SET && LA66_0<=REGEXP)||LA66_0==NOT_EXPR||(LA66_0>=SUM && LA66_0<=AVG)||(LA66_0>=COALESCE && LA66_0<=COUNT)||(LA66_0>=CASE && LA66_0<=CASE2)||LA66_0==LAST||(LA66_0>=PREVIOUS && LA66_0<=EXISTS)||(LA66_0>=LW && LA66_0<=CURRENT_TIMESTAMP)||(LA66_0>=NUMERIC_PARAM_RANGE && LA66_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA66_0>=EVAL_AND_EXPR && LA66_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA66_0==EVENT_PROP_EXPR||(LA66_0>=CONCAT && LA66_0<=LIB_FUNCTION)||(LA66_0>=TIME_PERIOD && LA66_0<=ARRAY_EXPR)||(LA66_0>=NOT_IN_SET && LA66_0<=NOT_REGEXP)||(LA66_0>=IN_RANGE && LA66_0<=NOT_IN_SUBSELECT_EXPR)||(LA66_0>=LAST_OPERATOR && LA66_0<=SUBSTITUTION)||(LA66_0>=NUMBERSETSTAR && LA66_0<=NULL_TYPE)||LA66_0==STAR||(LA66_0>=BAND && LA66_0<=BXOR)||(LA66_0>=LT && LA66_0<=GE)||(LA66_0>=PLUS && LA66_0<=MOD)) ) {
                    alt66=1;
                }


                switch (alt66) {
            	case 1 :
            	    // EsperEPL2Ast.g:218:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1269);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop66;
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
    // EsperEPL2Ast.g:221:1: whereClause : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:222:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:222:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1290); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1292);
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
    // EsperEPL2Ast.g:225:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:226:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:226:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1310); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1312);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:226:32: ( valueExpr )*
            loop67:
            do {
                int alt67=2;
                int LA67_0 = input.LA(1);

                if ( ((LA67_0>=IN_SET && LA67_0<=REGEXP)||LA67_0==NOT_EXPR||(LA67_0>=SUM && LA67_0<=AVG)||(LA67_0>=COALESCE && LA67_0<=COUNT)||(LA67_0>=CASE && LA67_0<=CASE2)||(LA67_0>=PREVIOUS && LA67_0<=EXISTS)||(LA67_0>=INSTANCEOF && LA67_0<=CURRENT_TIMESTAMP)||(LA67_0>=EVAL_AND_EXPR && LA67_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA67_0==EVENT_PROP_EXPR||(LA67_0>=CONCAT && LA67_0<=LIB_FUNCTION)||LA67_0==ARRAY_EXPR||(LA67_0>=NOT_IN_SET && LA67_0<=NOT_REGEXP)||(LA67_0>=IN_RANGE && LA67_0<=NOT_IN_SUBSELECT_EXPR)||LA67_0==SUBSTITUTION||(LA67_0>=INT_TYPE && LA67_0<=NULL_TYPE)||LA67_0==STAR||(LA67_0>=BAND && LA67_0<=BXOR)||(LA67_0>=LT && LA67_0<=GE)||(LA67_0>=PLUS && LA67_0<=MOD)) ) {
                    alt67=1;
                }


                switch (alt67) {
            	case 1 :
            	    // EsperEPL2Ast.g:226:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1315);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop67;
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
    // EsperEPL2Ast.g:229:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:230:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:230:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1333); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1335);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:230:35: ( orderByElement )*
            loop68:
            do {
                int alt68=2;
                int LA68_0 = input.LA(1);

                if ( (LA68_0==ORDER_ELEMENT_EXPR) ) {
                    alt68=1;
                }


                switch (alt68) {
            	case 1 :
            	    // EsperEPL2Ast.g:230:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1338);
            	    orderByElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop68;
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
    // EsperEPL2Ast.g:233:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:234:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:234:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1358); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1360);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:234:38: ( ASC | DESC )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=ASC && LA69_0<=DESC)) ) {
                alt69=1;
            }
            switch (alt69) {
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
    // EsperEPL2Ast.g:237:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:238:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:238:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1385); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1387);
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
    // EsperEPL2Ast.g:241:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:242:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt76=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt76=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt76=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt76=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt76=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:242:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1405); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:242:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt70=2;
                    int LA70_0 = input.LA(1);

                    if ( (LA70_0==ALL||(LA70_0>=FIRST && LA70_0<=LAST)||LA70_0==SNAPSHOT) ) {
                        alt70=1;
                    }
                    switch (alt70) {
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

                    // EsperEPL2Ast.g:242:52: ( number | IDENT )
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( ((LA71_0>=INT_TYPE && LA71_0<=DOUBLE_TYPE)) ) {
                        alt71=1;
                    }
                    else if ( (LA71_0==IDENT) ) {
                        alt71=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 71, 0, input);

                        throw nvae;
                    }
                    switch (alt71) {
                        case 1 :
                            // EsperEPL2Ast.g:242:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1419);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:242:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1421); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:243:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1438); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:243:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==ALL||(LA72_0>=FIRST && LA72_0<=LAST)||LA72_0==SNAPSHOT) ) {
                        alt72=1;
                    }
                    switch (alt72) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1451);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:244:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1466); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:244:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt73=2;
                    int LA73_0 = input.LA(1);

                    if ( (LA73_0==ALL||(LA73_0>=FIRST && LA73_0<=LAST)||LA73_0==SNAPSHOT) ) {
                        alt73=1;
                    }
                    switch (alt73) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1479);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:245:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1494); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:245:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt74=2;
                    int LA74_0 = input.LA(1);

                    if ( (LA74_0==ALL||(LA74_0>=FIRST && LA74_0<=LAST)||LA74_0==SNAPSHOT) ) {
                        alt74=1;
                    }
                    switch (alt74) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr1507);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:245:67: ( onSetExpr )?
                    int alt75=2;
                    int LA75_0 = input.LA(1);

                    if ( (LA75_0==ON_SET_EXPR) ) {
                        alt75=1;
                    }
                    switch (alt75) {
                        case 1 :
                            // EsperEPL2Ast.g:245:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr1509);
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
    // EsperEPL2Ast.g:248:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:249:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:249:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1528); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:249:23: ( number | IDENT )
            int alt77=2;
            int LA77_0 = input.LA(1);

            if ( ((LA77_0>=INT_TYPE && LA77_0<=DOUBLE_TYPE)) ) {
                alt77=1;
            }
            else if ( (LA77_0==IDENT) ) {
                alt77=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 77, 0, input);

                throw nvae;
            }
            switch (alt77) {
                case 1 :
                    // EsperEPL2Ast.g:249:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1531);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:249:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1533); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:249:38: ( number | IDENT )?
            int alt78=3;
            int LA78_0 = input.LA(1);

            if ( ((LA78_0>=INT_TYPE && LA78_0<=DOUBLE_TYPE)) ) {
                alt78=1;
            }
            else if ( (LA78_0==IDENT) ) {
                alt78=2;
            }
            switch (alt78) {
                case 1 :
                    // EsperEPL2Ast.g:249:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1537);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:249:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1539); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:249:54: ( COMMA )?
            int alt79=2;
            int LA79_0 = input.LA(1);

            if ( (LA79_0==COMMA) ) {
                alt79=1;
            }
            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:249:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause1543); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:249:61: ( OFFSET )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==OFFSET) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:249:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause1546); 

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
    // EsperEPL2Ast.g:252:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:253:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:253:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1564); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1566);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1568);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1570);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1572);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1574);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:253:121: ( valueExprWithTime )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( ((LA81_0>=IN_SET && LA81_0<=REGEXP)||LA81_0==NOT_EXPR||(LA81_0>=SUM && LA81_0<=AVG)||(LA81_0>=COALESCE && LA81_0<=COUNT)||(LA81_0>=CASE && LA81_0<=CASE2)||LA81_0==LAST||(LA81_0>=PREVIOUS && LA81_0<=EXISTS)||(LA81_0>=LW && LA81_0<=CURRENT_TIMESTAMP)||(LA81_0>=NUMERIC_PARAM_RANGE && LA81_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA81_0>=EVAL_AND_EXPR && LA81_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA81_0==EVENT_PROP_EXPR||(LA81_0>=CONCAT && LA81_0<=LIB_FUNCTION)||(LA81_0>=TIME_PERIOD && LA81_0<=ARRAY_EXPR)||(LA81_0>=NOT_IN_SET && LA81_0<=NOT_REGEXP)||(LA81_0>=IN_RANGE && LA81_0<=NOT_IN_SUBSELECT_EXPR)||(LA81_0>=LAST_OPERATOR && LA81_0<=SUBSTITUTION)||(LA81_0>=NUMBERSETSTAR && LA81_0<=NULL_TYPE)||LA81_0==STAR||(LA81_0>=BAND && LA81_0<=BXOR)||(LA81_0>=LT && LA81_0<=GE)||(LA81_0>=PLUS && LA81_0<=MOD)) ) {
                alt81=1;
            }
            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:253:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1576);
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
    // EsperEPL2Ast.g:256:1: relationalExpr : ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:257:2: ( ^(n= LT relationalExprValue ) | ^(n= GT relationalExprValue ) | ^(n= LE relationalExprValue ) | ^(n= GE relationalExprValue ) )
            int alt82=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt82=1;
                }
                break;
            case GT:
                {
                alt82=2;
                }
                break;
            case LE:
                {
                alt82=3;
                }
                break;
            case GE:
                {
                alt82=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:257:5: ^(n= LT relationalExprValue )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr1593); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1595);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:258:5: ^(n= GT relationalExprValue )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr1608); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1610);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:259:5: ^(n= LE relationalExprValue )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr1623); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1625);
                    relationalExprValue();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:260:4: ^(n= GE relationalExprValue )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr1637); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_relationalExprValue_in_relationalExpr1639);
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
    // EsperEPL2Ast.g:263:1: relationalExprValue : ( valueExpr valueExpr | ( ANY | SOME | ALL ) ( valueExpr )* ) ;
    public final void relationalExprValue() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:264:2: ( ( valueExpr valueExpr | ( ANY | SOME | ALL ) ( valueExpr )* ) )
            // EsperEPL2Ast.g:264:4: ( valueExpr valueExpr | ( ANY | SOME | ALL ) ( valueExpr )* )
            {
            // EsperEPL2Ast.g:264:4: ( valueExpr valueExpr | ( ANY | SOME | ALL ) ( valueExpr )* )
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( ((LA84_0>=IN_SET && LA84_0<=REGEXP)||LA84_0==NOT_EXPR||(LA84_0>=SUM && LA84_0<=AVG)||(LA84_0>=COALESCE && LA84_0<=COUNT)||(LA84_0>=CASE && LA84_0<=CASE2)||(LA84_0>=PREVIOUS && LA84_0<=EXISTS)||(LA84_0>=INSTANCEOF && LA84_0<=CURRENT_TIMESTAMP)||(LA84_0>=EVAL_AND_EXPR && LA84_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA84_0==EVENT_PROP_EXPR||(LA84_0>=CONCAT && LA84_0<=LIB_FUNCTION)||LA84_0==ARRAY_EXPR||(LA84_0>=NOT_IN_SET && LA84_0<=NOT_REGEXP)||(LA84_0>=IN_RANGE && LA84_0<=NOT_IN_SUBSELECT_EXPR)||LA84_0==SUBSTITUTION||(LA84_0>=INT_TYPE && LA84_0<=NULL_TYPE)||LA84_0==STAR||(LA84_0>=BAND && LA84_0<=BXOR)||(LA84_0>=LT && LA84_0<=GE)||(LA84_0>=PLUS && LA84_0<=MOD)) ) {
                alt84=1;
            }
            else if ( ((LA84_0>=ALL && LA84_0<=SOME)) ) {
                alt84=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 84, 0, input);

                throw nvae;
            }
            switch (alt84) {
                case 1 :
                    // EsperEPL2Ast.g:265:5: valueExpr valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1661);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1663);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:267:6: ( ANY | SOME | ALL ) ( valueExpr )*
                    {
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:267:21: ( valueExpr )*
                    loop83:
                    do {
                        int alt83=2;
                        int LA83_0 = input.LA(1);

                        if ( ((LA83_0>=IN_SET && LA83_0<=REGEXP)||LA83_0==NOT_EXPR||(LA83_0>=SUM && LA83_0<=AVG)||(LA83_0>=COALESCE && LA83_0<=COUNT)||(LA83_0>=CASE && LA83_0<=CASE2)||(LA83_0>=PREVIOUS && LA83_0<=EXISTS)||(LA83_0>=INSTANCEOF && LA83_0<=CURRENT_TIMESTAMP)||(LA83_0>=EVAL_AND_EXPR && LA83_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA83_0==EVENT_PROP_EXPR||(LA83_0>=CONCAT && LA83_0<=LIB_FUNCTION)||LA83_0==ARRAY_EXPR||(LA83_0>=NOT_IN_SET && LA83_0<=NOT_REGEXP)||(LA83_0>=IN_RANGE && LA83_0<=NOT_IN_SUBSELECT_EXPR)||LA83_0==SUBSTITUTION||(LA83_0>=INT_TYPE && LA83_0<=NULL_TYPE)||LA83_0==STAR||(LA83_0>=BAND && LA83_0<=BXOR)||(LA83_0>=LT && LA83_0<=GE)||(LA83_0>=PLUS && LA83_0<=MOD)) ) {
                            alt83=1;
                        }


                        switch (alt83) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:267:21: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_relationalExprValue1685);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop83;
                        }
                    } while (true);


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
    // $ANTLR end "relationalExprValue"


    // $ANTLR start "evalExprChoice"
    // EsperEPL2Ast.g:271:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR ( ANY | SOME | ALL ) ( valueExpr )* ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR ( ANY | SOME | ALL ) ( valueExpr )* ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree jge=null;
        CommonTree jgne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:272:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(jge= EVAL_EQUALS_GROUP_EXPR ( ANY | SOME | ALL ) ( valueExpr )* ) | ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR ( ANY | SOME | ALL ) ( valueExpr )* ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt89=8;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt89=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt89=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt89=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt89=4;
                }
                break;
            case EVAL_EQUALS_GROUP_EXPR:
                {
                alt89=5;
                }
                break;
            case EVAL_NOTEQUALS_GROUP_EXPR:
                {
                alt89=6;
                }
                break;
            case NOT_EXPR:
                {
                alt89=7;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt89=8;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 89, 0, input);

                throw nvae;
            }

            switch (alt89) {
                case 1 :
                    // EsperEPL2Ast.g:272:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1704); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1706);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1708);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:272:42: ( valueExpr )*
                    loop85:
                    do {
                        int alt85=2;
                        int LA85_0 = input.LA(1);

                        if ( ((LA85_0>=IN_SET && LA85_0<=REGEXP)||LA85_0==NOT_EXPR||(LA85_0>=SUM && LA85_0<=AVG)||(LA85_0>=COALESCE && LA85_0<=COUNT)||(LA85_0>=CASE && LA85_0<=CASE2)||(LA85_0>=PREVIOUS && LA85_0<=EXISTS)||(LA85_0>=INSTANCEOF && LA85_0<=CURRENT_TIMESTAMP)||(LA85_0>=EVAL_AND_EXPR && LA85_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA85_0==EVENT_PROP_EXPR||(LA85_0>=CONCAT && LA85_0<=LIB_FUNCTION)||LA85_0==ARRAY_EXPR||(LA85_0>=NOT_IN_SET && LA85_0<=NOT_REGEXP)||(LA85_0>=IN_RANGE && LA85_0<=NOT_IN_SUBSELECT_EXPR)||LA85_0==SUBSTITUTION||(LA85_0>=INT_TYPE && LA85_0<=NULL_TYPE)||LA85_0==STAR||(LA85_0>=BAND && LA85_0<=BXOR)||(LA85_0>=LT && LA85_0<=GE)||(LA85_0>=PLUS && LA85_0<=MOD)) ) {
                            alt85=1;
                        }


                        switch (alt85) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:272:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1711);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop85;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:273:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1725); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1727);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1729);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:273:43: ( valueExpr )*
                    loop86:
                    do {
                        int alt86=2;
                        int LA86_0 = input.LA(1);

                        if ( ((LA86_0>=IN_SET && LA86_0<=REGEXP)||LA86_0==NOT_EXPR||(LA86_0>=SUM && LA86_0<=AVG)||(LA86_0>=COALESCE && LA86_0<=COUNT)||(LA86_0>=CASE && LA86_0<=CASE2)||(LA86_0>=PREVIOUS && LA86_0<=EXISTS)||(LA86_0>=INSTANCEOF && LA86_0<=CURRENT_TIMESTAMP)||(LA86_0>=EVAL_AND_EXPR && LA86_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA86_0==EVENT_PROP_EXPR||(LA86_0>=CONCAT && LA86_0<=LIB_FUNCTION)||LA86_0==ARRAY_EXPR||(LA86_0>=NOT_IN_SET && LA86_0<=NOT_REGEXP)||(LA86_0>=IN_RANGE && LA86_0<=NOT_IN_SUBSELECT_EXPR)||LA86_0==SUBSTITUTION||(LA86_0>=INT_TYPE && LA86_0<=NULL_TYPE)||LA86_0==STAR||(LA86_0>=BAND && LA86_0<=BXOR)||(LA86_0>=LT && LA86_0<=GE)||(LA86_0>=PLUS && LA86_0<=MOD)) ) {
                            alt86=1;
                        }


                        switch (alt86) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:273:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1732);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop86;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:274:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1746); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1748);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1750);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:275:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1762); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1764);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1766);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:276:4: ^(jge= EVAL_EQUALS_GROUP_EXPR ( ANY | SOME | ALL ) ( valueExpr )* )
                    {
                    jge=(CommonTree)match(input,EVAL_EQUALS_GROUP_EXPR,FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice1778); 

                    match(input, Token.DOWN, null); 
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:276:48: ( valueExpr )*
                    loop87:
                    do {
                        int alt87=2;
                        int LA87_0 = input.LA(1);

                        if ( ((LA87_0>=IN_SET && LA87_0<=REGEXP)||LA87_0==NOT_EXPR||(LA87_0>=SUM && LA87_0<=AVG)||(LA87_0>=COALESCE && LA87_0<=COUNT)||(LA87_0>=CASE && LA87_0<=CASE2)||(LA87_0>=PREVIOUS && LA87_0<=EXISTS)||(LA87_0>=INSTANCEOF && LA87_0<=CURRENT_TIMESTAMP)||(LA87_0>=EVAL_AND_EXPR && LA87_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA87_0==EVENT_PROP_EXPR||(LA87_0>=CONCAT && LA87_0<=LIB_FUNCTION)||LA87_0==ARRAY_EXPR||(LA87_0>=NOT_IN_SET && LA87_0<=NOT_REGEXP)||(LA87_0>=IN_RANGE && LA87_0<=NOT_IN_SUBSELECT_EXPR)||LA87_0==SUBSTITUTION||(LA87_0>=INT_TYPE && LA87_0<=NULL_TYPE)||LA87_0==STAR||(LA87_0>=BAND && LA87_0<=BXOR)||(LA87_0>=LT && LA87_0<=GE)||(LA87_0>=PLUS && LA87_0<=MOD)) ) {
                            alt87=1;
                        }


                        switch (alt87) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:276:48: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1788);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop87;
                        }
                    } while (true);

                     leaveNode(jge); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:277:4: ^(jgne= EVAL_NOTEQUALS_GROUP_EXPR ( ANY | SOME | ALL ) ( valueExpr )* )
                    {
                    jgne=(CommonTree)match(input,EVAL_NOTEQUALS_GROUP_EXPR,FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice1801); 

                    match(input, Token.DOWN, null); 
                    if ( (input.LA(1)>=ALL && input.LA(1)<=SOME) ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:277:52: ( valueExpr )*
                    loop88:
                    do {
                        int alt88=2;
                        int LA88_0 = input.LA(1);

                        if ( ((LA88_0>=IN_SET && LA88_0<=REGEXP)||LA88_0==NOT_EXPR||(LA88_0>=SUM && LA88_0<=AVG)||(LA88_0>=COALESCE && LA88_0<=COUNT)||(LA88_0>=CASE && LA88_0<=CASE2)||(LA88_0>=PREVIOUS && LA88_0<=EXISTS)||(LA88_0>=INSTANCEOF && LA88_0<=CURRENT_TIMESTAMP)||(LA88_0>=EVAL_AND_EXPR && LA88_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA88_0==EVENT_PROP_EXPR||(LA88_0>=CONCAT && LA88_0<=LIB_FUNCTION)||LA88_0==ARRAY_EXPR||(LA88_0>=NOT_IN_SET && LA88_0<=NOT_REGEXP)||(LA88_0>=IN_RANGE && LA88_0<=NOT_IN_SUBSELECT_EXPR)||LA88_0==SUBSTITUTION||(LA88_0>=INT_TYPE && LA88_0<=NULL_TYPE)||LA88_0==STAR||(LA88_0>=BAND && LA88_0<=BXOR)||(LA88_0>=LT && LA88_0<=GE)||(LA88_0>=PLUS && LA88_0<=MOD)) ) {
                            alt88=1;
                        }


                        switch (alt88) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:277:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1811);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop88;
                        }
                    } while (true);

                     leaveNode(jgne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:278:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice1824); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1826);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:279:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice1837);
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
    // EsperEPL2Ast.g:282:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:283:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr[true] | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt90=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt90=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt90=2;
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
                alt90=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt90=4;
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
                alt90=5;
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
                alt90=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt90=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt90=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt90=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt90=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt90=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt90=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt90=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt90=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt90=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt90=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 90, 0, input);

                throw nvae;
            }

            switch (alt90) {
                case 1 :
                    // EsperEPL2Ast.g:283:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr1850);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:284:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr1856);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:285:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr1862);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:286:5: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr1869);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:287:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr1878);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:288:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr1883);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:289:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr1891);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:290:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr1896);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:291:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr1901);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:292:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr1907);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:293:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr1912);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:294:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr1917);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:295:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr1922);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:296:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr1927);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:297:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr1933);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:298:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr1940);
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
    // EsperEPL2Ast.g:301:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:302:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt92=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt92=1;
                }
                break;
            case LW:
                {
                alt92=2;
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
                alt92=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt92=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt92=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt92=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt92=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt92=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt92=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt92=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt92=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 92, 0, input);

                throw nvae;
            }

            switch (alt92) {
                case 1 :
                    // EsperEPL2Ast.g:302:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime1953); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:303:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime1962); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:304:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1969);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:305:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1977); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1979);
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
                    // EsperEPL2Ast.g:306:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime1994);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:307:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime2000);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:308:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime2005);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:309:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime2010);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:310:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2020); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:310:29: ( numericParameterList )+
                    int cnt91=0;
                    loop91:
                    do {
                        int alt91=2;
                        int LA91_0 = input.LA(1);

                        if ( (LA91_0==NUMERIC_PARAM_RANGE||LA91_0==NUMERIC_PARAM_FREQUENCY||(LA91_0>=INT_TYPE && LA91_0<=NULL_TYPE)) ) {
                            alt91=1;
                        }


                        switch (alt91) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:310:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime2022);
                    	    numericParameterList();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    if ( cnt91 >= 1 ) break loop91;
                                EarlyExitException eee =
                                    new EarlyExitException(91, input);
                                throw eee;
                        }
                        cnt91++;
                    } while (true);

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:311:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2033); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:312:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime2040);
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
    // EsperEPL2Ast.g:315:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:316:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt93=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt93=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt93=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:316:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList2053);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:317:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList2060);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:318:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList2066);
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
    // EsperEPL2Ast.g:321:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:322:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:322:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr[true] | substitution ) ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2082); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:322:29: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt94=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt94=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt94=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:322:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2085);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:322:45: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2088);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:322:69: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2091);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:322:83: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt95=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt95=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt95=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:322:84: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator2095);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:322:99: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator2098);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:322:123: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator2101);
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
    // EsperEPL2Ast.g:325:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:326:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:326:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2122); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:326:33: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt96=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt96=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt96=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:326:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator2125);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:326:49: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator2128);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:326:73: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator2131);
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
    // EsperEPL2Ast.g:329:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:330:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:330:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator2150); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:330:23: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt97=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt97=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt97=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt97=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 97, 0, input);

                throw nvae;
            }

            switch (alt97) {
                case 1 :
                    // EsperEPL2Ast.g:330:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator2153);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:330:39: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator2156);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:330:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator2159);
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
    // EsperEPL2Ast.g:333:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:334:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) ) )
            // EsperEPL2Ast.g:334:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr[true] | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2178); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:334:26: ( constant[true] | eventPropertyExpr[true] | substitution )
            int alt98=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt98=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt98=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:334:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator2181);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:334:42: eventPropertyExpr[true]
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator2184);
                    eventPropertyExpr(true);

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:334:66: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator2187);
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


    // $ANTLR start "subSelectRowExpr"
    // EsperEPL2Ast.g:337:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:338:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:338:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2208); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr2210);
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
    // EsperEPL2Ast.g:341:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:342:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:342:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2229); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr2231);
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
    // EsperEPL2Ast.g:345:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:346:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==IN_SUBSELECT_EXPR) ) {
                alt99=1;
            }
            else if ( (LA99_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt99=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // EsperEPL2Ast.g:346:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2250); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2252);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2254);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:347:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2266); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2268);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2270);
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
    // EsperEPL2Ast.g:350:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:351:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:351:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2289); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2291);
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
    // EsperEPL2Ast.g:354:1: subQueryExpr : selectionListElement subSelectFilterExpr ( whereClause )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:355:2: ( selectionListElement subSelectFilterExpr ( whereClause )? )
            // EsperEPL2Ast.g:355:4: selectionListElement subSelectFilterExpr ( whereClause )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2307);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2309);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:355:45: ( whereClause )?
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==WHERE_EXPR) ) {
                alt100=1;
            }
            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:355:46: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2312);
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
    // EsperEPL2Ast.g:358:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:359:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:359:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ( RETAINUNION )? ( RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2329); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2331);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:359:36: ( viewListExpr )?
            int alt101=2;
            int LA101_0 = input.LA(1);

            if ( (LA101_0==VIEW_EXPR) ) {
                alt101=1;
            }
            switch (alt101) {
                case 1 :
                    // EsperEPL2Ast.g:359:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2334);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:359:52: ( IDENT )?
            int alt102=2;
            int LA102_0 = input.LA(1);

            if ( (LA102_0==IDENT) ) {
                alt102=1;
            }
            switch (alt102) {
                case 1 :
                    // EsperEPL2Ast.g:359:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2339); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:359:61: ( RETAINUNION )?
            int alt103=2;
            int LA103_0 = input.LA(1);

            if ( (LA103_0==RETAINUNION) ) {
                alt103=1;
            }
            switch (alt103) {
                case 1 :
                    // EsperEPL2Ast.g:359:61: RETAINUNION
                    {
                    match(input,RETAINUNION,FOLLOW_RETAINUNION_in_subSelectFilterExpr2343); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:359:74: ( RETAININTERSECTION )?
            int alt104=2;
            int LA104_0 = input.LA(1);

            if ( (LA104_0==RETAININTERSECTION) ) {
                alt104=1;
            }
            switch (alt104) {
                case 1 :
                    // EsperEPL2Ast.g:359:74: RETAININTERSECTION
                    {
                    match(input,RETAININTERSECTION,FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2346); 

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
    // EsperEPL2Ast.g:362:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:363:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt107=2;
            int LA107_0 = input.LA(1);

            if ( (LA107_0==CASE) ) {
                alt107=1;
            }
            else if ( (LA107_0==CASE2) ) {
                alt107=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 107, 0, input);

                throw nvae;
            }
            switch (alt107) {
                case 1 :
                    // EsperEPL2Ast.g:363:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2366); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:363:13: ( valueExpr )*
                        loop105:
                        do {
                            int alt105=2;
                            int LA105_0 = input.LA(1);

                            if ( ((LA105_0>=IN_SET && LA105_0<=REGEXP)||LA105_0==NOT_EXPR||(LA105_0>=SUM && LA105_0<=AVG)||(LA105_0>=COALESCE && LA105_0<=COUNT)||(LA105_0>=CASE && LA105_0<=CASE2)||(LA105_0>=PREVIOUS && LA105_0<=EXISTS)||(LA105_0>=INSTANCEOF && LA105_0<=CURRENT_TIMESTAMP)||(LA105_0>=EVAL_AND_EXPR && LA105_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA105_0==EVENT_PROP_EXPR||(LA105_0>=CONCAT && LA105_0<=LIB_FUNCTION)||LA105_0==ARRAY_EXPR||(LA105_0>=NOT_IN_SET && LA105_0<=NOT_REGEXP)||(LA105_0>=IN_RANGE && LA105_0<=NOT_IN_SUBSELECT_EXPR)||LA105_0==SUBSTITUTION||(LA105_0>=INT_TYPE && LA105_0<=NULL_TYPE)||LA105_0==STAR||(LA105_0>=BAND && LA105_0<=BXOR)||(LA105_0>=LT && LA105_0<=GE)||(LA105_0>=PLUS && LA105_0<=MOD)) ) {
                                alt105=1;
                            }


                            switch (alt105) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:363:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2369);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop105;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:364:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2382); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:364:14: ( valueExpr )*
                        loop106:
                        do {
                            int alt106=2;
                            int LA106_0 = input.LA(1);

                            if ( ((LA106_0>=IN_SET && LA106_0<=REGEXP)||LA106_0==NOT_EXPR||(LA106_0>=SUM && LA106_0<=AVG)||(LA106_0>=COALESCE && LA106_0<=COUNT)||(LA106_0>=CASE && LA106_0<=CASE2)||(LA106_0>=PREVIOUS && LA106_0<=EXISTS)||(LA106_0>=INSTANCEOF && LA106_0<=CURRENT_TIMESTAMP)||(LA106_0>=EVAL_AND_EXPR && LA106_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA106_0==EVENT_PROP_EXPR||(LA106_0>=CONCAT && LA106_0<=LIB_FUNCTION)||LA106_0==ARRAY_EXPR||(LA106_0>=NOT_IN_SET && LA106_0<=NOT_REGEXP)||(LA106_0>=IN_RANGE && LA106_0<=NOT_IN_SUBSELECT_EXPR)||LA106_0==SUBSTITUTION||(LA106_0>=INT_TYPE && LA106_0<=NULL_TYPE)||LA106_0==STAR||(LA106_0>=BAND && LA106_0<=BXOR)||(LA106_0>=LT && LA106_0<=GE)||(LA106_0>=PLUS && LA106_0<=MOD)) ) {
                                alt106=1;
                            }


                            switch (alt106) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:364:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2385);
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
    // EsperEPL2Ast.g:367:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:368:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt110=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt110=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt110=2;
                }
                break;
            case IN_RANGE:
                {
                alt110=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt110=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }

            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:368:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2405); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2407);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2415);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:368:51: ( valueExpr )*
                    loop108:
                    do {
                        int alt108=2;
                        int LA108_0 = input.LA(1);

                        if ( ((LA108_0>=IN_SET && LA108_0<=REGEXP)||LA108_0==NOT_EXPR||(LA108_0>=SUM && LA108_0<=AVG)||(LA108_0>=COALESCE && LA108_0<=COUNT)||(LA108_0>=CASE && LA108_0<=CASE2)||(LA108_0>=PREVIOUS && LA108_0<=EXISTS)||(LA108_0>=INSTANCEOF && LA108_0<=CURRENT_TIMESTAMP)||(LA108_0>=EVAL_AND_EXPR && LA108_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA108_0==EVENT_PROP_EXPR||(LA108_0>=CONCAT && LA108_0<=LIB_FUNCTION)||LA108_0==ARRAY_EXPR||(LA108_0>=NOT_IN_SET && LA108_0<=NOT_REGEXP)||(LA108_0>=IN_RANGE && LA108_0<=NOT_IN_SUBSELECT_EXPR)||LA108_0==SUBSTITUTION||(LA108_0>=INT_TYPE && LA108_0<=NULL_TYPE)||LA108_0==STAR||(LA108_0>=BAND && LA108_0<=BXOR)||(LA108_0>=LT && LA108_0<=GE)||(LA108_0>=PLUS && LA108_0<=MOD)) ) {
                            alt108=1;
                        }


                        switch (alt108) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:368:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2418);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop108;
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
                    // EsperEPL2Ast.g:369:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr2437); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2439);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2447);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:369:55: ( valueExpr )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( ((LA109_0>=IN_SET && LA109_0<=REGEXP)||LA109_0==NOT_EXPR||(LA109_0>=SUM && LA109_0<=AVG)||(LA109_0>=COALESCE && LA109_0<=COUNT)||(LA109_0>=CASE && LA109_0<=CASE2)||(LA109_0>=PREVIOUS && LA109_0<=EXISTS)||(LA109_0>=INSTANCEOF && LA109_0<=CURRENT_TIMESTAMP)||(LA109_0>=EVAL_AND_EXPR && LA109_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA109_0==EVENT_PROP_EXPR||(LA109_0>=CONCAT && LA109_0<=LIB_FUNCTION)||LA109_0==ARRAY_EXPR||(LA109_0>=NOT_IN_SET && LA109_0<=NOT_REGEXP)||(LA109_0>=IN_RANGE && LA109_0<=NOT_IN_SUBSELECT_EXPR)||LA109_0==SUBSTITUTION||(LA109_0>=INT_TYPE && LA109_0<=NULL_TYPE)||LA109_0==STAR||(LA109_0>=BAND && LA109_0<=BXOR)||(LA109_0>=LT && LA109_0<=GE)||(LA109_0>=PLUS && LA109_0<=MOD)) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:369:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2450);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop109;
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
                    // EsperEPL2Ast.g:370:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr2469); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2471);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2479);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2481);
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
                    // EsperEPL2Ast.g:371:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr2498); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2500);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2508);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2510);
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
    // EsperEPL2Ast.g:374:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:375:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt112=2;
            int LA112_0 = input.LA(1);

            if ( (LA112_0==BETWEEN) ) {
                alt112=1;
            }
            else if ( (LA112_0==NOT_BETWEEN) ) {
                alt112=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 112, 0, input);

                throw nvae;
            }
            switch (alt112) {
                case 1 :
                    // EsperEPL2Ast.g:375:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr2535); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2537);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2539);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2541);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:376:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr2552); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2554);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2556);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:376:40: ( valueExpr )*
                    loop111:
                    do {
                        int alt111=2;
                        int LA111_0 = input.LA(1);

                        if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA111_0==EVENT_PROP_EXPR||(LA111_0>=CONCAT && LA111_0<=LIB_FUNCTION)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=INT_TYPE && LA111_0<=NULL_TYPE)||LA111_0==STAR||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=PLUS && LA111_0<=MOD)) ) {
                            alt111=1;
                        }


                        switch (alt111) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:376:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr2559);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop111;
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
    // EsperEPL2Ast.g:379:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:380:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==LIKE) ) {
                alt115=1;
            }
            else if ( (LA115_0==NOT_LIKE) ) {
                alt115=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 115, 0, input);

                throw nvae;
            }
            switch (alt115) {
                case 1 :
                    // EsperEPL2Ast.g:380:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr2579); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2581);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2583);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:380:33: ( valueExpr )?
                    int alt113=2;
                    int LA113_0 = input.LA(1);

                    if ( ((LA113_0>=IN_SET && LA113_0<=REGEXP)||LA113_0==NOT_EXPR||(LA113_0>=SUM && LA113_0<=AVG)||(LA113_0>=COALESCE && LA113_0<=COUNT)||(LA113_0>=CASE && LA113_0<=CASE2)||(LA113_0>=PREVIOUS && LA113_0<=EXISTS)||(LA113_0>=INSTANCEOF && LA113_0<=CURRENT_TIMESTAMP)||(LA113_0>=EVAL_AND_EXPR && LA113_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA113_0==EVENT_PROP_EXPR||(LA113_0>=CONCAT && LA113_0<=LIB_FUNCTION)||LA113_0==ARRAY_EXPR||(LA113_0>=NOT_IN_SET && LA113_0<=NOT_REGEXP)||(LA113_0>=IN_RANGE && LA113_0<=NOT_IN_SUBSELECT_EXPR)||LA113_0==SUBSTITUTION||(LA113_0>=INT_TYPE && LA113_0<=NULL_TYPE)||LA113_0==STAR||(LA113_0>=BAND && LA113_0<=BXOR)||(LA113_0>=LT && LA113_0<=GE)||(LA113_0>=PLUS && LA113_0<=MOD)) ) {
                        alt113=1;
                    }
                    switch (alt113) {
                        case 1 :
                            // EsperEPL2Ast.g:380:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2586);
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
                    // EsperEPL2Ast.g:381:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr2599); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2601);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2603);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:381:37: ( valueExpr )?
                    int alt114=2;
                    int LA114_0 = input.LA(1);

                    if ( ((LA114_0>=IN_SET && LA114_0<=REGEXP)||LA114_0==NOT_EXPR||(LA114_0>=SUM && LA114_0<=AVG)||(LA114_0>=COALESCE && LA114_0<=COUNT)||(LA114_0>=CASE && LA114_0<=CASE2)||(LA114_0>=PREVIOUS && LA114_0<=EXISTS)||(LA114_0>=INSTANCEOF && LA114_0<=CURRENT_TIMESTAMP)||(LA114_0>=EVAL_AND_EXPR && LA114_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA114_0==EVENT_PROP_EXPR||(LA114_0>=CONCAT && LA114_0<=LIB_FUNCTION)||LA114_0==ARRAY_EXPR||(LA114_0>=NOT_IN_SET && LA114_0<=NOT_REGEXP)||(LA114_0>=IN_RANGE && LA114_0<=NOT_IN_SUBSELECT_EXPR)||LA114_0==SUBSTITUTION||(LA114_0>=INT_TYPE && LA114_0<=NULL_TYPE)||LA114_0==STAR||(LA114_0>=BAND && LA114_0<=BXOR)||(LA114_0>=LT && LA114_0<=GE)||(LA114_0>=PLUS && LA114_0<=MOD)) ) {
                        alt114=1;
                    }
                    switch (alt114) {
                        case 1 :
                            // EsperEPL2Ast.g:381:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2606);
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
    // EsperEPL2Ast.g:384:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:385:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt116=2;
            int LA116_0 = input.LA(1);

            if ( (LA116_0==REGEXP) ) {
                alt116=1;
            }
            else if ( (LA116_0==NOT_REGEXP) ) {
                alt116=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 116, 0, input);

                throw nvae;
            }
            switch (alt116) {
                case 1 :
                    // EsperEPL2Ast.g:385:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr2625); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2627);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2629);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:386:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr2640); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2642);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2644);
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
    // EsperEPL2Ast.g:389:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr[true] ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:390:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr[true] ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr[true] ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt126=13;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt126=1;
                }
                break;
            case AVG:
                {
                alt126=2;
                }
                break;
            case COUNT:
                {
                alt126=3;
                }
                break;
            case MEDIAN:
                {
                alt126=4;
                }
                break;
            case STDDEV:
                {
                alt126=5;
                }
                break;
            case AVEDEV:
                {
                alt126=6;
                }
                break;
            case COALESCE:
                {
                alt126=7;
                }
                break;
            case PREVIOUS:
                {
                alt126=8;
                }
                break;
            case PRIOR:
                {
                alt126=9;
                }
                break;
            case INSTANCEOF:
                {
                alt126=10;
                }
                break;
            case CAST:
                {
                alt126=11;
                }
                break;
            case EXISTS:
                {
                alt126=12;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt126=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                throw nvae;
            }

            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:390:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc2663); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:390:13: ( DISTINCT )?
                    int alt117=2;
                    int LA117_0 = input.LA(1);

                    if ( (LA117_0==DISTINCT) ) {
                        alt117=1;
                    }
                    switch (alt117) {
                        case 1 :
                            // EsperEPL2Ast.g:390:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2666); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2670);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:391:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc2681); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:391:12: ( DISTINCT )?
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( (LA118_0==DISTINCT) ) {
                        alt118=1;
                    }
                    switch (alt118) {
                        case 1 :
                            // EsperEPL2Ast.g:391:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2684); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2688);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:392:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc2699); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:392:14: ( ( DISTINCT )? valueExpr )?
                        int alt120=2;
                        int LA120_0 = input.LA(1);

                        if ( ((LA120_0>=IN_SET && LA120_0<=REGEXP)||LA120_0==NOT_EXPR||(LA120_0>=SUM && LA120_0<=AVG)||(LA120_0>=COALESCE && LA120_0<=COUNT)||(LA120_0>=CASE && LA120_0<=CASE2)||LA120_0==DISTINCT||(LA120_0>=PREVIOUS && LA120_0<=EXISTS)||(LA120_0>=INSTANCEOF && LA120_0<=CURRENT_TIMESTAMP)||(LA120_0>=EVAL_AND_EXPR && LA120_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA120_0==EVENT_PROP_EXPR||(LA120_0>=CONCAT && LA120_0<=LIB_FUNCTION)||LA120_0==ARRAY_EXPR||(LA120_0>=NOT_IN_SET && LA120_0<=NOT_REGEXP)||(LA120_0>=IN_RANGE && LA120_0<=NOT_IN_SUBSELECT_EXPR)||LA120_0==SUBSTITUTION||(LA120_0>=INT_TYPE && LA120_0<=NULL_TYPE)||LA120_0==STAR||(LA120_0>=BAND && LA120_0<=BXOR)||(LA120_0>=LT && LA120_0<=GE)||(LA120_0>=PLUS && LA120_0<=MOD)) ) {
                            alt120=1;
                        }
                        switch (alt120) {
                            case 1 :
                                // EsperEPL2Ast.g:392:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:392:15: ( DISTINCT )?
                                int alt119=2;
                                int LA119_0 = input.LA(1);

                                if ( (LA119_0==DISTINCT) ) {
                                    alt119=1;
                                }
                                switch (alt119) {
                                    case 1 :
                                        // EsperEPL2Ast.g:392:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2703); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc2707);
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
                    // EsperEPL2Ast.g:393:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc2721); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:393:15: ( DISTINCT )?
                    int alt121=2;
                    int LA121_0 = input.LA(1);

                    if ( (LA121_0==DISTINCT) ) {
                        alt121=1;
                    }
                    switch (alt121) {
                        case 1 :
                            // EsperEPL2Ast.g:393:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2724); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2728);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:394:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc2739); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:394:15: ( DISTINCT )?
                    int alt122=2;
                    int LA122_0 = input.LA(1);

                    if ( (LA122_0==DISTINCT) ) {
                        alt122=1;
                    }
                    switch (alt122) {
                        case 1 :
                            // EsperEPL2Ast.g:394:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2742); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2746);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:395:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc2757); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:395:15: ( DISTINCT )?
                    int alt123=2;
                    int LA123_0 = input.LA(1);

                    if ( (LA123_0==DISTINCT) ) {
                        alt123=1;
                    }
                    switch (alt123) {
                        case 1 :
                            // EsperEPL2Ast.g:395:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2760); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2764);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:396:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc2776); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2778);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2780);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:396:38: ( valueExpr )*
                    loop124:
                    do {
                        int alt124=2;
                        int LA124_0 = input.LA(1);

                        if ( ((LA124_0>=IN_SET && LA124_0<=REGEXP)||LA124_0==NOT_EXPR||(LA124_0>=SUM && LA124_0<=AVG)||(LA124_0>=COALESCE && LA124_0<=COUNT)||(LA124_0>=CASE && LA124_0<=CASE2)||(LA124_0>=PREVIOUS && LA124_0<=EXISTS)||(LA124_0>=INSTANCEOF && LA124_0<=CURRENT_TIMESTAMP)||(LA124_0>=EVAL_AND_EXPR && LA124_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA124_0==EVENT_PROP_EXPR||(LA124_0>=CONCAT && LA124_0<=LIB_FUNCTION)||LA124_0==ARRAY_EXPR||(LA124_0>=NOT_IN_SET && LA124_0<=NOT_REGEXP)||(LA124_0>=IN_RANGE && LA124_0<=NOT_IN_SUBSELECT_EXPR)||LA124_0==SUBSTITUTION||(LA124_0>=INT_TYPE && LA124_0<=NULL_TYPE)||LA124_0==STAR||(LA124_0>=BAND && LA124_0<=BXOR)||(LA124_0>=LT && LA124_0<=GE)||(LA124_0>=PLUS && LA124_0<=MOD)) ) {
                            alt124=1;
                        }


                        switch (alt124) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:396:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc2783);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop124;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:397:5: ^(f= PREVIOUS valueExpr eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc2798); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2800);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2802);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:398:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc2815); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc2819); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2821);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:399:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc2834); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2836);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2838); 
                    // EsperEPL2Ast.g:399:42: ( CLASS_IDENT )*
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( (LA125_0==CLASS_IDENT) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:399:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2841); 

                    	    }
                    	    break;

                    	default :
                    	    break loop125;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:400:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc2855); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2857);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2859); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:401:5: ^(f= EXISTS eventPropertyExpr[true] )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc2871); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2873);
                    eventPropertyExpr(true);

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:402:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2885); 



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
    // EsperEPL2Ast.g:405:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:406:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:406:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr2905); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:406:19: ( valueExpr )*
                loop127:
                do {
                    int alt127=2;
                    int LA127_0 = input.LA(1);

                    if ( ((LA127_0>=IN_SET && LA127_0<=REGEXP)||LA127_0==NOT_EXPR||(LA127_0>=SUM && LA127_0<=AVG)||(LA127_0>=COALESCE && LA127_0<=COUNT)||(LA127_0>=CASE && LA127_0<=CASE2)||(LA127_0>=PREVIOUS && LA127_0<=EXISTS)||(LA127_0>=INSTANCEOF && LA127_0<=CURRENT_TIMESTAMP)||(LA127_0>=EVAL_AND_EXPR && LA127_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA127_0==EVENT_PROP_EXPR||(LA127_0>=CONCAT && LA127_0<=LIB_FUNCTION)||LA127_0==ARRAY_EXPR||(LA127_0>=NOT_IN_SET && LA127_0<=NOT_REGEXP)||(LA127_0>=IN_RANGE && LA127_0<=NOT_IN_SUBSELECT_EXPR)||LA127_0==SUBSTITUTION||(LA127_0>=INT_TYPE && LA127_0<=NULL_TYPE)||LA127_0==STAR||(LA127_0>=BAND && LA127_0<=BXOR)||(LA127_0>=LT && LA127_0<=GE)||(LA127_0>=PLUS && LA127_0<=MOD)) ) {
                        alt127=1;
                    }


                    switch (alt127) {
                	case 1 :
                	    // EsperEPL2Ast.g:406:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr2908);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop127;
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
    // EsperEPL2Ast.g:409:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:410:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt129=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt129=1;
                }
                break;
            case MINUS:
                {
                alt129=2;
                }
                break;
            case DIV:
                {
                alt129=3;
                }
                break;
            case STAR:
                {
                alt129=4;
                }
                break;
            case MOD:
                {
                alt129=5;
                }
                break;
            case BAND:
                {
                alt129=6;
                }
                break;
            case BOR:
                {
                alt129=7;
                }
                break;
            case BXOR:
                {
                alt129=8;
                }
                break;
            case CONCAT:
                {
                alt129=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 129, 0, input);

                throw nvae;
            }

            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:410:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr2929); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2931);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2933);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:411:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr2945); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2947);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2949);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:412:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr2961); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2963);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2965);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:413:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr2976); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2978);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2980);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:414:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr2992); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2994);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2996);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:415:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr3007); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3009);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3011);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:416:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr3022); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3024);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3026);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:417:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr3037); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3039);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3041);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:418:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr3053); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3055);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3057);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:418:36: ( valueExpr )*
                    loop128:
                    do {
                        int alt128=2;
                        int LA128_0 = input.LA(1);

                        if ( ((LA128_0>=IN_SET && LA128_0<=REGEXP)||LA128_0==NOT_EXPR||(LA128_0>=SUM && LA128_0<=AVG)||(LA128_0>=COALESCE && LA128_0<=COUNT)||(LA128_0>=CASE && LA128_0<=CASE2)||(LA128_0>=PREVIOUS && LA128_0<=EXISTS)||(LA128_0>=INSTANCEOF && LA128_0<=CURRENT_TIMESTAMP)||(LA128_0>=EVAL_AND_EXPR && LA128_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA128_0==EVENT_PROP_EXPR||(LA128_0>=CONCAT && LA128_0<=LIB_FUNCTION)||LA128_0==ARRAY_EXPR||(LA128_0>=NOT_IN_SET && LA128_0<=NOT_REGEXP)||(LA128_0>=IN_RANGE && LA128_0<=NOT_IN_SUBSELECT_EXPR)||LA128_0==SUBSTITUTION||(LA128_0>=INT_TYPE && LA128_0<=NULL_TYPE)||LA128_0==STAR||(LA128_0>=BAND && LA128_0<=BXOR)||(LA128_0>=LT && LA128_0<=GE)||(LA128_0>=PLUS && LA128_0<=MOD)) ) {
                            alt128=1;
                        }


                        switch (alt128) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:418:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr3060);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop128;
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
    // EsperEPL2Ast.g:421:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:422:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:422:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc3081); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:422:22: ( CLASS_IDENT )?
            int alt130=2;
            int LA130_0 = input.LA(1);

            if ( (LA130_0==CLASS_IDENT) ) {
                alt130=1;
            }
            switch (alt130) {
                case 1 :
                    // EsperEPL2Ast.g:422:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc3084); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc3088); 
            // EsperEPL2Ast.g:422:43: ( DISTINCT )?
            int alt131=2;
            int LA131_0 = input.LA(1);

            if ( (LA131_0==DISTINCT) ) {
                alt131=1;
            }
            switch (alt131) {
                case 1 :
                    // EsperEPL2Ast.g:422:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc3091); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:422:55: ( valueExpr )*
            loop132:
            do {
                int alt132=2;
                int LA132_0 = input.LA(1);

                if ( ((LA132_0>=IN_SET && LA132_0<=REGEXP)||LA132_0==NOT_EXPR||(LA132_0>=SUM && LA132_0<=AVG)||(LA132_0>=COALESCE && LA132_0<=COUNT)||(LA132_0>=CASE && LA132_0<=CASE2)||(LA132_0>=PREVIOUS && LA132_0<=EXISTS)||(LA132_0>=INSTANCEOF && LA132_0<=CURRENT_TIMESTAMP)||(LA132_0>=EVAL_AND_EXPR && LA132_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA132_0==EVENT_PROP_EXPR||(LA132_0>=CONCAT && LA132_0<=LIB_FUNCTION)||LA132_0==ARRAY_EXPR||(LA132_0>=NOT_IN_SET && LA132_0<=NOT_REGEXP)||(LA132_0>=IN_RANGE && LA132_0<=NOT_IN_SUBSELECT_EXPR)||LA132_0==SUBSTITUTION||(LA132_0>=INT_TYPE && LA132_0<=NULL_TYPE)||LA132_0==STAR||(LA132_0>=BAND && LA132_0<=BXOR)||(LA132_0>=LT && LA132_0<=GE)||(LA132_0>=PLUS && LA132_0<=MOD)) ) {
                    alt132=1;
                }


                switch (alt132) {
            	case 1 :
            	    // EsperEPL2Ast.g:422:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc3096);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop132;
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
    // EsperEPL2Ast.g:428:1: startPatternExpressionRule : exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:429:2: ( exprChoice )
            // EsperEPL2Ast.g:429:4: exprChoice
            {
            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule3116);
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
    // EsperEPL2Ast.g:432:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:433:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(n= PATTERN_NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt136=6;
            switch ( input.LA(1) ) {
            case PATTERN_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt136=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt136=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt136=3;
                }
                break;
            case PATTERN_NOT_EXPR:
                {
                alt136=4;
                }
                break;
            case GUARD_EXPR:
                {
                alt136=5;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt136=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 136, 0, input);

                throw nvae;
            }

            switch (alt136) {
                case 1 :
                    // EsperEPL2Ast.g:433:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice3130);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:434:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice3135);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:435:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice3145); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3147);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:436:5: ^(n= PATTERN_NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,PATTERN_NOT_EXPR,FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3161); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3163);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:437:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice3177); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice3179);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3181); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice3183); 
                    // EsperEPL2Ast.g:437:44: ( valueExprWithTime )*
                    loop133:
                    do {
                        int alt133=2;
                        int LA133_0 = input.LA(1);

                        if ( ((LA133_0>=IN_SET && LA133_0<=REGEXP)||LA133_0==NOT_EXPR||(LA133_0>=SUM && LA133_0<=AVG)||(LA133_0>=COALESCE && LA133_0<=COUNT)||(LA133_0>=CASE && LA133_0<=CASE2)||LA133_0==LAST||(LA133_0>=PREVIOUS && LA133_0<=EXISTS)||(LA133_0>=LW && LA133_0<=CURRENT_TIMESTAMP)||(LA133_0>=NUMERIC_PARAM_RANGE && LA133_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA133_0>=EVAL_AND_EXPR && LA133_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA133_0==EVENT_PROP_EXPR||(LA133_0>=CONCAT && LA133_0<=LIB_FUNCTION)||(LA133_0>=TIME_PERIOD && LA133_0<=ARRAY_EXPR)||(LA133_0>=NOT_IN_SET && LA133_0<=NOT_REGEXP)||(LA133_0>=IN_RANGE && LA133_0<=NOT_IN_SUBSELECT_EXPR)||(LA133_0>=LAST_OPERATOR && LA133_0<=SUBSTITUTION)||(LA133_0>=NUMBERSETSTAR && LA133_0<=NULL_TYPE)||LA133_0==STAR||(LA133_0>=BAND && LA133_0<=BXOR)||(LA133_0>=LT && LA133_0<=GE)||(LA133_0>=PLUS && LA133_0<=MOD)) ) {
                            alt133=1;
                        }


                        switch (alt133) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:437:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice3185);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop133;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:438:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3199); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:438:26: ( matchUntilRange )?
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( ((LA134_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA134_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt134=1;
                    }
                    switch (alt134) {
                        case 1 :
                            // EsperEPL2Ast.g:438:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice3201);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice3204);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:438:54: ( exprChoice )?
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( ((LA135_0>=OR_EXPR && LA135_0<=AND_EXPR)||LA135_0==EVERY_EXPR||LA135_0==FOLLOWED_BY_EXPR||(LA135_0>=PATTERN_FILTER_EXPR && LA135_0<=PATTERN_NOT_EXPR)||(LA135_0>=GUARD_EXPR && LA135_0<=OBSERVER_EXPR)||LA135_0==MATCH_UNTIL_EXPR) ) {
                        alt135=1;
                    }
                    switch (alt135) {
                        case 1 :
                            // EsperEPL2Ast.g:438:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice3206);
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


    // $ANTLR start "patternOp"
    // EsperEPL2Ast.g:441:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:442:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt140=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt140=1;
                }
                break;
            case OR_EXPR:
                {
                alt140=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:442:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3227); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3229);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3231);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:442:48: ( exprChoice )*
                    loop137:
                    do {
                        int alt137=2;
                        int LA137_0 = input.LA(1);

                        if ( ((LA137_0>=OR_EXPR && LA137_0<=AND_EXPR)||LA137_0==EVERY_EXPR||LA137_0==FOLLOWED_BY_EXPR||(LA137_0>=PATTERN_FILTER_EXPR && LA137_0<=PATTERN_NOT_EXPR)||(LA137_0>=GUARD_EXPR && LA137_0<=OBSERVER_EXPR)||LA137_0==MATCH_UNTIL_EXPR) ) {
                            alt137=1;
                        }


                        switch (alt137) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:442:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3234);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop137;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:443:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp3250); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3252);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3254);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:443:40: ( exprChoice )*
                    loop138:
                    do {
                        int alt138=2;
                        int LA138_0 = input.LA(1);

                        if ( ((LA138_0>=OR_EXPR && LA138_0<=AND_EXPR)||LA138_0==EVERY_EXPR||LA138_0==FOLLOWED_BY_EXPR||(LA138_0>=PATTERN_FILTER_EXPR && LA138_0<=PATTERN_NOT_EXPR)||(LA138_0>=GUARD_EXPR && LA138_0<=OBSERVER_EXPR)||LA138_0==MATCH_UNTIL_EXPR) ) {
                            alt138=1;
                        }


                        switch (alt138) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:443:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3257);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop138;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:444:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3273); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3275);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3277);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:444:41: ( exprChoice )*
                    loop139:
                    do {
                        int alt139=2;
                        int LA139_0 = input.LA(1);

                        if ( ((LA139_0>=OR_EXPR && LA139_0<=AND_EXPR)||LA139_0==EVERY_EXPR||LA139_0==FOLLOWED_BY_EXPR||(LA139_0>=PATTERN_FILTER_EXPR && LA139_0<=PATTERN_NOT_EXPR)||(LA139_0>=GUARD_EXPR && LA139_0<=OBSERVER_EXPR)||LA139_0==MATCH_UNTIL_EXPR) ) {
                            alt139=1;
                        }


                        switch (alt139) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:444:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3280);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop139;
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
    // EsperEPL2Ast.g:447:1: atomicExpr : ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:448:2: ( patternFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt142=2;
            int LA142_0 = input.LA(1);

            if ( (LA142_0==PATTERN_FILTER_EXPR) ) {
                alt142=1;
            }
            else if ( (LA142_0==OBSERVER_EXPR) ) {
                alt142=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 142, 0, input);

                throw nvae;
            }
            switch (alt142) {
                case 1 :
                    // EsperEPL2Ast.g:448:4: patternFilterExpr
                    {
                    pushFollow(FOLLOW_patternFilterExpr_in_atomicExpr3299);
                    patternFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:449:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3311); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3313); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3315); 
                    // EsperEPL2Ast.g:449:39: ( valueExprWithTime )*
                    loop141:
                    do {
                        int alt141=2;
                        int LA141_0 = input.LA(1);

                        if ( ((LA141_0>=IN_SET && LA141_0<=REGEXP)||LA141_0==NOT_EXPR||(LA141_0>=SUM && LA141_0<=AVG)||(LA141_0>=COALESCE && LA141_0<=COUNT)||(LA141_0>=CASE && LA141_0<=CASE2)||LA141_0==LAST||(LA141_0>=PREVIOUS && LA141_0<=EXISTS)||(LA141_0>=LW && LA141_0<=CURRENT_TIMESTAMP)||(LA141_0>=NUMERIC_PARAM_RANGE && LA141_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA141_0>=EVAL_AND_EXPR && LA141_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA141_0==EVENT_PROP_EXPR||(LA141_0>=CONCAT && LA141_0<=LIB_FUNCTION)||(LA141_0>=TIME_PERIOD && LA141_0<=ARRAY_EXPR)||(LA141_0>=NOT_IN_SET && LA141_0<=NOT_REGEXP)||(LA141_0>=IN_RANGE && LA141_0<=NOT_IN_SUBSELECT_EXPR)||(LA141_0>=LAST_OPERATOR && LA141_0<=SUBSTITUTION)||(LA141_0>=NUMBERSETSTAR && LA141_0<=NULL_TYPE)||LA141_0==STAR||(LA141_0>=BAND && LA141_0<=BXOR)||(LA141_0>=LT && LA141_0<=GE)||(LA141_0>=PLUS && LA141_0<=MOD)) ) {
                            alt141=1;
                        }


                        switch (alt141) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:449:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3317);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop141;
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
    // EsperEPL2Ast.g:452:1: patternFilterExpr : ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) ;
    public final void patternFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:453:2: ( ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:453:4: ^(f= PATTERN_FILTER_EXPR ( IDENT )? CLASS_IDENT ( propertyExpression )? ( valueExpr )* )
            {
            f=(CommonTree)match(input,PATTERN_FILTER_EXPR,FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3337); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:453:29: ( IDENT )?
            int alt143=2;
            int LA143_0 = input.LA(1);

            if ( (LA143_0==IDENT) ) {
                alt143=1;
            }
            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:453:29: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_patternFilterExpr3339); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_patternFilterExpr3342); 
            // EsperEPL2Ast.g:453:48: ( propertyExpression )?
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( (LA144_0==EVENT_FILTER_PROPERTY_EXPR) ) {
                alt144=1;
            }
            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:453:48: propertyExpression
                    {
                    pushFollow(FOLLOW_propertyExpression_in_patternFilterExpr3344);
                    propertyExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:453:68: ( valueExpr )*
            loop145:
            do {
                int alt145=2;
                int LA145_0 = input.LA(1);

                if ( ((LA145_0>=IN_SET && LA145_0<=REGEXP)||LA145_0==NOT_EXPR||(LA145_0>=SUM && LA145_0<=AVG)||(LA145_0>=COALESCE && LA145_0<=COUNT)||(LA145_0>=CASE && LA145_0<=CASE2)||(LA145_0>=PREVIOUS && LA145_0<=EXISTS)||(LA145_0>=INSTANCEOF && LA145_0<=CURRENT_TIMESTAMP)||(LA145_0>=EVAL_AND_EXPR && LA145_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA145_0==EVENT_PROP_EXPR||(LA145_0>=CONCAT && LA145_0<=LIB_FUNCTION)||LA145_0==ARRAY_EXPR||(LA145_0>=NOT_IN_SET && LA145_0<=NOT_REGEXP)||(LA145_0>=IN_RANGE && LA145_0<=NOT_IN_SUBSELECT_EXPR)||LA145_0==SUBSTITUTION||(LA145_0>=INT_TYPE && LA145_0<=NULL_TYPE)||LA145_0==STAR||(LA145_0>=BAND && LA145_0<=BXOR)||(LA145_0>=LT && LA145_0<=GE)||(LA145_0>=PLUS && LA145_0<=MOD)) ) {
                    alt145=1;
                }


                switch (alt145) {
            	case 1 :
            	    // EsperEPL2Ast.g:453:69: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_patternFilterExpr3348);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop145;
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
    // EsperEPL2Ast.g:456:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:457:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt146=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt146=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt146=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt146=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt146=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 146, 0, input);

                throw nvae;
            }

            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:457:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3366); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3368);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3370);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:458:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3378); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3380);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:459:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3388); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3390);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:460:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3397); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3399);
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
    // EsperEPL2Ast.g:463:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:464:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:468:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:469:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:469:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam3428); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam3430);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:469:35: ( valueExpr )*
            loop147:
            do {
                int alt147=2;
                int LA147_0 = input.LA(1);

                if ( ((LA147_0>=IN_SET && LA147_0<=REGEXP)||LA147_0==NOT_EXPR||(LA147_0>=SUM && LA147_0<=AVG)||(LA147_0>=COALESCE && LA147_0<=COUNT)||(LA147_0>=CASE && LA147_0<=CASE2)||(LA147_0>=PREVIOUS && LA147_0<=EXISTS)||(LA147_0>=INSTANCEOF && LA147_0<=CURRENT_TIMESTAMP)||(LA147_0>=EVAL_AND_EXPR && LA147_0<=EVAL_NOTEQUALS_GROUP_EXPR)||LA147_0==EVENT_PROP_EXPR||(LA147_0>=CONCAT && LA147_0<=LIB_FUNCTION)||LA147_0==ARRAY_EXPR||(LA147_0>=NOT_IN_SET && LA147_0<=NOT_REGEXP)||(LA147_0>=IN_RANGE && LA147_0<=NOT_IN_SUBSELECT_EXPR)||LA147_0==SUBSTITUTION||(LA147_0>=INT_TYPE && LA147_0<=NULL_TYPE)||LA147_0==STAR||(LA147_0>=BAND && LA147_0<=BXOR)||(LA147_0>=LT && LA147_0<=GE)||(LA147_0>=PLUS && LA147_0<=MOD)) ) {
                    alt147=1;
                }


                switch (alt147) {
            	case 1 :
            	    // EsperEPL2Ast.g:469:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam3433);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop147;
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
    // EsperEPL2Ast.g:472:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:473:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt160=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt160=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt160=2;
                }
                break;
            case LT:
                {
                alt160=3;
                }
                break;
            case LE:
                {
                alt160=4;
                }
                break;
            case GT:
                {
                alt160=5;
                }
                break;
            case GE:
                {
                alt160=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt160=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt160=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt160=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt160=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt160=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt160=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 160, 0, input);

                throw nvae;
            }

            switch (alt160) {
                case 1 :
                    // EsperEPL2Ast.g:473:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator3449); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3451);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:474:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator3458); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3460);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:475:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator3467); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3469);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:476:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator3476); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3478);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:477:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator3485); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3487);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:478:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator3494); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3496);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:479:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3503); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:479:41: ( constant[false] | filterIdentifier )
                    int alt148=2;
                    int LA148_0 = input.LA(1);

                    if ( ((LA148_0>=INT_TYPE && LA148_0<=NULL_TYPE)) ) {
                        alt148=1;
                    }
                    else if ( (LA148_0==EVENT_FILTER_IDENT) ) {
                        alt148=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 148, 0, input);

                        throw nvae;
                    }
                    switch (alt148) {
                        case 1 :
                            // EsperEPL2Ast.g:479:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3512);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:479:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3515);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:479:76: ( constant[false] | filterIdentifier )
                    int alt149=2;
                    int LA149_0 = input.LA(1);

                    if ( ((LA149_0>=INT_TYPE && LA149_0<=NULL_TYPE)) ) {
                        alt149=1;
                    }
                    else if ( (LA149_0==EVENT_FILTER_IDENT) ) {
                        alt149=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 149, 0, input);

                        throw nvae;
                    }
                    switch (alt149) {
                        case 1 :
                            // EsperEPL2Ast.g:479:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3519);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:479:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3522);
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
                    // EsperEPL2Ast.g:480:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3536); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:480:45: ( constant[false] | filterIdentifier )
                    int alt150=2;
                    int LA150_0 = input.LA(1);

                    if ( ((LA150_0>=INT_TYPE && LA150_0<=NULL_TYPE)) ) {
                        alt150=1;
                    }
                    else if ( (LA150_0==EVENT_FILTER_IDENT) ) {
                        alt150=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 150, 0, input);

                        throw nvae;
                    }
                    switch (alt150) {
                        case 1 :
                            // EsperEPL2Ast.g:480:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3545);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:480:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3548);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:480:80: ( constant[false] | filterIdentifier )
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( ((LA151_0>=INT_TYPE && LA151_0<=NULL_TYPE)) ) {
                        alt151=1;
                    }
                    else if ( (LA151_0==EVENT_FILTER_IDENT) ) {
                        alt151=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 151, 0, input);

                        throw nvae;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:480:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3552);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:480:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3555);
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
                    // EsperEPL2Ast.g:481:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3569); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:481:38: ( constant[false] | filterIdentifier )
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( ((LA152_0>=INT_TYPE && LA152_0<=NULL_TYPE)) ) {
                        alt152=1;
                    }
                    else if ( (LA152_0==EVENT_FILTER_IDENT) ) {
                        alt152=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 152, 0, input);

                        throw nvae;
                    }
                    switch (alt152) {
                        case 1 :
                            // EsperEPL2Ast.g:481:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3578);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:481:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3581);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:481:73: ( constant[false] | filterIdentifier )*
                    loop153:
                    do {
                        int alt153=3;
                        int LA153_0 = input.LA(1);

                        if ( ((LA153_0>=INT_TYPE && LA153_0<=NULL_TYPE)) ) {
                            alt153=1;
                        }
                        else if ( (LA153_0==EVENT_FILTER_IDENT) ) {
                            alt153=2;
                        }


                        switch (alt153) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:481:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3585);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:481:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3588);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop153;
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
                    // EsperEPL2Ast.g:482:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3603); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:482:42: ( constant[false] | filterIdentifier )
                    int alt154=2;
                    int LA154_0 = input.LA(1);

                    if ( ((LA154_0>=INT_TYPE && LA154_0<=NULL_TYPE)) ) {
                        alt154=1;
                    }
                    else if ( (LA154_0==EVENT_FILTER_IDENT) ) {
                        alt154=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 154, 0, input);

                        throw nvae;
                    }
                    switch (alt154) {
                        case 1 :
                            // EsperEPL2Ast.g:482:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3612);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:482:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3615);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:482:77: ( constant[false] | filterIdentifier )*
                    loop155:
                    do {
                        int alt155=3;
                        int LA155_0 = input.LA(1);

                        if ( ((LA155_0>=INT_TYPE && LA155_0<=NULL_TYPE)) ) {
                            alt155=1;
                        }
                        else if ( (LA155_0==EVENT_FILTER_IDENT) ) {
                            alt155=2;
                        }


                        switch (alt155) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:482:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3619);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:482:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3622);
                    	    filterIdentifier();

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

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:483:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3637); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:483:27: ( constant[false] | filterIdentifier )
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( ((LA156_0>=INT_TYPE && LA156_0<=NULL_TYPE)) ) {
                        alt156=1;
                    }
                    else if ( (LA156_0==EVENT_FILTER_IDENT) ) {
                        alt156=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 156, 0, input);

                        throw nvae;
                    }
                    switch (alt156) {
                        case 1 :
                            // EsperEPL2Ast.g:483:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3640);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:483:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3643);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:483:62: ( constant[false] | filterIdentifier )
                    int alt157=2;
                    int LA157_0 = input.LA(1);

                    if ( ((LA157_0>=INT_TYPE && LA157_0<=NULL_TYPE)) ) {
                        alt157=1;
                    }
                    else if ( (LA157_0==EVENT_FILTER_IDENT) ) {
                        alt157=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 157, 0, input);

                        throw nvae;
                    }
                    switch (alt157) {
                        case 1 :
                            // EsperEPL2Ast.g:483:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3647);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:483:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3650);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:484:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3658); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:484:31: ( constant[false] | filterIdentifier )
                    int alt158=2;
                    int LA158_0 = input.LA(1);

                    if ( ((LA158_0>=INT_TYPE && LA158_0<=NULL_TYPE)) ) {
                        alt158=1;
                    }
                    else if ( (LA158_0==EVENT_FILTER_IDENT) ) {
                        alt158=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 158, 0, input);

                        throw nvae;
                    }
                    switch (alt158) {
                        case 1 :
                            // EsperEPL2Ast.g:484:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3661);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:484:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3664);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:484:66: ( constant[false] | filterIdentifier )
                    int alt159=2;
                    int LA159_0 = input.LA(1);

                    if ( ((LA159_0>=INT_TYPE && LA159_0<=NULL_TYPE)) ) {
                        alt159=1;
                    }
                    else if ( (LA159_0==EVENT_FILTER_IDENT) ) {
                        alt159=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 159, 0, input);

                        throw nvae;
                    }
                    switch (alt159) {
                        case 1 :
                            // EsperEPL2Ast.g:484:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3668);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:484:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3671);
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
    // EsperEPL2Ast.g:487:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:488:2: ( constant[false] | filterIdentifier )
            int alt161=2;
            int LA161_0 = input.LA(1);

            if ( ((LA161_0>=INT_TYPE && LA161_0<=NULL_TYPE)) ) {
                alt161=1;
            }
            else if ( (LA161_0==EVENT_FILTER_IDENT) ) {
                alt161=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 161, 0, input);

                throw nvae;
            }
            switch (alt161) {
                case 1 :
                    // EsperEPL2Ast.g:488:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom3685);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:489:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom3691);
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
    // EsperEPL2Ast.g:491:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:492:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] ) )
            // EsperEPL2Ast.g:492:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr[true] )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3702); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier3704); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier3706);
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
    // EsperEPL2Ast.g:495:1: eventPropertyExpr[boolean isLeaveNode] : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr(boolean isLeaveNode) throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:496:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:496:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3725); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3727);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:496:44: ( eventPropertyAtomic )*
            loop162:
            do {
                int alt162=2;
                int LA162_0 = input.LA(1);

                if ( ((LA162_0>=EVENT_PROP_SIMPLE && LA162_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt162=1;
                }


                switch (alt162) {
            	case 1 :
            	    // EsperEPL2Ast.g:496:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3730);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop162;
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
    // EsperEPL2Ast.g:499:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:500:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt163=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt163=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt163=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt163=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt163=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt163=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt163=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 163, 0, input);

                throw nvae;
            }

            switch (alt163) {
                case 1 :
                    // EsperEPL2Ast.g:500:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3749); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3751); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:501:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3758); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3760); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3762); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:502:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3769); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3771); 
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
                    // EsperEPL2Ast.g:503:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3786); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3788); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:504:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3795); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3797); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3799); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:505:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3806); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3808); 
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
    // EsperEPL2Ast.g:508:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:509:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:509:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod3835); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod3837);
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
    // EsperEPL2Ast.g:512:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:513:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt174=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt174=1;
                }
                break;
            case HOUR_PART:
                {
                alt174=2;
                }
                break;
            case MINUTE_PART:
                {
                alt174=3;
                }
                break;
            case SECOND_PART:
                {
                alt174=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt174=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 174, 0, input);

                throw nvae;
            }

            switch (alt174) {
                case 1 :
                    // EsperEPL2Ast.g:513:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef3853);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:513:13: ( hourPart )?
                    int alt164=2;
                    int LA164_0 = input.LA(1);

                    if ( (LA164_0==HOUR_PART) ) {
                        alt164=1;
                    }
                    switch (alt164) {
                        case 1 :
                            // EsperEPL2Ast.g:513:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef3856);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:513:25: ( minutePart )?
                    int alt165=2;
                    int LA165_0 = input.LA(1);

                    if ( (LA165_0==MINUTE_PART) ) {
                        alt165=1;
                    }
                    switch (alt165) {
                        case 1 :
                            // EsperEPL2Ast.g:513:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3861);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:513:39: ( secondPart )?
                    int alt166=2;
                    int LA166_0 = input.LA(1);

                    if ( (LA166_0==SECOND_PART) ) {
                        alt166=1;
                    }
                    switch (alt166) {
                        case 1 :
                            // EsperEPL2Ast.g:513:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3866);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:513:53: ( millisecondPart )?
                    int alt167=2;
                    int LA167_0 = input.LA(1);

                    if ( (LA167_0==MILLISECOND_PART) ) {
                        alt167=1;
                    }
                    switch (alt167) {
                        case 1 :
                            // EsperEPL2Ast.g:513:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3871);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:514:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef3878);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:514:13: ( minutePart )?
                    int alt168=2;
                    int LA168_0 = input.LA(1);

                    if ( (LA168_0==MINUTE_PART) ) {
                        alt168=1;
                    }
                    switch (alt168) {
                        case 1 :
                            // EsperEPL2Ast.g:514:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3881);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:514:27: ( secondPart )?
                    int alt169=2;
                    int LA169_0 = input.LA(1);

                    if ( (LA169_0==SECOND_PART) ) {
                        alt169=1;
                    }
                    switch (alt169) {
                        case 1 :
                            // EsperEPL2Ast.g:514:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3886);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:514:41: ( millisecondPart )?
                    int alt170=2;
                    int LA170_0 = input.LA(1);

                    if ( (LA170_0==MILLISECOND_PART) ) {
                        alt170=1;
                    }
                    switch (alt170) {
                        case 1 :
                            // EsperEPL2Ast.g:514:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3891);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:515:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef3898);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:515:15: ( secondPart )?
                    int alt171=2;
                    int LA171_0 = input.LA(1);

                    if ( (LA171_0==SECOND_PART) ) {
                        alt171=1;
                    }
                    switch (alt171) {
                        case 1 :
                            // EsperEPL2Ast.g:515:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3901);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:515:29: ( millisecondPart )?
                    int alt172=2;
                    int LA172_0 = input.LA(1);

                    if ( (LA172_0==MILLISECOND_PART) ) {
                        alt172=1;
                    }
                    switch (alt172) {
                        case 1 :
                            // EsperEPL2Ast.g:515:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3906);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:516:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef3913);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:516:15: ( millisecondPart )?
                    int alt173=2;
                    int LA173_0 = input.LA(1);

                    if ( (LA173_0==MILLISECOND_PART) ) {
                        alt173=1;
                    }
                    switch (alt173) {
                        case 1 :
                            // EsperEPL2Ast.g:516:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3916);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:517:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3923);
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
    // EsperEPL2Ast.g:520:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:521:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:521:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart3937); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart3939);
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
    // EsperEPL2Ast.g:524:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:525:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:525:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart3954); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart3956);
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
    // EsperEPL2Ast.g:528:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:529:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:529:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart3971); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart3973);
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
    // EsperEPL2Ast.g:532:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:533:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:533:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart3988); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart3990);
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
    // EsperEPL2Ast.g:536:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:537:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:537:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart4005); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart4007);
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
    // EsperEPL2Ast.g:540:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:541:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:541:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution4022); 
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
    // EsperEPL2Ast.g:544:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:545:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt175=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt175=1;
                }
                break;
            case LONG_TYPE:
                {
                alt175=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt175=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt175=4;
                }
                break;
            case STRING_TYPE:
                {
                alt175=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt175=6;
                }
                break;
            case NULL_TYPE:
                {
                alt175=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 175, 0, input);

                throw nvae;
            }

            switch (alt175) {
                case 1 :
                    // EsperEPL2Ast.g:545:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant4038); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:546:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant4047); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:547:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant4056); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:548:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant4065); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:549:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant4081); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:550:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant4097); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:551:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant4110); 
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
    // EsperEPL2Ast.g:554:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:555:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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


 

    public static final BitSet FOLLOW_EPL_EXPR_in_startEPLExpressionRule89 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eplExpressionRule_in_startEPLExpressionRule91 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectExpr_in_eplExpressionRule108 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createWindowExpr_in_eplExpressionRule112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_createVariableExpr_in_eplExpressionRule116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_onExpr_in_eplExpressionRule120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_in_onExpr139 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000058L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000058L});
    public static final BitSet FOLLOW_IDENT_in_onExpr149 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000058L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr186 = new BitSet(new long[]{0x0000000000000008L,0x4000000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr189 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr206 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr209 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000600L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000000020L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr215 = new BitSet(new long[]{0x0000000000000008L,0xC000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr218 = new BitSet(new long[]{0x0000000000000008L,0x8000000000000000L,0x0000000000060000L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr223 = new BitSet(new long[]{0x0000000000000008L,0x8000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr228 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr252 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr255 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment270 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr309 = new BitSet(new long[]{0xC000000000000000L,0x0900000000000000L,0x0000000000000000L,0x0000000000004002L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr312 = new BitSet(new long[]{0xC000000000000000L,0x0900000000000000L,0x0000000000000000L,0x0000000000004002L});
    public static final BitSet FOLLOW_RETAINUNION_in_createWindowExpr316 = new BitSet(new long[]{0xC000000000000000L,0x0900000000000000L,0x0000000000000000L,0x0000000000004002L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_createWindowExpr319 = new BitSet(new long[]{0xC000000000000000L,0x0900000000000000L,0x0000000000000000L,0x0000000000004002L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr333 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr336 = new BitSet(new long[]{0x0020000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr365 = new BitSet(new long[]{0x0020000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr376 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert394 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert396 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList413 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList415 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0008000000000200L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList418 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0008000000000200L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList439 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList442 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement457 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement459 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement461 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement475 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement505 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement509 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement531 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement534 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr570 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr572 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr574 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr577 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr595 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr601 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr606 = new BitSet(new long[]{0x0000000000000002L,0xC000000100000000L,0x00000000B8060000L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr611 = new BitSet(new long[]{0x0000000000000002L,0x8000000100000000L,0x00000000B8060000L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr618 = new BitSet(new long[]{0x0000000000000002L,0x8000000100000000L,0x00000000B8040000L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr625 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L,0x00000000B8040000L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr632 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L,0x0000000000040000L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr639 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr663 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr665 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr674 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr677 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol696 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol698 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol701 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause719 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause721 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000600L});
    public static final BitSet FOLLOW_selectionList_in_selectClause734 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause748 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause751 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000001E800L});
    public static final BitSet FOLLOW_outerJoin_in_fromClause754 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000001E800L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList771 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000600L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList774 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0008000000000600L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement790 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement800 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement802 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement805 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement819 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement821 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement824 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin843 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent857 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent859 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent862 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent866 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent869 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent884 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent886 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent889 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent893 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent896 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent911 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent913 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent916 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent920 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent923 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent938 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent940 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent943 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent947 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent950 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression971 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression974 = new BitSet(new long[]{0xE000000000000008L,0x0800000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression978 = new BitSet(new long[]{0xE000000000000008L,0x0800000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression982 = new BitSet(new long[]{0xE000000000000008L,0x0800000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression986 = new BitSet(new long[]{0xE000000000000008L,0x0800000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression990 = new BitSet(new long[]{0xE000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression995 = new BitSet(new long[]{0xE000000000000008L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression1000 = new BitSet(new long[]{0xC000000000000008L});
    public static final BitSet FOLLOW_set_in_streamExpression1004 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr1028 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr1030 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr1033 = new BitSet(new long[]{0x000000001BE623C8L,0x0000080000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_propertyExpression_in_eventFilterExpr1035 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr1039 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_in_propertyExpression1059 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertyExpressionAtom_in_propertyExpression1061 = new BitSet(new long[]{0x0000000000000008L,0x0000100000000000L});
    public static final BitSet FOLLOW_EVENT_FILTER_PROPERTY_EXPR_ATOM_in_propertyExpressionAtom1080 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_propertySelectionListElement_in_propertyExpressionAtom1082 = new BitSet(new long[]{0x0000000000000000L,0x0000E00000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_propertyExpressionAtom1085 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_propertyExpressionAtom1088 = new BitSet(new long[]{0x0000000000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_propertyExpressionAtom1092 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertyExpressionAtom1094 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_WILDCARD_SELECT_in_propertySelectionListElement1114 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_ELEMENT_EXPR_in_propertySelectionListElement1124 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_propertySelectionListElement1126 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1129 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PROPERTY_SELECTION_STREAM_in_propertySelectionListElement1143 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1145 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_propertySelectionListElement1148 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1169 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1171 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1188 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1190 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1192 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1200 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1221 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1223 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1225 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1228 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1242 = new BitSet(new long[]{0x0000000000000002L,0x0800000000000000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1245 = new BitSet(new long[]{0x0000000000000002L,0x0800000000000000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1262 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1264 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1266 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1269 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1290 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1292 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1310 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1312 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1315 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1333 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1335 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1338 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1358 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1360 = new BitSet(new long[]{0x0300000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1362 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1385 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1387 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1405 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1407 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000041E0000L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1419 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1421 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1438 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1440 = new BitSet(new long[]{0x001000001BE623C0L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1451 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1466 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1468 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1479 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1494 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1496 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr1507 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000058L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr1509 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1528 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1531 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x0000000000000000L,0x000000000C1E0000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1533 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x0000000000000000L,0x000000000C1E0000L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1537 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1539 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause1543 = new BitSet(new long[]{0x0000000000000008L,0x0000000200000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause1546 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1564 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1566 = new BitSet(new long[]{0x001000001BE623C0L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1568 = new BitSet(new long[]{0x001000001BE623C0L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1570 = new BitSet(new long[]{0x001000001BE623C0L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1572 = new BitSet(new long[]{0x001000001BE623C0L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1574 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1576 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr1593 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1595 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr1608 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1610 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr1623 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1625 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr1637 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_relationalExprValue_in_relationalExpr1639 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1661 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1663 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_relationalExprValue1677 = new BitSet(new long[]{0x000000001BE623C2L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExprValue1685 = new BitSet(new long[]{0x000000001BE623C2L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1704 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1706 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1708 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1711 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1725 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1727 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1729 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1732 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1746 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1748 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1750 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1762 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1764 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1766 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_EQUALS_GROUP_EXPR_in_evalExprChoice1778 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_evalExprChoice1780 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1788 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_GROUP_EXPR_in_evalExprChoice1801 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_evalExprChoice1803 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1811 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice1824 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1826 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice1837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr1850 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr1856 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr1862 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr1869 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr1878 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr1883 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr1891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr1896 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr1901 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr1907 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr1912 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr1917 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr1922 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr1927 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr1933 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr1940 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime1953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime1962 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1969 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1977 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1979 = new BitSet(new long[]{0x0300000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime1981 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime1994 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime2000 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime2005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime2010 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime2020 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime2022 = new BitSet(new long[]{0x0000000000000008L,0x0000001400000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime2033 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime2040 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList2053 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList2060 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList2066 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator2082 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2085 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000100000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2088 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000100000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2091 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x4000000000100000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_rangeOperator2095 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator2098 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator2101 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator2122 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator2125 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator2128 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator2131 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator2150 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator2153 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator2156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator2159 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator2178 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator2181 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator2184 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator2187 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr2208 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr2210 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr2229 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr2231 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr2250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2254 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2266 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2268 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0800000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2270 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2289 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2291 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2307 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000800L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2309 = new BitSet(new long[]{0x0000000000000002L,0x4000000000000000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2329 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2331 = new BitSet(new long[]{0xC000000000000008L,0x0800000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2334 = new BitSet(new long[]{0xC000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2339 = new BitSet(new long[]{0xC000000000000008L});
    public static final BitSet FOLLOW_RETAINUNION_in_subSelectFilterExpr2343 = new BitSet(new long[]{0x8000000000000008L});
    public static final BitSet FOLLOW_RETAININTERSECTION_in_subSelectFilterExpr2346 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2369 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2382 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2385 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2405 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2407 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_set_in_inExpr2409 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2415 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C580FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2418 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C580FE0000L});
    public static final BitSet FOLLOW_set_in_inExpr2422 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr2437 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2439 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_set_in_inExpr2441 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2447 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C580FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2450 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C580FE0000L});
    public static final BitSet FOLLOW_set_in_inExpr2454 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr2469 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2471 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_set_in_inExpr2473 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2479 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2481 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_set_in_inExpr2483 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr2498 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2500 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_set_in_inExpr2502 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2508 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2510 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_set_in_inExpr2512 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr2535 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2537 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2539 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2541 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr2552 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2554 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2556 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2559 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr2579 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2581 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2583 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2586 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr2599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2601 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2603 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2606 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr2625 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2627 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2629 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr2640 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2642 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2644 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc2663 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2666 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc2681 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2684 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2688 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc2699 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2703 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2707 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc2721 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2724 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2728 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc2739 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2742 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2746 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc2757 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2760 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2764 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc2776 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2778 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2780 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2783 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc2798 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2800 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2802 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc2815 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc2819 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2821 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc2834 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2836 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2838 = new BitSet(new long[]{0x0000000000000008L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2841 = new BitSet(new long[]{0x0000000000000008L,0x0100000000000000L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc2855 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2857 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2859 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc2871 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2873 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2885 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr2905 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr2908 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr2929 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2931 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2933 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr2945 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2947 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2949 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr2961 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2963 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2965 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr2976 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2978 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2980 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr2992 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2994 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2996 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr3007 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3009 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3011 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr3022 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3024 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3026 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr3037 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3039 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3041 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr3053 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3055 = new BitSet(new long[]{0x000000001BE623C0L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3057 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr3060 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc3081 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc3084 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc3088 = new BitSet(new long[]{0x000020001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc3091 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc3096 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule3116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice3130 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice3135 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice3145 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3147 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_NOT_EXPR_in_exprChoice3161 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3163 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice3177 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3179 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3181 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice3183 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice3185 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice3199 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice3201 = new BitSet(new long[]{0x0000000000005800L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3204 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice3206 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp3227 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3229 = new BitSet(new long[]{0x0000000000005800L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3231 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3234 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp3250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3252 = new BitSet(new long[]{0x0000000000005800L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3254 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3257 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3273 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3275 = new BitSet(new long[]{0x0000000000005800L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3277 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3280 = new BitSet(new long[]{0x0000000000005808L,0x0600034000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_patternFilterExpr_in_atomicExpr3299 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3311 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3313 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3315 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3317 = new BitSet(new long[]{0x001000001BE623C8L,0x0000003C000007B8L,0x77E0F06C0010007EL,0x000F79C100FF0000L});
    public static final BitSet FOLLOW_PATTERN_FILTER_EXPR_in_patternFilterExpr3337 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_patternFilterExpr3339 = new BitSet(new long[]{0x0000000000000000L,0x0100000000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_patternFilterExpr3342 = new BitSet(new long[]{0x000000001BE623C8L,0x0000080000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_propertyExpression_in_patternFilterExpr3344 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_patternFilterExpr3348 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3366 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3368 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0040000001000000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3378 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3380 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3388 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3397 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3399 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam3428 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3430 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3433 = new BitSet(new long[]{0x000000001BE623C8L,0x0000000000000738L,0x47E0F04C0010007EL,0x000F79C100FE0000L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator3449 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3451 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator3458 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3460 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator3467 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3469 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator3476 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3478 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator3485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3487 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator3494 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3496 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3503 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3505 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3512 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3515 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3519 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3522 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3525 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3538 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3545 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3548 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3552 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3555 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000480000000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3558 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3569 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3571 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3578 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3581 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3585 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3588 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3592 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3603 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3605 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3612 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3615 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3619 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3622 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000480FE0000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3626 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3637 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3640 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3643 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3647 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3650 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3658 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3661 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3664 = new BitSet(new long[]{0x0000000000000000L,0x0001000000000000L,0x0000000000000000L,0x0000000000FE0000L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3668 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3671 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom3685 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom3691 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3702 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier3704 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000100000L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier3706 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3725 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3727 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000007E00000L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3730 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000007E00000L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3749 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3751 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3758 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3760 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3762 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3769 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3771 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3773 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3786 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3788 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3795 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3797 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0040000000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3799 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3806 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3808 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000003000000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3810 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod3835 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod3837 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef3853 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000F0000000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3856 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000E0000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3861 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3866 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3871 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3878 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000E0000000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3881 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3886 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3891 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3898 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000C0000000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3901 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3906 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3913 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3916 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3923 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart3937 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart3939 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart3954 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart3956 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart3971 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart3973 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart3988 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart3990 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart4005 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart4007 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution4022 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant4038 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant4047 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant4056 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant4065 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant4081 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant4097 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant4110 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}