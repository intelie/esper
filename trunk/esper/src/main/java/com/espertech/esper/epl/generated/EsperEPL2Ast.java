// $ANTLR 3.1.1 EsperEPL2Ast.g 2008-12-01 14:57:52

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "CREATE", "WINDOW", "IN_SET", "BETWEEN", "LIKE", "REGEXP", "ESCAPE", "OR_EXPR", "AND_EXPR", "NOT_EXPR", "EVERY_EXPR", "WHERE", "AS", "SUM", "AVG", "MAX", "MIN", "COALESCE", "MEDIAN", "STDDEV", "AVEDEV", "COUNT", "SELECT", "CASE", "CASE2", "ELSE", "WHEN", "THEN", "END", "FROM", "OUTER", "INNER", "JOIN", "LEFT", "RIGHT", "FULL", "ON", "IS", "BY", "GROUP", "HAVING", "DISTINCT", "ALL", "OUTPUT", "EVENTS", "FIRST", "LAST", "INSERT", "INTO", "ORDER", "ASC", "DESC", "RSTREAM", "ISTREAM", "IRSTREAM", "UNIDIRECTIONAL", "RETAINUNION", "RETAININTERSECTION", "PATTERN", "SQL", "METADATASQL", "PREVIOUS", "PRIOR", "EXISTS", "WEEKDAY", "LW", "INSTANCEOF", "CAST", "CURRENT_TIMESTAMP", "DELETE", "SNAPSHOT", "SET", "VARIABLE", "UNTIL", "AT", "TIMEPERIOD_DAY", "TIMEPERIOD_DAYS", "TIMEPERIOD_HOUR", "TIMEPERIOD_HOURS", "TIMEPERIOD_MINUTE", "TIMEPERIOD_MINUTES", "TIMEPERIOD_SEC", "TIMEPERIOD_SECOND", "TIMEPERIOD_SECONDS", "TIMEPERIOD_MILLISEC", "TIMEPERIOD_MILLISECOND", "TIMEPERIOD_MILLISECONDS", "BOOLEAN_TRUE", "BOOLEAN_FALSE", "VALUE_NULL", "ROW_LIMIT_EXPR", "OFFSET", "NUMERIC_PARAM_RANGE", "NUMERIC_PARAM_LIST", "NUMERIC_PARAM_FREQUENCY", "OBJECT_PARAM_ORDERED_EXPR", "FOLLOWED_BY_EXPR", "ARRAY_PARAM_LIST", "EVENT_FILTER_EXPR", "EVENT_FILTER_IDENT", "EVENT_FILTER_PARAM", "EVENT_FILTER_RANGE", "EVENT_FILTER_NOT_RANGE", "EVENT_FILTER_IN", "EVENT_FILTER_NOT_IN", "EVENT_FILTER_BETWEEN", "EVENT_FILTER_NOT_BETWEEN", "CLASS_IDENT", "GUARD_EXPR", "OBSERVER_EXPR", "VIEW_EXPR", "PATTERN_INCL_EXPR", "DATABASE_JOIN_EXPR", "WHERE_EXPR", "HAVING_EXPR", "EVAL_BITWISE_EXPR", "EVAL_AND_EXPR", "EVAL_OR_EXPR", "EVAL_EQUALS_EXPR", "EVAL_NOTEQUALS_EXPR", "EVAL_IDENT", "SELECTION_EXPR", "SELECTION_ELEMENT_EXPR", "SELECTION_STREAM", "STREAM_EXPR", "OUTERJOIN_EXPR", "INNERJOIN_EXPR", "LEFT_OUTERJOIN_EXPR", "RIGHT_OUTERJOIN_EXPR", "FULL_OUTERJOIN_EXPR", "GROUP_BY_EXPR", "ORDER_BY_EXPR", "ORDER_ELEMENT_EXPR", "EVENT_PROP_EXPR", "EVENT_PROP_SIMPLE", "EVENT_PROP_MAPPED", "EVENT_PROP_INDEXED", "EVENT_PROP_DYNAMIC_SIMPLE", "EVENT_PROP_DYNAMIC_INDEXED", "EVENT_PROP_DYNAMIC_MAPPED", "EVENT_LIMIT_EXPR", "TIMEPERIOD_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR", "CRONTAB_LIMIT_EXPR_PARAM", "WHEN_LIMIT_EXPR", "INSERTINTO_EXPR", "INSERTINTO_EXPRCOL", "CONCAT", "LIB_FUNCTION", "UNARY_MINUS", "TIME_PERIOD", "ARRAY_EXPR", "DAY_PART", "HOUR_PART", "MINUTE_PART", "SECOND_PART", "MILLISECOND_PART", "NOT_IN_SET", "NOT_BETWEEN", "NOT_LIKE", "NOT_REGEXP", "DBSELECT_EXPR", "DBFROM_CLAUSE", "DBWHERE_CLAUSE", "WILDCARD_SELECT", "INSERTINTO_STREAM_NAME", "IN_RANGE", "NOT_IN_RANGE", "SUBSELECT_EXPR", "EXISTS_SUBSELECT_EXPR", "IN_SUBSELECT_EXPR", "NOT_IN_SUBSELECT_EXPR", "IN_SUBSELECT_QUERY_EXPR", "LAST_OPERATOR", "WEEKDAY_OPERATOR", "SUBSTITUTION", "CAST_EXPR", "CREATE_WINDOW_EXPR", "CREATE_WINDOW_SELECT_EXPR", "ON_EXPR", "ON_DELETE_EXPR", "ON_SELECT_EXPR", "ON_EXPR_FROM", "ON_SET_EXPR", "CREATE_VARIABLE_EXPR", "METHOD_JOIN_EXPR", "MATCH_UNTIL_EXPR", "MATCH_UNTIL_RANGE_HALFOPEN", "MATCH_UNTIL_RANGE_HALFCLOSED", "MATCH_UNTIL_RANGE_CLOSED", "MATCH_UNTIL_RANGE_BOUNDED", "CREATE_WINDOW_COL_TYPE_LIST", "CREATE_WINDOW_COL_TYPE", "NUMBERSETSTAR", "INT_TYPE", "LONG_TYPE", "FLOAT_TYPE", "DOUBLE_TYPE", "STRING_TYPE", "BOOL_TYPE", "NULL_TYPE", "NUM_DOUBLE", "EPL_EXPR", "IDENT", "COMMA", "EQUALS", "DOT", "LPAREN", "RPAREN", "STAR", "LBRACK", "RBRACK", "COLON", "STRING_LITERAL", "QUOTED_STRING_LITERAL", "BAND", "BOR", "BXOR", "SQL_NE", "NOT_EQUAL", "LT", "GT", "LE", "GE", "LOR", "PLUS", "MINUS", "DIV", "MOD", "LCURLY", "RCURLY", "NUM_INT", "FOLLOWED_BY", "QUESTION", "ESCAPECHAR", "NUM_LONG", "NUM_FLOAT", "EQUAL", "LNOT", "BNOT", "DIV_ASSIGN", "PLUS_ASSIGN", "INC", "MINUS_ASSIGN", "DEC", "STAR_ASSIGN", "MOD_ASSIGN", "SR", "SR_ASSIGN", "BSR", "BSR_ASSIGN", "SL", "SL_ASSIGN", "BXOR_ASSIGN", "BOR_ASSIGN", "BAND_ASSIGN", "LAND", "SEMI", "EMAILAT", "WS", "SL_COMMENT", "ML_COMMENT", "EscapeSequence", "UnicodeEscape", "OctalEscape", "HexDigit", "EXPONENT", "FLOAT_SUFFIX"
    };
    public static final int CRONTAB_LIMIT_EXPR=146;
    public static final int FLOAT_SUFFIX=271;
    public static final int STAR=213;
    public static final int NUMERIC_PARAM_LIST=97;
    public static final int ISTREAM=57;
    public static final int MOD=232;
    public static final int OUTERJOIN_EXPR=129;
    public static final int BSR=253;
    public static final int LIB_FUNCTION=152;
    public static final int EOF=-1;
    public static final int TIMEPERIOD_MILLISECONDS=90;
    public static final int FULL_OUTERJOIN_EXPR=133;
    public static final int RPAREN=212;
    public static final int LNOT=242;
    public static final int INC=246;
    public static final int CREATE=4;
    public static final int STRING_LITERAL=217;
    public static final int BSR_ASSIGN=254;
    public static final int STREAM_EXPR=128;
    public static final int CAST_EXPR=180;
    public static final int TIMEPERIOD_SECONDS=87;
    public static final int NOT_EQUAL=223;
    public static final int METADATASQL=64;
    public static final int REGEXP=9;
    public static final int FOLLOWED_BY_EXPR=100;
    public static final int FOLLOWED_BY=236;
    public static final int HOUR_PART=157;
    public static final int RBRACK=215;
    public static final int MATCH_UNTIL_RANGE_CLOSED=193;
    public static final int GE=227;
    public static final int METHOD_JOIN_EXPR=189;
    public static final int ASC=54;
    public static final int IN_SET=6;
    public static final int EVENT_FILTER_EXPR=102;
    public static final int MINUS_ASSIGN=247;
    public static final int ELSE=29;
    public static final int EVENT_FILTER_NOT_IN=108;
    public static final int NUM_DOUBLE=205;
    public static final int INSERTINTO_STREAM_NAME=169;
    public static final int UNARY_MINUS=153;
    public static final int LCURLY=233;
    public static final int TIMEPERIOD_MILLISEC=88;
    public static final int RETAINUNION=60;
    public static final int DBWHERE_CLAUSE=167;
    public static final int MEDIAN=22;
    public static final int EVENTS=48;
    public static final int AND_EXPR=12;
    public static final int EVENT_FILTER_NOT_RANGE=106;
    public static final int GROUP=43;
    public static final int EMAILAT=262;
    public static final int WS=263;
    public static final int ESCAPECHAR=238;
    public static final int SL_COMMENT=264;
    public static final int NULL_TYPE=204;
    public static final int MATCH_UNTIL_RANGE_HALFOPEN=191;
    public static final int GT=225;
    public static final int BNOT=243;
    public static final int WHERE_EXPR=117;
    public static final int END=32;
    public static final int INNERJOIN_EXPR=130;
    public static final int LAND=260;
    public static final int NOT_REGEXP=164;
    public static final int MATCH_UNTIL_EXPR=190;
    public static final int EVENT_PROP_EXPR=137;
    public static final int LBRACK=214;
    public static final int VIEW_EXPR=114;
    public static final int LONG_TYPE=199;
    public static final int TIMEPERIOD_SEC=85;
    public static final int ON_SELECT_EXPR=185;
    public static final int MINUTE_PART=158;
    public static final int SUM=17;
    public static final int SQL_NE=222;
    public static final int HexDigit=269;
    public static final int LPAREN=211;
    public static final int IN_SUBSELECT_EXPR=174;
    public static final int AT=78;
    public static final int AS=16;
    public static final int OR_EXPR=11;
    public static final int BOOLEAN_TRUE=91;
    public static final int THEN=31;
    public static final int NOT_IN_RANGE=171;
    public static final int OFFSET=95;
    public static final int LEFT=37;
    public static final int AVG=18;
    public static final int SECOND_PART=159;
    public static final int PREVIOUS=65;
    public static final int DATABASE_JOIN_EXPR=116;
    public static final int IDENT=207;
    public static final int PLUS=229;
    public static final int BXOR=221;
    public static final int CASE2=28;
    public static final int TIMEPERIOD_DAY=79;
    public static final int EXISTS=67;
    public static final int EVENT_PROP_INDEXED=140;
    public static final int TIMEPERIOD_MILLISECOND=89;
    public static final int EVAL_NOTEQUALS_EXPR=123;
    public static final int MATCH_UNTIL_RANGE_HALFCLOSED=192;
    public static final int CREATE_VARIABLE_EXPR=188;
    public static final int CREATE_WINDOW_COL_TYPE=196;
    public static final int LIKE=8;
    public static final int OUTER=34;
    public static final int BY=42;
    public static final int ARRAY_PARAM_LIST=101;
    public static final int RIGHT_OUTERJOIN_EXPR=132;
    public static final int NUMBERSETSTAR=197;
    public static final int LAST_OPERATOR=177;
    public static final int EVAL_AND_EXPR=120;
    public static final int LEFT_OUTERJOIN_EXPR=131;
    public static final int EPL_EXPR=206;
    public static final int GROUP_BY_EXPR=134;
    public static final int SET=75;
    public static final int RIGHT=38;
    public static final int HAVING=44;
    public static final int INSTANCEOF=70;
    public static final int MIN=20;
    public static final int EVENT_PROP_SIMPLE=138;
    public static final int MINUS=230;
    public static final int SEMI=261;
    public static final int STAR_ASSIGN=249;
    public static final int COLON=216;
    public static final int BAND_ASSIGN=259;
    public static final int CRONTAB_LIMIT_EXPR_PARAM=147;
    public static final int VALUE_NULL=93;
    public static final int NOT_IN_SET=161;
    public static final int EVENT_PROP_DYNAMIC_SIMPLE=141;
    public static final int SL=255;
    public static final int WHEN=30;
    public static final int NOT_IN_SUBSELECT_EXPR=175;
    public static final int GUARD_EXPR=112;
    public static final int SR=251;
    public static final int RCURLY=234;
    public static final int PLUS_ASSIGN=245;
    public static final int DAY_PART=156;
    public static final int EXISTS_SUBSELECT_EXPR=173;
    public static final int EVENT_FILTER_IN=107;
    public static final int DIV=231;
    public static final int OBJECT_PARAM_ORDERED_EXPR=99;
    public static final int OctalEscape=268;
    public static final int BETWEEN=7;
    public static final int MILLISECOND_PART=160;
    public static final int PRIOR=66;
    public static final int FIRST=49;
    public static final int ROW_LIMIT_EXPR=94;
    public static final int SELECTION_EXPR=125;
    public static final int LOR=228;
    public static final int CAST=71;
    public static final int LW=69;
    public static final int WILDCARD_SELECT=168;
    public static final int EXPONENT=270;
    public static final int LT=224;
    public static final int PATTERN_INCL_EXPR=115;
    public static final int ORDER_BY_EXPR=135;
    public static final int BOOL_TYPE=203;
    public static final int MOD_ASSIGN=250;
    public static final int CASE=27;
    public static final int IN_SUBSELECT_QUERY_EXPR=176;
    public static final int EQUALS=209;
    public static final int COUNT=25;
    public static final int RETAININTERSECTION=61;
    public static final int DIV_ASSIGN=244;
    public static final int SL_ASSIGN=256;
    public static final int PATTERN=62;
    public static final int SQL=63;
    public static final int WEEKDAY=68;
    public static final int FULL=39;
    public static final int INSERT=51;
    public static final int ESCAPE=10;
    public static final int ARRAY_EXPR=155;
    public static final int LAST=50;
    public static final int BOOLEAN_FALSE=92;
    public static final int SELECT=26;
    public static final int INTO=52;
    public static final int FLOAT_TYPE=200;
    public static final int TIMEPERIOD_SECOND=86;
    public static final int COALESCE=21;
    public static final int EVENT_FILTER_BETWEEN=109;
    public static final int SUBSELECT_EXPR=172;
    public static final int NUMERIC_PARAM_RANGE=96;
    public static final int CONCAT=151;
    public static final int CLASS_IDENT=111;
    public static final int ON_EXPR=183;
    public static final int CREATE_WINDOW_EXPR=181;
    public static final int ON_DELETE_EXPR=184;
    public static final int NUM_LONG=239;
    public static final int ON=40;
    public static final int TIME_PERIOD=154;
    public static final int DOUBLE_TYPE=201;
    public static final int DELETE=73;
    public static final int INT_TYPE=198;
    public static final int EVAL_BITWISE_EXPR=119;
    public static final int EVERY_EXPR=14;
    public static final int ORDER_ELEMENT_EXPR=136;
    public static final int TIMEPERIOD_HOURS=82;
    public static final int VARIABLE=76;
    public static final int SUBSTITUTION=179;
    public static final int UNTIL=77;
    public static final int STRING_TYPE=202;
    public static final int ON_SET_EXPR=187;
    public static final int NUM_INT=235;
    public static final int STDDEV=23;
    public static final int ON_EXPR_FROM=186;
    public static final int NUM_FLOAT=240;
    public static final int FROM=33;
    public static final int DISTINCT=45;
    public static final int OUTPUT=47;
    public static final int EscapeSequence=266;
    public static final int WEEKDAY_OPERATOR=178;
    public static final int WHERE=15;
    public static final int CREATE_WINDOW_COL_TYPE_LIST=195;
    public static final int DEC=248;
    public static final int INNER=35;
    public static final int NUMERIC_PARAM_FREQUENCY=98;
    public static final int BXOR_ASSIGN=257;
    public static final int ORDER=53;
    public static final int SNAPSHOT=74;
    public static final int EVENT_PROP_DYNAMIC_MAPPED=143;
    public static final int EVENT_FILTER_PARAM=104;
    public static final int IRSTREAM=58;
    public static final int MAX=19;
    public static final int TIMEPERIOD_DAYS=80;
    public static final int EVENT_FILTER_RANGE=105;
    public static final int ML_COMMENT=265;
    public static final int EVENT_PROP_DYNAMIC_INDEXED=142;
    public static final int BOR_ASSIGN=258;
    public static final int COMMA=208;
    public static final int WHEN_LIMIT_EXPR=148;
    public static final int IS=41;
    public static final int TIMEPERIOD_LIMIT_EXPR=145;
    public static final int ALL=46;
    public static final int TIMEPERIOD_HOUR=81;
    public static final int BOR=220;
    public static final int EQUAL=241;
    public static final int EVENT_FILTER_NOT_BETWEEN=110;
    public static final int IN_RANGE=170;
    public static final int DOT=210;
    public static final int CURRENT_TIMESTAMP=72;
    public static final int INSERTINTO_EXPR=149;
    public static final int HAVING_EXPR=118;
    public static final int UNIDIRECTIONAL=59;
    public static final int MATCH_UNTIL_RANGE_BOUNDED=194;
    public static final int EVAL_EQUALS_EXPR=122;
    public static final int TIMEPERIOD_MINUTES=84;
    public static final int RSTREAM=56;
    public static final int NOT_LIKE=163;
    public static final int EVENT_LIMIT_EXPR=144;
    public static final int NOT_BETWEEN=162;
    public static final int TIMEPERIOD_MINUTE=83;
    public static final int EVAL_OR_EXPR=121;
    public static final int BAND=219;
    public static final int QUOTED_STRING_LITERAL=218;
    public static final int JOIN=36;
    public static final int NOT_EXPR=13;
    public static final int QUESTION=237;
    public static final int OBSERVER_EXPR=113;
    public static final int EVENT_FILTER_IDENT=103;
    public static final int EVENT_PROP_MAPPED=139;
    public static final int UnicodeEscape=267;
    public static final int AVEDEV=24;
    public static final int DBSELECT_EXPR=165;
    public static final int SELECTION_ELEMENT_EXPR=126;
    public static final int CREATE_WINDOW_SELECT_EXPR=182;
    public static final int INSERTINTO_EXPRCOL=150;
    public static final int WINDOW=5;
    public static final int DESC=55;
    public static final int SELECTION_STREAM=127;
    public static final int SR_ASSIGN=252;
    public static final int DBFROM_CLAUSE=166;
    public static final int LE=226;
    public static final int EVAL_IDENT=124;

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

      // For pattern processing within EPL and for create pattern
      protected void setIsPatternWalk(boolean isPatternWalk) {};
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
    // EsperEPL2Ast.g:58:1: startEPLExpressionRule : ^( EPL_EXPR eplExpressionRule ) ;
    public final void startEPLExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:59:2: ( ^( EPL_EXPR eplExpressionRule ) )
            // EsperEPL2Ast.g:59:4: ^( EPL_EXPR eplExpressionRule )
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
    // EsperEPL2Ast.g:62:1: eplExpressionRule : ( selectExpr | createWindowExpr | createVariableExpr | onExpr ) ;
    public final void eplExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:63:2: ( ( selectExpr | createWindowExpr | createVariableExpr | onExpr ) )
            // EsperEPL2Ast.g:63:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr )
            {
            // EsperEPL2Ast.g:63:4: ( selectExpr | createWindowExpr | createVariableExpr | onExpr )
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
                    // EsperEPL2Ast.g:63:5: selectExpr
                    {
                    pushFollow(FOLLOW_selectExpr_in_eplExpressionRule108);
                    selectExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:63:18: createWindowExpr
                    {
                    pushFollow(FOLLOW_createWindowExpr_in_eplExpressionRule112);
                    createWindowExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:63:37: createVariableExpr
                    {
                    pushFollow(FOLLOW_createVariableExpr_in_eplExpressionRule116);
                    createVariableExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:63:58: onExpr
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
    // EsperEPL2Ast.g:66:1: onExpr : ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr | onSetExpr ) ) ;
    public final void onExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:67:2: ( ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr | onSetExpr ) ) )
            // EsperEPL2Ast.g:67:4: ^(i= ON_EXPR ( eventFilterExpr | patternInclusionExpression ) ( IDENT )? ( onDeleteExpr | onSelectExpr | onSetExpr ) )
            {
            i=(CommonTree)match(input,ON_EXPR,FOLLOW_ON_EXPR_in_onExpr139); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:67:16: ( eventFilterExpr | patternInclusionExpression )
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
                    // EsperEPL2Ast.g:67:17: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_onExpr142);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:67:35: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_onExpr146);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:67:63: ( IDENT )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==IDENT) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // EsperEPL2Ast.g:67:63: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_onExpr149); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:68:3: ( onDeleteExpr | onSelectExpr | onSetExpr )
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
                    // EsperEPL2Ast.g:68:4: onDeleteExpr
                    {
                    pushFollow(FOLLOW_onDeleteExpr_in_onExpr156);
                    onDeleteExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:68:19: onSelectExpr
                    {
                    pushFollow(FOLLOW_onSelectExpr_in_onExpr160);
                    onSelectExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:68:34: onSetExpr
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
    // EsperEPL2Ast.g:72:1: onDeleteExpr : ^( ON_DELETE_EXPR onExprFrom ( whereClause )? ) ;
    public final void onDeleteExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:73:2: ( ^( ON_DELETE_EXPR onExprFrom ( whereClause )? ) )
            // EsperEPL2Ast.g:73:4: ^( ON_DELETE_EXPR onExprFrom ( whereClause )? )
            {
            match(input,ON_DELETE_EXPR,FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr184); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onExprFrom_in_onDeleteExpr186);
            onExprFrom();

            state._fsp--;

            // EsperEPL2Ast.g:73:32: ( whereClause )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==WHERE_EXPR) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // EsperEPL2Ast.g:73:33: whereClause
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
    // EsperEPL2Ast.g:76:1: onSelectExpr : ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList onExprFrom ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) ;
    public final void onSelectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:77:2: ( ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList onExprFrom ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? ) )
            // EsperEPL2Ast.g:77:4: ^( ON_SELECT_EXPR ( insertIntoExpr )? selectionList onExprFrom ( whereClause )? ( groupByClause )? ( havingClause )? ( orderByClause )? )
            {
            match(input,ON_SELECT_EXPR,FOLLOW_ON_SELECT_EXPR_in_onSelectExpr206); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:77:21: ( insertIntoExpr )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==INSERTINTO_EXPR) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // EsperEPL2Ast.g:77:22: insertIntoExpr
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

            // EsperEPL2Ast.g:77:64: ( whereClause )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==WHERE_EXPR) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // EsperEPL2Ast.g:77:65: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_onSelectExpr218);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:77:79: ( groupByClause )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==GROUP_BY_EXPR) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // EsperEPL2Ast.g:77:80: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_onSelectExpr223);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:77:96: ( havingClause )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==HAVING_EXPR) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // EsperEPL2Ast.g:77:97: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_onSelectExpr228);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:77:112: ( orderByClause )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==ORDER_BY_EXPR) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // EsperEPL2Ast.g:77:113: orderByClause
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
    // EsperEPL2Ast.g:80:1: onSetExpr : ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ) ;
    public final void onSetExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:81:2: ( ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* ) )
            // EsperEPL2Ast.g:81:4: ^( ON_SET_EXPR onSetAssignment ( onSetAssignment )* )
            {
            match(input,ON_SET_EXPR,FOLLOW_ON_SET_EXPR_in_onSetExpr250); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_onSetAssignment_in_onSetExpr252);
            onSetAssignment();

            state._fsp--;

            // EsperEPL2Ast.g:81:34: ( onSetAssignment )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==IDENT) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // EsperEPL2Ast.g:81:35: onSetAssignment
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
    // EsperEPL2Ast.g:84:1: onSetAssignment : IDENT valueExpr ;
    public final void onSetAssignment() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:85:2: ( IDENT valueExpr )
            // EsperEPL2Ast.g:85:4: IDENT valueExpr
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
    // EsperEPL2Ast.g:88:1: onExprFrom : ^( ON_EXPR_FROM IDENT ( IDENT )? ) ;
    public final void onExprFrom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:89:2: ( ^( ON_EXPR_FROM IDENT ( IDENT )? ) )
            // EsperEPL2Ast.g:89:4: ^( ON_EXPR_FROM IDENT ( IDENT )? )
            {
            match(input,ON_EXPR_FROM,FOLLOW_ON_EXPR_FROM_in_onExprFrom284); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_onExprFrom286); 
            // EsperEPL2Ast.g:89:25: ( IDENT )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==IDENT) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // EsperEPL2Ast.g:89:26: IDENT
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
    // EsperEPL2Ast.g:92:1: createWindowExpr : ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) ;
    public final void createWindowExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:93:2: ( ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? ) )
            // EsperEPL2Ast.g:93:4: ^(i= CREATE_WINDOW_EXPR IDENT ( viewListExpr )? ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) ) ( createWindowExprInsert )? )
            {
            i=(CommonTree)match(input,CREATE_WINDOW_EXPR,FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr307); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createWindowExpr309); 
            // EsperEPL2Ast.g:93:33: ( viewListExpr )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==VIEW_EXPR) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // EsperEPL2Ast.g:93:34: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_createWindowExpr312);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:94:4: ( ( ( createSelectionList )? CLASS_IDENT ) | ( createColTypeList ) )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( (LA15_0==CLASS_IDENT||LA15_0==CREATE_WINDOW_SELECT_EXPR) ) {
                alt15=1;
            }
            else if ( (LA15_0==CREATE_WINDOW_COL_TYPE_LIST) ) {
                alt15=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // EsperEPL2Ast.g:95:5: ( ( createSelectionList )? CLASS_IDENT )
                    {
                    // EsperEPL2Ast.g:95:5: ( ( createSelectionList )? CLASS_IDENT )
                    // EsperEPL2Ast.g:95:6: ( createSelectionList )? CLASS_IDENT
                    {
                    // EsperEPL2Ast.g:95:6: ( createSelectionList )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0==CREATE_WINDOW_SELECT_EXPR) ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // EsperEPL2Ast.g:95:6: createSelectionList
                            {
                            pushFollow(FOLLOW_createSelectionList_in_createWindowExpr327);
                            createSelectionList();

                            state._fsp--;


                            }
                            break;

                    }

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_createWindowExpr330); 

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:97:12: ( createColTypeList )
                    {
                    // EsperEPL2Ast.g:97:12: ( createColTypeList )
                    // EsperEPL2Ast.g:97:13: createColTypeList
                    {
                    pushFollow(FOLLOW_createColTypeList_in_createWindowExpr359);
                    createColTypeList();

                    state._fsp--;


                    }


                    }
                    break;

            }

            // EsperEPL2Ast.g:99:4: ( createWindowExprInsert )?
            int alt16=2;
            int LA16_0 = input.LA(1);

            if ( (LA16_0==INSERT) ) {
                alt16=1;
            }
            switch (alt16) {
                case 1 :
                    // EsperEPL2Ast.g:99:4: createWindowExprInsert
                    {
                    pushFollow(FOLLOW_createWindowExprInsert_in_createWindowExpr370);
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
    // EsperEPL2Ast.g:103:1: createWindowExprInsert : ^( INSERT ( valueExpr )? ) ;
    public final void createWindowExprInsert() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:104:2: ( ^( INSERT ( valueExpr )? ) )
            // EsperEPL2Ast.g:104:4: ^( INSERT ( valueExpr )? )
            {
            match(input,INSERT,FOLLOW_INSERT_in_createWindowExprInsert388); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:104:13: ( valueExpr )?
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( ((LA17_0>=IN_SET && LA17_0<=REGEXP)||LA17_0==NOT_EXPR||(LA17_0>=SUM && LA17_0<=AVG)||(LA17_0>=COALESCE && LA17_0<=COUNT)||(LA17_0>=CASE && LA17_0<=CASE2)||(LA17_0>=PREVIOUS && LA17_0<=EXISTS)||(LA17_0>=INSTANCEOF && LA17_0<=CURRENT_TIMESTAMP)||(LA17_0>=EVAL_AND_EXPR && LA17_0<=EVAL_NOTEQUALS_EXPR)||LA17_0==EVENT_PROP_EXPR||(LA17_0>=CONCAT && LA17_0<=LIB_FUNCTION)||LA17_0==ARRAY_EXPR||(LA17_0>=NOT_IN_SET && LA17_0<=NOT_REGEXP)||(LA17_0>=IN_RANGE && LA17_0<=NOT_IN_SUBSELECT_EXPR)||LA17_0==SUBSTITUTION||(LA17_0>=INT_TYPE && LA17_0<=NULL_TYPE)||LA17_0==STAR||(LA17_0>=BAND && LA17_0<=BXOR)||(LA17_0>=LT && LA17_0<=GE)||(LA17_0>=PLUS && LA17_0<=MOD)) ) {
                    alt17=1;
                }
                switch (alt17) {
                    case 1 :
                        // EsperEPL2Ast.g:104:13: valueExpr
                        {
                        pushFollow(FOLLOW_valueExpr_in_createWindowExprInsert390);
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
    // EsperEPL2Ast.g:107:1: createSelectionList : ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) ;
    public final void createSelectionList() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:108:2: ( ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* ) )
            // EsperEPL2Ast.g:108:4: ^(s= CREATE_WINDOW_SELECT_EXPR createSelectionListElement ( createSelectionListElement )* )
            {
            s=(CommonTree)match(input,CREATE_WINDOW_SELECT_EXPR,FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList407); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList409);
            createSelectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:108:61: ( createSelectionListElement )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==SELECTION_ELEMENT_EXPR||LA18_0==WILDCARD_SELECT) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // EsperEPL2Ast.g:108:62: createSelectionListElement
            	    {
            	    pushFollow(FOLLOW_createSelectionListElement_in_createSelectionList412);
            	    createSelectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop18;
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
    // EsperEPL2Ast.g:111:1: createColTypeList : ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) ;
    public final void createColTypeList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:112:2: ( ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* ) )
            // EsperEPL2Ast.g:112:4: ^( CREATE_WINDOW_COL_TYPE_LIST createColTypeListElement ( createColTypeListElement )* )
            {
            match(input,CREATE_WINDOW_COL_TYPE_LIST,FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList431); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList433);
            createColTypeListElement();

            state._fsp--;

            // EsperEPL2Ast.g:112:59: ( createColTypeListElement )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( (LA19_0==CREATE_WINDOW_COL_TYPE) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // EsperEPL2Ast.g:112:60: createColTypeListElement
            	    {
            	    pushFollow(FOLLOW_createColTypeListElement_in_createColTypeList436);
            	    createColTypeListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop19;
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
    // EsperEPL2Ast.g:115:1: createColTypeListElement : ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) ;
    public final void createColTypeListElement() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:116:2: ( ^( CREATE_WINDOW_COL_TYPE IDENT IDENT ) )
            // EsperEPL2Ast.g:116:4: ^( CREATE_WINDOW_COL_TYPE IDENT IDENT )
            {
            match(input,CREATE_WINDOW_COL_TYPE,FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement451); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement453); 
            match(input,IDENT,FOLLOW_IDENT_in_createColTypeListElement455); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:119:1: createSelectionListElement : (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) ) ) );
    public final void createSelectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:120:2: (w= WILDCARD_SELECT | ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) ) ) )
            int alt22=2;
            int LA22_0 = input.LA(1);

            if ( (LA22_0==WILDCARD_SELECT) ) {
                alt22=1;
            }
            else if ( (LA22_0==SELECTION_ELEMENT_EXPR) ) {
                alt22=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }
            switch (alt22) {
                case 1 :
                    // EsperEPL2Ast.g:120:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_createSelectionListElement469); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:121:4: ^(s= SELECTION_ELEMENT_EXPR ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) ) )
                    {
                    s=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement479); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:121:31: ( ( eventPropertyExpr ( IDENT )? ) | ( constant[true] IDENT ) )
                    int alt21=2;
                    int LA21_0 = input.LA(1);

                    if ( (LA21_0==EVENT_PROP_EXPR) ) {
                        alt21=1;
                    }
                    else if ( ((LA21_0>=INT_TYPE && LA21_0<=NULL_TYPE)) ) {
                        alt21=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 21, 0, input);

                        throw nvae;
                    }
                    switch (alt21) {
                        case 1 :
                            // EsperEPL2Ast.g:122:16: ( eventPropertyExpr ( IDENT )? )
                            {
                            // EsperEPL2Ast.g:122:16: ( eventPropertyExpr ( IDENT )? )
                            // EsperEPL2Ast.g:122:17: eventPropertyExpr ( IDENT )?
                            {
                            pushFollow(FOLLOW_eventPropertyExpr_in_createSelectionListElement499);
                            eventPropertyExpr();

                            state._fsp--;

                            // EsperEPL2Ast.g:122:35: ( IDENT )?
                            int alt20=2;
                            int LA20_0 = input.LA(1);

                            if ( (LA20_0==IDENT) ) {
                                alt20=1;
                            }
                            switch (alt20) {
                                case 1 :
                                    // EsperEPL2Ast.g:122:36: IDENT
                                    {
                                    match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement502); 

                                    }
                                    break;

                            }


                            }


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:123:16: ( constant[true] IDENT )
                            {
                            // EsperEPL2Ast.g:123:16: ( constant[true] IDENT )
                            // EsperEPL2Ast.g:123:17: constant[true] IDENT
                            {
                            pushFollow(FOLLOW_constant_in_createSelectionListElement524);
                            constant(true);

                            state._fsp--;

                            match(input,IDENT,FOLLOW_IDENT_in_createSelectionListElement527); 

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
    // EsperEPL2Ast.g:127:1: createVariableExpr : ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) ;
    public final void createVariableExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:128:2: ( ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? ) )
            // EsperEPL2Ast.g:128:4: ^(i= CREATE_VARIABLE_EXPR IDENT IDENT ( valueExpr )? )
            {
            i=(CommonTree)match(input,CREATE_VARIABLE_EXPR,FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr563); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr565); 
            match(input,IDENT,FOLLOW_IDENT_in_createVariableExpr567); 
            // EsperEPL2Ast.g:128:41: ( valueExpr )?
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( ((LA23_0>=IN_SET && LA23_0<=REGEXP)||LA23_0==NOT_EXPR||(LA23_0>=SUM && LA23_0<=AVG)||(LA23_0>=COALESCE && LA23_0<=COUNT)||(LA23_0>=CASE && LA23_0<=CASE2)||(LA23_0>=PREVIOUS && LA23_0<=EXISTS)||(LA23_0>=INSTANCEOF && LA23_0<=CURRENT_TIMESTAMP)||(LA23_0>=EVAL_AND_EXPR && LA23_0<=EVAL_NOTEQUALS_EXPR)||LA23_0==EVENT_PROP_EXPR||(LA23_0>=CONCAT && LA23_0<=LIB_FUNCTION)||LA23_0==ARRAY_EXPR||(LA23_0>=NOT_IN_SET && LA23_0<=NOT_REGEXP)||(LA23_0>=IN_RANGE && LA23_0<=NOT_IN_SUBSELECT_EXPR)||LA23_0==SUBSTITUTION||(LA23_0>=INT_TYPE && LA23_0<=NULL_TYPE)||LA23_0==STAR||(LA23_0>=BAND && LA23_0<=BXOR)||(LA23_0>=LT && LA23_0<=GE)||(LA23_0>=PLUS && LA23_0<=MOD)) ) {
                alt23=1;
            }
            switch (alt23) {
                case 1 :
                    // EsperEPL2Ast.g:128:42: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_createVariableExpr570);
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
    // EsperEPL2Ast.g:131:1: selectExpr : ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? ;
    public final void selectExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:132:2: ( ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )? )
            // EsperEPL2Ast.g:132:4: ( insertIntoExpr )? selectClause fromClause ( whereClause )? ( groupByClause )? ( havingClause )? ( outputLimitExpr )? ( orderByClause )? ( rowLimitClause )?
            {
            // EsperEPL2Ast.g:132:4: ( insertIntoExpr )?
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==INSERTINTO_EXPR) ) {
                alt24=1;
            }
            switch (alt24) {
                case 1 :
                    // EsperEPL2Ast.g:132:5: insertIntoExpr
                    {
                    pushFollow(FOLLOW_insertIntoExpr_in_selectExpr588);
                    insertIntoExpr();

                    state._fsp--;


                    }
                    break;

            }

            pushFollow(FOLLOW_selectClause_in_selectExpr594);
            selectClause();

            state._fsp--;

            pushFollow(FOLLOW_fromClause_in_selectExpr599);
            fromClause();

            state._fsp--;

            // EsperEPL2Ast.g:135:3: ( whereClause )?
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==WHERE_EXPR) ) {
                alt25=1;
            }
            switch (alt25) {
                case 1 :
                    // EsperEPL2Ast.g:135:4: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_selectExpr604);
                    whereClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:136:3: ( groupByClause )?
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==GROUP_BY_EXPR) ) {
                alt26=1;
            }
            switch (alt26) {
                case 1 :
                    // EsperEPL2Ast.g:136:4: groupByClause
                    {
                    pushFollow(FOLLOW_groupByClause_in_selectExpr611);
                    groupByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:137:3: ( havingClause )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==HAVING_EXPR) ) {
                alt27=1;
            }
            switch (alt27) {
                case 1 :
                    // EsperEPL2Ast.g:137:4: havingClause
                    {
                    pushFollow(FOLLOW_havingClause_in_selectExpr618);
                    havingClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:138:3: ( outputLimitExpr )?
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=EVENT_LIMIT_EXPR && LA28_0<=CRONTAB_LIMIT_EXPR)||LA28_0==WHEN_LIMIT_EXPR) ) {
                alt28=1;
            }
            switch (alt28) {
                case 1 :
                    // EsperEPL2Ast.g:138:4: outputLimitExpr
                    {
                    pushFollow(FOLLOW_outputLimitExpr_in_selectExpr625);
                    outputLimitExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:139:3: ( orderByClause )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==ORDER_BY_EXPR) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EsperEPL2Ast.g:139:4: orderByClause
                    {
                    pushFollow(FOLLOW_orderByClause_in_selectExpr632);
                    orderByClause();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:140:3: ( rowLimitClause )?
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==ROW_LIMIT_EXPR) ) {
                alt30=1;
            }
            switch (alt30) {
                case 1 :
                    // EsperEPL2Ast.g:140:4: rowLimitClause
                    {
                    pushFollow(FOLLOW_rowLimitClause_in_selectExpr639);
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
    // EsperEPL2Ast.g:143:1: insertIntoExpr : ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) ;
    public final void insertIntoExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:144:2: ( ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? ) )
            // EsperEPL2Ast.g:144:4: ^(i= INSERTINTO_EXPR ( ISTREAM | RSTREAM )? IDENT ( insertIntoExprCol )? )
            {
            i=(CommonTree)match(input,INSERTINTO_EXPR,FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr656); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:144:24: ( ISTREAM | RSTREAM )?
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=RSTREAM && LA31_0<=ISTREAM)) ) {
                alt31=1;
            }
            switch (alt31) {
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

            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExpr667); 
            // EsperEPL2Ast.g:144:51: ( insertIntoExprCol )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==INSERTINTO_EXPRCOL) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EsperEPL2Ast.g:144:52: insertIntoExprCol
                    {
                    pushFollow(FOLLOW_insertIntoExprCol_in_insertIntoExpr670);
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
    // EsperEPL2Ast.g:147:1: insertIntoExprCol : ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) ;
    public final void insertIntoExprCol() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:148:2: ( ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* ) )
            // EsperEPL2Ast.g:148:4: ^( INSERTINTO_EXPRCOL IDENT ( IDENT )* )
            {
            match(input,INSERTINTO_EXPRCOL,FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol689); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol691); 
            // EsperEPL2Ast.g:148:31: ( IDENT )*
            loop33:
            do {
                int alt33=2;
                int LA33_0 = input.LA(1);

                if ( (LA33_0==IDENT) ) {
                    alt33=1;
                }


                switch (alt33) {
            	case 1 :
            	    // EsperEPL2Ast.g:148:32: IDENT
            	    {
            	    match(input,IDENT,FOLLOW_IDENT_in_insertIntoExprCol694); 

            	    }
            	    break;

            	default :
            	    break loop33;
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
    // EsperEPL2Ast.g:151:1: selectClause : ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) ;
    public final void selectClause() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:152:2: ( ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList ) )
            // EsperEPL2Ast.g:152:4: ^(s= SELECTION_EXPR ( RSTREAM | ISTREAM | IRSTREAM )? selectionList )
            {
            s=(CommonTree)match(input,SELECTION_EXPR,FOLLOW_SELECTION_EXPR_in_selectClause712); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:152:23: ( RSTREAM | ISTREAM | IRSTREAM )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( ((LA34_0>=RSTREAM && LA34_0<=IRSTREAM)) ) {
                alt34=1;
            }
            switch (alt34) {
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

            pushFollow(FOLLOW_selectionList_in_selectClause727);
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
    // EsperEPL2Ast.g:155:1: fromClause : streamExpression ( streamExpression ( outerJoin )* )* ;
    public final void fromClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:156:2: ( streamExpression ( streamExpression ( outerJoin )* )* )
            // EsperEPL2Ast.g:156:4: streamExpression ( streamExpression ( outerJoin )* )*
            {
            pushFollow(FOLLOW_streamExpression_in_fromClause741);
            streamExpression();

            state._fsp--;

            // EsperEPL2Ast.g:156:21: ( streamExpression ( outerJoin )* )*
            loop36:
            do {
                int alt36=2;
                int LA36_0 = input.LA(1);

                if ( (LA36_0==STREAM_EXPR) ) {
                    alt36=1;
                }


                switch (alt36) {
            	case 1 :
            	    // EsperEPL2Ast.g:156:22: streamExpression ( outerJoin )*
            	    {
            	    pushFollow(FOLLOW_streamExpression_in_fromClause744);
            	    streamExpression();

            	    state._fsp--;

            	    // EsperEPL2Ast.g:156:39: ( outerJoin )*
            	    loop35:
            	    do {
            	        int alt35=2;
            	        int LA35_0 = input.LA(1);

            	        if ( ((LA35_0>=INNERJOIN_EXPR && LA35_0<=FULL_OUTERJOIN_EXPR)) ) {
            	            alt35=1;
            	        }


            	        switch (alt35) {
            	    	case 1 :
            	    	    // EsperEPL2Ast.g:156:40: outerJoin
            	    	    {
            	    	    pushFollow(FOLLOW_outerJoin_in_fromClause747);
            	    	    outerJoin();

            	    	    state._fsp--;


            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop35;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop36;
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
    // EsperEPL2Ast.g:159:1: selectionList : selectionListElement ( selectionListElement )* ;
    public final void selectionList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:160:2: ( selectionListElement ( selectionListElement )* )
            // EsperEPL2Ast.g:160:4: selectionListElement ( selectionListElement )*
            {
            pushFollow(FOLLOW_selectionListElement_in_selectionList764);
            selectionListElement();

            state._fsp--;

            // EsperEPL2Ast.g:160:25: ( selectionListElement )*
            loop37:
            do {
                int alt37=2;
                int LA37_0 = input.LA(1);

                if ( ((LA37_0>=SELECTION_ELEMENT_EXPR && LA37_0<=SELECTION_STREAM)||LA37_0==WILDCARD_SELECT) ) {
                    alt37=1;
                }


                switch (alt37) {
            	case 1 :
            	    // EsperEPL2Ast.g:160:26: selectionListElement
            	    {
            	    pushFollow(FOLLOW_selectionListElement_in_selectionList767);
            	    selectionListElement();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop37;
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
    // EsperEPL2Ast.g:163:1: selectionListElement : (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) );
    public final void selectionListElement() throws RecognitionException {
        CommonTree w=null;
        CommonTree e=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:164:2: (w= WILDCARD_SELECT | ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? ) | ^(s= SELECTION_STREAM IDENT ( IDENT )? ) )
            int alt40=3;
            switch ( input.LA(1) ) {
            case WILDCARD_SELECT:
                {
                alt40=1;
                }
                break;
            case SELECTION_ELEMENT_EXPR:
                {
                alt40=2;
                }
                break;
            case SELECTION_STREAM:
                {
                alt40=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 40, 0, input);

                throw nvae;
            }

            switch (alt40) {
                case 1 :
                    // EsperEPL2Ast.g:164:4: w= WILDCARD_SELECT
                    {
                    w=(CommonTree)match(input,WILDCARD_SELECT,FOLLOW_WILDCARD_SELECT_in_selectionListElement783); 
                     leaveNode(w); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:165:4: ^(e= SELECTION_ELEMENT_EXPR valueExpr ( IDENT )? )
                    {
                    e=(CommonTree)match(input,SELECTION_ELEMENT_EXPR,FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement793); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_selectionListElement795);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:165:41: ( IDENT )?
                    int alt38=2;
                    int LA38_0 = input.LA(1);

                    if ( (LA38_0==IDENT) ) {
                        alt38=1;
                    }
                    switch (alt38) {
                        case 1 :
                            // EsperEPL2Ast.g:165:42: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement798); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:166:4: ^(s= SELECTION_STREAM IDENT ( IDENT )? )
                    {
                    s=(CommonTree)match(input,SELECTION_STREAM,FOLLOW_SELECTION_STREAM_in_selectionListElement812); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_selectionListElement814); 
                    // EsperEPL2Ast.g:166:31: ( IDENT )?
                    int alt39=2;
                    int LA39_0 = input.LA(1);

                    if ( (LA39_0==IDENT) ) {
                        alt39=1;
                    }
                    switch (alt39) {
                        case 1 :
                            // EsperEPL2Ast.g:166:32: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_selectionListElement817); 

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
    // EsperEPL2Ast.g:169:1: outerJoin : outerJoinIdent ;
    public final void outerJoin() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:170:2: ( outerJoinIdent )
            // EsperEPL2Ast.g:170:4: outerJoinIdent
            {
            pushFollow(FOLLOW_outerJoinIdent_in_outerJoin836);
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
    // EsperEPL2Ast.g:173:1: outerJoinIdent : ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) );
    public final void outerJoinIdent() throws RecognitionException {
        CommonTree tl=null;
        CommonTree tr=null;
        CommonTree tf=null;
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:174:2: ( ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) | ^(i= INNERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* ) )
            int alt45=4;
            switch ( input.LA(1) ) {
            case LEFT_OUTERJOIN_EXPR:
                {
                alt45=1;
                }
                break;
            case RIGHT_OUTERJOIN_EXPR:
                {
                alt45=2;
                }
                break;
            case FULL_OUTERJOIN_EXPR:
                {
                alt45=3;
                }
                break;
            case INNERJOIN_EXPR:
                {
                alt45=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 45, 0, input);

                throw nvae;
            }

            switch (alt45) {
                case 1 :
                    // EsperEPL2Ast.g:174:4: ^(tl= LEFT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    tl=(CommonTree)match(input,LEFT_OUTERJOIN_EXPR,FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent850); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent852);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent854);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:174:65: ( eventPropertyExpr eventPropertyExpr )*
                    loop41:
                    do {
                        int alt41=2;
                        int LA41_0 = input.LA(1);

                        if ( (LA41_0==EVENT_PROP_EXPR) ) {
                            alt41=1;
                        }


                        switch (alt41) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:174:66: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent857);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent859);
                    	    eventPropertyExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop41;
                        }
                    } while (true);

                     leaveNode(tl); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:175:4: ^(tr= RIGHT_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    tr=(CommonTree)match(input,RIGHT_OUTERJOIN_EXPR,FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent873); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent875);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent877);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:175:66: ( eventPropertyExpr eventPropertyExpr )*
                    loop42:
                    do {
                        int alt42=2;
                        int LA42_0 = input.LA(1);

                        if ( (LA42_0==EVENT_PROP_EXPR) ) {
                            alt42=1;
                        }


                        switch (alt42) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:175:67: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent880);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent882);
                    	    eventPropertyExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop42;
                        }
                    } while (true);

                     leaveNode(tr); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:176:4: ^(tf= FULL_OUTERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    tf=(CommonTree)match(input,FULL_OUTERJOIN_EXPR,FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent896); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent898);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent900);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:176:65: ( eventPropertyExpr eventPropertyExpr )*
                    loop43:
                    do {
                        int alt43=2;
                        int LA43_0 = input.LA(1);

                        if ( (LA43_0==EVENT_PROP_EXPR) ) {
                            alt43=1;
                        }


                        switch (alt43) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:176:66: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent903);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent905);
                    	    eventPropertyExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop43;
                        }
                    } while (true);

                     leaveNode(tf); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:177:4: ^(i= INNERJOIN_EXPR eventPropertyExpr eventPropertyExpr ( eventPropertyExpr eventPropertyExpr )* )
                    {
                    i=(CommonTree)match(input,INNERJOIN_EXPR,FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent919); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent921);
                    eventPropertyExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent923);
                    eventPropertyExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:177:59: ( eventPropertyExpr eventPropertyExpr )*
                    loop44:
                    do {
                        int alt44=2;
                        int LA44_0 = input.LA(1);

                        if ( (LA44_0==EVENT_PROP_EXPR) ) {
                            alt44=1;
                        }


                        switch (alt44) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:177:60: eventPropertyExpr eventPropertyExpr
                    	    {
                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent926);
                    	    eventPropertyExpr();

                    	    state._fsp--;

                    	    pushFollow(FOLLOW_eventPropertyExpr_in_outerJoinIdent928);
                    	    eventPropertyExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop44;
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
    // EsperEPL2Ast.g:180:1: streamExpression : ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) ;
    public final void streamExpression() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:181:2: ( ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? ) )
            // EsperEPL2Ast.g:181:4: ^(v= STREAM_EXPR ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression ) ( viewListExpr )? ( IDENT )? ( UNIDIRECTIONAL )? ( RETAINUNION | RETAININTERSECTION )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_streamExpression948); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:181:20: ( eventFilterExpr | patternInclusionExpression | databaseJoinExpression | methodJoinExpression )
            int alt46=4;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
                {
                alt46=1;
                }
                break;
            case PATTERN_INCL_EXPR:
                {
                alt46=2;
                }
                break;
            case DATABASE_JOIN_EXPR:
                {
                alt46=3;
                }
                break;
            case METHOD_JOIN_EXPR:
                {
                alt46=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 46, 0, input);

                throw nvae;
            }

            switch (alt46) {
                case 1 :
                    // EsperEPL2Ast.g:181:21: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_streamExpression951);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:181:39: patternInclusionExpression
                    {
                    pushFollow(FOLLOW_patternInclusionExpression_in_streamExpression955);
                    patternInclusionExpression();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:181:68: databaseJoinExpression
                    {
                    pushFollow(FOLLOW_databaseJoinExpression_in_streamExpression959);
                    databaseJoinExpression();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:181:93: methodJoinExpression
                    {
                    pushFollow(FOLLOW_methodJoinExpression_in_streamExpression963);
                    methodJoinExpression();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:181:115: ( viewListExpr )?
            int alt47=2;
            int LA47_0 = input.LA(1);

            if ( (LA47_0==VIEW_EXPR) ) {
                alt47=1;
            }
            switch (alt47) {
                case 1 :
                    // EsperEPL2Ast.g:181:116: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_streamExpression967);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:181:131: ( IDENT )?
            int alt48=2;
            int LA48_0 = input.LA(1);

            if ( (LA48_0==IDENT) ) {
                alt48=1;
            }
            switch (alt48) {
                case 1 :
                    // EsperEPL2Ast.g:181:132: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_streamExpression972); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:181:140: ( UNIDIRECTIONAL )?
            int alt49=2;
            int LA49_0 = input.LA(1);

            if ( (LA49_0==UNIDIRECTIONAL) ) {
                alt49=1;
            }
            switch (alt49) {
                case 1 :
                    // EsperEPL2Ast.g:181:141: UNIDIRECTIONAL
                    {
                    match(input,UNIDIRECTIONAL,FOLLOW_UNIDIRECTIONAL_in_streamExpression977); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:181:158: ( RETAINUNION | RETAININTERSECTION )?
            int alt50=2;
            int LA50_0 = input.LA(1);

            if ( ((LA50_0>=RETAINUNION && LA50_0<=RETAININTERSECTION)) ) {
                alt50=1;
            }
            switch (alt50) {
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


    // $ANTLR start "patternInclusionExpression"
    // EsperEPL2Ast.g:184:1: patternInclusionExpression : ^(p= PATTERN_INCL_EXPR exprChoice ) ;
    public final void patternInclusionExpression() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:185:2: ( ^(p= PATTERN_INCL_EXPR exprChoice ) )
            // EsperEPL2Ast.g:185:4: ^(p= PATTERN_INCL_EXPR exprChoice )
            {
            p=(CommonTree)match(input,PATTERN_INCL_EXPR,FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1004); 

             setIsPatternWalk(true); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_exprChoice_in_patternInclusionExpression1008);
            exprChoice();

            state._fsp--;

             setIsPatternWalk(false); leaveNode(p); 

            match(input, Token.UP, null); 

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
    // EsperEPL2Ast.g:188:1: databaseJoinExpression : ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) ;
    public final void databaseJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:189:2: ( ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? ) )
            // EsperEPL2Ast.g:189:4: ^( DATABASE_JOIN_EXPR IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ( STRING_LITERAL | QUOTED_STRING_LITERAL )? )
            {
            match(input,DATABASE_JOIN_EXPR,FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1025); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_databaseJoinExpression1027); 
            if ( (input.LA(1)>=STRING_LITERAL && input.LA(1)<=QUOTED_STRING_LITERAL) ) {
                input.consume();
                state.errorRecovery=false;
            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EsperEPL2Ast.g:189:72: ( STRING_LITERAL | QUOTED_STRING_LITERAL )?
            int alt51=2;
            int LA51_0 = input.LA(1);

            if ( ((LA51_0>=STRING_LITERAL && LA51_0<=QUOTED_STRING_LITERAL)) ) {
                alt51=1;
            }
            switch (alt51) {
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
    // EsperEPL2Ast.g:192:1: methodJoinExpression : ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) ;
    public final void methodJoinExpression() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:193:2: ( ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:193:4: ^( METHOD_JOIN_EXPR IDENT CLASS_IDENT ( valueExpr )* )
            {
            match(input,METHOD_JOIN_EXPR,FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1058); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_methodJoinExpression1060); 
            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_methodJoinExpression1062); 
            // EsperEPL2Ast.g:193:41: ( valueExpr )*
            loop52:
            do {
                int alt52=2;
                int LA52_0 = input.LA(1);

                if ( ((LA52_0>=IN_SET && LA52_0<=REGEXP)||LA52_0==NOT_EXPR||(LA52_0>=SUM && LA52_0<=AVG)||(LA52_0>=COALESCE && LA52_0<=COUNT)||(LA52_0>=CASE && LA52_0<=CASE2)||(LA52_0>=PREVIOUS && LA52_0<=EXISTS)||(LA52_0>=INSTANCEOF && LA52_0<=CURRENT_TIMESTAMP)||(LA52_0>=EVAL_AND_EXPR && LA52_0<=EVAL_NOTEQUALS_EXPR)||LA52_0==EVENT_PROP_EXPR||(LA52_0>=CONCAT && LA52_0<=LIB_FUNCTION)||LA52_0==ARRAY_EXPR||(LA52_0>=NOT_IN_SET && LA52_0<=NOT_REGEXP)||(LA52_0>=IN_RANGE && LA52_0<=NOT_IN_SUBSELECT_EXPR)||LA52_0==SUBSTITUTION||(LA52_0>=INT_TYPE && LA52_0<=NULL_TYPE)||LA52_0==STAR||(LA52_0>=BAND && LA52_0<=BXOR)||(LA52_0>=LT && LA52_0<=GE)||(LA52_0>=PLUS && LA52_0<=MOD)) ) {
                    alt52=1;
                }


                switch (alt52) {
            	case 1 :
            	    // EsperEPL2Ast.g:193:42: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_methodJoinExpression1065);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop52;
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
    // EsperEPL2Ast.g:196:1: viewListExpr : viewExpr ( viewExpr )* ;
    public final void viewListExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:197:2: ( viewExpr ( viewExpr )* )
            // EsperEPL2Ast.g:197:4: viewExpr ( viewExpr )*
            {
            pushFollow(FOLLOW_viewExpr_in_viewListExpr1079);
            viewExpr();

            state._fsp--;

            // EsperEPL2Ast.g:197:13: ( viewExpr )*
            loop53:
            do {
                int alt53=2;
                int LA53_0 = input.LA(1);

                if ( (LA53_0==VIEW_EXPR) ) {
                    alt53=1;
                }


                switch (alt53) {
            	case 1 :
            	    // EsperEPL2Ast.g:197:14: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_viewListExpr1082);
            	    viewExpr();

            	    state._fsp--;


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
    // $ANTLR end "viewListExpr"


    // $ANTLR start "viewExpr"
    // EsperEPL2Ast.g:200:1: viewExpr : ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) ;
    public final void viewExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:201:2: ( ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            // EsperEPL2Ast.g:201:4: ^(n= VIEW_EXPR IDENT IDENT ( valueExprWithTime )* )
            {
            n=(CommonTree)match(input,VIEW_EXPR,FOLLOW_VIEW_EXPR_in_viewExpr1099); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1101); 
            match(input,IDENT,FOLLOW_IDENT_in_viewExpr1103); 
            // EsperEPL2Ast.g:201:30: ( valueExprWithTime )*
            loop54:
            do {
                int alt54=2;
                int LA54_0 = input.LA(1);

                if ( ((LA54_0>=IN_SET && LA54_0<=REGEXP)||LA54_0==NOT_EXPR||(LA54_0>=SUM && LA54_0<=AVG)||(LA54_0>=COALESCE && LA54_0<=COUNT)||(LA54_0>=CASE && LA54_0<=CASE2)||LA54_0==LAST||(LA54_0>=PREVIOUS && LA54_0<=EXISTS)||(LA54_0>=LW && LA54_0<=CURRENT_TIMESTAMP)||(LA54_0>=NUMERIC_PARAM_RANGE && LA54_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA54_0>=EVAL_AND_EXPR && LA54_0<=EVAL_NOTEQUALS_EXPR)||LA54_0==EVENT_PROP_EXPR||(LA54_0>=CONCAT && LA54_0<=LIB_FUNCTION)||(LA54_0>=TIME_PERIOD && LA54_0<=ARRAY_EXPR)||(LA54_0>=NOT_IN_SET && LA54_0<=NOT_REGEXP)||(LA54_0>=IN_RANGE && LA54_0<=NOT_IN_SUBSELECT_EXPR)||(LA54_0>=LAST_OPERATOR && LA54_0<=SUBSTITUTION)||(LA54_0>=NUMBERSETSTAR && LA54_0<=NULL_TYPE)||LA54_0==STAR||(LA54_0>=BAND && LA54_0<=BXOR)||(LA54_0>=LT && LA54_0<=GE)||(LA54_0>=PLUS && LA54_0<=MOD)) ) {
                    alt54=1;
                }


                switch (alt54) {
            	case 1 :
            	    // EsperEPL2Ast.g:201:31: valueExprWithTime
            	    {
            	    pushFollow(FOLLOW_valueExprWithTime_in_viewExpr1106);
            	    valueExprWithTime();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop54;
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
    // EsperEPL2Ast.g:204:1: whereClause : ^(n= WHERE_EXPR valueExpr ) ;
    public final void whereClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:205:2: ( ^(n= WHERE_EXPR valueExpr ) )
            // EsperEPL2Ast.g:205:4: ^(n= WHERE_EXPR valueExpr )
            {
            n=(CommonTree)match(input,WHERE_EXPR,FOLLOW_WHERE_EXPR_in_whereClause1127); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_whereClause1129);
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
    // EsperEPL2Ast.g:208:1: groupByClause : ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) ;
    public final void groupByClause() throws RecognitionException {
        CommonTree g=null;

        try {
            // EsperEPL2Ast.g:209:2: ( ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:209:4: ^(g= GROUP_BY_EXPR valueExpr ( valueExpr )* )
            {
            g=(CommonTree)match(input,GROUP_BY_EXPR,FOLLOW_GROUP_BY_EXPR_in_groupByClause1147); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_groupByClause1149);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:209:32: ( valueExpr )*
            loop55:
            do {
                int alt55=2;
                int LA55_0 = input.LA(1);

                if ( ((LA55_0>=IN_SET && LA55_0<=REGEXP)||LA55_0==NOT_EXPR||(LA55_0>=SUM && LA55_0<=AVG)||(LA55_0>=COALESCE && LA55_0<=COUNT)||(LA55_0>=CASE && LA55_0<=CASE2)||(LA55_0>=PREVIOUS && LA55_0<=EXISTS)||(LA55_0>=INSTANCEOF && LA55_0<=CURRENT_TIMESTAMP)||(LA55_0>=EVAL_AND_EXPR && LA55_0<=EVAL_NOTEQUALS_EXPR)||LA55_0==EVENT_PROP_EXPR||(LA55_0>=CONCAT && LA55_0<=LIB_FUNCTION)||LA55_0==ARRAY_EXPR||(LA55_0>=NOT_IN_SET && LA55_0<=NOT_REGEXP)||(LA55_0>=IN_RANGE && LA55_0<=NOT_IN_SUBSELECT_EXPR)||LA55_0==SUBSTITUTION||(LA55_0>=INT_TYPE && LA55_0<=NULL_TYPE)||LA55_0==STAR||(LA55_0>=BAND && LA55_0<=BXOR)||(LA55_0>=LT && LA55_0<=GE)||(LA55_0>=PLUS && LA55_0<=MOD)) ) {
                    alt55=1;
                }


                switch (alt55) {
            	case 1 :
            	    // EsperEPL2Ast.g:209:33: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_groupByClause1152);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop55;
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
    // EsperEPL2Ast.g:212:1: orderByClause : ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) ;
    public final void orderByClause() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:213:2: ( ^( ORDER_BY_EXPR orderByElement ( orderByElement )* ) )
            // EsperEPL2Ast.g:213:4: ^( ORDER_BY_EXPR orderByElement ( orderByElement )* )
            {
            match(input,ORDER_BY_EXPR,FOLLOW_ORDER_BY_EXPR_in_orderByClause1170); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_orderByElement_in_orderByClause1172);
            orderByElement();

            state._fsp--;

            // EsperEPL2Ast.g:213:35: ( orderByElement )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==ORDER_ELEMENT_EXPR) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // EsperEPL2Ast.g:213:36: orderByElement
            	    {
            	    pushFollow(FOLLOW_orderByElement_in_orderByClause1175);
            	    orderByElement();

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

          catch (RecognitionException rex) {
            throw rex;
          }
        finally {
        }
        return ;
    }
    // $ANTLR end "orderByClause"


    // $ANTLR start "orderByElement"
    // EsperEPL2Ast.g:216:1: orderByElement : ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) ;
    public final void orderByElement() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:217:2: ( ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? ) )
            // EsperEPL2Ast.g:217:5: ^(e= ORDER_ELEMENT_EXPR valueExpr ( ASC | DESC )? )
            {
            e=(CommonTree)match(input,ORDER_ELEMENT_EXPR,FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1195); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_orderByElement1197);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:217:38: ( ASC | DESC )?
            int alt57=2;
            int LA57_0 = input.LA(1);

            if ( ((LA57_0>=ASC && LA57_0<=DESC)) ) {
                alt57=1;
            }
            switch (alt57) {
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
    // EsperEPL2Ast.g:220:1: havingClause : ^(n= HAVING_EXPR valueExpr ) ;
    public final void havingClause() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:221:2: ( ^(n= HAVING_EXPR valueExpr ) )
            // EsperEPL2Ast.g:221:4: ^(n= HAVING_EXPR valueExpr )
            {
            n=(CommonTree)match(input,HAVING_EXPR,FOLLOW_HAVING_EXPR_in_havingClause1222); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_havingClause1224);
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
    // EsperEPL2Ast.g:224:1: outputLimitExpr : ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) );
    public final void outputLimitExpr() throws RecognitionException {
        CommonTree e=null;
        CommonTree tp=null;
        CommonTree cron=null;
        CommonTree when=null;

        try {
            // EsperEPL2Ast.g:225:2: ( ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) ) | ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod ) | ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet ) | ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? ) )
            int alt64=4;
            switch ( input.LA(1) ) {
            case EVENT_LIMIT_EXPR:
                {
                alt64=1;
                }
                break;
            case TIMEPERIOD_LIMIT_EXPR:
                {
                alt64=2;
                }
                break;
            case CRONTAB_LIMIT_EXPR:
                {
                alt64=3;
                }
                break;
            case WHEN_LIMIT_EXPR:
                {
                alt64=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 64, 0, input);

                throw nvae;
            }

            switch (alt64) {
                case 1 :
                    // EsperEPL2Ast.g:225:4: ^(e= EVENT_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? ( number | IDENT ) )
                    {
                    e=(CommonTree)match(input,EVENT_LIMIT_EXPR,FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1242); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:225:25: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt58=2;
                    int LA58_0 = input.LA(1);

                    if ( (LA58_0==ALL||(LA58_0>=FIRST && LA58_0<=LAST)||LA58_0==SNAPSHOT) ) {
                        alt58=1;
                    }
                    switch (alt58) {
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

                    // EsperEPL2Ast.g:225:52: ( number | IDENT )
                    int alt59=2;
                    int LA59_0 = input.LA(1);

                    if ( ((LA59_0>=INT_TYPE && LA59_0<=DOUBLE_TYPE)) ) {
                        alt59=1;
                    }
                    else if ( (LA59_0==IDENT) ) {
                        alt59=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 59, 0, input);

                        throw nvae;
                    }
                    switch (alt59) {
                        case 1 :
                            // EsperEPL2Ast.g:225:53: number
                            {
                            pushFollow(FOLLOW_number_in_outputLimitExpr1256);
                            number();

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:225:60: IDENT
                            {
                            match(input,IDENT,FOLLOW_IDENT_in_outputLimitExpr1258); 

                            }
                            break;

                    }

                     leaveNode(e); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:226:7: ^(tp= TIMEPERIOD_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? timePeriod )
                    {
                    tp=(CommonTree)match(input,TIMEPERIOD_LIMIT_EXPR,FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1275); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:226:34: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt60=2;
                    int LA60_0 = input.LA(1);

                    if ( (LA60_0==ALL||(LA60_0>=FIRST && LA60_0<=LAST)||LA60_0==SNAPSHOT) ) {
                        alt60=1;
                    }
                    switch (alt60) {
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

                    pushFollow(FOLLOW_timePeriod_in_outputLimitExpr1288);
                    timePeriod();

                    state._fsp--;

                     leaveNode(tp); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:227:7: ^(cron= CRONTAB_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? crontabLimitParameterSet )
                    {
                    cron=(CommonTree)match(input,CRONTAB_LIMIT_EXPR,FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1303); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:227:33: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt61=2;
                    int LA61_0 = input.LA(1);

                    if ( (LA61_0==ALL||(LA61_0>=FIRST && LA61_0<=LAST)||LA61_0==SNAPSHOT) ) {
                        alt61=1;
                    }
                    switch (alt61) {
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

                    pushFollow(FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1316);
                    crontabLimitParameterSet();

                    state._fsp--;

                     leaveNode(cron); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:228:7: ^(when= WHEN_LIMIT_EXPR ( ALL | FIRST | LAST | SNAPSHOT )? valueExpr ( onSetExpr )? )
                    {
                    when=(CommonTree)match(input,WHEN_LIMIT_EXPR,FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1331); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:228:30: ( ALL | FIRST | LAST | SNAPSHOT )?
                    int alt62=2;
                    int LA62_0 = input.LA(1);

                    if ( (LA62_0==ALL||(LA62_0>=FIRST && LA62_0<=LAST)||LA62_0==SNAPSHOT) ) {
                        alt62=1;
                    }
                    switch (alt62) {
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

                    pushFollow(FOLLOW_valueExpr_in_outputLimitExpr1344);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:228:67: ( onSetExpr )?
                    int alt63=2;
                    int LA63_0 = input.LA(1);

                    if ( (LA63_0==ON_SET_EXPR) ) {
                        alt63=1;
                    }
                    switch (alt63) {
                        case 1 :
                            // EsperEPL2Ast.g:228:67: onSetExpr
                            {
                            pushFollow(FOLLOW_onSetExpr_in_outputLimitExpr1346);
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
    // EsperEPL2Ast.g:231:1: rowLimitClause : ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) ;
    public final void rowLimitClause() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:232:2: ( ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? ) )
            // EsperEPL2Ast.g:232:4: ^(e= ROW_LIMIT_EXPR ( number | IDENT ) ( number | IDENT )? ( COMMA )? ( OFFSET )? )
            {
            e=(CommonTree)match(input,ROW_LIMIT_EXPR,FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1365); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:232:23: ( number | IDENT )
            int alt65=2;
            int LA65_0 = input.LA(1);

            if ( ((LA65_0>=INT_TYPE && LA65_0<=DOUBLE_TYPE)) ) {
                alt65=1;
            }
            else if ( (LA65_0==IDENT) ) {
                alt65=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 65, 0, input);

                throw nvae;
            }
            switch (alt65) {
                case 1 :
                    // EsperEPL2Ast.g:232:24: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1368);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:232:31: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1370); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:232:38: ( number | IDENT )?
            int alt66=3;
            int LA66_0 = input.LA(1);

            if ( ((LA66_0>=INT_TYPE && LA66_0<=DOUBLE_TYPE)) ) {
                alt66=1;
            }
            else if ( (LA66_0==IDENT) ) {
                alt66=2;
            }
            switch (alt66) {
                case 1 :
                    // EsperEPL2Ast.g:232:39: number
                    {
                    pushFollow(FOLLOW_number_in_rowLimitClause1374);
                    number();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:232:46: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_rowLimitClause1376); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:232:54: ( COMMA )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==COMMA) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EsperEPL2Ast.g:232:54: COMMA
                    {
                    match(input,COMMA,FOLLOW_COMMA_in_rowLimitClause1380); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:232:61: ( OFFSET )?
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==OFFSET) ) {
                alt68=1;
            }
            switch (alt68) {
                case 1 :
                    // EsperEPL2Ast.g:232:61: OFFSET
                    {
                    match(input,OFFSET,FOLLOW_OFFSET_in_rowLimitClause1383); 

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
    // EsperEPL2Ast.g:235:1: crontabLimitParameterSet : ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) ;
    public final void crontabLimitParameterSet() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:236:2: ( ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? ) )
            // EsperEPL2Ast.g:236:4: ^( CRONTAB_LIMIT_EXPR_PARAM valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime valueExprWithTime ( valueExprWithTime )? )
            {
            match(input,CRONTAB_LIMIT_EXPR_PARAM,FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1401); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1403);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1405);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1407);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1409);
            valueExprWithTime();

            state._fsp--;

            pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1411);
            valueExprWithTime();

            state._fsp--;

            // EsperEPL2Ast.g:236:121: ( valueExprWithTime )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( ((LA69_0>=IN_SET && LA69_0<=REGEXP)||LA69_0==NOT_EXPR||(LA69_0>=SUM && LA69_0<=AVG)||(LA69_0>=COALESCE && LA69_0<=COUNT)||(LA69_0>=CASE && LA69_0<=CASE2)||LA69_0==LAST||(LA69_0>=PREVIOUS && LA69_0<=EXISTS)||(LA69_0>=LW && LA69_0<=CURRENT_TIMESTAMP)||(LA69_0>=NUMERIC_PARAM_RANGE && LA69_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA69_0>=EVAL_AND_EXPR && LA69_0<=EVAL_NOTEQUALS_EXPR)||LA69_0==EVENT_PROP_EXPR||(LA69_0>=CONCAT && LA69_0<=LIB_FUNCTION)||(LA69_0>=TIME_PERIOD && LA69_0<=ARRAY_EXPR)||(LA69_0>=NOT_IN_SET && LA69_0<=NOT_REGEXP)||(LA69_0>=IN_RANGE && LA69_0<=NOT_IN_SUBSELECT_EXPR)||(LA69_0>=LAST_OPERATOR && LA69_0<=SUBSTITUTION)||(LA69_0>=NUMBERSETSTAR && LA69_0<=NULL_TYPE)||LA69_0==STAR||(LA69_0>=BAND && LA69_0<=BXOR)||(LA69_0>=LT && LA69_0<=GE)||(LA69_0>=PLUS && LA69_0<=MOD)) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // EsperEPL2Ast.g:236:121: valueExprWithTime
                    {
                    pushFollow(FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1413);
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
    // EsperEPL2Ast.g:239:1: relationalExpr : ( ^(n= LT valueExpr valueExpr ) | ^(n= GT valueExpr valueExpr ) | ^(n= LE valueExpr valueExpr ) | ^(n= GE valueExpr valueExpr ) );
    public final void relationalExpr() throws RecognitionException {
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:240:2: ( ^(n= LT valueExpr valueExpr ) | ^(n= GT valueExpr valueExpr ) | ^(n= LE valueExpr valueExpr ) | ^(n= GE valueExpr valueExpr ) )
            int alt70=4;
            switch ( input.LA(1) ) {
            case LT:
                {
                alt70=1;
                }
                break;
            case GT:
                {
                alt70=2;
                }
                break;
            case LE:
                {
                alt70=3;
                }
                break;
            case GE:
                {
                alt70=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }

            switch (alt70) {
                case 1 :
                    // EsperEPL2Ast.g:240:5: ^(n= LT valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,LT,FOLLOW_LT_in_relationalExpr1430); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1432);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1434);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:241:5: ^(n= GT valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,GT,FOLLOW_GT_in_relationalExpr1446); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1448);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1450);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:242:5: ^(n= LE valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,LE,FOLLOW_LE_in_relationalExpr1462); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1464);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1466);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:243:4: ^(n= GE valueExpr valueExpr )
                    {
                    n=(CommonTree)match(input,GE,FOLLOW_GE_in_relationalExpr1477); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1479);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_relationalExpr1481);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(n); 

                    }
                    break;

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


    // $ANTLR start "evalExprChoice"
    // EsperEPL2Ast.g:246:1: evalExprChoice : ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr );
    public final void evalExprChoice() throws RecognitionException {
        CommonTree jo=null;
        CommonTree ja=null;
        CommonTree je=null;
        CommonTree jne=null;
        CommonTree n=null;

        try {
            // EsperEPL2Ast.g:247:2: ( ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* ) | ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr ) | ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr ) | ^(n= NOT_EXPR valueExpr ) | r= relationalExpr )
            int alt73=6;
            switch ( input.LA(1) ) {
            case EVAL_OR_EXPR:
                {
                alt73=1;
                }
                break;
            case EVAL_AND_EXPR:
                {
                alt73=2;
                }
                break;
            case EVAL_EQUALS_EXPR:
                {
                alt73=3;
                }
                break;
            case EVAL_NOTEQUALS_EXPR:
                {
                alt73=4;
                }
                break;
            case NOT_EXPR:
                {
                alt73=5;
                }
                break;
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt73=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 73, 0, input);

                throw nvae;
            }

            switch (alt73) {
                case 1 :
                    // EsperEPL2Ast.g:247:4: ^(jo= EVAL_OR_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    jo=(CommonTree)match(input,EVAL_OR_EXPR,FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1498); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1500);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1502);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:247:42: ( valueExpr )*
                    loop71:
                    do {
                        int alt71=2;
                        int LA71_0 = input.LA(1);

                        if ( ((LA71_0>=IN_SET && LA71_0<=REGEXP)||LA71_0==NOT_EXPR||(LA71_0>=SUM && LA71_0<=AVG)||(LA71_0>=COALESCE && LA71_0<=COUNT)||(LA71_0>=CASE && LA71_0<=CASE2)||(LA71_0>=PREVIOUS && LA71_0<=EXISTS)||(LA71_0>=INSTANCEOF && LA71_0<=CURRENT_TIMESTAMP)||(LA71_0>=EVAL_AND_EXPR && LA71_0<=EVAL_NOTEQUALS_EXPR)||LA71_0==EVENT_PROP_EXPR||(LA71_0>=CONCAT && LA71_0<=LIB_FUNCTION)||LA71_0==ARRAY_EXPR||(LA71_0>=NOT_IN_SET && LA71_0<=NOT_REGEXP)||(LA71_0>=IN_RANGE && LA71_0<=NOT_IN_SUBSELECT_EXPR)||LA71_0==SUBSTITUTION||(LA71_0>=INT_TYPE && LA71_0<=NULL_TYPE)||LA71_0==STAR||(LA71_0>=BAND && LA71_0<=BXOR)||(LA71_0>=LT && LA71_0<=GE)||(LA71_0>=PLUS && LA71_0<=MOD)) ) {
                            alt71=1;
                        }


                        switch (alt71) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:247:43: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1505);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop71;
                        }
                    } while (true);

                     leaveNode(jo); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:248:4: ^(ja= EVAL_AND_EXPR valueExpr valueExpr ( valueExpr )* )
                    {
                    ja=(CommonTree)match(input,EVAL_AND_EXPR,FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1519); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1521);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1523);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:248:43: ( valueExpr )*
                    loop72:
                    do {
                        int alt72=2;
                        int LA72_0 = input.LA(1);

                        if ( ((LA72_0>=IN_SET && LA72_0<=REGEXP)||LA72_0==NOT_EXPR||(LA72_0>=SUM && LA72_0<=AVG)||(LA72_0>=COALESCE && LA72_0<=COUNT)||(LA72_0>=CASE && LA72_0<=CASE2)||(LA72_0>=PREVIOUS && LA72_0<=EXISTS)||(LA72_0>=INSTANCEOF && LA72_0<=CURRENT_TIMESTAMP)||(LA72_0>=EVAL_AND_EXPR && LA72_0<=EVAL_NOTEQUALS_EXPR)||LA72_0==EVENT_PROP_EXPR||(LA72_0>=CONCAT && LA72_0<=LIB_FUNCTION)||LA72_0==ARRAY_EXPR||(LA72_0>=NOT_IN_SET && LA72_0<=NOT_REGEXP)||(LA72_0>=IN_RANGE && LA72_0<=NOT_IN_SUBSELECT_EXPR)||LA72_0==SUBSTITUTION||(LA72_0>=INT_TYPE && LA72_0<=NULL_TYPE)||LA72_0==STAR||(LA72_0>=BAND && LA72_0<=BXOR)||(LA72_0>=LT && LA72_0<=GE)||(LA72_0>=PLUS && LA72_0<=MOD)) ) {
                            alt72=1;
                        }


                        switch (alt72) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:248:44: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1526);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop72;
                        }
                    } while (true);

                     leaveNode(ja); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:249:4: ^(je= EVAL_EQUALS_EXPR valueExpr valueExpr )
                    {
                    je=(CommonTree)match(input,EVAL_EQUALS_EXPR,FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1540); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1542);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1544);
                    valueExpr();

                    state._fsp--;

                     leaveNode(je); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:250:4: ^(jne= EVAL_NOTEQUALS_EXPR valueExpr valueExpr )
                    {
                    jne=(CommonTree)match(input,EVAL_NOTEQUALS_EXPR,FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1556); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1558);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1560);
                    valueExpr();

                    state._fsp--;

                     leaveNode(jne); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:251:4: ^(n= NOT_EXPR valueExpr )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_evalExprChoice1572); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_evalExprChoice1574);
                    valueExpr();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:252:4: r= relationalExpr
                    {
                    pushFollow(FOLLOW_relationalExpr_in_evalExprChoice1585);
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
    // EsperEPL2Ast.g:255:1: valueExpr : ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr );
    public final void valueExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:256:2: ( constant[true] | substitution | arithmeticExpr | eventPropertyExpr | evalExprChoice | builtinFunc | libFunc | caseExpr | inExpr | betweenExpr | likeExpr | regExpExpr | arrayExpr | subSelectInExpr | subSelectRowExpr | subSelectExistsExpr )
            int alt74=16;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt74=1;
                }
                break;
            case SUBSTITUTION:
                {
                alt74=2;
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
                alt74=3;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt74=4;
                }
                break;
            case NOT_EXPR:
            case EVAL_AND_EXPR:
            case EVAL_OR_EXPR:
            case EVAL_EQUALS_EXPR:
            case EVAL_NOTEQUALS_EXPR:
            case LT:
            case GT:
            case LE:
            case GE:
                {
                alt74=5;
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
                alt74=6;
                }
                break;
            case LIB_FUNCTION:
                {
                alt74=7;
                }
                break;
            case CASE:
            case CASE2:
                {
                alt74=8;
                }
                break;
            case IN_SET:
            case NOT_IN_SET:
            case IN_RANGE:
            case NOT_IN_RANGE:
                {
                alt74=9;
                }
                break;
            case BETWEEN:
            case NOT_BETWEEN:
                {
                alt74=10;
                }
                break;
            case LIKE:
            case NOT_LIKE:
                {
                alt74=11;
                }
                break;
            case REGEXP:
            case NOT_REGEXP:
                {
                alt74=12;
                }
                break;
            case ARRAY_EXPR:
                {
                alt74=13;
                }
                break;
            case IN_SUBSELECT_EXPR:
            case NOT_IN_SUBSELECT_EXPR:
                {
                alt74=14;
                }
                break;
            case SUBSELECT_EXPR:
                {
                alt74=15;
                }
                break;
            case EXISTS_SUBSELECT_EXPR:
                {
                alt74=16;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 74, 0, input);

                throw nvae;
            }

            switch (alt74) {
                case 1 :
                    // EsperEPL2Ast.g:256:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_valueExpr1598);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:257:4: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_valueExpr1604);
                    substitution();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:258:5: arithmeticExpr
                    {
                    pushFollow(FOLLOW_arithmeticExpr_in_valueExpr1610);
                    arithmeticExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:259:5: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_valueExpr1617);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:260:7: evalExprChoice
                    {
                    pushFollow(FOLLOW_evalExprChoice_in_valueExpr1625);
                    evalExprChoice();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:261:4: builtinFunc
                    {
                    pushFollow(FOLLOW_builtinFunc_in_valueExpr1630);
                    builtinFunc();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:262:7: libFunc
                    {
                    pushFollow(FOLLOW_libFunc_in_valueExpr1638);
                    libFunc();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:263:4: caseExpr
                    {
                    pushFollow(FOLLOW_caseExpr_in_valueExpr1643);
                    caseExpr();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:264:4: inExpr
                    {
                    pushFollow(FOLLOW_inExpr_in_valueExpr1648);
                    inExpr();

                    state._fsp--;


                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:265:4: betweenExpr
                    {
                    pushFollow(FOLLOW_betweenExpr_in_valueExpr1654);
                    betweenExpr();

                    state._fsp--;


                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:266:4: likeExpr
                    {
                    pushFollow(FOLLOW_likeExpr_in_valueExpr1659);
                    likeExpr();

                    state._fsp--;


                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:267:4: regExpExpr
                    {
                    pushFollow(FOLLOW_regExpExpr_in_valueExpr1664);
                    regExpExpr();

                    state._fsp--;


                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:268:4: arrayExpr
                    {
                    pushFollow(FOLLOW_arrayExpr_in_valueExpr1669);
                    arrayExpr();

                    state._fsp--;


                    }
                    break;
                case 14 :
                    // EsperEPL2Ast.g:269:4: subSelectInExpr
                    {
                    pushFollow(FOLLOW_subSelectInExpr_in_valueExpr1674);
                    subSelectInExpr();

                    state._fsp--;


                    }
                    break;
                case 15 :
                    // EsperEPL2Ast.g:270:5: subSelectRowExpr
                    {
                    pushFollow(FOLLOW_subSelectRowExpr_in_valueExpr1680);
                    subSelectRowExpr();

                    state._fsp--;


                    }
                    break;
                case 16 :
                    // EsperEPL2Ast.g:271:5: subSelectExistsExpr
                    {
                    pushFollow(FOLLOW_subSelectExistsExpr_in_valueExpr1687);
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
    // EsperEPL2Ast.g:274:1: valueExprWithTime : (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod );
    public final void valueExprWithTime() throws RecognitionException {
        CommonTree l=null;
        CommonTree lw=null;
        CommonTree ordered=null;
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:275:2: (l= LAST | lw= LW | valueExpr | ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) ) | rangeOperator | frequencyOperator | lastOperator | weekDayOperator | ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ ) | s= NUMBERSETSTAR | timePeriod )
            int alt76=11;
            switch ( input.LA(1) ) {
            case LAST:
                {
                alt76=1;
                }
                break;
            case LW:
                {
                alt76=2;
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
                alt76=3;
                }
                break;
            case OBJECT_PARAM_ORDERED_EXPR:
                {
                alt76=4;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt76=5;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
                {
                alt76=6;
                }
                break;
            case LAST_OPERATOR:
                {
                alt76=7;
                }
                break;
            case WEEKDAY_OPERATOR:
                {
                alt76=8;
                }
                break;
            case NUMERIC_PARAM_LIST:
                {
                alt76=9;
                }
                break;
            case NUMBERSETSTAR:
                {
                alt76=10;
                }
                break;
            case TIME_PERIOD:
                {
                alt76=11;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 76, 0, input);

                throw nvae;
            }

            switch (alt76) {
                case 1 :
                    // EsperEPL2Ast.g:275:4: l= LAST
                    {
                    l=(CommonTree)match(input,LAST,FOLLOW_LAST_in_valueExprWithTime1700); 
                     leaveNode(l); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:276:4: lw= LW
                    {
                    lw=(CommonTree)match(input,LW,FOLLOW_LW_in_valueExprWithTime1709); 
                     leaveNode(lw); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:277:4: valueExpr
                    {
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1716);
                    valueExpr();

                    state._fsp--;


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:278:4: ^(ordered= OBJECT_PARAM_ORDERED_EXPR valueExpr ( DESC | ASC ) )
                    {
                    ordered=(CommonTree)match(input,OBJECT_PARAM_ORDERED_EXPR,FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1724); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_valueExprWithTime1726);
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
                    // EsperEPL2Ast.g:279:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_valueExprWithTime1741);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:280:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_valueExprWithTime1747);
                    frequencyOperator();

                    state._fsp--;


                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:281:4: lastOperator
                    {
                    pushFollow(FOLLOW_lastOperator_in_valueExprWithTime1752);
                    lastOperator();

                    state._fsp--;


                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:282:4: weekDayOperator
                    {
                    pushFollow(FOLLOW_weekDayOperator_in_valueExprWithTime1757);
                    weekDayOperator();

                    state._fsp--;


                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:283:5: ^(l= NUMERIC_PARAM_LIST ( numericParameterList )+ )
                    {
                    l=(CommonTree)match(input,NUMERIC_PARAM_LIST,FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime1767); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:283:29: ( numericParameterList )+
                    int cnt75=0;
                    loop75:
                    do {
                        int alt75=2;
                        int LA75_0 = input.LA(1);

                        if ( (LA75_0==NUMERIC_PARAM_RANGE||LA75_0==NUMERIC_PARAM_FREQUENCY||(LA75_0>=INT_TYPE && LA75_0<=NULL_TYPE)) ) {
                            alt75=1;
                        }


                        switch (alt75) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:283:29: numericParameterList
                    	    {
                    	    pushFollow(FOLLOW_numericParameterList_in_valueExprWithTime1769);
                    	    numericParameterList();

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

                     leaveNode(l); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:284:4: s= NUMBERSETSTAR
                    {
                    s=(CommonTree)match(input,NUMBERSETSTAR,FOLLOW_NUMBERSETSTAR_in_valueExprWithTime1780); 
                     leaveNode(s); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:285:4: timePeriod
                    {
                    pushFollow(FOLLOW_timePeriod_in_valueExprWithTime1787);
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
    // EsperEPL2Ast.g:288:1: numericParameterList : ( constant[true] | rangeOperator | frequencyOperator );
    public final void numericParameterList() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:289:2: ( constant[true] | rangeOperator | frequencyOperator )
            int alt77=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt77=1;
                }
                break;
            case NUMERIC_PARAM_RANGE:
                {
                alt77=2;
                }
                break;
            case NUMERIC_PARAM_FREQUENCY:
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
                    // EsperEPL2Ast.g:289:5: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_numericParameterList1800);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:290:5: rangeOperator
                    {
                    pushFollow(FOLLOW_rangeOperator_in_numericParameterList1807);
                    rangeOperator();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:291:5: frequencyOperator
                    {
                    pushFollow(FOLLOW_frequencyOperator_in_numericParameterList1813);
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
    // EsperEPL2Ast.g:294:1: rangeOperator : ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr | substitution ) ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void rangeOperator() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:295:2: ( ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr | substitution ) ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:295:4: ^(r= NUMERIC_PARAM_RANGE ( constant[true] | eventPropertyExpr | substitution ) ( constant[true] | eventPropertyExpr | substitution ) )
            {
            r=(CommonTree)match(input,NUMERIC_PARAM_RANGE,FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator1829); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:295:29: ( constant[true] | eventPropertyExpr | substitution )
            int alt78=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt78=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt78=2;
                }
                break;
            case SUBSTITUTION:
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
                    // EsperEPL2Ast.g:295:30: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator1832);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:295:45: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator1835);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:295:63: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator1837);
                    substitution();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:295:77: ( constant[true] | eventPropertyExpr | substitution )
            int alt79=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt79=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt79=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt79=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 79, 0, input);

                throw nvae;
            }

            switch (alt79) {
                case 1 :
                    // EsperEPL2Ast.g:295:78: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_rangeOperator1841);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:295:93: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_rangeOperator1844);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:295:111: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_rangeOperator1846);
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
    // EsperEPL2Ast.g:298:1: frequencyOperator : ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void frequencyOperator() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:299:2: ( ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:299:4: ^(f= NUMERIC_PARAM_FREQUENCY ( constant[true] | eventPropertyExpr | substitution ) )
            {
            f=(CommonTree)match(input,NUMERIC_PARAM_FREQUENCY,FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator1867); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:299:33: ( constant[true] | eventPropertyExpr | substitution )
            int alt80=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt80=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt80=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt80=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 80, 0, input);

                throw nvae;
            }

            switch (alt80) {
                case 1 :
                    // EsperEPL2Ast.g:299:34: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_frequencyOperator1870);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:299:49: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_frequencyOperator1873);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:299:67: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_frequencyOperator1875);
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
    // EsperEPL2Ast.g:302:1: lastOperator : ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void lastOperator() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:303:2: ( ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:303:4: ^(l= LAST_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) )
            {
            l=(CommonTree)match(input,LAST_OPERATOR,FOLLOW_LAST_OPERATOR_in_lastOperator1894); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:303:23: ( constant[true] | eventPropertyExpr | substitution )
            int alt81=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt81=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt81=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt81=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 81, 0, input);

                throw nvae;
            }

            switch (alt81) {
                case 1 :
                    // EsperEPL2Ast.g:303:24: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_lastOperator1897);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:303:39: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_lastOperator1900);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:303:57: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_lastOperator1902);
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
    // EsperEPL2Ast.g:306:1: weekDayOperator : ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) ;
    public final void weekDayOperator() throws RecognitionException {
        CommonTree w=null;

        try {
            // EsperEPL2Ast.g:307:2: ( ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) ) )
            // EsperEPL2Ast.g:307:4: ^(w= WEEKDAY_OPERATOR ( constant[true] | eventPropertyExpr | substitution ) )
            {
            w=(CommonTree)match(input,WEEKDAY_OPERATOR,FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator1921); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:307:26: ( constant[true] | eventPropertyExpr | substitution )
            int alt82=3;
            switch ( input.LA(1) ) {
            case INT_TYPE:
            case LONG_TYPE:
            case FLOAT_TYPE:
            case DOUBLE_TYPE:
            case STRING_TYPE:
            case BOOL_TYPE:
            case NULL_TYPE:
                {
                alt82=1;
                }
                break;
            case EVENT_PROP_EXPR:
                {
                alt82=2;
                }
                break;
            case SUBSTITUTION:
                {
                alt82=3;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 82, 0, input);

                throw nvae;
            }

            switch (alt82) {
                case 1 :
                    // EsperEPL2Ast.g:307:27: constant[true]
                    {
                    pushFollow(FOLLOW_constant_in_weekDayOperator1924);
                    constant(true);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:307:42: eventPropertyExpr
                    {
                    pushFollow(FOLLOW_eventPropertyExpr_in_weekDayOperator1927);
                    eventPropertyExpr();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:307:60: substitution
                    {
                    pushFollow(FOLLOW_substitution_in_weekDayOperator1929);
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
    // EsperEPL2Ast.g:310:1: subSelectRowExpr : ^(s= SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectRowExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:311:2: ( ^(s= SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:311:4: ^(s= SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            s=(CommonTree)match(input,SUBSELECT_EXPR,FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr1950); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectRowExpr1952);
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
    // EsperEPL2Ast.g:314:1: subSelectExistsExpr : ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) ;
    public final void subSelectExistsExpr() throws RecognitionException {
        CommonTree e=null;

        try {
            // EsperEPL2Ast.g:315:2: ( ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:315:4: ^(e= EXISTS_SUBSELECT_EXPR subQueryExpr )
            {
            pushStmtContext();
            e=(CommonTree)match(input,EXISTS_SUBSELECT_EXPR,FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr1971); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectExistsExpr1973);
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
    // EsperEPL2Ast.g:318:1: subSelectInExpr : ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) );
    public final void subSelectInExpr() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:319:2: ( ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) | ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr ) )
            int alt83=2;
            int LA83_0 = input.LA(1);

            if ( (LA83_0==IN_SUBSELECT_EXPR) ) {
                alt83=1;
            }
            else if ( (LA83_0==NOT_IN_SUBSELECT_EXPR) ) {
                alt83=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 83, 0, input);

                throw nvae;
            }
            switch (alt83) {
                case 1 :
                    // EsperEPL2Ast.g:319:5: ^(s= IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,IN_SUBSELECT_EXPR,FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr1992); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr1994);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr1996);
                    subSelectInQueryExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(s); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:320:5: ^(s= NOT_IN_SUBSELECT_EXPR valueExpr subSelectInQueryExpr )
                    {
                    s=(CommonTree)match(input,NOT_IN_SUBSELECT_EXPR,FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2008); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_subSelectInExpr2010);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2012);
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
    // EsperEPL2Ast.g:323:1: subSelectInQueryExpr : ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) ;
    public final void subSelectInQueryExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:324:2: ( ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr ) )
            // EsperEPL2Ast.g:324:4: ^(i= IN_SUBSELECT_QUERY_EXPR subQueryExpr )
            {
            pushStmtContext();
            i=(CommonTree)match(input,IN_SUBSELECT_QUERY_EXPR,FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2031); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_subQueryExpr_in_subSelectInQueryExpr2033);
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
    // EsperEPL2Ast.g:327:1: subQueryExpr : selectionListElement subSelectFilterExpr ( viewExpr )* ( IDENT )? ( whereClause )? ;
    public final void subQueryExpr() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:328:2: ( selectionListElement subSelectFilterExpr ( viewExpr )* ( IDENT )? ( whereClause )? )
            // EsperEPL2Ast.g:328:4: selectionListElement subSelectFilterExpr ( viewExpr )* ( IDENT )? ( whereClause )?
            {
            pushFollow(FOLLOW_selectionListElement_in_subQueryExpr2049);
            selectionListElement();

            state._fsp--;

            pushFollow(FOLLOW_subSelectFilterExpr_in_subQueryExpr2051);
            subSelectFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:328:45: ( viewExpr )*
            loop84:
            do {
                int alt84=2;
                int LA84_0 = input.LA(1);

                if ( (LA84_0==VIEW_EXPR) ) {
                    alt84=1;
                }


                switch (alt84) {
            	case 1 :
            	    // EsperEPL2Ast.g:328:46: viewExpr
            	    {
            	    pushFollow(FOLLOW_viewExpr_in_subQueryExpr2054);
            	    viewExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop84;
                }
            } while (true);

            // EsperEPL2Ast.g:328:57: ( IDENT )?
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==IDENT) ) {
                alt85=1;
            }
            switch (alt85) {
                case 1 :
                    // EsperEPL2Ast.g:328:58: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subQueryExpr2059); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:328:66: ( whereClause )?
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==WHERE_EXPR) ) {
                alt86=1;
            }
            switch (alt86) {
                case 1 :
                    // EsperEPL2Ast.g:328:67: whereClause
                    {
                    pushFollow(FOLLOW_whereClause_in_subQueryExpr2064);
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
    // EsperEPL2Ast.g:331:1: subSelectFilterExpr : ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ) ;
    public final void subSelectFilterExpr() throws RecognitionException {
        CommonTree v=null;

        try {
            // EsperEPL2Ast.g:332:2: ( ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? ) )
            // EsperEPL2Ast.g:332:4: ^(v= STREAM_EXPR eventFilterExpr ( viewListExpr )? ( IDENT )? )
            {
            v=(CommonTree)match(input,STREAM_EXPR,FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2081); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventFilterExpr_in_subSelectFilterExpr2083);
            eventFilterExpr();

            state._fsp--;

            // EsperEPL2Ast.g:332:36: ( viewListExpr )?
            int alt87=2;
            int LA87_0 = input.LA(1);

            if ( (LA87_0==VIEW_EXPR) ) {
                alt87=1;
            }
            switch (alt87) {
                case 1 :
                    // EsperEPL2Ast.g:332:37: viewListExpr
                    {
                    pushFollow(FOLLOW_viewListExpr_in_subSelectFilterExpr2086);
                    viewListExpr();

                    state._fsp--;


                    }
                    break;

            }

            // EsperEPL2Ast.g:332:52: ( IDENT )?
            int alt88=2;
            int LA88_0 = input.LA(1);

            if ( (LA88_0==IDENT) ) {
                alt88=1;
            }
            switch (alt88) {
                case 1 :
                    // EsperEPL2Ast.g:332:53: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_subSelectFilterExpr2091); 

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
    // EsperEPL2Ast.g:335:1: caseExpr : ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) );
    public final void caseExpr() throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:336:2: ( ^(c= CASE ( valueExpr )* ) | ^(c= CASE2 ( valueExpr )* ) )
            int alt91=2;
            int LA91_0 = input.LA(1);

            if ( (LA91_0==CASE) ) {
                alt91=1;
            }
            else if ( (LA91_0==CASE2) ) {
                alt91=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 91, 0, input);

                throw nvae;
            }
            switch (alt91) {
                case 1 :
                    // EsperEPL2Ast.g:336:4: ^(c= CASE ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE,FOLLOW_CASE_in_caseExpr2112); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:336:13: ( valueExpr )*
                        loop89:
                        do {
                            int alt89=2;
                            int LA89_0 = input.LA(1);

                            if ( ((LA89_0>=IN_SET && LA89_0<=REGEXP)||LA89_0==NOT_EXPR||(LA89_0>=SUM && LA89_0<=AVG)||(LA89_0>=COALESCE && LA89_0<=COUNT)||(LA89_0>=CASE && LA89_0<=CASE2)||(LA89_0>=PREVIOUS && LA89_0<=EXISTS)||(LA89_0>=INSTANCEOF && LA89_0<=CURRENT_TIMESTAMP)||(LA89_0>=EVAL_AND_EXPR && LA89_0<=EVAL_NOTEQUALS_EXPR)||LA89_0==EVENT_PROP_EXPR||(LA89_0>=CONCAT && LA89_0<=LIB_FUNCTION)||LA89_0==ARRAY_EXPR||(LA89_0>=NOT_IN_SET && LA89_0<=NOT_REGEXP)||(LA89_0>=IN_RANGE && LA89_0<=NOT_IN_SUBSELECT_EXPR)||LA89_0==SUBSTITUTION||(LA89_0>=INT_TYPE && LA89_0<=NULL_TYPE)||LA89_0==STAR||(LA89_0>=BAND && LA89_0<=BXOR)||(LA89_0>=LT && LA89_0<=GE)||(LA89_0>=PLUS && LA89_0<=MOD)) ) {
                                alt89=1;
                            }


                            switch (alt89) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:336:14: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2115);
                        	    valueExpr();

                        	    state._fsp--;


                        	    }
                        	    break;

                        	default :
                        	    break loop89;
                            }
                        } while (true);


                        match(input, Token.UP, null); 
                    }
                     leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:337:4: ^(c= CASE2 ( valueExpr )* )
                    {
                    c=(CommonTree)match(input,CASE2,FOLLOW_CASE2_in_caseExpr2128); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:337:14: ( valueExpr )*
                        loop90:
                        do {
                            int alt90=2;
                            int LA90_0 = input.LA(1);

                            if ( ((LA90_0>=IN_SET && LA90_0<=REGEXP)||LA90_0==NOT_EXPR||(LA90_0>=SUM && LA90_0<=AVG)||(LA90_0>=COALESCE && LA90_0<=COUNT)||(LA90_0>=CASE && LA90_0<=CASE2)||(LA90_0>=PREVIOUS && LA90_0<=EXISTS)||(LA90_0>=INSTANCEOF && LA90_0<=CURRENT_TIMESTAMP)||(LA90_0>=EVAL_AND_EXPR && LA90_0<=EVAL_NOTEQUALS_EXPR)||LA90_0==EVENT_PROP_EXPR||(LA90_0>=CONCAT && LA90_0<=LIB_FUNCTION)||LA90_0==ARRAY_EXPR||(LA90_0>=NOT_IN_SET && LA90_0<=NOT_REGEXP)||(LA90_0>=IN_RANGE && LA90_0<=NOT_IN_SUBSELECT_EXPR)||LA90_0==SUBSTITUTION||(LA90_0>=INT_TYPE && LA90_0<=NULL_TYPE)||LA90_0==STAR||(LA90_0>=BAND && LA90_0<=BXOR)||(LA90_0>=LT && LA90_0<=GE)||(LA90_0>=PLUS && LA90_0<=MOD)) ) {
                                alt90=1;
                            }


                            switch (alt90) {
                        	case 1 :
                        	    // EsperEPL2Ast.g:337:15: valueExpr
                        	    {
                        	    pushFollow(FOLLOW_valueExpr_in_caseExpr2131);
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
    // EsperEPL2Ast.g:340:1: inExpr : ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) );
    public final void inExpr() throws RecognitionException {
        CommonTree i=null;

        try {
            // EsperEPL2Ast.g:341:2: ( ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) ) | ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) | ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) ) )
            int alt94=4;
            switch ( input.LA(1) ) {
            case IN_SET:
                {
                alt94=1;
                }
                break;
            case NOT_IN_SET:
                {
                alt94=2;
                }
                break;
            case IN_RANGE:
                {
                alt94=3;
                }
                break;
            case NOT_IN_RANGE:
                {
                alt94=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 94, 0, input);

                throw nvae;
            }

            switch (alt94) {
                case 1 :
                    // EsperEPL2Ast.g:341:4: ^(i= IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_SET,FOLLOW_IN_SET_in_inExpr2151); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2153);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2161);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:341:51: ( valueExpr )*
                    loop92:
                    do {
                        int alt92=2;
                        int LA92_0 = input.LA(1);

                        if ( ((LA92_0>=IN_SET && LA92_0<=REGEXP)||LA92_0==NOT_EXPR||(LA92_0>=SUM && LA92_0<=AVG)||(LA92_0>=COALESCE && LA92_0<=COUNT)||(LA92_0>=CASE && LA92_0<=CASE2)||(LA92_0>=PREVIOUS && LA92_0<=EXISTS)||(LA92_0>=INSTANCEOF && LA92_0<=CURRENT_TIMESTAMP)||(LA92_0>=EVAL_AND_EXPR && LA92_0<=EVAL_NOTEQUALS_EXPR)||LA92_0==EVENT_PROP_EXPR||(LA92_0>=CONCAT && LA92_0<=LIB_FUNCTION)||LA92_0==ARRAY_EXPR||(LA92_0>=NOT_IN_SET && LA92_0<=NOT_REGEXP)||(LA92_0>=IN_RANGE && LA92_0<=NOT_IN_SUBSELECT_EXPR)||LA92_0==SUBSTITUTION||(LA92_0>=INT_TYPE && LA92_0<=NULL_TYPE)||LA92_0==STAR||(LA92_0>=BAND && LA92_0<=BXOR)||(LA92_0>=LT && LA92_0<=GE)||(LA92_0>=PLUS && LA92_0<=MOD)) ) {
                            alt92=1;
                        }


                        switch (alt92) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:341:52: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2164);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop92;
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
                    // EsperEPL2Ast.g:342:4: ^(i= NOT_IN_SET valueExpr ( LPAREN | LBRACK ) valueExpr ( valueExpr )* ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_SET,FOLLOW_NOT_IN_SET_in_inExpr2183); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2185);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2193);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:342:55: ( valueExpr )*
                    loop93:
                    do {
                        int alt93=2;
                        int LA93_0 = input.LA(1);

                        if ( ((LA93_0>=IN_SET && LA93_0<=REGEXP)||LA93_0==NOT_EXPR||(LA93_0>=SUM && LA93_0<=AVG)||(LA93_0>=COALESCE && LA93_0<=COUNT)||(LA93_0>=CASE && LA93_0<=CASE2)||(LA93_0>=PREVIOUS && LA93_0<=EXISTS)||(LA93_0>=INSTANCEOF && LA93_0<=CURRENT_TIMESTAMP)||(LA93_0>=EVAL_AND_EXPR && LA93_0<=EVAL_NOTEQUALS_EXPR)||LA93_0==EVENT_PROP_EXPR||(LA93_0>=CONCAT && LA93_0<=LIB_FUNCTION)||LA93_0==ARRAY_EXPR||(LA93_0>=NOT_IN_SET && LA93_0<=NOT_REGEXP)||(LA93_0>=IN_RANGE && LA93_0<=NOT_IN_SUBSELECT_EXPR)||LA93_0==SUBSTITUTION||(LA93_0>=INT_TYPE && LA93_0<=NULL_TYPE)||LA93_0==STAR||(LA93_0>=BAND && LA93_0<=BXOR)||(LA93_0>=LT && LA93_0<=GE)||(LA93_0>=PLUS && LA93_0<=MOD)) ) {
                            alt93=1;
                        }


                        switch (alt93) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:342:56: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_inExpr2196);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop93;
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
                    // EsperEPL2Ast.g:343:4: ^(i= IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,IN_RANGE,FOLLOW_IN_RANGE_in_inExpr2215); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2217);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2225);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2227);
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
                    // EsperEPL2Ast.g:344:4: ^(i= NOT_IN_RANGE valueExpr ( LPAREN | LBRACK ) valueExpr valueExpr ( RPAREN | RBRACK ) )
                    {
                    i=(CommonTree)match(input,NOT_IN_RANGE,FOLLOW_NOT_IN_RANGE_in_inExpr2244); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_inExpr2246);
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

                    pushFollow(FOLLOW_valueExpr_in_inExpr2254);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_inExpr2256);
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
    // EsperEPL2Ast.g:347:1: betweenExpr : ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) );
    public final void betweenExpr() throws RecognitionException {
        CommonTree b=null;

        try {
            // EsperEPL2Ast.g:348:2: ( ^(b= BETWEEN valueExpr valueExpr valueExpr ) | ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* ) )
            int alt96=2;
            int LA96_0 = input.LA(1);

            if ( (LA96_0==BETWEEN) ) {
                alt96=1;
            }
            else if ( (LA96_0==NOT_BETWEEN) ) {
                alt96=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 96, 0, input);

                throw nvae;
            }
            switch (alt96) {
                case 1 :
                    // EsperEPL2Ast.g:348:4: ^(b= BETWEEN valueExpr valueExpr valueExpr )
                    {
                    b=(CommonTree)match(input,BETWEEN,FOLLOW_BETWEEN_in_betweenExpr2281); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2283);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2285);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2287);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(b); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:349:4: ^(b= NOT_BETWEEN valueExpr valueExpr ( valueExpr )* )
                    {
                    b=(CommonTree)match(input,NOT_BETWEEN,FOLLOW_NOT_BETWEEN_in_betweenExpr2298); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2300);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_betweenExpr2302);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:349:40: ( valueExpr )*
                    loop95:
                    do {
                        int alt95=2;
                        int LA95_0 = input.LA(1);

                        if ( ((LA95_0>=IN_SET && LA95_0<=REGEXP)||LA95_0==NOT_EXPR||(LA95_0>=SUM && LA95_0<=AVG)||(LA95_0>=COALESCE && LA95_0<=COUNT)||(LA95_0>=CASE && LA95_0<=CASE2)||(LA95_0>=PREVIOUS && LA95_0<=EXISTS)||(LA95_0>=INSTANCEOF && LA95_0<=CURRENT_TIMESTAMP)||(LA95_0>=EVAL_AND_EXPR && LA95_0<=EVAL_NOTEQUALS_EXPR)||LA95_0==EVENT_PROP_EXPR||(LA95_0>=CONCAT && LA95_0<=LIB_FUNCTION)||LA95_0==ARRAY_EXPR||(LA95_0>=NOT_IN_SET && LA95_0<=NOT_REGEXP)||(LA95_0>=IN_RANGE && LA95_0<=NOT_IN_SUBSELECT_EXPR)||LA95_0==SUBSTITUTION||(LA95_0>=INT_TYPE && LA95_0<=NULL_TYPE)||LA95_0==STAR||(LA95_0>=BAND && LA95_0<=BXOR)||(LA95_0>=LT && LA95_0<=GE)||(LA95_0>=PLUS && LA95_0<=MOD)) ) {
                            alt95=1;
                        }


                        switch (alt95) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:349:41: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_betweenExpr2305);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop95;
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
    // EsperEPL2Ast.g:352:1: likeExpr : ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) );
    public final void likeExpr() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:353:2: ( ^(l= LIKE valueExpr valueExpr ( valueExpr )? ) | ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? ) )
            int alt99=2;
            int LA99_0 = input.LA(1);

            if ( (LA99_0==LIKE) ) {
                alt99=1;
            }
            else if ( (LA99_0==NOT_LIKE) ) {
                alt99=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 99, 0, input);

                throw nvae;
            }
            switch (alt99) {
                case 1 :
                    // EsperEPL2Ast.g:353:4: ^(l= LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,LIKE,FOLLOW_LIKE_in_likeExpr2325); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2327);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2329);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:353:33: ( valueExpr )?
                    int alt97=2;
                    int LA97_0 = input.LA(1);

                    if ( ((LA97_0>=IN_SET && LA97_0<=REGEXP)||LA97_0==NOT_EXPR||(LA97_0>=SUM && LA97_0<=AVG)||(LA97_0>=COALESCE && LA97_0<=COUNT)||(LA97_0>=CASE && LA97_0<=CASE2)||(LA97_0>=PREVIOUS && LA97_0<=EXISTS)||(LA97_0>=INSTANCEOF && LA97_0<=CURRENT_TIMESTAMP)||(LA97_0>=EVAL_AND_EXPR && LA97_0<=EVAL_NOTEQUALS_EXPR)||LA97_0==EVENT_PROP_EXPR||(LA97_0>=CONCAT && LA97_0<=LIB_FUNCTION)||LA97_0==ARRAY_EXPR||(LA97_0>=NOT_IN_SET && LA97_0<=NOT_REGEXP)||(LA97_0>=IN_RANGE && LA97_0<=NOT_IN_SUBSELECT_EXPR)||LA97_0==SUBSTITUTION||(LA97_0>=INT_TYPE && LA97_0<=NULL_TYPE)||LA97_0==STAR||(LA97_0>=BAND && LA97_0<=BXOR)||(LA97_0>=LT && LA97_0<=GE)||(LA97_0>=PLUS && LA97_0<=MOD)) ) {
                        alt97=1;
                    }
                    switch (alt97) {
                        case 1 :
                            // EsperEPL2Ast.g:353:34: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2332);
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
                    // EsperEPL2Ast.g:354:4: ^(l= NOT_LIKE valueExpr valueExpr ( valueExpr )? )
                    {
                    l=(CommonTree)match(input,NOT_LIKE,FOLLOW_NOT_LIKE_in_likeExpr2345); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_likeExpr2347);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_likeExpr2349);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:354:37: ( valueExpr )?
                    int alt98=2;
                    int LA98_0 = input.LA(1);

                    if ( ((LA98_0>=IN_SET && LA98_0<=REGEXP)||LA98_0==NOT_EXPR||(LA98_0>=SUM && LA98_0<=AVG)||(LA98_0>=COALESCE && LA98_0<=COUNT)||(LA98_0>=CASE && LA98_0<=CASE2)||(LA98_0>=PREVIOUS && LA98_0<=EXISTS)||(LA98_0>=INSTANCEOF && LA98_0<=CURRENT_TIMESTAMP)||(LA98_0>=EVAL_AND_EXPR && LA98_0<=EVAL_NOTEQUALS_EXPR)||LA98_0==EVENT_PROP_EXPR||(LA98_0>=CONCAT && LA98_0<=LIB_FUNCTION)||LA98_0==ARRAY_EXPR||(LA98_0>=NOT_IN_SET && LA98_0<=NOT_REGEXP)||(LA98_0>=IN_RANGE && LA98_0<=NOT_IN_SUBSELECT_EXPR)||LA98_0==SUBSTITUTION||(LA98_0>=INT_TYPE && LA98_0<=NULL_TYPE)||LA98_0==STAR||(LA98_0>=BAND && LA98_0<=BXOR)||(LA98_0>=LT && LA98_0<=GE)||(LA98_0>=PLUS && LA98_0<=MOD)) ) {
                        alt98=1;
                    }
                    switch (alt98) {
                        case 1 :
                            // EsperEPL2Ast.g:354:38: valueExpr
                            {
                            pushFollow(FOLLOW_valueExpr_in_likeExpr2352);
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
    // EsperEPL2Ast.g:357:1: regExpExpr : ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) );
    public final void regExpExpr() throws RecognitionException {
        CommonTree r=null;

        try {
            // EsperEPL2Ast.g:358:2: ( ^(r= REGEXP valueExpr valueExpr ) | ^(r= NOT_REGEXP valueExpr valueExpr ) )
            int alt100=2;
            int LA100_0 = input.LA(1);

            if ( (LA100_0==REGEXP) ) {
                alt100=1;
            }
            else if ( (LA100_0==NOT_REGEXP) ) {
                alt100=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 100, 0, input);

                throw nvae;
            }
            switch (alt100) {
                case 1 :
                    // EsperEPL2Ast.g:358:4: ^(r= REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,REGEXP,FOLLOW_REGEXP_in_regExpExpr2371); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2373);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2375);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(r); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:359:4: ^(r= NOT_REGEXP valueExpr valueExpr )
                    {
                    r=(CommonTree)match(input,NOT_REGEXP,FOLLOW_NOT_REGEXP_in_regExpExpr2386); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2388);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_regExpExpr2390);
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
    // EsperEPL2Ast.g:362:1: builtinFunc : ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr ) | ^(f= CURRENT_TIMESTAMP ) );
    public final void builtinFunc() throws RecognitionException {
        CommonTree f=null;
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:363:2: ( ^(f= SUM ( DISTINCT )? valueExpr ) | ^(f= AVG ( DISTINCT )? valueExpr ) | ^(f= COUNT ( ( DISTINCT )? valueExpr )? ) | ^(f= MEDIAN ( DISTINCT )? valueExpr ) | ^(f= STDDEV ( DISTINCT )? valueExpr ) | ^(f= AVEDEV ( DISTINCT )? valueExpr ) | ^(f= COALESCE valueExpr valueExpr ( valueExpr )* ) | ^(f= PREVIOUS valueExpr eventPropertyExpr ) | ^(f= PRIOR c= NUM_INT eventPropertyExpr ) | ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* ) | ^(f= CAST valueExpr CLASS_IDENT ) | ^(f= EXISTS eventPropertyExpr ) | ^(f= CURRENT_TIMESTAMP ) )
            int alt110=13;
            switch ( input.LA(1) ) {
            case SUM:
                {
                alt110=1;
                }
                break;
            case AVG:
                {
                alt110=2;
                }
                break;
            case COUNT:
                {
                alt110=3;
                }
                break;
            case MEDIAN:
                {
                alt110=4;
                }
                break;
            case STDDEV:
                {
                alt110=5;
                }
                break;
            case AVEDEV:
                {
                alt110=6;
                }
                break;
            case COALESCE:
                {
                alt110=7;
                }
                break;
            case PREVIOUS:
                {
                alt110=8;
                }
                break;
            case PRIOR:
                {
                alt110=9;
                }
                break;
            case INSTANCEOF:
                {
                alt110=10;
                }
                break;
            case CAST:
                {
                alt110=11;
                }
                break;
            case EXISTS:
                {
                alt110=12;
                }
                break;
            case CURRENT_TIMESTAMP:
                {
                alt110=13;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 110, 0, input);

                throw nvae;
            }

            switch (alt110) {
                case 1 :
                    // EsperEPL2Ast.g:363:5: ^(f= SUM ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,SUM,FOLLOW_SUM_in_builtinFunc2409); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:363:13: ( DISTINCT )?
                    int alt101=2;
                    int LA101_0 = input.LA(1);

                    if ( (LA101_0==DISTINCT) ) {
                        alt101=1;
                    }
                    switch (alt101) {
                        case 1 :
                            // EsperEPL2Ast.g:363:14: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2412); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2416);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:364:4: ^(f= AVG ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVG,FOLLOW_AVG_in_builtinFunc2427); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:364:12: ( DISTINCT )?
                    int alt102=2;
                    int LA102_0 = input.LA(1);

                    if ( (LA102_0==DISTINCT) ) {
                        alt102=1;
                    }
                    switch (alt102) {
                        case 1 :
                            // EsperEPL2Ast.g:364:13: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2430); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2434);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:365:4: ^(f= COUNT ( ( DISTINCT )? valueExpr )? )
                    {
                    f=(CommonTree)match(input,COUNT,FOLLOW_COUNT_in_builtinFunc2445); 

                    if ( input.LA(1)==Token.DOWN ) {
                        match(input, Token.DOWN, null); 
                        // EsperEPL2Ast.g:365:14: ( ( DISTINCT )? valueExpr )?
                        int alt104=2;
                        int LA104_0 = input.LA(1);

                        if ( ((LA104_0>=IN_SET && LA104_0<=REGEXP)||LA104_0==NOT_EXPR||(LA104_0>=SUM && LA104_0<=AVG)||(LA104_0>=COALESCE && LA104_0<=COUNT)||(LA104_0>=CASE && LA104_0<=CASE2)||LA104_0==DISTINCT||(LA104_0>=PREVIOUS && LA104_0<=EXISTS)||(LA104_0>=INSTANCEOF && LA104_0<=CURRENT_TIMESTAMP)||(LA104_0>=EVAL_AND_EXPR && LA104_0<=EVAL_NOTEQUALS_EXPR)||LA104_0==EVENT_PROP_EXPR||(LA104_0>=CONCAT && LA104_0<=LIB_FUNCTION)||LA104_0==ARRAY_EXPR||(LA104_0>=NOT_IN_SET && LA104_0<=NOT_REGEXP)||(LA104_0>=IN_RANGE && LA104_0<=NOT_IN_SUBSELECT_EXPR)||LA104_0==SUBSTITUTION||(LA104_0>=INT_TYPE && LA104_0<=NULL_TYPE)||LA104_0==STAR||(LA104_0>=BAND && LA104_0<=BXOR)||(LA104_0>=LT && LA104_0<=GE)||(LA104_0>=PLUS && LA104_0<=MOD)) ) {
                            alt104=1;
                        }
                        switch (alt104) {
                            case 1 :
                                // EsperEPL2Ast.g:365:15: ( DISTINCT )? valueExpr
                                {
                                // EsperEPL2Ast.g:365:15: ( DISTINCT )?
                                int alt103=2;
                                int LA103_0 = input.LA(1);

                                if ( (LA103_0==DISTINCT) ) {
                                    alt103=1;
                                }
                                switch (alt103) {
                                    case 1 :
                                        // EsperEPL2Ast.g:365:16: DISTINCT
                                        {
                                        match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2449); 

                                        }
                                        break;

                                }

                                pushFollow(FOLLOW_valueExpr_in_builtinFunc2453);
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
                    // EsperEPL2Ast.g:366:4: ^(f= MEDIAN ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,MEDIAN,FOLLOW_MEDIAN_in_builtinFunc2467); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:366:15: ( DISTINCT )?
                    int alt105=2;
                    int LA105_0 = input.LA(1);

                    if ( (LA105_0==DISTINCT) ) {
                        alt105=1;
                    }
                    switch (alt105) {
                        case 1 :
                            // EsperEPL2Ast.g:366:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2470); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2474);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:367:4: ^(f= STDDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,STDDEV,FOLLOW_STDDEV_in_builtinFunc2485); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:367:15: ( DISTINCT )?
                    int alt106=2;
                    int LA106_0 = input.LA(1);

                    if ( (LA106_0==DISTINCT) ) {
                        alt106=1;
                    }
                    switch (alt106) {
                        case 1 :
                            // EsperEPL2Ast.g:367:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2488); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2492);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:368:4: ^(f= AVEDEV ( DISTINCT )? valueExpr )
                    {
                    f=(CommonTree)match(input,AVEDEV,FOLLOW_AVEDEV_in_builtinFunc2503); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:368:15: ( DISTINCT )?
                    int alt107=2;
                    int LA107_0 = input.LA(1);

                    if ( (LA107_0==DISTINCT) ) {
                        alt107=1;
                    }
                    switch (alt107) {
                        case 1 :
                            // EsperEPL2Ast.g:368:16: DISTINCT
                            {
                            match(input,DISTINCT,FOLLOW_DISTINCT_in_builtinFunc2506); 

                            }
                            break;

                    }

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2510);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:369:5: ^(f= COALESCE valueExpr valueExpr ( valueExpr )* )
                    {
                    f=(CommonTree)match(input,COALESCE,FOLLOW_COALESCE_in_builtinFunc2522); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2524);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2526);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:369:38: ( valueExpr )*
                    loop108:
                    do {
                        int alt108=2;
                        int LA108_0 = input.LA(1);

                        if ( ((LA108_0>=IN_SET && LA108_0<=REGEXP)||LA108_0==NOT_EXPR||(LA108_0>=SUM && LA108_0<=AVG)||(LA108_0>=COALESCE && LA108_0<=COUNT)||(LA108_0>=CASE && LA108_0<=CASE2)||(LA108_0>=PREVIOUS && LA108_0<=EXISTS)||(LA108_0>=INSTANCEOF && LA108_0<=CURRENT_TIMESTAMP)||(LA108_0>=EVAL_AND_EXPR && LA108_0<=EVAL_NOTEQUALS_EXPR)||LA108_0==EVENT_PROP_EXPR||(LA108_0>=CONCAT && LA108_0<=LIB_FUNCTION)||LA108_0==ARRAY_EXPR||(LA108_0>=NOT_IN_SET && LA108_0<=NOT_REGEXP)||(LA108_0>=IN_RANGE && LA108_0<=NOT_IN_SUBSELECT_EXPR)||LA108_0==SUBSTITUTION||(LA108_0>=INT_TYPE && LA108_0<=NULL_TYPE)||LA108_0==STAR||(LA108_0>=BAND && LA108_0<=BXOR)||(LA108_0>=LT && LA108_0<=GE)||(LA108_0>=PLUS && LA108_0<=MOD)) ) {
                            alt108=1;
                        }


                        switch (alt108) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:369:39: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_builtinFunc2529);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop108;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:370:5: ^(f= PREVIOUS valueExpr eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,PREVIOUS,FOLLOW_PREVIOUS_in_builtinFunc2544); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2546);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2548);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:371:5: ^(f= PRIOR c= NUM_INT eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,PRIOR,FOLLOW_PRIOR_in_builtinFunc2560); 

                    match(input, Token.DOWN, null); 
                    c=(CommonTree)match(input,NUM_INT,FOLLOW_NUM_INT_in_builtinFunc2564); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2566);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                    leaveNode(c); leaveNode(f);

                    }
                    break;
                case 10 :
                    // EsperEPL2Ast.g:372:5: ^(f= INSTANCEOF valueExpr CLASS_IDENT ( CLASS_IDENT )* )
                    {
                    f=(CommonTree)match(input,INSTANCEOF,FOLLOW_INSTANCEOF_in_builtinFunc2578); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2580);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2582); 
                    // EsperEPL2Ast.g:372:42: ( CLASS_IDENT )*
                    loop109:
                    do {
                        int alt109=2;
                        int LA109_0 = input.LA(1);

                        if ( (LA109_0==CLASS_IDENT) ) {
                            alt109=1;
                        }


                        switch (alt109) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:372:43: CLASS_IDENT
                    	    {
                    	    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2585); 

                    	    }
                    	    break;

                    	default :
                    	    break loop109;
                        }
                    } while (true);


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:373:5: ^(f= CAST valueExpr CLASS_IDENT )
                    {
                    f=(CommonTree)match(input,CAST,FOLLOW_CAST_in_builtinFunc2599); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_builtinFunc2601);
                    valueExpr();

                    state._fsp--;

                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_builtinFunc2603); 

                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:374:5: ^(f= EXISTS eventPropertyExpr )
                    {
                    f=(CommonTree)match(input,EXISTS,FOLLOW_EXISTS_in_builtinFunc2615); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_eventPropertyExpr_in_builtinFunc2617);
                    eventPropertyExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(f); 

                    }
                    break;
                case 13 :
                    // EsperEPL2Ast.g:375:4: ^(f= CURRENT_TIMESTAMP )
                    {
                    f=(CommonTree)match(input,CURRENT_TIMESTAMP,FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2628); 



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
    // EsperEPL2Ast.g:378:1: arrayExpr : ^(a= ARRAY_EXPR ( valueExpr )* ) ;
    public final void arrayExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:379:2: ( ^(a= ARRAY_EXPR ( valueExpr )* ) )
            // EsperEPL2Ast.g:379:4: ^(a= ARRAY_EXPR ( valueExpr )* )
            {
            a=(CommonTree)match(input,ARRAY_EXPR,FOLLOW_ARRAY_EXPR_in_arrayExpr2648); 

            if ( input.LA(1)==Token.DOWN ) {
                match(input, Token.DOWN, null); 
                // EsperEPL2Ast.g:379:19: ( valueExpr )*
                loop111:
                do {
                    int alt111=2;
                    int LA111_0 = input.LA(1);

                    if ( ((LA111_0>=IN_SET && LA111_0<=REGEXP)||LA111_0==NOT_EXPR||(LA111_0>=SUM && LA111_0<=AVG)||(LA111_0>=COALESCE && LA111_0<=COUNT)||(LA111_0>=CASE && LA111_0<=CASE2)||(LA111_0>=PREVIOUS && LA111_0<=EXISTS)||(LA111_0>=INSTANCEOF && LA111_0<=CURRENT_TIMESTAMP)||(LA111_0>=EVAL_AND_EXPR && LA111_0<=EVAL_NOTEQUALS_EXPR)||LA111_0==EVENT_PROP_EXPR||(LA111_0>=CONCAT && LA111_0<=LIB_FUNCTION)||LA111_0==ARRAY_EXPR||(LA111_0>=NOT_IN_SET && LA111_0<=NOT_REGEXP)||(LA111_0>=IN_RANGE && LA111_0<=NOT_IN_SUBSELECT_EXPR)||LA111_0==SUBSTITUTION||(LA111_0>=INT_TYPE && LA111_0<=NULL_TYPE)||LA111_0==STAR||(LA111_0>=BAND && LA111_0<=BXOR)||(LA111_0>=LT && LA111_0<=GE)||(LA111_0>=PLUS && LA111_0<=MOD)) ) {
                        alt111=1;
                    }


                    switch (alt111) {
                	case 1 :
                	    // EsperEPL2Ast.g:379:20: valueExpr
                	    {
                	    pushFollow(FOLLOW_valueExpr_in_arrayExpr2651);
                	    valueExpr();

                	    state._fsp--;


                	    }
                	    break;

                	default :
                	    break loop111;
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
    // EsperEPL2Ast.g:382:1: arithmeticExpr : ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) );
    public final void arithmeticExpr() throws RecognitionException {
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:383:2: ( ^(a= PLUS valueExpr valueExpr ) | ^(a= MINUS valueExpr valueExpr ) | ^(a= DIV valueExpr valueExpr ) | ^(a= STAR valueExpr valueExpr ) | ^(a= MOD valueExpr valueExpr ) | ^(a= BAND valueExpr valueExpr ) | ^(a= BOR valueExpr valueExpr ) | ^(a= BXOR valueExpr valueExpr ) | ^(a= CONCAT valueExpr valueExpr ( valueExpr )* ) )
            int alt113=9;
            switch ( input.LA(1) ) {
            case PLUS:
                {
                alt113=1;
                }
                break;
            case MINUS:
                {
                alt113=2;
                }
                break;
            case DIV:
                {
                alt113=3;
                }
                break;
            case STAR:
                {
                alt113=4;
                }
                break;
            case MOD:
                {
                alt113=5;
                }
                break;
            case BAND:
                {
                alt113=6;
                }
                break;
            case BOR:
                {
                alt113=7;
                }
                break;
            case BXOR:
                {
                alt113=8;
                }
                break;
            case CONCAT:
                {
                alt113=9;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 113, 0, input);

                throw nvae;
            }

            switch (alt113) {
                case 1 :
                    // EsperEPL2Ast.g:383:5: ^(a= PLUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,PLUS,FOLLOW_PLUS_in_arithmeticExpr2672); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2674);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2676);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:384:5: ^(a= MINUS valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MINUS,FOLLOW_MINUS_in_arithmeticExpr2688); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2690);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2692);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:385:5: ^(a= DIV valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,DIV,FOLLOW_DIV_in_arithmeticExpr2704); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2706);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2708);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:386:4: ^(a= STAR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,STAR,FOLLOW_STAR_in_arithmeticExpr2719); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2721);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2723);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:387:5: ^(a= MOD valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,MOD,FOLLOW_MOD_in_arithmeticExpr2735); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2737);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2739);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:388:4: ^(a= BAND valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BAND,FOLLOW_BAND_in_arithmeticExpr2750); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2752);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2754);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:389:4: ^(a= BOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BOR,FOLLOW_BOR_in_arithmeticExpr2765); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2767);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2769);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 8 :
                    // EsperEPL2Ast.g:390:4: ^(a= BXOR valueExpr valueExpr )
                    {
                    a=(CommonTree)match(input,BXOR,FOLLOW_BXOR_in_arithmeticExpr2780); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2782);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2784);
                    valueExpr();

                    state._fsp--;


                    match(input, Token.UP, null); 
                     leaveNode(a); 

                    }
                    break;
                case 9 :
                    // EsperEPL2Ast.g:391:5: ^(a= CONCAT valueExpr valueExpr ( valueExpr )* )
                    {
                    a=(CommonTree)match(input,CONCAT,FOLLOW_CONCAT_in_arithmeticExpr2796); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2798);
                    valueExpr();

                    state._fsp--;

                    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2800);
                    valueExpr();

                    state._fsp--;

                    // EsperEPL2Ast.g:391:36: ( valueExpr )*
                    loop112:
                    do {
                        int alt112=2;
                        int LA112_0 = input.LA(1);

                        if ( ((LA112_0>=IN_SET && LA112_0<=REGEXP)||LA112_0==NOT_EXPR||(LA112_0>=SUM && LA112_0<=AVG)||(LA112_0>=COALESCE && LA112_0<=COUNT)||(LA112_0>=CASE && LA112_0<=CASE2)||(LA112_0>=PREVIOUS && LA112_0<=EXISTS)||(LA112_0>=INSTANCEOF && LA112_0<=CURRENT_TIMESTAMP)||(LA112_0>=EVAL_AND_EXPR && LA112_0<=EVAL_NOTEQUALS_EXPR)||LA112_0==EVENT_PROP_EXPR||(LA112_0>=CONCAT && LA112_0<=LIB_FUNCTION)||LA112_0==ARRAY_EXPR||(LA112_0>=NOT_IN_SET && LA112_0<=NOT_REGEXP)||(LA112_0>=IN_RANGE && LA112_0<=NOT_IN_SUBSELECT_EXPR)||LA112_0==SUBSTITUTION||(LA112_0>=INT_TYPE && LA112_0<=NULL_TYPE)||LA112_0==STAR||(LA112_0>=BAND && LA112_0<=BXOR)||(LA112_0>=LT && LA112_0<=GE)||(LA112_0>=PLUS && LA112_0<=MOD)) ) {
                            alt112=1;
                        }


                        switch (alt112) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:391:37: valueExpr
                    	    {
                    	    pushFollow(FOLLOW_valueExpr_in_arithmeticExpr2803);
                    	    valueExpr();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop112;
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
    // EsperEPL2Ast.g:394:1: libFunc : ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) ;
    public final void libFunc() throws RecognitionException {
        CommonTree l=null;

        try {
            // EsperEPL2Ast.g:395:2: ( ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* ) )
            // EsperEPL2Ast.g:395:5: ^(l= LIB_FUNCTION ( CLASS_IDENT )? IDENT ( DISTINCT )? ( valueExpr )* )
            {
            l=(CommonTree)match(input,LIB_FUNCTION,FOLLOW_LIB_FUNCTION_in_libFunc2824); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:395:22: ( CLASS_IDENT )?
            int alt114=2;
            int LA114_0 = input.LA(1);

            if ( (LA114_0==CLASS_IDENT) ) {
                alt114=1;
            }
            switch (alt114) {
                case 1 :
                    // EsperEPL2Ast.g:395:23: CLASS_IDENT
                    {
                    match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_libFunc2827); 

                    }
                    break;

            }

            match(input,IDENT,FOLLOW_IDENT_in_libFunc2831); 
            // EsperEPL2Ast.g:395:43: ( DISTINCT )?
            int alt115=2;
            int LA115_0 = input.LA(1);

            if ( (LA115_0==DISTINCT) ) {
                alt115=1;
            }
            switch (alt115) {
                case 1 :
                    // EsperEPL2Ast.g:395:44: DISTINCT
                    {
                    match(input,DISTINCT,FOLLOW_DISTINCT_in_libFunc2834); 

                    }
                    break;

            }

            // EsperEPL2Ast.g:395:55: ( valueExpr )*
            loop116:
            do {
                int alt116=2;
                int LA116_0 = input.LA(1);

                if ( ((LA116_0>=IN_SET && LA116_0<=REGEXP)||LA116_0==NOT_EXPR||(LA116_0>=SUM && LA116_0<=AVG)||(LA116_0>=COALESCE && LA116_0<=COUNT)||(LA116_0>=CASE && LA116_0<=CASE2)||(LA116_0>=PREVIOUS && LA116_0<=EXISTS)||(LA116_0>=INSTANCEOF && LA116_0<=CURRENT_TIMESTAMP)||(LA116_0>=EVAL_AND_EXPR && LA116_0<=EVAL_NOTEQUALS_EXPR)||LA116_0==EVENT_PROP_EXPR||(LA116_0>=CONCAT && LA116_0<=LIB_FUNCTION)||LA116_0==ARRAY_EXPR||(LA116_0>=NOT_IN_SET && LA116_0<=NOT_REGEXP)||(LA116_0>=IN_RANGE && LA116_0<=NOT_IN_SUBSELECT_EXPR)||LA116_0==SUBSTITUTION||(LA116_0>=INT_TYPE && LA116_0<=NULL_TYPE)||LA116_0==STAR||(LA116_0>=BAND && LA116_0<=BXOR)||(LA116_0>=LT && LA116_0<=GE)||(LA116_0>=PLUS && LA116_0<=MOD)) ) {
                    alt116=1;
                }


                switch (alt116) {
            	case 1 :
            	    // EsperEPL2Ast.g:395:56: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_libFunc2839);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop116;
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
    // EsperEPL2Ast.g:401:1: startPatternExpressionRule : exprChoice ;
    public final void startPatternExpressionRule() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:402:2: ( exprChoice )
            // EsperEPL2Ast.g:402:4: exprChoice
            {
            setIsPatternWalk(true);
            pushFollow(FOLLOW_exprChoice_in_startPatternExpressionRule2861);
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
    // EsperEPL2Ast.g:405:1: exprChoice : ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(n= NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) );
    public final void exprChoice() throws RecognitionException {
        CommonTree a=null;
        CommonTree n=null;
        CommonTree g=null;
        CommonTree m=null;

        try {
            // EsperEPL2Ast.g:406:2: ( atomicExpr | patternOp | ^(a= EVERY_EXPR exprChoice ) | ^(n= NOT_EXPR exprChoice ) | ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* ) | ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? ) )
            int alt120=6;
            switch ( input.LA(1) ) {
            case EVENT_FILTER_EXPR:
            case OBSERVER_EXPR:
                {
                alt120=1;
                }
                break;
            case OR_EXPR:
            case AND_EXPR:
            case FOLLOWED_BY_EXPR:
                {
                alt120=2;
                }
                break;
            case EVERY_EXPR:
                {
                alt120=3;
                }
                break;
            case NOT_EXPR:
                {
                alt120=4;
                }
                break;
            case GUARD_EXPR:
                {
                alt120=5;
                }
                break;
            case MATCH_UNTIL_EXPR:
                {
                alt120=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 120, 0, input);

                throw nvae;
            }

            switch (alt120) {
                case 1 :
                    // EsperEPL2Ast.g:406:5: atomicExpr
                    {
                    pushFollow(FOLLOW_atomicExpr_in_exprChoice2875);
                    atomicExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:407:4: patternOp
                    {
                    pushFollow(FOLLOW_patternOp_in_exprChoice2880);
                    patternOp();

                    state._fsp--;


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:408:5: ^(a= EVERY_EXPR exprChoice )
                    {
                    a=(CommonTree)match(input,EVERY_EXPR,FOLLOW_EVERY_EXPR_in_exprChoice2890); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2892);
                    exprChoice();

                    state._fsp--;

                     leaveNode(a); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:409:5: ^(n= NOT_EXPR exprChoice )
                    {
                    n=(CommonTree)match(input,NOT_EXPR,FOLLOW_NOT_EXPR_in_exprChoice2906); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2908);
                    exprChoice();

                    state._fsp--;

                     leaveNode(n); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:410:5: ^(g= GUARD_EXPR exprChoice IDENT IDENT ( valueExprWithTime )* )
                    {
                    g=(CommonTree)match(input,GUARD_EXPR,FOLLOW_GUARD_EXPR_in_exprChoice2922); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_exprChoice2924);
                    exprChoice();

                    state._fsp--;

                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice2926); 
                    match(input,IDENT,FOLLOW_IDENT_in_exprChoice2928); 
                    // EsperEPL2Ast.g:410:44: ( valueExprWithTime )*
                    loop117:
                    do {
                        int alt117=2;
                        int LA117_0 = input.LA(1);

                        if ( ((LA117_0>=IN_SET && LA117_0<=REGEXP)||LA117_0==NOT_EXPR||(LA117_0>=SUM && LA117_0<=AVG)||(LA117_0>=COALESCE && LA117_0<=COUNT)||(LA117_0>=CASE && LA117_0<=CASE2)||LA117_0==LAST||(LA117_0>=PREVIOUS && LA117_0<=EXISTS)||(LA117_0>=LW && LA117_0<=CURRENT_TIMESTAMP)||(LA117_0>=NUMERIC_PARAM_RANGE && LA117_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA117_0>=EVAL_AND_EXPR && LA117_0<=EVAL_NOTEQUALS_EXPR)||LA117_0==EVENT_PROP_EXPR||(LA117_0>=CONCAT && LA117_0<=LIB_FUNCTION)||(LA117_0>=TIME_PERIOD && LA117_0<=ARRAY_EXPR)||(LA117_0>=NOT_IN_SET && LA117_0<=NOT_REGEXP)||(LA117_0>=IN_RANGE && LA117_0<=NOT_IN_SUBSELECT_EXPR)||(LA117_0>=LAST_OPERATOR && LA117_0<=SUBSTITUTION)||(LA117_0>=NUMBERSETSTAR && LA117_0<=NULL_TYPE)||LA117_0==STAR||(LA117_0>=BAND && LA117_0<=BXOR)||(LA117_0>=LT && LA117_0<=GE)||(LA117_0>=PLUS && LA117_0<=MOD)) ) {
                            alt117=1;
                        }


                        switch (alt117) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:410:44: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_exprChoice2930);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop117;
                        }
                    } while (true);

                     leaveNode(g); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:411:4: ^(m= MATCH_UNTIL_EXPR ( matchUntilRange )? exprChoice ( exprChoice )? )
                    {
                    m=(CommonTree)match(input,MATCH_UNTIL_EXPR,FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice2944); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:411:26: ( matchUntilRange )?
                    int alt118=2;
                    int LA118_0 = input.LA(1);

                    if ( ((LA118_0>=MATCH_UNTIL_RANGE_HALFOPEN && LA118_0<=MATCH_UNTIL_RANGE_BOUNDED)) ) {
                        alt118=1;
                    }
                    switch (alt118) {
                        case 1 :
                            // EsperEPL2Ast.g:411:26: matchUntilRange
                            {
                            pushFollow(FOLLOW_matchUntilRange_in_exprChoice2946);
                            matchUntilRange();

                            state._fsp--;


                            }
                            break;

                    }

                    pushFollow(FOLLOW_exprChoice_in_exprChoice2949);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:411:54: ( exprChoice )?
                    int alt119=2;
                    int LA119_0 = input.LA(1);

                    if ( ((LA119_0>=OR_EXPR && LA119_0<=EVERY_EXPR)||LA119_0==FOLLOWED_BY_EXPR||LA119_0==EVENT_FILTER_EXPR||(LA119_0>=GUARD_EXPR && LA119_0<=OBSERVER_EXPR)||LA119_0==MATCH_UNTIL_EXPR) ) {
                        alt119=1;
                    }
                    switch (alt119) {
                        case 1 :
                            // EsperEPL2Ast.g:411:54: exprChoice
                            {
                            pushFollow(FOLLOW_exprChoice_in_exprChoice2951);
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
    // EsperEPL2Ast.g:414:1: patternOp : ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) );
    public final void patternOp() throws RecognitionException {
        CommonTree f=null;
        CommonTree o=null;
        CommonTree a=null;

        try {
            // EsperEPL2Ast.g:415:2: ( ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* ) | ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* ) )
            int alt124=3;
            switch ( input.LA(1) ) {
            case FOLLOWED_BY_EXPR:
                {
                alt124=1;
                }
                break;
            case OR_EXPR:
                {
                alt124=2;
                }
                break;
            case AND_EXPR:
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
                    // EsperEPL2Ast.g:415:4: ^(f= FOLLOWED_BY_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    f=(CommonTree)match(input,FOLLOWED_BY_EXPR,FOLLOW_FOLLOWED_BY_EXPR_in_patternOp2972); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp2974);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp2976);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:415:48: ( exprChoice )*
                    loop121:
                    do {
                        int alt121=2;
                        int LA121_0 = input.LA(1);

                        if ( ((LA121_0>=OR_EXPR && LA121_0<=EVERY_EXPR)||LA121_0==FOLLOWED_BY_EXPR||LA121_0==EVENT_FILTER_EXPR||(LA121_0>=GUARD_EXPR && LA121_0<=OBSERVER_EXPR)||LA121_0==MATCH_UNTIL_EXPR) ) {
                            alt121=1;
                        }


                        switch (alt121) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:415:49: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp2979);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop121;
                        }
                    } while (true);

                     leaveNode(f); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:416:5: ^(o= OR_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    o=(CommonTree)match(input,OR_EXPR,FOLLOW_OR_EXPR_in_patternOp2995); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp2997);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp2999);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:416:40: ( exprChoice )*
                    loop122:
                    do {
                        int alt122=2;
                        int LA122_0 = input.LA(1);

                        if ( ((LA122_0>=OR_EXPR && LA122_0<=EVERY_EXPR)||LA122_0==FOLLOWED_BY_EXPR||LA122_0==EVENT_FILTER_EXPR||(LA122_0>=GUARD_EXPR && LA122_0<=OBSERVER_EXPR)||LA122_0==MATCH_UNTIL_EXPR) ) {
                            alt122=1;
                        }


                        switch (alt122) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:416:41: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3002);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop122;
                        }
                    } while (true);

                     leaveNode(o); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:417:5: ^(a= AND_EXPR exprChoice exprChoice ( exprChoice )* )
                    {
                    a=(CommonTree)match(input,AND_EXPR,FOLLOW_AND_EXPR_in_patternOp3018); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_exprChoice_in_patternOp3020);
                    exprChoice();

                    state._fsp--;

                    pushFollow(FOLLOW_exprChoice_in_patternOp3022);
                    exprChoice();

                    state._fsp--;

                    // EsperEPL2Ast.g:417:41: ( exprChoice )*
                    loop123:
                    do {
                        int alt123=2;
                        int LA123_0 = input.LA(1);

                        if ( ((LA123_0>=OR_EXPR && LA123_0<=EVERY_EXPR)||LA123_0==FOLLOWED_BY_EXPR||LA123_0==EVENT_FILTER_EXPR||(LA123_0>=GUARD_EXPR && LA123_0<=OBSERVER_EXPR)||LA123_0==MATCH_UNTIL_EXPR) ) {
                            alt123=1;
                        }


                        switch (alt123) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:417:42: exprChoice
                    	    {
                    	    pushFollow(FOLLOW_exprChoice_in_patternOp3025);
                    	    exprChoice();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop123;
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
    // EsperEPL2Ast.g:420:1: atomicExpr : ( eventFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) );
    public final void atomicExpr() throws RecognitionException {
        CommonTree ac=null;

        try {
            // EsperEPL2Ast.g:421:2: ( eventFilterExpr | ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* ) )
            int alt126=2;
            int LA126_0 = input.LA(1);

            if ( (LA126_0==EVENT_FILTER_EXPR) ) {
                alt126=1;
            }
            else if ( (LA126_0==OBSERVER_EXPR) ) {
                alt126=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 126, 0, input);

                throw nvae;
            }
            switch (alt126) {
                case 1 :
                    // EsperEPL2Ast.g:421:4: eventFilterExpr
                    {
                    pushFollow(FOLLOW_eventFilterExpr_in_atomicExpr3044);
                    eventFilterExpr();

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:422:7: ^(ac= OBSERVER_EXPR IDENT IDENT ( valueExprWithTime )* )
                    {
                    ac=(CommonTree)match(input,OBSERVER_EXPR,FOLLOW_OBSERVER_EXPR_in_atomicExpr3056); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3058); 
                    match(input,IDENT,FOLLOW_IDENT_in_atomicExpr3060); 
                    // EsperEPL2Ast.g:422:39: ( valueExprWithTime )*
                    loop125:
                    do {
                        int alt125=2;
                        int LA125_0 = input.LA(1);

                        if ( ((LA125_0>=IN_SET && LA125_0<=REGEXP)||LA125_0==NOT_EXPR||(LA125_0>=SUM && LA125_0<=AVG)||(LA125_0>=COALESCE && LA125_0<=COUNT)||(LA125_0>=CASE && LA125_0<=CASE2)||LA125_0==LAST||(LA125_0>=PREVIOUS && LA125_0<=EXISTS)||(LA125_0>=LW && LA125_0<=CURRENT_TIMESTAMP)||(LA125_0>=NUMERIC_PARAM_RANGE && LA125_0<=OBJECT_PARAM_ORDERED_EXPR)||(LA125_0>=EVAL_AND_EXPR && LA125_0<=EVAL_NOTEQUALS_EXPR)||LA125_0==EVENT_PROP_EXPR||(LA125_0>=CONCAT && LA125_0<=LIB_FUNCTION)||(LA125_0>=TIME_PERIOD && LA125_0<=ARRAY_EXPR)||(LA125_0>=NOT_IN_SET && LA125_0<=NOT_REGEXP)||(LA125_0>=IN_RANGE && LA125_0<=NOT_IN_SUBSELECT_EXPR)||(LA125_0>=LAST_OPERATOR && LA125_0<=SUBSTITUTION)||(LA125_0>=NUMBERSETSTAR && LA125_0<=NULL_TYPE)||LA125_0==STAR||(LA125_0>=BAND && LA125_0<=BXOR)||(LA125_0>=LT && LA125_0<=GE)||(LA125_0>=PLUS && LA125_0<=MOD)) ) {
                            alt125=1;
                        }


                        switch (alt125) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:422:39: valueExprWithTime
                    	    {
                    	    pushFollow(FOLLOW_valueExprWithTime_in_atomicExpr3062);
                    	    valueExprWithTime();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop125;
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


    // $ANTLR start "eventFilterExpr"
    // EsperEPL2Ast.g:425:1: eventFilterExpr : ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* ) ;
    public final void eventFilterExpr() throws RecognitionException {
        CommonTree f=null;

        try {
            // EsperEPL2Ast.g:426:2: ( ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* ) )
            // EsperEPL2Ast.g:426:4: ^(f= EVENT_FILTER_EXPR ( IDENT )? CLASS_IDENT ( valueExpr )* )
            {
            f=(CommonTree)match(input,EVENT_FILTER_EXPR,FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr3082); 

            match(input, Token.DOWN, null); 
            // EsperEPL2Ast.g:426:27: ( IDENT )?
            int alt127=2;
            int LA127_0 = input.LA(1);

            if ( (LA127_0==IDENT) ) {
                alt127=1;
            }
            switch (alt127) {
                case 1 :
                    // EsperEPL2Ast.g:426:27: IDENT
                    {
                    match(input,IDENT,FOLLOW_IDENT_in_eventFilterExpr3084); 

                    }
                    break;

            }

            match(input,CLASS_IDENT,FOLLOW_CLASS_IDENT_in_eventFilterExpr3087); 
            // EsperEPL2Ast.g:426:46: ( valueExpr )*
            loop128:
            do {
                int alt128=2;
                int LA128_0 = input.LA(1);

                if ( ((LA128_0>=IN_SET && LA128_0<=REGEXP)||LA128_0==NOT_EXPR||(LA128_0>=SUM && LA128_0<=AVG)||(LA128_0>=COALESCE && LA128_0<=COUNT)||(LA128_0>=CASE && LA128_0<=CASE2)||(LA128_0>=PREVIOUS && LA128_0<=EXISTS)||(LA128_0>=INSTANCEOF && LA128_0<=CURRENT_TIMESTAMP)||(LA128_0>=EVAL_AND_EXPR && LA128_0<=EVAL_NOTEQUALS_EXPR)||LA128_0==EVENT_PROP_EXPR||(LA128_0>=CONCAT && LA128_0<=LIB_FUNCTION)||LA128_0==ARRAY_EXPR||(LA128_0>=NOT_IN_SET && LA128_0<=NOT_REGEXP)||(LA128_0>=IN_RANGE && LA128_0<=NOT_IN_SUBSELECT_EXPR)||LA128_0==SUBSTITUTION||(LA128_0>=INT_TYPE && LA128_0<=NULL_TYPE)||LA128_0==STAR||(LA128_0>=BAND && LA128_0<=BXOR)||(LA128_0>=LT && LA128_0<=GE)||(LA128_0>=PLUS && LA128_0<=MOD)) ) {
                    alt128=1;
                }


                switch (alt128) {
            	case 1 :
            	    // EsperEPL2Ast.g:426:47: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_eventFilterExpr3090);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop128;
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


    // $ANTLR start "matchUntilRange"
    // EsperEPL2Ast.g:429:1: matchUntilRange : ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) );
    public final void matchUntilRange() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:430:2: ( ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam ) | ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam ) )
            int alt129=4;
            switch ( input.LA(1) ) {
            case MATCH_UNTIL_RANGE_CLOSED:
                {
                alt129=1;
                }
                break;
            case MATCH_UNTIL_RANGE_BOUNDED:
                {
                alt129=2;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFCLOSED:
                {
                alt129=3;
                }
                break;
            case MATCH_UNTIL_RANGE_HALFOPEN:
                {
                alt129=4;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 129, 0, input);

                throw nvae;
            }

            switch (alt129) {
                case 1 :
                    // EsperEPL2Ast.g:430:4: ^( MATCH_UNTIL_RANGE_CLOSED matchUntilRangeParam matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_CLOSED,FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3109); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3111);
                    matchUntilRangeParam();

                    state._fsp--;

                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3113);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:431:5: ^( MATCH_UNTIL_RANGE_BOUNDED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_BOUNDED,FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3121); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3123);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:432:5: ^( MATCH_UNTIL_RANGE_HALFCLOSED matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFCLOSED,FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3131); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3133);
                    matchUntilRangeParam();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:433:4: ^( MATCH_UNTIL_RANGE_HALFOPEN matchUntilRangeParam )
                    {
                    match(input,MATCH_UNTIL_RANGE_HALFOPEN,FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3140); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_matchUntilRangeParam_in_matchUntilRange3142);
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
    // EsperEPL2Ast.g:436:1: matchUntilRangeParam : ( NUM_DOUBLE | NUM_INT );
    public final void matchUntilRangeParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:437:2: ( NUM_DOUBLE | NUM_INT )
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
    // EsperEPL2Ast.g:441:1: filterParam : ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) ;
    public final void filterParam() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:442:2: ( ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* ) )
            // EsperEPL2Ast.g:442:4: ^( EVENT_FILTER_PARAM valueExpr ( valueExpr )* )
            {
            match(input,EVENT_FILTER_PARAM,FOLLOW_EVENT_FILTER_PARAM_in_filterParam3171); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_filterParam3173);
            valueExpr();

            state._fsp--;

            // EsperEPL2Ast.g:442:35: ( valueExpr )*
            loop130:
            do {
                int alt130=2;
                int LA130_0 = input.LA(1);

                if ( ((LA130_0>=IN_SET && LA130_0<=REGEXP)||LA130_0==NOT_EXPR||(LA130_0>=SUM && LA130_0<=AVG)||(LA130_0>=COALESCE && LA130_0<=COUNT)||(LA130_0>=CASE && LA130_0<=CASE2)||(LA130_0>=PREVIOUS && LA130_0<=EXISTS)||(LA130_0>=INSTANCEOF && LA130_0<=CURRENT_TIMESTAMP)||(LA130_0>=EVAL_AND_EXPR && LA130_0<=EVAL_NOTEQUALS_EXPR)||LA130_0==EVENT_PROP_EXPR||(LA130_0>=CONCAT && LA130_0<=LIB_FUNCTION)||LA130_0==ARRAY_EXPR||(LA130_0>=NOT_IN_SET && LA130_0<=NOT_REGEXP)||(LA130_0>=IN_RANGE && LA130_0<=NOT_IN_SUBSELECT_EXPR)||LA130_0==SUBSTITUTION||(LA130_0>=INT_TYPE && LA130_0<=NULL_TYPE)||LA130_0==STAR||(LA130_0>=BAND && LA130_0<=BXOR)||(LA130_0>=LT && LA130_0<=GE)||(LA130_0>=PLUS && LA130_0<=MOD)) ) {
                    alt130=1;
                }


                switch (alt130) {
            	case 1 :
            	    // EsperEPL2Ast.g:442:36: valueExpr
            	    {
            	    pushFollow(FOLLOW_valueExpr_in_filterParam3176);
            	    valueExpr();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop130;
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
    // EsperEPL2Ast.g:445:1: filterParamComparator : ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) );
    public final void filterParamComparator() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:446:2: ( ^( EQUALS filterAtom ) | ^( NOT_EQUAL filterAtom ) | ^( LT filterAtom ) | ^( LE filterAtom ) | ^( GT filterAtom ) | ^( GE filterAtom ) | ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) ) | ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) | ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ) )
            int alt143=12;
            switch ( input.LA(1) ) {
            case EQUALS:
                {
                alt143=1;
                }
                break;
            case NOT_EQUAL:
                {
                alt143=2;
                }
                break;
            case LT:
                {
                alt143=3;
                }
                break;
            case LE:
                {
                alt143=4;
                }
                break;
            case GT:
                {
                alt143=5;
                }
                break;
            case GE:
                {
                alt143=6;
                }
                break;
            case EVENT_FILTER_RANGE:
                {
                alt143=7;
                }
                break;
            case EVENT_FILTER_NOT_RANGE:
                {
                alt143=8;
                }
                break;
            case EVENT_FILTER_IN:
                {
                alt143=9;
                }
                break;
            case EVENT_FILTER_NOT_IN:
                {
                alt143=10;
                }
                break;
            case EVENT_FILTER_BETWEEN:
                {
                alt143=11;
                }
                break;
            case EVENT_FILTER_NOT_BETWEEN:
                {
                alt143=12;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 143, 0, input);

                throw nvae;
            }

            switch (alt143) {
                case 1 :
                    // EsperEPL2Ast.g:446:4: ^( EQUALS filterAtom )
                    {
                    match(input,EQUALS,FOLLOW_EQUALS_in_filterParamComparator3192); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3194);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:447:4: ^( NOT_EQUAL filterAtom )
                    {
                    match(input,NOT_EQUAL,FOLLOW_NOT_EQUAL_in_filterParamComparator3201); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3203);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:448:4: ^( LT filterAtom )
                    {
                    match(input,LT,FOLLOW_LT_in_filterParamComparator3210); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3212);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:449:4: ^( LE filterAtom )
                    {
                    match(input,LE,FOLLOW_LE_in_filterParamComparator3219); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3221);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:450:4: ^( GT filterAtom )
                    {
                    match(input,GT,FOLLOW_GT_in_filterParamComparator3228); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3230);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:451:4: ^( GE filterAtom )
                    {
                    match(input,GE,FOLLOW_GE_in_filterParamComparator3237); 

                    match(input, Token.DOWN, null); 
                    pushFollow(FOLLOW_filterAtom_in_filterParamComparator3239);
                    filterAtom();

                    state._fsp--;


                    match(input, Token.UP, null); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:452:4: ^( EVENT_FILTER_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_RANGE,FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3246); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:452:41: ( constant[false] | filterIdentifier )
                    int alt131=2;
                    int LA131_0 = input.LA(1);

                    if ( ((LA131_0>=INT_TYPE && LA131_0<=NULL_TYPE)) ) {
                        alt131=1;
                    }
                    else if ( (LA131_0==EVENT_FILTER_IDENT) ) {
                        alt131=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 131, 0, input);

                        throw nvae;
                    }
                    switch (alt131) {
                        case 1 :
                            // EsperEPL2Ast.g:452:42: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3255);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:452:58: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3258);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:452:76: ( constant[false] | filterIdentifier )
                    int alt132=2;
                    int LA132_0 = input.LA(1);

                    if ( ((LA132_0>=INT_TYPE && LA132_0<=NULL_TYPE)) ) {
                        alt132=1;
                    }
                    else if ( (LA132_0==EVENT_FILTER_IDENT) ) {
                        alt132=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 132, 0, input);

                        throw nvae;
                    }
                    switch (alt132) {
                        case 1 :
                            // EsperEPL2Ast.g:452:77: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3262);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:452:93: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3265);
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
                    // EsperEPL2Ast.g:453:4: ^( EVENT_FILTER_NOT_RANGE ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_RANGE,FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3279); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:453:45: ( constant[false] | filterIdentifier )
                    int alt133=2;
                    int LA133_0 = input.LA(1);

                    if ( ((LA133_0>=INT_TYPE && LA133_0<=NULL_TYPE)) ) {
                        alt133=1;
                    }
                    else if ( (LA133_0==EVENT_FILTER_IDENT) ) {
                        alt133=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 133, 0, input);

                        throw nvae;
                    }
                    switch (alt133) {
                        case 1 :
                            // EsperEPL2Ast.g:453:46: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3288);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:453:62: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3291);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:453:80: ( constant[false] | filterIdentifier )
                    int alt134=2;
                    int LA134_0 = input.LA(1);

                    if ( ((LA134_0>=INT_TYPE && LA134_0<=NULL_TYPE)) ) {
                        alt134=1;
                    }
                    else if ( (LA134_0==EVENT_FILTER_IDENT) ) {
                        alt134=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 134, 0, input);

                        throw nvae;
                    }
                    switch (alt134) {
                        case 1 :
                            // EsperEPL2Ast.g:453:81: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3295);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:453:97: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3298);
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
                    // EsperEPL2Ast.g:454:4: ^( EVENT_FILTER_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_IN,FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3312); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:454:38: ( constant[false] | filterIdentifier )
                    int alt135=2;
                    int LA135_0 = input.LA(1);

                    if ( ((LA135_0>=INT_TYPE && LA135_0<=NULL_TYPE)) ) {
                        alt135=1;
                    }
                    else if ( (LA135_0==EVENT_FILTER_IDENT) ) {
                        alt135=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 135, 0, input);

                        throw nvae;
                    }
                    switch (alt135) {
                        case 1 :
                            // EsperEPL2Ast.g:454:39: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3321);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:454:55: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3324);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:454:73: ( constant[false] | filterIdentifier )*
                    loop136:
                    do {
                        int alt136=3;
                        int LA136_0 = input.LA(1);

                        if ( ((LA136_0>=INT_TYPE && LA136_0<=NULL_TYPE)) ) {
                            alt136=1;
                        }
                        else if ( (LA136_0==EVENT_FILTER_IDENT) ) {
                            alt136=2;
                        }


                        switch (alt136) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:454:74: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3328);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:454:90: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3331);
                    	    filterIdentifier();

                    	    state._fsp--;


                    	    }
                    	    break;

                    	default :
                    	    break loop136;
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
                    // EsperEPL2Ast.g:455:4: ^( EVENT_FILTER_NOT_IN ( LPAREN | LBRACK ) ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier )* ( RPAREN | RBRACK ) )
                    {
                    match(input,EVENT_FILTER_NOT_IN,FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3346); 

                    match(input, Token.DOWN, null); 
                    if ( input.LA(1)==LPAREN||input.LA(1)==LBRACK ) {
                        input.consume();
                        state.errorRecovery=false;
                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        throw mse;
                    }

                    // EsperEPL2Ast.g:455:42: ( constant[false] | filterIdentifier )
                    int alt137=2;
                    int LA137_0 = input.LA(1);

                    if ( ((LA137_0>=INT_TYPE && LA137_0<=NULL_TYPE)) ) {
                        alt137=1;
                    }
                    else if ( (LA137_0==EVENT_FILTER_IDENT) ) {
                        alt137=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 137, 0, input);

                        throw nvae;
                    }
                    switch (alt137) {
                        case 1 :
                            // EsperEPL2Ast.g:455:43: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3355);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:455:59: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3358);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:455:77: ( constant[false] | filterIdentifier )*
                    loop138:
                    do {
                        int alt138=3;
                        int LA138_0 = input.LA(1);

                        if ( ((LA138_0>=INT_TYPE && LA138_0<=NULL_TYPE)) ) {
                            alt138=1;
                        }
                        else if ( (LA138_0==EVENT_FILTER_IDENT) ) {
                            alt138=2;
                        }


                        switch (alt138) {
                    	case 1 :
                    	    // EsperEPL2Ast.g:455:78: constant[false]
                    	    {
                    	    pushFollow(FOLLOW_constant_in_filterParamComparator3362);
                    	    constant(false);

                    	    state._fsp--;


                    	    }
                    	    break;
                    	case 2 :
                    	    // EsperEPL2Ast.g:455:94: filterIdentifier
                    	    {
                    	    pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3365);
                    	    filterIdentifier();

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

                    }
                    break;
                case 11 :
                    // EsperEPL2Ast.g:456:4: ^( EVENT_FILTER_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_BETWEEN,FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3380); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:456:27: ( constant[false] | filterIdentifier )
                    int alt139=2;
                    int LA139_0 = input.LA(1);

                    if ( ((LA139_0>=INT_TYPE && LA139_0<=NULL_TYPE)) ) {
                        alt139=1;
                    }
                    else if ( (LA139_0==EVENT_FILTER_IDENT) ) {
                        alt139=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 139, 0, input);

                        throw nvae;
                    }
                    switch (alt139) {
                        case 1 :
                            // EsperEPL2Ast.g:456:28: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3383);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:456:44: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3386);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:456:62: ( constant[false] | filterIdentifier )
                    int alt140=2;
                    int LA140_0 = input.LA(1);

                    if ( ((LA140_0>=INT_TYPE && LA140_0<=NULL_TYPE)) ) {
                        alt140=1;
                    }
                    else if ( (LA140_0==EVENT_FILTER_IDENT) ) {
                        alt140=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 140, 0, input);

                        throw nvae;
                    }
                    switch (alt140) {
                        case 1 :
                            // EsperEPL2Ast.g:456:63: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3390);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:456:79: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3393);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }


                    match(input, Token.UP, null); 

                    }
                    break;
                case 12 :
                    // EsperEPL2Ast.g:457:4: ^( EVENT_FILTER_NOT_BETWEEN ( constant[false] | filterIdentifier ) ( constant[false] | filterIdentifier ) )
                    {
                    match(input,EVENT_FILTER_NOT_BETWEEN,FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3401); 

                    match(input, Token.DOWN, null); 
                    // EsperEPL2Ast.g:457:31: ( constant[false] | filterIdentifier )
                    int alt141=2;
                    int LA141_0 = input.LA(1);

                    if ( ((LA141_0>=INT_TYPE && LA141_0<=NULL_TYPE)) ) {
                        alt141=1;
                    }
                    else if ( (LA141_0==EVENT_FILTER_IDENT) ) {
                        alt141=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 141, 0, input);

                        throw nvae;
                    }
                    switch (alt141) {
                        case 1 :
                            // EsperEPL2Ast.g:457:32: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3404);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:457:48: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3407);
                            filterIdentifier();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:457:66: ( constant[false] | filterIdentifier )
                    int alt142=2;
                    int LA142_0 = input.LA(1);

                    if ( ((LA142_0>=INT_TYPE && LA142_0<=NULL_TYPE)) ) {
                        alt142=1;
                    }
                    else if ( (LA142_0==EVENT_FILTER_IDENT) ) {
                        alt142=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 142, 0, input);

                        throw nvae;
                    }
                    switch (alt142) {
                        case 1 :
                            // EsperEPL2Ast.g:457:67: constant[false]
                            {
                            pushFollow(FOLLOW_constant_in_filterParamComparator3411);
                            constant(false);

                            state._fsp--;


                            }
                            break;
                        case 2 :
                            // EsperEPL2Ast.g:457:83: filterIdentifier
                            {
                            pushFollow(FOLLOW_filterIdentifier_in_filterParamComparator3414);
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
    // EsperEPL2Ast.g:460:1: filterAtom : ( constant[false] | filterIdentifier );
    public final void filterAtom() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:461:2: ( constant[false] | filterIdentifier )
            int alt144=2;
            int LA144_0 = input.LA(1);

            if ( ((LA144_0>=INT_TYPE && LA144_0<=NULL_TYPE)) ) {
                alt144=1;
            }
            else if ( (LA144_0==EVENT_FILTER_IDENT) ) {
                alt144=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 144, 0, input);

                throw nvae;
            }
            switch (alt144) {
                case 1 :
                    // EsperEPL2Ast.g:461:4: constant[false]
                    {
                    pushFollow(FOLLOW_constant_in_filterAtom3428);
                    constant(false);

                    state._fsp--;


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:462:4: filterIdentifier
                    {
                    pushFollow(FOLLOW_filterIdentifier_in_filterAtom3434);
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
    // EsperEPL2Ast.g:464:1: filterIdentifier : ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr ) ;
    public final void filterIdentifier() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:465:2: ( ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr ) )
            // EsperEPL2Ast.g:465:4: ^( EVENT_FILTER_IDENT IDENT eventPropertyExpr )
            {
            match(input,EVENT_FILTER_IDENT,FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3445); 

            match(input, Token.DOWN, null); 
            match(input,IDENT,FOLLOW_IDENT_in_filterIdentifier3447); 
            pushFollow(FOLLOW_eventPropertyExpr_in_filterIdentifier3449);
            eventPropertyExpr();

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
    // EsperEPL2Ast.g:468:1: eventPropertyExpr : ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) ;
    public final void eventPropertyExpr() throws RecognitionException {
        CommonTree p=null;

        try {
            // EsperEPL2Ast.g:469:2: ( ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* ) )
            // EsperEPL2Ast.g:469:4: ^(p= EVENT_PROP_EXPR eventPropertyAtomic ( eventPropertyAtomic )* )
            {
            p=(CommonTree)match(input,EVENT_PROP_EXPR,FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3466); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3468);
            eventPropertyAtomic();

            state._fsp--;

            // EsperEPL2Ast.g:469:44: ( eventPropertyAtomic )*
            loop145:
            do {
                int alt145=2;
                int LA145_0 = input.LA(1);

                if ( ((LA145_0>=EVENT_PROP_SIMPLE && LA145_0<=EVENT_PROP_DYNAMIC_MAPPED)) ) {
                    alt145=1;
                }


                switch (alt145) {
            	case 1 :
            	    // EsperEPL2Ast.g:469:45: eventPropertyAtomic
            	    {
            	    pushFollow(FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3471);
            	    eventPropertyAtomic();

            	    state._fsp--;


            	    }
            	    break;

            	default :
            	    break loop145;
                }
            } while (true);


            match(input, Token.UP, null); 
             leaveNode(p); 

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
    // EsperEPL2Ast.g:472:1: eventPropertyAtomic : ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) );
    public final void eventPropertyAtomic() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:473:2: ( ^( EVENT_PROP_SIMPLE IDENT ) | ^( EVENT_PROP_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) | ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT ) | ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT ) | ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) ) )
            int alt146=6;
            switch ( input.LA(1) ) {
            case EVENT_PROP_SIMPLE:
                {
                alt146=1;
                }
                break;
            case EVENT_PROP_INDEXED:
                {
                alt146=2;
                }
                break;
            case EVENT_PROP_MAPPED:
                {
                alt146=3;
                }
                break;
            case EVENT_PROP_DYNAMIC_SIMPLE:
                {
                alt146=4;
                }
                break;
            case EVENT_PROP_DYNAMIC_INDEXED:
                {
                alt146=5;
                }
                break;
            case EVENT_PROP_DYNAMIC_MAPPED:
                {
                alt146=6;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 146, 0, input);

                throw nvae;
            }

            switch (alt146) {
                case 1 :
                    // EsperEPL2Ast.g:473:4: ^( EVENT_PROP_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_SIMPLE,FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3490); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3492); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:474:4: ^( EVENT_PROP_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_INDEXED,FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3499); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3501); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3503); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:475:4: ^( EVENT_PROP_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_MAPPED,FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3510); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3512); 
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
                    // EsperEPL2Ast.g:476:4: ^( EVENT_PROP_DYNAMIC_SIMPLE IDENT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_SIMPLE,FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3527); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3529); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:477:4: ^( EVENT_PROP_DYNAMIC_INDEXED IDENT NUM_INT )
                    {
                    match(input,EVENT_PROP_DYNAMIC_INDEXED,FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3536); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3538); 
                    match(input,NUM_INT,FOLLOW_NUM_INT_in_eventPropertyAtomic3540); 

                    match(input, Token.UP, null); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:478:4: ^( EVENT_PROP_DYNAMIC_MAPPED IDENT ( STRING_LITERAL | QUOTED_STRING_LITERAL ) )
                    {
                    match(input,EVENT_PROP_DYNAMIC_MAPPED,FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3547); 

                    match(input, Token.DOWN, null); 
                    match(input,IDENT,FOLLOW_IDENT_in_eventPropertyAtomic3549); 
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
    // EsperEPL2Ast.g:481:1: timePeriod : ^(t= TIME_PERIOD timePeriodDef ) ;
    public final void timePeriod() throws RecognitionException {
        CommonTree t=null;

        try {
            // EsperEPL2Ast.g:482:2: ( ^(t= TIME_PERIOD timePeriodDef ) )
            // EsperEPL2Ast.g:482:5: ^(t= TIME_PERIOD timePeriodDef )
            {
            t=(CommonTree)match(input,TIME_PERIOD,FOLLOW_TIME_PERIOD_in_timePeriod3576); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_timePeriodDef_in_timePeriod3578);
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
    // EsperEPL2Ast.g:485:1: timePeriodDef : ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart );
    public final void timePeriodDef() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:486:2: ( dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )? | hourPart ( minutePart )? ( secondPart )? ( millisecondPart )? | minutePart ( secondPart )? ( millisecondPart )? | secondPart ( millisecondPart )? | millisecondPart )
            int alt157=5;
            switch ( input.LA(1) ) {
            case DAY_PART:
                {
                alt157=1;
                }
                break;
            case HOUR_PART:
                {
                alt157=2;
                }
                break;
            case MINUTE_PART:
                {
                alt157=3;
                }
                break;
            case SECOND_PART:
                {
                alt157=4;
                }
                break;
            case MILLISECOND_PART:
                {
                alt157=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 157, 0, input);

                throw nvae;
            }

            switch (alt157) {
                case 1 :
                    // EsperEPL2Ast.g:486:5: dayPart ( hourPart )? ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_dayPart_in_timePeriodDef3594);
                    dayPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:486:13: ( hourPart )?
                    int alt147=2;
                    int LA147_0 = input.LA(1);

                    if ( (LA147_0==HOUR_PART) ) {
                        alt147=1;
                    }
                    switch (alt147) {
                        case 1 :
                            // EsperEPL2Ast.g:486:14: hourPart
                            {
                            pushFollow(FOLLOW_hourPart_in_timePeriodDef3597);
                            hourPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:486:25: ( minutePart )?
                    int alt148=2;
                    int LA148_0 = input.LA(1);

                    if ( (LA148_0==MINUTE_PART) ) {
                        alt148=1;
                    }
                    switch (alt148) {
                        case 1 :
                            // EsperEPL2Ast.g:486:26: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3602);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:486:39: ( secondPart )?
                    int alt149=2;
                    int LA149_0 = input.LA(1);

                    if ( (LA149_0==SECOND_PART) ) {
                        alt149=1;
                    }
                    switch (alt149) {
                        case 1 :
                            // EsperEPL2Ast.g:486:40: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3607);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:486:53: ( millisecondPart )?
                    int alt150=2;
                    int LA150_0 = input.LA(1);

                    if ( (LA150_0==MILLISECOND_PART) ) {
                        alt150=1;
                    }
                    switch (alt150) {
                        case 1 :
                            // EsperEPL2Ast.g:486:54: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3612);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:487:4: hourPart ( minutePart )? ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_hourPart_in_timePeriodDef3619);
                    hourPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:487:13: ( minutePart )?
                    int alt151=2;
                    int LA151_0 = input.LA(1);

                    if ( (LA151_0==MINUTE_PART) ) {
                        alt151=1;
                    }
                    switch (alt151) {
                        case 1 :
                            // EsperEPL2Ast.g:487:14: minutePart
                            {
                            pushFollow(FOLLOW_minutePart_in_timePeriodDef3622);
                            minutePart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:487:27: ( secondPart )?
                    int alt152=2;
                    int LA152_0 = input.LA(1);

                    if ( (LA152_0==SECOND_PART) ) {
                        alt152=1;
                    }
                    switch (alt152) {
                        case 1 :
                            // EsperEPL2Ast.g:487:28: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3627);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:487:41: ( millisecondPart )?
                    int alt153=2;
                    int LA153_0 = input.LA(1);

                    if ( (LA153_0==MILLISECOND_PART) ) {
                        alt153=1;
                    }
                    switch (alt153) {
                        case 1 :
                            // EsperEPL2Ast.g:487:42: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3632);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:488:4: minutePart ( secondPart )? ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_minutePart_in_timePeriodDef3639);
                    minutePart();

                    state._fsp--;

                    // EsperEPL2Ast.g:488:15: ( secondPart )?
                    int alt154=2;
                    int LA154_0 = input.LA(1);

                    if ( (LA154_0==SECOND_PART) ) {
                        alt154=1;
                    }
                    switch (alt154) {
                        case 1 :
                            // EsperEPL2Ast.g:488:16: secondPart
                            {
                            pushFollow(FOLLOW_secondPart_in_timePeriodDef3642);
                            secondPart();

                            state._fsp--;


                            }
                            break;

                    }

                    // EsperEPL2Ast.g:488:29: ( millisecondPart )?
                    int alt155=2;
                    int LA155_0 = input.LA(1);

                    if ( (LA155_0==MILLISECOND_PART) ) {
                        alt155=1;
                    }
                    switch (alt155) {
                        case 1 :
                            // EsperEPL2Ast.g:488:30: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3647);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:489:4: secondPart ( millisecondPart )?
                    {
                    pushFollow(FOLLOW_secondPart_in_timePeriodDef3654);
                    secondPart();

                    state._fsp--;

                    // EsperEPL2Ast.g:489:15: ( millisecondPart )?
                    int alt156=2;
                    int LA156_0 = input.LA(1);

                    if ( (LA156_0==MILLISECOND_PART) ) {
                        alt156=1;
                    }
                    switch (alt156) {
                        case 1 :
                            // EsperEPL2Ast.g:489:16: millisecondPart
                            {
                            pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3657);
                            millisecondPart();

                            state._fsp--;


                            }
                            break;

                    }


                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:490:4: millisecondPart
                    {
                    pushFollow(FOLLOW_millisecondPart_in_timePeriodDef3664);
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
    // EsperEPL2Ast.g:493:1: dayPart : ^( DAY_PART valueExpr ) ;
    public final void dayPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:494:2: ( ^( DAY_PART valueExpr ) )
            // EsperEPL2Ast.g:494:4: ^( DAY_PART valueExpr )
            {
            match(input,DAY_PART,FOLLOW_DAY_PART_in_dayPart3678); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_dayPart3680);
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
    // EsperEPL2Ast.g:497:1: hourPart : ^( HOUR_PART valueExpr ) ;
    public final void hourPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:498:2: ( ^( HOUR_PART valueExpr ) )
            // EsperEPL2Ast.g:498:4: ^( HOUR_PART valueExpr )
            {
            match(input,HOUR_PART,FOLLOW_HOUR_PART_in_hourPart3695); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_hourPart3697);
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
    // EsperEPL2Ast.g:501:1: minutePart : ^( MINUTE_PART valueExpr ) ;
    public final void minutePart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:502:2: ( ^( MINUTE_PART valueExpr ) )
            // EsperEPL2Ast.g:502:4: ^( MINUTE_PART valueExpr )
            {
            match(input,MINUTE_PART,FOLLOW_MINUTE_PART_in_minutePart3712); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_minutePart3714);
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
    // EsperEPL2Ast.g:505:1: secondPart : ^( SECOND_PART valueExpr ) ;
    public final void secondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:506:2: ( ^( SECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:506:4: ^( SECOND_PART valueExpr )
            {
            match(input,SECOND_PART,FOLLOW_SECOND_PART_in_secondPart3729); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_secondPart3731);
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
    // EsperEPL2Ast.g:509:1: millisecondPart : ^( MILLISECOND_PART valueExpr ) ;
    public final void millisecondPart() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:510:2: ( ^( MILLISECOND_PART valueExpr ) )
            // EsperEPL2Ast.g:510:4: ^( MILLISECOND_PART valueExpr )
            {
            match(input,MILLISECOND_PART,FOLLOW_MILLISECOND_PART_in_millisecondPart3746); 

            match(input, Token.DOWN, null); 
            pushFollow(FOLLOW_valueExpr_in_millisecondPart3748);
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
    // EsperEPL2Ast.g:513:1: substitution : s= SUBSTITUTION ;
    public final void substitution() throws RecognitionException {
        CommonTree s=null;

        try {
            // EsperEPL2Ast.g:514:2: (s= SUBSTITUTION )
            // EsperEPL2Ast.g:514:4: s= SUBSTITUTION
            {
            s=(CommonTree)match(input,SUBSTITUTION,FOLLOW_SUBSTITUTION_in_substitution3763); 
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
    // EsperEPL2Ast.g:517:1: constant[boolean isLeaveNode] : (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE );
    public final void constant(boolean isLeaveNode) throws RecognitionException {
        CommonTree c=null;

        try {
            // EsperEPL2Ast.g:518:2: (c= INT_TYPE | c= LONG_TYPE | c= FLOAT_TYPE | c= DOUBLE_TYPE | c= STRING_TYPE | c= BOOL_TYPE | c= NULL_TYPE )
            int alt158=7;
            switch ( input.LA(1) ) {
            case INT_TYPE:
                {
                alt158=1;
                }
                break;
            case LONG_TYPE:
                {
                alt158=2;
                }
                break;
            case FLOAT_TYPE:
                {
                alt158=3;
                }
                break;
            case DOUBLE_TYPE:
                {
                alt158=4;
                }
                break;
            case STRING_TYPE:
                {
                alt158=5;
                }
                break;
            case BOOL_TYPE:
                {
                alt158=6;
                }
                break;
            case NULL_TYPE:
                {
                alt158=7;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 158, 0, input);

                throw nvae;
            }

            switch (alt158) {
                case 1 :
                    // EsperEPL2Ast.g:518:4: c= INT_TYPE
                    {
                    c=(CommonTree)match(input,INT_TYPE,FOLLOW_INT_TYPE_in_constant3779); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 2 :
                    // EsperEPL2Ast.g:519:4: c= LONG_TYPE
                    {
                    c=(CommonTree)match(input,LONG_TYPE,FOLLOW_LONG_TYPE_in_constant3788); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 3 :
                    // EsperEPL2Ast.g:520:4: c= FLOAT_TYPE
                    {
                    c=(CommonTree)match(input,FLOAT_TYPE,FOLLOW_FLOAT_TYPE_in_constant3797); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 4 :
                    // EsperEPL2Ast.g:521:4: c= DOUBLE_TYPE
                    {
                    c=(CommonTree)match(input,DOUBLE_TYPE,FOLLOW_DOUBLE_TYPE_in_constant3806); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 5 :
                    // EsperEPL2Ast.g:522:11: c= STRING_TYPE
                    {
                    c=(CommonTree)match(input,STRING_TYPE,FOLLOW_STRING_TYPE_in_constant3822); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 6 :
                    // EsperEPL2Ast.g:523:11: c= BOOL_TYPE
                    {
                    c=(CommonTree)match(input,BOOL_TYPE,FOLLOW_BOOL_TYPE_in_constant3838); 
                     if (isLeaveNode) leaveNode(c); 

                    }
                    break;
                case 7 :
                    // EsperEPL2Ast.g:524:8: c= NULL_TYPE
                    {
                    c=(CommonTree)match(input,NULL_TYPE,FOLLOW_NULL_TYPE_in_constant3851); 
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
    // EsperEPL2Ast.g:527:1: number : ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE );
    public final void number() throws RecognitionException {
        try {
            // EsperEPL2Ast.g:528:2: ( INT_TYPE | LONG_TYPE | FLOAT_TYPE | DOUBLE_TYPE )
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
    public static final BitSet FOLLOW_eventFilterExpr_in_onExpr142 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0B00000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_onExpr146 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0B00000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_onExpr149 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0B00000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_onDeleteExpr_in_onExpr156 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSelectExpr_in_onExpr160 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_onSetExpr_in_onExpr164 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_DELETE_EXPR_in_onDeleteExpr184 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onExprFrom_in_onDeleteExpr186 = new BitSet(new long[]{0x0000000000000008L,0x0020000000000000L});
    public static final BitSet FOLLOW_whereClause_in_onDeleteExpr189 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SELECT_EXPR_in_onSelectExpr206 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_insertIntoExpr_in_onSelectExpr209 = new BitSet(new long[]{0x0000000000000000L,0xC000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_selectionList_in_onSelectExpr213 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0400000000000000L});
    public static final BitSet FOLLOW_onExprFrom_in_onSelectExpr215 = new BitSet(new long[]{0x0000000000000008L,0x0060000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_whereClause_in_onSelectExpr218 = new BitSet(new long[]{0x0000000000000008L,0x0040000000000000L,0x00000000000000C0L});
    public static final BitSet FOLLOW_groupByClause_in_onSelectExpr223 = new BitSet(new long[]{0x0000000000000008L,0x0040000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_havingClause_in_onSelectExpr228 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_orderByClause_in_onSelectExpr233 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ON_SET_EXPR_in_onSetExpr250 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr252 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_onSetAssignment_in_onSetExpr255 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_onSetAssignment270 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_onSetAssignment272 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ON_EXPR_FROM_in_onExprFrom284 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom286 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_onExprFrom289 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_EXPR_in_createWindowExpr307 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createWindowExpr309 = new BitSet(new long[]{0x0000000000000000L,0x0004800000000000L,0x0040000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_viewListExpr_in_createWindowExpr312 = new BitSet(new long[]{0x0000000000000000L,0x0004800000000000L,0x0040000000000000L,0x0000000000000008L});
    public static final BitSet FOLLOW_createSelectionList_in_createWindowExpr327 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_createWindowExpr330 = new BitSet(new long[]{0x0008000000000008L});
    public static final BitSet FOLLOW_createColTypeList_in_createWindowExpr359 = new BitSet(new long[]{0x0008000000000008L});
    public static final BitSet FOLLOW_createWindowExprInsert_in_createWindowExpr370 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERT_in_createWindowExprInsert388 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_createWindowExprInsert390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_WINDOW_SELECT_EXPR_in_createSelectionList407 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList409 = new BitSet(new long[]{0x0000000000000008L,0x4000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_createSelectionListElement_in_createSelectionList412 = new BitSet(new long[]{0x0000000000000008L,0x4000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_LIST_in_createColTypeList431 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList433 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_createColTypeListElement_in_createColTypeList436 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000000010L});
    public static final BitSet FOLLOW_CREATE_WINDOW_COL_TYPE_in_createColTypeListElement451 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement453 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_createColTypeListElement455 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_createSelectionListElement469 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_createSelectionListElement479 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_createSelectionListElement499 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement502 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_createSelectionListElement524 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_createSelectionListElement527 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CREATE_VARIABLE_EXPR_in_createVariableExpr563 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr565 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_createVariableExpr567 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_createVariableExpr570 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_insertIntoExpr_in_selectExpr588 = new BitSet(new long[]{0x0000000000000000L,0x2000000000000000L});
    public static final BitSet FOLLOW_selectClause_in_selectExpr594 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_fromClause_in_selectExpr599 = new BitSet(new long[]{0x0000000000000002L,0x0060000040000000L,0x00000000001700C0L});
    public static final BitSet FOLLOW_whereClause_in_selectExpr604 = new BitSet(new long[]{0x0000000000000002L,0x0040000040000000L,0x00000000001700C0L});
    public static final BitSet FOLLOW_groupByClause_in_selectExpr611 = new BitSet(new long[]{0x0000000000000002L,0x0040000040000000L,0x0000000000170080L});
    public static final BitSet FOLLOW_havingClause_in_selectExpr618 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L,0x0000000000170080L});
    public static final BitSet FOLLOW_outputLimitExpr_in_selectExpr625 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L,0x0000000000000080L});
    public static final BitSet FOLLOW_orderByClause_in_selectExpr632 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_rowLimitClause_in_selectExpr639 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INSERTINTO_EXPR_in_insertIntoExpr656 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_insertIntoExpr658 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExpr667 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000400000L});
    public static final BitSet FOLLOW_insertIntoExprCol_in_insertIntoExpr670 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSERTINTO_EXPRCOL_in_insertIntoExprCol689 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol691 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_insertIntoExprCol694 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_SELECTION_EXPR_in_selectClause712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_selectClause714 = new BitSet(new long[]{0x0000000000000000L,0xC000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_selectionList_in_selectClause727 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause741 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_streamExpression_in_fromClause744 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000000003DL});
    public static final BitSet FOLLOW_outerJoin_in_fromClause747 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x000000000000003DL});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList764 = new BitSet(new long[]{0x0000000000000002L,0xC000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_selectionListElement_in_selectionList767 = new BitSet(new long[]{0x0000000000000002L,0xC000000000000000L,0x0000010000000000L});
    public static final BitSet FOLLOW_WILDCARD_SELECT_in_selectionListElement783 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SELECTION_ELEMENT_EXPR_in_selectionListElement793 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_selectionListElement795 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement798 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SELECTION_STREAM_in_selectionListElement812 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement814 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_selectionListElement817 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_outerJoinIdent_in_outerJoin836 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LEFT_OUTERJOIN_EXPR_in_outerJoinIdent850 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent852 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent854 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent857 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent859 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_RIGHT_OUTERJOIN_EXPR_in_outerJoinIdent873 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent875 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent877 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent880 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent882 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_FULL_OUTERJOIN_EXPR_in_outerJoinIdent896 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent898 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent900 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent903 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent905 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_INNERJOIN_EXPR_in_outerJoinIdent919 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent921 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent923 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_outerJoinIdent928 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_streamExpression948 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_streamExpression951 = new BitSet(new long[]{0x3800000000000008L,0x0004000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_patternInclusionExpression_in_streamExpression955 = new BitSet(new long[]{0x3800000000000008L,0x0004000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_databaseJoinExpression_in_streamExpression959 = new BitSet(new long[]{0x3800000000000008L,0x0004000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_methodJoinExpression_in_streamExpression963 = new BitSet(new long[]{0x3800000000000008L,0x0004000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_viewListExpr_in_streamExpression967 = new BitSet(new long[]{0x3800000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_streamExpression972 = new BitSet(new long[]{0x3800000000000008L});
    public static final BitSet FOLLOW_UNIDIRECTIONAL_in_streamExpression977 = new BitSet(new long[]{0x3000000000000008L});
    public static final BitSet FOLLOW_set_in_streamExpression981 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PATTERN_INCL_EXPR_in_patternInclusionExpression1004 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternInclusionExpression1008 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DATABASE_JOIN_EXPR_in_databaseJoinExpression1025 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_databaseJoinExpression1027 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1029 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_set_in_databaseJoinExpression1037 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_METHOD_JOIN_EXPR_in_methodJoinExpression1058 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_methodJoinExpression1060 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_methodJoinExpression1062 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_methodJoinExpression1065 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1079 = new BitSet(new long[]{0x0000000000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_viewExpr_in_viewListExpr1082 = new BitSet(new long[]{0x0000000000000002L,0x0004000000000000L});
    public static final BitSet FOLLOW_VIEW_EXPR_in_viewExpr1099 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1101 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_viewExpr1103 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_viewExpr1106 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_WHERE_EXPR_in_whereClause1127 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_whereClause1129 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GROUP_BY_EXPR_in_groupByClause1147 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1149 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_groupByClause1152 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_ORDER_BY_EXPR_in_orderByClause1170 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1172 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_orderByElement_in_orderByClause1175 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000100L});
    public static final BitSet FOLLOW_ORDER_ELEMENT_EXPR_in_orderByElement1195 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_orderByElement1197 = new BitSet(new long[]{0x00C0000000000008L});
    public static final BitSet FOLLOW_set_in_orderByElement1199 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HAVING_EXPR_in_havingClause1222 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_havingClause1224 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_LIMIT_EXPR_in_outputLimitExpr1242 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1244 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x00000000000083C0L});
    public static final BitSet FOLLOW_number_in_outputLimitExpr1256 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IDENT_in_outputLimitExpr1258 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIMEPERIOD_LIMIT_EXPR_in_outputLimitExpr1275 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1277 = new BitSet(new long[]{0x000400001BE623C0L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_timePeriod_in_outputLimitExpr1288 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_in_outputLimitExpr1303 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1305 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000080000L});
    public static final BitSet FOLLOW_crontabLimitParameterSet_in_outputLimitExpr1316 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WHEN_LIMIT_EXPR_in_outputLimitExpr1331 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_outputLimitExpr1333 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_outputLimitExpr1344 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0B00000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_onSetExpr_in_outputLimitExpr1346 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_ROW_LIMIT_EXPR_in_rowLimitClause1365 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1368 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x00000000000183C0L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1370 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x00000000000183C0L});
    public static final BitSet FOLLOW_number_in_rowLimitClause1374 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_IDENT_in_rowLimitClause1376 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L,0x0000000000000000L,0x0000000000010000L});
    public static final BitSet FOLLOW_COMMA_in_rowLimitClause1380 = new BitSet(new long[]{0x0000000000000008L,0x0000000080000000L});
    public static final BitSet FOLLOW_OFFSET_in_rowLimitClause1383 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CRONTAB_LIMIT_EXPR_PARAM_in_crontabLimitParameterSet1401 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1403 = new BitSet(new long[]{0x000400001BE623C0L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1405 = new BitSet(new long[]{0x000400001BE623C0L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1407 = new BitSet(new long[]{0x000400001BE623C0L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1409 = new BitSet(new long[]{0x000400001BE623C0L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1411 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_crontabLimitParameterSet1413 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_relationalExpr1430 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1432 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1434 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_relationalExpr1446 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1448 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1450 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_relationalExpr1462 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1464 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1466 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_relationalExpr1477 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1479 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_relationalExpr1481 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_OR_EXPR_in_evalExprChoice1498 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1500 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1502 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1505 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_EVAL_AND_EXPR_in_evalExprChoice1519 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1521 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1523 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1526 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_EVAL_EQUALS_EXPR_in_evalExprChoice1540 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1542 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1544 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVAL_NOTEQUALS_EXPR_in_evalExprChoice1556 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1558 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1560 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_evalExprChoice1572 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_evalExprChoice1574 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_relationalExpr_in_evalExprChoice1585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_valueExpr1598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_substitution_in_valueExpr1604 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arithmeticExpr_in_valueExpr1610 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_valueExpr1617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_evalExprChoice_in_valueExpr1625 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_builtinFunc_in_valueExpr1630 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_libFunc_in_valueExpr1638 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_caseExpr_in_valueExpr1643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_inExpr_in_valueExpr1648 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_betweenExpr_in_valueExpr1654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_likeExpr_in_valueExpr1659 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_regExpExpr_in_valueExpr1664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_arrayExpr_in_valueExpr1669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectInExpr_in_valueExpr1674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectRowExpr_in_valueExpr1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_subSelectExistsExpr_in_valueExpr1687 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LAST_in_valueExprWithTime1700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LW_in_valueExprWithTime1709 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1716 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBJECT_PARAM_ORDERED_EXPR_in_valueExprWithTime1724 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_valueExprWithTime1726 = new BitSet(new long[]{0x00C0000000000000L});
    public static final BitSet FOLLOW_set_in_valueExprWithTime1728 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_rangeOperator_in_valueExprWithTime1741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_valueExprWithTime1747 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lastOperator_in_valueExprWithTime1752 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_weekDayOperator_in_valueExprWithTime1757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_LIST_in_valueExprWithTime1767 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_numericParameterList_in_valueExprWithTime1769 = new BitSet(new long[]{0x0000000000000008L,0x0000000500000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_NUMBERSETSTAR_in_valueExprWithTime1780 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_timePeriod_in_valueExprWithTime1787 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_constant_in_numericParameterList1800 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rangeOperator_in_numericParameterList1807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_frequencyOperator_in_numericParameterList1813 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_RANGE_in_rangeOperator1829 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_rangeOperator1832 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000200L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator1835 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000200L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator1837 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0008000000000200L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_rangeOperator1841 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_rangeOperator1844 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_rangeOperator1846 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NUMERIC_PARAM_FREQUENCY_in_frequencyOperator1867 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_frequencyOperator1870 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_frequencyOperator1873 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_frequencyOperator1875 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LAST_OPERATOR_in_lastOperator1894 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_lastOperator1897 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_lastOperator1900 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_lastOperator1902 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_WEEKDAY_OPERATOR_in_weekDayOperator1921 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_weekDayOperator1924 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_weekDayOperator1927 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_substitution_in_weekDayOperator1929 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSELECT_EXPR_in_subSelectRowExpr1950 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectRowExpr1952 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_SUBSELECT_EXPR_in_subSelectExistsExpr1971 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectExistsExpr1973 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_EXPR_in_subSelectInExpr1992 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr1994 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr1996 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SUBSELECT_EXPR_in_subSelectInExpr2008 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_subSelectInExpr2010 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0001000000000000L});
    public static final BitSet FOLLOW_subSelectInQueryExpr_in_subSelectInExpr2012 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_SUBSELECT_QUERY_EXPR_in_subSelectInQueryExpr2031 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_subQueryExpr_in_subSelectInQueryExpr2033 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_selectionListElement_in_subQueryExpr2049 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000001L});
    public static final BitSet FOLLOW_subSelectFilterExpr_in_subQueryExpr2051 = new BitSet(new long[]{0x0000000000000002L,0x0024000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_viewExpr_in_subQueryExpr2054 = new BitSet(new long[]{0x0000000000000002L,0x0024000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_subQueryExpr2059 = new BitSet(new long[]{0x0000000000000002L,0x0020000000000000L});
    public static final BitSet FOLLOW_whereClause_in_subQueryExpr2064 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STREAM_EXPR_in_subSelectFilterExpr2081 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventFilterExpr_in_subSelectFilterExpr2083 = new BitSet(new long[]{0x0000000000000008L,0x0004000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_viewListExpr_in_subSelectFilterExpr2086 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_subSelectFilterExpr2091 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CASE_in_caseExpr2112 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2115 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_CASE2_in_caseExpr2128 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_caseExpr2131 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_IN_SET_in_inExpr2151 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2153 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000480000L});
    public static final BitSet FOLLOW_set_in_inExpr2155 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2161 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38B01FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2164 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38B01FC0L});
    public static final BitSet FOLLOW_set_in_inExpr2168 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_SET_in_inExpr2183 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2185 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000480000L});
    public static final BitSet FOLLOW_set_in_inExpr2187 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2193 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38B01FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2196 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38B01FC0L});
    public static final BitSet FOLLOW_set_in_inExpr2200 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_IN_RANGE_in_inExpr2215 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2217 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000480000L});
    public static final BitSet FOLLOW_set_in_inExpr2219 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2225 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2227 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000900000L});
    public static final BitSet FOLLOW_set_in_inExpr2229 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_IN_RANGE_in_inExpr2244 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2246 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000480000L});
    public static final BitSet FOLLOW_set_in_inExpr2248 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2254 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_inExpr2256 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000900000L});
    public static final BitSet FOLLOW_set_in_inExpr2258 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BETWEEN_in_betweenExpr2281 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2283 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2285 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2287 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_BETWEEN_in_betweenExpr2298 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2300 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2302 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_betweenExpr2305 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_LIKE_in_likeExpr2325 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2327 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2329 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2332 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_LIKE_in_likeExpr2345 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2347 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2349 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_likeExpr2352 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_REGEXP_in_regExpExpr2371 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2373 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2375 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_REGEXP_in_regExpExpr2386 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2388 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_regExpExpr2390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUM_in_builtinFunc2409 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2412 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2416 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVG_in_builtinFunc2427 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2430 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2434 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COUNT_in_builtinFunc2445 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2449 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2453 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MEDIAN_in_builtinFunc2467 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2470 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2474 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STDDEV_in_builtinFunc2485 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2488 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2492 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_AVEDEV_in_builtinFunc2503 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_DISTINCT_in_builtinFunc2506 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2510 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_COALESCE_in_builtinFunc2522 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2524 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2526 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2529 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_PREVIOUS_in_builtinFunc2544 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2546 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2548 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_PRIOR_in_builtinFunc2560 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_NUM_INT_in_builtinFunc2564 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2566 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_INSTANCEOF_in_builtinFunc2578 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2580 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2582 = new BitSet(new long[]{0x0000000000000008L,0x0000800000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2585 = new BitSet(new long[]{0x0000000000000008L,0x0000800000000000L});
    public static final BitSet FOLLOW_CAST_in_builtinFunc2599 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_builtinFunc2601 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_builtinFunc2603 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EXISTS_in_builtinFunc2615 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_builtinFunc2617 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CURRENT_TIMESTAMP_in_builtinFunc2628 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_ARRAY_EXPR_in_arrayExpr2648 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arrayExpr2651 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_PLUS_in_arithmeticExpr2672 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2674 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2676 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUS_in_arithmeticExpr2688 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2690 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2692 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_DIV_in_arithmeticExpr2704 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2706 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2708 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_STAR_in_arithmeticExpr2719 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2721 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2723 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MOD_in_arithmeticExpr2735 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2737 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2739 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BAND_in_arithmeticExpr2750 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2752 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2754 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BOR_in_arithmeticExpr2765 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2767 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2769 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_BXOR_in_arithmeticExpr2780 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2782 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2784 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_CONCAT_in_arithmeticExpr2796 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2798 = new BitSet(new long[]{0x000000001BE623C0L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2800 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_arithmeticExpr2803 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_LIB_FUNCTION_in_libFunc2824 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_libFunc2827 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_libFunc2831 = new BitSet(new long[]{0x000020001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_DISTINCT_in_libFunc2834 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_libFunc2839 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_exprChoice_in_startPatternExpressionRule2861 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_atomicExpr_in_exprChoice2875 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_patternOp_in_exprChoice2880 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVERY_EXPR_in_exprChoice2890 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2892 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EXPR_in_exprChoice2906 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2908 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GUARD_EXPR_in_exprChoice2922 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2924 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice2926 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_exprChoice2928 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_exprChoice2930 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_MATCH_UNTIL_EXPR_in_exprChoice2944 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRange_in_exprChoice2946 = new BitSet(new long[]{0x0000000000007800L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2949 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_exprChoice2951 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_FOLLOWED_BY_EXPR_in_patternOp2972 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2974 = new BitSet(new long[]{0x0000000000007800L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2976 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2979 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_OR_EXPR_in_patternOp2995 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2997 = new BitSet(new long[]{0x0000000000007800L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp2999 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3002 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_AND_EXPR_in_patternOp3018 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3020 = new BitSet(new long[]{0x0000000000007800L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3022 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_exprChoice_in_patternOp3025 = new BitSet(new long[]{0x0000000000007808L,0x0003005000000000L,0x4000000000000000L});
    public static final BitSet FOLLOW_eventFilterExpr_in_atomicExpr3044 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OBSERVER_EXPR_in_atomicExpr3056 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3058 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000008000L});
    public static final BitSet FOLLOW_IDENT_in_atomicExpr3060 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_valueExprWithTime_in_atomicExpr3062 = new BitSet(new long[]{0x000400001BE623C8L,0x0F00000F000001EEL,0x000EFC1E0D800200L,0x000001EF38201FE0L});
    public static final BitSet FOLLOW_EVENT_FILTER_EXPR_in_eventFilterExpr3082 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventFilterExpr3084 = new BitSet(new long[]{0x0000000000000000L,0x0000800000000000L});
    public static final BitSet FOLLOW_CLASS_IDENT_in_eventFilterExpr3087 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_eventFilterExpr3090 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_CLOSED_in_matchUntilRange3109 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3111 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000002000L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3113 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_BOUNDED_in_matchUntilRange3121 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3123 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFCLOSED_in_matchUntilRange3131 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3133 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MATCH_UNTIL_RANGE_HALFOPEN_in_matchUntilRange3140 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_matchUntilRangeParam_in_matchUntilRange3142 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_set_in_matchUntilRangeParam0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_PARAM_in_filterParam3171 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3173 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_valueExpr_in_filterParam3176 = new BitSet(new long[]{0x000000001BE623C8L,0x0F000000000001CEL,0x0008FC1E09800200L,0x000001EF38201FC0L});
    public static final BitSet FOLLOW_EQUALS_in_filterParamComparator3192 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3194 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_NOT_EQUAL_in_filterParamComparator3201 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3203 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LT_in_filterParamComparator3210 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3212 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_LE_in_filterParamComparator3219 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3221 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GT_in_filterParamComparator3228 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3230 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_GE_in_filterParamComparator3237 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_filterAtom_in_filterParamComparator3239 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_RANGE_in_filterParamComparator3246 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3248 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3255 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3258 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3262 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000900000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3265 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000900000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3268 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_RANGE_in_filterParamComparator3279 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3281 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3288 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3291 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3295 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000900000L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3298 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000000900000L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3301 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_IN_in_filterParamComparator3312 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3314 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3321 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3324 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3328 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3331 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3335 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_IN_in_filterParamComparator3346 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3348 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3355 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3358 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3362 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3365 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000901FC0L});
    public static final BitSet FOLLOW_set_in_filterParamComparator3369 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_BETWEEN_in_filterParamComparator3380 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3383 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3386 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3390 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3393 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_FILTER_NOT_BETWEEN_in_filterParamComparator3401 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3404 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3407 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L,0x0000000000000000L,0x0000000000001FC0L});
    public static final BitSet FOLLOW_constant_in_filterParamComparator3411 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterParamComparator3414 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_constant_in_filterAtom3428 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_filterIdentifier_in_filterAtom3434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EVENT_FILTER_IDENT_in_filterIdentifier3445 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_filterIdentifier3447 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000200L});
    public static final BitSet FOLLOW_eventPropertyExpr_in_filterIdentifier3449 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_EXPR_in_eventPropertyExpr3466 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3468 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000000FC00L});
    public static final BitSet FOLLOW_eventPropertyAtomic_in_eventPropertyExpr3471 = new BitSet(new long[]{0x0000000000000008L,0x0000000000000000L,0x000000000000FC00L});
    public static final BitSet FOLLOW_EVENT_PROP_SIMPLE_in_eventPropertyAtomic3490 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3492 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_INDEXED_in_eventPropertyAtomic3499 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3501 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3503 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_MAPPED_in_eventPropertyAtomic3510 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3512 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3514 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_SIMPLE_in_eventPropertyAtomic3527 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3529 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_INDEXED_in_eventPropertyAtomic3536 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3538 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000080000000000L});
    public static final BitSet FOLLOW_NUM_INT_in_eventPropertyAtomic3540 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_EVENT_PROP_DYNAMIC_MAPPED_in_eventPropertyAtomic3547 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_IDENT_in_eventPropertyAtomic3549 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_set_in_eventPropertyAtomic3551 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_TIME_PERIOD_in_timePeriod3576 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_timePeriodDef_in_timePeriod3578 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_dayPart_in_timePeriodDef3594 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000001E0000000L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3597 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000001C0000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3602 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000180000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3607 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3612 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_hourPart_in_timePeriodDef3619 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000001C0000000L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3622 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000180000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3627 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minutePart_in_timePeriodDef3639 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000180000000L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3642 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_secondPart_in_timePeriodDef3654 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_millisecondPart_in_timePeriodDef3664 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DAY_PART_in_dayPart3678 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_dayPart3680 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_HOUR_PART_in_hourPart3695 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_hourPart3697 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MINUTE_PART_in_minutePart3712 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_minutePart3714 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SECOND_PART_in_secondPart3729 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_secondPart3731 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_MILLISECOND_PART_in_millisecondPart3746 = new BitSet(new long[]{0x0000000000000004L});
    public static final BitSet FOLLOW_valueExpr_in_millisecondPart3748 = new BitSet(new long[]{0x0000000000000008L});
    public static final BitSet FOLLOW_SUBSTITUTION_in_substitution3763 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INT_TYPE_in_constant3779 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LONG_TYPE_in_constant3788 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FLOAT_TYPE_in_constant3797 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DOUBLE_TYPE_in_constant3806 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_STRING_TYPE_in_constant3822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_BOOL_TYPE_in_constant3838 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NULL_TYPE_in_constant3851 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_number0 = new BitSet(new long[]{0x0000000000000002L});

}